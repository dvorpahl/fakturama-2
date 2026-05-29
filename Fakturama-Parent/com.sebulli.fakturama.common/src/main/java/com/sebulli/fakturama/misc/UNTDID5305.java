/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2025 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * The Fakturama Team - initial API and implementation
 */

package com.sebulli.fakturama.misc;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
public enum UNTDID5305 {
    S("S", "einvoice.untdid5305.s"), // Standard rate
    Z("Z", "einvoice.untdid5305.z"), // Zero rated goods
    E("E", "einvoice.untdid5305.e"), // Exempt from tax
    AE("AE", "einvoice.untdid5305.ae"), // VAT Reverse Charge
    K("K", "einvoice.untdid5305.k"), // VAT exempt for EEA intra-community supply of goods and services
    G("G", "einvoice.untdid5305.g"), // Free export item, tax not charged
    O("O", "einvoice.untdid5305.o"), // Services outside scope of tax
    L("L", "einvoice.untdid5305.l"), // Canary Islands general indirect tax
    M("M", "einvoice.untdid5305.m"); // Tax for production, services and importation in Ceuta and Melilla

    private String code;
    private String translationKey;
    public static final UNTDID5305 DEFAULT_VALUE = UNTDID5305.S;

    UNTDID5305(final String code, final String translationKey) {
        this.code = code;
        this.translationKey = translationKey;
    }

    public static UNTDID5305 getByCode(final String code) {
        final String testCode = StringUtils.trimToNull(code);
        if (testCode == null) {
            return null;
        }

        final Optional<UNTDID5305> result = Arrays.stream(UNTDID5305.values()).filter(e -> e.getCode().equals(testCode)).findFirst();
        return result.orElse(null);
    }

    public String getCode() {
        return code;
    }

    public String getTranslationKey() {
        return translationKey;
    }

}
