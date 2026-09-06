package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.lang.Boolean;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

/**
 * A representation of the model object '<em><b>ProductWebshop</b></em>'.
 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
 * Holds the webshop-specific "Bewegungsdaten" for a Product that may
 * legitimately diverge from the Fakturama values - shop price/stock, plus
 * delivery time and sync bookkeeping for the WooCommerce sync client. Master
 * data (name, description, categories) stays shop-managed and is
 * deliberately not part of this entity. At most one row exists per Product
 * (enforced by a unique index on FK_PRODUCT, see the changelog), for
 * products flagged as "im Shop". <!-- end-model-doc -->
 *
 * @generated
 */
@Entity()
@Table(name = "FKT_PRODUCTWEBSHOP")
public class ProductWebshop extends ModelObject implements Serializable, IEntity, Cloneable {
    /**
     * A common serial ID.
     *
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The Fakturama product this webshop overlay belongs to. <!--
     * end-model-doc -->
     *
     * @generated
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "FK_PRODUCT") })
    private Product product = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Shop price, maps to WooCommerce's regular_price. <!-- end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "SHOPPRICE")
    private Double shopPrice = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Sale/promotional price, maps to WooCommerce's sale_price. <!--
     * end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "SHOPSALEPRICE")
    private Double shopSalePrice = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Start of the sale price window, maps to WooCommerce's
     * date_on_sale_from. <!-- end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "SHOPSALEFROM")
    @Temporal(TemporalType.DATE)
    private Date shopSaleFrom = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * End of the sale price window, maps to WooCommerce's date_on_sale_to.
     * <!-- end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "SHOPSALETO")
    @Temporal(TemporalType.DATE)
    private Date shopSaleTo = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Shop stock quantity, maps to WooCommerce's stock_quantity. May diverge
     * from the actual Fakturama warehouse stock on purpose (e.g. shop shows
     * less than what's physically on hand). <!-- end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "SHOPSTOCKQUANTITY")
    private Double shopStockQuantity = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Shop stock status override (instock/outofstock/onbackorder), maps to
     * WooCommerce's stock_status. <!-- end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "SHOPSTOCKSTATUS")
    private String shopStockStatus = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Backorder handling (no/notify/yes), maps to WooCommerce's backorders.
     * <!-- end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "SHOPBACKORDERS")
    private String shopBackorders = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Shop-specific reorder threshold, maps to WooCommerce's
     * low_stock_amount. Independent of Fakturama's own Meldebestand. <!--
     * end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "SHOPLOWSTOCKAMOUNT")
    private Integer shopLowStockAmount = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Delivery time label, maps to the Germanized plugin's delivery_time
     * field (not core WooCommerce, but actively maintained per-product in
     * prod). <!-- end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "DELIVERYTIME")
    private String deliveryTime = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Timestamp of the last successful push to the webshop. <!--
     * end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "LASTSYNCAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSyncAt = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Outcome of the last sync attempt (e.g. OK/ERROR). <!-- end-model-doc
     * -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "LASTSYNCSTATUS")
    private String lastSyncStatus = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Error text from the last failed sync attempt, if any. <!--
     * end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Lob()
    @Column(name = "LASTSYNCERROR")
    private String lastSyncError = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Checksum of the values last sent to the webshop, so unchanged articles
     * can be skipped on the next sync run instead of always pushing
     * everything. <!-- end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "SYNCHASH")
    private String syncHash = null;

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
    public boolean isSameAs(ProductWebshop other) {
        boolean retval = true;
        if (other != null) {
            if (shopPrice != null && other.getShopPrice() != null) {
                retval &= shopPrice.compareTo(other.getShopPrice()) == 0;
            }
            if (shopSalePrice != null && other.getShopSalePrice() != null) {
                retval &= shopSalePrice.compareTo(other.getShopSalePrice()) == 0;
            }
            if (shopSaleFrom != null && other.getShopSaleFrom() != null) {
                retval &= shopSaleFrom.compareTo(other.getShopSaleFrom()) == 0;
            }
            if (shopSaleTo != null && other.getShopSaleTo() != null) {
                retval &= shopSaleTo.compareTo(other.getShopSaleTo()) == 0;
            }
            if (shopStockQuantity != null && other.getShopStockQuantity() != null) {
                retval &= shopStockQuantity.compareTo(other.getShopStockQuantity()) == 0;
            }
            if (shopStockStatus != null && other.getShopStockStatus() != null) {
                retval &= shopStockStatus.compareTo(other.getShopStockStatus()) == 0;
            }
            if (shopBackorders != null && other.getShopBackorders() != null) {
                retval &= shopBackorders.compareTo(other.getShopBackorders()) == 0;
            }
            if (shopLowStockAmount != null && other.getShopLowStockAmount() != null) {
                retval &= shopLowStockAmount.compareTo(other.getShopLowStockAmount()) == 0;
            }
            if (deliveryTime != null && other.getDeliveryTime() != null) {
                retval &= deliveryTime.compareTo(other.getDeliveryTime()) == 0;
            }
            if (lastSyncAt != null && other.getLastSyncAt() != null) {
                retval &= lastSyncAt.compareTo(other.getLastSyncAt()) == 0;
            }
            if (lastSyncStatus != null && other.getLastSyncStatus() != null) {
                retval &= lastSyncStatus.compareTo(other.getLastSyncStatus()) == 0;
            }
            if (lastSyncError != null && other.getLastSyncError() != null) {
                retval &= lastSyncError.compareTo(other.getLastSyncError()) == 0;
            }
            if (syncHash != null && other.getSyncHash() != null) {
                retval &= syncHash.compareTo(other.getSyncHash()) == 0;
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
    public ProductWebshop clone() {
        ProductWebshop retval = new ProductWebshop();
        retval.setProduct(this.getProduct());
        retval.setShopPrice(this.getShopPrice());
        retval.setShopSalePrice(this.getShopSalePrice());
        retval.setShopSaleFrom(this.getShopSaleFrom());
        retval.setShopSaleTo(this.getShopSaleTo());
        retval.setShopStockQuantity(this.getShopStockQuantity());
        retval.setShopStockStatus(this.getShopStockStatus());
        retval.setShopBackorders(this.getShopBackorders());
        retval.setShopLowStockAmount(this.getShopLowStockAmount());
        retval.setDeliveryTime(this.getDeliveryTime());
        retval.setLastSyncAt(this.getLastSyncAt());
        retval.setLastSyncStatus(this.getLastSyncStatus());
        retval.setLastSyncError(this.getLastSyncError());
        retval.setSyncHash(this.getSyncHash());
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
     * Returns the value of '<em><b>product</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>product</b></em>' feature
     * @generated
     */
    public Product getProduct() {

        return product;
    }

    /**
     * Sets the '{@link ProductWebshop#getProduct() <em>product</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newProduct
     *            the new value of the '{@link ProductWebshop#getProduct()
     *            product}' feature.
     * @generated
     */
    public void setProduct(Product newProduct) {
        firePropertyChange("product", this.product, newProduct);
        product = newProduct;
    }

    /**
     * Returns the value of '<em><b>shopPrice</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>shopPrice</b></em>' feature
     * @generated
     */
    public Double getShopPrice() {

        return shopPrice;
    }

    /**
     * Sets the '{@link ProductWebshop#getShopPrice() <em>shopPrice</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newShopPrice
     *            the new value of the '{@link ProductWebshop#getShopPrice()
     *            shopPrice}' feature.
     * @generated
     */
    public void setShopPrice(Double newShopPrice) {
        firePropertyChange("shopPrice", this.shopPrice, newShopPrice);
        shopPrice = newShopPrice;
    }

    /**
     * Returns the value of '<em><b>shopSalePrice</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>shopSalePrice</b></em>' feature
     * @generated
     */
    public Double getShopSalePrice() {

        return shopSalePrice;
    }

    /**
     * Sets the '{@link ProductWebshop#getShopSalePrice()
     * <em>shopSalePrice</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newShopSalePrice
     *            the new value of the
     *            '{@link ProductWebshop#getShopSalePrice() shopSalePrice}'
     *            feature.
     * @generated
     */
    public void setShopSalePrice(Double newShopSalePrice) {
        firePropertyChange("shopSalePrice", this.shopSalePrice, newShopSalePrice);
        shopSalePrice = newShopSalePrice;
    }

    /**
     * Returns the value of '<em><b>shopSaleFrom</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>shopSaleFrom</b></em>' feature
     * @generated
     */
    public Date getShopSaleFrom() {

        return shopSaleFrom;
    }

    /**
     * Sets the '{@link ProductWebshop#getShopSaleFrom()
     * <em>shopSaleFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newShopSaleFrom
     *            the new value of the '{@link ProductWebshop#getShopSaleFrom()
     *            shopSaleFrom}' feature.
     * @generated
     */
    public void setShopSaleFrom(Date newShopSaleFrom) {
        firePropertyChange("shopSaleFrom", this.shopSaleFrom, newShopSaleFrom);
        shopSaleFrom = newShopSaleFrom;
    }

    /**
     * Returns the value of '<em><b>shopSaleTo</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>shopSaleTo</b></em>' feature
     * @generated
     */
    public Date getShopSaleTo() {

        return shopSaleTo;
    }

    /**
     * Sets the '{@link ProductWebshop#getShopSaleTo() <em>shopSaleTo</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newShopSaleTo
     *            the new value of the '{@link ProductWebshop#getShopSaleTo()
     *            shopSaleTo}' feature.
     * @generated
     */
    public void setShopSaleTo(Date newShopSaleTo) {
        firePropertyChange("shopSaleTo", this.shopSaleTo, newShopSaleTo);
        shopSaleTo = newShopSaleTo;
    }

    /**
     * Returns the value of '<em><b>shopStockQuantity</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>shopStockQuantity</b></em>' feature
     * @generated
     */
    public Double getShopStockQuantity() {

        return shopStockQuantity;
    }

    /**
     * Sets the '{@link ProductWebshop#getShopStockQuantity()
     * <em>shopStockQuantity</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newShopStockQuantity
     *            the new value of the
     *            '{@link ProductWebshop#getShopStockQuantity()
     *            shopStockQuantity}' feature.
     * @generated
     */
    public void setShopStockQuantity(Double newShopStockQuantity) {
        firePropertyChange("shopStockQuantity", this.shopStockQuantity, newShopStockQuantity);
        shopStockQuantity = newShopStockQuantity;
    }

    /**
     * Returns the value of '<em><b>shopStockStatus</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>shopStockStatus</b></em>' feature
     * @generated
     */
    public String getShopStockStatus() {

        return shopStockStatus;
    }

    /**
     * Sets the '{@link ProductWebshop#getShopStockStatus()
     * <em>shopStockStatus</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newShopStockStatus
     *            the new value of the
     *            '{@link ProductWebshop#getShopStockStatus() shopStockStatus}'
     *            feature.
     * @generated
     */
    public void setShopStockStatus(String newShopStockStatus) {
        firePropertyChange("shopStockStatus", this.shopStockStatus, newShopStockStatus);
        shopStockStatus = newShopStockStatus;
    }

    /**
     * Returns the value of '<em><b>shopBackorders</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>shopBackorders</b></em>' feature
     * @generated
     */
    public String getShopBackorders() {

        return shopBackorders;
    }

    /**
     * Sets the '{@link ProductWebshop#getShopBackorders()
     * <em>shopBackorders</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newShopBackorders
     *            the new value of the
     *            '{@link ProductWebshop#getShopBackorders() shopBackorders}'
     *            feature.
     * @generated
     */
    public void setShopBackorders(String newShopBackorders) {
        firePropertyChange("shopBackorders", this.shopBackorders, newShopBackorders);
        shopBackorders = newShopBackorders;
    }

    /**
     * Returns the value of '<em><b>shopLowStockAmount</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>shopLowStockAmount</b></em>' feature
     * @generated
     */
    public Integer getShopLowStockAmount() {

        return shopLowStockAmount;
    }

    /**
     * Sets the '{@link ProductWebshop#getShopLowStockAmount()
     * <em>shopLowStockAmount</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newShopLowStockAmount
     *            the new value of the
     *            '{@link ProductWebshop#getShopLowStockAmount()
     *            shopLowStockAmount}' feature.
     * @generated
     */
    public void setShopLowStockAmount(Integer newShopLowStockAmount) {
        firePropertyChange("shopLowStockAmount", this.shopLowStockAmount, newShopLowStockAmount);
        shopLowStockAmount = newShopLowStockAmount;
    }

    /**
     * Returns the value of '<em><b>deliveryTime</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>deliveryTime</b></em>' feature
     * @generated
     */
    public String getDeliveryTime() {

        return deliveryTime;
    }

    /**
     * Sets the '{@link ProductWebshop#getDeliveryTime()
     * <em>deliveryTime</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newDeliveryTime
     *            the new value of the '{@link ProductWebshop#getDeliveryTime()
     *            deliveryTime}' feature.
     * @generated
     */
    public void setDeliveryTime(String newDeliveryTime) {
        firePropertyChange("deliveryTime", this.deliveryTime, newDeliveryTime);
        deliveryTime = newDeliveryTime;
    }

    /**
     * Returns the value of '<em><b>lastSyncAt</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>lastSyncAt</b></em>' feature
     * @generated
     */
    public Date getLastSyncAt() {

        return lastSyncAt;
    }

    /**
     * Sets the '{@link ProductWebshop#getLastSyncAt() <em>lastSyncAt</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newLastSyncAt
     *            the new value of the '{@link ProductWebshop#getLastSyncAt()
     *            lastSyncAt}' feature.
     * @generated
     */
    public void setLastSyncAt(Date newLastSyncAt) {
        firePropertyChange("lastSyncAt", this.lastSyncAt, newLastSyncAt);
        lastSyncAt = newLastSyncAt;
    }

    /**
     * Returns the value of '<em><b>lastSyncStatus</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>lastSyncStatus</b></em>' feature
     * @generated
     */
    public String getLastSyncStatus() {

        return lastSyncStatus;
    }

    /**
     * Sets the '{@link ProductWebshop#getLastSyncStatus()
     * <em>lastSyncStatus</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newLastSyncStatus
     *            the new value of the
     *            '{@link ProductWebshop#getLastSyncStatus() lastSyncStatus}'
     *            feature.
     * @generated
     */
    public void setLastSyncStatus(String newLastSyncStatus) {
        firePropertyChange("lastSyncStatus", this.lastSyncStatus, newLastSyncStatus);
        lastSyncStatus = newLastSyncStatus;
    }

    /**
     * Returns the value of '<em><b>lastSyncError</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>lastSyncError</b></em>' feature
     * @generated
     */
    public String getLastSyncError() {

        return lastSyncError;
    }

    /**
     * Sets the '{@link ProductWebshop#getLastSyncError()
     * <em>lastSyncError</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newLastSyncError
     *            the new value of the
     *            '{@link ProductWebshop#getLastSyncError() lastSyncError}'
     *            feature.
     * @generated
     */
    public void setLastSyncError(String newLastSyncError) {
        firePropertyChange("lastSyncError", this.lastSyncError, newLastSyncError);
        lastSyncError = newLastSyncError;
    }

    /**
     * Returns the value of '<em><b>syncHash</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of '<em><b>syncHash</b></em>' feature
     * @generated
     */
    public String getSyncHash() {

        return syncHash;
    }

    /**
     * Sets the '{@link ProductWebshop#getSyncHash() <em>syncHash</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newSyncHash
     *            the new value of the '{@link ProductWebshop#getSyncHash()
     *            syncHash}' feature.
     * @generated
     */
    public void setSyncHash(String newSyncHash) {
        firePropertyChange("syncHash", this.syncHash, newSyncHash);
        syncHash = newSyncHash;
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
     * Sets the '{@link ProductWebshop#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     *
     * @param newName
     *            the new value of the '{@link ProductWebshop#getName() name}'
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
     * Sets the '{@link ProductWebshop#getDateAdded() <em>dateAdded</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     *
     * @param newDateAdded
     *            the new value of the '{@link ProductWebshop#getDateAdded()
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
     * Sets the '{@link ProductWebshop#getModifiedBy() <em>modifiedBy</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newModifiedBy
     *            the new value of the '{@link ProductWebshop#getModifiedBy()
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
     * Sets the '{@link ProductWebshop#getModified() <em>modified</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newModified
     *            the new value of the '{@link ProductWebshop#getModified()
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
     * Sets the '{@link ProductWebshop#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newId
     *            the new value of the '{@link ProductWebshop#getId() id}'
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
     * Sets the '{@link ProductWebshop#getDeleted() <em>deleted</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     *
     * @param newDeleted
     *            the new value of the '{@link ProductWebshop#getDeleted()
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
     * Sets the '{@link ProductWebshop#getValidFrom() <em>validFrom</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     *
     * @param newValidFrom
     *            the new value of the '{@link ProductWebshop#getValidFrom()
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
     * Sets the '{@link ProductWebshop#getValidTo() <em>validTo</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     *
     * @param newValidTo
     *            the new value of the '{@link ProductWebshop#getValidTo()
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
        return "ProductWebshop" + " shopPrice: [" + getShopPrice() + "]" + " shopSalePrice: [" + getShopSalePrice() + "]" + " shopSaleFrom: ["
                + getShopSaleFrom() + "]" + " shopSaleTo: [" + getShopSaleTo() + "]" + " shopStockQuantity: [" + getShopStockQuantity() + "]"
                + " shopStockStatus: [" + getShopStockStatus() + "]" + " shopBackorders: [" + getShopBackorders() + "]" + " shopLowStockAmount: ["
                + getShopLowStockAmount() + "]" + " deliveryTime: [" + getDeliveryTime() + "]" + " lastSyncAt: [" + getLastSyncAt() + "]" + " lastSyncStatus: ["
                + getLastSyncStatus() + "]" + " lastSyncError: [" + getLastSyncError() + "]" + " syncHash: [" + getSyncHash() + "]" + " name: [" + getName()
                + "]" + " dateAdded: [" + getDateAdded() + "]" + " modifiedBy: [" + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: ["
                + getId() + "]" + " deleted: [" + getDeleted() + "]" + " validFrom: [" + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
