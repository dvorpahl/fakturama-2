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
    private String deliverToParty_name;

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
    private final AddressData deliveryAddress = new AddressData();

    /**
     * @return the deliverToParty_name
     */
    public String getDeliverToParty_name() {
        return deliverToParty_name;
    }

    /**
     * @param deliverToParty_name
     *            the deliverToParty_name to set
     */
    public void setDeliverToParty_name(final String deliverToParty_name) {
        this.deliverToParty_name = deliverToParty_name;
    }

    /**
     * @return the deliverToLocationIdentifier
     */
    public String getDeliverToLocationIdentifier() {
        return deliverToLocationIdentifier;
    }

    /**
     * @param deliverToLocationIdentifier
     *            the deliverToLocationIdentifier to set
     */
    public void setDeliverToLocationIdentifier(final String deliverToLocationIdentifier) {
        this.deliverToLocationIdentifier = deliverToLocationIdentifier;
    }

    /**
     * @return the deliverToLocationIdentifierSchemeIdentifier
     */
    public String getDeliverToLocationIdentifierSchemeIdentifier() {
        return deliverToLocationIdentifierSchemeIdentifier;
    }

    /**
     * @param deliverToLocationIdentifierSchemeIdentifier
     *            the deliverToLocationIdentifierSchemeIdentifier to set
     */
    public void setDeliverToLocationIdentifierSchemeIdentifier(final String deliverToLocationIdentifierSchemeIdentifier) {
        this.deliverToLocationIdentifierSchemeIdentifier = deliverToLocationIdentifierSchemeIdentifier;
    }

    /**
     * @return the actualDeliveryDate
     */
    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    /**
     * @param actualDeliveryDate
     *            the actualDeliveryDate to set
     */
    public void setActualDeliveryDate(final LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    /**
     * @return the deliveryAddress
     */
    public AddressData getDeliveryAddress() {
        return deliveryAddress;
    }

}
