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
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.money.MonetaryAmount;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.fakturama.export.einvoice.ConformanceLevel;
import org.fakturama.export.facturx.modelgen.AmountType;
import org.fakturama.export.facturx.modelgen.CodeType;
import org.fakturama.export.facturx.modelgen.CountryIDContentType;
import org.fakturama.export.facturx.modelgen.CountryIDType;
import org.fakturama.export.facturx.modelgen.CreditorFinancialAccountType;
import org.fakturama.export.facturx.modelgen.CreditorFinancialInstitutionType;
import org.fakturama.export.facturx.modelgen.CrossIndustryInvoiceType;
import org.fakturama.export.facturx.modelgen.CurrencyCodeContentType;
import org.fakturama.export.facturx.modelgen.CurrencyCodeType;
import org.fakturama.export.facturx.modelgen.DateTimeType;
import org.fakturama.export.facturx.modelgen.DebtorFinancialAccountType;
import org.fakturama.export.facturx.modelgen.DocumentCodeContentType;
import org.fakturama.export.facturx.modelgen.DocumentCodeType;
import org.fakturama.export.facturx.modelgen.DocumentContextParameterType;
import org.fakturama.export.facturx.modelgen.DocumentLineDocumentType;
import org.fakturama.export.facturx.modelgen.ExchangedDocumentContextType;
import org.fakturama.export.facturx.modelgen.ExchangedDocumentType;
import org.fakturama.export.facturx.modelgen.FormattedDateTimeType;
import org.fakturama.export.facturx.modelgen.FormattedDateTimeType.DateTimeString;
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
import org.fakturama.export.facturx.modelgen.PaymentMeansCodeContentType;
import org.fakturama.export.facturx.modelgen.PaymentMeansCodeType;
import org.fakturama.export.facturx.modelgen.PercentType;
import org.fakturama.export.facturx.modelgen.QuantityType;
import org.fakturama.export.facturx.modelgen.ReferencedDocumentType;
import org.fakturama.export.facturx.modelgen.SpecifiedPeriodType;
import org.fakturama.export.facturx.modelgen.SupplyChainEventType;
import org.fakturama.export.facturx.modelgen.SupplyChainTradeLineItemType;
import org.fakturama.export.facturx.modelgen.SupplyChainTradeTransactionType;
import org.fakturama.export.facturx.modelgen.TaxCategoryCodeContentType;
import org.fakturama.export.facturx.modelgen.TaxCategoryCodeType;
import org.fakturama.export.facturx.modelgen.TaxRegistrationType;
import org.fakturama.export.facturx.modelgen.TaxTypeCodeContentType;
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

import com.sebulli.fakturama.calculate.DocumentSummaryCalculator;
import com.sebulli.fakturama.dto.DocumentSummary;
import com.sebulli.fakturama.dto.Price;
import com.sebulli.fakturama.dto.Transaction;
import com.sebulli.fakturama.dto.VatSummaryItem;
import com.sebulli.fakturama.dto.VatSummarySetManager;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.model.Address;
import com.sebulli.fakturama.model.BankAccount;
import com.sebulli.fakturama.model.CEFACTCode;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.DocumentItem;
import com.sebulli.fakturama.model.DocumentReceiver;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.model.VAT;
import com.sebulli.fakturama.office.TemplateProcessor;
import com.sebulli.fakturama.util.ContactUtil;
import com.sebulli.fakturama.util.DocumentTypeUtil;

import jakarta.xml.bind.JAXBElement;

/**
 * Create an XRechnung XML.
 */
public class XRechnung extends AbstractEInvoice {

    private ContactUtil contactUtil;

    private DocumentAllowances itemAllowances;

    @Override
    public JAXBElement<CrossIndustryInvoiceType> getInvoiceXml(final Optional<Invoice> invoiceDoc) {
        if (!invoiceDoc.isPresent()) {
            return null;
        }
        contactUtil = ContextInjectionFactory.make(ContactUtil.class, eclipseContext);
        factory = new ObjectFactory();
        itemAllowances = new DocumentAllowances();

        Document invoice = invoiceDoc.get();
        // Recalculate the sum of the document before exporting
        DocumentSummaryCalculator documentSummaryCalculator = ContextInjectionFactory.make(DocumentSummaryCalculator.class, eclipseContext);
        DocumentSummary documentSummary = documentSummaryCalculator.calculate(invoice);

        CrossIndustryInvoiceType root = new CrossIndustryInvoiceType();
        // at first create a reasonable context
        DocumentContextParameterType ctxParam = factory.createDocumentContextParameterType();
        ctxParam.setID(createIdFromString(ConformanceLevel.XRECHNUNG.getUrn()));

        // TODO specify the type in DocumentEditor (free text?)
        ExchangedDocumentContextType exchangedDocCtx = factory.createExchangedDocumentContextType();
        exchangedDocCtx.setGuidelineSpecifiedDocumentContextParameter(ctxParam)
        /*      .setBusinessProcessSpecifiedDocumentContextParameter(
                        factory.createDocumentContextParameterType()
                        .setID(createIdFromString("Baurechnung")))*/;

        root.setExchangedDocumentContext(exchangedDocCtx);

        // now the header information follows
        ExchangedDocumentType exchangedDocumentType = factory.createExchangedDocumentType();
        exchangedDocumentType.setID(createIdFromString(invoice.getName()));
        DocumentType documentType = DocumentTypeUtil.findByBillingType(invoice.getBillingType());

        if (documentType.getCode() > 0) {
            DocumentCodeType docTypeCode = factory.createDocumentCodeType();
            docTypeCode.setValue(DocumentCodeContentType.fromValue(Integer.toString(documentType.getCode())));
            exchangedDocumentType.setTypeCode(docTypeCode);
        }
        exchangedDocumentType.setIssueDateTime(createDateTime(invoice.getDocumentDate()));

        Optional.ofNullable(createNote(invoice.getMessage())).ifPresent(n -> exchangedDocumentType.getIncludedNote().add(n));
        Optional.ofNullable(createNote(invoice.getMessage2())).ifPresent(n -> exchangedDocumentType.getIncludedNote().add(n));
        Optional.ofNullable(createNote(invoice.getMessage3())).ifPresent(n -> exchangedDocumentType.getIncludedNote().add(n));

        NoteType note = factory.createNoteType();

        String owner = String.format("%s%n%s%n%s%n%s %s%n%s", preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER),
                preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME), preferences.getString(Constants.PREFERENCES_YOURCOMPANY_STREET),
                preferences.getString(Constants.PREFERENCES_YOURCOMPANY_ZIP), preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CITY),
                preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR));
        if (StringUtils.isBlank(owner)) {
            MessageDialog.openWarning(shell, messages.dialogMessageboxTitleWarning, zfMsg.zugferdExportErrorEmptycompanypref);
            owner = "(unknown)";
        }

        note.setContent(createText(owner)); // should only be free text for information about the invoice
        note.setSubjectCode(createCode("REG")); // see UNTDID 4451, explains the note content
        exchangedDocumentType.getIncludedNote().add(note);
        root.setExchangedDocument(exchangedDocumentType);

        // now follows the huge part for trade transaction
        SupplyChainTradeTransactionType tradeTransaction = factory.createSupplyChainTradeTransactionType();
        HeaderTradeAgreementType tradeAgreement = factory.createHeaderTradeAgreementType();
        tradeAgreement.setBuyerReference(createText(invoice.getCustomerRef())); // "Kundenreferenz"
        tradeTransaction.setApplicableHeaderTradeAgreement(tradeAgreement);

        // create seller information
        tradeAgreement.setSellerTradeParty(createSeller(invoice));

        // create buyer information
        TradePartyType buyer = createBuyer(invoice);
        if (buyer != null) {
            tradeAgreement.setBuyerTradeParty(buyer);
        }

        //        tradeAgreement.setSellerTaxRepresentativeTradeParty(value);  // Steuerbevollmächtigter des Verkäufers
        //        tradeAgreement.setSellerOrderReferencedDocument(value); // Detailangaben zur zugehörigen Auftragsbestätigung

        // referenced order
        Transaction transaction = ContextInjectionFactory.make(Transaction.class, eclipseContext).of(invoice);
        if (transaction != null) {
            ReferencedDocumentType orderRef = factory.createReferencedDocumentType();
            orderRef.setIssuerAssignedID(createIdFromString(transaction.getReference(DocumentType.ORDER)));
            // only if ID is not empty!
            if (!StringUtils.isEmpty(transaction.getReference(DocumentType.ORDER))) {
                tradeAgreement.setBuyerOrderReferencedDocument(orderRef);
            }
        }

        // there is no contract information in Fakturama!
        //          ReferencedDocumentType contractRef = factory.createReferencedDocumentType()
        //                  .setIssuerAssignedID(createIdFromString("contractNumber"));
        //          tradeAgreement.setContractReferencedDocument(contractRef);

        //          ReferencedDocumentType additionalReferencedDocument = factory.createReferencedDocumentType()
        //                  .setIssuerAssignedID(createIdFromString("contractNumber"))
        //                  .setURIID(createIdFromString("URI"))
        //                  .setTypeCode(factory.createDocumentCodeType().setValue("130"))  // UNTDID 1001
        //                  .setName(createText("AttachmentName"))
        //                  .setAttachmentBinaryObject(factory.createBinaryObjectType()
        //                          .setFilename("filename").setMimeCode("mime").setValue(new byte[] {}))
        //                  ;
        //          tradeAgreement.setAdditionalReferencedDocument(additionalReferencedDocument);

        //          tradeAgreement.setSpecifiedProcuringProject(factory.createProcuringProjectType().setName("").setID(createIdFromString("Project")));

        SupplyChainEventType deliveryEvent = factory.createSupplyChainEventType();
        deliveryEvent.setOccurrenceDateTime(createDateTime(invoice.getServiceDate()));

        HeaderTradeDeliveryType headerTradeDeliveryType = factory.createHeaderTradeDeliveryType();
        headerTradeDeliveryType.setShipToTradeParty(buyer);
        headerTradeDeliveryType.setActualDeliverySupplyChainEvent(deliveryEvent)
        //                .setDespatchAdviceReferencedDocument(value)
        //                .setReceivingAdviceReferencedDocument(value)
        ;

        tradeTransaction.setApplicableHeaderTradeDelivery(headerTradeDeliveryType);

        CurrencyCodeContentType currency = getGlobalCurrencyCode();

        // Verwendungszweck, Kassenzeichen 
        HeaderTradeSettlementType tradeSettlement = factory.createHeaderTradeSettlementType();
        //                .setCreditorReferenceID(createIdWithSchemeFromString(idString, scheme))
        tradeSettlement.setPaymentReference(createText(invoice.getName()));
        //                .setTaxCurrencyCode(createCurrencyCode(currency))  // see ISO 4217
        tradeSettlement.setInvoiceCurrencyCode(createCurrencyCode(currency));

        // TODO tradeSettlement.setPayeeTradeParty(value);   // Zahlungsempfänger
        DocumentReceiver documentReceiver = addressManager.getBillingAdress(invoice);
        Contact contact = getOriginContact(documentReceiver);
        TradeSettlementPaymentMeansType paymentType;
        if (contact != null) {
            DebtorFinancialAccountType debtorAccount = createDebtorAccount(contact.getBankAccount());

            paymentType = factory.createTradeSettlementPaymentMeansType();
            paymentType.setTypeCode(createPaymentTypeCode(invoice));
            paymentType.setInformation(createText(invoice.getPayment().getName()));
            //                    .setApplicableTradeSettlementFinancialCard(value)
            paymentType.setPayerPartyDebtorFinancialAccount(debtorAccount)
            //                                .setPaymentReference(createText(invoice.getName())) /* customerref ? */
            ;

        } else {
            paymentType = factory.createTradeSettlementPaymentMeansType();
            paymentType.setTypeCode(createPaymentTypeCode(invoice));
            paymentType.setInformation(createText(invoice.getPayment().getName()));
        }
        CreditorFinancialAccountType creditor = createCreditorAccount();
        if (creditor != null) {
            paymentType.setPayeePartyCreditorFinancialAccount(creditor);
            paymentType.setPayeeSpecifiedCreditorFinancialInstitution(createCreditorFinancialInstitution());
        }
        tradeSettlement.getSpecifiedTradeSettlementPaymentMeans().add(paymentType);

        // Get the items of the UniDataSet document
        invoice.getItems().forEach(item -> tradeTransaction.getIncludedSupplyChainTradeLineItem().add(createLineItem(item)));

        // Detailinformationen zur Rechnungsperiode 
        if (invoice.getVestingPeriodStart() != null || invoice.getVestingPeriodEnd() != null) {
            SpecifiedPeriodType billingSpecificPeriod = createBillingSpecificPeriod(invoice);
            tradeSettlement.setBillingSpecifiedPeriod(billingSpecificPeriod);
        }

        // TODO tradeSettlement.setBillingSpecifiedPeriod(createPeriod(invoice));
        // Abschläge / Zuschläge nur aufführen wenn sie auch tatsächlich angefallen sind! 
        if (Optional.ofNullable(invoice.getItemsRebate()).orElse(Double.valueOf(0.0)).compareTo(Double.valueOf(0.0)) != 0) {
            tradeSettlement.getSpecifiedTradeAllowanceCharge().add(createTradeAllowance(documentSummary, invoice));
        }
        // Hier kommen auch die Versandkosten mit rein 
        // (die sind nur bei EXTENDED in einem extra Node)
        if (invoice.getShipping() != null && invoice.getShipping().getShippingValue() > 0 || invoice.getShippingValue() > 0) {
            tradeSettlement.getSpecifiedTradeAllowanceCharge().add(createTradeAllowance(invoice));
        }
        tradeSettlement.getSpecifiedTradePaymentTerms().add(createTradePaymentTerms(invoice, documentSummary));
        tradeSettlement.setSpecifiedTradeSettlementHeaderMonetarySummation(createTradeSettlementMonetarySummation(invoice, documentSummary));

        //        tradeSettlement.setInvoiceReferencedDocument(factory.createReferencedDocumentType()
        //                .setIssuerAssignedID(createIdFromString("previousInvoice"))
        //                // Rechnungsdatum der vorausgegangenen Rechnung
        //                .setFormattedIssueDateTime(createFormattedDateTime(new Date())))   
        //        ;

        //  tradeSettlement.setReceivableSpecifiedTradeAccountingAccount(null);

        // Get the VAT summary of the UniDataSet document
        VatSummarySetManager vatSummarySetManager = ContextInjectionFactory.make(VatSummarySetManager.class, eclipseContext);
        vatSummarySetManager.getVatSummaryItems().clear();
        vatSummarySetManager.add(invoice, Double.valueOf(1.0));
        for (VatSummaryItem vatSummaryItem : vatSummarySetManager.getVatSummaryItems()) {
            // für jeden Steuerbetrag muß es einen eigenen Eintrag geben
            tradeSettlement.getApplicableTradeTax().add(createTradeTax(vatSummaryItem));
        }
        tradeTransaction.setApplicableHeaderTradeSettlement(tradeSettlement);
        root.setSupplyChainTradeTransaction(tradeTransaction);
        return new ObjectFactory().createCrossIndustryInvoice(root);
    }

    private TradePartyType createBuyer(final Document invoice) {
        DocumentReceiver documentReceiver = addressManager.getBillingAdress(invoice);
        if (documentReceiver != null) {
            TradePartyType buyer = factory.createTradePartyType();
            buyer.setName(createText(invoice.getAddressFirstLine()));
            //                .setSpecifiedLegalOrganization(createLegalOrganizationType(invoice))  // not available
            buyer.setPostalTradeAddress(createAddress(invoice, ContactType.BUYER))
            //                    .setURIUniversalCommunication(email)
            //                    .setSpecifiedTaxRegistration(createTaxNumber(invoice, ContactType.BUYER))
            ;
            Long originContactId = invoice.getReceiver() != null && !invoice.getReceiver().isEmpty() ? invoice.getReceiver().get(0).getOriginContactId()
                    : Long.valueOf(0);
            if (originContactId != null && originContactId != 0) {
                Contact originContact = contactsDAO.findById(originContactId);
                String schemaId = "0088";
                String globalId = Optional.ofNullable(originContact.getGln()).orElse(Long.valueOf(0)).toString();
                String debtorId = documentReceiver.getCustomerNumber();

                // additional fields from customer note takes precedence over "regular" fields 
                if (originContact.getNote() != null) {
                    debtorId = grepFromNoteField(originContact.getNote(), ".*?Debtor ID=(\\p{Alnum}+).*", debtorId);
                    globalId = grepFromNoteField(originContact.getNote(), ".*?Global ID=(\\p{Alnum}+).*", globalId);
                    schemaId = grepFromNoteField(originContact.getNote(), ".*?Global schemeID=(\\d+).*", schemaId);
                }

                if (StringUtils.isNotEmpty(globalId)) {
                    buyer.getGlobalID().add(createIdWithSchemeFromString(globalId, StringUtils.defaultString(schemaId)));
                } else {
                    buyer.getID().add(createIdFromString(debtorId));
                }
            } else {
                buyer.getID().add(createIdFromString(documentReceiver.getCustomerNumber()));
            }
            return buyer;
        }
        return null;
    }

    private String grepFromNoteField(final String noteField, final String searchPattern, final String defaultString) {
        // try to find a global schema id from note field (workaround)
        Pattern p = Pattern.compile(searchPattern, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(noteField);
        noteField.matches("Global.*");
        return m.matches() ? m.group(1) : defaultString;
    }

    private TradePartyType createSeller(final Document invoice) {
        UniversalCommunicationType email = factory.createUniversalCommunicationType();
        // EM = Electronic mail (SMPT)
        email.setURIID(createIdWithSchemeFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_EMAIL), "EM"));

        TradePartyType seller = factory.createTradePartyType();
        //              .setID(createIdFromString(""))  // Kennung des Verkäufers (Durch den Kunden zugewiesene Lieferantennummer)
        seller.setName(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME)));
        //              .setDescription(createText(""))  // Sonstige rechtliche Informationen des Verkäufers
        seller.setSpecifiedLegalOrganization(createLegalOrganizationType(invoice));
        seller.getDefinedTradeContact().add(createContact(invoice, ContactType.SELLER));
        seller.setPostalTradeAddress(createAddress(invoice, ContactType.SELLER));
        seller.setURIUniversalCommunication(email);
        seller.getSpecifiedTaxRegistration().add(createTaxNumber(invoice, ContactType.SELLER));
        seller.setSpecifiedLegalOrganization(createLegalOrganization(invoice, ContactType.SELLER));
        if (preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR) == null) {
            seller.getGlobalID().add(createIdFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TAXNR)));
        } else {
            seller.getGlobalID().add(createIdWithSchemeFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR), "0088")); // "EAN" according to ISO 6523
        }

        return seller;
    }

    /**
     * Details zur Organisation
     * 
     * @param invoice
     * @param seller
     * @return
     */
    private LegalOrganizationType createLegalOrganization(final Document invoice, final ContactType seller) {
        LegalOrganizationType retval = factory.createLegalOrganizationType();
        retval.setID(createIdFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR)));
        if (!preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME).equalsIgnoreCase(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER))) {
            retval.setTradingBusinessName(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME)));
        }
        return retval;
    }

    private LegalOrganizationType createLegalOrganizationType(final Document invoice) {
        LegalOrganizationType retval = factory.createLegalOrganizationType();
        retval.setID(createIdFromString("GTIN"));
        retval.setTradingBusinessName(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME)));
        return retval;
    }

    /**
     * @param invoice
     * @param exchangedDocumentType
     */
    private NoteType createNote(final String message) {
        NoteType note = null;
        if (!StringUtils.isEmpty(message)) {
            note = factory.createNoteType(); // free text on header level
            note.setContent(createText(message));
            note.setSubjectCode(createCode("AAK")); // see UNTDID 4451
        }
        return note;
    }

    /**
     * @return
     */
    private CurrencyCodeContentType getGlobalCurrencyCode() {
        String currency = DataUtils.getInstance().getDefaultCurrencyUnit().getCurrencyCode();
        CurrencyCodeContentType retval;
        // TODO later on we will use JSR 354...
        switch (currency) {
        case "€":
            retval = CurrencyCodeContentType.EUR;
            break;
        case "$":
            retval = CurrencyCodeContentType.USD;
            break;
        default:
            retval = CurrencyCodeContentType.EUR;
            break;
        }
        return retval;
    }

    /**
     * Gruppierung der Informationen zum Geschäftsvorfall
     * 
     * @param item
     * @return
     */
    private SupplyChainTradeLineItemType createLineItem(final DocumentItem item) {
        SupplyChainTradeLineItemType retval = factory.createSupplyChainTradeLineItemType();
        retval.setAssociatedDocumentLineDocument(createDocumentLine(item));
        retval.setSpecifiedTradeProduct(createTradeProduct(item));
        retval.setSpecifiedLineTradeAgreement(createLineTradeAgreement(item));
        retval.setSpecifiedLineTradeDelivery(createLineTradeDelivery(item));
        retval.setSpecifiedLineTradeSettlement(createLineTradeSettlement(item));

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
    private TradeProductType createTradeProduct(final DocumentItem item) {
        TradeProductType retval = factory.createTradeProductType();
        //          .setGlobalID(createIdFromString("EAN")) // see ISO 6523
        retval.setSellerAssignedID(createIdFromString(item.getItemNumber())); // perhaps GTIN?
        retval.setBuyerAssignedID(createIdFromString("buyerassigned ID"));
        retval.setName(createText(item.getName()));
        retval.setDescription(createText(item.getDescription()))
        //                .setDesignatedProductClassification(values)  // see UNTDID 7143
        //                .setApplicableProductCharacteristic(item.getProduct().getAttributes())
        //                .setOriginTradeCountry(createTradeCountry(item))
        ;
        return retval;
    }

    private TradeCountryType createTradeCountry(final DocumentItem item) {
        TradeCountryType retval = factory.createTradeCountryType();
        retval.setID(createCountry("DE"));
        return retval;
    }

    /**
     * Gruppierung von Angaben zur Abrechnung auf Positionsebene
     * 
     * @param item
     * @return
     */
    private LineTradeSettlementType createLineTradeSettlement(final DocumentItem item) {
        LineTradeSettlementType retval = factory.createLineTradeSettlementType();
        retval.getApplicableTradeTax().add(createTradeTax(item.getItemVat()));
        retval.setSpecifiedTradeSettlementLineMonetarySummation(createTradeSettlementLineMonetarySummation(item))
        //               .setAdditionalReferencedDocument(null);
        //                .setReceivableSpecifiedTradeAccountingAccount(null)
        ;
        if (item.getVestingPeriodStart() != null || item.getVestingPeriodEnd() != null) {
            retval.setBillingSpecifiedPeriod(createBillingSpecificPeriod(item));
        }

        if (item.getItemRebate() != null && item.getItemRebate().compareTo(Double.valueOf(0.0)) != 0) {
            retval.getSpecifiedTradeAllowanceCharge().addAll(createAllowanceCharges(item));
        }
        return retval;
    }

    private Collection<TradeAllowanceChargeType> createAllowanceCharges(final DocumentItem item) {
        List<TradeAllowanceChargeType> charges = new ArrayList<>();
        TradeAllowanceChargeType charge = createTradeAllowance(item);
        charges.add(charge);
        return charges;
    }

    private SpecifiedPeriodType createBillingSpecificPeriod(final DocumentItem item) {
        SpecifiedPeriodType retval = factory.createSpecifiedPeriodType();
        if (item.getVestingPeriodStart() != null) {
            retval.setStartDateTime(createDateTime(item.getVestingPeriodStart()));
        }

        if (item.getVestingPeriodEnd() != null) {
            retval.setEndDateTime(createDateTime(item.getVestingPeriodEnd()));
        }
        return retval;
    }

    private SpecifiedPeriodType createBillingSpecificPeriod(final Document item) {
        SpecifiedPeriodType retval = factory.createSpecifiedPeriodType();
        if (item.getVestingPeriodStart() != null) {
            retval.setStartDateTime(createDateTime(item.getVestingPeriodStart()));
        }

        if (item.getVestingPeriodEnd() != null) {
            retval.setEndDateTime(createDateTime(item.getVestingPeriodEnd()));
        }
        return retval;
    }

    private LineTradeDeliveryType createLineTradeDelivery(final DocumentItem item) {
        String qunit = determineQuantityUnit(item.getQuantityUnit());
        LineTradeDeliveryType retval = factory.createLineTradeDeliveryType();
        retval.setBilledQuantity(createQuantity(item.getQuantity(), qunit));
        return retval;
    }

    private QuantityType createQuantity(final Double value, final String unit) {
        QuantityType retval = factory.createQuantityType();
        retval.setValue(BigDecimal.valueOf(value));
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
    private LineTradeAgreementType createLineTradeAgreement(final DocumentItem item) {
        LineTradeAgreementType retval = factory.createLineTradeAgreementType();
        //          .setBuyerOrderReferencedDocument(createBuyerOrderReferencedDocument(item))
        retval.setGrossPriceProductTradePrice(createTradePrice(item, PriceType.GROSS_PRICE));
        retval.setNetPriceProductTradePrice(createTradePrice(item, PriceType.NET_PRICE_DISCOUNTED));
        return retval;
    }

    /**
     * Detailangaben zur zugehörigen Bestellung
     * 
     * @param item
     * @return
     */
    private ReferencedDocumentType createBuyerOrderReferencedDocument(final DocumentItem item) {
        // hier ist die Position in der zugehörigen Bestellung gemeint!
        ReferencedDocumentType retval = factory.createReferencedDocumentType();
        retval.setLineID(createIdFromString(Integer.toString(item.getPosNr())));
        return retval;
    }

    /**
     * Detailinformationen zum Bruttopreis des Artikels.
     * 
     * @param item
     * @param priceType
     * @return
     */
    private TradePriceType createTradePrice(final DocumentItem item, final PriceType priceType) {
        Price price = new Price(item);
        TradePriceType retval = null;
        String qunit = determineQuantityUnit(item.getQuantityUnit());
        double discount = item.getItemRebate();
        switch (priceType) {
        case GROSS_PRICE:
            retval = factory.createTradePriceType();
            // "ITEM.UNIT.NET.DISCOUNTED" oder "ITEM.TOTAL.NET"?
            // Preis nach Bruttokalkulation *ohne* Umsatzsteuer(!!!) 
            retval.setChargeAmount(createAmount(price.getUnitNet(), 2))
            // Die Anzahl von Artikeleinheiten, für die der Preis gilt (Preisbasismenge ==> 1, 10, 100,...)
            //.setBasisQuantity(createQuantity(item.getProduct().getBlock1(), qunit))
            ;
            if (discount != 0) {
                // Rabatt / Zuschlag auf Positionsebene
                retval.getAppliedTradeAllowanceCharge().add(createTradeAllowance(item, false));
            }
            break;
        case NET_PRICE:

            retval = factory.createTradePriceType();
            // Preis nach Bruttokalkulation ohne Umsatzsteuer 
            retval.setChargeAmount(createAmount(Money.of(item.getPrice(), DataUtils.getInstance().getDefaultCurrencyUnit()), DEFAULT_AMOUNT_SCALE))
            // TODO Preisbasismenge??? (1, 10, 100,...)
            //          .setBasisQuantity(createQuantity(1d, qunit))
            ;
            if (discount != 0) {
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
            retval.setChargeAmount(createAmount(price.getUnitNetDiscounted(), 2))
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
            Optional<CEFACTCode> code = measureUnits.findByAbbreviation(userdefinedQuantityUnit, localeUtil.getDefaultLocale());
            isoUnit = code.isPresent() ? code.get().getCode() : "";
        }
        return isoUnit;
    }

    private DocumentLineDocumentType createDocumentLine(final DocumentItem item) {
        DocumentLineDocumentType retval = factory.createDocumentLineDocumentType();
        retval.setLineID(createIdFromString(item.getPosNr().toString()));
        // TODO Detailinformationen zum Freitext zur Position 
        //Optional.ofNullable(createNote(item.getDescription())).ifPresent(n -> retval.getIncludedNote().add(n));
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

        if (!itemAllowances.getItemAllowances().isEmpty()) {
            MonetaryAmount allowance = itemAllowances.getItemAllowances().values().parallelStream()
                    .collect(() -> Money.of(BigDecimal.ONE, DataUtils.getInstance().getDefaultCurrencyUnit()), (a, t) -> t.add(a), (a, t) -> t.add(a));
            allowanceAmount.add(allowance);
        }
        MonetaryAmount totalAmount = Money.zero(DataUtils.getInstance().getDefaultCurrencyUnit());
        for (MonetaryAmount amt : netPricesPerVat.values()) {
            totalAmount = totalAmount.add(amt);
        }
        MonetaryAmount taxBasisTotalAmount = totalAmount.add(documentSummary.getShippingNet()).subtract(allowanceAmount);
        MonetaryAmount grandTotalAmount = taxBasisTotalAmount.add(documentSummary.getTotalVat());
        TradeSettlementHeaderMonetarySummationType retval = factory.createTradeSettlementHeaderMonetarySummationType();
        retval.setLineTotalAmount(createAmount(totalAmount));
        retval.setChargeTotalAmount(createAmount(documentSummary.getShippingNet()));
        retval.setAllowanceTotalAmount(createAmount(allowanceAmount));
        retval.getTaxBasisTotalAmount().add(createAmount(taxBasisTotalAmount));
        retval.getTaxTotalAmount().add(createAmount(documentSummary.getTotalVat(), 2, true));
        retval.getGrandTotalAmount().add(createAmount(grandTotalAmount));
        retval.setTotalPrepaidAmount(createAmount(Money.of(invoice.getPaidValue(), DataUtils.getInstance().getDefaultCurrencyUnit())));
        retval.setDuePayableAmount(createAmount(grandTotalAmount.subtract(Money.of(invoice.getPaidValue(), DataUtils.getInstance().getDefaultCurrencyUnit()))));
        return retval;
    }

    private TradeSettlementLineMonetarySummationType createTradeSettlementLineMonetarySummation(final DocumentItem item) {
        /*
         * Der Gesamtpositionsbetrag ist der Nettobetrag unter Berücksichtigung von Zu- und Abschlägen ohne 
         * Angabe des Umsatzsteuerbetrages. 
         */
        Price price = new Price(item);
        TradeSettlementLineMonetarySummationType retval = factory.createTradeSettlementLineMonetarySummationType();
        retval.setLineTotalAmount(createAmount(price.getTotalNetRounded()));

        storeNetPrice(price.getVatPercentFormatted(), price.getTotalNetRounded());

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
    private TradePaymentTermsType createTradePaymentTerms(final Document invoice, final DocumentSummary documentSummary) {
        LocalDateTime dueDate = DataUtils.getInstance().addToDate(invoice.getDocumentDate(), invoice.getDueDays());
        TemplateProcessor placeholders = ContextInjectionFactory.make(TemplateProcessor.class, eclipseContext);
        double percent = invoice.getPayment().getDiscountValue();

        Date out = Date.from(dueDate.atZone(ZoneId.systemDefault()).toInstant());
        Optional<String> paymentText = Optional.ofNullable(placeholders.createPaymentText(invoice, Optional.ofNullable(documentSummary), percent));
        TradePaymentTermsType tradePaymentTerms = factory.createTradePaymentTermsType();
        tradePaymentTerms.setDescription(createText(paymentText.orElse("unknown")));
        tradePaymentTerms.setDueDateDateTime(createDateTime(out));

        DocumentReceiver documentReceiver = addressManager.getBillingAdress(invoice);
        Contact contact = getOriginContact(documentReceiver);
        if (contact != null) {
            IDType id = StringUtils.isNotBlank(contact.getMandateReference())
                    ? createIdWithSchemeFromString(contact.getMandateReference(), preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CREDITORID))
                    : null;
            tradePaymentTerms.setDirectDebitMandateID(id);
        }
        return tradePaymentTerms;
    }

    private TradeAllowanceChargeType createTradeAllowance(final DocumentItem item) {
        return createTradeAllowance(item, true);
    }

    /**
     * Detailinformationen zu Zu- und Abschlägen.
     * 
     * @param item
     * @return
     */
    private TradeAllowanceChargeType createTradeAllowance(final DocumentItem item, final boolean withReason) {
        Price price = new Price(item);
        MonetaryAmount amount = price.getTotalAllowance();
        boolean isAllowance = amount.isPositiveOrZero();
        if (!isAllowance) {
            amount = amount.multiply(-1);
        }

        itemAllowances.add(item.getItemVat(), price.getTotalAllowance());

        TradeAllowanceChargeType tradeAllowanceCharge = factory.createTradeAllowanceChargeType();
        tradeAllowanceCharge.setChargeIndicator(createIndicator(isAllowance));
        //            .setCalculationPercent(factory.createPercentType().setValue(BigDecimal.valueOf(item.getItemRebate()))) // [CII-SR-122]
        //            .setBasisAmount(createAmount(price.getTotalNet(), 2))  // [CII-SR-123]

        // Der gesamte zur Berechnung des Nettopreises vom Bruttopreis subtrahierte Rabatt
        // (Gilt nur, wenn der Rabatt je Einheit gegeben wird und nicht im Bruttopreis enthalten ist.)
        tradeAllowanceCharge.setActualAmount(createAmount(amount, 2, false))
        // see UNTDID 5189 and UNTDID 7161
        ;
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
        IndicatorType indicator = factory.createIndicatorType();
        indicator.setIndicator(isAllowance);
        return indicator;
    }

    private TradeAllowanceChargeType createTradeAllowance(final DocumentSummary summary, final Document invoice) {
        MonetaryAmount amount = summary.getDiscountNet();
        // Abschlag ==> false
        // Zuschlag ==> true
        boolean isAllowance = amount.isPositive();
        if (!isAllowance) {
            amount = amount.multiply(-1);
        }
        TradeAllowanceChargeType retval = factory.createTradeAllowanceChargeType();
        retval.setChargeIndicator(createIndicator(isAllowance));
        retval.setActualAmount(createAmount(amount, 2, false));
        retval.setBasisAmount(createAmount(summary.getItemsNet().add(amount), 2));
        retval.setReason(createText(zfMsg.zugferdExportLabelRebate));
        retval.setCategoryTradeTax(createTradeTax(invoice.getItems().get(0).getItemVat()));
        return retval;
    }

    /**
     * Generate allowance for shipping costs (this is for COMFORT profile only!)
     * 
     * @param invoice
     * @return
     */
	private TradeAllowanceChargeType createTradeAllowance(final Document invoice) {
		Double amount = invoice.getShipping() != null ? invoice.getShipping().getShippingValue()
				: invoice.getShippingValue();

		TradeAllowanceChargeType retval = factory.createTradeAllowanceChargeType();
		retval.setChargeIndicator(createIndicator(true));
		retval.setActualAmount(
				createAmount(Money.of(amount, DataUtils.getInstance().getDefaultCurrencyUnit()), 2, false));
		retval.setBasisAmount(createAmount(
				Money.of(invoice.getTotalValue(), DataUtils.getInstance().getDefaultCurrencyUnit()), 2, false));
		retval.setReason(createText("Shipping costs")); // TODO Versandkosten!!!
		if (invoice.getShipping() != null && invoice.getShipping().getShippingVat().getTaxValue() > 0.0) {
			retval.setCategoryTradeTax(createTradeTax(invoice.getShipping().getShippingVat()));
		} else if (invoice.getAdditionalInfo().getShippingVatValue() != null) {
			VAT shippingVat = new VAT();
			shippingVat.setTaxValue(invoice.getAdditionalInfo().getShippingVatValue());
			shippingVat.setName(invoice.getAdditionalInfo().getShippingVatDescription());
			retval.setCategoryTradeTax(createTradeTax(shippingVat));
		}
		return retval;
	}

    private TradeTaxType createTradeTax(final VAT vatValue) {
        return createTradeTax(vatValue, vatValue.getTaxValue() > 0 ? TaxCategoryCodeContentType.S : TaxCategoryCodeContentType.Z);
    }

    private TradeTaxType createTradeTax(final VAT vatValue, final TaxCategoryCodeContentType taxCategoryCode) {
        TradeTaxType retval = factory.createTradeTaxType();
        PercentType percentType = factory.createPercentType();
        percentType.setValue(BigDecimal.valueOf(vatValue.getTaxValue()).multiply(BigDecimal.valueOf(100), new MathContext(2)).stripTrailingZeros());
        retval.setRateApplicablePercent(percentType);
        retval.setCategoryCode(createTaxCategoryCode(taxCategoryCode)); // see UNTDID 5305
        retval.setTypeCode(createTaxTypeCode(TaxTypeCodeContentType.VAT));
        return retval;
    }

    /**
     * Detailangaben zu Steuern
     * 
     * @param vatSummaryItem
     * @return
     */
    private TradeTaxType createTradeTax(final VatSummaryItem vatSummaryItem) {
        // VAT description
        // (unused) String key = vatSummaryItem.getVatName();
        // It's the VAT value
        MonetaryAmount basisAmount = Optional.ofNullable(vatSummaryItem.getNet()).orElse(Money.zero(DataUtils.getInstance().getDefaultCurrencyUnit()));
        TaxCategoryCodeContentType taxType = vatSummaryItem.getVatPercent() == 0 ? TaxCategoryCodeContentType.Z : TaxCategoryCodeContentType.S;
        TradeTaxType retval = factory.createTradeTaxType();
        retval.setCalculatedAmount(createAmount(basisAmount.multiply(vatSummaryItem.getVatPercent())));
        PercentType percentType = factory.createPercentType();
        percentType.setValue(BigDecimal.valueOf(vatSummaryItem.getVatPercent()).multiply(BigDecimal.valueOf(100), new MathContext(2)).stripTrailingZeros());
        retval.setRateApplicablePercent(percentType);
        retval.setBasisAmount(createAmount(basisAmount));
        retval.setCategoryCode(createTaxCategoryCode(taxType));
        //.setExemptionReason(TODO)
        //                .setTaxPointDate(value)
        //                .setDueDateTypeCode(value)
        retval.setTypeCode(createTaxTypeCode(TaxTypeCodeContentType.VAT));
        return retval;
    }

    private TaxTypeCodeType createTaxTypeCode(final TaxTypeCodeContentType string) {
        TaxTypeCodeType retval = factory.createTaxTypeCodeType();
        retval.setValue(string);
        return retval;
    }

    private TaxCategoryCodeType createTaxCategoryCode(final TaxCategoryCodeContentType taxCat) {
        TaxCategoryCodeType retval = factory.createTaxCategoryCodeType();
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
        BigDecimal scaledValue = BigDecimal.valueOf(amount.getNumber().doubleValue()).setScale(scale, RoundingMode.HALF_UP);

        AmountType retval = factory.createAmountType();
        retval.setValue(scaledValue);
        retval.setCurrencyID(withCurrency ? amount.getCurrency().getCurrencyCode() : null);
        return retval;
    }

    private CreditorFinancialInstitutionType createCreditorFinancialInstitution() {
        CreditorFinancialInstitutionType retval = factory.createCreditorFinancialInstitutionType();
        retval.setBICID(createIdFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BIC)));
        //              .setGermanBankleitzahlID(createIdFromString(preferences.getString("YOURCOMPANY_COMPANY_BANKCODE")))
        /*.setName(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BANK)))*/
        return retval;
    }

    private CreditorFinancialAccountType createCreditorAccount() {
        CreditorFinancialAccountType retval = null;
        if (!StringUtils.isEmpty(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN))
                || !StringUtils.isEmpty(preferences.getString("YOURCOMPANY_COMPANY_BANKACCOUNTNR"))) {
            retval = factory.createCreditorFinancialAccountType();
            retval.setIBANID(createIdFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN)));
            /* retval.setProprietaryID(createIdFromString(preferences.getString("YOURCOMPANY_COMPANY_BANKACCOUNTNR")))*/
        }
        return retval;
    }

    private DebtorFinancialAccountType createDebtorAccount(final BankAccount bankAccount) {
        if (bankAccount != null && !StringUtils.isEmpty(bankAccount.getIban())) {
            DebtorFinancialAccountType financialAccountType = factory.createDebtorFinancialAccountType();
            financialAccountType.setIBANID(createIdFromString(bankAccount.getIban()))
            // financialAccountType.setProprietaryID(createIdFromString(invoice.getFormatedStringValueByKeyFromOtherTable("addressid.CONTACTS:account")))
            ;
            return financialAccountType;
        } else {
            return null;
        }
    }

    private PaymentMeansCodeType createPaymentTypeCode(final Document invoice) {
        // TODO implement lookup:  UNTDID 4461 !!!

        /*
        * Payment type code gem. "Payment Means Code" lt. Codeliste ZUGFeRD
        * 10 ... bar
        * 31 ... Payment by debit movement of funds from one account to another.  International Transfers
        *        ==> SEPA-Überweisung!
        * 48 ... Bank card
        * 49 ... Direct debit (Lastschrift)
        *                   
        */
        PaymentMeansCodeType paymentMeansCode = factory.createPaymentMeansCodeType();
        paymentMeansCode.setValue(PaymentMeansCodeContentType.VALUE_31);
        return paymentMeansCode;
    }

    private CurrencyCodeType createCurrencyCode(final CurrencyCodeContentType currency) {
        CurrencyCodeType currencyCodeType = factory.createCurrencyCodeType();
        currencyCodeType.setValue(currency);
        return currencyCodeType;
    }

    private TaxRegistrationType createTaxNumber(final Document invoice, final ContactType contactType) {
        TaxRegistrationType retval = factory.createTaxRegistrationType();
        switch (contactType) {
        case SELLER:
            String companyVatNo = preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR);
            if (!StringUtils.isEmpty(companyVatNo)) {
                retval.setID(createIdWithSchemeFromString(getNullCheckedValue(companyVatNo), "VA"));
            }
            break;
        case BUYER:
            DocumentReceiver billingAddress = addressManager.getBillingAdress(invoice);
            if (!StringUtils.isEmpty(billingAddress.getVatNumber())) {
                retval.setID(createIdWithSchemeFromString(getNullCheckedValue(billingAddress.getVatNumber()), "VA"));
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
        CodeType codeType = factory.createCodeType();
        codeType.setValue(value);
        return codeType;
    }

    private TradeAddressType createAddress(final Document invoice, final ContactType contactType) {
        TradeAddressType retval = null;
        switch (contactType) {
        case SELLER:
            String countryCode = preferences.getString(Constants.PREFERENCES_YOURCOMPANY_COUNTRY);

            retval = factory.createTradeAddressType();
            retval.setPostcodeCode(createCode(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_ZIP)));
            retval.setLineOne(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_STREET)));
            //      retval.setLineTwo(is empty at the moment);
            //      retval.setLineThree(is empty at the moment);
            retval.setCityName(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CITY)));
            retval.setCountryID(createCountry(countryCode)); // Nur die Alpha-2 Darstellung darf verwendet werden
            //              retval.setCountrySubDivisionName(null)

            break;
        case BUYER:
            DocumentReceiver billingAddress = addressManager.getBillingAdress(invoice);
            retval = factory.createTradeAddressType();
            // attention! Mind the manualAddress!
            if (billingAddress.getManualAddress() == null) {
                retval.setPostcodeCode(createCode(billingAddress.getZip()));
                retval.setLineOne(createText(billingAddress.getStreet()));
                //      retval.setLineTwo(is empty at the moment)
                //      retval.setLineThree(is empty at the moment);
                retval.setCityName(createText(billingAddress.getCity()));
                retval.setCountryID(createCountry(billingAddress.getCountryCode())); // Nur die Alpha-2 Darstellung darf verwendet werden
                //      retval.setCountrySubDivisionName(null)

            } else {
                Address addressFromString = contactUtil.createAddressFromString(billingAddress.getManualAddress());
                retval.setPostcodeCode(createCode(addressFromString.getZip()));
                retval.setLineOne(createText(addressFromString.getStreet()));
                //      retval.setLineTwo(is empty at the moment)
                //      retval.setLineThree(is empty at the moment);
                retval.setCityName(createText(addressFromString.getCity()));
                retval.setCountryID(createCountry(addressFromString.getCountryCode())); // Nur die Alpha-2 Darstellung darf verwendet werden
                //      retval.setCountrySubDivisionName(null)

            }

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
        CountryIDType countryTypeId = factory.createCountryIDType();
        countryTypeId.setValue(CountryIDContentType.fromValue(Optional.ofNullable(countryStr).orElse("DE")));
        return countryTypeId;
    }

    private TradeContactType createContact(final Document invoice, final ContactType contactType) {
        TradeContactType contact = factory.createTradeContactType();
        UniversalCommunicationType email = factory.createUniversalCommunicationType();
        switch (contactType) {
        case SELLER:
            contact.setPersonName(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER)));
            //      contact.setDepartmentName(createText(dept));  // unknown (Kontaktstelle des Verkäufers)

            if (StringUtils.isNotBlank(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TEL))) {
                contact.setTelephoneUniversalCommunication((createCommunicationItem(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TEL))));
            }

            email.setURIID(createIdFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_EMAIL)));
            contact.setEmailURIUniversalCommunication(email);
            break;
        case BUYER:

            DocumentReceiver billingAdress = addressManager.getBillingAdress(invoice);
            if (billingAdress != null) {
                email.setURIID(createIdWithSchemeFromString(billingAdress.getEmail(), "EM"));
                contact.setPersonName(createText(invoice.getAddressFirstLine()));
                //            contact.setDepartmentName(createText(dept));  // unknown
                contact.setTelephoneUniversalCommunication(createCommunicationItem(billingAdress.getPhone()));
                contact.setEmailURIUniversalCommunication(email);
            }
            break;
        default:
            break;
        }
        return contact;
    }

    private UniversalCommunicationType createCommunicationItem(final String communicationItem) {
        UniversalCommunicationType universalCommunicationType = factory.createUniversalCommunicationType();
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
    private DateTimeType createDateTime(final Date dateString) {
        DateTimeType dateValue = null;
        if (dateString != null) {
            dateValue = factory.createDateTimeType();
            DateTimeType.DateTimeString dateTypeString = factory.createDateTimeTypeDateTimeString();
            dateTypeString.setValue(sdfDest.format(dateString));
            dateTypeString.setFormat("102");
            dateValue.setDateTimeString(dateTypeString);
        }
        return dateValue;
    }

    /**
     * Creates a {@link FormattedDateTimeType} from a given date string
     * ("YYYY-MM-DD").
     * 
     * @param dateString
     *            the date String
     * @return {@link FormattedDateTimeType}
     */
    private FormattedDateTimeType createFormattedDateTime(final Date dateString) {
        FormattedDateTimeType dateValue = null;
        if (dateString != null) {
            dateValue = factory.createFormattedDateTimeType();
            DateTimeString dateTypeString = factory.createFormattedDateTimeTypeDateTimeString();
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
        IDType idType = factory.createIDType();
        idType.setValue(idString);
        idType.setSchemeID(scheme);

        return idString != null ? idType : null;
    }

}
