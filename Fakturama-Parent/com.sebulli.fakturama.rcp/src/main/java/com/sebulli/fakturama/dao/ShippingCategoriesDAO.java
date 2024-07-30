package com.sebulli.fakturama.dao;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.sebulli.fakturama.model.Shipping;
import com.sebulli.fakturama.model.ShippingCategory;
import com.sebulli.fakturama.model.Shipping_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;

@Creatable
public class ShippingCategoriesDAO extends AbstractCategoriesDAO<ShippingCategory> {

    @Override
    protected Class<ShippingCategory> getEntityClass() {
        return ShippingCategory.class;
    }

    @Override
    protected void updateObsoleteEntities(final ShippingCategory oldCat) {
        // at first update all (deleted) entries in this category and set the category entry to null
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Shipping> updateShippings = getEntityManager().getCriteriaBuilder().createCriteriaUpdate(Shipping.class);
        Root<Shipping> root = updateShippings.from(Shipping.class);
        updateShippings.set(root.get(Shipping_.categories), (ShippingCategory) null);
        updateShippings.where(cb.and(cb.equal(root.get(Shipping_.categories), oldCat), cb.isTrue(root.get(Shipping_.deleted))));
        getEntityManager().createQuery(updateShippings).executeUpdate();
    }

}
