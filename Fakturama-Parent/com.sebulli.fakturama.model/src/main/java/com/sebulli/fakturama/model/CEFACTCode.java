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
 * A representation of the model object '<em><b>CEFACTCode</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This Entity
 * contains the UN/CEFACT values for measurement codes and others. Each entry
 * consists of a target type (i.e., Shipping, VAT or so) and the according code
 * information. <!-- end-model-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_CEFACTCODE")
@EntityListeners(value = { EntityListener.class })
public class CEFACTCode implements IEntity, Serializable, Cloneable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The abbreviation of this code in a readable manner (can be overwritten by
     * user). <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ABBREVIATION_DE")
    private String abbreviation_de = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ABBREVIATION_EN")
    private String abbreviation_en = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The UN/CEFACT code. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "CODE")
    private String code = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The target of this code. This can be "Shipping", "VAT" or others. It is
     * not modeled as reference to another "target table" because these values
     * are invariant and only like an ID for a category. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "TARGET")
    private String target = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "NAME_DE")
    private String name_de = null;

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
    public boolean isSameAs(CEFACTCode other) {
        boolean retval = true;
        if (other != null) {
            if (abbreviation_de != null && other.getAbbreviation_de() != null) {
                retval &= abbreviation_de.compareTo(other.getAbbreviation_de()) == 0;
            }
            if (abbreviation_en != null && other.getAbbreviation_en() != null) {
                retval &= abbreviation_en.compareTo(other.getAbbreviation_en()) == 0;
            }
            if (code != null && other.getCode() != null) {
                retval &= code.compareTo(other.getCode()) == 0;
            }
            if (target != null && other.getTarget() != null) {
                retval &= target.compareTo(other.getTarget()) == 0;
            }
            if (name_de != null && other.getName_de() != null) {
                retval &= name_de.compareTo(other.getName_de()) == 0;
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
    public CEFACTCode clone() {
        CEFACTCode retval = new CEFACTCode();
        retval.setAbbreviation_de(this.getAbbreviation_de());
        retval.setAbbreviation_en(this.getAbbreviation_en());
        retval.setCode(this.getCode());
        retval.setTarget(this.getTarget());
        retval.setName_de(this.getName_de());
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
     * Returns the value of '<em><b>abbreviation_de</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The abbreviation of this code in a readable manner (can be overwritten by
     * user). <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>abbreviation_de</b></em>' feature
     * @generated
     */
    public String getAbbreviation_de() {

        return abbreviation_de;
    }

    /**
     * Sets the '{@link CEFACTCode#getAbbreviation_de()
     * <em>abbreviation_de</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The abbreviation of this code in a readable manner (can be overwritten by
     * user). <!-- end-model-doc -->
     * 
     * @param newAbbreviation_de
     *            the new value of the '{@link CEFACTCode#getAbbreviation_de()
     *            abbreviation_de}' feature.
     * @generated
     */
    public void setAbbreviation_de(String newAbbreviation_de) {
        abbreviation_de = newAbbreviation_de;
    }

    /**
     * Returns the value of '<em><b>abbreviation_en</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>abbreviation_en</b></em>' feature
     * @generated
     */
    public String getAbbreviation_en() {

        return abbreviation_en;
    }

    /**
     * Sets the '{@link CEFACTCode#getAbbreviation_en()
     * <em>abbreviation_en</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newAbbreviation_en
     *            the new value of the '{@link CEFACTCode#getAbbreviation_en()
     *            abbreviation_en}' feature.
     * @generated
     */
    public void setAbbreviation_en(String newAbbreviation_en) {
        abbreviation_en = newAbbreviation_en;
    }

    /**
     * Returns the value of '<em><b>code</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The UN/CEFACT code. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>code</b></em>' feature
     * @generated
     */
    public String getCode() {

        return code;
    }

    /**
     * Sets the '{@link CEFACTCode#getCode() <em>code</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The UN/CEFACT code. <!-- end-model-doc -->
     * 
     * @param newCode
     *            the new value of the '{@link CEFACTCode#getCode() code}'
     *            feature.
     * @generated
     */
    public void setCode(String newCode) {
        code = newCode;
    }

    /**
     * Returns the value of '<em><b>target</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The target of this code. This can be "Shipping", "VAT" or others. It is
     * not modeled as reference to another "target table" because these values
     * are invariant and only like an ID for a category. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>target</b></em>' feature
     * @generated
     */
    public String getTarget() {

        return target;
    }

    /**
     * Sets the '{@link CEFACTCode#getTarget() <em>target</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The target of this code. This can be "Shipping", "VAT" or others. It is
     * not modeled as reference to another "target table" because these values
     * are invariant and only like an ID for a category. <!-- end-model-doc -->
     * 
     * @param newTarget
     *            the new value of the '{@link CEFACTCode#getTarget() target}'
     *            feature.
     * @generated
     */
    public void setTarget(String newTarget) {
        target = newTarget;
    }

    /**
     * Returns the value of '<em><b>name_de</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>name_de</b></em>' feature
     * @generated
     */
    public String getName_de() {

        return name_de;
    }

    /**
     * Sets the '{@link CEFACTCode#getName_de() <em>name_de</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newName_de
     *            the new value of the '{@link CEFACTCode#getName_de() name_de}'
     *            feature.
     * @generated
     */
    public void setName_de(String newName_de) {
        name_de = newName_de;
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
     * Sets the '{@link CEFACTCode#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link CEFACTCode#getName() name}'
     *            feature.
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
     * Sets the '{@link CEFACTCode#getDateAdded() <em>dateAdded</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link CEFACTCode#getDateAdded()
     *            dateAdded}' feature.
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
     * Sets the '{@link CEFACTCode#getModifiedBy() <em>modifiedBy</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link CEFACTCode#getModifiedBy()
     *            modifiedBy}' feature.
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
     * Sets the '{@link CEFACTCode#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link CEFACTCode#getModified()
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
     * Sets the '{@link CEFACTCode#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link CEFACTCode#getId() id}' feature.
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
     * Sets the '{@link CEFACTCode#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link CEFACTCode#getDeleted() deleted}'
     *            feature.
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
     * Sets the '{@link CEFACTCode#getValidFrom() <em>validFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link CEFACTCode#getValidFrom()
     *            validFrom}' feature.
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
     * Sets the '{@link CEFACTCode#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link CEFACTCode#getValidTo() validTo}'
     *            feature.
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
        return "CEFACTCode" + " abbreviation_de: [" + getAbbreviation_de() + "]" + " abbreviation_en: [" + getAbbreviation_en() + "]" + " code: [" + getCode()
                + "]" + " target: [" + getTarget() + "]" + " name_de: [" + getName_de() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded()
                + "]" + " modifiedBy: [" + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: ["
                + getDeleted() + "]" + " validFrom: [" + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
