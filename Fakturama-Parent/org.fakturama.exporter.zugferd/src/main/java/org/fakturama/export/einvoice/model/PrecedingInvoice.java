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
 * BG-3 Vorausgegangene Rechnungen
 */
public class PrecedingInvoice {

    /**
     * BT-25 vorausgegangene Rechnungsnummer
     */
    private String precedingInvoiceReference;

    /**
     * BT-26 Rechnungsdatum
     */
    private LocalDate precedingInvoiceIssueDate;

    public PrecedingInvoice() {
        //nothing for now
    }

    /**
     * @return the precedingInvoiceReference
     */
    public String getPrecedingInvoiceReference() {
        return precedingInvoiceReference;
    }

    /**
     * @param precedingInvoiceReference
     *            the precedingInvoiceReference to set
     */
    public void setPrecedingInvoiceReference(final String precedingInvoiceReference) {
        this.precedingInvoiceReference = precedingInvoiceReference;
    }

    /**
     * @return the precedingInvoiceIssueDate
     */
    public LocalDate getPrecedingInvoiceIssueDate() {
        return precedingInvoiceIssueDate;
    }

    /**
     * @param precedingInvoiceIssueDate
     *            the precedingInvoiceIssueDate to set
     */
    public void setPrecedingInvoiceIssueDate(final LocalDate precedingInvoiceIssueDate) {
        this.precedingInvoiceIssueDate = precedingInvoiceIssueDate;
    }

}
