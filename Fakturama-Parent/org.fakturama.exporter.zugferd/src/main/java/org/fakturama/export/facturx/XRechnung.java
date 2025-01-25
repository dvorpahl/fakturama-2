/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2020 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package org.fakturama.export.facturx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.money.MonetaryAmount;

import org.apache.commons.lang3.StringUtils;
import org.fakturama.export.einvoice.model.AddressData;
import org.fakturama.export.einvoice.model.EInvoice;
import org.fakturama.export.einvoice.model.InvoiceBuyer;
import org.fakturama.export.einvoice.model.InvoiceData;
import org.fakturama.export.einvoice.model.InvoiceLinePeriod;
import org.fakturama.export.einvoice.model.InvoiceNote;
import org.fakturama.export.einvoice.model.InvoicePosition;
import org.fakturama.export.einvoice.model.InvoiceSeller;
import org.fakturama.export.einvoice.model.InvoiceVatBreakdown;
import org.fakturama.export.facturx.modelgen.AmountType;
import org.fakturama.export.facturx.modelgen.CodeType;
import org.fakturama.export.facturx.modelgen.CountryIDType;
import org.fakturama.export.facturx.modelgen.CreditorFinancialAccountType;
import org.fakturama.export.facturx.modelgen.CreditorFinancialInstitutionType;
import org.fakturama.export.facturx.modelgen.CrossIndustryInvoiceType;
import org.fakturama.export.facturx.modelgen.CurrencyCodeType;
import org.fakturama.export.facturx.modelgen.DateTimeType;
import org.fakturama.export.facturx.modelgen.DebtorFinancialAccountType;
import org.fakturama.export.facturx.modelgen.DocumentCodeType;
import org.fakturama.export.facturx.modelgen.DocumentContextParameterType;
import org.fakturama.export.facturx.modelgen.DocumentLineDocumentType;
import org.fakturama.export.facturx.modelgen.ExchangedDocumentContextType;
import org.fakturama.export.facturx.modelgen.ExchangedDocumentType;
import org.fakturama.export.facturx.modelgen.FormattedDateTimeType;
import org.fakturama.export.facturx.modelgen.HeaderTradeAgreementType;
import org.fakturama.export.facturx.modelgen.HeaderTradeDeliveryType;
import org.fakturama.export.facturx.modelgen.HeaderTradeSettlementType;
import org.fakturama.export.facturx.modelgen.IDType;
import org.fakturama.export.facturx.modelgen.IndicatorType;
import org.fakturama.export.facturx.modelgen.LegalOrganizationType;
import org.fakturama.export.facturx.modelgen.LineTradeAgreementType;
import org.fakturama.export.facturx.modelgen.LineTradeDeliveryType;
import org.fakturama.export.facturx.modelgen.LineTradeSettlementType;
import org.fakturama.export.facturx.modelgen.NoteType;
import org.fakturama.export.facturx.modelgen.ObjectFactory;
import org.fakturama.export.facturx.modelgen.PaymentMeansCodeType;
import org.fakturama.export.facturx.modelgen.PercentType;
import org.fakturama.export.facturx.modelgen.QuantityType;
import org.fakturama.export.facturx.modelgen.ReferencedDocumentType;
import org.fakturama.export.facturx.modelgen.SpecifiedPeriodType;
import org.fakturama.export.facturx.modelgen.SupplyChainEventType;
import org.fakturama.export.facturx.modelgen.SupplyChainTradeLineItemType;
import org.fakturama.export.facturx.modelgen.SupplyChainTradeTransactionType;
import org.fakturama.export.facturx.modelgen.TaxCategoryCodeType;
import org.fakturama.export.facturx.modelgen.TaxRegistrationType;
import org.fakturama.export.facturx.modelgen.TaxTypeCodeType;
import org.fakturama.export.facturx.modelgen.TextType;
import org.fakturama.export.facturx.modelgen.TradeAddressType;
import org.fakturama.export.facturx.modelgen.TradeAllowanceChargeType;
import org.fakturama.export.facturx.modelgen.TradeContactType;
import org.fakturama.export.facturx.modelgen.TradeCountryType;
import org.fakturama.export.facturx.modelgen.TradePartyType;
import org.fakturama.export.facturx.modelgen.TradePaymentTermsType;
import org.fakturama.export.facturx.modelgen.TradePriceType;
import org.fakturama.export.facturx.modelgen.TradeProductType;
import org.fakturama.export.facturx.modelgen.TradeSettlementHeaderMonetarySummationType;
import org.fakturama.export.facturx.modelgen.TradeSettlementLineMonetarySummationType;
import org.fakturama.export.facturx.modelgen.TradeSettlementPaymentMeansType;
import org.fakturama.export.facturx.modelgen.TradeTaxType;
import org.fakturama.export.facturx.modelgen.UniversalCommunicationType;
import org.javamoney.moneta.Money;

import com.sebulli.fakturama.dto.DocumentSummary;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.model.BankAccount;
import com.sebulli.fakturama.model.CEFACTCode;
import com.sebulli.fakturama.model.Document;

import jakarta.xml.bind.JAXBElement;

/**
 * Create an XRechnung XML.
 */
public class XRechnung extends AbstractEInvoice {

    @Override
    public JAXBElement<CrossIndustryInvoiceType> getInvoiceXml(final EInvoice eInvoice) {
        if (eInvoice == null) {
            return null;
        }
        factory = new ObjectFactory();
        // Recalculate the sum of the document before exporting

        final CrossIndustryInvoiceType root = new CrossIndustryInvoiceType();
        // at first create a reasonable context
        final DocumentContextParameterType ctxParam = factory.createDocumentContextParameterType();

        // Allgemein
        ctxParam.setID(createIdFromString(eInvoice.getInvoiceData().getSpecificationIdentifier()));

        final ExchangedDocumentContextType exchangedDocCtx = factory.createExchangedDocumentContextType();
        exchangedDocCtx.setGuidelineSpecifiedDocumentContextParameter(ctxParam);

        if (eInvoice.getInvoiceData().getBusinessProcessType() != null) {
            final DocumentContextParameterType documentContextParameterType = factory.createDocumentContextParameterType();
            documentContextParameterType.setID(createIdFromString(eInvoice.getInvoiceData().getBusinessProcessType()));
            exchangedDocCtx.setBusinessProcessSpecifiedDocumentContextParameter(documentContextParameterType);
        }

        root.setExchangedDocumentContext(exchangedDocCtx);

        // now the header information follows
        final ExchangedDocumentType exchangedDocumentType = factory.createExchangedDocumentType();
        exchangedDocumentType.setID(createIdFromString(eInvoice.getInvoiceData().getInvoiceNumber()));

        final DocumentCodeType docTypeCode = factory.createDocumentCodeType();
        docTypeCode.setValue(eInvoice.getInvoiceData().getInvoiceTypeCode());
        exchangedDocumentType.setTypeCode(docTypeCode);
        exchangedDocumentType.setIssueDateTime(createDateTime(eInvoice.getInvoiceData().getInvoiceIssueDate()));

        for (final InvoiceNote note : eInvoice.getInvoiceNotes()) {
            exchangedDocumentType.getIncludedNote().add(createNote(note));
        }
        root.setExchangedDocument(exchangedDocumentType);

        // now follows the huge part for trade transaction
        final SupplyChainTradeTransactionType tradeTransaction = factory.createSupplyChainTradeTransactionType();
        final HeaderTradeAgreementType tradeAgreement = factory.createHeaderTradeAgreementType();
        tradeAgreement.setBuyerReference(createText(eInvoice.getInvoiceData().getBuyerReference())); // "Kundenreferenz"
        tradeTransaction.setApplicableHeaderTradeAgreement(tradeAgreement);

        // create seller information
        tradeAgreement.setSellerTradeParty(createSeller(eInvoice));

        // create buyer information
        final TradePartyType buyer = createBuyer(eInvoice);
        if (buyer != null) {
            tradeAgreement.setBuyerTradeParty(buyer);
        }

        // Bestellung / referenced order
        if (eInvoice.getInvoiceData().getPurchaseOrderReference() != null) {
            final ReferencedDocumentType orderRef = factory.createReferencedDocumentType();
            orderRef.setIssuerAssignedID(createIdFromString(eInvoice.getInvoiceData().getPurchaseOrderReference()));
            // only if ID is not empty!
            if (!StringUtils.isEmpty(eInvoice.getInvoiceData().getPurchaseOrderReference())) {
                tradeAgreement.setBuyerOrderReferencedDocument(orderRef);
            }
        }

        if (eInvoice.getInvoiceDeliveryInformation() != null) {
            final SupplyChainEventType deliveryEvent = factory.createSupplyChainEventType();
            deliveryEvent.setOccurrenceDateTime(createDateTime(eInvoice.getInvoiceDeliveryInformation().getActualDeliveryDate()));
            final HeaderTradeDeliveryType headerTradeDeliveryType = factory.createHeaderTradeDeliveryType();
            headerTradeDeliveryType.setShipToTradeParty(buyer);
            headerTradeDeliveryType.setActualDeliverySupplyChainEvent(deliveryEvent);

            tradeTransaction.setApplicableHeaderTradeDelivery(headerTradeDeliveryType);
        }

        // Verwendungszweck, Kassenzeichen 
        final HeaderTradeSettlementType tradeSettlement = factory.createHeaderTradeSettlementType();
        //                .setCreditorReferenceID(createIdWithSchemeFromString(idString, scheme))
        tradeSettlement.setPaymentReference(createText(eInvoice.getInvoiceData().getInvoiceNumber()));
        //                .setTaxCurrencyCode(createCurrencyCode(currency))  // see ISO 4217
        final CurrencyCodeType currency = getGlobalCurrencyCode(eInvoice.getInvoiceData().getInvoiceCurrencyCode());
        tradeSettlement.setInvoiceCurrencyCode(currency);

        // TODO tradeSettlement.setPayeeTradeParty(value);   // Zahlungsempfänger 
        // TODO wieder rein
        //        final DocumentReceiver documentReceiver = addressManager.getBillingAdress(invoice);
        //        final Contact contact = getOriginContact(documentReceiver);
        TradeSettlementPaymentMeansType paymentType;
        //        if (contact != null) {
        //            final DebtorFinancialAccountType debtorAccount = createDebtorAccount(contact.getBankAccount());
        //
        paymentType = factory.createTradeSettlementPaymentMeansType();
        //            paymentType.setTypeCode(createPaymentTypeCode(eInvoice));
        //            paymentType.setInformation(createText(invoice.getPayment().getName()));
        //            //                    .setApplicableTradeSettlementFinancialCard(value)
        //            paymentType.setPayerPartyDebtorFinancialAccount(debtorAccount)
        //            //                                .setPaymentReference(createText(invoice.getName())) /* customerref ? */
        //            ;
        //
        //        } else {
        paymentType = factory.createTradeSettlementPaymentMeansType();
        //            paymentType.setTypeCode(createPaymentTypeCode(invoice));
        //            paymentType.setInformation(createText(invoice.getPayment().getName()));
        //        }
        final CreditorFinancialAccountType creditor = createCreditorAccount();
        if (creditor != null) {
            paymentType.setPayeePartyCreditorFinancialAccount(creditor);
            paymentType.setPayeeSpecifiedCreditorFinancialInstitution(createCreditorFinancialInstitution());
        }
        tradeSettlement.getSpecifiedTradeSettlementPaymentMeans().add(paymentType);

        // Get the items of the UniDataSet document
        eInvoice.getInvoicePositions().forEach(item -> tradeTransaction.getIncludedSupplyChainTradeLineItem().add(createLineItem(item)));

        // Detailinformationen zur Rechnungsperiode 
        final SpecifiedPeriodType billingSpecificPeriod = createBillingSpecificPeriod(eInvoice.getInvoiceData());
        tradeSettlement.setBillingSpecifiedPeriod(billingSpecificPeriod);

        // TODO tradeSettlement.setBillingSpecifiedPeriod(createPeriod(invoice));
        // Abschläge / Zuschläge nur aufführen wenn sie auch tatsächlich angefallen sind! 
        // TODO wieder ein
        //        if (Optional.ofNullable(invoice.getItemsRebate()).orElse(Double.valueOf(0.0)).compareTo(Double.valueOf(0.0)) != 0) {
        //            tradeSettlement.getSpecifiedTradeAllowanceCharge().add(createTradeAllowance(documentSummary, invoice));
        //        }
        //        // Hier kommen auch die Versandkosten mit rein 
        //        // (die sind nur bei EXTENDED in einem extra Node)
        //        if (invoice.getShipping() != null && invoice.getShipping().getShippingValue() > 0 || invoice.getShippingValue() > 0) {
        //            tradeSettlement.getSpecifiedTradeAllowanceCharge().add(createTradeAllowance(invoice));
        //        }
        //        tradeSettlement.getSpecifiedTradePaymentTerms().add(createTradePaymentTerms(invoice, documentSummary));
        //        tradeSettlement.setSpecifiedTradeSettlementHeaderMonetarySummation(createTradeSettlementMonetarySummation(invoice, documentSummary));

        // Get the VAT summary of the UniDataSet document

        for (final InvoiceVatBreakdown invoiceVatBreakdown : eInvoice.getInvoiceVatBreakdowns()) {
            // für jeden Steuerbetrag muß es einen eigenen Eintrag geben
            tradeSettlement.getApplicableTradeTax().add(createTradeTax(invoiceVatBreakdown, eInvoice.getInvoiceData().getInvoiceCurrencyCode()));
        }
        tradeTransaction.setApplicableHeaderTradeSettlement(tradeSettlement);
        root.setSupplyChainTradeTransaction(tradeTransaction);
        return new ObjectFactory().createCrossIndustryInvoice(root);
    }

    private TradePartyType createBuyer(final EInvoice eInvoice) {
        final InvoiceBuyer invoiceBuyer = eInvoice.getInvoiceBuyer();
        if (invoiceBuyer != null) {
            final TradePartyType buyer = factory.createTradePartyType();
            buyer.setName(createText(invoiceBuyer.getBuyerName()));
            buyer.setPostalTradeAddress(createAddress(eInvoice, ContactType.BUYER));
            if (invoiceBuyer.getBuyerIdentifierSchemeIdentifier() != null) {
                buyer.getGlobalID().add(createIdWithSchemeFromString(invoiceBuyer.getBuyerIdentifier(),
                        StringUtils.defaultString(invoiceBuyer.getBuyerIdentifierSchemeIdentifier())));
            } else {
                buyer.getID().add(createIdFromString(invoiceBuyer.getBuyerIdentifier()));
            }
            return buyer;
        }
        return null;

    }

    private TradePartyType createSeller(final EInvoice eInvoice) {
        final InvoiceSeller invoiceSeller = eInvoice.getInvoiceSeller();
        final UniversalCommunicationType email = factory.createUniversalCommunicationType();
        // EM = Electronic mail (SMPT)
        email.setURIID(createIdWithSchemeFromString(invoiceSeller.getSellerElectronicAddress(), invoiceSeller.getSellerElectronicAddressSchemeIdentifier()));

        final TradePartyType seller = factory.createTradePartyType();
        //              .setID(createIdFromString(""))  // Kennung des Verkäufers (Durch den Kunden zugewiesene Lieferantennummer)
        seller.setName(createText(invoiceSeller.getSellerName()));
        //              .setDescription(createText(""))  // Sonstige rechtliche Informationen des Verkäufers
        seller.getDefinedTradeContact().add(createContact(eInvoice, ContactType.SELLER));
        seller.setPostalTradeAddress(createAddress(eInvoice, ContactType.SELLER));
        seller.setURIUniversalCommunication(email);
        seller.getSpecifiedTaxRegistration().add(createTaxNumber(eInvoice, ContactType.SELLER));
        seller.setSpecifiedLegalOrganization(createLegalOrganization(eInvoice, ContactType.SELLER));
        //        if (preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR) == null) {
        //            seller.getGlobalID().add(createIdFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TAXNR)));
        //        } else {
        //            seller.getGlobalID().add(createIdWithSchemeFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR), "0088")); // "EAN" according to ISO 6523
        //        }

        return seller;
    }

    /**
     * Details zur Organisation des Verkäufers
     * 
     * @param invoice
     * @param seller
     * @return
     */
    private LegalOrganizationType createLegalOrganization(final EInvoice eInvoice, final ContactType seller) {
        final LegalOrganizationType retval = factory.createLegalOrganizationType();
        retval.setID(createIdFromString(eInvoice.getInvoiceSeller().getSellerLegalRegistrationIdentifier()));
        if (StringUtils.trimToNull(eInvoice.getInvoiceSeller().getSellerTradingName()) != null) { //!preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME).equalsIgnoreCase(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER))) {
            retval.setTradingBusinessName(createText(eInvoice.getInvoiceSeller().getSellerTradingName()));
        }
        return retval;

    }

    @Deprecated
    private LegalOrganizationType createLegalOrganizationType(final EInvoice eInvoice) {
        final LegalOrganizationType retval = factory.createLegalOrganizationType();
        retval.setID(createIdFromString("GTIN"));
        retval.setTradingBusinessName(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME)));
        return retval;
    }

    /**
     * @param invoice
     * @param exchangedDocumentType
     */
    private NoteType createNote(final InvoiceNote invoiceNote) {
        final NoteType note = factory.createNoteType(); // free text on header level
        note.setContent(createText(invoiceNote.getInvoiceNote()));
        note.setSubjectCode(createCode(invoiceNote.getInvoiceNoteSubjectCode())); // see UNTDID 4451

        return note;
    }

    /**
     * @return
     */
    private CurrencyCodeType getGlobalCurrencyCode(final String code) {
        final CurrencyCodeType cct = new CurrencyCodeType();
        cct.setValue(code);
        return cct;
    }

    /**
     * Gruppierung der Informationen zum Geschäftsvorfall
     * 
     * @param item
     * @return
     */
    private SupplyChainTradeLineItemType createLineItem(final InvoicePosition invoicePosition) {
        final SupplyChainTradeLineItemType retval = factory.createSupplyChainTradeLineItemType();
        retval.setAssociatedDocumentLineDocument(createDocumentLine(invoicePosition));
        retval.setSpecifiedTradeProduct(createTradeProduct(invoicePosition));
        retval.setSpecifiedLineTradeAgreement(createLineTradeAgreement(invoicePosition));
        retval.setSpecifiedLineTradeDelivery(createLineTradeDelivery(invoicePosition));
        retval.setSpecifiedLineTradeSettlement(createLineTradeSettlement(invoicePosition));

        return retval;
    }

    /**
     * Gruppierung von Angaben zum Produkt bzw. zur erbrachten Leistung. Eine
     * Gruppe von betriebswirtschaftlichen Begriffen, die Informationen über die
     * in Rechnung gestellten Waren und Dienstleistungen enthält.
     * 
     * @param item
     * @return
     */
    private TradeProductType createTradeProduct(final InvoicePosition invoicePosition) {
        final TradeProductType retval = factory.createTradeProductType();
        retval.setSellerAssignedID(createIdFromString(invoicePosition.getItemSellersIdentifier()));
        retval.setName(createText(invoicePosition.getItemName()));
        retval.setDescription(createText(invoicePosition.getItemDescription()));
        return retval;
    }

    @Deprecated
    private TradeCountryType createTradeCountry(final InvoicePosition invoicePosition) {
        final TradeCountryType retval = factory.createTradeCountryType();
        retval.setID(createCountry(invoicePosition.getItemCountryOfOrigin()));
        return retval;
    }

    /**
     * Gruppierung von Angaben zur Abrechnung auf Positionsebene
     * 
     * @param item
     * @return
     */
    private LineTradeSettlementType createLineTradeSettlement(final InvoicePosition invoicePosition) {
        final LineTradeSettlementType retval = factory.createLineTradeSettlementType();
        retval.getApplicableTradeTax().add(createTradeTax(invoicePosition));
        retval.setSpecifiedTradeSettlementLineMonetarySummation(createTradeSettlementLineMonetarySummation(invoicePosition))
        //               .setAdditionalReferencedDocument(null);
        //                .setReceivableSpecifiedTradeAccountingAccount(null)
        ;
        if (invoicePosition.getInvoiceLinePeriod() != null) {
            retval.setBillingSpecifiedPeriod(createBillingSpecificPeriod(invoicePosition.getInvoiceLinePeriod()));
        }

        if (invoicePosition.getItemPriceDiscount() != null && invoicePosition.getItemPriceDiscount().compareTo(BigDecimal.ZERO) != 0) {
            retval.getSpecifiedTradeAllowanceCharge().addAll(createAllowanceCharges(invoicePosition));
        }
        return retval;
    }

    private Collection<TradeAllowanceChargeType> createAllowanceCharges(final InvoicePosition invoicePosition) {
        final List<TradeAllowanceChargeType> charges = new ArrayList<>();
        final TradeAllowanceChargeType charge = createTradeAllowance(invoicePosition);
        charges.add(charge);
        return charges;
    }

    /**
     * Invoice Period on Position level
     * 
     * @param invoiceLinePeriod
     * @return the data or null
     */
    private SpecifiedPeriodType createBillingSpecificPeriod(final InvoiceLinePeriod invoiceLinePeriod) {
        final SpecifiedPeriodType retval = factory.createSpecifiedPeriodType();
        boolean set = false;
        if (invoiceLinePeriod.getInvoiceLinePeriodStartDate() != null) {
            retval.setStartDateTime(createDateTime(invoiceLinePeriod.getInvoiceLinePeriodStartDate()));
            set = true;
        }

        if (invoiceLinePeriod.getInvoiceLinePeriodEndDate() != null) {
            retval.setEndDateTime(createDateTime(invoiceLinePeriod.getInvoiceLinePeriodEndDate()));
            set = true;
        }
        return set ? retval : null;
    }

    /**
     * Invoice Period on document level
     * 
     * @param invoiceData
     * @return
     */
    private SpecifiedPeriodType createBillingSpecificPeriod(final InvoiceData invoiceData) {
        final SpecifiedPeriodType retval = factory.createSpecifiedPeriodType();
        if (invoiceData.getInvoicingPeriodStartDate() != null) {
            retval.setStartDateTime(createDateTime(invoiceData.getInvoicingPeriodStartDate()));
        }

        if (invoiceData.getInvoicingPeriodEndDate() != null) {
            retval.setEndDateTime(createDateTime(invoiceData.getInvoicingPeriodEndDate()));
        }
        return retval;
    }

    private LineTradeDeliveryType createLineTradeDelivery(final InvoicePosition itemPosition) {
        final String qunit = determineQuantityUnit(itemPosition.getInvoicedQuantityUnitOfMeasureCode());
        final LineTradeDeliveryType retval = factory.createLineTradeDeliveryType();
        retval.setBilledQuantity(createQuantity(itemPosition.getInvoicedQuantity(), qunit));
        return retval;
    }

    private QuantityType createQuantity(final BigDecimal value, final String unit) {
        final QuantityType retval = factory.createQuantityType();
        retval.setValue(value);
        // use a default value since this field shouldn't be empty
        retval.setUnitCode(StringUtils.isBlank(unit) ? "C62" : unit);
        return retval;
    }

    /**
     * Detailinformationen zum Preis. Eine Gruppe von betriebswirtschaftlichen
     * Begriffen, die Informationen über den Preis für die in der betreffenden
     * Rechnungsposition in Rechnung gestellten Waren und Dienstleistungen
     * enthält.
     * 
     * @param item
     * @return
     */
    private LineTradeAgreementType createLineTradeAgreement(final InvoicePosition itemPosition) {
        final LineTradeAgreementType retval = factory.createLineTradeAgreementType();
        //          .setBuyerOrderReferencedDocument(createBuyerOrderReferencedDocument(item))
        retval.setGrossPriceProductTradePrice(createTradePrice(itemPosition, PriceType.GROSS_PRICE));
        retval.setNetPriceProductTradePrice(createTradePrice(itemPosition, PriceType.NET_PRICE_DISCOUNTED));
        return retval;
    }

    /**
     * Detailangaben zur zugehörigen Bestellung
     * 
     * @param item
     * @return
     */
    private ReferencedDocumentType createBuyerOrderReferencedDocument(final InvoicePosition item) {
        // hier ist die Position in der zugehörigen Bestellung gemeint!
        final ReferencedDocumentType retval = factory.createReferencedDocumentType();
        retval.setLineID(createIdFromString(item.getInvoiceLineIdentifier()));
        return retval;
    }

    /**
     * Detailinformationen zum Bruttopreis des Artikels.
     * 
     * @param item
     * @param priceType
     * @return
     */
    private TradePriceType createTradePrice(final InvoicePosition item, final PriceType priceType) {

        TradePriceType retval = null;
        final int scale = preferences.getInt(Constants.PREFERENCES_GENERAL_CURRENCY_DECIMALPLACES);
        final BigDecimal discount = item.getItemPriceDiscount();
        switch (priceType) {
            case GROSS_PRICE:
                retval = factory.createTradePriceType();
                // "ITEM.UNIT.NET.DISCOUNTED" oder "ITEM.TOTAL.NET"?
                // Preis nach Bruttokalkulation *ohne* Umsatzsteuer(!!!) 
                retval.setChargeAmount(createAmount(Money.of(item.getItemGrossPrice(), "EUR"), scale))
                // Die Anzahl von Artikeleinheiten, für die der Preis gilt (Preisbasismenge ==> 1, 10, 100,...)
                //.setBasisQuantity(createQuantity(item.getProduct().getBlock1(), qunit))
                ;
                if (discount.compareTo(BigDecimal.ZERO) != 0) {
                    // Rabatt / Zuschlag auf Positionsebene
                    retval.getAppliedTradeAllowanceCharge().add(createTradeAllowance(item, false));
                }
                break;
            case NET_PRICE:

                retval = factory.createTradePriceType();
                // Preis nach Bruttokalkulation ohne Umsatzsteuer 
                retval.setChargeAmount(createAmount(Money.of(item.getItemNetPrice(), DataUtils.getInstance().getDefaultCurrencyUnit()), scale))
                // TODO Preisbasismenge??? (1, 10, 100,...)
                //          .setBasisQuantity(createQuantity(1d, qunit))
                ;
                if (discount.compareTo(BigDecimal.ZERO) != 0) {
                    // Rabatt / Zuschlag auf Positionsebene
                    retval.getAppliedTradeAllowanceCharge().add(createTradeAllowance(item));
                }
                break;
            case NET_PRICE_DISCOUNTED:
                // Detailinformationen zum Preis gemäß Nettokalkulation exklusive Umsatzsteuer
                retval = factory.createTradePriceType();
                // "ITEM.UNIT.NET.DISCOUNTED"
                // Preis nach Bruttokalkulation +- Zu-/Abschläge = Preis 
                // nach Nettokalkulation;
                // TODO Zeile wieder rein
                //                retval.setChargeAmount(createAmount(price.getUnitNetDiscounted(), scale))
                // TODO Preisbasismenge??? (1, 10, 100,...)
                //                .setBasisQuantity(createQuantity(1d, qunit))
                ;
                //          if(discount != 0) {
                //              // Rabatt / Zuschlag auf Positionsebene
                //              retval.getAppliedTradeAllowanceCharge().add(createTradeAllowance(item));
                //          }
                break;

            default:
                break;
        }
        return retval;
    }

    /**
     * Bestimmt aus der Mengeneinheit das entsprechende ISO-Kürzel. Umrechnung
     * entsprechend der von ZUGFeRD gelieferten Codeliste. TODO in einer
     * späteren Version sollten die Mengeneinheiten per Auswahlbox angelegt
     * werden.
     * 
     * @param userdefinedQuantityUnit
     * @return ISOquantityUnit
     */
    private String determineQuantityUnit(final String userdefinedQuantityUnit) {
        String isoUnit = "";
        if (StringUtils.isNotBlank(userdefinedQuantityUnit)) {
            final Optional<CEFACTCode> code = measureUnits.findByAbbreviation(userdefinedQuantityUnit, localeUtil.getDefaultLocale());
            isoUnit = code.isPresent() ? code.get().getCode() : "";
        }
        return isoUnit;
    }

    private DocumentLineDocumentType createDocumentLine(final InvoicePosition invoicePosition) {
        final DocumentLineDocumentType retval = factory.createDocumentLineDocumentType();
        retval.setLineID(createIdFromString(invoicePosition.getInvoiceLineIdentifier()));
        return retval;
    }

    /**
     * Detailinformationen zu Belegsummen.
     * 
     * @param invoice
     * @param documentSummary
     * @return
     */
    private TradeSettlementHeaderMonetarySummationType createTradeSettlementMonetarySummation(final Document invoice, final DocumentSummary documentSummary) {
        MonetaryAmount allowanceAmount = documentSummary.getDiscountNet();
        if (!allowanceAmount.isPositiveOrZero()) {
            allowanceAmount = allowanceAmount.multiply(-1.0);
        }

        // TODO wieder rein
        //        if (!itemAllowances.getItemAllowances().isEmpty()) {
        //            final MonetaryAmount allowance = itemAllowances.getItemAllowances().values().parallelStream()
        //                    .collect(() -> Money.of(BigDecimal.ONE, DataUtils.getInstance().getDefaultCurrencyUnit()), (a, t) -> t.add(a), (a, t) -> t.add(a));
        //            allowanceAmount.add(allowance);
        //        }
        MonetaryAmount totalAmount = Money.zero(DataUtils.getInstance().getDefaultCurrencyUnit());
        for (final MonetaryAmount amt : netPricesPerVat.values()) {
            totalAmount = totalAmount.add(amt);
        }
        final MonetaryAmount taxBasisTotalAmount = totalAmount.add(documentSummary.getShippingNet()).subtract(allowanceAmount);
        final MonetaryAmount grandTotalAmount = taxBasisTotalAmount.add(documentSummary.getTotalVat());
        final TradeSettlementHeaderMonetarySummationType retval = factory.createTradeSettlementHeaderMonetarySummationType();
        retval.setLineTotalAmount(createAmount(totalAmount));
        retval.setChargeTotalAmount(createAmount(documentSummary.getShippingNet()));
        retval.setAllowanceTotalAmount(createAmount(allowanceAmount));
        retval.setTaxBasisTotalAmount(createAmount(taxBasisTotalAmount));
        retval.getTaxTotalAmount().add(createAmount(documentSummary.getTotalVat(), 2, true));
        retval.setGrandTotalAmount(createAmount(grandTotalAmount));
        retval.setTotalPrepaidAmount(createAmount(Money.of(invoice.getPaidValue(), DataUtils.getInstance().getDefaultCurrencyUnit())));
        retval.setDuePayableAmount(createAmount(grandTotalAmount.subtract(Money.of(invoice.getPaidValue(), DataUtils.getInstance().getDefaultCurrencyUnit()))));
        return retval;
    }

    private TradeSettlementLineMonetarySummationType createTradeSettlementLineMonetarySummation(final InvoicePosition item) {
        /*
         * Der Gesamtpositionsbetrag ist der Nettobetrag unter Berücksichtigung
         * von Zu- und Abschlägen ohne
         * Angabe des Umsatzsteuerbetrages.
         */
        // TODO wieder rein
        //        final Price price = new Price(item);
        final TradeSettlementLineMonetarySummationType retval = factory.createTradeSettlementLineMonetarySummationType();
        //        retval.setLineTotalAmount(createAmount(price.getTotalNetRounded()));
        //
        //        storeNetPrice(price.getVatPercentFormatted(), price.getTotalNetRounded());

        // alle anderen hier angebotenen Felder resultieren nur aus dem XSD, die sind in der Spezifikation
        // gar nicht aufgeführt (nur an anderer Stelle, wo sie sinnvoller sind)
        return retval;
    }

    /**
     * Stores the net price value per VAT for later use
     * 
     * @param vatPercent
     * @param totalNetRounded
     */
    private void storeNetPrice(final String vatPercent, final MonetaryAmount totalNetRounded) {
        MonetaryAmount newAmount = totalNetRounded;
        if (netPricesPerVat.get(vatPercent) != null) {
            newAmount = netPricesPerVat.get(vatPercent).add(totalNetRounded);
        }
        netPricesPerVat.put(vatPercent, newAmount);
    }

    /**
     * Detailinformationen zu Zahlungsbedingungen
     * 
     * @param invoice
     * @param documentSummary
     * @return
     */
    private TradePaymentTermsType createTradePaymentTerms(final EInvoice eInvoice, final DocumentSummary documentSummary) {
        final LocalDate dueDate = eInvoice.getInvoiceData().getPaymentDueDate();
        //        final TemplateProcessor placeholders = ContextInjectionFactory.make(TemplateProcessor.class, eclipseContext);
        //        final double percent = invoice.getPayment().getDiscountValue();

        //        final Optional<String> paymentText = Optional.ofNullable(placeholders.createPaymentText(invoice, Optional.ofNullable(documentSummary), percent));
        final TradePaymentTermsType tradePaymentTerms = factory.createTradePaymentTermsType();
        tradePaymentTerms.setDescription(createText(eInvoice.getInvoiceData().getPaymentTerms()));
        tradePaymentTerms.setDueDateDateTime(createDateTime(dueDate));

        //        final DocumentReceiver documentReceiver = addressManager.getBillingAdress(invoice);
        //        final Contact contact = getOriginContact(documentReceiver);
        // TODO wieder rein
        //        if (contact != null) {
        //            final IDType id = StringUtils.isNotBlank(contact.getMandateReference())
        //                    ? createIdWithSchemeFromString(contact.getMandateReference(), preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CREDITORID))
        //                    : null;
        //            tradePaymentTerms.setDirectDebitMandateID(id);
        //        }
        return tradePaymentTerms;
    }

    private TradeAllowanceChargeType createTradeAllowance(final InvoicePosition invoicePosition) {
        return createTradeAllowance(invoicePosition, true);
    }

    /**
     * Detailinformationen zu Zu- und Abschlägen.
     * 
     * @param item
     * @return
     */
    private TradeAllowanceChargeType createTradeAllowance(final InvoicePosition item, final boolean withReason) {
        // TODO wieder rein
        //        final Price price = new Price(item);
        //        MonetaryAmount amount = price.getTotalAllowance();
        //        final boolean isAllowance = amount.isPositiveOrZero();
        //        if (!isAllowance) {
        //            amount = amount.multiply(-1);
        //        }
        //
        //        itemAllowances.add(item.getInvoicedItemVatRate(), price.getTotalAllowance());

        final TradeAllowanceChargeType tradeAllowanceCharge = factory.createTradeAllowanceChargeType();
        //        tradeAllowanceCharge.setChargeIndicator(createIndicator(isAllowance));
        //            .setCalculationPercent(factory.createPercentType().setValue(BigDecimal.valueOf(item.getItemRebate()))) // [CII-SR-122]
        //            .setBasisAmount(createAmount(price.getTotalNet(), 2))  // [CII-SR-123]

        // Der gesamte zur Berechnung des Nettopreises vom Bruttopreis subtrahierte Rabatt
        // (Gilt nur, wenn der Rabatt je Einheit gegeben wird und nicht im Bruttopreis enthalten ist.)
        //        tradeAllowanceCharge.setActualAmount(createAmount(amount, 2, false))
        // see UNTDID 5189 and UNTDID 7161

        if (withReason) {
            tradeAllowanceCharge.setReason(createText(zfMsg.zugferdExportLabelRebate));
            //            tradeAllowanceCharge.setReasonCode(factory.createAllowanceChargeReasonCodeType().setValue("95"))  // "Discount" [CII-SR-127]
        }
        return tradeAllowanceCharge
        //            .setCategoryTradeTax(createTradeTax(item.getItemVat()))
        ;
    }

    /**
     * @param isAllowance
     */
    private IndicatorType createIndicator(final boolean isAllowance) {
        final IndicatorType indicator = factory.createIndicatorType();
        indicator.setIndicator(isAllowance);
        return indicator;
    }

    private TradeAllowanceChargeType createTradeAllowance(final DocumentSummary summary, final Document invoice) {
        MonetaryAmount amount = summary.getDiscountNet();
        // Abschlag ==> false
        // Zuschlag ==> true
        final boolean isAllowance = amount.isPositive();
        if (!isAllowance) {
            amount = amount.multiply(-1);
        }
        final TradeAllowanceChargeType retval = factory.createTradeAllowanceChargeType();
        retval.setChargeIndicator(createIndicator(isAllowance));
        retval.setActualAmount(createAmount(amount, 2, false));
        retval.setBasisAmount(createAmount(summary.getItemsNet().add(amount), 2));
        retval.setReason(createText(zfMsg.zugferdExportLabelRebate));
        // TODO wieder rein
        //        retval.setCategoryTradeTax(createTradeTax(invoice.getItems().get(0).getItemVat()));
        return retval;
    }

    /**
     * Generate allowance for shipping costs (this is for COMFORT profile only!)
     * 
     * @param invoice
     * @return
     */
    private TradeAllowanceChargeType createTradeAllowance(final EInvoice eInvoice) {
        // TODO wieder rein
        //        final Double amount = invoice.getShipping() != null ? invoice.getShipping().getShippingValue() : invoice.getShippingValue();

        final TradeAllowanceChargeType retval = factory.createTradeAllowanceChargeType();
        //        retval.setChargeIndicator(createIndicator(true));
        //        retval.setActualAmount(createAmount(Money.of(amount, DataUtils.getInstance().getDefaultCurrencyUnit()), 2, false));
        //        retval.setBasisAmount(createAmount(Money.of(invoice.getTotalValue(), DataUtils.getInstance().getDefaultCurrencyUnit()), 2, false));
        //        retval.setReason(createText("Shipping costs")); // TODO Versandkosten!!!
        //        if (invoice.getShipping() != null && invoice.getShipping().getShippingVat().getTaxValue() > 0.0) {
        //            retval.setCategoryTradeTax(createTradeTax(invoice.getShipping().getShippingVat()));
        //        } else if (invoice.getAdditionalInfo().getShippingVatValue() != null) {
        //            final VAT shippingVat = new VAT();
        //            shippingVat.setTaxValue(invoice.getAdditionalInfo().getShippingVatValue());
        //            shippingVat.setName(invoice.getAdditionalInfo().getShippingVatDescription());
        //            retval.setCategoryTradeTax(createTradeTax(shippingVat));
        //        }
        return retval;
    }

    /**
     * Detailangaben zu Steuern auf Positionsebene
     * 
     * @param vatDocument
     * @return
     */
    private TradeTaxType createTradeTax(final InvoicePosition invoicePosition) {// final VAT vatValue) {
        final TradeTaxType retval = factory.createTradeTaxType();
        final PercentType percentType = factory.createPercentType();
        percentType.setValue(invoicePosition.getInvoicedItemVatRate());
        retval.setRateApplicablePercent(percentType);
        retval.setCategoryCode(createTaxCategoryCode(invoicePosition.getInvoicedItemVatCategoryCode()));
        retval.setTypeCode(createTaxTypeCode("VAT"));
        return retval;

    }

    /**
     * Detailangaben zu Steuern im VatBreakdown
     * 
     * @param vatSummaryItem
     * @return
     */
    private TradeTaxType createTradeTax(final InvoiceVatBreakdown invoiceVatBreakdown, final String currencyCode) {
        final TradeTaxType retval = factory.createTradeTaxType();
        retval.setCalculatedAmount(createAmount(Money.of(invoiceVatBreakdown.getVatCategoryTaxAmount(), currencyCode)));
        final PercentType percentType = factory.createPercentType();
        percentType.setValue(invoiceVatBreakdown.getVatCategoryRate());
        retval.setRateApplicablePercent(percentType);
        retval.setBasisAmount(createAmount(Money.of(invoiceVatBreakdown.getVatCategoryTaxableAmount(), currencyCode)));
        retval.setCategoryCode(createTaxCategoryCode(invoiceVatBreakdown.getVatCategoryCode()));
        retval.setTypeCode(createTaxTypeCode("VAT"));
        return retval;
    }

    private TaxTypeCodeType createTaxTypeCode(final String string) {
        final TaxTypeCodeType retval = factory.createTaxTypeCodeType();
        retval.setValue(string);
        return retval;
    }

    private TaxCategoryCodeType createTaxCategoryCode(final String taxCat) {
        final TaxCategoryCodeType retval = factory.createTaxCategoryCodeType();
        retval.setValue(taxCat);
        return retval;
    }

    /**
     * creates an Amount field
     * 
     * @param the
     *            VAT value
     * @return
     */
    private AmountType createAmount(final MonetaryAmount amount) {
        return createAmount(amount, 2, false);
    }

    private AmountType createAmount(final MonetaryAmount amount, final int scale) {
        return createAmount(amount, scale, false);
    }

    /**
     * Creates an Amount with given value, scale and currency.
     * 
     * @param amount
     * @param scale
     * @param withCurrency
     * @return
     */
    private AmountType createAmount(final MonetaryAmount amount, final int scale, final boolean withCurrency) {
        final BigDecimal scaledValue = BigDecimal.valueOf(amount.getNumber().doubleValue()).setScale(scale, RoundingMode.HALF_UP);

        final AmountType retval = factory.createAmountType();
        retval.setValue(scaledValue);
        retval.setCurrencyID(withCurrency ? amount.getCurrency().getCurrencyCode() : null);
        return retval;
    }

    private CreditorFinancialInstitutionType createCreditorFinancialInstitution() {
        final CreditorFinancialInstitutionType retval = factory.createCreditorFinancialInstitutionType();
        retval.setBICID(createIdFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BIC)));
        //              .setGermanBankleitzahlID(createIdFromString(preferences.getString("YOURCOMPANY_COMPANY_BANKCODE")))
        /*
         * .setName(createText(preferences.getString(Constants.
         * PREFERENCES_YOURCOMPANY_BANK)))
         */
        return retval;
    }

    private CreditorFinancialAccountType createCreditorAccount() {
        CreditorFinancialAccountType retval = null;
        if (!StringUtils.isEmpty(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN))
                || !StringUtils.isEmpty(preferences.getString("YOURCOMPANY_COMPANY_BANKACCOUNTNR"))) {
            retval = factory.createCreditorFinancialAccountType();
            retval.setIBANID(createIdFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN)));
            /*
             * retval.setProprietaryID(createIdFromString(preferences.getString(
             * "YOURCOMPANY_COMPANY_BANKACCOUNTNR")))
             */
        }
        return retval;
    }

    private DebtorFinancialAccountType createDebtorAccount(final BankAccount bankAccount) {
        if (bankAccount != null && !StringUtils.isEmpty(bankAccount.getIban())) {
            final DebtorFinancialAccountType financialAccountType = factory.createDebtorFinancialAccountType();
            financialAccountType.setIBANID(createIdFromString(bankAccount.getIban()))
            // financialAccountType.setProprietaryID(createIdFromString(invoice.getFormatedStringValueByKeyFromOtherTable("addressid.CONTACTS:account")))
            ;
            return financialAccountType;
        } else {
            return null;
        }
    }

    private PaymentMeansCodeType createPaymentTypeCode(final EInvoice eInvoice) {
        // TODO implement lookup:  UNTDID 4461 !!!

        /*
         * Payment type code gem. "Payment Means Code" lt. Codeliste ZUGFeRD
         * 10 ... bar
         * 31 ... Payment by debit movement of funds from one account to
         * another. International Transfers
         * ==> SEPA-Überweisung!
         * 48 ... Bank card
         * 49 ... Direct debit (Lastschrift)
         * 
         */
        final PaymentMeansCodeType paymentMeansCode = factory.createPaymentMeansCodeType();
        paymentMeansCode.setValue(eInvoice.getInvoicePayment().getPaymentMeansTypeCode());
        return paymentMeansCode;
    }

    private TaxRegistrationType createTaxNumber(final EInvoice eInvoice, final ContactType contactType) {
        TaxRegistrationType retval = factory.createTaxRegistrationType();
        switch (contactType) {
            case SELLER:
                final String companyVatNo = eInvoice.getInvoiceSeller().getSellerVatIdentifier();
                if (!StringUtils.isEmpty(companyVatNo)) {
                    retval.setID(createIdWithSchemeFromString(getNullCheckedValue(companyVatNo), "VA"));
                }
                break;
            case BUYER:
                final String buyerVatNo = eInvoice.getInvoiceBuyer().getBuyerVatIdentifier();

                if (StringUtils.trimToNull(buyerVatNo) != null) {
                    retval.setID(createIdWithSchemeFromString(getNullCheckedValue(StringUtils.trimToNull(buyerVatNo)), "VA"));
                }
                break;
            default:
                retval = null;
                break;
        }
        return retval;
    }

    /**
     * Returns the given value only if it is not <code>null</code> or not empty.
     * This is to prevent empty XML tags.
     * 
     * @param string
     *            string to test
     * @return <code>null</code> if the given string is empty or
     *         <code>null</code>
     */
    private String getNullCheckedValue(final String formatedStringValueByKeyFromOtherTable) {
        return StringUtils.isEmpty(formatedStringValueByKeyFromOtherTable) ? null : formatedStringValueByKeyFromOtherTable;
    }

    private CodeType createCode(final String value) {
        final CodeType codeType = factory.createCodeType();
        codeType.setValue(value);
        return codeType;
    }

    private TradeAddressType createAddress(final EInvoice eInvoice, final ContactType contactType) {
        TradeAddressType retval = null;
        switch (contactType) {
            case SELLER:
                final AddressData sellerAddress = eInvoice.getInvoiceSeller().getSellerAddress();

                retval = factory.createTradeAddressType();
                retval.setPostcodeCode(createCode(sellerAddress.getPostCode()));
                retval.setLineOne(createText(sellerAddress.getAddressLine1()));
                retval.setLineTwo(createText(sellerAddress.getAddressLine2()));
                retval.setLineThree(createText(sellerAddress.getAddressLine3()));
                retval.setCityName(createText(sellerAddress.getCity()));
                retval.setCountryID(createCountry(sellerAddress.getCountryCode())); // Nur die Alpha-2 Darstellung darf verwendet werden
                retval.setCountrySubDivisionName(createText(sellerAddress.getCountrySubdivision()));

                break;
            case BUYER:
                final AddressData billingAddress = eInvoice.getInvoiceBuyer().getBuyerAddress();
                retval = factory.createTradeAddressType();
                retval.setPostcodeCode(createCode(billingAddress.getPostCode()));
                retval.setLineOne(createText(billingAddress.getAddressLine1()));
                retval.setLineTwo(createText(billingAddress.getAddressLine2()));
                retval.setLineThree(createText(billingAddress.getAddressLine3()));
                retval.setCityName(createText(billingAddress.getCity()));
                retval.setCountryID(createCountry(billingAddress.getCountryCode())); // Nur die Alpha-2 Darstellung darf verwendet werden
                retval.setCountrySubDivisionName(createText(billingAddress.getCountrySubdivision()));
                break;
            default:
                break;
        }
        return retval;
    }

    private CountryIDType createCountry(final String value) {
        String countryStr = value;
        // FIXME CHANGE THIS!!!
        if (StringUtils.length(value) > 2) {
            countryStr = localeUtil.findCodeByDisplayCountry(value, "DE");
        }
        // null values aren't allowed!
        final CountryIDType countryTypeId = factory.createCountryIDType();
        countryTypeId.setValue(Optional.ofNullable(countryStr).orElse("DE"));
        return countryTypeId;
    }

    private TradeContactType createContact(final EInvoice eInvoice, final ContactType contactType) {
        final TradeContactType contact = factory.createTradeContactType();
        final UniversalCommunicationType email = factory.createUniversalCommunicationType();
        switch (contactType) {
            case SELLER:
                final InvoiceSeller invoiceSeller = eInvoice.getInvoiceSeller();
                contact.setPersonName(createText(invoiceSeller.getSellerContactPoint()));
                //      contact.setDepartmentName(createText(dept));  // unknown (Kontaktstelle des Verkäufers)

                contact.setTelephoneUniversalCommunication((createCommunicationItem(invoiceSeller.getSellerContactTelephoneNumber())));
                email.setURIID(createIdFromString(invoiceSeller.getSellerContactEmailAddress()));
                contact.setEmailURIUniversalCommunication(email);
                break;
            case BUYER:
                final InvoiceBuyer invoiceBuyer = eInvoice.getInvoiceBuyer();
                email.setURIID(createIdWithSchemeFromString(invoiceBuyer.getBuyerContactEmailAddress(), "EM"));
                contact.setPersonName(createText(invoiceBuyer.getBuyerContactPoint()));
                //            contact.setDepartmentName(createText(dept));  // unknown
                contact.setTelephoneUniversalCommunication(createCommunicationItem(invoiceBuyer.getBuyerContactTelephoneNumber()));
                contact.setEmailURIUniversalCommunication(email);
                break;
            default:
                break;
        }
        return contact;
    }

    private UniversalCommunicationType createCommunicationItem(final String communicationItem) {
        final UniversalCommunicationType universalCommunicationType = factory.createUniversalCommunicationType();
        universalCommunicationType.setCompleteNumber(createText(communicationItem));
        return universalCommunicationType;
    }

    /**
     * Creates a {@link FormattedDateTimeType} from a given date string
     * ("YYYY-MM-DD").
     * 
     * @param dateString
     *            the date String
     * @return {@link FormattedDateTimeType}
     */
    private DateTimeType createDateTime(final LocalDate dateString) {
        DateTimeType dateValue = null;
        if (dateString != null) {
            dateValue = factory.createDateTimeType();
            final DateTimeType.DateTimeString dateTypeString = factory.createDateTimeTypeDateTimeString();
            dateTypeString.setValue(sdfDest.format(dateString));
            dateTypeString.setFormat("102");
            dateValue.setDateTimeString(dateTypeString);
        }
        return dateValue;
    }

    /**
     * @param invoice
     * @return
     */
    private TextType createText(final String text) {
        TextType retval = null;
        if (!StringUtils.isBlank(text)) {
            retval = factory.createTextType();
            retval.setValue(text);
        }
        return retval;
    }

    private IDType createIdFromString(final String idString) {
        return createIdWithSchemeFromString(idString, null);
    }

    private IDType createIdWithSchemeFromString(final String idString, final String scheme) {
        final IDType idType = factory.createIDType();
        idType.setValue(idString);
        idType.setSchemeID(scheme);

        return idString != null ? idType : null;
    }

}
