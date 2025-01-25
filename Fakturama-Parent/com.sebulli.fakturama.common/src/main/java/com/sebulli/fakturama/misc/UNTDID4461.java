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

public enum UNTDID4461 {
    VALUE_1("1", false, "translation.untdid4461.v1"), // 
    VALUE_2("2", false, "translation.untdid4461.v2"), // 
    VALUE_3("3", false, "translation.untdid4461.v3"), // 
    VALUE_4("4", false, "translation.untdid4461.v4"), // 
    VALUE_5("5", false, "translation.untdid4461.v5"), // 
    VALUE_6("6", false, "translation.untdid4461.v6"), // 
    VALUE_7("7", false, "translation.untdid4461.v7"), // 
    VALUE_8("8", false, "translation.untdid4461.v8"), // 
    VALUE_9("9", false, "translation.untdid4461.v9"), // 
    VALUE_10("10", true, "translation.untdid4461.v10"), // 
    VALUE_11("11", false, "translation.untdid4461.v11"), // 
    VALUE_12("12", false, "translation.untdid4461.v12"), // 
    VALUE_13("13", false, "translation.untdid4461.v13"), // 
    VALUE_14("14", false, "translation.untdid4461.v14"), // 
    VALUE_15("15", false, "translation.untdid4461.v15"), // 
    VALUE_16("16", false, "translation.untdid4461.v16"), // 
    VALUE_17("17", false, "translation.untdid4461.v17"), // 
    VALUE_18("18", false, "translation.untdid4461.v18"), // 
    VALUE_19("19", false, "translation.untdid4461.v19"), // 
    VALUE_20("20", false, "translation.untdid4461.v20"), // 
    VALUE_21("21", false, "translation.untdid4461.v21"), // 
    VALUE_22("22", false, "translation.untdid4461.v22"), // 
    VALUE_23("23", false, "translation.untdid4461.v23"), // 
    VALUE_24("24", false, "translation.untdid4461.v24"), // 
    VALUE_25("25", false, "translation.untdid4461.v25"), // 
    VALUE_26("26", false, "translation.untdid4461.v26"), // 
    VALUE_27("27", false, "translation.untdid4461.v27"), // 
    VALUE_28("28", false, "translation.untdid4461.v28"), // 
    VALUE_29("29", false, "translation.untdid4461.v29"), // 
    VALUE_30("30", true, "translation.untdid4461.v30"), // 
    VALUE_31("31", true, "translation.untdid4461.v31"), // 
    VALUE_32("32", false, "translation.untdid4461.v32"), // 
    VALUE_33("33", false, "translation.untdid4461.v33"), // 
    VALUE_34("34", false, "translation.untdid4461.v34"), // 
    VALUE_35("35", false, "translation.untdid4461.v35"), // 
    VALUE_36("36", false, "translation.untdid4461.v36"), // 
    VALUE_37("37", false, "translation.untdid4461.v37"), // 
    VALUE_38("38", false, "translation.untdid4461.v38"), // 
    VALUE_39("39", false, "translation.untdid4461.v39"), // 
    VALUE_40("40", false, "translation.untdid4461.v40"), // 
    VALUE_41("41", false, "translation.untdid4461.v41"), // 
    VALUE_42("42", false, "translation.untdid4461.v42"), // 
    VALUE_43("43", false, "translation.untdid4461.v43"), // 
    VALUE_44("44", false, "translation.untdid4461.v44"), // 
    VALUE_45("45", false, "translation.untdid4461.v45"), // 
    VALUE_46("46", false, "translation.untdid4461.v46"), // 
    VALUE_47("47", false, "translation.untdid4461.v47"), // 
    VALUE_48("48", false, "translation.untdid4461.v48"), // 
    VALUE_49("49", false, "translation.untdid4461.v49"), // 
    VALUE_50("50", false, "translation.untdid4461.v50"), // 
    VALUE_51("51", false, "translation.untdid4461.v51"), // 
    VALUE_52("52", false, "translation.untdid4461.v52"), // 
    VALUE_53("53", false, "translation.untdid4461.v53"), // 
    VALUE_54("54", false, "translation.untdid4461.v54"), // 
    VALUE_55("55", false, "translation.untdid4461.v55"), // 
    VALUE_56("56", false, "translation.untdid4461.v56"), // 
    VALUE_57("57", false, "translation.untdid4461.v57"), // 
    VALUE_58("58", true, "translation.untdid4461.v58"), // 
    VALUE_59("59", true, "translation.untdid4461.v59"), // 
    VALUE_60("60", false, "translation.untdid4461.v60"), // 
    VALUE_61("61", false, "translation.untdid4461.v61"), // 
    VALUE_62("62", false, "translation.untdid4461.v62"), // 
    VALUE_63("63", false, "translation.untdid4461.v63"), // 
    VALUE_64("64", false, "translation.untdid4461.v64"), // 
    VALUE_65("65", false, "translation.untdid4461.v65"), // 
    VALUE_66("66", false, "translation.untdid4461.v66"), // 
    VALUE_67("67", false, "translation.untdid4461.v67"), //
    VALUE_68("68", true, "translation.untdid4461.v68"), // 
    VALUE_69("69", false, "translation.untdid4461.v69"), //
    VALUE_70("70", false, "translation.untdid4461.v70"), //
    VALUE_74("74", false, "translation.untdid4461.v74"), // 
    VALUE_75("75", false, "translation.untdid4461.v75"), //
    VALUE_76("76", false, "translation.untdid4461.v76"), //
    VALUE_77("77", false, "translation.untdid4461.v77"), // 
    VALUE_78("78", false, "translation.untdid4461.v78"), //
    VALUE_91("91", false, "translation.untdid4461.v91"), //
    VALUE_92("92", false, "translation.untdid4461.v92"), // 
    VALUE_93("93", false, "translation.untdid4461.v93"), //
    VALUE_94("94", false, "translation.untdid4461.v94"), //
    VALUE_95("95", false, "translation.untdid4461.v95"), // 
    VALUE_96("96", false, "translation.untdid4461.v96"), //
    VALUE_97("97", false, "translation.untdid4461.v97"), // 
    VALUE_ZZZ("ZZZ", true, "translation.untdid4461.vZZZ");

    private String code;
    private boolean used;
    private String translationKey;

    UNTDID4461(final String code, final boolean used, final String translationKey) {
        this.code = code;
        this.used = used;
        this.translationKey = translationKey;
    }

    public static UNTDID4461 getByCode(final String code) {
        final String testCode = StringUtils.trimToNull(code);
        if (testCode == null) {
            return null;
        }

        final Optional<UNTDID4461> result = Arrays.stream(UNTDID4461.values()).filter(e -> e.code.equals(testCode)).findFirst();
        return result.orElse(null);
    }

    public String getCode() {
        return code;
    }

    public boolean getUsed() {
        return used;
    }

    public String getTranslationKey() {
        return translationKey;
    }

}
