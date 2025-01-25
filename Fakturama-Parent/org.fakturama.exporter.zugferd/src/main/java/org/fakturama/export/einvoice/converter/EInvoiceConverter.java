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
import org.fakturama.export.einvoice.model.AddressData;
import org.fakturama.export.einvoice.model.EInvoice;
import org.fakturama.export.einvoice.model.InvoiceBuyer;
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
import com.sebulli.fakturama.dao.ContactsDAO;
import com.sebulli.fakturama.dto.DocumentSummary;
import com.sebulli.fakturama.dto.Price;
import com.sebulli.fakturama.dto.Transaction;
import com.sebulli.fakturama.dto.VatSummaryItem;
import com.sebulli.fakturama.dto.VatSummarySetManager;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.misc.UNTDID4461;
import com.sebulli.fakturama.model.Address;
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

    public EInvoiceConverter(final IEclipseContext eclipseContext, final IPreferenceStore preferences, final ContactsDAO contactsDAO,
            final ContactUtil contactUtil, final IDocumentAddressManager addressManager) {
        this.eclipseContext = eclipseContext;
        this.preferences = preferences;
        this.contactUtil = contactUtil;
        this.addressManager = addressManager;
        this.contactsDAO = contactsDAO;

    }

    public EInvoice postProcess(final EInvoice eInvoice, final ConformanceLevel zugferdProfile) {
        switch (zugferdProfile) {
            case XRECHNUNG:
                eInvoice.getInvoiceData().setBusinessProcessType("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0");
                eInvoice.getInvoiceData().setSpecificationIdentifier("urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0");
                break;
            case FACTURX_EN16931, ZUGFERD_V2_EN16931:
                eInvoice.getInvoiceData().setBusinessProcessType(null);
                eInvoice.getInvoiceData().setSpecificationIdentifier("urn:cen.eu:en16931:2017");
                break;
        }
        return eInvoice;
    }

    public EInvoice convertInvoice(final Invoice invoice) throws InvoiceConverterException {

        try {
            final EInvoice eInvoice = new EInvoice();
            setInvoiceData(eInvoice, invoice);
            setInvoiceSeller(eInvoice, invoice);
            setInvoiceNote(eInvoice, invoice);
            setInvoiceBuyer(eInvoice, invoice);
            setInvoiceDeliveryInformation(eInvoice, invoice);
            setImvoicePayments(eInvoice, invoice);
            setInvoicePositions(eInvoice, invoice);
            setInvoiceTotals(eInvoice, invoice);
            setInvoiceVatBreakdowns(eInvoice, invoice);
            return eInvoice;
        } catch (final Exception e) {
            throw new InvoiceConverterException(e);
        }
    }

    /**
     * 
     * @param eInvoice
     * @param invoice
     */
    private void setInvoiceTotals(final EInvoice eInvoice, final Invoice invoice) {
        final InvoiceDocumentTotals documentTotals = eInvoice.getInvoiceDocumentTotals();

    }

    /**
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
            invoiceVatBreakdown.setVatCategoryCode(vatSummaryItem.getVatPercent() == 0 ? "Z" : "S");
            invoiceVatBreakdown.setVatCategoryRate(
                    BigDecimal.valueOf(vatSummaryItem.getVatPercent()).multiply(BigDecimal.valueOf(100), new MathContext(2)).stripTrailingZeros());
            invoiceVatBreakdown.setVatCategoryTaxableAmount(BigDecimal.valueOf(basisAmount.getNumber().doubleValueExact()));
            invoiceVatBreakdown
                    .setVatCategoryTaxAmount(BigDecimal.valueOf(basisAmount.multiply(vatSummaryItem.getVatPercent()).getNumber().doubleValueExact()));
            vatBreakdowns.add(invoiceVatBreakdown);
        }
    }

    /**
     * @param eInvoice
     * @param invoice
     */
    private void setInvoicePositions(final EInvoice eInvoice, final Invoice invoice) {
        final List<InvoicePosition> invoicePositions = eInvoice.getInvoicePositions();
        for (final DocumentItem invoiceItem : invoice.getItems()) {
            final InvoicePosition position = new InvoicePosition();
            position.setInvoiceLineIdentifier(StringUtils.trimToNull(invoiceItem.getPosNr().toString()));
            position.setItemSellersIdentifier(StringUtils.trimToNull(invoiceItem.getItemNumber()));
            position.setItemName(StringUtils.trimToNull(invoiceItem.getName()));
            position.setItemDescription(StringUtils.trimToNull(invoiceItem.getDescription()));
            position.setItemCountryOfOrigin("DE");

            // VAT
            position.setInvoicedItemVatRate(
                    BigDecimal.valueOf(invoiceItem.getItemVat().getTaxValue()).multiply(BigDecimal.valueOf(100), new MathContext(2)).stripTrailingZeros());
            position.setInvoicedItemVatCategoryCode(invoiceItem.getItemVat().getTaxValue() > 0 ? "S" : "Z"); // see UNTDID 5305

            position.setInvoicedQuantity(BigDecimal.valueOf(invoiceItem.getQuantity()));
            position.setInvoicedQuantityUnitOfMeasureCode(invoiceItem.getQuantityUnit());

            // Rechnungszeiträume
            final InvoiceLinePeriod period = new InvoiceLinePeriod();
            boolean periodToAdd = false;
            if (invoiceItem.getVestingPeriodStart() != null) {
                period.setInvoiceLinePeriodStartDate(LocalDate.ofInstant(invoiceItem.getVestingPeriodStart().toInstant(), ZONE_ID_UTC));
                periodToAdd = true;
            }
            if (invoiceItem.getVestingPeriodEnd() != null) {
                period.setInvoiceLinePeriodEndDate(LocalDate.ofInstant(invoiceItem.getVestingPeriodEnd().toInstant(), ZONE_ID_UTC));
                periodToAdd = true;
            }

            if (periodToAdd) {
                position.setInvoiceLinePeriod(period);
            }
            final Price price = new Price(BooleanUtils.isTrue(invoiceItem.getOptional()) ? Double.valueOf(0.0) : invoiceItem.getQuantity(),
                    Money.of(invoiceItem.getPrice() * 1, DataUtils.getInstance().getDefaultCurrencyUnit()), invoiceItem.getItemVat().getTaxValue(),
                    invoiceItem.getItemRebate(), BooleanUtils.toBoolean(invoiceItem.getNoVat()), false, null);
            position.setItemPriceDiscount(BigDecimal.valueOf(price.getUnitNetDiscounted().getNumber().doubleValue()).setScale(2, RoundingMode.HALF_UP));
            position.setItemGrossPrice(BigDecimal.valueOf(price.getUnitGross().getNumber().doubleValue()).setScale(2, RoundingMode.HALF_UP));
            position.setItemNetPrice(BigDecimal.valueOf(price.getUnitNet().getNumber().doubleValue()).setScale(2, RoundingMode.HALF_UP));

            invoicePositions.add(position);
        }

    }

    /**
     * @param eInvoice
     * @param invoice
     * @throws UnsupportedCodeException
     */
    private void setImvoicePayments(final EInvoice eInvoice, final Invoice invoice) throws UnsupportedCodeException {
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

        switch (code) {
            case VALUE_10:
                // Barzahlung
                // keine weiteren Felder zu füllen
                break;
            case VALUE_30:
                // VISA Zahlung
                // erstmal nix weiter
                break;
            case VALUE_31:
                // EC Zahlung
                // erstmal nix weiter
                break;
            case VALUE_58:
                // SEPA Überweisung
                // IBAN-Daten vom Bankkonto
                final List<InvoiceCreditTransfer> listInvoiceCreditTransfers = new ArrayList<>();
                final InvoiceCreditTransfer invoiceCreditTransfer = new InvoiceCreditTransfer();
                listInvoiceCreditTransfers.add(invoiceCreditTransfer);
                invoiceCreditTransfer.setPaymentAccountIdentifier(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN));
                invoiceCreditTransfer.setPaymentServiceProviderIdentifier(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BIC));
                invoiceCreditTransfer.setPaymentAccountName(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME));
                payment.setInvoiceCreditTransfers(listInvoiceCreditTransfers);
                break;
            case VALUE_59:
                // SEPA Lastschrift
                // TODO: Mandatsref und Gläubiger-ID
                break;
            case VALUE_68:
                // Onlinezahlung
                // nix weiter nötig
                break;
            case VALUE_ZZZ:
                // Sonstige Zahlungsweise, unbekannt
            default:
                throw new UnsupportedCodeException("Code " + code.getCode() + "is not supported");
        }
        payment.setPaymentMeansTypeCode(code.getCode());
        // Vorerst Translation key, später Übersetzung
        payment.setPaymentMeansText(code.getTranslationKey());

        // Zahlungsbedingungen hier erstellen

    }

    /**
     * @param eInvoice
     * @param invoice
     */
    private void setInvoiceDeliveryInformation(final EInvoice eInvoice, final Invoice invoice) {
        final InvoiceDeliveryInformation invoiceDeliveryInformation = eInvoice.getInvoiceDeliveryInformation();
        if (invoiceDeliveryInformation == null) {
            return;
        }
        if (invoice.getServiceDate() != null) {
            invoiceDeliveryInformation.setActualDeliveryDate(LocalDate.ofInstant(invoice.getServiceDate().toInstant(), EInvoiceConverter.ZONE_ID_UTC));
        }
        // for now, same as invoice address
        invoiceDeliveryInformation.setDeliveryAddress(eInvoice.getInvoiceBuyer().getBuyerAddress());
    }

    /**
     * @param eInvoice
     * @param invoice
     */
    private void setInvoiceBuyer(final EInvoice eInvoice, final Invoice invoice) {

        final DocumentReceiver billingAddress = addressManager.getBillingAdress(invoice);

        final InvoiceBuyer invoiceBuyer = eInvoice.getInvoiceBuyer();
        final AddressData address = invoiceBuyer.getBuyerAddress();

        // attention! Mind the manualAddress!
        if (billingAddress.getManualAddress() == null) {
            address.setPostCode(billingAddress.getZip());
            address.setAddressLine1(billingAddress.getStreet());
            //      retval.setLineTwo(is empty at the moment)
            //      retval.setLineThree(is empty at the moment);
            address.setCity(billingAddress.getCity());
            address.setCountryCode(billingAddress.getCountryCode()); // Nur die Alpha-2 Darstellung darf verwendet werden
            //      retval.setCountrySubDivisionName(null)

        } else {
            final Address addressFromString = contactUtil.createAddressFromString(billingAddress.getManualAddress());
            address.setPostCode(addressFromString.getZip());
            address.setAddressLine1(addressFromString.getStreet());
            //      retval.setLineTwo(is empty at the moment)
            //      retval.setLineThree(is empty at the moment);
            address.setCity(addressFromString.getCity());
            address.setCountryCode(addressFromString.getCountryCode()); // Nur die Alpha-2 Darstellung darf verwendet werden
            //      retval.setCountrySubDivisionName(null)

        }
        // get ID for buyer Contact
        final Long originContactId = (invoice.getReceiver() != null && !invoice.getReceiver().isEmpty()) ? invoice.getReceiver().get(0).getOriginContactId()
                : null;
        String debtorId = billingAddress.getCustomerNumber();
        String schemaId = null;
        String globalId = null;
        if (originContactId != null) {
            final Contact originContact = contactsDAO.findById(originContactId);
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
        invoiceBuyer.setBuyerIdentifier(Objects.toString(globalId, debtorId));
        invoiceBuyer.setBuyerIdentifierSchemeIdentifier(globalId != null ? schemaId : null);
        invoiceBuyer.setBuyerVatIdentifier(billingAddress.getVatNumber());
    }

    private String grepFromNoteField(final String noteField, final Pattern searchPattern, final String defaultString) {
        // try to find a global schema id from note field (workaround)
        final Matcher m = searchPattern.matcher(noteField);
        final boolean matches = noteField.matches("Global.*");
        return matches ? m.group(1) : defaultString;
    }

    /**
     * @param eInvoice
     * @param invoice
     */
    private void setInvoiceNote(final EInvoice eInvoice, final Invoice invoice) {
        final List<InvoiceNote> invoiceNote = eInvoice.getInvoiceNotes();
        if (StringUtils.trimToNull(invoice.getMessage()) != null) {
            final InvoiceNote note = new InvoiceNote();
            note.setInvoiceNote(StringUtils.trimToEmpty(invoice.getMessage()));
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

    private void setInvoiceData(final EInvoice eInvoice, final Invoice invoice) throws InvoiceConverterException {
        final InvoiceData invoiceData = eInvoice.getInvoiceData();
        invoiceData.setInvoiceNumber(StringUtils.trimToNull(invoice.getName()));
        invoiceData.setInvoiceIssueDate(LocalDate.ofInstant(invoice.getDocumentDate().toInstant(), EInvoiceConverter.ZONE_ID_UTC));

        invoiceData.setInvoiceTypeCode(getDocumentTypeCode(invoice));
        invoiceData.setInvoiceCurrencyCode(getGlobalCurrencyCode());

        // only set if other than above
        invoiceData.setVatAccountingCurrencyCode(null);
        // one or other, we use code as default here
        invoiceData.setValueAddedTaxPointDate(null);
        /*
         * 3 (Invoice document issue date time)
         * 35 (Delivery date/time, actual)
         * 432 (Paid to date)
         */
        invoiceData.setValueAddedTaxPointDateCode("3");

        final LocalDateTime dueDate = DataUtils.getInstance().addToDate(invoice.getDocumentDate(), invoice.getDueDays());
        invoiceData.setPaymentDueDate(dueDate.toLocalDate());
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
            invoiceData.setPurchaseOrderReference(transaction.getReference(DocumentType.ORDER));
        }
        // if a useable text is available, use it, else let it be empty (For now just old stuff, later rebuild this!)
        final TemplateProcessor placeholders = ContextInjectionFactory.make(TemplateProcessor.class, eclipseContext);
        final DocumentSummaryCalculator documentSummaryCalculator = ContextInjectionFactory.make(DocumentSummaryCalculator.class, eclipseContext);
        final DocumentSummary documentSummary = documentSummaryCalculator.calculate(invoice);
        final double percent = invoice.getPayment().getDiscountValue();

        final Optional<String> paymentText = Optional.ofNullable(placeholders.createPaymentText(invoice, Optional.ofNullable(documentSummary), percent));

        invoiceData.setPaymentTerms(paymentText.orElse(null));
        if (invoice.getVestingPeriodStart() != null) {
            invoiceData.setInvoicingPeriodStartDate(LocalDate.ofInstant(invoice.getVestingPeriodStart().toInstant(), ZONE_ID_UTC));
        }
        if (invoice.getVestingPeriodEnd() != null) {
            invoiceData.setInvoicingPeriodEndDate(LocalDate.ofInstant(invoice.getVestingPeriodEnd().toInstant(), ZONE_ID_UTC));
        }
    }

    private void setInvoiceSeller(final EInvoice eInvoice, final Invoice invoice) throws InvoiceConverterException {
        final InvoiceSeller invoiceSeller = eInvoice.getInvoiceSeller();
        invoiceSeller.setSellerElectronicAddressSchemeIdentifier("EM"); // always EMail
        invoiceSeller.setSellerElectronicAddress(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_EMAIL));
        invoiceSeller.setSellerName(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME));
        final AddressData address = invoiceSeller.getSellerAddress();
        // Nur die Alpha-2 Darstellung darf verwendet werden
        address.setCountryCode(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_COUNTRY));
        address.setPostCode(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_ZIP));
        address.setAddressLine1(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_STREET));
        address.setCity(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CITY));

        // Contact Data BG-6
        invoiceSeller.setSellerContactEmailAddress(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_EMAIL)));
        invoiceSeller.setSellerContactTelephoneNumber(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TEL)));
        invoiceSeller.setSellerContactPoint(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER)));

        invoiceSeller.setSellerVatIdentifier(StringUtils.trimToNull(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR)));
        invoiceSeller.setSellerTaxRegistrationIdentifier(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TAXNR));
        invoiceSeller.setSellerTaxRegistrationIdentifierSchemeIdentifier("FC"); // Hardcoded Tax Number identifier
        // TODO: Handelsregisternummer
        invoiceSeller.setSellerLegalRegistrationIdentifier(null);
        // Schema nicht unterstützt von Fakturama (zur Zeit), Sinnvoll wäre 0189 - European Business Identifier (EBID)
        invoiceSeller.setSellerLegalRegistrationIdentifierSchemeIdentifier(null);
        if (!preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME).equalsIgnoreCase(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER))) {
            invoiceSeller.setSellerTradingName(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME));
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
