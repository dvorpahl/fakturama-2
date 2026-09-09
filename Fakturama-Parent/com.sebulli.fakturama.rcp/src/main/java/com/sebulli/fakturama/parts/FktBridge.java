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

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.browser.Browser;

import com.sebulli.fakturama.log.ILogger;

/**
 * Installs the {@code window.FKT} JS namespace on an embedded {@link Browser} (currently only
 * used by {@link BrowserEditor}): the single entry point through which a web page loaded there
 * can ask Fakturama things or make it navigate.
 *
 * <p>SWT's {@link org.eclipse.swt.browser.BrowserFunction} only registers plain global JS
 * functions, not a namespaced object, so this registers each bridge function under an internal
 * {@code __fkt_*} name and wraps them into {@code window.FKT} via an injected script. The
 * registered {@code BrowserFunction}s themselves survive page navigation (SWT re-attaches them
 * to each new document), but the plain JS object {@code window.FKT} does not - so
 * {@link #injectNamespace(Browser)} must be called again after every page load (e.g. from a
 * {@link org.eclipse.swt.browser.ProgressListener#completed}).</p>
 *
 * <p>Available today:</p>
 * <ul>
 * <li>{@code FKT.version} / {@code FKT.getVersion()} - the running app version.</li>
 * <li>{@code FKT.getEnvironmentInfo()} - version, company/owner data, OS/Java/locale, workspace
 * path. Deliberately never includes database connection info.</li>
 * <li>{@code FKT.openProduct(itemNumber)} / {@code FKT.openProductById(id)}</li>
 * <li>{@code FKT.openDocument(name)} / {@code FKT.openDocumentById(id)}</li>
 * <li>{@code FKT.updatedObject(id, dtype)} - tells Fakturama an object was changed externally,
 * so any open list view for that {@code dtype} ("product"/"document") refreshes.</li>
 * <li>{@code FKT.identify(userName, hostName)} - the page introduces itself to Fakturama; shown
 * as the browser part's tooltip and logged.</li>
 * <li>{@code FKT.getAuthToken()} - the shared secret the trusted web app can use to log itself
 * in without a password (see {@link FktGetAuthTokenFunction}).</li>
 * </ul>
 *
 * <p>Every function above except {@code identify} only works for a trusted origin - see
 * {@link AbstractFktBrowserFunction} - so an arbitrary web page loaded in this browser (e.g. via
 * the URL bar) can't call into Fakturama just by being displayed.</p>
 */
public class FktBridge {

    private final String version;
    private final ILogger log;

    public FktBridge(final Browser browser, final IEclipseContext ctx, final MPart part, final ILogger log) {
        // Registering these attaches them to the Browser; SWT keeps them available across
        // page navigations, so this only needs to run once per Browser instance.
        new FktGetEnvironmentInfoFunction(browser, log);
        new FktOpenProductFunction(browser, ctx, log);
        new FktOpenProductByIdFunction(browser, ctx, log);
        new FktOpenDocumentFunction(browser, ctx, log);
        new FktOpenDocumentByIdFunction(browser, ctx, log);
        new FktUpdatedObjectFunction(browser, ctx, log);
        new FktIdentifyFunction(browser, part, log);
        new FktGetAuthTokenFunction(browser, log);

        this.version = Platform.getProduct().getDefiningBundle().getVersion().toString();
        this.log = log;
    }

    /**
     * (Re-)installs {@code window.FKT} in the current page.
     *
     * <p>Call this as early as possible during a page load (e.g. from
     * {@link org.eclipse.swt.browser.ProgressListener#changed}), not only once from
     * {@code completed} - {@code completed} only fires once the whole page (including every
     * subresource) has finished loading, which for most pages is well after their own inline
     * scripts have already run. A page that checks {@code window.FKT} synchronously at parse time
     * will always see it as {@code undefined} if this is only ever called from {@code completed}.
     * Repeated calls are safe/idempotent.</p>
     *
     * <p>Also dispatches a {@code window.FKTReady} event and sets {@code window.FKT.ready = true}
     * on every (re-)install, so pages can listen for readiness instead of racing a one-shot check -
     * see {@code FKT-Debug} console output on the page itself for what the page observed.</p>
     *
     * <p>Self-delaying: if SWT hasn't (re-)attached its native BrowserFunctions to the current
     * document yet (a separate, independently-timed step from the {@code changed}/{@code completed}
     * events that normally trigger this call - see {@link #nativeFunctionsAreAttached(Browser)}),
     * this reschedules itself on a short timer instead of installing wrappers around functions
     * that don't exist yet. Callers never need to retry this themselves.</p>
     */
    /** Poll interval while waiting for SWT to (re-)attach its native BrowserFunctions to a
     * freshly (re-)created document - see {@link #injectNamespace(Browser)}'s javadoc for why
     * this polling exists at all. Short enough that the whole retry loop stays imperceptible in
     * the near-universal case where the attachment is already done by the time this runs. */
    private static final int READY_POLL_INTERVAL_MS = 20;

    /** ~2s worst case (see {@link #READY_POLL_INTERVAL_MS}) before giving up and logging instead
     * of polling forever - a document that never gets native functions attached (disposed
     * Browser, an engine that doesn't support BrowserFunction at all, ...) must not leave a
     * runaway timer chain behind. */
    private static final int MAX_READY_POLL_ATTEMPTS = 100;

    public void injectNamespace(final Browser browser) {
        injectNamespace(browser, 0);
    }

    private void injectNamespace(final Browser browser, final int attempt) {
        if (browser.isDisposed()) {
            return;
        }
        // window.FKT.getAuthToken() (and every other FKT.* call) used to throw
        // "ReferenceError: Can't find variable: __fkt_getAuthToken" reproducibly on the first
        // FKTReady after a (re-)navigation, succeeding only on a later retry - not a page-load
        // timing issue on the JS side (login.html already retries across FKTReady events for
        // exactly this), but a genuine race on THIS side: this method runs from
        // ProgressListener#changed/#completed, which fire on network-load progress, while SWT's
        // own (re-)attachment of the native BrowserFunctions to a new document is a separate,
        // independently-timed step - nothing guarantees the latter has completed before the
        // former fires, on any backend (originally seen on Windows/IE, confirmed on
        // Linux/WebKitGTK too). Rather than defining window.FKT.* wrappers that reference a
        // native function which may not exist yet and hoping a later network event retries
        // this in time, verify every native function is actually callable first and, if not,
        // retry on a short Java-side timer - independent of network activity, so success is
        // typically single-digit-milliseconds after the real attachment happens instead of
        // "whenever the next resource happens to load".
        if (!nativeFunctionsAreAttached(browser)) {
            if (attempt < MAX_READY_POLL_ATTEMPTS) {
                browser.getDisplay().timerExec(READY_POLL_INTERVAL_MS, () -> injectNamespace(browser, attempt + 1));
            } else {
                log.warn("FKT bridge: native BrowserFunctions never became callable after " + MAX_READY_POLL_ATTEMPTS
                        + " retries (~" + (MAX_READY_POLL_ATTEMPTS * READY_POLL_INTERVAL_MS) + "ms) for " + safeUrl(browser)
                        + " - window.FKT will not be installed for this document.");
            }
            return;
        }
        String script = "(function() {"
                + "try {"
                + "window.FKT = window.FKT || {};"
                + "window.FKT.version = \"" + FktJsonUtil.escape(version) + "\";"
                + "window.FKT.getVersion = function() { return window.FKT.version; };"
                + "window.FKT.getEnvironmentInfo = function() { return JSON.parse(" + FktGetEnvironmentInfoFunction.JS_NAME + "()); };"
                + "window.FKT.openProduct = function(itemNumber) { return " + FktOpenProductFunction.JS_NAME + "(itemNumber); };"
                + "window.FKT.openProductById = function(id) { return " + FktOpenProductByIdFunction.JS_NAME + "(id); };"
                + "window.FKT.openDocument = function(name) { return " + FktOpenDocumentFunction.JS_NAME + "(name); };"
                + "window.FKT.openDocumentById = function(id) { return " + FktOpenDocumentByIdFunction.JS_NAME + "(id); };"
                + "window.FKT.updatedObject = function(id, dtype) { return " + FktUpdatedObjectFunction.JS_NAME + "(id, dtype); };"
                + "window.FKT.identify = function(userName, hostName) { return " + FktIdentifyFunction.JS_NAME + "(userName, hostName); };"
                + "window.FKT.getAuthToken = function() { return " + FktGetAuthTokenFunction.JS_NAME + "(); };"
                + "window.FKT.ready = true;"
                + "if (window.console && window.console.log) {"
                + "console.log('[FKT-Bridge] window.FKT (re-)installed, version ' + window.FKT.version + ', readyState=' + document.readyState + ', url=' + location.href);"
                + "console.log('[FKT-Bridge] origin trust: " + FktJsonUtil.escape(FktOriginTrust.describe(browser)) + "');"
                + "}"
                + "window.dispatchEvent(new Event('FKTReady'));"
                + "} catch (e) {"
                + "if (window.console && window.console.error) { console.error('[FKT-Bridge] injection failed', e); }"
                + "}"
                + "})();";
        boolean executed = false;
        try {
            executed = browser.execute(script);
        } catch (RuntimeException e) {
            log.error(e, "FKT bridge: browser.execute() threw while injecting window.FKT for " + safeUrl(browser));
        }
        if (!executed) {
            log.warn("FKT bridge: browser.execute() returned false (script was not run) while injecting window.FKT for " + safeUrl(browser));
        }
    }

    /** Whether SWT has (re-)attached its native BrowserFunction bridge to the browser's CURRENT
     * document yet - checked against every function {@link #injectNamespace(Browser, int)} is
     * about to wrap, rather than assuming one being callable means they all are. {@code
     * evaluate()} rather than {@code execute()} - needs the actual boolean result, not just
     * whether the script ran. */
    private boolean nativeFunctionsAreAttached(final Browser browser) {
        try {
            String check = Stream.of(FktGetEnvironmentInfoFunction.JS_NAME, FktOpenProductFunction.JS_NAME,
                    FktOpenProductByIdFunction.JS_NAME, FktOpenDocumentFunction.JS_NAME, FktOpenDocumentByIdFunction.JS_NAME,
                    FktUpdatedObjectFunction.JS_NAME, FktIdentifyFunction.JS_NAME, FktGetAuthTokenFunction.JS_NAME)
                    .map(name -> "typeof " + name + " === 'function'")
                    .collect(Collectors.joining(" && "));
            Object result = browser.evaluate("return " + check + ";");
            return Boolean.TRUE.equals(result);
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static String safeUrl(final Browser browser) {
        try {
            return browser.getUrl();
        } catch (RuntimeException e) {
            return "<unknown>";
        }
    }
}
