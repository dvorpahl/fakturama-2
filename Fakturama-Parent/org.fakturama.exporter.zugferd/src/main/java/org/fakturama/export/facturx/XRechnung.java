/* Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2020 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation */

package org.fakturama.export.facturx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.fakturama.export.einvoice.model.AddressData;
import org.fakturama.export.einvoice.model.EInvoice;
import org.fakturama.export.einvoice.model.InvoiceBuyer;
import org.fakturama.export.einvoice.model.InvoiceChargesAllowances;
import org.fakturama.export.einvoice.model.InvoiceCreditTransfer;
import org.fakturama.export.einvoice.model.InvoiceData;
import org.fakturama.export.einvoice.model.InvoiceDocumentTotals;
import org.fakturama.export.einvoice.model.InvoiceLinePeriod;
import org.fakturama.export.einvoice.model.InvoiceNote;
import org.fakturama.export.einvoice.model.InvoicePosition;
import org.fakturama.export.einvoice.model.InvoiceSeller;
import org.fakturama.export.einvoice.model.InvoiceVatBreakdown;
import org.fakturama.export.facturx.modelgen.AllowanceChargeReasonCodeType;
import org.fakturama.export.facturx.modelgen.AmountType;
import org.fakturama.export.facturx.modelgen.CodeType;
import org.fakturama.export.facturx.modelgen.CountryIDType;
import org.fakturama.export.facturx.modelgen.CreditorFinancialAccountType;
import org.fakturama.export.facturx.modelgen.CreditorFinancialInstitutionType;
import org.fakturama.export.facturx.modelgen.CrossIndustryInvoiceType;
import org.fakturama.export.facturx.modelgen.CurrencyCodeType;
import org.fakturama.export.facturx.modelgen.DateTimeType;
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
import org.fakturama.export.facturx.modelgen.TradePartyType;
import org.fakturama.export.facturx.modelgen.TradePaymentTermsType;
import org.fakturama.export.facturx.modelgen.TradePriceType;
import org.fakturama.export.facturx.modelgen.TradeProductType;
import org.fakturama.export.facturx.modelgen.TradeSettlementHeaderMonetarySummationType;
import org.fakturama.export.facturx.modelgen.TradeSettlementLineMonetarySummationType;
import org.fakturama.export.facturx.modelgen.TradeSettlementPaymentMeansType;
import org.fakturama.export.facturx.modelgen.TradeTaxType;
import org.fakturama.export.facturx.modelgen.UniversalCommunicationType;

import com.sebulli.fakturama.misc.UNTDID5305;

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
        // We create all the root elements and 2nd level here, all other things
        // are done in the methods

        final CrossIndustryInvoiceType root = new CrossIndustryInvoiceType();
        // 3 root level objects
        root.setExchangedDocumentContext(makeExchangedDocumentContext(eInvoice));
        root.setExchangedDocument(makeExchangedDocument(eInvoice));

        final SupplyChainTradeTransactionType supplyChainTradeTransaction = factory.createSupplyChainTradeTransactionType();
        root.setSupplyChainTradeTransaction(supplyChainTradeTransaction);
        // now submethods of SupplyChainTradeTransaction

        // BG-25 IncludedSupplyChainTradeLineItem
        supplyChainTradeTransaction.getIncludedSupplyChainTradeLineItem().addAll(eInvoice.getInvoicePositions().stream().map(this::createLineItem).toList());
        // ApplicableHeaderTradeAgreement OK
        supplyChainTradeTransaction.setApplicableHeaderTradeAgreement(makeApplicableHeaderTradeAgreement(eInvoice));
        // ApplicableHeaderTradeDelivery OK
        supplyChainTradeTransaction.setApplicableHeaderTradeDelivery(makeApplicableHeaderTradeDelivery(eInvoice));
        // ApplicableHeaderTradeSettlement OK
        supplyChainTradeTransaction.setApplicableHeaderTradeSettlement(makeApplicableHeaderTradeSettlement(eInvoice));

        return new ObjectFactory().createCrossIndustryInvoice(root);
    }

    /**
     * OK
     * 
     * @param eInvoice
     * @return
     */
    private HeaderTradeSettlementType makeApplicableHeaderTradeSettlement(final EInvoice eInvoice) {
        // What we do not need: BT-6 (TaxCurrencyCode),
        final HeaderTradeSettlementType headerTradeSettlement = factory.createHeaderTradeSettlementType();
        // BT-5
        final CurrencyCodeType currency = getGlobalCurrencyCode(eInvoice.getInvoiceData().getInvoiceCurrencyCode());
        headerTradeSettlement.setInvoiceCurrencyCode(currency);
        if (eInvoice.getInvoicePayment() != null) {
            // BT-90
            headerTradeSettlement.setCreditorReferenceID(createIdFromString(eInvoice.getInvoicePayment().getBankAssignedCreditorIdentifier()));
            // BT-83
            headerTradeSettlement.setPaymentReference(createText(eInvoice.getInvoicePayment().getRemittanceInformation()));

        }

        // BG-10 PayeeTradeParty --> OK
        headerTradeSettlement.setPayeeTradeParty(createPayeeTradeParty(eInvoice));
        // BG-16 SpecifiedTradeSettlementPaymentMeans --> OK
        headerTradeSettlement.getSpecifiedTradeSettlementPaymentMeans().addAll(createSpecifiedTradeSettlementPaymentMeans(eInvoice));
        // BG-23 ApplicableTradeTax --> OK
        for (final InvoiceVatBreakdown invoiceVatBreakdown : eInvoice.getInvoiceVatBreakdowns()) {
            // für jeden Steuerbetrag muß es einen eigenen Eintrag geben
            headerTradeSettlement.getApplicableTradeTax().add(createTradeTax(invoiceVatBreakdown, eInvoice.getInvoiceData().getInvoiceCurrencyCode()));
        }
        // BG-14 BillingSpecifiedPeriod --> OK
        headerTradeSettlement.setBillingSpecifiedPeriod(createBillingSpecificPeriod(eInvoice.getInvoiceData()));
        // BG-20 SpecifiedTradeAllowanceCharge <Document Level Allowances
        // ABSCHLÄGE AUF DOKUMENTENEBENE > OK
        // BG-21 SpecifiedTradeAllowanceCharge <Document Level Charges ZUSCHLÄGE
        // AUF DOKUMENTENEBENE > OK
        final Collection<TradeAllowanceChargeType> specifiedTradeAllowanceChargeList = createSpecifiedTradeAllowanceChargeList(eInvoice);
        if (specifiedTradeAllowanceChargeList != null) {
            headerTradeSettlement.getSpecifiedTradeAllowanceCharge().addAll(specifiedTradeAllowanceChargeList);
        }
        // BT-20 SpecifiedTradePaymentTerms --> OK
        headerTradeSettlement.getSpecifiedTradePaymentTerms().addAll(List.of(createTradePaymentTerms(eInvoice)));
        // BG-22 SpecifiedTradeSettlementHeaderMonetarySummation --> OK
        headerTradeSettlement.setSpecifiedTradeSettlementHeaderMonetarySummation(createTradeSettlementMonetarySummation(eInvoice));
        // BG-3 InvoiceReferencedDocument NOT USED
        // BT-19 ReceivableSpecifiedTradeAccountingAccount --> NOT USED, not
        // interesting

        return headerTradeSettlement;
    }

    /**
     * 
     * @param eInvoice
     * @return
     */
    private Collection<TradeAllowanceChargeType> createSpecifiedTradeAllowanceChargeList(final EInvoice eInvoice) {
        final String currency = eInvoice.getInvoiceData().getInvoiceCurrencyCode();

        final List<TradeAllowanceChargeType> tradeAllowanceCharges = new ArrayList<>();
        final List<InvoiceChargesAllowances> allowances = eInvoice.getInvoiceAllowances();
        if (!allowances.isEmpty()) {
            allowances.forEach(allowance -> tradeAllowanceCharges.add(createAllowanceChargeDocDetail(currency, allowance, false)));
        }
        final List<InvoiceChargesAllowances> charges = eInvoice.getInvoiceCharges();
        if (!charges.isEmpty()) {
            charges.forEach(charge -> tradeAllowanceCharges.add(createAllowanceChargeDocDetail(currency, charge, true)));
        }
        return tradeAllowanceCharges.isEmpty() ? null : tradeAllowanceCharges;
    }

    private TradeAllowanceChargeType createAllowanceChargeDocDetail(final String currency, final InvoiceChargesAllowances allowance, final boolean isCharge) {
        final TradeAllowanceChargeType tradeAllowance = factory.createTradeAllowanceChargeType();
        // BG-20
        tradeAllowance.setChargeIndicator(createIndicator(isCharge));
        // BT-101
        tradeAllowance.setCalculationPercent(createPercentType(allowance.getPercentage()));
        // BT-100
        tradeAllowance.setBasisAmount(createAmount(allowance.getBaseAmount()));
        // BT-99
        tradeAllowance.setActualAmount(createAmount(allowance.getAmount()));
        // BT-105
        if (allowance.getReasonCode() != null) {
            final AllowanceChargeReasonCodeType reasonCode = factory.createAllowanceChargeReasonCodeType();
            reasonCode.setValue(allowance.getReasonCode());
            tradeAllowance.setReasonCode(reasonCode);
        }
        // BT-104
        tradeAllowance.setReason(createText(allowance.getReason()));
        // BT-102
        tradeAllowance.setCategoryTradeTax(createTradeTax(allowance, currency));
        return tradeAllowance;
    }

    /**
     * Helpermethod
     * 
     * @param percentageValue
     * @return
     */
    private PercentType createPercentType(final BigDecimal percentageValue) {

        final PercentType percentType = factory.createPercentType();
        percentType.setValue(percentageValue);
        return percentageValue == null ? null : percentType;
    }

    /**
     * 
     * @param eInvoice
     * @return List for payment terms
     */
    private Collection<? extends TradeSettlementPaymentMeansType> createSpecifiedTradeSettlementPaymentMeans(final EInvoice eInvoice) {
        // not here: BG-18 (NA), BT-91 (NA, there is no Lastschrift)
        final List<TradeSettlementPaymentMeansType> paymentMeans = new ArrayList<>();

        TradeSettlementPaymentMeansType paymentType;
        paymentType = factory.createTradeSettlementPaymentMeansType();
        final PaymentMeansCodeType paymentMeansCode = factory.createPaymentMeansCodeType();
        paymentMeansCode.setValue(eInvoice.getInvoicePayment().getPaymentMeansTypeCode());
        // BT-81
        paymentType.setTypeCode(paymentMeansCode);
        // BT-82
        paymentType.setInformation(createText(eInvoice.getInvoicePayment().getPaymentMeansText()));

        // BG-17
        final CreditorFinancialAccountType creditor = createCreditorAccount(eInvoice);
        if (creditor != null) {
            paymentType.setPayeePartyCreditorFinancialAccount(creditor);
            final CreditorFinancialInstitutionType creditorFinancialInstitution = factory.createCreditorFinancialInstitutionType();
            // we can safely use get(0) here case if creditor is nut null, this
            // was used there already. Also we only have one bic
            creditorFinancialInstitution
                    .setBICID(createIdFromString(eInvoice.getInvoicePayment().getInvoiceCreditTransfers().get(0).getPaymentServiceProviderIdentifier()));

            // BT-86
            paymentType.setPayeeSpecifiedCreditorFinancialInstitution(creditorFinancialInstitution);
        }
        paymentMeans.add(paymentType);

        return paymentMeans;
    }

    /**
     * BG-10
     * 
     * @param eInvoice
     * @return
     */
    private TradePartyType createPayeeTradeParty(final EInvoice eInvoice) {
        final TradePartyType payeeTradeParty = factory.createTradePartyType();
        // BT-61-00
        if (eInvoice.getInvoiceSeller().getPayeeLegalRegistrationIdentifier() != null) {
            final LegalOrganizationType specifiedLegalOrganization = factory.createLegalOrganizationType();
            specifiedLegalOrganization.setID(createIdWithSchemeFromString(eInvoice.getInvoiceSeller().getPayeeLegalRegistrationIdentifier(),
                    eInvoice.getInvoiceSeller().getPayeeLegalRegistrationIdentifierSchemeIdentifier()));
            payeeTradeParty.setSpecifiedLegalOrganization(specifiedLegalOrganization);
        }
        // BT-60 (set to ID and Global ID)
        final IDType idForPayeeIdentifier = createIdWithSchemeFromString(StringUtils.trimToNull(eInvoice.getInvoiceSeller().getPayeeIdentifier()),
                eInvoice.getInvoiceSeller().getPayeeIdentifierSchemeIdentifier());
        if (idForPayeeIdentifier != null) {
            payeeTradeParty.getID().add(idForPayeeIdentifier);
            payeeTradeParty.getGlobalID().add(idForPayeeIdentifier);
        }
        return payeeTradeParty.getSpecifiedLegalOrganization() != null || !payeeTradeParty.getID().isEmpty() ? payeeTradeParty : null;
    }

    /**
     * BG-13, BG-15 OK
     * 
     * @param eInvoice
     * @return
     */
    private HeaderTradeDeliveryType makeApplicableHeaderTradeDelivery(final EInvoice eInvoice) {
        // We dont need here: BT-15, BT-16
        final HeaderTradeDeliveryType headerTradeDeliveryType = factory.createHeaderTradeDeliveryType();
        // BG-13
        final TradePartyType tradePartyType = factory.createTradePartyType();
        // BT-71
        tradePartyType.getID().add(createIdFromString(eInvoice.getInvoiceDeliveryInformation().getDeliverToLocationIdentifier()));
        // BT-70
        tradePartyType.setName(createText(eInvoice.getInvoiceDeliveryInformation().getDeliverToPartyName()));
        // BG-15 Lieferanschrift
        tradePartyType.setPostalTradeAddress(createAddress(eInvoice.getInvoiceDeliveryInformation().getDeliveryAddress()));

        headerTradeDeliveryType.setShipToTradeParty(tradePartyType);

        if (eInvoice.getInvoiceDeliveryInformation().getActualDeliveryDate() != null) {
            final SupplyChainEventType deliveryEvent = factory.createSupplyChainEventType();
            deliveryEvent.setOccurrenceDateTime(createDateTime(eInvoice.getInvoiceDeliveryInformation().getActualDeliveryDate()));
            // BT-72
            headerTradeDeliveryType.setActualDeliverySupplyChainEvent(deliveryEvent);
        }
        return headerTradeDeliveryType;
    }

    /**
     * OK
     * 
     * @param eInvoice
     * @return
     */
    private HeaderTradeAgreementType makeApplicableHeaderTradeAgreement(final EInvoice eInvoice) {
        final HeaderTradeAgreementType tradeAgreement = factory.createHeaderTradeAgreementType();

        // BT-10
        tradeAgreement.setBuyerReference(createText(eInvoice.getInvoiceData().getBuyerReference())); // "Kundenreferenz"

        // BG-4 seller information
        tradeAgreement.setSellerTradeParty(createSeller(eInvoice));

        // BG-7 buyer information
        tradeAgreement.setBuyerTradeParty(createBuyer(eInvoice));

        // BG-11 SellerTaxRepresentativeTradeParty (NA)
        // BT-12 ContractReferencedDocument (NA)
        // BG-24 AdditionalReferencedDocument (NA for now... )
        // BT-11 SpecifiedProcuringProject (NA, Projektreferenz)
        // BT-17 AdditionalReferencedDocument (NA, Tender or Lot-Reference)
        // BT-18 AdditionalReferencedDocument (NA, Invoiced object identifier)

        // BT-13 BuyerOrderReferencedDocument Bestellung / referenced order
        if (eInvoice.getInvoiceData().getPurchaseOrderReference() != null) {
            final ReferencedDocumentType orderRef = factory.createReferencedDocumentType();
            // BT-13
            orderRef.setIssuerAssignedID(createIdFromString(eInvoice.getInvoiceData().getPurchaseOrderReference()));
            // only if ID is not empty!
            if (!StringUtils.isEmpty(eInvoice.getInvoiceData().getPurchaseOrderReference())) {
                tradeAgreement.setBuyerOrderReferencedDocument(orderRef);
            }
        }
        return tradeAgreement;
    }

    /**
     * OK
     * 
     * @param eInvoice
     * @return
     */
    private ExchangedDocumentType makeExchangedDocument(final EInvoice eInvoice) {
        // BT-X-6 EffectiveSpecifiedPeriod will not be used

        final ExchangedDocumentType exchangedDocumentType = factory.createExchangedDocumentType();
        // now the header information follows
        // BT-1
        exchangedDocumentType.setID(createIdFromString(eInvoice.getInvoiceData().getInvoiceNumber()));
        // BT-2
        exchangedDocumentType.setIssueDateTime(createDateTime(eInvoice.getInvoiceData().getInvoiceIssueDate()));
        // BT-3
        final DocumentCodeType docTypeCode = factory.createDocumentCodeType();
        docTypeCode.setValue(eInvoice.getInvoiceData().getInvoiceTypeCode());
        exchangedDocumentType.setTypeCode(docTypeCode);

        // BT-21, BT-22
        for (final InvoiceNote note : eInvoice.getInvoiceNotes()) {
            exchangedDocumentType.getIncludedNote().add(createNote(note));
        }
        return exchangedDocumentType;
    }

    /**
     * OK
     * 
     * @param eInvoice
     * @return
     */
    private ExchangedDocumentContextType makeExchangedDocumentContext(final EInvoice eInvoice) {
        final ExchangedDocumentContextType exchangedDocCtx = factory.createExchangedDocumentContextType();

        // BT-24 Allgemein
        final DocumentContextParameterType documentContextParameter = factory.createDocumentContextParameterType();
        documentContextParameter.setID(createIdFromString(eInvoice.getInvoiceData().getSpecificationIdentifier()));
        exchangedDocCtx.setGuidelineSpecifiedDocumentContextParameter(documentContextParameter);

        // BT-23: BusinessProcessSpecifiedDocumentContextParameter
        if (eInvoice.getInvoiceData().getBusinessProcessType() != null) {
            final DocumentContextParameterType businessProcessSpecifiedDocumentContextParameter = factory.createDocumentContextParameterType();
            businessProcessSpecifiedDocumentContextParameter.setID(createIdFromString(eInvoice.getInvoiceData().getBusinessProcessType()));
            exchangedDocCtx.setBusinessProcessSpecifiedDocumentContextParameter(businessProcessSpecifiedDocumentContextParameter);
        }
        return exchangedDocCtx;
    }

    /**
     * BG-7
     * 
     * @param eInvoice
     * @return
     */
    private TradePartyType createBuyer(final EInvoice eInvoice) {
        final InvoiceBuyer invoiceBuyer = eInvoice.getInvoiceBuyer();
        final TradePartyType buyer = factory.createTradePartyType();

        // BT-44
        buyer.setName(createText(invoiceBuyer.getBuyerName()));

        // BG-8
        buyer.setPostalTradeAddress(createAddress(eInvoice.getInvoiceBuyer().getBuyerAddress()));

        // BT-47 SpecifiedLegalOrganization (NA), inside: BT-45, BT-47-1
        // BG-9 DefinedTradeContact
        final TradeContactType tradeContact = createContact(eInvoice, ContactType.BUYER);
        if (tradeContact != null) {
            buyer.getDefinedTradeContact().add(tradeContact);
        }
        // BT-49, BT-49-1
        final UniversalCommunicationType universalCommunicationType = factory.createUniversalCommunicationType();
        universalCommunicationType
                .setURIID(createIdWithSchemeFromString(invoiceBuyer.getBuyerElectronicAddress(), invoiceBuyer.getBuyerElectronicAddressSchemeIdentifier()));
        buyer.setURIUniversalCommunication(universalCommunicationType.getURIID() == null ? null : universalCommunicationType);
        // BT-48
        final TaxRegistrationType vatTaxNumber = createVatTaxNumber(eInvoice, ContactType.BUYER);
        if (vatTaxNumber != null) {
            buyer.getSpecifiedTaxRegistration().add(vatTaxNumber);
        }

        // BT-46
        if (invoiceBuyer.getBuyerIdentifierSchemeIdentifier() != null && StringUtils.trimToNull(invoiceBuyer.getBuyerIdentifier()) != null) {
            buyer.getGlobalID().add(createIdWithSchemeFromString(invoiceBuyer.getBuyerIdentifier(),
                    StringUtils.defaultString(invoiceBuyer.getBuyerIdentifierSchemeIdentifier())));
        } else if (StringUtils.trimToNull(invoiceBuyer.getBuyerIdentifier()) != null) {
            buyer.getID().add(createIdFromString(invoiceBuyer.getBuyerIdentifier()));
        }
        return buyer;

    }

    /**
     * BG-4 Seller Trade party in ApplicableHeaderTradeAgreement
     * BG-5, BG-6
     * 
     * @param eInvoice
     * @return
     */
    private TradePartyType createSeller(final EInvoice eInvoice) {
        // BT-29 (NA, ID, GlobalID), BT-29-1 (NA), BT-33 (NA, Description),
        final InvoiceSeller invoiceSeller = eInvoice.getInvoiceSeller();

        final TradePartyType seller = factory.createTradePartyType();

        // BT-29
        // Kennung des Verkäufers (Durch den Kunden zugewiesene Lieferantennummer)
        seller.getID().add(createIdFromString(eInvoice.getInvoiceSeller().getSellerIdentifier())); 
        
        // BT-27
        seller.setName(createText(invoiceSeller.getSellerName()));
        // .setDescription(createText("")) // Sonstige rechtliche Informationen
        // des Verkäufers
        // BG-6 DefinedTradeContact (OK)
        seller.getDefinedTradeContact().add(createContact(eInvoice, ContactType.SELLER));
        //
        // BG-5 PostalTradeAddress
        seller.setPostalTradeAddress(createAddress(eInvoice.getInvoiceSeller().getSellerAddress()));

        final UniversalCommunicationType email = factory.createUniversalCommunicationType();
        // EM = Electronic mail (SMPT)
        email.setURIID(createIdWithSchemeFromString(invoiceSeller.getSellerElectronicAddress(), invoiceSeller.getSellerElectronicAddressSchemeIdentifier()));
        // BT-34
        seller.setURIUniversalCommunication(email);

        // BT-31 (VAT Identifier)
        seller.getSpecifiedTaxRegistration().add(createVatTaxNumber(eInvoice, ContactType.SELLER));
        if (eInvoice.getInvoiceSeller().getSellerTaxRegistrationIdentifier() != null) {
            final TaxRegistrationType retval = factory.createTaxRegistrationType();
            retval.setID(createIdWithSchemeFromString(eInvoice.getInvoiceSeller().getSellerTaxRegistrationIdentifier(),
                    eInvoice.getInvoiceSeller().getSellerTaxRegistrationIdentifierSchemeIdentifier()));
            // BT-32 (Local Tax Identifier)
            seller.getSpecifiedTaxRegistration().add(retval);
        }

        // BT-30, BT-28 OK
        seller.setSpecifiedLegalOrganization(createSellerLegalOrganization(eInvoice));
        // if (preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR) ==
        // null) {
        // seller.getGlobalID().add(createIdFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TAXNR)));
        // } else {
        // seller.getGlobalID().add(createIdWithSchemeFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR),
        // "0088")); // "EAN" according to ISO 6523
        // }

        return seller;
    }

    /**
     * Details zur Organisation des Verkäufers
     * 
     * @param invoice
     * @param seller
     * @return
     */
    private LegalOrganizationType createSellerLegalOrganization(final EInvoice eInvoice) {
        final LegalOrganizationType retval = factory.createLegalOrganizationType();
        // BT-30
        retval.setID(createIdWithSchemeFromString(eInvoice.getInvoiceSeller().getSellerLegalRegistrationIdentifier(),
                eInvoice.getInvoiceSeller().getSellerLegalRegistrationIdentifierSchemeIdentifier()));

        // BT-28
        retval.setTradingBusinessName(createText(eInvoice.getInvoiceSeller().getSellerTradingName()));
        return retval;

    }

    /**
     * @param invoice
     * @param exchangedDocumentType
     */
    private NoteType createNote(final InvoiceNote invoiceNote) {
        final NoteType note = factory.createNoteType(); // free text on header
                                                        // level
        // BT-22
        note.setContent(createText(invoiceNote.getInvoiceNote()));
        // BT-21
        note.setSubjectCode(createCode(invoiceNote.getInvoiceNoteSubjectCode())); // see
                                                                                  // UNTDID
                                                                                  // 4451

        return note;
    }

    /**
     * Helpermethod
     * 
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
        // BT-126-00 OK
        retval.setAssociatedDocumentLineDocument(createDocumentLine(invoicePosition));
        // BG-31 OK
        retval.setSpecifiedTradeProduct(createTradeProduct(invoicePosition));
        // BG-29 OK
        retval.setSpecifiedLineTradeAgreement(createLineTradeAgreement(invoicePosition));
        // BT-129 OK
        retval.setSpecifiedLineTradeDelivery(createLineTradeDelivery(invoicePosition));
        // BG-30 OK
        retval.setSpecifiedLineTradeSettlement(createLineTradeSettlement(invoicePosition));

        return retval;
    }

    /**
     * 
     * Gruppierung von Angaben zum Produkt bzw. zur erbrachten Leistung. Eine
     * Gruppe von betriebswirtschaftlichen Begriffen, die Informationen über die
     * in Rechnung gestellten Waren und Dienstleistungen enthält.
     * 
     * @param item
     * @return
     */
    private TradeProductType createTradeProduct(final InvoicePosition invoicePosition) {
        // no need: BT-157, BT-156, BG-32, BT-158, BT-159
        final TradeProductType retval = factory.createTradeProductType();
        // BT-155
        retval.setSellerAssignedID(createIdFromString(invoicePosition.getItemSellersIdentifier()));
        // BT-153
        retval.setName(createText(invoicePosition.getItemName()));
        // BT-154
        retval.setDescription(createText(invoicePosition.getItemDescription()));

        return retval;
    }

    /**
     * Gruppierung von Angaben zur Abrechnung auf Positionsebene
     * 
     * @param item
     * @return
     */
    private LineTradeSettlementType createLineTradeSettlement(final InvoicePosition invoicePosition) {
        // We dont implement BT-28 since it is not allowed anymore, BT-128 (no
        // support in Fakturama), BT-133 (NA, Invoice line Buyer accounting
        // reference)
        final LineTradeSettlementType retval = factory.createLineTradeSettlementType();
        // BG-30
        retval.getApplicableTradeTax().add(createTradeTax(invoicePosition));
        // BT-131-00
        retval.setSpecifiedTradeSettlementLineMonetarySummation(createTradeSettlementLineMonetarySummation(invoicePosition));

        if (invoicePosition.getInvoiceLinePeriod() != null) {
            // BG-26
            retval.setBillingSpecifiedPeriod(createBillingSpecificPeriod(invoicePosition.getInvoiceLinePeriod()));
        }

        if (!invoicePosition.getInvoiceLineAllowances().isEmpty()) {
            // BG-27
            final Collection<TradeAllowanceChargeType> allowanceChargesList = createAllowanceCharges(invoicePosition);
            if (allowanceChargesList != null) {
                retval.getSpecifiedTradeAllowanceCharge().addAll(allowanceChargesList);
            }
        }
        return retval;
    }

    /**
     * mostly create allowances for invoice positions
     * 
     * @param invoicePosition
     * @return
     */
    private Collection<TradeAllowanceChargeType> createAllowanceCharges(final InvoicePosition invoicePosition) {
        final List<TradeAllowanceChargeType> allowanceOrCharges = new ArrayList<>();
        for (final InvoiceChargesAllowances invoiceAllowances : invoicePosition.getInvoiceLineAllowances()) {
            final TradeAllowanceChargeType tradeAllowanceCharge = factory.createTradeAllowanceChargeType();
            final IndicatorType indicator = factory.createIndicatorType();
            indicator.setIndicator(false); // its always allowance here
            tradeAllowanceCharge.setChargeIndicator(indicator);
            // BT-136
            tradeAllowanceCharge.setActualAmount(createAmount(invoiceAllowances.getAmount()));
            // BT-137
            tradeAllowanceCharge.setBasisAmount(createAmount(invoiceAllowances.getBaseAmount()));
            // BT-138
            tradeAllowanceCharge.setCalculationPercent(createPercentType(invoiceAllowances.getPercentage()));
            // BT-139
            tradeAllowanceCharge.setReason(createText(invoiceAllowances.getReason()));
            // BT-140
            if (StringUtils.trimToNull(invoiceAllowances.getReasonCode()) != null) {
                final AllowanceChargeReasonCodeType reasonCodeType = factory.createAllowanceChargeReasonCodeType();
                reasonCodeType.setValue(invoiceAllowances.getReasonCode());
                tradeAllowanceCharge.setReasonCode(reasonCodeType);
            }
            allowanceOrCharges.add(tradeAllowanceCharge);
        }
        return allowanceOrCharges.isEmpty() ? null : allowanceOrCharges;
    }

    /**
     * OK
     * Invoice Period on Position level
     * 
     * @param invoiceLinePeriod
     * @return the data or null
     */
    private SpecifiedPeriodType createBillingSpecificPeriod(final InvoiceLinePeriod invoiceLinePeriod) {
        final SpecifiedPeriodType retval = factory.createSpecifiedPeriodType();
        boolean set = false;
        if (invoiceLinePeriod.getInvoiceLinePeriodStartDate() != null) {
            // BT-134
            retval.setStartDateTime(createDateTime(invoiceLinePeriod.getInvoiceLinePeriodStartDate()));
            set = true;
        }

        if (invoiceLinePeriod.getInvoiceLinePeriodEndDate() != null) {
            // BT-135
            retval.setEndDateTime(createDateTime(invoiceLinePeriod.getInvoiceLinePeriodEndDate()));
            set = true;
        }
        return set ? retval : null;
    }

    /**
     * Invoice Period on document level (BG-14)
     * 
     * @param invoiceData
     * @return
     */
    private SpecifiedPeriodType createBillingSpecificPeriod(final InvoiceData invoiceData) {
        final SpecifiedPeriodType retval = factory.createSpecifiedPeriodType();
        boolean set = false;
        if (invoiceData.getInvoicingPeriodStartDate() != null) {
            // BT-73
            retval.setStartDateTime(createDateTime(invoiceData.getInvoicingPeriodStartDate()));
            set = true;
        }

        if (invoiceData.getInvoicingPeriodEndDate() != null) {
            // BT-74
            retval.setEndDateTime(createDateTime(invoiceData.getInvoicingPeriodEndDate()));
            set = true;
        }
        return set ? retval : null;
    }

    /**
     * Helpermethod
     * 
     * @param itemPosition
     * @return
     */
    private LineTradeDeliveryType createLineTradeDelivery(final InvoicePosition itemPosition) {
        final LineTradeDeliveryType retval = factory.createLineTradeDeliveryType();
        // BT-129, BT-130
        retval.setBilledQuantity(createQuantity(itemPosition.getInvoicedQuantity(), itemPosition.getInvoicedQuantityUnitOfMeasureCode()));
        return retval;
    }

    /**
     * Helpermethod
     * 
     * @param value
     * @param unit
     * @return
     */
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
        // BT-132
        if (StringUtils.trimToNull(itemPosition.getReferencedPurchaseOrderLineReference()) != null) {
            final ReferencedDocumentType referencedDocument = factory.createReferencedDocumentType();
            referencedDocument.setLineID(createIdFromString(itemPosition.getReferencedPurchaseOrderLineReference()));
            retval.setBuyerOrderReferencedDocument(referencedDocument);
        }
        // BT-148
        final AmountType grossAmount = createMoneyAmount(itemPosition.getItemGrossPrice());
        if (grossAmount != null) {
            final TradePriceType tradePriceTypeGross = factory.createTradePriceType();
            tradePriceTypeGross.setChargeAmount(grossAmount);
            retval.setGrossPriceProductTradePrice(tradePriceTypeGross);
        }
        // BT-146
        final TradePriceType tradePriceTypeNet = factory.createTradePriceType();
        tradePriceTypeNet.setChargeAmount(createMoneyAmount(itemPosition.getItemNetPrice()));
        if (itemPosition.getItemPriceBaseQuantity() != null) {
            // BT-149, 150 we do not set this at the moment
            // tradePriceTypeNet.setBasisQuantity(createQuantity(itemPosition.getItemPriceBaseQuantity(),
            // itemPosition.getItemPriceBaseQuantityUnitOfMeasure()));
        }
        retval.setNetPriceProductTradePrice(tradePriceTypeNet);

        return retval;
    }

    /**
     * @param invoicePosition
     * @return
     */
    private DocumentLineDocumentType createDocumentLine(final InvoicePosition invoicePosition) {
        final DocumentLineDocumentType retval = factory.createDocumentLineDocumentType();
        // BT-126
        retval.setLineID(createIdFromString(invoicePosition.getInvoiceLineIdentifier()));
        // BT-127: not used atm
        return retval;
    }

    /**
     * Detailinformationen zu Belegsummen.
     * 
     * @param invoice
     * @param documentSummary
     * @return
     */
    private TradeSettlementHeaderMonetarySummationType createTradeSettlementMonetarySummation(final EInvoice eInvoice) {
        // Not needed: BT-111
        final InvoiceDocumentTotals documentTotals = eInvoice.getInvoiceDocumentTotals();
        final String currency = eInvoice.getInvoiceData().getInvoiceCurrencyCode();
        final TradeSettlementHeaderMonetarySummationType retval = factory.createTradeSettlementHeaderMonetarySummationType();

        // BT-106
        retval.setLineTotalAmount(createAmount(documentTotals.getSumOfInvoiceLineNetAmount()));
        // BT-108
        retval.setChargeTotalAmount(createAmount(documentTotals.getSumOfChargesOnDocumentLevel()));
        // BT-107
        retval.setAllowanceTotalAmount(createAmount(documentTotals.getSumOfAllowancesOnDocumentLevel()));
        // BT-109
        retval.setTaxBasisTotalAmount(createAmount(documentTotals.getInvoiceTotalAmountWithoutVat()));
        // BT-110 (needs currency)
        retval.getTaxTotalAmount().add(createAmount(documentTotals.getInvoiceTotalVatAmount(), 2, currency));
        // BT-112
        retval.setGrandTotalAmount(createAmount(documentTotals.getInvoiceTotalAmountWithVat()));
        // BT-113
        retval.setTotalPrepaidAmount(createAmount(documentTotals.getPaidAmount()));
        // BT-115
        retval.setDuePayableAmount(createAmount(documentTotals.getAmountDueForPayment()));
        // BT-114
        retval.setRoundingAmount(createAmount(documentTotals.getRoundingAmount()));
        return retval;
    }

    /**
     * FOR BT-131
     * 
     * @param invoicePosition
     * @return
     */
    private TradeSettlementLineMonetarySummationType createTradeSettlementLineMonetarySummation(final InvoicePosition invoicePosition) {
        /*
         * Der Gesamtpositionsbetrag ist der Nettobetrag unter Berücksichtigung
         * von Zu- und Abschlägen ohne
         * Angabe des Umsatzsteuerbetrages.
         */
        final TradeSettlementLineMonetarySummationType retval = factory.createTradeSettlementLineMonetarySummationType();
        // BT-131
        retval.setLineTotalAmount(createAmount(invoicePosition.getInvoiceLineNetAmount()));

        // alle anderen hier angebotenen Felder resultieren nur aus dem XSD, die
        // sind in der Spezifikation
        // gar nicht aufgeführt (nur an anderer Stelle, wo sie sinnvoller sind)
        return retval;
    }

    /**
     * 
     * Detailinformationen zu Zahlungsbedingungen
     * 
     * @param invoice
     * @param documentSummary
     * @return
     */
    private TradePaymentTermsType createTradePaymentTerms(final EInvoice eInvoice) {
        final TradePaymentTermsType tradePaymentTerms = factory.createTradePaymentTermsType();
        // BT-20
        tradePaymentTerms.setDescription(createText(eInvoice.getInvoiceData().getPaymentTerms()));
        // BT-9
        tradePaymentTerms.setDueDateDateTime(createDateTime(eInvoice.getInvoiceData().getPaymentDueDate()));
        return tradePaymentTerms;
    }

    /**
     * Helpermethod
     * 
     * @param isCharge:
     *            true if charge, false if allowance
     */
    private IndicatorType createIndicator(final boolean isCharge) {
        final IndicatorType indicator = factory.createIndicatorType();
        indicator.setIndicator(isCharge);
        return indicator;
    }

    /**
     * Detailangaben zu Steuern auf Positionsebene
     * 
     * @param vatDocument
     * @return
     */
    private TradeTaxType createTradeTax(final InvoicePosition invoicePosition) {
        final TradeTaxType retval = factory.createTradeTaxType();
        // BT-152
        retval.setRateApplicablePercent(createPercentType(invoicePosition.getInvoicedItemVatRate()));
        // BT-151
        retval.setCategoryCode(createTaxCategoryCode(invoicePosition.getInvoicedItemVatCategoryCode()));
        // BT-151-0
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
        // BT-120, BT-121: not yet
        // TODO check that no VAT is twice added (error in calculation?)
        final TradeTaxType retval = factory.createTradeTaxType();
        // BT-117
        retval.setCalculatedAmount(createAmount(invoiceVatBreakdown.getVatCategoryTaxAmount()));

        // BT-119
        retval.setRateApplicablePercent(createPercentType(invoiceVatBreakdown.getVatCategoryRate()));
        // BT-116
        retval.setBasisAmount(createAmount(invoiceVatBreakdown.getVatCategoryTaxableAmount()));
        // BT-118
        retval.setCategoryCode(createTaxCategoryCode(invoiceVatBreakdown.getVatCategoryCode()));
        // BT-118-0
        retval.setTypeCode(createTaxTypeCode("VAT"));
        if (StringUtils.containsAny(invoiceVatBreakdown.getVatCategoryCode(), UNTDID5305.E.getCode(), UNTDID5305.Z.getCode())
                && invoiceVatBreakdown.getVatExemptionReasonText() != null) {
            retval.setExemptionReason(createText(invoiceVatBreakdown.getVatExemptionReasonText()));
        }
        return retval;
    }

    /**
     * Detailangaben zu Steuern im Allowance/charge document section
     * 
     * @param vatSummaryItem
     * @return
     */
    private TradeTaxType createTradeTax(final InvoiceChargesAllowances invoiceChargesAllowances, final String currencyCode) {
        // BT-120, BT-121: not yet
        // TODO check that no VAT is twice added (error in calculation?)
        final TradeTaxType retval = factory.createTradeTaxType();

        // BT-103
        retval.setRateApplicablePercent(createPercentType(invoiceChargesAllowances.getVatRate()));
        // BT-102
        retval.setCategoryCode(createTaxCategoryCode(invoiceChargesAllowances.getVatCategoryCode()));
        // BT-102-0
        retval.setTypeCode(createTaxTypeCode("VAT"));
        return retval;
    }

    /**
     * Helpermethod
     * 
     * @param string
     * @return
     */
    private TaxTypeCodeType createTaxTypeCode(final String string) {
        final TaxTypeCodeType retval = factory.createTaxTypeCodeType();
        retval.setValue(string);
        return retval;
    }

    /**
     * Helpermethod
     * 
     * @param taxCat
     * @return
     */
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
    private AmountType createAmount(final BigDecimal amount) {
        return createAmount(amount, 2, null);
    }

    /**
     * creates an Amount field
     * 
     * @param the
     *            VAT value
     * @return
     */
    private AmountType createQuantityAmount(final BigDecimal amount) {
        return createAmount(amount, customQuantityScale, null);
    }

    /**
     * creates an Amount field
     * 
     * @param the
     *            VAT value
     * @return
     */
    private AmountType createMoneyAmount(final BigDecimal amount) {
        return createAmount(amount, customMoneyScale, null);
    }

    /**
     * Creates an Amount with given value, scale and currency.
     * 
     * @param amount
     * @param scale
     * @param withCurrency
     * @return
     */
    private AmountType createAmount(final BigDecimal amount, final int scale, final String currency) {
        if (amount == null) {
            return null;
        }
        final BigDecimal scaledValue = amount.setScale(scale, RoundingMode.HALF_UP);

        final AmountType retval = factory.createAmountType();
        retval.setValue(scaledValue);
        retval.setCurrencyID(currency != null ? currency : null);
        return retval;
    }

    /**
     * @param eInvoice
     * @return
     */
    private CreditorFinancialAccountType createCreditorAccount(final EInvoice eInvoice) {
        // dont need: ProprietaryID (also BT-84)
        CreditorFinancialAccountType retval = null;
        if (eInvoice.getInvoicePayment().getInvoiceCreditTransfers() != null && !eInvoice.getInvoicePayment().getInvoiceCreditTransfers().isEmpty()) {

            retval = factory.createCreditorFinancialAccountType();
            final InvoiceCreditTransfer invoiceCreditTransfer = eInvoice.getInvoicePayment().getInvoiceCreditTransfers().get(0);
            // BT-84
            retval.setIBANID(createIdFromString(invoiceCreditTransfer.getPaymentAccountIdentifier()));
            // BT-85
            retval.setAccountName(createText(invoiceCreditTransfer.getPaymentAccountName()));
        }
        return retval;
    }

    /**
     * Helpermethod
     * 
     * @param eInvoice
     * @param contactType
     * @return
     */
    private TaxRegistrationType createVatTaxNumber(final EInvoice eInvoice, final ContactType contactType) {
        TaxRegistrationType retval = factory.createTaxRegistrationType();
        switch (contactType) {
        case SELLER:
            final String companyVatNo = eInvoice.getInvoiceSeller().getSellerVatIdentifier();
            retval.setID(createIdWithSchemeFromString(StringUtils.trimToNull(companyVatNo), "VA"));
            break;
        case BUYER:
            final String buyerVatNo = eInvoice.getInvoiceBuyer().getBuyerVatIdentifier();
            retval.setID(createIdWithSchemeFromString(StringUtils.trimToNull(buyerVatNo), "VA"));
            break;
        default:
            retval = null;
            break;
        }
        return retval.getID() == null ? null : retval;
    }

    /**
     * Helperclass
     * 
     * @param value
     * @return
     */
    private CodeType createCode(final String value) {
        final CodeType codeType = factory.createCodeType();
        codeType.setValue(value);
        return codeType;
    }

    /**
     * Helperclass
     * 
     * @param addressData
     * @return
     */
    private TradeAddressType createAddress(final AddressData addressData) {
        final TradeAddressType retval = factory.createTradeAddressType();
        retval.setPostcodeCode(createCode(addressData.getPostCode()));
        retval.setLineOne(createText(addressData.getAddressLine1()));
        retval.setLineTwo(createText(addressData.getAddressLine2()));
        retval.setLineThree(createText(addressData.getAddressLine3()));
        retval.setCityName(createText(addressData.getCity()));
        retval.setCountryID(createCountry(addressData.getCountryCode())); // Nur
                                                                          // die
                                                                          // Alpha-2
                                                                          // Darstellung
                                                                          // darf
                                                                          // verwendet
                                                                          // werden
        retval.setCountrySubDivisionName(createText(addressData.getCountrySubdivision()));
        return retval;
    }

    /**
     * Helpermethod
     * 
     * @param countryStr
     * @return
     */
    private CountryIDType createCountry(final String countryStr) {
        // null values aren't allowed!
        final CountryIDType countryTypeId = factory.createCountryIDType();
        countryTypeId.setValue(Optional.ofNullable(countryStr).orElse("DE"));
        return countryTypeId;
    }

    /**
     * Create Contact Person information
     * 
     * @param eInvoice
     * @param contactType
     * @return
     */
    private TradeContactType createContact(final EInvoice eInvoice, final ContactType contactType) {
        final TradeContactType contact = factory.createTradeContactType();
        boolean somethingSet = false;
        final UniversalCommunicationType email = factory.createUniversalCommunicationType();
        switch (contactType) {
        case SELLER:
            final InvoiceSeller invoiceSeller = eInvoice.getInvoiceSeller();
            // BT-41
            contact.setPersonName(createText(StringUtils.trimToNull(invoiceSeller.getSellerContactPoint())));
            // BT-42
            contact.setTelephoneUniversalCommunication(createCommunicationItem(StringUtils.trimToNull(invoiceSeller.getSellerContactTelephoneNumber())));
            email.setURIID(createIdFromString(StringUtils.trimToNull(invoiceSeller.getSellerContactEmailAddress())));
            // BT-43
            contact.setEmailURIUniversalCommunication(email.getURIID() == null ? null : email);

            break;
        case BUYER:
            final InvoiceBuyer invoiceBuyer = eInvoice.getInvoiceBuyer();
            // BT-56
            contact.setPersonName(createText(StringUtils.trimToNull(invoiceBuyer.getBuyerContactPoint())));
            // BT-57
            contact.setTelephoneUniversalCommunication(createCommunicationItem(StringUtils.trimToNull(invoiceBuyer.getBuyerContactTelephoneNumber())));
            // BT-58
            email.setURIID(createIdWithSchemeFromString(StringUtils.trimToNull(invoiceBuyer.getBuyerContactEmailAddress()), "EM"));
            contact.setEmailURIUniversalCommunication(email.getURIID() == null ? null : email);
            break;
        default:
            break;
        }
        somethingSet = contact.getPersonName() != null || contact.getTelephoneUniversalCommunication() != null
                || contact.getEmailURIUniversalCommunication() != null;
        return somethingSet ? contact : null;
    }

    /**
     * Helpermethod
     * 
     * @param communicationItem
     * @return
     */
    private UniversalCommunicationType createCommunicationItem(final String communicationItem) {
        final UniversalCommunicationType universalCommunicationType = factory.createUniversalCommunicationType();
        universalCommunicationType.setCompleteNumber(createText(communicationItem));
        return universalCommunicationType.getCompleteNumber() == null ? null : universalCommunicationType;
    }

    /**
     * Helpermethod
     * 
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
     * Helpermethod
     * 
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

    /**
     * Helpermethod
     * 
     * @param idString
     * @return
     */
    private IDType createIdFromString(final String idString) {
        return createIdWithSchemeFromString(idString, null);
    }

    /**
     * Helpermethod
     * 
     * @param idString
     * @param scheme
     * @return
     */
    private IDType createIdWithSchemeFromString(final String idString, final String scheme) {
        final IDType idType = factory.createIDType();
        idType.setValue(idString);
        idType.setSchemeID(scheme);

        return idString != null ? idType : null;
    }

}
