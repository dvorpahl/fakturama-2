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
     * BT-122 Kennung
     */
    private String supportingDocumentReference;

    /**
     * BT-123 Beschreibung
     */
    private String supportingDocumentDescription;

    /**
     * BT-124 Externer Dateiname oder Verweis
     */
    private String externalDocumentLocation;

    /**
     * BT-125 Dokument (Base64)
     */
    private String attachedDocument;

    /**
     * BT-125-1 Mimecode der Anlage
     */
    private String attachedDocumentMimeCode;

    /**
     * BT-125-2 Dateiname der Anlage
     */
    private String attachedDocumentFilename;

    /**
     * @return the supportingDocumentReference
     */
    public String getSupportingDocumentReference() {
        return supportingDocumentReference;
    }

    /**
     * @param supportingDocumentReference
     *            the supportingDocumentReference to set
     */
    public void setSupportingDocumentReference(final String supportingDocumentReference) {
        this.supportingDocumentReference = supportingDocumentReference;
    }

    /**
     * @return the supportingDocumentDescription
     */
    public String getSupportingDocumentDescription() {
        return supportingDocumentDescription;
    }

    /**
     * @param supportingDocumentDescription
     *            the supportingDocumentDescription to set
     */
    public void setSupportingDocumentDescription(final String supportingDocumentDescription) {
        this.supportingDocumentDescription = supportingDocumentDescription;
    }

    /**
     * @return the externalDocumentLocation
     */
    public String getExternalDocumentLocation() {
        return externalDocumentLocation;
    }

    /**
     * @param externalDocumentLocation
     *            the externalDocumentLocation to set
     */
    public void setExternalDocumentLocation(final String externalDocumentLocation) {
        this.externalDocumentLocation = externalDocumentLocation;
    }

    /**
     * @return the attachedDocument
     */
    public String getAttachedDocument() {
        return attachedDocument;
    }

    /**
     * @param attachedDocument
     *            the attachedDocument to set
     */
    public void setAttachedDocument(final String attachedDocument) {
        this.attachedDocument = attachedDocument;
    }

    /**
     * @return the attachedDocumentMimeCode
     */
    public String getAttachedDocumentMimeCode() {
        return attachedDocumentMimeCode;
    }

    /**
     * @param attachedDocumentMimeCode
     *            the attachedDocumentMimeCode to set
     */
    public void setAttachedDocumentMimeCode(final String attachedDocumentMimeCode) {
        this.attachedDocumentMimeCode = attachedDocumentMimeCode;
    }

    /**
     * @return the attachedDocumentFilename
     */
    public String getAttachedDocumentFilename() {
        return attachedDocumentFilename;
    }

    /**
     * @param attachedDocumentFilename
     *            the attachedDocumentFilename to set
     */
    public void setAttachedDocumentFilename(final String attachedDocumentFilename) {
        this.attachedDocumentFilename = attachedDocumentFilename;
    }

}
