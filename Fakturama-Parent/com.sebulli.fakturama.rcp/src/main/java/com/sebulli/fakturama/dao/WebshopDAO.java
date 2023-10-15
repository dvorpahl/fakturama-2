package com.sebulli.fakturama.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.persistence.queries.QueryByExamplePolicy;

import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.model.FakturamaModelPackage;
import com.sebulli.fakturama.model.WebShop;
import com.sebulli.fakturama.model.WebShop_;
import com.sebulli.fakturama.model.WebshopStateMapping;
import com.sebulli.fakturama.model.WebshopStateMapping_;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Creatable
public class WebshopDAO extends AbstractDAO<WebShop> {

    List<String> excludedAttributes = Arrays.asList(WebshopStateMapping_.fakturamaOrderState.getName());

    @Inject
    @Translation
    protected Messages msg;

    @Override
    protected Class<WebShop> getEntityClass() {
        return WebShop.class;
    }

    @PostConstruct
    public void init() {
        FakturamaModelPackage.initialize(); // objectStore.get(WebshopStateMapping.class,
                                            // 1L);
    }

    public List<WebshopStateMapping> findAllForWebshop(final String webshopId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<WebShop> criteria = cb.createQuery(getEntityClass());
        Root<WebShop> root = criteria.from(getEntityClass());
        CriteriaQuery<WebShop> cq = criteria.where(cb.equal(root.<WebShop> get(WebShop_.name.getName()), webshopId));
        TypedQuery<WebShop> query = getEntityManager().createQuery(cq);
        // TODO doesn't work :-(
        //		query.setHint(QueryHints.FETCH, "FKT_WEBSHOP." + WebShop_.stateMapping.getName()/* + "." + WebshopStateMapping_.name.getName()*/);
        WebShop result;
        try {
            result = query.getSingleResult();
            // initialize the stateMapping manually...
            result.getStateMapping().forEach(e -> e.getDeleted());
        } catch (NoResultException e) {
            result = null;
        }
        return result != null ? result.getStateMapping() : Collections.emptyList();
    }

    @Override
    protected Set<Predicate> getRestrictions(final WebShop object, final CriteriaBuilder criteriaBuilder, final Root<WebShop> root) {
        Set<Predicate> restrictions = new HashSet<>();
        restrictions.add(criteriaBuilder.equal(root.get(WebShop_.name), object.getName()));
        return restrictions;
    }

    @Override
    protected Map<Class<WebShop>, Vector<String>> getAlwaysIncludeAttributes() {
        Map<Class<WebShop>, Vector<String>> map = new HashMap<>();
        Vector<String> attribVector = new Vector<>();
        attribVector.addElement(WebShop_.stateMapping.getName() + "." + WebshopStateMapping_.webshopState.getName());
        attribVector.addElement(WebShop_.stateMapping.getName() + "." + WebshopStateMapping_.fakturamaOrderState.getName());
        map.put(getEntityClass(), attribVector);
        return map;
    }

    @Override
    protected QueryByExamplePolicy getQueryByExamplePolicy() {
        QueryByExamplePolicy policy = new WebshopStateQueryByExamplePolicy();
        policy.addSpecialOperation(String.class, "containsSubstring");
        policy.setAttributesToAlwaysInclude(getAlwaysIncludeAttributes());
        policy.setShouldUseEqualityForNulls(true);
        return policy;
    }

    /**
     * Clear old mappings.
     *
     * @param webshopId
     *            the webshop id
     * @throws FakturamaStoringException
     */
    public WebShop clearOldMappings(final String webshopId) throws FakturamaStoringException {
        WebShop webShop = findByName(webshopId);
        if (webShop != null) {
            List<Long> orphanedStateIds = webShop.getStateMapping().stream().map(WebshopStateMapping::getId).collect(Collectors.toList());
            webShop.setStateMapping(new ArrayList<>());

            // remove old mapping entries from database
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaDelete<WebshopStateMapping> deleteQuery = criteriaBuilder.createCriteriaDelete(WebshopStateMapping.class);
            Root<WebshopStateMapping> root = deleteQuery.from(WebshopStateMapping.class);
            deleteQuery.where(root.get(WebshopStateMapping_.id).in(orphanedStateIds));
            try {
                checkConnection();
                EntityTransaction trx = getEntityManager().getTransaction();
                trx.begin();
                getEntityManager().createQuery(deleteQuery).executeUpdate();
                trx.commit();
            } catch (SQLException e) {
                throw new FakturamaStoringException("Error cleaning web shop state mappings from database for your web shop (name=" + webshopId + ").", e);
            }

            webShop = save(webShop);
        }
        return webShop;
    }

    public Optional<WebshopStateMapping> findOrderState(final String webshopId, final String status) {
        Optional<WebshopStateMapping> mapping = Optional.empty();
        WebShop result = findByName(webshopId);
        if (result != null) {
            mapping = result.getStateMapping().stream().filter(s -> status.equalsIgnoreCase(s.getName())).findFirst();
        }
        return mapping;
    }

    /**
     * Creates an identifier (name) for a webshop (only for lookup in the
     * database).
     * 
     * @param webShopUrl
     *            the URL where the shop resides
     * @return identifier for the shop
     */
    public String createWebShopIdentifier(final String webShopUrl) {
        String retval = "DEFAULT_SHOP_ID";
        String[] splittedUrl = StringUtils.split(webShopUrl, "/");
        if (splittedUrl.length > 2) {
            retval = splittedUrl[1];
        }
        return retval;
    }

    class WebshopStateQueryByExamplePolicy extends QueryByExamplePolicy {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -6551973233104289426L;

        @SuppressWarnings("rawtypes")
        @Override
        public boolean shouldIncludeInQuery(final Class aClass, final String attributeName, final Object attributeValue) {
            boolean preCheckedValue = super.shouldIncludeInQuery(aClass, attributeName, attributeValue);
            // enumerate all attributes which don't have to come into the query
            boolean retval = !excludedAttributes.contains(attributeName);
            return retval && preCheckedValue;
        }
    }
}
