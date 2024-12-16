/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2014, 2020 Ralf Heydenreich
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Ralf Heydenreich - initial API and implementation
 */
package org.fakturama.export.zugferd;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.money.MonetaryAmount;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.fakturama.export.einvoice.ConformanceLevel;
import org.fakturama.export.einvoice.ZFConstants;
import org.fakturama.export.facturx.AbstractEInvoice;
import org.fakturama.export.facturx.XRechnungCreator;
import org.fakturama.export.facturx.modelgen.FormattedDateTimeType;
import org.fakturama.export.zugferd.modelgen.AmountType;
import org.fakturama.export.zugferd.modelgen.CodeType;
import org.fakturama.export.zugferd.modelgen.CountryIDType;
import org.fakturama.export.zugferd.modelgen.CreditorFinancialAccountType;
import org.fakturama.export.zugferd.modelgen.CreditorFinancialInstitutionType;
import org.fakturama.export.zugferd.modelgen.CrossIndustryDocument;
import org.fakturama.export.zugferd.modelgen.DateTimeType;
import org.fakturama.export.zugferd.modelgen.DateTimeType.DateTimeString;
import org.fakturama.export.zugferd.modelgen.DebtorFinancialAccountType;
import org.fakturama.export.zugferd.modelgen.DebtorFinancialInstitutionType;
import org.fakturama.export.zugferd.modelgen.DocumentCodeType;
import org.fakturama.export.zugferd.modelgen.DocumentContextParameterType;
import org.fakturama.export.zugferd.modelgen.DocumentLineDocumentType;
import org.fakturama.export.zugferd.modelgen.ExchangedDocumentContextType;
import org.fakturama.export.zugferd.modelgen.ExchangedDocumentType;
import org.fakturama.export.zugferd.modelgen.IDType;
import org.fakturama.export.zugferd.modelgen.IndicatorType;
import org.fakturama.export.zugferd.modelgen.LogisticsServiceChargeType;
import org.fakturama.export.zugferd.modelgen.NoteType;
import org.fakturama.export.zugferd.modelgen.ObjectFactory;
import org.fakturama.export.zugferd.modelgen.PaymentMeansCodeType;
import org.fakturama.export.zugferd.modelgen.PercentType;
import org.fakturama.export.zugferd.modelgen.QuantityType;
import org.fakturama.export.zugferd.modelgen.ReferencedDocumentType;
import org.fakturama.export.zugferd.modelgen.SupplyChainEventType;
import org.fakturama.export.zugferd.modelgen.SupplyChainTradeAgreementType;
import org.fakturama.export.zugferd.modelgen.SupplyChainTradeDeliveryType;
import org.fakturama.export.zugferd.modelgen.SupplyChainTradeLineItemType;
import org.fakturama.export.zugferd.modelgen.SupplyChainTradeSettlementType;
import org.fakturama.export.zugferd.modelgen.SupplyChainTradeTransactionType;
import org.fakturama.export.zugferd.modelgen.TaxCategoryCodeType;
import org.fakturama.export.zugferd.modelgen.TaxRegistrationType;
import org.fakturama.export.zugferd.modelgen.TaxTypeCodeType;
import org.fakturama.export.zugferd.modelgen.TextType;
import org.fakturama.export.zugferd.modelgen.TradeAddressType;
import org.fakturama.export.zugferd.modelgen.TradeAllowanceChargeType;
import org.fakturama.export.zugferd.modelgen.TradePartyType;
import org.fakturama.export.zugferd.modelgen.TradePaymentTermsType;
import org.fakturama.export.zugferd.modelgen.TradePriceType;
import org.fakturama.export.zugferd.modelgen.TradeProductType;
import org.fakturama.export.zugferd.modelgen.TradeSettlementMonetarySummationType;
import org.fakturama.export.zugferd.modelgen.TradeSettlementPaymentMeansType;
import org.fakturama.export.zugferd.modelgen.TradeTaxType;
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
import com.sebulli.fakturama.model.BankAccount;
import com.sebulli.fakturama.model.CEFACTCode;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.DocumentItem;
import com.sebulli.fakturama.model.DocumentReceiver;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.model.VAT;
import com.sebulli.fakturama.office.TemplateProcessor;
import com.sebulli.fakturama.util.DocumentTypeUtil;

/**
 * Implementation for the (deprecated) ZUGFeRD standard. Use
 * {@link XRechnungCreator} instead.
 *
 */
public class ZUGFeRD extends AbstractEInvoice {
    private ObjectFactory factory;

    @Override
    public Serializable getInvoiceXml(final Optional<Invoice> invoiceDoc) {
        if (!invoiceDoc.isPresent()) {
            return null;
        }
        factory = new ObjectFactory();

        Document invoice = invoiceDoc.get();
        // Recalculate the sum of the document before exporting
        DocumentSummaryCalculator documentSummaryCalculator = ContextInjectionFactory.make(DocumentSummaryCalculator.class, eclipseContext);
        DocumentSummary documentSummary = documentSummaryCalculator.calculate(invoice);

        Boolean testMode = BooleanUtils.toBooleanObject(eclipsePrefs.get(ZFConstants.PREFERENCES_ZUGFERD_TEST, "TRUE"));

        CrossIndustryDocument root = factory.createCrossIndustryDocument();
        // at first create a reasonable context
        IndicatorType indicator = factory.createIndicatorType();
        indicator.setIndicator(testMode);
        ExchangedDocumentContextType exchangedDocCtx = factory.createExchangedDocumentContextType();
        exchangedDocCtx.setTestIndicator(indicator);

        DocumentContextParameterType ctxParam = factory.createDocumentContextParameterType();
        ctxParam.setID(createIdFromString(ConformanceLevel.ZUGFERD_V1_COMFORT.getUrn()));
        exchangedDocCtx.getGuidelineSpecifiedDocumentContextParameter().add(ctxParam);
        root.setSpecifiedExchangedDocumentContext(exchangedDocCtx);

        // now the header information follows
        ExchangedDocumentType exchangedDocumentType = factory.createExchangedDocumentType();
        exchangedDocumentType.setID(createIdFromString(invoice.getName()));
        exchangedDocumentType.getName().add(createText(DocumentTypeUtil.findByBillingType(invoice.getBillingType()).name()));
        DocumentCodeType docTypeCode = factory.createDocumentCodeType();
        docTypeCode.setValue("380"); // this is static for BASIC and COMFORT
        exchangedDocumentType.setTypeCode(docTypeCode);
        exchangedDocumentType.setIssueDateTime(createDateTime(invoice.getDocumentDate()));

        createNote(invoice.getMessage(), exchangedDocumentType);
        createNote(invoice.getMessage2(), exchangedDocumentType);
        createNote(invoice.getMessage3(), exchangedDocumentType);

        NoteType note = factory.createNoteType();

        String owner = String.format("%s%n%s%n%s%n%s %s%n%s", preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER),
                preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME), preferences.getString(Constants.PREFERENCES_YOURCOMPANY_STREET),
                preferences.getString(Constants.PREFERENCES_YOURCOMPANY_ZIP), preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CITY),
                preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR));
        if (StringUtils.isBlank(owner)) {
            MessageDialog.openWarning(shell, messages.dialogMessageboxTitleWarning, zfMsg.zugferdExportErrorEmptycompanypref);
            owner = "(unknown)";
        }

        note.getContent().add(createText(owner));
        note.setSubjectCode(createCode("REG"));
        exchangedDocumentType.getIncludedNote().add(note);
        root.setHeaderExchangedDocument(exchangedDocumentType);

        // now follows the huge part for trade transaction
        SupplyChainTradeTransactionType tradeTransaction = factory.createSupplyChainTradeTransactionType();
        SupplyChainTradeAgreementType tradeAgreement = factory.createSupplyChainTradeAgreementType();
        tradeAgreement.getBuyerReference().add(createText(invoice.getCustomerRef())); // "Kundenreferenz"
        tradeTransaction.getApplicableSupplyChainTradeAgreement().add(tradeAgreement);

        // create seller information
        TradePartyType seller = factory.createTradePartyType()
        //              ;retval.setID(createIdFromString("id assigned from customer(!)"))
        ;
        seller.setName(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME)))
        //TODO EXTENDED             ;seller.setDefinedTradeContact(createContact(invoice, ContactType.SELLER))
        ;
        seller.setPostalTradeAddress(createAddress(invoice, ContactType.SELLER))
        //              ;seller.setGlobalID(createIdWithSchemeFromString("EAN|BIC or whatever", "according to ISO 6523"))
        ;
        seller.getSpecifiedTaxRegistration().add(createTaxNumber(invoice, ContactType.SELLER));
        tradeAgreement.setSellerTradeParty(seller);

        // create buyer information
        TradePartyType buyer = factory.createTradePartyType();
        buyer.getID().add(createIdFromString(addressManager.getBillingAdress(invoice).getCustomerNumber()));
        buyer.setName(createText(invoice.getAddressFirstLine()))
        //TODO EXTENDED                 ;buyer.setDefinedTradeContact(createContact(invoice, ContactType.BUYER))
        ;
        buyer.setPostalTradeAddress(createAddress(invoice, ContactType.BUYER))
        //              ;buyer.setGlobalID(createIdWithSchemeFromString("EAN|BIC or whatever", "according to ISO 6523"))
        ;
        buyer.getSpecifiedTaxRegistration().add(createTaxNumber(invoice, ContactType.BUYER));
        tradeAgreement.setBuyerTradeParty(buyer);

        // EXTENDED: Applicable.Trade_DeliveryTerms (not yet available) => Incoterms
        //          tradeAgreement.

        // referenced order
        Transaction transaction = ContextInjectionFactory.make(Transaction.class, eclipseContext).of(invoice);
        if (transaction != null) {
            ReferencedDocumentType orderRef = factory.createReferencedDocumentType();
            orderRef.setIssueDateTime(createSimpleDateTime(invoice.getOrderDate()));
            orderRef.getID().add(createIdFromString(transaction.getReference(DocumentType.ORDER)));
            // only if ID is not empty!
            if (!StringUtils.isEmpty(transaction.getReference(DocumentType.ORDER))) {
                tradeAgreement.getBuyerOrderReferencedDocument().add(orderRef);
            }
        }

        // there is no contract information in Fakturama!
        //          ReferencedDocumentType contractRef = factory.createReferencedDocumentType();
        //          contractRef.setIssueDateTime(value); // unknown
        //          contractRef.getID().add(createIdFromString("contractNumber"))  // unknown
        //          tradeAgreement.setContractReferencedDocument(contractRef);

        // EXTENDED: AdditionalReferencedDocument (IssueDateTime, TypeCode, ID)

        // unknown
        //          ReferencedDocumentType custOrder = factory.createReferencedDocumentType(); + IssueDateTime, ID
        //          tradeAgreement.setCustomerOrderReferencedDocument(custOrder);

        SupplyChainTradeDeliveryType tradeDelivery = factory.createSupplyChainTradeDeliveryType();
        // Related.SupplyChain_Consignment => EXTENDED (not yet available)
        SupplyChainEventType deliveryEvent = factory.createSupplyChainEventType();
        deliveryEvent.getOccurrenceDateTime().add(createDateTime(invoice.getServiceDate()));
        tradeDelivery.getActualDeliverySupplyChainEvent().add(deliveryEvent);

        // Despatch Advice_ Referenced.Referenced_ Document => EXTENDED (not yet available)

        ReferencedDocumentType refDoc = factory.createReferencedDocumentType();
        String deliveryRef = transaction != null ? transaction.getReference(DocumentType.DELIVERY) : "";
        String splitted[] = StringUtils.isEmpty(deliveryRef) ? new String[] {} : deliveryRef.split(",");
        for (String string : splitted) {
            refDoc.getID().add(createIdFromString(string));
            break; // ONLY FIRST ID IS USED (according to the specification only ONE document can be referenced!)
        }
        if (transaction != null) {
            refDoc.setIssueDateTime(dateFormatterService.DateAsISO8601String(transaction.getFirstReferencedDocumentDate(DocumentType.DELIVERY)));
        }
        if (!refDoc.getID().isEmpty()) {
            tradeDelivery.setDeliveryNoteReferencedDocument(refDoc);
        }
        tradeTransaction.setApplicableSupplyChainTradeDelivery(tradeDelivery);
        String currency = getGlobalCurrencyCode();
        //      
        //      // Verwendungszweck, Kassenzeichen 
        SupplyChainTradeSettlementType tradeSettlement = factory.createSupplyChainTradeSettlementType();
        tradeSettlement.getPaymentReference().add(createText(invoice.getName())) /* customerref ? */
        ;
        tradeSettlement.setInvoiceCurrencyCode(createCurrencyCode(currency));
        // Detailinformationen zum abweichenden Rechnungsempfänger
        //      tradeSettlement.setInvoiceeTradeParty(createTradeParty(invoice));  // das wird gar nicht erfaßt!

        DocumentReceiver documentReceiver = addressManager.getBillingAdress(invoice);
        Contact contact = getOriginContact(documentReceiver);
        if (contact != null) {
            DebtorFinancialAccountType debtorAccount = createDebtorAccount(contact.getBankAccount());
            IDType retval = factory.createIDType();
            retval.setValue(contact.getMandateReference());
            retval.setSchemeAgencyID(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CREDITORID));
            IDType id = StringUtils.isNotBlank(contact.getMandateReference()) ? retval : null;

            TradeSettlementPaymentMeansType paymentType = factory.createTradeSettlementPaymentMeansType();
            paymentType.setTypeCode(createPaymentTypeCode(invoice));
            paymentType.getInformation().add(createText(invoice.getPayment().getName()));
            paymentType.getID().add(id);
            CreditorFinancialAccountType creditor = createCreditorAccount();
            if (creditor != null) {
                paymentType.setPayeePartyCreditorFinancialAccount(creditor);
                paymentType.setPayeeSpecifiedCreditorFinancialInstitution(createCreditorFinancialInstitution());

            }
            if (debtorAccount != null) {
                paymentType.setPayerPartyDebtorFinancialAccount(debtorAccount);
                paymentType.setPayerSpecifiedDebtorFinancialInstitution(createDebtorFinancialInstitution(invoice));
            }
            tradeSettlement.getSpecifiedTradeSettlementPaymentMeans().add(paymentType);
        }

        // Get the items of the UniDataSet document
        List<DocumentItem> itemDataSets = invoice.getItems();
        for (int row = 0; row < itemDataSets.size(); row++) {
            // Get the item
            DocumentItem item = itemDataSets.get(row);
            tradeTransaction.getIncludedSupplyChainTradeLineItem().add(createLineItem(item, row + 1));
        }

        // Detailinformationen zur Rechnungsperiode 
        // TODO tradeSettlement.setBillingSpecifiedPeriod(createPeriod(invoice));
        tradeSettlement.getSpecifiedTradeAllowanceCharge().add(createTradeAllowance(documentSummary));
        tradeSettlement.getSpecifiedLogisticsServiceCharge().add(createLogisticsServiceCharge(invoice, documentSummary));
        tradeSettlement.getSpecifiedTradePaymentTerms().add(createTradePaymentTerms(invoice, documentSummary));
        tradeSettlement.setSpecifiedTradeSettlementMonetarySummation(createTradeSettlementMonetarySummation(invoice, documentSummary));
        // TODO EXTENDED: tradeSettlement.setReceivableSpecifiedTradeAccountingAccount(null);

        tradeTransaction.setApplicableSupplyChainTradeSettlement(tradeSettlement);

        // Get the VAT summary of the UniDataSet document
        VatSummarySetManager vatSummarySetManager = ContextInjectionFactory.make(VatSummarySetManager.class, eclipseContext);
        vatSummarySetManager.add(invoice, Double.valueOf(1.0));
        for (VatSummaryItem vatSummaryItem : vatSummarySetManager.getVatSummaryItems()) {
            // für jeden Steuerbetrag muß es einen eigenen Eintrag geben
            tradeSettlement.getApplicableTradeTax().add(createTradeTax(vatSummaryItem));
        }
        root.setSpecifiedSupplyChainTradeTransaction(tradeTransaction);
        return root;
    }

    /**
     * @param invoice
     * @param exchangedDocumentType
     */
    private void createNote(final String message, final ExchangedDocumentType exchangedDocumentType) {
        if (!StringUtils.isEmpty(message)) {
            NoteType note = factory.createNoteType(); // free text on header level
            note.getContent().add(createText(message));
            note.setSubjectCode(createCode("AAK"));
            exchangedDocumentType.getIncludedNote().add(note);
        }
    }

    /**
     * Creates a DateTime value in the form dd.MM.yyyy'T00:00:00'
     * 
     * @param dateValue
     * @return
     */
    private String createSimpleDateTime(final Date dateValue) {
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(dateValue);
        return formatted;
    }

    /**
     * @return
     */
    private String getGlobalCurrencyCode() {
        String currency = DataUtils.getInstance().getDefaultCurrencyUnit().getCurrencyCode();
        //
        //      // TODO later on we will use JSR 354...
        //      switch (currency) {
        //      case "€":
        //          currency = "EUR";
        //          break;
        //      case "$":
        //          currency = "USD";
        //          break;
        //      default:
        //          break;
        //      }
        return currency;
    }

    private SupplyChainTradeLineItemType createLineItem(final DocumentItem item, final int row) {
        SupplyChainTradeLineItemType retval = factory.createSupplyChainTradeLineItemType();
        retval.setAssociatedDocumentLineDocument(createDocumentLine(item, row));
        retval.setSpecifiedSupplyChainTradeAgreement(createSpecifiedSupplyChainTradeAgreement(item, row));
        retval.setSpecifiedSupplyChainTradeDelivery(createSupplyChainTradeDelivery(item));
        retval.setSpecifiedSupplyChainTradeSettlement(createSupplyChainTradeSettlement(item));
        retval.setSpecifiedTradeProduct(createTradeProduct(item));
        return retval;
    }

    /**
     * Gruppierung von Angaben zum Produkt bzw. zur erbrachten Leistung
     * 
     * @param item
     * @return
     */
    private TradeProductType createTradeProduct(final DocumentItem item) {
        TradeProductType retval = factory.createTradeProductType()
        //          ;retval.setGlobalID(createIdFromString("EAN"))
        ;
        retval.setSellerAssignedID(createIdFromString(item.getItemNumber()));
        retval.setBuyerAssignedID(createIdFromString("buyerassigned ID"));
        retval.getName().add(createText(item.getName()));
        retval.getDescription().add(createText(item.getDescription()))
        // TODO EXTENDED    ;retval.setOriginTradeCountry(createTradeCountry(item))
        ;
        return retval;
    }

    /**
     * Gruppierung von Angaben zur Abrechnung auf Positionsebene
     * 
     * @param item
     * @return
     */
    private SupplyChainTradeSettlementType createSupplyChainTradeSettlement(final DocumentItem item) {
        SupplyChainTradeSettlementType retval = factory.createSupplyChainTradeSettlementType();
        retval.getApplicableTradeTax().add(createTradeTax(item.getItemVat()));
        retval.setSpecifiedTradeSettlementMonetarySummation(createTradeSettlementMonetarySummation(item));
        return retval;
    }

    private SupplyChainTradeDeliveryType createSupplyChainTradeDelivery(final DocumentItem item) {
        String qunit = determineQuantityUnit(item.getQuantityUnit());
        SupplyChainTradeDeliveryType retval = factory.createSupplyChainTradeDeliveryType();
        retval.setBilledQuantity(createQuantity(item.getQuantity(), qunit))
        // TODO EXTENDED:   ;retval.setActualDeliverySupplyChainEvent(createSupplyChainEvent(item))
        // TODO EXTENDED:   Despatch Advice_ Referenced.Referenced_ Document   
        // TODO EXTENDED:   Receiving Advice_ Referenced. Referenced_ Document          
        // TODO EXTENDED:   Delivery Note_ Referenced. Referenced_ Document         
        ;
        return retval;
    }

    //  /**
    //   * Detailinformationen zur tatsächlichen Lieferung 
    //   * 
    //   * @param item
    //   * @return
    //   */
    //  private SupplyChainEventType createSupplyChainEvent(DocumentItem item) {
    //      return factory.createSupplyChainEventType()
    //              ;retval.setOccurrenceDateTime(createDateTime(dateString))
    //      ;
    //    }

    private QuantityType createQuantity(final Double value, final String unit) {
        QuantityType retval = factory.createQuantityType();
        retval.setValue(String.format(Locale.ENGLISH, "%.4f", value))
        // use a default value since this field shouldn't be empty
        ;
        retval.setUnitCode(StringUtils.isBlank(unit) ? "C62" : unit);
        return retval;
    }

    /**
     * Gruppierung der Vertragsangaben auf Positionsebene
     * 
     * @param item
     * @param row
     * @return
     */
    private SupplyChainTradeAgreementType createSpecifiedSupplyChainTradeAgreement(final DocumentItem item, final int row) {
        SupplyChainTradeAgreementType retval = factory.createSupplyChainTradeAgreementType()
        //TODO EXTENDED
        //          ;retval.setBuyerOrderReferencedDocument(createBuyerOrderReferencedDocument(item))
        //          ;retval.setContractReferencedDocument(createContractReferencedDocument(item))
        //       - Additional_ Referenced.Referenced_ Document  
        ;
        retval.getGrossPriceProductTradePrice().add(createTradePrice(item, PriceType.GROSS_PRICE));
        retval.getNetPriceProductTradePrice().add(createTradePrice(item, PriceType.NET_PRICE_DISCOUNTED))
        // Detailangaben zur zugehörigen Endkundenbestellung
        //TODO EXTENDED         ;retval.setCustomerOrderReferencedDocument(value)
        ;
        return retval;
    }

    //  /**
    //   * Detailangaben zum zugehörigen Vertrag 
    //   * @param item
    //   * @return
    //   */
    //  private ReferencedDocumentType createContractReferencedDocument(DocumentItem item) {
    //      // TODO Auto-generated method stub
    //      return null;
    //    }
    //
    //  /**
    //   * Detailangaben zur zugehörigen Bestellung 
    //   * @param item
    //   * @return
    //   */
    //  private ReferencedDocumentType createBuyerOrderReferencedDocument(DocumentItem item) {
    //      // TODO Auto-generated method stub
    //      return null;
    //    }

    /**
     * Detailinformationen zum Preis gemäß Bruttokalkulation exklusive
     * Umsatzsteuer.
     * 
     * @param item
     * @param priceType
     * @return
     */
    private TradePriceType createTradePrice(final DocumentItem item, final PriceType priceType) {
        Price price = new Price(item);
        TradePriceType retval = null;
        int scale = preferences.getInt(Constants.PREFERENCES_GENERAL_CURRENCY_DECIMALPLACES);
        String qunit = determineQuantityUnit(item.getQuantityUnit());
        double discount = item.getItemRebate();
        switch (priceType) {
        case GROSS_PRICE:
            retval = factory.createTradePriceType()
            // "ITEM.UNIT.NET.DISCOUNTED" oder "ITEM.TOTAL.NET"?
            // Preis nach Bruttokalkulation *ohne* Umsatzsteuer(!!!) 
            ;
            retval.getChargeAmount().add(createAmount(price.getUnitNet(), scale))
            // TODO Preisbasismenge??? (1, 10, 100,...)
            // EXTENDED             ;retval.setBasisQuantity(createQuantity(1d, qunit))
            ;
            if (discount != 0) {
                // Rabatt / Zuschlag auf Positionsebene
                retval.getAppliedTradeAllowanceCharge().add(createTradeAllowance(item));
            }
            break;
        case NET_PRICE:

            retval = factory.createTradePriceType()
            // Preis nach Bruttokalkulation ohne Umsatzsteuer 
            ;
            retval.getChargeAmount().add(createAmount(Money.of(item.getPrice(), DataUtils.getInstance().getDefaultCurrencyUnit()), DEFAULT_AMOUNT_SCALE))
            // TODO Preisbasismenge??? (1, 10, 100,...)
            //          ;retval.setBasisQuantity(createQuantity(1d, qunit))
            ;
            //          if(discount != 0) {
            //              // Rabatt / Zuschlag auf Positionsebene
            //              retval.getAppliedTradeAllowanceCharge().add(createTradeAllowance(item));
            //          }
            break;
        case NET_PRICE_DISCOUNTED:
            // Detailinformationen zum Preis gemäß Nettokalkulation exklusive Umsatzsteuer
            retval = factory.createTradePriceType()
            // "ITEM.UNIT.NET.DISCOUNTED"
            // Preis nach Bruttokalkulation +- Zu-/Abschläge = Preis 
            // nach Nettokalkulation;
            ;
            retval.getChargeAmount().add(createAmount(price.getUnitNetDiscounted(), scale))
            // TODO Preisbasismenge??? (1, 10, 100,...)
            ;
            retval.setBasisQuantity(createQuantity(1d, qunit));
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

    private DocumentLineDocumentType createDocumentLine(final DocumentItem item, final int row) {
        DocumentLineDocumentType retval = factory.createDocumentLineDocumentType()
        // TODO Detailinformationen zum Freitext zur Position 
        //      ;retval.setIncludedNote(null)
        ;
        retval.setLineID(createIdFromString(Integer.toString(row)));
        return retval;
    }

    /**
     * Detailinformationen zu Belegsummen.
     * 
     * @param invoice
     * @param documentSummary
     * @return
     */
    private TradeSettlementMonetarySummationType createTradeSettlementMonetarySummation(final Document invoice, final DocumentSummary documentSummary) {
        MonetaryAmount allowanceAmount = documentSummary.getDiscountGross();
        boolean isAllowance = allowanceAmount.isPositiveOrZero();
        if (!isAllowance) {
            allowanceAmount = allowanceAmount.multiply(-1.0);
        }
        MonetaryAmount totalAmount = Money.zero(DataUtils.getInstance().getDefaultCurrencyUnit());
        for (MonetaryAmount amt : netPricesPerVat.values()) {
            totalAmount = totalAmount.add(amt);
        }
        MonetaryAmount taxBasisTotalAmount = totalAmount.add(documentSummary.getShippingNet()).subtract(allowanceAmount);
        TradeSettlementMonetarySummationType retval = factory.createTradeSettlementMonetarySummationType();
        retval.getLineTotalAmount().add(createAmount(totalAmount));
        retval.getChargeTotalAmount().add(createAmount(documentSummary.getShippingNet()));
        retval.getAllowanceTotalAmount().add(createAmount(allowanceAmount));
        retval.getTaxBasisTotalAmount().add(createAmount(taxBasisTotalAmount));
        retval.getTaxTotalAmount().add(createAmount(documentSummary.getTotalVat()));
        retval.getGrandTotalAmount().add(createAmount(taxBasisTotalAmount.add(documentSummary.getTotalVat())))
        //  ;retval.setTotalPrepaidAmount(createAmount(Money.of(invoice.getPaidValue(), DataUtils.getInstance().getDefaultCurrencyUnit())))
        //  ;retval.setDuePayableAmount(createAmount(documentSummary.getTotalGross().subtract(Money.of(invoice.getPaidValue(), DataUtils.getInstance().getDefaultCurrencyUnit()))
        //          )
        ;
        return retval;
    }

    private TradeSettlementMonetarySummationType createTradeSettlementMonetarySummation(final DocumentItem item) {
        /*
         * Der Gesamtpositionsbetrag ist der Nettobetrag unter Berücksichtigung von Zu- und Abschlägen ohne 
         * Angabe des Umsatzsteuerbetrages. 
         */
        Price price = new Price(item);
        TradeSettlementMonetarySummationType retval = factory.createTradeSettlementMonetarySummationType();
        retval.getLineTotalAmount().add(createAmount(price.getTotalNetRounded()));

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

    private TradePaymentTermsType createTradePaymentTerms(final Document invoice, final DocumentSummary documentSummary) {
        LocalDateTime dueDate = DataUtils.getInstance().addToDate(invoice.getDocumentDate(), invoice.getDueDays());
        TemplateProcessor placeholders = ContextInjectionFactory.make(TemplateProcessor.class, eclipseContext);
        double percent = invoice.getPayment().getDiscountValue();

        Date out = Date.from(dueDate.atZone(ZoneId.systemDefault()).toInstant());

        Optional<String> paymentText = Optional.ofNullable(placeholders.createPaymentText(invoice, Optional.ofNullable(documentSummary), percent));
        TradePaymentTermsType tradePaymentTerms = factory.createTradePaymentTermsType();
        tradePaymentTerms.getDescription().add(createText(paymentText.orElse("unknown")));
        tradePaymentTerms.setDueDateDateTime(createDateTime(out));
        // TODO EXTENDED: Applicable. Trade_ Payment Penalty Terms
        // TODO EXTENDED: Applicable. Trade_ Payment Discount Terms
        return tradePaymentTerms;
    }

    private LogisticsServiceChargeType createLogisticsServiceCharge(final Document invoice, final DocumentSummary documentSummary) {
        LogisticsServiceChargeType logisticsServiceCharge;
        if (invoice.getShipping() != null) {
            logisticsServiceCharge = factory.createLogisticsServiceChargeType();
            logisticsServiceCharge.getDescription().add(createText(invoice.getShipping().getDescription()));
            logisticsServiceCharge.getAppliedAmount().add(createAmount(documentSummary.getShippingNet(), 2, true));
            VAT shippingVat = invoice.getShipping().getShippingVat();
            if (shippingVat != null) {
                logisticsServiceCharge.getAppliedTradeTax().add(createTradeTax(shippingVat));
            }
        } else if (invoice.getShippingValue() != null) {
            logisticsServiceCharge = factory.createLogisticsServiceChargeType();
            logisticsServiceCharge.getAppliedAmount().add(createAmount(documentSummary.getShippingNet(), 2, true));
        } else {
            logisticsServiceCharge = factory.createLogisticsServiceChargeType();
        }

        return logisticsServiceCharge;
    }

    /**
     * Detailinformationen zu Zu- und Abschlägen.
     * 
     * @param item
     * @return
     */
    private TradeAllowanceChargeType createTradeAllowance(final DocumentItem item) {
        Double discountPercent = item.getItemRebate();
        Double amount = item.getPrice() * discountPercent;
        Price price = new Price(item);
        int scale = preferences.getInt(Constants.PREFERENCES_GENERAL_CURRENCY_DECIMALPLACES);
        double factor = Math.pow(10, scale);
        double s = Math.round(price.getUnitNet().multiply(factor).getNumber().doubleValue()) / factor;
        double t = Math.round(price.getUnitNetDiscounted().multiply(factor).getNumber().doubleValue()) / factor;
        double u = Math.round((s - t) * factor) / factor;
        boolean isAllowance = amount > 0;
        if (!isAllowance) {
            amount *= -1;
        }
        IndicatorType indicatorType = factory.createIndicatorType();
        indicatorType.setIndicator(isAllowance);
        TradeAllowanceChargeType retval = factory.createTradeAllowanceChargeType();
        retval.setChargeIndicator(indicatorType);
        retval.getActualAmount().add(createAmount(Money.of(u/*amount*/, DataUtils.getInstance().getDefaultCurrencyUnit()), scale, true))
        //          ;retval.setBasisAmount(createAmount(item.getDoubleValueByKey("price")))
        ;
        retval.setReason(createText(zfMsg.zugferdExportLabelRebate));
        //          ;retval.setCategoryTradeTax(createTradeTax(item.getDoubleValueByKey("vatvalue")));
        return retval;
    }

    private TradeAllowanceChargeType createTradeAllowance(final DocumentSummary summary) {
        MonetaryAmount amount = summary.getDiscountGross();
        boolean isAllowance = amount.isPositive();
        if (!isAllowance) {
            amount = amount.multiply(-1);
        }
        IndicatorType indicatorType = factory.createIndicatorType();
        indicatorType.setIndicator(isAllowance);
        TradeAllowanceChargeType retval = factory.createTradeAllowanceChargeType();
        retval.setChargeIndicator(indicatorType);
        retval.getActualAmount().add(createAmount(amount, 2 /*DEFAULT_AMOUNT_SCALE*/))
        //          ;retval.setBasisAmount(createAmount(summary.getItemsNet().asRoundedDouble(), true))
        ;
        retval.setReason(createText(zfMsg.zugferdExportLabelRebate))
        // TODO which VAT?          ;retval.setCategoryTradeTax(createTradeTax(item.getDoubleValueByKey("vatvalue")));
        ;
        return retval;
    }

    private TradeTaxType createTradeTax(final VAT vatValue) {
        PercentType percentType = factory.createPercentType();
        percentType.setValue(String.format(Locale.ENGLISH, "%.2f", vatValue.getTaxValue() * 100));
        TradeTaxType retval = factory.createTradeTaxType();
        retval.setApplicablePercent(percentType);
        retval.setCategoryCode(createTaxCategoryCode("S")); // Standard rate, FIXME for other uses!

        retval.setTypeCode(createTaxTypeCode("VAT"));
        return retval;
    }

    private TradeTaxType createTradeTax(final VatSummaryItem vatSummaryItem) {
        // VAT description
        // (unused) String key = vatSummaryItem.getVatName();
        // It's the VAT value
        MonetaryAmount basisAmount = Optional.ofNullable(netPricesPerVat.get(numberFormatterService.DoubleToFormatedPercent(vatSummaryItem.getVatPercent())))
                .orElse(Money.zero(DataUtils.getInstance().getDefaultCurrencyUnit()));

        PercentType percentType = factory.createPercentType();
        percentType.setValue(String.format(Locale.ENGLISH, "%.2f", DataUtils.getInstance().round(vatSummaryItem.getVatPercent() * 100)));
        TradeTaxType retval = factory.createTradeTaxType();
        retval.getCalculatedAmount().add(createAmount(basisAmount.multiply(vatSummaryItem.getVatPercent())));
        retval.setApplicablePercent(percentType);
        retval.getBasisAmount().add(createAmount(basisAmount));
        retval.setCategoryCode(createTaxCategoryCode("S")) // Standard rate, FIXME for other uses!
        //;retval.setExemptionReason(TODO)
        ;
        retval.setTypeCode(createTaxTypeCode("VAT"));
        return retval;
    }

    /**
     * Detailangaben zu Steuern
     * 
     * @param vatPercent
     * @return
     */
    private TradeTaxType createTradeTax(final Double vatPercent) {
        TradeTaxType retval = factory.createTradeTaxType();
        PercentType percentType = factory.createPercentType();

        retval.setApplicablePercent(percentType);
        percentType.setValue(String.format(Locale.ENGLISH, "%.2f", (vatPercent * 100.0)));
        retval.setCategoryCode(createTaxCategoryCode("S")) // Standard rate, FIXME for other uses!
        //;retval.setExemptionReason(TODO)
        ;
        retval.setTypeCode(createTaxTypeCode("VAT"));
        return retval;
    }

    private TaxTypeCodeType createTaxTypeCode(final String string) {
        TaxTypeCodeType retval = factory.createTaxTypeCodeType();
        retval.setValue(string);
        return retval;
    }

    private TaxCategoryCodeType createTaxCategoryCode(final String taxCat) {
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
        return createAmount(amount, 2, true);
    }

    private AmountType createAmount(final MonetaryAmount amount, final int scale) {
        return createAmount(amount, scale, true);
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
        AmountType retval = factory.createAmountType();
        retval.setValue(String.format(Locale.ENGLISH, "%." + scale + "f", amount.getNumber().doubleValue()));
        retval.setCurrencyID(withCurrency ? amount.getCurrency().getCurrencyCode() : null);
        return retval;
    }

    private CreditorFinancialInstitutionType createCreditorFinancialInstitution() {
        CreditorFinancialInstitutionType retval = factory.createCreditorFinancialInstitutionType();
        retval.setBICID(createIdFromString(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BIC)))
        //              ;retval.setGermanBankleitzahlID(createIdFromString(preferences.getString("YOURCOMPANY_COMPANY_BANKCODE")))
        ;
        retval.setName(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BANK)));
        return retval;
    }

    private DebtorFinancialInstitutionType createDebtorFinancialInstitution(final Document invoice) {
        DocumentReceiver documentReceiver = addressManager.getBillingAdress(invoice);
        Contact billingAddress = getOriginContact(documentReceiver);
        DebtorFinancialInstitutionType retval = null;
        if (!StringUtils.isEmpty(billingAddress.getBankAccount().getBic())) {
            retval = factory.createDebtorFinancialInstitutionType();
            retval.setBICID(createIdFromString(billingAddress.getBankAccount().getBic()))
            //                  ;retval.setGermanBankleitzahlID(createIdFromString(invoice.getFormatedStringValueByKeyFromOtherTable("addressid.CONTACTS:bank_code")))
            ;
            retval.setName(createText(billingAddress.getBankAccount().getBankName()));
        }
        return retval;
    }

    private CreditorFinancialAccountType createCreditorAccount() {
        CreditorFinancialAccountType retval = null;
        if (!StringUtils.isEmpty(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN))
                || !StringUtils.isEmpty(preferences.getString("YOURCOMPANY_COMPANY_BANKACCOUNTNR"))) {
            retval = factory.createCreditorFinancialAccountType();
            retval.setIBANID(createIdFromString(preferences.getString(
                    Constants.PREFERENCES_YOURCOMPANY_IBAN)))/*
                                                             ;retval.setProprietaryID(createIdFromString(preferences.getString("YOURCOMPANY_COMPANY_BANKACCOUNTNR")))*/;
        }
        return retval;
    }

    private DebtorFinancialAccountType createDebtorAccount(final BankAccount bankAccount) {
        DebtorFinancialAccountType retval = null;
        if (bankAccount != null && !StringUtils.isEmpty(bankAccount.getIban())) {
            retval = factory.createDebtorFinancialAccountType();
            retval.setIBANID(createIdFromString(bankAccount.getIban()))
            //                  ;retval.setProprietaryID(createIdFromString(invoice.getFormatedStringValueByKeyFromOtherTable("addressid.CONTACTS:account")))
            ;
        }
        return retval;

    }

    private PaymentMeansCodeType createPaymentTypeCode(final Document invoice) {
        // TODO implement lookup:  UNCL 4461 !!!

        /*
        * Payment type code gem. "Payment Means Code" lt. Codeliste ZUGFeRD
        * 10 ... bar
        * 31 ... Payment by debit movement of funds from one account to another.  International Transfers
        *        ==> SEPA-Überweisung!
        * 48 ... Bank card
        * 49 ... Direct debit (Lastschrift)
        *                   
        */
        PaymentMeansCodeType retval = factory.createPaymentMeansCodeType();
        retval.setValue("31");
        return retval;
    }

    //  private TradePartyType createTradeParty(Document invoice) {
    //      String customerNumber = invoice.getFormatedStringValueByKeyFromOtherTable("addressid.CONTACTS:nr");
    //      TradePartyType retval = factory.createTradePartyType();retval.setID(createIdWithSchemeFromString(customerNumber, "SELLER_ASSIGNED"))
    //      //  ;retval.setGlobalID(...)
    //          ;retval.setName(createText(invoice.getStringValueByKey("addressfirstline")))
    //          // ;retval.setDefinedTradeContact(createContact(invoice, contactType)) TODO EXTENDED
    //          ;retval.setPostalTradeAddress(createAddress(invoice, ContactType.BUYER))
    //          ;retval.setSpecifiedTaxRegistration(createTaxNumber(invoice, ContactType.BUYER))
    //          ;
    //      return retval;
    //  }

    private CodeType createCurrencyCode(final String currency) {
        CodeType retval = factory.createCodeType();
        retval.setValue(currency);
        return retval;
    }

    private TaxRegistrationType createTaxNumber(final Document invoice, final ContactType contactType) {
        TaxRegistrationType retval = null;
        switch (contactType) {
        case SELLER:
            String companyVatNo = preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR);
            if (!StringUtils.isEmpty(companyVatNo)) {
                retval = factory.createTaxRegistrationType();
                retval.setID(createIdWithSchemeFromString(getNullCheckedValue(companyVatNo), "VA"));
            }
            break;
        case BUYER:
            DocumentReceiver billingAddress = addressManager.getBillingAdress(invoice);
            if (!StringUtils.isEmpty(billingAddress.getVatNumber())) {
                retval = factory.createTaxRegistrationType();
                retval.setID(createIdWithSchemeFromString(getNullCheckedValue(billingAddress.getVatNumber()), "VA"));
            }
            break;
        default:
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
        CodeType retval = factory.createCodeType();
        retval.setValue(value);
        return retval;
    }

    private TradeAddressType createAddress(final Document invoice, final ContactType contactType) {
        TradeAddressType retval = null;
        switch (contactType) {
        case SELLER:
            String countryCode = preferences.getString(Constants.PREFERENCES_YOURCOMPANY_COUNTRY);

            retval = factory.createTradeAddressType();
            retval.getPostcodeCode().add(createCode(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_ZIP)));
            retval.setLineOne(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_STREET)))
            //      ;retval.setLineTwo(is empty at the moment);
            ;
            retval.setCityName(createText(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CITY)));
            retval.setCountryID(createCountry(countryCode));
            //;retval.setCountryID(createCountry(preferences.getString("YOURCOMPANY_COMPANY_COUNTRY")));
            break;
        case BUYER:
            DocumentReceiver billingAddress = addressManager.getBillingAdress(invoice);
            // Korrektur
            countryCode = billingAddress.getCountryCode();
            retval = factory.createTradeAddressType();
            retval.getPostcodeCode().add(createCode(billingAddress.getZip()));
            retval.setLineOne(createText(billingAddress.getStreet()))
            //      ;retval.setLineTwo(is empty at the moment)
            ;
            retval.setCityName(createText(billingAddress.getCity()));
            retval.setCountryID(createCountry(countryCode));
            //          ;retval.setCountryID(createCountry(invoice.getFormatedStringValueByKeyFromOtherTable("addressid.CONTACTS:country")));
            break;
        default:
            break;
        }
        return retval;
    }

    private CountryIDType createCountry(final String value) {
        String countryStr = null;
        // FIXME CHANGE THIS!!!
        if (StringUtils.length(value) > 2) {
            countryStr = localeUtil.findCodeByDisplayCountry(value, "DE");
        }
        // null values aren't allowed!
        CountryIDType retval = factory.createCountryIDType();
        retval.setValue(Optional.ofNullable(countryStr).orElse("DE"));
        return retval;
    }

    //  private TradeContactType createContact(Document invoice, ContactType contactType) {
    //      TradeContactType contact = factory.createTradeContactType();
    //      switch (contactType) {
    //      case SELLER:
    //          // this is only EXTENDED profile!
    //          contact.setPersonName(createText(preferences.getString("YOURCOMPANY_COMPANY_OWNER"))); // YOURCOMPANY.OWNER
    //          //      contact.setDepartmentName(createText(dept));  // unknown
    //          contact.getTelephoneUniversalCommunication().add((createCommunicationItem(preferences.getString("YOURCOMPANY_COMPANY_TEL"))));
    //          contact.getFaxUniversalCommunication().add(createCommunicationItem(preferences.getString("YOURCOMPANY_COMPANY_FAX")));
    //          contact.setEmailURIUniversalCommunication(createCommunicationItem(preferences.getString("YOURCOMPANY_COMPANY_EMAIL")));
    //          break;
    //      case BUYER:
    //          // "consultant"???
    //          contact.setPersonName(createText(invoice.getFormatedStringValueByKey("addressfirstline")));
    ////            contact.setDepartmentName(createText(dept));  // unknown
    //          // this is only EXTENDED profile!
    //          contact.getTelephoneUniversalCommunication().add(createCommunicationItem(invoice.getFormatedStringValueByKeyFromOtherTable("addressid.CONTACTS:phone")));
    //          contact.getFaxUniversalCommunication().add(createCommunicationItem(invoice.getFormatedStringValueByKeyFromOtherTable("addressid.CONTACTS:fax")));
    //          contact.setEmailURIUniversalCommunication(createCommunicationItem(invoice.getFormatedStringValueByKeyFromOtherTable("addressid.CONTACTS:email")));
    //          break;
    //      default:
    //          break;
    //      }
    //      return contact;
    //  }

    //  private UniversalCommunicationType createCommunicationItem(String communicationItem) {
    //      return factory.createUniversalCommunicationType();retval.setCompleteNumber(createText(communicationItem));
    //  }

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
            DateTimeString dateTypeString = factory.createDateTimeTypeDateTimeString();
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
        IDType retval = factory.createIDType();
        retval.setValue(idString);
        retval.setSchemeID(scheme);
        return idString != null ? retval : null;
    }

}
