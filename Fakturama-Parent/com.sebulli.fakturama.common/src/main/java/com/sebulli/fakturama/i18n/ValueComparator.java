/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2014 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */
package com.sebulli.fakturama.i18n;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

class ValueComparator implements Comparator<String> {

    private Map<String, String> base;
    private Collator collator;

    public ValueComparator(final Map<String, String> base, final Locale defaultLocale) {
        this.base = base;
        this.collator = Collator.getInstance(defaultLocale);
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    @Override
    public int compare(final String a, final String b) {
        return collator.compare(base.get(a), base.get(b));
    }
}