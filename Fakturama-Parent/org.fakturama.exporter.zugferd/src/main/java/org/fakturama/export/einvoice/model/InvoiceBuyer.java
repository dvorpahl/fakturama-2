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
    private AddressData buyerAddress = new AddressData();

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
     * Field BT-44 Firmenname
     * 
     * @return the buyerName
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * Field BT-44 Firmenname
     * 
     * @param buyerName
     *            the buyerName to set
     */
    public void setBuyerName(final String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * Field BT-45 Abweichender Handelsname
     * 
     * @return the buyerTradingName
     */
    public String getBuyerTradingName() {
        return buyerTradingName;
    }

    /**
     * Field BT-45 Abweichender Handelsname
     * 
     * @param buyerTradingName
     *            the buyerTradingName to set
     */
    public void setBuyerTradingName(final String buyerTradingName) {
        this.buyerTradingName = buyerTradingName;
    }

    /**
     * Field BT-46 Kennung (Kreditorennumme / Lieferantennummer)
     * 
     * @return the buyerIdentifier
     */
    public String getBuyerIdentifier() {
        return buyerIdentifier;
    }

    /**
     * Field BT-46 Kennung (Kreditorennumme / Lieferantennummer)
     * 
     * @param buyerIdentifier
     *            the buyerIdentifier to set
     */
    public void setBuyerIdentifier(final String buyerIdentifier) {
        this.buyerIdentifier = buyerIdentifier;
    }

    /**
     * Field BT-46-1 Schema für Kennung
     * 
     * @return the buyerIdentifierSchemeIdentifier
     */
    public String getBuyerIdentifierSchemeIdentifier() {
        return buyerIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-46-1 Schema für Kennung
     * 
     * @param buyerIdentifierSchemeIdentifier
     *            the buyerIdentifierSchemeIdentifier to set
     */
    public void setBuyerIdentifierSchemeIdentifier(final String buyerIdentifierSchemeIdentifier) {
        this.buyerIdentifierSchemeIdentifier = buyerIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-47 Handelsregisternummer / Eindeutige Kennung
     * 
     * @return the buyerLegalRegistrationIdentifier
     */
    public String getBuyerLegalRegistrationIdentifier() {
        return buyerLegalRegistrationIdentifier;
    }

    /**
     * Field BT-47 Handelsregisternummer / Eindeutige Kennung
     * 
     * @param buyerLegalRegistrationIdentifier
     *            the buyerLegalRegistrationIdentifier to set
     */
    public void setBuyerLegalRegistrationIdentifier(final String buyerLegalRegistrationIdentifier) {
        this.buyerLegalRegistrationIdentifier = buyerLegalRegistrationIdentifier;
    }

    /**
     * Field BT-47-1 Schema / Typ der Kennung
     * 
     * @return the buyerLegalRegistrationIdentifierSchemeIdentifier
     */
    public String getBuyerLegalRegistrationIdentifierSchemeIdentifier() {
        return buyerLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-47-1 Schema / Typ der Kennung
     * 
     * @param buyerLegalRegistrationIdentifierSchemeIdentifier
     *            the buyerLegalRegistrationIdentifierSchemeIdentifier to set
     */
    public void setBuyerLegalRegistrationIdentifierSchemeIdentifier(final String buyerLegalRegistrationIdentifierSchemeIdentifier) {
        this.buyerLegalRegistrationIdentifierSchemeIdentifier = buyerLegalRegistrationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-48 Umsatzsteuer-ID
     * 
     * @return the buyerVatIdentifier
     */
    public String getBuyerVatIdentifier() {
        return buyerVatIdentifier;
    }

    /**
     * Field BT-48 Umsatzsteuer-ID
     * 
     * @param buyerVatIdentifier
     *            the buyerVatIdentifier to set
     */
    public void setBuyerVatIdentifier(final String buyerVatIdentifier) {
        this.buyerVatIdentifier = buyerVatIdentifier;
    }

    /**
     * Field BT-49 Erreichbarkeit des Verkäufers (EMail)
     * 
     * @return the buyerElectronicAddress
     */
    public String getBuyerElectronicAddress() {
        return buyerElectronicAddress;
    }

    /**
     * Field BT-49 Erreichbarkeit des Verkäufers (EMail)
     * 
     * @param buyerElectronicAddress
     *            the buyerElectronicAddress to set
     */
    public void setBuyerElectronicAddress(final String buyerElectronicAddress) {
        this.buyerElectronicAddress = buyerElectronicAddress;
    }

    /**
     * Field BT-49-1 Typ der Adresse
     * 
     * @return the buyerElectronicAddressSchemeIdentifier
     */
    public String getBuyerElectronicAddressSchemeIdentifier() {
        return buyerElectronicAddressSchemeIdentifier;
    }

    /**
     * Field BT-49-1 Typ der Adresse
     * 
     * @param buyerElectronicAddressSchemeIdentifier
     *            the buyerElectronicAddressSchemeIdentifier to set
     */
    public void setBuyerElectronicAddressSchemeIdentifier(final String buyerElectronicAddressSchemeIdentifier) {
        this.buyerElectronicAddressSchemeIdentifier = buyerElectronicAddressSchemeIdentifier;
    }

    /**
     * Field BT-56 Name, Kontaktperson
     * 
     * @return the buyerContactPoint
     */
    public String getBuyerContactPoint() {
        return buyerContactPoint;
    }

    /**
     * Field BT-56 Name, Kontaktperson
     * 
     * @param buyerContactPoint
     *            the buyerContactPoint to set
     */
    public void setBuyerContactPoint(final String buyerContactPoint) {
        this.buyerContactPoint = buyerContactPoint;
    }

    /**
     * Field BT-57 Telefonnummer
     * 
     * @return the buyerContactTelephoneNumber
     */
    public String getBuyerContactTelephoneNumber() {
        return buyerContactTelephoneNumber;
    }

    /**
     * Field BT-57 Telefonnummer
     * 
     * @param buyerContactTelephoneNumber
     *            the buyerContactTelephoneNumber to set
     */
    public void setBuyerContactTelephoneNumber(final String buyerContactTelephoneNumber) {
        this.buyerContactTelephoneNumber = buyerContactTelephoneNumber;
    }

    /**
     * Field BT-58 E-Mail
     * 
     * @return the buyerContactEmailAddress
     */
    public String getBuyerContactEmailAddress() {
        return buyerContactEmailAddress;
    }

    /**
     * Field BT-58 E-Mail
     * 
     * @param buyerContactEmailAddress
     *            the buyerContactEmailAddress to set
     */
    public void setBuyerContactEmailAddress(final String buyerContactEmailAddress) {
        this.buyerContactEmailAddress = buyerContactEmailAddress;
    }

    /**
     * Field insert BG-8
     * 
     * @return the buyerAddress
     */
    public AddressData getBuyerAddress() {
        return buyerAddress;
    }

    /**
     * Field insert BG-8
     * 
     * @param buyerAddress
     *            the buyerAddress to set
     */
    public void setBuyerAddress(final AddressData buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

}
