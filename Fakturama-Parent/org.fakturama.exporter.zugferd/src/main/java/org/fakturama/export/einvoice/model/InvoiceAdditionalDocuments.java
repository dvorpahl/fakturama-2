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
 * BG-24 Rechnungsbegründende Unterlagen
 */
public class InvoiceAdditionalDocuments {

    /**
     * <strong>BT-122</strong> Kennung
     */
    private String supportingDocumentReference;

    /**
     * <strong>BT-123</strong> Beschreibung
     */
    private String supportingDocumentDescription;

    /**
     * <strong>BT-124</strong> Externer Dateiname oder Verweis
     */
    private String externalDocumentLocation;

    /**
     * <strong>BT-125</strong> Dokument (Base64)
     */
    private String attachedDocument;

    /**
     * <strong>BT-125-1</strong> Mimecode der Anlage
     */
    private String attachedDocumentMimeCode;

    /**
     * <strong>BT-125-2</strong> Dateiname der Anlage
     */
    private String attachedDocumentFilename;

    /**
     * Field <strong>BT-122</strong> Kennung
     * 
     * @return the supportingDocumentReference
     */
    public String getSupportingDocumentReference() {
        return supportingDocumentReference;
    }

    /**
     * Field <strong>BT-122</strong> Kennung
     * 
     * @param supportingDocumentReference
     *            the supportingDocumentReference to set
     */
    public void setSupportingDocumentReference(final String supportingDocumentReference) {
        this.supportingDocumentReference = supportingDocumentReference;
    }

    /**
     * Field <strong>BT-123</strong> Beschreibung
     * 
     * @return the supportingDocumentDescription
     */
    public String getSupportingDocumentDescription() {
        return supportingDocumentDescription;
    }

    /**
     * Field <strong>BT-123</strong> Beschreibung
     * 
     * @param supportingDocumentDescription
     *            the supportingDocumentDescription to set
     */
    public void setSupportingDocumentDescription(final String supportingDocumentDescription) {
        this.supportingDocumentDescription = supportingDocumentDescription;
    }

    /**
     * Field <strong>BT-124</strong> Externer Dateiname oder Verweis
     * 
     * @return the externalDocumentLocation
     */
    public String getExternalDocumentLocation() {
        return externalDocumentLocation;
    }

    /**
     * Field <strong>BT-124</strong> Externer Dateiname oder Verweis
     * 
     * @param externalDocumentLocation
     *            the externalDocumentLocation to set
     */
    public void setExternalDocumentLocation(final String externalDocumentLocation) {
        this.externalDocumentLocation = externalDocumentLocation;
    }

    /**
     * Field <strong>BT-125</strong> Dokument (Base64)
     * 
     * @return the attachedDocument
     */
    public String getAttachedDocument() {
        return attachedDocument;
    }

    /**
     * Field <strong>BT-125</strong> Dokument (Base64)
     * 
     * @param attachedDocument
     *            the attachedDocument to set
     */
    public void setAttachedDocument(final String attachedDocument) {
        this.attachedDocument = attachedDocument;
    }

    /**
     * Field <strong>BT-125-1</strong> Mimecode der Anlage
     * 
     * @return the attachedDocumentMimeCode
     */
    public String getAttachedDocumentMimeCode() {
        return attachedDocumentMimeCode;
    }

    /**
     * Field <strong>BT-125-1</strong> Mimecode der Anlage
     * 
     * @param attachedDocumentMimeCode
     *            the attachedDocumentMimeCode to set
     */
    public void setAttachedDocumentMimeCode(final String attachedDocumentMimeCode) {
        this.attachedDocumentMimeCode = attachedDocumentMimeCode;
    }

    /**
     * Field <strong>BT-125-2</strong> Dateiname der Anlage
     * 
     * @return the attachedDocumentFilename
     */
    public String getAttachedDocumentFilename() {
        return attachedDocumentFilename;
    }

    /**
     * Field <strong>BT-125-2</strong> Dateiname der Anlage
     * 
     * @param attachedDocumentFilename
     *            the attachedDocumentFilename to set
     */
    public void setAttachedDocumentFilename(final String attachedDocumentFilename) {
        this.attachedDocumentFilename = attachedDocumentFilename;
    }

}
