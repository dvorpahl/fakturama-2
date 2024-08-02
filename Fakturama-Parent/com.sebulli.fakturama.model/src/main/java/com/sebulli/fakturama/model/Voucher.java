package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.lang.Boolean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * A representation of the model object '<em><b>Voucher</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_VOUCHERS")
@EntityListeners(value = { EntityListener.class })
public class Voucher extends ModelObject implements IEntity, Serializable, Cloneable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The account type of this Expenditure. This was formerly known as
     * Category. <!-- end-model-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumns({ @JoinColumn(name = "FK_CATEGORY") })
    private VoucherCategory account = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VOUCHERDATE")
    @Temporal(TemporalType.DATE)
    private Date voucherDate = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DISCOUNTED")
    private Boolean discounted = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DONOTBOOK")
    private Boolean doNotBook = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The document number of this expenditure. This is *NOT* a reference to the
     * Document type! <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DOCUMENTNUMBER")
    private String documentNumber = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * category for an individual item on a voucher (expenditure or receipt).
     * This is not the same as the VoucherCategory. <!-- end-model-doc -->
     * 
     * @generated
     */
    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumns({ @JoinColumn(name = "FK_VOUCHER") })
    private List<VoucherItem> items = new ArrayList<VoucherItem>();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VOUCHERNUMBER")
    private String voucherNumber = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PAIDVALUE")
    private Double paidValue = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "TOTALVALUE")
    private Double totalValue = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The type of this voucher. This is for distinguishing between Expenditures
     * and Receiptvouchers. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VOUCHERTYPE")
    @Enumerated(EnumType.STRING)
    private VoucherType voucherType = null;

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
    public boolean isSameAs(Voucher other) {
        boolean retval = true;
        if (other != null) {
            if (getAccount() != null) {
                retval &= getAccount().isSameAs(other.getAccount());
            }
            if (voucherDate != null && other.getVoucherDate() != null) {
                retval &= voucherDate.compareTo(other.getVoucherDate()) == 0;
            }
            if (discounted != null && other.getDiscounted() != null) {
                retval &= discounted.compareTo(other.getDiscounted()) == 0;
            }
            if (doNotBook != null && other.getDoNotBook() != null) {
                retval &= doNotBook.compareTo(other.getDoNotBook()) == 0;
            }
            if (documentNumber != null && other.getDocumentNumber() != null) {
                retval &= documentNumber.compareTo(other.getDocumentNumber()) == 0;
            }
            /* reference to a Set (items) or a volatile member cannot be compared... */
            if (voucherNumber != null && other.getVoucherNumber() != null) {
                retval &= voucherNumber.compareTo(other.getVoucherNumber()) == 0;
            }
            if (paidValue != null && other.getPaidValue() != null) {
                retval &= paidValue.compareTo(other.getPaidValue()) == 0;
            }
            if (totalValue != null && other.getTotalValue() != null) {
                retval &= totalValue.compareTo(other.getTotalValue()) == 0;
            }
            if (voucherType != null) {
                retval &= voucherType.equals(other.getVoucherType());
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
    public Voucher clone() {
        Voucher retval = new Voucher();
        retval.setAccount(this.getAccount());
        retval.setVoucherDate(this.getVoucherDate());
        retval.setDiscounted(this.getDiscounted());
        retval.setDoNotBook(this.getDoNotBook());
        retval.setDocumentNumber(this.getDocumentNumber());
        /* reference to a Set (items) cannot be compared... */
        retval.setVoucherNumber(this.getVoucherNumber());
        retval.setPaidValue(this.getPaidValue());
        retval.setTotalValue(this.getTotalValue());
        retval.setVoucherType(this.getVoucherType());
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
     * Returns the value of '<em><b>account</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The account type of this Expenditure. This was formerly known as
     * Category. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>account</b></em>' feature
     * @generated
     */
    public VoucherCategory getAccount() {

        return account;
    }

    /**
     * Sets the '{@link Voucher#getAccount() <em>account</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The account type of this Expenditure. This was formerly known as
     * Category. <!-- end-model-doc -->
     * 
     * @param newAccount
     *            the new value of the '{@link Voucher#getAccount() account}'
     *            feature.
     * @generated
     */
    public void setAccount(VoucherCategory newAccount) {
        firePropertyChange("account", this.account, newAccount);
        account = newAccount;
    }

    /**
     * Returns the value of '<em><b>voucherDate</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>voucherDate</b></em>' feature
     * @generated
     */
    public Date getVoucherDate() {

        return voucherDate;
    }

    /**
     * Sets the '{@link Voucher#getVoucherDate() <em>voucherDate</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newVoucherDate
     *            the new value of the '{@link Voucher#getVoucherDate()
     *            voucherDate}' feature.
     * @generated
     */
    public void setVoucherDate(Date newVoucherDate) {
        firePropertyChange("voucherDate", this.voucherDate, newVoucherDate);
        voucherDate = newVoucherDate;
    }

    /**
     * Returns the value of '<em><b>discounted</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>discounted</b></em>' feature
     * @generated
     */
    public Boolean getDiscounted() {

        return discounted;
    }

    /**
     * Sets the '{@link Voucher#getDiscounted() <em>discounted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDiscounted
     *            the new value of the '{@link Voucher#getDiscounted()
     *            discounted}' feature.
     * @generated
     */
    public void setDiscounted(Boolean newDiscounted) {
        firePropertyChange("discounted", this.discounted, newDiscounted);
        discounted = newDiscounted;
    }

    /**
     * Returns the value of '<em><b>doNotBook</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>doNotBook</b></em>' feature
     * @generated
     */
    public Boolean getDoNotBook() {

        return doNotBook;
    }

    /**
     * Sets the '{@link Voucher#getDoNotBook() <em>doNotBook</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDoNotBook
     *            the new value of the '{@link Voucher#getDoNotBook()
     *            doNotBook}' feature.
     * @generated
     */
    public void setDoNotBook(Boolean newDoNotBook) {
        firePropertyChange("doNotBook", this.doNotBook, newDoNotBook);
        doNotBook = newDoNotBook;
    }

    /**
     * Returns the value of '<em><b>documentNumber</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The document number of this expenditure. This is *NOT* a reference to the
     * Document type! <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>documentNumber</b></em>' feature
     * @generated
     */
    public String getDocumentNumber() {

        return documentNumber;
    }

    /**
     * Sets the '{@link Voucher#getDocumentNumber() <em>documentNumber</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The document number of this expenditure. This is *NOT* a reference to the
     * Document type! <!-- end-model-doc -->
     * 
     * @param newDocumentNumber
     *            the new value of the '{@link Voucher#getDocumentNumber()
     *            documentNumber}' feature.
     * @generated
     */
    public void setDocumentNumber(String newDocumentNumber) {
        firePropertyChange("documentNumber", this.documentNumber, newDocumentNumber);
        documentNumber = newDocumentNumber;
    }

    /**
     * Returns the value of '<em><b>items</b></em>' feature. Note: the returned
     * collection is Unmodifiable use the
     * {#addToItems(com.sebulli.fakturama.model.VoucherItem value)} and
     * {@link #removeFromItems(VoucherItem value)} methods to modify this
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * category for an individual item on a voucher (expenditure or receipt).
     * This is not the same as the VoucherCategory. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>items</b></em>' feature
     * @generated
     */
    public List<VoucherItem> getItems() {

        return Collections.unmodifiableList(items);
    }

    /**
     * Adds to the <em>items</em> feature.
     *
     * @param itemsValue
     *            value to add
     *
     * @generated
     */
    public boolean addToItems(VoucherItem itemsValue) {
        if (!items.contains(itemsValue)) {
            items.add(itemsValue);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes from the <em>items</em> feature.
     *
     * @param itemsValue
     *            value to remove
     *
     * @generated
     */
    public boolean removeFromItems(VoucherItem itemsValue) {
        if (items.contains(itemsValue)) {
            items.remove(itemsValue);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Clears the <em>items</em> feature.
     * 
     * @generated
     */
    public void clearItems() {
        while (!items.isEmpty()) {
            removeFromItems(items.iterator().next());
        }
    }

    /**
     * Sets the '{@link Voucher#getItems() <em>items</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * category for an individual item on a voucher (expenditure or receipt).
     * This is not the same as the VoucherCategory. <!-- end-model-doc -->
     * 
     * @param newItems
     *            the new value of the '{@link Voucher#getItems() items}'
     *            feature.
     * @generated
     */
    public void setItems(List<VoucherItem> newItems) {
        firePropertyChange("items", this.items, newItems);
        clearItems();
        for (VoucherItem value : newItems) {
            addToItems(value);
        }
    }

    /**
     * Returns the value of '<em><b>voucherNumber</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>voucherNumber</b></em>' feature
     * @generated
     */
    public String getVoucherNumber() {

        return voucherNumber;
    }

    /**
     * Sets the '{@link Voucher#getVoucherNumber() <em>voucherNumber</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newVoucherNumber
     *            the new value of the '{@link Voucher#getVoucherNumber()
     *            voucherNumber}' feature.
     * @generated
     */
    public void setVoucherNumber(String newVoucherNumber) {
        firePropertyChange("voucherNumber", this.voucherNumber, newVoucherNumber);
        voucherNumber = newVoucherNumber;
    }

    /**
     * Returns the value of '<em><b>paidValue</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>paidValue</b></em>' feature
     * @generated
     */
    public Double getPaidValue() {

        return paidValue;
    }

    /**
     * Sets the '{@link Voucher#getPaidValue() <em>paidValue</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPaidValue
     *            the new value of the '{@link Voucher#getPaidValue()
     *            paidValue}' feature.
     * @generated
     */
    public void setPaidValue(Double newPaidValue) {
        firePropertyChange("paidValue", this.paidValue, newPaidValue);
        paidValue = newPaidValue;
    }

    /**
     * Returns the value of '<em><b>totalValue</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>totalValue</b></em>' feature
     * @generated
     */
    public Double getTotalValue() {

        return totalValue;
    }

    /**
     * Sets the '{@link Voucher#getTotalValue() <em>totalValue</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newTotalValue
     *            the new value of the '{@link Voucher#getTotalValue()
     *            totalValue}' feature.
     * @generated
     */
    public void setTotalValue(Double newTotalValue) {
        firePropertyChange("totalValue", this.totalValue, newTotalValue);
        totalValue = newTotalValue;
    }

    /**
     * Returns the value of '<em><b>voucherType</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The type of this voucher. This is for distinguishing between Expenditures
     * and Receiptvouchers. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>voucherType</b></em>' feature
     * @generated
     */
    public VoucherType getVoucherType() {

        return voucherType;
    }

    /**
     * Sets the '{@link Voucher#getVoucherType() <em>voucherType</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The type of this voucher. This is for distinguishing between Expenditures
     * and Receiptvouchers. <!-- end-model-doc -->
     * 
     * @param newVoucherType
     *            the new value of the '{@link Voucher#getVoucherType()
     *            voucherType}' feature.
     * @generated
     */
    public void setVoucherType(VoucherType newVoucherType) {
        firePropertyChange("voucherType", this.voucherType, newVoucherType);
        voucherType = newVoucherType;
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
     * Sets the '{@link Voucher#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link Voucher#getName() name}' feature.
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
     * Sets the '{@link Voucher#getDateAdded() <em>dateAdded</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link Voucher#getDateAdded()
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
     * Sets the '{@link Voucher#getModifiedBy() <em>modifiedBy</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link Voucher#getModifiedBy()
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
     * Sets the '{@link Voucher#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link Voucher#getModified() modified}'
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
     * Sets the '{@link Voucher#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link Voucher#getId() id}' feature.
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
     * Sets the '{@link Voucher#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link Voucher#getDeleted() deleted}'
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
     * Sets the '{@link Voucher#getValidFrom() <em>validFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link Voucher#getValidFrom()
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
     * Sets the '{@link Voucher#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link Voucher#getValidTo() validTo}'
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
        return "Voucher" + " voucherDate: [" + getVoucherDate() + "]" + " discounted: [" + getDiscounted() + "]" + " doNotBook: [" + getDoNotBook() + "]"
                + " documentNumber: [" + getDocumentNumber() + "]" + " voucherNumber: [" + getVoucherNumber() + "]" + " paidValue: [" + getPaidValue() + "]"
                + " totalValue: [" + getTotalValue() + "]" + " voucherType: [" + getVoucherType() + "]" + " name: [" + getName() + "]" + " dateAdded: ["
                + getDateAdded() + "]" + " modifiedBy: [" + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]"
                + " deleted: [" + getDeleted() + "]" + " validFrom: [" + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
