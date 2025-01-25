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
 * BG-5, BG-8, BG-12, BG-15 Anschriften
 */
public class AddressData {

    /**
     * BT-35, BT-50, BT-64, BT-75 Adresszeile 1
     */
    private String addressLine1;

    /**
     * BT-36, BT-51, BT-65, BT-76 Adresszeile 2, Postfach
     */
    private String addressLine2;

    /**
     * BT-162, BT-163, BT-164, BT-165 Adresszeile 3, Adresszusatz
     */
    private String addressLine3;

    /**
     * BT-37, BT-52, BT-66, BT-77 Ort
     */
    private String city;

    /**
     * BT-38, BT-53, BT-67, BT-78 Postleitzahl
     */
    private String postCode;

    /**
     * BT-39, BT-54, BT-68, BT-79 Bundesland
     */
    private String countrySubdivision;

    /**
     * BT-40, BT-55, BT-69, BT-80 Land
     */
    private String countryCode;

    /**
     * 
     */
    public AddressData() {
        // nothing for now
    }

    /**
     * Field BT-35, BT-50, BT-64, BT-75 Adresszeile 1
     * 
     * @return the addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Field BT-35, BT-50, BT-64, BT-75 Adresszeile 1
     * 
     * @param addressLine1
     *            the addressLine1 to set
     */
    public void setAddressLine1(final String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * Field BT-36, BT-51, BT-65, BT-76 Adresszeile 2, Postfach
     * 
     * @return the addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Field BT-36, BT-51, BT-65, BT-76 Adresszeile 2, Postfach
     * 
     * @param addressLine2
     *            the addressLine2 to set
     */
    public void setAddressLine2(final String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * Field BT-162, BT-163, BT-164, BT-165 Adresszeile 3, Adresszusatz
     * 
     * @return the addressLine3
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * Field BT-162, BT-163, BT-164, BT-165 Adresszeile 3, Adresszusatz
     * 
     * @param addressLine3
     *            the addressLine3 to set
     */
    public void setAddressLine3(final String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    /**
     * Field BT-37, BT-52, BT-66, BT-77 Ort
     * 
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Field BT-37, BT-52, BT-66, BT-77 Ort
     * 
     * @param city
     *            the city to set
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * Field BT-38, BT-53, BT-67, BT-78 Postleitzahl
     * 
     * @return the postCode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Field BT-38, BT-53, BT-67, BT-78 Postleitzahl
     * 
     * @param postCode
     *            the postCode to set
     */
    public void setPostCode(final String postCode) {
        this.postCode = postCode;
    }

    /**
     * Field BT-39, BT-54, BT-68, BT-79 Bundesland
     * 
     * @return the countrySubdivision
     */
    public String getCountrySubdivision() {
        return countrySubdivision;
    }

    /**
     * Field BT-39, BT-54, BT-68, BT-79 Bundesland
     * 
     * @param countrySubdivision
     *            the countrySubdivision to set
     */
    public void setCountrySubdivision(final String countrySubdivision) {
        this.countrySubdivision = countrySubdivision;
    }

    /**
     * Field BT-40, BT-55, BT-69, BT-80 Land
     * 
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Field BT-40, BT-55, BT-69, BT-80 Land
     * 
     * @param countryCode
     *            the countryCode to set
     */
    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

}
