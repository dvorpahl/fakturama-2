/**
 * Utility for handling different Locale and language codes
 */
package com.sebulli.fakturama.i18n;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.eclipse.osgi.service.localization.LocaleProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;

import com.sebulli.fakturama.common.Activator;
import com.sebulli.fakturama.misc.Constants;

/**
 * Utility class for handling {@link Locale}s.
 */
@Component(configurationPid = "com.sebulli.fakturama.i18n.LocaleUtil", immediate = true)
public class LocaleUtil implements ILocaleService {

    private final Map<String, Locale> countryLocaleMap = new HashMap<>();

    private Locale defaultLocale = Locale.getDefault();
    private SortedMap<String, String> localeCountryMap;
    private final Map<String, Locale> localeLookUp = new HashMap<>();
    private Locale currencyLocale = null;

    /**
     * Returns a reference to the {@link LocaleUtil}. Used for initialization
     * with a language code.
     * 
     * @param lang
     *            the language code to be used. If <code>null</code>, then
     *            "en_US" is used.
     * @return a {@link LocaleUtil} instance
     */
    @SuppressWarnings("restriction")
    public void getInstance() {

        String lang = (Activator.getContext() == null ? System.getProperty(EclipseStarter.PROP_NL)
                : Activator.getContext().getProperty(EclipseStarter.PROP_NL));

        if (lang == null) {
            initLocaleUtil("en_US");
        }
        // We have to track different language settings (e.g., from command line) which
        // aren't equal to the default locale.
        if (lang != null && !getDefaultLocale().getLanguage().contentEquals(lang)) {
            /*
             * If a two-letter locale is given try to interpret it (because we need it later
             * for determining currency etc.)
             */
            if (lang.length() < 3) {
                List<Locale> countriesByLanguage = LocaleUtils.countriesByLanguage(lang);
                // try to get the locale from language, use the first fitting country
                if (!countriesByLanguage.isEmpty()) {
                    Locale tmpLocale = countriesByLanguage.get(0);
                    initLocaleUtil(String.format("%s_%s", tmpLocale.getLanguage(), tmpLocale.getCountry()));
                } else {
                    // if none found, try to guess it from country code (very uncertain!)
                    initLocaleUtil(String.format("%s_%s", lang, lang.toUpperCase()));
                }
            } else {
                initLocaleUtil(lang);
            }
        }
    }

    @Override
    public Locale getDefaultLocale() {
        // use OSGi service
        ServiceReference[] references = null;
        BundleContext bundleContext = Activator.getContext();
        try {
            references = bundleContext.getAllServiceReferences(null, LocaleProvider.class.getName());
        } catch (InvalidSyntaxException e) {
            // do nothing
        }
        if (references == null || references.length < 1) {
            return Locale.getDefault();
        }
        Object service = bundleContext.getService(references[0]);
        LocaleProvider localeProvider = (LocaleProvider) service;
        if (localeProvider != null) {
            Locale currentLocale = localeProvider.getLocale();
            bundleContext.ungetService(references[0]);
            if (currentLocale != null) {
                return currentLocale;
            }
        }
        return Locale.getDefault();
    }

    /**
     * Private constructor initializes the Locale hashmap.
     * 
     * @param lang
     */
    private void initLocaleUtil(final String lang) {
        Locale[] availableLocales = Locale.getAvailableLocales();

        // clear caches
        localeLookUp.clear();
        countryLocaleMap.clear();
        // only countries are relevant
        //        String[] locales = Locale.getISOCountries();
        if (StringUtils.isNotBlank(lang)) {
            // the language code are the letters before "_"
            String splittedString[] = lang.split("_");
            Locale.Builder builder = new Locale.Builder().setLanguage(splittedString[0]);
            if (splittedString.length > 1) {
                builder.setRegion(splittedString[1]);
            }
            Locale b = builder.build();
            if (b != null) {
                defaultLocale = b;
            }
        }

        // fill some helper maps
        for (Locale locale : availableLocales) {
            if (locale != null && StringUtils.length(locale.getCountry()) > 0 && localeLookUp.get(locale.getCountry()) == null) {
                localeLookUp.put(locale.getCountry(), locale);
                countryLocaleMap.put(locale.getDisplayCountry(defaultLocale), locale);
                //            System.out.println("Country Code = " + obj.getCountry() + ", Country Name = " + obj.getDisplayCountry(defaultLocale));
            }
        }
    }

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.i18n.ILocaleService#findCodeByDisplayCountry(java.lang.String)
     */
    @Override
    public String findCodeByDisplayCountry(final String country, final String lang) {
        // TODO use lang, but without interfering with other methods!
        //        Optional<Locale> retval = findLocaleByDisplayCountry(country);

        Optional<Locale> retval = countryLocaleMap.values().stream()
                .filter(e -> country.equalsIgnoreCase(e.getDisplayCountry(getDefaultLocale())) || country.equalsIgnoreCase(e.getDisplayCountry(Locale.ENGLISH)))
                .findAny();
        // hint: countryLocaleString.getDisplayCountry(defaultLocale) gives
        // the country as localized string
        return retval.isPresent() ? retval.get().getCountry() : null;
    }

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.i18n.ILocaleService#findLocaleByDisplayCountry(java.lang.String)
     */
    @Override
    public Optional<Locale> findLocaleByDisplayCountry(final String country) {
        return Optional.ofNullable(countryLocaleMap.get(country));
    }

    /* (non-Javadoc)
    * @see com.sebulli.fakturama.i18n.ILocaleService#findByCode(java.lang.String)
    */
    @Override
    public Optional<Locale> findByCode(final String code) {
        return Optional.ofNullable(localeLookUp.get(StringUtils.upperCase(code)));
    }

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.i18n.ILocaleService#getDefaultLocale()
     */
    //    @Override
    public Locale getDefaultLocale1() {
        return defaultLocale;
    }

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.i18n.ILocaleService#getCountryLocaleMap()
     */
    @Override
    public Map<String, Locale> getCountryLocaleMap() {
        return countryLocaleMap;
    }

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.i18n.ILocaleService#getLocaleCountryMap()
     */
    @Override
    public Map<String, String> getLocaleCountryMap() {
        if (localeCountryMap == null || localeCountryMap.isEmpty()) {
            initLocaleUtil(getDefaultLocale().toString());
            Map<String, String> tmpMap = getCountryLocaleMap().entrySet().stream()
                    .collect(Collectors.toMap((final Entry<String, Locale> e) -> e.getValue().getCountry(), (final Entry<String, Locale> e) -> e.getKey()));
            ValueComparator bvc = new ValueComparator(tmpMap, defaultLocale);
            localeCountryMap = new TreeMap<>(bvc);
            localeCountryMap.putAll(tmpMap);
        }
        return localeCountryMap;
    }

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.i18n.ILocaleService#getCurrencyLocale()
     */
    @Override
    public Locale getCurrencyLocale() {
        if (currencyLocale == null) {
            String localeString = Activator.getPreferenceStore().getString(Constants.PREFERENCE_CURRENCY_LOCALE);
            if (localeString.isEmpty()) {
                localeString = Locale.US.getDisplayCountry();
            }
            Pattern pattern = Pattern.compile("(\\w{2})/(\\w{2})");
            Matcher matcher = pattern.matcher(localeString);
            if (matcher.matches() && matcher.groupCount() > 1) {
                String s = matcher.group(1);
                String s2 = matcher.group(2);
                currencyLocale = new Locale(s, s2);
            } else {
                currencyLocale = getDefaultLocale();
            }
        }
        return currencyLocale;
    }

    @Override
    public void refresh() {
        currencyLocale = null;
    }

}
