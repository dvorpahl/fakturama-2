/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2024 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package org.fakturama.export.einvoice.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * BG-25 Positionen
 */
public class InvoicePosition {

    /**
     * BG-26 Rechnungszeiträume für Positionen
     */
    public class InvoiceLinePeriod {

        /**
         * BT-134 Startdatum Abrechnung
         */
        private LocalDate invoiceLinePeriodStartDate;

        /**
         * BT-135 Enddatum Abrechnung
         */
        private LocalDate invoiceLinePeriodEndDate;

        /**
         * @return the invoiceLinePeriodStartDate
         */
        public LocalDate getInvoiceLinePeriodStartDate() {
            return invoiceLinePeriodStartDate;
        }

        /**
         * @param invoiceLinePeriodStartDate
         *            the invoiceLinePeriodStartDate to set
         */
        public void setInvoiceLinePeriodStartDate(final LocalDate invoiceLinePeriodStartDate) {
            this.invoiceLinePeriodStartDate = invoiceLinePeriodStartDate;
        }

        /**
         * @return the invoiceLinePeriodEndDate
         */
        public LocalDate getInvoiceLinePeriodEndDate() {
            return invoiceLinePeriodEndDate;
        }

        /**
         * @param invoiceLinePeriodEndDate
         *            the invoiceLinePeriodEndDate to set
         */
        public void setInvoiceLinePeriodEndDate(final LocalDate invoiceLinePeriodEndDate) {
            this.invoiceLinePeriodEndDate = invoiceLinePeriodEndDate;
        }

    }

    /**
     * BG-32 Metadaten zur Position
     */
    public class InvoicePositionMetadata {

        /**
         * BT-160 Schlüssel zum Metadatum
         */
        private String itemAttributeName;

        /**
         * BT-161 Wert zum Metadatum
         */
        private String itemAttributeValue;

        /**
         * @return the itemAttributeName
         */
        public String getItemAttributeName() {
            return itemAttributeName;
        }

        /**
         * @param itemAttributeName
         *            the itemAttributeName to set
         */
        public void setItemAttributeName(final String itemAttributeName) {
            this.itemAttributeName = itemAttributeName;
        }

        /**
         * @return the itemAttributeValue
         */
        public String getItemAttributeValue() {
            return itemAttributeValue;
        }

        /**
         * @param itemAttributeValue
         *            the itemAttributeValue to set
         */
        public void setItemAttributeValue(final String itemAttributeValue) {
            this.itemAttributeValue = itemAttributeValue;
        }

    }

    /**
     * BT-126 Positionskennung / Positionsnummer
     */
    private String invoiceLineIdentifier;

    /**
     * BT-127 Positionstext
     */
    private String invoiceLineNote;

    /**
     * BT-128 Artikelkennung des Verkäufers
     */
    private String invoiceLineObjectIdentifier;

    /**
     * BT-128-1 Code für Artikelkennung
     */
    private String invoiceLineObjectIdentifierSchemeIdentifier;

    /**
     * BT-129 Menge
     */
    private String invoicedQuantity;

    /**
     * BT-130 Einheit
     */
    private String invoicedQuantityUnitOfMeasureCode;

    /**
     * BT-131 Gesamtpreis Netto
     */
    private String invoiceLineNetAmount;

    /**
     * BT-132 Bestellreferenz / Auftragsreferenz des Käufers
     */
    private String referencedPurchaseOrderLineReference;

    /**
     * BT-133 Kontierungshinweis
     */
    private String invoiceLineBuyerAccountingReference;

    /**
     * BG-26 Rechnungszeiträume Position
     */
    private List<InvoiceLinePeriod> invoiceLinePeriods = new ArrayList<>();

    /**
     * BG-27 Zeilenabschläge
     */
    private List<InvoiceChargesAllowances> invoiceLineAllowances = new ArrayList<>();

    /**
     * BG-28 Zeilenzuschläge
     */
    private List<InvoiceChargesAllowances> invoiceLineCharges = new ArrayList<>();

    /**
     * BG-29 Preise der Position
     */

    /**
     * BT-146 Preis pro Einheit netto, nach Rabatt
     */
    private BigDecimal itemNetPrice;

    /**
     * BT-147 Rabatt (Gesamt)
     */
    private BigDecimal itemPriceDiscount;

    /**
     * BT-148 Preis pro Einheit netto, vor Rabatt
     */
    private BigDecimal itemGrossPrice;

    /**
     * BT-149 Menge
     */
    private BigDecimal itemPriceBaseQuantity;

    /**
     * BT-150 Einheit
     */
    private BigDecimal itemPriceBaseQuantityUnitOfMeasure;

    /**
     * BG-30 Umsatzsteuer der Position
     */

    /**
     * BT-151 Umsatzteuerkategorie
     */
    private String invoicedItemVatCategoryCode;

    /**
     * BT-152 Umsatzsteuersatz
     */
    private BigDecimal invoicedItemVatRate;

    /**
     * BG-31 Artikelinformationen
     */

    /**
     * BT-153 Artikebezeichnung
     */
    private String itemName;

    /**
     * BT-154 Artikelbeschreibung
     */
    private String itemDescription;

    /**
     * BT-155 Artikelnummer
     */
    private String itemSellersIdentifier;

    /**
     * BT-156 Artikelkennung des Käufers
     */
    private String itemBuyersIdentifier;

    /**
     * BT-157 Artikelnummer laut Schema
     */
    private String itemStandardIdentifier;

    /**
     * BT-157-1 Schema zur Artikelnummer
     */
    private String itemStandardIdentifierSchemeIdentifier;

    /**
     * BT-158 Klassifizierung des Artikels
     */
    private String itemClassificationIdentifier;

    /**
     * BT-158-1 Schema zur Klassifizierung
     */
    private String itemClassificationIdentifierSchemeIdentifier;

    /**
     * BT-158-2 Version des Schema zur Klassifizierung
     */
    private String itemClassificationIdentifierSchemeVersionIdentifier;

    /**
     * BT-159 Ursprungsland des Artikels
     */
    private String itemCountryOfOrigin;

    /**
     * BG-32 Metadaten zur Position
     */
    private List<InvoicePositionMetadata> invoicePositionMetadatas = new ArrayList<>();

    /**
     * @return the invoiceLineIdentifier
     */
    public String getInvoiceLineIdentifier() {
        return invoiceLineIdentifier;
    }

    /**
     * @param invoiceLineIdentifier
     *            the invoiceLineIdentifier to set
     */
    public void setInvoiceLineIdentifier(final String invoiceLineIdentifier) {
        this.invoiceLineIdentifier = invoiceLineIdentifier;
    }

    /**
     * @return the invoiceLineNote
     */
    public String getInvoiceLineNote() {
        return invoiceLineNote;
    }

    /**
     * @param invoiceLineNote
     *            the invoiceLineNote to set
     */
    public void setInvoiceLineNote(final String invoiceLineNote) {
        this.invoiceLineNote = invoiceLineNote;
    }

    /**
     * @return the invoiceLineObjectIdentifier
     */
    public String getInvoiceLineObjectIdentifier() {
        return invoiceLineObjectIdentifier;
    }

    /**
     * @param invoiceLineObjectIdentifier
     *            the invoiceLineObjectIdentifier to set
     */
    public void setInvoiceLineObjectIdentifier(final String invoiceLineObjectIdentifier) {
        this.invoiceLineObjectIdentifier = invoiceLineObjectIdentifier;
    }

    /**
     * @return the invoiceLineObjectIdentifierSchemeIdentifier
     */
    public String getInvoiceLineObjectIdentifierSchemeIdentifier() {
        return invoiceLineObjectIdentifierSchemeIdentifier;
    }

    /**
     * @param invoiceLineObjectIdentifierSchemeIdentifier
     *            the invoiceLineObjectIdentifierSchemeIdentifier to set
     */
    public void setInvoiceLineObjectIdentifierSchemeIdentifier(final String invoiceLineObjectIdentifierSchemeIdentifier) {
        this.invoiceLineObjectIdentifierSchemeIdentifier = invoiceLineObjectIdentifierSchemeIdentifier;
    }

    /**
     * @return the invoicedQuantity
     */
    public String getInvoicedQuantity() {
        return invoicedQuantity;
    }

    /**
     * @param invoicedQuantity
     *            the invoicedQuantity to set
     */
    public void setInvoicedQuantity(final String invoicedQuantity) {
        this.invoicedQuantity = invoicedQuantity;
    }

    /**
     * @return the invoicedQuantityUnitOfMeasureCode
     */
    public String getInvoicedQuantityUnitOfMeasureCode() {
        return invoicedQuantityUnitOfMeasureCode;
    }

    /**
     * @param invoicedQuantityUnitOfMeasureCode
     *            the invoicedQuantityUnitOfMeasureCode to set
     */
    public void setInvoicedQuantityUnitOfMeasureCode(final String invoicedQuantityUnitOfMeasureCode) {
        this.invoicedQuantityUnitOfMeasureCode = invoicedQuantityUnitOfMeasureCode;
    }

    /**
     * @return the invoiceLineNetAmount
     */
    public String getInvoiceLineNetAmount() {
        return invoiceLineNetAmount;
    }

    /**
     * @param invoiceLineNetAmount
     *            the invoiceLineNetAmount to set
     */
    public void setInvoiceLineNetAmount(final String invoiceLineNetAmount) {
        this.invoiceLineNetAmount = invoiceLineNetAmount;
    }

    /**
     * @return the referencedPurchaseOrderLineReference
     */
    public String getReferencedPurchaseOrderLineReference() {
        return referencedPurchaseOrderLineReference;
    }

    /**
     * @param referencedPurchaseOrderLineReference
     *            the referencedPurchaseOrderLineReference to set
     */
    public void setReferencedPurchaseOrderLineReference(final String referencedPurchaseOrderLineReference) {
        this.referencedPurchaseOrderLineReference = referencedPurchaseOrderLineReference;
    }

    /**
     * @return the invoiceLineBuyerAccountingReference
     */
    public String getInvoiceLineBuyerAccountingReference() {
        return invoiceLineBuyerAccountingReference;
    }

    /**
     * @param invoiceLineBuyerAccountingReference
     *            the invoiceLineBuyerAccountingReference to set
     */
    public void setInvoiceLineBuyerAccountingReference(final String invoiceLineBuyerAccountingReference) {
        this.invoiceLineBuyerAccountingReference = invoiceLineBuyerAccountingReference;
    }

    /**
     * @return the invoiceLinePeriods
     */
    public List<InvoiceLinePeriod> getInvoiceLinePeriods() {
        return invoiceLinePeriods;
    }

    /**
     * @param invoiceLinePeriods
     *            the invoiceLinePeriods to set
     */
    public void setInvoiceLinePeriods(final List<InvoiceLinePeriod> invoiceLinePeriods) {
        this.invoiceLinePeriods = invoiceLinePeriods;
    }

    /**
     * @return the invoiceLineAllowances
     */
    public List<InvoiceChargesAllowances> getInvoiceLineAllowances() {
        return invoiceLineAllowances;
    }

    /**
     * @param invoiceLineAllowances
     *            the invoiceLineAllowances to set
     */
    public void setInvoiceLineAllowances(final List<InvoiceChargesAllowances> invoiceLineAllowances) {
        this.invoiceLineAllowances = invoiceLineAllowances;
    }

    /**
     * @return the invoiceLineCharges
     */
    public List<InvoiceChargesAllowances> getInvoiceLineCharges() {
        return invoiceLineCharges;
    }

    /**
     * @param invoiceLineCharges
     *            the invoiceLineCharges to set
     */
    public void setInvoiceLineCharges(final List<InvoiceChargesAllowances> invoiceLineCharges) {
        this.invoiceLineCharges = invoiceLineCharges;
    }

    /**
     * @return the itemNetPrice
     */
    public BigDecimal getItemNetPrice() {
        return itemNetPrice;
    }

    /**
     * @param itemNetPrice
     *            the itemNetPrice to set
     */
    public void setItemNetPrice(final BigDecimal itemNetPrice) {
        this.itemNetPrice = itemNetPrice;
    }

    /**
     * @return the itemPriceDiscount
     */
    public BigDecimal getItemPriceDiscount() {
        return itemPriceDiscount;
    }

    /**
     * @param itemPriceDiscount
     *            the itemPriceDiscount to set
     */
    public void setItemPriceDiscount(final BigDecimal itemPriceDiscount) {
        this.itemPriceDiscount = itemPriceDiscount;
    }

    /**
     * @return the itemGrossPrice
     */
    public BigDecimal getItemGrossPrice() {
        return itemGrossPrice;
    }

    /**
     * @param itemGrossPrice
     *            the itemGrossPrice to set
     */
    public void setItemGrossPrice(final BigDecimal itemGrossPrice) {
        this.itemGrossPrice = itemGrossPrice;
    }

    /**
     * @return the itemPriceBaseQuantity
     */
    public BigDecimal getItemPriceBaseQuantity() {
        return itemPriceBaseQuantity;
    }

    /**
     * @param itemPriceBaseQuantity
     *            the itemPriceBaseQuantity to set
     */
    public void setItemPriceBaseQuantity(final BigDecimal itemPriceBaseQuantity) {
        this.itemPriceBaseQuantity = itemPriceBaseQuantity;
    }

    /**
     * @return the itemPriceBaseQuantityUnitOfMeasure
     */
    public BigDecimal getItemPriceBaseQuantityUnitOfMeasure() {
        return itemPriceBaseQuantityUnitOfMeasure;
    }

    /**
     * @param itemPriceBaseQuantityUnitOfMeasure
     *            the itemPriceBaseQuantityUnitOfMeasure to set
     */
    public void setItemPriceBaseQuantityUnitOfMeasure(final BigDecimal itemPriceBaseQuantityUnitOfMeasure) {
        this.itemPriceBaseQuantityUnitOfMeasure = itemPriceBaseQuantityUnitOfMeasure;
    }

    /**
     * @return the invoicedItemVatCategoryCode
     */
    public String getInvoicedItemVatCategoryCode() {
        return invoicedItemVatCategoryCode;
    }

    /**
     * @param invoicedItemVatCategoryCode
     *            the invoicedItemVatCategoryCode to set
     */
    public void setInvoicedItemVatCategoryCode(final String invoicedItemVatCategoryCode) {
        this.invoicedItemVatCategoryCode = invoicedItemVatCategoryCode;
    }

    /**
     * @return the invoicedItemVatRate
     */
    public BigDecimal getInvoicedItemVatRate() {
        return invoicedItemVatRate;
    }

    /**
     * @param invoicedItemVatRate
     *            the invoicedItemVatRate to set
     */
    public void setInvoicedItemVatRate(final BigDecimal invoicedItemVatRate) {
        this.invoicedItemVatRate = invoicedItemVatRate;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName
     *            the itemName to set
     */
    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemDescription
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * @param itemDescription
     *            the itemDescription to set
     */
    public void setItemDescription(final String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * @return the itemSellersIdentifier
     */
    public String getItemSellersIdentifier() {
        return itemSellersIdentifier;
    }

    /**
     * @param itemSellersIdentifier
     *            the itemSellersIdentifier to set
     */
    public void setItemSellersIdentifier(final String itemSellersIdentifier) {
        this.itemSellersIdentifier = itemSellersIdentifier;
    }

    /**
     * @return the itemBuyersIdentifier
     */
    public String getItemBuyersIdentifier() {
        return itemBuyersIdentifier;
    }

    /**
     * @param itemBuyersIdentifier
     *            the itemBuyersIdentifier to set
     */
    public void setItemBuyersIdentifier(final String itemBuyersIdentifier) {
        this.itemBuyersIdentifier = itemBuyersIdentifier;
    }

    /**
     * @return the itemStandardIdentifier
     */
    public String getItemStandardIdentifier() {
        return itemStandardIdentifier;
    }

    /**
     * @param itemStandardIdentifier
     *            the itemStandardIdentifier to set
     */
    public void setItemStandardIdentifier(final String itemStandardIdentifier) {
        this.itemStandardIdentifier = itemStandardIdentifier;
    }

    /**
     * @return the itemStandardIdentifierSchemeIdentifier
     */
    public String getItemStandardIdentifierSchemeIdentifier() {
        return itemStandardIdentifierSchemeIdentifier;
    }

    /**
     * @param itemStandardIdentifierSchemeIdentifier
     *            the itemStandardIdentifierSchemeIdentifier to set
     */
    public void setItemStandardIdentifierSchemeIdentifier(final String itemStandardIdentifierSchemeIdentifier) {
        this.itemStandardIdentifierSchemeIdentifier = itemStandardIdentifierSchemeIdentifier;
    }

    /**
     * @return the itemClassificationIdentifier
     */
    public String getItemClassificationIdentifier() {
        return itemClassificationIdentifier;
    }

    /**
     * @param itemClassificationIdentifier
     *            the itemClassificationIdentifier to set
     */
    public void setItemClassificationIdentifier(final String itemClassificationIdentifier) {
        this.itemClassificationIdentifier = itemClassificationIdentifier;
    }

    /**
     * @return the itemClassificationIdentifierSchemeIdentifier
     */
    public String getItemClassificationIdentifierSchemeIdentifier() {
        return itemClassificationIdentifierSchemeIdentifier;
    }

    /**
     * @param itemClassificationIdentifierSchemeIdentifier
     *            the itemClassificationIdentifierSchemeIdentifier to set
     */
    public void setItemClassificationIdentifierSchemeIdentifier(final String itemClassificationIdentifierSchemeIdentifier) {
        this.itemClassificationIdentifierSchemeIdentifier = itemClassificationIdentifierSchemeIdentifier;
    }

    /**
     * @return the itemClassificationIdentifierSchemeVersionIdentifier
     */
    public String getItemClassificationIdentifierSchemeVersionIdentifier() {
        return itemClassificationIdentifierSchemeVersionIdentifier;
    }

    /**
     * @param itemClassificationIdentifierSchemeVersionIdentifier
     *            the itemClassificationIdentifierSchemeVersionIdentifier to set
     */
    public void setItemClassificationIdentifierSchemeVersionIdentifier(final String itemClassificationIdentifierSchemeVersionIdentifier) {
        this.itemClassificationIdentifierSchemeVersionIdentifier = itemClassificationIdentifierSchemeVersionIdentifier;
    }

    /**
     * @return the itemCountryOfOrigin
     */
    public String getItemCountryOfOrigin() {
        return itemCountryOfOrigin;
    }

    /**
     * @param itemCountryOfOrigin
     *            the itemCountryOfOrigin to set
     */
    public void setItemCountryOfOrigin(final String itemCountryOfOrigin) {
        this.itemCountryOfOrigin = itemCountryOfOrigin;
    }

    /**
     * @return the invoicePositionMetadatas
     */
    public List<InvoicePositionMetadata> getInvoicePositionMetadatas() {
        return invoicePositionMetadatas;
    }

    /**
     * @param invoicePositionMetadatas
     *            the invoicePositionMetadatas to set
     */
    public void setInvoicePositionMetadatas(final List<InvoicePositionMetadata> invoicePositionMetadatas) {
        this.invoicePositionMetadatas = invoicePositionMetadatas;
    }

}
