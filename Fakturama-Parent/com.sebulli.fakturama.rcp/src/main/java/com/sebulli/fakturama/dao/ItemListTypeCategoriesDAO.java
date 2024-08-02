package com.sebulli.fakturama.dao;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.sebulli.fakturama.model.ItemAccountType;
import com.sebulli.fakturama.model.ItemAccountType_;
import com.sebulli.fakturama.model.ItemListTypeCategory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;

@Creatable
public class ItemListTypeCategoriesDAO extends AbstractCategoriesDAO<ItemListTypeCategory> {

    @Override
    protected Class<ItemListTypeCategory> getEntityClass() {
        return ItemListTypeCategory.class;
    }

    @Override
    protected void updateObsoleteEntities(final ItemListTypeCategory oldCat) {
        // at first update all (deleted) entries in this category and set the category entry to null
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<ItemAccountType> updateItemAccountTypes = getEntityManager().getCriteriaBuilder().createCriteriaUpdate(ItemAccountType.class);
        Root<ItemAccountType> root = updateItemAccountTypes.from(ItemAccountType.class);
        updateItemAccountTypes.set(root.get(ItemAccountType_.category), (ItemListTypeCategory) null);
        updateItemAccountTypes.where(cb.and(cb.equal(root.get(ItemAccountType_.category), oldCat), cb.isTrue(root.get(ItemAccountType_.deleted))));
        getEntityManager().createQuery(updateItemAccountTypes).executeUpdate();
    }

}
