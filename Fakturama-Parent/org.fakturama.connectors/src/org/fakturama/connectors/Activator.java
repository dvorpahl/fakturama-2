/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2021 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package org.fakturama.connectors;

import org.fakturama.connectors.mail.FakturamaDCHFactory;
import org.fakturama.connectors.mail.MailServiceDefaultPreferencesInitializer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import jakarta.activation.DataContentHandlerFactory;
import jakarta.activation.DataHandler;

/**
 *
 */
public class Activator implements BundleActivator {

    // The bundle ID (Bundle-SymbolicName)
    public static final String PLUGIN_ID = "org.fakturama.connectors";

    // The shared instance
    private BundleContext context;

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public BundleContext getContext() {
        return this.context;
    }

    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        this.context = bundleContext;
        DataContentHandlerFactory dchFactory = new FakturamaDCHFactory();
        DataHandler.setDataContentHandlerFactory(dchFactory);

        MailServiceDefaultPreferencesInitializer mailServiceDefaultPreferencesInitializer = new MailServiceDefaultPreferencesInitializer();
        mailServiceDefaultPreferencesInitializer.initializeDefaultPreferences();
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        this.context = null;

    }
}
