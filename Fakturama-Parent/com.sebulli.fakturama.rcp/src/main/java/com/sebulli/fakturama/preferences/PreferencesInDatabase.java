/*
 * Fakturama - Free Invoicing Software - http://fakturama.sebulli.com
 * 
 * Copyright (C) 2012 Gerd Bartelt
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Gerd Bartelt - initial API and implementation
 */

package com.sebulli.fakturama.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.preference.IPreferenceStore;

import com.sebulli.fakturama.dao.PropertiesDAO;

/**
 * Write or read preference settings to or from the data base
 * 
 */
public class PreferencesInDatabase {

    public static final String LOAD_OR_SAVE_PREFERENCES_FROM_OR_IN_DATABASE = "loadOrSavePreferencesFromOrInDatabase";

    @Inject
    private IPreferenceStore preferences;

    @Inject
    @Preference
    private IEclipsePreferences pref;

    @Inject
    private PropertiesDAO propertiesDAO;

    @Inject
    private IEclipseContext context;

    /**
     * Snapshot of every stored property, loaded once per {@link #loadOrSavePreferencesFromOrInDatabase}
     * read pass so {@link #loadPreferenceValue(String)} doesn't hit the database once per key (~15
     * preference pages x ~10 keys each = ~145 individual SELECTs otherwise). {@code null} outside
     * such a pass (e.g. a direct {@link #syncWithPreferencesFromDatabase} call), in which case
     * {@link #loadPreferenceValue(String)} falls back to the original per-key lookup.
     */
    private Map<String, String> cachedPropertyValues;

    /**
     * Load one preference from the data base
     *
     * @param key
     *            The key of the preference value
     */
    private void loadPreferenceValue(final String key) {
        final String value = cachedPropertyValues != null ? cachedPropertyValues.get(key) : propertiesDAO.findPropertyValue(key).orElse(null);
        if (value != null) {
            preferences.setValue(key, value);
            pref.put(key, value);
        }
    }

    /**
     * Save one preference to the data base
     *
     * @param key
     *            The key of the preference value
     */
    private void savePreferenceValue(final String key) {
        // A demo run (see run-demo.sh / -Dfakturama.demoMode=true) points at a shared,
        // reused-by-everyone test database - writing local preference edits (table settings,
        // number ranges, company data, ...) back into it would bleed from one demo session into
        // the next. Every write path (this batch sync AND a preference page's own performOk())
        // funnels through this one method, so gating it here covers all of them.
        if (Boolean.getBoolean("fakturama.demoMode")) {
            return;
        }
        final String s = preferences.getString(key);
        if (s != null && propertiesDAO != null) {
            propertiesDAO.setProperty(key, s);
        }
    }

    /**
     * Write to or read from the data base
     * 
     * @param key
     *            The key to read or to write
     * @param write
     *            True, if the value should be written
     */
    public void syncWithPreferencesFromDatabase(final String key, final boolean write) {
        if (write) {
            savePreferenceValue(key);
        } else {
            loadPreferenceValue(key);
        }
    }

    /**
     * Load or save all preference values from database of the following
     * preference pages.
     */
    public void loadOrSavePreferencesFromOrInDatabase(final boolean save) {
        final List<Class<? extends IInitializablePreference>> classesToInit = new ArrayList<>();
        classesToInit.add(ContactFormatPreferencePage.class);
        classesToInit.add(DocumentPreferencePage.class);
        classesToInit.add(NumberRangeFormatPreferencePage.class);
        classesToInit.add(WebShopImportPreferencePage.class);
        classesToInit.add(OptionalItemsPreferencePage.class);
        classesToInit.add(ToolbarPreferencePage.class);
        classesToInit.add(ContactPreferencePage.class);
        classesToInit.add(GeneralPreferencePage.class);
        classesToInit.add(NumberRangeValuesPreferencePage.class);
        classesToInit.add(OfficePreferencePage.class);
        classesToInit.add(ProductPreferencePage.class);
        classesToInit.add(YourCompanyPreferencePage.class);
        classesToInit.add(ExportPreferencePage.class);
        classesToInit.add(WebShopAuthorizationPreferencePage.class);
        classesToInit.add(BrowserPreferencePage.class);

        context.set(LOAD_OR_SAVE_PREFERENCES_FROM_OR_IN_DATABASE, save);
        if (!save) {
            cachedPropertyValues = propertiesDAO.findAllPropertyValues();
        }
        try {
            // Initialize every single preference page
            for (final Class<? extends IInitializablePreference> clazz : classesToInit) {
                final IInitializablePreference p = ContextInjectionFactory.make(clazz, context);
                ContextInjectionFactory.invoke(p, Synchronize.class, context);
            }
        } finally {
            cachedPropertyValues = null;
        }
    }

    /**
     * Load all preference values from database of the following preference
     * pages.
     */
    public void loadPreferencesFromDatabase() {
        loadOrSavePreferencesFromOrInDatabase(false);
    }

    /**
     * Write all preference values to database of the following preference
     * pages.
     */
    public void savePreferencesInDatabase() {
        loadOrSavePreferencesFromOrInDatabase(true);
    }

}
