package com.sebulli.fakturama.parts;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.browser.Browser;

import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.preferences.FakturamaPreferenceStoreProvider;

/**
 * Sends authentication data back to the trusted page after the native callback returns.
 *
 * <p>On current WebKitGTK, SWT's synchronous BrowserFunction callback reaches Java but its
 * {@code swt://browserfunction} XHR response raises NetworkError in JavaScript. A subsequent
 * {@link Browser#execute(String)} works, so the page receives this data through an event.
 */
public class FktRequestAuthInfoFunction extends AbstractFktBrowserFunction {

    public static final String JS_NAME = "__fkt_requestAuthInfo";

    public FktRequestAuthInfoFunction(final Browser browser, final ILogger log) {
        super(browser, JS_NAME, log);
    }

    @Override
    protected Object doFunction(final Object[] arguments) {
        Browser browser = getBrowser();
        String requestedUrl = browser.getUrl();
        IPreferenceStore preferences = FakturamaPreferenceStoreProvider.getInstance().getPreferenceStore();
        String secret = preferences != null ? preferences.getString(Constants.PREFERENCES_BROWSER_FKT_SHARED_SECRET) : "";

        Map<String, String> info = new LinkedHashMap<>();
        info.put("token", secret);
        info.put("clientUsername", FktGetEnvironmentInfoFunction.resolveGreetingName());
        info.put("clientHostname", FktGetEnvironmentInfoFunction.resolveHostName());
        String payload = FktJsonUtil.toJsonObject(info);

        browser.getDisplay().asyncExec(() -> {
            // Navigation may complete between the callback and this deferred response.
            if (browser.isDisposed() || !requestedUrl.equals(browser.getUrl()) || !FktOriginTrust.isTrusted(browser)) {
                return;
            }
            browser.execute("window.dispatchEvent(new CustomEvent('FKTAuthInfo', {detail: " + payload + "}));");
        });
        return null;
    }
}
