package com.sebulli.fakturama.dao;

import java.util.ArrayList;
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
import com.sebulli.fakturama.model.Creditor;
import com.sebulli.fakturama.model.Creditor_;
import com.sebulli.fakturama.model.Debitor;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Creatable
public class CreditorsDAO extends AbstractDAO<Creditor> {

    @Inject
    private ContactCategoriesDAO contactCategoriesDAO;

    /** See DebitorsDAO#findPage's javadoc for the full story. */
    public List<Creditor> findPage(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType, final String orderByProperty,
            final boolean descending, final int firstResult, final int maxResults) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Creditor> criteria = cb.createQuery(Creditor.class);
        final Root<Creditor> root = criteria.from(Creditor.class);
        criteria.distinct(true).where(buildPageablePredicate(cb, root, searchTerm, categoryName, treeObjectType));
        if (StringUtils.isNotBlank(orderByProperty)) {
            // id last for a stable row order across pages when orderByProperty alone has ties -
            // see ProductsDAO#findPage's comment / DebitorsDAO#findPage's identical fix.
            final jakarta.persistence.criteria.Order order = descending ? cb.desc(root.get(orderByProperty)) : cb.asc(root.get(orderByProperty));
            criteria.orderBy(order, cb.desc(root.get(Creditor_.id)));
        } else {
            criteria.orderBy(cb.asc(root.get(Creditor_.customerNumber)));
        }
        // Fetch joins MUST be added before createQuery() - see findAll(boolean)'s comment. Also
        // fetch-joins addresses, same as every other Creditor query below - findPage() was the one
        // caller still missing it (see DebitorsDAO#findPage's identical fix/comment).
        root.fetch(Creditor_.categories, JoinType.LEFT);
        root.fetch(Creditor_.payment, JoinType.LEFT);
        root.fetch(Creditor_.bankAccount, JoinType.LEFT);
        root.fetch(Creditor_.addresses, JoinType.LEFT);
        final TypedQuery<Creditor> query = getEntityManager().createQuery(criteria);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return query.getResultList();
    }

    /** Row count for the same criteria as {@link #findPage}. */
    public long countPage(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        final Root<Creditor> root = criteria.from(Creditor.class);
        criteria.select(cb.countDistinct(root)).where(buildPageablePredicate(cb, root, searchTerm, categoryName, treeObjectType));
        return getEntityManager().createQuery(criteria).getSingleResult();
    }

    private Predicate buildPageablePredicate(final CriteriaBuilder cb, final Root<Creditor> root, final String searchTerm, final String categoryName,
            final TreeObjectType treeObjectType) {
        Predicate predicate = buildTreeListPredicate(cb, root, searchTerm);
        final Set<Long> categoryIds = resolveCategoryIds(categoryName, treeObjectType);
        if (categoryIds != null) {
            // An empty IN(...) is not "no restriction" here - EclipseLink can compile it as an
            // unrestricted predicate instead of "always false", which would silently show
            // everything for a category selection that (for whatever reason) resolved to no
            // matching categories. Make that case explicitly match nothing instead.
            predicate = cb.and(predicate, categoryIds.isEmpty() ? cb.disjunction() : root.get(Creditor_.categories).get("id").in(categoryIds));
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
        // TreeObject#getFullPathName() always prepends the tree's synthetic, empty-named root -
        // see DebitorsDAO#resolveCategoryIds's javadoc for the full story.
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
    public List<Creditor> findAll() {
        return findAll(false);
    }

    @Override
    protected Set<Predicate> getRestrictions(final Creditor object, final CriteriaBuilder criteriaBuilder, final Root<Creditor> root) {
        throw new RuntimeException("HIER BITTE NOCHMAL NACHSEHEN!!!");
    }

    @Override
    public List<Creditor> findAll(final boolean forceRead) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Creditor> criteria = cb.createQuery(getEntityClass());
        Root<Creditor> root = criteria.from(getEntityClass());
        /*
         * Since referenced contacts are stored as own data set we have to
         * test for NULL customer number. If customer number is NULL we have
         * an alternate contact which belongs to a "legal" contact and thus we 
         * don't have to show them up.
         */
        criteria.distinct(true);
        CriteriaQuery<Creditor> cq = criteria.where(cb.and(cb.not(root.get(Creditor_.deleted)), cb.isNotNull(root.get(Creditor_.customerNumber))));
        // Fetch joins MUST be added before createQuery() below - EclipseLink translates the
        // CriteriaQuery into its internal query representation at createQuery() time, so any
        // fetch() added afterwards on the Root is silently a no-op. This was the actual, still-
        // unfixed cause of the per-row FKT_BANKACCOUNT/FKT_ADDRESS/FKT_ADDRESS_CONTACTTYPES
        // SELECTs - verified via SQL log: the "fetch-joined" query came back plain, no JOIN at all.
        root.fetch(Creditor_.categories, JoinType.LEFT);
        root.fetch(Creditor_.payment, JoinType.LEFT);
        root.fetch(Creditor_.bankAccount, JoinType.LEFT);
        root.fetch(Creditor_.addresses, JoinType.LEFT);
        TypedQuery<Creditor> query = getEntityManager().createQuery(cq);
        // No CACHE_STORE_MODE=REFRESH here - see findForTreeListView()'s comment: combined with
        // fetch joins it defeats them and re-queries every eager relation one row at a time.
        query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return query.getResultList();
    }

    @Override
    protected Class<Creditor> getEntityClass() {
        return Creditor.class;
    }

    /**
     * Gets the all visible properties of this VAT object.
     * 
     * @return String[] of visible VAT properties
     */
    public String[] getVisibleProperties() {
        return new String[] { Creditor_.customerNumber.getName(), Creditor_.firstName.getName(), Creditor_.name.getName(), Creditor_.company.getName(),
                Creditor_.addresses.getName() + "." + Address_.zip.getName(), Creditor_.addresses.getName() + "." + Address_.city.getName() };
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

    /** @param maxDebitors caps how many Creditors are loaded, or {@code null} for no cap - see DebitorsDAO's overload for the full story. */
    public List<DebitorAddress> findForTreeListView(final ContactType contactType, final Integer maxDebitors) {
        return findForTreeListView(contactType, null, null, maxDebitors);
    }

    /** See DebitorsDAO's overload for the full story (search predicate, offset/limit paging for the progressive background load). */
    public List<DebitorAddress> findForTreeListView(final ContactType contactType, final String searchTerm, final Integer firstResult,
            final Integer maxDebitors) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        // Step 1: page of matching creditor IDs only - no collection fetch join here, so
        // firstResult/maxResults slice real creditors, not SQL rows multiplied by a join. See
        // DebitorsDAO's identical fix for the full story (a creditor with several addresses, or
        // one address with several contact types, turns one logical creditor into several joined
        // rows - LIMIT/OFFSET against that both breaks paging *and* duplicates rows on a page).
        final CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        final Root<Creditor> idRoot = idQuery.from(getEntityClass());
        idQuery.select(idRoot.get(Creditor_.id)).where(buildTreeListPredicate(cb, idRoot, searchTerm)).orderBy(cb.asc(idRoot.get(Creditor_.customerNumber)));
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

        // Step 2: the full graph (fetch-joined, so no per-row N+1) for exactly those creditors -
        // no LIMIT here, so the join multiplication above can't corrupt which page we got, only
        // (harmlessly) repeat a creditor within the already-correct result list - deduped below.
        final CriteriaQuery<Creditor> query = cb.createQuery(getEntityClass());
        final Root<Creditor> debitorQuery = query.from(getEntityClass());
        query.distinct(true).select(debitorQuery).where(debitorQuery.get(Creditor_.id).in(ids)).orderBy(cb.asc(debitorQuery.get(Creditor_.customerNumber)));
        // Fetch joins MUST be added before createQuery() - see findAll()'s comment: EclipseLink
        // translates the CriteriaQuery at createQuery() time, so fetches added afterwards on the
        // Root are silently ignored - this was still happening here despite looking fetch-joined.
        debitorQuery.fetch(Creditor_.categories, JoinType.LEFT);
        debitorQuery.fetch(Creditor_.payment, JoinType.LEFT);
        debitorQuery.fetch(Creditor_.bankAccount, JoinType.LEFT);
        debitorQuery.fetch(Creditor_.addresses, JoinType.LEFT).fetch(Address_.contactTypes, JoinType.LEFT);
        final TypedQuery<Creditor> q = getEntityManager().createQuery(query);
        // Deliberately NOT using CACHE_STORE_MODE=REFRESH: combined with fetch joins, REFRESH
        // mode does not trust the joined-in relation data and independently re-queries every one
        // of Contact's eager relations one row at a time regardless - see DebitorsDAO's identical
        // fix for the full story (this was the single biggest contributor to a ~14000-query
        // application startup, unrelated to whether this list was even open).
        q.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        final List<Creditor> debitorsFromDb = new ArrayList<>(new java.util.LinkedHashSet<>(q.getResultList()));
        List<DebitorAddress> treeItems = new ArrayList<>();

        /*
         * Create a list of DebitorAddresses. This is done by creating at least one
         * entry (for the main address) and some child entries for other matching addresses.
         */
        for (Creditor debitor : debitorsFromDb) {
            List<Address> addresses = debitor.getAddresses();
            DebitorAddress treeItemDebitorAddress;
            if (addresses.size() >= 1) {
                // create the first entry for a debitor
                treeItemDebitorAddress = createDebitorTreeItem(debitor, addresses.get(0));
                if (addresses.size() > 1) {
                    // if more than one address exists create child entries
                    addresses.subList(1, addresses.size()).stream()
                            .filter(adr -> adr.getContactTypes().isEmpty() || adr.getContactTypes().contains(contactType))
                            .forEach(adr -> treeItems.add(createDebitorTreeItem(debitor, adr)));
                }
                treeItems.add(treeItemDebitorAddress);
            }
        }

        return treeItems;
    }

    /** Counts the {@link Creditor}s (not {@link DebitorAddress} rows) matching the same criteria as {@link #findForTreeListView}. */
    public long countForTreeListView(final String searchTerm) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> query = cb.createQuery(Long.class);
        final Root<Creditor> debitorQuery = query.from(getEntityClass());
        query.select(cb.countDistinct(debitorQuery)).where(buildTreeListPredicate(cb, debitorQuery, searchTerm));
        return getEntityManager().createQuery(query).getSingleResult().longValue();
    }

    private Predicate buildTreeListPredicate(final CriteriaBuilder cb, final Root<Creditor> debitorQuery, final String searchTerm) {
        Predicate predicate = cb.and(debitorQuery.get(Creditor_.customerNumber).isNotNull(), cb.not(debitorQuery.get(Creditor_.deleted)));
        if (StringUtils.isNotBlank(searchTerm)) {
            final String likeTerm = "%" + searchTerm.toLowerCase(java.util.Locale.ROOT) + "%";
            final jakarta.persistence.criteria.Join<Creditor, Address> addressJoin = debitorQuery.join(Creditor_.addresses, JoinType.LEFT);
            predicate = cb.and(predicate,
                    cb.or(cb.like(cb.lower(debitorQuery.get(Creditor_.customerNumber)), likeTerm),
                            cb.like(cb.lower(debitorQuery.get(Creditor_.firstName)), likeTerm),
                            cb.like(cb.lower(debitorQuery.get(Creditor_.name)), likeTerm),
                            cb.like(cb.lower(debitorQuery.get(Creditor_.company)), likeTerm),
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

    private DebitorAddress createDebitorTreeItem(final Creditor debitor2, final Address adr) {
        return new DebitorAddress(debitor2, adr);
    }

}
