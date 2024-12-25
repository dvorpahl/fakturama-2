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
 * BG-7: Informationen zum Verkäufer // BG-8: Kontaktdaten des Verkäufers
 */
public class InvoiceBuyer {

    /**
     * BT-44 Firmenname
     */
    private String buyerName;

    /**
     * BT-45 Abweichender Handelsname
     */
    private String buyerTradingName;

    /**
     * BT-46 Kennung (Kreditorennumme / Lieferantennummer)
     */
    private String buyerIdentifier;

    /**
     * BT-46-1 Schema für Kennung
     */
    private String buyerIdentifierSchemeIdentifier;

    /**
     * BT-47 Handelsregisternummer / Eindeutige Kennung
     */
    private String buyerLegalRegistrationIdentifier;

    /**
     * BT-47-1 Schema / Typ der Kennung
     */
    private String buyerLegalRegistrationIdentifierSchemeIdentifier;

    /**
     * BT-48 Umsatzsteuer-ID
     */
    private String buyerVatIdentifier;

    /**
     * BT-49 Erreichbarkeit des Verkäufers (EMail)
     */
    private String buyerElectronicAddress;

    /**
     * BT-49-1 Typ der Adresse
     */
    private String buyerElectronicAddressSchemeIdentifier;

    /**
     * insert BG-8
     */
    private final AddressData buyerAddress = new AddressData();

    /****
     * BG-9 Kontaktdaten des Verkäufers
     ****/

    /**
     * BT-56 Name, Kontaktperson
     */
    private String buyerContactPoint;

    /**
     * BT-57 Telefonnummer
     */
    private String buyerContactTelephoneNumber;

    /**
     * BT-58 E-Mail
     */
    private String buyerContactEmailAddress;

    public InvoiceBuyer() {
        // Nothing for now
    }

    /**
     * @return the buyerName
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * @param buyerName
     *            the buyerName to set
     */
    public void setBuyerName(final String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * @return the buyerTradingName
     */
    public String getBuyerTradingName() {
        return buyerTradingName;
    }

    /**
     * @param buyerTradingName
     *            the buyerTradingName to set
     */
    public void setBuyerTradingName(final String buyerTradingName) {
        this.buyerTradingName = buyerTradingName;
    }

    /**
     * @return the buyerIdentifier
     */
    public String getBuyerIdentifier() {
        return buyerIdentifier;
    }

    /**
     * @param buyerIdentifier
     *            the buyerIdentifier to set
     */
    public void setBuyerIdentifier(final String buyerIdentifier) {
        this.buyerIdentifier = buyerIdentifier;
    }

    /**
     * @return the buyerIdentifierSchemeIdentifier
     */
    public String getBuyerIdentifierSchemeIdentifier() {
        return buyerIdentifierSchemeIdentifier;
    }

    /**
     * @param buyerIdentifierSchemeIdentifier
     *            the buyerIdentifierSchemeIdentifier to set
     */
    public void setBuyerIdentifierSchemeIdentifier(final String buyerIdentifierSchemeIdentifier) {
        this.buyerIdentifierSchemeIdentifier = buyerIdentifierSchemeIdentifier;
    }

    /**
     * @return the buyerLegalRegistrationIdentifier
     */
    public String getBuyerLegalRegistrationIdentifier() {
        return buyerLegalRegistrationIdentifier;
    }

    /**
     * @param buyerLegalRegistrationIdentifier
     *            the buyerLegalRegistrationIdentifier to set
     */
    public void setBuyerLegalRegistrationIdentifier(final String buyerLegalRegistrationIdentifier) {
        this.buyerLegalRegistrationIdentifier = buyerLegalRegistrationIdentifier;
    }

    /**
     * @return the buyerLegalRegistrationIdentifierSchemeIdentifier
     */
    public String getBuyerLegalRegistrationIdentifierSchemeIdentifier() {
        return buyerLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * @param buyerLegalRegistrationIdentifierSchemeIdentifier
     *            the buyerLegalRegistrationIdentifierSchemeIdentifier to set
     */
    public void setBuyerLegalRegistrationIdentifierSchemeIdentifier(final String buyerLegalRegistrationIdentifierSchemeIdentifier) {
        this.buyerLegalRegistrationIdentifierSchemeIdentifier = buyerLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * @return the buyerVatIdentifier
     */
    public String getBuyerVatIdentifier() {
        return buyerVatIdentifier;
    }

    /**
     * @param buyerVatIdentifier
     *            the buyerVatIdentifier to set
     */
    public void setBuyerVatIdentifier(final String buyerVatIdentifier) {
        this.buyerVatIdentifier = buyerVatIdentifier;
    }

    /**
     * @return the buyerElectronicAddress
     */
    public String getBuyerElectronicAddress() {
        return buyerElectronicAddress;
    }

    /**
     * @param buyerElectronicAddress
     *            the buyerElectronicAddress to set
     */
    public void setBuyerElectronicAddress(final String buyerElectronicAddress) {
        this.buyerElectronicAddress = buyerElectronicAddress;
    }

    /**
     * @return the buyerElectronicAddressSchemeIdentifier
     */
    public String getBuyerElectronicAddressSchemeIdentifier() {
        return buyerElectronicAddressSchemeIdentifier;
    }

    /**
     * @param buyerElectronicAddressSchemeIdentifier
     *            the buyerElectronicAddressSchemeIdentifier to set
     */
    public void setBuyerElectronicAddressSchemeIdentifier(final String buyerElectronicAddressSchemeIdentifier) {
        this.buyerElectronicAddressSchemeIdentifier = buyerElectronicAddressSchemeIdentifier;
    }

    /**
     * @return the buyerContactPoint
     */
    public String getBuyerContactPoint() {
        return buyerContactPoint;
    }

    /**
     * @param buyerContactPoint
     *            the buyerContactPoint to set
     */
    public void setBuyerContactPoint(final String buyerContactPoint) {
        this.buyerContactPoint = buyerContactPoint;
    }

    /**
     * @return the buyerContactTelephoneNumber
     */
    public String getBuyerContactTelephoneNumber() {
        return buyerContactTelephoneNumber;
    }

    /**
     * @param buyerContactTelephoneNumber
     *            the buyerContactTelephoneNumber to set
     */
    public void setBuyerContactTelephoneNumber(final String buyerContactTelephoneNumber) {
        this.buyerContactTelephoneNumber = buyerContactTelephoneNumber;
    }

    /**
     * @return the buyerContactEmailAddress
     */
    public String getBuyerContactEmailAddress() {
        return buyerContactEmailAddress;
    }

    /**
     * @param buyerContactEmailAddress
     *            the buyerContactEmailAddress to set
     */
    public void setBuyerContactEmailAddress(final String buyerContactEmailAddress) {
        this.buyerContactEmailAddress = buyerContactEmailAddress;
    }

    /**
     * @return the buyerAddress
     */
    public AddressData getBuyerAddress() {
        return buyerAddress;
    }

}
