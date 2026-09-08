package com.sebulli.fakturama.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * A representation of the model object '<em><b>Document</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Abstract
 * Superclass for all types of documents (invoices, orders etc.) <!--
 * end-model-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_DOCUMENT")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(value = { EntityListener.class })
public abstract class Document extends ModelObject implements IEntity, Serializable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This field references a block of additional infos for this document
     * entity. It is used especially for storing manually edited values or
     * values that has to be unchangeable (e.g., within invoices for audit
     * proofing). <!-- end-model-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "FK_INDIVIDUALINFO") })
    private IndividualDocumentInfo additionalInfo = new IndividualDocumentInfo();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Convenience attribut which contains the first line of the address from
     * the document receiver (according to billing type). <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ADDRESSFIRSTLINE")
    private String addressFirstLine = null;

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
     * The customer number or other reference. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "CUSTOMERREF")
    private String customerRef = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicator that shows if the paid value is only a deposit. If so, this
     * flag is set to <code>true</code>. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DEPOSIT")
    private Boolean deposit = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DOCUMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date documentDate = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DUEDAYS")
    private Integer dueDays = Integer.valueOf(0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This is a reference to the invoice for this transaction. <!--
     * end-model-doc -->
     * 
     * @generated
     */
    // FetchType.LAZY only takes effect with weaving enabled (see model/pom.xml's static-weave
    // execution) - without it EclipseLink always resolves this eagerly regardless of this
    // annotation. Without LAZY, every findPage()/findById() row for a Document with a non-null
    // invoiceReference fires its own extra single-row SELECT while the row is being built, since
    // this self-referencing relation is deliberately not fetch-joined (see
    // DocumentsDAO#fetchDocumentRelations' Javadoc) - confirmed via thread dump: 138 such reads for
    // one findPage() page in the demo dataset.
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "DOCUMENT_INVOICEREFERENCE") })
    private Invoice invoiceReference = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The items of this document. <!-- end-model-doc -->
     * 
     * @generated
     */
    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_DOCUMENT") })
    private List<DocumentItem> items = new ArrayList<>();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This attribute was formerly known as "itemsdiscount" but was changed
     * because of ambiguity to cash discount. <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ITEMSREBATE")
    private Double itemsRebate = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "MESSAGE")
    @Lob()
    private String message = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "MESSAGE2")
    @Lob()
    private String message2 = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "MESSAGE3")
    @Lob()
    private String message3 = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Information about rounding of document prices. The following values are
     * valid:
     * <ul>
     * <li>0 &hellip; no rounding</li>
     * <li>1 &hellip; The prices are rounded, that the net values are full cent
     * values.</li>
     * <li>2 &hellip; The prices are rounded, that the gross values are full
     * cent values.</li>
     * </ul>
     * <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "NETGROSS")
    private Integer netGross = Integer.valueOf(0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> If
     * a document doesn't hava a VAT use thisreference for a description. <!--
     * end-model-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_NOVATREF") })
    private VAT noVatReference = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ODTPATH")
    private String odtPath = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "ORDERDATE")
    @Temporal(TemporalType.DATE)
    private Date orderDate = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The amount of what is paid (relevant for partial payment). <!--
     * end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PAIDVALUE")
    private Double paidValue = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PAID")
    private Boolean paid = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the invoice (or dunning or whatever) is completely paid.
     * <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PAYDATE")
    @Temporal(TemporalType.DATE)
    private Date payDate = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * reference to the payment entry (if not changed manually). <!--
     * end-model-doc -->
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
    @Column(name = "PDFPATH")
    private String pdfPath = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PRINTED")
    private Boolean printed = false;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PRINTTEMPLATE")
    private String printTemplate = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The status of the transaction (is an order shipped, paid or in any other
     * state). <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "PROGRESS")
    private Integer progress = Integer.valueOf(0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The receivers of this document. <!-- end-model-doc -->
     * 
     * @generated
     */
    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_DOCUMENT") })
    private List<DocumentReceiver> receiver = new ArrayList<>();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SERVICEDATE")
    @Temporal(TemporalType.DATE)
    private Date serviceDate = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * reference to the shipping entry (if not changed manually). <!--
     * end-model-doc -->
     * 
     * @generated
     */
    @ManyToOne(cascade = { CascadeType.REFRESH })
    @JoinColumns({ @JoinColumn(name = "FK_SHIPPING") })
    private Shipping shipping = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SHIPPINGAUTOVAT")
    @Enumerated(EnumType.STRING)
    private ShippingVatType shippingAutoVat = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "SHIPPINGVALUE")
    private Double shippingValue = Double.valueOf(0.0);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This is the reference to the document from which this document was
     * created. <!-- end-model-doc -->
     * 
     * @generated
     */
    // See invoiceReference's comment above - same reasoning, same fix.
    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "FK_SRCDOCUMENT") })
    private Document sourceDocument = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "TARA")
    private Double tara = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "TOTALVALUE", precision = 12, scale = 3)
    private Double totalValue = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This id is for grouping of multiple documents that belongs to the same
     * transaction. It is a unique ID which determines the transaction. <!--
     * end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "TRANSACTIONID")
    private Integer transactionId = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "WEBSHOPDATE")
    @Temporal(TemporalType.DATE)
    private Date webshopDate = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "WEBSHOPID")
    private String webshopId = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VESTINGPERIODSTART")
    @Temporal(TemporalType.DATE)
    private Date vestingPeriodStart = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VESTINGPERIODEND")
    @Temporal(TemporalType.DATE)
    private Date vestingPeriodEnd = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * version attribute <!-- end-model-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "VERSION")
    private Integer version = null;

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
    public boolean isSameAs(final Document other) {
        boolean retval = true;
        if (other != null) {
            if (getAdditionalInfo() != null) {
                retval &= getAdditionalInfo().isSameAs(other.getAdditionalInfo());
            }
            if (addressFirstLine != null && other.getAddressFirstLine() != null) {
                retval &= addressFirstLine.compareTo(other.getAddressFirstLine()) == 0;
            }
            if (billingType != null) {
                retval &= billingType.equals(other.getBillingType());
            }
            if (customerRef != null && other.getCustomerRef() != null) {
                retval &= customerRef.compareTo(other.getCustomerRef()) == 0;
            }
            if (deposit != null && other.getDeposit() != null) {
                retval &= deposit.compareTo(other.getDeposit()) == 0;
            }
            if (documentDate != null && other.getDocumentDate() != null) {
                retval &= documentDate.compareTo(other.getDocumentDate()) == 0;
            }
            if (dueDays != null && other.getDueDays() != null) {
                retval &= dueDays.compareTo(other.getDueDays()) == 0;
            }
            if (getInvoiceReference() != null) {
                retval &= getInvoiceReference().isSameAs(other.getInvoiceReference());
            }
            /* reference to a Set (items) or a volatile member cannot be compared... */
            if (itemsRebate != null && other.getItemsRebate() != null) {
                retval &= itemsRebate.compareTo(other.getItemsRebate()) == 0;
            }
            if (message != null && other.getMessage() != null) {
                retval &= message.compareTo(other.getMessage()) == 0;
            }
            if (message2 != null && other.getMessage2() != null) {
                retval &= message2.compareTo(other.getMessage2()) == 0;
            }
            if (message3 != null && other.getMessage3() != null) {
                retval &= message3.compareTo(other.getMessage3()) == 0;
            }
            if (netGross != null && other.getNetGross() != null) {
                retval &= netGross.compareTo(other.getNetGross()) == 0;
            }
            if (getNoVatReference() != null) {
                retval &= getNoVatReference().isSameAs(other.getNoVatReference());
            }
            if (odtPath != null && other.getOdtPath() != null) {
                retval &= odtPath.compareTo(other.getOdtPath()) == 0;
            }
            if (orderDate != null && other.getOrderDate() != null) {
                retval &= orderDate.compareTo(other.getOrderDate()) == 0;
            }
            if (paidValue != null && other.getPaidValue() != null) {
                retval &= paidValue.compareTo(other.getPaidValue()) == 0;
            }
            if (paid != null && other.getPaid() != null) {
                retval &= paid.compareTo(other.getPaid()) == 0;
            }
            if (payDate != null && other.getPayDate() != null) {
                retval &= payDate.compareTo(other.getPayDate()) == 0;
            }
            if (getPayment() != null) {
                retval &= getPayment().isSameAs(other.getPayment());
            }
            if (pdfPath != null && other.getPdfPath() != null) {
                retval &= pdfPath.compareTo(other.getPdfPath()) == 0;
            }

            retval &= printed.compareTo(other.getPrinted()) == 0;

            if (printTemplate != null && other.getPrintTemplate() != null) {
                retval &= printTemplate.compareTo(other.getPrintTemplate()) == 0;
            }
            if (progress != null && other.getProgress() != null) {
                retval &= progress.compareTo(other.getProgress()) == 0;
            }
            /* reference to a Set (receiver) or a volatile member cannot be compared... */
            if (serviceDate != null && other.getServiceDate() != null) {
                retval &= serviceDate.compareTo(other.getServiceDate()) == 0;
            }
            if (getShipping() != null) {
                retval &= getShipping().isSameAs(other.getShipping());
            }
            if (shippingAutoVat != null) {
                retval &= shippingAutoVat.equals(other.getShippingAutoVat());
            }
            if (shippingValue != null && other.getShippingValue() != null) {
                retval &= shippingValue.compareTo(other.getShippingValue()) == 0;
            }
            if (getSourceDocument() != null) {
                retval &= getSourceDocument().isSameAs(other.getSourceDocument());
            }
            if (tara != null && other.getTara() != null) {
                retval &= tara.compareTo(other.getTara()) == 0;
            }
            if (totalValue != null && other.getTotalValue() != null) {
                retval &= totalValue.compareTo(other.getTotalValue()) == 0;
            }
            if (transactionId != null && other.getTransactionId() != null) {
                retval &= transactionId.compareTo(other.getTransactionId()) == 0;
            }
            if (webshopDate != null && other.getWebshopDate() != null) {
                retval &= webshopDate.compareTo(other.getWebshopDate()) == 0;
            }
            if (webshopId != null && other.getWebshopId() != null) {
                retval &= webshopId.compareTo(other.getWebshopId()) == 0;
            }
            if (vestingPeriodStart != null && other.getVestingPeriodStart() != null) {
                retval &= vestingPeriodStart.compareTo(other.getVestingPeriodStart()) == 0;
            }
            if (vestingPeriodEnd != null && other.getVestingPeriodEnd() != null) {
                retval &= vestingPeriodEnd.compareTo(other.getVestingPeriodEnd()) == 0;
            }
            if (version != null && other.getVersion() != null) {
                retval &= version.compareTo(other.getVersion()) == 0;
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
     * Returns the value of '<em><b>additionalInfo</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This field references a block of additional infos for this document
     * entity. It is used especially for storing manually edited values or
     * values that has to be unchangeable (e.g., within invoices for audit
     * proofing). <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>additionalInfo</b></em>' feature
     */
    public IndividualDocumentInfo getAdditionalInfo() {
        // additionalInfo's field initializer (new IndividualDocumentInfo()) only applies to a
        // freshly-constructed, not-yet-persisted Document - for a Document loaded from the DB by
        // JPA, EclipseLink overwrites the field straight from FK_INDIVIDUALINFO, which turns it
        // back into null whenever that column is NULL or points at a row that no longer exists
        // (e.g. an externally/manually inserted FKT_DOCUMENT row, or one hand-edited on the DB).
        // DocumentEditor calls document.getAdditionalInfo().setXxx(...) in several places without
        // a null check, so a null here crashed the whole editor on open ("Unable to create class
        // 'DocumentEditor'", live reproduced with an order imported by an external tool whose
        // FKT_DOCUMENT row had no matching FKT_INDIVIDUALDOCUMENTINFO companion row) - lazily
        // healing it here, once, for every caller, is more robust than guarding each call site
        // individually (a class this central will keep growing new callers). additionalInfo is
        // mapped with cascade = CascadeType.ALL, so saving the document afterwards persists this
        // fresh instance and backfills FK_INDIVIDUALINFO on its own, no manual SQL needed.
        if (additionalInfo == null) {
            additionalInfo = new IndividualDocumentInfo();
        }
        return additionalInfo;
    }

    /**
     * Sets the '{@link Document#getAdditionalInfo() <em>additionalInfo</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This field references a block of additional infos for this document
     * entity. It is used especially for storing manually edited values or
     * values that has to be unchangeable (e.g., within invoices for audit
     * proofing). <!-- end-model-doc -->
     * 
     * @param newAdditionalInfo
     *            the new value of the '{@link Document#getAdditionalInfo()
     *            additionalInfo}' feature.
     * @generated
     */
    public void setAdditionalInfo(final IndividualDocumentInfo newAdditionalInfo) {
        firePropertyChange("additionalInfo", this.additionalInfo, newAdditionalInfo);
        additionalInfo = newAdditionalInfo;
    }

    /**
     * Returns the value of '<em><b>addressFirstLine</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Convenience attribut which contains the first line of the address from
     * the document receiver (according to billing type). <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>addressFirstLine</b></em>' feature
     * @generated
     */
    public String getAddressFirstLine() {

        return addressFirstLine;
    }

    /**
     * Sets the '{@link Document#getAddressFirstLine()
     * <em>addressFirstLine</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Convenience attribut which contains the first line of the address from
     * the document receiver (according to billing type). <!-- end-model-doc -->
     * 
     * @param newAddressFirstLine
     *            the new value of the '{@link Document#getAddressFirstLine()
     *            addressFirstLine}' feature.
     * @generated
     */
    public void setAddressFirstLine(final String newAddressFirstLine) {
        firePropertyChange("addressFirstLine", this.addressFirstLine, newAddressFirstLine);
        addressFirstLine = newAddressFirstLine;
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
     * Sets the '{@link Document#getBillingType() <em>billingType</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The billing type of this document (if it is a letter, a dunning, an
     * invoice etc). <!-- end-model-doc -->
     * 
     * @param newBillingType
     *            the new value of the '{@link Document#getBillingType()
     *            billingType}' feature.
     * @generated
     */
    public void setBillingType(final BillingType newBillingType) {
        firePropertyChange("billingType", this.billingType, newBillingType);
        billingType = newBillingType;
    }

    /**
     * Returns the value of '<em><b>customerRef</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The customer number or other reference. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>customerRef</b></em>' feature
     * @generated
     */
    public String getCustomerRef() {

        return customerRef;
    }

    /**
     * Sets the '{@link Document#getCustomerRef() <em>customerRef</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The customer number or other reference. <!-- end-model-doc -->
     * 
     * @param newCustomerRef
     *            the new value of the '{@link Document#getCustomerRef()
     *            customerRef}' feature.
     * @generated
     */
    public void setCustomerRef(final String newCustomerRef) {
        firePropertyChange("customerRef", this.customerRef, newCustomerRef);
        customerRef = newCustomerRef;
    }

    /**
     * Returns the value of '<em><b>deposit</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicator that shows if the paid value is only a deposit. If so, this
     * flag is set to <code>true</code>. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>deposit</b></em>' feature
     * @generated
     */
    public Boolean getDeposit() {

        return deposit;
    }

    /**
     * Sets the '{@link Document#getDeposit() <em>deposit</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicator that shows if the paid value is only a deposit. If so, this
     * flag is set to <code>true</code>. <!-- end-model-doc -->
     * 
     * @param newDeposit
     *            the new value of the '{@link Document#getDeposit() deposit}'
     *            feature.
     * @generated
     */
    public void setDeposit(final Boolean newDeposit) {
        firePropertyChange("deposit", this.deposit, newDeposit);
        deposit = newDeposit;
    }

    /**
     * Returns the value of '<em><b>documentDate</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>documentDate</b></em>' feature
     * @generated
     */
    public Date getDocumentDate() {

        return documentDate;
    }

    /**
     * Sets the '{@link Document#getDocumentDate() <em>documentDate</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDocumentDate
     *            the new value of the '{@link Document#getDocumentDate()
     *            documentDate}' feature.
     * @generated
     */
    public void setDocumentDate(final Date newDocumentDate) {
        firePropertyChange("documentDate", this.documentDate, newDocumentDate);
        documentDate = newDocumentDate;
    }

    /**
     * Returns the value of '<em><b>dueDays</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>dueDays</b></em>' feature
     * @generated
     */
    public Integer getDueDays() {

        return dueDays;
    }

    /**
     * Sets the '{@link Document#getDueDays() <em>dueDays</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDueDays
     *            the new value of the '{@link Document#getDueDays() dueDays}'
     *            feature.
     * @generated
     */
    public void setDueDays(final Integer newDueDays) {
        firePropertyChange("dueDays", this.dueDays, newDueDays);
        dueDays = newDueDays;
    }

    /**
     * Returns the value of '<em><b>invoiceReference</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This is a reference to the invoice for this transaction. <!--
     * end-model-doc -->
     * 
     * @return the value of '<em><b>invoiceReference</b></em>' feature
     * @generated
     */
    public Invoice getInvoiceReference() {

        return invoiceReference;
    }

    /**
     * Sets the '{@link Document#getInvoiceReference()
     * <em>invoiceReference</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This is a reference to the invoice for this transaction. <!--
     * end-model-doc -->
     * 
     * @param newInvoiceReference
     *            the new value of the '{@link Document#getInvoiceReference()
     *            invoiceReference}' feature.
     * @generated
     */
    public void setInvoiceReference(final Invoice newInvoiceReference) {
        firePropertyChange("invoiceReference", this.invoiceReference, newInvoiceReference);
        invoiceReference = newInvoiceReference;
    }

    /**
     * Returns the value of '<em><b>items</b></em>' feature. Note: the returned
     * collection is Unmodifiable use the
     * {#addToItems(com.sebulli.fakturama.model.DocumentItem value)} and
     * {@link #removeFromItems(DocumentItem value)} methods to modify this
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The items of this document. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>items</b></em>' feature
     * @generated
     */
    public List<DocumentItem> getItems() {

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
    public boolean addToItems(final DocumentItem itemsValue) {
        items.add(itemsValue);
        return true;
    }

    /**
     * Removes from the <em>items</em> feature.
     *
     * @param itemsValue
     *            value to remove
     *
     * @generated
     */
    public boolean removeFromItems(final DocumentItem itemsValue) {
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
     * Sets the '{@link Document#getItems() <em>items</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The items of this document. <!-- end-model-doc -->
     * 
     * @param newItems
     *            the new value of the '{@link Document#getItems() items}'
     *            feature.
     * @generated
     */
    public void setItems(final List<DocumentItem> newItems) {
        firePropertyChange("items", this.items, newItems);
        clearItems();
        for (DocumentItem value : newItems) {
            addToItems(value);
        }
    }

    /**
     * Returns the value of '<em><b>itemsRebate</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This attribute was formerly known as "itemsdiscount" but was changed
     * because of ambiguity to cash discount. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>itemsRebate</b></em>' feature
     * @generated
     */
    public Double getItemsRebate() {

        return itemsRebate;
    }

    /**
     * Sets the '{@link Document#getItemsRebate() <em>itemsRebate</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This attribute was formerly known as "itemsdiscount" but was changed
     * because of ambiguity to cash discount. <!-- end-model-doc -->
     * 
     * @param newItemsRebate
     *            the new value of the '{@link Document#getItemsRebate()
     *            itemsRebate}' feature.
     * @generated
     */
    public void setItemsRebate(final Double newItemsRebate) {
        firePropertyChange("itemsRebate", this.itemsRebate, newItemsRebate);
        itemsRebate = newItemsRebate;
    }

    /**
     * Returns the value of '<em><b>message</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>message</b></em>' feature
     * @generated
     */
    public String getMessage() {

        return message;
    }

    /**
     * Sets the '{@link Document#getMessage() <em>message</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newMessage
     *            the new value of the '{@link Document#getMessage() message}'
     *            feature.
     * @generated
     */
    public void setMessage(final String newMessage) {
        firePropertyChange("message", this.message, newMessage);
        message = newMessage;
    }

    /**
     * Returns the value of '<em><b>message2</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>message2</b></em>' feature
     * @generated
     */
    public String getMessage2() {

        return message2;
    }

    /**
     * Sets the '{@link Document#getMessage2() <em>message2</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newMessage2
     *            the new value of the '{@link Document#getMessage2() message2}'
     *            feature.
     * @generated
     */
    public void setMessage2(final String newMessage2) {
        firePropertyChange("message2", this.message2, newMessage2);
        message2 = newMessage2;
    }

    /**
     * Returns the value of '<em><b>message3</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>message3</b></em>' feature
     * @generated
     */
    public String getMessage3() {

        return message3;
    }

    /**
     * Sets the '{@link Document#getMessage3() <em>message3</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newMessage3
     *            the new value of the '{@link Document#getMessage3() message3}'
     *            feature.
     * @generated
     */
    public void setMessage3(final String newMessage3) {
        firePropertyChange("message3", this.message3, newMessage3);
        message3 = newMessage3;
    }

    /**
     * Returns the value of '<em><b>netGross</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Information about rounding of document prices. The following values are
     * valid:
     * <ul>
     * <li>0 &hellip; no rounding</li>
     * <li>1 &hellip; The prices are rounded, that the net values are full cent
     * values.</li>
     * <li>2 &hellip; The prices are rounded, that the gross values are full
     * cent values.</li>
     * </ul>
     * <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>netGross</b></em>' feature
     * @generated
     */
    public Integer getNetGross() {

        return netGross;
    }

    /**
     * Sets the '{@link Document#getNetGross() <em>netGross</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Information about rounding of document prices. The following values are
     * valid:
     * <ul>
     * <li>0 &hellip; no rounding</li>
     * <li>1 &hellip; The prices are rounded, that the net values are full cent
     * values.</li>
     * <li>2 &hellip; The prices are rounded, that the gross values are full
     * cent values.</li>
     * </ul>
     * <!-- end-model-doc -->
     * 
     * @param newNetGross
     *            the new value of the '{@link Document#getNetGross() netGross}'
     *            feature.
     * @generated
     */
    public void setNetGross(final Integer newNetGross) {
        firePropertyChange("netGross", this.netGross, newNetGross);
        netGross = newNetGross;
    }

    /**
     * Returns the value of '<em><b>noVatReference</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> If
     * a document doesn't hava a VAT use thisreference for a description. <!--
     * end-model-doc -->
     * 
     * @return the value of '<em><b>noVatReference</b></em>' feature
     * @generated
     */
    public VAT getNoVatReference() {

        return noVatReference;
    }

    /**
     * Sets the '{@link Document#getNoVatReference() <em>noVatReference</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> If
     * a document doesn't hava a VAT use thisreference for a description. <!--
     * end-model-doc -->
     * 
     * @param newNoVatReference
     *            the new value of the '{@link Document#getNoVatReference()
     *            noVatReference}' feature.
     * @generated
     */
    public void setNoVatReference(final VAT newNoVatReference) {
        firePropertyChange("noVatReference", this.noVatReference, newNoVatReference);
        noVatReference = newNoVatReference;
    }

    /**
     * Returns the value of '<em><b>odtPath</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>odtPath</b></em>' feature
     * @generated
     */
    public String getOdtPath() {

        return odtPath;
    }

    /**
     * Sets the '{@link Document#getOdtPath() <em>odtPath</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newOdtPath
     *            the new value of the '{@link Document#getOdtPath() odtPath}'
     *            feature.
     * @generated
     */
    public void setOdtPath(final String newOdtPath) {
        firePropertyChange("odtPath", this.odtPath, newOdtPath);
        odtPath = newOdtPath;
    }

    /**
     * Returns the value of '<em><b>orderDate</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>orderDate</b></em>' feature
     * @generated
     */
    public Date getOrderDate() {

        return orderDate;
    }

    /**
     * Sets the '{@link Document#getOrderDate() <em>orderDate</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newOrderDate
     *            the new value of the '{@link Document#getOrderDate()
     *            orderDate}' feature.
     * @generated
     */
    public void setOrderDate(final Date newOrderDate) {
        firePropertyChange("orderDate", this.orderDate, newOrderDate);
        orderDate = newOrderDate;
    }

    /**
     * Returns the value of '<em><b>paidValue</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The amount of what is paid (relevant for partial payment). <!--
     * end-model-doc -->
     * 
     * @return the value of '<em><b>paidValue</b></em>' feature
     * @generated
     */
    public Double getPaidValue() {

        return paidValue;
    }

    /**
     * Sets the '{@link Document#getPaidValue() <em>paidValue</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The amount of what is paid (relevant for partial payment). <!--
     * end-model-doc -->
     * 
     * @param newPaidValue
     *            the new value of the '{@link Document#getPaidValue()
     *            paidValue}' feature.
     * @generated
     */
    public void setPaidValue(final Double newPaidValue) {
        firePropertyChange("paidValue", this.paidValue, newPaidValue);
        paidValue = newPaidValue;
    }

    /**
     * Returns the value of '<em><b>paid</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>paid</b></em>' feature
     * @generated
     */
    public Boolean getPaid() {

        return paid;
    }

    /**
     * Sets the '{@link Document#getPaid() <em>paid</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPaid
     *            the new value of the '{@link Document#getPaid() paid}'
     *            feature.
     * @generated
     */
    public void setPaid(final Boolean newPaid) {
        firePropertyChange("paid", this.paid, newPaid);
        paid = newPaid;
    }

    /**
     * Returns the value of '<em><b>payDate</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the invoice (or dunning or whatever) is completely paid.
     * <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>payDate</b></em>' feature
     * @generated
     */
    public Date getPayDate() {

        return payDate;
    }

    /**
     * Sets the '{@link Document#getPayDate() <em>payDate</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the invoice (or dunning or whatever) is completely paid.
     * <!-- end-model-doc -->
     * 
     * @param newPayDate
     *            the new value of the '{@link Document#getPayDate() payDate}'
     *            feature.
     * @generated
     */
    public void setPayDate(final Date newPayDate) {
        firePropertyChange("payDate", this.payDate, newPayDate);
        payDate = newPayDate;
    }

    /**
     * Returns the value of '<em><b>payment</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * reference to the payment entry (if not changed manually). <!--
     * end-model-doc -->
     * 
     * @return the value of '<em><b>payment</b></em>' feature
     * @generated
     */
    public Payment getPayment() {

        return payment;
    }

    /**
     * Sets the '{@link Document#getPayment() <em>payment</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * reference to the payment entry (if not changed manually). <!--
     * end-model-doc -->
     * 
     * @param newPayment
     *            the new value of the '{@link Document#getPayment() payment}'
     *            feature.
     * @generated
     */
    public void setPayment(final Payment newPayment) {
        firePropertyChange("payment", this.payment, newPayment);
        payment = newPayment;
    }

    /**
     * Returns the value of '<em><b>pdfPath</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>pdfPath</b></em>' feature
     * @generated
     */
    public String getPdfPath() {

        return pdfPath;
    }

    /**
     * Sets the '{@link Document#getPdfPath() <em>pdfPath</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPdfPath
     *            the new value of the '{@link Document#getPdfPath() pdfPath}'
     *            feature.
     * @generated
     */
    public void setPdfPath(final String newPdfPath) {
        firePropertyChange("pdfPath", this.pdfPath, newPdfPath);
        pdfPath = newPdfPath;
    }

    /**
     * Returns the value of '<em><b>printed</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>printed</b></em>' feature
     * @generated
     */
    public boolean getPrinted() {
        return !Objects.isNull(printed) && printed;
    }

    /**
     * Sets the '{@link Document#getPrinted() <em>printed</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPrinted
     *            the new value of the '{@link Document#getPrinted() printed}'
     *            feature.
     * @generated
     */
    public void setPrinted(Boolean newPrinted) {
        if (newPrinted == null) {
            newPrinted = Boolean.FALSE;
        }
        firePropertyChange("printed", this.printed, newPrinted);
        printed = newPrinted;
    }

    /**
     * Returns the value of '<em><b>printTemplate</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>printTemplate</b></em>' feature
     * @generated
     */
    public String getPrintTemplate() {

        return printTemplate;
    }

    /**
     * Sets the '{@link Document#getPrintTemplate() <em>printTemplate</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPrintTemplate
     *            the new value of the '{@link Document#getPrintTemplate()
     *            printTemplate}' feature.
     * @generated
     */
    public void setPrintTemplate(final String newPrintTemplate) {
        firePropertyChange("printTemplate", this.printTemplate, newPrintTemplate);
        printTemplate = newPrintTemplate;
    }

    /**
     * Returns the value of '<em><b>progress</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The status of the transaction (is an order shipped, paid or in any other
     * state). <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>progress</b></em>' feature
     * @generated
     */
    public Integer getProgress() {

        return progress;
    }

    /**
     * Sets the '{@link Document#getProgress() <em>progress</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The status of the transaction (is an order shipped, paid or in any other
     * state). <!-- end-model-doc -->
     * 
     * @param newProgress
     *            the new value of the '{@link Document#getProgress() progress}'
     *            feature.
     * @generated
     */
    public void setProgress(final Integer newProgress) {
        firePropertyChange("progress", this.progress, newProgress);
        progress = newProgress;
    }

    /**
     * Returns the value of '<em><b>receiver</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The receivers of this document. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>receiver</b></em>' feature
     * @generated
     */
    public List<DocumentReceiver> getReceiver() {

        return receiver;
    }

    /**
     * Sets the '{@link Document#getReceiver() <em>receiver</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The receivers of this document. <!-- end-model-doc -->
     * 
     * @param newReceiver
     *            the new value of the '{@link Document#getReceiver() receiver}'
     *            feature.
     * @generated
     */
    public void setReceiver(final List<DocumentReceiver> newReceiver) {
        firePropertyChange("receiver", this.receiver, newReceiver);
        receiver = newReceiver;
    }

    /**
     * Returns the value of '<em><b>serviceDate</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>serviceDate</b></em>' feature
     * @generated
     */
    public Date getServiceDate() {

        return serviceDate;
    }

    /**
     * Sets the '{@link Document#getServiceDate() <em>serviceDate</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newServiceDate
     *            the new value of the '{@link Document#getServiceDate()
     *            serviceDate}' feature.
     * @generated
     */
    public void setServiceDate(final Date newServiceDate) {
        firePropertyChange("serviceDate", this.serviceDate, newServiceDate);
        serviceDate = newServiceDate;
    }

    /**
     * Returns the value of '<em><b>shipping</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * reference to the shipping entry (if not changed manually). <!--
     * end-model-doc -->
     * 
     * @return the value of '<em><b>shipping</b></em>' feature
     * @generated
     */
    public Shipping getShipping() {

        return shipping;
    }

    /**
     * Sets the '{@link Document#getShipping() <em>shipping</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> A
     * reference to the shipping entry (if not changed manually). <!--
     * end-model-doc -->
     * 
     * @param newShipping
     *            the new value of the '{@link Document#getShipping() shipping}'
     *            feature.
     * @generated
     */
    public void setShipping(final Shipping newShipping) {
        firePropertyChange("shipping", this.shipping, newShipping);
        shipping = newShipping;
    }

    /**
     * Returns the value of '<em><b>shippingAutoVat</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>shippingAutoVat</b></em>' feature
     * @generated
     */
    public ShippingVatType getShippingAutoVat() {

        return shippingAutoVat;
    }

    /**
     * Sets the '{@link Document#getShippingAutoVat() <em>shippingAutoVat</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newShippingAutoVat
     *            the new value of the '{@link Document#getShippingAutoVat()
     *            shippingAutoVat}' feature.
     * @generated
     */
    public void setShippingAutoVat(final ShippingVatType newShippingAutoVat) {
        firePropertyChange("shippingAutoVat", this.shippingAutoVat, newShippingAutoVat);
        shippingAutoVat = newShippingAutoVat;
    }

    /**
     * Returns the value of '<em><b>shippingValue</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>shippingValue</b></em>' feature
     * @generated
     */
    public Double getShippingValue() {

        return shippingValue;
    }

    /**
     * Sets the '{@link Document#getShippingValue() <em>shippingValue</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newShippingValue
     *            the new value of the '{@link Document#getShippingValue()
     *            shippingValue}' feature.
     * @generated
     */
    public void setShippingValue(final Double newShippingValue) {
        firePropertyChange("shippingValue", this.shippingValue, newShippingValue);
        shippingValue = newShippingValue;
    }

    /**
     * Returns the value of '<em><b>sourceDocument</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This is the reference to the document from which this document was
     * created. <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>sourceDocument</b></em>' feature
     * @generated
     */
    public Document getSourceDocument() {

        return sourceDocument;
    }

    /**
     * Sets the '{@link Document#getSourceDocument() <em>sourceDocument</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This is the reference to the document from which this document was
     * created. <!-- end-model-doc -->
     * 
     * @param newSourceDocument
     *            the new value of the '{@link Document#getSourceDocument()
     *            sourceDocument}' feature.
     * @generated
     */
    public void setSourceDocument(final Document newSourceDocument) {
        firePropertyChange("sourceDocument", this.sourceDocument, newSourceDocument);
        sourceDocument = newSourceDocument;
    }

    /**
     * Returns the value of '<em><b>tara</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>tara</b></em>' feature
     * @generated
     */
    public Double getTara() {

        return tara;
    }

    /**
     * Sets the '{@link Document#getTara() <em>tara</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newTara
     *            the new value of the '{@link Document#getTara() tara}'
     *            feature.
     * @generated
     */
    public void setTara(final Double newTara) {
        firePropertyChange("tara", this.tara, newTara);
        tara = newTara;
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
     * Sets the '{@link Document#getTotalValue() <em>totalValue</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newTotalValue
     *            the new value of the '{@link Document#getTotalValue()
     *            totalValue}' feature.
     * @generated
     */
    public void setTotalValue(final Double newTotalValue) {
        firePropertyChange("totalValue", this.totalValue, newTotalValue);
        totalValue = newTotalValue;
    }

    /**
     * Returns the value of '<em><b>transactionId</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This id is for grouping of multiple documents that belongs to the same
     * transaction. It is a unique ID which determines the transaction. <!--
     * end-model-doc -->
     * 
     * @return the value of '<em><b>transactionId</b></em>' feature
     * @generated
     */
    public Integer getTransactionId() {

        return transactionId;
    }

    /**
     * Sets the '{@link Document#getTransactionId() <em>transactionId</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * This id is for grouping of multiple documents that belongs to the same
     * transaction. It is a unique ID which determines the transaction. <!--
     * end-model-doc -->
     * 
     * @param newTransactionId
     *            the new value of the '{@link Document#getTransactionId()
     *            transactionId}' feature.
     * @generated
     */
    public void setTransactionId(final Integer newTransactionId) {
        firePropertyChange("transactionId", this.transactionId, newTransactionId);
        transactionId = newTransactionId;
    }

    /**
     * Returns the value of '<em><b>webshopDate</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>webshopDate</b></em>' feature
     * @generated
     */
    public Date getWebshopDate() {

        return webshopDate;
    }

    /**
     * Sets the '{@link Document#getWebshopDate() <em>webshopDate</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newWebshopDate
     *            the new value of the '{@link Document#getWebshopDate()
     *            webshopDate}' feature.
     * @generated
     */
    public void setWebshopDate(final Date newWebshopDate) {
        firePropertyChange("webshopDate", this.webshopDate, newWebshopDate);
        webshopDate = newWebshopDate;
    }

    /**
     * Returns the value of '<em><b>webshopId</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>webshopId</b></em>' feature
     * @generated
     */
    public String getWebshopId() {

        return webshopId;
    }

    /**
     * Sets the '{@link Document#getWebshopId() <em>webshopId</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newWebshopId
     *            the new value of the '{@link Document#getWebshopId()
     *            webshopId}' feature.
     * @generated
     */
    public void setWebshopId(final String newWebshopId) {
        firePropertyChange("webshopId", this.webshopId, newWebshopId);
        webshopId = newWebshopId;
    }

    /**
     * Returns the value of '<em><b>vestingPeriodStart</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>vestingPeriodStart</b></em>' feature
     * @generated
     */
    public Date getVestingPeriodStart() {

        return vestingPeriodStart;
    }

    /**
     * Sets the '{@link Document#getVestingPeriodStart()
     * <em>vestingPeriodStart</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newVestingPeriodStart
     *            the new value of the '{@link Document#getVestingPeriodStart()
     *            vestingPeriodStart}' feature.
     * @generated
     */
    public void setVestingPeriodStart(final Date newVestingPeriodStart) {
        firePropertyChange("vestingPeriodStart", this.vestingPeriodStart, newVestingPeriodStart);
        vestingPeriodStart = newVestingPeriodStart;
    }

    /**
     * Returns the value of '<em><b>vestingPeriodEnd</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>vestingPeriodEnd</b></em>' feature
     * @generated
     */
    public Date getVestingPeriodEnd() {

        return vestingPeriodEnd;
    }

    /**
     * Sets the '{@link Document#getVestingPeriodEnd()
     * <em>vestingPeriodEnd</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newVestingPeriodEnd
     *            the new value of the '{@link Document#getVestingPeriodEnd()
     *            vestingPeriodEnd}' feature.
     * @generated
     */
    public void setVestingPeriodEnd(final Date newVestingPeriodEnd) {
        firePropertyChange("vestingPeriodEnd", this.vestingPeriodEnd, newVestingPeriodEnd);
        vestingPeriodEnd = newVestingPeriodEnd;
    }

    /**
     * Returns the value of '<em><b>version</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * version attribute <!-- end-model-doc -->
     * 
     * @return the value of '<em><b>version</b></em>' feature
     * @generated
     */
    public Integer getVersion() {

        return version;
    }

    /**
     * Sets the '{@link Document#getVersion() <em>version</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * version attribute <!-- end-model-doc -->
     * 
     * @param newVersion
     *            the new value of the '{@link Document#getVersion() version}'
     *            feature.
     * @generated
     */
    public void setVersion(final Integer newVersion) {
        firePropertyChange("version", this.version, newVersion);
        version = newVersion;
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
     * Sets the '{@link Document#getName() <em>name</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The name is equal to account name. <!-- end-model-doc -->
     * 
     * @param newName
     *            the new value of the '{@link Document#getName() name}'
     *            feature.
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
     * Sets the '{@link Document#getDateAdded() <em>dateAdded</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date when the entity was added. <!-- end-model-doc -->
     * 
     * @param newDateAdded
     *            the new value of the '{@link Document#getDateAdded()
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
     * Sets the '{@link Document#getModifiedBy() <em>modifiedBy</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModifiedBy
     *            the new value of the '{@link Document#getModifiedBy()
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
     * Sets the '{@link Document#getModified() <em>modified</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newModified
     *            the new value of the '{@link Document#getModified() modified}'
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
     * Sets the '{@link Document#getId() <em>id</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newId
     *            the new value of the '{@link Document#getId() id}' feature.
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
     * Sets the '{@link Document#getDeleted() <em>deleted</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Indicates if the entity has been deleted. <!-- end-model-doc -->
     * 
     * @param newDeleted
     *            the new value of the '{@link Document#getDeleted() deleted}'
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
     * Sets the '{@link Document#getValidFrom() <em>validFrom</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date from which this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidFrom
     *            the new value of the '{@link Document#getValidFrom()
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
     * Sets the '{@link Document#getValidTo() <em>validTo</em>}' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The date until this entity should be valid (used for historized
     * entities). <!-- end-model-doc -->
     * 
     * @param newValidTo
     *            the new value of the '{@link Document#getValidTo() validTo}'
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
        return "Document" + " addressFirstLine: [" + getAddressFirstLine() + "]" + " billingType: [" + getBillingType() + "]" + " customerRef: ["
                + getCustomerRef() + "]" + " deposit: [" + getDeposit() + "]" + " documentDate: [" + getDocumentDate() + "]" + " dueDays: [" + getDueDays()
                + "]" + " itemsRebate: [" + getItemsRebate() + "]" + " message: [" + getMessage() + "]" + " message2: [" + getMessage2() + "]" + " message3: ["
                + getMessage3() + "]" + " netGross: [" + getNetGross() + "]" + " odtPath: [" + getOdtPath() + "]" + " orderDate: [" + getOrderDate() + "]"
                + " paidValue: [" + getPaidValue() + "]" + " paid: [" + getPaid() + "]" + " payDate: [" + getPayDate() + "]" + " pdfPath: [" + getPdfPath()
                + "]" + " printed: [" + getPrinted() + "]" + " printTemplate: [" + getPrintTemplate() + "]" + " progress: [" + getProgress() + "]"
                + " serviceDate: [" + getServiceDate() + "]" + " shippingAutoVat: [" + getShippingAutoVat() + "]" + " shippingValue: [" + getShippingValue()
                + "]" + " tara: [" + getTara() + "]" + " totalValue: [" + getTotalValue() + "]" + " transactionId: [" + getTransactionId() + "]"
                + " webshopDate: [" + getWebshopDate() + "]" + " webshopId: [" + getWebshopId() + "]" + " vestingPeriodStart: [" + getVestingPeriodStart() + "]"
                + " vestingPeriodEnd: [" + getVestingPeriodEnd() + "]" + " version: [" + getVersion() + "]" + " name: [" + getName() + "]" + " dateAdded: ["
                + getDateAdded() + "]" + " modifiedBy: [" + getModifiedBy() + "]" + " modified: [" + getModified() + "]" + " id: [" + getId() + "]"
                + " deleted: [" + getDeleted() + "]" + " validFrom: [" + getValidFrom() + "]" + " validTo: [" + getValidTo() + "]";
    }
}
