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

package com.sebulli.fakturama.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sebulli.fakturama.model.FakturamaModelPackage;
import com.sebulli.fakturama.model.ObjectDuplicator;
import com.sebulli.fakturama.model.Product;

/**
 * Tests that the product-level {@code stockManaged} flag is preserved
 * correctly wherever a {@link Product} is copied or newly created.
 */
class ProductStockManagedTest {

    @Test
    @DisplayName("A freshly created product is stock-managed by default")
    void testNewProductDefaultsToStockManaged() {
        Product product = FakturamaModelPackage.MODELFACTORY.createProduct();
        assertTrue(product.getStockManaged());
    }

    @Test
    @DisplayName("clone() preserves the stockManaged flag")
    void testClonePreservesStockManaged() {
        Product product = FakturamaModelPackage.MODELFACTORY.createProduct();
        product.setStockManaged(Boolean.FALSE);

        Product cloned = product.clone();

        assertFalse(cloned.getStockManaged());
    }

    @Test
    @DisplayName("isSameAs() takes stockManaged into account")
    void testIsSameAsComparesStockManaged() {
        Product a = FakturamaModelPackage.MODELFACTORY.createProduct();
        a.setStockManaged(Boolean.TRUE);
        Product b = FakturamaModelPackage.MODELFACTORY.createProduct();
        b.setStockManaged(Boolean.FALSE);

        assertFalse(a.isSameAs(b));

        b.setStockManaged(Boolean.TRUE);
        assertTrue(a.isSameAs(b));
    }

    @Test
    @DisplayName("ObjectDuplicator#duplicateProduct preserves stockManaged for both managed and unmanaged products")
    void testDuplicateProductPreservesStockManaged() {
        ObjectDuplicator objectDuplicator = new ObjectDuplicator();

        Product managed = FakturamaModelPackage.MODELFACTORY.createProduct();
        managed.setStockManaged(Boolean.TRUE);
        Product duplicatedManaged = objectDuplicator.duplicateProduct(managed);
        assertTrue(duplicatedManaged.getStockManaged());

        Product notManaged = FakturamaModelPackage.MODELFACTORY.createProduct();
        notManaged.setStockManaged(Boolean.FALSE);
        Product duplicatedNotManaged = objectDuplicator.duplicateProduct(notManaged);
        assertFalse(duplicatedNotManaged.getStockManaged());
    }

    @Test
    @DisplayName("toString() includes the stockManaged value")
    void testToStringIncludesStockManaged() {
        Product product = FakturamaModelPackage.MODELFACTORY.createProduct();
        product.setStockManaged(Boolean.FALSE);
        assertEquals(true, product.toString().contains("stockManaged: [false]"));
    }
}
