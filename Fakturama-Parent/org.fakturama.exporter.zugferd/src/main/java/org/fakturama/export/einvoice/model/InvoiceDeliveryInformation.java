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

import java.time.LocalDate;

/**
 * BG-13 Lieferinformationen
 */
public class InvoiceDeliveryInformation {

    /**
     * BT-70 Name des Empfängers
     */
    private String deliverToPartyName;

    /**
     * BT-71 Lieferort
     */
    private String deliverToLocationIdentifier;

    /**
     * BT-71-1 Schema zum Lieferort
     */
    private String deliverToLocationIdentifierSchemeIdentifier;

    /**
     * BT-72 Lieferdatum
     */
    private LocalDate actualDeliveryDate;

    /**
     * BG-15: Lieferadresse
     */
    private AddressData deliveryAddress = new AddressData();

    /**
     * Field BT-70 Name des Empfängers
     * 
     * @return the deliverToParty_name
     */
    public String getDeliverToPartyName() {
        return deliverToPartyName;
    }

    /**
     * Field BT-70 Name des Empfängers
     * 
     * @param deliverToParty_name
     *            the deliverToParty_name to set
     */
    public void setDeliverToPartyName(final String deliverToParty_name) {
        this.deliverToPartyName = deliverToParty_name;
    }

    /**
     * Field BT-71 Lieferort
     * 
     * @return the deliverToLocationIdentifier
     */
    public String getDeliverToLocationIdentifier() {
        return deliverToLocationIdentifier;
    }

    /**
     * Field BT-71 Lieferort
     * 
     * @param deliverToLocationIdentifier
     *            the deliverToLocationIdentifier to set
     */
    public void setDeliverToLocationIdentifier(final String deliverToLocationIdentifier) {
        this.deliverToLocationIdentifier = deliverToLocationIdentifier;
    }

    /**
     * Field BT-71-1 Schema zum Lieferort
     * 
     * @return the deliverToLocationIdentifierSchemeIdentifier
     */
    public String getDeliverToLocationIdentifierSchemeIdentifier() {
        return deliverToLocationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-71-1 Schema zum Lieferort
     * 
     * @param deliverToLocationIdentifierSchemeIdentifier
     *            the deliverToLocationIdentifierSchemeIdentifier to set
     */
    public void setDeliverToLocationIdentifierSchemeIdentifier(final String deliverToLocationIdentifierSchemeIdentifier) {
        this.deliverToLocationIdentifierSchemeIdentifier = deliverToLocationIdentifierSchemeIdentifier;
    }

    /**
     * Field BT-72 Lieferdatum
     * 
     * @return the actualDeliveryDate
     */
    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    /**
     * Field BT-72 Lieferdatum
     * 
     * @param actualDeliveryDate
     *            the actualDeliveryDate to set
     */
    public void setActualDeliveryDate(final LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    /**
     * Field BG-15: Lieferadresse
     * 
     * @return the deliveryAddress
     */
    public AddressData getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Field BG-15: Lieferadresse
     * 
     * @param deliveryAddress
     *            the deliveryAddress to set
     */
    public void setDeliveryAddress(final AddressData deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

}
