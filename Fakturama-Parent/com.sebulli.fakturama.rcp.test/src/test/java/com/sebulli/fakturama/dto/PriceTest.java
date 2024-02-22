package com.sebulli.fakturama.dto;

import javax.inject.Inject;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.MoneyUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.sebulli.fakturama.Activator;
import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PriceTest {

    private static final double DOUBLE_DELTA = 0.001;
    @Inject
    private IEclipseContext ctx;

    //    @Mock
    //    private IPreferenceStore defaultValuePrefs;

    private AutoCloseable closeable;

    @Before
    public void setUp() throws Exception {
        // start common for locale, money for money
        //        Locale.setDefault(Locale.GERMANY);
        FrameworkUtil.getBundle(ILocaleService.class).start();
        ctx = EclipseContextFactory.getServiceContext(Activator.getContext());

        closeable = MockitoAnnotations.openMocks(this);
        IPreferenceStore mockedPreferenceStore = Mockito.mock(IPreferenceStore.class);
        //        Mockito.when(mockedPreferenceStore.getBoolean(Constants.PREFERENCES_CONTACT_USE_SALES_EQUALIZATION_TAX)).thenReturn(Boolean.FALSE);
        Mockito.when(mockedPreferenceStore.getInt(Constants.PREFERENCES_GENERAL_CURRENCY_DECIMALPLACES)).thenReturn(Integer.valueOf(2));
        Mockito.when(mockedPreferenceStore.getString(Constants.PREFERENCE_GENERAL_CURRENCY)).thenReturn("EUR");
        Mockito.when(mockedPreferenceStore.getString(Constants.PREFERENCE_CURRENCY_LOCALE)).thenReturn("DE");

        //        Mockito.when(mockedPreferenceStore.getInt(Constants.PREFERENCES_DOCUMENT_USE_NET_GROSS))
        //                .thenReturn(Integer.valueOf(DocumentSummary.ROUND_NOTSPECIFIED));
        ctx.set(IPreferenceStore.class, mockedPreferenceStore);
        DataUtils.getInstance().setPreferenceStore(mockedPreferenceStore);

        ContextInjectionFactory.setDefault(ctx);

        FrameworkUtil.getBundle(org.javamoney.moneta.OSGIServiceHelper.class).start(Bundle.START_ACTIVATION_POLICY);

        //        CurrencyUnit currencyEUR = Monetary.getCurrency(new Locale("", "GER")); // Germany
        //
        MonetaryAmount testAmount0EUR = Money.of(MoneyUtils.getBigDecimal(0.0), Monetary.getCurrency("EUR"));
        MonetaryAmount testAmount1EUR = Money.of(MoneyUtils.getBigDecimal(1.0), Monetary.getCurrency("EUR"));
    }

    @After
    public void cleanUp() throws Exception {
        closeable.close();

    }

    @Test
    public void testSimpleNetPrice() {
        Price testPrice = new PriceBuilder().withUnitPrice(Money.of(MoneyUtils.getBigDecimal(10), Monetary.getCurrency("EUR"))).withGrossPrices(false)
                .withQuantity(1.0).withVatPercent(0.1).build();

        Assert.assertEquals(10.0, testPrice.getTotalNet().getNumber().doubleValue(), 0);
        Assert.assertEquals(10.0, testPrice.getTotalNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getTotalVat().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getTotalVatRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getTotalSalesEqTax().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getTotalSalesEqTaxRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getTotalGross().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getTotalGrossRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(10.0, testPrice.getUnitNetDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(10.0, testPrice.getUnitNetDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getUnitVatDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getUnitVatDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getUnitSalesEqTaxDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getUnitSalesEqTaxDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getUnitGrossDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getUnitGrossDiscountedRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(10.0, testPrice.getUnitNet().getNumber().doubleValue(), 0);
        Assert.assertEquals(10.0, testPrice.getUnitNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getUnitVat().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getUnitVatRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getUnitSalesEqTax().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getUnitGross().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getUnitGrossRounded().getNumber().doubleValue(), 0);
    }

    @Test
    public void testComplexNetPrice() {

        Price testPrice = new PriceBuilder().withUnitPrice(Money.of(MoneyUtils.getBigDecimal(2.06), Monetary.getCurrency("EUR"))).withGrossPrices(false)
                .withQuantity(25.0).withDiscount(-0.03).withVatPercent(0.07).build();

        Assert.assertEquals(49.955, testPrice.getTotalNet().getNumber().doubleValue(), 0);
        Assert.assertEquals(49.96, testPrice.getTotalNetRounded().getNumber().doubleValue(), 0);
        //		Assert.assertEquals(3.49685..., testPrice.getTotalVat().getNumber().doubleValue(), 0);
        Assert.assertEquals(3.50, testPrice.getTotalVatRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getTotalSalesEqTax().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getTotalSalesEqTaxRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(53.45185, testPrice.getTotalGross().getNumber().doubleValue(), 0);
        Assert.assertEquals(53.45, testPrice.getTotalGrossRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(1.9982, testPrice.getUnitNetDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(2, testPrice.getUnitNetDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.139874, testPrice.getUnitVatDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.14, testPrice.getUnitVatDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getUnitSalesEqTaxDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getUnitSalesEqTaxDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.138074, testPrice.getUnitGrossDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.14, testPrice.getUnitGrossDiscountedRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(2.06, testPrice.getUnitNet().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.06, testPrice.getUnitNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.1442, testPrice.getUnitVat().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.14, testPrice.getUnitVatRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getUnitSalesEqTax().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.2042, testPrice.getUnitGross().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.2, testPrice.getUnitGrossRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(-0.06, testPrice.getUnitAllowance().getNumber().doubleValue(), 0);
        Assert.assertEquals(-1.5, testPrice.getTotalAllowance().getNumber().doubleValue(), 0);
    }

    @Test
    public void testComplexNetWithSETPrice() {
        Price testPrice = new PriceBuilder().withUnitPrice(Money.of(MoneyUtils.getBigDecimal(2.06), "EUR")).withGrossPrices(false).withUseSET(true)
                .withSalesEqualizationTax(Double.valueOf(0.052)).withQuantity(25.0).withDiscount(-0.03).withVatPercent(0.07).build();

        Assert.assertEquals(49.955, testPrice.getTotalNet().getNumber().doubleValue(), 0);
        Assert.assertEquals(49.96, testPrice.getTotalNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(3.49685, testPrice.getTotalVat().getNumber().doubleValue(), 0);
        Assert.assertEquals(3.50, testPrice.getTotalVatRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.59766, testPrice.getTotalSalesEqTax().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.6, testPrice.getTotalSalesEqTaxRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(56.04951, testPrice.getTotalGross().getNumber().doubleValue(), 0);
        Assert.assertEquals(56.05, testPrice.getTotalGrossRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(1.9982, testPrice.getUnitNetDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(2, testPrice.getUnitNetDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.139874, testPrice.getUnitVatDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.14, testPrice.getUnitVatDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.1039064, testPrice.getUnitSalesEqTaxDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.10, testPrice.getUnitSalesEqTaxDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.2419804, testPrice.getUnitGrossDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.24, testPrice.getUnitGrossDiscountedRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(2.06, testPrice.getUnitNet().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.06, testPrice.getUnitNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.1442, testPrice.getUnitVat().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.14, testPrice.getUnitVatRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.10712, testPrice.getUnitSalesEqTax().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.31132, testPrice.getUnitGross().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.31, testPrice.getUnitGrossRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(-0.06, testPrice.getUnitAllowance().getNumber().doubleValue(), 0);
        Assert.assertEquals(-1.5, testPrice.getTotalAllowance().getNumber().doubleValue(), 0);
    }

    @Test
    public void testSimpleGrossPrice() {
        Price testPrice = new PriceBuilder().withUnitPrice(Money.of(MoneyUtils.getBigDecimal(11), "EUR")).withGrossPrices(true).withQuantity(1.0)
                .withVatPercent(0.1).build();

        Assert.assertEquals(10.0, testPrice.getTotalNet().getNumber().doubleValue(), 0);
        Assert.assertEquals(10.0, testPrice.getTotalNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getTotalVat().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getTotalVatRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getTotalSalesEqTax().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getTotalSalesEqTaxRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getTotalGross().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getTotalGrossRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(10.0, testPrice.getUnitNetDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(10.0, testPrice.getUnitNetDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getUnitVatDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getUnitVatDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getUnitSalesEqTaxDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getUnitSalesEqTaxDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getUnitGrossDiscounted().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getUnitGrossDiscountedRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(10.0, testPrice.getUnitNet().getNumber().doubleValue(), 0);
        Assert.assertEquals(10.0, testPrice.getUnitNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getUnitVat().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.0, testPrice.getUnitVatRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.0, testPrice.getUnitSalesEqTax().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getUnitGross().getNumber().doubleValue(), 0);
        Assert.assertEquals(11.0, testPrice.getUnitGrossRounded().getNumber().doubleValue(), 0);
    }

    @Test
    public void testComplexGrossPrice() {
        Price testPrice = new PriceBuilder().withUnitPrice(Money.of(MoneyUtils.getBigDecimal(2.20), "EUR")).withGrossPrices(true).withQuantity(25.0)
                .withDiscount(-0.03).withVatPercent(0.07).build();

        Assert.assertEquals(49.86, testPrice.getTotalNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(53.35, testPrice.getTotalGrossRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(3.49, testPrice.getTotalVatRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.06, testPrice.getUnitNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.99, testPrice.getUnitNetDiscountedRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(2.2, testPrice.getUnitGross().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.13, testPrice.getUnitGrossDiscountedRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(0.14, testPrice.getUnitVatRounded().getNumber().doubleValue(), DOUBLE_DELTA);
        //		Assert.assertEquals(0.13961, testPrice.getUnitVatDiscounted().getNumber().doubleValue(), 0.00002);
        Assert.assertEquals(0.14, testPrice.getUnitVatDiscountedRounded().getNumber().doubleValue(), 0);

        Assert.assertEquals(-0.06, testPrice.getUnitAllowance().getNumber().doubleValue(), 0);
        Assert.assertEquals(-1.5, testPrice.getTotalAllowance().getNumber().doubleValue(), 0);
    }

    @Test
    public void testGrossPriceWithQuantity() {
        Price testPrice = new PriceBuilder().withUnitPrice(Money.of(MoneyUtils.getBigDecimal(2.2), "EUR")).withGrossPrices(true).withQuantity(25.0)
                .withVatPercent(0.07).build();

        Assert.assertEquals(51.4, testPrice.getTotalNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(55.0, testPrice.getTotalGrossRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(3.6, testPrice.getTotalVatRounded().getNumber().doubleValue(), 0);
    }

    @Test
    public void testGrossPriceWithDiscount() {
        Price testPrice = new PriceBuilder().withUnitPrice(Money.of(MoneyUtils.getBigDecimal(2.2), "EUR")).withGrossPrices(true).withDiscount(-0.03)
                .withQuantity(25.0).withVatPercent(0.07).build();
        Assert.assertEquals(49.86, testPrice.getTotalNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(53.35, testPrice.getTotalGrossRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(3.49, testPrice.getTotalVatRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(-1.5, testPrice.getTotalAllowance().getNumber().doubleValue(), 0);
        Assert.assertEquals(-0.06, testPrice.getUnitAllowance().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.2, testPrice.getUnitGrossRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.13, testPrice.getUnitGrossDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(1.99, testPrice.getUnitNetDiscountedRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(2.06, testPrice.getUnitNetRounded().getNumber().doubleValue(), 0);
        Assert.assertEquals(0.14, testPrice.getUnitVatDiscountedRounded().getNumber().doubleValue(), 0);
    }

}
