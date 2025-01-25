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

import java.time.LocalDate;

/**
 * General: Section of eInvoice BT-Fields // BG-2: Informationen über den
 * Geschäftsprozess // BG-14: Abrechnungszeitraum
 */
public class InvoiceData {

    /**
     * BT-1 Rechnungsnummer
     */
    private String invoiceNumber;

    /**
     * BT-2 Rechnungsdatum
     */
    private LocalDate invoiceIssueDate;

    /**
     * BT-3 Rechnungsart
     */
    private String invoiceTypeCode;

    /**
     * BT-5 Währung
     */
    private String invoiceCurrencyCode;

    /**
     * BT-6 Währung Steuer (nur gefüllt, falls ungleich BT-5)
     */
    private String vatAccountingCurrencyCode;

    /**
     * BT-7 Buchungsdatum Umsatzsteuer
     */
    private LocalDate valueAddedTaxPointDate;

    /**
     * BT-8 Buchungscode Umsatzsteuer
     */
    private String valueAddedTaxPointDateCode;

    /**
     * BT-9 Fälligkeitsdatum
     */
    private LocalDate paymentDueDate;

    /**
     * BT-10 Käuferreferenz (z.B. Leitweg-ID)
     */
    private String buyerReference;

    /**
     * BT-11 Projektnummer
     */
    private String projectReference;

    /**
     * BT-12 Vertragsnummer
     */
    private String contractReference;

    /**
     * BT-13 Bestellnummer
     */
    private String purchaseOrderReference;

    /**
     * BT-14 Auftragsnummer
     */
    private String salesOrderReference;

    /**
     * BT-15 referenzierte Empfangsbestätigung
     */
    private String receivingAdviceReference;

    /**
     * BT-16 referenzierte Versandanzeige
     */
    private String despatchAdviceReference;

    /**
     * BT-17 Vergabenummer
     */
    private String tenderOrLotReference;

    /**
     * BT-18 Objektkennung (Verkäufer)
     */
    private String invoicedObjectIdentifier;

    /**
     * BT-18-1 Bildungsmuster Objektkennung
     */
    private String invoicedObjectIdentifierSchemeIdentifier;

    /**
     * BT-19 Buchhaltungsinformationen
     */
    private String buyerAccountingReference;

    /**
     * BT-20 Zahlungsbedingungen, Skonto
     */
    private String paymentTerms;

    /************************************************
     * BG-2 Informationen zum Geschäftsprozess
     ************************************************/

    /**
     * BT-23 Kontext des Geschäftsprozesses
     */
    private String businessProcessType;

    /**
     * BT-24 Identifier (Version XRechnung / Zugpferd, ...)
     */
    private String specificationIdentifier;

    /************************************************
     * BG-14 Abrechnungszeitraum
     ************************************************/

    /**
     * BT-73 Abrechnungszeitraum (von)
     */
    private LocalDate invoicingPeriodStartDate;

    /**
     * BT-74 Abrechnungszeitraum (bis)
     */
    private LocalDate invoicingPeriodEndDate;

    public InvoiceData() {
        // nothing at the moment
    }

    /**
     * Field BT-1 Rechnungsnummer
     * 
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Field BT-1 Rechnungsnummer
     * 
     * @param invoiceNumber
     *            the invoiceNumber to set
     */
    public void setInvoiceNumber(final String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * Field BT-2 Rechnungsdatum
     * 
     * @return the invoiceIssueDate
     */
    public LocalDate getInvoiceIssueDate() {
        return invoiceIssueDate;
    }

    /**
     * Field BT-2 Rechnungsdatum
     * 
     * @param invoiceIssueDate
     *            the invoiceIssueDate to set
     */
    public void setInvoiceIssueDate(final LocalDate invoiceIssueDate) {
        this.invoiceIssueDate = invoiceIssueDate;
    }

    /**
     * Field BT-3 Rechnungsart
     * 
     * @return the invoiceTypeCode
     */
    public String getInvoiceTypeCode() {
        return invoiceTypeCode;
    }

    /**
     * Field BT-3 Rechnungsart
     * 
     * @param invoiceTypeCode
     *            the invoiceTypeCode to set
     */
    public void setInvoiceTypeCode(final String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    /**
     * Field BT-5 Währung
     * 
     * @return the invoiceCurrencyCode
     */
    public String getInvoiceCurrencyCode() {
        return invoiceCurrencyCode;
    }

    /**
     * Field BT-5 Währung
     * 
     * @param invoiceCurrencyCode
     *            the invoiceCurrencyCode to set
     */
    public void setInvoiceCurrencyCode(final String invoiceCurrencyCode) {
        this.invoiceCurrencyCode = invoiceCurrencyCode;
    }

    /**
     * Field BT-6 Währung Steuer (nur gefüllt, falls ungleich BT-5)
     * 
     * @return the vatAccountingCurrencyCode
     */
    public String getVatAccountingCurrencyCode() {
        return vatAccountingCurrencyCode;
    }

    /**
     * Field BT-6 Währung Steuer (nur gefüllt, falls ungleich BT-5)
     * 
     * @param vatAccountingCurrencyCode
     *            the vatAccountingCurrencyCode to set
     */
    public void setVatAccountingCurrencyCode(final String vatAccountingCurrencyCode) {
        this.vatAccountingCurrencyCode = vatAccountingCurrencyCode;
    }

    /**
     * Field BT-7 Buchungsdatum Umsatzsteuer
     * 
     * @return the valueAddedTaxPointDate
     */
    public LocalDate getValueAddedTaxPointDate() {
        return valueAddedTaxPointDate;
    }

    /**
     * Field BT-7 Buchungsdatum Umsatzsteuer
     * 
     * @param valueAddedTaxPointDate
     *            the valueAddedTaxPointDate to set
     */
    public void setValueAddedTaxPointDate(final LocalDate valueAddedTaxPointDate) {
        this.valueAddedTaxPointDate = valueAddedTaxPointDate;
    }

    /**
     * Field BT-8 Buchungscode Umsatzsteuer
     * 
     * @return the valueAddedTaxPointDateCode
     */
    public String getValueAddedTaxPointDateCode() {
        return valueAddedTaxPointDateCode;
    }

    /**
     * Field BT-8 Buchungscode Umsatzsteuer
     * 
     * @param valueAddedTaxPointDateCode
     *            the valueAddedTaxPointDateCode to set
     */
    public void setValueAddedTaxPointDateCode(final String valueAddedTaxPointDateCode) {
        this.valueAddedTaxPointDateCode = valueAddedTaxPointDateCode;
    }

    /**
     * Field BT-9 Fälligkeitsdatum
     * 
     * @return the paymentDueDate
     */
    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    /**
     * Field BT-9 Fälligkeitsdatum
     * 
     * @param paymentDueDate
     *            the paymentDueDate to set
     */
    public void setPaymentDueDate(final LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    /**
     * Field BT-10 Käuferreferenz (z.B. Leitweg-ID)
     * 
     * @return the buyerReference
     */
    public String getBuyerReference() {
        return buyerReference;
    }

    /**
     * Field BT-10 Käuferreferenz (z.B. Leitweg-ID)
     * 
     * @param buyerReference
     *            the buyerReference to set
     */
    public void setBuyerReference(final String buyerReference) {
        this.buyerReference = buyerReference;
    }

    /**
     * Field BT-11 Projektnummer
     * 
     * @return the projectReference
     */
    public String getProjectReference() {
        return projectReference;
    }

    /**
     * Field BT-11 Projektnummer
     * 
     * @param projectReference
     *            the projectReference to set
     */
    public void setProjectReference(final String projectReference) {
        this.projectReference = projectReference;
    }

    /**
     * Field BT-12 Vertragsnummer
     * 
     * @return the contractReference
     */
    public String getContractReference() {
        return contractReference;
    }

    /**
     * Field BT-12 Vertragsnummer
     * 
     * @param contractReference
     *            the contractReference to set
     */
    public void setContractReference(final String contractReference) {
        this.contractReference = contractReference;
    }

    /**
     * Field BT-13 Bestellnummer
     * 
     * @return the purchaseOrderReference
     */
    public String getPurchaseOrderReference() {
        return purchaseOrderReference;
    }

    /**
     * Field BT-13 Bestellnummer
     * 
     * @param purchaseOrderReference
     *            the purchaseOrderReference to set
     */
    public void setPurchaseOrderReference(final String purchaseOrderReference) {
        this.purchaseOrderReference = purchaseOrderReference;
    }

    /**
     * Field BT-14 Auftragsnummer
     * 
     * @return the salesOrderReference
     */
    public String getSalesOrderReference() {
        return salesOrderReference;
    }

    /**
     * Field BT-14 Auftragsnummer
     * 
     * @param salesOrderReference
     *            the salesOrderReference to set
     */
    public void setSalesOrderReference(final String salesOrderReference) {
        this.salesOrderReference = salesOrderReference;
    }

    /**
     * Field BT-15 referenzierte Empfangsbestätigung
     * 
     * @return the receivingAdviceReference
     */
    public String getReceivingAdviceReference() {
        return receivingAdviceReference;
    }

    /**
     * Field BT-15 referenzierte Empfangsbestätigung
     * 
     * @param receivingAdviceReference
     *            the receivingAdviceReference to set
     */
    public void setReceivingAdviceReference(final String receivingAdviceReference) {
        this.receivingAdviceReference = receivingAdviceReference;
    }

    /**
     * Field BT-16 referenzierte Versandanzeige
     * 
     * @return the despatchAdviceReference
     */
    public String getDespatchAdviceReference() {
        return despatchAdviceReference;
    }

    /**
     * Field BT-16 referenzierte Versandanzeige
     * 
     * @param despatchAdviceReference
     *            the despatchAdviceReference to set
     */
    public void setDespatchAdviceReference(final String despatchAdviceReference) {
        this.despatchAdviceReference = despatchAdviceReference;
    }

    /**
     * Field BT-17 Vergabenummer
     * 
     * @return the tenderOrLotReference
     */
    public String getTenderOrLotReference() {
        return tenderOrLotReference;
    }

    /**
     * Field BT-17 Vergabenummer
     * 
     * @param tenderOrLotReference
     *            the tenderOrLotReference to set
     */
    public void setTenderOrLotReference(final String tenderOrLotReference) {
        this.tenderOrLotReference = tenderOrLotReference;
    }

    /**
     * Field BT-18 Objektkennung (Verkäufer)
     * 
     * @return the invoicedObjectIdentifier
     */
    public String getInvoicedObjectIdentifier() {
        return invoicedObjectIdentifier;
    }

    /**
     * Field BT-18 Objektkennung (Verkäufer)
     * 
     * @param invoicedObjectIdentifier
     *            the invoicedObjectIdentifier to set
     */
    public void setInvoicedObjectIdentifier(final String invoicedObjectIdentifier) {
        this.invoicedObjectIdentifier = invoicedObjectIdentifier;
    }

    /**
     * Field BT-18-1 Bildungsmuster Objektkennung
     * 
     * @return the invoicedObjectIdentifierSchemeIdentifier
     */
    public String getInvoicedObjectIdentifierSchemeIdentifier() {
        return invoicedObjectIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-18-1 Bildungsmuster Objektkennung
     * 
     * @param invoicedObjectIdentifierSchemeIdentifier
     *            the invoicedObjectIdentifierSchemeIdentifier to set
     */
    public void setInvoicedObjectIdentifierSchemeIdentifier(final String invoicedObjectIdentifierSchemeIdentifier) {
        this.invoicedObjectIdentifierSchemeIdentifier = invoicedObjectIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-19 Buchhaltungsinformationen
     * 
     * @return the buyerAccountingReference
     */
    public String getBuyerAccountingReference() {
        return buyerAccountingReference;
    }

    /**
     * Field BT-19 Buchhaltungsinformationen
     * 
     * @param buyerAccountingReference
     *            the buyerAccountingReference to set
     */
    public void setBuyerAccountingReference(final String buyerAccountingReference) {
        this.buyerAccountingReference = buyerAccountingReference;
    }

    /**
     * Field BT-20 Zahlungsbedingungen, Skonto
     * 
     * @return the paymentTerms
     */
    public String getPaymentTerms() {
        return paymentTerms;
    }

    /**
     * Field BT-20 Zahlungsbedingungen, Skonto
     * 
     * @param paymentTerms
     *            the paymentTerms to set
     */
    public void setPaymentTerms(final String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    /**
     * Field BT-23 Kontext des Geschäftsprozesses
     * 
     * @return the businessProcessType
     */
    public String getBusinessProcessType() {
        return businessProcessType;
    }

    /**
     * Field BT-23 Kontext des Geschäftsprozesses
     * 
     * @param businessProcessType
     *            the businessProcessType to set
     */
    public void setBusinessProcessType(final String businessProcessType) {
        this.businessProcessType = businessProcessType;
    }

    /**
     * Field BT-24 Identifier (Version XRechnung / Zugpferd, ...)
     * 
     * @return the specificationIdentifier
     */
    public String getSpecificationIdentifier() {
        return specificationIdentifier;
    }

    /**
     * Field BT-24 Identifier (Version XRechnung / Zugpferd, ...)
     * 
     * @param specificationIdentifier
     *            the specificationIdentifier to set
     */
    public void setSpecificationIdentifier(final String specificationIdentifier) {
        this.specificationIdentifier = specificationIdentifier;
    }

    /**
     * Field BT-73 Abrechnungszeitraum (von)
     * 
     * @return the invoicingPeriodStartDate
     */
    public LocalDate getInvoicingPeriodStartDate() {
        return invoicingPeriodStartDate;
    }

    /**
     * Field BT-73 Abrechnungszeitraum (von)
     * 
     * @param invoicingPeriodStartDate
     *            the invoicingPeriodStartDate to set
     */
    public void setInvoicingPeriodStartDate(final LocalDate invoicingPeriodStartDate) {
        this.invoicingPeriodStartDate = invoicingPeriodStartDate;
    }

    /**
     * Field BT-74 Abrechnungszeitraum (bis)
     * 
     * @return the invoicingPeriodEndDate
     */
    public LocalDate getInvoicingPeriodEndDate() {
        return invoicingPeriodEndDate;
    }

    /**
     * Field BT-74 Abrechnungszeitraum (bis)
     * 
     * @param invoicingPeriodEndDate
     *            the invoicingPeriodEndDate to set
     */
    public void setInvoicingPeriodEndDate(final LocalDate invoicingPeriodEndDate) {
        this.invoicingPeriodEndDate = invoicingPeriodEndDate;
    }

}
