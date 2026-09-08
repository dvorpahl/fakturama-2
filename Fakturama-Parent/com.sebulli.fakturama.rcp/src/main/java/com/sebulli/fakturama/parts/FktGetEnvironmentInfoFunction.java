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

import java.net.InetAddress;
import java.net.UnknownHostException;
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
        info.put("hostName", resolveHostName());
        info.put("os", Platform.getOS());
        info.put("osVersion", System.getProperty("os.version", ""));
        info.put("osArch", Platform.getOSArch());
        info.put("javaVersion", System.getProperty("java.version", ""));
        info.put("locale", Locale.getDefault().toLanguageTag());

        info.put("workspace", pref(preferences, Constants.GENERAL_WORKSPACE));

        return FktJsonUtil.toJsonObject(info);
    }

    /**
     * The machine's own network name, for the "welcher Client/Rechner" badge fakturama-tool
     * shows once a session logs in via FKT.getAuthToken()/{@code /login/fkt} (see
     * auth.py's {@code client_hostname}). Env vars checked first - they're instant and set by
     * the OS itself (COMPUTERNAME on Windows, HOSTNAME on most Linux shells), whereas
     * {@link InetAddress#getLocalHost()} can trigger a real (if usually fast) DNS/NSS lookup and
     * has historically been unreliable on machines with an unusual /etc/hosts or no reverse DNS
     * entry for their own address.
     */
    private static String resolveHostName() {
        String fromEnv = System.getenv("COMPUTERNAME");
        if (fromEnv == null || fromEnv.isBlank()) {
            fromEnv = System.getenv("HOSTNAME");
        }
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "(unknown)";
        }
    }

    private static String pref(final IPreferenceStore preferences, final String key) {
        return preferences != null ? preferences.getString(key) : "";
    }
}
