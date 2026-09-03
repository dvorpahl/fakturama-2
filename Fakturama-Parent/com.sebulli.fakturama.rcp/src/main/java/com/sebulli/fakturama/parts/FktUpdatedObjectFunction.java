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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.browser.Browser;

import com.sebulli.fakturama.log.ILogger;

/**
 * JS-to-Java bridge backing {@code FKT.updatedObject(id, dtype)} (see {@link FktBridge}): tells
 * Fakturama that an object was changed from outside the app (e.g. by an external system writing
 * to the shared database directly), so any open list view for that entity type should refresh -
 * e.g. an open "offers"/orders view picking up an externally-changed document.
 *
 * <p>Reuses the same {@code IEventBroker} topic mechanism every editor already uses after a save
 * (topic = the editor's {@code EDITOR_ID}, payload = {@link Editor#UPDATE_EVENT}) - see
 * {@code ProductEditor.save()}, {@code DocumentEditor.save()}, or the batch post in
 * {@code WebShopImportManager} after an import run. Like those, this triggers a full reload of
 * the affected view(s) rather than an id-targeted patch, so {@code id} is accepted for the
 * caller's own bookkeeping/logging but isn't otherwise used yet.</p>
 */
public class FktUpdatedObjectFunction extends AbstractFktBrowserFunction {

    public static final String JS_NAME = "__fkt_updatedObject";

    private final IEclipseContext ctx;

    public FktUpdatedObjectFunction(final Browser browser, final IEclipseContext ctx, final ILogger log) {
        super(browser, JS_NAME, log);
        this.ctx = ctx;
    }

    @Override
    protected Object doFunction(final Object[] arguments) {
        if (arguments.length < 2 || !(arguments[1] instanceof String)) {
            debug("rejected - expected 2 arguments (id, dtype), got " + java.util.Arrays.toString(arguments));
            return Boolean.FALSE;
        }

        String dtype = ((String) arguments[1]).trim();
        String editorId = editorIdForType(dtype);
        if (editorId == null) {
            debug("rejected - unknown dtype '" + dtype + "'");
            return Boolean.FALSE;
        }

        IEventBroker eventBroker = ctx.get(IEventBroker.class);
        if (eventBroker == null) {
            debug("rejected - IEventBroker not available from context");
            return Boolean.FALSE;
        }

        debug("posting update event on topic '" + editorId + "' for dtype='" + dtype + "'");
        eventBroker.post(editorId, Editor.UPDATE_EVENT);
        return Boolean.TRUE;
    }

    /**
     * Maps the JS-facing {@code dtype} to the editor topic it should refresh. Only the entity
     * types already reachable through the other {@code FKT.open*} functions are supported today
     * ({@link FktOpenProductFunction}, {@link FktOpenDocumentFunction}) - extend here (and in
     * the corresponding {@code open*} function) when a new entity type is wired in.
     */
    private static String editorIdForType(final String dtype) {
        if ("product".equalsIgnoreCase(dtype)) {
            return ProductEditor.EDITOR_ID;
        }
        if ("document".equalsIgnoreCase(dtype)) {
            return DocumentEditor.EDITOR_ID;
        }
        return null;
    }
}
