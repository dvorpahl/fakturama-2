package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.util.Date;

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
 * A representation of the model object '<em><b>VAT</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_VAT")
@EntityListeners(value = { EntityListener.class })
public class VAT extends ModelObject implements Serializable, IDescribableEntity, Cloneable {
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
    @Column(name = "TAXVALUE")
    private Double taxValue = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_CATEGORY") })
    private VATCategory category = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Sales equalization tax for this VAT which has to be paid by a contact
     * (debtor). <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SALESEQUALIZATIONTAX")
    private Double salesEqualizationTax = null;

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
    @Column(name = "ID")
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
    public boolean isSameAs(final VAT other) {
        boolean retval = true;
        if (other != null) {
            if (taxValue != null && other.getTaxValue() != null) {
                retval &= taxValue.compareTo(other.getTaxValue()) == 0;
            }
            if (getCategory() != null) {
                retval &= getCategory().isSameAs(other.getCategory());
            }
            if (salesEqualizationTax != null && other.getSalesEqualizationTax() != null) {
                retval &= salesEqualizationTax.compareTo(other.getSalesEqualizationTax()) == 0;
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
    public VAT clone() {
        VAT retval = new VAT();
        retval.setTaxValue(this.getTaxValue());
        retval.setCategory(this.getCategory());
        retval.setSalesEqualizationTax(this.getSalesEqualizationTax());
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
     * Returns the value of '<em><b>taxValue</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>taxValue</b></em>' feature
     * @generated
     */
    public Double getTaxValue() {

        return taxValue;
    }

    /**
     * Sets the '{@link VAT#getTaxValue() <em>taxValue</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newTaxValue
     *            the new value of the '{@link VAT#getTaxValue() taxValue}'
     *            feature.
     * @generated
     */
    public void setTaxValue(final Double newTaxValue) {
        firePropertyChange("taxValue", this.taxValue, newTaxValue);
        taxValue = newTaxValue;
    }

    /**
     * Returns the value of '<em><b>category</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>category</b></em>' feature
     * @generated
     */
    public VATCategory getCategory() {

        return category;
    }

    /**
     * Sets the '{@link VAT#getCategory() <em>category</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCategory
     *            the new value of the '{@link VAT#getCategory() category}'
     *            feature.
     * @generated
     */
    public void setCategory(final VATCategory newCategory) {
        firePropertyChange("category", this.category, newCategory);
        category = newCategory;
    }

    /**
     * Returns the value of '<em><b>salesEqualizationTax</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Sales equalization tax for this VAT which has to be paid by a contact
     * (debtor). <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>salesEqualizationTax</b></em>' feature
     * @generated
     */
    public Double getSalesEqualizationTax() {

        return salesEqualizationTax;
    }

    /**
     * Sets the '{@link VAT#getSalesEqualizationTax()
     * <em>salesEqualizationTax</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Sales equalization tax for this VAT which has to be paid by a contact
     * (debtor). <!-- end-model-doc -->
     * 
     * @param newSalesEqualizationTax
     *            the new value of the '{@link VAT#getSalesEqualizationTax()
     *            salesEqualizationTax}' feature.
     * @generated
     */
    public void setSalesEqualizationTax(final Double newSalesEqualizationTax) {
        firePropertyChange("salesEqualizationTax", this.salesEqualizationTax, newSalesEqualizationTax);
        salesEqualizationTax = newSalesEqualizationTax;
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
     * Sets the '{@link VAT#getDescription() <em>description</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDescription
     *            the new value of the '{@link VAT#getDescription()
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
     * Sets the '{@link VAT#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link VAT#getName() name}' feature.
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
     * Sets the '{@link VAT#getDateAdded() <em>dateAdded</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link VAT#getDateAdded() dateAdded}'
     *            feature.
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
     * Sets the '{@link VAT#getModifiedBy() <em>modifiedBy</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link VAT#getModifiedBy() modifiedBy}'
     *            feature.
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
     * Sets the '{@link VAT#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link VAT#getModified() modified}'
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
     * Sets the '{@link VAT#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link VAT#getId() id}' feature.
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
     * Sets the '{@link VAT#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link VAT#getDeleted() deleted}'
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
     * Sets the '{@link VAT#getValidFrom() <em>validFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link VAT#getValidFrom() validFrom}'
     *            feature.
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
     * Sets the '{@link VAT#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link VAT#getValidTo() validTo}'
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
        return "VAT" + " taxValue: [" + getTaxValue() + "]" + " salesEqualizationTax: [" + getSalesEqualizationTax() + "]" + " description: ["
                + getDescription() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded() + "]" + " modifiedBy: [" + getModifiedBy() + "]"
                + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: [" + getDeleted() + "]" + " validFrom: [" + getValidFrom() + "]"
                + " validTo: [" + getValidTo() + "]";
    }
}
