/*
 * Fakturama - Free Invoicing Software - http://fakturama.sebulli.com
 *
 * Copyright (C) 2012 Gerd Bartelt
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Gerd Bartelt - initial API and implementation
 */

package com.sebulli.fakturama.parts;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.browser.Browser;

import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.preferences.FakturamaPreferenceStoreProvider;

/**
 * JS-to-Java bridge backing {@code FKT.getAuthToken()} (see {@link FktBridge}): hands the
 * trusted web app (see {@link AbstractFktBrowserFunction} for what "trusted" means here) the
 * shared secret configured in {@code Constants.PREFERENCES_BROWSER_FKT_SHARED_SECRET}, so it can
 * log itself in (matching secret -> its own session cookie) without prompting the user for a
 * password every time it's opened from inside Fakturama.
 *
 * <p>Returns an empty string if no secret is configured - the web app must treat that the same
 * as "not logged in" and fall back to its normal password form.</p>
 */
public class FktGetAuthTokenFunction extends AbstractFktBrowserFunction {

    public static final String JS_NAME = "__fkt_getAuthToken";

    public FktGetAuthTokenFunction(final Browser browser, final ILogger log) {
        super(browser, JS_NAME, log);
    }

    @Override
    protected Object doFunction(final Object[] arguments) {
        IPreferenceStore preferences = FakturamaPreferenceStoreProvider.getInstance().getPreferenceStore();
        String secret = preferences != null ? preferences.getString(Constants.PREFERENCES_BROWSER_FKT_SHARED_SECRET) : "";
        debug(secret.isEmpty() ? "no shared secret configured - returning empty string" : "returning configured shared secret (" + secret.length() + " chars)");
        return secret;
    }
}
