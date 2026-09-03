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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.browser.Browser;

import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.preferences.FakturamaPreferenceStoreProvider;

/**
 * Shared origin-trust decision for the {@code FKT.*} bridge (see {@link FktBridge},
 * {@link AbstractFktBrowserFunction}), plus a human-readable {@link #describe(Browser)} of that
 * same decision so it can be printed to the page's own console via
 * {@link FktBridge#injectNamespace(Browser)} - the only debugging channel available for pages we
 * don't control (no Java-side log access from there).
 */
final class FktOriginTrust {

    private FktOriginTrust() {
    }

    static boolean isTrusted(final Browser browser) {
        return decide(browser).trusted;
    }

    static String describe(final Browser browser) {
        Decision d = decide(browser);
        return "currentUrl=" + d.currentUrl + " currentOrigin=" + d.currentOrigin + " trustedUrl=" + d.trustedUrl
                + " trustedOrigin=" + d.trustedOrigin + " trusted=" + d.trusted
                + (d.reason != null ? " reason=" + d.reason : "");
    }

    private static Decision decide(final Browser browser) {
        String currentUrl = browser.getUrl();
        if (currentUrl == null) {
            return new Decision(null, null, null, null, false, "browser.getUrl() is null");
        }
        if (currentUrl.startsWith("file://")) {
            return new Decision(currentUrl, null, null, null, true, "file:// is always trusted");
        }
        if (!(currentUrl.startsWith("http://") || currentUrl.startsWith("https://"))) {
            return new Decision(currentUrl, null, null, null, false, "unsupported scheme");
        }

        String trustedUrl = FakturamaPreferenceStoreProvider.getInstance().getPreferenceStore()
                .getString(Constants.PREFERENCES_GENERAL_WEBBROWSER_URL);
        if (StringUtils.isBlank(trustedUrl)) {
            return new Decision(currentUrl, null, "", null, false, "GENERAL_WEBBROWSER_URL preference is blank");
        }

        String currentOrigin = originOf(currentUrl);
        String trustedOrigin = originOf(trustedUrl);
        boolean trusted = currentOrigin != null && currentOrigin.equalsIgnoreCase(trustedOrigin);
        return new Decision(currentUrl, currentOrigin, trustedUrl, trustedOrigin, trusted, null);
    }

    private static String originOf(final String url) {
        try {
            URL parsed = new URL(url);
            int port = parsed.getPort();
            return parsed.getProtocol() + "://" + parsed.getHost() + (port != -1 ? ":" + port : "");
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private static final class Decision {
        final String currentUrl;
        final String currentOrigin;
        final String trustedUrl;
        final String trustedOrigin;
        final boolean trusted;
        final String reason;

        Decision(final String currentUrl, final String currentOrigin, final String trustedUrl, final String trustedOrigin,
                final boolean trusted, final String reason) {
            this.currentUrl = currentUrl;
            this.currentOrigin = currentOrigin;
            this.trustedUrl = trustedUrl;
            this.trustedOrigin = trustedOrigin;
            this.trusted = trusted;
            this.reason = reason;
        }
    }
}
