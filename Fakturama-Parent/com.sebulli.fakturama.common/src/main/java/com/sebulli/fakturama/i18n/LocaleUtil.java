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
     *            the language code to be used. If no OSGi language is set,
     *            the process locale (LC_ALL/LC_MESSAGES/LANG) and finally
     *            {@link Locale#getDefault()} are used.
     * @return a {@link LocaleUtil} instance
     */
    @SuppressWarnings("restriction")
    public LocaleUtil getInstance() {

        String lang = (Activator.getContext() == null ? System.getProperty(EclipseStarter.PROP_NL)
                : Activator.getContext().getProperty(EclipseStarter.PROP_NL));

        // Product launchers commonly provide a language-only `-nl en`.
        // In that case honour a regional OS locale (LANG/LC_*) so the UI and
        // currency follow the user's desktop settings instead of the
        // launcher's generic English default.
        final String systemLang = systemLanguage();
        if (StringUtils.isBlank(lang) || (lang.length() <= 2 && systemLang.matches(".*[_-].*"))) {
            lang = systemLang;
        }
        lang = normalizeLanguage(lang);
        if (StringUtils.isBlank(lang)) {
            lang = "en_US";
        }
        // A language-only value (for example Eclipse's default NL=en) must be
        // expanded to a region before it is used for currency formatting.
        if (lang.length() < 3) {
            final List<Locale> countriesByLanguage = LocaleUtils.countriesByLanguage(lang);
            final Optional<Locale> regionalLocale = countriesByLanguage.stream()
                    .filter(locale -> StringUtils.isNotBlank(locale.getCountry())).findFirst();
            if (regionalLocale.isPresent()) {
                final Locale tmpLocale = regionalLocale.get();
                initLocaleUtil(String.format("%s_%s", tmpLocale.getLanguage(), tmpLocale.getCountry()));
            } else {
                initLocaleUtil(String.format("%s_%s", lang, lang.toUpperCase()));
            }
        } else if (!getDefaultLocale().getLanguage().contentEquals(lang) || localeLookUp.isEmpty()) {
            initLocaleUtil(lang);
        }
        // A language-only value such as "de" can equal the JVM default
        // language and would otherwise skip initialization of the lookup maps.
        if (localeLookUp.isEmpty()) {
            initLocaleUtil(lang);
        }
        
        return this;
    }

    /** Resolve the conventional Unix locale variables when Eclipse was not
     * started with an explicit -nl argument. */
    private static String systemLanguage() {
        final String[] candidates = { System.getenv("LC_ALL"), System.getenv("LC_MESSAGES"), System.getenv("LANG") };
        for (final String candidate : candidates) {
            if (StringUtils.isNotBlank(candidate) && !"C".equalsIgnoreCase(candidate) && !"POSIX".equalsIgnoreCase(candidate)) {
                return candidate;
            }
        }
        final Locale locale = Locale.getDefault();
        if (locale != null && StringUtils.isNotBlank(locale.getLanguage())) {
            return locale.getLanguage() + (StringUtils.isNotBlank(locale.getCountry()) ? "_" + locale.getCountry() : "");
        }
        return null;
    }

    /** Convert LANG-style values such as {@code de_DE.UTF-8} or
     * {@code de-DE} to the underscore form used by the existing bundles. */
    private static String normalizeLanguage(final String language) {
        if (StringUtils.isBlank(language)) {
            return null;
        }
        String normalized = language.trim().replace('-', '_');
        final int encoding = normalized.indexOf('.');
        if (encoding >= 0) {
            normalized = normalized.substring(0, encoding);
        }
        final int modifier = normalized.indexOf('@');
        if (modifier >= 0) {
            normalized = normalized.substring(0, modifier);
        }
        return normalized;
    }

    @Override
    public Locale getDefaultLocale() {
        // use OSGi service
        ServiceReference[] references = null;
        BundleContext bundleContext = Activator.getContext();
        if (bundleContext == null) {
            return Locale.getDefault();
        }
        try {
            references = bundleContext.getAllServiceReferences(null, "(objectClass=" + LocaleProvider.class.getName() + ")");
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
    	ensureInitialized();
    	Optional<Locale> retval = Optional.ofNullable(countryLocaleMap.get(country));
    	if(!retval.isPresent()) {
    		// fallback with English country names
    		retval = countryLocaleMap.values().stream()
	                .filter(e -> country.equalsIgnoreCase(e.getDisplayCountry(getDefaultLocale())) 
	                		|| country.equalsIgnoreCase(e.getDisplayCountry(Locale.ENGLISH)))
	                .findAny();
    	}
		return retval;
    }

    private void ensureInitialized() {
		if(countryLocaleMap.isEmpty() || localeLookUp.isEmpty()) {
			initLocaleUtil(String.join("_", defaultLocale.getLanguage(), defaultLocale.getCountry()));
		}
	}

	/* (non-Javadoc)
    * @see com.sebulli.fakturama.i18n.ILocaleService#findByCode(java.lang.String)
    */
    @Override
    public Optional<Locale> findByCode(final String code) {
    	ensureInitialized();
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
                // Use a real locale identifier as fallback.  The former
                // display-country value ("United States") could not be
                // parsed and consequently returned the language-only JVM
                // locale (for example "en"), which has no currency unit.
                currencyLocale = Locale.US;
                return currencyLocale;
// Alternative:
//              localeString = Locale.GERMAN.getCountry() + "/" + Locale.GERMAN.getLanguage();
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
