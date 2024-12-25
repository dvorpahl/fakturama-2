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

public class InvoiceDocumentTotals {
    /**
     * BG-23 Aufschlüsslung der Steuern
     */
    public class InvoiceVatBreakdown {

        /**
         * BT-116 Gesamtsumme Netto
         */
        private BigDecimal vatCategoryTaxableAmount;

        /**
         * BT-117 Steuerbetrag
         */
        private BigDecimal vatCategoryTaxAmount;

        /**
         * BT-118 Steuerkategorie
         */
        private String vatCategoryCode;

        /**
         * BT-119 Steuersatz
         */
        private BigDecimal vatCategoryRate;

        /**
         * BT-120 Befreiungsgrund
         */
        private String vatExemptionReasonText;

        /**
         * BT-121 Code für Befreiungsgrund
         */
        private String vatExemptionReasonCode;

        /**
         * @return the vatCategoryTaxableAmount
         */
        public BigDecimal getVatCategoryTaxableAmount() {
            return vatCategoryTaxableAmount;
        }

        /**
         * @param vatCategoryTaxableAmount
         *            the vatCategoryTaxableAmount to set
         */
        public void setVatCategoryTaxableAmount(final BigDecimal vatCategoryTaxableAmount) {
            this.vatCategoryTaxableAmount = vatCategoryTaxableAmount;
        }

        /**
         * @return the vatCategoryTaxAmount
         */
        public BigDecimal getVatCategoryTaxAmount() {
            return vatCategoryTaxAmount;
        }

        /**
         * @param vatCategoryTaxAmount
         *            the vatCategoryTaxAmount to set
         */
        public void setVatCategoryTaxAmount(final BigDecimal vatCategoryTaxAmount) {
            this.vatCategoryTaxAmount = vatCategoryTaxAmount;
        }

        /**
         * @return the vatCategoryCode
         */
        public String getVatCategoryCode() {
            return vatCategoryCode;
        }

        /**
         * @param vatCategoryCode
         *            the vatCategoryCode to set
         */
        public void setVatCategoryCode(final String vatCategoryCode) {
            this.vatCategoryCode = vatCategoryCode;
        }

        /**
         * @return the vatCategoryRate
         */
        public BigDecimal getVatCategoryRate() {
            return vatCategoryRate;
        }

        /**
         * @param vatCategoryRate
         *            the vatCategoryRate to set
         */
        public void setVatCategoryRate(final BigDecimal vatCategoryRate) {
            this.vatCategoryRate = vatCategoryRate;
        }

        /**
         * @return the vatExemptionReasonText
         */
        public String getVatExemptionReasonText() {
            return vatExemptionReasonText;
        }

        /**
         * @param vatExemptionReasonText
         *            the vatExemptionReasonText to set
         */
        public void setVatExemptionReasonText(final String vatExemptionReasonText) {
            this.vatExemptionReasonText = vatExemptionReasonText;
        }

        /**
         * @return the vatExemptionReasonCode
         */
        public String getVatExemptionReasonCode() {
            return vatExemptionReasonCode;
        }

        /**
         * @param vatExemptionReasonCode
         *            the vatExemptionReasonCode to set
         */
        public void setVatExemptionReasonCode(final String vatExemptionReasonCode) {
            this.vatExemptionReasonCode = vatExemptionReasonCode;
        }

    }

    private BigDecimal sumOfInvoiceLineNetAmount;
    private BigDecimal sumOfAllowancesOnDocumentLevel;
    private BigDecimal sumOfChargesOnDocumentLevel;
    private BigDecimal invoiceTotalAmountWithoutVat;
    private BigDecimal invoiceTotalVatAmount;
    private BigDecimal invoiceTotalVatAmountInAccountingCurrency;
    private BigDecimal invoiceTotalAmountWithVat;
    private BigDecimal paidAmount;
    private BigDecimal roundingAmount;
    private BigDecimal amountDueForPayment;
    private List<InvoiceVatBreakdown> invoiceVatBreakdowns = new ArrayList<>();

    /**
     * @return the sumOfInvoiceLineNetAmount
     */
    public BigDecimal getSumOfInvoiceLineNetAmount() {
        return sumOfInvoiceLineNetAmount;
    }

    /**
     * @param sumOfInvoiceLineNetAmount
     *            the sumOfInvoiceLineNetAmount to set
     */
    public void setSumOfInvoiceLineNetAmount(final BigDecimal sumOfInvoiceLineNetAmount) {
        this.sumOfInvoiceLineNetAmount = sumOfInvoiceLineNetAmount;
    }

    /**
     * @return the sumOfAllowancesOnDocumentLevel
     */
    public BigDecimal getSumOfAllowancesOnDocumentLevel() {
        return sumOfAllowancesOnDocumentLevel;
    }

    /**
     * @param sumOfAllowancesOnDocumentLevel
     *            the sumOfAllowancesOnDocumentLevel to set
     */
    public void setSumOfAllowancesOnDocumentLevel(final BigDecimal sumOfAllowancesOnDocumentLevel) {
        this.sumOfAllowancesOnDocumentLevel = sumOfAllowancesOnDocumentLevel;
    }

    /**
     * @return the sumOfChargesOnDocumentLevel
     */
    public BigDecimal getSumOfChargesOnDocumentLevel() {
        return sumOfChargesOnDocumentLevel;
    }

    /**
     * @param sumOfChargesOnDocumentLevel
     *            the sumOfChargesOnDocumentLevel to set
     */
    public void setSumOfChargesOnDocumentLevel(final BigDecimal sumOfChargesOnDocumentLevel) {
        this.sumOfChargesOnDocumentLevel = sumOfChargesOnDocumentLevel;
    }

    /**
     * @return the invoiceTotalAmountWithoutVat
     */
    public BigDecimal getInvoiceTotalAmountWithoutVat() {
        return invoiceTotalAmountWithoutVat;
    }

    /**
     * @param invoiceTotalAmountWithoutVat
     *            the invoiceTotalAmountWithoutVat to set
     */
    public void setInvoiceTotalAmountWithoutVat(final BigDecimal invoiceTotalAmountWithoutVat) {
        this.invoiceTotalAmountWithoutVat = invoiceTotalAmountWithoutVat;
    }

    /**
     * @return the invoiceTotalVatAmount
     */
    public BigDecimal getInvoiceTotalVatAmount() {
        return invoiceTotalVatAmount;
    }

    /**
     * @param invoiceTotalVatAmount
     *            the invoiceTotalVatAmount to set
     */
    public void setInvoiceTotalVatAmount(final BigDecimal invoiceTotalVatAmount) {
        this.invoiceTotalVatAmount = invoiceTotalVatAmount;
    }

    /**
     * @return the invoiceTotalVatAmountInAccountingCurrency
     */
    public BigDecimal getInvoiceTotalVatAmountInAccountingCurrency() {
        return invoiceTotalVatAmountInAccountingCurrency;
    }

    /**
     * @param invoiceTotalVatAmountInAccountingCurrency
     *            the invoiceTotalVatAmountInAccountingCurrency to set
     */
    public void setInvoiceTotalVatAmountInAccountingCurrency(final BigDecimal invoiceTotalVatAmountInAccountingCurrency) {
        this.invoiceTotalVatAmountInAccountingCurrency = invoiceTotalVatAmountInAccountingCurrency;
    }

    /**
     * @return the invoiceTotalAmountWithVat
     */
    public BigDecimal getInvoiceTotalAmountWithVat() {
        return invoiceTotalAmountWithVat;
    }

    /**
     * @param invoiceTotalAmountWithVat
     *            the invoiceTotalAmountWithVat to set
     */
    public void setInvoiceTotalAmountWithVat(final BigDecimal invoiceTotalAmountWithVat) {
        this.invoiceTotalAmountWithVat = invoiceTotalAmountWithVat;
    }

    /**
     * @return the paidAmount
     */
    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    /**
     * @param paidAmount
     *            the paidAmount to set
     */
    public void setPaidAmount(final BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * @return the roundingAmount
     */
    public BigDecimal getRoundingAmount() {
        return roundingAmount;
    }

    /**
     * @param roundingAmount
     *            the roundingAmount to set
     */
    public void setRoundingAmount(final BigDecimal roundingAmount) {
        this.roundingAmount = roundingAmount;
    }

    /**
     * @return the amountDueForPayment
     */
    public BigDecimal getAmountDueForPayment() {
        return amountDueForPayment;
    }

    /**
     * @param amountDueForPayment
     *            the amountDueForPayment to set
     */
    public void setAmountDueForPayment(final BigDecimal amountDueForPayment) {
        this.amountDueForPayment = amountDueForPayment;
    }

    /**
     * @return the invoiceVatBreakdowns
     */
    public List<InvoiceVatBreakdown> getInvoiceVatBreakdowns() {
        return invoiceVatBreakdowns;
    }

    /**
     * @param invoiceVatBreakdowns
     *            the invoiceVatBreakdowns to set
     */
    public void setInvoiceVatBreakdowns(final List<InvoiceVatBreakdown> invoiceVatBreakdowns) {
        this.invoiceVatBreakdowns = invoiceVatBreakdowns;
    }

}
