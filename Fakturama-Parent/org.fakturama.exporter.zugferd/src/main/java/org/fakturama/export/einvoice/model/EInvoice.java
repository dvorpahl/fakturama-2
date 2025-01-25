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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class EInvoice {

    private InvoiceData invoiceData = new InvoiceData();
    private List<InvoiceNote> invoiceNotes = new ArrayList<>();
    private List<PrecedingInvoice> precedingInvoices = new ArrayList<>();

    private InvoiceSeller invoiceSeller = new InvoiceSeller();
    private InvoiceBuyer invoiceBuyer = new InvoiceBuyer();
    private InvoiceSellerRepresentative invoiceSellerRepresentative;
    private InvoiceDeliveryInformation invoiceDeliveryInformation = new InvoiceDeliveryInformation();
    private InvoicePayment invoicePayment = new InvoicePayment();
    private List<InvoiceChargesAllowances> invoiceAllowances = new ArrayList<>();
    private List<InvoiceChargesAllowances> invoiceCharges = new ArrayList<>();
    private InvoiceDocumentTotals invoiceDocumentTotals = new InvoiceDocumentTotals();
    private List<InvoiceAdditionalDocuments> invoiceAdditionalDocuments = new ArrayList<>();
    private List<InvoicePosition> invoicePositions = new ArrayList<>();

    /**
     * BG-23 Aufschlüsselung der Umsatzsteuer auf Ebene der Rechnung
     */
    private List<InvoiceVatBreakdown> invoiceVatBreakdowns = new ArrayList<>();

    /**
     * 
     */
    public EInvoice() {
        // nothing for now
    }

    /**
     * @return the invoiceData
     */
    public InvoiceData getInvoiceData() {
        return invoiceData;
    }

    /**
     * @param invoiceData
     *            the invoiceData to set
     */
    public void setInvoiceData(final InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
    }

    /**
     * @return the invoiceNotes
     */
    public List<InvoiceNote> getInvoiceNotes() {
        return invoiceNotes;
    }

    /**
     * @param invoiceNotes
     *            the invoiceNotes to set
     */
    public void setInvoiceNotes(final List<InvoiceNote> invoiceNotes) {
        this.invoiceNotes = invoiceNotes;
    }

    /**
     * @return the precedingInvoices
     */
    public List<PrecedingInvoice> getPrecedingInvoices() {
        return precedingInvoices;
    }

    /**
     * @param precedingInvoices
     *            the precedingInvoices to set
     */
    public void setPrecedingInvoices(final List<PrecedingInvoice> precedingInvoices) {
        this.precedingInvoices = precedingInvoices;
    }

    /**
     * @return the invoiceSeller
     */
    public InvoiceSeller getInvoiceSeller() {
        return invoiceSeller;
    }

    /**
     * @param invoiceSeller
     *            the invoiceSeller to set
     */
    public void setInvoiceSeller(final InvoiceSeller invoiceSeller) {
        this.invoiceSeller = invoiceSeller;
    }

    /**
     * @return the invoiceBuyer
     */
    public InvoiceBuyer getInvoiceBuyer() {
        return invoiceBuyer;
    }

    /**
     * @param invoiceBuyer
     *            the invoiceBuyer to set
     */
    public void setInvoiceBuyer(final InvoiceBuyer invoiceBuyer) {
        this.invoiceBuyer = invoiceBuyer;
    }

    /**
     * @return the invoiceSellerRepresentative
     */
    public InvoiceSellerRepresentative getInvoiceSellerRepresentative() {
        return invoiceSellerRepresentative;
    }

    /**
     * @param invoiceSellerRepresentative
     *            the invoiceSellerRepresentative to set
     */
    public void setInvoiceSellerRepresentative(final InvoiceSellerRepresentative invoiceSellerRepresentative) {
        this.invoiceSellerRepresentative = invoiceSellerRepresentative;
    }

    /**
     * @return the invoiceDeliveryInformation
     */
    public InvoiceDeliveryInformation getInvoiceDeliveryInformation() {
        return invoiceDeliveryInformation;
    }

    /**
     * @param invoiceDeliveryInformation
     *            the invoiceDeliveryInformation to set
     */
    public void setInvoiceDeliveryInformation(final InvoiceDeliveryInformation invoiceDeliveryInformation) {
        this.invoiceDeliveryInformation = invoiceDeliveryInformation;
    }

    /**
     * @return the invoicePayment
     */
    public InvoicePayment getInvoicePayment() {
        return invoicePayment;
    }

    /**
     * @param invoicePayment
     *            the invoicePayment to set
     */
    public void setInvoicePayment(final InvoicePayment invoicePayment) {
        this.invoicePayment = invoicePayment;
    }

    /**
     * @return the invoiceAllowances
     */
    public List<InvoiceChargesAllowances> getInvoiceAllowances() {
        return invoiceAllowances;
    }

    /**
     * @param invoiceAllowances
     *            the invoiceAllowances to set
     */
    public void setInvoiceAllowances(final List<InvoiceChargesAllowances> invoiceAllowances) {
        this.invoiceAllowances = invoiceAllowances;
    }

    /**
     * @return the invoiceCharges
     */
    public List<InvoiceChargesAllowances> getInvoiceCharges() {
        return invoiceCharges;
    }

    /**
     * @param invoiceCharges
     *            the invoiceCharges to set
     */
    public void setInvoiceCharges(final List<InvoiceChargesAllowances> invoiceCharges) {
        this.invoiceCharges = invoiceCharges;
    }

    /**
     * @return the invoiceDocumentTotals
     */
    public InvoiceDocumentTotals getInvoiceDocumentTotals() {
        return invoiceDocumentTotals;
    }

    /**
     * @param invoiceDocumentTotals
     *            the invoiceDocumentTotals to set
     */
    public void setInvoiceDocumentTotals(final InvoiceDocumentTotals invoiceDocumentTotals) {
        this.invoiceDocumentTotals = invoiceDocumentTotals;
    }

    /**
     * @return the invoiceAdditionalDocuments
     */
    public List<InvoiceAdditionalDocuments> getInvoiceAdditionalDocuments() {
        return invoiceAdditionalDocuments;
    }

    /**
     * @param invoiceAdditionalDocuments
     *            the invoiceAdditionalDocuments to set
     */
    public void setInvoiceAdditionalDocuments(final List<InvoiceAdditionalDocuments> invoiceAdditionalDocuments) {
        this.invoiceAdditionalDocuments = invoiceAdditionalDocuments;
    }

    /**
     * @return the invoicePositions
     */
    public List<InvoicePosition> getInvoicePositions() {
        return invoicePositions;
    }

    /**
     * @param invoicePositions
     *            the invoicePositions to set
     */
    public void setInvoicePositions(final List<InvoicePosition> invoicePositions) {
        this.invoicePositions = invoicePositions;
    }

    /**
     * insert BG-23 Aufschlüsselung der Umsatzsteuer auf Ebene der Rechnung
     * 
     * @return the invoiceVatBreakdowns
     */
    public List<InvoiceVatBreakdown> getInvoiceVatBreakdowns() {
        return invoiceVatBreakdowns;
    }

    /**
     * inert BG-23 Aufschlüsselung der Umsatzsteuer auf Ebene der Rechnung
     * 
     * @param invoiceVatBreakdowns
     *            the invoiceVatBreakdowns to set
     */
    public void setInvoiceVatBreakdowns(final List<InvoiceVatBreakdown> invoiceVatBreakdowns) {
        this.invoiceVatBreakdowns = invoiceVatBreakdowns;
    }

}
