package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.lang.Boolean;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * A representation of the model object '<em><b>ItemAccountType</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The account
 * for a single item (e.g., from receipt or expenditure voucher). More specific
 * it could also be a list entry for an arbitrary list (e.g., measure units).
 * <!-- end-model-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_ITEMACCOUNTTYPE")
@EntityListeners(value = { EntityListener.class })
public class ItemAccountType extends ModelObject implements IEntity, Serializable, Cloneable {
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
    @Column(name = "T_VALUE")
    private String value = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_CATEGORY") })
    private ItemListTypeCategory category = null;

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
    public boolean isSameAs(ItemAccountType other) {
        boolean retval = true;
        if (other != null) {
            if (value != null && other.getValue() != null) {
                retval &= value.compareTo(other.getValue()) == 0;
            }
            if (getCategory() != null) {
                retval &= getCategory().isSameAs(other.getCategory());
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
    public ItemAccountType clone() {
        ItemAccountType retval = new ItemAccountType();
        retval.setValue(this.getValue());
        retval.setCategory(this.getCategory());
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
     * Returns the value of '<em><b>value</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>value</b></em>' feature
     * @generated
     */
    public String getValue() {

        return value;
    }

    /**
     * Sets the '{@link ItemAccountType#getValue() <em>value</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newValue
     *            the new value of the '{@link ItemAccountType#getValue()
     *            value}' feature.
     * @generated
     */
    public void setValue(String newValue) {
        firePropertyChange("value", this.value, newValue);
        value = newValue;
    }

    /**
     * Returns the value of '<em><b>category</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>category</b></em>' feature
     * @generated
     */
    public ItemListTypeCategory getCategory() {

        return category;
    }

    /**
     * Sets the '{@link ItemAccountType#getCategory() <em>category</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCategory
     *            the new value of the '{@link ItemAccountType#getCategory()
     *            category}' feature.
     * @generated
     */
    public void setCategory(ItemListTypeCategory newCategory) {
        firePropertyChange("category", this.category, newCategory);
        category = newCategory;
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
     * Sets the '{@link ItemAccountType#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link ItemAccountType#getName() name}'
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
     * Sets the '{@link ItemAccountType#getDateAdded() <em>dateAdded</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link ItemAccountType#getDateAdded()
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
     * Sets the '{@link ItemAccountType#getModifiedBy() <em>modifiedBy</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link ItemAccountType#getModifiedBy()
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
     * Sets the '{@link ItemAccountType#getModified() <em>modified</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link ItemAccountType#getModified()
     *            modified}' feature.
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
     * Sets the '{@link ItemAccountType#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link ItemAccountType#getId() id}'
     *            feature.
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
     * Sets the '{@link ItemAccountType#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link ItemAccountType#getDeleted()
     *            deleted}' feature.
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
     * Sets the '{@link ItemAccountType#getValidFrom() <em>validFrom</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link ItemAccountType#getValidFrom()
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
     * Sets the '{@link ItemAccountType#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link ItemAccountType#getValidTo()
     *            validTo}' feature.
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
        return "ItemAccountType" + " value: [" + getValue() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded() + "]" + " modifiedBy: ["
                + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: [" + getDeleted() + "]" + " validFrom: ["
                + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
