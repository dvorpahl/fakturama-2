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
 * JS-to-Java bridge backing {@code FKT.openProduct(itemNumber)} (see {@link FktBridge}): looks
 * up a product by its item number ("Artikelnummer") and opens its editor. For the real database
 * id, use {@code FKT.openProductById(id)} ({@link FktOpenProductByIdFunction}) instead.
 */
public class FktOpenProductFunction extends AbstractFktBrowserFunction {

    public static final String JS_NAME = "__fkt_openProduct";

    private final IEclipseContext ctx;

    public FktOpenProductFunction(final Browser browser, final IEclipseContext ctx, final ILogger log) {
        super(browser, JS_NAME, log);
        this.ctx = ctx;
    }

    @Override
    protected Object doFunction(final Object[] arguments) {
        if (arguments.length < 1 || !(arguments[0] instanceof String)) {
            debug("rejected - expected 1 string argument (itemNumber), got " + java.util.Arrays.toString(arguments));
            return Boolean.FALSE;
        }

        String itemNumber = (String) arguments[0];
        // ProductsDAO is @Creatable - ctx.get(ProductsDAO.class) only finds an instance already
        // put into the context, which is never the case here, so it must be created via DI.
        ProductsDAO productsDAO = ContextInjectionFactory.make(ProductsDAO.class, ctx);
        Product product = productsDAO.findByItemNumber(itemNumber);
        if (product == null) {
            debug("no product found for itemNumber='" + itemNumber + "' (exact match against FKT_PRODUCT.ITEMNUMBER)");
            return Boolean.FALSE;
        }

        debug("opening product id=" + product.getId() + " for itemNumber='" + itemNumber + "'");
        FktCallEditorSupport.openEditor(ctx, ProductEditor.ID, product.getId());
        return Boolean.TRUE;
    }
}
