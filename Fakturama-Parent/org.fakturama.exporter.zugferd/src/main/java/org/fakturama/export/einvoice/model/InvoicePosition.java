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
import java.util.ArrayList;
import java.util.List;

/**
 * BG-25 Positionen
 */
public class InvoicePosition {

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
    private BigDecimal invoicedQuantity;

    /**
     * BT-130 Einheit
     */
    private String invoicedQuantityUnitOfMeasureCode;

    /**
     * BT-131 Gesamtpreis Netto
     */
    private BigDecimal invoiceLineNetAmount;

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
    private InvoiceLinePeriod invoiceLinePeriod;

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
     * Field BT-126 Positionskennung / Positionsnummer
     * 
     * @return the invoiceLineIdentifier
     */
    public String getInvoiceLineIdentifier() {
        return invoiceLineIdentifier;
    }

    /**
     * Field BT-126 Positionskennung / Positionsnummer
     * 
     * @param invoiceLineIdentifier
     *            the invoiceLineIdentifier to set
     */
    public void setInvoiceLineIdentifier(final String invoiceLineIdentifier) {
        this.invoiceLineIdentifier = invoiceLineIdentifier;
    }

    /**
     * Field BT-127 Positionstext
     * 
     * @return the invoiceLineNote
     */
    public String getInvoiceLineNote() {
        return invoiceLineNote;
    }

    /**
     * Field BT-127 Positionstext
     * 
     * @param invoiceLineNote
     *            the invoiceLineNote to set
     */
    public void setInvoiceLineNote(final String invoiceLineNote) {
        this.invoiceLineNote = invoiceLineNote;
    }

    /**
     * Field BT-128 Artikelkennung des Verkäufers
     * 
     * @return the invoiceLineObjectIdentifier
     */
    public String getInvoiceLineObjectIdentifier() {
        return invoiceLineObjectIdentifier;
    }

    /**
     * Field BT-128 Artikelkennung des Verkäufers
     * 
     * @param invoiceLineObjectIdentifier
     *            the invoiceLineObjectIdentifier to set
     */
    public void setInvoiceLineObjectIdentifier(final String invoiceLineObjectIdentifier) {
        this.invoiceLineObjectIdentifier = invoiceLineObjectIdentifier;
    }

    /**
     * Field BT-128-1 Code für Artikelkennung
     * 
     * @return the invoiceLineObjectIdentifierSchemeIdentifier
     */
    public String getInvoiceLineObjectIdentifierSchemeIdentifier() {
        return invoiceLineObjectIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-128-1 Code für Artikelkennung
     * 
     * @param invoiceLineObjectIdentifierSchemeIdentifier
     *            the invoiceLineObjectIdentifierSchemeIdentifier to set
     */
    public void setInvoiceLineObjectIdentifierSchemeIdentifier(final String invoiceLineObjectIdentifierSchemeIdentifier) {
        this.invoiceLineObjectIdentifierSchemeIdentifier = invoiceLineObjectIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-129 Menge
     * 
     * @return the invoicedQuantity
     */
    public BigDecimal getInvoicedQuantity() {
        return invoicedQuantity;
    }

    /**
     * Field BT-129 Menge
     * 
     * @param invoicedQuantity
     *            the invoicedQuantity to set
     */
    public void setInvoicedQuantity(final BigDecimal invoicedQuantity) {
        this.invoicedQuantity = invoicedQuantity;
    }

    /**
     * Field BT-130 Einheit
     * 
     * @return the invoicedQuantityUnitOfMeasureCode
     */
    public String getInvoicedQuantityUnitOfMeasureCode() {
        return invoicedQuantityUnitOfMeasureCode;
    }

    /**
     * Field BT-130 Einheit
     * 
     * @param invoicedQuantityUnitOfMeasureCode
     *            the invoicedQuantityUnitOfMeasureCode to set
     */
    public void setInvoicedQuantityUnitOfMeasureCode(final String invoicedQuantityUnitOfMeasureCode) {
        this.invoicedQuantityUnitOfMeasureCode = invoicedQuantityUnitOfMeasureCode;
    }

    /**
     * Field BT-131 Gesamtpreis Netto
     * 
     * @return the invoiceLineNetAmount
     */
    public BigDecimal getInvoiceLineNetAmount() {
        return invoiceLineNetAmount;
    }

    /**
     * Field BT-131 Gesamtpreis Netto
     * 
     * @param invoiceLineNetAmount
     *            the invoiceLineNetAmount to set
     */
    public void setInvoiceLineNetAmount(final BigDecimal invoiceLineNetAmount) {
        this.invoiceLineNetAmount = invoiceLineNetAmount;
    }

    /**
     * Field BT-132 Bestellreferenz / Auftragsreferenz des Käufers
     * 
     * @return the referencedPurchaseOrderLineReference
     */
    public String getReferencedPurchaseOrderLineReference() {
        return referencedPurchaseOrderLineReference;
    }

    /**
     * Field BT-132 Bestellreferenz / Auftragsreferenz des Käufers
     * 
     * @param referencedPurchaseOrderLineReference
     *            the referencedPurchaseOrderLineReference to set
     */
    public void setReferencedPurchaseOrderLineReference(final String referencedPurchaseOrderLineReference) {
        this.referencedPurchaseOrderLineReference = referencedPurchaseOrderLineReference;
    }

    /**
     * Field BT-133 Kontierungshinweis
     * 
     * @return the invoiceLineBuyerAccountingReference
     */
    public String getInvoiceLineBuyerAccountingReference() {
        return invoiceLineBuyerAccountingReference;
    }

    /**
     * Field BT-133 Kontierungshinweis
     * 
     * @param invoiceLineBuyerAccountingReference
     *            the invoiceLineBuyerAccountingReference to set
     */
    public void setInvoiceLineBuyerAccountingReference(final String invoiceLineBuyerAccountingReference) {
        this.invoiceLineBuyerAccountingReference = invoiceLineBuyerAccountingReference;
    }

    /**
     * BG-26 Rechnungszeiträume Position
     * 
     * @return the invoiceLinePeriod
     */
    public InvoiceLinePeriod getInvoiceLinePeriod() {
        return invoiceLinePeriod;
    }

    /**
     * BG-26 Rechnungszeiträume Position
     * 
     * @param invoiceLinePeriod
     *            the invoiceLinePeriod to set
     */
    public void setInvoiceLinePeriod(final InvoiceLinePeriod invoiceLinePeriod) {
        this.invoiceLinePeriod = invoiceLinePeriod;
    }

    /**
     * BG-27 Zeilenabschläge
     * 
     * @return the invoiceLineAllowances
     */
    public List<InvoiceChargesAllowances> getInvoiceLineAllowances() {
        return invoiceLineAllowances;
    }

    /**
     * BG-27 Zeilenabschläge
     * 
     * @param invoiceLineAllowances
     *            the invoiceLineAllowances to set
     */
    public void setInvoiceLineAllowances(final List<InvoiceChargesAllowances> invoiceLineAllowances) {
        this.invoiceLineAllowances = invoiceLineAllowances;
    }

    /**
     * BG-28 Zeilenzuschläge
     * 
     * @return the invoiceLineCharges
     */
    public List<InvoiceChargesAllowances> getInvoiceLineCharges() {
        return invoiceLineCharges;
    }

    /**
     * BG-28 Zeilenzuschläge
     * 
     * @param invoiceLineCharges
     *            the invoiceLineCharges to set
     */
    public void setInvoiceLineCharges(final List<InvoiceChargesAllowances> invoiceLineCharges) {
        this.invoiceLineCharges = invoiceLineCharges;
    }

    /**
     * Field BT-146 Preis pro Einheit netto, nach Rabatt
     * 
     * @return the itemNetPrice
     */
    public BigDecimal getItemNetPrice() {
        return itemNetPrice;
    }

    /**
     * Field BT-146 Preis pro Einheit netto, nach Rabatt
     * 
     * @param itemNetPrice
     *            the itemNetPrice to set
     */
    public void setItemNetPrice(final BigDecimal itemNetPrice) {
        this.itemNetPrice = itemNetPrice;
    }

    /**
     * Field BT-147 Rabatt (Gesamt)
     * 
     * @return the itemPriceDiscount
     */
    public BigDecimal getItemPriceDiscount() {
        return itemPriceDiscount;
    }

    /**
     * Field BT-147 Rabatt (Gesamt)
     * 
     * @param itemPriceDiscount
     *            the itemPriceDiscount to set
     */
    public void setItemPriceDiscount(final BigDecimal itemPriceDiscount) {
        this.itemPriceDiscount = itemPriceDiscount;
    }

    /**
     * Field BT-148 Preis pro Einheit netto, vor Rabatt
     * 
     * @return the itemGrossPrice
     */
    public BigDecimal getItemGrossPrice() {
        return itemGrossPrice;
    }

    /**
     * Field BT-148 Preis pro Einheit netto, vor Rabatt
     * 
     * @param itemGrossPrice
     *            the itemGrossPrice to set
     */
    public void setItemGrossPrice(final BigDecimal itemGrossPrice) {
        this.itemGrossPrice = itemGrossPrice;
    }

    /**
     * Field BT-149 Menge
     * 
     * @return the itemPriceBaseQuantity
     */
    public BigDecimal getItemPriceBaseQuantity() {
        return itemPriceBaseQuantity;
    }

    /**
     * Field BT-149 Menge
     * 
     * @param itemPriceBaseQuantity
     *            the itemPriceBaseQuantity to set
     */
    public void setItemPriceBaseQuantity(final BigDecimal itemPriceBaseQuantity) {
        this.itemPriceBaseQuantity = itemPriceBaseQuantity;
    }

    /**
     * Field BT-150 Einheit
     * 
     * @return the itemPriceBaseQuantityUnitOfMeasure
     */
    public BigDecimal getItemPriceBaseQuantityUnitOfMeasure() {
        return itemPriceBaseQuantityUnitOfMeasure;
    }

    /**
     * Field BT-150 Einheit
     * 
     * @param itemPriceBaseQuantityUnitOfMeasure
     *            the itemPriceBaseQuantityUnitOfMeasure to set
     */
    public void setItemPriceBaseQuantityUnitOfMeasure(final BigDecimal itemPriceBaseQuantityUnitOfMeasure) {
        this.itemPriceBaseQuantityUnitOfMeasure = itemPriceBaseQuantityUnitOfMeasure;
    }

    /**
     * Field BT-151 Umsatzteuerkategorie
     * 
     * @return the invoicedItemVatCategoryCode
     */
    public String getInvoicedItemVatCategoryCode() {
        return invoicedItemVatCategoryCode;
    }

    /**
     * Field BT-151 Umsatzteuerkategorie
     * 
     * @param invoicedItemVatCategoryCode
     *            the invoicedItemVatCategoryCode to set
     */
    public void setInvoicedItemVatCategoryCode(final String invoicedItemVatCategoryCode) {
        this.invoicedItemVatCategoryCode = invoicedItemVatCategoryCode;
    }

    /**
     * Field BT-152 Umsatzsteuersatz
     * 
     * @return the invoicedItemVatRate
     */
    public BigDecimal getInvoicedItemVatRate() {
        return invoicedItemVatRate;
    }

    /**
     * Field BT-152 Umsatzsteuersatz
     * 
     * @param invoicedItemVatRate
     *            the invoicedItemVatRate to set
     */
    public void setInvoicedItemVatRate(final BigDecimal invoicedItemVatRate) {
        this.invoicedItemVatRate = invoicedItemVatRate;
    }

    /**
     * Field BT-153 Artikebezeichnung
     * 
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Field BT-153 Artikebezeichnung
     * 
     * @param itemName
     *            the itemName to set
     */
    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }

    /**
     * Field BT-154 Artikelbeschreibung
     * 
     * @return the itemDescription
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Field BT-154 Artikelbeschreibung
     * 
     * @param itemDescription
     *            the itemDescription to set
     */
    public void setItemDescription(final String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * Field BT-155 Artikelnummer
     * 
     * @return the itemSellersIdentifier
     */
    public String getItemSellersIdentifier() {
        return itemSellersIdentifier;
    }

    /**
     * Field BT-155 Artikelnummer
     * 
     * @param itemSellersIdentifier
     *            the itemSellersIdentifier to set
     */
    public void setItemSellersIdentifier(final String itemSellersIdentifier) {
        this.itemSellersIdentifier = itemSellersIdentifier;
    }

    /**
     * Field BT-156 Artikelkennung des Käufers
     * 
     * @return the itemBuyersIdentifier
     */
    public String getItemBuyersIdentifier() {
        return itemBuyersIdentifier;
    }

    /**
     * Field BT-156 Artikelkennung des Käufers
     * 
     * @param itemBuyersIdentifier
     *            the itemBuyersIdentifier to set
     */
    public void setItemBuyersIdentifier(final String itemBuyersIdentifier) {
        this.itemBuyersIdentifier = itemBuyersIdentifier;
    }

    /**
     * Field BT-157 Artikelnummer laut Schema
     * 
     * @return the itemStandardIdentifier
     */
    public String getItemStandardIdentifier() {
        return itemStandardIdentifier;
    }

    /**
     * Field BT-157 Artikelnummer laut Schema
     * 
     * @param itemStandardIdentifier
     *            the itemStandardIdentifier to set
     */
    public void setItemStandardIdentifier(final String itemStandardIdentifier) {
        this.itemStandardIdentifier = itemStandardIdentifier;
    }

    /**
     * Field BT-157-1 Schema zur Artikelnummer
     * 
     * @return the itemStandardIdentifierSchemeIdentifier
     */
    public String getItemStandardIdentifierSchemeIdentifier() {
        return itemStandardIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-157-1 Schema zur Artikelnummer
     * 
     * @param itemStandardIdentifierSchemeIdentifier
     *            the itemStandardIdentifierSchemeIdentifier to set
     */
    public void setItemStandardIdentifierSchemeIdentifier(final String itemStandardIdentifierSchemeIdentifier) {
        this.itemStandardIdentifierSchemeIdentifier = itemStandardIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-158 Klassifizierung des Artikels
     * 
     * @return the itemClassificationIdentifier
     */
    public String getItemClassificationIdentifier() {
        return itemClassificationIdentifier;
    }

    /**
     * Field BT-158 Klassifizierung des Artikels
     * 
     * @param itemClassificationIdentifier
     *            the itemClassificationIdentifier to set
     */
    public void setItemClassificationIdentifier(final String itemClassificationIdentifier) {
        this.itemClassificationIdentifier = itemClassificationIdentifier;
    }

    /**
     * Field BT-158-1 Schema zur Klassifizierung
     * 
     * @return the itemClassificationIdentifierSchemeIdentifier
     */
    public String getItemClassificationIdentifierSchemeIdentifier() {
        return itemClassificationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-158-1 Schema zur Klassifizierung
     * 
     * @param itemClassificationIdentifierSchemeIdentifier
     *            the itemClassificationIdentifierSchemeIdentifier to set
     */
    public void setItemClassificationIdentifierSchemeIdentifier(final String itemClassificationIdentifierSchemeIdentifier) {
        this.itemClassificationIdentifierSchemeIdentifier = itemClassificationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-158-2 Version des Schema zur Klassifizierung
     * 
     * @return the itemClassificationIdentifierSchemeVersionIdentifier
     */
    public String getItemClassificationIdentifierSchemeVersionIdentifier() {
        return itemClassificationIdentifierSchemeVersionIdentifier;
    }

    /**
     * Field BT-158-2 Version des Schema zur Klassifizierung
     * 
     * @param itemClassificationIdentifierSchemeVersionIdentifier
     *            the itemClassificationIdentifierSchemeVersionIdentifier to set
     */
    public void setItemClassificationIdentifierSchemeVersionIdentifier(final String itemClassificationIdentifierSchemeVersionIdentifier) {
        this.itemClassificationIdentifierSchemeVersionIdentifier = itemClassificationIdentifierSchemeVersionIdentifier;
    }

    /**
     * Field BT-159 Ursprungsland des Artikels
     * 
     * @return the itemCountryOfOrigin
     */
    public String getItemCountryOfOrigin() {
        return itemCountryOfOrigin;
    }

    /**
     * Field BT-159 Ursprungsland des Artikels
     * 
     * @param itemCountryOfOrigin
     *            the itemCountryOfOrigin to set
     */
    public void setItemCountryOfOrigin(final String itemCountryOfOrigin) {
        this.itemCountryOfOrigin = itemCountryOfOrigin;
    }

    /**
     * insert BG-32 Metadaten zur Position
     * 
     * @return the invoicePositionMetadatas
     */
    public List<InvoicePositionMetadata> getInvoicePositionMetadatas() {
        return invoicePositionMetadatas;
    }

    /**
     * insert BG-32 Metadaten zur Position
     * 
     * @param invoicePositionMetadatas
     *            the invoicePositionMetadatas to set
     */
    public void setInvoicePositionMetadatas(final List<InvoicePositionMetadata> invoicePositionMetadatas) {
        this.invoicePositionMetadatas = invoicePositionMetadatas;
    }

}
