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
 * A representation of the model object '<em><b>WebshopStateMapping</b></em>'.
 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
 * Mapping between order states of a web shop to the order states of the
 * Fakturama application. Since we plan to assign multiple web shops we store a
 * mapping with its webshop ID. This is for later use, at the moment we don't
 * use it. <!-- end-model-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_WEBSHOPSTATEMAPPING")
@EntityListeners(value = { EntityListener.class })
public class WebshopStateMapping implements IEntity, Serializable, Cloneable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the state from web shop <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "WEBSHOPSTATE")
    private String webshopState = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * OrderState from Fakturama, to which the web shop state is mapped. <!--
     * end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "FAKTURAMAORDERSTATE")
    private String fakturamaOrderState = null;

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
    public boolean isSameAs(WebshopStateMapping other) {
        boolean retval = true;
        if (other != null) {
            if (webshopState != null && other.getWebshopState() != null) {
                retval &= webshopState.compareTo(other.getWebshopState()) == 0;
            }
            if (fakturamaOrderState != null && other.getFakturamaOrderState() != null) {
                retval &= fakturamaOrderState.compareTo(other.getFakturamaOrderState()) == 0;
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
    public WebshopStateMapping clone() {
        WebshopStateMapping retval = new WebshopStateMapping();
        retval.setWebshopState(this.getWebshopState());
        retval.setFakturamaOrderState(this.getFakturamaOrderState());
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
     * Returns the value of '<em><b>webshopState</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the state from web shop <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>webshopState</b></em>' feature
     * @generated
     */
    public String getWebshopState() {

        return webshopState;
    }

    /**
     * Sets the '{@link WebshopStateMapping#getWebshopState()
     * <em>webshopState</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the state from web shop <!-- end-model-doc -->
     * 
     * @param newWebshopState
     *            the new value of the
     *            '{@link WebshopStateMapping#getWebshopState() webshopState}'
     *            feature.
     * @generated
     */
    public void setWebshopState(String newWebshopState) {
        webshopState = newWebshopState;
    }

    /**
     * Returns the value of '<em><b>fakturamaOrderState</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * OrderState from Fakturama, to which the web shop state is mapped. <!--
     * end-model-doc -->
     * 
     * @return the value of '<em><b>fakturamaOrderState</b></em>' feature
     * @generated
     */
    public String getFakturamaOrderState() {

        return fakturamaOrderState;
    }

    /**
     * Sets the '{@link WebshopStateMapping#getFakturamaOrderState()
     * <em>fakturamaOrderState</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * OrderState from Fakturama, to which the web shop state is mapped. <!--
     * end-model-doc -->
     * 
     * @param newFakturamaOrderState
     *            the new value of the
     *            '{@link WebshopStateMapping#getFakturamaOrderState()
     *            fakturamaOrderState}' feature.
     * @generated
     */
    public void setFakturamaOrderState(String newFakturamaOrderState) {
        fakturamaOrderState = newFakturamaOrderState;
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
     * Sets the '{@link WebshopStateMapping#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link WebshopStateMapping#getName()
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
     * Sets the '{@link WebshopStateMapping#getDateAdded() <em>dateAdded</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the
     *            '{@link WebshopStateMapping#getDateAdded() dateAdded}'
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
     * Sets the '{@link WebshopStateMapping#getModifiedBy()
     * <em>modifiedBy</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the
     *            '{@link WebshopStateMapping#getModifiedBy() modifiedBy}'
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
     * Sets the '{@link WebshopStateMapping#getModified() <em>modified</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link WebshopStateMapping#getModified()
     *            modified}' feature.
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
     * Sets the '{@link WebshopStateMapping#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link WebshopStateMapping#getId() id}'
     *            feature.
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
     * Sets the '{@link WebshopStateMapping#getDeleted() <em>deleted</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link WebshopStateMapping#getDeleted()
     *            deleted}' feature.
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
     * Sets the '{@link WebshopStateMapping#getValidFrom() <em>validFrom</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the
     *            '{@link WebshopStateMapping#getValidFrom() validFrom}'
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
     * Sets the '{@link WebshopStateMapping#getValidTo() <em>validTo</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link WebshopStateMapping#getValidTo()
     *            validTo}' feature.
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
        return "WebshopStateMapping" + " webshopState: [" + getWebshopState() + "]" + " fakturamaOrderState: [" + getFakturamaOrderState() + "]" + " name: ["
                + getName() + "]" + " dateAdded: [" + getDateAdded() + "]" + " modifiedBy: [" + getModifiedBy() + "]" + " modified: [" + getModified() + "]"
                + " id: [" + getId() + "]" + " deleted: [" + getDeleted() + "]" + " validFrom: [" + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
