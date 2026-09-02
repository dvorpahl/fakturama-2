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
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * A representation of the model object '<em><b>Product</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This entity
 * represents a Product. <!-- end-model-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_PRODUCT")
@EntityListeners(value = { EntityListener.class })
public class Product extends ModelObject implements Serializable, IDescribableEntity, ICategorizable<ProductCategory>, Cloneable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * 
     * <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "BLOCK1")
    private Integer block1 = Integer.valueOf(1);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "BLOCK2")
    private Integer block2 = Integer.valueOf(10);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "BLOCK3")
    private Integer block3 = Integer.valueOf(100);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "BLOCK4")
    private Integer block4 = Integer.valueOf(1000);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "BLOCK5")
    private Integer block5 = Integer.valueOf(10000);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_CATEGORY") })
    private ProductCategory categories = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * ProductOptions specify the product in terms of color or any other
     * attribute. <!-- end-model-doc -->
     * 
     * @generated
     */
    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_PRODUCT") })
    private List<ProductOptions> attributes = new ArrayList<>();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Picture for this product. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "PICTURE")
    @Lob()
    private byte[] picture = null;

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
     * the supplier's item number <!-- end-model-doc -->
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
    @Column(name = "PRICE1", precision = 8, scale = 3)
    private Double price1 = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PRICE2", precision = 8, scale = 3)
    private Double price2 = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PRICE3", precision = 8, scale = 3)
    private Double price3 = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PRICE4", precision = 8, scale = 3)
    private Double price4 = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PRICE5", precision = 8, scale = 3)
    private Double price5 = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "QUANTITY")
    private Double quantity = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "QUANTITYUNIT")
    private String quantityUnit = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SELLINGUNIT")
    private Integer sellingUnit = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_VAT") })
    private VAT vat = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "WEBSHOPID")
    private Long webshopId = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "WEIGHT")
    private Double weight = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The GTIN (Global Trade Item Number) is a worldwide valid article
     * identification number. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "GTIN")
    private Long gtin = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The price for which this product was bought. Always as net price. <!--
     * end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "COSTPRICE")
    private Double costPrice = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ALLOWANCE")
    private String allowance = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_PRODUCT") })
    private List<ProductBlockPrice> blockPrices = new ArrayList<>();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Customer defined field. This field is for free text information about the
     * product. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "CDF01")
    private String cdf01 = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "CDF02")
    private String cdf02 = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "CDF03")
    private String cdf03 = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "NOTE")
    private String note = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the stock quantity of this product is actively tracked.
     * <!-- end-model-doc -->
     *
     * @generated
     */
    @Basic()
    @Column(name = "STOCKMANAGED")
    private Boolean stockManaged = Boolean.TRUE;

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
    public boolean isSameAs(final Product other) {
        boolean retval = true;
        if (other != null) {
            if (block1 != null && other.getBlock1() != null) {
                retval &= block1.compareTo(other.getBlock1()) == 0;
            }
            if (block2 != null && other.getBlock2() != null) {
                retval &= block2.compareTo(other.getBlock2()) == 0;
            }
            if (block3 != null && other.getBlock3() != null) {
                retval &= block3.compareTo(other.getBlock3()) == 0;
            }
            if (block4 != null && other.getBlock4() != null) {
                retval &= block4.compareTo(other.getBlock4()) == 0;
            }
            if (block5 != null && other.getBlock5() != null) {
                retval &= block5.compareTo(other.getBlock5()) == 0;
            }
            if (getCategories() != null) {
                retval &= getCategories().isSameAs(other.getCategories());
            }
            /*
             * reference to a Set (attributes) or a volatile member cannot be
             * compared...
             */
            if (picture != null) {
                retval &= picture.equals(other.getPicture());
            }
            if (itemNumber != null && other.getItemNumber() != null) {
                retval &= itemNumber.compareTo(other.getItemNumber()) == 0;
            }
            if (supplierItemNumber != null && other.getSupplierItemNumber() != null) {
                retval &= supplierItemNumber.compareTo(other.getSupplierItemNumber()) == 0;
            }
            if (price1 != null && other.getPrice1() != null) {
                retval &= price1.compareTo(other.getPrice1()) == 0;
            }
            if (price2 != null && other.getPrice2() != null) {
                retval &= price2.compareTo(other.getPrice2()) == 0;
            }
            if (price3 != null && other.getPrice3() != null) {
                retval &= price3.compareTo(other.getPrice3()) == 0;
            }
            if (price4 != null && other.getPrice4() != null) {
                retval &= price4.compareTo(other.getPrice4()) == 0;
            }
            if (price5 != null && other.getPrice5() != null) {
                retval &= price5.compareTo(other.getPrice5()) == 0;
            }
            if (quantity != null && other.getQuantity() != null) {
                retval &= quantity.compareTo(other.getQuantity()) == 0;
            }
            if (quantityUnit != null && other.getQuantityUnit() != null) {
                retval &= quantityUnit.compareTo(other.getQuantityUnit()) == 0;
            }
            if (sellingUnit != null && other.getSellingUnit() != null) {
                retval &= sellingUnit.compareTo(other.getSellingUnit()) == 0;
            }
            if (getVat() != null) {
                retval &= getVat().isSameAs(other.getVat());
            }
            if (webshopId != null) {
                retval &= webshopId.equals(other.getWebshopId());
            }
            if (weight != null && other.getWeight() != null) {
                retval &= weight.compareTo(other.getWeight()) == 0;
            }
            if (gtin != null) {
                retval &= gtin.equals(other.getGtin());
            }
            if (costPrice != null && other.getCostPrice() != null) {
                retval &= costPrice.compareTo(other.getCostPrice()) == 0;
            }
            if (allowance != null && other.getAllowance() != null) {
                retval &= allowance.compareTo(other.getAllowance()) == 0;
            }
            /*
             * reference to a Set (blockPrices) or a volatile member cannot be
             * compared...
             */
            if (cdf01 != null && other.getCdf01() != null) {
                retval &= cdf01.compareTo(other.getCdf01()) == 0;
            }
            if (cdf02 != null && other.getCdf02() != null) {
                retval &= cdf02.compareTo(other.getCdf02()) == 0;
            }
            if (cdf03 != null && other.getCdf03() != null) {
                retval &= cdf03.compareTo(other.getCdf03()) == 0;
            }
            if (note != null && other.getNote() != null) {
                retval &= note.compareTo(other.getNote()) == 0;
            }
            if (stockManaged != null && other.getStockManaged() != null) {
                retval &= stockManaged.compareTo(other.getStockManaged()) == 0;
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
    public Product clone() {
        final Product retval = new Product();
        retval.setBlock1(this.getBlock1());
        retval.setBlock2(this.getBlock2());
        retval.setBlock3(this.getBlock3());
        retval.setBlock4(this.getBlock4());
        retval.setBlock5(this.getBlock5());
        retval.setCategories(this.getCategories());
        /* reference to a Set (attributes) cannot be compared... */
        retval.setPicture(this.getPicture());
        retval.setItemNumber(this.getItemNumber());
        retval.setSupplierItemNumber(this.getSupplierItemNumber());
        retval.setPrice1(this.getPrice1());
        retval.setPrice2(this.getPrice2());
        retval.setPrice3(this.getPrice3());
        retval.setPrice4(this.getPrice4());
        retval.setPrice5(this.getPrice5());
        retval.setQuantity(this.getQuantity());
        retval.setQuantityUnit(this.getQuantityUnit());
        retval.setSellingUnit(this.getSellingUnit());
        retval.setVat(this.getVat());
        retval.setWebshopId(this.getWebshopId());
        retval.setWeight(this.getWeight());
        retval.setGtin(this.getGtin());
        retval.setCostPrice(this.getCostPrice());
        retval.setAllowance(this.getAllowance());
        /* reference to a Set (blockPrices) cannot be compared... */
        retval.setCdf01(this.getCdf01());
        retval.setCdf02(this.getCdf02());
        retval.setCdf03(this.getCdf03());
        retval.setNote(this.getNote());
        retval.setStockManaged(this.getStockManaged());
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
     * Returns the value of '<em><b>block1</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * 
     * <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>block1</b></em>' feature
     * @generated
     */
    public Integer getBlock1() {

        return block1;
    }

    /**
     * Sets the '{@link Product#getBlock1() <em>block1</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * 
     * <!-- end-model-doc -->
     * 
     * @param newBlock1
     *            the new value of the '{@link Product#getBlock1() block1}'
     *            feature.
     * @generated
     */
    public void setBlock1(final Integer newBlock1) {
        firePropertyChange("block1", this.block1, newBlock1);
        block1 = newBlock1;
    }

    /**
     * Returns the value of '<em><b>block2</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>block2</b></em>' feature
     * @generated
     */
    public Integer getBlock2() {

        return block2;
    }

    /**
     * Sets the '{@link Product#getBlock2() <em>block2</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newBlock2
     *            the new value of the '{@link Product#getBlock2() block2}'
     *            feature.
     * @generated
     */
    public void setBlock2(final Integer newBlock2) {
        firePropertyChange("block2", this.block2, newBlock2);
        block2 = newBlock2;
    }

    /**
     * Returns the value of '<em><b>block3</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>block3</b></em>' feature
     * @generated
     */
    public Integer getBlock3() {

        return block3;
    }

    /**
     * Sets the '{@link Product#getBlock3() <em>block3</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newBlock3
     *            the new value of the '{@link Product#getBlock3() block3}'
     *            feature.
     * @generated
     */
    public void setBlock3(final Integer newBlock3) {
        firePropertyChange("block3", this.block3, newBlock3);
        block3 = newBlock3;
    }

    /**
     * Returns the value of '<em><b>block4</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>block4</b></em>' feature
     * @generated
     */
    public Integer getBlock4() {

        return block4;
    }

    /**
     * Sets the '{@link Product#getBlock4() <em>block4</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newBlock4
     *            the new value of the '{@link Product#getBlock4() block4}'
     *            feature.
     * @generated
     */
    public void setBlock4(final Integer newBlock4) {
        firePropertyChange("block4", this.block4, newBlock4);
        block4 = newBlock4;
    }

    /**
     * Returns the value of '<em><b>block5</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>block5</b></em>' feature
     * @generated
     */
    public Integer getBlock5() {

        return block5;
    }

    /**
     * Sets the '{@link Product#getBlock5() <em>block5</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newBlock5
     *            the new value of the '{@link Product#getBlock5() block5}'
     *            feature.
     * @generated
     */
    public void setBlock5(final Integer newBlock5) {
        firePropertyChange("block5", this.block5, newBlock5);
        block5 = newBlock5;
    }

    /**
     * Returns the value of '<em><b>categories</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>categories</b></em>' feature
     * @generated
     */
    @Override
    public ProductCategory getCategories() {

        return categories;
    }

    /**
     * Sets the '{@link Product#getCategories() <em>categories</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCategories
     *            the new value of the '{@link Product#getCategories()
     *            categories}' feature.
     * @generated
     */
    public void setCategories(final ProductCategory newCategories) {
        firePropertyChange("categories", this.categories, newCategories);
        categories = newCategories;
    }

    /**
     * Returns the value of '<em><b>attributes</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * ProductOptions specify the product in terms of color or any other
     * attribute. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>attributes</b></em>' feature
     * @generated
     */
    public List<ProductOptions> getAttributes() {

        return attributes;
    }

    /**
     * Sets the '{@link Product#getAttributes() <em>attributes</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * ProductOptions specify the product in terms of color or any other
     * attribute. <!-- end-model-doc -->
     * 
     * @param newAttributes
     *            the new value of the '{@link Product#getAttributes()
     *            attributes}' feature.
     * @generated
     */
    public void setAttributes(final List<ProductOptions> newAttributes) {
        firePropertyChange("attributes", this.attributes, newAttributes);
        attributes = newAttributes;
    }

    /**
     * Returns the value of '<em><b>picture</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Picture for this product. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>picture</b></em>' feature
     * @generated
     */
    public byte[] getPicture() {

        return picture;
    }

    /**
     * Sets the '{@link Product#getPicture() <em>picture</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Picture for this product. <!-- end-model-doc -->
     * 
     * @param newPicture
     *            the new value of the '{@link Product#getPicture() picture}'
     *            feature.
     * @generated
     */
    public void setPicture(final byte[] newPicture) {
        firePropertyChange("picture", this.picture, newPicture);
        picture = newPicture;
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
     * Sets the '{@link Product#getItemNumber() <em>itemNumber</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newItemNumber
     *            the new value of the '{@link Product#getItemNumber()
     *            itemNumber}' feature.
     * @generated
     */
    public void setItemNumber(final String newItemNumber) {
        firePropertyChange("itemNumber", this.itemNumber, newItemNumber);
        itemNumber = newItemNumber;
    }

    /**
     * Returns the value of '<em><b>supplierItemNumber</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the supplier's item number <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>supplierItemNumber</b></em>' feature
     * @generated
     */
    public String getSupplierItemNumber() {

        return supplierItemNumber;
    }

    /**
     * Sets the '{@link Product#getSupplierItemNumber()
     * <em>supplierItemNumber</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * the supplier's item number <!-- end-model-doc -->
     * 
     * @param newSupplierItemNumber
     *            the new value of the '{@link Product#getSupplierItemNumber()
     *            supplierItemNumber}' feature.
     * @generated
     */
    public void setSupplierItemNumber(final String newSupplierItemNumber) {
        firePropertyChange("supplierItemNumber", this.supplierItemNumber, newSupplierItemNumber);
        supplierItemNumber = newSupplierItemNumber;
    }

    /**
     * Returns the value of '<em><b>price1</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>price1</b></em>' feature
     * @generated
     */
    public Double getPrice1() {

        return price1;
    }

    /**
     * Sets the '{@link Product#getPrice1() <em>price1</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPrice1
     *            the new value of the '{@link Product#getPrice1() price1}'
     *            feature.
     * @generated
     */
    public void setPrice1(final Double newPrice1) {
        firePropertyChange("price1", this.price1, newPrice1);
        price1 = newPrice1;
    }

    /**
     * Returns the value of '<em><b>price2</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>price2</b></em>' feature
     * @generated
     */
    public Double getPrice2() {

        return price2;
    }

    /**
     * Sets the '{@link Product#getPrice2() <em>price2</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPrice2
     *            the new value of the '{@link Product#getPrice2() price2}'
     *            feature.
     * @generated
     */
    public void setPrice2(final Double newPrice2) {
        firePropertyChange("price2", this.price2, newPrice2);
        price2 = newPrice2;
    }

    /**
     * Returns the value of '<em><b>price3</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>price3</b></em>' feature
     * @generated
     */
    public Double getPrice3() {

        return price3;
    }

    /**
     * Sets the '{@link Product#getPrice3() <em>price3</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPrice3
     *            the new value of the '{@link Product#getPrice3() price3}'
     *            feature.
     * @generated
     */
    public void setPrice3(final Double newPrice3) {
        firePropertyChange("price3", this.price3, newPrice3);
        price3 = newPrice3;
    }

    /**
     * Returns the value of '<em><b>price4</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>price4</b></em>' feature
     * @generated
     */
    public Double getPrice4() {

        return price4;
    }

    /**
     * Sets the '{@link Product#getPrice4() <em>price4</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPrice4
     *            the new value of the '{@link Product#getPrice4() price4}'
     *            feature.
     * @generated
     */
    public void setPrice4(final Double newPrice4) {
        firePropertyChange("price4", this.price4, newPrice4);
        price4 = newPrice4;
    }

    /**
     * Returns the value of '<em><b>price5</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>price5</b></em>' feature
     * @generated
     */
    public Double getPrice5() {

        return price5;
    }

    /**
     * Sets the '{@link Product#getPrice5() <em>price5</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPrice5
     *            the new value of the '{@link Product#getPrice5() price5}'
     *            feature.
     * @generated
     */
    public void setPrice5(final Double newPrice5) {
        firePropertyChange("price5", this.price5, newPrice5);
        price5 = newPrice5;
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
     * Sets the '{@link Product#getQuantity() <em>quantity</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newQuantity
     *            the new value of the '{@link Product#getQuantity() quantity}'
     *            feature.
     * @generated
     */
    public void setQuantity(final Double newQuantity) {
        firePropertyChange("quantity", this.quantity, newQuantity);
        quantity = newQuantity;
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
     * Sets the '{@link Product#getQuantityUnit() <em>quantityUnit</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newQuantityUnit
     *            the new value of the '{@link Product#getQuantityUnit()
     *            quantityUnit}' feature.
     * @generated
     */
    public void setQuantityUnit(final String newQuantityUnit) {
        firePropertyChange("quantityUnit", this.quantityUnit, newQuantityUnit);
        quantityUnit = newQuantityUnit;
    }

    /**
     * Returns the value of '<em><b>sellingUnit</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>sellingUnit</b></em>' feature
     * @generated
     */
    public Integer getSellingUnit() {

        return sellingUnit;
    }

    /**
     * Sets the '{@link Product#getSellingUnit() <em>sellingUnit</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newSellingUnit
     *            the new value of the '{@link Product#getSellingUnit()
     *            sellingUnit}' feature.
     * @generated
     */
    public void setSellingUnit(final Integer newSellingUnit) {
        firePropertyChange("sellingUnit", this.sellingUnit, newSellingUnit);
        sellingUnit = newSellingUnit;
    }

    /**
     * Returns the value of '<em><b>vat</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>vat</b></em>' feature
     * @generated
     */
    public VAT getVat() {

        return vat;
    }

    /**
     * Sets the '{@link Product#getVat() <em>vat</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newVat
     *            the new value of the '{@link Product#getVat() vat}' feature.
     * @generated
     */
    public void setVat(final VAT newVat) {
        firePropertyChange("vat", this.vat, newVat);
        vat = newVat;
    }

    /**
     * Returns the value of '<em><b>webshopId</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>webshopId</b></em>' feature
     * @generated
     */
    public Long getWebshopId() {

        return webshopId;
    }

    /**
     * Sets the '{@link Product#getWebshopId() <em>webshopId</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newWebshopId
     *            the new value of the '{@link Product#getWebshopId()
     *            webshopId}' feature.
     * @generated
     */
    public void setWebshopId(final Long newWebshopId) {
        firePropertyChange("webshopId", this.webshopId, newWebshopId);
        webshopId = newWebshopId;
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
     * Sets the '{@link Product#getWeight() <em>weight</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newWeight
     *            the new value of the '{@link Product#getWeight() weight}'
     *            feature.
     * @generated
     */
    public void setWeight(final Double newWeight) {
        firePropertyChange("weight", this.weight, newWeight);
        weight = newWeight;
    }

    /**
     * Returns the value of '<em><b>gtin</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The GTIN (Global Trade Item Number) is a worldwide valid article
     * identification number. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>gtin</b></em>' feature
     * @generated
     */
    public Long getGtin() {

        return gtin;
    }

    /**
     * Sets the '{@link Product#getGtin() <em>gtin</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The GTIN (Global Trade Item Number) is a worldwide valid article
     * identification number. <!-- end-model-doc -->
     * 
     * @param newGtin
     *            the new value of the '{@link Product#getGtin() gtin}' feature.
     * @generated
     */
    public void setGtin(final Long newGtin) {
        firePropertyChange("gtin", this.gtin, newGtin);
        gtin = newGtin;
    }

    /**
     * Returns the value of '<em><b>costPrice</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The price for which this product was bought. Always as net price. <!--
     * end-model-doc -->
     * 
     * @return the value of '<em><b>costPrice</b></em>' feature
     * @generated
     */
    public Double getCostPrice() {

        return costPrice;
    }

    /**
     * Sets the '{@link Product#getCostPrice() <em>costPrice</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The price for which this product was bought. Always as net price. <!--
     * end-model-doc -->
     * 
     * @param newCostPrice
     *            the new value of the '{@link Product#getCostPrice()
     *            costPrice}' feature.
     * @generated
     */
    public void setCostPrice(final Double newCostPrice) {
        firePropertyChange("costPrice", this.costPrice, newCostPrice);
        costPrice = newCostPrice;
    }

    /**
     * Returns the value of '<em><b>allowance</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>allowance</b></em>' feature
     * @generated
     */
    public String getAllowance() {

        return allowance;
    }

    /**
     * Sets the '{@link Product#getAllowance() <em>allowance</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newAllowance
     *            the new value of the '{@link Product#getAllowance()
     *            allowance}' feature.
     * @generated
     */
    public void setAllowance(final String newAllowance) {
        firePropertyChange("allowance", this.allowance, newAllowance);
        allowance = newAllowance;
    }

    /**
     * Returns the value of '<em><b>blockPrices</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>blockPrices</b></em>' feature
     * @generated
     */
    public List<ProductBlockPrice> getBlockPrices() {

        return blockPrices;
    }

    /**
     * Sets the '{@link Product#getBlockPrices() <em>blockPrices</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newBlockPrices
     *            the new value of the '{@link Product#getBlockPrices()
     *            blockPrices}' feature.
     * @generated
     */
    public void setBlockPrices(final List<ProductBlockPrice> newBlockPrices) {
        firePropertyChange("blockPrices", this.blockPrices, newBlockPrices);
        blockPrices = newBlockPrices;
    }

    /**
     * Returns the value of '<em><b>cdf01</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Customer defined field. This field is for free text information about the
     * product. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>cdf01</b></em>' feature
     * @generated
     */
    public String getCdf01() {

        return cdf01;
    }

    /**
     * Sets the '{@link Product#getCdf01() <em>cdf01</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Customer defined field. This field is for free text information about the
     * product. <!-- end-model-doc -->
     * 
     * @param newCdf01
     *            the new value of the '{@link Product#getCdf01() cdf01}'
     *            feature.
     * @generated
     */
    public void setCdf01(final String newCdf01) {
        firePropertyChange("cdf01", this.cdf01, newCdf01);
        cdf01 = newCdf01;
    }

    /**
     * Returns the value of '<em><b>cdf02</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>cdf02</b></em>' feature
     * @generated
     */
    public String getCdf02() {

        return cdf02;
    }

    /**
     * Sets the '{@link Product#getCdf02() <em>cdf02</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCdf02
     *            the new value of the '{@link Product#getCdf02() cdf02}'
     *            feature.
     * @generated
     */
    public void setCdf02(final String newCdf02) {
        firePropertyChange("cdf02", this.cdf02, newCdf02);
        cdf02 = newCdf02;
    }

    /**
     * Returns the value of '<em><b>cdf03</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>cdf03</b></em>' feature
     * @generated
     */
    public String getCdf03() {

        return cdf03;
    }

    /**
     * Sets the '{@link Product#getCdf03() <em>cdf03</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newCdf03
     *            the new value of the '{@link Product#getCdf03() cdf03}'
     *            feature.
     * @generated
     */
    public void setCdf03(final String newCdf03) {
        firePropertyChange("cdf03", this.cdf03, newCdf03);
        cdf03 = newCdf03;
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
     * Sets the '{@link Product#getNote() <em>note</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newNote
     *            the new value of the '{@link Product#getNote() note}' feature.
     * @generated
     */
    public void setNote(final String newNote) {
        firePropertyChange("note", this.note, newNote);
        note = newNote;
    }

    /**
     * Returns the value of '<em><b>stockManaged</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the stock quantity of this product is actively tracked.
     * <!-- end-model-doc -->
     *
     * @return the value of '<em><b>stockManaged</b></em>' feature
     * @generated
     */
    public Boolean getStockManaged() {

        return stockManaged;
    }

    /**
     * Sets the '{@link Product#getStockManaged() <em>stockManaged</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the stock quantity of this product is actively tracked.
     * <!-- end-model-doc -->
     *
     * @param newStockManaged
     *            the new value of the '{@link Product#getStockManaged() stockManaged}' feature.
     * @generated
     */
    public void setStockManaged(final Boolean newStockManaged) {
        firePropertyChange("stockManaged", this.stockManaged, newStockManaged);
        stockManaged = newStockManaged;
    }

    /**
     * Returns the value of '<em><b>description</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>description</b></em>' feature
     * @generated
     */
    @Override
    public String getDescription() {

        return description;
    }

    /**
     * Sets the '{@link Product#getDescription() <em>description</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDescription
     *            the new value of the '{@link Product#getDescription()
     *            description}' feature.
     * @generated
     */
    @Override
    public void setDescription(final String newDescription) {
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
    @Override
    public String getName() {

        return name;
    }

    /**
     * Sets the '{@link Product#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link Product#getName() name}' feature.
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
     * Sets the '{@link Product#getDateAdded() <em>dateAdded</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link Product#getDateAdded()
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
     * Sets the '{@link Product#getModifiedBy() <em>modifiedBy</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link Product#getModifiedBy()
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
     * Sets the '{@link Product#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link Product#getModified() modified}'
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
     * Sets the '{@link Product#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link Product#getId() id}' feature.
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
     * Sets the '{@link Product#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link Product#getDeleted() deleted}'
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
     * Sets the '{@link Product#getValidFrom() <em>validFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link Product#getValidFrom()
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
     * Sets the '{@link Product#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link Product#getValidTo() validTo}'
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
        return "Product" + " block1: [" + getBlock1() + "]" + " block2: [" + getBlock2() + "]" + " block3: [" + getBlock3() + "]" + " block4: [" + getBlock4()
                + "]" + " block5: [" + getBlock5() + "]" + " picture: [" + getPicture() + "]" + " itemNumber: [" + getItemNumber() + "]"
                + " supplierItemNumber: [" + getSupplierItemNumber() + "]" + " price1: [" + getPrice1() + "]" + " price2: [" + getPrice2() + "]" + " price3: ["
                + getPrice3() + "]" + " price4: [" + getPrice4() + "]" + " price5: [" + getPrice5() + "]" + " quantity: [" + getQuantity() + "]"
                + " quantityUnit: [" + getQuantityUnit() + "]" + " sellingUnit: [" + getSellingUnit() + "]" + " webshopId: [" + getWebshopId() + "]"
                + " weight: [" + getWeight() + "]" + " gtin: [" + getGtin() + "]" + " costPrice: [" + getCostPrice() + "]" + " allowance: [" + getAllowance()
                + "]" + " cdf01: [" + getCdf01() + "]" + " cdf02: [" + getCdf02() + "]" + " cdf03: [" + getCdf03() + "]" + " note: [" + getNote() + "]"
                + " stockManaged: [" + getStockManaged() + "]"
                + " description: [" + getDescription() + "]" + " name: [" + getName() + "]" + " dateAdded: [" + getDateAdded() + "]" + " modifiedBy: ["
                + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]" + " deleted: [" + getDeleted() + "]" + " validFrom: ["
                + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
