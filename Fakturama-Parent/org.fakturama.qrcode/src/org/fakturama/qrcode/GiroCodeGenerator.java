package org.fakturama.qrcode;

import java.io.ByteArrayOutputStream;
import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.preference.IPreferenceStore;

import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.IDateFormatterService;
import com.sebulli.fakturama.misc.INumberFormatterService;
import com.sebulli.fakturama.model.BankAccount;
import com.sebulli.fakturama.model.Invoice;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.core.scheme.Girocode;
import net.glxn.qrgen.core.scheme.Girocode.Encoding;
import net.glxn.qrgen.javase.QRCode;

public class GiroCodeGenerator {

    @Inject
    private INumberFormatterService numberFormatterService;
    
    @Inject
    private IDateFormatterService dateFormatter;
    
    @Inject
    protected IPreferenceStore defaultValuePrefs;

    @Inject
    private ILocaleService localeUtil;

    public byte[] createGiroCode(Invoice document, BankAccount companyBankaccount) {
        if (companyBankaccount == null || companyBankaccount.getBic() == null || companyBankaccount.getIban() == null) {
            throw new InvalidParameterException(StringUtils.join("No bank account given for your company", '\n'));
        }
        Girocode girocode = new Girocode();
        
        NumberFormat numberInstance = NumberFormat.getNumberInstance(Locale.US);
        numberInstance.setMinimumFractionDigits(2);
        numberInstance.setMaximumFractionDigits(2);
        
        String amount = String.format("%s%s", numberFormatterService.getCurrencyUnit(localeUtil.getCurrencyLocale()), 
                numberInstance.format(document.getTotalValue()));
        girocode.setAmount(amount);

        girocode.setEncoding(Encoding.UTF_8);
        girocode.setBic(companyBankaccount.getBic());
        girocode.setIban(companyBankaccount.getIban());
        girocode.setName(defaultValuePrefs.getString(Constants.PREFERENCES_YOURCOMPANY_NAME));
        girocode.setReference(document.getName());
        girocode.setText("Rechnung vom " + dateFormatter.getFormattedLocalizedDate(document.getDocumentDate()));
        String generatedString = girocode.generateString();
        
        // since the version is hardcoded ("001") we have to use an ugly hack
        ByteArrayOutputStream qrCodeFile = QRCode.from(generatedString.replaceFirst("001", "002")).to(ImageType.PNG).stream();
        return qrCodeFile.toByteArray();
    }
}
