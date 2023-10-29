package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.lang.Boolean;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * A representation of the model object
 * '<em><b>IndividualDocumentInfo</b></em>'. <!-- begin-user-doc --> <!--
 * end-user-doc --> <!-- begin-model-doc --> Container for additional infos for
 * a document. <!-- end-model-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_INDIVIDUALDOCUMENTINFO")
@EntityListeners(value = { EntityListener.class })
public class IndividualDocumentInfo implements IEntity, Serializable, Cloneable {
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
    @Column(name = "PAYMENTNAME")
    private String paymentName = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(length = 4096, name = "PAYMENTTEXT")
    private String paymentText = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(length = 2048, name = "PAYMENTDESCRIPTION")
    private String paymentDescription = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SHIPPINGNAME")
    private String shippingName = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(length = 2048, name = "SHIPPINGDESCRIPTION")
    private String shippingDescription = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(length = 2048, name = "SHIPPINGVATDESCRIPTION")
    private String shippingVatDescription = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SHIPPINGVATVALUE")
    private Double shippingVatValue = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "NOVATNAME")
    private String noVatName = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(length = 2048, name = "NOVATDESCRIPTION")
    private String noVatDescription = null;

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
    public boolean isSameAs(IndividualDocumentInfo other) {
        boolean retval = true;
        if (other != null) {
            if (paymentName != null && other.getPaymentName() != null) {
                retval &= paymentName.compareTo(other.getPaymentName()) == 0;
            }
            if (paymentText != null && other.getPaymentText() != null) {
                retval &= paymentText.compareTo(other.getPaymentText()) == 0;
            }
            if (paymentDescription != null && other.getPaymentDescription() != null) {
                retval &= paymentDescription.compareTo(other.getPaymentDescription()) == 0;
            }
            if (shippingName != null && other.getShippingName() != null) {
                retval &= shippingName.compareTo(other.getShippingName()) == 0;
            }
            if (shippingDescription != null && other.getShippingDescription() != null) {
                retval &= shippingDescription.compareTo(other.getShippingDescription()) == 0;
            }
            if (shippingVatDescription != null && other.getShippingVatDescription() != null) {
                retval &= shippingVatDescription.compareTo(other.getShippingVatDescription()) == 0;
            }
            if (shippingVatValue != null && other.getShippingVatValue() != null) {
                retval &= shippingVatValue.compareTo(other.getShippingVatValue()) == 0;
            }
            if (noVatName != null && other.getNoVatName() != null) {
                retval &= noVatName.compareTo(other.getNoVatName()) == 0;
            }
            if (noVatDescription != null && other.getNoVatDescription() != null) {
                retval &= noVatDescription.compareTo(other.getNoVatDescription()) == 0;
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
    public IndividualDocumentInfo clone() {
        IndividualDocumentInfo retval = new IndividualDocumentInfo();
        retval.setPaymentName(this.getPaymentName());
        retval.setPaymentText(this.getPaymentText());
        retval.setPaymentDescription(this.getPaymentDescription());
        retval.setShippingName(this.getShippingName());
        retval.setShippingDescription(this.getShippingDescription());
        retval.setShippingVatDescription(this.getShippingVatDescription());
        retval.setShippingVatValue(this.getShippingVatValue());
        retval.setNoVatName(this.getNoVatName());
        retval.setNoVatDescription(this.getNoVatDescription());
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
     * Returns the value of '<em><b>paymentName</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>paymentName</b></em>' feature
     * @generated
     */
    public String getPaymentName() {

        return paymentName;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getPaymentName()
     * <em>paymentName</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPaymentName
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getPaymentName() paymentName}'
     *            feature.
     * @generated
     */
    public void setPaymentName(String newPaymentName) {
        paymentName = newPaymentName;
    }

    /**
     * Returns the value of '<em><b>paymentText</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>paymentText</b></em>' feature
     * @generated
     */
    public String getPaymentText() {

        return paymentText;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getPaymentText()
     * <em>paymentText</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPaymentText
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getPaymentText() paymentText}'
     *            feature.
     * @generated
     */
    public void setPaymentText(String newPaymentText) {
        paymentText = newPaymentText;
    }

    /**
     * Returns the value of '<em><b>paymentDescription</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>paymentDescription</b></em>' feature
     * @generated
     */
    public String getPaymentDescription() {

        return paymentDescription;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getPaymentDescription()
     * <em>paymentDescription</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPaymentDescription
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getPaymentDescription()
     *            paymentDescription}' feature.
     * @generated
     */
    public void setPaymentDescription(String newPaymentDescription) {
        paymentDescription = newPaymentDescription;
    }

    /**
     * Returns the value of '<em><b>shippingName</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>shippingName</b></em>' feature
     * @generated
     */
    public String getShippingName() {

        return shippingName;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getShippingName()
     * <em>shippingName</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newShippingName
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getShippingName()
     *            shippingName}' feature.
     * @generated
     */
    public void setShippingName(String newShippingName) {
        shippingName = newShippingName;
    }

    /**
     * Returns the value of '<em><b>shippingDescription</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>shippingDescription</b></em>' feature
     * @generated
     */
    public String getShippingDescription() {

        return shippingDescription;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getShippingDescription()
     * <em>shippingDescription</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newShippingDescription
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getShippingDescription()
     *            shippingDescription}' feature.
     * @generated
     */
    public void setShippingDescription(String newShippingDescription) {
        shippingDescription = newShippingDescription;
    }

    /**
     * Returns the value of '<em><b>shippingVatDescription</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>shippingVatDescription</b></em>' feature
     * @generated
     */
    public String getShippingVatDescription() {

        return shippingVatDescription;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getShippingVatDescription()
     * <em>shippingVatDescription</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newShippingVatDescription
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getShippingVatDescription()
     *            shippingVatDescription}' feature.
     * @generated
     */
    public void setShippingVatDescription(String newShippingVatDescription) {
        shippingVatDescription = newShippingVatDescription;
    }

    /**
     * Returns the value of '<em><b>shippingVatValue</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>shippingVatValue</b></em>' feature
     * @generated
     */
    public Double getShippingVatValue() {

        return shippingVatValue;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getShippingVatValue()
     * <em>shippingVatValue</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newShippingVatValue
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getShippingVatValue()
     *            shippingVatValue}' feature.
     * @generated
     */
    public void setShippingVatValue(Double newShippingVatValue) {
        shippingVatValue = newShippingVatValue;
    }

    /**
     * Returns the value of '<em><b>noVatName</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>noVatName</b></em>' feature
     * @generated
     */
    public String getNoVatName() {

        return noVatName;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getNoVatName()
     * <em>noVatName</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newNoVatName
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getNoVatName() noVatName}'
     *            feature.
     * @generated
     */
    public void setNoVatName(String newNoVatName) {
        noVatName = newNoVatName;
    }

    /**
     * Returns the value of '<em><b>noVatDescription</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>noVatDescription</b></em>' feature
     * @generated
     */
    public String getNoVatDescription() {

        return noVatDescription;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getNoVatDescription()
     * <em>noVatDescription</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newNoVatDescription
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getNoVatDescription()
     *            noVatDescription}' feature.
     * @generated
     */
    public void setNoVatDescription(String newNoVatDescription) {
        noVatDescription = newNoVatDescription;
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
    public String getName() {

        return name;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getName() <em>name</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link IndividualDocumentInfo#getName()
     *            name}' feature.
     * @generated
     */
    public void setName(String newName) {
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
    public Date getDateAdded() {

        return dateAdded;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getDateAdded()
     * <em>dateAdded</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getDateAdded() dateAdded}'
     *            feature.
     * @generated
     */
    public void setDateAdded(Date newDateAdded) {
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
    public String getModifiedBy() {

        return modifiedBy;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getModifiedBy()
     * <em>modifiedBy</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getModifiedBy() modifiedBy}'
     *            feature.
     * @generated
     */
    public void setModifiedBy(String newModifiedBy) {
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
    public Date getModified() {

        return modified;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getModified() <em>modified</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getModified() modified}'
     *            feature.
     * @generated
     */
    public void setModified(Date newModified) {
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
    public long getId() {

        return id;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link IndividualDocumentInfo#getId()
     *            id}' feature.
     * @generated
     */
    public void setId(long newId) {
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
    public Boolean getDeleted() {

        return deleted;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getDeleted() <em>deleted</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getDeleted() deleted}' feature.
     * @generated
     */
    public void setDeleted(Boolean newDeleted) {
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
    public Date getValidFrom() {

        return validFrom;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getValidFrom()
     * <em>validFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getValidFrom() validFrom}'
     *            feature.
     * @generated
     */
    public void setValidFrom(Date newValidFrom) {
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
    public Date getValidTo() {

        return validTo;
    }

    /**
     * Sets the '{@link IndividualDocumentInfo#getValidTo() <em>validTo</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the
     *            '{@link IndividualDocumentInfo#getValidTo() validTo}' feature.
     * @generated
     */
    public void setValidTo(Date newValidTo) {
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
        return "IndividualDocumentInfo" + " paymentName: [" + getPaymentName() + "]" + " paymentText: [" + getPaymentText() + "]" + " paymentDescription: ["
                + getPaymentDescription() + "]" + " shippingName: [" + getShippingName() + "]" + " shippingDescription: [" + getShippingDescription() + "]"
                + " shippingVatDescription: [" + getShippingVatDescription() + "]" + " shippingVatValue: [" + getShippingVatValue() + "]" + " noVatName: ["
                + getNoVatName() + "]" + " noVatDescription: [" + getNoVatDescription() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded()
                + "]" + " modifiedBy: [" + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: ["
                + getDeleted() + "]" + " validFrom: [" + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
