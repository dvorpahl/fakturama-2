package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * A representation of the model object '<em><b>Contact</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
 * abstract base class for all kinds of contacts. <!-- end-model-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_CONTACT")
@EntityListeners(value = { EntityListener.class })
@MappedSuperclass()
public abstract class Contact extends ModelObject implements IEntity, Serializable, ICategorizable<ContactCategory> {
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
    @Column(name = "T_ALIAS")
    private String alias = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }/* , fetch = FetchType.LAZY */)
    @JoinColumns({ @JoinColumn(name = "FK_CATEGORY") })
    private ContactCategory categories = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the company name of the contact <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "COMPANY")
    private String company = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "CUSTOMERNUMBER")
    private String customerNumber = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * 
     * <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "TITLE")
    private String title = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "FIRSTNAME")
    private String firstName = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the Integer coded value of the contact's gender (0=unknown / 1=male /
     * 2=female / 3=company) <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "GENDER")
    private Integer gender = Integer.valueOf(0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The contact's birthday (can be <code>null</code>) <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.DATE)
    private Date birthday = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * All addresses which belongs to this contact. <!-- end-model-doc -->
     * 
     * @generated
     */
    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumns({ @JoinColumn(name = "CONTACT_ADDRESSES") })
    private List<Address> addresses = new ArrayList<Address>();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The individual discount which is granted to the customer / contact <!--
     * end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DISCOUNT", precision = 5, scale = 3)
    private Double discount = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The default payment for this contact. <!-- end-model-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_PAYMENT") })
    private Payment payment = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "RELIABILITY")
    @Enumerated(EnumType.STRING)
    private ReliabilityType reliability = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Does the contact use net (=1) or gross (=2) price values? <!--
     * end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "USENETGROSS")
    private Short useNetGross = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VATNUMBER")
    private String vatNumber = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VATNUMBERVALID")
    private Boolean vatNumberValid = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "WEBSITE")
    private String website = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name of the contact in a webshop. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "WEBSHOPNAME")
    private String webshopName = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SUPPLIERNUMBER")
    private String supplierNumber = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The GLN (Global Location Number) of this creditor. The GLN is a worldwide
     * identifier for each logistic supplier. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "GLN")
    private Long gln = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The mandate reference for this contact (which in this case is a
     * customer). Used for SEPA direct debit. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "MANDATEREFERENCE")
    private String mandateReference = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_BANKACCOUNT") })
    private BankAccount bankAccount = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This contact has to pay a sales equalization tax. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "USESALESEQUALIZATIONTAX")
    private Boolean useSalesEqualizationTax = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "NOTE")
    @Lob()
    private String note = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "REGISTERNUMBER")
    private String registerNumber = null;

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
    public boolean isSameAs(Contact other) {
        boolean retval = true;
        if (other != null) {
            if (alias != null && other.getAlias() != null) {
                retval &= alias.compareTo(other.getAlias()) == 0;
            }
            if (getCategories() != null) {
                retval &= getCategories().isSameAs(other.getCategories());
            }
            if (company != null && other.getCompany() != null) {
                retval &= company.compareTo(other.getCompany()) == 0;
            }
            if (customerNumber != null && other.getCustomerNumber() != null) {
                retval &= customerNumber.compareTo(other.getCustomerNumber()) == 0;
            }
            if (title != null && other.getTitle() != null) {
                retval &= title.compareTo(other.getTitle()) == 0;
            }
            if (firstName != null && other.getFirstName() != null) {
                retval &= firstName.compareTo(other.getFirstName()) == 0;
            }
            if (gender != null && other.getGender() != null) {
                retval &= gender.compareTo(other.getGender()) == 0;
            }
            if (birthday != null && other.getBirthday() != null) {
                retval &= birthday.compareTo(other.getBirthday()) == 0;
            }
            /* reference to a Set (addresses) or a volatile member cannot be compared... */
            if (discount != null && other.getDiscount() != null) {
                retval &= discount.compareTo(other.getDiscount()) == 0;
            }
            if (getPayment() != null) {
                retval &= getPayment().isSameAs(other.getPayment());
            }
            if (reliability != null) {
                retval &= reliability.equals(other.getReliability());
            }
            if (useNetGross != null) {
                retval &= useNetGross.equals(other.getUseNetGross());
            }
            if (vatNumber != null && other.getVatNumber() != null) {
                retval &= vatNumber.compareTo(other.getVatNumber()) == 0;
            }
            if (vatNumberValid != null && other.getVatNumberValid() != null) {
                retval &= vatNumberValid.compareTo(other.getVatNumberValid()) == 0;
            }
            if (website != null && other.getWebsite() != null) {
                retval &= website.compareTo(other.getWebsite()) == 0;
            }
            if (webshopName != null && other.getWebshopName() != null) {
                retval &= webshopName.compareTo(other.getWebshopName()) == 0;
            }
            if (supplierNumber != null && other.getSupplierNumber() != null) {
                retval &= supplierNumber.compareTo(other.getSupplierNumber()) == 0;
            }
            if (gln != null) {
                retval &= gln.equals(other.getGln());
            }
            if (mandateReference != null && other.getMandateReference() != null) {
                retval &= mandateReference.compareTo(other.getMandateReference()) == 0;
            }
            if (getBankAccount() != null) {
                retval &= getBankAccount().isSameAs(other.getBankAccount());
            }
            if (useSalesEqualizationTax != null && other.getUseSalesEqualizationTax() != null) {
                retval &= useSalesEqualizationTax.compareTo(other.getUseSalesEqualizationTax()) == 0;
            }
            if (note != null && other.getNote() != null) {
                retval &= note.compareTo(other.getNote()) == 0;
            }
            if (registerNumber != null && other.getRegisterNumber() != null) {
                retval &= registerNumber.compareTo(other.getRegisterNumber()) == 0;
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

    /**
     * Returns the value of '<em><b>alias</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>alias</b></em>' feature
     * @generated
     */
    public String getAlias() {

        return alias;
    }

    /**
     * Sets the '{@link Contact#getAlias() <em>alias</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newAlias
     *            the new value of the '{@link Contact#getAlias() alias}'
     *            feature.
     * @generated
     */
    public void setAlias(String newAlias) {
        firePropertyChange("alias", this.alias, newAlias);
        alias = newAlias;
    }

    /**
     * Returns the value of '<em><b>categories</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>categories</b></em>' feature
     * @generated
     */
    public ContactCategory getCategories() {

        return categories;
    }

    /**
     * Sets the '{@link Contact#getCategories() <em>categories</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCategories
     *            the new value of the '{@link Contact#getCategories()
     *            categories}' feature.
     * @generated
     */
    public void setCategories(ContactCategory newCategories) {
        firePropertyChange("categories", this.categories, newCategories);
        categories = newCategories;
    }

    /**
     * Returns the value of '<em><b>company</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the company name of the contact <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>company</b></em>' feature
     * @generated
     */
    public String getCompany() {

        return company;
    }

    /**
     * Sets the '{@link Contact#getCompany() <em>company</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the company name of the contact <!-- end-model-doc -->
     * 
     * @param newCompany
     *            the new value of the '{@link Contact#getCompany() company}'
     *            feature.
     * @generated
     */
    public void setCompany(String newCompany) {
        firePropertyChange("company", this.company, newCompany);
        company = newCompany;
    }

    /**
     * Returns the value of '<em><b>customerNumber</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>customerNumber</b></em>' feature
     * @generated
     */
    public String getCustomerNumber() {

        return customerNumber;
    }

    /**
     * Sets the '{@link Contact#getCustomerNumber() <em>customerNumber</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCustomerNumber
     *            the new value of the '{@link Contact#getCustomerNumber()
     *            customerNumber}' feature.
     * @generated
     */
    public void setCustomerNumber(String newCustomerNumber) {
        firePropertyChange("customerNumber", this.customerNumber, newCustomerNumber);
        customerNumber = newCustomerNumber;
    }

    /**
     * Returns the value of '<em><b>title</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * 
     * <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>title</b></em>' feature
     * @generated
     */
    public String getTitle() {

        return title;
    }

    /**
     * Sets the '{@link Contact#getTitle() <em>title</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * 
     * <!-- end-model-doc -->
     * 
     * @param newTitle
     *            the new value of the '{@link Contact#getTitle() title}'
     *            feature.
     * @generated
     */
    public void setTitle(String newTitle) {
        firePropertyChange("title", this.title, newTitle);
        title = newTitle;
    }

    /**
     * Returns the value of '<em><b>firstName</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>firstName</b></em>' feature
     * @generated
     */
    public String getFirstName() {

        return firstName;
    }

    /**
     * Sets the '{@link Contact#getFirstName() <em>firstName</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newFirstName
     *            the new value of the '{@link Contact#getFirstName()
     *            firstName}' feature.
     * @generated
     */
    public void setFirstName(String newFirstName) {
        firePropertyChange("firstName", this.firstName, newFirstName);
        firstName = newFirstName;
    }

    /**
     * Returns the value of '<em><b>gender</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the Integer coded value of the contact's gender (0=unknown / 1=male /
     * 2=female / 3=company) <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>gender</b></em>' feature
     * @generated
     */
    public Integer getGender() {

        return gender;
    }

    /**
     * Sets the '{@link Contact#getGender() <em>gender</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the Integer coded value of the contact's gender (0=unknown / 1=male /
     * 2=female / 3=company) <!-- end-model-doc -->
     * 
     * @param newGender
     *            the new value of the '{@link Contact#getGender() gender}'
     *            feature.
     * @generated
     */
    public void setGender(Integer newGender) {
        firePropertyChange("gender", this.gender, newGender);
        gender = newGender;
    }

    /**
     * Returns the value of '<em><b>birthday</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The contact's birthday (can be <code>null</code>) <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>birthday</b></em>' feature
     * @generated
     */
    public Date getBirthday() {

        return birthday;
    }

    /**
     * Sets the '{@link Contact#getBirthday() <em>birthday</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The contact's birthday (can be <code>null</code>) <!-- end-model-doc -->
     * 
     * @param newBirthday
     *            the new value of the '{@link Contact#getBirthday() birthday}'
     *            feature.
     * @generated
     */
    public void setBirthday(Date newBirthday) {
        firePropertyChange("birthday", this.birthday, newBirthday);
        birthday = newBirthday;
    }

    /**
     * Returns the value of '<em><b>addresses</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * All addresses which belongs to this contact. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>addresses</b></em>' feature
     * @generated
     */
    public List<Address> getAddresses() {

        return addresses;
    }

    /**
     * Adds to the <em>addresses</em> feature.
     *
     * @param addressesValue
     *            value to add
     *
     * @generated
     */
    public boolean addToAddresses(Address addressesValue) {
        if (!addresses.contains(addressesValue)) {
            addresses.add(addressesValue);
            addressesValue.setContact(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes from the <em>addresses</em> feature.
     *
     * @param addressesValue
     *            value to remove
     *
     * @generated
     */
    public boolean removeFromAddresses(Address addressesValue) {
        if (addresses.contains(addressesValue)) {
            addresses.remove(addressesValue);
            addressesValue.setContact(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Clears the <em>addresses</em> feature.
     * 
     * @generated
     */
    public void clearAddresses() {
        while (!addresses.isEmpty()) {
            removeFromAddresses(addresses.iterator().next());
        }
    }

    /**
     * Sets the '{@link Contact#getAddresses() <em>addresses</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * All addresses which belongs to this contact. <!-- end-model-doc -->
     * 
     * @param newAddresses
     *            the new value of the '{@link Contact#getAddresses()
     *            addresses}' feature.
     * @generated
     */
    public void setAddresses(List<Address> newAddresses) {
        firePropertyChange("addresses", this.addresses, newAddresses);
        addresses = newAddresses;
    }

    /**
     * Returns the value of '<em><b>discount</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The individual discount which is granted to the customer / contact <!--
     * end-model-doc -->
     * 
     * @return the value of '<em><b>discount</b></em>' feature
     * @generated
     */
    public Double getDiscount() {

        return discount;
    }

    /**
     * Sets the '{@link Contact#getDiscount() <em>discount</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The individual discount which is granted to the customer / contact <!--
     * end-model-doc -->
     * 
     * @param newDiscount
     *            the new value of the '{@link Contact#getDiscount() discount}'
     *            feature.
     * @generated
     */
    public void setDiscount(Double newDiscount) {
        firePropertyChange("discount", this.discount, newDiscount);
        discount = newDiscount;
    }

    /**
     * Returns the value of '<em><b>payment</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The default payment for this contact. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>payment</b></em>' feature
     * @generated
     */
    public Payment getPayment() {

        return payment;
    }

    /**
     * Sets the '{@link Contact#getPayment () <em>payment</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The default payment for this contact. <!-- end-model-doc -->
     * 
     * @param newPayment
     *            the new value of the '{@link Contact#getPayment () payment}'
     *            feature.
     * @generated
     */
    public void setPayment(Payment newPayment) {
        firePropertyChange("payment", this.payment, newPayment);
        payment = newPayment;
    }

    /**
     * Returns the value of '<em><b>reliability</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>reliability</b></em>' feature
     * @generated
     */
    public ReliabilityType getReliability() {

        return reliability;
    }

    /**
     * Sets the '{@link Contact#getReliability() <em>reliability</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newReliability
     *            the new value of the '{@link Contact#getReliability()
     *            reliability}' feature.
     * @generated
     */
    public void setReliability(ReliabilityType newReliability) {
        firePropertyChange("reliability", this.reliability, newReliability);
        reliability = newReliability;
    }

    /**
     * Returns the value of '<em><b>useNetGross</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Does the contact use net (=1) or gross (=2) price values? <!--
     * end-model-doc -->
     * 
     * @return the value of '<em><b>useNetGross</b></em>' feature
     * @generated
     */
    public Short getUseNetGross() {

        return useNetGross;
    }

    /**
     * Sets the '{@link Contact#getUseNetGross() <em>useNetGross</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Does the contact use net (=1) or gross (=2) price values? <!--
     * end-model-doc -->
     * 
     * @param newUseNetGross
     *            the new value of the '{@link Contact#getUseNetGross()
     *            useNetGross}' feature.
     * @generated
     */
    public void setUseNetGross(Short newUseNetGross) {
        firePropertyChange("useNetGross", this.useNetGross, newUseNetGross);
        useNetGross = newUseNetGross;
    }

    /**
     * Returns the value of '<em><b>vatNumber</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>vatNumber</b></em>' feature
     * @generated
     */
    public String getVatNumber() {

        return vatNumber;
    }

    /**
     * Sets the '{@link Contact#getVatNumber() <em>vatNumber</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newVatNumber
     *            the new value of the '{@link Contact#getVatNumber()
     *            vatNumber}' feature.
     * @generated
     */
    public void setVatNumber(String newVatNumber) {
        firePropertyChange("vatNumber", this.vatNumber, newVatNumber);
        vatNumber = newVatNumber;
    }

    /**
     * Returns the value of '<em><b>vatNumberValid</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>vatNumberValid</b></em>' feature
     * @generated
     */
    public Boolean getVatNumberValid() {

        return vatNumberValid;
    }

    /**
     * Sets the '{@link Contact#getVatNumberValid() <em>vatNumberValid</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newVatNumberValid
     *            the new value of the '{@link Contact#getVatNumberValid()
     *            vatNumberValid}' feature.
     * @generated
     */
    public void setVatNumberValid(Boolean newVatNumberValid) {
        firePropertyChange("vatNumberValid", this.vatNumberValid, newVatNumberValid);
        vatNumberValid = newVatNumberValid;
    }

    /**
     * Returns the value of '<em><b>website</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>website</b></em>' feature
     * @generated
     */
    public String getWebsite() {

        return website;
    }

    /**
     * Sets the '{@link Contact#getWebsite() <em>website</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newWebsite
     *            the new value of the '{@link Contact#getWebsite() website}'
     *            feature.
     * @generated
     */
    public void setWebsite(String newWebsite) {
        firePropertyChange("website", this.website, newWebsite);
        website = newWebsite;
    }

    /**
     * Returns the value of '<em><b>webshopName</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name of the contact in a webshop. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>webshopName</b></em>' feature
     * @generated
     */
    public String getWebshopName() {

        return webshopName;
    }

    /**
     * Sets the '{@link Contact#getWebshopName() <em>webshopName</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name of the contact in a webshop. <!-- end-model-doc -->
     * 
     * @param newWebshopName
     *            the new value of the '{@link Contact#getWebshopName()
     *            webshopName}' feature.
     * @generated
     */
    public void setWebshopName(String newWebshopName) {
        firePropertyChange("webshopName", this.webshopName, newWebshopName);
        webshopName = newWebshopName;
    }

    /**
     * Returns the value of '<em><b>supplierNumber</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>supplierNumber</b></em>' feature
     * @generated
     */
    public String getSupplierNumber() {

        return supplierNumber;
    }

    /**
     * Sets the '{@link Contact#getSupplierNumber() <em>supplierNumber</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newSupplierNumber
     *            the new value of the '{@link Contact#getSupplierNumber()
     *            supplierNumber}' feature.
     * @generated
     */
    public void setSupplierNumber(String newSupplierNumber) {
        firePropertyChange("supplierNumber", this.supplierNumber, newSupplierNumber);
        supplierNumber = newSupplierNumber;
    }

    /**
     * Returns the value of '<em><b>gln</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The GLN (Global Location Number) of this creditor. The GLN is a worldwide
     * identifier for each logistic supplier. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>gln</b></em>' feature
     * @generated
     */
    public Long getGln() {

        return gln;
    }

    /**
     * Sets the '{@link Contact#getGln() <em>gln</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The GLN (Global Location Number) of this creditor. The GLN is a worldwide
     * identifier for each logistic supplier. <!-- end-model-doc -->
     * 
     * @param newGln
     *            the new value of the '{@link Contact#getGln() gln}' feature.
     * @generated
     */
    public void setGln(Long newGln) {
        firePropertyChange("gln", this.gln, newGln);
        gln = newGln;
    }

    /**
     * Returns the value of '<em><b>mandateReference</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The mandate reference for this contact (which in this case is a
     * customer). Used for SEPA direct debit. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>mandateReference</b></em>' feature
     * @generated
     */
    public String getMandateReference() {

        return mandateReference;
    }

    /**
     * Sets the '{@link Contact#getMandateReference()
     * <em>mandateReference</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The mandate reference for this contact (which in this case is a
     * customer). Used for SEPA direct debit. <!-- end-model-doc -->
     * 
     * @param newMandateReference
     *            the new value of the '{@link Contact#getMandateReference()
     *            mandateReference}' feature.
     * @generated
     */
    public void setMandateReference(String newMandateReference) {
        firePropertyChange("mandateReference", this.mandateReference, newMandateReference);
        mandateReference = newMandateReference;
    }

    /**
     * Returns the value of '<em><b>bankAccount</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>bankAccount</b></em>' feature
     * @generated
     */
    public BankAccount getBankAccount() {

        return bankAccount;
    }

    /**
     * Sets the '{@link Contact#getBankAccount() <em>bankAccount</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newBankAccount
     *            the new value of the '{@link Contact#getBankAccount()
     *            bankAccount}' feature.
     * @generated
     */
    public void setBankAccount(BankAccount newBankAccount) {
        firePropertyChange("bankAccount", this.bankAccount, newBankAccount);
        bankAccount = newBankAccount;
    }

    /**
     * Returns the value of '<em><b>useSalesEqualizationTax</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This contact has to pay a sales equalization tax. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>useSalesEqualizationTax</b></em>' feature
     * @generated
     */
    public Boolean getUseSalesEqualizationTax() {

        return useSalesEqualizationTax;
    }

    /**
     * Sets the '{@link Contact#getUseSalesEqualizationTax()
     * <em>useSalesEqualizationTax</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This contact has to pay a sales equalization tax. <!-- end-model-doc -->
     * 
     * @param newUseSalesEqualizationTax
     *            the new value of the
     *            '{@link Contact#getUseSalesEqualizationTax()
     *            useSalesEqualizationTax}' feature.
     * @generated
     */
    public void setUseSalesEqualizationTax(Boolean newUseSalesEqualizationTax) {
        firePropertyChange("useSalesEqualizationTax", this.useSalesEqualizationTax, newUseSalesEqualizationTax);
        useSalesEqualizationTax = newUseSalesEqualizationTax;
    }

    /**
     * Returns the value of '<em><b>note</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>note</b></em>' feature
     * @generated
     */
    public String getNote() {

        return note;
    }

    /**
     * Sets the '{@link Contact#getNote() <em>note</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newNote
     *            the new value of the '{@link Contact#getNote() note}' feature.
     * @generated
     */
    public void setNote(String newNote) {
        firePropertyChange("note", this.note, newNote);
        note = newNote;
    }

    /**
     * Returns the value of '<em><b>registerNumber</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>registerNumber</b></em>' feature
     * @generated
     */
    public String getRegisterNumber() {

        return registerNumber;
    }

    /**
     * Sets the '{@link Contact#getRegisterNumber() <em>registerNumber</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newRegisterNumber
     *            the new value of the '{@link Contact#getRegisterNumber()
     *            registerNumber}' feature.
     * @generated
     */
    public void setRegisterNumber(String newRegisterNumber) {
        firePropertyChange("registerNumber", this.registerNumber, newRegisterNumber);
        registerNumber = newRegisterNumber;
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
     * Sets the '{@link Contact#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link Contact#getName() name}' feature.
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
     * Sets the '{@link Contact#getDateAdded() <em>dateAdded</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link Contact#getDateAdded()
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
     * Sets the '{@link Contact#getModifiedBy() <em>modifiedBy</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link Contact#getModifiedBy()
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
     * Sets the '{@link Contact#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link Contact#getModified() modified}'
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
     * Sets the '{@link Contact#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link Contact#getId() id}' feature.
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
     * Sets the '{@link Contact#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link Contact#getDeleted() deleted}'
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
     * Sets the '{@link Contact#getValidFrom() <em>validFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link Contact#getValidFrom()
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
     * Sets the '{@link Contact#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link Contact#getValidTo() validTo}'
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
        return "Contact" + " alias: [" + getAlias() + "]" + " company: [" + getCompany() + "]" + " customerNumber: [" + getCustomerNumber() + "]" + " title: ["
                + getTitle() + "]" + " firstName: [" + getFirstName() + "]" + " gender: [" + getGender() + "]" + " birthday: [" + getBirthday() + "]"
                + " discount: [" + getDiscount() + "]" + " reliability: [" + getReliability() + "]" + " useNetGross: [" + getUseNetGross() + "]"
                + " vatNumber: [" + getVatNumber() + "]" + " vatNumberValid: [" + getVatNumberValid() + "]" + " website: [" + getWebsite() + "]"
                + " webshopName: [" + getWebshopName() + "]" + " supplierNumber: [" + getSupplierNumber() + "]" + " gln: [" + getGln() + "]"
                + " mandateReference: [" + getMandateReference() + "]" + " useSalesEqualizationTax: [" + getUseSalesEqualizationTax() + "]" + " note: ["
                + getNote() + "]" + " registerNumber: [" + getRegisterNumber() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded() + "]"
                + " modifiedBy: [" + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: [" + getDeleted()
                + "]" + " validFrom: [" + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
