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

package org.fakturama.logging;

import org.slf4j.LoggerFactory;

/**
 * 
 */
public class Activator {

    static {
        final ClassLoader logbackClassLoader = null;
        // Set the context class loader temporarily
        final Thread currentThread = Thread.currentThread();
        final ClassLoader originalClassLoader = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(logbackClassLoader);
        try {
            LoggerFactory.getLogger(Activator.class);
        } finally {
            // Restore the original class loader
            currentThread.setContextClassLoader(originalClassLoader);
        }
    }

}
