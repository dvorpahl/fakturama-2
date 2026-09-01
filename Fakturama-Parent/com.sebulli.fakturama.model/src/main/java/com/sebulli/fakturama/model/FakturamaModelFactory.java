package com.sebulli.fakturama.model;

import java.util.Date;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.texo.model.AbstractModelFeatureMapEntry;
import org.eclipse.emf.texo.model.AbstractModelObject;
import org.eclipse.emf.texo.model.ModelFactory;
import org.eclipse.emf.texo.model.ModelFeatureMapEntry;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.model.ModelPackage;

/**
 * The <b>{@link ModelFactory}</b> for the types of this model: model. It
 * contains code to create instances {@link ModelObject} wrappers and instances
 * for EClasses and convert objects back and forth from their String (XML)
 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class FakturamaModelFactory implements ModelFactory {

    /**
     * Creates an instance for an {@link EClass} <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param eClass
     *            creates a Object instance for this EClass
     * @return an object representing the eClass
     * @generated
     */
    public Object create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case FakturamaModelPackage.VOUCHER_CLASSIFIER_ID:
            return createVoucher();
        case FakturamaModelPackage.VOUCHERCATEGORY_CLASSIFIER_ID:
            return createVoucherCategory();
        case FakturamaModelPackage.VOUCHERITEM_CLASSIFIER_ID:
            return createVoucherItem();
        case FakturamaModelPackage.BANKACCOUNT_CLASSIFIER_ID:
            return createBankAccount();
        case FakturamaModelPackage.ADDRESS_CLASSIFIER_ID:
            return createAddress();
        case FakturamaModelPackage.CREDITOR_CLASSIFIER_ID:
            return createCreditor();
        case FakturamaModelPackage.CONTACTCATEGORY_CLASSIFIER_ID:
            return createContactCategory();
        case FakturamaModelPackage.DOCUMENTRECEIVER_CLASSIFIER_ID:
            return createDocumentReceiver();
        case FakturamaModelPackage.DOCUMENTITEM_CLASSIFIER_ID:
            return createDocumentItem();
        case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_CLASSIFIER_ID:
            return createIndividualDocumentInfo();
        case FakturamaModelPackage.ITEMACCOUNTTYPE_CLASSIFIER_ID:
            return createItemAccountType();
        case FakturamaModelPackage.PAYMENT_CLASSIFIER_ID:
            return createPayment();
        case FakturamaModelPackage.PRODUCT_CLASSIFIER_ID:
            return createProduct();
        case FakturamaModelPackage.PRODUCTCATEGORY_CLASSIFIER_ID:
            return createProductCategory();
        case FakturamaModelPackage.PRODUCTOPTIONS_CLASSIFIER_ID:
            return createProductOptions();
        case FakturamaModelPackage.PRODUCTBLOCKPRICE_CLASSIFIER_ID:
            return createProductBlockPrice();
        case FakturamaModelPackage.ROLE_CLASSIFIER_ID:
            return createRole();
        case FakturamaModelPackage.TENANT_CLASSIFIER_ID:
            return createTenant();
        case FakturamaModelPackage.SHIPPING_CLASSIFIER_ID:
            return createShipping();
        case FakturamaModelPackage.SHIPPINGCATEGORY_CLASSIFIER_ID:
            return createShippingCategory();
        case FakturamaModelPackage.TEXTCATEGORY_CLASSIFIER_ID:
            return createTextCategory();
        case FakturamaModelPackage.TEXTMODULE_CLASSIFIER_ID:
            return createTextModule();
        case FakturamaModelPackage.USER_CLASSIFIER_ID:
            return createUser();
        case FakturamaModelPackage.USERPROPERTY_CLASSIFIER_ID:
            return createUserProperty();
        case FakturamaModelPackage.VAT_CLASSIFIER_ID:
            return createVAT();
        case FakturamaModelPackage.VATCATEGORY_CLASSIFIER_ID:
            return createVATCategory();
        case FakturamaModelPackage.CEFACTCODE_CLASSIFIER_ID:
            return createCEFACTCode();
        case FakturamaModelPackage.WEBSHOP_CLASSIFIER_ID:
            return createWebShop();
        case FakturamaModelPackage.WEBSHOPSTATEMAPPING_CLASSIFIER_ID:
            return createWebshopStateMapping();
        case FakturamaModelPackage.CONFIRMATION_CLASSIFIER_ID:
            return createConfirmation();
        case FakturamaModelPackage.CREDIT_CLASSIFIER_ID:
            return createCredit();
        case FakturamaModelPackage.DELIVERY_CLASSIFIER_ID:
            return createDelivery();
        case FakturamaModelPackage.DUNNING_CLASSIFIER_ID:
            return createDunning();
        case FakturamaModelPackage.DEBITOR_CLASSIFIER_ID:
            return createDebitor();
        case FakturamaModelPackage.INVOICE_CLASSIFIER_ID:
            return createInvoice();
        case FakturamaModelPackage.ITEMLISTTYPECATEGORY_CLASSIFIER_ID:
            return createItemListTypeCategory();
        case FakturamaModelPackage.LETTER_CLASSIFIER_ID:
            return createLetter();
        case FakturamaModelPackage.OFFER_CLASSIFIER_ID:
            return createOffer();
        case FakturamaModelPackage.ORDER_CLASSIFIER_ID:
            return createOrder();
        case FakturamaModelPackage.PROFORMA_CLASSIFIER_ID:
            return createProforma();
        default:
            throw new IllegalArgumentException("The EClass '" + eClass.getName() + "' is not a valid EClass for this EPackage");
        }
    }

    /**
     * Wraps an object in a {@link ModelObject}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param eClass
     *            the EClass of the object
     * @param adaptee
     *            the object being wrapped/adapted
     * @return the wrapper {@link ModelObject}
     * @generated
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> ModelObject<T> createModelObject(EClass eClass, T adaptee) {
        ModelObject<Object> modelObject = null;
        switch (eClass.getClassifierID()) {
        case FakturamaModelPackage.ABSTRACTCATEGORY_CLASSIFIER_ID:
            modelObject = new AbstractCategoryModelObject();
            break;
        case FakturamaModelPackage.VOUCHER_CLASSIFIER_ID:
            modelObject = new VoucherModelObject();
            break;
        case FakturamaModelPackage.VOUCHERCATEGORY_CLASSIFIER_ID:
            modelObject = new VoucherCategoryModelObject();
            break;
        case FakturamaModelPackage.VOUCHERITEM_CLASSIFIER_ID:
            modelObject = new VoucherItemModelObject();
            break;
        case FakturamaModelPackage.BANKACCOUNT_CLASSIFIER_ID:
            modelObject = new BankAccountModelObject();
            break;
        case FakturamaModelPackage.IENTITY_CLASSIFIER_ID:
            modelObject = new IEntityModelObject();
            break;
        case FakturamaModelPackage.ADDRESS_CLASSIFIER_ID:
            modelObject = new AddressModelObject();
            break;
        case FakturamaModelPackage.CONTACT_CLASSIFIER_ID:
            modelObject = new ContactModelObject();
            break;
        case FakturamaModelPackage.CREDITOR_CLASSIFIER_ID:
            modelObject = new CreditorModelObject();
            break;
        case FakturamaModelPackage.CONTACTCATEGORY_CLASSIFIER_ID:
            modelObject = new ContactCategoryModelObject();
            break;
        case FakturamaModelPackage.DOCUMENT_CLASSIFIER_ID:
            modelObject = new DocumentModelObject();
            break;
        case FakturamaModelPackage.DOCUMENTRECEIVER_CLASSIFIER_ID:
            modelObject = new DocumentReceiverModelObject();
            break;
        case FakturamaModelPackage.DOCUMENTITEM_CLASSIFIER_ID:
            modelObject = new DocumentItemModelObject();
            break;
        case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_CLASSIFIER_ID:
            modelObject = new IndividualDocumentInfoModelObject();
            break;
        case FakturamaModelPackage.IDESCRIBABLEENTITY_CLASSIFIER_ID:
            modelObject = new IDescribableEntityModelObject();
            break;
        case FakturamaModelPackage.ITEMACCOUNTTYPE_CLASSIFIER_ID:
            modelObject = new ItemAccountTypeModelObject();
            break;
        case FakturamaModelPackage.PAYMENT_CLASSIFIER_ID:
            modelObject = new PaymentModelObject();
            break;
        case FakturamaModelPackage.PRODUCT_CLASSIFIER_ID:
            modelObject = new ProductModelObject();
            break;
        case FakturamaModelPackage.PRODUCTCATEGORY_CLASSIFIER_ID:
            modelObject = new ProductCategoryModelObject();
            break;
        case FakturamaModelPackage.PRODUCTOPTIONS_CLASSIFIER_ID:
            modelObject = new ProductOptionsModelObject();
            break;
        case FakturamaModelPackage.PRODUCTBLOCKPRICE_CLASSIFIER_ID:
            modelObject = new ProductBlockPriceModelObject();
            break;
        case FakturamaModelPackage.ROLE_CLASSIFIER_ID:
            modelObject = new RoleModelObject();
            break;
        case FakturamaModelPackage.TENANT_CLASSIFIER_ID:
            modelObject = new TenantModelObject();
            break;
        case FakturamaModelPackage.SHIPPING_CLASSIFIER_ID:
            modelObject = new ShippingModelObject();
            break;
        case FakturamaModelPackage.SHIPPINGCATEGORY_CLASSIFIER_ID:
            modelObject = new ShippingCategoryModelObject();
            break;
        case FakturamaModelPackage.TEXTCATEGORY_CLASSIFIER_ID:
            modelObject = new TextCategoryModelObject();
            break;
        case FakturamaModelPackage.TEXTMODULE_CLASSIFIER_ID:
            modelObject = new TextModuleModelObject();
            break;
        case FakturamaModelPackage.USER_CLASSIFIER_ID:
            modelObject = new UserModelObject();
            break;
        case FakturamaModelPackage.USERPROPERTY_CLASSIFIER_ID:
            modelObject = new UserPropertyModelObject();
            break;
        case FakturamaModelPackage.VAT_CLASSIFIER_ID:
            modelObject = new VATModelObject();
            break;
        case FakturamaModelPackage.VATCATEGORY_CLASSIFIER_ID:
            modelObject = new VATCategoryModelObject();
            break;
        case FakturamaModelPackage.CEFACTCODE_CLASSIFIER_ID:
            modelObject = new CEFACTCodeModelObject();
            break;
        case FakturamaModelPackage.WEBSHOP_CLASSIFIER_ID:
            modelObject = new WebShopModelObject();
            break;
        case FakturamaModelPackage.WEBSHOPSTATEMAPPING_CLASSIFIER_ID:
            modelObject = new WebshopStateMappingModelObject();
            break;
        case FakturamaModelPackage.CONFIRMATION_CLASSIFIER_ID:
            modelObject = new ConfirmationModelObject();
            break;
        case FakturamaModelPackage.CREDIT_CLASSIFIER_ID:
            modelObject = new CreditModelObject();
            break;
        case FakturamaModelPackage.DELIVERY_CLASSIFIER_ID:
            modelObject = new DeliveryModelObject();
            break;
        case FakturamaModelPackage.DUNNING_CLASSIFIER_ID:
            modelObject = new DunningModelObject();
            break;
        case FakturamaModelPackage.DEBITOR_CLASSIFIER_ID:
            modelObject = new DebitorModelObject();
            break;
        case FakturamaModelPackage.INVOICE_CLASSIFIER_ID:
            modelObject = new InvoiceModelObject();
            break;
        case FakturamaModelPackage.ITEMLISTTYPECATEGORY_CLASSIFIER_ID:
            modelObject = new ItemListTypeCategoryModelObject();
            break;
        case FakturamaModelPackage.LETTER_CLASSIFIER_ID:
            modelObject = new LetterModelObject();
            break;
        case FakturamaModelPackage.OFFER_CLASSIFIER_ID:
            modelObject = new OfferModelObject();
            break;
        case FakturamaModelPackage.ORDER_CLASSIFIER_ID:
            modelObject = new OrderModelObject();
            break;
        case FakturamaModelPackage.PROFORMA_CLASSIFIER_ID:
            modelObject = new ProformaModelObject();
            break;
        default:
            throw new IllegalArgumentException("The EClass '" + eClass + "' is not defined in this EPackage");
        }
        modelObject.setTarget(adaptee);
        return (ModelObject<T>) modelObject;
    }

    /**
     * Creates a feature map entry instance for a certain EStructuralFeature.
     *
     * @param eFeature
     *            the feature map feature
     * @return the pojo feature map entry
     * @generated
     */
    public Object createFeatureMapEntry(EStructuralFeature eFeature) {
        throw new IllegalArgumentException("The EStructuralFeature '" + eFeature + "' is not a valid feature map in this EPackage");
    }

    /**
     * Wraps a feature map entry pojo in a {@link AbstractModelFeatureMapEntry}.
     * If the feature map entry is null then a new one is created and <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param eFeature
     *            the feature map feature of the object
     * @param adaptee
     *            the pojo feature map entry being wrapped/adapted
     * @return the wrapper {@link ModelFeatureMapEntry}
     * @generated
     */
    public ModelFeatureMapEntry<?> createModelFeatureMapEntry(EStructuralFeature eFeature, Object adaptee) {
        throw new IllegalArgumentException("The EStructuralFeature '" + eFeature + "' is not a valid feature map in this EPackage");
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Voucher
     * @generated
     */
    public Voucher createVoucher() {
        return new Voucher();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         VoucherCategory
     * @generated
     */
    public VoucherCategory createVoucherCategory() {
        return new VoucherCategory();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         VoucherItem
     * @generated
     */
    public VoucherItem createVoucherItem() {
        return new VoucherItem();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         BankAccount
     * @generated
     */
    public BankAccount createBankAccount() {
        return new BankAccount();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Address
     * @generated
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Creditor
     * @generated
     */
    public Creditor createCreditor() {
        return new Creditor();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         ContactCategory
     * @generated
     */
    public ContactCategory createContactCategory() {
        return new ContactCategory();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         DocumentReceiver
     * @generated
     */
    public DocumentReceiver createDocumentReceiver() {
        return new DocumentReceiver();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         DocumentItem
     * @generated
     */
    public DocumentItem createDocumentItem() {
        return new DocumentItem();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         IndividualDocumentInfo
     * @generated
     */
    public IndividualDocumentInfo createIndividualDocumentInfo() {
        return new IndividualDocumentInfo();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         ItemAccountType
     * @generated
     */
    public ItemAccountType createItemAccountType() {
        return new ItemAccountType();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Payment
     * @generated
     */
    public Payment createPayment() {
        return new Payment();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Product
     * @generated
     */
    public Product createProduct() {
        return new Product();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         ProductCategory
     * @generated
     */
    public ProductCategory createProductCategory() {
        return new ProductCategory();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         ProductOptions
     * @generated
     */
    public ProductOptions createProductOptions() {
        return new ProductOptions();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         ProductBlockPrice
     * @generated
     */
    public ProductBlockPrice createProductBlockPrice() {
        return new ProductBlockPrice();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Role
     * @generated
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Tenant
     * @generated
     */
    public Tenant createTenant() {
        return new Tenant();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Shipping
     * @generated
     */
    public Shipping createShipping() {
        return new Shipping();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         ShippingCategory
     * @generated
     */
    public ShippingCategory createShippingCategory() {
        return new ShippingCategory();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         TextCategory
     * @generated
     */
    public TextCategory createTextCategory() {
        return new TextCategory();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         TextModule
     * @generated
     */
    public TextModule createTextModule() {
        return new TextModule();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass User
     * @generated
     */
    public User createUser() {
        return new User();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         UserProperty
     * @generated
     */
    public UserProperty createUserProperty() {
        return new UserProperty();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass VAT
     * @generated
     */
    public VAT createVAT() {
        return new VAT();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         VATCategory
     * @generated
     */
    public VATCategory createVATCategory() {
        return new VATCategory();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         CEFACTCode
     * @generated
     */
    public CEFACTCode createCEFACTCode() {
        return new CEFACTCode();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass WebShop
     * @generated
     */
    public WebShop createWebShop() {
        return new WebShop();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         WebshopStateMapping
     * @generated
     */
    public WebshopStateMapping createWebshopStateMapping() {
        return new WebshopStateMapping();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         Confirmation
     * @generated
     */
    public Confirmation createConfirmation() {
        return new Confirmation();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Credit
     * @generated
     */
    public Credit createCredit() {
        return new Credit();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Delivery
     * @generated
     */
    public Delivery createDelivery() {
        return new Delivery();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Dunning
     * @generated
     */
    public Dunning createDunning() {
        return new Dunning();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Debitor
     * @generated
     */
    public Debitor createDebitor() {
        return new Debitor();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Invoice
     * @generated
     */
    public Invoice createInvoice() {
        return new Invoice();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass
     *         ItemListTypeCategory
     * @generated
     */
    public ItemListTypeCategory createItemListTypeCategory() {
        return new ItemListTypeCategory();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Letter
     * @generated
     */
    public Letter createLetter() {
        return new Letter();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Offer
     * @generated
     */
    public Offer createOffer() {
        return new Offer();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Order
     * @generated
     */
    public Order createOrder() {
        return new Order();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return an instance of the model object representing the EClass Proforma
     * @generated
     */
    public Proforma createProforma() {
        return new Proforma();
    }

    /**
     * Converts an instance of an {@link EDataType} to a String. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param eDataType
     *            the {@link EDataType} defining the type
     * @param value
     *            the object to convert, if the value is null then null is
     *            returned.
     * @generated
     */
    public Object createFromString(EDataType eDataType, String value) {
        switch (eDataType.getClassifierID()) {
        case FakturamaModelPackage.ITEMTYPE_CLASSIFIER_ID:
            return createItemTypeFromString(value);
        case FakturamaModelPackage.BILLINGTYPE_CLASSIFIER_ID:
            return createBillingTypeFromString(value);
        case FakturamaModelPackage.VOUCHERTYPE_CLASSIFIER_ID:
            return createVoucherTypeFromString(value);
        case FakturamaModelPackage.CONTACTTYPE_CLASSIFIER_ID:
            return createContactTypeFromString(value);
        case FakturamaModelPackage.RELIABILITYTYPE_CLASSIFIER_ID:
            return createReliabilityTypeFromString(value);
        case FakturamaModelPackage.SHIPPINGVATTYPE_CLASSIFIER_ID:
            return createShippingVatTypeFromString(value);
        default:
            throw new IllegalArgumentException("The EDatatype '" + eDataType + "' is not defined in this EPackage");
        }
    }

    /**
     * Converts an instance of an {@link EDataType} to a String. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param eDataType
     *            the {@link EDataType} defining the type
     * @param value
     *            the object to convert, if value == null then null is returned
     * @generated
     */
    public String convertToString(EDataType eDataType, Object value) {
        switch (eDataType.getClassifierID()) {
        case FakturamaModelPackage.ITEMTYPE_CLASSIFIER_ID:
            return convertItemTypeToString((ItemType) value);
        case FakturamaModelPackage.BILLINGTYPE_CLASSIFIER_ID:
            return convertBillingTypeToString((BillingType) value);
        case FakturamaModelPackage.VOUCHERTYPE_CLASSIFIER_ID:
            return convertVoucherTypeToString((VoucherType) value);
        case FakturamaModelPackage.CONTACTTYPE_CLASSIFIER_ID:
            return convertContactTypeToString((ContactType) value);
        case FakturamaModelPackage.RELIABILITYTYPE_CLASSIFIER_ID:
            return convertReliabilityTypeToString((ReliabilityType) value);
        case FakturamaModelPackage.SHIPPINGVATTYPE_CLASSIFIER_ID:
            return convertShippingVatTypeToString((ShippingVatType) value);
        default:
            throw new IllegalArgumentException("The EDatatype '" + eDataType + "' is not defined in this EPackage.");
        }
    }

    /**
     * Converts the EDataType: ItemType to a String. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the object to convert
     * @return the String representing the value, if value == null then null is
     *         returned
     * @generated
     */
    public String convertItemTypeToString(ItemType value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /**
     * Creates an instance of the EDataType: ItemType from a String. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the string value to convert to an object
     * @return the instance of the data type, if value == null then null is
     *         returned
     * @generated
     */
    public ItemType createItemTypeFromString(String value) {
        if (value == null) {
            return null;
        }
        return ItemType.get(value);
    }

    /**
     * Converts the EDataType: BillingType to a String. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the object to convert
     * @return the String representing the value, if value == null then null is
     *         returned
     * @generated
     */
    public String convertBillingTypeToString(BillingType value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /**
     * Creates an instance of the EDataType: BillingType from a String. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the string value to convert to an object
     * @return the instance of the data type, if value == null then null is
     *         returned
     * @generated
     */
    public BillingType createBillingTypeFromString(String value) {
        if (value == null) {
            return null;
        }
        return BillingType.get(value);
    }

    /**
     * Converts the EDataType: VoucherType to a String. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the object to convert
     * @return the String representing the value, if value == null then null is
     *         returned
     * @generated
     */
    public String convertVoucherTypeToString(VoucherType value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /**
     * Creates an instance of the EDataType: VoucherType from a String. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the string value to convert to an object
     * @return the instance of the data type, if value == null then null is
     *         returned
     * @generated
     */
    public VoucherType createVoucherTypeFromString(String value) {
        if (value == null) {
            return null;
        }
        return VoucherType.get(value);
    }

    /**
     * Converts the EDataType: ContactType to a String. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the object to convert
     * @return the String representing the value, if value == null then null is
     *         returned
     * @generated
     */
    public String convertContactTypeToString(ContactType value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /**
     * Creates an instance of the EDataType: ContactType from a String. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the string value to convert to an object
     * @return the instance of the data type, if value == null then null is
     *         returned
     * @generated
     */
    public ContactType createContactTypeFromString(String value) {
        if (value == null) {
            return null;
        }
        return ContactType.get(value);
    }

    /**
     * Converts the EDataType: ReliabilityType to a String. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @param value
     *            the object to convert
     * @return the String representing the value, if value == null then null is
     *         returned
     * @generated
     */
    public String convertReliabilityTypeToString(ReliabilityType value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /**
     * Creates an instance of the EDataType: ReliabilityType from a String. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the string value to convert to an object
     * @return the instance of the data type, if value == null then null is
     *         returned
     * @generated
     */
    public ReliabilityType createReliabilityTypeFromString(String value) {
        if (value == null) {
            return null;
        }
        return ReliabilityType.get(value);
    }

    /**
     * Converts the EDataType: ShippingVatType to a String. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @param value
     *            the object to convert
     * @return the String representing the value, if value == null then null is
     *         returned
     * @generated
     */
    public String convertShippingVatTypeToString(ShippingVatType value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /**
     * Creates an instance of the EDataType: ShippingVatType from a String. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the string value to convert to an object
     * @return the instance of the data type, if value == null then null is
     *         returned
     * @generated
     */
    public ShippingVatType createShippingVatTypeFromString(String value) {
        if (value == null) {
            return null;
        }
        return ShippingVatType.get(value);
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>AbstractCategory</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class AbstractCategoryModelObject<E extends AbstractCategory> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getAbstractCategoryEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return AbstractCategory.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.ABSTRACTCATEGORY_PARENT_FEATURE_ID:
                return getTarget().getParent();
            case FakturamaModelPackage.ABSTRACTCATEGORY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.ABSTRACTCATEGORY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.ABSTRACTCATEGORY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.ABSTRACTCATEGORY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.ABSTRACTCATEGORY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.ABSTRACTCATEGORY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.ABSTRACTCATEGORY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.ABSTRACTCATEGORY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.ABSTRACTCATEGORY_PARENT_FEATURE_ID:
                getTarget().setParent((AbstractCategory) value);
                return;
            case FakturamaModelPackage.ABSTRACTCATEGORY_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.ABSTRACTCATEGORY_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.ABSTRACTCATEGORY_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.ABSTRACTCATEGORY_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.ABSTRACTCATEGORY_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.ABSTRACTCATEGORY_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.ABSTRACTCATEGORY_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.ABSTRACTCATEGORY_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Voucher</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class VoucherModelObject<E extends Voucher> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getVoucherEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Voucher.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.VOUCHER_ACCOUNT_FEATURE_ID:
                return getTarget().getAccount();
            case FakturamaModelPackage.VOUCHER_VOUCHERDATE_FEATURE_ID:
                return getTarget().getVoucherDate();
            case FakturamaModelPackage.VOUCHER_DISCOUNTED_FEATURE_ID:
                return getTarget().getDiscounted();
            case FakturamaModelPackage.VOUCHER_DONOTBOOK_FEATURE_ID:
                return getTarget().getDoNotBook();
            case FakturamaModelPackage.VOUCHER_DOCUMENTNUMBER_FEATURE_ID:
                return getTarget().getDocumentNumber();
            case FakturamaModelPackage.VOUCHER_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.VOUCHER_VOUCHERNUMBER_FEATURE_ID:
                return getTarget().getVoucherNumber();
            case FakturamaModelPackage.VOUCHER_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.VOUCHER_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.VOUCHER_VOUCHERTYPE_FEATURE_ID:
                return getTarget().getVoucherType();
            case FakturamaModelPackage.VOUCHER_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.VOUCHER_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.VOUCHER_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.VOUCHER_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.VOUCHER_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.VOUCHER_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.VOUCHER_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.VOUCHER_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @SuppressWarnings("unchecked")
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.VOUCHER_ACCOUNT_FEATURE_ID:
                getTarget().setAccount((VoucherCategory) value);
                return;
            case FakturamaModelPackage.VOUCHER_VOUCHERDATE_FEATURE_ID:
                getTarget().setVoucherDate((Date) value);
                return;
            case FakturamaModelPackage.VOUCHER_DISCOUNTED_FEATURE_ID:
                getTarget().setDiscounted((Boolean) value);
                return;
            case FakturamaModelPackage.VOUCHER_DONOTBOOK_FEATURE_ID:
                getTarget().setDoNotBook((Boolean) value);
                return;
            case FakturamaModelPackage.VOUCHER_DOCUMENTNUMBER_FEATURE_ID:
                getTarget().setDocumentNumber((String) value);
                return;
            case FakturamaModelPackage.VOUCHER_ITEMS_FEATURE_ID:
                getTarget().setItems((List<VoucherItem>) value);
                return;
            case FakturamaModelPackage.VOUCHER_VOUCHERNUMBER_FEATURE_ID:
                getTarget().setVoucherNumber((String) value);
                return;
            case FakturamaModelPackage.VOUCHER_PAIDVALUE_FEATURE_ID:
                getTarget().setPaidValue((Double) value);
                return;
            case FakturamaModelPackage.VOUCHER_TOTALVALUE_FEATURE_ID:
                getTarget().setTotalValue((Double) value);
                return;
            case FakturamaModelPackage.VOUCHER_VOUCHERTYPE_FEATURE_ID:
                getTarget().setVoucherType((VoucherType) value);
                return;
            case FakturamaModelPackage.VOUCHER_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.VOUCHER_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.VOUCHER_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.VOUCHER_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.VOUCHER_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.VOUCHER_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.VOUCHER_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.VOUCHER_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.VOUCHER_ITEMS_FEATURE_ID:
                return getTarget().addToItems((VoucherItem) value);

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.VOUCHER_ITEMS_FEATURE_ID:
                return getTarget().removeFromItems((VoucherItem) value);

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>VoucherCategory</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class VoucherCategoryModelObject<E extends VoucherCategory> extends AbstractCategoryModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getVoucherCategoryEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return VoucherCategory.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.VOUCHERCATEGORY_PARENT_FEATURE_ID:
                return getTarget().getParent();
            case FakturamaModelPackage.VOUCHERCATEGORY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.VOUCHERCATEGORY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.VOUCHERCATEGORY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.VOUCHERCATEGORY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.VOUCHERCATEGORY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.VOUCHERCATEGORY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.VOUCHERCATEGORY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.VOUCHERCATEGORY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>VoucherItem</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class VoucherItemModelObject<E extends VoucherItem> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getVoucherItemEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return VoucherItem.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.VOUCHERITEM_ACCOUNTTYPE_FEATURE_ID:
                return getTarget().getAccountType();
            case FakturamaModelPackage.VOUCHERITEM_VAT_FEATURE_ID:
                return getTarget().getVat();
            case FakturamaModelPackage.VOUCHERITEM_PRICE_FEATURE_ID:
                return getTarget().getPrice();
            case FakturamaModelPackage.VOUCHERITEM_POSNR_FEATURE_ID:
                return getTarget().getPosNr();
            case FakturamaModelPackage.VOUCHERITEM_ITEMVOUCHERTYPE_FEATURE_ID:
                return getTarget().getItemVoucherType();
            case FakturamaModelPackage.VOUCHERITEM_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.VOUCHERITEM_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.VOUCHERITEM_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.VOUCHERITEM_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.VOUCHERITEM_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.VOUCHERITEM_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.VOUCHERITEM_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.VOUCHERITEM_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.VOUCHERITEM_ACCOUNTTYPE_FEATURE_ID:
                getTarget().setAccountType((ItemAccountType) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_VAT_FEATURE_ID:
                getTarget().setVat((VAT) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_PRICE_FEATURE_ID:
                getTarget().setPrice((Double) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_POSNR_FEATURE_ID:
                getTarget().setPosNr((Integer) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_ITEMVOUCHERTYPE_FEATURE_ID:
                getTarget().setItemVoucherType((VoucherType) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.VOUCHERITEM_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>BankAccount</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class BankAccountModelObject<E extends BankAccount> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getBankAccountEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return BankAccount.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.BANKACCOUNT_ACCOUNTHOLDER_FEATURE_ID:
                return getTarget().getAccountHolder();
            case FakturamaModelPackage.BANKACCOUNT_BANKCODE_FEATURE_ID:
                return getTarget().getBankCode();
            case FakturamaModelPackage.BANKACCOUNT_BANKNAME_FEATURE_ID:
                return getTarget().getBankName();
            case FakturamaModelPackage.BANKACCOUNT_BIC_FEATURE_ID:
                return getTarget().getBic();
            case FakturamaModelPackage.BANKACCOUNT_IBAN_FEATURE_ID:
                return getTarget().getIban();
            case FakturamaModelPackage.BANKACCOUNT_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.BANKACCOUNT_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.BANKACCOUNT_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.BANKACCOUNT_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.BANKACCOUNT_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.BANKACCOUNT_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.BANKACCOUNT_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.BANKACCOUNT_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.BANKACCOUNT_ACCOUNTHOLDER_FEATURE_ID:
                getTarget().setAccountHolder((String) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_BANKCODE_FEATURE_ID:
                getTarget().setBankCode((Integer) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_BANKNAME_FEATURE_ID:
                getTarget().setBankName((String) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_BIC_FEATURE_ID:
                getTarget().setBic((String) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_IBAN_FEATURE_ID:
                getTarget().setIban((String) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.BANKACCOUNT_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>IEntity</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class IEntityModelObject<E extends IEntity> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getIEntityEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return IEntity.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.IENTITY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.IENTITY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.IENTITY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.IENTITY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.IENTITY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.IENTITY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.IENTITY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.IENTITY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.IENTITY_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.IENTITY_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.IENTITY_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.IENTITY_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.IENTITY_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.IENTITY_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.IENTITY_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.IENTITY_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Address</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class AddressModelObject<E extends Address> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getAddressEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Address.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.ADDRESS_STREET_FEATURE_ID:
                return getTarget().getStreet();
            case FakturamaModelPackage.ADDRESS_CITYADDON_FEATURE_ID:
                return getTarget().getCityAddon();
            case FakturamaModelPackage.ADDRESS_CITY_FEATURE_ID:
                return getTarget().getCity();
            case FakturamaModelPackage.ADDRESS_ZIP_FEATURE_ID:
                return getTarget().getZip();
            case FakturamaModelPackage.ADDRESS_COUNTRYCODE_FEATURE_ID:
                return getTarget().getCountryCode();
            case FakturamaModelPackage.ADDRESS_CONTACT_FEATURE_ID:
                return getTarget().getContact();
            case FakturamaModelPackage.ADDRESS_LOCALCONSULTANT_FEATURE_ID:
                return getTarget().getLocalConsultant();
            case FakturamaModelPackage.ADDRESS_EMAIL_FEATURE_ID:
                return getTarget().getEmail();
            case FakturamaModelPackage.ADDRESS_MOBILE_FEATURE_ID:
                return getTarget().getMobile();
            case FakturamaModelPackage.ADDRESS_PHONE_FEATURE_ID:
                return getTarget().getPhone();
            case FakturamaModelPackage.ADDRESS_ADDITIONALPHONE_FEATURE_ID:
                return getTarget().getAdditionalPhone();
            case FakturamaModelPackage.ADDRESS_FAX_FEATURE_ID:
                return getTarget().getFax();
            case FakturamaModelPackage.ADDRESS_CONTACTTYPES_FEATURE_ID:
                return getTarget().getContactTypes();
            case FakturamaModelPackage.ADDRESS_ADDRESSADDON_FEATURE_ID:
                return getTarget().getAddressAddon();
            case FakturamaModelPackage.ADDRESS_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.ADDRESS_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.ADDRESS_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.ADDRESS_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.ADDRESS_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.ADDRESS_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.ADDRESS_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.ADDRESS_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @SuppressWarnings("unchecked")
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.ADDRESS_STREET_FEATURE_ID:
                getTarget().setStreet((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_CITYADDON_FEATURE_ID:
                getTarget().setCityAddon((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_CITY_FEATURE_ID:
                getTarget().setCity((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_ZIP_FEATURE_ID:
                getTarget().setZip((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_COUNTRYCODE_FEATURE_ID:
                getTarget().setCountryCode((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_CONTACT_FEATURE_ID:
                getTarget().setContact((Contact) value);
                return;
            case FakturamaModelPackage.ADDRESS_LOCALCONSULTANT_FEATURE_ID:
                getTarget().setLocalConsultant((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_EMAIL_FEATURE_ID:
                getTarget().setEmail((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_MOBILE_FEATURE_ID:
                getTarget().setMobile((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_PHONE_FEATURE_ID:
                getTarget().setPhone((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_ADDITIONALPHONE_FEATURE_ID:
                getTarget().setAdditionalPhone((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_FAX_FEATURE_ID:
                getTarget().setFax((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_CONTACTTYPES_FEATURE_ID:
                getTarget().setContactTypes((List<ContactType>) value);
                return;
            case FakturamaModelPackage.ADDRESS_ADDRESSADDON_FEATURE_ID:
                getTarget().setAddressAddon((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.ADDRESS_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.ADDRESS_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.ADDRESS_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.ADDRESS_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.ADDRESS_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.ADDRESS_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.ADDRESS_CONTACTTYPES_FEATURE_ID:
                return getTarget().getContactTypes().add((ContactType) value);

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.ADDRESS_CONTACTTYPES_FEATURE_ID:
                return getTarget().getContactTypes().remove(value);

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Contact</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ContactModelObject<E extends Contact> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getContactEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Contact.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.CONTACT_ALIAS_FEATURE_ID:
                return getTarget().getAlias();
            case FakturamaModelPackage.CONTACT_CATEGORIES_FEATURE_ID:
                return getTarget().getCategories();
            case FakturamaModelPackage.CONTACT_COMPANY_FEATURE_ID:
                return getTarget().getCompany();
            case FakturamaModelPackage.CONTACT_CUSTOMERNUMBER_FEATURE_ID:
                return getTarget().getCustomerNumber();
            case FakturamaModelPackage.CONTACT_TITLE_FEATURE_ID:
                return getTarget().getTitle();
            case FakturamaModelPackage.CONTACT_FIRSTNAME_FEATURE_ID:
                return getTarget().getFirstName();
            case FakturamaModelPackage.CONTACT_GENDER_FEATURE_ID:
                return getTarget().getGender();
            case FakturamaModelPackage.CONTACT_BIRTHDAY_FEATURE_ID:
                return getTarget().getBirthday();
            case FakturamaModelPackage.CONTACT_ADDRESSES_FEATURE_ID:
                return getTarget().getAddresses();
            case FakturamaModelPackage.CONTACT_DISCOUNT_FEATURE_ID:
                return getTarget().getDiscount();
            case FakturamaModelPackage.CONTACT_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.CONTACT_RELIABILITY_FEATURE_ID:
                return getTarget().getReliability();
            case FakturamaModelPackage.CONTACT_USENETGROSS_FEATURE_ID:
                return getTarget().getUseNetGross();
            case FakturamaModelPackage.CONTACT_VATNUMBER_FEATURE_ID:
                return getTarget().getVatNumber();
            case FakturamaModelPackage.CONTACT_VATNUMBERVALID_FEATURE_ID:
                return getTarget().getVatNumberValid();
            case FakturamaModelPackage.CONTACT_WEBSITE_FEATURE_ID:
                return getTarget().getWebsite();
            case FakturamaModelPackage.CONTACT_WEBSHOPNAME_FEATURE_ID:
                return getTarget().getWebshopName();
            case FakturamaModelPackage.CONTACT_SUPPLIERNUMBER_FEATURE_ID:
                return getTarget().getSupplierNumber();
            case FakturamaModelPackage.CONTACT_GLN_FEATURE_ID:
                return getTarget().getGln();
            case FakturamaModelPackage.CONTACT_MANDATEREFERENCE_FEATURE_ID:
                return getTarget().getMandateReference();
            case FakturamaModelPackage.CONTACT_BANKACCOUNT_FEATURE_ID:
                return getTarget().getBankAccount();
            case FakturamaModelPackage.CONTACT_USESALESEQUALIZATIONTAX_FEATURE_ID:
                return getTarget().getUseSalesEqualizationTax();
            case FakturamaModelPackage.CONTACT_NOTE_FEATURE_ID:
                return getTarget().getNote();
            case FakturamaModelPackage.CONTACT_REGISTERNUMBER_FEATURE_ID:
                return getTarget().getRegisterNumber();
            case FakturamaModelPackage.CONTACT_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.CONTACT_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.CONTACT_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.CONTACT_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.CONTACT_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.CONTACT_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.CONTACT_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.CONTACT_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @SuppressWarnings("unchecked")
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.CONTACT_ALIAS_FEATURE_ID:
                getTarget().setAlias((String) value);
                return;
            case FakturamaModelPackage.CONTACT_CATEGORIES_FEATURE_ID:
                getTarget().setCategories((ContactCategory) value);
                return;
            case FakturamaModelPackage.CONTACT_COMPANY_FEATURE_ID:
                getTarget().setCompany((String) value);
                return;
            case FakturamaModelPackage.CONTACT_CUSTOMERNUMBER_FEATURE_ID:
                getTarget().setCustomerNumber((String) value);
                return;
            case FakturamaModelPackage.CONTACT_TITLE_FEATURE_ID:
                getTarget().setTitle((String) value);
                return;
            case FakturamaModelPackage.CONTACT_FIRSTNAME_FEATURE_ID:
                getTarget().setFirstName((String) value);
                return;
            case FakturamaModelPackage.CONTACT_GENDER_FEATURE_ID:
                getTarget().setGender((Integer) value);
                return;
            case FakturamaModelPackage.CONTACT_BIRTHDAY_FEATURE_ID:
                getTarget().setBirthday((Date) value);
                return;
            case FakturamaModelPackage.CONTACT_ADDRESSES_FEATURE_ID:
                getTarget().setAddresses((List<Address>) value);
                return;
            case FakturamaModelPackage.CONTACT_DISCOUNT_FEATURE_ID:
                getTarget().setDiscount((Double) value);
                return;
            case FakturamaModelPackage.CONTACT_PAYMENT_FEATURE_ID:
                getTarget().setPayment((Payment) value);
                return;
            case FakturamaModelPackage.CONTACT_RELIABILITY_FEATURE_ID:
                getTarget().setReliability((ReliabilityType) value);
                return;
            case FakturamaModelPackage.CONTACT_USENETGROSS_FEATURE_ID:
                getTarget().setUseNetGross((Short) value);
                return;
            case FakturamaModelPackage.CONTACT_VATNUMBER_FEATURE_ID:
                getTarget().setVatNumber((String) value);
                return;
            case FakturamaModelPackage.CONTACT_VATNUMBERVALID_FEATURE_ID:
                getTarget().setVatNumberValid((Boolean) value);
                return;
            case FakturamaModelPackage.CONTACT_WEBSITE_FEATURE_ID:
                getTarget().setWebsite((String) value);
                return;
            case FakturamaModelPackage.CONTACT_WEBSHOPNAME_FEATURE_ID:
                getTarget().setWebshopName((String) value);
                return;
            case FakturamaModelPackage.CONTACT_SUPPLIERNUMBER_FEATURE_ID:
                getTarget().setSupplierNumber((String) value);
                return;
            case FakturamaModelPackage.CONTACT_GLN_FEATURE_ID:
                getTarget().setGln((Long) value);
                return;
            case FakturamaModelPackage.CONTACT_MANDATEREFERENCE_FEATURE_ID:
                getTarget().setMandateReference((String) value);
                return;
            case FakturamaModelPackage.CONTACT_BANKACCOUNT_FEATURE_ID:
                getTarget().setBankAccount((BankAccount) value);
                return;
            case FakturamaModelPackage.CONTACT_USESALESEQUALIZATIONTAX_FEATURE_ID:
                getTarget().setUseSalesEqualizationTax((Boolean) value);
                return;
            case FakturamaModelPackage.CONTACT_NOTE_FEATURE_ID:
                getTarget().setNote((String) value);
                return;
            case FakturamaModelPackage.CONTACT_REGISTERNUMBER_FEATURE_ID:
                getTarget().setRegisterNumber((String) value);
                return;
            case FakturamaModelPackage.CONTACT_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.CONTACT_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.CONTACT_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.CONTACT_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.CONTACT_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.CONTACT_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.CONTACT_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.CONTACT_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.CONTACT_ADDRESSES_FEATURE_ID:
                return getTarget().addToAddresses((Address) value);

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.CONTACT_ADDRESSES_FEATURE_ID:
                return getTarget().removeFromAddresses((Address) value);

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Creditor</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class CreditorModelObject<E extends Creditor> extends ContactModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getCreditorEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Creditor.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.CREDITOR_ALIAS_FEATURE_ID:
                return getTarget().getAlias();
            case FakturamaModelPackage.CREDITOR_CATEGORIES_FEATURE_ID:
                return getTarget().getCategories();
            case FakturamaModelPackage.CREDITOR_COMPANY_FEATURE_ID:
                return getTarget().getCompany();
            case FakturamaModelPackage.CREDITOR_CUSTOMERNUMBER_FEATURE_ID:
                return getTarget().getCustomerNumber();
            case FakturamaModelPackage.CREDITOR_TITLE_FEATURE_ID:
                return getTarget().getTitle();
            case FakturamaModelPackage.CREDITOR_FIRSTNAME_FEATURE_ID:
                return getTarget().getFirstName();
            case FakturamaModelPackage.CREDITOR_GENDER_FEATURE_ID:
                return getTarget().getGender();
            case FakturamaModelPackage.CREDITOR_BIRTHDAY_FEATURE_ID:
                return getTarget().getBirthday();
            case FakturamaModelPackage.CREDITOR_ADDRESSES_FEATURE_ID:
                return getTarget().getAddresses();
            case FakturamaModelPackage.CREDITOR_DISCOUNT_FEATURE_ID:
                return getTarget().getDiscount();
            case FakturamaModelPackage.CREDITOR_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.CREDITOR_RELIABILITY_FEATURE_ID:
                return getTarget().getReliability();
            case FakturamaModelPackage.CREDITOR_USENETGROSS_FEATURE_ID:
                return getTarget().getUseNetGross();
            case FakturamaModelPackage.CREDITOR_VATNUMBER_FEATURE_ID:
                return getTarget().getVatNumber();
            case FakturamaModelPackage.CREDITOR_VATNUMBERVALID_FEATURE_ID:
                return getTarget().getVatNumberValid();
            case FakturamaModelPackage.CREDITOR_WEBSITE_FEATURE_ID:
                return getTarget().getWebsite();
            case FakturamaModelPackage.CREDITOR_WEBSHOPNAME_FEATURE_ID:
                return getTarget().getWebshopName();
            case FakturamaModelPackage.CREDITOR_SUPPLIERNUMBER_FEATURE_ID:
                return getTarget().getSupplierNumber();
            case FakturamaModelPackage.CREDITOR_GLN_FEATURE_ID:
                return getTarget().getGln();
            case FakturamaModelPackage.CREDITOR_MANDATEREFERENCE_FEATURE_ID:
                return getTarget().getMandateReference();
            case FakturamaModelPackage.CREDITOR_BANKACCOUNT_FEATURE_ID:
                return getTarget().getBankAccount();
            case FakturamaModelPackage.CREDITOR_USESALESEQUALIZATIONTAX_FEATURE_ID:
                return getTarget().getUseSalesEqualizationTax();
            case FakturamaModelPackage.CREDITOR_NOTE_FEATURE_ID:
                return getTarget().getNote();
            case FakturamaModelPackage.CREDITOR_REGISTERNUMBER_FEATURE_ID:
                return getTarget().getRegisterNumber();
            case FakturamaModelPackage.CREDITOR_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.CREDITOR_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.CREDITOR_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.CREDITOR_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.CREDITOR_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.CREDITOR_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.CREDITOR_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.CREDITOR_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>ContactCategory</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ContactCategoryModelObject<E extends ContactCategory> extends AbstractCategoryModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getContactCategoryEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return ContactCategory.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.CONTACTCATEGORY_PARENT_FEATURE_ID:
                return getTarget().getParent();
            case FakturamaModelPackage.CONTACTCATEGORY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.CONTACTCATEGORY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.CONTACTCATEGORY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.CONTACTCATEGORY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.CONTACTCATEGORY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.CONTACTCATEGORY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.CONTACTCATEGORY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.CONTACTCATEGORY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Document</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class DocumentModelObject<E extends Document> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getDocumentEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Document.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.DOCUMENT_ADDITIONALINFO_FEATURE_ID:
                return getTarget().getAdditionalInfo();
            case FakturamaModelPackage.DOCUMENT_ADDRESSFIRSTLINE_FEATURE_ID:
                return getTarget().getAddressFirstLine();
            case FakturamaModelPackage.DOCUMENT_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.DOCUMENT_CUSTOMERREF_FEATURE_ID:
                return getTarget().getCustomerRef();
            case FakturamaModelPackage.DOCUMENT_DEPOSIT_FEATURE_ID:
                return getTarget().getDeposit();
            case FakturamaModelPackage.DOCUMENT_DOCUMENTDATE_FEATURE_ID:
                return getTarget().getDocumentDate();
            case FakturamaModelPackage.DOCUMENT_DUEDAYS_FEATURE_ID:
                return getTarget().getDueDays();
            case FakturamaModelPackage.DOCUMENT_INVOICEREFERENCE_FEATURE_ID:
                return getTarget().getInvoiceReference();
            case FakturamaModelPackage.DOCUMENT_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.DOCUMENT_ITEMSREBATE_FEATURE_ID:
                return getTarget().getItemsRebate();
            case FakturamaModelPackage.DOCUMENT_MESSAGE_FEATURE_ID:
                return getTarget().getMessage();
            case FakturamaModelPackage.DOCUMENT_MESSAGE2_FEATURE_ID:
                return getTarget().getMessage2();
            case FakturamaModelPackage.DOCUMENT_MESSAGE3_FEATURE_ID:
                return getTarget().getMessage3();
            case FakturamaModelPackage.DOCUMENT_NETGROSS_FEATURE_ID:
                return getTarget().getNetGross();
            case FakturamaModelPackage.DOCUMENT_NOVATREFERENCE_FEATURE_ID:
                return getTarget().getNoVatReference();
            case FakturamaModelPackage.DOCUMENT_ODTPATH_FEATURE_ID:
                return getTarget().getOdtPath();
            case FakturamaModelPackage.DOCUMENT_ORDERDATE_FEATURE_ID:
                return getTarget().getOrderDate();
            case FakturamaModelPackage.DOCUMENT_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.DOCUMENT_PAID_FEATURE_ID:
                return getTarget().getPaid();
            case FakturamaModelPackage.DOCUMENT_PAYDATE_FEATURE_ID:
                return getTarget().getPayDate();
            case FakturamaModelPackage.DOCUMENT_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.DOCUMENT_PDFPATH_FEATURE_ID:
                return getTarget().getPdfPath();
            case FakturamaModelPackage.DOCUMENT_PRINTED_FEATURE_ID:
                return getTarget().getPrinted();
            case FakturamaModelPackage.DOCUMENT_PRINTTEMPLATE_FEATURE_ID:
                return getTarget().getPrintTemplate();
            case FakturamaModelPackage.DOCUMENT_PROGRESS_FEATURE_ID:
                return getTarget().getProgress();
            case FakturamaModelPackage.DOCUMENT_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver();
            case FakturamaModelPackage.DOCUMENT_SERVICEDATE_FEATURE_ID:
                return getTarget().getServiceDate();
            case FakturamaModelPackage.DOCUMENT_SHIPPING_FEATURE_ID:
                return getTarget().getShipping();
            case FakturamaModelPackage.DOCUMENT_SHIPPINGAUTOVAT_FEATURE_ID:
                return getTarget().getShippingAutoVat();
            case FakturamaModelPackage.DOCUMENT_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.DOCUMENT_SOURCEDOCUMENT_FEATURE_ID:
                return getTarget().getSourceDocument();
            case FakturamaModelPackage.DOCUMENT_TARA_FEATURE_ID:
                return getTarget().getTara();
            case FakturamaModelPackage.DOCUMENT_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.DOCUMENT_TRANSACTIONID_FEATURE_ID:
                return getTarget().getTransactionId();
            case FakturamaModelPackage.DOCUMENT_WEBSHOPDATE_FEATURE_ID:
                return getTarget().getWebshopDate();
            case FakturamaModelPackage.DOCUMENT_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.DOCUMENT_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.DOCUMENT_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.DOCUMENT_VERSION_FEATURE_ID:
                return getTarget().getVersion();
            case FakturamaModelPackage.DOCUMENT_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.DOCUMENT_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.DOCUMENT_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.DOCUMENT_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.DOCUMENT_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.DOCUMENT_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.DOCUMENT_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.DOCUMENT_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @SuppressWarnings("unchecked")
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.DOCUMENT_ADDITIONALINFO_FEATURE_ID:
                getTarget().setAdditionalInfo((IndividualDocumentInfo) value);
                return;
            case FakturamaModelPackage.DOCUMENT_ADDRESSFIRSTLINE_FEATURE_ID:
                getTarget().setAddressFirstLine((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_BILLINGTYPE_FEATURE_ID:
                getTarget().setBillingType((BillingType) value);
                return;
            case FakturamaModelPackage.DOCUMENT_CUSTOMERREF_FEATURE_ID:
                getTarget().setCustomerRef((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_DEPOSIT_FEATURE_ID:
                getTarget().setDeposit((Boolean) value);
                return;
            case FakturamaModelPackage.DOCUMENT_DOCUMENTDATE_FEATURE_ID:
                getTarget().setDocumentDate((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENT_DUEDAYS_FEATURE_ID:
                getTarget().setDueDays((Integer) value);
                return;
            case FakturamaModelPackage.DOCUMENT_INVOICEREFERENCE_FEATURE_ID:
                getTarget().setInvoiceReference((Invoice) value);
                return;
            case FakturamaModelPackage.DOCUMENT_ITEMS_FEATURE_ID:
                getTarget().setItems((List<DocumentItem>) value);
                return;
            case FakturamaModelPackage.DOCUMENT_ITEMSREBATE_FEATURE_ID:
                getTarget().setItemsRebate((Double) value);
                return;
            case FakturamaModelPackage.DOCUMENT_MESSAGE_FEATURE_ID:
                getTarget().setMessage((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_MESSAGE2_FEATURE_ID:
                getTarget().setMessage2((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_MESSAGE3_FEATURE_ID:
                getTarget().setMessage3((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_NETGROSS_FEATURE_ID:
                getTarget().setNetGross((Integer) value);
                return;
            case FakturamaModelPackage.DOCUMENT_NOVATREFERENCE_FEATURE_ID:
                getTarget().setNoVatReference((VAT) value);
                return;
            case FakturamaModelPackage.DOCUMENT_ODTPATH_FEATURE_ID:
                getTarget().setOdtPath((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_ORDERDATE_FEATURE_ID:
                getTarget().setOrderDate((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENT_PAIDVALUE_FEATURE_ID:
                getTarget().setPaidValue((Double) value);
                return;
            case FakturamaModelPackage.DOCUMENT_PAID_FEATURE_ID:
                getTarget().setPaid((Boolean) value);
                return;
            case FakturamaModelPackage.DOCUMENT_PAYDATE_FEATURE_ID:
                getTarget().setPayDate((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENT_PAYMENT_FEATURE_ID:
                getTarget().setPayment((Payment) value);
                return;
            case FakturamaModelPackage.DOCUMENT_PDFPATH_FEATURE_ID:
                getTarget().setPdfPath((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_PRINTED_FEATURE_ID:
                getTarget().setPrinted((Boolean) value);
                return;
            case FakturamaModelPackage.DOCUMENT_PRINTTEMPLATE_FEATURE_ID:
                getTarget().setPrintTemplate((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_PROGRESS_FEATURE_ID:
                getTarget().setProgress((Integer) value);
                return;
            case FakturamaModelPackage.DOCUMENT_RECEIVER_FEATURE_ID:
                getTarget().setReceiver((List<DocumentReceiver>) value);
                return;
            case FakturamaModelPackage.DOCUMENT_SERVICEDATE_FEATURE_ID:
                getTarget().setServiceDate((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENT_SHIPPING_FEATURE_ID:
                getTarget().setShipping((Shipping) value);
                return;
            case FakturamaModelPackage.DOCUMENT_SHIPPINGAUTOVAT_FEATURE_ID:
                getTarget().setShippingAutoVat((ShippingVatType) value);
                return;
            case FakturamaModelPackage.DOCUMENT_SHIPPINGVALUE_FEATURE_ID:
                getTarget().setShippingValue((Double) value);
                return;
            case FakturamaModelPackage.DOCUMENT_SOURCEDOCUMENT_FEATURE_ID:
                getTarget().setSourceDocument((Document) value);
                return;
            case FakturamaModelPackage.DOCUMENT_TARA_FEATURE_ID:
                getTarget().setTara((Double) value);
                return;
            case FakturamaModelPackage.DOCUMENT_TOTALVALUE_FEATURE_ID:
                getTarget().setTotalValue((Double) value);
                return;
            case FakturamaModelPackage.DOCUMENT_TRANSACTIONID_FEATURE_ID:
                getTarget().setTransactionId((Integer) value);
                return;
            case FakturamaModelPackage.DOCUMENT_WEBSHOPDATE_FEATURE_ID:
                getTarget().setWebshopDate((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENT_WEBSHOPID_FEATURE_ID:
                getTarget().setWebshopId((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_VESTINGPERIODSTART_FEATURE_ID:
                getTarget().setVestingPeriodStart((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENT_VESTINGPERIODEND_FEATURE_ID:
                getTarget().setVestingPeriodEnd((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENT_VERSION_FEATURE_ID:
                getTarget().setVersion((Integer) value);
                return;
            case FakturamaModelPackage.DOCUMENT_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENT_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.DOCUMENT_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENT_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.DOCUMENT_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.DOCUMENT_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENT_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.DOCUMENT_ITEMS_FEATURE_ID:
                return getTarget().addToItems((DocumentItem) value);

            case FakturamaModelPackage.DOCUMENT_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver().add((DocumentReceiver) value);

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.DOCUMENT_ITEMS_FEATURE_ID:
                return getTarget().removeFromItems((DocumentItem) value);

            case FakturamaModelPackage.DOCUMENT_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver().remove(value);

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>DocumentReceiver</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class DocumentReceiverModelObject<E extends DocumentReceiver> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getDocumentReceiverEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return DocumentReceiver.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.DOCUMENTRECEIVER_ALIAS_FEATURE_ID:
                return getTarget().getAlias();
            case FakturamaModelPackage.DOCUMENTRECEIVER_CUSTOMERNUMBER_FEATURE_ID:
                return getTarget().getCustomerNumber();
            case FakturamaModelPackage.DOCUMENTRECEIVER_CONSULTANT_FEATURE_ID:
                return getTarget().getConsultant();
            case FakturamaModelPackage.DOCUMENTRECEIVER_COMPANY_FEATURE_ID:
                return getTarget().getCompany();
            case FakturamaModelPackage.DOCUMENTRECEIVER_TITLE_FEATURE_ID:
                return getTarget().getTitle();
            case FakturamaModelPackage.DOCUMENTRECEIVER_FIRSTNAME_FEATURE_ID:
                return getTarget().getFirstName();
            case FakturamaModelPackage.DOCUMENTRECEIVER_STREET_FEATURE_ID:
                return getTarget().getStreet();
            case FakturamaModelPackage.DOCUMENTRECEIVER_CITYADDON_FEATURE_ID:
                return getTarget().getCityAddon();
            case FakturamaModelPackage.DOCUMENTRECEIVER_CITY_FEATURE_ID:
                return getTarget().getCity();
            case FakturamaModelPackage.DOCUMENTRECEIVER_ZIP_FEATURE_ID:
                return getTarget().getZip();
            case FakturamaModelPackage.DOCUMENTRECEIVER_COUNTRYCODE_FEATURE_ID:
                return getTarget().getCountryCode();
            case FakturamaModelPackage.DOCUMENTRECEIVER_MANUALADDRESS_FEATURE_ID:
                return getTarget().getManualAddress();
            case FakturamaModelPackage.DOCUMENTRECEIVER_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.DOCUMENTRECEIVER_ORIGINCONTACTID_FEATURE_ID:
                return getTarget().getOriginContactId();
            case FakturamaModelPackage.DOCUMENTRECEIVER_ORIGINADDRESSID_FEATURE_ID:
                return getTarget().getOriginAddressId();
            case FakturamaModelPackage.DOCUMENTRECEIVER_GENDER_FEATURE_ID:
                return getTarget().getGender();
            case FakturamaModelPackage.DOCUMENTRECEIVER_EMAIL_FEATURE_ID:
                return getTarget().getEmail();
            case FakturamaModelPackage.DOCUMENTRECEIVER_MOBILE_FEATURE_ID:
                return getTarget().getMobile();
            case FakturamaModelPackage.DOCUMENTRECEIVER_PHONE_FEATURE_ID:
                return getTarget().getPhone();
            case FakturamaModelPackage.DOCUMENTRECEIVER_FAX_FEATURE_ID:
                return getTarget().getFax();
            case FakturamaModelPackage.DOCUMENTRECEIVER_SUPPLIERNUMBER_FEATURE_ID:
                return getTarget().getSupplierNumber();
            case FakturamaModelPackage.DOCUMENTRECEIVER_GLN_FEATURE_ID:
                return getTarget().getGln();
            case FakturamaModelPackage.DOCUMENTRECEIVER_MANDATEREFERENCE_FEATURE_ID:
                return getTarget().getMandateReference();
            case FakturamaModelPackage.DOCUMENTRECEIVER_VATNUMBER_FEATURE_ID:
                return getTarget().getVatNumber();
            case FakturamaModelPackage.DOCUMENTRECEIVER_DESCRIPTION_FEATURE_ID:
                return getTarget().getDescription();
            case FakturamaModelPackage.DOCUMENTRECEIVER_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.DOCUMENTRECEIVER_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.DOCUMENTRECEIVER_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.DOCUMENTRECEIVER_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.DOCUMENTRECEIVER_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.DOCUMENTRECEIVER_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.DOCUMENTRECEIVER_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.DOCUMENTRECEIVER_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.DOCUMENTRECEIVER_ALIAS_FEATURE_ID:
                getTarget().setAlias((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_CUSTOMERNUMBER_FEATURE_ID:
                getTarget().setCustomerNumber((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_CONSULTANT_FEATURE_ID:
                getTarget().setConsultant((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_COMPANY_FEATURE_ID:
                getTarget().setCompany((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_TITLE_FEATURE_ID:
                getTarget().setTitle((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_FIRSTNAME_FEATURE_ID:
                getTarget().setFirstName((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_STREET_FEATURE_ID:
                getTarget().setStreet((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_CITYADDON_FEATURE_ID:
                getTarget().setCityAddon((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_CITY_FEATURE_ID:
                getTarget().setCity((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_ZIP_FEATURE_ID:
                getTarget().setZip((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_COUNTRYCODE_FEATURE_ID:
                getTarget().setCountryCode((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_MANUALADDRESS_FEATURE_ID:
                getTarget().setManualAddress((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_BILLINGTYPE_FEATURE_ID:
                getTarget().setBillingType((BillingType) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_ORIGINCONTACTID_FEATURE_ID:
                getTarget().setOriginContactId((Long) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_ORIGINADDRESSID_FEATURE_ID:
                getTarget().setOriginAddressId((Long) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_GENDER_FEATURE_ID:
                getTarget().setGender((Integer) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_EMAIL_FEATURE_ID:
                getTarget().setEmail((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_MOBILE_FEATURE_ID:
                getTarget().setMobile((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_PHONE_FEATURE_ID:
                getTarget().setPhone((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_FAX_FEATURE_ID:
                getTarget().setFax((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_SUPPLIERNUMBER_FEATURE_ID:
                getTarget().setSupplierNumber((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_GLN_FEATURE_ID:
                getTarget().setGln((Long) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_MANDATEREFERENCE_FEATURE_ID:
                getTarget().setMandateReference((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_VATNUMBER_FEATURE_ID:
                getTarget().setVatNumber((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_DESCRIPTION_FEATURE_ID:
                getTarget().setDescription((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENTRECEIVER_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>DocumentItem</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class DocumentItemModelObject<E extends DocumentItem> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getDocumentItemEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return DocumentItem.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.DOCUMENTITEM_NOVAT_FEATURE_ID:
                return getTarget().getNoVat();
            case FakturamaModelPackage.DOCUMENTITEM_ITEMREBATE_FEATURE_ID:
                return getTarget().getItemRebate();
            case FakturamaModelPackage.DOCUMENTITEM_ITEMNUMBER_FEATURE_ID:
                return getTarget().getItemNumber();
            case FakturamaModelPackage.DOCUMENTITEM_PRODUCT_FEATURE_ID:
                return getTarget().getProduct();
            case FakturamaModelPackage.DOCUMENTITEM_QUANTITY_FEATURE_ID:
                return getTarget().getQuantity();
            case FakturamaModelPackage.DOCUMENTITEM_ORIGINQUANTITY_FEATURE_ID:
                return getTarget().getOriginQuantity();
            case FakturamaModelPackage.DOCUMENTITEM_WEIGHT_FEATURE_ID:
                return getTarget().getWeight();
            case FakturamaModelPackage.DOCUMENTITEM_OPTIONAL_FEATURE_ID:
                return getTarget().getOptional();
            case FakturamaModelPackage.DOCUMENTITEM_PICTURE_FEATURE_ID:
                return getTarget().getPicture();
            case FakturamaModelPackage.DOCUMENTITEM_PRICE_FEATURE_ID:
                return getTarget().getPrice();
            case FakturamaModelPackage.DOCUMENTITEM_QUANTITYUNIT_FEATURE_ID:
                return getTarget().getQuantityUnit();
            case FakturamaModelPackage.DOCUMENTITEM_ITEMVAT_FEATURE_ID:
                return getTarget().getItemVat();
            case FakturamaModelPackage.DOCUMENTITEM_GTIN_FEATURE_ID:
                return getTarget().getGtin();
            case FakturamaModelPackage.DOCUMENTITEM_POSNR_FEATURE_ID:
                return getTarget().getPosNr();
            case FakturamaModelPackage.DOCUMENTITEM_ITEMTYPE_FEATURE_ID:
                return getTarget().getItemType();
            case FakturamaModelPackage.DOCUMENTITEM_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.DOCUMENTITEM_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.DOCUMENTITEM_SUPPLIERITEMNUMBER_FEATURE_ID:
                return getTarget().getSupplierItemNumber();
            case FakturamaModelPackage.DOCUMENTITEM_DESCRIPTION_FEATURE_ID:
                return getTarget().getDescription();
            case FakturamaModelPackage.DOCUMENTITEM_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.DOCUMENTITEM_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.DOCUMENTITEM_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.DOCUMENTITEM_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.DOCUMENTITEM_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.DOCUMENTITEM_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.DOCUMENTITEM_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.DOCUMENTITEM_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.DOCUMENTITEM_NOVAT_FEATURE_ID:
                getTarget().setNoVat((Boolean) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_ITEMREBATE_FEATURE_ID:
                getTarget().setItemRebate((Double) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_ITEMNUMBER_FEATURE_ID:
                getTarget().setItemNumber((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_PRODUCT_FEATURE_ID:
                getTarget().setProduct((Product) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_QUANTITY_FEATURE_ID:
                getTarget().setQuantity((Double) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_ORIGINQUANTITY_FEATURE_ID:
                getTarget().setOriginQuantity((Double) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_WEIGHT_FEATURE_ID:
                getTarget().setWeight((Double) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_OPTIONAL_FEATURE_ID:
                getTarget().setOptional((Boolean) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_PICTURE_FEATURE_ID:
                getTarget().setPicture((byte[]) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_PRICE_FEATURE_ID:
                getTarget().setPrice((Double) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_QUANTITYUNIT_FEATURE_ID:
                getTarget().setQuantityUnit((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_ITEMVAT_FEATURE_ID:
                getTarget().setItemVat((VAT) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_GTIN_FEATURE_ID:
                getTarget().setGtin((Long) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_POSNR_FEATURE_ID:
                getTarget().setPosNr((Integer) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_ITEMTYPE_FEATURE_ID:
                getTarget().setItemType((ItemType) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_VESTINGPERIODSTART_FEATURE_ID:
                getTarget().setVestingPeriodStart((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_VESTINGPERIODEND_FEATURE_ID:
                getTarget().setVestingPeriodEnd((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_SUPPLIERITEMNUMBER_FEATURE_ID:
                getTarget().setSupplierItemNumber((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_DESCRIPTION_FEATURE_ID:
                getTarget().setDescription((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.DOCUMENTITEM_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass
     * '<em><b>IndividualDocumentInfo</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class IndividualDocumentInfoModelObject<E extends IndividualDocumentInfo> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getIndividualDocumentInfoEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return IndividualDocumentInfo.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_PAYMENTNAME_FEATURE_ID:
                return getTarget().getPaymentName();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_PAYMENTTEXT_FEATURE_ID:
                return getTarget().getPaymentText();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_PAYMENTDESCRIPTION_FEATURE_ID:
                return getTarget().getPaymentDescription();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_SHIPPINGNAME_FEATURE_ID:
                return getTarget().getShippingName();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_SHIPPINGDESCRIPTION_FEATURE_ID:
                return getTarget().getShippingDescription();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_SHIPPINGVATDESCRIPTION_FEATURE_ID:
                return getTarget().getShippingVatDescription();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_SHIPPINGVATVALUE_FEATURE_ID:
                return getTarget().getShippingVatValue();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_NOVATNAME_FEATURE_ID:
                return getTarget().getNoVatName();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_NOVATDESCRIPTION_FEATURE_ID:
                return getTarget().getNoVatDescription();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_PAYMENTNAME_FEATURE_ID:
                getTarget().setPaymentName((String) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_PAYMENTTEXT_FEATURE_ID:
                getTarget().setPaymentText((String) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_PAYMENTDESCRIPTION_FEATURE_ID:
                getTarget().setPaymentDescription((String) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_SHIPPINGNAME_FEATURE_ID:
                getTarget().setShippingName((String) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_SHIPPINGDESCRIPTION_FEATURE_ID:
                getTarget().setShippingDescription((String) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_SHIPPINGVATDESCRIPTION_FEATURE_ID:
                getTarget().setShippingVatDescription((String) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_SHIPPINGVATVALUE_FEATURE_ID:
                getTarget().setShippingVatValue((Double) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_NOVATNAME_FEATURE_ID:
                getTarget().setNoVatName((String) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_NOVATDESCRIPTION_FEATURE_ID:
                getTarget().setNoVatDescription((String) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.INDIVIDUALDOCUMENTINFO_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>IDescribableEntity</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class IDescribableEntityModelObject<E extends IDescribableEntity> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getIDescribableEntityEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return IDescribableEntity.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.IDESCRIBABLEENTITY_DESCRIPTION_FEATURE_ID:
                return getTarget().getDescription();
            case FakturamaModelPackage.IDESCRIBABLEENTITY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.IDESCRIBABLEENTITY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.IDESCRIBABLEENTITY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.IDESCRIBABLEENTITY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.IDESCRIBABLEENTITY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.IDESCRIBABLEENTITY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.IDESCRIBABLEENTITY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.IDESCRIBABLEENTITY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.IDESCRIBABLEENTITY_DESCRIPTION_FEATURE_ID:
                getTarget().setDescription((String) value);
                return;
            case FakturamaModelPackage.IDESCRIBABLEENTITY_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.IDESCRIBABLEENTITY_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.IDESCRIBABLEENTITY_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.IDESCRIBABLEENTITY_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.IDESCRIBABLEENTITY_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.IDESCRIBABLEENTITY_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.IDESCRIBABLEENTITY_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.IDESCRIBABLEENTITY_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>ItemAccountType</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ItemAccountTypeModelObject<E extends ItemAccountType> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getItemAccountTypeEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return ItemAccountType.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.ITEMACCOUNTTYPE_VALUE_FEATURE_ID:
                return getTarget().getValue();
            case FakturamaModelPackage.ITEMACCOUNTTYPE_CATEGORY_FEATURE_ID:
                return getTarget().getCategory();
            case FakturamaModelPackage.ITEMACCOUNTTYPE_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.ITEMACCOUNTTYPE_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.ITEMACCOUNTTYPE_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.ITEMACCOUNTTYPE_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.ITEMACCOUNTTYPE_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.ITEMACCOUNTTYPE_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.ITEMACCOUNTTYPE_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.ITEMACCOUNTTYPE_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.ITEMACCOUNTTYPE_VALUE_FEATURE_ID:
                getTarget().setValue((String) value);
                return;
            case FakturamaModelPackage.ITEMACCOUNTTYPE_CATEGORY_FEATURE_ID:
                getTarget().setCategory((ItemListTypeCategory) value);
                return;
            case FakturamaModelPackage.ITEMACCOUNTTYPE_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.ITEMACCOUNTTYPE_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.ITEMACCOUNTTYPE_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.ITEMACCOUNTTYPE_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.ITEMACCOUNTTYPE_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.ITEMACCOUNTTYPE_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.ITEMACCOUNTTYPE_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.ITEMACCOUNTTYPE_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Payment</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class PaymentModelObject<E extends Payment> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getPaymentEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Payment.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.PAYMENT_DISCOUNTDAYS_FEATURE_ID:
                return getTarget().getDiscountDays();
            case FakturamaModelPackage.PAYMENT_DISCOUNTVALUE_FEATURE_ID:
                return getTarget().getDiscountValue();
            case FakturamaModelPackage.PAYMENT_NETDAYS_FEATURE_ID:
                return getTarget().getNetDays();
            case FakturamaModelPackage.PAYMENT_CATEGORY_FEATURE_ID:
                return getTarget().getCategory();
            case FakturamaModelPackage.PAYMENT_PAIDTEXT_FEATURE_ID:
                return getTarget().getPaidText();
            case FakturamaModelPackage.PAYMENT_UNPAIDTEXT_FEATURE_ID:
                return getTarget().getUnpaidText();
            case FakturamaModelPackage.PAYMENT_DEPOSITTEXT_FEATURE_ID:
                return getTarget().getDepositText();
            case FakturamaModelPackage.PAYMENT_CODE_FEATURE_ID:
                return getTarget().getCode();
            case FakturamaModelPackage.PAYMENT_DESCRIPTION_FEATURE_ID:
                return getTarget().getDescription();
            case FakturamaModelPackage.PAYMENT_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.PAYMENT_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.PAYMENT_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.PAYMENT_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.PAYMENT_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.PAYMENT_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.PAYMENT_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.PAYMENT_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.PAYMENT_DISCOUNTDAYS_FEATURE_ID:
                getTarget().setDiscountDays((Integer) value);
                return;
            case FakturamaModelPackage.PAYMENT_DISCOUNTVALUE_FEATURE_ID:
                getTarget().setDiscountValue((Double) value);
                return;
            case FakturamaModelPackage.PAYMENT_NETDAYS_FEATURE_ID:
                getTarget().setNetDays((Integer) value);
                return;
            case FakturamaModelPackage.PAYMENT_CATEGORY_FEATURE_ID:
                getTarget().setCategory((VoucherCategory) value);
                return;
            case FakturamaModelPackage.PAYMENT_PAIDTEXT_FEATURE_ID:
                getTarget().setPaidText((String) value);
                return;
            case FakturamaModelPackage.PAYMENT_UNPAIDTEXT_FEATURE_ID:
                getTarget().setUnpaidText((String) value);
                return;
            case FakturamaModelPackage.PAYMENT_DEPOSITTEXT_FEATURE_ID:
                getTarget().setDepositText((String) value);
                return;
            case FakturamaModelPackage.PAYMENT_CODE_FEATURE_ID:
                getTarget().setCode((String) value);
                return;
            case FakturamaModelPackage.PAYMENT_DESCRIPTION_FEATURE_ID:
                getTarget().setDescription((String) value);
                return;
            case FakturamaModelPackage.PAYMENT_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.PAYMENT_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.PAYMENT_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.PAYMENT_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.PAYMENT_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.PAYMENT_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.PAYMENT_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.PAYMENT_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Product</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ProductModelObject<E extends Product> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getProductEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Product.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.PRODUCT_BLOCK1_FEATURE_ID:
                return getTarget().getBlock1();
            case FakturamaModelPackage.PRODUCT_BLOCK2_FEATURE_ID:
                return getTarget().getBlock2();
            case FakturamaModelPackage.PRODUCT_BLOCK3_FEATURE_ID:
                return getTarget().getBlock3();
            case FakturamaModelPackage.PRODUCT_BLOCK4_FEATURE_ID:
                return getTarget().getBlock4();
            case FakturamaModelPackage.PRODUCT_BLOCK5_FEATURE_ID:
                return getTarget().getBlock5();
            case FakturamaModelPackage.PRODUCT_CATEGORIES_FEATURE_ID:
                return getTarget().getCategories();
            case FakturamaModelPackage.PRODUCT_ATTRIBUTES_FEATURE_ID:
                return getTarget().getAttributes();
            case FakturamaModelPackage.PRODUCT_PICTURE_FEATURE_ID:
                return getTarget().getPicture();
            case FakturamaModelPackage.PRODUCT_ITEMNUMBER_FEATURE_ID:
                return getTarget().getItemNumber();
            case FakturamaModelPackage.PRODUCT_SUPPLIERITEMNUMBER_FEATURE_ID:
                return getTarget().getSupplierItemNumber();
            case FakturamaModelPackage.PRODUCT_PRICE1_FEATURE_ID:
                return getTarget().getPrice1();
            case FakturamaModelPackage.PRODUCT_PRICE2_FEATURE_ID:
                return getTarget().getPrice2();
            case FakturamaModelPackage.PRODUCT_PRICE3_FEATURE_ID:
                return getTarget().getPrice3();
            case FakturamaModelPackage.PRODUCT_PRICE4_FEATURE_ID:
                return getTarget().getPrice4();
            case FakturamaModelPackage.PRODUCT_PRICE5_FEATURE_ID:
                return getTarget().getPrice5();
            case FakturamaModelPackage.PRODUCT_QUANTITY_FEATURE_ID:
                return getTarget().getQuantity();
            case FakturamaModelPackage.PRODUCT_QUANTITYUNIT_FEATURE_ID:
                return getTarget().getQuantityUnit();
            case FakturamaModelPackage.PRODUCT_SELLINGUNIT_FEATURE_ID:
                return getTarget().getSellingUnit();
            case FakturamaModelPackage.PRODUCT_VAT_FEATURE_ID:
                return getTarget().getVat();
            case FakturamaModelPackage.PRODUCT_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.PRODUCT_WEIGHT_FEATURE_ID:
                return getTarget().getWeight();
            case FakturamaModelPackage.PRODUCT_GTIN_FEATURE_ID:
                return getTarget().getGtin();
            case FakturamaModelPackage.PRODUCT_COSTPRICE_FEATURE_ID:
                return getTarget().getCostPrice();
            case FakturamaModelPackage.PRODUCT_ALLOWANCE_FEATURE_ID:
                return getTarget().getAllowance();
            case FakturamaModelPackage.PRODUCT_BLOCKPRICES_FEATURE_ID:
                return getTarget().getBlockPrices();
            case FakturamaModelPackage.PRODUCT_CDF01_FEATURE_ID:
                return getTarget().getCdf01();
            case FakturamaModelPackage.PRODUCT_CDF02_FEATURE_ID:
                return getTarget().getCdf02();
            case FakturamaModelPackage.PRODUCT_CDF03_FEATURE_ID:
                return getTarget().getCdf03();
            case FakturamaModelPackage.PRODUCT_NOTE_FEATURE_ID:
                return getTarget().getNote();
            case FakturamaModelPackage.PRODUCT_STOCKMANAGED_FEATURE_ID:
                return getTarget().getStockManaged();
            case FakturamaModelPackage.PRODUCT_DESCRIPTION_FEATURE_ID:
                return getTarget().getDescription();
            case FakturamaModelPackage.PRODUCT_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.PRODUCT_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.PRODUCT_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.PRODUCT_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.PRODUCT_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.PRODUCT_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.PRODUCT_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.PRODUCT_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @SuppressWarnings("unchecked")
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.PRODUCT_BLOCK1_FEATURE_ID:
                getTarget().setBlock1((Integer) value);
                return;
            case FakturamaModelPackage.PRODUCT_BLOCK2_FEATURE_ID:
                getTarget().setBlock2((Integer) value);
                return;
            case FakturamaModelPackage.PRODUCT_BLOCK3_FEATURE_ID:
                getTarget().setBlock3((Integer) value);
                return;
            case FakturamaModelPackage.PRODUCT_BLOCK4_FEATURE_ID:
                getTarget().setBlock4((Integer) value);
                return;
            case FakturamaModelPackage.PRODUCT_BLOCK5_FEATURE_ID:
                getTarget().setBlock5((Integer) value);
                return;
            case FakturamaModelPackage.PRODUCT_CATEGORIES_FEATURE_ID:
                getTarget().setCategories((ProductCategory) value);
                return;
            case FakturamaModelPackage.PRODUCT_ATTRIBUTES_FEATURE_ID:
                getTarget().setAttributes((List<ProductOptions>) value);
                return;
            case FakturamaModelPackage.PRODUCT_PICTURE_FEATURE_ID:
                getTarget().setPicture((byte[]) value);
                return;
            case FakturamaModelPackage.PRODUCT_ITEMNUMBER_FEATURE_ID:
                getTarget().setItemNumber((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_SUPPLIERITEMNUMBER_FEATURE_ID:
                getTarget().setSupplierItemNumber((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_PRICE1_FEATURE_ID:
                getTarget().setPrice1((Double) value);
                return;
            case FakturamaModelPackage.PRODUCT_PRICE2_FEATURE_ID:
                getTarget().setPrice2((Double) value);
                return;
            case FakturamaModelPackage.PRODUCT_PRICE3_FEATURE_ID:
                getTarget().setPrice3((Double) value);
                return;
            case FakturamaModelPackage.PRODUCT_PRICE4_FEATURE_ID:
                getTarget().setPrice4((Double) value);
                return;
            case FakturamaModelPackage.PRODUCT_PRICE5_FEATURE_ID:
                getTarget().setPrice5((Double) value);
                return;
            case FakturamaModelPackage.PRODUCT_QUANTITY_FEATURE_ID:
                getTarget().setQuantity((Double) value);
                return;
            case FakturamaModelPackage.PRODUCT_QUANTITYUNIT_FEATURE_ID:
                getTarget().setQuantityUnit((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_SELLINGUNIT_FEATURE_ID:
                getTarget().setSellingUnit((Integer) value);
                return;
            case FakturamaModelPackage.PRODUCT_VAT_FEATURE_ID:
                getTarget().setVat((VAT) value);
                return;
            case FakturamaModelPackage.PRODUCT_WEBSHOPID_FEATURE_ID:
                getTarget().setWebshopId((Long) value);
                return;
            case FakturamaModelPackage.PRODUCT_WEIGHT_FEATURE_ID:
                getTarget().setWeight((Double) value);
                return;
            case FakturamaModelPackage.PRODUCT_GTIN_FEATURE_ID:
                getTarget().setGtin((Long) value);
                return;
            case FakturamaModelPackage.PRODUCT_COSTPRICE_FEATURE_ID:
                getTarget().setCostPrice((Double) value);
                return;
            case FakturamaModelPackage.PRODUCT_ALLOWANCE_FEATURE_ID:
                getTarget().setAllowance((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_BLOCKPRICES_FEATURE_ID:
                getTarget().setBlockPrices((List<ProductBlockPrice>) value);
                return;
            case FakturamaModelPackage.PRODUCT_CDF01_FEATURE_ID:
                getTarget().setCdf01((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_CDF02_FEATURE_ID:
                getTarget().setCdf02((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_CDF03_FEATURE_ID:
                getTarget().setCdf03((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_NOTE_FEATURE_ID:
                getTarget().setNote((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_STOCKMANAGED_FEATURE_ID:
                getTarget().setStockManaged((Boolean) value);
                return;
            case FakturamaModelPackage.PRODUCT_DESCRIPTION_FEATURE_ID:
                getTarget().setDescription((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.PRODUCT_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.PRODUCT_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.PRODUCT_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.PRODUCT_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.PRODUCT_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.PRODUCT_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.PRODUCT_ATTRIBUTES_FEATURE_ID:
                return getTarget().getAttributes().add((ProductOptions) value);

            case FakturamaModelPackage.PRODUCT_BLOCKPRICES_FEATURE_ID:
                return getTarget().getBlockPrices().add((ProductBlockPrice) value);

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.PRODUCT_ATTRIBUTES_FEATURE_ID:
                return getTarget().getAttributes().remove(value);

            case FakturamaModelPackage.PRODUCT_BLOCKPRICES_FEATURE_ID:
                return getTarget().getBlockPrices().remove(value);

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>ProductCategory</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ProductCategoryModelObject<E extends ProductCategory> extends AbstractCategoryModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getProductCategoryEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return ProductCategory.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.PRODUCTCATEGORY_PARENT_FEATURE_ID:
                return getTarget().getParent();
            case FakturamaModelPackage.PRODUCTCATEGORY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.PRODUCTCATEGORY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.PRODUCTCATEGORY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.PRODUCTCATEGORY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.PRODUCTCATEGORY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.PRODUCTCATEGORY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.PRODUCTCATEGORY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.PRODUCTCATEGORY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>ProductOptions</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ProductOptionsModelObject<E extends ProductOptions> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getProductOptionsEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return ProductOptions.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.PRODUCTOPTIONS_ATTRIBUTEVALUE_FEATURE_ID:
                return getTarget().getAttributeValue();
            case FakturamaModelPackage.PRODUCTOPTIONS_SEQUENCENUMBER_FEATURE_ID:
                return getTarget().getSequenceNumber();
            case FakturamaModelPackage.PRODUCTOPTIONS_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.PRODUCTOPTIONS_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.PRODUCTOPTIONS_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.PRODUCTOPTIONS_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.PRODUCTOPTIONS_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.PRODUCTOPTIONS_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.PRODUCTOPTIONS_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.PRODUCTOPTIONS_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.PRODUCTOPTIONS_ATTRIBUTEVALUE_FEATURE_ID:
                getTarget().setAttributeValue((String) value);
                return;
            case FakturamaModelPackage.PRODUCTOPTIONS_SEQUENCENUMBER_FEATURE_ID:
                getTarget().setSequenceNumber((Integer) value);
                return;
            case FakturamaModelPackage.PRODUCTOPTIONS_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.PRODUCTOPTIONS_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.PRODUCTOPTIONS_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.PRODUCTOPTIONS_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.PRODUCTOPTIONS_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.PRODUCTOPTIONS_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.PRODUCTOPTIONS_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.PRODUCTOPTIONS_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>ProductBlockPrice</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ProductBlockPriceModelObject<E extends ProductBlockPrice> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getProductBlockPriceEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return ProductBlockPrice.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_BLOCK_FEATURE_ID:
                return getTarget().getBlock();
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_PRICE_FEATURE_ID:
                return getTarget().getPrice();
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_BLOCK_FEATURE_ID:
                getTarget().setBlock((Integer) value);
                return;
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_PRICE_FEATURE_ID:
                getTarget().setPrice((Double) value);
                return;
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.PRODUCTBLOCKPRICE_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Role</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class RoleModelObject<E extends Role> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getRoleEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Role.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.ROLE_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.ROLE_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.ROLE_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.ROLE_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.ROLE_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.ROLE_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.ROLE_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.ROLE_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.ROLE_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.ROLE_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.ROLE_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.ROLE_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.ROLE_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.ROLE_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.ROLE_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.ROLE_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Tenant</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class TenantModelObject<E extends Tenant> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getTenantEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Tenant.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.TENANT_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.TENANT_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.TENANT_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.TENANT_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.TENANT_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.TENANT_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.TENANT_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.TENANT_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.TENANT_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.TENANT_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.TENANT_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.TENANT_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.TENANT_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.TENANT_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.TENANT_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.TENANT_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Shipping</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ShippingModelObject<E extends Shipping> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getShippingEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Shipping.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.SHIPPING_AUTOVAT_FEATURE_ID:
                return getTarget().getAutoVat();
            case FakturamaModelPackage.SHIPPING_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.SHIPPING_SHIPPINGVAT_FEATURE_ID:
                return getTarget().getShippingVat();
            case FakturamaModelPackage.SHIPPING_CATEGORIES_FEATURE_ID:
                return getTarget().getCategories();
            case FakturamaModelPackage.SHIPPING_CODE_FEATURE_ID:
                return getTarget().getCode();
            case FakturamaModelPackage.SHIPPING_DESCRIPTION_FEATURE_ID:
                return getTarget().getDescription();
            case FakturamaModelPackage.SHIPPING_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.SHIPPING_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.SHIPPING_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.SHIPPING_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.SHIPPING_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.SHIPPING_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.SHIPPING_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.SHIPPING_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.SHIPPING_AUTOVAT_FEATURE_ID:
                getTarget().setAutoVat((ShippingVatType) value);
                return;
            case FakturamaModelPackage.SHIPPING_SHIPPINGVALUE_FEATURE_ID:
                getTarget().setShippingValue((Double) value);
                return;
            case FakturamaModelPackage.SHIPPING_SHIPPINGVAT_FEATURE_ID:
                getTarget().setShippingVat((VAT) value);
                return;
            case FakturamaModelPackage.SHIPPING_CATEGORIES_FEATURE_ID:
                getTarget().setCategories((ShippingCategory) value);
                return;
            case FakturamaModelPackage.SHIPPING_CODE_FEATURE_ID:
                getTarget().setCode((String) value);
                return;
            case FakturamaModelPackage.SHIPPING_DESCRIPTION_FEATURE_ID:
                getTarget().setDescription((String) value);
                return;
            case FakturamaModelPackage.SHIPPING_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.SHIPPING_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.SHIPPING_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.SHIPPING_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.SHIPPING_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.SHIPPING_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.SHIPPING_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.SHIPPING_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>ShippingCategory</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ShippingCategoryModelObject<E extends ShippingCategory> extends AbstractCategoryModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getShippingCategoryEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return ShippingCategory.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.SHIPPINGCATEGORY_PARENT_FEATURE_ID:
                return getTarget().getParent();
            case FakturamaModelPackage.SHIPPINGCATEGORY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.SHIPPINGCATEGORY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.SHIPPINGCATEGORY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.SHIPPINGCATEGORY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.SHIPPINGCATEGORY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.SHIPPINGCATEGORY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.SHIPPINGCATEGORY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.SHIPPINGCATEGORY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>TextCategory</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class TextCategoryModelObject<E extends TextCategory> extends AbstractCategoryModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getTextCategoryEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return TextCategory.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.TEXTCATEGORY_PARENT_FEATURE_ID:
                return getTarget().getParent();
            case FakturamaModelPackage.TEXTCATEGORY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.TEXTCATEGORY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.TEXTCATEGORY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.TEXTCATEGORY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.TEXTCATEGORY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.TEXTCATEGORY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.TEXTCATEGORY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.TEXTCATEGORY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>TextModule</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class TextModuleModelObject<E extends TextModule> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getTextModuleEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return TextModule.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.TEXTMODULE_TEXT_FEATURE_ID:
                return getTarget().getText();
            case FakturamaModelPackage.TEXTMODULE_CATEGORIES_FEATURE_ID:
                return getTarget().getCategories();
            case FakturamaModelPackage.TEXTMODULE_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.TEXTMODULE_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.TEXTMODULE_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.TEXTMODULE_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.TEXTMODULE_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.TEXTMODULE_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.TEXTMODULE_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.TEXTMODULE_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.TEXTMODULE_TEXT_FEATURE_ID:
                getTarget().setText((String) value);
                return;
            case FakturamaModelPackage.TEXTMODULE_CATEGORIES_FEATURE_ID:
                getTarget().setCategories((TextCategory) value);
                return;
            case FakturamaModelPackage.TEXTMODULE_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.TEXTMODULE_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.TEXTMODULE_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.TEXTMODULE_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.TEXTMODULE_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.TEXTMODULE_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.TEXTMODULE_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.TEXTMODULE_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>User</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class UserModelObject<E extends User> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getUserEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return User.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.USER_PASSWORD_FEATURE_ID:
                return getTarget().getPassword();
            case FakturamaModelPackage.USER_TENANT_FEATURE_ID:
                return getTarget().getTenant();
            case FakturamaModelPackage.USER_USERNAME_FEATURE_ID:
                return getTarget().getUserName();
            case FakturamaModelPackage.USER_ROLES_FEATURE_ID:
                return getTarget().getRoles();
            case FakturamaModelPackage.USER_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.USER_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.USER_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.USER_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.USER_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.USER_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.USER_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.USER_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @SuppressWarnings("unchecked")
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.USER_PASSWORD_FEATURE_ID:
                getTarget().setPassword((String) value);
                return;
            case FakturamaModelPackage.USER_TENANT_FEATURE_ID:
                getTarget().setTenant((Tenant) value);
                return;
            case FakturamaModelPackage.USER_USERNAME_FEATURE_ID:
                getTarget().setUserName((String) value);
                return;
            case FakturamaModelPackage.USER_ROLES_FEATURE_ID:
                getTarget().setRoles((List<Role>) value);
                return;
            case FakturamaModelPackage.USER_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.USER_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.USER_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.USER_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.USER_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.USER_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.USER_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.USER_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.USER_ROLES_FEATURE_ID:
                return getTarget().getRoles().add((Role) value);

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.USER_ROLES_FEATURE_ID:
                return getTarget().getRoles().remove(value);

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>UserProperty</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class UserPropertyModelObject<E extends UserProperty> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getUserPropertyEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return UserProperty.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.USERPROPERTY_VALUE_FEATURE_ID:
                return getTarget().getValue();
            case FakturamaModelPackage.USERPROPERTY_USER_FEATURE_ID:
                return getTarget().getUser();
            case FakturamaModelPackage.USERPROPERTY_DEFAULT__FEATURE_ID:
                return getTarget().getDefault_();
            case FakturamaModelPackage.USERPROPERTY_GLOBAL_FEATURE_ID:
                return getTarget().getGlobal();
            case FakturamaModelPackage.USERPROPERTY_QUALIFIER_FEATURE_ID:
                return getTarget().getQualifier();
            case FakturamaModelPackage.USERPROPERTY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.USERPROPERTY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.USERPROPERTY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.USERPROPERTY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.USERPROPERTY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.USERPROPERTY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.USERPROPERTY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.USERPROPERTY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.USERPROPERTY_VALUE_FEATURE_ID:
                getTarget().setValue((String) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_USER_FEATURE_ID:
                getTarget().setUser((String) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_DEFAULT__FEATURE_ID:
                getTarget().setDefault_((String) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_GLOBAL_FEATURE_ID:
                getTarget().setGlobal((Boolean) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_QUALIFIER_FEATURE_ID:
                getTarget().setQualifier((String) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.USERPROPERTY_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>VAT</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class VATModelObject<E extends VAT> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getVATEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return VAT.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.VAT_TAXVALUE_FEATURE_ID:
                return getTarget().getTaxValue();
            case FakturamaModelPackage.VAT_CATEGORY_FEATURE_ID:
                return getTarget().getCategory();
            case FakturamaModelPackage.VAT_SALESEQUALIZATIONTAX_FEATURE_ID:
                return getTarget().getSalesEqualizationTax();
            case FakturamaModelPackage.VAT_DESCRIPTION_FEATURE_ID:
                return getTarget().getDescription();
            case FakturamaModelPackage.VAT_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.VAT_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.VAT_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.VAT_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.VAT_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.VAT_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.VAT_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.VAT_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.VAT_TAXVALUE_FEATURE_ID:
                getTarget().setTaxValue((Double) value);
                return;
            case FakturamaModelPackage.VAT_CATEGORY_FEATURE_ID:
                getTarget().setCategory((VATCategory) value);
                return;
            case FakturamaModelPackage.VAT_SALESEQUALIZATIONTAX_FEATURE_ID:
                getTarget().setSalesEqualizationTax((Double) value);
                return;
            case FakturamaModelPackage.VAT_DESCRIPTION_FEATURE_ID:
                getTarget().setDescription((String) value);
                return;
            case FakturamaModelPackage.VAT_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.VAT_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.VAT_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.VAT_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.VAT_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.VAT_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.VAT_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.VAT_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>VATCategory</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class VATCategoryModelObject<E extends VATCategory> extends AbstractCategoryModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getVATCategoryEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return VATCategory.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.VATCATEGORY_PARENT_FEATURE_ID:
                return getTarget().getParent();
            case FakturamaModelPackage.VATCATEGORY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.VATCATEGORY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.VATCATEGORY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.VATCATEGORY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.VATCATEGORY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.VATCATEGORY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.VATCATEGORY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.VATCATEGORY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>CEFACTCode</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class CEFACTCodeModelObject<E extends CEFACTCode> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getCEFACTCodeEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return CEFACTCode.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.CEFACTCODE_ABBREVIATION_DE_FEATURE_ID:
                return getTarget().getAbbreviation_de();
            case FakturamaModelPackage.CEFACTCODE_ABBREVIATION_EN_FEATURE_ID:
                return getTarget().getAbbreviation_en();
            case FakturamaModelPackage.CEFACTCODE_CODE_FEATURE_ID:
                return getTarget().getCode();
            case FakturamaModelPackage.CEFACTCODE_TARGET_FEATURE_ID:
                return getTarget().getTarget();
            case FakturamaModelPackage.CEFACTCODE_NAME_DE_FEATURE_ID:
                return getTarget().getName_de();
            case FakturamaModelPackage.CEFACTCODE_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.CEFACTCODE_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.CEFACTCODE_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.CEFACTCODE_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.CEFACTCODE_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.CEFACTCODE_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.CEFACTCODE_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.CEFACTCODE_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.CEFACTCODE_ABBREVIATION_DE_FEATURE_ID:
                getTarget().setAbbreviation_de((String) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_ABBREVIATION_EN_FEATURE_ID:
                getTarget().setAbbreviation_en((String) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_CODE_FEATURE_ID:
                getTarget().setCode((String) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_TARGET_FEATURE_ID:
                getTarget().setTarget((String) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_NAME_DE_FEATURE_ID:
                getTarget().setName_de((String) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.CEFACTCODE_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>WebShop</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class WebShopModelObject<E extends WebShop> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getWebShopEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return WebShop.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.WEBSHOP_WEBSHOPVENDOR_FEATURE_ID:
                return getTarget().getWebshopVendor();
            case FakturamaModelPackage.WEBSHOP_WEBSHOPVERSION_FEATURE_ID:
                return getTarget().getWebshopVersion();
            case FakturamaModelPackage.WEBSHOP_STATEMAPPING_FEATURE_ID:
                return getTarget().getStateMapping();
            case FakturamaModelPackage.WEBSHOP_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.WEBSHOP_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.WEBSHOP_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.WEBSHOP_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.WEBSHOP_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.WEBSHOP_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.WEBSHOP_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.WEBSHOP_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @SuppressWarnings("unchecked")
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.WEBSHOP_WEBSHOPVENDOR_FEATURE_ID:
                getTarget().setWebshopVendor((String) value);
                return;
            case FakturamaModelPackage.WEBSHOP_WEBSHOPVERSION_FEATURE_ID:
                getTarget().setWebshopVersion((String) value);
                return;
            case FakturamaModelPackage.WEBSHOP_STATEMAPPING_FEATURE_ID:
                getTarget().setStateMapping((List<WebshopStateMapping>) value);
                return;
            case FakturamaModelPackage.WEBSHOP_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.WEBSHOP_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.WEBSHOP_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.WEBSHOP_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.WEBSHOP_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.WEBSHOP_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.WEBSHOP_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.WEBSHOP_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.WEBSHOP_STATEMAPPING_FEATURE_ID:
                return getTarget().getStateMapping().add((WebshopStateMapping) value);

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            case FakturamaModelPackage.WEBSHOP_STATEMAPPING_FEATURE_ID:
                return getTarget().getStateMapping().remove(value);

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>WebshopStateMapping</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class WebshopStateMappingModelObject<E extends WebshopStateMapping> extends AbstractModelObject<E> {
        /**
         * @generated
         */
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getWebshopStateMappingEClass();
        }

        /**
         * @generated
         */
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return WebshopStateMapping.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_WEBSHOPSTATE_FEATURE_ID:
                return getTarget().getWebshopState();
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_FAKTURAMAORDERSTATE_FEATURE_ID:
                return getTarget().getFakturamaOrderState();
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_WEBSHOPSTATE_FEATURE_ID:
                getTarget().setWebshopState((String) value);
                return;
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_FAKTURAMAORDERSTATE_FEATURE_ID:
                getTarget().setFakturamaOrderState((String) value);
                return;
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_NAME_FEATURE_ID:
                getTarget().setName((String) value);
                return;
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_DATEADDED_FEATURE_ID:
                getTarget().setDateAdded((Date) value);
                return;
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_MODIFIEDBY_FEATURE_ID:
                getTarget().setModifiedBy((String) value);
                return;
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_MODIFIED_FEATURE_ID:
                getTarget().setModified((Date) value);
                return;
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_ID_FEATURE_ID:
                getTarget().setId((Long) value);
                return;
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_DELETED_FEATURE_ID:
                getTarget().setDeleted((Boolean) value);
                return;
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_VALIDFROM_FEATURE_ID:
                getTarget().setValidFrom((Date) value);
                return;
            case FakturamaModelPackage.WEBSHOPSTATEMAPPING_VALIDTO_FEATURE_ID:
                getTarget().setValidTo((Date) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Confirmation</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ConfirmationModelObject<E extends Confirmation> extends DocumentModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getConfirmationEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Confirmation.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.CONFIRMATION_ADDITIONALINFO_FEATURE_ID:
                return getTarget().getAdditionalInfo();
            case FakturamaModelPackage.CONFIRMATION_ADDRESSFIRSTLINE_FEATURE_ID:
                return getTarget().getAddressFirstLine();
            case FakturamaModelPackage.CONFIRMATION_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.CONFIRMATION_CUSTOMERREF_FEATURE_ID:
                return getTarget().getCustomerRef();
            case FakturamaModelPackage.CONFIRMATION_DEPOSIT_FEATURE_ID:
                return getTarget().getDeposit();
            case FakturamaModelPackage.CONFIRMATION_DOCUMENTDATE_FEATURE_ID:
                return getTarget().getDocumentDate();
            case FakturamaModelPackage.CONFIRMATION_DUEDAYS_FEATURE_ID:
                return getTarget().getDueDays();
            case FakturamaModelPackage.CONFIRMATION_INVOICEREFERENCE_FEATURE_ID:
                return getTarget().getInvoiceReference();
            case FakturamaModelPackage.CONFIRMATION_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.CONFIRMATION_ITEMSREBATE_FEATURE_ID:
                return getTarget().getItemsRebate();
            case FakturamaModelPackage.CONFIRMATION_MESSAGE_FEATURE_ID:
                return getTarget().getMessage();
            case FakturamaModelPackage.CONFIRMATION_MESSAGE2_FEATURE_ID:
                return getTarget().getMessage2();
            case FakturamaModelPackage.CONFIRMATION_MESSAGE3_FEATURE_ID:
                return getTarget().getMessage3();
            case FakturamaModelPackage.CONFIRMATION_NETGROSS_FEATURE_ID:
                return getTarget().getNetGross();
            case FakturamaModelPackage.CONFIRMATION_NOVATREFERENCE_FEATURE_ID:
                return getTarget().getNoVatReference();
            case FakturamaModelPackage.CONFIRMATION_ODTPATH_FEATURE_ID:
                return getTarget().getOdtPath();
            case FakturamaModelPackage.CONFIRMATION_ORDERDATE_FEATURE_ID:
                return getTarget().getOrderDate();
            case FakturamaModelPackage.CONFIRMATION_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.CONFIRMATION_PAID_FEATURE_ID:
                return getTarget().getPaid();
            case FakturamaModelPackage.CONFIRMATION_PAYDATE_FEATURE_ID:
                return getTarget().getPayDate();
            case FakturamaModelPackage.CONFIRMATION_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.CONFIRMATION_PDFPATH_FEATURE_ID:
                return getTarget().getPdfPath();
            case FakturamaModelPackage.CONFIRMATION_PRINTED_FEATURE_ID:
                return getTarget().getPrinted();
            case FakturamaModelPackage.CONFIRMATION_PRINTTEMPLATE_FEATURE_ID:
                return getTarget().getPrintTemplate();
            case FakturamaModelPackage.CONFIRMATION_PROGRESS_FEATURE_ID:
                return getTarget().getProgress();
            case FakturamaModelPackage.CONFIRMATION_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver();
            case FakturamaModelPackage.CONFIRMATION_SERVICEDATE_FEATURE_ID:
                return getTarget().getServiceDate();
            case FakturamaModelPackage.CONFIRMATION_SHIPPING_FEATURE_ID:
                return getTarget().getShipping();
            case FakturamaModelPackage.CONFIRMATION_SHIPPINGAUTOVAT_FEATURE_ID:
                return getTarget().getShippingAutoVat();
            case FakturamaModelPackage.CONFIRMATION_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.CONFIRMATION_SOURCEDOCUMENT_FEATURE_ID:
                return getTarget().getSourceDocument();
            case FakturamaModelPackage.CONFIRMATION_TARA_FEATURE_ID:
                return getTarget().getTara();
            case FakturamaModelPackage.CONFIRMATION_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.CONFIRMATION_TRANSACTIONID_FEATURE_ID:
                return getTarget().getTransactionId();
            case FakturamaModelPackage.CONFIRMATION_WEBSHOPDATE_FEATURE_ID:
                return getTarget().getWebshopDate();
            case FakturamaModelPackage.CONFIRMATION_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.CONFIRMATION_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.CONFIRMATION_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.CONFIRMATION_VERSION_FEATURE_ID:
                return getTarget().getVersion();
            case FakturamaModelPackage.CONFIRMATION_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.CONFIRMATION_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.CONFIRMATION_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.CONFIRMATION_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.CONFIRMATION_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.CONFIRMATION_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.CONFIRMATION_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.CONFIRMATION_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Credit</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class CreditModelObject<E extends Credit> extends DocumentModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getCreditEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Credit.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.CREDIT_ADDITIONALINFO_FEATURE_ID:
                return getTarget().getAdditionalInfo();
            case FakturamaModelPackage.CREDIT_ADDRESSFIRSTLINE_FEATURE_ID:
                return getTarget().getAddressFirstLine();
            case FakturamaModelPackage.CREDIT_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.CREDIT_CUSTOMERREF_FEATURE_ID:
                return getTarget().getCustomerRef();
            case FakturamaModelPackage.CREDIT_DEPOSIT_FEATURE_ID:
                return getTarget().getDeposit();
            case FakturamaModelPackage.CREDIT_DOCUMENTDATE_FEATURE_ID:
                return getTarget().getDocumentDate();
            case FakturamaModelPackage.CREDIT_DUEDAYS_FEATURE_ID:
                return getTarget().getDueDays();
            case FakturamaModelPackage.CREDIT_INVOICEREFERENCE_FEATURE_ID:
                return getTarget().getInvoiceReference();
            case FakturamaModelPackage.CREDIT_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.CREDIT_ITEMSREBATE_FEATURE_ID:
                return getTarget().getItemsRebate();
            case FakturamaModelPackage.CREDIT_MESSAGE_FEATURE_ID:
                return getTarget().getMessage();
            case FakturamaModelPackage.CREDIT_MESSAGE2_FEATURE_ID:
                return getTarget().getMessage2();
            case FakturamaModelPackage.CREDIT_MESSAGE3_FEATURE_ID:
                return getTarget().getMessage3();
            case FakturamaModelPackage.CREDIT_NETGROSS_FEATURE_ID:
                return getTarget().getNetGross();
            case FakturamaModelPackage.CREDIT_NOVATREFERENCE_FEATURE_ID:
                return getTarget().getNoVatReference();
            case FakturamaModelPackage.CREDIT_ODTPATH_FEATURE_ID:
                return getTarget().getOdtPath();
            case FakturamaModelPackage.CREDIT_ORDERDATE_FEATURE_ID:
                return getTarget().getOrderDate();
            case FakturamaModelPackage.CREDIT_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.CREDIT_PAID_FEATURE_ID:
                return getTarget().getPaid();
            case FakturamaModelPackage.CREDIT_PAYDATE_FEATURE_ID:
                return getTarget().getPayDate();
            case FakturamaModelPackage.CREDIT_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.CREDIT_PDFPATH_FEATURE_ID:
                return getTarget().getPdfPath();
            case FakturamaModelPackage.CREDIT_PRINTED_FEATURE_ID:
                return getTarget().getPrinted();
            case FakturamaModelPackage.CREDIT_PRINTTEMPLATE_FEATURE_ID:
                return getTarget().getPrintTemplate();
            case FakturamaModelPackage.CREDIT_PROGRESS_FEATURE_ID:
                return getTarget().getProgress();
            case FakturamaModelPackage.CREDIT_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver();
            case FakturamaModelPackage.CREDIT_SERVICEDATE_FEATURE_ID:
                return getTarget().getServiceDate();
            case FakturamaModelPackage.CREDIT_SHIPPING_FEATURE_ID:
                return getTarget().getShipping();
            case FakturamaModelPackage.CREDIT_SHIPPINGAUTOVAT_FEATURE_ID:
                return getTarget().getShippingAutoVat();
            case FakturamaModelPackage.CREDIT_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.CREDIT_SOURCEDOCUMENT_FEATURE_ID:
                return getTarget().getSourceDocument();
            case FakturamaModelPackage.CREDIT_TARA_FEATURE_ID:
                return getTarget().getTara();
            case FakturamaModelPackage.CREDIT_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.CREDIT_TRANSACTIONID_FEATURE_ID:
                return getTarget().getTransactionId();
            case FakturamaModelPackage.CREDIT_WEBSHOPDATE_FEATURE_ID:
                return getTarget().getWebshopDate();
            case FakturamaModelPackage.CREDIT_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.CREDIT_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.CREDIT_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.CREDIT_VERSION_FEATURE_ID:
                return getTarget().getVersion();
            case FakturamaModelPackage.CREDIT_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.CREDIT_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.CREDIT_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.CREDIT_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.CREDIT_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.CREDIT_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.CREDIT_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.CREDIT_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Delivery</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class DeliveryModelObject<E extends Delivery> extends DocumentModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getDeliveryEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Delivery.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.DELIVERY_ADDITIONALINFO_FEATURE_ID:
                return getTarget().getAdditionalInfo();
            case FakturamaModelPackage.DELIVERY_ADDRESSFIRSTLINE_FEATURE_ID:
                return getTarget().getAddressFirstLine();
            case FakturamaModelPackage.DELIVERY_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.DELIVERY_CUSTOMERREF_FEATURE_ID:
                return getTarget().getCustomerRef();
            case FakturamaModelPackage.DELIVERY_DEPOSIT_FEATURE_ID:
                return getTarget().getDeposit();
            case FakturamaModelPackage.DELIVERY_DOCUMENTDATE_FEATURE_ID:
                return getTarget().getDocumentDate();
            case FakturamaModelPackage.DELIVERY_DUEDAYS_FEATURE_ID:
                return getTarget().getDueDays();
            case FakturamaModelPackage.DELIVERY_INVOICEREFERENCE_FEATURE_ID:
                return getTarget().getInvoiceReference();
            case FakturamaModelPackage.DELIVERY_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.DELIVERY_ITEMSREBATE_FEATURE_ID:
                return getTarget().getItemsRebate();
            case FakturamaModelPackage.DELIVERY_MESSAGE_FEATURE_ID:
                return getTarget().getMessage();
            case FakturamaModelPackage.DELIVERY_MESSAGE2_FEATURE_ID:
                return getTarget().getMessage2();
            case FakturamaModelPackage.DELIVERY_MESSAGE3_FEATURE_ID:
                return getTarget().getMessage3();
            case FakturamaModelPackage.DELIVERY_NETGROSS_FEATURE_ID:
                return getTarget().getNetGross();
            case FakturamaModelPackage.DELIVERY_NOVATREFERENCE_FEATURE_ID:
                return getTarget().getNoVatReference();
            case FakturamaModelPackage.DELIVERY_ODTPATH_FEATURE_ID:
                return getTarget().getOdtPath();
            case FakturamaModelPackage.DELIVERY_ORDERDATE_FEATURE_ID:
                return getTarget().getOrderDate();
            case FakturamaModelPackage.DELIVERY_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.DELIVERY_PAID_FEATURE_ID:
                return getTarget().getPaid();
            case FakturamaModelPackage.DELIVERY_PAYDATE_FEATURE_ID:
                return getTarget().getPayDate();
            case FakturamaModelPackage.DELIVERY_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.DELIVERY_PDFPATH_FEATURE_ID:
                return getTarget().getPdfPath();
            case FakturamaModelPackage.DELIVERY_PRINTED_FEATURE_ID:
                return getTarget().getPrinted();
            case FakturamaModelPackage.DELIVERY_PRINTTEMPLATE_FEATURE_ID:
                return getTarget().getPrintTemplate();
            case FakturamaModelPackage.DELIVERY_PROGRESS_FEATURE_ID:
                return getTarget().getProgress();
            case FakturamaModelPackage.DELIVERY_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver();
            case FakturamaModelPackage.DELIVERY_SERVICEDATE_FEATURE_ID:
                return getTarget().getServiceDate();
            case FakturamaModelPackage.DELIVERY_SHIPPING_FEATURE_ID:
                return getTarget().getShipping();
            case FakturamaModelPackage.DELIVERY_SHIPPINGAUTOVAT_FEATURE_ID:
                return getTarget().getShippingAutoVat();
            case FakturamaModelPackage.DELIVERY_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.DELIVERY_SOURCEDOCUMENT_FEATURE_ID:
                return getTarget().getSourceDocument();
            case FakturamaModelPackage.DELIVERY_TARA_FEATURE_ID:
                return getTarget().getTara();
            case FakturamaModelPackage.DELIVERY_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.DELIVERY_TRANSACTIONID_FEATURE_ID:
                return getTarget().getTransactionId();
            case FakturamaModelPackage.DELIVERY_WEBSHOPDATE_FEATURE_ID:
                return getTarget().getWebshopDate();
            case FakturamaModelPackage.DELIVERY_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.DELIVERY_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.DELIVERY_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.DELIVERY_VERSION_FEATURE_ID:
                return getTarget().getVersion();
            case FakturamaModelPackage.DELIVERY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.DELIVERY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.DELIVERY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.DELIVERY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.DELIVERY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.DELIVERY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.DELIVERY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.DELIVERY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Dunning</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class DunningModelObject<E extends Dunning> extends DocumentModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getDunningEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Dunning.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.DUNNING_ADDITIONALINFO_FEATURE_ID:
                return getTarget().getAdditionalInfo();
            case FakturamaModelPackage.DUNNING_ADDRESSFIRSTLINE_FEATURE_ID:
                return getTarget().getAddressFirstLine();
            case FakturamaModelPackage.DUNNING_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.DUNNING_CUSTOMERREF_FEATURE_ID:
                return getTarget().getCustomerRef();
            case FakturamaModelPackage.DUNNING_DEPOSIT_FEATURE_ID:
                return getTarget().getDeposit();
            case FakturamaModelPackage.DUNNING_DOCUMENTDATE_FEATURE_ID:
                return getTarget().getDocumentDate();
            case FakturamaModelPackage.DUNNING_DUEDAYS_FEATURE_ID:
                return getTarget().getDueDays();
            case FakturamaModelPackage.DUNNING_INVOICEREFERENCE_FEATURE_ID:
                return getTarget().getInvoiceReference();
            case FakturamaModelPackage.DUNNING_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.DUNNING_ITEMSREBATE_FEATURE_ID:
                return getTarget().getItemsRebate();
            case FakturamaModelPackage.DUNNING_MESSAGE_FEATURE_ID:
                return getTarget().getMessage();
            case FakturamaModelPackage.DUNNING_MESSAGE2_FEATURE_ID:
                return getTarget().getMessage2();
            case FakturamaModelPackage.DUNNING_MESSAGE3_FEATURE_ID:
                return getTarget().getMessage3();
            case FakturamaModelPackage.DUNNING_NETGROSS_FEATURE_ID:
                return getTarget().getNetGross();
            case FakturamaModelPackage.DUNNING_NOVATREFERENCE_FEATURE_ID:
                return getTarget().getNoVatReference();
            case FakturamaModelPackage.DUNNING_ODTPATH_FEATURE_ID:
                return getTarget().getOdtPath();
            case FakturamaModelPackage.DUNNING_ORDERDATE_FEATURE_ID:
                return getTarget().getOrderDate();
            case FakturamaModelPackage.DUNNING_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.DUNNING_PAID_FEATURE_ID:
                return getTarget().getPaid();
            case FakturamaModelPackage.DUNNING_PAYDATE_FEATURE_ID:
                return getTarget().getPayDate();
            case FakturamaModelPackage.DUNNING_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.DUNNING_PDFPATH_FEATURE_ID:
                return getTarget().getPdfPath();
            case FakturamaModelPackage.DUNNING_PRINTED_FEATURE_ID:
                return getTarget().getPrinted();
            case FakturamaModelPackage.DUNNING_PRINTTEMPLATE_FEATURE_ID:
                return getTarget().getPrintTemplate();
            case FakturamaModelPackage.DUNNING_PROGRESS_FEATURE_ID:
                return getTarget().getProgress();
            case FakturamaModelPackage.DUNNING_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver();
            case FakturamaModelPackage.DUNNING_SERVICEDATE_FEATURE_ID:
                return getTarget().getServiceDate();
            case FakturamaModelPackage.DUNNING_SHIPPING_FEATURE_ID:
                return getTarget().getShipping();
            case FakturamaModelPackage.DUNNING_SHIPPINGAUTOVAT_FEATURE_ID:
                return getTarget().getShippingAutoVat();
            case FakturamaModelPackage.DUNNING_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.DUNNING_SOURCEDOCUMENT_FEATURE_ID:
                return getTarget().getSourceDocument();
            case FakturamaModelPackage.DUNNING_TARA_FEATURE_ID:
                return getTarget().getTara();
            case FakturamaModelPackage.DUNNING_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.DUNNING_TRANSACTIONID_FEATURE_ID:
                return getTarget().getTransactionId();
            case FakturamaModelPackage.DUNNING_WEBSHOPDATE_FEATURE_ID:
                return getTarget().getWebshopDate();
            case FakturamaModelPackage.DUNNING_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.DUNNING_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.DUNNING_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.DUNNING_VERSION_FEATURE_ID:
                return getTarget().getVersion();
            case FakturamaModelPackage.DUNNING_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.DUNNING_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.DUNNING_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.DUNNING_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.DUNNING_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.DUNNING_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.DUNNING_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.DUNNING_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            case FakturamaModelPackage.DUNNING_DUNNINGLEVEL_FEATURE_ID:
                return getTarget().getDunningLevel();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.DUNNING_DUNNINGLEVEL_FEATURE_ID:
                getTarget().setDunningLevel((Integer) value);
                return;
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {

            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Debitor</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class DebitorModelObject<E extends Debitor> extends ContactModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getDebitorEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Debitor.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.DEBITOR_ALIAS_FEATURE_ID:
                return getTarget().getAlias();
            case FakturamaModelPackage.DEBITOR_CATEGORIES_FEATURE_ID:
                return getTarget().getCategories();
            case FakturamaModelPackage.DEBITOR_COMPANY_FEATURE_ID:
                return getTarget().getCompany();
            case FakturamaModelPackage.DEBITOR_CUSTOMERNUMBER_FEATURE_ID:
                return getTarget().getCustomerNumber();
            case FakturamaModelPackage.DEBITOR_TITLE_FEATURE_ID:
                return getTarget().getTitle();
            case FakturamaModelPackage.DEBITOR_FIRSTNAME_FEATURE_ID:
                return getTarget().getFirstName();
            case FakturamaModelPackage.DEBITOR_GENDER_FEATURE_ID:
                return getTarget().getGender();
            case FakturamaModelPackage.DEBITOR_BIRTHDAY_FEATURE_ID:
                return getTarget().getBirthday();
            case FakturamaModelPackage.DEBITOR_ADDRESSES_FEATURE_ID:
                return getTarget().getAddresses();
            case FakturamaModelPackage.DEBITOR_DISCOUNT_FEATURE_ID:
                return getTarget().getDiscount();
            case FakturamaModelPackage.DEBITOR_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.DEBITOR_RELIABILITY_FEATURE_ID:
                return getTarget().getReliability();
            case FakturamaModelPackage.DEBITOR_USENETGROSS_FEATURE_ID:
                return getTarget().getUseNetGross();
            case FakturamaModelPackage.DEBITOR_VATNUMBER_FEATURE_ID:
                return getTarget().getVatNumber();
            case FakturamaModelPackage.DEBITOR_VATNUMBERVALID_FEATURE_ID:
                return getTarget().getVatNumberValid();
            case FakturamaModelPackage.DEBITOR_WEBSITE_FEATURE_ID:
                return getTarget().getWebsite();
            case FakturamaModelPackage.DEBITOR_WEBSHOPNAME_FEATURE_ID:
                return getTarget().getWebshopName();
            case FakturamaModelPackage.DEBITOR_SUPPLIERNUMBER_FEATURE_ID:
                return getTarget().getSupplierNumber();
            case FakturamaModelPackage.DEBITOR_GLN_FEATURE_ID:
                return getTarget().getGln();
            case FakturamaModelPackage.DEBITOR_MANDATEREFERENCE_FEATURE_ID:
                return getTarget().getMandateReference();
            case FakturamaModelPackage.DEBITOR_BANKACCOUNT_FEATURE_ID:
                return getTarget().getBankAccount();
            case FakturamaModelPackage.DEBITOR_USESALESEQUALIZATIONTAX_FEATURE_ID:
                return getTarget().getUseSalesEqualizationTax();
            case FakturamaModelPackage.DEBITOR_NOTE_FEATURE_ID:
                return getTarget().getNote();
            case FakturamaModelPackage.DEBITOR_REGISTERNUMBER_FEATURE_ID:
                return getTarget().getRegisterNumber();
            case FakturamaModelPackage.DEBITOR_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.DEBITOR_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.DEBITOR_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.DEBITOR_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.DEBITOR_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.DEBITOR_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.DEBITOR_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.DEBITOR_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Invoice</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class InvoiceModelObject<E extends Invoice> extends DocumentModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getInvoiceEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Invoice.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.INVOICE_ADDITIONALINFO_FEATURE_ID:
                return getTarget().getAdditionalInfo();
            case FakturamaModelPackage.INVOICE_ADDRESSFIRSTLINE_FEATURE_ID:
                return getTarget().getAddressFirstLine();
            case FakturamaModelPackage.INVOICE_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.INVOICE_CUSTOMERREF_FEATURE_ID:
                return getTarget().getCustomerRef();
            case FakturamaModelPackage.INVOICE_DEPOSIT_FEATURE_ID:
                return getTarget().getDeposit();
            case FakturamaModelPackage.INVOICE_DOCUMENTDATE_FEATURE_ID:
                return getTarget().getDocumentDate();
            case FakturamaModelPackage.INVOICE_DUEDAYS_FEATURE_ID:
                return getTarget().getDueDays();
            case FakturamaModelPackage.INVOICE_INVOICEREFERENCE_FEATURE_ID:
                return getTarget().getInvoiceReference();
            case FakturamaModelPackage.INVOICE_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.INVOICE_ITEMSREBATE_FEATURE_ID:
                return getTarget().getItemsRebate();
            case FakturamaModelPackage.INVOICE_MESSAGE_FEATURE_ID:
                return getTarget().getMessage();
            case FakturamaModelPackage.INVOICE_MESSAGE2_FEATURE_ID:
                return getTarget().getMessage2();
            case FakturamaModelPackage.INVOICE_MESSAGE3_FEATURE_ID:
                return getTarget().getMessage3();
            case FakturamaModelPackage.INVOICE_NETGROSS_FEATURE_ID:
                return getTarget().getNetGross();
            case FakturamaModelPackage.INVOICE_NOVATREFERENCE_FEATURE_ID:
                return getTarget().getNoVatReference();
            case FakturamaModelPackage.INVOICE_ODTPATH_FEATURE_ID:
                return getTarget().getOdtPath();
            case FakturamaModelPackage.INVOICE_ORDERDATE_FEATURE_ID:
                return getTarget().getOrderDate();
            case FakturamaModelPackage.INVOICE_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.INVOICE_PAID_FEATURE_ID:
                return getTarget().getPaid();
            case FakturamaModelPackage.INVOICE_PAYDATE_FEATURE_ID:
                return getTarget().getPayDate();
            case FakturamaModelPackage.INVOICE_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.INVOICE_PDFPATH_FEATURE_ID:
                return getTarget().getPdfPath();
            case FakturamaModelPackage.INVOICE_PRINTED_FEATURE_ID:
                return getTarget().getPrinted();
            case FakturamaModelPackage.INVOICE_PRINTTEMPLATE_FEATURE_ID:
                return getTarget().getPrintTemplate();
            case FakturamaModelPackage.INVOICE_PROGRESS_FEATURE_ID:
                return getTarget().getProgress();
            case FakturamaModelPackage.INVOICE_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver();
            case FakturamaModelPackage.INVOICE_SERVICEDATE_FEATURE_ID:
                return getTarget().getServiceDate();
            case FakturamaModelPackage.INVOICE_SHIPPING_FEATURE_ID:
                return getTarget().getShipping();
            case FakturamaModelPackage.INVOICE_SHIPPINGAUTOVAT_FEATURE_ID:
                return getTarget().getShippingAutoVat();
            case FakturamaModelPackage.INVOICE_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.INVOICE_SOURCEDOCUMENT_FEATURE_ID:
                return getTarget().getSourceDocument();
            case FakturamaModelPackage.INVOICE_TARA_FEATURE_ID:
                return getTarget().getTara();
            case FakturamaModelPackage.INVOICE_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.INVOICE_TRANSACTIONID_FEATURE_ID:
                return getTarget().getTransactionId();
            case FakturamaModelPackage.INVOICE_WEBSHOPDATE_FEATURE_ID:
                return getTarget().getWebshopDate();
            case FakturamaModelPackage.INVOICE_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.INVOICE_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.INVOICE_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.INVOICE_VERSION_FEATURE_ID:
                return getTarget().getVersion();
            case FakturamaModelPackage.INVOICE_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.INVOICE_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.INVOICE_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.INVOICE_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.INVOICE_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.INVOICE_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.INVOICE_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.INVOICE_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass
     * '<em><b>ItemListTypeCategory</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ItemListTypeCategoryModelObject<E extends ItemListTypeCategory> extends AbstractCategoryModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getItemListTypeCategoryEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return ItemListTypeCategory.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.ITEMLISTTYPECATEGORY_PARENT_FEATURE_ID:
                return getTarget().getParent();
            case FakturamaModelPackage.ITEMLISTTYPECATEGORY_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.ITEMLISTTYPECATEGORY_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.ITEMLISTTYPECATEGORY_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.ITEMLISTTYPECATEGORY_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.ITEMLISTTYPECATEGORY_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.ITEMLISTTYPECATEGORY_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.ITEMLISTTYPECATEGORY_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.ITEMLISTTYPECATEGORY_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Letter</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class LetterModelObject<E extends Letter> extends DocumentModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getLetterEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Letter.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.LETTER_ADDITIONALINFO_FEATURE_ID:
                return getTarget().getAdditionalInfo();
            case FakturamaModelPackage.LETTER_ADDRESSFIRSTLINE_FEATURE_ID:
                return getTarget().getAddressFirstLine();
            case FakturamaModelPackage.LETTER_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.LETTER_CUSTOMERREF_FEATURE_ID:
                return getTarget().getCustomerRef();
            case FakturamaModelPackage.LETTER_DEPOSIT_FEATURE_ID:
                return getTarget().getDeposit();
            case FakturamaModelPackage.LETTER_DOCUMENTDATE_FEATURE_ID:
                return getTarget().getDocumentDate();
            case FakturamaModelPackage.LETTER_DUEDAYS_FEATURE_ID:
                return getTarget().getDueDays();
            case FakturamaModelPackage.LETTER_INVOICEREFERENCE_FEATURE_ID:
                return getTarget().getInvoiceReference();
            case FakturamaModelPackage.LETTER_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.LETTER_ITEMSREBATE_FEATURE_ID:
                return getTarget().getItemsRebate();
            case FakturamaModelPackage.LETTER_MESSAGE_FEATURE_ID:
                return getTarget().getMessage();
            case FakturamaModelPackage.LETTER_MESSAGE2_FEATURE_ID:
                return getTarget().getMessage2();
            case FakturamaModelPackage.LETTER_MESSAGE3_FEATURE_ID:
                return getTarget().getMessage3();
            case FakturamaModelPackage.LETTER_NETGROSS_FEATURE_ID:
                return getTarget().getNetGross();
            case FakturamaModelPackage.LETTER_NOVATREFERENCE_FEATURE_ID:
                return getTarget().getNoVatReference();
            case FakturamaModelPackage.LETTER_ODTPATH_FEATURE_ID:
                return getTarget().getOdtPath();
            case FakturamaModelPackage.LETTER_ORDERDATE_FEATURE_ID:
                return getTarget().getOrderDate();
            case FakturamaModelPackage.LETTER_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.LETTER_PAID_FEATURE_ID:
                return getTarget().getPaid();
            case FakturamaModelPackage.LETTER_PAYDATE_FEATURE_ID:
                return getTarget().getPayDate();
            case FakturamaModelPackage.LETTER_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.LETTER_PDFPATH_FEATURE_ID:
                return getTarget().getPdfPath();
            case FakturamaModelPackage.LETTER_PRINTED_FEATURE_ID:
                return getTarget().getPrinted();
            case FakturamaModelPackage.LETTER_PRINTTEMPLATE_FEATURE_ID:
                return getTarget().getPrintTemplate();
            case FakturamaModelPackage.LETTER_PROGRESS_FEATURE_ID:
                return getTarget().getProgress();
            case FakturamaModelPackage.LETTER_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver();
            case FakturamaModelPackage.LETTER_SERVICEDATE_FEATURE_ID:
                return getTarget().getServiceDate();
            case FakturamaModelPackage.LETTER_SHIPPING_FEATURE_ID:
                return getTarget().getShipping();
            case FakturamaModelPackage.LETTER_SHIPPINGAUTOVAT_FEATURE_ID:
                return getTarget().getShippingAutoVat();
            case FakturamaModelPackage.LETTER_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.LETTER_SOURCEDOCUMENT_FEATURE_ID:
                return getTarget().getSourceDocument();
            case FakturamaModelPackage.LETTER_TARA_FEATURE_ID:
                return getTarget().getTara();
            case FakturamaModelPackage.LETTER_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.LETTER_TRANSACTIONID_FEATURE_ID:
                return getTarget().getTransactionId();
            case FakturamaModelPackage.LETTER_WEBSHOPDATE_FEATURE_ID:
                return getTarget().getWebshopDate();
            case FakturamaModelPackage.LETTER_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.LETTER_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.LETTER_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.LETTER_VERSION_FEATURE_ID:
                return getTarget().getVersion();
            case FakturamaModelPackage.LETTER_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.LETTER_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.LETTER_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.LETTER_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.LETTER_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.LETTER_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.LETTER_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.LETTER_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Offer</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class OfferModelObject<E extends Offer> extends DocumentModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getOfferEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Offer.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.OFFER_ADDITIONALINFO_FEATURE_ID:
                return getTarget().getAdditionalInfo();
            case FakturamaModelPackage.OFFER_ADDRESSFIRSTLINE_FEATURE_ID:
                return getTarget().getAddressFirstLine();
            case FakturamaModelPackage.OFFER_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.OFFER_CUSTOMERREF_FEATURE_ID:
                return getTarget().getCustomerRef();
            case FakturamaModelPackage.OFFER_DEPOSIT_FEATURE_ID:
                return getTarget().getDeposit();
            case FakturamaModelPackage.OFFER_DOCUMENTDATE_FEATURE_ID:
                return getTarget().getDocumentDate();
            case FakturamaModelPackage.OFFER_DUEDAYS_FEATURE_ID:
                return getTarget().getDueDays();
            case FakturamaModelPackage.OFFER_INVOICEREFERENCE_FEATURE_ID:
                return getTarget().getInvoiceReference();
            case FakturamaModelPackage.OFFER_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.OFFER_ITEMSREBATE_FEATURE_ID:
                return getTarget().getItemsRebate();
            case FakturamaModelPackage.OFFER_MESSAGE_FEATURE_ID:
                return getTarget().getMessage();
            case FakturamaModelPackage.OFFER_MESSAGE2_FEATURE_ID:
                return getTarget().getMessage2();
            case FakturamaModelPackage.OFFER_MESSAGE3_FEATURE_ID:
                return getTarget().getMessage3();
            case FakturamaModelPackage.OFFER_NETGROSS_FEATURE_ID:
                return getTarget().getNetGross();
            case FakturamaModelPackage.OFFER_NOVATREFERENCE_FEATURE_ID:
                return getTarget().getNoVatReference();
            case FakturamaModelPackage.OFFER_ODTPATH_FEATURE_ID:
                return getTarget().getOdtPath();
            case FakturamaModelPackage.OFFER_ORDERDATE_FEATURE_ID:
                return getTarget().getOrderDate();
            case FakturamaModelPackage.OFFER_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.OFFER_PAID_FEATURE_ID:
                return getTarget().getPaid();
            case FakturamaModelPackage.OFFER_PAYDATE_FEATURE_ID:
                return getTarget().getPayDate();
            case FakturamaModelPackage.OFFER_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.OFFER_PDFPATH_FEATURE_ID:
                return getTarget().getPdfPath();
            case FakturamaModelPackage.OFFER_PRINTED_FEATURE_ID:
                return getTarget().getPrinted();
            case FakturamaModelPackage.OFFER_PRINTTEMPLATE_FEATURE_ID:
                return getTarget().getPrintTemplate();
            case FakturamaModelPackage.OFFER_PROGRESS_FEATURE_ID:
                return getTarget().getProgress();
            case FakturamaModelPackage.OFFER_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver();
            case FakturamaModelPackage.OFFER_SERVICEDATE_FEATURE_ID:
                return getTarget().getServiceDate();
            case FakturamaModelPackage.OFFER_SHIPPING_FEATURE_ID:
                return getTarget().getShipping();
            case FakturamaModelPackage.OFFER_SHIPPINGAUTOVAT_FEATURE_ID:
                return getTarget().getShippingAutoVat();
            case FakturamaModelPackage.OFFER_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.OFFER_SOURCEDOCUMENT_FEATURE_ID:
                return getTarget().getSourceDocument();
            case FakturamaModelPackage.OFFER_TARA_FEATURE_ID:
                return getTarget().getTara();
            case FakturamaModelPackage.OFFER_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.OFFER_TRANSACTIONID_FEATURE_ID:
                return getTarget().getTransactionId();
            case FakturamaModelPackage.OFFER_WEBSHOPDATE_FEATURE_ID:
                return getTarget().getWebshopDate();
            case FakturamaModelPackage.OFFER_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.OFFER_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.OFFER_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.OFFER_VERSION_FEATURE_ID:
                return getTarget().getVersion();
            case FakturamaModelPackage.OFFER_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.OFFER_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.OFFER_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.OFFER_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.OFFER_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.OFFER_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.OFFER_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.OFFER_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Order</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class OrderModelObject<E extends Order> extends DocumentModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getOrderEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Order.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.ORDER_ADDITIONALINFO_FEATURE_ID:
                return getTarget().getAdditionalInfo();
            case FakturamaModelPackage.ORDER_ADDRESSFIRSTLINE_FEATURE_ID:
                return getTarget().getAddressFirstLine();
            case FakturamaModelPackage.ORDER_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.ORDER_CUSTOMERREF_FEATURE_ID:
                return getTarget().getCustomerRef();
            case FakturamaModelPackage.ORDER_DEPOSIT_FEATURE_ID:
                return getTarget().getDeposit();
            case FakturamaModelPackage.ORDER_DOCUMENTDATE_FEATURE_ID:
                return getTarget().getDocumentDate();
            case FakturamaModelPackage.ORDER_DUEDAYS_FEATURE_ID:
                return getTarget().getDueDays();
            case FakturamaModelPackage.ORDER_INVOICEREFERENCE_FEATURE_ID:
                return getTarget().getInvoiceReference();
            case FakturamaModelPackage.ORDER_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.ORDER_ITEMSREBATE_FEATURE_ID:
                return getTarget().getItemsRebate();
            case FakturamaModelPackage.ORDER_MESSAGE_FEATURE_ID:
                return getTarget().getMessage();
            case FakturamaModelPackage.ORDER_MESSAGE2_FEATURE_ID:
                return getTarget().getMessage2();
            case FakturamaModelPackage.ORDER_MESSAGE3_FEATURE_ID:
                return getTarget().getMessage3();
            case FakturamaModelPackage.ORDER_NETGROSS_FEATURE_ID:
                return getTarget().getNetGross();
            case FakturamaModelPackage.ORDER_NOVATREFERENCE_FEATURE_ID:
                return getTarget().getNoVatReference();
            case FakturamaModelPackage.ORDER_ODTPATH_FEATURE_ID:
                return getTarget().getOdtPath();
            case FakturamaModelPackage.ORDER_ORDERDATE_FEATURE_ID:
                return getTarget().getOrderDate();
            case FakturamaModelPackage.ORDER_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.ORDER_PAID_FEATURE_ID:
                return getTarget().getPaid();
            case FakturamaModelPackage.ORDER_PAYDATE_FEATURE_ID:
                return getTarget().getPayDate();
            case FakturamaModelPackage.ORDER_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.ORDER_PDFPATH_FEATURE_ID:
                return getTarget().getPdfPath();
            case FakturamaModelPackage.ORDER_PRINTED_FEATURE_ID:
                return getTarget().getPrinted();
            case FakturamaModelPackage.ORDER_PRINTTEMPLATE_FEATURE_ID:
                return getTarget().getPrintTemplate();
            case FakturamaModelPackage.ORDER_PROGRESS_FEATURE_ID:
                return getTarget().getProgress();
            case FakturamaModelPackage.ORDER_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver();
            case FakturamaModelPackage.ORDER_SERVICEDATE_FEATURE_ID:
                return getTarget().getServiceDate();
            case FakturamaModelPackage.ORDER_SHIPPING_FEATURE_ID:
                return getTarget().getShipping();
            case FakturamaModelPackage.ORDER_SHIPPINGAUTOVAT_FEATURE_ID:
                return getTarget().getShippingAutoVat();
            case FakturamaModelPackage.ORDER_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.ORDER_SOURCEDOCUMENT_FEATURE_ID:
                return getTarget().getSourceDocument();
            case FakturamaModelPackage.ORDER_TARA_FEATURE_ID:
                return getTarget().getTara();
            case FakturamaModelPackage.ORDER_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.ORDER_TRANSACTIONID_FEATURE_ID:
                return getTarget().getTransactionId();
            case FakturamaModelPackage.ORDER_WEBSHOPDATE_FEATURE_ID:
                return getTarget().getWebshopDate();
            case FakturamaModelPackage.ORDER_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.ORDER_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.ORDER_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.ORDER_VERSION_FEATURE_ID:
                return getTarget().getVersion();
            case FakturamaModelPackage.ORDER_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.ORDER_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.ORDER_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.ORDER_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.ORDER_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.ORDER_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.ORDER_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.ORDER_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }

    /**
     * The adapter/wrapper for the EClass '<em><b>Proforma</b></em>'.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param <E>
     *            the domain model java class
     *
     * @generated
     */
    public static class ProformaModelObject<E extends Proforma> extends DocumentModelObject<E> {
        /**
         * @generated
         */
        @Override
        public EClass eClass() {
            return FakturamaModelPackage.INSTANCE.getProformaEClass();
        }

        /**
         * @generated
         */
        @Override
        public ModelPackage getModelPackage() {
            return FakturamaModelPackage.INSTANCE;
        }

        /**
         * @generated
         */
        @Override
        public Class<?> getTargetClass() {
            return Proforma.class;
        }

        /**
         * @generated
         */
        @Override
        public Object eGet(EStructuralFeature eStructuralFeature) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            case FakturamaModelPackage.PROFORMA_ADDITIONALINFO_FEATURE_ID:
                return getTarget().getAdditionalInfo();
            case FakturamaModelPackage.PROFORMA_ADDRESSFIRSTLINE_FEATURE_ID:
                return getTarget().getAddressFirstLine();
            case FakturamaModelPackage.PROFORMA_BILLINGTYPE_FEATURE_ID:
                return getTarget().getBillingType();
            case FakturamaModelPackage.PROFORMA_CUSTOMERREF_FEATURE_ID:
                return getTarget().getCustomerRef();
            case FakturamaModelPackage.PROFORMA_DEPOSIT_FEATURE_ID:
                return getTarget().getDeposit();
            case FakturamaModelPackage.PROFORMA_DOCUMENTDATE_FEATURE_ID:
                return getTarget().getDocumentDate();
            case FakturamaModelPackage.PROFORMA_DUEDAYS_FEATURE_ID:
                return getTarget().getDueDays();
            case FakturamaModelPackage.PROFORMA_INVOICEREFERENCE_FEATURE_ID:
                return getTarget().getInvoiceReference();
            case FakturamaModelPackage.PROFORMA_ITEMS_FEATURE_ID:
                return getTarget().getItems();
            case FakturamaModelPackage.PROFORMA_ITEMSREBATE_FEATURE_ID:
                return getTarget().getItemsRebate();
            case FakturamaModelPackage.PROFORMA_MESSAGE_FEATURE_ID:
                return getTarget().getMessage();
            case FakturamaModelPackage.PROFORMA_MESSAGE2_FEATURE_ID:
                return getTarget().getMessage2();
            case FakturamaModelPackage.PROFORMA_MESSAGE3_FEATURE_ID:
                return getTarget().getMessage3();
            case FakturamaModelPackage.PROFORMA_NETGROSS_FEATURE_ID:
                return getTarget().getNetGross();
            case FakturamaModelPackage.PROFORMA_NOVATREFERENCE_FEATURE_ID:
                return getTarget().getNoVatReference();
            case FakturamaModelPackage.PROFORMA_ODTPATH_FEATURE_ID:
                return getTarget().getOdtPath();
            case FakturamaModelPackage.PROFORMA_ORDERDATE_FEATURE_ID:
                return getTarget().getOrderDate();
            case FakturamaModelPackage.PROFORMA_PAIDVALUE_FEATURE_ID:
                return getTarget().getPaidValue();
            case FakturamaModelPackage.PROFORMA_PAID_FEATURE_ID:
                return getTarget().getPaid();
            case FakturamaModelPackage.PROFORMA_PAYDATE_FEATURE_ID:
                return getTarget().getPayDate();
            case FakturamaModelPackage.PROFORMA_PAYMENT_FEATURE_ID:
                return getTarget().getPayment();
            case FakturamaModelPackage.PROFORMA_PDFPATH_FEATURE_ID:
                return getTarget().getPdfPath();
            case FakturamaModelPackage.PROFORMA_PRINTED_FEATURE_ID:
                return getTarget().getPrinted();
            case FakturamaModelPackage.PROFORMA_PRINTTEMPLATE_FEATURE_ID:
                return getTarget().getPrintTemplate();
            case FakturamaModelPackage.PROFORMA_PROGRESS_FEATURE_ID:
                return getTarget().getProgress();
            case FakturamaModelPackage.PROFORMA_RECEIVER_FEATURE_ID:
                return getTarget().getReceiver();
            case FakturamaModelPackage.PROFORMA_SERVICEDATE_FEATURE_ID:
                return getTarget().getServiceDate();
            case FakturamaModelPackage.PROFORMA_SHIPPING_FEATURE_ID:
                return getTarget().getShipping();
            case FakturamaModelPackage.PROFORMA_SHIPPINGAUTOVAT_FEATURE_ID:
                return getTarget().getShippingAutoVat();
            case FakturamaModelPackage.PROFORMA_SHIPPINGVALUE_FEATURE_ID:
                return getTarget().getShippingValue();
            case FakturamaModelPackage.PROFORMA_SOURCEDOCUMENT_FEATURE_ID:
                return getTarget().getSourceDocument();
            case FakturamaModelPackage.PROFORMA_TARA_FEATURE_ID:
                return getTarget().getTara();
            case FakturamaModelPackage.PROFORMA_TOTALVALUE_FEATURE_ID:
                return getTarget().getTotalValue();
            case FakturamaModelPackage.PROFORMA_TRANSACTIONID_FEATURE_ID:
                return getTarget().getTransactionId();
            case FakturamaModelPackage.PROFORMA_WEBSHOPDATE_FEATURE_ID:
                return getTarget().getWebshopDate();
            case FakturamaModelPackage.PROFORMA_WEBSHOPID_FEATURE_ID:
                return getTarget().getWebshopId();
            case FakturamaModelPackage.PROFORMA_VESTINGPERIODSTART_FEATURE_ID:
                return getTarget().getVestingPeriodStart();
            case FakturamaModelPackage.PROFORMA_VESTINGPERIODEND_FEATURE_ID:
                return getTarget().getVestingPeriodEnd();
            case FakturamaModelPackage.PROFORMA_VERSION_FEATURE_ID:
                return getTarget().getVersion();
            case FakturamaModelPackage.PROFORMA_NAME_FEATURE_ID:
                return getTarget().getName();
            case FakturamaModelPackage.PROFORMA_DATEADDED_FEATURE_ID:
                return getTarget().getDateAdded();
            case FakturamaModelPackage.PROFORMA_MODIFIEDBY_FEATURE_ID:
                return getTarget().getModifiedBy();
            case FakturamaModelPackage.PROFORMA_MODIFIED_FEATURE_ID:
                return getTarget().getModified();
            case FakturamaModelPackage.PROFORMA_ID_FEATURE_ID:
                return getTarget().getId();
            case FakturamaModelPackage.PROFORMA_DELETED_FEATURE_ID:
                return getTarget().getDeleted();
            case FakturamaModelPackage.PROFORMA_VALIDFROM_FEATURE_ID:
                return getTarget().getValidFrom();
            case FakturamaModelPackage.PROFORMA_VALIDTO_FEATURE_ID:
                return getTarget().getValidTo();
            default:
                return super.eGet(eStructuralFeature);
            }
        }

        /**
         * @generated
         */
        @Override
        public void eSet(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                super.eSet(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eAddTo(eStructuralFeature, value);
            }
        }

        /**
         * @generated
         */
        @Override
        public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
            final int featureID = eClass().getFeatureID(eStructuralFeature);
            switch (featureID) {
            default:
                return super.eRemoveFrom(eStructuralFeature, value);
            }
        }
    }
}