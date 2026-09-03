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

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.browser.Browser;

import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.preferences.FakturamaPreferenceStoreProvider;

/**
 * JS-to-Java bridge function backing {@code FKT.getEnvironmentInfo()} (see {@link FktBridge}).
 * Returns a JSON object (as a string, parsed on the JS side) with what Fakturama knows about
 * itself and its client environment: app version/name, the configured company/owner data
 * (there is no login/user-account concept in Fakturama - this is the closest analog to
 * "who this installation belongs to"), OS user name, OS/Java/locale, and the workspace path.
 *
 * <p>Deliberately excluded: any database connection info (host, port, database name, user,
 * password) - that is not client-environment info a web page should ever see.</p>
 */
public class FktGetEnvironmentInfoFunction extends AbstractFktBrowserFunction {

    public static final String JS_NAME = "__fkt_getEnvironmentInfo";

    public FktGetEnvironmentInfoFunction(final Browser browser, final ILogger log) {
        super(browser, JS_NAME, log);
    }

    @Override
    protected Object doFunction(final Object[] arguments) {
        IPreferenceStore preferences = FakturamaPreferenceStoreProvider.getInstance().getPreferenceStore();

        Map<String, String> info = new LinkedHashMap<>();
        info.put("version", Platform.getProduct().getDefiningBundle().getVersion().toString());
        info.put("productName", Platform.getProduct().getName());

        info.put("companyName", pref(preferences, Constants.PREFERENCES_YOURCOMPANY_NAME));
        info.put("companyOwner", pref(preferences, Constants.PREFERENCES_YOURCOMPANY_OWNER));
        info.put("companyStreet", pref(preferences, Constants.PREFERENCES_YOURCOMPANY_STREET));
        info.put("companyZip", pref(preferences, Constants.PREFERENCES_YOURCOMPANY_ZIP));
        info.put("companyCity", pref(preferences, Constants.PREFERENCES_YOURCOMPANY_CITY));
        info.put("companyCountry", pref(preferences, Constants.PREFERENCES_YOURCOMPANY_COUNTRY));
        info.put("companyEmail", pref(preferences, Constants.PREFERENCES_YOURCOMPANY_EMAIL));
        info.put("companyWebsite", pref(preferences, Constants.PREFERENCES_YOURCOMPANY_WEBSITE));

        info.put("osUserName", System.getProperty("user.name", "(unknown)"));
        info.put("os", Platform.getOS());
        info.put("osVersion", System.getProperty("os.version", ""));
        info.put("osArch", Platform.getOSArch());
        info.put("javaVersion", System.getProperty("java.version", ""));
        info.put("locale", Locale.getDefault().toLanguageTag());

        info.put("workspace", pref(preferences, Constants.GENERAL_WORKSPACE));

        return FktJsonUtil.toJsonObject(info);
    }

    private static String pref(final IPreferenceStore preferences, final String key) {
        return preferences != null ? preferences.getString(key) : "";
    }
}
