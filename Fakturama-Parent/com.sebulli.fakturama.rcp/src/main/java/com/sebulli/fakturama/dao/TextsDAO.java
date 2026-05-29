package com.sebulli.fakturama.dao;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.sebulli.fakturama.model.AbstractCategory;
import com.sebulli.fakturama.model.TextModule;
import com.sebulli.fakturama.model.TextModule_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Creatable
public class TextsDAO extends AbstractDAO<TextModule> {

    @Override
    protected Class<TextModule> getEntityClass() {
        return TextModule.class;
    }

    /**
     * Gets the all visible properties of this VAT object.
     * 
     * @return String[] of visible VAT properties
     */
    public String[] getVisibleProperties() {
        return new String[] { TextModule_.name.getName(), TextModule_.text.getName() };
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
        Root<TextModule> root = criteria.from(getEntityClass());
        criteria.select(cb.count(root)).where(cb.equal(root.get(TextModule_.categories), cat));
        return getEntityManager().createQuery(criteria).getSingleResult();
    }

}
