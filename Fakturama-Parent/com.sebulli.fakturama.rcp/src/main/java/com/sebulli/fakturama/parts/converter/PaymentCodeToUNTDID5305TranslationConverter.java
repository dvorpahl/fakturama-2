/**
 * 
 */
package com.sebulli.fakturama.parts.converter;

import org.eclipse.core.databinding.conversion.Converter;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.UNTDID5305;

/**
 *
 */
public class PaymentCodeToUNTDID5305TranslationConverter extends Converter<UNTDID5305, String> {

    protected Messages msg;

    public PaymentCodeToUNTDID5305TranslationConverter(final Messages msg) {
        super(UNTDID5305.class, String.class);
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
    public String convert(final UNTDID5305 fromObject) {
        if (fromObject == null) {
            return translate(UNTDID5305.DEFAULT_VALUE);
        }

        try {
            return translate(fromObject);
        } catch (final Exception ex) {
            // return default if not found
            return translate(UNTDID5305.DEFAULT_VALUE);
        }
    }

    private String translate(final UNTDID5305 value) {
        return msg.getMessageFromKey(value.getTranslationKey());
    }
}
