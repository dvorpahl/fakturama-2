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

import java.util.Arrays;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

import com.sebulli.fakturama.log.ILogger;

/**
 * Shared base for every {@code FKT.*} bridge function (see {@link FktBridge}): gates the actual
 * work behind an origin check so arbitrary web pages can't call into Fakturama just by being
 * loaded in the {@link Browser} widget.
 *
 * <p>Trust rule: Fakturama's own bundled local content ({@code file://}, e.g. the workspace
 * start page/templates created by {@code TemplateResourceManager}) is always trusted. An
 * {@code http(s)} page is only trusted if its origin (scheme + host + port) matches the
 * configured app URL ({@code Constants.PREFERENCES_GENERAL_WEBBROWSER_URL}) - if that preference
 * is blank, no remote page gets {@code FKT.*} access at all.</p>
 *
 * <p>This is a same-desktop-user convenience boundary (Fakturama has no login/sandboxing
 * concept), not a hardened security sandbox - see the caveats noted in
 * {@code 2.2.1-log+planed.txt}.</p>
 */
abstract class AbstractFktBrowserFunction extends BrowserFunction {

    private final String jsName;
    private final ILogger log;

    protected AbstractFktBrowserFunction(final Browser browser, final String name, final ILogger log) {
        super(browser, name);
        this.jsName = name;
        this.log = log;
    }

    @Override
    public final Object function(final Object[] arguments) {
        if (!FktOriginTrust.isTrusted(getBrowser())) {
            debug("rejected " + jsName + Arrays.toString(arguments) + " - untrusted origin (url=" + safeUrl() + ")");
            return Boolean.FALSE;
        }
        return doFunction(arguments);
    }

    /**
     * The actual bridge logic, only reached once {@link FktOriginTrust#isTrusted(Browser)} passed.
     */
    protected abstract Object doFunction(Object[] arguments);

    /**
     * Logs a line prefixed with this function's JS name, so a rejected/failed call is traceable
     * in the log instead of a bare {@code false} on the JS side with no explanation.
     *
     * <p>Deliberately {@link ILogger#warn}, not {@code debug}: {@link ILogger} goes through the
     * OSGi {@code LogService}, whose own default root {@code LoggerContext} level is {@code WARN}
     * - {@code DEBUG}/{@code INFO} calls are dropped there before ever reaching the Logback config
     * (which has its own, independent level per logger/appender), no matter how that's set.</p>
     */
    protected final void debug(final String message) {
        if (log != null) {
            log.warn("FKT bridge: " + jsName + " - " + message);
        }
    }

    private String safeUrl() {
        try {
            return getBrowser().getUrl();
        } catch (RuntimeException e) {
            return "<unknown>";
        }
    }
}
