/**
 * 
 */
package com.sebulli.fakturama.parts.converter;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.conversion.Converter;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.UNTDID4461;

/**
 *
 */
public class UNTDID4461TranslationToPaymentCodeConverter extends Converter<String, String> {

    protected Messages msg;

    public UNTDID4461TranslationToPaymentCodeConverter(final Messages msg) {
        super(String.class, String.class);
        this.msg = msg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.databinding.conversion.IConverter#convert(java.lang.
     * Object)
     * fromObject: untdid translation
     * result: the code?
     */
    @Override
    public String convert(final String fromObject) {
        final String result = StringUtils.trimToNull(fromObject);
        if (result == null) {
            return UNTDID4461.DEFAULT_VALUE.getCode();
        }

        return Arrays.stream(UNTDID4461.values()).filter(e -> result.equalsIgnoreCase(msg.getMessageFromKey(e.getTranslationKey()).trim())) //
                .findFirst() //
                .orElse(UNTDID4461.DEFAULT_VALUE).getCode();

    }

}
