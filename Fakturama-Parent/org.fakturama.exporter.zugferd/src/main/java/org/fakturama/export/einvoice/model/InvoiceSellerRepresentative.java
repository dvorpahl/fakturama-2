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
 * BG-11 Steuervertreter des Verkäufers
 */
public class InvoiceSellerRepresentative {

    /**
     * BT-62 Name Steuervertreter
     */
    private String sellerTaxRepresentativeName;

    /**
     * BT-63 Umsatzsteuer-ID Steuervertreter
     */
    private String sellerTaxRepresentativeVatIdentifier;

    /**
     * BG-12 Adresse des Steuervertreters
     */
    private AddressData sellerTaxRepresentativeAddress = new AddressData();

    /**
     * Field BT-62 Name Steuervertreter
     * 
     * @return the sellerTaxRepresentativeName
     */
    public String getSellerTaxRepresentativeName() {
        return sellerTaxRepresentativeName;
    }

    /**
     * Field BT-62 Name Steuervertreter
     * 
     * @param sellerTaxRepresentativeName
     *            the sellerTaxRepresentativeName to set
     */
    public void setSellerTaxRepresentativeName(final String sellerTaxRepresentativeName) {
        this.sellerTaxRepresentativeName = sellerTaxRepresentativeName;
    }

    /**
     * Field BT-63 Umsatzsteuer-ID Steuervertreter
     * 
     * @return the sellerTaxRepresentativeVatIdentifier
     */
    public String getSellerTaxRepresentativeVatIdentifier() {
        return sellerTaxRepresentativeVatIdentifier;
    }

    /**
     * Field BT-63 Umsatzsteuer-ID Steuervertreter
     * 
     * @param sellerTaxRepresentativeVatIdentifier
     *            the sellerTaxRepresentativeVatIdentifier to set
     */
    public void setSellerTaxRepresentativeVatIdentifier(final String sellerTaxRepresentativeVatIdentifier) {
        this.sellerTaxRepresentativeVatIdentifier = sellerTaxRepresentativeVatIdentifier;
    }

    /**
     * Field BG-12 Adresse des Steuervertreters
     * 
     * @return the sellerTaxRepresentativeAddress
     */
    public AddressData getSellerTaxRepresentativeAddress() {
        return sellerTaxRepresentativeAddress;
    }

    /**
     * Field BG-12 Adresse des Steuervertreters
     * 
     * @param sellerTaxRepresentativeAddress
     *            the sellerTaxRepresentativeAddress to set
     */
    public void setSellerTaxRepresentativeAddress(final AddressData sellerTaxRepresentativeAddress) {
        this.sellerTaxRepresentativeAddress = sellerTaxRepresentativeAddress;
    }
}
