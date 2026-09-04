package com.sebulli.fakturama.dao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.persistence.config.CascadePolicy;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import com.sebulli.fakturama.dialogs.SelectDeliveryNoteDialog;
import com.sebulli.fakturama.dto.AccountEntry;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.misc.OrderState;
import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.Confirmation;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.Credit;
import com.sebulli.fakturama.model.Delivery;
import com.sebulli.fakturama.model.Delivery_;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.DocumentItem_;
import com.sebulli.fakturama.model.DocumentReceiver_;
import com.sebulli.fakturama.model.Document_;
import com.sebulli.fakturama.model.DummyStringCategory;
import com.sebulli.fakturama.model.Dunning;
import com.sebulli.fakturama.model.Dunning_;
import com.sebulli.fakturama.model.FakturamaModelFactory;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.model.Invoice_;
import com.sebulli.fakturama.model.Letter;
import com.sebulli.fakturama.model.Offer;
import com.sebulli.fakturama.model.Order;
import com.sebulli.fakturama.model.Payment;
import com.sebulli.fakturama.model.Payment_;
import com.sebulli.fakturama.model.Product_;
import com.sebulli.fakturama.model.Proforma;
import com.sebulli.fakturama.model.VoucherCategory;
import com.sebulli.fakturama.views.datatable.documents.DocumentMatcher;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Creatable
public class DocumentsDAO extends AbstractDAO<Document> {

    @Inject
    @Translation
    protected Messages msg;

    @Override
    protected Class<Document> getEntityClass() {
        return Document.class;
    }

    @Override
    public Document findByName(final String name) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Document> criteria = cb.createQuery(Document.class);
        Root<Document> root = criteria.from(Document.class);
        CriteriaQuery<Document> cq = criteria.where(cb.equal(root.<String> get(Document_.name), name));
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<Document> findAll(final boolean forceRead) {
        List<Document> resultList;
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Document> criteria = cb.createQuery(getEntityClass());
        Root<Document> root = criteria.from(getEntityClass());
        fetchDocumentRelations(root);
        CriteriaQuery<Document> cq = criteria.where(cb.notEqual(root.get(Document_.deleted), Boolean.TRUE));
        TypedQuery<Document> query = getEntityManager().createQuery(cq);
        // Document uses JOINED inheritance across many subclass tables (Invoice, Offer, Order, ...).
        // Without this hint EclipseLink issues one extra SELECT per row to fetch the subclass-specific
        // columns (InheritancePolicy#selectOneRowUsingMultipleTableSubclassRead) instead of a single
        // outer-joined query, which turns findAll() into N+1 round trips and can look like a hang on
        // a high-latency DB connection.
        query.setHint(QueryHints.INHERITANCE_OUTER_JOIN, HintValues.TRUE);
        // Deliberately NOT using CACHE_STORE_MODE=REFRESH here, even though callers historically
        // asked for it via forceRead: with fetchDocumentRelations()'s explicit fetch joins in
        // place, REFRESH mode does not trust the joined-in relation data and independently
        // re-queries every @ManyToOne relation one row at a time regardless - the exact N+1
        // this method exists to avoid (verified: with REFRESH, a single ~2000-row page pull
        // turned into ~14000 extra per-row SELECTs; without it, the fetch-joined main query is
        // the only round trip). A plain query still always hits the DB for the primary rows -
        // REFRESH only ever affected already-cached *related* entities, which this list view's
        // columns never read from anyway (see fetchDocumentRelations' Javadoc).
        query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        try {
            resultList = query.getResultList();
        } catch (PersistenceException e) {
            System.err.println("First start, no table found. If this problem remains after first start, please contact your administrator.");
            resultList = Collections.emptyList();
        }
        return resultList;
    }

    @Override
    public Document findById(final Long id) {
        return findById(id, false);
    }

    @Override
    public Document findById(final Long id, final boolean forceReadFromDatabase) {
        if (id == null) {
            return null;
        }
        // Deliberately NOT using AbstractDAO's EntityManager#find() here: that path was still
        // producing the classic JOINED-inheritance N+1 (InheritancePolicy#selectOneRowUsingMultipleTableSubclassRead)
        // despite passing INHERITANCE_OUTER_JOIN via find()'s properties map - that hint is only
        // honored on a real Query/CriteriaQuery, not on the find()-by-id fast path. Confirmed via
        // SQL log: switching the document list to "Lieferscheine" alone fired ~2100 of these
        // comma-style "FROM FKT_DOCUMENT t0, FKT_ORDER t1 WHERE (t0.ID = ?) AND ..." lookups in
        // under 20 seconds - one per document whose transaction/reference chain got walked - which
        // is exactly the "always blocking" behaviour reported when switching to that category.
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Document> criteria = cb.createQuery(Document.class);
        final Root<Document> root = criteria.from(Document.class);
        fetchDocumentRelations(root);
        criteria.where(cb.equal(root.get(Document_.id), id));
        final TypedQuery<Document> query = getEntityManager().createQuery(criteria);
        query.setHint(QueryHints.INHERITANCE_OUTER_JOIN, HintValues.TRUE);
        Document result;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        if (forceReadFromDatabase && result != null) {
            getEntityManager().refresh(result);
        }
        return result;
    }

    /**
     * Explicitly fetch-joins {@link Document}'s eager, non-self-referencing {@code @ManyToOne}
     * relations ({@code additionalInfo}, {@code payment}, {@code shipping}, {@code noVatReference})
     * into the same query, instead of letting EclipseLink resolve each one lazily with its own
     * round trip per row. None of these mappings can be made truly {@code LAZY} - weaving isn't
     * enabled in this OSGi launch (see the "Reverting the lazy setting" startup warning for
     * {@code additionalInfo}), so EclipseLink always resolves them eagerly one way or another; the
     * only way to keep that to a single query instead of one-per-row is to fetch-join explicitly.
     * <p>
     * Deliberately NOT fetch-joining {@code sourceDocument}/{@code invoiceReference} here: both
     * are self-referencing ({@code Document -> Document}), so fetch-joining them doesn't just add
     * one more join - it makes EclipseLink fully build the *referenced* Document too, which (since
     * that nested build also can't use LAZY, same reason as above) resolves *its* additionalInfo/
     * payment/shipping/sourceDocument/invoiceReference as a synchronous, un-joined single-row read
     * each. For a document at the end of a long reference chain (collective invoices covering many
     * delivery notes, multi-hop order->delivery->invoice chains) this cascades into thousands of
     * one-row-at-a-time reads while building a single page - confirmed via thread dump mid-burst
     * (nested ReadObjectQuery -> NoIndirectionPolicy -> ObjectBuilder.buildObject, several levels
     * deep, all inside one findPage() row). Leaving these two lazy-on-access instead is cheap now
     * that InheritanceOuterJoinSessionCustomizer makes every such access properly outer-joined
     * (one query, not two) - and most callers (e.g. the status-icon column) only need a null check
     * or a single field, not this row's full transaction chain built eagerly.
     */
    private void fetchDocumentRelations(final Root<? extends Document> root) {
        root.fetch(Document_.additionalInfo, JoinType.LEFT);
        root.fetch(Document_.payment, JoinType.LEFT);
        root.fetch(Document_.shipping, JoinType.LEFT);
        root.fetch(Document_.noVatReference, JoinType.LEFT);
    }

    /**
     * Like {@link #fetchDocumentRelations(Root)}, but also fetch-joins {@code sourceDocument}/
     * {@code invoiceReference} - safe here specifically because {@link #findByTransactionId(Integer)}
     * only ever returns the flat, finite set of documents belonging to ONE transaction, so
     * fetch-joining these two self-referencing relations cannot recurse into an unrelated,
     * unbounded chain the way it could for {@link #findById(Long)}/{@link #findPage} (see the
     * Javadoc above for why those two deliberately don't call this). Fixes DocumentEditor's
     * document-chain breadcrumb (every editor open calls findByTransactionId once) firing one
     * extra un-joined single-row read per document in the transaction for each of these two
     * fields - confirmed via SQL log: opening one invoice in a 166-document transaction fired 166
     * such reads (~6s against a remote DB) before this fetch join was added.
     */
    private void fetchDocumentRelationsWithChainReferences(final Root<? extends Document> root) {
        fetchDocumentRelations(root);
        root.fetch(Document_.sourceDocument, JoinType.LEFT);
        root.fetch(Document_.invoiceReference, JoinType.LEFT);
    }

    /**
     * Finds Documents having a given account. Only {@link BillingType#INVOICE}
     * and {@link BillingType#CREDIT} are considered. An account is a
     * {@link VoucherCategory} from a {@link Payment}.
     * 
     * @param account
     *            which account should be used for filtering
     * @return List of {@link AccountEntry}s, sorted by Document date
     */
    public List<AccountEntry> findAccountedDocuments(final VoucherCategory account) {
        return findAccountedDocuments(account, null, null);
    }

    /**
     * Finds Documents having a given account. Only {@link BillingType#INVOICE}
     * and {@link BillingType#CREDIT} are considered. An account is a
     * {@link VoucherCategory} from a {@link Payment}. The Documents can be
     * filtered for a certain date range.
     * 
     * @param account
     *            which account should be used for filtering
     * @param startDate
     *            Date for filtering (can be <code>null</code>)
     * @param endDate
     *            Date for filtering (can be <code>null</code>)
     * @return List of {@link AccountEntry}s, sorted by Document date
     */
    public List<AccountEntry> findAccountedDocuments(final VoucherCategory account, final Date startDate, final Date endDate) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Document> criteria = cb.createQuery(getEntityClass());
        Root<Document> root = criteria.from(getEntityClass());
        Predicate predicate = cb.and(cb.not(root.get(Document_.deleted)),
                cb.or(cb.equal(root.get(Document_.billingType), BillingType.INVOICE), cb.equal(root.get(Document_.billingType), BillingType.CREDIT)),
                cb.equal(root.get(Document_.payment).get(Payment_.category), account));

        // take the paydate into account (NOT the document date!)
        if (startDate != null && endDate != null) {
            // if startDate is after endDate we switch the two dates silently
            predicate = cb.and(predicate,
                    cb.between(root.get(Document_.payDate), startDate.before(endDate) ? startDate : endDate, endDate.after(startDate) ? endDate : startDate));
        }
        CriteriaQuery<Document> cq = criteria.where(predicate).orderBy(cb.asc(root.get(Document_.payDate)));
        TypedQuery<Document> query = getEntityManager().createQuery(cq);
        List<Document> documentList = query.getResultList();
        List<AccountEntry> resultList = new ArrayList<>();
        for (Document document : documentList) {
            AccountEntry accountEntry = new AccountEntry(document);
            resultList.add(accountEntry);
        }
        return resultList;
    }

    /**
     * Find {@link Document}s by type, their webshop ID and a date.
     * 
     * @param type
     *            the {@link DocumentType} of the document
     * @param webshopId
     *            the ID from webshop which is assigned to this {@link Document}
     * @param calendarWebshopDate
     *            the dateTime for which this {@link Document} was retrieved
     *            from webShop
     * @return a List of {@link Document}s (or an empty List if none was found)
     */
    public List<Document> findByDocIdAndDocDate(final DocumentType type, final String webshopId, final LocalDateTime calendarWebshopDate) {
        FakturamaModelFactory modelFactory = new FakturamaModelFactory();
        BillingType billingType = modelFactory.createBillingTypeFromString(type.getTypeAsString());
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Document> criteria = cb.createQuery(Document.class);
        Root<Document> root = criteria.from(Document.class);
        Instant instant = calendarWebshopDate.atZone(ZoneId.systemDefault()).toInstant();
        Date res = Date.from(instant);
        CriteriaQuery<Document> cq = criteria
                .where(cb.and(cb.equal(root.<BillingType> get(Document_.billingType), billingType), cb.equal(root.<String> get(Document_.webshopId), webshopId),
                        cb.equal(root.<Date> get(Document_.webshopDate), res), cb.notEqual(root.get(Document_.deleted), Boolean.TRUE)));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Gets the all visible properties of this Documents object.
     * 
     * @return String[] of visible Documents properties
     */
    public String[] getVisibleProperties() {
        return new String[] { Document_.name.getName(), Document_.addressFirstLine.getName(), Document_.documentDate.getName(), Document_.totalValue.getName(),
                Document_.customerRef.getName() };
    }

    /**
     * Get an array of strings of all category strings.
     * 
     * Only the categories of the document types are returned, that are in use.
     * e.g. If there is an type "invoice", the categories "invoice/paid" and
     * "invoice/unpaid" are returned.
     * 
     * @return Array of all category strings
     */
    public List<DummyStringCategory> getCategoryStrings() {
        List<DummyStringCategory> resultList = new ArrayList<>();

        if (getEntityManager() == null) {
            return null;
        }

        Query q = getEntityManager().createQuery("select distinct type(d) from Document d where d.deleted = false");

        @SuppressWarnings("unchecked")
        List<Class<? extends Document>> typeList = q.getResultList();
        for (Class<? extends Document> document : typeList) {

            // Letters
            if (document.getName().contentEquals(Letter.class.getName())) {
                // add letter documents
                List<DummyStringCategory> cats = createDummyCategories(DocumentType.LETTER);
                resultList.addAll(cats);
            }

            if (document.getName().contentEquals(Offer.class.getName())) {
                // add order documents
                List<DummyStringCategory> cats = createDummyCategories(DocumentType.OFFER);
                resultList.addAll(cats);
            }

            // Orders
            if (document.getName().contentEquals(Order.class.getName())) {
                // add order documents
                List<DummyStringCategory> cats = createDummyCategories(DocumentType.ORDER, msg.documentOrderStateNotshipped, msg.documentOrderStateShipped);
                resultList.addAll(cats);
            }

            if (document.getName().contentEquals(Confirmation.class.getName())) {
                // add letter documents
                List<DummyStringCategory> cats = createDummyCategories(DocumentType.CONFIRMATION);
                resultList.addAll(cats);
            }

            // Invoices
            if (document.getName().contentEquals(Invoice.class.getName())) {
                // add invoice documents
                List<DummyStringCategory> cats = createDummyCategories(DocumentType.INVOICE, msg.documentOrderStateUnpaid, msg.documentOrderStatePaid);
                resultList.addAll(cats);
            }

            // Deliveries
            if (document.getName().contentEquals(Delivery.class.getName())) {
                // add dunning documents
                List<DummyStringCategory> cats = createDummyCategories(DocumentType.DELIVERY, msg.documentDeliveryStateHasinvoice,
                        msg.documentDeliveryStateHasnoinvoice);
                resultList.addAll(cats);
            }

            // Credits
            if (document.getName().contentEquals(Credit.class.getName())) {
                // add credit documents
                List<DummyStringCategory> cats = createDummyCategories(DocumentType.CREDIT, msg.documentOrderStateUnpaid, msg.documentOrderStatePaid);
                resultList.addAll(cats);
            }

            // Dunnings
            if (document.getName().contentEquals(Dunning.class.getName())) {
                // add dunning documents
                List<DummyStringCategory> cats = createDummyCategories(DocumentType.DUNNING, msg.documentOrderStateUnpaid, msg.documentOrderStatePaid);
                resultList.addAll(cats);
            }

            if (document.getName().contentEquals(Proforma.class.getName())) {
                // add letter documents
                List<DummyStringCategory> cats = createDummyCategories(DocumentType.PROFORMA);
                resultList.addAll(cats);
            }

        }
        return resultList;
    }

    /**
     * Creates a List of {@link DummyStringCategory}s.
     * 
     * @param category
     *            one or more categories which belong together
     * 
     * @return List of {@link DummyStringCategory}s
     */
    private List<DummyStringCategory> createDummyCategories(final DocumentType docType, final String... pCategory) {
        List<DummyStringCategory> retList = new ArrayList<>();
        DummyStringCategory parent = null;
        if (parent == null) {
            parent = new DummyStringCategory(msg.getMessageFromKey(DocumentType.getPluralString(docType)), docType);
            retList.add(parent);
        }
        for (String string : pCategory) {
            DummyStringCategory cat = new DummyStringCategory(string, docType);
            cat.setParent(parent);
            retList.add(cat);
        }
        return retList;
    }

    /**
     * Finds all paid {@link Invoice}s.
     * 
     * @return List of paid {@link Invoice}s
     */
    public List<Invoice> findPaidInvoices() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Invoice> criteria = cb.createQuery(Invoice.class);
        Root<Invoice> root = criteria.from(Invoice.class);
        // See fetchDocumentRelations()'s javadoc: without the fetch joins + inheritance hint,
        // every row returned here triggers its own extra per-row SELECTs once callers read
        // additionalInfo/payment/shipping - and this method can return every paid invoice in
        // the database.
        fetchDocumentRelations(root);
        CriteriaQuery<Invoice> cq = criteria.where(
                cb.and(cb.equal(root.<Boolean> get(Invoice_.paid), true), cb.equal(root.<Boolean> get(Invoice_.deleted), false)));
        TypedQuery<Invoice> query = getEntityManager().createQuery(cq);
        query.setHint(QueryHints.INHERITANCE_OUTER_JOIN, HintValues.TRUE);
        return query.getResultList();
    }

    /**
     * Find all paid {@link Invoice}s by a given {@link Contact}.
     * 
     * @param contact
     *            the {@link Contact} to look up
     * @return List of paid {@link Invoice}s
     */
    public List<Invoice> findPaidInvoicesForContact(final Contact contact) {
        if (contact == null) {
            return Collections.emptyList();
        }
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Invoice> criteria = cb.createQuery(Invoice.class);
        Root<Invoice> root = criteria.from(Invoice.class);
        // See fetchDocumentRelations()'s javadoc: avoids one extra per-row SELECT per invoice
        // once callers read additionalInfo/payment/shipping.
        fetchDocumentRelations(root);

        /*
         *  SELECT distinct d.name
        	FROM FKT_DOCUMENTRECEIVER dr ,
        	     FKT_DOCUMENT d,
        	     FKT_CONTACT c
        	WHERE dr.FK_DOCUMENT = d.ID
        	  AND dr.ORIGINCONTACTID = c.ID
        	  AND d.dtype = 'Invoice'
        	  and c.id = 1
         */

        CriteriaQuery<Invoice> cq = criteria.distinct(true)
                .where(cb.and(cb.equal(root.<Boolean> get(Invoice_.paid), true), cb.equal(root.<Boolean> get(Invoice_.deleted), false),
                        cb.equal(root.join(Invoice_.receiver).get(DocumentReceiver_.originContactId), contact.getId())));
        TypedQuery<Invoice> query = getEntityManager().createQuery(cq);
        query.setHint(QueryHints.INHERITANCE_OUTER_JOIN, HintValues.TRUE);
        return query.getResultList();
    }

    /**
     * Loads one deterministic page of visible documents.  The legacy
     * {@link #findAll(boolean)} method intentionally keeps its original
     * unbounded contract for existing callers; new list views should use this
     * method so that a large document archive is not materialised at once.
     *
     * @param forceRead whether the persistence cache should be bypassed
     * @param firstResult zero-based offset
     * @param maxResults maximum number of documents to return
     * @return a page ordered by document date and id
     */
    public List<Document> findPage(final boolean forceRead, final int firstResult, final int maxResults) {
        if (firstResult < 0 || maxResults <= 0) {
            throw new IllegalArgumentException("firstResult must be >= 0 and maxResults must be > 0");
        }
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Document> criteria = cb.createQuery(getEntityClass());
        final Root<Document> root = criteria.from(getEntityClass());
        fetchDocumentRelations(root);
        criteria.where(cb.notEqual(root.get(Document_.deleted), Boolean.TRUE));
        criteria.orderBy(cb.desc(root.get(Document_.documentDate)), cb.desc(root.get(Document_.id)));
        final TypedQuery<Document> query = getEntityManager().createQuery(criteria);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        // Document uses JOINED inheritance across many subclass tables - without this hint
        // EclipseLink issues one extra SELECT per row to fetch the subclass-specific columns
        // instead of a single outer-joined query (see DocumentsDAO#findAll for the full story).
        query.setHint(QueryHints.INHERITANCE_OUTER_JOIN, HintValues.TRUE);
        // Deliberately NOT using CACHE_STORE_MODE=REFRESH here, even though callers historically
        // asked for it via forceRead: with fetchDocumentRelations()'s explicit fetch joins in
        // place, REFRESH mode does not trust the joined-in relation data and independently
        // re-queries every @ManyToOne relation one row at a time regardless - the exact N+1
        // this method exists to avoid (verified: with REFRESH, a single ~2000-row page pull
        // turned into ~14000 extra per-row SELECTs; without it, the fetch-joined main query is
        // the only round trip). A plain query still always hits the DB for the primary rows -
        // REFRESH only ever affected already-cached *related* entities, which this list view's
        // columns never read from anyway (see fetchDocumentRelations' Javadoc).
        query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return query.getResultList();
    }

    /** Returns the number of visible documents without loading entities. */
    public long countVisible() {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        final Root<Document> root = criteria.from(getEntityClass());
        criteria.select(cb.count(root));
        criteria.where(cb.notEqual(root.get(Document_.deleted), Boolean.TRUE));
        return getEntityManager().createQuery(criteria).getSingleResult().longValue();
    }

    /**
     * Loads one page of documents, restricted to a free-text search term and/or the same
     * category selection the document-type tree in {@code DocumentsListTable} offers
     * (document type + paid/shipped/has-invoice state, or the special "this contact"/"this
     * transaction" root nodes) - the DB-side equivalent of what {@code DocumentMatcher} used
     * to check in memory, and the free-text equivalent of what the old
     * {@code TextWidgetMatcherEditor} filtered in memory over {@code name}/{@code
     * addressFirstLine}/{@code customerRef}.
     *
     * @param forceRead whether the persistence cache should be bypassed
     * @param searchTerm free-text search term, or blank/null for no text filter
     * @param categoryName the selected tree node's filter string (may be null - same contract as
     *            {@link DocumentMatcher}/{@code AbstractViewDataTable#setCategoryFilter})
     * @param treeObjectType the kind of node {@code categoryName} refers to
     * @param orderByProperty a {@code Document} attribute name to sort by, or null for the default
     *            (document date desc, id desc)
     * @param descending sort direction for {@code orderByProperty} (ignored if null)
     * @param firstResult zero-based offset
     * @param maxResults maximum number of documents to return
     * @return a page of documents matching the given search/category criteria
     */
    public List<Document> findPage(final boolean forceRead, final String searchTerm, final String categoryName, final TreeObjectType treeObjectType,
            final String orderByProperty, final boolean descending, final int firstResult, final int maxResults) {
        if (firstResult < 0 || maxResults <= 0) {
            throw new IllegalArgumentException("firstResult must be >= 0 and maxResults must be > 0");
        }
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Document> criteria = cb.createQuery(getEntityClass());
        final Root<Document> root = criteria.from(getEntityClass());
        fetchDocumentRelations(root);
        criteria.where(buildVisiblePredicate(cb, root, searchTerm, categoryName, treeObjectType));
        if (StringUtils.isNotBlank(orderByProperty)) {
            final jakarta.persistence.criteria.Order order = descending ? cb.desc(root.get(orderByProperty)) : cb.asc(root.get(orderByProperty));
            criteria.orderBy(order, cb.desc(root.get(Document_.id)));
        } else {
            criteria.orderBy(cb.desc(root.get(Document_.documentDate)), cb.desc(root.get(Document_.id)));
        }
        final TypedQuery<Document> query = getEntityManager().createQuery(criteria);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setHint(QueryHints.INHERITANCE_OUTER_JOIN, HintValues.TRUE);
        // Deliberately NOT using CACHE_STORE_MODE=REFRESH here, even though callers historically
        // asked for it via forceRead: with fetchDocumentRelations()'s explicit fetch joins in
        // place, REFRESH mode does not trust the joined-in relation data and independently
        // re-queries every @ManyToOne relation one row at a time regardless - the exact N+1
        // this method exists to avoid (verified: with REFRESH, a single ~2000-row page pull
        // turned into ~14000 extra per-row SELECTs; without it, the fetch-joined main query is
        // the only round trip). A plain query still always hits the DB for the primary rows -
        // REFRESH only ever affected already-cached *related* entities, which this list view's
        // columns never read from anyway (see fetchDocumentRelations' Javadoc).
        query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return query.getResultList();
    }

    /** Counts documents matching the same search/category criteria as {@link #findPage}. */
    public long countPage(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        final Root<Document> root = criteria.from(getEntityClass());
        criteria.select(cb.count(root));
        criteria.where(buildVisiblePredicate(cb, root, searchTerm, categoryName, treeObjectType));
        return getEntityManager().createQuery(criteria).getSingleResult().longValue();
    }

    /** {@code deleted != TRUE}, AND-ed with the search predicate and the category predicate (either may be absent). */
    private Predicate buildVisiblePredicate(final CriteriaBuilder cb, final Root<Document> root, final String searchTerm, final String categoryName,
            final TreeObjectType treeObjectType) {
        Predicate predicate = cb.notEqual(root.get(Document_.deleted), Boolean.TRUE);
        final Predicate searchPredicate = buildSearchPredicate(cb, root, searchTerm);
        if (searchPredicate != null) {
            predicate = cb.and(predicate, searchPredicate);
        }
        final Predicate categoryPredicate = buildCategoryPredicate(cb, root, categoryName, treeObjectType);
        if (categoryPredicate != null) {
            predicate = cb.and(predicate, categoryPredicate);
        }
        return predicate;
    }

    /**
     * Free-text search across the same fields the old in-memory
     * {@code GlazedLists.textFilterator(Document.class, name, addressFirstLine, customerRef)}
     * matched against.
     */
    private Predicate buildSearchPredicate(final CriteriaBuilder cb, final Root<Document> root, final String searchTerm) {
        if (StringUtils.isBlank(searchTerm)) {
            return null;
        }
        final String likeTerm = "%" + searchTerm.toLowerCase(Locale.ROOT) + "%";
        return cb.or(cb.like(cb.lower(root.get(Document_.name)), likeTerm), cb.like(cb.lower(root.get(Document_.addressFirstLine)), likeTerm),
                cb.like(cb.lower(root.get(Document_.customerRef)), likeTerm));
    }

    /**
     * DB-side equivalent of {@link DocumentMatcher#matches(Document)} - translates the tree
     * selection (document type + paid/shipped/has-invoice state, or one of the two special root
     * nodes) into a query predicate instead of checking each already-loaded {@link Document} in
     * memory. Returns {@code null} when nothing should be filtered (root/"all" node, or the
     * "/---" no-selection sentinel {@code DocumentMatcher} also treats as "match everything").
     */
    private Predicate buildCategoryPredicate(final CriteriaBuilder cb, final Root<Document> root, final String categoryName,
            final TreeObjectType treeObjectType) {
        if (treeObjectType == null || treeObjectType == TreeObjectType.ALL_NODE || treeObjectType == TreeObjectType.ROOT_NODE
                || StringUtils.isBlank(categoryName) || "/---".equals(categoryName)) {
            return null;
        }
        if (treeObjectType == TreeObjectType.TRANSACTIONS_ROOTNODE) {
            final int transactionId = StringUtils.isNumeric(categoryName) ? Integer.parseInt(categoryName) : 0;
            return cb.equal(root.get(Document_.transactionId), transactionId);
        }
        if (treeObjectType == TreeObjectType.CONTACTS_ROOTNODE) {
            return cb.equal(root.get(Document_.addressFirstLine), categoryName);
        }

        // Document-type tree: categoryName is "/<TypePlural>" or "/<TypePlural>/<State>", built
        // the same way DocumentMatcher#getCategory() builds it for comparison.
        final String normalized = StringUtils.prependIfMissing(categoryName, "/", "/");
        for (final DocumentType docType : DocumentType.values()) {
            if (docType == DocumentType.NONE) {
                continue;
            }
            final String typePath = "/" + msg.getMessageFromKey(DocumentType.getPluralString(docType));
            if (!normalized.startsWith(typePath)) {
                continue;
            }
            final Predicate typePredicate = cb.equal(root.get(Document_.billingType), BillingType.getByName(docType.name()));
            if (normalized.equals(typePath)) {
                // just the type node itself - both states included, mirrors the startsWith() match
                return typePredicate;
            }
            final Predicate statePredicate = buildStatePredicate(cb, root, docType, typePath, normalized);
            return statePredicate != null ? cb.and(typePredicate, statePredicate) : typePredicate;
        }
        // categoryName didn't match any known type path - fail safe (match nothing) rather than
        // silently showing the unfiltered table.
        return cb.disjunction();
    }

    /** The paid/shipped/has-invoice sub-node predicate for a document-type node, or null if none matched. */
    private Predicate buildStatePredicate(final CriteriaBuilder cb, final Root<Document> root, final DocumentType docType, final String typePath,
            final String normalized) {
        switch (docType) {
            case INVOICE:
            case CREDIT:
            case DUNNING:
                if (normalized.equals(typePath + "/" + msg.documentOrderStatePaid)) {
                    return cb.and(cb.isNotNull(root.get(Document_.payDate)), cb.isTrue(root.get(Document_.paid)));
                }
                if (normalized.equals(typePath + "/" + msg.documentOrderStateUnpaid)) {
                    return cb.not(cb.and(cb.isNotNull(root.get(Document_.payDate)), cb.isTrue(root.get(Document_.paid))));
                }
                break;
            case DELIVERY:
                final Predicate hasInvoice = cb.and(cb.isNotNull(root.get(Document_.invoiceReference)),
                        cb.equal(root.get(Document_.invoiceReference).get(Document_.billingType), BillingType.INVOICE));
                if (normalized.equals(typePath + "/" + msg.documentDeliveryStateHasinvoice)) {
                    return hasInvoice;
                }
                if (normalized.equals(typePath + "/" + msg.documentDeliveryStateHasnoinvoice)) {
                    return cb.not(hasInvoice);
                }
                break;
            case ORDER:
                final Predicate shipped = root.get(Document_.progress).in(OrderState.SHIPPED.getState(), OrderState.COMPLETED.getState());
                if (normalized.equals(typePath + "/" + msg.documentOrderStateShipped)) {
                    return shipped;
                }
                if (normalized.equals(typePath + "/" + msg.documentOrderStateNotshipped)) {
                    return cb.not(shipped);
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * Finds all undeleted invoices for a given contact. The paid flag is left
     * untouched so callers can distinguish completely paid, partially paid and
     * unpaid invoices using the existing invoice fields.
     *
     * @param contact
     *            contact whose invoices should be returned
     * @return all undeleted invoices assigned to the contact
     */
    public List<Invoice> findInvoicesForContact(final Contact contact) {
        if (contact == null) {
            return Collections.emptyList();
        }
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Invoice> criteria = cb.createQuery(Invoice.class);
        final Root<Invoice> root = criteria.from(Invoice.class);
        // See fetchDocumentRelations()'s javadoc: this powers the document editor's customer
        // summary, called on every editor open - without the fetch joins + inheritance hint,
        // every invoice for that contact triggers its own extra per-row SELECTs once
        // CustomerStatistics reads additionalInfo/payment/shipping off each one.
        fetchDocumentRelations(root);
        final CriteriaQuery<Invoice> query = criteria.distinct(true)
                .where(cb.and(cb.equal(root.<Boolean> get(Invoice_.deleted), false),
                        cb.equal(root.join(Invoice_.receiver).get(DocumentReceiver_.originContactId), contact.getId())));
        final TypedQuery<Invoice> typedQuery = getEntityManager().createQuery(query);
        typedQuery.setHint(QueryHints.INHERITANCE_OUTER_JOIN, HintValues.TRUE);
        return typedQuery.getResultList();
    }

    public void updateDunnings(final Document document) {
        updateDunnings(document, document.getPaid(), document.getPayDate());
    }

    /**
     * Update {@link Dunning}s which are related to a certain invoice.
     * 
     * @param document
     *            the invoice which is related
     * @param isPaid
     *            is it paid?
     * @param paidDate
     *            paid date
     */
    public void updateDunnings(final Document document, final boolean isPaid, final Date paidDate) {
        //      UPDATE dunning SET paid, paidValue, paidDate WHERE dunning.invoiceid = invid  
        //      // TODO What if "payvalue" is not the total sum? Is it paid?
        if (!document.getBillingType().isINVOICE()) {
            // only update dunnings if we have an invoice!
            return;
        }
        Double paidValue = document.getPaidValue();
        //          dunning.setPaid(bPaid.getSelection());
        //          dunning.setStringValueByKey("paydate", DataUtils.getDateTimeAsString(dtPaidDate));
        //          dunning.setDoubleValueByKey("payvalue", paidValue.getValueAsDouble());
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Dunning> criteria = cb.createCriteriaUpdate(Dunning.class);
        criteria.set(Dunning_.paid, isPaid).set(Dunning_.payDate, paidDate).set(Dunning_.paidValue, paidValue)
                .where(cb.equal(criteria.from(Dunning.class).get(Dunning_.invoiceReference), document));
        executeCriteria(criteria);
    }

    /**
     * Executes a given {@link CriteriaUpdate} within a separate
     * {@link EntityTransaction}.
     * 
     * @param criteria
     *            the Criteria to execute
     */
    private void executeCriteria(final CriteriaUpdate<?> criteria) {
        EntityTransaction tx = getEntityManager().getTransaction();
        tx.begin();
        try {
            getEntityManager().createQuery(criteria).executeUpdate();
            tx.commit();
        } catch (PersistenceException e) {
            tx.rollback();
        }
    }

    /**
     * Selects all given Deliveries (which don't have an invoice reference) by
     * ID.
     * 
     * @param selectedIds
     * @return
     */
    public List<Delivery> findSelectedDeliveries(final List<Long> selectedIds) {
        // setCategoryFilter(DocumentType.getPluralString(DocumentType.DELIVERY)
        // + "/" + DataSetDocument.getStringHASNOINVOICE());
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Delivery> criteria = cb.createQuery(Delivery.class);
        Root<Delivery> root = criteria.from(Delivery.class);
        CriteriaQuery<Delivery> cq;
        Predicate baseClause = cb.and(cb.equal(root.<BillingType> get(Document_.billingType), BillingType.DELIVERY),
                cb.isNull(root.get(Delivery_.invoiceReference)), cb.equal(root.<Boolean> get(Document_.deleted), false));
        if (selectedIds != null) {
            cq = criteria.where(cb.and(baseClause, root.get(Delivery_.id).in(selectedIds)));
        } else {
            cq = criteria.where(baseClause);
        }

        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Finds all {@link Delivery} documents without an invoice (should be used
     * for {@link SelectDeliveryNoteDialog}).
     * 
     * @return List of {@link Delivery} documents
     */
    public List<Delivery> findAllDeliveriesWithoutInvoice() {
        return findSelectedDeliveries(null);
    }

    /**
     * Find all printed documents. This is relevant for reorganizing documents.
     * 
     * @return
     */
    public List<Document> findAllPrintedDocuments() {
        List<Document> resultList;
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Document> criteria = cb.createQuery(getEntityClass());
        Root<Document> root = criteria.from(getEntityClass());
        CriteriaQuery<Document> cq = criteria.where(cb.and(cb.notEqual(root.get(Document_.deleted), Boolean.TRUE),
                cb.or(cb.notEqual(root.get(Document_.odtPath), ' '), cb.notEqual(root.get(Document_.pdfPath), ' '))));
        TypedQuery<Document> query = getEntityManager().createQuery(cq);
        try {
            resultList = query.getResultList();
        } catch (PersistenceException e) {
            resultList = Collections.emptyList();
        }
        return resultList;

    }

    /**
     * Update the invoice references in all documents within the same
     * transaction.
     * 
     * @param document
     */
    public void updateInvoiceReferences(final Invoice document) {
        /*
            Transaction trans = new Transaction(document);
            List<DataSetDocument> docs = trans.getDocuments();
            for (DataSetDocument doc : docs) {
                if(doc.getIntValueByKey("invoiceid") < 0) {
                    doc.setIntValueByKey("invoiceid", documentId );
                    Data.INSTANCE.updateDataSet(doc);
                }
            }
         */
        // update documents set fk_invoiceref = document where fk_invoiceref = null and transactionid = ?
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Document> criteria = cb.createCriteriaUpdate(Document.class);
        Root<Document> root = criteria.from(Document.class);
        criteria.set(Document_.invoiceReference, document)
                .where(cb.and(cb.isNull(root.get(Document_.invoiceReference)), cb.equal(root.get(Document_.transactionId), document.getTransactionId())));
        executeCriteria(criteria);
    }

    /**
     * Updates the {@link Delivery} entities that are contained in the given
     * document (as part of a collecting invoice). Update contains setting the
     * invoice reference and merging the transactions (if needed).
     * 
     * @param importedDeliveryNotes
     *            List of {@link Delivery} IDs
     * @param document
     *            {@link Invoice} document
     */
    public void updateDeliveries(final List<Long> importedDeliveryNotes, final Invoice document) {
        /*        for (Long importedDeliveryNote : importedDeliveryNotes) {
            if (importedDeliveryNote >= 0) {
                DataSetDocument deliveryNote = Data.INSTANCE.getDocuments().getDatasetById(importedDeliveryNote);
                deliveryNote.setIntValueByKey("invoiceid", documentId );
                Data.INSTANCE.updateDataSet(deliveryNote);
                
                // Change also the transaction id of the imported delivery note
                Transaction.mergeTwoTransactions(document, deliveryNote);
            }
        }
        */
        if (!importedDeliveryNotes.isEmpty()) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaUpdate<Delivery> criteria = cb.createCriteriaUpdate(Delivery.class);
            Root<Delivery> root = criteria.from(Delivery.class);
            criteria.set(Delivery_.invoiceReference, document).where(root.get(Delivery_.id).in(importedDeliveryNotes));
            executeCriteria(criteria);
            mergeTwoTransactions(document, importedDeliveryNotes);
        }
    }

    /**
     * Merge 2 transactions into a single one
     * 
     * @param mainDocument
     *            the main {@link Document}
     * @param otherDocument
     *            the {@link Document} which gets the id of the main
     *            {@link Document}
     */
    public void mergeTwoTransactions(final Document mainDocument, final Document otherDocument) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Document> criteria = cb.createCriteriaUpdate(Document.class);
        Root<Document> root = criteria.from(Document.class);
        criteria.set(Document_.transactionId, mainDocument.getTransactionId()).where(cb.equal(root.get(Document_.id), otherDocument.getId()));
        executeCriteria(criteria);
    }

    /**
     * Merge 2 transactions into a single one for a given List of
     * {@link Document}s.
     * 
     * @param mainDocument
     *            the main {@link Document}
     * @param otherDocument
     *            the list of {@link Document}s which gets the id of the main
     *            {@link Document}
     */
    public void mergeTwoTransactions(final Document mainDocument, final List<Long> importedDeliveryNotes) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Document> criteria = cb.createCriteriaUpdate(Document.class);
        Root<Document> root = criteria.from(Document.class);
        criteria.set(Document_.transactionId, mainDocument.getTransactionId()).where(root.get(Document_.id).in(importedDeliveryNotes));
        executeCriteria(criteria);
    }

    /**
     * Search for all documents with the same number
     * 
     * @param transaction
     * @return
     */
    public List<Document> findByTransactionId(final Integer transaction) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Document> criteria = cb.createQuery(Document.class);
        final Root<Document> root = criteria.from(Document.class);
        fetchDocumentRelationsWithChainReferences(root);
        final CriteriaQuery<Document> cq = criteria.where(cb.equal(root.<Integer> get(Document_.transactionId), transaction));
        final TypedQuery<Document> query = getEntityManager().createQuery(cq);
        // A transaction's document chain (Order -> Invoice -> ...) is exactly what
        // DocumentEditor's breadcrumb calls this for on every open - same N+1-per-row risk as
        // findAll()/findPage() without this hint, just triggered once per document opened instead
        // of once per list load.
        query.setHint(QueryHints.INHERITANCE_OUTER_JOIN, HintValues.TRUE);
        return query.getResultList();
    }

    /**
     * For a set of Document ids, returns the subset that have a non-null {@code invoiceReference}
     * (i.e. delivery notes already covered by an invoice). Deliberately a plain {@code IS NOT NULL}
     * check on the FK column via a WHERE predicate rather than {@code doc.getInvoiceReference() !=
     * null} on each row - the latter resolves (builds) the full referenced Invoice one row at a
     * time, since invoiceReference can't be truly LAZY (weaving disabled - see
     * fetchDocumentRelations' javadoc) and is deliberately not fetch-joined either (self-referencing
     * Document -&gt; Document). Confirmed via SQL log: the document list's status column was firing
     * one such resolve per visible delivery-note row, on every scroll/reload.
     *
     * @param ids document ids to check (typically the ids of one loaded page)
     * @return the subset of {@code ids} whose invoiceReference is set
     */
    public java.util.Set<Long> findIdsWithInvoiceReference(final java.util.Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return java.util.Collections.emptySet();
        }
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        final Root<Document> root = criteria.from(Document.class);
        criteria.select(root.get(Document_.id))
                .where(cb.and(root.get(Document_.id).in(ids), cb.isNotNull(root.get(Document_.invoiceReference))));
        return new java.util.HashSet<>(getEntityManager().createQuery(criteria).getResultList());
    }

    /**
     * Pre-loads a document's items together with their {@code Product} and each product's
     * {@code categories} in a single outer-joined query, so that the item list's normal per-row
     * access ({@code DocumentItem#getProduct()}, used e.g. by {@code DocumentItemListTable}'s label
     * provider) finds an already-managed instance in the persistence context instead of firing its
     * own lazy single-row SELECT. Same rationale as {@link #fetchDocumentRelations(Root)}'s
     * Javadoc: weaving isn't enabled in this OSGi launch, so a "lazy" relation access is never
     * actually free - the only way to keep it to one round trip is to resolve it eagerly, in bulk,
     * before anything touches it row by row. Confirmed via SQL log: opening an invoice with 20 items
     * fired 20 individual Product reads plus one ProductCategory read per distinct product,
     * back-to-back, while the item table populated - a single call to this method up front replaces
     * all of them with one query.
     * <p>
     * The result is discarded on purpose - this method is called purely for its side effect of
     * populating the shared persistence context (the same {@link jakarta.persistence.EntityManager}
     * {@code document} itself was loaded through), not for its return value.
     *
     * @param document the document whose items' products should be warmed; a no-op for {@code null}
     *            or a document that isn't yet persisted
     */
    public void warmItemProductCache(final Document document) {
        if (document == null) {
            return;
        }
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Document> criteria = cb.createQuery(Document.class);
        final Root<Document> root = criteria.from(Document.class);
        root.fetch(Document_.items, JoinType.LEFT).fetch(DocumentItem_.product, JoinType.LEFT).fetch(Product_.categories, JoinType.LEFT);
        criteria.where(cb.equal(root.get(Document_.id), document.getId()));
        getEntityManager().createQuery(criteria).getResultList();
    }

    /**
     * Returns a string with all documents with the same transaction
     *
     * @param docType
     *            Only those documents will be returned
     * @return String with the document names
     */
    public String getReference(final Integer transaction, final DocumentType docType) {
        final BillingType billingType = BillingType.get(docType.getKey());
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Document> criteria = cb.createQuery(Document.class);
        final Root<Document> root = criteria.from(Document.class);
        fetchDocumentRelations(root);
        final CriteriaQuery<Document> cq = criteria.where(cb.and(cb.not(root.get(Document_.deleted)),
                cb.equal(root.<BillingType> get(Document_.billingType), billingType), cb.equal(root.<Integer> get(Document_.transactionId), transaction)));
        final TypedQuery<Document> query = getEntityManager().createQuery(cq);
        query.setHint(QueryHints.INHERITANCE_OUTER_JOIN, HintValues.TRUE);
        final List<Document> resultList = query.getResultList();
        final List<String> stringList = resultList.stream().map(d -> d.getName()).collect(Collectors.toList());
        return StringUtils.join(stringList, ", ");
    }

    /**
     * Calculates the sum of all document totals in a given
     * {@link DummyStringCategory}. Used for displaying tooltips.
     * 
     * @param category
     * @return sum of all document totals in the given category
     */
    public Optional<Double> sumAllDocumentsWithinCategory(final DummyStringCategory category) {
        if (category.getDocType() != DocumentType.INVOICE) {
            return null;
        }

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Double> query = cb.createQuery(Double.class);
        Root<Invoice> root = query.from(Invoice.class);
        CriteriaQuery<Double> cq = query.select(cb.sum(root.get(Document_.totalValue)));

        Predicate predicate;
        if (category.getName().contentEquals(msg.getMessageFromKey(DocumentType.getPluralString(DocumentType.INVOICE)))) {
            // sum paid and unpaid invoices
            predicate = cb.not(root.get(Document_.deleted));
        } else {
            // only paid or unpaid invoices
            predicate = cb.and(cb.not(root.get(Document_.deleted)),
                    cb.equal(root.<Boolean> get(Invoice_.paid), !category.getName().contentEquals(msg.documentOrderStateUnpaid)));
        }

        CriteriaQuery<Double> cq1 = cq.where(predicate);
        return Optional.ofNullable(getEntityManager().createQuery(cq1).getSingleResult());
    }

    /**
     * Finds all {@link Document}s within a given date range (or any document if
     * no date is given). Only unpaid {@link BillingType#INVOICE} and
     * {@link BillingType#CREDIT} are taken into account.
     * 
     * @param usePaidDate
     *            use "paid date" (<code>true</code>) or use "document date"
     *            (<code>false</code>)
     * @param startDate
     *            the start of the date range to retrieve (or <code>null</code>)
     * @param endDate
     *            the end of the date range to retrieve (or <code>null</code>)
     * @return List of {@link Document}s (sort by date according to
     *         <tt>usePaidDate</tt>)
     */
    public List<Document> findUnpaidDocumentsInRange(final boolean usePaidDate, final Date startDate, final Date endDate) {
        return findPaidOrUnpaidDocumentsInRange(usePaidDate, startDate, endDate, false);
    }

    /**
     * Finds all {@link Document}s within a given date range (or any document if
     * no date is given). Only paid {@link BillingType#INVOICE} and
     * {@link BillingType#CREDIT} are taken into account.
     * 
     * @param usePaidDate
     *            use "paid date" (<code>true</code>) or use "document date"
     *            (<code>false</code>)
     * @param startDate
     *            the start of the date range to retrieve (or <code>null</code>)
     * @param endDate
     *            the end of the date range to retrieve (or <code>null</code>)
     * @return List of {@link Document}s (sort by date according to
     *         <tt>usePaidDate</tt>)
     */
    public List<Document> findPaidDocumentsInRange(final boolean usePaidDate, final Date startDate, final Date endDate) {
        return findPaidOrUnpaidDocumentsInRange(usePaidDate, startDate, endDate, true);
    }

    private List<Document> findPaidOrUnpaidDocumentsInRange(final boolean usePaidDate, final Date startDate, final Date endDate, final boolean paidFlag) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Document> criteria = cb.createQuery(getEntityClass());
        Root<Document> root = criteria.from(getEntityClass());
        Predicate predicate = cb.and(cb.not(root.get(Document_.deleted)),
                cb.or(cb.equal(root.get(Document_.billingType), BillingType.INVOICE), cb.equal(root.get(Document_.billingType), BillingType.CREDIT)));

        if (paidFlag) {
            predicate = cb.and(predicate, cb.equal(root.get(Document_.paid), paidFlag));
        } else {
            // unpaid documents could have a null paid flag
            predicate = cb.and(predicate, cb.or(cb.isNull(root.get(Document_.paid)), cb.equal(root.get(Document_.paid), paidFlag)));
        }

        if (startDate != null && endDate != null) {
            // if startDate is after endDate we switch the two dates silently
            predicate = cb.and(predicate, cb.between(root.get(usePaidDate ? Document_.payDate : Document_.documentDate),
                    startDate.before(endDate) ? startDate : endDate, endDate.after(startDate) ? endDate : startDate));
        }
        // take the paydate OR the document date into account
        CriteriaQuery<Document> cq = criteria.where(predicate).orderBy(cb.asc(root.get(usePaidDate ? Document_.payDate : Document_.documentDate)));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public Document findDunningByTransactionId(final Integer transactionId, final int dunninglevel) {
        Document retval = null;
        if (transactionId != null) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Dunning> criteria = cb.createQuery(Dunning.class);
            Root<Dunning> root = criteria.from(Dunning.class);
            CriteriaQuery<Dunning> cq = criteria.where(cb.and(cb.equal(root.<Integer> get(Dunning_.transactionId), transactionId),
                    cb.equal(root.<BillingType> get(Dunning_.billingType), BillingType.DUNNING), cb.not(root.<Boolean> get(Dunning_.deleted)),
                    // check for dunnings
                    cb.equal(root.<Integer> get(Dunning_.dunningLevel), (dunninglevel > 0) ? dunninglevel : Integer.valueOf(1))));
            try {
                retval = getEntityManager().createQuery(cq).getSingleResult();
            } catch (NoResultException e) {
                // is ok, we have to return a null value
            }
        }
        return retval;
    }

    /**
     * Finds a document by its transaction id and billing type. Returns
     * <code>null</code> if none is found.
     * 
     * @param transactionId
     *            the transaction id to which the document belongs
     * @param targetype
     *            the type of document which is to be searched
     * @param dunninglevel
     *            the dunning level to prove
     * @throws FakturamaStoringException
     */
    public Document findExistingDocumentByTransactionIdAndBillingType(final Integer transactionId, final BillingType targetype) {
        Document retval = null;
        if (transactionId != null && targetype != null) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Document> criteria = cb.createQuery(getEntityClass());
            Root<Document> root = criteria.from(getEntityClass());
            Predicate whereClause = cb.and(cb.equal(root.<Integer> get(Document_.transactionId), transactionId),
                    cb.equal(root.<BillingType> get(Document_.billingType), targetype), cb.not(root.<Boolean> get(Document_.deleted)));
            CriteriaQuery<Document> cq = criteria.where(whereClause);
            try {
                List<Document> result = getEntityManager().createQuery(cq).getResultList();
                retval = !result.isEmpty() && result.size() > 0 ? result.get(0) : null;
            } catch (NoResultException e) {
                // is ok, we have to return a null value
            }
        }
        return retval;
    }

    //	public Set<Long> saveBatch(List<Document> resultList) throws FakturamaStoringException {
    //		Set<Long> documentIds = new HashSet<>();
    //		Set<Document> docSet = new HashSet<>();
    //		Document lastSuccessfulObject = null;
    //		try {
    //			checkConnection();
    //			EntityManager entityManager = getEntityManager();
    //			entityManager.setProperty(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
    //			entityManager.setProperty(PersistenceUnitProperties.BATCH_WRITING_SIZE, 20);
    //			EntityTransaction trx = entityManager.getTransaction();
    //			trx.begin();
    //			for (Document doc : resultList) {
    //				lastSuccessfulObject = entityManager.merge(doc);
    //				getEntityManager().persist(lastSuccessfulObject);
    //				// documentIds.add(currentDocument.getId());
    ////				System.out.println("t");
    //				docSet.add(lastSuccessfulObject);
    //			}
    //			trx.commit();
    //		} catch (SQLException e) {
    //			throw new FakturamaStoringException("Error saving to the database.", e, lastSuccessfulObject);
    //		}
    //		documentIds = docSet.stream().map(d -> d.getId()).collect(Collectors.toSet());
    //		return documentIds;
    //	}
    //	
    //	

}
