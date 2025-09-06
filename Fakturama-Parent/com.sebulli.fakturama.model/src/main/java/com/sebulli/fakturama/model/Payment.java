package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.util.Date;

import com.sebulli.fakturama.misc.UNTDID4461;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * A representation of the model object '<em><b>Payment</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_PAYMENT")
@EntityListeners(value = { EntityListener.class })
public class Payment extends ModelObject implements Serializable, IDescribableEntity, Cloneable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DISCOUNTDAYS")
    private Integer discountDays = Integer.valueOf(0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DISCOUNTVALUE")
    private Double discountValue = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "NETDAYS")
    private Integer netDays = Integer.valueOf(0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_CATEGORY") })
    private VoucherCategory category = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(length = 4096, name = "PAIDTEXT")
    private String paidText = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(length = 4096, name = "UNPAIDTEXT")
    private String unpaidText = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(length = 4096, name = "DEPOSITTEXT")
    private String depositText = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The code based on UNCL 4461. This field is necessary for creating ZUGFeRD
     * invoices. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "CODE")
    private String code = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DESCRIPTION")
    @Lob()
    private String description = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "NAME")
    private String name = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DATEADDED")
    @Temporal(TemporalType.DATE)
    private Date dateAdded = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "MODIFIEDBY")
    private String modifiedBy = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "MODIFIED")
    @Temporal(TemporalType.DATE)
    private Date modified = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DELETED")
    private Boolean deleted = Boolean.FALSE;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VALIDFROM")
    @Temporal(TemporalType.DATE)
    private Date validFrom = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VALIDTO")
    @Temporal(TemporalType.DATE)
    private Date validTo = null;

    /**
     * A semantical compare method. This method compares the actual object
     * attribute by attribute to another object.
     *
     * @param other
     *            the other object to compare
     * @generated
     */
    public boolean isSameAs(final Payment other) {
        boolean retval = true;
        if (other != null) {
            if (discountDays != null && other.getDiscountDays() != null) {
                retval &= discountDays.compareTo(other.getDiscountDays()) == 0;
            }
            if (discountValue != null && other.getDiscountValue() != null) {
                retval &= discountValue.compareTo(other.getDiscountValue()) == 0;
            }
            if (netDays != null && other.getNetDays() != null) {
                retval &= netDays.compareTo(other.getNetDays()) == 0;
            }
            if (getCategory() != null) {
                retval &= getCategory().isSameAs(other.getCategory());
            }
            if (paidText != null && other.getPaidText() != null) {
                retval &= paidText.compareTo(other.getPaidText()) == 0;
            }
            if (unpaidText != null && other.getUnpaidText() != null) {
                retval &= unpaidText.compareTo(other.getUnpaidText()) == 0;
            }
            if (depositText != null && other.getDepositText() != null) {
                retval &= depositText.compareTo(other.getDepositText()) == 0;
            }
            if (code != null && other.getCode() != null) {
                retval &= code.compareTo(other.getCode()) == 0;
            }
            if (description != null && other.getDescription() != null) {
                retval &= description.compareTo(other.getDescription()) == 0;
            }
            if (name != null && other.getName() != null) {
                retval &= name.compareTo(other.getName()) == 0;
            }
            if (dateAdded != null && other.getDateAdded() != null) {
                retval &= dateAdded.compareTo(other.getDateAdded()) == 0;
            }
            if (modifiedBy != null && other.getModifiedBy() != null) {
                retval &= modifiedBy.compareTo(other.getModifiedBy()) == 0;
            }
            if (modified != null && other.getModified() != null) {
                retval &= modified.compareTo(other.getModified()) == 0;
            }

            if (deleted != null && other.getDeleted() != null) {
                retval &= deleted.compareTo(other.getDeleted()) == 0;
            }
            if (validFrom != null && other.getValidFrom() != null) {
                retval &= validFrom.compareTo(other.getValidFrom()) == 0;
            }
            if (validTo != null && other.getValidTo() != null) {
                retval &= validTo.compareTo(other.getValidTo()) == 0;
            }
        } else {
            retval = false;
        }
        return retval;
    }

    @Override
    public Payment clone() {
        final Payment retval = new Payment();
        retval.setDiscountDays(this.getDiscountDays());
        retval.setDiscountValue(this.getDiscountValue());
        retval.setNetDays(this.getNetDays());
        retval.setCategory(this.getCategory());
        retval.setPaidText(this.getPaidText());
        retval.setUnpaidText(this.getUnpaidText());
        retval.setDepositText(this.getDepositText());
        retval.setCode(this.getCode());
        retval.setDescription(this.getDescription());
        retval.setName(this.getName());
        retval.setDateAdded(this.getDateAdded());
        retval.setModifiedBy(this.getModifiedBy());
        retval.setModified(this.getModified());

        retval.setDeleted(this.getDeleted());
        retval.setValidFrom(this.getValidFrom());
        retval.setValidTo(this.getValidTo());
        return retval;
    }

    /**
     * Returns the value of '<em><b>discountDays</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>discountDays</b></em>' feature
     * @generated
     */
    public Integer getDiscountDays() {

        return discountDays;
    }

    /**
     * Sets the '{@link Payment#getDiscountDays() <em>discountDays</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDiscountDays
     *            the new value of the '{@link Payment#getDiscountDays()
     *            discountDays}' feature.
     * @generated
     */
    public void setDiscountDays(final Integer newDiscountDays) {
        firePropertyChange("discountDays", this.discountDays, newDiscountDays);
        discountDays = newDiscountDays;
    }

    /**
     * Returns the value of '<em><b>discountValue</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>discountValue</b></em>' feature
     * @generated
     */
    public Double getDiscountValue() {

        return discountValue;
    }

    /**
     * Sets the '{@link Payment#getDiscountValue() <em>discountValue</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDiscountValue
     *            the new value of the '{@link Payment#getDiscountValue()
     *            discountValue}' feature.
     * @generated
     */
    public void setDiscountValue(final Double newDiscountValue) {
        firePropertyChange("discountValue", this.discountValue, newDiscountValue);
        discountValue = newDiscountValue;
    }

    /**
     * Returns the value of '<em><b>netDays</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>netDays</b></em>' feature
     * @generated
     */
    public Integer getNetDays() {

        return netDays;
    }

    /**
     * Sets the '{@link Payment#getNetDays() <em>netDays</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newNetDays
     *            the new value of the '{@link Payment#getNetDays() netDays}'
     *            feature.
     * @generated
     */
    public void setNetDays(final Integer newNetDays) {
        firePropertyChange("netDays", this.netDays, newNetDays);
        netDays = newNetDays;
    }

    /**
     * Returns the value of '<em><b>category</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>category</b></em>' feature
     * @generated
     */
    public VoucherCategory getCategory() {

        return category;
    }

    /**
     * Sets the '{@link Payment#getCategory() <em>category</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCategory
     *            the new value of the '{@link Payment#getCategory() category}'
     *            feature.
     * @generated
     */
    public void setCategory(final VoucherCategory newCategory) {
        firePropertyChange("category", this.category, newCategory);
        category = newCategory;
    }

    /**
     * Returns the value of '<em><b>paidText</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>paidText</b></em>' feature
     * @generated
     */
    public String getPaidText() {

        return paidText;
    }

    /**
     * Sets the '{@link Payment#getPaidText() <em>paidText</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPaidText
     *            the new value of the '{@link Payment#getPaidText() paidText}'
     *            feature.
     * @generated
     */
    public void setPaidText(final String newPaidText) {
        firePropertyChange("paidText", this.paidText, newPaidText);
        paidText = newPaidText;
    }

    /**
     * Returns the value of '<em><b>unpaidText</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>unpaidText</b></em>' feature
     * @generated
     */
    public String getUnpaidText() {

        return unpaidText;
    }

    /**
     * Sets the '{@link Payment#getUnpaidText() <em>unpaidText</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newUnpaidText
     *            the new value of the '{@link Payment#getUnpaidText()
     *            unpaidText}' feature.
     * @generated
     */
    public void setUnpaidText(final String newUnpaidText) {
        firePropertyChange("unpaidText", this.unpaidText, newUnpaidText);
        unpaidText = newUnpaidText;
    }

    /**
     * Returns the value of '<em><b>depositText</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>depositText</b></em>' feature
     * @generated
     */
    public String getDepositText() {

        return depositText;
    }

    /**
     * Sets the '{@link Payment#getDepositText() <em>depositText</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDepositText
     *            the new value of the '{@link Payment#getDepositText()
     *            depositText}' feature.
     * @generated
     */
    public void setDepositText(final String newDepositText) {
        firePropertyChange("depositText", this.depositText, newDepositText);
        depositText = newDepositText;
    }

    /**
     * Returns the value of '<em><b>code</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The code based on UNCL 4461. This field is necessary for creating ZUGFeRD
     * invoices. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>code</b></em>' feature
     * @generated
     */
    public String getCode() {

        return code;
    }

    /**
     * Sets the '{@link Payment#getCode() <em>code</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The code based on UNCL 4461. This field is necessary for creating ZUGFeRD
     * invoices. <!-- end-model-doc -->
     * 
     * @param newCode
     *            the new value of the '{@link Payment#getCode() code}' feature.
     * @generated
     */
    public void setCode(final String newCode) {
        UNTDID4461 untCode;
        if ((untCode = UNTDID4461.getByCode(newCode)) != null) {
            firePropertyChange("code", this.code, newCode);
            code = untCode.getCode();
        }
    }

    /**
     * Returns the value of '<em><b>description</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>description</b></em>' feature
     * @generated
     */
    @Override
    public String getDescription() {

        return description;
    }

    /**
     * Sets the '{@link Payment#getDescription() <em>description</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDescription
     *            the new value of the '{@link Payment#getDescription()
     *            description}' feature.
     * @generated
     */
    @Override
    public void setDescription(final String newDescription) {
        firePropertyChange("description", this.description, newDescription);
        description = newDescription;
    }

    /**
     * Returns the value of '<em><b>name</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>name</b></em>' feature
     * @generated
     */
    @Override
    public String getName() {

        return name;
    }

    /**
     * Sets the '{@link Payment#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link Payment#getName() name}' feature.
     * @generated
     */
    @Override
    public void setName(final String newName) {
        firePropertyChange("name", this.name, newName);
        name = newName;
    }

    /**
     * Returns the value of '<em><b>dateAdded</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>dateAdded</b></em>' feature
     * @generated
     */
    @Override
    public Date getDateAdded() {

        return dateAdded;
    }

    /**
     * Sets the '{@link Payment#getDateAdded() <em>dateAdded</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link Payment#getDateAdded()
     *            dateAdded}' feature.
     * @generated
     */
    @Override
    public void setDateAdded(final Date newDateAdded) {
        firePropertyChange("dateAdded", this.dateAdded, newDateAdded);
        dateAdded = newDateAdded;
    }

    /**
     * Returns the value of '<em><b>modifiedBy</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>modifiedBy</b></em>' feature
     * @generated
     */
    @Override
    public String getModifiedBy() {

        return modifiedBy;
    }

    /**
     * Sets the '{@link Payment#getModifiedBy() <em>modifiedBy</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link Payment#getModifiedBy()
     *            modifiedBy}' feature.
     * @generated
     */
    @Override
    public void setModifiedBy(final String newModifiedBy) {
        firePropertyChange("modifiedBy", this.modifiedBy, newModifiedBy);
        modifiedBy = newModifiedBy;
    }

    /**
     * Returns the value of '<em><b>modified</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>modified</b></em>' feature
     * @generated
     */
    @Override
    public Date getModified() {

        return modified;
    }

    /**
     * Sets the '{@link Payment#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link Payment#getModified() modified}'
     *            feature.
     * @generated
     */
    @Override
    public void setModified(final Date newModified) {
        firePropertyChange("modified", this.modified, newModified);
        modified = newModified;
    }

    /**
     * Returns the value of '<em><b>id</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>id</b></em>' feature
     * @generated
     */
    @Override
    public long getId() {

        return id;
    }

    /**
     * Sets the '{@link Payment#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link Payment#getId() id}' feature.
     * @generated
     */
    @Override
    public void setId(final long newId) {
        firePropertyChange("id", this.id, newId);
        id = newId;
    }

    /**
     * Returns the value of '<em><b>deleted</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>deleted</b></em>' feature
     * @generated
     */
    @Override
    public Boolean getDeleted() {

        return deleted;
    }

    /**
     * Sets the '{@link Payment#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link Payment#getDeleted() deleted}'
     *            feature.
     * @generated
     */
    @Override
    public void setDeleted(final Boolean newDeleted) {
        firePropertyChange("deleted", this.deleted, newDeleted);
        deleted = newDeleted;
    }

    /**
     * Returns the value of '<em><b>validFrom</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>validFrom</b></em>' feature
     * @generated
     */
    @Override
    public Date getValidFrom() {

        return validFrom;
    }

    /**
     * Sets the '{@link Payment#getValidFrom() <em>validFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link Payment#getValidFrom()
     *            validFrom}' feature.
     * @generated
     */
    @Override
    public void setValidFrom(final Date newValidFrom) {
        firePropertyChange("validFrom", this.validFrom, newValidFrom);
        validFrom = newValidFrom;
    }

    /**
     * Returns the value of '<em><b>validTo</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>validTo</b></em>' feature
     * @generated
     */
    @Override
    public Date getValidTo() {

        return validTo;
    }

    /**
     * Sets the '{@link Payment#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link Payment#getValidTo() validTo}'
     *            feature.
     * @generated
     */
    @Override
    public void setValidTo(final Date newValidTo) {
        firePropertyChange("validTo", this.validTo, newValidTo);
        validTo = newValidTo;
    }

    /**
     * A toString method which prints the values of all EAttributes of this
     * instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "Payment" + " discountDays: [" + getDiscountDays() + "]" + " discountValue: [" + getDiscountValue() + "]" + " netDays: [" + getNetDays() + "]"
                + " paidText: [" + getPaidText() + "]" + " unpaidText: [" + getUnpaidText() + "]" + " depositText: [" + getDepositText() + "]" + " code: ["
                + getCode() + "]" + " description: [" + getDescription() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded() + "]"
                + " modifiedBy: [" + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: [" + getDeleted()
                + "]" + " validFrom: [" + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
