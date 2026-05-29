/* 
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2024 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     The Fakturama Team - initial API and implementation
 */
 
package org.fakturama.export.einvoice.model;

import java.math.BigDecimal;

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
     * Field BT-116 Gesamtsumme Netto
     * 
     * @return the vatCategoryTaxableAmount
     */
    public BigDecimal getVatCategoryTaxableAmount() {
        return vatCategoryTaxableAmount;
    }

    /**
     * Field BT-116 Gesamtsumme Netto
     * 
     * @param vatCategoryTaxableAmount
     *            the vatCategoryTaxableAmount to set
     */
    public void setVatCategoryTaxableAmount(final BigDecimal vatCategoryTaxableAmount) {
        this.vatCategoryTaxableAmount = vatCategoryTaxableAmount;
    }

    /**
     * Field BT-117 Steuerbetrag
     * 
     * @return the vatCategoryTaxAmount
     */
    public BigDecimal getVatCategoryTaxAmount() {
        return vatCategoryTaxAmount;
    }

    /**
     * Field BT-117 Steuerbetrag
     * 
     * @param vatCategoryTaxAmount
     *            the vatCategoryTaxAmount to set
     */
    public void setVatCategoryTaxAmount(final BigDecimal vatCategoryTaxAmount) {
        this.vatCategoryTaxAmount = vatCategoryTaxAmount;
    }

    /**
     * Field BT-118 Steuerkategorie
     * 
     * @return the vatCategoryCode
     */
    public String getVatCategoryCode() {
        return vatCategoryCode;
    }

    /**
     * Field BT-118 Steuerkategorie
     * 
     * @param vatCategoryCode
     *            the vatCategoryCode to set
     */
    public void setVatCategoryCode(final String vatCategoryCode) {
        this.vatCategoryCode = vatCategoryCode;
    }

    /**
     * Field BT-119 Steuersatz
     * 
     * @return the vatCategoryRate
     */
    public BigDecimal getVatCategoryRate() {
        return vatCategoryRate;
    }

    /**
     * Field BT-119 Steuersatz
     * 
     * @param vatCategoryRate
     *            the vatCategoryRate to set
     */
    public void setVatCategoryRate(final BigDecimal vatCategoryRate) {
        this.vatCategoryRate = vatCategoryRate;
    }

    /**
     * Field BT-120 Befreiungsgrund
     * 
     * @return the vatExemptionReasonText
     */
    public String getVatExemptionReasonText() {
        return vatExemptionReasonText;
    }

    /**
     * Field BT-120 Befreiungsgrund
     * 
     * @param vatExemptionReasonText
     *            the vatExemptionReasonText to set
     */
    public void setVatExemptionReasonText(final String vatExemptionReasonText) {
        this.vatExemptionReasonText = vatExemptionReasonText;
    }

    /**
     * Field BT-121 Code für Befreiungsgrund
     * 
     * @return the vatExemptionReasonCode
     */
    public String getVatExemptionReasonCode() {
        return vatExemptionReasonCode;
    }

    /**
     * Field BT-121 Code für Befreiungsgrund
     * 
     * @param vatExemptionReasonCode
     *            the vatExemptionReasonCode to set
     */
    public void setVatExemptionReasonCode(final String vatExemptionReasonCode) {
        this.vatExemptionReasonCode = vatExemptionReasonCode;
    }

}