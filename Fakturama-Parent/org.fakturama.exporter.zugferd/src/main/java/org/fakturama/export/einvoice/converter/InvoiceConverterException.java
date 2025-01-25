/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2024 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * The Fakturama Team - initial API and implementation
 */

package org.fakturama.export.einvoice.converter;

/**
 * 
 */
public class InvoiceConverterException extends Exception {

    private static final long serialVersionUID = -4075873053072857789L;

    public InvoiceConverterException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public InvoiceConverterException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public InvoiceConverterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public InvoiceConverterException(final String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public InvoiceConverterException(final Throwable cause) {
        super(cause);
    }

}
