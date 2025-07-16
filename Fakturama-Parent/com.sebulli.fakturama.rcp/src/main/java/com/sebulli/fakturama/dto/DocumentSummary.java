/*
 * Fakturama - Free Invoicing Software - http://fakturama.sebulli.com
 * 
 * Copyright (C) 2012 Gerd Bartelt
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Gerd Bartelt - initial API and implementation
 */

package com.sebulli.fakturama.dto;

import javax.inject.Inject;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import com.sebulli.fakturama.misc.DataUtils;

/**
 * Calculates the tax, gross and sum of one document. This is the central
 * calculation used by the document editors and the export functions.
 * 
 * @author Gerd Bartelt
 */
public class DocumentSummary {

    /*
     * TODO
     * auch mal ansehen (für Summenbildung):
     * 
     * /functional-example/src/main/java/org/javamoney/examples/console/
     * functional/MonetaryGroupOperations.java
     * 
     * hier klingt vor allem das interessant:
     * 
     * MonetarySummaryStatistics summary = getCurrencies().stream()
     * .filter(MonetaryFunctions.isCurrency(DOLLAR))
     * .collect(MonetaryFunctions.summarizingMonetary(DOLLAR));
     * 
     */

    /** The prices are not rounded to net or gross */
    public static final int ROUND_NOTSPECIFIED = 0;
    /** The prices are rounded, that the net values are full cent values. */
    public static final int ROUND_NET_VALUES = 1;
    /** The prices are rounded, that the gross values are full cent values. */
    public static final int ROUND_GROSS_VALUES = 2;

    // sum of items
    private MonetaryAmount itemsNet;
    private MonetaryAmount itemsNetDiscounted;
    private MonetaryAmount itemsGross;
    private MonetaryAmount itemsGrossDiscounted;

    // total sum
    private MonetaryAmount totalNet;
    private MonetaryAmount totalGross;
    private double totalQuantity;

    // discount values
    private MonetaryAmount discountNet;
    private MonetaryAmount discountGross;

    // shipping value
    private MonetaryAmount shippingNet;
    private MonetaryAmount shippingVat;
    private MonetaryAmount shippingGross;

    // deposit value
    private MonetaryAmount deposit;
    private MonetaryAmount finalPayment;

    private final CurrencyUnit currencyCode;

    private final VatSummarySet vatSummary;
    private final VatSummarySet shippingVatSummary;
    private final MonetaryRounding rounding;

    /**
     * Default constructor. Resets all value to 0.
     */
    @Inject
    public DocumentSummary(final IEclipseContext ctx) {
        final DataUtils dataUtils = ContextInjectionFactory.make(DataUtils.class, ctx);
        this.currencyCode = dataUtils.getDefaultCurrencyUnit();

        // This VAT summary contains only the VAT entries of this document,
        // whereas the the parameter vatSummaryItems is a global VAT summary
        // and contains entries from this document and from others.
        this.vatSummary = ContextInjectionFactory.make(VatSummarySet.class, ctx);
        this.shippingVatSummary = ContextInjectionFactory.make(VatSummarySet.class, ctx);
        rounding = dataUtils.getRounding();
        resetValues();
    }

    /**
     * Reset all values to 0
     */
    private void resetValues() {
        itemsNet = Money.zero(currencyCode);
        itemsGross = Money.zero(currencyCode);
        itemsNetDiscounted = Money.zero(currencyCode);
        itemsGrossDiscounted = Money.zero(currencyCode);
        totalNet = Money.zero(currencyCode);
        totalGross = Money.zero(currencyCode);
        discountNet = Money.zero(currencyCode);
        discountGross = Money.zero(currencyCode);
        shippingNet = Money.zero(currencyCode);
        shippingVat = Money.zero(currencyCode);
        shippingGross = Money.zero(currencyCode);
        deposit = Money.zero(currencyCode);
        finalPayment = Money.zero(currencyCode);
    }

    public void addPrice(final Price price, final Double quantity) {
        if (price != null && quantity != null) {
            addQuantity(quantity);
            addToItemsNet(price.getTotalNetRounded());
            addToItemsNetDiscounted(price.getTotalNet());
            addToItemsGross(price.getTotalGrossRounded());
            addToItemsGrossDiscounted(price.getUnitGrossDiscounted().multiply(price.getQuantity()));
        }
    }

    /**
     * Getter for shipping value (net)
     * 
     * @return shipping net as MonetaryAmount
     */
    public MonetaryAmount getShippingNet() {
        return this.shippingNet;
    }

    /**
     * Getter for shipping Vat value (Vat)
     * 
     * @return shipping Vat as MonetaryAmount
     */
    public MonetaryAmount getShippingVat() {
        return this.shippingVat;
    }

    /**
     * Getter for shipping value (gross)
     * 
     * @return shipping gross as MonetaryAmount
     */
    public MonetaryAmount getShippingGross() {
        return this.shippingGross;
    }

    /**
     * Getter for sum of items (net)
     * 
     * @return Sum as MonetaryAmount
     */
    public MonetaryAmount getItemsNet() {
        return this.itemsNet;
    }

    /**
     * Getter for sum of items (gross)
     * 
     * @return Sum as MonetaryAmount
     */
    public MonetaryAmount getItemsGross() {
        return this.itemsGross;
    }

    /**
     * Getter for total document sum (net)
     * 
     * @return Sum as MonetaryAmount
     */
    public MonetaryAmount getTotalNet() {
        return this.totalNet;
    }

    /**
     * Getter for total document sum (vat)
     * 
     * @return Sum as MonetaryAmount
     */
    public MonetaryAmount getTotalVat() {
        return this.vatSummary.parallelStream().map(VatSummaryItem::getVat).reduce(Money.zero(currencyCode), MonetaryFunctions::sum).with(rounding);
    }

    public MonetaryAmount getTotalVatRounded() {
        return this.vatSummary.parallelStream().map(VatSummaryItem::getVatRounded).reduce(Money.zero(currencyCode), MonetaryFunctions::sum).with(rounding);
    }

    public MonetaryAmount getTotalVatBase() {
        return this.vatSummary.parallelStream().map(VatSummaryItem::getNet).reduce(Money.zero(currencyCode), MonetaryFunctions::sum).with(rounding);
    }

    /**
     * @return the totalSET
     */
    public final MonetaryAmount getTotalSET() {
        return this.vatSummary.parallelStream().map(VatSummaryItem::getSalesEqTax).reduce(Money.zero(currencyCode), MonetaryFunctions::sum).with(rounding);
    }

    /**
     * Getter for total document sum (gross)
     * 
     * @return Sum as MonetaryAmount
     */
    public MonetaryAmount getTotalGross() {
        return this.totalGross;
    }

    /**
     * Getter for discount (net)
     * 
     * @return Sum as MonetaryAmount
     */
    public MonetaryAmount getDiscountNet() {
        return this.discountNet;
    }

    /**
     * Getter for discount (gross)
     * 
     * @return Sum as MonetaryAmount
     */
    public MonetaryAmount getDiscountGross() {
        return this.discountGross;
    }

    public MonetaryAmount getItemsNetDiscounted() {
        return itemsNetDiscounted;
    }

    public void setItemsNetDiscounted(final MonetaryAmount itemsNetDiscounted) {
        this.itemsNetDiscounted = itemsNetDiscounted;
    }

    public void addToItemsNetDiscounted(final MonetaryAmount itemsNetDiscounted) {
        this.itemsNetDiscounted = this.itemsNetDiscounted.add(itemsNetDiscounted);
    }

    public MonetaryAmount getItemsGrossDiscounted() {
        return itemsGrossDiscounted;
    }

    public void setItemsGrossDiscounted(final MonetaryAmount itemsGrossDiscounted) {
        this.itemsGrossDiscounted = itemsGrossDiscounted;
    }

    public void addToItemsGrossDiscounted(final MonetaryAmount itemsGrossDiscounted) {
        this.itemsGrossDiscounted = this.itemsGrossDiscounted.add(itemsGrossDiscounted);
    }

    /**
     * Getter for the deposit
     * 
     * @return Sum as MonetaryAmount
     */
    public MonetaryAmount getDeposit() {
        return this.deposit;
    }

    /**
     * Getter for the final payment
     * 
     * @return Sum as MonetaryAmount
     */
    public MonetaryAmount getFinalPayment() {
        return this.finalPayment;
    }

    /**
     * @param itemsNet
     *            the itemsNet to set
     */
    public void setItemsNet(final MonetaryAmount itemsNet) {
        this.itemsNet = itemsNet;
    }

    public void addToItemsNet(final MonetaryAmount itemsNet) {
        this.itemsNet = this.itemsNet.add(itemsNet);
    }

    /**
     * @param itemsGross
     *            the itemsGross to set
     */
    public void setItemsGross(final MonetaryAmount itemsGross) {
        this.itemsGross = itemsGross;
    }

    public void addToItemsGross(final MonetaryAmount itemsGross) {
        this.itemsGross = this.itemsGross.add(itemsGross);
    }

    /**
     * @param totalNet
     *            the totalNet to set
     */
    public void setTotalNet(final MonetaryAmount totalNet) {
        this.totalNet = totalNet;
    }

    public void addToTotalNet(final MonetaryAmount totalNet) {
        this.totalNet = this.totalNet.add(totalNet);
    }

    /**
     * @param totalGross
     *            the totalGross to set
     */
    public void setTotalGross(final MonetaryAmount totalGross) {
        this.totalGross = totalGross;
    }

    public void addToTotalGross(final MonetaryAmount totalGross) {
        this.totalGross = this.totalGross.add(totalGross);
    }

    /**
     * @param discountNet
     *            the discountNet to set
     */
    public void setDiscountNet(final MonetaryAmount discountNet) {
        this.discountNet = discountNet;
    }

    /**
     * @param discountGross
     *            the discountGross to set
     */
    public void setDiscountGross(final MonetaryAmount discountGross) {
        this.discountGross = discountGross;
    }

    /**
     * @param shippingNet
     *            the shippingNet to set
     */
    public void setShippingNet(final MonetaryAmount shippingNet) {
        this.shippingNet = shippingNet;
    }

    /**
     * @param shippingVat
     *            the shippingVat to set
     */
    public void setShippingVat(final MonetaryAmount shippingVat) {
        this.shippingVat = shippingVat;
    }

    public void addToShippingVat(final MonetaryAmount shippingVat) {
        this.shippingVat = this.shippingVat.add(shippingVat);
    }

    /**
     * @param shippingGross
     *            the shippingGross to set
     */
    public void setShippingGross(final MonetaryAmount shippingGross) {
        this.shippingGross = shippingGross;
    }

    /**
     * @param deposit
     *            the deposit to set
     */
    public void setDeposit(final MonetaryAmount deposit) {
        this.deposit = deposit;
    }

    /**
     * @param finalPayment
     *            the finalPayment to set
     */
    public void setFinalPayment(final MonetaryAmount finalPayment) {
        this.finalPayment = finalPayment;
    }

    /**
     * @return the totalQuantity
     */
    public double getTotalQuantity() {
        return totalQuantity;
    }

    public VatSummarySet getVatSummary() {
        return vatSummary;
    }

    public VatSummarySet getShippingVatSummary() {
        return shippingVatSummary;
    }

    public void addVatSummaryItem(final VatSummaryItem vatSummaryItem) {
        this.vatSummary.add(vatSummaryItem);
    }

    public void addShippingVatSummaryItem(final VatSummaryItem shippingVatSummaryItem) {
        this.shippingVatSummary.add(shippingVatSummaryItem);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void addQuantity(final Double quantity) {
        this.totalQuantity += quantity;
    }

    public void addToNetDiscount(final MonetaryAmount totalAllowance) {
        this.discountNet = this.discountNet.add(totalAllowance);
    }
}
