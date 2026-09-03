package com.sebulli.fakturama.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.model.AbstractCategory;
import com.sebulli.fakturama.model.Product;
import com.sebulli.fakturama.model.ProductCategory;
import com.sebulli.fakturama.model.Product_;
import com.sebulli.fakturama.oldmodel.OldProducts;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Creatable
public class ProductsDAO extends AbstractDAO<Product> {

    @Inject
    private ProductCategoriesDAO productCategoriesDAO;

    /**
     * One page of products for the current search/category/sort criteria - the DB-side
     * equivalent of what {@code CommonListItemMatcher}/{@code GlazedLists.textFilterator} used to
     * do in-memory over the whole, fully-loaded product table (see {@code ProductListTable}).
     *
     * @param searchTerm free-text search across itemNumber/name/description, or blank/null for none
     * @param categoryName the tree-selected category path (e.g. "/Screws/M8"), or null/blank/root for none
     * @param orderByProperty a {@code Product_} attribute name, or null for the default (id desc)
     */
    public List<Product> findPage(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType,
            final String orderByProperty, final boolean descending, final int firstResult, final int maxResults) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
        final Root<Product> root = criteria.from(Product.class);
        criteria.distinct(true).where(buildPageablePredicate(cb, root, searchTerm, categoryName, treeObjectType));
        if (StringUtils.isNotBlank(orderByProperty)) {
            // id is always the last ORDER BY criterion for a stable row order across pages - ties
            // on orderByProperty alone (e.g. two products with the same name) would otherwise let
            // the DB return them in a different relative order per page, which can duplicate or
            // skip rows across a LIMIT/OFFSET boundary.
            final jakarta.persistence.criteria.Order order = descending ? cb.desc(root.get(orderByProperty)) : cb.asc(root.get(orderByProperty));
            criteria.orderBy(order, cb.desc(root.get(Product_.id)));
        } else {
            criteria.orderBy(cb.desc(root.get(Product_.id)));
        }
        // Fetch joins MUST be added before createQuery() - see findAll(boolean)'s javadoc.
        root.fetch(Product_.categories, JoinType.LEFT);
        root.fetch(Product_.vat, JoinType.LEFT);
        final TypedQuery<Product> query = getEntityManager().createQuery(criteria);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return query.getResultList();
    }

    /** Row count for the same criteria as {@link #findPage}. */
    public long countPage(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        final Root<Product> root = criteria.from(Product.class);
        criteria.select(cb.countDistinct(root)).where(buildPageablePredicate(cb, root, searchTerm, categoryName, treeObjectType));
        return getEntityManager().createQuery(criteria).getSingleResult();
    }

    private Predicate buildPageablePredicate(final CriteriaBuilder cb, final Root<Product> root, final String searchTerm, final String categoryName,
            final TreeObjectType treeObjectType) {
        Predicate predicate = cb.not(root.get(Product_.deleted));
        if (StringUtils.isNotBlank(searchTerm)) {
            final String likeTerm = "%" + searchTerm.toLowerCase(java.util.Locale.ROOT) + "%";
            predicate = cb.and(predicate,
                    cb.or(cb.like(cb.lower(root.get(Product_.itemNumber)), likeTerm), cb.like(cb.lower(root.get(Product_.name)), likeTerm),
                            cb.like(cb.lower(root.get(Product_.description)), likeTerm)));
        }
        final Set<Long> categoryIds = resolveCategoryIds(categoryName, treeObjectType);
        if (categoryIds != null) {
            // An empty IN(...) is not "no restriction" here - EclipseLink can compile it as an
            // unrestricted predicate instead of "always false", which would silently show
            // everything for a category selection that (for whatever reason) resolved to no
            // matching categories. Make that case explicitly match nothing instead.
            predicate = cb.and(predicate, categoryIds.isEmpty() ? cb.disjunction() : root.get(Product_.categories).get("id").in(categoryIds));
        }
        return predicate;
    }

    /**
     * Resolves the tree-selected category path (e.g. "/Screws/M8") to the set of {@link
     * ProductCategory} IDs whose full path starts with it - i.e. the selected node itself plus
     * every descendant, mirroring what {@code CommonListItemMatcher#matches} used to check
     * per-product in memory (path.startsWith(selected)). Categories are a small, bounded table
     * (unlike products), so walking it fully in Java once per filter change is cheap - much
     * cheaper than re-deriving this per product row in SQL would be. Returns {@code null} for "no
     * category filter" (root/all node or blank name).
     */
    private Set<Long> resolveCategoryIds(final String categoryName, final TreeObjectType treeObjectType) {
        if (treeObjectType == null || treeObjectType == TreeObjectType.ALL_NODE || treeObjectType == TreeObjectType.ROOT_NODE
                || StringUtils.isBlank(categoryName)) {
            return null;
        }
        // TreeObject#getFullPathName() always prepends the tree's synthetic, empty-named root
        // (TopicTreeViewer: "root = new TreeObject(\"\")"), giving e.g. "/Screws" or
        // "/Screws/M8" - strip that leading "/" to get the real category path, which then matches
        // 1:1 what CommonConverter.getCategoryName(category, "") computes for each real
        // ProductCategory (a real category's own getParent() chain never includes the synthetic UI
        // root - that's a TreeObject-only construct, not a real category row). An earlier version
        // of this tried to reconstruct and re-prepend the synthetic root's name (mirroring
        // AbstractViewDataTable#createRootNodeDescriptor) - that assumed a "/root/leaf"-shaped
        // path where index 1 is the real root, but for a *top-level* category the path is only
        // "/CategoryName" (one real segment), so index 1 was actually the category's own name -
        // which then got double-prepended onto itself and never matched anything. Confirmed broken
        // in testing (every non-"all" category filter returned zero rows).
        final String realPath = StringUtils.stripStart(categoryName, "/");
        if (realPath.isEmpty()) {
            return null;
        }
        final String prefix = StringUtils.stripEnd(realPath, "/");
        final List<ProductCategory> allCategories = productCategoriesDAO.findAll();
        final Set<Long> matchingIds = new HashSet<>();
        for (final ProductCategory category : allCategories) {
            final String fullPath = com.sebulli.fakturama.converter.CommonConverter.getCategoryName(category, "");
            if (fullPath.equals(prefix) || fullPath.startsWith(prefix + "/")) {
                matchingIds.add(category.getId());
            }
        }
        return matchingIds;
    }

    /**
     * Overrides {@link AbstractDAO#findAll(boolean)}, which has neither fetch joins nor an
     * inheritance/weaving-aware setup - it also sets {@code CACHE_STORE_MODE=REFRESH} when
     * {@code forceRead} is true. {@link Product}'s four relations ({@code categories}, {@code vat},
     * {@code attributes}, {@code blockPrices}) can't be made truly {@code LAZY} either (weaving
     * isn't enabled in this OSGi launch, same reason as Document/Contact - see DocumentsDAO's
     * fetchDocumentRelations() javadoc), so the plain generic findAll() turned "open the product
     * list" into the same per-row-per-relation N+1 that Document/Debitor/Creditor had before
     * tonight's fixes - REFRESH alone defeats fetch joins even where they exist (see
     * DebitorsDAO#findForListView's javadoc), and here there weren't even fetch joins to defeat.
     */
    @Override
    public List<Product> findAll(final boolean forceRead) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
        final Root<Product> root = criteria.from(Product.class);
        criteria.distinct(true).where(cb.not(root.get(Product_.deleted)));
        // Fetch joins MUST be added before createQuery() below - EclipseLink translates the
        // CriteriaQuery into its internal query representation at createQuery() time, so any
        // fetch() added afterwards on the Root is silently a no-op (verified the hard way on
        // DebitorsDAO/CreditorsDAO earlier tonight).
        root.fetch(Product_.categories, JoinType.LEFT);
        root.fetch(Product_.vat, JoinType.LEFT);
        root.fetch(Product_.attributes, JoinType.LEFT);
        root.fetch(Product_.blockPrices, JoinType.LEFT);
        final TypedQuery<Product> query = getEntityManager().createQuery(criteria);
        // Deliberately NOT using CACHE_STORE_MODE=REFRESH even though callers historically pass
        // forceRead=true - see DebitorsDAO#findForListView's javadoc for why that combination
        // defeats fetch joins and re-queries every relation one row at a time regardless.
        query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return query.getResultList();
    }

    @Inject
    @Preference
    private IEclipsePreferences eclipsePrefs;

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    /**
     * Finds a {@link Product} by a given {@link OldProducts}.
     * 
     * @param oldVat
     * @return
     */
    public Product findByOldVat(final OldProducts oldProduct) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);
        CriteriaQuery<Product> cq = criteria.where(cb.and(cb.equal(root.<String> get(Product_.description), oldProduct.getDescription()),
                cb.equal(root.<String> get(Product_.name), oldProduct.getName())));
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    /**
     * Counts all entities with the given category.
     * 
     * @param cat
     *            count of entities which have the given category
     */
    public long countByCategory(final AbstractCategory cat) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<Product> root = criteria.from(getEntityClass());
        criteria.select(cb.count(root)).where(cb.and(cb.equal(root.get(Product_.categories), cat), cb.isFalse(root.get(Product_.deleted))));
        return getEntityManager().createQuery(criteria).getSingleResult();
    }

    /**
     * @param object
     * @param cb
     * @param product
     * @return
     */
    @Override
    protected Set<Predicate> getRestrictions(final Product object, final CriteriaBuilder cb, final Root<Product> product) {
        Set<Predicate> restrictions = new HashSet<>();
        if (object.getWebshopId() != null && object.getWebshopId() > 0) {
            restrictions.add(cb.equal(product.get(Product_.webshopId), object.getWebshopId()));
        }
        restrictions.add(cb.equal(product.get(Product_.itemNumber), StringUtils.defaultString(object.getItemNumber())));
        restrictions.add(cb.equal(product.get(Product_.name), StringUtils.defaultString(object.getName())));
        return restrictions;
    }

    public List<Product> findSelectedProducts(final List<Long> selectedIds) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);
        CriteriaQuery<Product> cq = criteria.where(root.get(Product_.id).in(selectedIds));
        TypedQuery<Product> typedQuery = getEntityManager().createQuery(cq);
        typedQuery.setHint(QueryHints.CACHE_STORE_MODE, "REFRESH");
        return typedQuery.getResultList();
    }

    /**
     * Gets the all visible properties of this VAT object.
     * 
     * @return String[] of visible VAT properties
     */
    public String[] getVisibleProperties() {
        // remove invisible properties
        List<String> resultList = Arrays.asList(Product_.itemNumber.getName(), Product_.name.getName(), Product_.description.getName(),
                Product_.quantity.getName(), Product_.price1.getName(), Product_.vat.getName()).stream().filter((final String prop) -> {

                    if (eclipsePrefs != null) {
                        if (prop.equalsIgnoreCase(Product_.quantity.getName()) && !eclipsePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_QUANTITY, true)) {
                            return false;
                        } else if (prop.equalsIgnoreCase(Product_.vat.getName()) && !eclipsePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_VAT, true)) {
                            return false;
                        } else if (prop.equalsIgnoreCase(Product_.description.getName())
                                && !eclipsePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_DESCRIPTION, true)) {
                            return false;
                        } else if (prop.equalsIgnoreCase(Product_.itemNumber.getName())
                                && !eclipsePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_ITEMNR, true)) {
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toList());

        return resultList.toArray(new String[resultList.size()]);
    }

    public Product findByItemNumber(final String itemNo) {
        Product result = null;
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(getEntityClass());
        Root<Product> product = query.from(getEntityClass());
        query.select(product).where(cb.and(cb.equal(product.get(Product_.itemNumber), itemNo), cb.not(product.get(Product_.deleted))));
        TypedQuery<Product> q = getEntityManager().createQuery(query);
        q.setHint(QueryHints.CACHE_STORE_MODE, "REFRESH");
        q.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        try {
            result = q.getSingleResult();
        } catch (NoResultException e) {
            // no result means we return a null value
        } catch (Exception e) {
            // multiple results mean we have a problem with numbering of products
        }
        return result;
    }

    /**
     * Looks up the current product picture for the given item number from
     * VW_PRODUCT_PICTURE (Fakturama-2 changelog 2.2.1, MySQL/MariaDB only) - a
     * read-only view over fakturama-tool's FKT_PRODUCTPICTURES table. This is now the
     * only source for a product picture on the print/print-preview path -
     * Product.getPicture() is unused (pictures live in fakturama-tool's own table to
     * keep FKT_PRODUCT small) and DocumentItem.getPicture() is intentionally no longer
     * populated (see WebShopDataImporter#createOrderFromXMLOrderNode). Returns null whenever no
     * picture is available, including when the view itself doesn't exist (e.g. an
     * HSQLDB/H2 dev install, or one that never ran fakturama-tool) - a missing picture
     * must never fail a print job.
     *
     * @param itemNumber
     * @return the picture bytes, or null if none is available
     */
    public byte[] findPictureBytesForItemNumber(final String itemNumber) {
        if (StringUtils.isBlank(itemNumber)) {
            return null;
        }
        try {
            @SuppressWarnings("unchecked")
            List<Object> results = getEntityManager().createNativeQuery("SELECT PICTURE FROM VW_PRODUCT_PICTURE WHERE ITEMNUMBER = ?1")
                    .setParameter(1, itemNumber).getResultList();
            return results.isEmpty() ? null : (byte[]) results.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
