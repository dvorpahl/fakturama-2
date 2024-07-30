package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.lang.Boolean;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * A representation of the model object '<em><b>Shipping</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_SHIPPING")
@EntityListeners(value = { EntityListener.class })
public class Shipping extends ModelObject implements Serializable, IDescribableEntity, ICategorizable<ShippingCategory>, Cloneable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> If
     * shippingAutoVat is not fix, the shipping vat is an average value of the
     * vats of the items. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "AUTOVAT")
    @Enumerated(EnumType.STRING)
    private ShippingVatType autoVat = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SHIPPINGVALUE")
    private Double shippingValue = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
    @JoinColumns({ @JoinColumn(name = "FK_VAT") })
    private VAT shippingVat = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_CATEGORY") })
    private ShippingCategory categories = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The code according to INCOTERMS 2010 (excerpt). This field is necessary
     * for creating ZUGFeRD invoices. <!-- end-model-doc -->
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
    public boolean isSameAs(Shipping other) {
        boolean retval = true;
        if (other != null) {
            if (autoVat != null) {
                retval &= autoVat.equals(other.getAutoVat());
            }
            if (shippingValue != null && other.getShippingValue() != null) {
                retval &= shippingValue.compareTo(other.getShippingValue()) == 0;
            }
            if (getShippingVat() != null) {
                retval &= getShippingVat().isSameAs(other.getShippingVat());
            }
            if (getCategories() != null) {
                retval &= getCategories().isSameAs(other.getCategories());
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
    public Shipping clone() {
        Shipping retval = new Shipping();
        retval.setAutoVat(this.getAutoVat());
        retval.setShippingValue(this.getShippingValue());
        retval.setShippingVat(this.getShippingVat());
        retval.setCategories(this.getCategories());
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
     * Returns the value of '<em><b>autoVat</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> If
     * shippingAutoVat is not fix, the shipping vat is an average value of the
     * vats of the items. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>autoVat</b></em>' feature
     * @generated
     */
    public ShippingVatType getAutoVat() {

        return autoVat;
    }

    /**
     * Sets the '{@link Shipping#getAutoVat() <em>autoVat</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> If
     * shippingAutoVat is not fix, the shipping vat is an average value of the
     * vats of the items. <!-- end-model-doc -->
     * 
     * @param newAutoVat
     *            the new value of the '{@link Shipping#getAutoVat() autoVat}'
     *            feature.
     * @generated
     */
    public void setAutoVat(ShippingVatType newAutoVat) {
        firePropertyChange("autoVat", this.autoVat, newAutoVat);
        autoVat = newAutoVat;
    }

    /**
     * Returns the value of '<em><b>shippingValue</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>shippingValue</b></em>' feature
     * @generated
     */
    public Double getShippingValue() {

        return shippingValue;
    }

    /**
     * Sets the '{@link Shipping#getShippingValue() <em>shippingValue</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newShippingValue
     *            the new value of the '{@link Shipping#getShippingValue()
     *            shippingValue}' feature.
     * @generated
     */
    public void setShippingValue(Double newShippingValue) {
        firePropertyChange("shippingValue", this.shippingValue, newShippingValue);
        shippingValue = newShippingValue;
    }

    /**
     * Returns the value of '<em><b>shippingVat</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>shippingVat</b></em>' feature
     * @generated
     */
    public VAT getShippingVat() {

        return shippingVat;
    }

    /**
     * Sets the '{@link Shipping#getShippingVat() <em>shippingVat</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newShippingVat
     *            the new value of the '{@link Shipping#getShippingVat()
     *            shippingVat}' feature.
     * @generated
     */
    public void setShippingVat(VAT newShippingVat) {
        firePropertyChange("shippingVat", this.shippingVat, newShippingVat);
        shippingVat = newShippingVat;
    }

    /**
     * Returns the value of '<em><b>categories</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>categories</b></em>' feature
     * @generated
     */
    public ShippingCategory getCategories() {

        return categories;
    }

    /**
     * Sets the '{@link Shipping#getCategories() <em>categories</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCategories
     *            the new value of the '{@link Shipping#getCategories()
     *            categories}' feature.
     * @generated
     */
    public void setCategories(ShippingCategory newCategories) {
        firePropertyChange("categories", this.categories, newCategories);
        categories = newCategories;
    }

    /**
     * Returns the value of '<em><b>code</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The code according to INCOTERMS 2010 (excerpt). This field is necessary
     * for creating ZUGFeRD invoices. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>code</b></em>' feature
     * @generated
     */
    public String getCode() {

        return code;
    }

    /**
     * Sets the '{@link Shipping#getCode() <em>code</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The code according to INCOTERMS 2010 (excerpt). This field is necessary
     * for creating ZUGFeRD invoices. <!-- end-model-doc -->
     * 
     * @param newCode
     *            the new value of the '{@link Shipping#getCode() code}'
     *            feature.
     * @generated
     */
    public void setCode(String newCode) {
        firePropertyChange("code", this.code, newCode);
        code = newCode;
    }

    /**
     * Returns the value of '<em><b>description</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>description</b></em>' feature
     * @generated
     */
    public String getDescription() {

        return description;
    }

    /**
     * Sets the '{@link Shipping#getDescription() <em>description</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDescription
     *            the new value of the '{@link Shipping#getDescription()
     *            description}' feature.
     * @generated
     */
    public void setDescription(String newDescription) {
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
    public String getName() {

        return name;
    }

    /**
     * Sets the '{@link Shipping#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link Shipping#getName() name}'
     *            feature.
     * @generated
     */
    public void setName(String newName) {
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
    public Date getDateAdded() {

        return dateAdded;
    }

    /**
     * Sets the '{@link Shipping#getDateAdded() <em>dateAdded</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link Shipping#getDateAdded()
     *            dateAdded}' feature.
     * @generated
     */
    public void setDateAdded(Date newDateAdded) {
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
    public String getModifiedBy() {

        return modifiedBy;
    }

    /**
     * Sets the '{@link Shipping#getModifiedBy() <em>modifiedBy</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link Shipping#getModifiedBy()
     *            modifiedBy}' feature.
     * @generated
     */
    public void setModifiedBy(String newModifiedBy) {
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
    public Date getModified() {

        return modified;
    }

    /**
     * Sets the '{@link Shipping#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link Shipping#getModified() modified}'
     *            feature.
     * @generated
     */
    public void setModified(Date newModified) {
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
    public long getId() {

        return id;
    }

    /**
     * Sets the '{@link Shipping#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link Shipping#getId() id}' feature.
     * @generated
     */
    public void setId(long newId) {
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
    public Boolean getDeleted() {

        return deleted;
    }

    /**
     * Sets the '{@link Shipping#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link Shipping#getDeleted() deleted}'
     *            feature.
     * @generated
     */
    public void setDeleted(Boolean newDeleted) {
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
    public Date getValidFrom() {

        return validFrom;
    }

    /**
     * Sets the '{@link Shipping#getValidFrom() <em>validFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link Shipping#getValidFrom()
     *            validFrom}' feature.
     * @generated
     */
    public void setValidFrom(Date newValidFrom) {
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
    public Date getValidTo() {

        return validTo;
    }

    /**
     * Sets the '{@link Shipping#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link Shipping#getValidTo() validTo}'
     *            feature.
     * @generated
     */
    public void setValidTo(Date newValidTo) {
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
        return "Shipping" + " autoVat: [" + getAutoVat() + "]" + " shippingValue: [" + getShippingValue() + "]" + " code: [" + getCode() + "]"
                + " description: [" + getDescription() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded() + "]" + " modifiedBy: ["
                + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: [" + getDeleted() + "]" + " validFrom: ["
                + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
