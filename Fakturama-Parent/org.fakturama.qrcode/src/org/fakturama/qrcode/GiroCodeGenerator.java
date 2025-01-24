package org.fakturama.qrcode;

import java.io.ByteArrayOutputStream;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.preference.IPreferenceStore;

import com.google.zxing.EncodeHintType;
import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.i18n.Messages;
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
    @Translation
    protected Messages msg;

    @Inject
    private ILocaleService localeUtil;

    public byte[] createGiroCode(Invoice document, BankAccount companyBankaccount, Map<String, Object> params) {
        if (companyBankaccount == null || companyBankaccount.getBic() == null || companyBankaccount.getIban() == null) {
            throw new InvalidParameterException(StringUtils.join("No bank account given for your company", '\n'));
        }
        Girocode girocode = new Girocode();
        
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMinimumFractionDigits(2);
        numberInstance.setMaximumFractionDigits(2);
        
        String amount = String.format("%s%s", numberFormatterService.getCurrencyUnit(localeUtil.getCurrencyLocale()), 
                numberInstance.format(document.getTotalValue()));
        girocode.setAmount(amount);

        girocode.setEncoding(Encoding.UTF_8);
        girocode.setBic(companyBankaccount.getBic());
        girocode.setIban(companyBankaccount.getIban());
        girocode.setName(defaultValuePrefs.getString(Constants.PREFERENCES_YOURCOMPANY_NAME));
        String giroText = MessageFormat.format(msg.exporterGirocodePurpose, document.getName(),dateFormatter.getFormattedLocalizedDate(document.getDocumentDate())); 
        girocode.setText(giroText);
        String generatedString = girocode.generateString();
        
        // since the version is hardcoded ("001") we have to use an ugly hack
        var margin = params.get("MARGIN");
        ByteArrayOutputStream qrCodeFile = QRCode.from(generatedString.replaceFirst("001", "002"))
        		.withHint(EncodeHintType.MARGIN, margin)
        		.to(ImageType.PNG).stream();
        return qrCodeFile.toByteArray();
    }
}
