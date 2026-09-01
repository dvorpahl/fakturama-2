package com.sebulli.fakturama.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.texo.model.ModelFactory;
import org.eclipse.emf.texo.model.ModelPackage;
import org.eclipse.emf.texo.model.ModelResolver;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * The <b>Package</b> for the model '<em><b>model</b></em>'. It contains
 * initialization code and access to the Factory to instantiate types of this
 * package.
 *
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class FakturamaModelPackage extends ModelPackage {

    /**
     * Is set when the package has been initialized.
     * 
     * @generated
     */
    private static boolean isInitialized = false;

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String NS_URI = "http://www.fakturama.org";

    /**
     * The {@link ModelFactory} for this package. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public static final FakturamaModelFactory MODELFACTORY = new FakturamaModelFactory();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMTYPE_CLASSIFIER_ID = 47;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BILLINGTYPE_CLASSIFIER_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERTYPE_CLASSIFIER_ID = 50;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTTYPE_CLASSIFIER_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int RELIABILITYTYPE_CLASSIFIER_ID = 49;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGVATTYPE_CLASSIFIER_ID = 48;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ABSTRACTCATEGORY_CLASSIFIER_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ABSTRACTCATEGORY_PARENT_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ABSTRACTCATEGORY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ABSTRACTCATEGORY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ABSTRACTCATEGORY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ABSTRACTCATEGORY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ABSTRACTCATEGORY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ABSTRACTCATEGORY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ABSTRACTCATEGORY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ABSTRACTCATEGORY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_CLASSIFIER_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_ACCOUNT_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_VOUCHERDATE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_DISCOUNTED_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_DONOTBOOK_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_DOCUMENTNUMBER_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_ITEMS_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_VOUCHERNUMBER_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_PAIDVALUE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_TOTALVALUE_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_VOUCHERTYPE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHER_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERCATEGORY_CLASSIFIER_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERCATEGORY_PARENT_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERCATEGORY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERCATEGORY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERCATEGORY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERCATEGORY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERCATEGORY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERCATEGORY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERCATEGORY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERCATEGORY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_CLASSIFIER_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_ACCOUNTTYPE_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_VAT_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_PRICE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_POSNR_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_ITEMVOUCHERTYPE_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VOUCHERITEM_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_CLASSIFIER_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_ACCOUNTHOLDER_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_BANKCODE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_BANKNAME_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_BIC_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_IBAN_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BANKACCOUNT_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IENTITY_CLASSIFIER_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IENTITY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IENTITY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IENTITY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IENTITY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IENTITY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IENTITY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IENTITY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IENTITY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_CLASSIFIER_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_STREET_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_CITYADDON_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_CITY_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_ZIP_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_COUNTRYCODE_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_CONTACT_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_LOCALCONSULTANT_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_EMAIL_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_MOBILE_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_PHONE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_ADDITIONALPHONE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_FAX_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_CONTACTTYPES_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_ADDRESSADDON_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ADDRESS_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_CLASSIFIER_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_ALIAS_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_CATEGORIES_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_COMPANY_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_CUSTOMERNUMBER_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_TITLE_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_FIRSTNAME_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_GENDER_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_BIRTHDAY_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_ADDRESSES_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_DISCOUNT_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_PAYMENT_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_RELIABILITY_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_USENETGROSS_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_VATNUMBER_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_VATNUMBERVALID_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_WEBSITE_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_WEBSHOPNAME_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_SUPPLIERNUMBER_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_GLN_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_MANDATEREFERENCE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_BANKACCOUNT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_USESALESEQUALIZATIONTAX_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_NOTE_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_REGISTERNUMBER_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACT_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_CLASSIFIER_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_ALIAS_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_CATEGORIES_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_COMPANY_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_CUSTOMERNUMBER_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_TITLE_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_FIRSTNAME_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_GENDER_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_BIRTHDAY_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_ADDRESSES_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_DISCOUNT_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_PAYMENT_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_RELIABILITY_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_USENETGROSS_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_VATNUMBER_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_VATNUMBERVALID_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_WEBSITE_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_WEBSHOPNAME_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_SUPPLIERNUMBER_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_GLN_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_MANDATEREFERENCE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_BANKACCOUNT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_USESALESEQUALIZATIONTAX_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_NOTE_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_REGISTERNUMBER_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDITOR_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTCATEGORY_CLASSIFIER_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTCATEGORY_PARENT_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTCATEGORY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTCATEGORY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTCATEGORY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTCATEGORY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTCATEGORY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTCATEGORY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTCATEGORY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONTACTCATEGORY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_CLASSIFIER_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_ADDITIONALINFO_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_ADDRESSFIRSTLINE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_BILLINGTYPE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_CUSTOMERREF_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_DEPOSIT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_DOCUMENTDATE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_DUEDAYS_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_INVOICEREFERENCE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_ITEMS_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_ITEMSREBATE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_MESSAGE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_MESSAGE2_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_MESSAGE3_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_NETGROSS_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_NOVATREFERENCE_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_ODTPATH_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_ORDERDATE_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_PAIDVALUE_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_PAID_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_PAYDATE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_PAYMENT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_PDFPATH_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_PRINTED_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_PRINTTEMPLATE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_PROGRESS_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_RECEIVER_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_SERVICEDATE_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_SHIPPING_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_SHIPPINGAUTOVAT_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_SHIPPINGVALUE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_SOURCEDOCUMENT_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_TARA_FEATURE_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_TOTALVALUE_FEATURE_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_TRANSACTIONID_FEATURE_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_WEBSHOPDATE_FEATURE_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_WEBSHOPID_FEATURE_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_VESTINGPERIODSTART_FEATURE_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_VESTINGPERIODEND_FEATURE_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_VERSION_FEATURE_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENT_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_CLASSIFIER_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_ALIAS_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_CUSTOMERNUMBER_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_CONSULTANT_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_COMPANY_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_TITLE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_FIRSTNAME_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_STREET_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_CITYADDON_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_CITY_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_ZIP_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_COUNTRYCODE_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_MANUALADDRESS_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_BILLINGTYPE_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_ORIGINCONTACTID_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_ORIGINADDRESSID_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_GENDER_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_EMAIL_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_MOBILE_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_PHONE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_FAX_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_SUPPLIERNUMBER_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_GLN_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_MANDATEREFERENCE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_VATNUMBER_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_DESCRIPTION_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTRECEIVER_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_CLASSIFIER_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_NOVAT_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_ITEMREBATE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_ITEMNUMBER_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_PRODUCT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_QUANTITY_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_ORIGINQUANTITY_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_WEIGHT_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_OPTIONAL_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_PICTURE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_PRICE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_QUANTITYUNIT_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_ITEMVAT_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_GTIN_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_POSNR_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_ITEMTYPE_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_VESTINGPERIODSTART_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_VESTINGPERIODEND_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_SUPPLIERITEMNUMBER_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_DESCRIPTION_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DOCUMENTITEM_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_CLASSIFIER_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_PAYMENTNAME_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_PAYMENTTEXT_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_PAYMENTDESCRIPTION_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_SHIPPINGNAME_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_SHIPPINGDESCRIPTION_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_SHIPPINGVATDESCRIPTION_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_SHIPPINGVATVALUE_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_NOVATNAME_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_NOVATDESCRIPTION_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INDIVIDUALDOCUMENTINFO_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IDESCRIBABLEENTITY_CLASSIFIER_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IDESCRIBABLEENTITY_DESCRIPTION_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IDESCRIBABLEENTITY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IDESCRIBABLEENTITY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IDESCRIBABLEENTITY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IDESCRIBABLEENTITY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IDESCRIBABLEENTITY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IDESCRIBABLEENTITY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IDESCRIBABLEENTITY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int IDESCRIBABLEENTITY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_CLASSIFIER_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_VALUE_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_CATEGORY_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMACCOUNTTYPE_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_CLASSIFIER_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_DISCOUNTDAYS_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_DISCOUNTVALUE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_NETDAYS_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_CATEGORY_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_PAIDTEXT_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_UNPAIDTEXT_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_DEPOSITTEXT_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_CODE_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_DESCRIPTION_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PAYMENT_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_CLASSIFIER_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_BLOCK1_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_BLOCK2_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_BLOCK3_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_BLOCK4_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_BLOCK5_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_CATEGORIES_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_ATTRIBUTES_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_PICTURE_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_ITEMNUMBER_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_SUPPLIERITEMNUMBER_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_PRICE1_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_PRICE2_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_PRICE3_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_PRICE4_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_PRICE5_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_QUANTITY_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_QUANTITYUNIT_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_SELLINGUNIT_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_VAT_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_WEBSHOPID_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_WEIGHT_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_GTIN_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_COSTPRICE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_ALLOWANCE_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_BLOCKPRICES_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_CDF01_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_CDF02_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_CDF03_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_NOTE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int PRODUCT_STOCKMANAGED_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int PRODUCT_DESCRIPTION_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCT_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTCATEGORY_CLASSIFIER_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTCATEGORY_PARENT_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTCATEGORY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTCATEGORY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTCATEGORY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTCATEGORY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTCATEGORY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTCATEGORY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTCATEGORY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTCATEGORY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_CLASSIFIER_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_ATTRIBUTEVALUE_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_SEQUENCENUMBER_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTOPTIONS_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_CLASSIFIER_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_BLOCK_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_PRICE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PRODUCTBLOCKPRICE_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ROLE_CLASSIFIER_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ROLE_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ROLE_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ROLE_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ROLE_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ROLE_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ROLE_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ROLE_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ROLE_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TENANT_CLASSIFIER_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TENANT_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TENANT_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TENANT_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TENANT_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TENANT_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TENANT_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TENANT_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TENANT_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_CLASSIFIER_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_AUTOVAT_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_SHIPPINGVALUE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_SHIPPINGVAT_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_CATEGORIES_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_CODE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_DESCRIPTION_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPING_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGCATEGORY_CLASSIFIER_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGCATEGORY_PARENT_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGCATEGORY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGCATEGORY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGCATEGORY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGCATEGORY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGCATEGORY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGCATEGORY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGCATEGORY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SHIPPINGCATEGORY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTCATEGORY_CLASSIFIER_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTCATEGORY_PARENT_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTCATEGORY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTCATEGORY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTCATEGORY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTCATEGORY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTCATEGORY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTCATEGORY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTCATEGORY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTCATEGORY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_CLASSIFIER_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_TEXT_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_CATEGORIES_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int TEXTMODULE_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_CLASSIFIER_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_PASSWORD_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_TENANT_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_USERNAME_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_ROLES_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USER_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_CLASSIFIER_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_VALUE_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_USER_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_DEFAULT__FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_GLOBAL_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_QUALIFIER_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int USERPROPERTY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_CLASSIFIER_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_TAXVALUE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_CATEGORY_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_SALESEQUALIZATIONTAX_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_DESCRIPTION_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VAT_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VATCATEGORY_CLASSIFIER_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VATCATEGORY_PARENT_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VATCATEGORY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VATCATEGORY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VATCATEGORY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VATCATEGORY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VATCATEGORY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VATCATEGORY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VATCATEGORY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int VATCATEGORY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_CLASSIFIER_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_ABBREVIATION_DE_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_ABBREVIATION_EN_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_CODE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_TARGET_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_NAME_DE_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CEFACTCODE_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_CLASSIFIER_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_WEBSHOPVENDOR_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_WEBSHOPVERSION_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_STATEMAPPING_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOP_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_CLASSIFIER_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_WEBSHOPSTATE_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_FAKTURAMAORDERSTATE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int WEBSHOPSTATEMAPPING_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_CLASSIFIER_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_ADDITIONALINFO_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_ADDRESSFIRSTLINE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_BILLINGTYPE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_CUSTOMERREF_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_DEPOSIT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_DOCUMENTDATE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_DUEDAYS_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_INVOICEREFERENCE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_ITEMS_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_ITEMSREBATE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_MESSAGE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_MESSAGE2_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_MESSAGE3_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_NETGROSS_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_NOVATREFERENCE_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_ODTPATH_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_ORDERDATE_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_PAIDVALUE_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_PAID_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_PAYDATE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_PAYMENT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_PDFPATH_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_PRINTED_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_PRINTTEMPLATE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_PROGRESS_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_RECEIVER_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_SERVICEDATE_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_SHIPPING_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_SHIPPINGAUTOVAT_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_SHIPPINGVALUE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_SOURCEDOCUMENT_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_TARA_FEATURE_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_TOTALVALUE_FEATURE_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_TRANSACTIONID_FEATURE_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_WEBSHOPDATE_FEATURE_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_WEBSHOPID_FEATURE_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_VESTINGPERIODSTART_FEATURE_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_VESTINGPERIODEND_FEATURE_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_VERSION_FEATURE_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CONFIRMATION_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_CLASSIFIER_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_ADDITIONALINFO_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_ADDRESSFIRSTLINE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_BILLINGTYPE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_CUSTOMERREF_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_DEPOSIT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_DOCUMENTDATE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_DUEDAYS_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_INVOICEREFERENCE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_ITEMS_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_ITEMSREBATE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_MESSAGE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_MESSAGE2_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_MESSAGE3_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_NETGROSS_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_NOVATREFERENCE_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_ODTPATH_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_ORDERDATE_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_PAIDVALUE_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_PAID_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_PAYDATE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_PAYMENT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_PDFPATH_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_PRINTED_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_PRINTTEMPLATE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_PROGRESS_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_RECEIVER_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_SERVICEDATE_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_SHIPPING_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_SHIPPINGAUTOVAT_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_SHIPPINGVALUE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_SOURCEDOCUMENT_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_TARA_FEATURE_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_TOTALVALUE_FEATURE_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_TRANSACTIONID_FEATURE_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_WEBSHOPDATE_FEATURE_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_WEBSHOPID_FEATURE_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_VESTINGPERIODSTART_FEATURE_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_VESTINGPERIODEND_FEATURE_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_VERSION_FEATURE_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int CREDIT_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_CLASSIFIER_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_ADDITIONALINFO_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_ADDRESSFIRSTLINE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_BILLINGTYPE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_CUSTOMERREF_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_DEPOSIT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_DOCUMENTDATE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_DUEDAYS_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_INVOICEREFERENCE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_ITEMS_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_ITEMSREBATE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_MESSAGE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_MESSAGE2_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_MESSAGE3_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_NETGROSS_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_NOVATREFERENCE_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_ODTPATH_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_ORDERDATE_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_PAIDVALUE_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_PAID_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_PAYDATE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_PAYMENT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_PDFPATH_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_PRINTED_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_PRINTTEMPLATE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_PROGRESS_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_RECEIVER_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_SERVICEDATE_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_SHIPPING_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_SHIPPINGAUTOVAT_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_SHIPPINGVALUE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_SOURCEDOCUMENT_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_TARA_FEATURE_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_TOTALVALUE_FEATURE_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_TRANSACTIONID_FEATURE_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_WEBSHOPDATE_FEATURE_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_WEBSHOPID_FEATURE_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_VESTINGPERIODSTART_FEATURE_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_VESTINGPERIODEND_FEATURE_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_VERSION_FEATURE_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DELIVERY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_CLASSIFIER_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_ADDITIONALINFO_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_ADDRESSFIRSTLINE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_BILLINGTYPE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_CUSTOMERREF_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_DEPOSIT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_DOCUMENTDATE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_DUEDAYS_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_INVOICEREFERENCE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_ITEMS_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_ITEMSREBATE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_MESSAGE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_MESSAGE2_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_MESSAGE3_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_NETGROSS_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_NOVATREFERENCE_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_ODTPATH_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_ORDERDATE_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_PAIDVALUE_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_PAID_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_PAYDATE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_PAYMENT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_PDFPATH_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_PRINTED_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_PRINTTEMPLATE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_PROGRESS_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_RECEIVER_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_SERVICEDATE_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_SHIPPING_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_SHIPPINGAUTOVAT_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_SHIPPINGVALUE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_SOURCEDOCUMENT_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_TARA_FEATURE_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_TOTALVALUE_FEATURE_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_TRANSACTIONID_FEATURE_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_WEBSHOPDATE_FEATURE_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_WEBSHOPID_FEATURE_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_VESTINGPERIODSTART_FEATURE_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_VESTINGPERIODEND_FEATURE_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_VERSION_FEATURE_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DUNNING_DUNNINGLEVEL_FEATURE_ID = 47;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_CLASSIFIER_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_ALIAS_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_CATEGORIES_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_COMPANY_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_CUSTOMERNUMBER_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_TITLE_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_FIRSTNAME_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_GENDER_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_BIRTHDAY_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_ADDRESSES_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_DISCOUNT_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_PAYMENT_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_RELIABILITY_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_USENETGROSS_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_VATNUMBER_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_VATNUMBERVALID_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_WEBSITE_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_WEBSHOPNAME_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_SUPPLIERNUMBER_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_GLN_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_MANDATEREFERENCE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_BANKACCOUNT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_USESALESEQUALIZATIONTAX_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_NOTE_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_REGISTERNUMBER_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int DEBITOR_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_CLASSIFIER_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_ADDITIONALINFO_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_ADDRESSFIRSTLINE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_BILLINGTYPE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_CUSTOMERREF_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_DEPOSIT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_DOCUMENTDATE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_DUEDAYS_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_INVOICEREFERENCE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_ITEMS_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_ITEMSREBATE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_MESSAGE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_MESSAGE2_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_MESSAGE3_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_NETGROSS_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_NOVATREFERENCE_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_ODTPATH_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_ORDERDATE_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_PAIDVALUE_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_PAID_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_PAYDATE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_PAYMENT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_PDFPATH_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_PRINTED_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_PRINTTEMPLATE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_PROGRESS_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_RECEIVER_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_SERVICEDATE_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_SHIPPING_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_SHIPPINGAUTOVAT_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_SHIPPINGVALUE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_SOURCEDOCUMENT_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_TARA_FEATURE_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_TOTALVALUE_FEATURE_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_TRANSACTIONID_FEATURE_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_WEBSHOPDATE_FEATURE_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_WEBSHOPID_FEATURE_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_VESTINGPERIODSTART_FEATURE_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_VESTINGPERIODEND_FEATURE_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_VERSION_FEATURE_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INVOICE_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMLISTTYPECATEGORY_CLASSIFIER_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMLISTTYPECATEGORY_PARENT_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMLISTTYPECATEGORY_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMLISTTYPECATEGORY_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMLISTTYPECATEGORY_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMLISTTYPECATEGORY_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMLISTTYPECATEGORY_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMLISTTYPECATEGORY_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMLISTTYPECATEGORY_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ITEMLISTTYPECATEGORY_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_CLASSIFIER_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_ADDITIONALINFO_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_ADDRESSFIRSTLINE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_BILLINGTYPE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_CUSTOMERREF_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_DEPOSIT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_DOCUMENTDATE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_DUEDAYS_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_INVOICEREFERENCE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_ITEMS_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_ITEMSREBATE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_MESSAGE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_MESSAGE2_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_MESSAGE3_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_NETGROSS_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_NOVATREFERENCE_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_ODTPATH_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_ORDERDATE_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_PAIDVALUE_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_PAID_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_PAYDATE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_PAYMENT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_PDFPATH_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_PRINTED_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_PRINTTEMPLATE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_PROGRESS_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_RECEIVER_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_SERVICEDATE_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_SHIPPING_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_SHIPPINGAUTOVAT_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_SHIPPINGVALUE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_SOURCEDOCUMENT_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_TARA_FEATURE_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_TOTALVALUE_FEATURE_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_TRANSACTIONID_FEATURE_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_WEBSHOPDATE_FEATURE_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_WEBSHOPID_FEATURE_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_VESTINGPERIODSTART_FEATURE_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_VESTINGPERIODEND_FEATURE_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_VERSION_FEATURE_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int LETTER_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_CLASSIFIER_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_ADDITIONALINFO_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_ADDRESSFIRSTLINE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_BILLINGTYPE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_CUSTOMERREF_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_DEPOSIT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_DOCUMENTDATE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_DUEDAYS_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_INVOICEREFERENCE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_ITEMS_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_ITEMSREBATE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_MESSAGE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_MESSAGE2_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_MESSAGE3_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_NETGROSS_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_NOVATREFERENCE_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_ODTPATH_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_ORDERDATE_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_PAIDVALUE_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_PAID_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_PAYDATE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_PAYMENT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_PDFPATH_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_PRINTED_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_PRINTTEMPLATE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_PROGRESS_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_RECEIVER_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_SERVICEDATE_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_SHIPPING_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_SHIPPINGAUTOVAT_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_SHIPPINGVALUE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_SOURCEDOCUMENT_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_TARA_FEATURE_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_TOTALVALUE_FEATURE_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_TRANSACTIONID_FEATURE_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_WEBSHOPDATE_FEATURE_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_WEBSHOPID_FEATURE_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_VESTINGPERIODSTART_FEATURE_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_VESTINGPERIODEND_FEATURE_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_VERSION_FEATURE_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OFFER_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_CLASSIFIER_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_ADDITIONALINFO_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_ADDRESSFIRSTLINE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_BILLINGTYPE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_CUSTOMERREF_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_DEPOSIT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_DOCUMENTDATE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_DUEDAYS_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_INVOICEREFERENCE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_ITEMS_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_ITEMSREBATE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_MESSAGE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_MESSAGE2_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_MESSAGE3_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_NETGROSS_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_NOVATREFERENCE_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_ODTPATH_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_ORDERDATE_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_PAIDVALUE_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_PAID_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_PAYDATE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_PAYMENT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_PDFPATH_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_PRINTED_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_PRINTTEMPLATE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_PROGRESS_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_RECEIVER_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_SERVICEDATE_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_SHIPPING_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_SHIPPINGAUTOVAT_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_SHIPPINGVALUE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_SOURCEDOCUMENT_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_TARA_FEATURE_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_TOTALVALUE_FEATURE_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_TRANSACTIONID_FEATURE_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_WEBSHOPDATE_FEATURE_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_WEBSHOPID_FEATURE_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_VESTINGPERIODSTART_FEATURE_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_VESTINGPERIODEND_FEATURE_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_VERSION_FEATURE_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ORDER_VALIDTO_FEATURE_ID = 7;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_CLASSIFIER_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_ADDITIONALINFO_FEATURE_ID = 8;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_ADDRESSFIRSTLINE_FEATURE_ID = 9;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_BILLINGTYPE_FEATURE_ID = 10;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_CUSTOMERREF_FEATURE_ID = 11;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_DEPOSIT_FEATURE_ID = 12;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_DOCUMENTDATE_FEATURE_ID = 13;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_DUEDAYS_FEATURE_ID = 14;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_INVOICEREFERENCE_FEATURE_ID = 15;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_ITEMS_FEATURE_ID = 16;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_ITEMSREBATE_FEATURE_ID = 17;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_MESSAGE_FEATURE_ID = 18;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_MESSAGE2_FEATURE_ID = 19;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_MESSAGE3_FEATURE_ID = 20;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_NETGROSS_FEATURE_ID = 21;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_NOVATREFERENCE_FEATURE_ID = 22;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_ODTPATH_FEATURE_ID = 23;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_ORDERDATE_FEATURE_ID = 24;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_PAIDVALUE_FEATURE_ID = 25;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_PAID_FEATURE_ID = 26;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_PAYDATE_FEATURE_ID = 27;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_PAYMENT_FEATURE_ID = 28;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_PDFPATH_FEATURE_ID = 29;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_PRINTED_FEATURE_ID = 30;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_PRINTTEMPLATE_FEATURE_ID = 31;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_PROGRESS_FEATURE_ID = 32;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_RECEIVER_FEATURE_ID = 33;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_SERVICEDATE_FEATURE_ID = 34;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_SHIPPING_FEATURE_ID = 35;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_SHIPPINGAUTOVAT_FEATURE_ID = 36;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_SHIPPINGVALUE_FEATURE_ID = 37;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_SOURCEDOCUMENT_FEATURE_ID = 38;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_TARA_FEATURE_ID = 39;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_TOTALVALUE_FEATURE_ID = 40;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_TRANSACTIONID_FEATURE_ID = 41;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_WEBSHOPDATE_FEATURE_ID = 42;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_WEBSHOPID_FEATURE_ID = 43;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_VESTINGPERIODSTART_FEATURE_ID = 44;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_VESTINGPERIODEND_FEATURE_ID = 45;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_VERSION_FEATURE_ID = 46;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_NAME_FEATURE_ID = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_DATEADDED_FEATURE_ID = 1;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_MODIFIEDBY_FEATURE_ID = 2;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_MODIFIED_FEATURE_ID = 3;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_ID_FEATURE_ID = 4;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_DELETED_FEATURE_ID = 5;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_VALIDFROM_FEATURE_ID = 6;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROFORMA_VALIDTO_FEATURE_ID = 7;

    /**
     * The static member with the instance of this {@link ModelPackage}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final FakturamaModelPackage INSTANCE = initialize();

    /**
     * Initializes this {@link ModelPackage}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return an initialized instance of this class
     *
     * @generated
     */
    public static FakturamaModelPackage initialize() {

        if (isInitialized) {
            return (FakturamaModelPackage) ModelResolver.getInstance().getModelPackage(NS_URI);
        }

        final FakturamaModelPackage modelPackage = new FakturamaModelPackage();

        ModelResolver.getInstance().registerModelPackage(modelPackage);

        // read the model from the ecore file, the EPackage is registered in the EPackage.Registry
        // see the ModelResolver getEPackageRegistry method
        ModelUtils.readEPackagesFromFile(modelPackage);

        isInitialized = true;

        // force the initialization of the EFactory proxy
        modelPackage.getEPackage();

        // register the relation between a Class and its EClassifier
        ModelResolver.getInstance().registerClassModelMapping(AbstractCategory.class, modelPackage.getAbstractCategoryEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Voucher.class, modelPackage.getVoucherEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(VoucherCategory.class, modelPackage.getVoucherCategoryEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(VoucherItem.class, modelPackage.getVoucherItemEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(BankAccount.class, modelPackage.getBankAccountEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(IEntity.class, modelPackage.getIEntityEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Address.class, modelPackage.getAddressEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Contact.class, modelPackage.getContactEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Creditor.class, modelPackage.getCreditorEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(ContactCategory.class, modelPackage.getContactCategoryEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Document.class, modelPackage.getDocumentEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(DocumentReceiver.class, modelPackage.getDocumentReceiverEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(DocumentItem.class, modelPackage.getDocumentItemEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(IndividualDocumentInfo.class, modelPackage.getIndividualDocumentInfoEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(IDescribableEntity.class, modelPackage.getIDescribableEntityEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(ItemAccountType.class, modelPackage.getItemAccountTypeEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Payment.class, modelPackage.getPaymentEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Product.class, modelPackage.getProductEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(ProductCategory.class, modelPackage.getProductCategoryEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(ProductOptions.class, modelPackage.getProductOptionsEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(ProductBlockPrice.class, modelPackage.getProductBlockPriceEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Role.class, modelPackage.getRoleEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Tenant.class, modelPackage.getTenantEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Shipping.class, modelPackage.getShippingEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(ShippingCategory.class, modelPackage.getShippingCategoryEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(TextCategory.class, modelPackage.getTextCategoryEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(TextModule.class, modelPackage.getTextModuleEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(User.class, modelPackage.getUserEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(UserProperty.class, modelPackage.getUserPropertyEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(VAT.class, modelPackage.getVATEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(VATCategory.class, modelPackage.getVATCategoryEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(CEFACTCode.class, modelPackage.getCEFACTCodeEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(WebShop.class, modelPackage.getWebShopEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(WebshopStateMapping.class, modelPackage.getWebshopStateMappingEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Confirmation.class, modelPackage.getConfirmationEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Credit.class, modelPackage.getCreditEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Delivery.class, modelPackage.getDeliveryEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Dunning.class, modelPackage.getDunningEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Debitor.class, modelPackage.getDebitorEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Invoice.class, modelPackage.getInvoiceEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(ItemListTypeCategory.class, modelPackage.getItemListTypeCategoryEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Letter.class, modelPackage.getLetterEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Offer.class, modelPackage.getOfferEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Order.class, modelPackage.getOrderEClass(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(Proforma.class, modelPackage.getProformaEClass(), modelPackage);

        ModelResolver.getInstance().registerClassModelMapping(ItemType.class, modelPackage.getItemTypeEEnum(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(BillingType.class, modelPackage.getBillingTypeEEnum(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(VoucherType.class, modelPackage.getVoucherTypeEEnum(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(ContactType.class, modelPackage.getContactTypeEEnum(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(ReliabilityType.class, modelPackage.getReliabilityTypeEEnum(), modelPackage);
        ModelResolver.getInstance().registerClassModelMapping(ShippingVatType.class, modelPackage.getShippingVatTypeEEnum(), modelPackage);

        // and return ourselves
        return modelPackage;
    }

    /**
     * Returns the {@link ModelFactory} of this ModelPackage. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the {@link FakturamaModelFactory} instance.
     * @generated
     */
    @Override
    public FakturamaModelFactory getModelFactory() {
        return MODELFACTORY;
    }

    /**
     * Returns the nsUri of the {@link EPackage} managed by this Package
     * instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the nsUri of the EPackage
     * @generated
     */
    @Override
    public String getNsURI() {
        return NS_URI;
    }

    /**
     * Returns the name of the ecore file containing the ecore model of the
     * {@link EPackage} managed here. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the name of the ecore file
     * @generated
     */
    @Override
    public String getEcoreFileName() {
        return "model.ecore";
    }

    /**
     * Returns the {@link EClass} '<em><b>AbstractCategory</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>AbstractCategory</b></em>'
     * @generated
     */
    public EClass getAbstractCategoryEClass() {
        return (EClass) getEPackage().getEClassifiers().get(ABSTRACTCATEGORY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>AbstractCategory.parent</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>AbstractCategory.parent</b></em>'.
     * @generated
     */
    public EReference getAbstractCategory_Parent() {
        return (EReference) getAbstractCategoryEClass().getEAllStructuralFeatures().get(ABSTRACTCATEGORY_PARENT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>AbstractCategory.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>AbstractCategory.name</b></em>'.
     * @generated
     */
    public EAttribute getAbstractCategory_Name() {
        return (EAttribute) getAbstractCategoryEClass().getEAllStructuralFeatures().get(ABSTRACTCATEGORY_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>AbstractCategory.dateAdded</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>AbstractCategory.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getAbstractCategory_DateAdded() {
        return (EAttribute) getAbstractCategoryEClass().getEAllStructuralFeatures().get(ABSTRACTCATEGORY_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>AbstractCategory.modifiedBy</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>AbstractCategory.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getAbstractCategory_ModifiedBy() {
        return (EAttribute) getAbstractCategoryEClass().getEAllStructuralFeatures().get(ABSTRACTCATEGORY_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>AbstractCategory.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>AbstractCategory.modified</b></em>'.
     * @generated
     */
    public EAttribute getAbstractCategory_Modified() {
        return (EAttribute) getAbstractCategoryEClass().getEAllStructuralFeatures().get(ABSTRACTCATEGORY_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>AbstractCategory.id</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>AbstractCategory.id</b></em>'.
     * @generated
     */
    public EAttribute getAbstractCategory_Id() {
        return (EAttribute) getAbstractCategoryEClass().getEAllStructuralFeatures().get(ABSTRACTCATEGORY_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>AbstractCategory.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>AbstractCategory.deleted</b></em>'.
     * @generated
     */
    public EAttribute getAbstractCategory_Deleted() {
        return (EAttribute) getAbstractCategoryEClass().getEAllStructuralFeatures().get(ABSTRACTCATEGORY_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>AbstractCategory.validFrom</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>AbstractCategory.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getAbstractCategory_ValidFrom() {
        return (EAttribute) getAbstractCategoryEClass().getEAllStructuralFeatures().get(ABSTRACTCATEGORY_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>AbstractCategory.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>AbstractCategory.validTo</b></em>'.
     * @generated
     */
    public EAttribute getAbstractCategory_ValidTo() {
        return (EAttribute) getAbstractCategoryEClass().getEAllStructuralFeatures().get(ABSTRACTCATEGORY_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Voucher</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Voucher</b></em>'
     * @generated
     */
    public EClass getVoucherEClass() {
        return (EClass) getEPackage().getEClassifiers().get(VOUCHER_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Voucher.account</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.account</b></em>'.
     * @generated
     */
    public EReference getVoucher_Account() {
        return (EReference) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_ACCOUNT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.voucherDate</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.voucherDate</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_VoucherDate() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_VOUCHERDATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.discounted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.discounted</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_Discounted() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_DISCOUNTED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.doNotBook</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.doNotBook</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_DoNotBook() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_DONOTBOOK_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.documentNumber</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.documentNumber</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_DocumentNumber() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_DOCUMENTNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Voucher.items</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.items</b></em>'.
     * @generated
     */
    public EReference getVoucher_Items() {
        return (EReference) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_ITEMS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.voucherNumber</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.voucherNumber</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_VoucherNumber() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_VOUCHERNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.paidValue</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.paidValue</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_PaidValue() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_PAIDVALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.totalValue</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.totalValue</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_TotalValue() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_TOTALVALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.voucherType</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.voucherType</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_VoucherType() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_VOUCHERTYPE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Voucher.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.name</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_Name() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_DateAdded() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_ModifiedBy() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.modified</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_Modified() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Voucher.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.id</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_Id() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Voucher.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.deleted</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_Deleted() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Voucher.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_ValidFrom() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Voucher.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Voucher.validTo</b></em>'.
     * @generated
     */
    public EAttribute getVoucher_ValidTo() {
        return (EAttribute) getVoucherEClass().getEAllStructuralFeatures().get(VOUCHER_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>VoucherCategory</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>VoucherCategory</b></em>'
     * @generated
     */
    public EClass getVoucherCategoryEClass() {
        return (EClass) getEPackage().getEClassifiers().get(VOUCHERCATEGORY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>VoucherItem</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>VoucherItem</b></em>'
     * @generated
     */
    public EClass getVoucherItemEClass() {
        return (EClass) getEPackage().getEClassifiers().get(VOUCHERITEM_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.accountType</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.accountType</b></em>'.
     * @generated
     */
    public EReference getVoucherItem_AccountType() {
        return (EReference) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_ACCOUNTTYPE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VoucherItem.vat</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.vat</b></em>'.
     * @generated
     */
    public EReference getVoucherItem_Vat() {
        return (EReference) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_VAT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.price</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.price</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_Price() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_PRICE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.posNr</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.posNr</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_PosNr() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_POSNR_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.itemVoucherType</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.itemVoucherType</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_ItemVoucherType() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_ITEMVOUCHERTYPE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.name</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_Name() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_DateAdded() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_ModifiedBy() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.modified</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_Modified() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VoucherItem.id</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.id</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_Id() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.deleted</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_Deleted() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_ValidFrom() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VoucherItem.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VoucherItem.validTo</b></em>'.
     * @generated
     */
    public EAttribute getVoucherItem_ValidTo() {
        return (EAttribute) getVoucherItemEClass().getEAllStructuralFeatures().get(VOUCHERITEM_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>BankAccount</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>BankAccount</b></em>'
     * @generated
     */
    public EClass getBankAccountEClass() {
        return (EClass) getEPackage().getEClassifiers().get(BANKACCOUNT_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.accountHolder</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.accountHolder</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_AccountHolder() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_ACCOUNTHOLDER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.bankCode</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.bankCode</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_BankCode() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_BANKCODE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.bankName</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.bankName</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_BankName() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_BANKNAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>BankAccount.bic</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.bic</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_Bic() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_BIC_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.iban</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.iban</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_Iban() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_IBAN_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.name</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_Name() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_DateAdded() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_ModifiedBy() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.modified</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_Modified() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>BankAccount.id</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.id</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_Id() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.deleted</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_Deleted() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_ValidFrom() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>BankAccount.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>BankAccount.validTo</b></em>'.
     * @generated
     */
    public EAttribute getBankAccount_ValidTo() {
        return (EAttribute) getBankAccountEClass().getEAllStructuralFeatures().get(BANKACCOUNT_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>IEntity</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>IEntity</b></em>'
     * @generated
     */
    public EClass getIEntityEClass() {
        return (EClass) getEPackage().getEClassifiers().get(IENTITY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>IEntity.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IEntity.name</b></em>'.
     * @generated
     */
    public EAttribute getIEntity_Name() {
        return (EAttribute) getIEntityEClass().getEAllStructuralFeatures().get(IENTITY_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IEntity.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IEntity.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getIEntity_DateAdded() {
        return (EAttribute) getIEntityEClass().getEAllStructuralFeatures().get(IENTITY_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IEntity.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IEntity.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getIEntity_ModifiedBy() {
        return (EAttribute) getIEntityEClass().getEAllStructuralFeatures().get(IENTITY_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IEntity.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IEntity.modified</b></em>'.
     * @generated
     */
    public EAttribute getIEntity_Modified() {
        return (EAttribute) getIEntityEClass().getEAllStructuralFeatures().get(IENTITY_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>IEntity.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IEntity.id</b></em>'.
     * @generated
     */
    public EAttribute getIEntity_Id() {
        return (EAttribute) getIEntityEClass().getEAllStructuralFeatures().get(IENTITY_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>IEntity.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IEntity.deleted</b></em>'.
     * @generated
     */
    public EAttribute getIEntity_Deleted() {
        return (EAttribute) getIEntityEClass().getEAllStructuralFeatures().get(IENTITY_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IEntity.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IEntity.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getIEntity_ValidFrom() {
        return (EAttribute) getIEntityEClass().getEAllStructuralFeatures().get(IENTITY_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>IEntity.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IEntity.validTo</b></em>'.
     * @generated
     */
    public EAttribute getIEntity_ValidTo() {
        return (EAttribute) getIEntityEClass().getEAllStructuralFeatures().get(IENTITY_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Address</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Address</b></em>'
     * @generated
     */
    public EClass getAddressEClass() {
        return (EClass) getEPackage().getEClassifiers().get(ADDRESS_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.street</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.street</b></em>'.
     * @generated
     */
    public EAttribute getAddress_Street() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_STREET_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Address.cityAddon</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.cityAddon</b></em>'.
     * @generated
     */
    public EAttribute getAddress_CityAddon() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_CITYADDON_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.city</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.city</b></em>'.
     * @generated
     */
    public EAttribute getAddress_City() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_CITY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.zip</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.zip</b></em>'.
     * @generated
     */
    public EAttribute getAddress_Zip() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_ZIP_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Address.countryCode</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.countryCode</b></em>'.
     * @generated
     */
    public EAttribute getAddress_CountryCode() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_COUNTRYCODE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.contact</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.contact</b></em>'.
     * @generated
     */
    public EReference getAddress_Contact() {
        return (EReference) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_CONTACT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Address.localConsultant</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.localConsultant</b></em>'.
     * @generated
     */
    public EAttribute getAddress_LocalConsultant() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_LOCALCONSULTANT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.email</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.email</b></em>'.
     * @generated
     */
    public EAttribute getAddress_Email() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_EMAIL_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.mobile</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.mobile</b></em>'.
     * @generated
     */
    public EAttribute getAddress_Mobile() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_MOBILE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.phone</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.phone</b></em>'.
     * @generated
     */
    public EAttribute getAddress_Phone() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_PHONE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Address.additionalPhone</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.additionalPhone</b></em>'.
     * @generated
     */
    public EAttribute getAddress_AdditionalPhone() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_ADDITIONALPHONE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.fax</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.fax</b></em>'.
     * @generated
     */
    public EAttribute getAddress_Fax() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_FAX_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Address.contactTypes</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.contactTypes</b></em>'.
     * @generated
     */
    public EAttribute getAddress_ContactTypes() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_CONTACTTYPES_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Address.addressAddon</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.addressAddon</b></em>'.
     * @generated
     */
    public EAttribute getAddress_AddressAddon() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_ADDRESSADDON_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.name</b></em>'.
     * @generated
     */
    public EAttribute getAddress_Name() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Address.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getAddress_DateAdded() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Address.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getAddress_ModifiedBy() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Address.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.modified</b></em>'.
     * @generated
     */
    public EAttribute getAddress_Modified() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.id</b></em>'.
     * @generated
     */
    public EAttribute getAddress_Id() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.deleted</b></em>'.
     * @generated
     */
    public EAttribute getAddress_Deleted() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Address.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getAddress_ValidFrom() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Address.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Address.validTo</b></em>'.
     * @generated
     */
    public EAttribute getAddress_ValidTo() {
        return (EAttribute) getAddressEClass().getEAllStructuralFeatures().get(ADDRESS_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Contact</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Contact</b></em>'
     * @generated
     */
    public EClass getContactEClass() {
        return (EClass) getEPackage().getEClassifiers().get(CONTACT_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.alias</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.alias</b></em>'.
     * @generated
     */
    public EAttribute getContact_Alias() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_ALIAS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.categories</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.categories</b></em>'.
     * @generated
     */
    public EReference getContact_Categories() {
        return (EReference) getContactEClass().getEAllStructuralFeatures().get(CONTACT_CATEGORIES_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.company</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.company</b></em>'.
     * @generated
     */
    public EAttribute getContact_Company() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_COMPANY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.customerNumber</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.customerNumber</b></em>'.
     * @generated
     */
    public EAttribute getContact_CustomerNumber() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_CUSTOMERNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.title</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.title</b></em>'.
     * @generated
     */
    public EAttribute getContact_Title() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_TITLE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.firstName</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.firstName</b></em>'.
     * @generated
     */
    public EAttribute getContact_FirstName() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_FIRSTNAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.gender</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.gender</b></em>'.
     * @generated
     */
    public EAttribute getContact_Gender() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_GENDER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.birthday</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.birthday</b></em>'.
     * @generated
     */
    public EAttribute getContact_Birthday() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_BIRTHDAY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.addresses</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.addresses</b></em>'.
     * @generated
     */
    public EReference getContact_Addresses() {
        return (EReference) getContactEClass().getEAllStructuralFeatures().get(CONTACT_ADDRESSES_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.discount</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.discount</b></em>'.
     * @generated
     */
    public EAttribute getContact_Discount() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_DISCOUNT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.payment</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.payment</b></em>'.
     * @generated
     */
    public EReference getContact_Payment() {
        return (EReference) getContactEClass().getEAllStructuralFeatures().get(CONTACT_PAYMENT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.reliability</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.reliability</b></em>'.
     * @generated
     */
    public EAttribute getContact_Reliability() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_RELIABILITY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.useNetGross</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.useNetGross</b></em>'.
     * @generated
     */
    public EAttribute getContact_UseNetGross() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_USENETGROSS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.vatNumber</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.vatNumber</b></em>'.
     * @generated
     */
    public EAttribute getContact_VatNumber() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_VATNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.vatNumberValid</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.vatNumberValid</b></em>'.
     * @generated
     */
    public EAttribute getContact_VatNumberValid() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_VATNUMBERVALID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.website</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.website</b></em>'.
     * @generated
     */
    public EAttribute getContact_Website() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_WEBSITE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.webshopName</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.webshopName</b></em>'.
     * @generated
     */
    public EAttribute getContact_WebshopName() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_WEBSHOPNAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.supplierNumber</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.supplierNumber</b></em>'.
     * @generated
     */
    public EAttribute getContact_SupplierNumber() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_SUPPLIERNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.gln</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.gln</b></em>'.
     * @generated
     */
    public EAttribute getContact_Gln() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_GLN_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.mandateReference</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.mandateReference</b></em>'.
     * @generated
     */
    public EAttribute getContact_MandateReference() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_MANDATEREFERENCE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.bankAccount</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.bankAccount</b></em>'.
     * @generated
     */
    public EReference getContact_BankAccount() {
        return (EReference) getContactEClass().getEAllStructuralFeatures().get(CONTACT_BANKACCOUNT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.useSalesEqualizationTax</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.useSalesEqualizationTax</b></em>'.
     * @generated
     */
    public EAttribute getContact_UseSalesEqualizationTax() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_USESALESEQUALIZATIONTAX_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.note</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.note</b></em>'.
     * @generated
     */
    public EAttribute getContact_Note() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_NOTE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.registerNumber</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.registerNumber</b></em>'.
     * @generated
     */
    public EAttribute getContact_RegisterNumber() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_REGISTERNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.name</b></em>'.
     * @generated
     */
    public EAttribute getContact_Name() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getContact_DateAdded() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getContact_ModifiedBy() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.modified</b></em>'.
     * @generated
     */
    public EAttribute getContact_Modified() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.id</b></em>'.
     * @generated
     */
    public EAttribute getContact_Id() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.deleted</b></em>'.
     * @generated
     */
    public EAttribute getContact_Deleted() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Contact.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getContact_ValidFrom() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Contact.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Contact.validTo</b></em>'.
     * @generated
     */
    public EAttribute getContact_ValidTo() {
        return (EAttribute) getContactEClass().getEAllStructuralFeatures().get(CONTACT_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Creditor</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Creditor</b></em>'
     * @generated
     */
    public EClass getCreditorEClass() {
        return (EClass) getEPackage().getEClassifiers().get(CREDITOR_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>ContactCategory</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>ContactCategory</b></em>'
     * @generated
     */
    public EClass getContactCategoryEClass() {
        return (EClass) getEPackage().getEClassifiers().get(CONTACTCATEGORY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Document</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Document</b></em>'
     * @generated
     */
    public EClass getDocumentEClass() {
        return (EClass) getEPackage().getEClassifiers().get(DOCUMENT_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.additionalInfo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.additionalInfo</b></em>'.
     * @generated
     */
    public EReference getDocument_AdditionalInfo() {
        return (EReference) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_ADDITIONALINFO_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.addressFirstLine</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.addressFirstLine</b></em>'.
     * @generated
     */
    public EAttribute getDocument_AddressFirstLine() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_ADDRESSFIRSTLINE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.billingType</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.billingType</b></em>'.
     * @generated
     */
    public EAttribute getDocument_BillingType() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_BILLINGTYPE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.customerRef</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.customerRef</b></em>'.
     * @generated
     */
    public EAttribute getDocument_CustomerRef() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_CUSTOMERREF_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.deposit</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.deposit</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Deposit() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_DEPOSIT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.documentDate</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.documentDate</b></em>'.
     * @generated
     */
    public EAttribute getDocument_DocumentDate() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_DOCUMENTDATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.dueDays</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.dueDays</b></em>'.
     * @generated
     */
    public EAttribute getDocument_DueDays() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_DUEDAYS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.invoiceReference</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.invoiceReference</b></em>'.
     * @generated
     */
    public EReference getDocument_InvoiceReference() {
        return (EReference) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_INVOICEREFERENCE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Document.items</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.items</b></em>'.
     * @generated
     */
    public EReference getDocument_Items() {
        return (EReference) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_ITEMS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.itemsRebate</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.itemsRebate</b></em>'.
     * @generated
     */
    public EAttribute getDocument_ItemsRebate() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_ITEMSREBATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.message</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.message</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Message() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_MESSAGE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.message2</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.message2</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Message2() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_MESSAGE2_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.message3</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.message3</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Message3() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_MESSAGE3_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.netGross</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.netGross</b></em>'.
     * @generated
     */
    public EAttribute getDocument_NetGross() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_NETGROSS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.noVatReference</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.noVatReference</b></em>'.
     * @generated
     */
    public EReference getDocument_NoVatReference() {
        return (EReference) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_NOVATREFERENCE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.odtPath</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.odtPath</b></em>'.
     * @generated
     */
    public EAttribute getDocument_OdtPath() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_ODTPATH_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.orderDate</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.orderDate</b></em>'.
     * @generated
     */
    public EAttribute getDocument_OrderDate() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_ORDERDATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.paidValue</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.paidValue</b></em>'.
     * @generated
     */
    public EAttribute getDocument_PaidValue() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_PAIDVALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Document.paid</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.paid</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Paid() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_PAID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.payDate</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.payDate</b></em>'.
     * @generated
     */
    public EAttribute getDocument_PayDate() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_PAYDATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.payment</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.payment</b></em>'.
     * @generated
     */
    public EReference getDocument_Payment() {
        return (EReference) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_PAYMENT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.pdfPath</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.pdfPath</b></em>'.
     * @generated
     */
    public EAttribute getDocument_PdfPath() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_PDFPATH_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.printed</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.printed</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Printed() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_PRINTED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.printTemplate</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.printTemplate</b></em>'.
     * @generated
     */
    public EAttribute getDocument_PrintTemplate() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_PRINTTEMPLATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.progress</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.progress</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Progress() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_PROGRESS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.receiver</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.receiver</b></em>'.
     * @generated
     */
    public EReference getDocument_Receiver() {
        return (EReference) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_RECEIVER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.serviceDate</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.serviceDate</b></em>'.
     * @generated
     */
    public EAttribute getDocument_ServiceDate() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_SERVICEDATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.shipping</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.shipping</b></em>'.
     * @generated
     */
    public EReference getDocument_Shipping() {
        return (EReference) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_SHIPPING_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.shippingAutoVat</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.shippingAutoVat</b></em>'.
     * @generated
     */
    public EAttribute getDocument_ShippingAutoVat() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_SHIPPINGAUTOVAT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.shippingValue</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.shippingValue</b></em>'.
     * @generated
     */
    public EAttribute getDocument_ShippingValue() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_SHIPPINGVALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.sourceDocument</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.sourceDocument</b></em>'.
     * @generated
     */
    public EReference getDocument_SourceDocument() {
        return (EReference) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_SOURCEDOCUMENT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Document.tara</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.tara</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Tara() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_TARA_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.totalValue</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.totalValue</b></em>'.
     * @generated
     */
    public EAttribute getDocument_TotalValue() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_TOTALVALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.transactionId</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.transactionId</b></em>'.
     * @generated
     */
    public EAttribute getDocument_TransactionId() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_TRANSACTIONID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.webshopDate</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.webshopDate</b></em>'.
     * @generated
     */
    public EAttribute getDocument_WebshopDate() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_WEBSHOPDATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.webshopId</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.webshopId</b></em>'.
     * @generated
     */
    public EAttribute getDocument_WebshopId() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_WEBSHOPID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.vestingPeriodStart</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.vestingPeriodStart</b></em>'.
     * @generated
     */
    public EAttribute getDocument_VestingPeriodStart() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_VESTINGPERIODSTART_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.vestingPeriodEnd</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.vestingPeriodEnd</b></em>'.
     * @generated
     */
    public EAttribute getDocument_VestingPeriodEnd() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_VESTINGPERIODEND_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.version</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.version</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Version() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_VERSION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Document.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.name</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Name() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getDocument_DateAdded() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getDocument_ModifiedBy() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.modified</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Modified() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Document.id</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.id</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Id() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.deleted</b></em>'.
     * @generated
     */
    public EAttribute getDocument_Deleted() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getDocument_ValidFrom() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Document.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Document.validTo</b></em>'.
     * @generated
     */
    public EAttribute getDocument_ValidTo() {
        return (EAttribute) getDocumentEClass().getEAllStructuralFeatures().get(DOCUMENT_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>DocumentReceiver</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>DocumentReceiver</b></em>'
     * @generated
     */
    public EClass getDocumentReceiverEClass() {
        return (EClass) getEPackage().getEClassifiers().get(DOCUMENTRECEIVER_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.alias</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.alias</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Alias() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_ALIAS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.customerNumber</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.customerNumber</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_CustomerNumber() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_CUSTOMERNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.consultant</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.consultant</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Consultant() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_CONSULTANT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.company</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.company</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Company() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_COMPANY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.title</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.title</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Title() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_TITLE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.firstName</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.firstName</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_FirstName() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_FIRSTNAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.street</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.street</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Street() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_STREET_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.cityAddon</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.cityAddon</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_CityAddon() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_CITYADDON_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.city</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.city</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_City() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_CITY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.zip</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.zip</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Zip() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_ZIP_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.countryCode</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.countryCode</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_CountryCode() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_COUNTRYCODE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.manualAddress</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.manualAddress</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_ManualAddress() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_MANUALADDRESS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.billingType</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.billingType</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_BillingType() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_BILLINGTYPE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.originContactId</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.originContactId</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_OriginContactId() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_ORIGINCONTACTID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.originAddressId</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.originAddressId</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_OriginAddressId() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_ORIGINADDRESSID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.gender</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.gender</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Gender() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_GENDER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.email</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.email</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Email() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_EMAIL_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.mobile</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.mobile</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Mobile() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_MOBILE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.phone</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.phone</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Phone() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_PHONE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.fax</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.fax</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Fax() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_FAX_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.supplierNumber</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.supplierNumber</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_SupplierNumber() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_SUPPLIERNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.gln</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.gln</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Gln() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_GLN_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.mandateReference</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.mandateReference</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_MandateReference() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_MANDATEREFERENCE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.vatNumber</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.vatNumber</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_VatNumber() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_VATNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.description</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.description</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Description() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_DESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.name</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Name() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.dateAdded</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_DateAdded() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.modifiedBy</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_ModifiedBy() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.modified</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Modified() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.id</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.id</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Id() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.deleted</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_Deleted() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.validFrom</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_ValidFrom() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentReceiver.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentReceiver.validTo</b></em>'.
     * @generated
     */
    public EAttribute getDocumentReceiver_ValidTo() {
        return (EAttribute) getDocumentReceiverEClass().getEAllStructuralFeatures().get(DOCUMENTRECEIVER_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>DocumentItem</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>DocumentItem</b></em>'
     * @generated
     */
    public EClass getDocumentItemEClass() {
        return (EClass) getEPackage().getEClassifiers().get(DOCUMENTITEM_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.noVat</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.noVat</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_NoVat() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_NOVAT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.itemRebate</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.itemRebate</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_ItemRebate() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_ITEMREBATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.itemNumber</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.itemNumber</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_ItemNumber() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_ITEMNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.product</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.product</b></em>'.
     * @generated
     */
    public EReference getDocumentItem_Product() {
        return (EReference) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_PRODUCT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.quantity</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.quantity</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Quantity() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_QUANTITY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.originQuantity</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.originQuantity</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_OriginQuantity() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_ORIGINQUANTITY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.weight</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.weight</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Weight() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_WEIGHT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.optional</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.optional</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Optional() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_OPTIONAL_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.picture</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.picture</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Picture() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_PICTURE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.price</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.price</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Price() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_PRICE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.quantityUnit</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.quantityUnit</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_QuantityUnit() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_QUANTITYUNIT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.itemVat</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.itemVat</b></em>'.
     * @generated
     */
    public EReference getDocumentItem_ItemVat() {
        return (EReference) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_ITEMVAT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.gtin</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.gtin</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Gtin() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_GTIN_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.posNr</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.posNr</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_PosNr() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_POSNR_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.itemType</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.itemType</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_ItemType() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_ITEMTYPE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.vestingPeriodStart</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.vestingPeriodStart</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_VestingPeriodStart() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_VESTINGPERIODSTART_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.vestingPeriodEnd</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.vestingPeriodEnd</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_VestingPeriodEnd() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_VESTINGPERIODEND_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.supplierItemNumber</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.supplierItemNumber</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_SupplierItemNumber() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_SUPPLIERITEMNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.description</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.description</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Description() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_DESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.name</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Name() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_DateAdded() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_ModifiedBy() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.modified</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Modified() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>DocumentItem.id</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.id</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Id() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.deleted</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_Deleted() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_ValidFrom() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>DocumentItem.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>DocumentItem.validTo</b></em>'.
     * @generated
     */
    public EAttribute getDocumentItem_ValidTo() {
        return (EAttribute) getDocumentItemEClass().getEAllStructuralFeatures().get(DOCUMENTITEM_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>IndividualDocumentInfo</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>IndividualDocumentInfo</b></em>'
     * @generated
     */
    public EClass getIndividualDocumentInfoEClass() {
        return (EClass) getEPackage().getEClassifiers().get(INDIVIDUALDOCUMENTINFO_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.paymentName</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.paymentName</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_PaymentName() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_PAYMENTNAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.paymentText</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.paymentText</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_PaymentText() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_PAYMENTTEXT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.paymentDescription</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.paymentDescription</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_PaymentDescription() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_PAYMENTDESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.shippingName</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.shippingName</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_ShippingName() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_SHIPPINGNAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.shippingDescription</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.shippingDescription</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_ShippingDescription() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_SHIPPINGDESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.shippingVatDescription</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.shippingVatDescription</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_ShippingVatDescription() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_SHIPPINGVATDESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.shippingVatValue</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.shippingVatValue</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_ShippingVatValue() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_SHIPPINGVATVALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.noVatName</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.noVatName</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_NoVatName() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_NOVATNAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.noVatDescription</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.noVatDescription</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_NoVatDescription() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_NOVATDESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.name</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.name</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_Name() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.dateAdded</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_DateAdded() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.modifiedBy</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_ModifiedBy() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.modified</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.modified</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_Modified() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.id</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.id</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_Id() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.deleted</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.deleted</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_Deleted() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.validFrom</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_ValidFrom() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IndividualDocumentInfo.validTo</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IndividualDocumentInfo.validTo</b></em>'.
     * @generated
     */
    public EAttribute getIndividualDocumentInfo_ValidTo() {
        return (EAttribute) getIndividualDocumentInfoEClass().getEAllStructuralFeatures().get(INDIVIDUALDOCUMENTINFO_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>IDescribableEntity</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>IDescribableEntity</b></em>'
     * @generated
     */
    public EClass getIDescribableEntityEClass() {
        return (EClass) getEPackage().getEClassifiers().get(IDESCRIBABLEENTITY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IDescribableEntity.description</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IDescribableEntity.description</b></em>'.
     * @generated
     */
    public EAttribute getIDescribableEntity_Description() {
        return (EAttribute) getIDescribableEntityEClass().getEAllStructuralFeatures().get(IDESCRIBABLEENTITY_DESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IDescribableEntity.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IDescribableEntity.name</b></em>'.
     * @generated
     */
    public EAttribute getIDescribableEntity_Name() {
        return (EAttribute) getIDescribableEntityEClass().getEAllStructuralFeatures().get(IDESCRIBABLEENTITY_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IDescribableEntity.dateAdded</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IDescribableEntity.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getIDescribableEntity_DateAdded() {
        return (EAttribute) getIDescribableEntityEClass().getEAllStructuralFeatures().get(IDESCRIBABLEENTITY_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IDescribableEntity.modifiedBy</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IDescribableEntity.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getIDescribableEntity_ModifiedBy() {
        return (EAttribute) getIDescribableEntityEClass().getEAllStructuralFeatures().get(IDESCRIBABLEENTITY_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IDescribableEntity.modified</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IDescribableEntity.modified</b></em>'.
     * @generated
     */
    public EAttribute getIDescribableEntity_Modified() {
        return (EAttribute) getIDescribableEntityEClass().getEAllStructuralFeatures().get(IDESCRIBABLEENTITY_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IDescribableEntity.id</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IDescribableEntity.id</b></em>'.
     * @generated
     */
    public EAttribute getIDescribableEntity_Id() {
        return (EAttribute) getIDescribableEntityEClass().getEAllStructuralFeatures().get(IDESCRIBABLEENTITY_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IDescribableEntity.deleted</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IDescribableEntity.deleted</b></em>'.
     * @generated
     */
    public EAttribute getIDescribableEntity_Deleted() {
        return (EAttribute) getIDescribableEntityEClass().getEAllStructuralFeatures().get(IDESCRIBABLEENTITY_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IDescribableEntity.validFrom</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IDescribableEntity.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getIDescribableEntity_ValidFrom() {
        return (EAttribute) getIDescribableEntityEClass().getEAllStructuralFeatures().get(IDESCRIBABLEENTITY_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>IDescribableEntity.validTo</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>IDescribableEntity.validTo</b></em>'.
     * @generated
     */
    public EAttribute getIDescribableEntity_ValidTo() {
        return (EAttribute) getIDescribableEntityEClass().getEAllStructuralFeatures().get(IDESCRIBABLEENTITY_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>ItemAccountType</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>ItemAccountType</b></em>'
     * @generated
     */
    public EClass getItemAccountTypeEClass() {
        return (EClass) getEPackage().getEClassifiers().get(ITEMACCOUNTTYPE_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ItemAccountType.value</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ItemAccountType.value</b></em>'.
     * @generated
     */
    public EAttribute getItemAccountType_Value() {
        return (EAttribute) getItemAccountTypeEClass().getEAllStructuralFeatures().get(ITEMACCOUNTTYPE_VALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ItemAccountType.category</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ItemAccountType.category</b></em>'.
     * @generated
     */
    public EReference getItemAccountType_Category() {
        return (EReference) getItemAccountTypeEClass().getEAllStructuralFeatures().get(ITEMACCOUNTTYPE_CATEGORY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ItemAccountType.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ItemAccountType.name</b></em>'.
     * @generated
     */
    public EAttribute getItemAccountType_Name() {
        return (EAttribute) getItemAccountTypeEClass().getEAllStructuralFeatures().get(ITEMACCOUNTTYPE_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ItemAccountType.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ItemAccountType.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getItemAccountType_DateAdded() {
        return (EAttribute) getItemAccountTypeEClass().getEAllStructuralFeatures().get(ITEMACCOUNTTYPE_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ItemAccountType.modifiedBy</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ItemAccountType.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getItemAccountType_ModifiedBy() {
        return (EAttribute) getItemAccountTypeEClass().getEAllStructuralFeatures().get(ITEMACCOUNTTYPE_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ItemAccountType.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ItemAccountType.modified</b></em>'.
     * @generated
     */
    public EAttribute getItemAccountType_Modified() {
        return (EAttribute) getItemAccountTypeEClass().getEAllStructuralFeatures().get(ITEMACCOUNTTYPE_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ItemAccountType.id</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ItemAccountType.id</b></em>'.
     * @generated
     */
    public EAttribute getItemAccountType_Id() {
        return (EAttribute) getItemAccountTypeEClass().getEAllStructuralFeatures().get(ITEMACCOUNTTYPE_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ItemAccountType.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ItemAccountType.deleted</b></em>'.
     * @generated
     */
    public EAttribute getItemAccountType_Deleted() {
        return (EAttribute) getItemAccountTypeEClass().getEAllStructuralFeatures().get(ITEMACCOUNTTYPE_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ItemAccountType.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ItemAccountType.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getItemAccountType_ValidFrom() {
        return (EAttribute) getItemAccountTypeEClass().getEAllStructuralFeatures().get(ITEMACCOUNTTYPE_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ItemAccountType.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ItemAccountType.validTo</b></em>'.
     * @generated
     */
    public EAttribute getItemAccountType_ValidTo() {
        return (EAttribute) getItemAccountTypeEClass().getEAllStructuralFeatures().get(ITEMACCOUNTTYPE_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Payment</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Payment</b></em>'
     * @generated
     */
    public EClass getPaymentEClass() {
        return (EClass) getEPackage().getEClassifiers().get(PAYMENT_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.discountDays</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.discountDays</b></em>'.
     * @generated
     */
    public EAttribute getPayment_DiscountDays() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_DISCOUNTDAYS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.discountValue</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.discountValue</b></em>'.
     * @generated
     */
    public EAttribute getPayment_DiscountValue() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_DISCOUNTVALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Payment.netDays</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.netDays</b></em>'.
     * @generated
     */
    public EAttribute getPayment_NetDays() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_NETDAYS_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.category</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.category</b></em>'.
     * @generated
     */
    public EReference getPayment_Category() {
        return (EReference) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_CATEGORY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.paidText</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.paidText</b></em>'.
     * @generated
     */
    public EAttribute getPayment_PaidText() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_PAIDTEXT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.unpaidText</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.unpaidText</b></em>'.
     * @generated
     */
    public EAttribute getPayment_UnpaidText() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_UNPAIDTEXT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.depositText</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.depositText</b></em>'.
     * @generated
     */
    public EAttribute getPayment_DepositText() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_DEPOSITTEXT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Payment.code</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.code</b></em>'.
     * @generated
     */
    public EAttribute getPayment_Code() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_CODE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.description</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.description</b></em>'.
     * @generated
     */
    public EAttribute getPayment_Description() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_DESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Payment.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.name</b></em>'.
     * @generated
     */
    public EAttribute getPayment_Name() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getPayment_DateAdded() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getPayment_ModifiedBy() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.modified</b></em>'.
     * @generated
     */
    public EAttribute getPayment_Modified() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Payment.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.id</b></em>'.
     * @generated
     */
    public EAttribute getPayment_Id() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Payment.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.deleted</b></em>'.
     * @generated
     */
    public EAttribute getPayment_Deleted() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Payment.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getPayment_ValidFrom() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Payment.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Payment.validTo</b></em>'.
     * @generated
     */
    public EAttribute getPayment_ValidTo() {
        return (EAttribute) getPaymentEClass().getEAllStructuralFeatures().get(PAYMENT_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Product</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Product</b></em>'
     * @generated
     */
    public EClass getProductEClass() {
        return (EClass) getEPackage().getEClassifiers().get(PRODUCT_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.block1</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.block1</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Block1() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_BLOCK1_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.block2</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.block2</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Block2() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_BLOCK2_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.block3</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.block3</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Block3() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_BLOCK3_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.block4</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.block4</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Block4() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_BLOCK4_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.block5</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.block5</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Block5() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_BLOCK5_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.categories</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.categories</b></em>'.
     * @generated
     */
    public EReference getProduct_Categories() {
        return (EReference) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_CATEGORIES_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.attributes</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.attributes</b></em>'.
     * @generated
     */
    public EReference getProduct_Attributes() {
        return (EReference) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_ATTRIBUTES_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.picture</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.picture</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Picture() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_PICTURE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.itemNumber</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.itemNumber</b></em>'.
     * @generated
     */
    public EAttribute getProduct_ItemNumber() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_ITEMNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.supplierItemNumber</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.supplierItemNumber</b></em>'.
     * @generated
     */
    public EAttribute getProduct_SupplierItemNumber() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_SUPPLIERITEMNUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.price1</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.price1</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Price1() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_PRICE1_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.price2</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.price2</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Price2() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_PRICE2_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.price3</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.price3</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Price3() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_PRICE3_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.price4</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.price4</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Price4() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_PRICE4_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.price5</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.price5</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Price5() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_PRICE5_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.quantity</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.quantity</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Quantity() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_QUANTITY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.quantityUnit</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.quantityUnit</b></em>'.
     * @generated
     */
    public EAttribute getProduct_QuantityUnit() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_QUANTITYUNIT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.sellingUnit</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.sellingUnit</b></em>'.
     * @generated
     */
    public EAttribute getProduct_SellingUnit() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_SELLINGUNIT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.vat</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.vat</b></em>'.
     * @generated
     */
    public EReference getProduct_Vat() {
        return (EReference) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_VAT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.webshopId</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.webshopId</b></em>'.
     * @generated
     */
    public EAttribute getProduct_WebshopId() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_WEBSHOPID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.weight</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.weight</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Weight() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_WEIGHT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.gtin</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.gtin</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Gtin() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_GTIN_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.costPrice</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.costPrice</b></em>'.
     * @generated
     */
    public EAttribute getProduct_CostPrice() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_COSTPRICE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.allowance</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.allowance</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Allowance() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_ALLOWANCE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.blockPrices</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.blockPrices</b></em>'.
     * @generated
     */
    public EReference getProduct_BlockPrices() {
        return (EReference) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_BLOCKPRICES_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.cdf01</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.cdf01</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Cdf01() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_CDF01_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.cdf02</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.cdf02</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Cdf02() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_CDF02_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.cdf03</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.cdf03</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Cdf03() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_CDF03_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.note</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.note</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Note() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_NOTE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.stockManaged</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.stockManaged</b></em>'.
     * @generated
     */
    public EAttribute getProduct_StockManaged() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_STOCKMANAGED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.description</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.description</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Description() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_DESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.name</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Name() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getProduct_DateAdded() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getProduct_ModifiedBy() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.modified</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Modified() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.id</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Id() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.deleted</b></em>'.
     * @generated
     */
    public EAttribute getProduct_Deleted() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Product.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getProduct_ValidFrom() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Product.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Product.validTo</b></em>'.
     * @generated
     */
    public EAttribute getProduct_ValidTo() {
        return (EAttribute) getProductEClass().getEAllStructuralFeatures().get(PRODUCT_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>ProductCategory</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>ProductCategory</b></em>'
     * @generated
     */
    public EClass getProductCategoryEClass() {
        return (EClass) getEPackage().getEClassifiers().get(PRODUCTCATEGORY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>ProductOptions</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>ProductOptions</b></em>'
     * @generated
     */
    public EClass getProductOptionsEClass() {
        return (EClass) getEPackage().getEClassifiers().get(PRODUCTOPTIONS_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductOptions.attributeValue</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductOptions.attributeValue</b></em>'.
     * @generated
     */
    public EAttribute getProductOptions_AttributeValue() {
        return (EAttribute) getProductOptionsEClass().getEAllStructuralFeatures().get(PRODUCTOPTIONS_ATTRIBUTEVALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductOptions.sequenceNumber</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductOptions.sequenceNumber</b></em>'.
     * @generated
     */
    public EAttribute getProductOptions_SequenceNumber() {
        return (EAttribute) getProductOptionsEClass().getEAllStructuralFeatures().get(PRODUCTOPTIONS_SEQUENCENUMBER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductOptions.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductOptions.name</b></em>'.
     * @generated
     */
    public EAttribute getProductOptions_Name() {
        return (EAttribute) getProductOptionsEClass().getEAllStructuralFeatures().get(PRODUCTOPTIONS_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductOptions.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductOptions.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getProductOptions_DateAdded() {
        return (EAttribute) getProductOptionsEClass().getEAllStructuralFeatures().get(PRODUCTOPTIONS_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductOptions.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductOptions.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getProductOptions_ModifiedBy() {
        return (EAttribute) getProductOptionsEClass().getEAllStructuralFeatures().get(PRODUCTOPTIONS_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductOptions.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductOptions.modified</b></em>'.
     * @generated
     */
    public EAttribute getProductOptions_Modified() {
        return (EAttribute) getProductOptionsEClass().getEAllStructuralFeatures().get(PRODUCTOPTIONS_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductOptions.id</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductOptions.id</b></em>'.
     * @generated
     */
    public EAttribute getProductOptions_Id() {
        return (EAttribute) getProductOptionsEClass().getEAllStructuralFeatures().get(PRODUCTOPTIONS_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductOptions.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductOptions.deleted</b></em>'.
     * @generated
     */
    public EAttribute getProductOptions_Deleted() {
        return (EAttribute) getProductOptionsEClass().getEAllStructuralFeatures().get(PRODUCTOPTIONS_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductOptions.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductOptions.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getProductOptions_ValidFrom() {
        return (EAttribute) getProductOptionsEClass().getEAllStructuralFeatures().get(PRODUCTOPTIONS_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductOptions.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductOptions.validTo</b></em>'.
     * @generated
     */
    public EAttribute getProductOptions_ValidTo() {
        return (EAttribute) getProductOptionsEClass().getEAllStructuralFeatures().get(PRODUCTOPTIONS_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>ProductBlockPrice</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>ProductBlockPrice</b></em>'
     * @generated
     */
    public EClass getProductBlockPriceEClass() {
        return (EClass) getEPackage().getEClassifiers().get(PRODUCTBLOCKPRICE_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductBlockPrice.block</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductBlockPrice.block</b></em>'.
     * @generated
     */
    public EAttribute getProductBlockPrice_Block() {
        return (EAttribute) getProductBlockPriceEClass().getEAllStructuralFeatures().get(PRODUCTBLOCKPRICE_BLOCK_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductBlockPrice.price</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductBlockPrice.price</b></em>'.
     * @generated
     */
    public EAttribute getProductBlockPrice_Price() {
        return (EAttribute) getProductBlockPriceEClass().getEAllStructuralFeatures().get(PRODUCTBLOCKPRICE_PRICE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductBlockPrice.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductBlockPrice.name</b></em>'.
     * @generated
     */
    public EAttribute getProductBlockPrice_Name() {
        return (EAttribute) getProductBlockPriceEClass().getEAllStructuralFeatures().get(PRODUCTBLOCKPRICE_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductBlockPrice.dateAdded</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductBlockPrice.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getProductBlockPrice_DateAdded() {
        return (EAttribute) getProductBlockPriceEClass().getEAllStructuralFeatures().get(PRODUCTBLOCKPRICE_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductBlockPrice.modifiedBy</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductBlockPrice.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getProductBlockPrice_ModifiedBy() {
        return (EAttribute) getProductBlockPriceEClass().getEAllStructuralFeatures().get(PRODUCTBLOCKPRICE_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductBlockPrice.modified</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductBlockPrice.modified</b></em>'.
     * @generated
     */
    public EAttribute getProductBlockPrice_Modified() {
        return (EAttribute) getProductBlockPriceEClass().getEAllStructuralFeatures().get(PRODUCTBLOCKPRICE_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductBlockPrice.id</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductBlockPrice.id</b></em>'.
     * @generated
     */
    public EAttribute getProductBlockPrice_Id() {
        return (EAttribute) getProductBlockPriceEClass().getEAllStructuralFeatures().get(PRODUCTBLOCKPRICE_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductBlockPrice.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductBlockPrice.deleted</b></em>'.
     * @generated
     */
    public EAttribute getProductBlockPrice_Deleted() {
        return (EAttribute) getProductBlockPriceEClass().getEAllStructuralFeatures().get(PRODUCTBLOCKPRICE_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductBlockPrice.validFrom</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductBlockPrice.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getProductBlockPrice_ValidFrom() {
        return (EAttribute) getProductBlockPriceEClass().getEAllStructuralFeatures().get(PRODUCTBLOCKPRICE_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>ProductBlockPrice.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>ProductBlockPrice.validTo</b></em>'.
     * @generated
     */
    public EAttribute getProductBlockPrice_ValidTo() {
        return (EAttribute) getProductBlockPriceEClass().getEAllStructuralFeatures().get(PRODUCTBLOCKPRICE_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Role</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Role</b></em>'
     * @generated
     */
    public EClass getRoleEClass() {
        return (EClass) getEPackage().getEClassifiers().get(ROLE_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Role.name</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Role.name</b></em>'.
     * @generated
     */
    public EAttribute getRole_Name() {
        return (EAttribute) getRoleEClass().getEAllStructuralFeatures().get(ROLE_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Role.dateAdded</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Role.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getRole_DateAdded() {
        return (EAttribute) getRoleEClass().getEAllStructuralFeatures().get(ROLE_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Role.modifiedBy</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Role.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getRole_ModifiedBy() {
        return (EAttribute) getRoleEClass().getEAllStructuralFeatures().get(ROLE_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Role.modified</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Role.modified</b></em>'.
     * @generated
     */
    public EAttribute getRole_Modified() {
        return (EAttribute) getRoleEClass().getEAllStructuralFeatures().get(ROLE_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Role.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Role.id</b></em>'.
     * @generated
     */
    public EAttribute getRole_Id() {
        return (EAttribute) getRoleEClass().getEAllStructuralFeatures().get(ROLE_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Role.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Role.deleted</b></em>'.
     * @generated
     */
    public EAttribute getRole_Deleted() {
        return (EAttribute) getRoleEClass().getEAllStructuralFeatures().get(ROLE_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Role.validFrom</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Role.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getRole_ValidFrom() {
        return (EAttribute) getRoleEClass().getEAllStructuralFeatures().get(ROLE_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Role.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Role.validTo</b></em>'.
     * @generated
     */
    public EAttribute getRole_ValidTo() {
        return (EAttribute) getRoleEClass().getEAllStructuralFeatures().get(ROLE_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Tenant</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Tenant</b></em>'
     * @generated
     */
    public EClass getTenantEClass() {
        return (EClass) getEPackage().getEClassifiers().get(TENANT_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Tenant.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Tenant.name</b></em>'.
     * @generated
     */
    public EAttribute getTenant_Name() {
        return (EAttribute) getTenantEClass().getEAllStructuralFeatures().get(TENANT_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Tenant.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Tenant.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getTenant_DateAdded() {
        return (EAttribute) getTenantEClass().getEAllStructuralFeatures().get(TENANT_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Tenant.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Tenant.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getTenant_ModifiedBy() {
        return (EAttribute) getTenantEClass().getEAllStructuralFeatures().get(TENANT_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Tenant.modified</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Tenant.modified</b></em>'.
     * @generated
     */
    public EAttribute getTenant_Modified() {
        return (EAttribute) getTenantEClass().getEAllStructuralFeatures().get(TENANT_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Tenant.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Tenant.id</b></em>'.
     * @generated
     */
    public EAttribute getTenant_Id() {
        return (EAttribute) getTenantEClass().getEAllStructuralFeatures().get(TENANT_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Tenant.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Tenant.deleted</b></em>'.
     * @generated
     */
    public EAttribute getTenant_Deleted() {
        return (EAttribute) getTenantEClass().getEAllStructuralFeatures().get(TENANT_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Tenant.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Tenant.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getTenant_ValidFrom() {
        return (EAttribute) getTenantEClass().getEAllStructuralFeatures().get(TENANT_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Tenant.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Tenant.validTo</b></em>'.
     * @generated
     */
    public EAttribute getTenant_ValidTo() {
        return (EAttribute) getTenantEClass().getEAllStructuralFeatures().get(TENANT_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Shipping</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Shipping</b></em>'
     * @generated
     */
    public EClass getShippingEClass() {
        return (EClass) getEPackage().getEClassifiers().get(SHIPPING_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.autoVat</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.autoVat</b></em>'.
     * @generated
     */
    public EAttribute getShipping_AutoVat() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_AUTOVAT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.shippingValue</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.shippingValue</b></em>'.
     * @generated
     */
    public EAttribute getShipping_ShippingValue() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_SHIPPINGVALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.shippingVat</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.shippingVat</b></em>'.
     * @generated
     */
    public EReference getShipping_ShippingVat() {
        return (EReference) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_SHIPPINGVAT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.categories</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.categories</b></em>'.
     * @generated
     */
    public EReference getShipping_Categories() {
        return (EReference) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_CATEGORIES_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Shipping.code</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.code</b></em>'.
     * @generated
     */
    public EAttribute getShipping_Code() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_CODE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.description</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.description</b></em>'.
     * @generated
     */
    public EAttribute getShipping_Description() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_DESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Shipping.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.name</b></em>'.
     * @generated
     */
    public EAttribute getShipping_Name() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getShipping_DateAdded() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getShipping_ModifiedBy() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.modified</b></em>'.
     * @generated
     */
    public EAttribute getShipping_Modified() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>Shipping.id</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.id</b></em>'.
     * @generated
     */
    public EAttribute getShipping_Id() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.deleted</b></em>'.
     * @generated
     */
    public EAttribute getShipping_Deleted() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getShipping_ValidFrom() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Shipping.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Shipping.validTo</b></em>'.
     * @generated
     */
    public EAttribute getShipping_ValidTo() {
        return (EAttribute) getShippingEClass().getEAllStructuralFeatures().get(SHIPPING_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>ShippingCategory</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>ShippingCategory</b></em>'
     * @generated
     */
    public EClass getShippingCategoryEClass() {
        return (EClass) getEPackage().getEClassifiers().get(SHIPPINGCATEGORY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>TextCategory</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>TextCategory</b></em>'
     * @generated
     */
    public EClass getTextCategoryEClass() {
        return (EClass) getEPackage().getEClassifiers().get(TEXTCATEGORY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>TextModule</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>TextModule</b></em>'
     * @generated
     */
    public EClass getTextModuleEClass() {
        return (EClass) getEPackage().getEClassifiers().get(TEXTMODULE_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>TextModule.text</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>TextModule.text</b></em>'.
     * @generated
     */
    public EAttribute getTextModule_Text() {
        return (EAttribute) getTextModuleEClass().getEAllStructuralFeatures().get(TEXTMODULE_TEXT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>TextModule.categories</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>TextModule.categories</b></em>'.
     * @generated
     */
    public EReference getTextModule_Categories() {
        return (EReference) getTextModuleEClass().getEAllStructuralFeatures().get(TEXTMODULE_CATEGORIES_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>TextModule.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>TextModule.name</b></em>'.
     * @generated
     */
    public EAttribute getTextModule_Name() {
        return (EAttribute) getTextModuleEClass().getEAllStructuralFeatures().get(TEXTMODULE_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>TextModule.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>TextModule.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getTextModule_DateAdded() {
        return (EAttribute) getTextModuleEClass().getEAllStructuralFeatures().get(TEXTMODULE_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>TextModule.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>TextModule.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getTextModule_ModifiedBy() {
        return (EAttribute) getTextModuleEClass().getEAllStructuralFeatures().get(TEXTMODULE_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>TextModule.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>TextModule.modified</b></em>'.
     * @generated
     */
    public EAttribute getTextModule_Modified() {
        return (EAttribute) getTextModuleEClass().getEAllStructuralFeatures().get(TEXTMODULE_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>TextModule.id</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>TextModule.id</b></em>'.
     * @generated
     */
    public EAttribute getTextModule_Id() {
        return (EAttribute) getTextModuleEClass().getEAllStructuralFeatures().get(TEXTMODULE_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>TextModule.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>TextModule.deleted</b></em>'.
     * @generated
     */
    public EAttribute getTextModule_Deleted() {
        return (EAttribute) getTextModuleEClass().getEAllStructuralFeatures().get(TEXTMODULE_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>TextModule.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>TextModule.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getTextModule_ValidFrom() {
        return (EAttribute) getTextModuleEClass().getEAllStructuralFeatures().get(TEXTMODULE_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>TextModule.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>TextModule.validTo</b></em>'.
     * @generated
     */
    public EAttribute getTextModule_ValidTo() {
        return (EAttribute) getTextModuleEClass().getEAllStructuralFeatures().get(TEXTMODULE_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>User</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>User</b></em>'
     * @generated
     */
    public EClass getUserEClass() {
        return (EClass) getEPackage().getEClassifiers().get(USER_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.password</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.password</b></em>'.
     * @generated
     */
    public EAttribute getUser_Password() {
        return (EAttribute) getUserEClass().getEAllStructuralFeatures().get(USER_PASSWORD_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.tenant</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.tenant</b></em>'.
     * @generated
     */
    public EReference getUser_Tenant() {
        return (EReference) getUserEClass().getEAllStructuralFeatures().get(USER_TENANT_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.userName</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.userName</b></em>'.
     * @generated
     */
    public EAttribute getUser_UserName() {
        return (EAttribute) getUserEClass().getEAllStructuralFeatures().get(USER_USERNAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.roles</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.roles</b></em>'.
     * @generated
     */
    public EReference getUser_Roles() {
        return (EReference) getUserEClass().getEAllStructuralFeatures().get(USER_ROLES_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.name</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.name</b></em>'.
     * @generated
     */
    public EAttribute getUser_Name() {
        return (EAttribute) getUserEClass().getEAllStructuralFeatures().get(USER_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.dateAdded</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getUser_DateAdded() {
        return (EAttribute) getUserEClass().getEAllStructuralFeatures().get(USER_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.modifiedBy</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getUser_ModifiedBy() {
        return (EAttribute) getUserEClass().getEAllStructuralFeatures().get(USER_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.modified</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.modified</b></em>'.
     * @generated
     */
    public EAttribute getUser_Modified() {
        return (EAttribute) getUserEClass().getEAllStructuralFeatures().get(USER_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.id</b></em>'.
     * @generated
     */
    public EAttribute getUser_Id() {
        return (EAttribute) getUserEClass().getEAllStructuralFeatures().get(USER_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.deleted</b></em>'.
     * @generated
     */
    public EAttribute getUser_Deleted() {
        return (EAttribute) getUserEClass().getEAllStructuralFeatures().get(USER_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.validFrom</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getUser_ValidFrom() {
        return (EAttribute) getUserEClass().getEAllStructuralFeatures().get(USER_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>User.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>User.validTo</b></em>'.
     * @generated
     */
    public EAttribute getUser_ValidTo() {
        return (EAttribute) getUserEClass().getEAllStructuralFeatures().get(USER_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>UserProperty</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>UserProperty</b></em>'
     * @generated
     */
    public EClass getUserPropertyEClass() {
        return (EClass) getEPackage().getEClassifiers().get(USERPROPERTY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.value</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.value</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_Value() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_VALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.user</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.user</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_User() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_USER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.default</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.default</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_Default_() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_DEFAULT__FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.global</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.global</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_Global() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_GLOBAL_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.qualifier</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.qualifier</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_Qualifier() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_QUALIFIER_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.name</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_Name() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_DateAdded() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_ModifiedBy() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.modified</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_Modified() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>UserProperty.id</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.id</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_Id() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.deleted</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_Deleted() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_ValidFrom() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>UserProperty.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>UserProperty.validTo</b></em>'.
     * @generated
     */
    public EAttribute getUserProperty_ValidTo() {
        return (EAttribute) getUserPropertyEClass().getEAllStructuralFeatures().get(USERPROPERTY_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>VAT</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>VAT</b></em>'
     * @generated
     */
    public EClass getVATEClass() {
        return (EClass) getEPackage().getEClassifiers().get(VAT_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.taxValue</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.taxValue</b></em>'.
     * @generated
     */
    public EAttribute getVAT_TaxValue() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_TAXVALUE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.category</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.category</b></em>'.
     * @generated
     */
    public EReference getVAT_Category() {
        return (EReference) getVATEClass().getEAllStructuralFeatures().get(VAT_CATEGORY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>VAT.salesEqualizationTax</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.salesEqualizationTax</b></em>'.
     * @generated
     */
    public EAttribute getVAT_SalesEqualizationTax() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_SALESEQUALIZATIONTAX_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.description</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.description</b></em>'.
     * @generated
     */
    public EAttribute getVAT_Description() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_DESCRIPTION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.name</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.name</b></em>'.
     * @generated
     */
    public EAttribute getVAT_Name() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.dateAdded</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getVAT_DateAdded() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.modifiedBy</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getVAT_ModifiedBy() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.modified</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.modified</b></em>'.
     * @generated
     */
    public EAttribute getVAT_Modified() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.id</b></em>'.
     * @generated
     */
    public EAttribute getVAT_Id() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.deleted</b></em>'.
     * @generated
     */
    public EAttribute getVAT_Deleted() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.validFrom</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getVAT_ValidFrom() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>VAT.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>VAT.validTo</b></em>'.
     * @generated
     */
    public EAttribute getVAT_ValidTo() {
        return (EAttribute) getVATEClass().getEAllStructuralFeatures().get(VAT_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>VATCategory</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>VATCategory</b></em>'
     * @generated
     */
    public EClass getVATCategoryEClass() {
        return (EClass) getEPackage().getEClassifiers().get(VATCATEGORY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>CEFACTCode</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>CEFACTCode</b></em>'
     * @generated
     */
    public EClass getCEFACTCodeEClass() {
        return (EClass) getEPackage().getEClassifiers().get(CEFACTCODE_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>CEFACTCode.abbreviation_de</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.abbreviation_de</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_Abbreviation_de() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_ABBREVIATION_DE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>CEFACTCode.abbreviation_en</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.abbreviation_en</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_Abbreviation_en() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_ABBREVIATION_EN_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>CEFACTCode.code</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.code</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_Code() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_CODE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>CEFACTCode.target</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.target</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_Target() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_TARGET_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>CEFACTCode.name_de</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.name_de</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_Name_de() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_NAME_DE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>CEFACTCode.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.name</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_Name() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>CEFACTCode.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_DateAdded() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>CEFACTCode.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_ModifiedBy() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>CEFACTCode.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.modified</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_Modified() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>CEFACTCode.id</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.id</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_Id() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>CEFACTCode.deleted</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.deleted</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_Deleted() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>CEFACTCode.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_ValidFrom() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>CEFACTCode.validTo</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>CEFACTCode.validTo</b></em>'.
     * @generated
     */
    public EAttribute getCEFACTCode_ValidTo() {
        return (EAttribute) getCEFACTCodeEClass().getEAllStructuralFeatures().get(CEFACTCODE_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>WebShop</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>WebShop</b></em>'
     * @generated
     */
    public EClass getWebShopEClass() {
        return (EClass) getEPackage().getEClassifiers().get(WEBSHOP_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebShop.webshopVendor</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.webshopVendor</b></em>'.
     * @generated
     */
    public EAttribute getWebShop_WebshopVendor() {
        return (EAttribute) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_WEBSHOPVENDOR_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebShop.webshopVersion</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.webshopVersion</b></em>'.
     * @generated
     */
    public EAttribute getWebShop_WebshopVersion() {
        return (EAttribute) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_WEBSHOPVERSION_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebShop.stateMapping</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.stateMapping</b></em>'.
     * @generated
     */
    public EReference getWebShop_StateMapping() {
        return (EReference) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_STATEMAPPING_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>WebShop.name</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.name</b></em>'.
     * @generated
     */
    public EAttribute getWebShop_Name() {
        return (EAttribute) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebShop.dateAdded</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getWebShop_DateAdded() {
        return (EAttribute) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebShop.modifiedBy</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getWebShop_ModifiedBy() {
        return (EAttribute) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebShop.modified</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.modified</b></em>'.
     * @generated
     */
    public EAttribute getWebShop_Modified() {
        return (EAttribute) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>WebShop.id</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.id</b></em>'.
     * @generated
     */
    public EAttribute getWebShop_Id() {
        return (EAttribute) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>WebShop.deleted</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.deleted</b></em>'.
     * @generated
     */
    public EAttribute getWebShop_Deleted() {
        return (EAttribute) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebShop.validFrom</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getWebShop_ValidFrom() {
        return (EAttribute) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature} '<em><b>WebShop.validTo</b></em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebShop.validTo</b></em>'.
     * @generated
     */
    public EAttribute getWebShop_ValidTo() {
        return (EAttribute) getWebShopEClass().getEAllStructuralFeatures().get(WEBSHOP_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>WebshopStateMapping</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>WebshopStateMapping</b></em>'
     * @generated
     */
    public EClass getWebshopStateMappingEClass() {
        return (EClass) getEPackage().getEClassifiers().get(WEBSHOPSTATEMAPPING_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebshopStateMapping.webshopState</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebshopStateMapping.webshopState</b></em>'.
     * @generated
     */
    public EAttribute getWebshopStateMapping_WebshopState() {
        return (EAttribute) getWebshopStateMappingEClass().getEAllStructuralFeatures().get(WEBSHOPSTATEMAPPING_WEBSHOPSTATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebshopStateMapping.fakturamaOrderState</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebshopStateMapping.fakturamaOrderState</b></em>'.
     * @generated
     */
    public EAttribute getWebshopStateMapping_FakturamaOrderState() {
        return (EAttribute) getWebshopStateMappingEClass().getEAllStructuralFeatures().get(WEBSHOPSTATEMAPPING_FAKTURAMAORDERSTATE_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebshopStateMapping.name</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebshopStateMapping.name</b></em>'.
     * @generated
     */
    public EAttribute getWebshopStateMapping_Name() {
        return (EAttribute) getWebshopStateMappingEClass().getEAllStructuralFeatures().get(WEBSHOPSTATEMAPPING_NAME_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebshopStateMapping.dateAdded</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebshopStateMapping.dateAdded</b></em>'.
     * @generated
     */
    public EAttribute getWebshopStateMapping_DateAdded() {
        return (EAttribute) getWebshopStateMappingEClass().getEAllStructuralFeatures().get(WEBSHOPSTATEMAPPING_DATEADDED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebshopStateMapping.modifiedBy</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebshopStateMapping.modifiedBy</b></em>'.
     * @generated
     */
    public EAttribute getWebshopStateMapping_ModifiedBy() {
        return (EAttribute) getWebshopStateMappingEClass().getEAllStructuralFeatures().get(WEBSHOPSTATEMAPPING_MODIFIEDBY_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebshopStateMapping.modified</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebshopStateMapping.modified</b></em>'.
     * @generated
     */
    public EAttribute getWebshopStateMapping_Modified() {
        return (EAttribute) getWebshopStateMappingEClass().getEAllStructuralFeatures().get(WEBSHOPSTATEMAPPING_MODIFIED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebshopStateMapping.id</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebshopStateMapping.id</b></em>'.
     * @generated
     */
    public EAttribute getWebshopStateMapping_Id() {
        return (EAttribute) getWebshopStateMappingEClass().getEAllStructuralFeatures().get(WEBSHOPSTATEMAPPING_ID_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebshopStateMapping.deleted</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebshopStateMapping.deleted</b></em>'.
     * @generated
     */
    public EAttribute getWebshopStateMapping_Deleted() {
        return (EAttribute) getWebshopStateMappingEClass().getEAllStructuralFeatures().get(WEBSHOPSTATEMAPPING_DELETED_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebshopStateMapping.validFrom</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebshopStateMapping.validFrom</b></em>'.
     * @generated
     */
    public EAttribute getWebshopStateMapping_ValidFrom() {
        return (EAttribute) getWebshopStateMappingEClass().getEAllStructuralFeatures().get(WEBSHOPSTATEMAPPING_VALIDFROM_FEATURE_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>WebshopStateMapping.validTo</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>WebshopStateMapping.validTo</b></em>'.
     * @generated
     */
    public EAttribute getWebshopStateMapping_ValidTo() {
        return (EAttribute) getWebshopStateMappingEClass().getEAllStructuralFeatures().get(WEBSHOPSTATEMAPPING_VALIDTO_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Confirmation</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Confirmation</b></em>'
     * @generated
     */
    public EClass getConfirmationEClass() {
        return (EClass) getEPackage().getEClassifiers().get(CONFIRMATION_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Credit</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Credit</b></em>'
     * @generated
     */
    public EClass getCreditEClass() {
        return (EClass) getEPackage().getEClassifiers().get(CREDIT_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Delivery</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Delivery</b></em>'
     * @generated
     */
    public EClass getDeliveryEClass() {
        return (EClass) getEPackage().getEClassifiers().get(DELIVERY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Dunning</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Dunning</b></em>'
     * @generated
     */
    public EClass getDunningEClass() {
        return (EClass) getEPackage().getEClassifiers().get(DUNNING_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EStructuralFeature}
     * '<em><b>Dunning.dunningLevel</b></em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return an instance of the {@link EStructuralFeature}:
     *         '<em><b>Dunning.dunningLevel</b></em>'.
     * @generated
     */
    public EAttribute getDunning_DunningLevel() {
        return (EAttribute) getDunningEClass().getEAllStructuralFeatures().get(DUNNING_DUNNINGLEVEL_FEATURE_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Debitor</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Debitor</b></em>'
     * @generated
     */
    public EClass getDebitorEClass() {
        return (EClass) getEPackage().getEClassifiers().get(DEBITOR_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Invoice</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Invoice</b></em>'
     * @generated
     */
    public EClass getInvoiceEClass() {
        return (EClass) getEPackage().getEClassifiers().get(INVOICE_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>ItemListTypeCategory</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass}
     *         '<em><b>ItemListTypeCategory</b></em>'
     * @generated
     */
    public EClass getItemListTypeCategoryEClass() {
        return (EClass) getEPackage().getEClassifiers().get(ITEMLISTTYPECATEGORY_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Letter</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Letter</b></em>'
     * @generated
     */
    public EClass getLetterEClass() {
        return (EClass) getEPackage().getEClassifiers().get(LETTER_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Offer</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Offer</b></em>'
     * @generated
     */
    public EClass getOfferEClass() {
        return (EClass) getEPackage().getEClassifiers().get(OFFER_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Order</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Order</b></em>'
     * @generated
     */
    public EClass getOrderEClass() {
        return (EClass) getEPackage().getEClassifiers().get(ORDER_CLASSIFIER_ID);
    }

    /**
     * Returns the {@link EClass} '<em><b>Proforma</b></em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the {@link EClass} '<em><b>Proforma</b></em>'
     * @generated
     */
    public EClass getProformaEClass() {
        return (EClass) getEPackage().getEClassifiers().get(PROFORMA_CLASSIFIER_ID);
    }

    /**
     * Returns the EEnum '<em><b>ItemType</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the EEnum representing '<em><b>ItemType</b></em>'
     * @generated
     */
    public EEnum getItemTypeEEnum() {
        return (EEnum) getEPackage().getEClassifiers().get(ITEMTYPE_CLASSIFIER_ID);
    }

    /**
     * Returns the EEnum '<em><b>BillingType</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the EEnum representing
     *         '<em><b>BillingType</b></em>'
     * @generated
     */
    public EEnum getBillingTypeEEnum() {
        return (EEnum) getEPackage().getEClassifiers().get(BILLINGTYPE_CLASSIFIER_ID);
    }

    /**
     * Returns the EEnum '<em><b>VoucherType</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the EEnum representing
     *         '<em><b>VoucherType</b></em>'
     * @generated
     */
    public EEnum getVoucherTypeEEnum() {
        return (EEnum) getEPackage().getEClassifiers().get(VOUCHERTYPE_CLASSIFIER_ID);
    }

    /**
     * Returns the EEnum '<em><b>ContactType</b></em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return an instance of the EEnum representing
     *         '<em><b>ContactType</b></em>'
     * @generated
     */
    public EEnum getContactTypeEEnum() {
        return (EEnum) getEPackage().getEClassifiers().get(CONTACTTYPE_CLASSIFIER_ID);
    }

    /**
     * Returns the EEnum '<em><b>ReliabilityType</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the EEnum representing
     *         '<em><b>ReliabilityType</b></em>'
     * @generated
     */
    public EEnum getReliabilityTypeEEnum() {
        return (EEnum) getEPackage().getEClassifiers().get(RELIABILITYTYPE_CLASSIFIER_ID);
    }

    /**
     * Returns the EEnum '<em><b>ShippingVatType</b></em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return an instance of the EEnum representing
     *         '<em><b>ShippingVatType</b></em>'
     * @generated
     */
    public EEnum getShippingVatTypeEEnum() {
        return (EEnum) getEPackage().getEClassifiers().get(SHIPPINGVATTYPE_CLASSIFIER_ID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param eClassifier
     *            the {@link EClassifier}
     * @return the class implementing a specific {@link EClass}.
     * @generated
     */
    @Override
    public Class<?> getEClassifierClass(EClassifier eClassifier) {
        switch (eClassifier.getClassifierID()) {
        case ABSTRACTCATEGORY_CLASSIFIER_ID:
            return AbstractCategory.class;
        case VOUCHER_CLASSIFIER_ID:
            return Voucher.class;
        case VOUCHERCATEGORY_CLASSIFIER_ID:
            return VoucherCategory.class;
        case VOUCHERITEM_CLASSIFIER_ID:
            return VoucherItem.class;
        case BANKACCOUNT_CLASSIFIER_ID:
            return BankAccount.class;
        case IENTITY_CLASSIFIER_ID:
            return IEntity.class;
        case ADDRESS_CLASSIFIER_ID:
            return Address.class;
        case CONTACT_CLASSIFIER_ID:
            return Contact.class;
        case CREDITOR_CLASSIFIER_ID:
            return Creditor.class;
        case CONTACTCATEGORY_CLASSIFIER_ID:
            return ContactCategory.class;
        case DOCUMENT_CLASSIFIER_ID:
            return Document.class;
        case DOCUMENTRECEIVER_CLASSIFIER_ID:
            return DocumentReceiver.class;
        case DOCUMENTITEM_CLASSIFIER_ID:
            return DocumentItem.class;
        case INDIVIDUALDOCUMENTINFO_CLASSIFIER_ID:
            return IndividualDocumentInfo.class;
        case IDESCRIBABLEENTITY_CLASSIFIER_ID:
            return IDescribableEntity.class;
        case ITEMACCOUNTTYPE_CLASSIFIER_ID:
            return ItemAccountType.class;
        case PAYMENT_CLASSIFIER_ID:
            return Payment.class;
        case PRODUCT_CLASSIFIER_ID:
            return Product.class;
        case PRODUCTCATEGORY_CLASSIFIER_ID:
            return ProductCategory.class;
        case PRODUCTOPTIONS_CLASSIFIER_ID:
            return ProductOptions.class;
        case PRODUCTBLOCKPRICE_CLASSIFIER_ID:
            return ProductBlockPrice.class;
        case ROLE_CLASSIFIER_ID:
            return Role.class;
        case TENANT_CLASSIFIER_ID:
            return Tenant.class;
        case SHIPPING_CLASSIFIER_ID:
            return Shipping.class;
        case SHIPPINGCATEGORY_CLASSIFIER_ID:
            return ShippingCategory.class;
        case TEXTCATEGORY_CLASSIFIER_ID:
            return TextCategory.class;
        case TEXTMODULE_CLASSIFIER_ID:
            return TextModule.class;
        case USER_CLASSIFIER_ID:
            return User.class;
        case USERPROPERTY_CLASSIFIER_ID:
            return UserProperty.class;
        case VAT_CLASSIFIER_ID:
            return VAT.class;
        case VATCATEGORY_CLASSIFIER_ID:
            return VATCategory.class;
        case CEFACTCODE_CLASSIFIER_ID:
            return CEFACTCode.class;
        case WEBSHOP_CLASSIFIER_ID:
            return WebShop.class;
        case WEBSHOPSTATEMAPPING_CLASSIFIER_ID:
            return WebshopStateMapping.class;
        case CONFIRMATION_CLASSIFIER_ID:
            return Confirmation.class;
        case CREDIT_CLASSIFIER_ID:
            return Credit.class;
        case DELIVERY_CLASSIFIER_ID:
            return Delivery.class;
        case DUNNING_CLASSIFIER_ID:
            return Dunning.class;
        case DEBITOR_CLASSIFIER_ID:
            return Debitor.class;
        case INVOICE_CLASSIFIER_ID:
            return Invoice.class;
        case ITEMLISTTYPECATEGORY_CLASSIFIER_ID:
            return ItemListTypeCategory.class;
        case LETTER_CLASSIFIER_ID:
            return Letter.class;
        case OFFER_CLASSIFIER_ID:
            return Offer.class;
        case ORDER_CLASSIFIER_ID:
            return Order.class;
        case PROFORMA_CLASSIFIER_ID:
            return Proforma.class;
        case ITEMTYPE_CLASSIFIER_ID:
            return ItemType.class;
        case BILLINGTYPE_CLASSIFIER_ID:
            return BillingType.class;
        case VOUCHERTYPE_CLASSIFIER_ID:
            return VoucherType.class;
        case CONTACTTYPE_CLASSIFIER_ID:
            return ContactType.class;
        case RELIABILITYTYPE_CLASSIFIER_ID:
            return ReliabilityType.class;
        case SHIPPINGVATTYPE_CLASSIFIER_ID:
            return ShippingVatType.class;
        default:
            throw new IllegalArgumentException("The EClassifier '" + eClassifier + "' is not defined in this EPackage");
        }
    }
}
