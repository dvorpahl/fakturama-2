package com.sebulli.fakturama.model;

import java.util.Date;

/**
 * A representation of the model object '<em><b>IDescribableEntity</b></em>'.
 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This
 * interface marks a class as describable. I.e., it has a description field.
 * This is used e.g. for combo boxes. <!-- end-model-doc -->
 * 
 * @generated
 */
public interface IDescribableEntity extends IEntity {
    /**
     * Returns the value of '<em><b>description</em></b>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>description</b></em>' feature
     * @generated
     */
    public String getDescription();

    /**
     * Sets the '{@link IDescribableEntity#getDescription()
     * <em>description</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDescription
     *            the new value of the
     *            '{@link IDescribableEntity#getDescription()
     *            <em>description</em>}' feature.
     * @generated
     */
    public void setDescription(String newDescription);

    /**
     * Returns the value of '<em><b>name</em></b>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>name</b></em>' feature
     * @generated
     */
    public String getName();

    /**
     * Sets the '{@link IDescribableEntity#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newName
     *            the new value of the '{@link IDescribableEntity#getName()
     *            <em>name</em>}' feature.
     * @generated
     */
    public void setName(String newName);

    /**
     * Returns the value of '<em><b>dateAdded</em></b>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>dateAdded</b></em>' feature
     * @generated
     */
    public Date getDateAdded();

    /**
     * Sets the '{@link IDescribableEntity#getDateAdded() <em>dateAdded</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link IDescribableEntity#getDateAdded()
     *            <em>dateAdded</em>}' feature.
     * @generated
     */
    public void setDateAdded(Date newDateAdded);

    /**
     * Returns the value of '<em><b>modifiedBy</em></b>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>modifiedBy</b></em>' feature
     * @generated
     */
    public String getModifiedBy();

    /**
     * Sets the '{@link IDescribableEntity#getModifiedBy() <em>modifiedBy</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the
     *            '{@link IDescribableEntity#getModifiedBy()
     *            <em>modifiedBy</em>}' feature.
     * @generated
     */
    public void setModifiedBy(String newModifiedBy);

    /**
     * Returns the value of '<em><b>modified</em></b>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>modified</b></em>' feature
     * @generated
     */
    public Date getModified();

    /**
     * Sets the '{@link IDescribableEntity#getModified() <em>modified</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link IDescribableEntity#getModified()
     *            <em>modified</em>}' feature.
     * @generated
     */
    public void setModified(Date newModified);

    /**
     * Returns the value of '<em><b>id</em></b>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>id</b></em>' feature
     * @generated
     */
    public long getId();

    /**
     * Sets the '{@link IDescribableEntity#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link IDescribableEntity#getId()
     *            <em>id</em>}' feature.
     * @generated
     */
    public void setId(long newId);

    /**
     * Returns the value of '<em><b>deleted</em></b>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>deleted</b></em>' feature
     * @generated
     */
    public Boolean getDeleted();

    /**
     * Sets the '{@link IDescribableEntity#getDeleted() <em>deleted</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link IDescribableEntity#getDeleted()
     *            <em>deleted</em>}' feature.
     * @generated
     */
    public void setDeleted(Boolean newDeleted);

    /**
     * Returns the value of '<em><b>validFrom</em></b>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>validFrom</b></em>' feature
     * @generated
     */
    public Date getValidFrom();

    /**
     * Sets the '{@link IDescribableEntity#getValidFrom() <em>validFrom</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link IDescribableEntity#getValidFrom()
     *            <em>validFrom</em>}' feature.
     * @generated
     */
    public void setValidFrom(Date newValidFrom);

    /**
     * Returns the value of '<em><b>validTo</em></b>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>validTo</b></em>' feature
     * @generated
     */
    public Date getValidTo();

    /**
     * Sets the '{@link IDescribableEntity#getValidTo() <em>validTo</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link IDescribableEntity#getValidTo()
     *            <em>validTo</em>}' feature.
     * @generated
     */
    public void setValidTo(Date newValidTo);

}
