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

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

import com.sebulli.fakturama.log.ILogger;

/**
 * JS-to-Java bridge backing {@code FKT.identify(userName, hostName)} (see {@link FktBridge}):
 * the counterpart to {@code FKT.getEnvironmentInfo()} - instead of Fakturama telling the page
 * about itself, the page tells Fakturama who/where it is. Logged, and shown as the tooltip of
 * the browser editor's part so it's visible right in the application window (not just the log).
 *
 * <p>Deliberately does <b>not</b> extend {@link AbstractFktBrowserFunction}: unlike the other
 * {@code FKT.*} functions, this one exposes/changes nothing sensitive (just a log line and a
 * tooltip), and gating it behind the origin check would defeat its own purpose - it's the one
 * function meant to work even from a not-yet-trusted page, e.g. while diagnosing why other
 * {@code FKT.*} calls are being rejected.</p>
 */
public class FktIdentifyFunction extends BrowserFunction {

    public static final String JS_NAME = "__fkt_identify";

    private final MPart part;
    private final ILogger log;

    public FktIdentifyFunction(final Browser browser, final MPart part, final ILogger log) {
        super(browser, JS_NAME);
        this.part = part;
        this.log = log;
    }

    @Override
    public Object function(final Object[] arguments) {
        String userName = argToString(arguments, 0);
        String hostName = argToString(arguments, 1);
        if (userName == null && hostName == null) {
            return Boolean.FALSE;
        }

        String identity = (userName != null ? userName : "?") + "@" + (hostName != null ? hostName : "?");

        if (log != null) {
            log.info("FKT.identify: %s hat sich aus der WebView gemeldet.", identity);
        }
        if (part != null) {
            part.setTooltip("Verbundene Gegenstelle: " + identity);
        }
        return Boolean.TRUE;
    }

    private static String argToString(final Object[] arguments, final int index) {
        if (arguments.length <= index || !(arguments[index] instanceof String)) {
            return null;
        }
        return (String) arguments[index];
    }
}
