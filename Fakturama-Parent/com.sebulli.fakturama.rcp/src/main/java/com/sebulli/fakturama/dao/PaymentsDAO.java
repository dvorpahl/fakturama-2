package com.sebulli.fakturama.dao;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.emf.ecore.EClass;

import com.sebulli.fakturama.model.FakturamaModelPackage;
import com.sebulli.fakturama.model.Payment;
import com.sebulli.fakturama.model.Payment_;
import com.sebulli.fakturama.oldmodel.OldPayments;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Creatable
public class PaymentsDAO extends AbstractDAO<Payment> {

    @Override
    protected Class<Payment> getEntityClass() {
        return Payment.class;
    }

    @Override
    protected EClass getEClass() {
        return FakturamaModelPackage.INSTANCE.getPaymentEClass();
    }

    /**
     * Finds a {@link Payment} by a given {@link OldPayments}.
     * 
     * @param oldVat
     * @return
     */
    public Payment findByOldPayment(final OldPayments oldPayment) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Payment> criteria = cb.createQuery(Payment.class);
        Root<Payment> root = criteria.from(Payment.class);
        CriteriaQuery<Payment> cq = criteria
                .where(cb.and(cb.equal(root.get(Payment_.description), oldPayment.getDescription()), cb.equal(root.get(Payment_.name), oldPayment.getName())));
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    protected Set<Predicate> getRestrictions(final Payment object, final CriteriaBuilder criteriaBuilder, final Root<Payment> root) {
        Set<Predicate> restrictions = new HashSet<>();
        /*
         * Only the names are compared.
         */
        restrictions.add(criteriaBuilder.equal(root.get(Payment_.name), StringUtils.defaultString(object.getName())));
        return restrictions;
    }

    /**
     * Gets the all visible properties of this Shipping object.
     * 
     * @return String[] of visible Shipping properties
     */
    public String[] getVisibleProperties() {
        return new String[] { Payment_.name.getName(), Payment_.description.getName(), Payment_.discountValue.getName(), Payment_.discountDays.getName(),
                Payment_.netDays.getName() };
    }

}
