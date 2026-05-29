/**
 * 
 */
package com.sebulli.fakturama.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;

import javax.money.MonetaryAmount;

import org.apache.commons.lang3.StringUtils;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sebulli.fakturama.common.Activator;
import com.sebulli.fakturama.i18n.ILocaleService;

/**
 *
 */
class DataUtilsTest {
    private static final double EPSILON = 0.00000001;

    @Mock
    private ILocaleService localeService;

    private DataUtils dataUtils;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(localeService.getDefaultLocale()).thenReturn(Locale.GERMANY);
        Mockito.when(localeService.getCurrencyLocale()).thenReturn(Locale.GERMANY);
        Dictionary<String, Object> dict = new Hashtable<>();
        dict.put(org.osgi.framework.Constants.SERVICE_RANKING, Integer.MAX_VALUE);
        Activator.getContext().registerService(ILocaleService.class, localeService, dict);
        dataUtils = DataUtils.getInstance();
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#getDefaultCurrencyUnit()}.
     */
    @Test
    void testGetDefaultCurrencyUnit() {
        assertTrue("EUR".contentEquals(dataUtils.getDefaultCurrencyUnit().toString()));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#DoublesAreEqual(java.lang.Double, java.lang.Double)}.
     */
    @Test
    void testDoublesAreEqual() {
        Double testValue = Double.valueOf(10);
        assertTrue(dataUtils.DoublesAreEqual(testValue, Double.valueOf(10.000000001)));
        assertFalse(dataUtils.DoublesAreEqual(testValue, Double.valueOf(10.0001)));

        testValue *= -1;
        assertTrue(dataUtils.DoublesAreEqual(testValue, Double.valueOf(-10.000000001)));
        assertFalse(dataUtils.DoublesAreEqual(testValue, Double.valueOf(-10.0001)));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#StringToDouble(java.lang.String)}.
     */
    @Test
    void testStringToDouble() {
        Double testValue = Double.valueOf(10);
        assertEquals(testValue, dataUtils.StringToDouble("10"));
        assertEquals(testValue, dataUtils.StringToDouble("+10"));
        assertEquals(testValue, dataUtils.StringToDouble("10,0"));
        assertEquals(testValue, dataUtils.StringToDouble("10.0"));

        assertEquals(Double.valueOf(0.1), dataUtils.StringToDouble("10%"));
        assertEquals(Double.valueOf(0.1), dataUtils.StringToDouble("10,0 %"));
        assertEquals(Double.valueOf(0.1), dataUtils.StringToDouble("10.0 %"));
        assertEquals(Double.valueOf(0.1), dataUtils.StringToDouble("10,0 %"));

        assertEquals(Double.valueOf(0.1), dataUtils.StringToDouble("10;0 %"));
        // a little bit crazy... Should be fixed in future.
        assertEquals(Double.valueOf(0.1), dataUtils.StringToDouble("a10#0 %"));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#round(java.lang.Double)}.
     */
    @Test
    void testRound() {
        Double testValue = Double.valueOf(10.12645);
        assertEquals(Double.valueOf(10.13), dataUtils.round(testValue)); // scale 2 is the default
        assertEquals(Double.valueOf(10.126), dataUtils.round(testValue, 3));
        assertEquals(Double.valueOf(10.1265), dataUtils.round(testValue, 4));
        assertEquals(Double.valueOf(10.12645), dataUtils.round(testValue, 5));

        testValue *= -1;
        assertEquals(Double.valueOf(-10.13), dataUtils.round(testValue)); // scale 2 is the default
        assertEquals(Double.valueOf(-10.126), dataUtils.round(testValue, 3));
        assertEquals(Double.valueOf(-10.1265), dataUtils.round(testValue, 4));
        assertEquals(Double.valueOf(-10.12645), dataUtils.round(testValue, 5));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#CalculateGrossFromNet(javax.money.MonetaryAmount, java.lang.Double)}.
     */
    @Test
    void testCalculateGrossFromNetMonetaryAmountDouble() {
        MonetaryAmount netValue = Money.of(BigDecimal.valueOf(100), "EUR");
        MonetaryAmount grossValue = Money.of(BigDecimal.valueOf(110), "EUR");

        assertTrue(grossValue.isEqualTo(dataUtils.CalculateGrossFromNet(netValue, Double.valueOf(0.1))));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#CalculateGrossFromNet(java.lang.Double, java.lang.Double)}.
     */
    @Test
    void testCalculateGrossFromNetDoubleDouble() {
        assertEquals(Double.valueOf(110), dataUtils.CalculateGrossFromNet(Double.valueOf(100), Double.valueOf(0.1)), EPSILON);
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#calculateNetFromGross(java.lang.String, java.lang.Double)}.
     */
    @Test
    void testCalculateNetFromGrossStringDouble() {
        Double netValue = Double.valueOf(100);
        MonetaryAmount testValue = dataUtils.calculateNetFromGross("110 EUR", Double.valueOf(0.1));
        assertEquals(netValue, testValue.getNumber().doubleValue(), EPSILON);
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#calculateNetFromGross(java.lang.Double, java.lang.Double)}.
     */
    @Test
    void testCalculateNetFromGrossDoubleDouble() {
        Double netValue = Double.valueOf(100);
        MonetaryAmount testValue = dataUtils.calculateNetFromGross(Double.valueOf(110), Double.valueOf(0.1));
        assertEquals(netValue, testValue.getNumber().doubleValue(), EPSILON);
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#calculateNetFromGross(java.lang.String, java.lang.Double, javax.money.MonetaryAmount)}.
     */
    @Test
    void testCalculateNetFromGrossStringDoubleMonetaryAmount() {
        Money amount = Money.of(Double.valueOf(100), "EUR");
        MonetaryAmount testValue = dataUtils.calculateNetFromGross("110", Double.valueOf(0.1));
        assertEquals(amount, dataUtils.getDefaultRounding().apply(testValue));

        amount = Money.of(Double.valueOf(100.15), "EUR");
        testValue = dataUtils.calculateNetFromGross("110,17", Double.valueOf(0.1));
        assertEquals(amount, dataUtils.getDefaultRounding().apply(testValue));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#calculateNetFromGrossAsDouble(java.lang.Double, java.lang.Double)}.
     */
    @Test
    void testCalculateNetFromGrossAsDouble() {
        assertEquals(Double.valueOf(100.0), dataUtils.calculateNetFromGrossAsDouble(110.0, 0.1), EPSILON);
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#addToDate(java.util.Date, int)}.
     */
    @Test
    void testAddToDate() {
        LocalDateTime testValue = LocalDateTime.of(2019, 2, 10, 0, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2019, 1, 1);
        LocalDateTime retval = dataUtils.addToDate(calendar.getTime(), 9);
        assertTrue(testValue.isEqual(retval),
                String.format("returned value [%s] is not equal to expected value [%s]", retval, testValue.format(DateTimeFormatter.ISO_DATE)));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#removeCR(java.lang.String)}.
     */
    @Test
    void testRemoveCR() {
        String aStringWithManyLinebreaks = StringUtils.join(new String[] { "a", "String", "With", "Many", "Linebreaks" }, System.lineSeparator());
        assertTrue("aStringWithManyLinebreaks".contentEquals(dataUtils.removeCR(aStringWithManyLinebreaks)));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#makeOSLineFeeds(java.lang.String)}.
     */
    @Test
    void testMakeOSLineFeeds() {
        String stringWithLinefeed = "stringWith\nLinefeed";
        String stringWithOSLinefeed = "stringWith" + System.lineSeparator() + "Linefeed";
        assertTrue(stringWithOSLinefeed.contentEquals(dataUtils.makeOSLineFeeds(stringWithLinefeed)));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#MultiLineStringsAreEqual(java.lang.String, java.lang.String)}.
     */
    @Test
    void testMultiLineStringsAreEqual() {
        DataUtils dataUtils = DataUtils.getInstance();
        String myFirstTestString = "A long line" + System.lineSeparator() + "with a break";
        String mySecondTestString = "A long line\rwith a break";
        assertTrue(dataUtils.MultiLineStringsAreEqual(myFirstTestString, mySecondTestString));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#convertCRLF2LF(java.lang.String)}.
     */
    @Test
    void testConvertCRLF2LF() {
        DataUtils dataUtils = DataUtils.getInstance();
        String myTestString = "A long line\r\nwith a break";
        assertEquals("A long line\nwith a break", dataUtils.convertCRLF2LF(myTestString));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#getSingleLine(java.lang.String)}.
     */
    @Test
    void testGetSingleLine() {
        DataUtils dataUtils = DataUtils.getInstance();
        String myTestString = "A long line" + System.lineSeparator() + "with a break";
        assertEquals("A long line", dataUtils.getSingleLine(myTestString));
    }

    /**
     * Test method for
     * {@link com.sebulli.fakturama.misc.DataUtils#replaceAllAccentedChars(java.lang.String)}.
     */
    @Test
    void testReplaceAllAccentedChars() {
        DataUtils dataUtils = DataUtils.getInstance();
        assertEquals("eee", dataUtils.replaceAllAccentedChars("eee"));
        assertEquals("eee", dataUtils.replaceAllAccentedChars("éèê"));
    }
}
