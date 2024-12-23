package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.lang.Boolean;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * A representation of the model object '<em><b>DocumentReceiver</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
 * receiver's information for a certain document. <!-- end-model-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_DOCUMENTRECEIVER")
@EntityListeners(value = { EntityListener.class })
public class DocumentReceiver extends ModelObject implements Serializable, IDescribableEntity, Cloneable {
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
    @Basic()
    @Column(name = "CUSTOMERNUMBER")
    private String customerNumber = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * contact person for this document. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "CONSULTANT")
    private String consultant = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "STREET")
    private String street = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This attribute is for determining a distributed city, i.e. one big city
     * with several urban districts. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "CITYADDON")
    private String cityAddon = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "CITY")
    private String city = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ZIP")
    private String zip = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "COUNTRYCODE")
    private String countryCode = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "MANUALADDRESS")
    private String manualAddress = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The billing type of this document (if it is a letter, a dunning, an
     * invoice etc). <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "BILLINGTYPE")
    @Enumerated(EnumType.ORDINAL)
    private BillingType billingType = BillingType.NONE;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Reference to the original Contact object. This field only holds an ID
     * because it's only a "remember my root". <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ORIGINCONTACTID")
    private Long originContactId = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Reference to the original Address object. This field only holds an ID
     * because it's only a "remember my root". <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ORIGINADDRESSID")
    private Long originAddressId = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "GENDER")
    private Integer gender = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "EMAIL")
    private String email = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "MOBILE")
    private String mobile = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PHONE")
    private String phone = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "FAX")
    private String fax = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SUPPLIERNUMBER")
    private String supplierNumber = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "GLN")
    private Long gln = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
    @Basic()
    @Column(name = "VATNUMBER")
    private String vatNumber = null;

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
    public boolean isSameAs(DocumentReceiver other) {
        boolean retval = true;
        if (other != null) {
            if (alias != null && other.getAlias() != null) {
                retval &= alias.compareTo(other.getAlias()) == 0;
            }
            if (customerNumber != null && other.getCustomerNumber() != null) {
                retval &= customerNumber.compareTo(other.getCustomerNumber()) == 0;
            }
            if (consultant != null && other.getConsultant() != null) {
                retval &= consultant.compareTo(other.getConsultant()) == 0;
            }
            if (company != null && other.getCompany() != null) {
                retval &= company.compareTo(other.getCompany()) == 0;
            }
            if (title != null && other.getTitle() != null) {
                retval &= title.compareTo(other.getTitle()) == 0;
            }
            if (firstName != null && other.getFirstName() != null) {
                retval &= firstName.compareTo(other.getFirstName()) == 0;
            }
            if (street != null && other.getStreet() != null) {
                retval &= street.compareTo(other.getStreet()) == 0;
            }
            if (cityAddon != null && other.getCityAddon() != null) {
                retval &= cityAddon.compareTo(other.getCityAddon()) == 0;
            }
            if (city != null && other.getCity() != null) {
                retval &= city.compareTo(other.getCity()) == 0;
            }
            if (zip != null && other.getZip() != null) {
                retval &= zip.compareTo(other.getZip()) == 0;
            }
            if (countryCode != null && other.getCountryCode() != null) {
                retval &= countryCode.compareTo(other.getCountryCode()) == 0;
            }
            if (manualAddress != null && other.getManualAddress() != null) {
                retval &= manualAddress.compareTo(other.getManualAddress()) == 0;
            }
            if (billingType != null) {
                retval &= billingType.equals(other.getBillingType());
            }
            if (originContactId != null) {
                retval &= originContactId.equals(other.getOriginContactId());
            }
            if (originAddressId != null) {
                retval &= originAddressId.equals(other.getOriginAddressId());
            }
            if (gender != null && other.getGender() != null) {
                retval &= gender.compareTo(other.getGender()) == 0;
            }
            if (email != null && other.getEmail() != null) {
                retval &= email.compareTo(other.getEmail()) == 0;
            }
            if (mobile != null && other.getMobile() != null) {
                retval &= mobile.compareTo(other.getMobile()) == 0;
            }
            if (phone != null && other.getPhone() != null) {
                retval &= phone.compareTo(other.getPhone()) == 0;
            }
            if (fax != null && other.getFax() != null) {
                retval &= fax.compareTo(other.getFax()) == 0;
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
            if (vatNumber != null && other.getVatNumber() != null) {
                retval &= vatNumber.compareTo(other.getVatNumber()) == 0;
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
    public DocumentReceiver clone() {
        DocumentReceiver retval = new DocumentReceiver();
        retval.setAlias(this.getAlias());
        retval.setCustomerNumber(this.getCustomerNumber());
        retval.setConsultant(this.getConsultant());
        retval.setCompany(this.getCompany());
        retval.setTitle(this.getTitle());
        retval.setFirstName(this.getFirstName());
        retval.setStreet(this.getStreet());
        retval.setCityAddon(this.getCityAddon());
        retval.setCity(this.getCity());
        retval.setZip(this.getZip());
        retval.setCountryCode(this.getCountryCode());
        retval.setManualAddress(this.getManualAddress());
        retval.setBillingType(this.getBillingType());
        retval.setOriginContactId(this.getOriginContactId());
        retval.setOriginAddressId(this.getOriginAddressId());
        retval.setGender(this.getGender());
        retval.setEmail(this.getEmail());
        retval.setMobile(this.getMobile());
        retval.setPhone(this.getPhone());
        retval.setFax(this.getFax());
        retval.setSupplierNumber(this.getSupplierNumber());
        retval.setGln(this.getGln());
        retval.setMandateReference(this.getMandateReference());
        retval.setVatNumber(this.getVatNumber());
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
     * Sets the '{@link DocumentReceiver#getAlias() <em>alias</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newAlias
     *            the new value of the '{@link DocumentReceiver#getAlias()
     *            alias}' feature.
     * @generated
     */
    public void setAlias(String newAlias) {
        firePropertyChange("alias", this.alias, newAlias);
        alias = newAlias;
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
     * Sets the '{@link DocumentReceiver#getCustomerNumber()
     * <em>customerNumber</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCustomerNumber
     *            the new value of the
     *            '{@link DocumentReceiver#getCustomerNumber() customerNumber}'
     *            feature.
     * @generated
     */
    public void setCustomerNumber(String newCustomerNumber) {
        firePropertyChange("customerNumber", this.customerNumber, newCustomerNumber);
        customerNumber = newCustomerNumber;
    }

    /**
     * Returns the value of '<em><b>consultant</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * contact person for this document. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>consultant</b></em>' feature
     * @generated
     */
    public String getConsultant() {

        return consultant;
    }

    /**
     * Sets the '{@link DocumentReceiver#getConsultant() <em>consultant</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * contact person for this document. <!-- end-model-doc -->
     * 
     * @param newConsultant
     *            the new value of the '{@link DocumentReceiver#getConsultant()
     *            consultant}' feature.
     * @generated
     */
    public void setConsultant(String newConsultant) {
        firePropertyChange("consultant", this.consultant, newConsultant);
        consultant = newConsultant;
    }

    /**
     * Returns the value of '<em><b>company</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>company</b></em>' feature
     * @generated
     */
    public String getCompany() {

        return company;
    }

    /**
     * Sets the '{@link DocumentReceiver#getCompany() <em>company</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCompany
     *            the new value of the '{@link DocumentReceiver#getCompany()
     *            company}' feature.
     * @generated
     */
    public void setCompany(String newCompany) {
        firePropertyChange("company", this.company, newCompany);
        company = newCompany;
    }

    /**
     * Returns the value of '<em><b>title</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>title</b></em>' feature
     * @generated
     */
    public String getTitle() {

        return title;
    }

    /**
     * Sets the '{@link DocumentReceiver#getTitle() <em>title</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newTitle
     *            the new value of the '{@link DocumentReceiver#getTitle()
     *            title}' feature.
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
     * Sets the '{@link DocumentReceiver#getFirstName() <em>firstName</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newFirstName
     *            the new value of the '{@link DocumentReceiver#getFirstName()
     *            firstName}' feature.
     * @generated
     */
    public void setFirstName(String newFirstName) {
        firePropertyChange("firstName", this.firstName, newFirstName);
        firstName = newFirstName;
    }

    /**
     * Returns the value of '<em><b>street</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>street</b></em>' feature
     * @generated
     */
    public String getStreet() {

        return street;
    }

    /**
     * Sets the '{@link DocumentReceiver#getStreet() <em>street</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newStreet
     *            the new value of the '{@link DocumentReceiver#getStreet()
     *            street}' feature.
     * @generated
     */
    public void setStreet(String newStreet) {
        firePropertyChange("street", this.street, newStreet);
        street = newStreet;
    }

    /**
     * Returns the value of '<em><b>cityAddon</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This attribute is for determining a distributed city, i.e. one big city
     * with several urban districts. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>cityAddon</b></em>' feature
     * @generated
     */
    public String getCityAddon() {

        return cityAddon;
    }

    /**
     * Sets the '{@link DocumentReceiver#getCityAddon() <em>cityAddon</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This attribute is for determining a distributed city, i.e. one big city
     * with several urban districts. <!-- end-model-doc -->
     * 
     * @param newCityAddon
     *            the new value of the '{@link DocumentReceiver#getCityAddon()
     *            cityAddon}' feature.
     * @generated
     */
    public void setCityAddon(String newCityAddon) {
        firePropertyChange("cityAddon", this.cityAddon, newCityAddon);
        cityAddon = newCityAddon;
    }

    /**
     * Returns the value of '<em><b>city</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>city</b></em>' feature
     * @generated
     */
    public String getCity() {

        return city;
    }

    /**
     * Sets the '{@link DocumentReceiver#getCity() <em>city</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCity
     *            the new value of the '{@link DocumentReceiver#getCity() city}'
     *            feature.
     * @generated
     */
    public void setCity(String newCity) {
        firePropertyChange("city", this.city, newCity);
        city = newCity;
    }

    /**
     * Returns the value of '<em><b>zip</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>zip</b></em>' feature
     * @generated
     */
    public String getZip() {

        return zip;
    }

    /**
     * Sets the '{@link DocumentReceiver#getZip() <em>zip</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newZip
     *            the new value of the '{@link DocumentReceiver#getZip() zip}'
     *            feature.
     * @generated
     */
    public void setZip(String newZip) {
        firePropertyChange("zip", this.zip, newZip);
        zip = newZip;
    }

    /**
     * Returns the value of '<em><b>countryCode</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>countryCode</b></em>' feature
     * @generated
     */
    public String getCountryCode() {

        return countryCode;
    }

    /**
     * Sets the '{@link DocumentReceiver#getCountryCode() <em>countryCode</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCountryCode
     *            the new value of the '{@link DocumentReceiver#getCountryCode()
     *            countryCode}' feature.
     * @generated
     */
    public void setCountryCode(String newCountryCode) {
        firePropertyChange("countryCode", this.countryCode, newCountryCode);
        countryCode = newCountryCode;
    }

    /**
     * Returns the value of '<em><b>manualAddress</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>manualAddress</b></em>' feature
     * @generated
     */
    public String getManualAddress() {

        return manualAddress;
    }

    /**
     * Sets the '{@link DocumentReceiver#getManualAddress()
     * <em>manualAddress</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newManualAddress
     *            the new value of the
     *            '{@link DocumentReceiver#getManualAddress() manualAddress}'
     *            feature.
     * @generated
     */
    public void setManualAddress(String newManualAddress) {
    	if(newManualAddress != null) {
    		newManualAddress.replaceAll("\\t", "");
    	}
        firePropertyChange("manualAddress", this.manualAddress, newManualAddress);
        manualAddress = newManualAddress;
    }

    /**
     * Returns the value of '<em><b>billingType</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The billing type of this document (if it is a letter, a dunning, an
     * invoice etc). <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>billingType</b></em>' feature
     * @generated
     */
    public BillingType getBillingType() {

        return billingType;
    }

    /**
     * Sets the '{@link DocumentReceiver#getBillingType() <em>billingType</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The billing type of this document (if it is a letter, a dunning, an
     * invoice etc). <!-- end-model-doc -->
     * 
     * @param newBillingType
     *            the new value of the '{@link DocumentReceiver#getBillingType()
     *            billingType}' feature.
     * @generated
     */
    public void setBillingType(BillingType newBillingType) {
        firePropertyChange("billingType", this.billingType, newBillingType);
        billingType = newBillingType;
    }

    /**
     * Returns the value of '<em><b>originContactId</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Reference to the original Contact object. This field only holds an ID
     * because it's only a "remember my root". <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>originContactId</b></em>' feature
     * @generated
     */
    public Long getOriginContactId() {

        return originContactId;
    }

    /**
     * Sets the '{@link DocumentReceiver#getOriginContactId()
     * <em>originContactId</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Reference to the original Contact object. This field only holds an ID
     * because it's only a "remember my root". <!-- end-model-doc -->
     * 
     * @param newOriginContactId
     *            the new value of the
     *            '{@link DocumentReceiver#getOriginContactId()
     *            originContactId}' feature.
     * @generated
     */
    public void setOriginContactId(Long newOriginContactId) {
        firePropertyChange("originContactId", this.originContactId, newOriginContactId);
        originContactId = newOriginContactId;
    }

    /**
     * Returns the value of '<em><b>originAddressId</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Reference to the original Address object. This field only holds an ID
     * because it's only a "remember my root". <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>originAddressId</b></em>' feature
     * @generated
     */
    public Long getOriginAddressId() {

        return originAddressId;
    }

    /**
     * Sets the '{@link DocumentReceiver#getOriginAddressId()
     * <em>originAddressId</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Reference to the original Address object. This field only holds an ID
     * because it's only a "remember my root". <!-- end-model-doc -->
     * 
     * @param newOriginAddressId
     *            the new value of the
     *            '{@link DocumentReceiver#getOriginAddressId()
     *            originAddressId}' feature.
     * @generated
     */
    public void setOriginAddressId(Long newOriginAddressId) {
        firePropertyChange("originAddressId", this.originAddressId, newOriginAddressId);
        originAddressId = newOriginAddressId;
    }

    /**
     * Returns the value of '<em><b>gender</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>gender</b></em>' feature
     * @generated
     */
    public Integer getGender() {

        return gender;
    }

    /**
     * Sets the '{@link DocumentReceiver#getGender() <em>gender</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newGender
     *            the new value of the '{@link DocumentReceiver#getGender()
     *            gender}' feature.
     * @generated
     */
    public void setGender(Integer newGender) {
        firePropertyChange("gender", this.gender, newGender);
        gender = newGender;
    }

    /**
     * Returns the value of '<em><b>email</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>email</b></em>' feature
     * @generated
     */
    public String getEmail() {

        return email;
    }

    /**
     * Sets the '{@link DocumentReceiver#getEmail() <em>email</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newEmail
     *            the new value of the '{@link DocumentReceiver#getEmail()
     *            email}' feature.
     * @generated
     */
    public void setEmail(String newEmail) {
        firePropertyChange("email", this.email, newEmail);
        email = newEmail;
    }

    /**
     * Returns the value of '<em><b>mobile</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>mobile</b></em>' feature
     * @generated
     */
    public String getMobile() {

        return mobile;
    }

    /**
     * Sets the '{@link DocumentReceiver#getMobile() <em>mobile</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newMobile
     *            the new value of the '{@link DocumentReceiver#getMobile()
     *            mobile}' feature.
     * @generated
     */
    public void setMobile(String newMobile) {
        firePropertyChange("mobile", this.mobile, newMobile);
        mobile = newMobile;
    }

    /**
     * Returns the value of '<em><b>phone</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>phone</b></em>' feature
     * @generated
     */
    public String getPhone() {

        return phone;
    }

    /**
     * Sets the '{@link DocumentReceiver#getPhone() <em>phone</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPhone
     *            the new value of the '{@link DocumentReceiver#getPhone()
     *            phone}' feature.
     * @generated
     */
    public void setPhone(String newPhone) {
        firePropertyChange("phone", this.phone, newPhone);
        phone = newPhone;
    }

    /**
     * Returns the value of '<em><b>fax</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>fax</b></em>' feature
     * @generated
     */
    public String getFax() {

        return fax;
    }

    /**
     * Sets the '{@link DocumentReceiver#getFax() <em>fax</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newFax
     *            the new value of the '{@link DocumentReceiver#getFax() fax}'
     *            feature.
     * @generated
     */
    public void setFax(String newFax) {
        firePropertyChange("fax", this.fax, newFax);
        fax = newFax;
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
     * Sets the '{@link DocumentReceiver#getSupplierNumber()
     * <em>supplierNumber</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newSupplierNumber
     *            the new value of the
     *            '{@link DocumentReceiver#getSupplierNumber() supplierNumber}'
     *            feature.
     * @generated
     */
    public void setSupplierNumber(String newSupplierNumber) {
        firePropertyChange("supplierNumber", this.supplierNumber, newSupplierNumber);
        supplierNumber = newSupplierNumber;
    }

    /**
     * Returns the value of '<em><b>gln</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>gln</b></em>' feature
     * @generated
     */
    public Long getGln() {

        return gln;
    }

    /**
     * Sets the '{@link DocumentReceiver#getGln() <em>gln</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newGln
     *            the new value of the '{@link DocumentReceiver#getGln() gln}'
     *            feature.
     * @generated
     */
    public void setGln(Long newGln) {
        firePropertyChange("gln", this.gln, newGln);
        gln = newGln;
    }

    /**
     * Returns the value of '<em><b>mandateReference</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>mandateReference</b></em>' feature
     * @generated
     */
    public String getMandateReference() {

        return mandateReference;
    }

    /**
     * Sets the '{@link DocumentReceiver#getMandateReference()
     * <em>mandateReference</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newMandateReference
     *            the new value of the
     *            '{@link DocumentReceiver#getMandateReference()
     *            mandateReference}' feature.
     * @generated
     */
    public void setMandateReference(String newMandateReference) {
        firePropertyChange("mandateReference", this.mandateReference, newMandateReference);
        mandateReference = newMandateReference;
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
     * Sets the '{@link DocumentReceiver#getVatNumber() <em>vatNumber</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newVatNumber
     *            the new value of the '{@link DocumentReceiver#getVatNumber()
     *            vatNumber}' feature.
     * @generated
     */
    public void setVatNumber(String newVatNumber) {
        firePropertyChange("vatNumber", this.vatNumber, newVatNumber);
        vatNumber = newVatNumber;
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
     * Sets the '{@link DocumentReceiver#getDescription() <em>description</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDescription
     *            the new value of the '{@link DocumentReceiver#getDescription()
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
     * Sets the '{@link DocumentReceiver#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link DocumentReceiver#getName() name}'
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
     * Sets the '{@link DocumentReceiver#getDateAdded() <em>dateAdded</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link DocumentReceiver#getDateAdded()
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
     * Sets the '{@link DocumentReceiver#getModifiedBy() <em>modifiedBy</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link DocumentReceiver#getModifiedBy()
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
     * Sets the '{@link DocumentReceiver#getModified() <em>modified</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link DocumentReceiver#getModified()
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
     * Sets the '{@link DocumentReceiver#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link DocumentReceiver#getId() id}'
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
     * Sets the '{@link DocumentReceiver#getDeleted() <em>deleted</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link DocumentReceiver#getDeleted()
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
     * Sets the '{@link DocumentReceiver#getValidFrom() <em>validFrom</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link DocumentReceiver#getValidFrom()
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
     * Sets the '{@link DocumentReceiver#getValidTo() <em>validTo</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link DocumentReceiver#getValidTo()
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
        return "DocumentReceiver" + " alias: [" + getAlias() + "]" + " customerNumber: [" + getCustomerNumber() + "]" + " consultant: [" + getConsultant() + "]"
                + " company: [" + getCompany() + "]" + " title: [" + getTitle() + "]" + " firstName: [" + getFirstName() + "]" + " street: [" + getStreet()
                + "]" + " cityAddon: [" + getCityAddon() + "]" + " city: [" + getCity() + "]" + " zip: [" + getZip() + "]" + " countryCode: ["
                + getCountryCode() + "]" + " manualAddress: [" + getManualAddress() + "]" + " billingType: [" + getBillingType() + "]" + " originContactId: ["
                + getOriginContactId() + "]" + " originAddressId: [" + getOriginAddressId() + "]" + " gender: [" + getGender() + "]" + " email: [" + getEmail()
                + "]" + " mobile: [" + getMobile() + "]" + " phone: [" + getPhone() + "]" + " fax: [" + getFax() + "]" + " supplierNumber: ["
                + getSupplierNumber() + "]" + " gln: [" + getGln() + "]" + " mandateReference: [" + getMandateReference() + "]" + " vatNumber: ["
                + getVatNumber() + "]" + " description: [" + getDescription() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded() + "]"
                + " modifiedBy: [" + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: [" + getDeleted()
                + "]" + " validFrom: [" + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
