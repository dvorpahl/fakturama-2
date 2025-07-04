/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2024 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package org.fakturama.export.einvoice.converter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.money.MonetaryAmount;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.fakturama.export.einvoice.ConformanceLevel;
import org.fakturama.export.einvoice.ZFMessages;
import org.fakturama.export.einvoice.model.AddressData;
import org.fakturama.export.einvoice.model.EInvoice;
import org.fakturama.export.einvoice.model.InvoiceBuyer;
import org.fakturama.export.einvoice.model.InvoiceChargesAllowances;
import org.fakturama.export.einvoice.model.InvoiceCreditTransfer;
import org.fakturama.export.einvoice.model.InvoiceData;
import org.fakturama.export.einvoice.model.InvoiceDeliveryInformation;
import org.fakturama.export.einvoice.model.InvoiceDocumentTotals;
import org.fakturama.export.einvoice.model.InvoiceLinePeriod;
import org.fakturama.export.einvoice.model.InvoiceNote;
import org.fakturama.export.einvoice.model.InvoicePayment;
import org.fakturama.export.einvoice.model.InvoicePosition;
import org.fakturama.export.einvoice.model.InvoiceSeller;
import org.fakturama.export.einvoice.model.InvoiceVatBreakdown;
import org.javamoney.moneta.Money;

import com.sebulli.fakturama.calculate.DocumentSummaryCalculator;
import com.sebulli.fakturama.dao.CEFACTCodeDAO;
import com.sebulli.fakturama.dao.ContactsDAO;
import com.sebulli.fakturama.dto.DocumentSummary;
import com.sebulli.fakturama.dto.Price;
import com.sebulli.fakturama.dto.Transaction;
import com.sebulli.fakturama.dto.VatSummaryItem;
import com.sebulli.fakturama.dto.VatSummarySetManager;
import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.misc.UNTDID4461;
import com.sebulli.fakturama.model.Address;
import com.sebulli.fakturama.model.CEFACTCode;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.DocumentItem;
import com.sebulli.fakturama.model.DocumentReceiver;
import com.sebulli.fakturama.model.IDocumentAddressManager;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.office.TemplateProcessor;
import com.sebulli.fakturama.util.ContactUtil;
import com.sebulli.fakturama.util.DocumentTypeUtil;

/**
 * 
 */
public class EInvoiceConverter {

    /**
     * 
     */
    private static final ZoneId ZONE_ID_UTC = TimeZone.getTimeZone("UTC").toZoneId();
    // Define patterns
    private static final String PATTERN_DEBTOR = ".*?Debtor ID=(\\p{Alnum}+).*";
    private static final String PATTERN_GLOBAL_ID = ".*?Global ID=(\\p{Alnum}+).*";
    private static final String PATTERN_GLOBAL_SCHEME = ".*?Global schemeID=(\\d+).*";

    // Precompile Regex
    private static final Pattern CPATTERN_DEBTOR = Pattern.compile(PATTERN_DEBTOR, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static final Pattern CPATTERN_GLOBAL_ID = Pattern.compile(PATTERN_GLOBAL_ID, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static final Pattern CPATTERN_GLOBAL_SCHEME = Pattern.compile(PATTERN_GLOBAL_SCHEME, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private final IPreferenceStore preferences;
    private final IEclipseContext eclipseContext;
    private final IDocumentAddressManager addressManager;

    private final ContactUtil contactUtil;

    protected ContactsDAO contactsDAO;
    private DocumentSummary documentSummary;

    private final Messages msg;

    private final ILocaleService localeUtil;

    private final CEFACTCodeDAO measureUnits;

    private final ZFMessages zfMsg;

    private static final int MONEY_SCALE = 2;
    private final int customMoneyScale = 2;
    private final int customQuantityScale = 2;

    public EInvoiceConverter(final IEclipseContext eclipseContext, final IPreferenceStore preferences, final ContactsDAO contactsDAO,
            final ContactUtil contactUtil, final IDocumentAddressManager addressManager, final Messages msg, final ILocaleService localeUtil,
            final CEFACTCodeDAO measureUnits, final ZFMessages zfMsg) {
        this.eclipseContext = eclipseContext;
        this.preferences = preferences;
        this.contactUtil = contactUtil;
        this.addressManager = addressManager;
        this.contactsDAO = contactsDAO;
        this.msg = msg;
        this.localeUtil = localeUtil;
        this.measureUnits = measureUnits;
        this.zfMsg = zfMsg;
        // TODO: Scales einbauen

    }

    public EInvoice postProcess(final EInvoice eInvoice, final ConformanceLevel zugferdProfile) {
        switch (zugferdProfile) {
            case XRECHNUNG:
                // BT-23
                eInvoice.getInvoiceData().setBusinessProcessType("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0");
                // BT-24
                eInvoice.getInvoiceData().setSpecificationIdentifier("urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0");
                break;
            case FACTURX_EN16931, ZUGFERD_V2_EN16931:
                // BT-23
                eInvoice.getInvoiceData().setBusinessProcessType(null);
                // BT-24
                eInvoice.getInvoiceData().setSpecificationIdentifier("urn:cen.eu:en16931:2017");
                break;
        }
        return eInvoice;
    }

    public EInvoice convertInvoice(final Invoice invoice) throws InvoiceConverterException {

        try {
            final EInvoice eInvoice = new EInvoice();
            // BG-2 (post-process) (Y), BG-14 (Y), Wurzelelemente (Y)
            setInvoiceData(eInvoice, invoice);
            // BG-4 (Y), BG-5 (Y), BG-6 (Y), BG-10 (NA), BG-11 (NA), BG-12 (NA)
            setInvoiceSeller(eInvoice, invoice);
            // BG-1 (Y)
            setInvoiceNote(eInvoice, invoice);
            //BG-7 (Y), BG-8 (Y), BG-9 (Y)
            setInvoiceBuyer(eInvoice, invoice);
            // BG-13 (Y), BG-15 (Y)
            setInvoiceDeliveryInformation(eInvoice, invoice);
            // BG-16 (Y), BG-17 (Y), BG-18 (NA), BG-19 (NA)
            setInvoicePayments(eInvoice, invoice);
            // BG-20, BG-21
            setDocumentLevelAllowancesCharges(eInvoice, invoice);

            // BG-25 (Y), BG-26 (Y), BG-27 (N), BG-28 (N), BG-29 (Y), BG-30 (Y), BG-31 (Y), BG-32 (NA)
            setInvoicePositions(eInvoice, invoice);
            // BG-22 (Y)
            setInvoiceTotals(eInvoice, invoice);
            // BG-23 (Y)
            setInvoiceVatBreakdowns(eInvoice, invoice);
            // BG-3 (NA)
            // setPreceedingInvoice(eInvoice, invoice);
            // BG-24 (NA)
            // setAdditionalDocuments(eInvoice, invoice);
            return eInvoice;
        } catch (final Exception e) {
            throw new InvoiceConverterException(e);
        }
    }

    /**
     * BG-20, BG-21 TODO
     * 
     * @param eInvoice
     * @param invoice
     */
    private void setDocumentLevelAllowancesCharges(final EInvoice eInvoice, final Invoice invoice) {
        // FOR BG-20/BG-21
        // Abschläge / Zuschläge nur aufführen wenn sie auch tatsächlich angefallen sind! 

        if (invoice.getShipping() != null && documentSummary.getShippingNet() != null && !documentSummary.getShippingNet().isZero()) {
            final String shippingName = invoice.getShipping().getDescription();

            // we have shipping costs
            final InvoiceChargesAllowances invoiceChargesAllowances = new InvoiceChargesAllowances();
            invoiceChargesAllowances.setAmount(BigDecimal.valueOf(documentSummary.getShippingNet().getNumber().doubleValueExact()));
            invoiceChargesAllowances.setReason(shippingName);
            invoiceChargesAllowances.setVatRate(BigDecimal.valueOf(invoice.getShipping().getShippingVat().getTaxValue()));
            invoiceChargesAllowances.setVatCategoryCode("Z");
            eInvoice.getInvoiceCharges().add(invoiceChargesAllowances);
        }

        if (invoice.getItemsRebate() != null && !BigDecimal.valueOf(invoice.getItemsRebate()).equals(BigDecimal.ZERO)) {
            // we have global rebate
            final InvoiceChargesAllowances invoiceChargesAllowances = new InvoiceChargesAllowances();
            invoiceChargesAllowances.setAmount(BigDecimal.valueOf(documentSummary.getDiscountNet().getNumber().doubleValueExact()).abs());
            invoiceChargesAllowances.setReason(zfMsg.zugferdExportLabelRebate);
            invoiceChargesAllowances.setVatRate(BigDecimal.ZERO);
            invoiceChargesAllowances.setVatCategoryCode("Z");
            eInvoice.getInvoiceAllowances().add(invoiceChargesAllowances);
        }
    }

    /**
     * BG-22
     * 
     * @param eInvoice
     * @param invoice
     */
    private void setInvoiceTotals(final EInvoice eInvoice, final Invoice invoice) {
        final InvoiceDocumentTotals documentTotals = eInvoice.getInvoiceDocumentTotals();
        // not here: BT-111 (no other currency), 
        if (this.documentSummary != null) {
            // BT-106 TODO
            documentTotals.setSumOfInvoiceLineNetAmount(moneyToBigDecimal(documentSummary.getItemsNet()));
            // BT-107 TODO
            documentTotals.setSumOfAllowancesOnDocumentLevel(moneyToBigDecimal(documentSummary.getDiscountNet()).abs());
            // BT-108
            documentTotals.setSumOfChargesOnDocumentLevel(moneyToBigDecimal(documentSummary.getShippingNet()));
            // BT-109
            documentTotals.setInvoiceTotalAmountWithoutVat(moneyToBigDecimal(documentSummary.getTotalNet()));
            // BT-110
            documentTotals.setInvoiceTotalVatAmount(moneyToBigDecimal(documentSummary.getTotalVat()));
            // BT-112 TODO
            documentTotals.setInvoiceTotalAmountWithVat(moneyToBigDecimal(documentSummary.getTotalGross()));
            // BT-113 For now its Zero, to be determined later
            documentTotals.setPaidAmount(BigDecimal.ZERO);
            // BT-114 equals to BT-112 - (BT-106 + BT-110)
            documentTotals.setRoundingAmount(documentTotals.getInvoiceTotalAmountWithVat()
                    .subtract(documentTotals.getSumOfInvoiceLineNetAmount().add(documentTotals.getInvoiceTotalVatAmount())));

            // BT-115 TODO
            documentTotals.setAmountDueForPayment(documentTotals.getInvoiceTotalAmountWithVat());
        }

    }

    private BigDecimal moneyToBigDecimal(final MonetaryAmount money) {
        return BigDecimal.valueOf(money.getNumber().doubleValueExact());
    }

    /**
     * BG-23
     * 
     * VAT Rates
     * S (Standard rate)
     * Z (Zero rated goods)
     * E (Exempt from tax)
     * AE (VAT Reverse Charge)
     * K (VAT exempt for EEA intra-community supply of goods and services)
     * G (Free export item, tax not charged)
     * O (Services outside scope of tax)
     * L (Canary Islands general indirect tax)
     * M (Tax for production, services and importation in Ceuta and Melilla)
     * 
     * @param eInvoice
     * @param invoice
     */
    private void setInvoiceVatBreakdowns(final EInvoice eInvoice, final Invoice invoice) {
        // not here: BT-120, BT-121 (VAT excemption, to be done!)

        final List<InvoiceVatBreakdown> vatBreakdowns = eInvoice.getInvoiceVatBreakdowns();
        // Get the VAT summary of the UniDataSet document
        final VatSummarySetManager vatSummarySetManager = ContextInjectionFactory.make(VatSummarySetManager.class, eclipseContext);
        vatSummarySetManager.getVatSummaryItems().clear();
        vatSummarySetManager.add(invoice, Double.valueOf(1.0));
        for (final VatSummaryItem vatSummaryItem : vatSummarySetManager.getVatSummaryItems()) {
            final InvoiceVatBreakdown invoiceVatBreakdown = new InvoiceVatBreakdown();
            // für jeden Steuerbetrag muß es einen eigenen Eintrag geben
            final MonetaryAmount basisAmount = Optional.ofNullable(vatSummaryItem.getNet())
                    .orElse(Money.zero(DataUtils.getInstance().getDefaultCurrencyUnit()));
            // BT-118
            invoiceVatBreakdown.setVatCategoryCode(vatSummaryItem.getVatPercent() == 0 ? "Z" : "S");
            // BT-119
            invoiceVatBreakdown.setVatCategoryRate(
                    BigDecimal.valueOf(vatSummaryItem.getVatPercent()).multiply(BigDecimal.valueOf(100), new MathContext(2)).stripTrailingZeros());
            // BT-116
            invoiceVatBreakdown.setVatCategoryTaxableAmount(moneyToBigDecimal(basisAmount));
            // BT-117
            invoiceVatBreakdown.setVatCategoryTaxAmount(moneyToBigDecimal(basisAmount.multiply(vatSummaryItem.getVatPercent())));
            vatBreakdowns.add(invoiceVatBreakdown);
        }
    }

    /**
     * BG-25, BG-26, BG-27 (N), BG-28 (N), BG-29, BG-30, BG-31, BG-32
     * 
     * @param eInvoice
     * @param invoice
     */
    private void setInvoicePositions(final EInvoice eInvoice, final Invoice invoice) {
        final List<InvoicePosition> invoicePositions = eInvoice.getInvoicePositions();
        // not here: BG-25: BT-127 (NA, Invoice line note), BT-128 (NA, Invoice line object identifier), 
        // BT-132 (NA, Referenced purchase order line reference), BT-133 (NA, Invoice line Buyer accounting reference)
        // BG-29: BT-149 (NA, Item price base quantity), BT-150 (NA, Item price base quantity unit of measure code)
        // BG-31: BT-156 (NA, Item Buyers identifier), BT-157 (NA, Item standard identifier), BT-158 (NA, Item classification identifier)
        // BG-32: No use of it atm

        for (final DocumentItem invoiceItem : invoice.getItems()) {
            final InvoicePosition position = new InvoicePosition();
            // BT-126
            position.setInvoiceLineIdentifier(StringUtils.trimToNull(invoiceItem.getPosNr().toString()));

            // BT-129
            position.setInvoicedQuantity(BigDecimal.valueOf(invoiceItem.getQuantity()));
            // BT-130
            final String userdefinedQuantityUnit = invoiceItem.getQuantityUnit();
            String isoUnit = "";
            if (StringUtils.isNotBlank(userdefinedQuantityUnit)) {
                final Optional<CEFACTCode> code = measureUnits.findByAbbreviation(userdefinedQuantityUnit, localeUtil.getDefaultLocale());
                isoUnit = code.isPresent() ? code.get().getCode() : "";
            }
            position.setInvoicedQuantityUnitOfMeasureCode(isoUnit);

            // BT-151
            position.setInvoicedItemVatCategoryCode(invoiceItem.getItemVat().getTaxValue() > 0 ? "S" : "Z"); // see UNTDID 5305
            // BT-152 VAT
            position.setInvoicedItemVatRate(
                    BigDecimal.valueOf(invoiceItem.getItemVat().getTaxValue()).multiply(BigDecimal.valueOf(100), new MathContext(2)).stripTrailingZeros());
            // BT-153
            position.setItemName(StringUtils.trimToNull(invoiceItem.getName()));
            // BT-154
            position.setItemDescription(StringUtils.trimToNull(invoiceItem.getDescription()));
            // BT-155
            position.setItemSellersIdentifier(StringUtils.trimToNull(invoiceItem.getItemNumber()));
            // BT-159 (is not managed in fakturama)
            // position.setItemCountryOfOrigin("DE");

            // Rechnungszeiträume
            final InvoiceLinePeriod period = new InvoiceLinePeriod();
            boolean periodToAdd = false;
            if (invoiceItem.getVestingPeriodStart() != null) {
                // BT-134
                period.setInvoiceLinePeriodStartDate(LocalDate.ofInstant(invoiceItem.getVestingPeriodStart().toInstant(), ZONE_ID_UTC));
                periodToAdd = true;
            }
            if (invoiceItem.getVestingPeriodEnd() != null) {
                // BT-135
                period.setInvoiceLinePeriodEndDate(LocalDate.ofInstant(invoiceItem.getVestingPeriodEnd().toInstant(), ZONE_ID_UTC));
                periodToAdd = true;
            }

            if (periodToAdd) {
                position.setInvoiceLinePeriod(period);
            }
            final Price price = new Price(BooleanUtils.isTrue(invoiceItem.getOptional()) ? Double.valueOf(0.0) : invoiceItem.getQuantity(),
                    Money.of(invoiceItem.getPrice() * 1, DataUtils.getInstance().getDefaultCurrencyUnit()), invoiceItem.getItemVat().getTaxValue(),
                    invoiceItem.getItemRebate(), BooleanUtils.toBoolean(invoiceItem.getNoVat()), false, null);

            // BT-146 (= BT-148 - BT-147)
            position.setItemNetPrice(moneyToBigDecimal(price.getUnitNet()).setScale(2, RoundingMode.HALF_UP));
            // BT-147 (wont be used since we use allowances instead)
            //            position.setItemPriceDiscount(moneyToBigDecimal(price.getUnitNetDiscounted()).setScale(2, RoundingMode.HALF_UP));
            if (invoiceItem.getItemRebate() != null && !BigDecimal.ZERO.equals(invoiceItem.getItemRebate())) {
                // BG-27
                final InvoiceChargesAllowances invoiceChargesAllowances = new InvoiceChargesAllowances();
                // BT-138
                invoiceChargesAllowances.setPercentage(BigDecimal.valueOf(invoiceItem.getItemRebate()).multiply(BigDecimal.valueOf(100L)).abs());
                // BT-137
                invoiceChargesAllowances.setBaseAmount(BigDecimal.valueOf(price.getUnitNet().getNumber().doubleValueExact()));
                // BT-126
                invoiceChargesAllowances.setAmount(BigDecimal.valueOf(price.getTotalAllowance().getNumber().doubleValueExact()).abs());
                // BT-139
                invoiceChargesAllowances.setReason(zfMsg.zugferdExportLabelRebate);
                // BT-140
                invoiceChargesAllowances.setReasonCode("95");
                position.getInvoiceLineAllowances().add(invoiceChargesAllowances);

            }
            // BT-148
            position.setItemGrossPrice(moneyToBigDecimal(price.getUnitGross()).setScale(2, RoundingMode.HALF_UP));

            // BT-131 TODO (no vat, but all charges/allowances)
            position.setInvoiceLineNetAmount(BigDecimal.valueOf(price.getTotalNet().getNumber().doubleValueExact()));

            invoicePositions.add(position);
        }

    }

    /**
     * BG-16 (NA, Payment card), BG-17(Y, Credit Transfer),
     * BG-18(NA, Payment Card Information), BG-19 (NA, Direct Debit)
     * 
     * @param eInvoice
     * @param invoice
     * @throws UnsupportedCodeException
     */
    private void setInvoicePayments(final EInvoice eInvoice, final Invoice invoice) throws UnsupportedCodeException {
        // TODO Auto-generated method stub
        /*
         * Implement and set subs accordingly
         * Payment type code gem. "Payment Means Code" lt. Codeliste ZUGFeRD
         * 10 ... bar
         * 31 ... Payment by debit movement of funds from one account to
         * another. International Transfers
         * ==> SEPA-Überweisung!
         * 48 ... Bank card
         * 49 ... Direct debit (Lastschrift)
         * 
         */

        final InvoicePayment payment = eInvoice.getInvoicePayment();
        final UNTDID4461 code = UNTDID4461.getByCode(invoice.getPayment().getCode());

        if (code == null) {
            throw new UnsupportedCodeException("Payment Code not set");
        }

        switch (code) {
            case VALUE_10:
                // Barzahlung
                // keine weiteren Felder zu füllen
                break;
            case VALUE_30:
                // VISA Zahlung
                // erstmal nix weiter
                break;
            case VALUE_31, VALUE_48, VALUE_54, VALUE_55:
                // EC Zahlung, Bankkarte, kreditkarte, debitkarte
                // erstmal nix weiter
                break;
            case VALUE_58:
                // SEPA Überweisung
                // IBAN-Daten vom Bankkonto
                final List<InvoiceCreditTransfer> listInvoiceCreditTransfers = new ArrayList<>();
                final InvoiceCreditTransfer invoiceCreditTransfer = new InvoiceCreditTransfer();
                listInvoiceCreditTransfers.add(invoiceCreditTransfer);
                // BT-84
                invoiceCreditTransfer.setPaymentAccountIdentifier(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN));
                // BT-86
                invoiceCreditTransfer.setPaymentServiceProviderIdentifier(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BIC));
                // BT-85
                invoiceCreditTransfer.setPaymentAccountName(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME));
                payment.setInvoiceCreditTransfers(listInvoiceCreditTransfers);
                break;
            case VALUE_59:
                // SEPA Lastschrift & Lastschrift
                // TODO: Mandatsref und Gläubiger-ID
                break;
            case VALUE_68:
                // Onlinezahlung
                // nix weiter nötig
                break;
            case VALUE_ZZZ, VALUE_57:
                // Sonstige Zahlungsweise, unbekannt, Rahmenvertrag
                break;
            default:
                throw new UnsupportedCodeException("Code " + code.getCode() + " is not supported");
        }
        // BT-81
        payment.setPaymentMeansTypeCode(code.getCode());
        // Vorerst Translation key, später Übersetzung
        // BT-82
        payment.setPaymentMeansText(invoice.getPayment().getDescription());

        // BT-83: Use Invoice number as payment reference
        payment.setRemittanceInformation(eInvoice.getInvoiceData().getInvoiceNumber());
        // Zahlungsbedingungen hier erstellen

    }

    /**
     * BG-13, BG-15
     * 
     * @param eInvoice
     * @param invoice
     */
    private void setInvoiceDeliveryInformation(final EInvoice eInvoice, final Invoice invoice) {
        final InvoiceDeliveryInformation invoiceDeliveryInformation = eInvoice.getInvoiceDeliveryInformation();
        final DocumentReceiver deliveryAddr = addressManager.getDeliveryAdress(invoice);
        if (invoiceDeliveryInformation == null) {
            return;
        }
        if (invoice.getServiceDate() != null) {
            // BT-72
            invoiceDeliveryInformation.setActualDeliveryDate(LocalDate.ofInstant(invoice.getServiceDate().toInstant(), EInvoiceConverter.ZONE_ID_UTC));
        }

        if (deliveryAddr.getDeleted() != null && deliveryAddr.getDeleted().booleanValue()) {
            return;
        }

        // BT-70 Company of delivery address if differs from buyer
        if (eInvoice.getInvoiceBuyer().getBuyerName() != null && !eInvoice.getInvoiceBuyer().getBuyerName().equals(deliveryAddr.getCompany())) {
            invoiceDeliveryInformation.setDeliverToPartyName(deliveryAddr.getCompany());
        }

        // BT-71: name of place for delivery - not supported
        // for now, same as invoice address
        // BG-15 (Y)
        if (deliveryAddr != null) {
            // autoset all required fields
            final AddressData address = invoiceDeliveryInformation.getDeliveryAddress();
            getAdressDataForInvoiceAddress(deliveryAddr, address);
        }
    }

    /**
     * BG-7, BG-8
     * 
     * @param eInvoice
     * @param invoice
     */
    private void setInvoiceBuyer(final EInvoice eInvoice, final Invoice invoice) {

        // missing: BT-45 (Buyer trading name), BT-47, BT-47-1 (Handelsregistereintrag)
        // further missing: BT-51, bt-163 (Adresse 2 und 3), BT-54 (Bundesland)
        final DocumentReceiver billingAddress = addressManager.getBillingAdress(invoice);

        final InvoiceBuyer invoiceBuyer = eInvoice.getInvoiceBuyer();
        final AddressData address = invoiceBuyer.getBuyerAddress();
        // BT-44
        invoiceBuyer.setBuyerName(billingAddress.getName());

        // BT-48
        invoiceBuyer.setBuyerVatIdentifier(StringUtils.trimToNull(billingAddress.getVatNumber()));
        // BT-49
        invoiceBuyer.setBuyerElectronicAddress(StringUtils.trimToNull(billingAddress.getEmail()));
        // BT-49-1: Hardcoded Schema Email (EM, codelist urn:xoev-de:kosit:codeliste:eas_5)
        invoiceBuyer.setBuyerElectronicAddressSchemeIdentifier("EM");

        getAdressDataForInvoiceAddress(billingAddress, address);
        // BT-58
        invoiceBuyer.setBuyerContactEmailAddress(StringUtils.trimToNull(billingAddress.getEmail()));
        // BT-56
        invoiceBuyer.setBuyerContactPoint(StringUtils.trimToNull(billingAddress.getConsultant()));
        String phone = StringUtils.trimToNull(billingAddress.getPhone());
        if (phone == null) {
            phone = StringUtils.trimToNull(billingAddress.getMobile());
        }
        //BT-57
        invoiceBuyer.setBuyerContactTelephoneNumber(phone);

        // get ID for buyer Contact
        final Long originContactId = (invoice.getReceiver() != null && !invoice.getReceiver().isEmpty()) ? invoice.getReceiver().get(0).getOriginContactId()
                : null;
        String debtorId = billingAddress.getCustomerNumber();
        String schemaId = null;
        String globalId = null;
        Contact originContact = null;
        if (originContactId != null) {
            originContact = contactsDAO.findById(originContactId);
            schemaId = "0088";
            globalId = originContact.getGln() != null ? originContact.getGln().toString() : "";

            // additional fields from customer note takes precedence over "regular" fields 
            if (originContact.getNote() != null) {
                debtorId = grepFromNoteField(originContact.getNote(), CPATTERN_DEBTOR, debtorId);
                globalId = grepFromNoteField(originContact.getNote(), CPATTERN_GLOBAL_ID, globalId);
                schemaId = grepFromNoteField(originContact.getNote(), CPATTERN_GLOBAL_SCHEME, schemaId);
            }
        }
        // use global-ID if its not null, else id (with no schema)
        // BT-46
        invoiceBuyer.setBuyerIdentifier(Objects.toString(globalId, debtorId));
        // BT-46-1
        invoiceBuyer.setBuyerIdentifierSchemeIdentifier(globalId != null ? schemaId : null);

        // just a fallback
        if (StringUtils.trimToNull(invoiceBuyer.getBuyerIdentifier()) == null && originContact != null) {
            invoiceBuyer.setBuyerIdentifier(originContact.getCustomerNumber());
        }
    }

    private void getAdressDataForInvoiceAddress(final DocumentReceiver invoiceAddress, final AddressData address) {
        // attention! Mind the manualAddress!, first BT: Seller, second: Delivery
        if (invoiceAddress.getManualAddress() == null) {
            // BT-50, BT-75
            address.setAddressLine1(invoiceAddress.getStreet());
            // BT-51, BT-76
            //      retval.setLineTwo(is empty at the moment)
            // BT-162, BT-165
            //      retval.setLineThree(is empty at the moment);

            // BT-52, BT-77
            address.setCity(invoiceAddress.getCity());
            // BT-53, BT-78
            address.setPostCode(invoiceAddress.getZip());
            // BT-55, BT-80
            address.setCountryCode(getCountryCode(invoiceAddress.getCountryCode())); // Nur die Alpha-2 Darstellung darf verwendet werden
            // BT-54, BT-79
            //      retval.setCountrySubDivisionName(null)

        } else {
            final Address addressFromString = contactUtil.createAddressFromString(invoiceAddress.getManualAddress());
            address.setPostCode(addressFromString.getZip());
            address.setAddressLine1(addressFromString.getStreet());
            //      retval.setLineTwo(is empty at the moment)
            //      retval.setLineThree(is empty at the moment);
            address.setCity(addressFromString.getCity());
            address.setCountryCode(getCountryCode(addressFromString.getCountryCode())); // Nur die Alpha-2 Darstellung darf verwendet werden
            //      retval.setCountrySubDivisionName(null)

        }
    }

    private String getCountryCode(final String code) {
        String countryStr = code;
        if (StringUtils.length(countryStr) > 2) {
            countryStr = localeUtil.findCodeByDisplayCountry(countryStr, "DE");
        }
        return countryStr;
    }

    private String grepFromNoteField(final String noteField, final Pattern searchPattern, final String defaultString) {
        // try to find a global schema id from note field (workaround)
        final Matcher m = searchPattern.matcher(noteField);
        final boolean matches = noteField.matches("Global.*");
        return matches ? m.group(1) : defaultString;
    }

    /**
     * BG-1
     * 
     * @param eInvoice
     * @param invoice
     */
    private void setInvoiceNote(final EInvoice eInvoice, final Invoice invoice) {
        final List<InvoiceNote> invoiceNote = eInvoice.getInvoiceNotes();
        if (StringUtils.trimToNull(invoice.getMessage()) != null) {
            final InvoiceNote note = new InvoiceNote();
            // BT-22
            note.setInvoiceNote(StringUtils.trimToEmpty(invoice.getMessage()));
            // BT-21
            note.setInvoiceNoteSubjectCode("AAK"); // Information on the price conditions that are expected or given.
            invoiceNote.add(note);
        }
        if (StringUtils.trimToNull(invoice.getMessage2()) != null) {
            final InvoiceNote note = new InvoiceNote();
            note.setInvoiceNote(StringUtils.trimToEmpty(invoice.getMessage2()));
            note.setInvoiceNoteSubjectCode("AAK"); // Information on the price conditions that are expected or given.
            invoiceNote.add(note);
        }
        if (StringUtils.trimToNull(invoice.getMessage3()) != null) {
            final InvoiceNote note = new InvoiceNote();
            note.setInvoiceNote(StringUtils.trimToEmpty(invoice.getMessage3()));
            note.setInvoiceNoteSubjectCode("AAK"); // Information on the price conditions that are expected or given.
            invoiceNote.add(note);
        }
    }

    /**
     * BG-2, BG-14
     * 
     * @param eInvoice
     * @param invoice
     * @throws InvoiceConverterException
     */
    private void setInvoiceData(final EInvoice eInvoice, final Invoice invoice) throws InvoiceConverterException {

        // not here: BT-11, BT-12, BT-14, BT-15, BT-16, BT-17, BT-18, BT-19
        final InvoiceData invoiceData = eInvoice.getInvoiceData();
        // BT-1
        invoiceData.setInvoiceNumber(StringUtils.trimToNull(invoice.getName()));
        // BT-2
        invoiceData.setInvoiceIssueDate(LocalDate.ofInstant(invoice.getDocumentDate().toInstant(), EInvoiceConverter.ZONE_ID_UTC));

        //BT-3
        invoiceData.setInvoiceTypeCode(getDocumentTypeCode(invoice));
        //BT-5
        invoiceData.setInvoiceCurrencyCode(getGlobalCurrencyCode());

        // only set if other than above
        // BT-6 (we have always same currency)
        invoiceData.setVatAccountingCurrencyCode(null);
        // one or other, we use code as default here
        // BT-7 (must be null since we use BT-8)
        invoiceData.setValueAddedTaxPointDate(null);
        /*
         * 3 (Invoice document issue date time)
         * 35 (Delivery date/time, actual)
         * 432 (Paid to date)
         */
        // BT-8
        invoiceData.setValueAddedTaxPointDateCode("3");

        final LocalDateTime dueDate = DataUtils.getInstance().addToDate(invoice.getDocumentDate(), invoice.getDueDays());
        // BT-9
        invoiceData.setPaymentDueDate(dueDate.toLocalDate());
        // BT-10
        invoiceData.setBuyerReference(StringUtils.trimToNull(invoice.getCustomerRef()));

        /*
         * not available for now (Fakturama has no support for it)
         * invoiceData.setProjectReference();
         * invoiceData.setContractReference();
         * invoiceData.setSalesOrderReference();
         * invoiceData.setReceivingAdviceReference();
         * invoiceData.setDespatchAdviceReference();
         * invoiceData.setTenderOrLotReference();
         * invoiceData.setInvoicedObjectIdentifier();
         * invoiceData.setInvoicedObjectIdentifierSchemeIdentifier();
         * invoiceData.setBuyerAccountingReference();
         */
        final Transaction transaction = ContextInjectionFactory.make(Transaction.class, eclipseContext).of(invoice);
        if (transaction != null) {
            // BT-13
            invoiceData.setPurchaseOrderReference(transaction.getReference(DocumentType.ORDER));
        }
        // if a useable text is available, use it, else let it be empty (For now just old stuff, later rebuild this!)
        final TemplateProcessor placeholders = ContextInjectionFactory.make(TemplateProcessor.class, eclipseContext);
        final DocumentSummaryCalculator documentSummaryCalculator = ContextInjectionFactory.make(DocumentSummaryCalculator.class, eclipseContext);
        this.documentSummary = documentSummaryCalculator.calculate(invoice);
        final double percent = invoice.getPayment().getDiscountValue();

        // BT-20
        final Optional<String> paymentText = Optional.ofNullable(placeholders.createPaymentText(invoice, Optional.ofNullable(documentSummary), percent));

        invoiceData.setPaymentTerms(paymentText.orElse(null));
        if (invoice.getVestingPeriodStart() != null) {
            // BT-73
            invoiceData.setInvoicingPeriodStartDate(LocalDate.ofInstant(invoice.getVestingPeriodStart().toInstant(), ZONE_ID_UTC));
        }
        if (invoice.getVestingPeriodEnd() != null) {
            // BT-74
            invoiceData.setInvoicingPeriodEndDate(LocalDate.ofInstant(invoice.getVestingPeriodEnd().toInstant(), ZONE_ID_UTC));
        }
    }

    /**
     * BG-4, BG-5, BG-6, BG-10 (NA)
     * 
     * @param eInvoice
     * @param invoice
     * @throws InvoiceConverterException
     */
    private void setInvoiceSeller(final EInvoice eInvoice, final Invoice invoice) throws InvoiceConverterException {
        // not: BT-29 (NA), BT-29-1 (NA), BT-33 (NA),  BT-36(NA, address line 2), BT-162 (NA, address line 3), BT-39 (NA, Bundesland)
        final InvoiceSeller invoiceSeller = eInvoice.getInvoiceSeller();
        // BT-34-1
        invoiceSeller.setSellerElectronicAddressSchemeIdentifier("EM"); // always EMail
        // BT-34
        invoiceSeller.setSellerElectronicAddress(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_EMAIL));
        // BT-27
        invoiceSeller.setSellerName(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME));
        final AddressData address = invoiceSeller.getSellerAddress();
        // Nur die Alpha-2 Darstellung darf verwendet werden
        // BT-40
        address.setCountryCode(getCountryCode(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_COUNTRY)));
        // BT-38
        address.setPostCode(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_ZIP));
        // BT-35
        address.setAddressLine1(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_STREET));
        // BT-37
        address.setCity(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CITY));

        // Contact Data BG-6
        // BT-43
        invoiceSeller.setSellerContactEmailAddress(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_EMAIL)));
        // BT-42
        invoiceSeller.setSellerContactTelephoneNumber(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TEL)));
        // BT-41
        invoiceSeller.setSellerContactPoint(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER)));

        // BT-31
        invoiceSeller.setSellerVatIdentifier(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR)));
        // BT-32
        invoiceSeller.setSellerTaxRegistrationIdentifier(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TAXNR)));
        // BT-32-1
        invoiceSeller.setSellerTaxRegistrationIdentifierSchemeIdentifier("FC"); // Hardcoded Tax Number identifier (for local Tax number)
        // BT-30 - not supported at the moment
        invoiceSeller.setSellerLegalRegistrationIdentifier(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TRADE_REGISTER)));
        // BT-30-1
        invoiceSeller.setSellerLegalRegistrationIdentifierSchemeIdentifier("0189");
        if (!preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME).equalsIgnoreCase(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER))) {
            // BT-28
            invoiceSeller.setSellerTradingName(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME)));
        }

    }

    private String getGlobalCurrencyCode() {
        final String currency = DataUtils.getInstance().getDefaultCurrencyUnit().getCurrencyCode();
        return switch (currency) {
            case "€" -> "EUR";
            case "$" -> "USD";
            default -> currency;
        };
    }

    /**
     * 326 (Partial invoice)
     * 380 (Commercial invoice)
     * 384 (Corrected invoice)
     * 389 (Self-billed invoice)
     * 381 (Credit note)
     * 875 (Partial construction invoice)
     * 876 (Partial final construction invoice)
     * 877 (Final construction invoice)
     * 
     * @param invoice
     * @return
     * @throws InvoiceConverterException
     */
    private String getDocumentTypeCode(final Invoice invoice) throws InvoiceConverterException {
        final DocumentType documentType = DocumentTypeUtil.findByBillingType(invoice.getBillingType());

        if (documentType.getCode() == 0) {
            throw new InvoiceConverterException("Document Type not supported");
        }
        return String.valueOf(documentType.getCode());
    }
}
