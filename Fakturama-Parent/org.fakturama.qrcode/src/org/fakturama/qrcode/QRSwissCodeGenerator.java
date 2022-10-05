package org.fakturama.qrcode;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.preference.IPreferenceStore;

import com.sebulli.fakturama.dao.ContactsDAO;
import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.DocumentReceiver;
import com.sebulli.fakturama.model.IDocumentAddressManager;
import com.sebulli.fakturama.util.ContactUtil;

import net.codecrete.qrbill.generator.Bill;
import net.codecrete.qrbill.generator.BillFormat;
import net.codecrete.qrbill.generator.GraphicsFormat;
import net.codecrete.qrbill.generator.Language;
import net.codecrete.qrbill.generator.OutputSize;
import net.codecrete.qrbill.generator.Payments;
import net.codecrete.qrbill.generator.QRBill;
import net.codecrete.qrbill.generator.ValidationMessage;
import net.codecrete.qrbill.generator.ValidationResult;

public class QRSwissCodeGenerator {

    @Inject
    private ILocaleService localeUtil;

    @Inject
    private IPreferenceStore preferences;
    
    @Inject
    private IDocumentAddressManager addressManager;
    
    @Inject
    private ContactsDAO contactsDAO;
    
    @Inject
    private IEclipseContext context;

    private ContactUtil contactUtil;
    
    @Inject
    @Translation
    private Messages msg;

    public byte[] createSwissCodeQR(Document document) {
        this.contactUtil = ContextInjectionFactory.make(ContactUtil.class, context);

        DocumentReceiver documentReceiver = addressManager.getBillingAdress(document);
        Contact debitor = contactsDAO.findById(documentReceiver.getOriginContactId());
        if (debitor != null) {

            // Setup bill
            Bill bill = new Bill();
            bill.setAccount(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN));
            bill.setAmountFromDouble(document.getTotalValue());
            bill.setUnstructuredMessage(String.format("%s %s", msg.exporterDataInvoiceno, document.getName()));
            bill.setCurrency(DataUtils.getInstance().getDefaultCurrencyUnit().getCurrencyCode());

            // Set creditor
            net.codecrete.qrbill.generator.Address creditor = new net.codecrete.qrbill.generator.Address();
            creditor.setName(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER));
            creditor.setStreet(contactUtil.getStreetName(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_STREET)));
            creditor.setHouseNo(contactUtil.getStreetNo(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_STREET)));
            
            creditor.setPostalCode(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_ZIP));
            creditor.setTown(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CITY));
            
            String countryCode = localeUtil.findCodeByDisplayCountry(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_COUNTRY),
                    localeUtil.getDefaultLocale().getLanguage());
            creditor.setCountryCode(countryCode);
            
            bill.setCreditor(creditor);

            // only add a reference if the customer reference is a valid QR-IBAN
            if (document.getCustomerRef() != null && Payments.isQRIBAN(document.getCustomerRef())) {
                bill.setReference(document.getCustomerRef());
            }

            // Set debtor - structured SwissCode address
            // If AddressLine1 or AddressLine2 is set, the type changes to UNSTRUCTURED and then
            // you don't have to set street, town, postalCode and houseNo.
            net.codecrete.qrbill.generator.Address debtor = new net.codecrete.qrbill.generator.Address();
            debtor.setName(document.getAddressFirstLine());
            debtor.setStreet(contactUtil.getStreetName(documentReceiver.getStreet()));
            debtor.setHouseNo(contactUtil.getStreetNo(documentReceiver.getStreet()));
            debtor.setTown(documentReceiver.getCity());
            debtor.setPostalCode(documentReceiver.getZip());
            debtor.setCountryCode(documentReceiver.getCountryCode());
            bill.setDebtor(debtor);

            // Set output format
            BillFormat format = bill.getFormat();
            format.setGraphicsFormat(GraphicsFormat.PNG);
            format.setOutputSize(OutputSize.QR_BILL_ONLY);
            
            net.codecrete.qrbill.generator.Language lang;
            // TODO change in Java 17 to value switch!
            switch (localeUtil.getCurrencyLocale().getLanguage()) {
            case "de":
                lang = Language.DE;
                break;
            case "en":
                lang = Language.EN;
                break;
            case "fr":
                lang = Language.FR;
                break;
            case "it":
                lang = Language.IT;
                break;
            case "rm":
                lang = Language.RM;
                break;
            default:
                lang = Language.DE;
                break;
            }
            
            format.setLanguage(lang);

            ValidationResult validationResult = QRBill.validate(bill);
            if (validationResult.isValid()) {
                // Generate QR bill
                return QRBill.generate(bill);
            } else {
                List<ValidationMessage> validationMessages = validationResult.getValidationMessages();
                List<String> errorStrings = validationMessages.stream().map(m -> m.getField() + ": " + m.getMessageKey()).collect(Collectors.toList());
                throw new InvalidParameterException(StringUtils.join(errorStrings, '\n'));
            }
        } else 
            return new byte[] {};
    }

}
