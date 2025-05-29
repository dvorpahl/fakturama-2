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

/**
 * BG-22
 */
public class InvoiceDocumentTotals {
    /**
     * BT-106 Summe aller Positionen (netto)
     */
    private BigDecimal sumOfInvoiceLineNetAmount;

    /**
     * BT-107 Summe Nachlässe (netto)
     */
    private BigDecimal sumOfAllowancesOnDocumentLevel;

    /**
     * BT-108 Summe Zuschläge (netto)
     */
    private BigDecimal sumOfChargesOnDocumentLevel;

    /**
     * BT-109 Gesamtsumme (netto)
     */
    private BigDecimal invoiceTotalAmountWithoutVat;

    /**
     * BT-110 Summe Umsatzsteuer
     */
    private BigDecimal invoiceTotalVatAmount;

    /**
     * BT-111 Ust Betrag in Währung des Verkäufers
     */
    private BigDecimal invoiceTotalVatAmountInAccountingCurrency;

    /**
     * BT-112 Gesamtsumme (brutto)
     */
    private BigDecimal invoiceTotalAmountWithVat;

    /**
     * BT-113 Gezahlter Betrag
     */
    private BigDecimal paidAmount;

    /**
     * BT-114 Rundungsbetrag
     */
    private BigDecimal roundingAmount;

    /**
     * BT-115 Fälliger Betrag
     */
    private BigDecimal amountDueForPayment;

    /**
     * Field BT-106 Summe aller Positionen (netto)
     * 
     * @return the sumOfInvoiceLineNetAmount
     */
    public BigDecimal getSumOfInvoiceLineNetAmount() {
        return sumOfInvoiceLineNetAmount;
    }

    /**
     * Field BT-106 Summe aller Positionen (netto)
     * 
     * @param sumOfInvoiceLineNetAmount
     *            the sumOfInvoiceLineNetAmount to set
     */
    public void setSumOfInvoiceLineNetAmount(final BigDecimal sumOfInvoiceLineNetAmount) {
        this.sumOfInvoiceLineNetAmount = sumOfInvoiceLineNetAmount;
    }

    /**
     * Field BT-107 Summe Nachlässe (netto)
     * 
     * @return the sumOfAllowancesOnDocumentLevel
     */
    public BigDecimal getSumOfAllowancesOnDocumentLevel() {
        return sumOfAllowancesOnDocumentLevel;
    }

    /**
     * Field BT-107 Summe Nachlässe (netto)
     * 
     * @param sumOfAllowancesOnDocumentLevel
     *            the sumOfAllowancesOnDocumentLevel to set
     */
    public void setSumOfAllowancesOnDocumentLevel(final BigDecimal sumOfAllowancesOnDocumentLevel) {
        this.sumOfAllowancesOnDocumentLevel = sumOfAllowancesOnDocumentLevel;
    }

    /**
     * Field BT-108 Summe Zuschläge (netto)
     * 
     * @return the sumOfChargesOnDocumentLevel
     */
    public BigDecimal getSumOfChargesOnDocumentLevel() {
        return sumOfChargesOnDocumentLevel;
    }

    /**
     * Field BT-108 Summe Zuschläge (netto)
     * 
     * @param sumOfChargesOnDocumentLevel
     *            the sumOfChargesOnDocumentLevel to set
     */
    public void setSumOfChargesOnDocumentLevel(final BigDecimal sumOfChargesOnDocumentLevel) {
        this.sumOfChargesOnDocumentLevel = sumOfChargesOnDocumentLevel;
    }

    /**
     * Field BT-109 Gesamtsumme (netto)
     * 
     * @return the invoiceTotalAmountWithoutVat
     */
    public BigDecimal getInvoiceTotalAmountWithoutVat() {
        return invoiceTotalAmountWithoutVat;
    }

    /**
     * Field BT-109 Gesamtsumme (netto)
     * 
     * @param invoiceTotalAmountWithoutVat
     *            the invoiceTotalAmountWithoutVat to set
     */
    public void setInvoiceTotalAmountWithoutVat(final BigDecimal invoiceTotalAmountWithoutVat) {
        this.invoiceTotalAmountWithoutVat = invoiceTotalAmountWithoutVat;
    }

    /**
     * Field BT-110 Summe Umsatzsteuer
     * 
     * @return the invoiceTotalVatAmount
     */
    public BigDecimal getInvoiceTotalVatAmount() {
        return invoiceTotalVatAmount;
    }

    /**
     * Field BT-110 Summe Umsatzsteuer
     * 
     * @param invoiceTotalVatAmount
     *            the invoiceTotalVatAmount to set
     */
    public void setInvoiceTotalVatAmount(final BigDecimal invoiceTotalVatAmount) {
        this.invoiceTotalVatAmount = invoiceTotalVatAmount;
    }

    /**
     * Field BT-111 Ust Betrag in Währung des Verkäufers
     * 
     * @return the invoiceTotalVatAmountInAccountingCurrency
     */
    public BigDecimal getInvoiceTotalVatAmountInAccountingCurrency() {
        return invoiceTotalVatAmountInAccountingCurrency;
    }

    /**
     * Field BT-111 Ust Betrag in Währung des Verkäufers
     * 
     * @param invoiceTotalVatAmountInAccountingCurrency
     *            the invoiceTotalVatAmountInAccountingCurrency to set
     */
    public void setInvoiceTotalVatAmountInAccountingCurrency(final BigDecimal invoiceTotalVatAmountInAccountingCurrency) {
        this.invoiceTotalVatAmountInAccountingCurrency = invoiceTotalVatAmountInAccountingCurrency;
    }

    /**
     * Field BT-112 Gesamtsumme (brutto)
     * 
     * @return the invoiceTotalAmountWithVat
     */
    public BigDecimal getInvoiceTotalAmountWithVat() {
        return invoiceTotalAmountWithVat;
    }

    /**
     * Field BT-112 Gesamtsumme (brutto)
     * 
     * @param invoiceTotalAmountWithVat
     *            the invoiceTotalAmountWithVat to set
     */
    public void setInvoiceTotalAmountWithVat(final BigDecimal invoiceTotalAmountWithVat) {
        this.invoiceTotalAmountWithVat = invoiceTotalAmountWithVat;
    }

    /**
     * Field BT-113 Gezahlter Betrag
     * 
     * @return the paidAmount
     */
    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    /**
     * Field BT-113 Gezahlter Betrag
     * 
     * @param paidAmount
     *            the paidAmount to set
     */
    public void setPaidAmount(final BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * Field BT-114 Rundungsbetrag
     * 
     * @return the roundingAmount
     */
    public BigDecimal getRoundingAmount() {
        return roundingAmount;
    }

    /**
     * Field BT-114 Rundungsbetrag
     * 
     * @param roundingAmount
     *            the roundingAmount to set
     */
    public void setRoundingAmount(final BigDecimal roundingAmount) {
        this.roundingAmount = roundingAmount;
    }

    /**
     * Field BT-115 Fälliger Betrag
     * 
     * @return the amountDueForPayment
     */
    public BigDecimal getAmountDueForPayment() {
        return amountDueForPayment;
    }

    /**
     * Field BT-115 Fälliger Betrag
     * 
     * @param amountDueForPayment
     *            the amountDueForPayment to set
     */
    public void setAmountDueForPayment(final BigDecimal amountDueForPayment) {
        this.amountDueForPayment = amountDueForPayment;
    }

}
