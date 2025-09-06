/* 
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2025 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     The Fakturama Team - initial API and implementation
 */
 
package org.fakturama.export.einvoice.model;

/**
 * BG-32 Metadaten zur Position
 */
public class InvoicePositionMetadata {

    /**
     * BT-160 Schlüssel zum Metadatum
     */
    private String itemAttributeName;

    /**
     * BT-161 Wert zum Metadatum
     */
    private String itemAttributeValue;

    /**
     * Field BT-160 Schlüssel zum Metadatum
     * 
     * @return the itemAttributeName
     */
    public String getItemAttributeName() {
        return itemAttributeName;
    }

    /**
     * Field BT-160 Schlüssel zum Metadatum
     * 
     * @param itemAttributeName
     *            the itemAttributeName to set
     */
    public void setItemAttributeName(final String itemAttributeName) {
        this.itemAttributeName = itemAttributeName;
    }

    /**
     * Field BT-161 Wert zum Metadatum
     * 
     * @return the itemAttributeValue
     */
    public String getItemAttributeValue() {
        return itemAttributeValue;
    }

    /**
     * Field BT-161 Wert zum Metadatum
     * 
     * @param itemAttributeValue
     *            the itemAttributeValue to set
     */
    public void setItemAttributeValue(final String itemAttributeValue) {
        this.itemAttributeValue = itemAttributeValue;
    }

}