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
 * BG-20 Nachlässe Dokument // BG-21 Zuschläge Dokument //
 */
public class InvoiceChargesAllowances {

    /**
     * BT-92, BT-99 Betrag Netto
     */
    private BigDecimal amount;

    /**
     * BT-93, BT-100 Basisbetrag Netto
     */
    private BigDecimal baseAmount;

    /**
     * BT-94, BT-101 Prozentsatz
     */
    private BigDecimal percentage;

    /**
     * BT-95, BT-102 Umsatzsteuerkategorie
     */
    private String vatCategoryCode;

    /**
     * BT-96, BT-103 Umsatzsteuersatz
     */
    private BigDecimal vatRate;

    /**
     * BT-97, BT-104 Begründung
     */
    private String reason;

    /**
     * BT-98, BT-105 Code zur Begründung
     */
    private String reasonCode;

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the baseAmount
     */
    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    /**
     * @param baseAmount
     *            the baseAmount to set
     */
    public void setBaseAmount(final BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    /**
     * @return the percentage
     */
    public BigDecimal getPercentage() {
        return percentage;
    }

    /**
     * @param percentage
     *            the percentage to set
     */
    public void setPercentage(final BigDecimal percentage) {
        this.percentage = percentage;
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
     * @return the vatRate
     */
    public BigDecimal getVatRate() {
        return vatRate;
    }

    /**
     * @param vatRate
     *            the vatRate to set
     */
    public void setVatRate(final BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     *            the reason to set
     */
    public void setReason(final String reason) {
        this.reason = reason;
    }

    /**
     * @return the reasonCode
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * @param reasonCode
     *            the reasonCode to set
     */
    public void setReasonCode(final String reasonCode) {
        this.reasonCode = reasonCode;
    }

}
