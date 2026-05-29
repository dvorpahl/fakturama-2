package com.sebulli.fakturama.dao;

import java.util.List;
import java.util.Optional;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.persistence.config.QueryHints;

import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.model.UserProperty;
import com.sebulli.fakturama.model.UserProperty_;
import com.sebulli.fakturama.oldmodel.OldProperties;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Creatable
public class PropertiesDAO extends AbstractDAO<UserProperty> {

    @Override
    protected Class<UserProperty> getEntityClass() {
        return UserProperty.class;
    }

    /**
     * Finds a {@link UserProperty} by a given {@link OldProperties}.
     * 
     * @param oldVat
     * @return
     */
    public OldProperties findByOldProperty(final OldProperties oldProperties) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<OldProperties> criteria = cb.createQuery(OldProperties.class);
        final Root<OldProperties> root = criteria.from(OldProperties.class);
        final CriteriaQuery<OldProperties> cq = criteria.where(
                cb.and(cb.equal(root.<String> get("description"), oldProperties.getName()), cb.equal(root.<String> get("name"), oldProperties.getName())));
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    /**
     * Finds the value of an user specific property.
     * 
     * @param name
     *            property
     * @return value of that property
     */
    public Optional<String> findPropertyValue(final String name) {
        return findPropertyValue(name, false);
    }

    /**
     * Finds the value of an user specific property.
     * 
     * @param name
     *            property
     * @param force
     *            forces read of database
     * @return value of that property
     */
    public Optional<String> findPropertyValue(final String name, final boolean force) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<UserProperty> criteria = cb.createQuery(getEntityClass());
        final Root<UserProperty> root = criteria.from(UserProperty.class);
        criteria.where(cb.equal(root.get(UserProperty_.name), name)).orderBy(cb.desc(root.get(UserProperty_.dateAdded)));
        TypedQuery<UserProperty> query = null;
        UserProperty result = null;
        try {
            query = getEntityManager().createQuery(criteria);
            query.setHint(QueryHints.CACHE_STORE_MODE, "REFRESH");
            result = query.getSingleResult();
        } catch (final NoResultException e) {
            // ignore, retval is an empty Optional
        } catch (final NonUniqueResultException nuex) {
            log.warn("non-unique result found for property " + name);
            result = query.getResultList().get(0);
        }
        return result != null ? Optional.ofNullable(result.getValue()) : Optional.empty();
    }

    /**
     * Updates a user property. If the given property isn't available then it
     * will be created.
     * 
     * @param key
     * @param value
     */
    public void setProperty(final String key, final String value) {
        try {
            UserProperty prop = findByName(key);
            if (prop != null) {
                prop.setValue(value);
                update(prop);
            } else {
                prop = modelFactory.createUserProperty();
                prop.setName(key);
                prop.setValue(value);
                prop.setUser(System.getProperty("user.name", "(unknown)"));
                save(prop);
            }
        } catch (final FakturamaStoringException e) {
            getLog().error(e);
        }
    }

    /**
     * Find all stored mappings for a certain section.
     * 
     * @param qualifier
     *            which section should be used (product, contact etc)
     * @return List of valid mappings
     */
    public List<UserProperty> findMappingSpecs(final String qualifier) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<UserProperty> criteria = cb.createQuery(getEntityClass());
        final Root<UserProperty> root = criteria.from(UserProperty.class);
        criteria.where(cb.equal(root.get(UserProperty_.qualifier), qualifier));
        List<UserProperty> result = null;
        try {
            final TypedQuery<UserProperty> query = getEntityManager().createQuery(criteria);
            query.setHint(QueryHints.CACHE_STORE_MODE, "REFRESH");
            result = query.getResultList();
        } catch (final NoResultException e) {
            // ignore
        }
        return result;
    }

    public boolean delete(UserProperty prop) {
        if (prop != null) {
            final EntityManager entityManager = getEntityManager();
            final EntityTransaction trx = entityManager.getTransaction();
            trx.begin();
            prop = entityManager.merge(prop);
            getEntityManager().remove(prop);
            trx.commit();
        }
        return true;
    }
}
