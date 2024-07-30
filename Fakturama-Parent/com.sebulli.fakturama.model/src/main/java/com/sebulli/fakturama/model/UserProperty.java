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
 * A representation of the model object '<em><b>UserProperty</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Contains
 * the user settings for this application. <!-- end-model-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_USERPROPERTY")
@EntityListeners(value = { EntityListener.class })
public class UserProperty extends ModelObject implements Serializable, IEntity, Cloneable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The current value of this property. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "T_VALUE")
    private String value = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * User name for which this property belongs to. Later on we should make a
     * reference to the User entity, but for the moment a simple String value is
     * sufficient. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "T_USER")
    private String user = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The default value of this property. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "T_DEFAULT")
    private String default_ = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Set this to TRUE if this setting is a global one. Global settings can't
     * be overwritten by users. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "T_GLOBAL")
    private Boolean global = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicator for which type of setting this property stands (e.g. Product,
     * Contact etc) <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "QUALIFIER")
    private String qualifier = null;

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
    public boolean isSameAs(UserProperty other) {
        boolean retval = true;
        if (other != null) {
            if (value != null && other.getValue() != null) {
                retval &= value.compareTo(other.getValue()) == 0;
            }
            if (user != null && other.getUser() != null) {
                retval &= user.compareTo(other.getUser()) == 0;
            }
            if (default_ != null && other.getDefault_() != null) {
                retval &= default_.compareTo(other.getDefault_()) == 0;
            }
            if (global != null && other.getGlobal() != null) {
                retval &= global.compareTo(other.getGlobal()) == 0;
            }
            if (qualifier != null && other.getQualifier() != null) {
                retval &= qualifier.compareTo(other.getQualifier()) == 0;
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
    public UserProperty clone() {
        UserProperty retval = new UserProperty();
        retval.setValue(this.getValue());
        retval.setUser(this.getUser());
        retval.setDefault_(this.getDefault_());
        retval.setGlobal(this.getGlobal());
        retval.setQualifier(this.getQualifier());
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
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The current value of this property. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>value</b></em>' feature
     * @generated
     */
    public String getValue() {

        return value;
    }

    /**
     * Sets the '{@link UserProperty#getValue() <em>value</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The current value of this property. <!-- end-model-doc -->
     * 
     * @param newValue
     *            the new value of the '{@link UserProperty#getValue() value}'
     *            feature.
     * @generated
     */
    public void setValue(String newValue) {
        firePropertyChange("value", this.value, newValue);
        value = newValue;
    }

    /**
     * Returns the value of '<em><b>user</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * User name for which this property belongs to. Later on we should make a
     * reference to the User entity, but for the moment a simple String value is
     * sufficient. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>user</b></em>' feature
     * @generated
     */
    public String getUser() {

        return user;
    }

    /**
     * Sets the '{@link UserProperty#getUser() <em>user</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * User name for which this property belongs to. Later on we should make a
     * reference to the User entity, but for the moment a simple String value is
     * sufficient. <!-- end-model-doc -->
     * 
     * @param newUser
     *            the new value of the '{@link UserProperty#getUser() user}'
     *            feature.
     * @generated
     */
    public void setUser(String newUser) {
        firePropertyChange("user", this.user, newUser);
        user = newUser;
    }

    /**
     * Returns the value of '<em><b>default</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The default value of this property. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>default</b></em>' feature
     * @generated
     */
    public String getDefault_() {

        return default_;
    }

    /**
     * Sets the '{@link UserProperty#getDefault_() <em>default</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The default value of this property. <!-- end-model-doc -->
     * 
     * @param newDefault_
     *            the new value of the '{@link UserProperty#getDefault_()
     *            default}' feature.
     * @generated
     */
    public void setDefault_(String newDefault_) {
        firePropertyChange("default_", this.default_, newDefault_);
        default_ = newDefault_;
    }

    /**
     * Returns the value of '<em><b>global</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Set this to TRUE if this setting is a global one. Global settings can't
     * be overwritten by users. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>global</b></em>' feature
     * @generated
     */
    public Boolean getGlobal() {

        return global;
    }

    /**
     * Sets the '{@link UserProperty#getGlobal() <em>global</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Set this to TRUE if this setting is a global one. Global settings can't
     * be overwritten by users. <!-- end-model-doc -->
     * 
     * @param newGlobal
     *            the new value of the '{@link UserProperty#getGlobal() global}'
     *            feature.
     * @generated
     */
    public void setGlobal(Boolean newGlobal) {
        firePropertyChange("global", this.global, newGlobal);
        global = newGlobal;
    }

    /**
     * Returns the value of '<em><b>qualifier</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicator for which type of setting this property stands (e.g. Product,
     * Contact etc) <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>qualifier</b></em>' feature
     * @generated
     */
    public String getQualifier() {

        return qualifier;
    }

    /**
     * Sets the '{@link UserProperty#getQualifier() <em>qualifier</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicator for which type of setting this property stands (e.g. Product,
     * Contact etc) <!-- end-model-doc -->
     * 
     * @param newQualifier
     *            the new value of the '{@link UserProperty#getQualifier()
     *            qualifier}' feature.
     * @generated
     */
    public void setQualifier(String newQualifier) {
        firePropertyChange("qualifier", this.qualifier, newQualifier);
        qualifier = newQualifier;
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
     * Sets the '{@link UserProperty#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link UserProperty#getName() name}'
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
     * Sets the '{@link UserProperty#getDateAdded() <em>dateAdded</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link UserProperty#getDateAdded()
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
     * Sets the '{@link UserProperty#getModifiedBy() <em>modifiedBy</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link UserProperty#getModifiedBy()
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
     * Sets the '{@link UserProperty#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link UserProperty#getModified()
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
     * Sets the '{@link UserProperty#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link UserProperty#getId() id}'
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
     * Sets the '{@link UserProperty#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link UserProperty#getDeleted()
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
     * Sets the '{@link UserProperty#getValidFrom() <em>validFrom</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link UserProperty#getValidFrom()
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
     * Sets the '{@link UserProperty#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link UserProperty#getValidTo()
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
        return "UserProperty" + " value: [" + getValue() + "]" + " user: [" + getUser() + "]" + " default_: [" + getDefault_() + "]" + " global: ["
                + getGlobal() + "]" + " qualifier: [" + getQualifier() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded() + "]"
                + " modifiedBy: [" + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: [" + getDeleted()
                + "]" + " validFrom: [" + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
