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

package com.sebulli.fakturama.exception;

/**
 * 
 */
public class FakturamaException extends Exception {

    private static final long serialVersionUID = -2627104044455234017L;

    public FakturamaException() {
        super();
    }

    /**
     * @param string
     */
    public FakturamaException(final String string) {
        super(string);
    }

}
