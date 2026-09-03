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

import com.sebulli.fakturama.dao.ProductsDAO;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.model.Product;

/**
 * JS-to-Java bridge backing {@code FKT.openProductById(id)} (see {@link FktBridge}): opens the
 * product editor for the real database id, without the item-number lookup that
 * {@code FKT.openProduct(itemNumber)} ({@link FktOpenProductFunction}) does.
 */
public class FktOpenProductByIdFunction extends AbstractFktBrowserFunction {

    public static final String JS_NAME = "__fkt_openProductById";

    private final IEclipseContext ctx;

    public FktOpenProductByIdFunction(final Browser browser, final IEclipseContext ctx, final ILogger log) {
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

        // ProductsDAO is @Creatable - ctx.get(ProductsDAO.class) only finds an instance already
        // put into the context, which is never the case here, so it must be created via DI.
        ProductsDAO productsDAO = ContextInjectionFactory.make(ProductsDAO.class, ctx);
        Product product = productsDAO.findById(id);
        if (product == null) {
            debug("no product found for id=" + id + " (FKT_PRODUCT.ID) - probably an id from a different id space than Fakturama's own");
            return Boolean.FALSE;
        }

        debug("opening product id=" + id + " (itemNumber='" + product.getItemNumber() + "')");
        FktCallEditorSupport.openEditor(ctx, ProductEditor.ID, id);
        return Boolean.TRUE;
    }
}
