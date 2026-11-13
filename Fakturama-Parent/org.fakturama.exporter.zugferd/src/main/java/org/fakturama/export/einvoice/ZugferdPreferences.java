/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2016 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package org.fakturama.export.einvoice;

import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.BooleanPropertyAction;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.nebula.widgets.opal.checkboxgroup.CheckBoxGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

import com.sebulli.fakturama.preferences.IInitializablePreference;
import com.sebulli.fakturama.preferences.PreferencesInDatabase;
import com.sebulli.fakturama.preferences.Synchronize;

/**
 * Preferences for the ZUGFeRD export settings (including Factur-X and
 * XRechnung)
 */
public class ZugferdPreferences extends FieldEditorPreferencePage implements IInitializablePreference {

    @Inject
    @Translation
    protected ZFMessages msg;

    @Inject
    @Optional
    private PreferencesInDatabase preferencesInDatabase;

    @Inject
    @Optional
    private IEclipseContext context;

    private StringFieldEditor xrechnungPathField;

    private Composite editorParent;

    /**
     * The Constructor.
     */
    public ZugferdPreferences() {
        super(GRID);

    }

    /**
     * Creates the page's field editors.
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
     */
    @Override
    protected void createFieldEditors() {
        final CheckBoxGroup group = new CheckBoxGroup(getFieldEditorParent(), SWT.NONE);
        editorParent = group.getContent();

        group.setText(msg.zugferdPreferencesIsActive);
        group.setLayoutData(GridDataFactory.fillDefaults().span(2, 1).align(SWT.FILL, SWT.FILL).grab(true, false).create());

        final BooleanPropertyAction booleanPropertyAction = new BooleanPropertyAction("useZF", getPreferenceStore(), ZFConstants.PREFERENCES_ZUGFERD_ACTIVE);
        group.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                booleanPropertyAction.setChecked(((CheckBoxGroup) e.getSource()).getSelection());
                booleanPropertyAction.run();
            }
        });

        xrechnungPathField = new StringFieldEditor(ZFConstants.PREFERENCES_ZUGFERD_PATH, msg.zugferdPreferencesFilelocation, editorParent) {
            @Override
            public void setEmptyStringAllowed(final boolean b) {
                super.setEmptyStringAllowed(b);
                // else the error flag isn't reset
                refreshValidState();
            }
        };
        xrechnungPathField.fillIntoGrid(editorParent, 2);

        xrechnungPathField.setEmptyStringAllowed(false);
        addField(xrechnungPathField);

        final boolean isZFActive = getPreferenceStore().getBoolean(ZFConstants.PREFERENCES_ZUGFERD_ACTIVE);
        group.setSelection(isZFActive);
        xrechnungPathField.setEnabled(isZFActive, editorParent);
        xrechnungPathField.setEmptyStringAllowed(!isZFActive);
    }

    /**
     * Write or read the preference settings to or from the data base
     * 
     * @param write
     *            TRUE: Write to the data base
     */
    public void syncWithPreferencesFromDatabase(final boolean write) {
        preferencesInDatabase.syncWithPreferencesFromDatabase(ZFConstants.PREFERENCES_ZUGFERD_ACTIVE, write);
        preferencesInDatabase.syncWithPreferencesFromDatabase(ZFConstants.PREFERENCES_ZUGFERD_PATH, write);
    }

    @Override
    public void setInitValues(final IPreferenceStore node) {
        node.setDefault(ZFConstants.PREFERENCES_ZUGFERD_ACTIVE, Boolean.FALSE);
        node.setDefault(ZFConstants.PREFERENCES_ZUGFERD_PATH, "XML/{yyyy}/{doctype}/{docname}_{address}.xml");
    }

    @Synchronize
    public void loadUserValuesFromDB() {

    }

    @Override
    @Synchronize
    public void loadOrSaveUserValuesFromDB(final IEclipseContext context) {
        // TRUE ==> Save preferences
        // FALSE ==> Load preferences
        if (preferencesInDatabase != null) {
            final Boolean isWrite = (Boolean) context.get(PreferencesInDatabase.LOAD_OR_SAVE_PREFERENCES_FROM_OR_IN_DATABASE);
            syncWithPreferencesFromDatabase(BooleanUtils.toBoolean(isWrite));
        }
    }

}
