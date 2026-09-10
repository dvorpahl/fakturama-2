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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.browser.Browser;

import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.preferences.FakturamaPreferenceStoreProvider;
import com.sun.jna.platform.win32.Secur32;
import com.sun.jna.platform.win32.Secur32Util;

/**
 * JS-to-Java bridge function backing {@code FKT.getEnvironmentInfo()} (see {@link FktBridge}).
 * Returns a JSON object (as a string, parsed on the JS side) with what Fakturama knows about
 * itself and its client environment: app version/name, the configured company/owner data
 * (there is no login/user-account concept in Fakturama - this is the closest analog to
 * "who this installation belongs to"), a greeting name (the account's first name where the OS
 * has one on file, otherwise the plain OS user name - see {@link #resolveGreetingName()}),
 * OS/Java/locale, and the workspace path.
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

        info.put("osUserName", resolveGreetingName());
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

    /**
     * Best-effort personalization name for the "Hallo {name}"-greeting fakturama-tool shows
     * after a login via FKT.getAuthToken() (see auth.py's {@code display_name}) - not an actual
     * login/account name, so any failure here is never fatal, it just falls back to the plain OS
     * user name ("danilo") instead of a nicer first name ("Danilo"). Only Windows and Linux are
     * attempted; other platforms (and every failure/empty-result case on those two) fall back
     * the same way.
     */
    private static String resolveGreetingName() {
        String osUserName = System.getProperty("user.name", "(unknown)");
        String os = Platform.getOS();
        String fullName;
        if (Platform.OS_WIN32.equals(os)) {
            fullName = resolveWindowsFullName();
        } else if (Platform.OS_LINUX.equals(os)) {
            fullName = resolveLinuxFullName(osUserName);
        } else {
            fullName = null;
        }
        if (fullName == null || fullName.isBlank()) {
            return osUserName;
        }
        String firstName = fullName.trim().split("\\s+", 2)[0];
        return firstName.isBlank() ? osUserName : firstName;
    }

    /**
     * The account's configured "full name"/display name, via the same Win32 API Windows itself
     * uses to show it (Secur32's GetUserNameEx, NameDisplay format) - returns null (never
     * throws) whenever that field simply isn't set, which is the normal case for a plain local
     * (non-domain, non-Microsoft-account) Windows user.
     */
    private static String resolveWindowsFullName() {
        try {
            return Secur32Util.getUserNameEx(Secur32.EXTENDED_NAME_FORMAT.NameDisplay);
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * The account's GECOS full-name field (passwd(5): name:password:uid:gid:GECOS:home:shell,
     * GECOS itself conventionally "Full Name,Room,WorkPhone,HomePhone") via getent, which also
     * covers LDAP/sssd-backed accounts, not just /etc/passwd. Returns null (never throws)
     * whenever that field isn't set - common on single-user desktop installs - or getent isn't
     * present, or takes too long to answer.
     */
    private static String resolveLinuxFullName(final String osUserName) {
        Process process = null;
        try {
            process = new ProcessBuilder("getent", "passwd", osUserName).redirectErrorStream(true).start();
            if (!process.waitFor(500, TimeUnit.MILLISECONDS)) {
                return null;
            }
            String line;
            try (BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                line = reader.readLine();
            }
            if (line == null) {
                return null;
            }
            String[] fields = line.split(":", -1);
            if (fields.length < 5) {
                return null;
            }
            String gecos = fields[4].split(",", 2)[0].trim();
            return gecos.isBlank() ? null : gecos;
        } catch (IOException e) {
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            if (process != null) {
                process.destroyForcibly();
            }
        }
    }

    private static String pref(final IPreferenceStore preferences, final String key) {
        return preferences != null ? preferences.getString(key) : "";
    }
}
