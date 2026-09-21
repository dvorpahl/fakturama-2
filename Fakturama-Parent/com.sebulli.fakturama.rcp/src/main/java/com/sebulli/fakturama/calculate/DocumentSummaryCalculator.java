/**
 * 
 */
package com.sebulli.fakturama.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.javamoney.moneta.Money;

import com.sebulli.fakturama.dao.DocumentReceiverDAO;
import com.sebulli.fakturama.dto.DocumentItemDTO;
import com.sebulli.fakturama.dto.DocumentSummary;
import com.sebulli.fakturama.dto.DocumentSummaryParam;
import com.sebulli.fakturama.dto.Price;
import com.sebulli.fakturama.dto.VatSummaryItem;
import com.sebulli.fakturama.dto.VatSummarySet;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.misc.UNTDID5305;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.DocumentItem;
import com.sebulli.fakturama.model.Shipping;
import com.sebulli.fakturama.model.ShippingVatType;
import com.sebulli.fakturama.model.VAT;
import com.sebulli.fakturama.util.DocumentTypeUtil;

/**
 * Calculator for the document summaries.
 *
 */
public class DocumentSummaryCalculator {
    public static final String CURRENCY_CODE = "CURRENCY_CODE";

    @Inject
    private DocumentReceiverDAO documentReceiverDao;

    @Inject
    private IEclipseContext ctx;

    @Inject
    protected IPreferenceStore defaultValuePrefs;

    /**
     * Checks if the current editor uses sales equalization tax (this is only
     * needed for some customers).
     */
    private boolean useSET = false;
    private CurrencyUnit currencyCode;

    @Inject
    public DocumentSummaryCalculator(final IEclipseContext context) {
        this((CurrencyUnit) context.get(CURRENCY_CODE));
    }

    public DocumentSummaryCalculator(final boolean useSET) {
        this(useSET, DataUtils.getInstance().getDefaultCurrencyUnit());
    }

    public DocumentSummaryCalculator(final CurrencyUnit currencyCode) {
        this(false, currencyCode);
    }

    public DocumentSummaryCalculator(final boolean useSET, final CurrencyUnit currencyCode) {
        this.useSET = useSET;
        this.currencyCode = currencyCode;
    }

    public DocumentSummary calculate(final Document dataSetDocument) {
        return calculate(dataSetDocument, null);
    }

    public DocumentSummary calculate(final Document dataSetDocument, final VatSummarySet vatSummarySet) {
        this.useSET = documentReceiverDao.isSETEnabled(dataSetDocument);
        final Double scaleFactor = NumberUtils.DOUBLE_ONE;
        final VAT noVatReference = dataSetDocument.getNoVatReference();
        // only calculate a deposit if the document is really deposited (else, if e.g. we have a fully paid invoice and the deposit 
        // amount is not equal to zero we get unpredictable results in later processing)
        final MonetaryAmount deposit = Money.of(BooleanUtils.isTrue(dataSetDocument.getDeposit()) ? dataSetDocument.getPaidValue() : NumberUtils.DOUBLE_ZERO,
                getCurrencyCode());
        final Shipping shipping = dataSetDocument.getShipping();

        // Get the sign of this document ( + or -)
        final int sign = DocumentTypeUtil.findByBillingType(dataSetDocument.getBillingType()).getSign();

        return calculate(vatSummarySet, dataSetDocument.getItems(),
                Optional.ofNullable(dataSetDocument.getShippingValue()).orElse(NumberUtils.DOUBLE_ZERO),
                shipping != null ? shipping.getShippingVat() : null, shipping != null ? shipping.getAutoVat() : dataSetDocument.getShippingAutoVat(),
                Optional.ofNullable(dataSetDocument.getItemsRebate()).orElse(NumberUtils.DOUBLE_ZERO), noVatReference, scaleFactor,
                dataSetDocument.getNetGross(), deposit, sign);
    }

    public DocumentSummary calculate(final List<DocumentItem> items, final Shipping shipping, final ShippingVatType shippingAutoVat, final Double itemsDiscount,
            final VAT noVatReference, final int netGross, final MonetaryAmount deposit, final int sign) {
        return calculate(null, items, shipping.getShippingValue(), shipping.getShippingVat(), shippingAutoVat, itemsDiscount, noVatReference,
                NumberUtils.DOUBLE_ONE, netGross, deposit, sign);
    }

    /**
     * Calculates the tax, gross and sum of a document
     * 
     * @param globalVatSummarySet
     *            The documents vat is added to this global VAT summary set.
     * @param items
     *            Document's items
     * @param shippingValue
     *            Document's shipping
     * @param shippingVatPercent
     *            Shipping's VAT - This is only used, if the shipping's VAT is
     *            not calculated based on the items.
     * @param shippingVatDescription
     *            Shipping's VAT name
     * @param shippingAutoVat
     *            If TRUE, the shipping VAT is based on the item's VAT
     * @param itemsDiscount
     *            Discount value
     * @param VAT
     *            noVatReference
     *            VAT which has a taxValue of 0. If set, it's assumed that noVat
     *            is given and all VAT values are set to 0.
     *            (replacement for noVat flag)
     * @param scaleFactor
     * 
     * @param deposit
     * @param shippingValue
     */
    public DocumentSummary calculate(final VatSummarySet globalVatSummarySet, final List<DocumentItem> items, final Double shippingValue, final VAT shippingVat,
            final ShippingVatType shippingAutoVat, final Double itemsDiscount, final VAT noVatReference, final Double scaleFactor, final int netGross,
            final MonetaryAmount deposit, final int sign) {
        return calculate(new DocumentSummaryParam().withItems(items).withShippingValue(shippingValue).withShippingVat(shippingVat).withAutoVat(shippingAutoVat)
                .withItemsDiscount(itemsDiscount).withNoVatRef(noVatReference).withScaleFactor(scaleFactor).withNetGross(netGross).withDeposit(deposit)
                .withSign(sign));
    }

    public DocumentSummary calculate(final DocumentSummaryParam param) {
        final DataUtils dataUtils = ContextInjectionFactory.make(DataUtils.class, ctx);

        final DocumentSummary retval = ContextInjectionFactory.make(DocumentSummary.class, ctx);
        final MonetaryRounding rounding = dataUtils.getRounding();

        // Use all non-deleted items
        for (final DocumentItem item : param.getDocumentItems()) {

            if (BooleanUtils.isTrue(item.getDeleted())) {
                continue;
            }
            final Price price = new DocumentItemDTO(item, param.getSign()).getPrice(useSET, false); // scaleFactor was always 1.0 in the old application, hence we could omit this

            // Add the total net value of this item to the sum of net items
            retval.addPrice(price, item.getQuantity());

            // Add the VAT summary item to the ... 
            final VatSummaryItem vatSummaryItem = createVatSummaryItem(param, item, price);

            // .. VAT summary of the document ..
            retval.addVatSummaryItem(vatSummaryItem);
        }

        // *** round sum of items

        // round to full net cents
        if (param.getNetGross() == DocumentSummary.ROUND_NET_VALUES) {
            retval.setItemsNet(retval.getItemsNet().with(rounding));
        }

        // Gross value is the sum of net and VAT and sales equalization tax value 
        retval.setTotalNet(retval.getItemsNet()); // ...Discounted is wrong at this point

        if (!isUseGross(param)) {
            retval.setItemsGross(retval.getTotalNet().add(retval.getTotalVatRounded()).add(retval.getTotalSET()));
        }

        // round to full gross cents
        if (param.getNetGross() == DocumentSummary.ROUND_GROSS_VALUES) {
            retval.setItemsGrossDiscounted(retval.getItemsGross().with(rounding).multiply(param.getSign()));
            retval.setTotalNet(retval.getItemsNet().multiply(param.getSign()));
        }

        retval.setTotalGross(retval.getItemsGrossDiscounted());

        final MonetaryAmount itemsNet = retval.getItemsNetDiscounted().multiply(param.getSign());
        final MonetaryAmount itemsGross = retval.getItemsGross().multiply(param.getSign());

        // *** DISCOUNT ***

        // Calculate the absolute discount values
        final MonetaryAmount discountNet = itemsNet.multiply(param.getItemsDiscount());
        retval.setDiscountNet(discountNet);
        retval.setDiscountGross(itemsGross.multiply(param.getItemsDiscount()));

        final MonetaryAmount zero = Money.zero(getCurrencyCode());

        // Calculate discount
        calculateDiscount(param, retval);

        // calculate shipping

        // Scale the shipping
        final MonetaryAmount shippingAmount = Money.of(param.getShippingValue() * param.getScaleFactor() * param.getSign(), getCurrencyCode());
        Double shippingVatPercent = param.getShippingVat() != null ? param.getShippingVat().getTaxValue() : NumberUtils.DOUBLE_ZERO;
        String currentVatDescription = param.getShippingVat() != null
                ? Objects.toString(param.getShippingVat().getName(), param.getShippingVat().getDescription())
                : ""; // TODO or get it from additional document info???

        // If shippingAutoVat is not fix, the shipping VAT is 
        // an average value of the VATs of the items.
        if (param.getAutoVat() != null && !param.getAutoVat().isSHIPPINGVATFIX()) {

            // If the shipping is set as gross value, calculate the net value.
            // Use the average VAT of all the items.
            if (param.getAutoVat().isSHIPPINGVATGROSS()) {
                if (!itemsGross.isZero()) {
                    // shippingValue * itemsNet / itemsGross
                    retval.setShippingNet(shippingAmount.multiply(itemsNet.getNumber()).divide(itemsGross.subtract(retval.getTotalSET()).getNumber()));
                } else {
                    retval.setShippingNet(shippingAmount);
                }
            }

            // If the shipping is set as net value, use the net value.
            if (param.getAutoVat().isSHIPPINGVATNET()) {
                retval.setShippingNet(shippingAmount);
            }

            // Use the average VAT of all the items.
            shippingVatPercent = itemsNet.isZero() ? NumberUtils.DOUBLE_ZERO
                    : itemsGross.subtract((retval.getTotalSET())).divide(itemsNet.getNumber()).getNumber().doubleValue() - 1;

            // Increase the VAT summary entries by the shipping ratio

            // Calculate the sum of all VatSummary entries
            final MonetaryAmount netSumOfAllVatSummaryItems = retval.getTotalVatBase();

            // at this point we have all relevant VATs (discounted items)
            // and store them into a separate container.
            final List<VatSummaryItem> vatSummarySnapshot;
            synchronized (retval.getVatSummary()) {
                vatSummarySnapshot = new ArrayList<>(retval.getVatSummary());
            }
            for (final VatSummaryItem vatSummaryItem : vatSummarySnapshot) {
                // Get the data from each entry
                currentVatDescription = vatSummaryItem.getVatName();
                shippingVatPercent = vatSummaryItem.getVatPercent();

                // If noVat is set, the VAT is 0%
                if (param.getNoVatRef() != null) {
                    currentVatDescription = param.getNoVatRef().getDescription();
                    shippingVatPercent = NumberUtils.DOUBLE_ZERO;
                }

                // Calculate the ratio of this vat summary item and all items.
                // The shippingNetPart is proportional to this ratio.
                MonetaryAmount shippingNetPart = Money.from(zero);
                if (!netSumOfAllVatSummaryItems.isZero()) {
                    shippingNetPart = retval.getShippingNet().multiply(vatSummaryItem.getNet().divide(netSumOfAllVatSummaryItems.getNumber()).getNumber());
                }

                // Add shippingNetPart to the sum "shippingVatValue"  
                final Price shippingPart = new Price(shippingNetPart, shippingVatPercent);
                retval.addToShippingVat(shippingPart.getUnitVat());

                final VatSummaryItem shippingVatSummaryItem = new VatSummaryItem(currentVatDescription, shippingVatPercent, vatSummaryItem.getVatCode(),
                        shippingPart.getUnitNet(), shippingPart.getUnitVat(),Objects.toString(vatSummaryItem.getDescription(), currentVatDescription));
                if (this.useSET && !vatSummaryItem.getSalesEqTax().isZero()) {
                    shippingVatSummaryItem.setSalesEqTaxPercent(vatSummaryItem.getSalesEqTaxPercent());
                }

                // Adjust the vat summary item by the shipping part
                retval.addVatSummaryItem(shippingVatSummaryItem);
                // add for einvoice
                retval.addShippingVatSummaryItem(shippingVatSummaryItem);
            }
        }

        // If shippingAutoVat is fix set, the shipping vat is 
        // a constant percent value.
        else {
            retval.setShippingNet(shippingAmount);

            // If noVat is set, the VAT is 0%
            if (param.getNoVatRef() != null) {
                currentVatDescription = param.getNoVatRef().getDescription();
                shippingVatPercent = NumberUtils.DOUBLE_ZERO;
            }

            // use shippingVatPercent as fix percent value for the shipping
            retval.setShippingVat(retval.getShippingNet().multiply(shippingVatPercent));

            // only add VAT if a shipping value is set
            if (!shippingAmount.isZero()) {
                final VatSummaryItem shippingVatSummaryItem = new VatSummaryItem(currentVatDescription, shippingVatPercent, param.getShippingVat().getCode(),
                        retval.getShippingNet(), retval.getShippingVat(), param.getShippingVat().getDescription());

                // Adjust the vat summary item by the shipping part
                retval.addVatSummaryItem(shippingVatSummaryItem);
            }
        }

        retval.setShippingGross(retval.getShippingNet().add(retval.getShippingVat()));

        // round to full net cents
        if (param.getNetGross() == DocumentSummary.ROUND_NET_VALUES) {
            retval.setShippingNet(retval.getShippingNet().with(rounding));
            retval.setTotalNet(retval.getTotalNet().with(rounding));
        }

        // Add the shipping to the documents sum.
        retval.addToTotalNet(retval.getShippingNet());
        retval.addToTotalGross(retval.getShippingGross());

        // Finally, round the values
        retval.setDiscountNet(retval.getDiscountNet().with(rounding));
        retval.setDiscountGross(retval.getDiscountGross().with(rounding));

        retval.setItemsNet(retval.getItemsNet().with(rounding));
        retval.setItemsGross(retval.getItemsGross().with(rounding));

        retval.setTotalGross(retval.getTotalGross().with(rounding));

        // round the shipping values
        if (param.getNetGross() == DocumentSummary.ROUND_NET_VALUES) {
            retval.setShippingNet(retval.getShippingNet().with(rounding));
            retval.setShippingVat(retval.getShippingVat().with(rounding));
            retval.setShippingGross(retval.getShippingNet().add(retval.getShippingVat()));
        } else if (param.getNetGross() == DocumentSummary.ROUND_GROSS_VALUES) {
            retval.setShippingGross(retval.getShippingGross().with(rounding));
            retval.setShippingVat(retval.getShippingVat().with(rounding));
            retval.setShippingNet(retval.getShippingGross().subtract(retval.getShippingVat()));
        } else {
            retval.setShippingNet(retval.getShippingNet().with(rounding));
            retval.setShippingGross(retval.getShippingGross().with(rounding));
            retval.setShippingVat(retval.getShippingGross().subtract(retval.getShippingNet()));
        }

        //calculate the final payment
        retval.setDeposit(param.getDeposit());
        retval.setFinalPayment(retval.getTotalGross().subtract(retval.getDeposit()));

        return retval;
    }

    private void calculateDiscount(final DocumentSummaryParam param, final DocumentSummary retval) {
        if (!DataUtils.getInstance().isDoubleZero(param.getItemsDiscount())) {
            final MonetaryAmount discountNet = retval.getDiscountNet();
            final MonetaryAmount itemsNet = retval.getItemsNetDiscounted();
            final MonetaryAmount itemsGross = retval.getItemsGross();
            final MonetaryAmount zero = Money.zero(getCurrencyCode());

            // Calculate the vat value in percent from the gross value of all items
            // and the net value of all items. So the discount's vat is the average 
            // value of the item's vat
            Double discountVatPercent;
            if (!itemsNet.isZero()) {
                discountVatPercent = itemsGross.divide(itemsNet.getNumber()).getNumber().doubleValue() - NumberUtils.DOUBLE_ONE;
            } else {
                // do not divide by zero
                discountVatPercent = NumberUtils.DOUBLE_ZERO;
            }

            // If noVat is set, the VAT is 0%
            if (param.getNoVatRef() != null) {
                discountVatPercent = NumberUtils.DOUBLE_ZERO;
            }

            // Reduce all the VAT entries in the VAT Summary Set by the discount 
            MonetaryAmount discountVatValue = Money.from(zero);
            String discountVatDescription = "";
            // Get the data from each entry
            final List<VatSummaryItem> vatSummarySnapshot;
            synchronized (retval.getVatSummary()) {
                vatSummarySnapshot = new ArrayList<>(retval.getVatSummary());
            }
            for (final VatSummaryItem vatSummaryItem : vatSummarySnapshot) {

                // If noVat is set, the VAT is 0%
                if (param.getNoVatRef() != null) {
                    discountVatDescription = param.getNoVatRef().getDescription();
                    discountVatPercent = NumberUtils.DOUBLE_ZERO;
                } else {
                    discountVatDescription = vatSummaryItem.getVatName();
                    discountVatPercent = vatSummaryItem.getVatPercent();
                }

                // Calculate the ratio of this vat summary item and all items.
                // The discountNetPart is proportional to this ratio.
                MonetaryAmount discountNetPart = Money.from(zero);
                if (!itemsNet.isZero()) {
                    discountNetPart = discountNet.multiply(vatSummaryItem.getNet().divide(itemsNet.getNumber()).getNumber());
                }

                // Add discountNetPart to the sum "discountVatValue"  
                final Price discountPart = new Price(discountNetPart, discountVatPercent,
                        Optional.ofNullable(vatSummaryItem.getSalesEqTaxPercent()).orElse(NumberUtils.DOUBLE_ZERO));
                discountVatValue = discountVatValue.add(discountPart.getUnitVat()).add(discountPart.getTotalSalesEqTax());

                final VatSummaryItem discountVatSummaryItem = new VatSummaryItem(discountVatDescription, discountVatPercent, vatSummaryItem.getVatCode(),
                        discountPart.getUnitNet(), discountPart.getUnitVat(), vatSummaryItem.getDescription());
                if (this.useSET && !vatSummaryItem.getSalesEqTax().isZero()) {
                    discountVatSummaryItem.setSalesEqTax(discountPart.getTotalSalesEqTaxRounded());
                    discountVatSummaryItem.setSalesEqTaxPercent(vatSummaryItem.getSalesEqTaxPercent());
                }

                // Adjust the vat summary item by the discount part
                retval.getVatSummary().add(discountVatSummaryItem);
            }

            // adjust the documents sum by the discount
            retval.addToTotalNet(discountNet);

            if (param.getNetGross() != DocumentSummary.ROUND_GROSS_VALUES) {
                retval.setTotalGross(retval.getTotalNet().add(retval.getTotalVat().add(retval.getTotalSET())));
            } else {

                // round to full gross cents
                retval.addToTotalGross(retval.getDiscountGross());
                retval.setDiscountNet(retval.getDiscountGross().subtract(discountVatValue));
                retval.setTotalNet(retval.getTotalGross().subtract(retval.getTotalVat()).subtract(retval.getTotalSET()));
            }
        }
    }

    private VatSummaryItem createVatSummaryItem(final DocumentSummaryParam param, final DocumentItem item, final Price price) {

        // Get the data from each item
        final VAT itemVat = getItemVat(item);
        String vatDescription = itemVat.getDescription();
        Double vatPercent = itemVat.getTaxValue();
        final UNTDID5305 vatCode = itemVat.getCode();
        MonetaryAmount itemVatAmount = price.getTotalVat();

        // If noVat is set, the VAT is 0%
        if (param.getNoVatRef() != null) {
            vatDescription = Objects.toString(param.getNoVatRef().getDescription(), param.getNoVatRef().getName());
            vatPercent = param.getNoVatRef().getTaxValue();
            itemVatAmount = Money.zero(getCurrencyCode());
        }
        final boolean useGross = isUseGross(param);

        final VatSummaryItem vatSummaryItem = new VatSummaryItem(itemVat.getName(), vatPercent, vatCode,
                useGross ? price.getUnitGrossDiscounted().subtract(price.getUnitVatDiscounted()).subtract(price.getUnitSalesEqTaxDiscounted())
                        .multiply(price.getQuantity()) : price.getTotalNet(), itemVatAmount, Objects.toString(vatDescription, itemVat.getName()));

        if (itemVat != null && this.useSET && itemVat.getSalesEqualizationTax() != null && !item.getNoVat()) {
            final double taxValue = DataUtils.getInstance().round(itemVat.getSalesEqualizationTax(),
                    defaultValuePrefs.getInt(Constants.PREFERENCES_GENERAL_CURRENCY_DECIMALPLACES) + 3);
            vatSummaryItem.setSalesEqTaxPercent(taxValue);
        }
        return vatSummaryItem;
    }

    private boolean isUseGross(final DocumentSummaryParam param) {
        boolean useGross;
        useGross = param.getNetGross() == DocumentSummary.ROUND_NOTSPECIFIED
                ? defaultValuePrefs.getInt(Constants.PREFERENCES_DOCUMENT_USE_NET_GROSS) == DocumentSummary.ROUND_NET_VALUES
                : param.getNetGross() == DocumentSummary.ROUND_GROSS_VALUES;
        return useGross;
    }

    private VAT getItemVat(final DocumentItem item) {
        VAT retval = item.getItemVat();
        if (retval == null && item.getProduct() != null) {
            retval = item.getProduct().getVat();
        }
        return retval;
    }

    /**
     * @return the useSET
     */
    public final boolean isUseSET() {
        return useSET;
    }

    /**
     * @param useSET
     *            the useSET to set
     */
    public final void setUseSET(final boolean useSET) {
        this.useSET = useSET;
    }

    private CurrencyUnit getCurrencyCode() {
        if (currencyCode == null) {
            currencyCode = DataUtils.getInstance().getDefaultCurrencyUnit();
        }
        return currencyCode;
    }

}
