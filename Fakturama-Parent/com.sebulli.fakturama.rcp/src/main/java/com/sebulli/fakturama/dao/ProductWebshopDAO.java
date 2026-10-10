package com.sebulli.fakturama.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.model.Product;
import com.sebulli.fakturama.model.ProductWebshop;
import com.sebulli.fakturama.model.ProductWebshop_;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Creatable
public class ProductWebshopDAO extends AbstractDAO<ProductWebshop> {

    @Override
    protected Class<ProductWebshop> getEntityClass() {
        return ProductWebshop.class;
    }

    /**
     * Finds the webshop overlay row for a product, if one exists - not every
     * product is flagged "im Shop", so this is {@code null} for most
     * products.
     *
     * @param product
     *            the product to look up
     * @return the webshop overlay, or {@code null} if the product has none
     */
    public ProductWebshop findByProduct(final Product product) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<ProductWebshop> criteria = cb.createQuery(ProductWebshop.class);
        final Root<ProductWebshop> root = criteria.from(ProductWebshop.class);
        criteria.where(cb.and(cb.equal(root.get(ProductWebshop_.product), product), cb.isFalse(root.get(ProductWebshop_.deleted))));
        final TypedQuery<ProductWebshop> query = getEntityManager().createQuery(criteria);
        ProductWebshop result = null;
        try {
            result = query.getSingleResult();
        } catch (final NoResultException e) {
            // no webshop data for this product yet - that's the normal case for most products
        }
        return result;
    }

    /**
     * Batched counterpart to {@link #findByProduct(Product)} for a whole page of products at
     * once (see {@code ProductListTable}'s "Webshop price" column) - Product has no mapped
     * relationship back to ProductWebshop (only the other direction, see
     * {@link ProductWebshop#getProduct()}), so a JPA fetch-join from the Product side isn't
     * possible here; this is the one query-per-page alternative instead of one query per row.
     *
     * @param products
     *            the page of products to look up webshop overlays for
     * @return a map from {@link Product#getId()} to its (non-deleted) webshop overlay - products
     *         without one (most products) are simply absent from the map, not mapped to
     *         {@code null}
     */
    public Map<Long, ProductWebshop> findByProducts(final Collection<Product> products) {
        if (products == null || products.isEmpty()) {
            return Collections.emptyMap();
        }
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<ProductWebshop> criteria = cb.createQuery(ProductWebshop.class);
        final Root<ProductWebshop> root = criteria.from(ProductWebshop.class);
        criteria.where(cb.and(root.get(ProductWebshop_.product).in(products), cb.isFalse(root.get(ProductWebshop_.deleted))));
        final TypedQuery<ProductWebshop> query = getEntityManager().createQuery(criteria);
        // The product editor saves the webshop overlay through another editor action while this
        // DAO may already have an older managed instance in EclipseLink's first-level cache.
        // Force the list query to use the just-persisted values after a product save.
        query.setHint(QueryHints.REFRESH, HintValues.TRUE);
        final List<ProductWebshop> results = query.getResultList();
        return results.stream().collect(Collectors.toMap((final ProductWebshop pw) -> pw.getProduct().getId(), Function.identity()));
    }

    /**
     * Finds the existing webshop overlay for a product, or creates a new
     * (unsaved) one already linked to it. Useful both for the article
     * editor's "im Shop" tab and for the sync client, which needs exactly one
     * row per shop-enabled product.
     *
     * @param product
     *            the product to find or create a webshop overlay for
     * @return the existing or newly persisted webshop overlay
     * @throws FakturamaStoringException
     *             if creating a new overlay fails
     */
    public ProductWebshop findOrCreateForProduct(final Product product) throws FakturamaStoringException {
        final ProductWebshop template = new ProductWebshop();
        template.setProduct(product);
        return findOrCreate(template);
    }

    /** Enables or disables the webshop overlay for several products. */
    public void setEnabled(final Collection<Product> products, final boolean enabled) throws FakturamaStoringException {
        if (products == null) {
            return;
        }
        for (final Product product : products) {
            ProductWebshop webshop = findByProductIncludingDeleted(product);
            if (enabled) {
                if (webshop == null) {
                    webshop = findOrCreateForProduct(product);
                }
                boolean changed = false;
                if (Boolean.TRUE.equals(webshop.getDeleted())) {
                    webshop.setDeleted(Boolean.FALSE);
                    changed = true;
                }
                if (webshop.getShopPrice() == null && product.getPrice1() != null) {
                    webshop.setShopPrice(product.getPrice1());
                    changed = true;
                }
                if (changed) {
                    update(webshop);
                }
            } else if (webshop != null && !Boolean.TRUE.equals(webshop.getDeleted())) {
                webshop.setDeleted(Boolean.TRUE);
                update(webshop);
            }
        }
    }

    private ProductWebshop findByProductIncludingDeleted(final Product product) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<ProductWebshop> criteria = cb.createQuery(ProductWebshop.class);
        final Root<ProductWebshop> root = criteria.from(ProductWebshop.class);
        criteria.where(cb.equal(root.get(ProductWebshop_.product), product));
        final List<ProductWebshop> results = getEntityManager().createQuery(criteria).setMaxResults(1).getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    protected Set<Predicate> getRestrictions(final ProductWebshop object, final CriteriaBuilder cb, final Root<ProductWebshop> root) {
        final Set<Predicate> restrictions = new HashSet<>();
        restrictions.add(cb.equal(root.get(ProductWebshop_.product), object.getProduct()));
        return restrictions;
    }
}
