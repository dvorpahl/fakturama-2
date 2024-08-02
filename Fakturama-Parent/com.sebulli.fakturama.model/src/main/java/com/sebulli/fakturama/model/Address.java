package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * A representation of the model object '<em><b>Address</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_ADDRESS")
@EntityListeners(value = { EntityListener.class })
public class Address extends ModelObject implements Serializable, IEntity, Cloneable {
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
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The country information is stored as a String containing the language and
     * the country abbreviation (from ISO 3166), e.g., "de_CH" for the German
     * speaking part of Switzerland. The Locale class from java.util is used to
     * display the country information. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "COUNTRYCODE")
    private String countryCode = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The contact to which this address belongs. This field is set via Contact
     * class (bidirectional association). <!-- end-model-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "CONTACT_ADDRESSES") })
    private Contact contact = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The localConsultant holds information about someone who is responsible at
     * the given address. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "LOCALCONSULTANT")
    private String localConsultant = null;

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
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> An
     * additional phone number. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ADDITIONALPHONE")
    private String additionalPhone = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "FAX")
    private String fax = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Denotes what kind of address it is (i.e., an address for billing, for
     * delivery etc). If no ContactType is set this address is valid for all
     * types. <!-- end-model-doc -->
     * 
     * @generated
     */
    @ElementCollection()
    @Column(name = "T_ELEMENT")
    @Enumerated(EnumType.STRING)
    @CollectionTable(joinColumns = { @JoinColumn(name = "ADDRESS_CONTACTTYPES") }, name = "FKT_ADDRESS_CONTACTTYPES")
    private List<ContactType> contactTypes = new ArrayList<>();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Additional information to the address. Here you can store e.g. a delivery
     * info or any other hint which should be printed in the delivery document.
     * <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ADDRESSADDON")
    private String addressAddon = null;

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
    public boolean isSameAs(final Address other) {
        boolean retval = true;
        if (other != null) {
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
            if (getContact() != null) {
                retval &= getContact().isSameAs(other.getContact());
            }
            if (localConsultant != null && other.getLocalConsultant() != null) {
                retval &= localConsultant.compareTo(other.getLocalConsultant()) == 0;
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
            if (additionalPhone != null && other.getAdditionalPhone() != null) {
                retval &= additionalPhone.compareTo(other.getAdditionalPhone()) == 0;
            }
            if (fax != null && other.getFax() != null) {
                retval &= fax.compareTo(other.getFax()) == 0;
            }
            /* reference to a Set (contactTypes) or a volatile member cannot be compared... */
            if (addressAddon != null && other.getAddressAddon() != null) {
                retval &= addressAddon.compareTo(other.getAddressAddon()) == 0;
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
    public Address clone() {
        Address retval = new Address();
        retval.setStreet(this.getStreet());
        retval.setCityAddon(this.getCityAddon());
        retval.setCity(this.getCity());
        retval.setZip(this.getZip());
        retval.setCountryCode(this.getCountryCode());
        retval.setContact(this.getContact());
        retval.setLocalConsultant(this.getLocalConsultant());
        retval.setEmail(this.getEmail());
        retval.setMobile(this.getMobile());
        retval.setPhone(this.getPhone());
        retval.setAdditionalPhone(this.getAdditionalPhone());
        retval.setFax(this.getFax());
        /* reference to a Set (contactTypes) cannot be compared... */
        retval.setAddressAddon(this.getAddressAddon());
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
     * Sets the '{@link Address#getStreet() <em>street</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newStreet
     *            the new value of the '{@link Address#getStreet() street}'
     *            feature.
     * @generated
     */
    public void setStreet(final String newStreet) {
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
     * Sets the '{@link Address#getCityAddon() <em>cityAddon</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This attribute is for determining a distributed city, i.e. one big city
     * with several urban districts. <!-- end-model-doc -->
     * 
     * @param newCityAddon
     *            the new value of the '{@link Address#getCityAddon()
     *            cityAddon}' feature.
     * @generated
     */
    public void setCityAddon(final String newCityAddon) {
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
     * Sets the '{@link Address#getCity() <em>city</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCity
     *            the new value of the '{@link Address#getCity() city}' feature.
     * @generated
     */
    public void setCity(final String newCity) {
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
     * Sets the '{@link Address#getZip() <em>zip</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newZip
     *            the new value of the '{@link Address#getZip() zip}' feature.
     * @generated
     */
    public void setZip(final String newZip) {
        firePropertyChange("zip", this.zip, newZip);
        zip = newZip;
    }

    /**
     * Returns the value of '<em><b>countryCode</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The country information is stored as a String containing the language and
     * the country abbreviation (from ISO 3166), e.g., "de_CH" for the German
     * speaking part of Switzerland. The Locale class from java.util is used to
     * display the country information. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>countryCode</b></em>' feature
     * @generated
     */
    public String getCountryCode() {

        return countryCode;
    }

    /**
     * Sets the '{@link Address#getCountryCode() <em>countryCode</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The country information is stored as a String containing the language and
     * the country abbreviation (from ISO 3166), e.g., "de_CH" for the German
     * speaking part of Switzerland. The Locale class from java.util is used to
     * display the country information. <!-- end-model-doc -->
     * 
     * @param newCountryCode
     *            the new value of the '{@link Address#getCountryCode()
     *            countryCode}' feature.
     * @generated
     */
    public void setCountryCode(final String newCountryCode) {
        firePropertyChange("countryCode", this.countryCode, newCountryCode);
        countryCode = newCountryCode;
    }

    /**
     * Returns the value of '<em><b>contact</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The contact to which this address belongs. This field is set via Contact
     * class (bidirectional association). <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>contact</b></em>' feature
     * @generated
     */
    public Contact getContact() {

        return contact;
    }

    /**
     * Sets the '{@link Address#getContact() <em>contact</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The contact to which this address belongs. This field is set via Contact
     * class (bidirectional association). <!-- end-model-doc -->
     * 
     * @param newContact
     *            the new value of the '{@link Address#getContact() contact}'
     *            feature.
     * @generated
     */
    public void setContact(final Contact newContact) {
        firePropertyChange("contact", this.contact, newContact);
        contact = newContact;
    }

    /**
     * Returns the value of '<em><b>localConsultant</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The localConsultant holds information about someone who is responsible at
     * the given address. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>localConsultant</b></em>' feature
     * @generated
     */
    public String getLocalConsultant() {

        return localConsultant;
    }

    /**
     * Sets the '{@link Address#getLocalConsultant() <em>localConsultant</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The localConsultant holds information about someone who is responsible at
     * the given address. <!-- end-model-doc -->
     * 
     * @param newLocalConsultant
     *            the new value of the '{@link Address#getLocalConsultant()
     *            localConsultant}' feature.
     * @generated
     */
    public void setLocalConsultant(final String newLocalConsultant) {
        firePropertyChange("localConsultant", this.localConsultant, newLocalConsultant);
        localConsultant = newLocalConsultant;
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
     * Sets the '{@link Address#getEmail() <em>email</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newEmail
     *            the new value of the '{@link Address#getEmail() email}'
     *            feature.
     * @generated
     */
    public void setEmail(final String newEmail) {
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
     * Sets the '{@link Address#getMobile() <em>mobile</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newMobile
     *            the new value of the '{@link Address#getMobile() mobile}'
     *            feature.
     * @generated
     */
    public void setMobile(final String newMobile) {
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
     * Sets the '{@link Address#getPhone() <em>phone</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPhone
     *            the new value of the '{@link Address#getPhone() phone}'
     *            feature.
     * @generated
     */
    public void setPhone(final String newPhone) {
        firePropertyChange("phone", this.phone, newPhone);
        phone = newPhone;
    }

    /**
     * Returns the value of '<em><b>additionalPhone</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> An
     * additional phone number. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>additionalPhone</b></em>' feature
     * @generated
     */
    public String getAdditionalPhone() {

        return additionalPhone;
    }

    /**
     * Sets the '{@link Address#getAdditionalPhone() <em>additionalPhone</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> An
     * additional phone number. <!-- end-model-doc -->
     * 
     * @param newAdditionalPhone
     *            the new value of the '{@link Address#getAdditionalPhone()
     *            additionalPhone}' feature.
     * @generated
     */
    public void setAdditionalPhone(final String newAdditionalPhone) {
        firePropertyChange("additionalPhone", this.additionalPhone, newAdditionalPhone);
        additionalPhone = newAdditionalPhone;
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
     * Sets the '{@link Address#getFax() <em>fax</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newFax
     *            the new value of the '{@link Address#getFax() fax}' feature.
     * @generated
     */
    public void setFax(final String newFax) {
        firePropertyChange("fax", this.fax, newFax);
        fax = newFax;
    }

    /**
     * Returns the value of '<em><b>contactTypes</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Denotes what kind of address it is (i.e., an address for billing, for
     * delivery etc). If no ContactType is set this address is valid for all
     * types. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>contactTypes</b></em>' feature
     * @generated
     */
    public List<ContactType> getContactTypes() {

        return contactTypes;
    }

    /**
     * Sets the '{@link Address#getContactTypes() <em>contactTypes</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Denotes what kind of address it is (i.e., an address for billing, for
     * delivery etc). If no ContactType is set this address is valid for all
     * types. <!-- end-model-doc -->
     * 
     * @param newContactTypes
     *            the new value of the '{@link Address#getContactTypes()
     *            contactTypes}' feature.
     * @generated
     */
    public void setContactTypes(final List<ContactType> newContactTypes) {
        firePropertyChange("contactTypes", this.contactTypes, newContactTypes);
        contactTypes = newContactTypes;
    }

    /**
     * Returns the value of '<em><b>addressAddon</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Additional information to the address. Here you can store e.g. a delivery
     * info or any other hint which should be printed in the delivery document.
     * <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>addressAddon</b></em>' feature
     * @generated
     */
    public String getAddressAddon() {

        return addressAddon;
    }

    /**
     * Sets the '{@link Address#getAddressAddon() <em>addressAddon</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Additional information to the address. Here you can store e.g. a delivery
     * info or any other hint which should be printed in the delivery document.
     * <!-- end-model-doc -->
     * 
     * @param newAddressAddon
     *            the new value of the '{@link Address#getAddressAddon()
     *            addressAddon}' feature.
     * @generated
     */
    public void setAddressAddon(final String newAddressAddon) {
        firePropertyChange("addressAddon", this.addressAddon, newAddressAddon);
        addressAddon = newAddressAddon;
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
     * Sets the '{@link Address#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link Address#getName() name}' feature.
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
     * Sets the '{@link Address#getDateAdded() <em>dateAdded</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link Address#getDateAdded()
     *            dateAdded}' feature.
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
     * Sets the '{@link Address#getModifiedBy() <em>modifiedBy</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link Address#getModifiedBy()
     *            modifiedBy}' feature.
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
     * Sets the '{@link Address#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link Address#getModified() modified}'
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
     * Sets the '{@link Address#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link Address#getId() id}' feature.
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
     * Sets the '{@link Address#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link Address#getDeleted() deleted}'
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
     * Sets the '{@link Address#getValidFrom() <em>validFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link Address#getValidFrom()
     *            validFrom}' feature.
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
     * Sets the '{@link Address#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link Address#getValidTo() validTo}'
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
        return "Address" + " street: [" + getStreet() + "]" + " cityAddon: [" + getCityAddon() + "]" + " city: [" + getCity() + "]" + " zip: [" + getZip() + "]"
                + " countryCode: [" + getCountryCode() + "]" + " localConsultant: [" + getLocalConsultant() + "]" + " email: [" + getEmail() + "]"
                + " mobile: [" + getMobile() + "]" + " phone: [" + getPhone() + "]" + " additionalPhone: [" + getAdditionalPhone() + "]" + " fax: [" + getFax()
                + "]" + " addressAddon: [" + getAddressAddon() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded() + "]" + " modifiedBy: ["
                + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: [" + getDeleted() + "]" + " validFrom: ["
                + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
