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
 * The Fakturama Team - initial API and implementation
 */

package org.fakturama.export.einvoice.model;

import java.time.LocalDate;

/**
 * BG-26 Rechnungszeiträume für Positionen
 */
public class InvoiceLinePeriod {

    /**
     * BT-134 Startdatum Abrechnung
     */
    private LocalDate invoiceLinePeriodStartDate;

    /**
     * BT-135 Enddatum Abrechnung
     */
    private LocalDate invoiceLinePeriodEndDate;

    /**
     * BT-134 Startdatum Abrechnung
     * 
     * @return the invoiceLinePeriodStartDate
     */
    public LocalDate getInvoiceLinePeriodStartDate() {
        return invoiceLinePeriodStartDate;
    }

    /**
     * BT-134 Startdatum Abrechnung
     * 
     * @param invoiceLinePeriodStartDate
     *            the invoiceLinePeriodStartDate to set
     */
    public void setInvoiceLinePeriodStartDate(final LocalDate invoiceLinePeriodStartDate) {
        this.invoiceLinePeriodStartDate = invoiceLinePeriodStartDate;
    }

    /**
     * BT-135 Enddatum Abrechnung
     * 
     * @return the invoiceLinePeriodEndDate
     */
    public LocalDate getInvoiceLinePeriodEndDate() {
        return invoiceLinePeriodEndDate;
    }

    /**
     * BT-135 Enddatum Abrechnung
     * 
     * @param invoiceLinePeriodEndDate
     *            the invoiceLinePeriodEndDate to set
     */
    public void setInvoiceLinePeriodEndDate(final LocalDate invoiceLinePeriodEndDate) {
        this.invoiceLinePeriodEndDate = invoiceLinePeriodEndDate;
    }

}