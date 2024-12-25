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
 * BG-16: Zahlungsdaten // BG-18 Kreditkarteninformationen // BG-19 Lastschrift
 */
public class InvoicePayment {

    /**
     * BG-17 Überweisungen
     */
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
         * @return the paymentAccountIdentifier
         */
        public String getPaymentAccountIdentifier() {
            return paymentAccountIdentifier;
        }

        /**
         * @param paymentAccountIdentifier
         *            the paymentAccountIdentifier to set
         */
        public void setPaymentAccountIdentifier(final String paymentAccountIdentifier) {
            this.paymentAccountIdentifier = paymentAccountIdentifier;
        }

        /**
         * @return the paymentAccountName
         */
        public String getPaymentAccountName() {
            return paymentAccountName;
        }

        /**
         * @param paymentAccountName
         *            the paymentAccountName to set
         */
        public void setPaymentAccountName(final String paymentAccountName) {
            this.paymentAccountName = paymentAccountName;
        }

        /**
         * @return the paymentServiceProviderIdentifier
         */
        public String getPaymentServiceProviderIdentifier() {
            return paymentServiceProviderIdentifier;
        }

        /**
         * @param paymentServiceProviderIdentifier
         *            the paymentServiceProviderIdentifier to set
         */
        public void setPaymentServiceProviderIdentifier(final String paymentServiceProviderIdentifier) {
            this.paymentServiceProviderIdentifier = paymentServiceProviderIdentifier;
        }

    }

    /**
     * BT-81 Zahlungsmittel (Code)
     */
    private String paymentMeansTypeCode;

    /**
     * BT-82 Zahlungsmittel
     */
    private String paymentMeansText;

    /**
     * BT-83 Verwendungszweck
     */
    private String remittanceInformation;

    /************************************************
     * BG-17 Bezahlung Kreditkarte
     ************************************************/
    private List<InvoiceCreditTransfer> invoiceCreditTransfers = new ArrayList<>();

    /************************************************
     * BG-18 Bezahlung Kreditkarte
     ************************************************/

    /**
     * BT-87 Kreditkartennummer
     */
    private String paymentCardPrimaryAccountNumber;

    /**
     * BT-88 Kreditkarteninhaber
     */
    private String paymentCardHolderName;

    /************************************************
     * BG-19 SEPA-Lastschrift
     ************************************************/

    /**
     * BT-89 Mandatsreferenz
     */
    private String mandateReferenceIdentifier;

    /**
     * BT-90 Gläubiger-ID
     */
    private String bankAssignedCreditorIdentifier;
    /**
     * BT-91 IBAN
     */
    private String debitedAccountIdentifier;

    /**
     * @return the paymentMeansTypeCode
     */
    public String getPaymentMeansTypeCode() {
        return paymentMeansTypeCode;
    }

    /**
     * @param paymentMeansTypeCode
     *            the paymentMeansTypeCode to set
     */
    public void setPaymentMeansTypeCode(final String paymentMeansTypeCode) {
        this.paymentMeansTypeCode = paymentMeansTypeCode;
    }

    /**
     * @return the paymentMeansText
     */
    public String getPaymentMeansText() {
        return paymentMeansText;
    }

    /**
     * @param paymentMeansText
     *            the paymentMeansText to set
     */
    public void setPaymentMeansText(final String paymentMeansText) {
        this.paymentMeansText = paymentMeansText;
    }

    /**
     * @return the remittanceInformation
     */
    public String getRemittanceInformation() {
        return remittanceInformation;
    }

    /**
     * @param remittanceInformation
     *            the remittanceInformation to set
     */
    public void setRemittanceInformation(final String remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
    }

    /**
     * @return the invoiceCreditTransfers
     */
    public List<InvoiceCreditTransfer> getInvoiceCreditTransfers() {
        return invoiceCreditTransfers;
    }

    /**
     * @param invoiceCreditTransfers
     *            the invoiceCreditTransfers to set
     */
    public void setInvoiceCreditTransfers(final List<InvoiceCreditTransfer> invoiceCreditTransfers) {
        this.invoiceCreditTransfers = invoiceCreditTransfers;
    }

    /**
     * @return the paymentCardPrimaryAccountNumber
     */
    public String getPaymentCardPrimaryAccountNumber() {
        return paymentCardPrimaryAccountNumber;
    }

    /**
     * @param paymentCardPrimaryAccountNumber
     *            the paymentCardPrimaryAccountNumber to set
     */
    public void setPaymentCardPrimaryAccountNumber(final String paymentCardPrimaryAccountNumber) {
        this.paymentCardPrimaryAccountNumber = paymentCardPrimaryAccountNumber;
    }

    /**
     * @return the paymentCardHolderName
     */
    public String getPaymentCardHolderName() {
        return paymentCardHolderName;
    }

    /**
     * @param paymentCardHolderName
     *            the paymentCardHolderName to set
     */
    public void setPaymentCardHolderName(final String paymentCardHolderName) {
        this.paymentCardHolderName = paymentCardHolderName;
    }

    /**
     * @return the mandateReferenceIdentifier
     */
    public String getMandateReferenceIdentifier() {
        return mandateReferenceIdentifier;
    }

    /**
     * @param mandateReferenceIdentifier
     *            the mandateReferenceIdentifier to set
     */
    public void setMandateReferenceIdentifier(final String mandateReferenceIdentifier) {
        this.mandateReferenceIdentifier = mandateReferenceIdentifier;
    }

    /**
     * @return the bankAssignedCreditorIdentifier
     */
    public String getBankAssignedCreditorIdentifier() {
        return bankAssignedCreditorIdentifier;
    }

    /**
     * @param bankAssignedCreditorIdentifier
     *            the bankAssignedCreditorIdentifier to set
     */
    public void setBankAssignedCreditorIdentifier(final String bankAssignedCreditorIdentifier) {
        this.bankAssignedCreditorIdentifier = bankAssignedCreditorIdentifier;
    }

    /**
     * @return the debitedAccountIdentifier
     */
    public String getDebitedAccountIdentifier() {
        return debitedAccountIdentifier;
    }

    /**
     * @param debitedAccountIdentifier
     *            the debitedAccountIdentifier to set
     */
    public void setDebitedAccountIdentifier(final String debitedAccountIdentifier) {
        this.debitedAccountIdentifier = debitedAccountIdentifier;
    }

}