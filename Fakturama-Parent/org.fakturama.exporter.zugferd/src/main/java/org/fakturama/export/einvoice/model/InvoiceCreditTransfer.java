/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2025 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * The Fakturama Team - initial API and implementation
 */

package org.fakturama.export.einvoice.model;

/************************************************
 * BG-19 SEPA-Lastschrift
 ************************************************/
public class InvoiceCreditTransfer {

    /**
     * BT-84 IBAN
     */
    private String paymentAccountIdentifier;

    /**
     * BT-85 Kontoinhaber
     */
    private String paymentAccountName;

    /**
     * BT-86 BIC
     */
    private String paymentServiceProviderIdentifier;

    /**
     * Field BT-84 IBAN
     * 
     * @return the paymentAccountIdentifier
     */
    public String getPaymentAccountIdentifier() {
        return paymentAccountIdentifier;
    }

    /**
     * Field BT-84 IBAN
     * 
     * @param paymentAccountIdentifier
     *            the paymentAccountIdentifier to set
     */
    public void setPaymentAccountIdentifier(final String paymentAccountIdentifier) {
        this.paymentAccountIdentifier = paymentAccountIdentifier;
    }

    /**
     * Field BT-85 Kontoinhaber
     * 
     * @return the paymentAccountName
     */
    public String getPaymentAccountName() {
        return paymentAccountName;
    }

    /**
     * Field BT-85 Kontoinhaber
     * 
     * @param paymentAccountName
     *            the paymentAccountName to set
     */
    public void setPaymentAccountName(final String paymentAccountName) {
        this.paymentAccountName = paymentAccountName;
    }

    /**
     * Field BT-86 BIC
     * 
     * @return the paymentServiceProviderIdentifier
     */
    public String getPaymentServiceProviderIdentifier() {
        return paymentServiceProviderIdentifier;
    }

    /**
     * Field BT-86 BIC
     * 
     * @param paymentServiceProviderIdentifier
     *            the paymentServiceProviderIdentifier to set
     */
    public void setPaymentServiceProviderIdentifier(final String paymentServiceProviderIdentifier) {
        this.paymentServiceProviderIdentifier = paymentServiceProviderIdentifier;
    }

}