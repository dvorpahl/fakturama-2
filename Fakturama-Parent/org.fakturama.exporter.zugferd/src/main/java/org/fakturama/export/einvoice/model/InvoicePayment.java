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
 * BG-16: Zahlungsdaten // BG-17 // BG-18 Kreditkarteninformationen // BG-19
 * Lastschrift
 */
public class InvoicePayment {

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
    /**
     * BT-87 Kreditkartennummer
     */
    private String paymentCardPrimaryAccountNumber;
    /**
     * BT-88 Kreditkarteninhaber
     */
    private String paymentCardHolderName;
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

    /************************************************
     * BG-18 Bezahlung Kreditkarte
     ************************************************/

    /************************************************
     * BG-19 SEPA-Lastschrift
     ************************************************/

    /**
     * Field BT-81 Zahlungsmittel (Code)
     * 
     * @return the paymentMeansTypeCode
     */
    public String getPaymentMeansTypeCode() {
        return paymentMeansTypeCode;
    }

    /**
     * Field BT-81 Zahlungsmittel (Code)
     * 
     * @param paymentMeansTypeCode
     *            the paymentMeansTypeCode to set
     */
    public void setPaymentMeansTypeCode(final String paymentMeansTypeCode) {
        this.paymentMeansTypeCode = paymentMeansTypeCode;
    }

    /**
     * Field BT-82 Zahlungsmittel
     * 
     * @return the paymentMeansText
     */
    public String getPaymentMeansText() {
        return paymentMeansText;
    }

    /**
     * Field BT-82 Zahlungsmittel
     * 
     * @param paymentMeansText
     *            the paymentMeansText to set
     */
    public void setPaymentMeansText(final String paymentMeansText) {
        this.paymentMeansText = paymentMeansText;
    }

    /**
     * Field BT-83 Verwendungszweck
     * 
     * @return the remittanceInformation
     */
    public String getRemittanceInformation() {
        return remittanceInformation;
    }

    /**
     * Field BT-83 Verwendungszweck
     * 
     * @param remittanceInformation
     *            the remittanceInformation to set
     */
    public void setRemittanceInformation(final String remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
    }

    /**
     * insert BG-17 Bezahlung Kreditkarte
     * 
     * @return the invoiceCreditTransfers
     */
    public List<InvoiceCreditTransfer> getInvoiceCreditTransfers() {
        return invoiceCreditTransfers;
    }

    /**
     * insert BG-17 Bezahlung Kreditkarte
     * 
     * @param invoiceCreditTransfers
     *            the invoiceCreditTransfers to set
     */
    public void setInvoiceCreditTransfers(final List<InvoiceCreditTransfer> invoiceCreditTransfers) {
        this.invoiceCreditTransfers = invoiceCreditTransfers;
    }

    /**
     * Field BT-87 Kreditkartennummer
     * 
     * @return the paymentCardPrimaryAccountNumber
     */
    public String getPaymentCardPrimaryAccountNumber() {
        return paymentCardPrimaryAccountNumber;
    }

    /**
     * Field BT-87 Kreditkartennummer
     * 
     * @param paymentCardPrimaryAccountNumber
     *            the paymentCardPrimaryAccountNumber to set
     */
    public void setPaymentCardPrimaryAccountNumber(final String paymentCardPrimaryAccountNumber) {
        this.paymentCardPrimaryAccountNumber = paymentCardPrimaryAccountNumber;
    }

    /**
     * Field BT-88 Kreditkarteninhaber
     * 
     * @return the paymentCardHolderName
     */
    public String getPaymentCardHolderName() {
        return paymentCardHolderName;
    }

    /**
     * Field BT-88 Kreditkarteninhaber
     * 
     * @param paymentCardHolderName
     *            the paymentCardHolderName to set
     */
    public void setPaymentCardHolderName(final String paymentCardHolderName) {
        this.paymentCardHolderName = paymentCardHolderName;
    }

    /**
     * Field BT-89 Mandatsreferenz
     * 
     * @return the mandateReferenceIdentifier
     */
    public String getMandateReferenceIdentifier() {
        return mandateReferenceIdentifier;
    }

    /**
     * Field BT-89 Mandatsreferenz
     * 
     * @param mandateReferenceIdentifier
     *            the mandateReferenceIdentifier to set
     */
    public void setMandateReferenceIdentifier(final String mandateReferenceIdentifier) {
        this.mandateReferenceIdentifier = mandateReferenceIdentifier;
    }

    /**
     * Field BT-90 Gläubiger-ID
     * 
     * @return the bankAssignedCreditorIdentifier
     */
    public String getBankAssignedCreditorIdentifier() {
        return bankAssignedCreditorIdentifier;
    }

    /**
     * Field BT-90 Gläubiger-ID
     * 
     * @param bankAssignedCreditorIdentifier
     *            the bankAssignedCreditorIdentifier to set
     */
    public void setBankAssignedCreditorIdentifier(final String bankAssignedCreditorIdentifier) {
        this.bankAssignedCreditorIdentifier = bankAssignedCreditorIdentifier;
    }

    /**
     * Field BT-91 IBAN
     * 
     * @return the debitedAccountIdentifier
     */
    public String getDebitedAccountIdentifier() {
        return debitedAccountIdentifier;
    }

    /**
     * Field BT-91 IBAN
     * 
     * @param debitedAccountIdentifier
     *            the debitedAccountIdentifier to set
     */
    public void setDebitedAccountIdentifier(final String debitedAccountIdentifier) {
        this.debitedAccountIdentifier = debitedAccountIdentifier;
    }

}