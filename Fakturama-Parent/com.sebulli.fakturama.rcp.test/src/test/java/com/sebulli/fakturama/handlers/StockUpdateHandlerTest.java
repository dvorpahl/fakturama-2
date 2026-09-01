/* Fakturama - Free Invoicing Software - http://www.fakturama.org
 *
 * Copyright (C) 2026 www.fakturama.org
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: The Fakturama Team - initial API and implementation */
package com.sebulli.fakturama.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sebulli.fakturama.dao.ProductsDAO;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.DocumentItem;
import com.sebulli.fakturama.model.FakturamaModelPackage;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.model.Product;

/**
 * Tests for the central, product-level {@code stockManaged} check in
 * {@link StockUpdateHandler}.
 */
@ExtendWith(MockitoExtension.class)
class StockUpdateHandlerTest {

    @Mock
    private Messages msg;

    @Mock
    private IEclipsePreferences eclipsePrefs;

    @Mock
    private ProductsDAO productsDAO;

    @Mock
    private IEventBroker evtBroker;

    @InjectMocks
    private StockUpdateHandler stockUpdateHandler;

    private void stubGlobalStockSettings(final boolean useQuantity) {
        Mockito.lenient().when(eclipsePrefs.getBoolean(Mockito.eq(Constants.PREFERENCES_PRODUCT_USE_QUANTITY), Mockito.anyBoolean()))
                .thenReturn(useQuantity);
        Mockito.lenient().when(eclipsePrefs.get(Mockito.eq(Constants.PREFERENCES_PRODUCT_CHANGE_QTY), Mockito.anyString()))
                .thenReturn(Constants.PREFERENCES_PRODUCT_CHANGE_QTY_INVOICE);
    }

    private Product productWithQuantity(final double quantity, final boolean stockManaged) {
        Product product = FakturamaModelPackage.MODELFACTORY.createProduct();
        product.setQuantity(Double.valueOf(quantity));
        product.setStockManaged(Boolean.valueOf(stockManaged));
        return product;
    }

    private DocumentItem itemFor(final Product product, final double quantity) {
        DocumentItem item = FakturamaModelPackage.MODELFACTORY.createDocumentItem();
        item.setProduct(product);
        item.setQuantity(Double.valueOf(quantity));
        return item;
    }

    private Invoice invoiceWith(final DocumentItem... items) {
        Invoice invoice = FakturamaModelPackage.MODELFACTORY.createInvoice();
        invoice.setBillingType(BillingType.INVOICE);
        for (DocumentItem item : items) {
            invoice.addToItems(item);
        }
        return invoice;
    }

    @Test
    @DisplayName("A stock-managed product is reduced when the configured posting event (invoice) occurs")
    void testStockManagedProductIsReduced() {
        stubGlobalStockSettings(true);
        Product product = productWithQuantity(10.0, true);
        Invoice invoice = invoiceWith(itemFor(product, 3.0));

        stockUpdateHandler.updateStockQuantity(null, null, invoice);

        assertEquals(7.0, product.getQuantity(), 0.0);
    }

    @Test
    @DisplayName("A product that is not stock-managed stays unchanged during the same event")
    void testNotStockManagedProductIsUnchanged() {
        stubGlobalStockSettings(true);
        Product product = productWithQuantity(10.0, false);
        Invoice invoice = invoiceWith(itemFor(product, 3.0));

        stockUpdateHandler.updateStockQuantity(null, null, invoice);

        assertEquals(10.0, product.getQuantity(), 0.0);
    }

    @Test
    @DisplayName("A document with mixed items only changes stock for stock-managed products")
    void testMixedDocumentOnlyChangesStockManagedProducts() {
        stubGlobalStockSettings(true);
        Product managed = productWithQuantity(10.0, true);
        Product notManaged = productWithQuantity(10.0, false);
        Invoice invoice = invoiceWith(itemFor(managed, 3.0), itemFor(notManaged, 4.0));

        stockUpdateHandler.updateStockQuantity(null, null, invoice);

        assertEquals(7.0, managed.getQuantity(), 0.0);
        assertEquals(10.0, notManaged.getQuantity(), 0.0);
    }

    @Test
    @DisplayName("No stock change occurs when global inventory management is disabled, even for a stock-managed product")
    void testNoStockChangeWhenGlobalFeatureDisabled() {
        stubGlobalStockSettings(false);
        Product product = productWithQuantity(10.0, true);
        Invoice invoice = invoiceWith(itemFor(product, 3.0));

        stockUpdateHandler.updateStockQuantity(null, null, invoice);

        assertEquals(10.0, product.getQuantity(), 0.0);
    }

    @Test
    @DisplayName("A document item that references no product (free-text item) is skipped without error")
    void testItemWithoutProductIsSkipped() {
        stubGlobalStockSettings(true);
        DocumentItem freeTextItem = FakturamaModelPackage.MODELFACTORY.createDocumentItem();
        freeTextItem.setQuantity(Double.valueOf(2.0));
        Invoice invoice = invoiceWith(freeTextItem);

        stockUpdateHandler.updateStockQuantity(null, null, invoice);
        // no exception, nothing to assert on since there's no product involved
    }
}
