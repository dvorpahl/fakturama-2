package com.sebulli.fakturama.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import com.sebulli.fakturama.model.Address;
import com.sebulli.fakturama.model.Address_;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.ContactCategory;
import com.sebulli.fakturama.model.ContactType;
import com.sebulli.fakturama.model.Debitor;
import com.sebulli.fakturama.model.Debitor_;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Creatable
public class DebitorsDAO extends AbstractDAO<Debitor> {

    @Inject
    private ContactCategoriesDAO contactCategoriesDAO;

    /**
     * One page of debitors for the current search/category/sort criteria - the DB-side
     * equivalent of what {@code CommonListItemMatcher}/{@code GlazedLists.textFilterator} used to
     * do in-memory over the whole, fully-loaded debitor table (see {@code ContactListTable}).
     */
    public List<Debitor> findPage(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType, final String orderByProperty,
            final boolean descending, final int firstResult, final int maxResults) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Debitor> criteria = cb.createQuery(Debitor.class);
        final Root<Debitor> root = criteria.from(Debitor.class);
        criteria.distinct(true).where(buildPageablePredicate(cb, root, searchTerm, categoryName, treeObjectType));
        if (StringUtils.isNotBlank(orderByProperty)) {
            // id last for a stable row order across pages when orderByProperty alone has ties
            // (e.g. two debitors with the same company name) - see ProductsDAO#findPage's comment.
            final jakarta.persistence.criteria.Order order = descending ? cb.desc(root.get(orderByProperty)) : cb.asc(root.get(orderByProperty));
            criteria.orderBy(order, cb.desc(root.get(Debitor_.id)));
        } else {
            criteria.orderBy(cb.asc(root.get(Debitor_.customerNumber)));
        }
        // Fetch joins MUST be added before createQuery() - see findForListView()'s comment.
        fetchContactRelations(root);
        final TypedQuery<Debitor> query = getEntityManager().createQuery(criteria);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return query.getResultList();
    }

    /** Row count for the same criteria as {@link #findPage}. */
    public long countPage(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        final Root<Debitor> root = criteria.from(Debitor.class);
        criteria.select(cb.countDistinct(root)).where(buildPageablePredicate(cb, root, searchTerm, categoryName, treeObjectType));
        return getEntityManager().createQuery(criteria).getSingleResult();
    }

    private Predicate buildPageablePredicate(final CriteriaBuilder cb, final Root<Debitor> root, final String searchTerm, final String categoryName,
            final TreeObjectType treeObjectType) {
        Predicate predicate = buildTreeListPredicate(cb, root, searchTerm);
        final Set<Long> categoryIds = resolveCategoryIds(categoryName, treeObjectType);
        if (categoryIds != null) {
            // An empty IN(...) is not "no restriction" here - EclipseLink can compile it as an
            // unrestricted predicate instead of "always false", which would silently show
            // everything for a category selection that (for whatever reason) resolved to no
            // matching categories. Make that case explicitly match nothing instead.
            predicate = cb.and(predicate, categoryIds.isEmpty() ? cb.disjunction() : root.get(Debitor_.categories).get("id").in(categoryIds));
        }
        return predicate;
    }

    /** See ProductsDAO#resolveCategoryIds's javadoc for why this walks the (small) category table in Java rather than in SQL. */
    private Set<Long> resolveCategoryIds(final String categoryName, final TreeObjectType treeObjectType) {
        if (treeObjectType == null || treeObjectType == TreeObjectType.ALL_NODE || treeObjectType == TreeObjectType.ROOT_NODE
                || treeObjectType == TreeObjectType.CONTACTS_ROOTNODE || treeObjectType == TreeObjectType.TRANSACTIONS_ROOTNODE
                || StringUtils.isBlank(categoryName)) {
            return null;
        }
        // TreeObject#getFullPathName() always prepends the tree's synthetic, empty-named root
        // (TopicTreeViewer: "root = new TreeObject(\"\")"), giving e.g. "/Sales" or
        // "/Sales/Returning" - strip that leading "/" to get the real category path, which then
        // matches 1:1 what CommonConverter.getCategoryName(category, "") computes for each real
        // ContactCategory (a real category's own getParent() chain never includes the synthetic UI
        // root - that's a TreeObject-only construct, not a real category row).
        final String realPath = StringUtils.stripStart(categoryName, "/");
        if (realPath.isEmpty()) {
            return null;
        }
        final String prefix = StringUtils.stripEnd(realPath, "/");
        final Set<Long> matchingIds = new HashSet<>();
        for (final ContactCategory category : contactCategoriesDAO.findAll()) {
            final String fullPath = com.sebulli.fakturama.converter.CommonConverter.getCategoryName(category, "");
            if (fullPath.equals(prefix) || fullPath.startsWith(prefix + "/")) {
                matchingIds.add(category.getId());
            }
        }
        return matchingIds;
    }

    @Override
    protected Set<Predicate> getRestrictions(final Debitor object, final CriteriaBuilder cb, final Root<Debitor> root) {
        /* Customer number, first
         * name, name and ZIP are compared. Customer number is only compared, if it
         * is set.
         */
        Set<Predicate> restrictions = new HashSet<>();
        // Compare customer number, only if it is set.
        if (StringUtils.isNotBlank(object.getCustomerNumber())) {
            restrictions.add(cb.equal(root.get(Debitor_.customerNumber), object.getCustomerNumber()));
        }
        // if the value is not set (null), then we use the empty String for comparison. 
        // Then we get no result (which is correct).
        restrictions.add(cb.equal(root.get(Debitor_.firstName), StringUtils.defaultString(object.getFirstName())));
        restrictions.add(cb.equal(root.get(Debitor_.name), StringUtils.defaultString(object.getName())));
        //    	TODO HIER BITTE NOCHMAL NACHSEHEN!!!
        //        if (object.getAddress() != null) {
        //            restrictions.add(cb.equal(root.get(Debitor_.address).get(Address_.zip), StringUtils.defaultString(object.getAddress().getZip())));
        //        } else {
        //            // set to an undefined value so we get no result (then the contact is not found in the database)
        //            restrictions.add(cb.equal(root.get(Debitor_.address).get(Address_.zip), "-1"));
        //        }
        return restrictions;
    }

    @Override
    public List<Debitor> findAll() {
        return findAll(false);
    }

    public List<Debitor> findForListView() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Debitor> query = cb.createQuery(getEntityClass());
        Root<Debitor> debitor = query.from(getEntityClass());
        query.distinct(true).select(debitor).where(cb.and(debitor.get(Debitor_.customerNumber).isNotNull(), cb.not(debitor.get(Debitor_.deleted))))
                .orderBy(cb.asc(debitor.get(Debitor_.customerNumber)));
        // Fetch joins MUST be added before createQuery() below - EclipseLink translates the
        // CriteriaQuery into its internal query representation at createQuery() time, so any
        // fetch() added afterwards on the (still mutable-looking) Root is silently a no-op: the
        // TypedQuery already ignores it. This was the actual, still-unfixed cause of the
        // thousands of per-row FKT_BANKACCOUNT/FKT_ADDRESS/FKT_ADDRESS_CONTACTTYPES SELECTs -
        // verified via SQL log: the "fetch-joined" query came back as a plain, join-less SELECT.
        fetchContactRelations(debitor);
        debitor.fetch(Debitor_.addresses, JoinType.LEFT);
        TypedQuery<Debitor> q = getEntityManager().createQuery(query);
        // Deliberately NOT using CACHE_STORE_MODE=REFRESH: combined with the fetch joins above,
        // REFRESH mode does not trust the joined-in relation data and independently re-queries
        // every one of Contact's eager @ManyToOne/@OneToMany relations (categories, payment,
        // bankAccount, addresses) one row at a time regardless - verified against DocumentsDAO's
        // identical bug (see its findPage() javadoc): here it turned a few thousand debitors into
        // ~9000 extra per-row SELECTs against FKT_BANKACCOUNT/FKT_ADDRESS/FKT_ADDRESS_CONTACTTYPES
        // alone, running on every application startup regardless of whether this list was even open.
        q.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return q.getResultList();
    }

    /**
     * Explicitly fetch-joins every one of {@link Contact}'s eager relations ({@code categories},
     * {@code payment}, {@code bankAccount}, {@code addresses}) into the same query instead of
     * letting EclipseLink resolve each one with its own round trip per row - none of these
     * mappings can be made truly {@code LAZY} (weaving isn't enabled in this OSGi launch), so
     * EclipseLink always resolves them eagerly one way or another; fetch-joining explicitly is
     * the only way to keep that to a single query.
     */
    private void fetchContactRelations(final Root<Debitor> root) {
        root.fetch(Debitor_.categories, JoinType.LEFT);
        root.fetch(Debitor_.payment, JoinType.LEFT);
        root.fetch(Debitor_.bankAccount, JoinType.LEFT);
    }

    /**
     * Finds all {@link DebitorAddress}es for a given {@link ContactType}. This
     * is used for selection of a certain {@link Contact} in the
     * DocumentEditor's address field. If a {@link Debitor} has only one address
     * then this one is used. If a {@link Debitor} has more than one address and
     * many of them matches the given {@link ContactType}, all of these matching
     * {@link Debitor}s are returned.
     * 
     * @param contactType
     * 
     * @return List of {@link DebitorAddress}es for a certain
     *         {@link ContactType}.
     */
    public List<DebitorAddress> findForTreeListView(final ContactType contactType) {
        return findForTreeListView(contactType, null, null, null);
    }

    /**
     * @param maxDebitors caps how many {@link Debitor}s (not rows - a debitor with several
     *            matching addresses still yields several {@link DebitorAddress} rows) are loaded,
     *            or {@code null} for no cap. Used for a fast, small first fill of the contact-tree
     *            dialog (see {@code ContactTreeListTable#createListTable}) that's cheap enough to
     *            run synchronously while the full list loads in the background - showing the
     *            dialog with a handful of real rows immediately instead of visibly empty.
     */
    public List<DebitorAddress> findForTreeListView(final ContactType contactType, final Integer maxDebitors) {
        return findForTreeListView(contactType, null, null, maxDebitors);
    }

    /**
     * @param searchTerm free-text search across customer number/first name/name/company/zip/city,
     *            or blank/null for no text filter - a real {@code WHERE}, not client-side
     *            filtering of an already-loaded list (see {@code ContactTreeListTable}'s debounced
     *            search).
     * @param firstResult zero-based debitor offset (not row offset), or {@code null} for 0.
     * @param maxDebitors caps how many {@link Debitor}s are loaded from {@code firstResult}, or
     *            {@code null} for no cap.
     */
    public List<DebitorAddress> findForTreeListView(final ContactType contactType, final String searchTerm, final Integer firstResult,
            final Integer maxDebitors) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        // Step 1: page of matching debitor IDs only - no collection fetch join here, so
        // firstResult/maxResults slice real debitors, not SQL rows multiplied by a join.
        // (A debitor with N addresses, or one address with M contact types, turns one logical
        // debitor into N*M joined rows - LIMIT/OFFSET against that is the classic broken-JPA-
        // pagination trap, and it doesn't stop at wrong page boundaries: it also duplicates
        // debitors within a page, which is what actually surfaced this - see findForListView.)
        final CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        final Root<Debitor> idRoot = idQuery.from(getEntityClass());
        idQuery.select(idRoot.get(Debitor_.id)).where(buildTreeListPredicate(cb, idRoot, searchTerm)).orderBy(cb.asc(idRoot.get(Debitor_.customerNumber)));
        final TypedQuery<Long> idTypedQuery = getEntityManager().createQuery(idQuery);
        if (firstResult != null) {
            idTypedQuery.setFirstResult(firstResult);
        }
        if (maxDebitors != null) {
            idTypedQuery.setMaxResults(maxDebitors);
        }
        final List<Long> ids = idTypedQuery.getResultList();
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        // Step 2: the full graph (fetch-joined, so no per-row N+1) for exactly those debitors -
        // no LIMIT here, so the join multiplication above can't corrupt which page we got, only
        // (harmlessly) repeat a debitor within the already-correct result list - deduped below.
        final CriteriaQuery<Debitor> query = cb.createQuery(getEntityClass());
        final Root<Debitor> debitorQuery = query.from(getEntityClass());
        query.distinct(true).select(debitorQuery).where(debitorQuery.get(Debitor_.id).in(ids)).orderBy(cb.asc(debitorQuery.get(Debitor_.customerNumber)));
        // Fetch joins MUST be added before createQuery() - see findForListView()'s comment for
        // why (EclipseLink translates the CriteriaQuery at createQuery() time; fetches added to
        // the Root afterwards are silently ignored, which is exactly what was still happening here).
        fetchContactRelations(debitorQuery);
        debitorQuery.fetch(Debitor_.addresses, JoinType.LEFT).fetch(Address_.contactTypes, JoinType.LEFT);
        final TypedQuery<Debitor> q = getEntityManager().createQuery(query);
        // See findForListView() - REFRESH mode defeats fetch joins and re-queries every eager
        // relation one row at a time, regardless of what's joined in.
        q.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        final List<Debitor> debitorsFromDb = new ArrayList<>(new java.util.LinkedHashSet<>(q.getResultList()));
        final List<DebitorAddress> treeItems = new ArrayList<>();

        // Create a list of DebitorAddresses
        for (final Debitor debitor : debitorsFromDb) {
            final List<Address> addresses = debitor.getAddresses();
            if (!addresses.isEmpty()) {
				addresses.stream()
                    .filter(adr -> adr.getContactTypes().isEmpty() || adr.getContactTypes().contains(contactType))
                    .forEach(adr -> treeItems.add(createDebitorTreeItem(debitor, adr)));
            }
        }

        return treeItems;
    }

    /** Counts the {@link Debitor}s (not {@link DebitorAddress} rows) matching the same criteria as {@link #findForTreeListView}. */
    public long countForTreeListView(final String searchTerm) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> query = cb.createQuery(Long.class);
        final Root<Debitor> debitorQuery = query.from(getEntityClass());
        query.select(cb.countDistinct(debitorQuery)).where(buildTreeListPredicate(cb, debitorQuery, searchTerm));
        return getEntityManager().createQuery(query).getSingleResult().longValue();
    }

    private Predicate buildTreeListPredicate(final CriteriaBuilder cb, final Root<Debitor> debitorQuery, final String searchTerm) {
        Predicate predicate = cb.and(debitorQuery.get(Debitor_.customerNumber).isNotNull(), cb.not(debitorQuery.get(Debitor_.deleted)));
        if (StringUtils.isNotBlank(searchTerm)) {
            final String likeTerm = "%" + searchTerm.toLowerCase(java.util.Locale.ROOT) + "%";
            final jakarta.persistence.criteria.Join<Debitor, Address> addressJoin = debitorQuery.join(Debitor_.addresses, JoinType.LEFT);
            predicate = cb.and(predicate,
                    cb.or(cb.like(cb.lower(debitorQuery.get(Debitor_.customerNumber)), likeTerm),
                            cb.like(cb.lower(debitorQuery.get(Debitor_.firstName)), likeTerm),
                            cb.like(cb.lower(debitorQuery.get(Debitor_.name)), likeTerm),
                            cb.like(cb.lower(debitorQuery.get(Debitor_.company)), likeTerm),
                            cb.like(cb.lower(addressJoin.get(Address_.zip)), likeTerm),
                            cb.like(cb.lower(addressJoin.get(Address_.city)), likeTerm)));
        }
        return predicate;
    }

    /*
    
       Müller | Fritz | Bahnhofstraße 3 | 05885 | Friesland      => ContactType.INVOICE
    v  Meyer  | Johannes 
     --   | ---   | Hauptstraße 4  | 08554 | Adorf           => ContactType.INVOICE
     --   | ---   | Carolastraße 3 | 18554 | Bedorf          => ContactType.INVOICE, ContactType.DELIVERY
       Emsland | Jaqueline | Karlstraße 9 | 82282 | Rühmkirchen  => ContactType.INVOICE
    
     */

    private DebitorAddress createDebitorTreeItem(final Debitor debitor2, final Address adr) {
        return new DebitorAddress(debitor2, adr);
    }

    public Debitor findByDebitorNumber(final String debNo) {
        Debitor result = null;
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Debitor> query = cb.createQuery(getEntityClass());
        Root<Debitor> debitor = query.from(getEntityClass());
        query.select(debitor).where(cb.and(cb.equal(debitor.get(Debitor_.customerNumber), debNo), cb.not(debitor.get(Debitor_.deleted))));
        TypedQuery<Debitor> q = getEntityManager().createQuery(query);
        q.setHint(QueryHints.CACHE_STORE_MODE, "REFRESH");
        q.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        try {
            result = q.getSingleResult();
        } catch (NoResultException e) {
            // no result means we return a null value
        } catch (NonUniqueResultException nurex) {
            // not so good - we prefer to not return any data...
        }
        return result;
    }

    @Override
    public List<Debitor> findAll(final boolean forceRead) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Debitor> query = cb.createQuery(getEntityClass());
        Root<Debitor> root = query.from(getEntityClass());
        /*
         * Since referenced contacts are stored as own data set we have to
         * test for NULL customer number. If customer number is NULL we have
         * an alternate contact which belongs to a "legal" contact and thus we 
         * don't have to show them up.
         */
        query.distinct(true);
        query.where(cb.and(cb.not(root.get(Debitor_.deleted)), cb.isNotNull(root.get(Debitor_.customerNumber))));
        // Fetch joins MUST be added before createQuery() - see findForListView()'s comment.
        fetchContactRelations(root);
        root.fetch(Debitor_.addresses, JoinType.LEFT);
        TypedQuery<Debitor> q = getEntityManager().createQuery(query);
        // No CACHE_STORE_MODE=REFRESH here - see findForListView()'s comment: combined with fetch
        // joins it defeats them and re-queries every eager relation one row at a time.
        q.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return q.getResultList();
    }

    /**
     * Get a list of all categories stored for {@link Debitor}s.
     * 
     * @return list of all categories
     */
    public Collection<String> getCategoryStrings() {
        List<String> result = getEntityManager().createQuery("select distinct c.category from Debitor c where c.deleted = false", String.class).getResultList();
        return result;
    }

    @Override
    protected Class<Debitor> getEntityClass() {
        return Debitor.class;
    }

    /**
     * Gets the all visible properties of this VAT object.
     * 
     * @return String[] of visible VAT properties
     */
    public String[] getVisibleProperties() {
        return new String[] { Debitor_.customerNumber.getName(), Debitor_.firstName.getName(), Debitor_.name.getName(), Debitor_.company.getName(),
                Address_.zip.getName(), Address_.city.getName() };
    }
}
