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

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.browser.Browser;

import com.sebulli.fakturama.dao.DocumentsDAO;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.model.Document;

/**
 * JS-to-Java bridge backing {@code FKT.openDocument(name)} (see {@link FktBridge}): looks up a
 * document by its document number/name and opens its editor. For the real database id, use
 * {@code FKT.openDocumentById(id)} ({@link FktOpenDocumentByIdFunction}) instead.
 */
public class FktOpenDocumentFunction extends AbstractFktBrowserFunction {

    public static final String JS_NAME = "__fkt_openDocument";

    private final IEclipseContext ctx;

    public FktOpenDocumentFunction(final Browser browser, final IEclipseContext ctx, final ILogger log) {
        super(browser, JS_NAME, log);
        this.ctx = ctx;
    }

    @Override
    protected Object doFunction(final Object[] arguments) {
        if (arguments.length < 1 || !(arguments[0] instanceof String)) {
            debug("rejected - expected 1 string argument (name), got " + java.util.Arrays.toString(arguments));
            return Boolean.FALSE;
        }

        String name = (String) arguments[0];
        // DocumentsDAO is @Creatable - ctx.get(DocumentsDAO.class) only finds an instance already
        // put into the context, which is never the case here, so it must be created via DI.
        DocumentsDAO documentsDAO = ContextInjectionFactory.make(DocumentsDAO.class, ctx);
        Document document = documentsDAO.findByName(name);
        if (document == null) {
            debug("no document found for name='" + name + "'");
            return Boolean.FALSE;
        }

        debug("opening document id=" + document.getId() + " for name='" + name + "'");
        FktCallEditorSupport.openEditor(ctx, DocumentEditor.ID, document.getId());
        return Boolean.TRUE;
    }
}
