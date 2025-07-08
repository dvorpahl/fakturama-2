/**
 * 
 */
package com.sebulli.fakturama.parts.converter;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.conversion.Converter;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.UNTDID5305;

/**
 *
 */
public class UNTDID5305TranslationToPaymentCodeConverter extends Converter<String, UNTDID5305> {

    protected Messages msg;

    public UNTDID5305TranslationToPaymentCodeConverter(final Messages msg) {
        super(String.class, UNTDID5305.class);
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
    public UNTDID5305 convert(final String fromObject) {
        final String result = StringUtils.trimToNull(fromObject);
        if (result == null) {
            return UNTDID5305.DEFAULT_VALUE;
        }

        return Arrays.stream(UNTDID5305.values()).filter(e -> result.equalsIgnoreCase(msg.getMessageFromKey(e.getTranslationKey()).trim())) //
                .findFirst() //
                .orElse(UNTDID5305.DEFAULT_VALUE);

    }

}
