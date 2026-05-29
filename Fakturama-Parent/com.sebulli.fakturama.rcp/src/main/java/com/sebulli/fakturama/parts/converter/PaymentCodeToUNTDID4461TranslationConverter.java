/**
 * 
 */
package com.sebulli.fakturama.parts.converter;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.conversion.Converter;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.UNTDID4461;

/**
 *
 */
public class PaymentCodeToUNTDID4461TranslationConverter extends Converter<String, String> {

    protected Messages msg;

    public PaymentCodeToUNTDID4461TranslationConverter(final Messages msg) {
        super(String.class, String.class);
        this.msg = msg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.databinding.conversion.IConverter#convert(java.lang.
     * Object)
     * imput: payment code
     * 
     * @return translation
     */
    @Override
    public String convert(final String fromObject) {
        String input;
        if ((input = StringUtils.trimToNull(fromObject)) == null) {
            return translate(UNTDID4461.DEFAULT_VALUE);
        }

        try {
            final UNTDID4461 value = UNTDID4461.getByCode(input);
            return translate(value);
        } catch (final Exception ex) {
            // return default if not found
            return translate(UNTDID4461.DEFAULT_VALUE);
        }
    }

    private String translate(final UNTDID4461 value) {
        return msg.getMessageFromKey(value.getTranslationKey());
    }
}
