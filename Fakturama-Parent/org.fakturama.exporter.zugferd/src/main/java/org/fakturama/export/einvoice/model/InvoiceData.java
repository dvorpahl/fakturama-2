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
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber
     *            the invoiceNumber to set
     */
    public void setInvoiceNumber(final String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * @return the invoiceIssueDate
     */
    public LocalDate getInvoiceIssueDate() {
        return invoiceIssueDate;
    }

    /**
     * @param invoiceIssueDate
     *            the invoiceIssueDate to set
     */
    public void setInvoiceIssueDate(final LocalDate invoiceIssueDate) {
        this.invoiceIssueDate = invoiceIssueDate;
    }

    /**
     * @return the invoiceTypeCode
     */
    public String getInvoiceTypeCode() {
        return invoiceTypeCode;
    }

    /**
     * @param invoiceTypeCode
     *            the invoiceTypeCode to set
     */
    public void setInvoiceTypeCode(final String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    /**
     * @return the invoiceCurrencyCode
     */
    public String getInvoiceCurrencyCode() {
        return invoiceCurrencyCode;
    }

    /**
     * @param invoiceCurrencyCode
     *            the invoiceCurrencyCode to set
     */
    public void setInvoiceCurrencyCode(final String invoiceCurrencyCode) {
        this.invoiceCurrencyCode = invoiceCurrencyCode;
    }

    /**
     * @return the vatAccountingCurrencyCode
     */
    public String getVatAccountingCurrencyCode() {
        return vatAccountingCurrencyCode;
    }

    /**
     * @param vatAccountingCurrencyCode
     *            the vatAccountingCurrencyCode to set
     */
    public void setVatAccountingCurrencyCode(final String vatAccountingCurrencyCode) {
        this.vatAccountingCurrencyCode = vatAccountingCurrencyCode;
    }

    /**
     * @return the valueAddedTaxPointDate
     */
    public LocalDate getValueAddedTaxPointDate() {
        return valueAddedTaxPointDate;
    }

    /**
     * @param valueAddedTaxPointDate
     *            the valueAddedTaxPointDate to set
     */
    public void setValueAddedTaxPointDate(final LocalDate valueAddedTaxPointDate) {
        this.valueAddedTaxPointDate = valueAddedTaxPointDate;
    }

    /**
     * @return the valueAddedTaxPointDateCode
     */
    public String getValueAddedTaxPointDateCode() {
        return valueAddedTaxPointDateCode;
    }

    /**
     * @param valueAddedTaxPointDateCode
     *            the valueAddedTaxPointDateCode to set
     */
    public void setValueAddedTaxPointDateCode(final String valueAddedTaxPointDateCode) {
        this.valueAddedTaxPointDateCode = valueAddedTaxPointDateCode;
    }

    /**
     * @return the paymentDueDate
     */
    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    /**
     * @param paymentDueDate
     *            the paymentDueDate to set
     */
    public void setPaymentDueDate(final LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    /**
     * @return the buyerReference
     */
    public String getBuyerReference() {
        return buyerReference;
    }

    /**
     * @param buyerReference
     *            the buyerReference to set
     */
    public void setBuyerReference(final String buyerReference) {
        this.buyerReference = buyerReference;
    }

    /**
     * @return the projectReference
     */
    public String getProjectReference() {
        return projectReference;
    }

    /**
     * @param projectReference
     *            the projectReference to set
     */
    public void setProjectReference(final String projectReference) {
        this.projectReference = projectReference;
    }

    /**
     * @return the contractReference
     */
    public String getContractReference() {
        return contractReference;
    }

    /**
     * @param contractReference
     *            the contractReference to set
     */
    public void setContractReference(final String contractReference) {
        this.contractReference = contractReference;
    }

    /**
     * @return the purchaseOrderReference
     */
    public String getPurchaseOrderReference() {
        return purchaseOrderReference;
    }

    /**
     * @param purchaseOrderReference
     *            the purchaseOrderReference to set
     */
    public void setPurchaseOrderReference(final String purchaseOrderReference) {
        this.purchaseOrderReference = purchaseOrderReference;
    }

    /**
     * @return the salesOrderReference
     */
    public String getSalesOrderReference() {
        return salesOrderReference;
    }

    /**
     * @param salesOrderReference
     *            the salesOrderReference to set
     */
    public void setSalesOrderReference(final String salesOrderReference) {
        this.salesOrderReference = salesOrderReference;
    }

    /**
     * @return the receivingAdviceReference
     */
    public String getReceivingAdviceReference() {
        return receivingAdviceReference;
    }

    /**
     * @param receivingAdviceReference
     *            the receivingAdviceReference to set
     */
    public void setReceivingAdviceReference(final String receivingAdviceReference) {
        this.receivingAdviceReference = receivingAdviceReference;
    }

    /**
     * @return the despatchAdviceReference
     */
    public String getDespatchAdviceReference() {
        return despatchAdviceReference;
    }

    /**
     * @param despatchAdviceReference
     *            the despatchAdviceReference to set
     */
    public void setDespatchAdviceReference(final String despatchAdviceReference) {
        this.despatchAdviceReference = despatchAdviceReference;
    }

    /**
     * @return the tenderOrLotReference
     */
    public String getTenderOrLotReference() {
        return tenderOrLotReference;
    }

    /**
     * @param tenderOrLotReference
     *            the tenderOrLotReference to set
     */
    public void setTenderOrLotReference(final String tenderOrLotReference) {
        this.tenderOrLotReference = tenderOrLotReference;
    }

    /**
     * @return the invoicedObjectIdentifier
     */
    public String getInvoicedObjectIdentifier() {
        return invoicedObjectIdentifier;
    }

    /**
     * @param invoicedObjectIdentifier
     *            the invoicedObjectIdentifier to set
     */
    public void setInvoicedObjectIdentifier(final String invoicedObjectIdentifier) {
        this.invoicedObjectIdentifier = invoicedObjectIdentifier;
    }

    /**
     * @return the invoicedObjectIdentifierSchemeIdentifier
     */
    public String getInvoicedObjectIdentifierSchemeIdentifier() {
        return invoicedObjectIdentifierSchemeIdentifier;
    }

    /**
     * @param invoicedObjectIdentifierSchemeIdentifier
     *            the invoicedObjectIdentifierSchemeIdentifier to set
     */
    public void setInvoicedObjectIdentifierSchemeIdentifier(final String invoicedObjectIdentifierSchemeIdentifier) {
        this.invoicedObjectIdentifierSchemeIdentifier = invoicedObjectIdentifierSchemeIdentifier;
    }

    /**
     * @return the buyerAccountingReference
     */
    public String getBuyerAccountingReference() {
        return buyerAccountingReference;
    }

    /**
     * @param buyerAccountingReference
     *            the buyerAccountingReference to set
     */
    public void setBuyerAccountingReference(final String buyerAccountingReference) {
        this.buyerAccountingReference = buyerAccountingReference;
    }

    /**
     * @return the paymentTerms
     */
    public String getPaymentTerms() {
        return paymentTerms;
    }

    /**
     * @param paymentTerms
     *            the paymentTerms to set
     */
    public void setPaymentTerms(final String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    /**
     * @return the businessProcessType
     */
    public String getBusinessProcessType() {
        return businessProcessType;
    }

    /**
     * @param businessProcessType
     *            the businessProcessType to set
     */
    public void setBusinessProcessType(final String businessProcessType) {
        this.businessProcessType = businessProcessType;
    }

    /**
     * @return the specificationIdentifier
     */
    public String getSpecificationIdentifier() {
        return specificationIdentifier;
    }

    /**
     * @param specificationIdentifier
     *            the specificationIdentifier to set
     */
    public void setSpecificationIdentifier(final String specificationIdentifier) {
        this.specificationIdentifier = specificationIdentifier;
    }

    /**
     * @return the invoicingPeriodStartDate
     */
    public LocalDate getInvoicingPeriodStartDate() {
        return invoicingPeriodStartDate;
    }

    /**
     * @param invoicingPeriodStartDate
     *            the invoicingPeriodStartDate to set
     */
    public void setInvoicingPeriodStartDate(final LocalDate invoicingPeriodStartDate) {
        this.invoicingPeriodStartDate = invoicingPeriodStartDate;
    }

    /**
     * @return the invoicingPeriodEndDate
     */
    public LocalDate getInvoicingPeriodEndDate() {
        return invoicingPeriodEndDate;
    }

    /**
     * @param invoicingPeriodEndDate
     *            the invoicingPeriodEndDate to set
     */
    public void setInvoicingPeriodEndDate(final LocalDate invoicingPeriodEndDate) {
        this.invoicingPeriodEndDate = invoicingPeriodEndDate;
    }

}
