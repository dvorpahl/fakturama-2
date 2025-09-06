package com.sebulli.fakturama.common.i18n;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.Optional;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.sebulli.fakturama.common.Activator;
import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.i18n.LocaleUtil;
import com.sebulli.fakturama.misc.Constants;

class LocaleUtilTest {

    private ILocaleService localeService;

    @Mock
    private IPreferenceStore mockPrefs;

    @BeforeEach
    public void setUp() {
        //		MockitoAnnotations.initMocks(this);
        IEclipseContext mockContext = EclipseContextFactory.create("mockContext");
        localeService = ContextInjectionFactory.make(LocaleUtil.class, mockContext);
    }

    @Test
    void testGetDefaultLocale() {
        Locale locale = localeService.getDefaultLocale();
        System.out.println(locale);

        assertNotNull(locale, "Default Locale should not be null");
    }

    @Test
    void testFindLocaleByDisplayCountry() {
        Optional<Locale> localeByDisplayCountry = localeService.findLocaleByDisplayCountry("Italien");
        assertTrue(localeByDisplayCountry.isPresent());
        assertEquals(Locale.ITALY, localeByDisplayCountry.get());
    }

    @Test
    void testFindCodeByDisplayCountry() {
        String localeByDisplayCountry = localeService.findCodeByDisplayCountry("Italien", "de");
        assertEquals("IT", localeByDisplayCountry);
    }

    @Test
    void testGetCurrencyLocale() {
        Locale currencyLocale = localeService.getCurrencyLocale();
        assertEquals(Locale.GERMANY, currencyLocale);
    }

    @Test
    @Disabled("can't be executed because of OSGi class loading quirks")
    void testFindByCode() {
        Mockito.when(Activator.getPreferenceStore()).thenReturn(mockPrefs);
        Mockito.when(mockPrefs.getString(Constants.PREFERENCE_CURRENCY_LOCALE)).thenReturn("de_DE");
        Optional<Locale> localeByCode = localeService.findByCode("de");
        assertTrue(localeByCode.isPresent());
        assertEquals(Locale.GERMANY, localeByCode.get());
    }
}
