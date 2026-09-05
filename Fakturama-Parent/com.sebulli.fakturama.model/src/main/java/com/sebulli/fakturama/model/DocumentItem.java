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
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

/**
 * A representation of the model object '<em><b>DocumentItem</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Holds an
 * item of a document. <!-- end-model-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_DOCUMENTITEM")
@EntityListeners(value = { EntityListener.class })
public class DocumentItem extends ModelObject implements Serializable, IDescribableEntity, Cloneable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This attribute gives an information if a VAT is used or not. If not, this
     * flag is <code>true</code>, else <code>false</code> (this is the default).
     * <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "NOVAT")
    private Boolean noVat = Boolean.FALSE;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The discount (rebate) if this item. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ITEMREBATE")
    private Double itemRebate = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ITEMNUMBER")
    private String itemNumber = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Reference to an existing Product. <!-- end-model-doc -->
     * 
     * @generated
     */
    // No REFRESH cascade: Product is static reference data, not part of what
    // DocumentEditor#init's forced findById(id, true) needs to guard against (unsaved edits to
    // *this document's own* items) - but em.refresh() always re-hits the DB regardless of
    // whether the entity is already cached, so REFRESH here means every document reopen also
    // re-fetches every line item's product (and, via Product's own REFRESH-less associations
    // below, would otherwise cascade into its category chain and VAT too).
    // FetchType.LAZY only takes effect with weaving enabled (see model/pom.xml's static-weave
    // execution). Without it, EclipseLink resolves this eagerly on every row regardless of the
    // annotation - which is exactly what still happened here even after DocumentsDAO's
    // warmItemProductCache() pre-fetch: that pre-fetch populates the cache, but an EAGER mapping
    // still gets independently re-resolved, one row at a time, whenever document.getItems() is
    // read afterward (confirmed via SQL log: ~40 individual FKT_PRODUCT/FKT_CATEGORY reads
    // opening one invoice, even with the pre-fetch in place).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "FK_PRODUCT") })
    private Product product = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "QUANTITY")
    private Double quantity = Double.valueOf(1.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Transient()
    private Double originQuantity = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "WEIGHT")
    private Double weight = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "OPTIONAL")
    private Boolean optional = Boolean.FALSE;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * picture of this product. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PICTURE")
    @Lob()
    private byte[] picture = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PRICE", precision = 12, scale = 3)
    private Double price = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "QUANTITYUNIT")
    private String quantityUnit = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The VAT of this item. <!-- end-model-doc -->
     * 
     * @generated
     */
    // No REFRESH cascade: see the note on product above - VAT is static reference data too.
    @ManyToOne()
    @JoinColumns({ @JoinColumn(name = "FK_VAT") })
    private VAT itemVat = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "GTIN")
    private Long gtin = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The posNr holds the position within the list of items for a certain
     * Document. This is beause databse fetches the rows in an unpredictable
     * order per default. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "POSNR")
    private Integer posNr = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ITEMTYPE")
    @Enumerated(EnumType.STRING)
    private ItemType itemType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The beginning of a vesting period. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VESTINGPERIODSTART")
    @Temporal(TemporalType.DATE)
    private Date vestingPeriodStart = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The end of a vesting period. If this field is not set but the
     * vestinPeriodStart is set, then that field is a fixed date for a given
     * deliverable. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VESTINGPERIODEND")
    @Temporal(TemporalType.DATE)
    private Date vestingPeriodEnd = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SUPPLIERITEMNUMBER")
    private String supplierItemNumber = null;

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
    public boolean isSameAs(DocumentItem other) {
        boolean retval = true;
        if (other != null) {
            if (noVat != null && other.getNoVat() != null) {
                retval &= noVat.compareTo(other.getNoVat()) == 0;
            }
            if (itemRebate != null && other.getItemRebate() != null) {
                retval &= itemRebate.compareTo(other.getItemRebate()) == 0;
            }
            if (itemNumber != null && other.getItemNumber() != null) {
                retval &= itemNumber.compareTo(other.getItemNumber()) == 0;
            }
            if (getProduct() != null) {
                retval &= getProduct().isSameAs(other.getProduct());
            }
            if (quantity != null && other.getQuantity() != null) {
                retval &= quantity.compareTo(other.getQuantity()) == 0;
            }
            if (originQuantity != null && other.getOriginQuantity() != null) {
                retval &= originQuantity.compareTo(other.getOriginQuantity()) == 0;
            }
            if (weight != null && other.getWeight() != null) {
                retval &= weight.compareTo(other.getWeight()) == 0;
            }
            if (optional != null && other.getOptional() != null) {
                retval &= optional.compareTo(other.getOptional()) == 0;
            }
            if (picture != null) {
                retval &= picture.equals(other.getPicture());
            }
            if (price != null && other.getPrice() != null) {
                retval &= price.compareTo(other.getPrice()) == 0;
            }
            if (quantityUnit != null && other.getQuantityUnit() != null) {
                retval &= quantityUnit.compareTo(other.getQuantityUnit()) == 0;
            }
            if (getItemVat() != null) {
                retval &= getItemVat().isSameAs(other.getItemVat());
            }
            if (gtin != null) {
                retval &= gtin.equals(other.getGtin());
            }
            if (posNr != null && other.getPosNr() != null) {
                retval &= posNr.compareTo(other.getPosNr()) == 0;
            }
            if (itemType != null) {
                retval &= itemType.equals(other.getItemType());
            }
            if (vestingPeriodStart != null && other.getVestingPeriodStart() != null) {
                retval &= vestingPeriodStart.compareTo(other.getVestingPeriodStart()) == 0;
            }
            if (vestingPeriodEnd != null && other.getVestingPeriodEnd() != null) {
                retval &= vestingPeriodEnd.compareTo(other.getVestingPeriodEnd()) == 0;
            }
            if (supplierItemNumber != null && other.getSupplierItemNumber() != null) {
                retval &= supplierItemNumber.compareTo(other.getSupplierItemNumber()) == 0;
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
    public DocumentItem clone() {
        DocumentItem retval = new DocumentItem();
        retval.setNoVat(this.getNoVat());
        retval.setItemRebate(this.getItemRebate());
        retval.setItemNumber(this.getItemNumber());
        retval.setProduct(this.getProduct());
        retval.setQuantity(this.getQuantity());
        retval.setOriginQuantity(this.getOriginQuantity());
        retval.setWeight(this.getWeight());
        retval.setOptional(this.getOptional());
        retval.setPicture(this.getPicture());
        retval.setPrice(this.getPrice());
        retval.setQuantityUnit(this.getQuantityUnit());
        retval.setItemVat(this.getItemVat());
        retval.setGtin(this.getGtin());
        retval.setPosNr(this.getPosNr());
        retval.setItemType(this.getItemType());
        retval.setVestingPeriodStart(this.getVestingPeriodStart());
        retval.setVestingPeriodEnd(this.getVestingPeriodEnd());
        retval.setSupplierItemNumber(this.getSupplierItemNumber());
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
     * Returns the value of '<em><b>noVat</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This attribute gives an information if a VAT is used or not. If not, this
     * flag is <code>true</code>, else <code>false</code> (this is the default).
     * <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>noVat</b></em>' feature
     * @generated
     */
    public Boolean getNoVat() {

        return noVat;
    }

    /**
     * Sets the '{@link DocumentItem#getNoVat() <em>noVat</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This attribute gives an information if a VAT is used or not. If not, this
     * flag is <code>true</code>, else <code>false</code> (this is the default).
     * <!-- end-model-doc -->
     * 
     * @param newNoVat
     *            the new value of the '{@link DocumentItem#getNoVat() noVat}'
     *            feature.
     * @generated
     */
    public void setNoVat(Boolean newNoVat) {
        firePropertyChange("noVat", this.noVat, newNoVat);
        noVat = newNoVat;
    }

    /**
     * Returns the value of '<em><b>itemRebate</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The discount (rebate) if this item. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>itemRebate</b></em>' feature
     * @generated
     */
    public Double getItemRebate() {

        return itemRebate;
    }

    /**
     * Sets the '{@link DocumentItem#getItemRebate() <em>itemRebate</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The discount (rebate) if this item. <!-- end-model-doc -->
     * 
     * @param newItemRebate
     *            the new value of the '{@link DocumentItem#getItemRebate()
     *            itemRebate}' feature.
     * @generated
     */
    public void setItemRebate(Double newItemRebate) {
        firePropertyChange("itemRebate", this.itemRebate, newItemRebate);
        itemRebate = newItemRebate;
    }

    /**
     * Returns the value of '<em><b>itemNumber</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>itemNumber</b></em>' feature
     * @generated
     */
    public String getItemNumber() {

        return itemNumber;
    }

    /**
     * Sets the '{@link DocumentItem#getItemNumber() <em>itemNumber</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newItemNumber
     *            the new value of the '{@link DocumentItem#getItemNumber()
     *            itemNumber}' feature.
     * @generated
     */
    public void setItemNumber(String newItemNumber) {
        firePropertyChange("itemNumber", this.itemNumber, newItemNumber);
        itemNumber = newItemNumber;
    }

    /**
     * Returns the value of '<em><b>product</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Reference to an existing Product. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>product</b></em>' feature
     * @generated
     */
    public Product getProduct() {

        return product;
    }

    /**
     * Sets the '{@link DocumentItem#getProduct() <em>product</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Reference to an existing Product. <!-- end-model-doc -->
     * 
     * @param newProduct
     *            the new value of the '{@link DocumentItem#getProduct()
     *            product}' feature.
     * @generated
     */
    public void setProduct(Product newProduct) {
        firePropertyChange("product", this.product, newProduct);
        product = newProduct;
    }

    /**
     * Returns the value of '<em><b>quantity</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>quantity</b></em>' feature
     * @generated
     */
    public Double getQuantity() {

        return quantity;
    }

    /**
     * Sets the '{@link DocumentItem#getQuantity() <em>quantity</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newQuantity
     *            the new value of the '{@link DocumentItem#getQuantity()
     *            quantity}' feature.
     * @generated
     */
    public void setQuantity(Double newQuantity) {
        firePropertyChange("quantity", this.quantity, newQuantity);
        quantity = newQuantity;
    }

    /**
     * Returns the value of '<em><b>originQuantity</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>originQuantity</b></em>' feature
     * @generated
     */
    public Double getOriginQuantity() {

        return originQuantity;
    }

    /**
     * Sets the '{@link DocumentItem#getOriginQuantity()
     * <em>originQuantity</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newOriginQuantity
     *            the new value of the '{@link DocumentItem#getOriginQuantity()
     *            originQuantity}' feature.
     * @generated
     */
    public void setOriginQuantity(Double newOriginQuantity) {
        firePropertyChange("originQuantity", this.originQuantity, newOriginQuantity);
        originQuantity = newOriginQuantity;
    }

    /**
     * Returns the value of '<em><b>weight</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>weight</b></em>' feature
     * @generated
     */
    public Double getWeight() {

        return weight;
    }

    /**
     * Sets the '{@link DocumentItem#getWeight() <em>weight</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newWeight
     *            the new value of the '{@link DocumentItem#getWeight() weight}'
     *            feature.
     * @generated
     */
    public void setWeight(Double newWeight) {
        firePropertyChange("weight", this.weight, newWeight);
        weight = newWeight;
    }

    /**
     * Returns the value of '<em><b>optional</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>optional</b></em>' feature
     * @generated
     */
    public Boolean getOptional() {

        return optional;
    }

    /**
     * Sets the '{@link DocumentItem#getOptional() <em>optional</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newOptional
     *            the new value of the '{@link DocumentItem#getOptional()
     *            optional}' feature.
     * @generated
     */
    public void setOptional(Boolean newOptional) {
        firePropertyChange("optional", this.optional, newOptional);
        optional = newOptional;
    }

    /**
     * Returns the value of '<em><b>picture</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * picture of this product. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>picture</b></em>' feature
     * @generated
     */
    public byte[] getPicture() {

        return picture;
    }

    /**
     * Sets the '{@link DocumentItem#getPicture() <em>picture</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * picture of this product. <!-- end-model-doc -->
     * 
     * @param newPicture
     *            the new value of the '{@link DocumentItem#getPicture()
     *            picture}' feature.
     * @generated
     */
    public void setPicture(byte[] newPicture) {
        firePropertyChange("picture", this.picture, newPicture);
        picture = newPicture;
    }

    /**
     * Returns the value of '<em><b>price</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>price</b></em>' feature
     * @generated
     */
    public Double getPrice() {

        return price;
    }

    /**
     * Sets the '{@link DocumentItem#getPrice() <em>price</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPrice
     *            the new value of the '{@link DocumentItem#getPrice() price}'
     *            feature.
     * @generated
     */
    public void setPrice(Double newPrice) {
        firePropertyChange("price", this.price, newPrice);
        price = newPrice;
    }

    /**
     * Returns the value of '<em><b>quantityUnit</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>quantityUnit</b></em>' feature
     * @generated
     */
    public String getQuantityUnit() {

        return quantityUnit;
    }

    /**
     * Sets the '{@link DocumentItem#getQuantityUnit() <em>quantityUnit</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newQuantityUnit
     *            the new value of the '{@link DocumentItem#getQuantityUnit()
     *            quantityUnit}' feature.
     * @generated
     */
    public void setQuantityUnit(String newQuantityUnit) {
        firePropertyChange("quantityUnit", this.quantityUnit, newQuantityUnit);
        quantityUnit = newQuantityUnit;
    }

    /**
     * Returns the value of '<em><b>itemVat</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The VAT of this item. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>itemVat</b></em>' feature
     * @generated
     */
    public VAT getItemVat() {

        return itemVat;
    }

    /**
     * Sets the '{@link DocumentItem#getItemVat() <em>itemVat</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The VAT of this item. <!-- end-model-doc -->
     * 
     * @param newItemVat
     *            the new value of the '{@link DocumentItem#getItemVat()
     *            itemVat}' feature.
     * @generated
     */
    public void setItemVat(VAT newItemVat) {
        firePropertyChange("itemVat", this.itemVat, newItemVat);
        itemVat = newItemVat;
    }

    /**
     * Returns the value of '<em><b>gtin</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>gtin</b></em>' feature
     * @generated
     */
    public Long getGtin() {

        return gtin;
    }

    /**
     * Sets the '{@link DocumentItem#getGtin() <em>gtin</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newGtin
     *            the new value of the '{@link DocumentItem#getGtin() gtin}'
     *            feature.
     * @generated
     */
    public void setGtin(Long newGtin) {
        firePropertyChange("gtin", this.gtin, newGtin);
        gtin = newGtin;
    }

    /**
     * Returns the value of '<em><b>posNr</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The posNr holds the position within the list of items for a certain
     * Document. This is beause databse fetches the rows in an unpredictable
     * order per default. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>posNr</b></em>' feature
     * @generated
     */
    public Integer getPosNr() {

        return posNr;
    }

    /**
     * Sets the '{@link DocumentItem#getPosNr() <em>posNr</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The posNr holds the position within the list of items for a certain
     * Document. This is beause databse fetches the rows in an unpredictable
     * order per default. <!-- end-model-doc -->
     * 
     * @param newPosNr
     *            the new value of the '{@link DocumentItem#getPosNr() posNr}'
     *            feature.
     * @generated
     */
    public void setPosNr(Integer newPosNr) {
        firePropertyChange("posNr", this.posNr, newPosNr);
        posNr = newPosNr;
    }

    /**
     * Returns the value of '<em><b>itemType</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>itemType</b></em>' feature
     * @generated
     */
    public ItemType getItemType() {

        return itemType;
    }

    /**
     * Sets the '{@link DocumentItem#getItemType() <em>itemType</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newItemType
     *            the new value of the '{@link DocumentItem#getItemType()
     *            itemType}' feature.
     * @generated
     */
    public void setItemType(ItemType newItemType) {
        firePropertyChange("itemType", this.itemType, newItemType);
        itemType = newItemType;
    }

    /**
     * Returns the value of '<em><b>vestingPeriodStart</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The beginning of a vesting period. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>vestingPeriodStart</b></em>' feature
     * @generated
     */
    public Date getVestingPeriodStart() {

        return vestingPeriodStart;
    }

    /**
     * Sets the '{@link DocumentItem#getVestingPeriodStart()
     * <em>vestingPeriodStart</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The beginning of a vesting period. <!-- end-model-doc -->
     * 
     * @param newVestingPeriodStart
     *            the new value of the
     *            '{@link DocumentItem#getVestingPeriodStart()
     *            vestingPeriodStart}' feature.
     * @generated
     */
    public void setVestingPeriodStart(Date newVestingPeriodStart) {
        firePropertyChange("vestingPeriodStart", this.vestingPeriodStart, newVestingPeriodStart);
        vestingPeriodStart = newVestingPeriodStart;
    }

    /**
     * Returns the value of '<em><b>vestingPeriodEnd</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The end of a vesting period. If this field is not set but the
     * vestinPeriodStart is set, then that field is a fixed date for a given
     * deliverable. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>vestingPeriodEnd</b></em>' feature
     * @generated
     */
    public Date getVestingPeriodEnd() {

        return vestingPeriodEnd;
    }

    /**
     * Sets the '{@link DocumentItem#getVestingPeriodEnd()
     * <em>vestingPeriodEnd</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The end of a vesting period. If this field is not set but the
     * vestinPeriodStart is set, then that field is a fixed date for a given
     * deliverable. <!-- end-model-doc -->
     * 
     * @param newVestingPeriodEnd
     *            the new value of the
     *            '{@link DocumentItem#getVestingPeriodEnd() vestingPeriodEnd}'
     *            feature.
     * @generated
     */
    public void setVestingPeriodEnd(Date newVestingPeriodEnd) {
        firePropertyChange("vestingPeriodEnd", this.vestingPeriodEnd, newVestingPeriodEnd);
        vestingPeriodEnd = newVestingPeriodEnd;
    }

    /**
     * Returns the value of '<em><b>supplierItemNumber</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>supplierItemNumber</b></em>' feature
     * @generated
     */
    public String getSupplierItemNumber() {

        return supplierItemNumber;
    }

    /**
     * Sets the '{@link DocumentItem#getSupplierItemNumber()
     * <em>supplierItemNumber</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newSupplierItemNumber
     *            the new value of the
     *            '{@link DocumentItem#getSupplierItemNumber()
     *            supplierItemNumber}' feature.
     * @generated
     */
    public void setSupplierItemNumber(String newSupplierItemNumber) {
        firePropertyChange("supplierItemNumber", this.supplierItemNumber, newSupplierItemNumber);
        supplierItemNumber = newSupplierItemNumber;
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
     * Sets the '{@link DocumentItem#getDescription() <em>description</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDescription
     *            the new value of the '{@link DocumentItem#getDescription()
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
     * Sets the '{@link DocumentItem#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link DocumentItem#getName() name}'
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
     * Sets the '{@link DocumentItem#getDateAdded() <em>dateAdded</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link DocumentItem#getDateAdded()
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
     * Sets the '{@link DocumentItem#getModifiedBy() <em>modifiedBy</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link DocumentItem#getModifiedBy()
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
     * Sets the '{@link DocumentItem#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link DocumentItem#getModified()
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
     * Sets the '{@link DocumentItem#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link DocumentItem#getId() id}'
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
     * Sets the '{@link DocumentItem#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link DocumentItem#getDeleted()
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
     * Sets the '{@link DocumentItem#getValidFrom() <em>validFrom</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link DocumentItem#getValidFrom()
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
     * Sets the '{@link DocumentItem#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link DocumentItem#getValidTo()
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
        return "DocumentItem" + " noVat: [" + getNoVat() + "]" + " itemRebate: [" + getItemRebate() + "]" + " itemNumber: [" + getItemNumber() + "]"
                + " quantity: [" + getQuantity() + "]" + " originQuantity: [" + getOriginQuantity() + "]" + " weight: [" + getWeight() + "]" + " optional: ["
                + getOptional() + "]" + " picture: [" + getPicture() + "]" + " price: [" + getPrice() + "]" + " quantityUnit: [" + getQuantityUnit() + "]"
                + " gtin: [" + getGtin() + "]" + " posNr: [" + getPosNr() + "]" + " itemType: [" + getItemType() + "]" + " vestingPeriodStart: ["
                + getVestingPeriodStart() + "]" + " vestingPeriodEnd: [" + getVestingPeriodEnd() + "]" + " supplierItemNumber: [" + getSupplierItemNumber()
                + "]" + " description: [" + getDescription() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded() + "]" + " modifiedBy: ["
                + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: [" + getDeleted() + "]" + " validFrom: ["
                + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
