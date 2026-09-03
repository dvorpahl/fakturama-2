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
 * JS-to-Java bridge backing {@code FKT.openDocumentById(id)} (see {@link FktBridge}): opens the
 * document editor for the real database id, without the document-number lookup that
 * {@code FKT.openDocument(name)} ({@link FktOpenDocumentFunction}) does.
 */
public class FktOpenDocumentByIdFunction extends AbstractFktBrowserFunction {

    public static final String JS_NAME = "__fkt_openDocumentById";

    private final IEclipseContext ctx;

    public FktOpenDocumentByIdFunction(final Browser browser, final IEclipseContext ctx, final ILogger log) {
        super(browser, JS_NAME, log);
        this.ctx = ctx;
    }

    @Override
    protected Object doFunction(final Object[] arguments) {
        Long id = FktCallEditorSupport.toId(arguments);
        if (id == null) {
            debug("rejected - could not parse id from arguments " + java.util.Arrays.toString(arguments));
            return Boolean.FALSE;
        }

        // DocumentsDAO is @Creatable - ctx.get(DocumentsDAO.class) only finds an instance already
        // put into the context, which is never the case here, so it must be created via DI.
        DocumentsDAO documentsDAO = ContextInjectionFactory.make(DocumentsDAO.class, ctx);
        Document document = documentsDAO.findById(id);
        if (document == null) {
            debug("no document found for id=" + id);
            return Boolean.FALSE;
        }

        debug("opening document id=" + id + " (name='" + document.getName() + "')");
        FktCallEditorSupport.openEditor(ctx, DocumentEditor.ID, id);
        return Boolean.TRUE;
    }
}
