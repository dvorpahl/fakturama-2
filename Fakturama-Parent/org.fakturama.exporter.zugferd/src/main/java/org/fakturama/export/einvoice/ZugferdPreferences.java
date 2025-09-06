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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.BooleanPropertyAction;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.nebula.widgets.opal.checkboxgroup.CheckBoxGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
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

    private ComboFieldEditor conformanceLevelCombo;

    enum ZugferdVersion {
        V1("1", "1"), //
        V2_1("2.1 (XRechnung / Factur-X)", "2.1");

        final String description, version;

        ZugferdVersion(final String description, final String version) {
            this.description = description;
            this.version = version;
        }

        public String getDescription() {
            return description;
        }

        public String getVersion() {
            return version;
        }
    }

    private final Map<ZugferdVersion, String[][]> featureMap;

    private StringFieldEditor xrechnungPathField;

    private BooleanFieldEditor xrechnungEmbedXmlInPdf;

    private Composite editorParent;

    private Composite paddingComposite;

    /**
     * The Constructor.
     */
    public ZugferdPreferences() {
        super(GRID);

        featureMap = new EnumMap<>(ZugferdVersion.class);

        featureMap.put(ZugferdVersion.V2_1,
                new String[][] { { ConformanceLevel.ZUGFERD_V2_EN16931.toString(), ConformanceLevel.ZUGFERD_V2_EN16931.toString() },
                        { ConformanceLevel.XRECHNUNG.toString(), ConformanceLevel.XRECHNUNG.toString() },
                        { ConformanceLevel.FACTURX_EN16931.toString(), ConformanceLevel.FACTURX_EN16931.toString() } });
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

        // fill combo box according to selected version!
        final ZugferdVersion zfVersion = ZugferdVersion.V2_1;
        conformanceLevelCombo = new ComboFieldEditor(ZFConstants.PREFERENCES_ZUGFERD_PROFILE, msg.zugferdPreferencesProfile, featureMap.get(zfVersion),
                editorParent);
        conformanceLevelCombo.fillIntoGrid(editorParent, 2);
        addField(conformanceLevelCombo);

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

        paddingComposite = WidgetFactory.composite(SWT.NONE).layout(GridLayoutFactory.fillDefaults().numColumns(1).create())
                .layoutData(GridDataFactory.swtDefaults().span(1, 2).indent(0, 5).align(SWT.BEGINNING, SWT.BEGINNING).create()).create(editorParent);

        xrechnungEmbedXmlInPdf = new BooleanFieldEditor(ZFConstants.PREFERENCES_ZUGFERD_EMBED_IN_PDF, msg.zugferdPreferencesEmbedinpdf, paddingComposite);

        addField(xrechnungEmbedXmlInPdf);

        final boolean isZFActive = getPreferenceStore().getBoolean(ZFConstants.PREFERENCES_ZUGFERD_ACTIVE);
        group.setSelection(isZFActive);
        enableXRechnungPathField(isZFActive, getPreferenceStore().getString(ZFConstants.PREFERENCES_ZUGFERD_PROFILE));
    }

    private void enableXRechnungPathField(final boolean isZFActive, String currentConformanceLevelString) {
        ConformanceLevel currentConformanceLevel;

        if (currentConformanceLevelString == null) {
            final Combo comboBox = getCombo(conformanceLevelCombo);
            if (comboBox != null) {
                currentConformanceLevelString = comboBox.getItem(comboBox.getSelectionIndex());
            }
        }

        try {
            currentConformanceLevel = ConformanceLevel.valueOf(currentConformanceLevelString);
        } catch (final Exception e) {
            // only if conformance level can't be determined
            currentConformanceLevel = ConformanceLevel.FACTURX_EN16931;
        }
        final boolean enabled = isZFActive && ConformanceLevel.XRECHNUNG == currentConformanceLevel;
        xrechnungPathField.setEnabled(enabled, editorParent);
        xrechnungPathField.setEmptyStringAllowed(!enabled);
        xrechnungEmbedXmlInPdf.setEnabled(enabled, paddingComposite);
        checkState();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent event) {
        super.propertyChange(event);
        final boolean isZFActive = getPreferenceStore().getBoolean(ZFConstants.PREFERENCES_ZUGFERD_ACTIVE);
        if (event.getSource() instanceof ComboFieldEditor) {
            enableXRechnungPathField(isZFActive, (String) event.getNewValue());
        }
    }

    /**
     * Ugly hack get the combo box from field editor.
     * 
     * @param comboFieldEditor
     * @return
     */
    private Combo getCombo(final ComboFieldEditor comboFieldEditor) {
        Method privateStringMethod;

        try {
            privateStringMethod = ComboFieldEditor.class.getDeclaredMethod("getComboBoxControl", Composite.class);
            privateStringMethod.setAccessible(true);
            return (Combo) privateStringMethod.invoke(comboFieldEditor, editorParent);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Write or read the preference settings to or from the data base
     * 
     * @param write
     *            TRUE: Write to the data base
     */
    public void syncWithPreferencesFromDatabase(final boolean write) {
        preferencesInDatabase.syncWithPreferencesFromDatabase(ZFConstants.PREFERENCES_ZUGFERD_ACTIVE, write);
        preferencesInDatabase.syncWithPreferencesFromDatabase(ZFConstants.PREFERENCES_ZUGFERD_VERSION, write);
        preferencesInDatabase.syncWithPreferencesFromDatabase(ZFConstants.PREFERENCES_ZUGFERD_TEST, write);
        preferencesInDatabase.syncWithPreferencesFromDatabase(ZFConstants.PREFERENCES_ZUGFERD_PATH, write);
        preferencesInDatabase.syncWithPreferencesFromDatabase(ZFConstants.PREFERENCES_ZUGFERD_PROFILE, write);
        preferencesInDatabase.syncWithPreferencesFromDatabase(ZFConstants.PREFERENCES_ZUGFERD_EMBED_IN_PDF, write);
    }

    @Override
    public void setInitValues(final IPreferenceStore node) {
        node.setDefault(ZFConstants.PREFERENCES_ZUGFERD_ACTIVE, Boolean.FALSE);
        node.setDefault(ZFConstants.PREFERENCES_ZUGFERD_VERSION, ZugferdVersion.V2_1.getVersion());
        node.setDefault(ZFConstants.PREFERENCES_ZUGFERD_TEST, Boolean.FALSE);
        node.setDefault(ZFConstants.PREFERENCES_ZUGFERD_PATH, "XML/{yyyy}/{doctype}/{docname}_{address}.xml");
        node.setDefault(ZFConstants.PREFERENCES_ZUGFERD_EMBED_IN_PDF, Boolean.FALSE);
        node.setDefault(ZFConstants.PREFERENCES_ZUGFERD_PROFILE, ConformanceLevel.XRECHNUNG.name());
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
