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
    private AddressData sellerAddress = new AddressData();

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

    /**
     * insert BG-11 Steuervertreter des Verkäufers
     */
    private InvoiceSellerRepresentative invoiceSellerRepresentative = new InvoiceSellerRepresentative();

    public InvoiceSeller() {
        // Nothing for now
    }

    /**
     * 
     * Field BT-27 Firmenname
     * 
     * @return the sellerName
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * Field BT-27 Firmenname
     * 
     * @param sellerName
     *            the sellerName to set
     */
    public void setSellerName(final String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * Field BT-28 Abweichender Handelsname
     * 
     * @return the sellerTradingName
     */
    public String getSellerTradingName() {
        return sellerTradingName;
    }

    /**
     * Field BT-28 Abweichender Handelsname
     * 
     * @param sellerTradingName
     *            the sellerTradingName to set
     */
    public void setSellerTradingName(final String sellerTradingName) {
        this.sellerTradingName = sellerTradingName;
    }

    /**
     * Field BT-29 Kennung (Kreditorennumme / Lieferantennummer)
     * 
     * @return the sellerIdentifier
     */
    public String getSellerIdentifier() {
        return sellerIdentifier;
    }

    /**
     * Field BT-29 Kennung (Kreditorennumme / Lieferantennummer)
     * 
     * @param sellerIdentifier
     *            the sellerIdentifier to set
     */
    public void setSellerIdentifier(final String sellerIdentifier) {
        this.sellerIdentifier = sellerIdentifier;
    }

    /**
     * Field BT-29-1 Schema für Kennung
     * 
     * @return the sellerIdentifierSchemeIdentifier
     */
    public String getSellerIdentifierSchemeIdentifier() {
        return sellerIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-29-1 Schema für Kennung
     * 
     * @param sellerIdentifierSchemeIdentifier
     *            the sellerIdentifierSchemeIdentifier to set
     */
    public void setSellerIdentifierSchemeIdentifier(final String sellerIdentifierSchemeIdentifier) {
        this.sellerIdentifierSchemeIdentifier = sellerIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-30 Handelsregisternummer / Eindeutige Kennung
     * 
     * @return the sellerLegalRegistrationIdentifier
     */
    public String getSellerLegalRegistrationIdentifier() {
        return sellerLegalRegistrationIdentifier;
    }

    /**
     * Field BT-30 Handelsregisternummer / Eindeutige Kennung
     * 
     * @param sellerLegalRegistrationIdentifier
     *            the sellerLegalRegistrationIdentifier to set
     */
    public void setSellerLegalRegistrationIdentifier(final String sellerLegalRegistrationIdentifier) {
        this.sellerLegalRegistrationIdentifier = sellerLegalRegistrationIdentifier;
    }

    /**
     * Field BT-30-1 Schema / Typ der Kennung
     * 
     * @return the sellerLegalRegistrationIdentifierSchemeIdentifier
     */
    public String getSellerLegalRegistrationIdentifierSchemeIdentifier() {
        return sellerLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-30-1 Schema / Typ der Kennung
     * 
     * @param sellerLegalRegistrationIdentifierSchemeIdentifier
     *            the sellerLegalRegistrationIdentifierSchemeIdentifier to set
     */
    public void setSellerLegalRegistrationIdentifierSchemeIdentifier(final String sellerLegalRegistrationIdentifierSchemeIdentifier) {
        this.sellerLegalRegistrationIdentifierSchemeIdentifier = sellerLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-31 Umsatzsteuer-ID
     * 
     * @return the sellerVatIdentifier
     */
    public String getSellerVatIdentifier() {
        return sellerVatIdentifier;
    }

    /**
     * Field BT-31 Umsatzsteuer-ID
     * 
     * @param sellerVatIdentifier
     *            the sellerVatIdentifier to set
     */
    public void setSellerVatIdentifier(final String sellerVatIdentifier) {
        this.sellerVatIdentifier = sellerVatIdentifier;
    }

    /**
     * Field BT-32 Steuernummer
     * 
     * @return the sellerTaxRegistrationIdentifier
     */
    public String getSellerTaxRegistrationIdentifier() {
        return sellerTaxRegistrationIdentifier;
    }

    /**
     * Field BT-32 Steuernummer
     * 
     * @param sellerTaxRegistrationIdentifier
     *            the sellerTaxRegistrationIdentifier to set
     */
    public void setSellerTaxRegistrationIdentifier(final String sellerTaxRegistrationIdentifier) {
        this.sellerTaxRegistrationIdentifier = sellerTaxRegistrationIdentifier;
    }

    /**
     * Field BT-32-1 Schema zur Steuernummer
     * 
     * @return the sellerTaxRegistrationIdentifierSchemeIdentifier
     */
    public String getSellerTaxRegistrationIdentifierSchemeIdentifier() {
        return sellerTaxRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-32-1 Schema zur Steuernummer
     * 
     * @param sellerTaxRegistrationIdentifierSchemeIdentifier
     *            the sellerTaxRegistrationIdentifierSchemeIdentifier to set
     */
    public void setSellerTaxRegistrationIdentifierSchemeIdentifier(final String sellerTaxRegistrationIdentifierSchemeIdentifier) {
        this.sellerTaxRegistrationIdentifierSchemeIdentifier = sellerTaxRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-33 zusätzliche rechtliche Informationen
     * 
     * @return the sellerAdditionalLegalInformation
     */
    public String getSellerAdditionalLegalInformation() {
        return sellerAdditionalLegalInformation;
    }

    /**
     * Field BT-33 zusätzliche rechtliche Informationen
     * 
     * @param sellerAdditionalLegalInformation
     *            the sellerAdditionalLegalInformation to set
     */
    public void setSellerAdditionalLegalInformation(final String sellerAdditionalLegalInformation) {
        this.sellerAdditionalLegalInformation = sellerAdditionalLegalInformation;
    }

    /**
     * Field BT-34 Erreichbarkeit des Verkäufers (EMail)
     * 
     * @return the sellerElectronicAddress
     */
    public String getSellerElectronicAddress() {
        return sellerElectronicAddress;
    }

    /**
     * Field BT-34 Erreichbarkeit des Verkäufers (EMail)
     * 
     * @param sellerElectronicAddress
     *            the sellerElectronicAddress to set
     */
    public void setSellerElectronicAddress(final String sellerElectronicAddress) {
        this.sellerElectronicAddress = sellerElectronicAddress;
    }

    /**
     * Field BT-34-1 Typ der Adresse
     * 
     * @return the sellerElectronicAddressSchemeIdentifier
     */
    public String getSellerElectronicAddressSchemeIdentifier() {
        return sellerElectronicAddressSchemeIdentifier;
    }

    /**
     * Field BT-34-1 Typ der Adresse
     * 
     * @param sellerElectronicAddressSchemeIdentifier
     *            the sellerElectronicAddressSchemeIdentifier to set
     */
    public void setSellerElectronicAddressSchemeIdentifier(final String sellerElectronicAddressSchemeIdentifier) {
        this.sellerElectronicAddressSchemeIdentifier = sellerElectronicAddressSchemeIdentifier;
    }

    /**
     * Field insert BG-5
     * 
     * @return the sellerAddress
     */
    public AddressData getSellerAddress() {
        return sellerAddress;
    }

    /**
     * Field insert BG-5
     * 
     * @param sellerAddress
     *            the sellerAddress to set
     */
    public void setSellerAddress(final AddressData sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    /**
     * Field BT-41 Name, Kontaktperson
     * 
     * @return the sellerContactPoint
     */
    public String getSellerContactPoint() {
        return sellerContactPoint;
    }

    /**
     * Field BT-41 Name, Kontaktperson
     * 
     * @param sellerContactPoint
     *            the sellerContactPoint to set
     */
    public void setSellerContactPoint(final String sellerContactPoint) {
        this.sellerContactPoint = sellerContactPoint;
    }

    /**
     * Field BT-42 Telefonnummer
     * 
     * @return the sellerContactTelephoneNumber
     */
    public String getSellerContactTelephoneNumber() {
        return sellerContactTelephoneNumber;
    }

    /**
     * Field BT-42 Telefonnummer
     * 
     * @param sellerContactTelephoneNumber
     *            the sellerContactTelephoneNumber to set
     */
    public void setSellerContactTelephoneNumber(final String sellerContactTelephoneNumber) {
        this.sellerContactTelephoneNumber = sellerContactTelephoneNumber;
    }

    /**
     * Field BT-43 E-Mail
     * 
     * @return the sellerContactEmailAddress
     */
    public String getSellerContactEmailAddress() {
        return sellerContactEmailAddress;
    }

    /**
     * Field BT-43 E-Mail
     * 
     * @param sellerContactEmailAddress
     *            the sellerContactEmailAddress to set
     */
    public void setSellerContactEmailAddress(final String sellerContactEmailAddress) {
        this.sellerContactEmailAddress = sellerContactEmailAddress;
    }

    /**
     * Field BT-59 Name
     * 
     * @return the payeeName
     */
    public String getPayeeName() {
        return payeeName;
    }

    /**
     * Field BT-59 Name
     * 
     * @param payeeName
     *            the payeeName to set
     */
    public void setPayeeName(final String payeeName) {
        this.payeeName = payeeName;
    }

    /**
     * Field BT-60 Kennung
     * 
     * @return the payeeIdentifier
     */
    public String getPayeeIdentifier() {
        return payeeIdentifier;
    }

    /**
     * Field BT-60 Kennung
     * 
     * @param payeeIdentifier
     *            the payeeIdentifier to set
     */
    public void setPayeeIdentifier(final String payeeIdentifier) {
        this.payeeIdentifier = payeeIdentifier;
    }

    /**
     * Field BT-60-1 Schema Kennung
     * 
     * @return the payeeIdentifierSchemeIdentifier
     */
    public String getPayeeIdentifierSchemeIdentifier() {
        return payeeIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-60-1 Schema Kennung
     * 
     * @param payeeIdentifierSchemeIdentifier
     *            the payeeIdentifierSchemeIdentifier to set
     */
    public void setPayeeIdentifierSchemeIdentifier(final String payeeIdentifierSchemeIdentifier) {
        this.payeeIdentifierSchemeIdentifier = payeeIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-61 Handelsregisternummer / Eindeutige Kennung
     * 
     * @return the payeeLegalRegistrationIdentifier
     */
    public String getPayeeLegalRegistrationIdentifier() {
        return payeeLegalRegistrationIdentifier;
    }

    /**
     * Field BT-61 Handelsregisternummer / Eindeutige Kennung
     * 
     * @param payeeLegalRegistrationIdentifier
     *            the payeeLegalRegistrationIdentifier to set
     */
    public void setPayeeLegalRegistrationIdentifier(final String payeeLegalRegistrationIdentifier) {
        this.payeeLegalRegistrationIdentifier = payeeLegalRegistrationIdentifier;
    }

    /**
     * Field BT-61-1 Schema zur Kennung
     * 
     * @return the payeeLegalRegistrationIdentifierSchemeIdentifier
     */
    public String getPayeeLegalRegistrationIdentifierSchemeIdentifier() {
        return payeeLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-61-1 Schema zur Kennung
     * 
     * @param payeeLegalRegistrationIdentifierSchemeIdentifier
     *            the payeeLegalRegistrationIdentifierSchemeIdentifier to set
     */
    public void setPayeeLegalRegistrationIdentifierSchemeIdentifier(final String payeeLegalRegistrationIdentifierSchemeIdentifier) {
        this.payeeLegalRegistrationIdentifierSchemeIdentifier = payeeLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * Field insert BG-11 Steuervertreter des Verkäufers
     * 
     * @return the invoiceSellerRepresentative
     */
    public InvoiceSellerRepresentative getInvoiceSellerRepresentative() {
        return invoiceSellerRepresentative;
    }

    /**
     * Field insert BG-11 Steuervertreter des Verkäufers
     * 
     * @param invoiceSellerRepresentative
     *            the invoiceSellerRepresentative to set
     */
    public void setInvoiceSellerRepresentative(final InvoiceSellerRepresentative invoiceSellerRepresentative) {
        this.invoiceSellerRepresentative = invoiceSellerRepresentative;
    }

}
