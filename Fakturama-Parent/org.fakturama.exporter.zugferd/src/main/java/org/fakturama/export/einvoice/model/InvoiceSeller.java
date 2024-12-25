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

/**
 * BG-4: Informationen zum Verkäufer // BG-6: Kontaktdaten des Verkäufers //
 * BG-10: Abweichender Zahlungsempfänger
 */
public class InvoiceSeller {

    /**
     * BT-27 Firmenname
     */
    private String sellerName;

    /**
     * BT-28 Abweichender Handelsname
     */
    private String sellerTradingName;

    /**
     * BT-29 Kennung (Kreditorennumme / Lieferantennummer)
     */
    private String sellerIdentifier;

    /**
     * BT-29-1 Schema für Kennung
     */
    private String sellerIdentifierSchemeIdentifier;

    /**
     * BT-30 Handelsregisternummer / Eindeutige Kennung
     */
    private String sellerLegalRegistrationIdentifier;

    /**
     * BT-30-1 Schema / Typ der Kennung
     */
    private String sellerLegalRegistrationIdentifierSchemeIdentifier;

    /**
     * BT-31 Umsatzsteuer-ID
     */
    private String sellerVatIdentifier;

    /**
     * BT-32 Steuernummer
     */
    private String sellerTaxRegistrationIdentifier;

    /**
     * BT-32-1 Schema zur Steuernummer
     */
    private String sellerTaxRegistrationIdentifierSchemeIdentifier;

    /**
     * BT-33 zusätzliche rechtliche Informationen
     */
    private String sellerAdditionalLegalInformation;

    /**
     * BT-34 Erreichbarkeit des Verkäufers (EMail)
     */
    private String sellerElectronicAddress;

    /**
     * BT-34-1 Typ der Adresse
     */
    private String sellerElectronicAddressSchemeIdentifier;

    /**
     * insert BG-5
     */
    private final AddressData sellerAddress = new AddressData();

    /************************************************
     * BG-6 Kontaktdaten des Verkäufers
     ************************************************/

    /**
     * BT-41 Name, Kontaktperson
     */
    private String sellerContactPoint;

    /**
     * BT-42 Telefonnummer
     */
    private String sellerContactTelephoneNumber;

    /**
     * BT-43 E-Mail
     */
    private String sellerContactEmailAddress;

    /************************************************
     * BG-10 Abweichender Zahlungsempfänger
     ************************************************/

    /**
     * BT-59 Name
     */
    private String payeeName;

    /**
     * BT-60 Kennung
     */
    private String payeeIdentifier;

    /**
     * BT-60-1 Schema Kennung
     */
    private String payeeIdentifierSchemeIdentifier;

    /**
     * BT-61 Handelsregisternummer / Eindeutige Kennung
     */
    private String payeeLegalRegistrationIdentifier;

    /**
     * BT-61-1 Schema zur Kennung
     */
    private String payeeLegalRegistrationIdentifierSchemeIdentifier;

    private InvoiceSellerRepresentative invoiceSellerRepresentative = new InvoiceSellerRepresentative();

    public InvoiceSeller() {
        // Nothing for now
    }

    /**
     * @return the sellerName
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * @param sellerName
     *            the sellerName to set
     */
    public void setSellerName(final String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * @return the sellerTradingName
     */
    public String getSellerTradingName() {
        return sellerTradingName;
    }

    /**
     * @param sellerTradingName
     *            the sellerTradingName to set
     */
    public void setSellerTradingName(final String sellerTradingName) {
        this.sellerTradingName = sellerTradingName;
    }

    /**
     * @return the sellerIdentifier
     */
    public String getSellerIdentifier() {
        return sellerIdentifier;
    }

    /**
     * @param sellerIdentifier
     *            the sellerIdentifier to set
     */
    public void setSellerIdentifier(final String sellerIdentifier) {
        this.sellerIdentifier = sellerIdentifier;
    }

    /**
     * @return the sellerIdentifierSchemeIdentifier
     */
    public String getSellerIdentifierSchemeIdentifier() {
        return sellerIdentifierSchemeIdentifier;
    }

    /**
     * @param sellerIdentifierSchemeIdentifier
     *            the sellerIdentifierSchemeIdentifier to set
     */
    public void setSellerIdentifierSchemeIdentifier(final String sellerIdentifierSchemeIdentifier) {
        this.sellerIdentifierSchemeIdentifier = sellerIdentifierSchemeIdentifier;
    }

    /**
     * @return the sellerLegalRegistrationIdentifier
     */
    public String getSellerLegalRegistrationIdentifier() {
        return sellerLegalRegistrationIdentifier;
    }

    /**
     * @param sellerLegalRegistrationIdentifier
     *            the sellerLegalRegistrationIdentifier to set
     */
    public void setSellerLegalRegistrationIdentifier(final String sellerLegalRegistrationIdentifier) {
        this.sellerLegalRegistrationIdentifier = sellerLegalRegistrationIdentifier;
    }

    /**
     * @return the sellerLegalRegistrationIdentifierSchemeIdentifier
     */
    public String getSellerLegalRegistrationIdentifierSchemeIdentifier() {
        return sellerLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * @param sellerLegalRegistrationIdentifierSchemeIdentifier
     *            the sellerLegalRegistrationIdentifierSchemeIdentifier to set
     */
    public void setSellerLegalRegistrationIdentifierSchemeIdentifier(final String sellerLegalRegistrationIdentifierSchemeIdentifier) {
        this.sellerLegalRegistrationIdentifierSchemeIdentifier = sellerLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * @return the sellerVatIdentifier
     */
    public String getSellerVatIdentifier() {
        return sellerVatIdentifier;
    }

    /**
     * @param sellerVatIdentifier
     *            the sellerVatIdentifier to set
     */
    public void setSellerVatIdentifier(final String sellerVatIdentifier) {
        this.sellerVatIdentifier = sellerVatIdentifier;
    }

    /**
     * @return the sellerTaxRegistrationIdentifier
     */
    public String getSellerTaxRegistrationIdentifier() {
        return sellerTaxRegistrationIdentifier;
    }

    /**
     * @param sellerTaxRegistrationIdentifier
     *            the sellerTaxRegistrationIdentifier to set
     */
    public void setSellerTaxRegistrationIdentifier(final String sellerTaxRegistrationIdentifier) {
        this.sellerTaxRegistrationIdentifier = sellerTaxRegistrationIdentifier;
    }

    /**
     * @return the sellerTaxRegistrationIdentifierSchemeIdentifier
     */
    public String getSellerTaxRegistrationIdentifierSchemeIdentifier() {
        return sellerTaxRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * @param sellerTaxRegistrationIdentifierSchemeIdentifier
     *            the sellerTaxRegistrationIdentifierSchemeIdentifier to set
     */
    public void setSellerTaxRegistrationIdentifierSchemeIdentifier(final String sellerTaxRegistrationIdentifierSchemeIdentifier) {
        this.sellerTaxRegistrationIdentifierSchemeIdentifier = sellerTaxRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * @return the sellerAdditionalLegalInformation
     */
    public String getSellerAdditionalLegalInformation() {
        return sellerAdditionalLegalInformation;
    }

    /**
     * @param sellerAdditionalLegalInformation
     *            the sellerAdditionalLegalInformation to set
     */
    public void setSellerAdditionalLegalInformation(final String sellerAdditionalLegalInformation) {
        this.sellerAdditionalLegalInformation = sellerAdditionalLegalInformation;
    }

    /**
     * @return the sellerElectronicAddress
     */
    public String getSellerElectronicAddress() {
        return sellerElectronicAddress;
    }

    /**
     * @param sellerElectronicAddress
     *            the sellerElectronicAddress to set
     */
    public void setSellerElectronicAddress(final String sellerElectronicAddress) {
        this.sellerElectronicAddress = sellerElectronicAddress;
    }

    /**
     * @return the sellerElectronicAddressSchemeIdentifier
     */
    public String getSellerElectronicAddressSchemeIdentifier() {
        return sellerElectronicAddressSchemeIdentifier;
    }

    /**
     * @param sellerElectronicAddressSchemeIdentifier
     *            the sellerElectronicAddressSchemeIdentifier to set
     */
    public void setSellerElectronicAddressSchemeIdentifier(final String sellerElectronicAddressSchemeIdentifier) {
        this.sellerElectronicAddressSchemeIdentifier = sellerElectronicAddressSchemeIdentifier;
    }

    /**
     * @return the sellerAddress
     */
    public AddressData getSellerAddress() {
        return sellerAddress;
    }

    /**
     * @return the sellerContactPoint
     */
    public String getSellerContactPoint() {
        return sellerContactPoint;
    }

    /**
     * @param sellerContactPoint
     *            the sellerContactPoint to set
     */
    public void setSellerContactPoint(final String sellerContactPoint) {
        this.sellerContactPoint = sellerContactPoint;
    }

    /**
     * @return the sellerContactTelephoneNumber
     */
    public String getSellerContactTelephoneNumber() {
        return sellerContactTelephoneNumber;
    }

    /**
     * @param sellerContactTelephoneNumber
     *            the sellerContactTelephoneNumber to set
     */
    public void setSellerContactTelephoneNumber(final String sellerContactTelephoneNumber) {
        this.sellerContactTelephoneNumber = sellerContactTelephoneNumber;
    }

    /**
     * @return the sellerContactEmailAddress
     */
    public String getSellerContactEmailAddress() {
        return sellerContactEmailAddress;
    }

    /**
     * @param sellerContactEmailAddress
     *            the sellerContactEmailAddress to set
     */
    public void setSellerContactEmailAddress(final String sellerContactEmailAddress) {
        this.sellerContactEmailAddress = sellerContactEmailAddress;
    }

    /**
     * @return the payeeName
     */
    public String getPayeeName() {
        return payeeName;
    }

    /**
     * @param payeeName
     *            the payeeName to set
     */
    public void setPayeeName(final String payeeName) {
        this.payeeName = payeeName;
    }

    /**
     * @return the payeeIdentifier
     */
    public String getPayeeIdentifier() {
        return payeeIdentifier;
    }

    /**
     * @param payeeIdentifier
     *            the payeeIdentifier to set
     */
    public void setPayeeIdentifier(final String payeeIdentifier) {
        this.payeeIdentifier = payeeIdentifier;
    }

    /**
     * @return the payeeIdentifierSchemeIdentifier
     */
    public String getPayeeIdentifierSchemeIdentifier() {
        return payeeIdentifierSchemeIdentifier;
    }

    /**
     * @param payeeIdentifierSchemeIdentifier
     *            the payeeIdentifierSchemeIdentifier to set
     */
    public void setPayeeIdentifierSchemeIdentifier(final String payeeIdentifierSchemeIdentifier) {
        this.payeeIdentifierSchemeIdentifier = payeeIdentifierSchemeIdentifier;
    }

    /**
     * @return the payeeLegalRegistrationIdentifier
     */
    public String getPayeeLegalRegistrationIdentifier() {
        return payeeLegalRegistrationIdentifier;
    }

    /**
     * @param payeeLegalRegistrationIdentifier
     *            the payeeLegalRegistrationIdentifier to set
     */
    public void setPayeeLegalRegistrationIdentifier(final String payeeLegalRegistrationIdentifier) {
        this.payeeLegalRegistrationIdentifier = payeeLegalRegistrationIdentifier;
    }

    /**
     * @return the payeeLegalRegistrationIdentifierSchemeIdentifier
     */
    public String getPayeeLegalRegistrationIdentifierSchemeIdentifier() {
        return payeeLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * @param payeeLegalRegistrationIdentifierSchemeIdentifier
     *            the payeeLegalRegistrationIdentifierSchemeIdentifier to set
     */
    public void setPayeeLegalRegistrationIdentifierSchemeIdentifier(final String payeeLegalRegistrationIdentifierSchemeIdentifier) {
        this.payeeLegalRegistrationIdentifierSchemeIdentifier = payeeLegalRegistrationIdentifierSchemeIdentifier;
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

}
