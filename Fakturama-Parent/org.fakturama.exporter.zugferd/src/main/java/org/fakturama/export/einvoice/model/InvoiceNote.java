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
 * BG-1 Bemerkungen zur Rechnung
 */
public class InvoiceNote {

    /**
     * BT-21 Betreff
     */
    private String invoiceNoteSubjectCode;

    /**
     * BT-22 Bemerkungen (ACHTUNG SCHEMA)
     */
    private String invoiceNote;

    /**
     * Field BT-21 Betreff
     * 
     * @return the invoiceNoteSubjectCode
     */
    public String getInvoiceNoteSubjectCode() {
        return invoiceNoteSubjectCode;
    }

    /**
     * Field BT-21 Betreff
     * 
     * @param invoiceNoteSubjectCode
     *            the invoiceNoteSubjectCode to set
     */
    public void setInvoiceNoteSubjectCode(final String invoiceNoteSubjectCode) {
        this.invoiceNoteSubjectCode = invoiceNoteSubjectCode;
    }

    /**
     * Field BT-22 Bemerkungen (ACHTUNG SCHEMA)
     * 
     * @return the invoiceNote
     */
    public String getInvoiceNote() {
        return invoiceNote;
    }

    /**
     * Field BT-22 Bemerkungen (ACHTUNG SCHEMA)
     * 
     * @param invoiceNote
     *            the invoiceNote to set
     */
    public void setInvoiceNote(final String invoiceNote) {
        this.invoiceNote = invoiceNote;
    }

}
