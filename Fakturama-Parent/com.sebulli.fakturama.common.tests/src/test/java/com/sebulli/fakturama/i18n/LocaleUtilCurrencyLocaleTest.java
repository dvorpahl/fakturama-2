package com.sebulli.fakturama.i18n;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;

class LocaleUtilCurrencyLocaleTest {

    @Test
    void keepsCurrencyLocaleWithCountry() {
        assertEquals(Locale.GERMANY, LocaleUtil.ensureCountryForCurrency(Locale.GERMANY, Locale.US));
    }

    @Test
    void usesFormatLocaleForLanguageOnlyLocale() {
        assertEquals(Locale.GERMANY, LocaleUtil.ensureCountryForCurrency(Locale.GERMAN, Locale.GERMANY));
    }

    @Test
    void usesSafeFallbackWhenNeitherLocaleHasCountry() {
        assertEquals(Locale.US, LocaleUtil.ensureCountryForCurrency(Locale.GERMAN, Locale.ENGLISH));
    }
}
