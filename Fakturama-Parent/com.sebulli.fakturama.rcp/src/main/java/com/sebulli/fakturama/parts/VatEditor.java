/*
 * Fakturama - Free Invoicing Software - http://fakturama.sebulli.com
 * 
 * Copyright (C) 2012 Gerd Bartelt
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Gerd Bartelt - initial API and implementation
 */

package com.sebulli.fakturama.parts;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.formattedtext.PercentFormatter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.event.Event;

import com.sebulli.fakturama.converter.CommonConverter;
import com.sebulli.fakturama.dao.VatCategoriesDAO;
import com.sebulli.fakturama.dao.VatsDAO;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.handlers.CallEditor;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.UNTDID5305;
import com.sebulli.fakturama.model.CategoryComparator;
import com.sebulli.fakturama.model.VAT;
import com.sebulli.fakturama.model.VATCategory;
import com.sebulli.fakturama.model.VAT_;
import com.sebulli.fakturama.parts.converter.CategoryConverter;
import com.sebulli.fakturama.parts.converter.PaymentCodeToUNTDID5305TranslationConverter;
import com.sebulli.fakturama.parts.converter.StringToCategoryConverter;
import com.sebulli.fakturama.parts.converter.UNTDID5305TranslationToPaymentCodeConverter;
import com.sebulli.fakturama.resources.core.Icon;

/**
 * The VAT editor
 */
public class VatEditor extends Editor<VAT> {

    @Inject
    protected VatsDAO vatDao;

    @Inject
    protected VatCategoriesDAO vatCategoriesDAO;

    @Inject
    private IPreferenceStore preferences;

    // Editor's ID
    public static final String ID = "com.sebulli.fakturama.editors.vatEditor";

    public static final String EDITOR_ID = "VatEditor";

    // SWT widgets of the editor
    private Composite top;
    private Text textName;
    private Text textDescription;
    private FormattedText textValue;
    private FormattedText textSalesEqTax;
    private CCombo comboCategory;

    private Combo comboTaxCode;

    private VATCategory oldCat;

    // defines if the vat is just created
    private boolean newVat;

    /**
     * This field can't be injected since the part is created from
     * a PartDescriptor (see createPartControl).
     */
    private MPart part;

    @Inject
    private EPartService partService;

    // This UniDataSet represents the editor's input 
    private VAT editorVat = null;

    /**
     * Saves the contents of this part
     * 
     * @param monitor
     *            Progress monitor
     */
    @Persist
    public Boolean doSave(final IProgressMonitor monitor) {

        /*
         * the following parameters are not saved:
         * - id (constant)
         */

        // Always set the editor's data to "undeleted"
        editorVat.setDeleted(Boolean.FALSE);

        // Set the VAT data
        // ... done through databinding...

        // save the new or updated VAT
        try {
            // at first, check the category for a new entry
            // (the user could have written a new one into the combo field)
            final String testCat = comboCategory.getText();
            // if there's no category we can skip this step
            if (StringUtils.isNotBlank(testCat)) {
                final VATCategory parentCategory = vatCategoriesDAO.getCategory(testCat, true);

                // parentCategory now has the last found Category
                editorVat.setCategory(parentCategory);
            }

            /*
             * If we DON'T use update, the category is saved again and again and
             * again
             * because we have CascadeType.PERSIST. If we use update and save
             * the new VatCategory before,
             * all went ok. That's the point...
             */
            editorVat = vatDao.update(editorVat);

            // check if we can delete the old category (if it's empty and is not a parent category of the current one)
            if (oldCat != null && oldCat != editorVat.getCategory() && !isParent(oldCat, editorVat.getCategory())) {
                final long countOfEntriesInCategory = vatDao.countByCategory(oldCat);
                if (countOfEntriesInCategory == 0) {
                    vatCategoriesDAO.deleteEmptyCategory(oldCat);
                }
            }

            oldCat = editorVat.getCategory();
        } catch (final FakturamaStoringException e) {
            log.error(e, "can't save the current VAT: " + editorVat.toString());
            return Boolean.FALSE;
        }

        if (newVat) {
            newVat = false;
            stdComposite.stdButton.setEnabled(true);
        }

        // Set the Editor's name to the payment name...
        part.setLabel(editorVat.getName());

        // ...and "mark" it with current objectId (though it can be find by 
        // CallEditor if one tries to open it immediately from list view)
        // please note that you have to use transientData Hashmap, else
        // else you get an ugly "java.lang.IllegalArgumentException: A StringToStringMap that was NOT MApplicationElement.persistedState changed"
        // while saving a new VAT or also other editor.
        part.getTransientData().put(CallEditor.PARAM_OBJ_ID, Long.toString(editorVat.getId()));

        // Refresh the table view of all VATs (this also refreshes the tree of categories)
        evtBroker.post(VatEditor.EDITOR_ID, Editor.UPDATE_EVENT);

        // re-bind model
        bindModel();

        // reset dirty flag
        getMDirtyablePart().setDirty(false);
        return Boolean.TRUE;
    }

    /**
     * Checks if the given {@link VATCategory} is parent of the other one.
     * 
     * @param testParent
     *            the parent {@link VATCategory} to test
     * @param category
     *            a valid category
     * @return <code>true</code>, if <code>testParent</code> is parent of
     *         <code>category</code>
     */
    private boolean isParent(final VATCategory testParent, final VATCategory category) {
        if (testParent != null && category != null && category.getParent() != null) {
            if (category.getParent() == testParent) {
                return true;
            } else {
                return isParent(testParent, (VATCategory) category.getParent());
            }
        }
        return false;
    }

    /**
     * Initializes the editor. If an existing data set is opened, the local
     * variable "vat" is set to this data set. If the editor is opened to create
     * a new one, a new data set is created and the local variable "vat" is set
     * to this one.<br />
     * If we get an ID from the opening command we try to open the given
     * {@link VAT}.
     * 
     * @param parent
     *            the parent control
     */
    @PostConstruct
    public void createPartControl(final Composite parent) {
        Long objId = null;
        VAT stdVat = null;
        this.part = (MPart) parent.getData("modelElement");
        this.part.setIconURI(Icon.COMMAND_VAT.getIconURI());
        final String tmpObjId = CallEditor.resolveParam(part, CallEditor.PARAM_OBJ_ID);
        if (StringUtils.isNumeric(tmpObjId)) {
            objId = Long.valueOf(tmpObjId);
            // Set the editor's data set to the editor's input
            editorVat = vatDao.findById(objId);
        }

        // test, if the editor is opened to create a new data set. This is,
        // if there is no input set.
        newVat = (editorVat == null);

        // If new ..
        if (newVat) {
            // Create a new data set
            editorVat = modelFactory.createVAT();
            final String category = (String) part.getTransientData().get(CallEditor.PARAM_CATEGORY);
            if (StringUtils.isNotEmpty(category)) {
                final VATCategory newCat = vatCategoriesDAO.findCategoryByName(category);
                editorVat.setCategory(newCat);
            }

            editorVat.setCode(UNTDID5305.DEFAULT_VALUE);
            //T: VAT Editor: Part Name of a new VAT Entry
            part.setLabel(msg.editorVatHeader);
            getMDirtyablePart().setDirty(true);
        } else {
            // Set the Editor's name to the VAT name.
            part.setLabel(editorVat.getName());
        }

        // Create the top Composite
        top = new Composite(parent, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(top);

        // Add context help reference 
        //		PlatformUI.getWorkbench().getHelpSystem().setHelp(top, ContextHelpConstants.VAT_EDITOR);

        // Large VAT label
        final Label labelTitle = new Label(top, SWT.NONE);
        labelTitle.setText(msg.editorVatTitle);
        GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false).span(2, 1).applyTo(labelTitle);
        makeLargeLabel(labelTitle);

        // Name of the VAT
        final Label labelName = new Label(top, SWT.NONE);
        labelName.setText(msg.commonFieldName);
        labelName.setToolTipText(msg.editorVatNameTooltip);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelName);
        textName = new Text(top, SWT.BORDER);
        textName.setToolTipText(labelName.getToolTipText());
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textName);

        // Category of the VAT
        final Label labelCategory = new Label(top, SWT.NONE);
        labelCategory.setText(msg.commonFieldCategory);
        labelCategory.setToolTipText(msg.editorVatCategoryTooltip);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelCategory);

        comboCategory = new CCombo(top, SWT.BORDER);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(comboCategory);

        // The description
        final Label labelDescription = new Label(top, SWT.NONE);
        labelDescription.setText(msg.commonFieldDescription);
        labelDescription.setToolTipText(msg.editorVatDescriptionTooltip);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelDescription);
        textDescription = new Text(top, SWT.BORDER);
        textDescription.setToolTipText(labelDescription.getToolTipText());
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textDescription);

        // Tax code for einvoice
        final Label labelPaymentCode = new Label(top, SWT.NONE);
        labelPaymentCode.setText(msg.editorVatEinvoiceCode);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelPaymentCode);
        comboTaxCode = new Combo(top, SWT.BORDER | SWT.READ_ONLY);
        GridDataFactory.fillDefaults().grab(true, false).span(1, 1).applyTo(comboTaxCode);

        // The value
        final Label labelValue = new Label(top, SWT.NONE);
        labelValue.setText(msg.commonFieldValue);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelValue);

        textValue = new FormattedText(top, SWT.BORDER | SWT.SINGLE);
        textValue.setFormatter(new PercentFormatter());
        final GridData data = new GridData();
        data.widthHint = 200;
        textValue.getControl().setLayoutData(data);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textValue.getControl());

        if (preferences.getBoolean(Constants.PREFERENCES_CONTACT_USE_SALES_EQUALIZATION_TAX)) {
            // sales equalization tax
            final Label salesEqTaxValue = new Label(top, SWT.NONE);
            salesEqTaxValue.setText(msg.dataTaxSalesequalizationtax);
            GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(salesEqTaxValue);

            textSalesEqTax = new FormattedText(top, SWT.BORDER | SWT.SINGLE);
            textSalesEqTax.setFormatter(new PercentFormatter());
            textSalesEqTax.getControl().setLayoutData(data);
            GridDataFactory.fillDefaults().grab(true, false).applyTo(textSalesEqTax.getControl());
        }

        // Create the composite to make this VAT to the standard VAT. 
        final Label labelStdVat = new Label(top, SWT.NONE);
        labelStdVat.setText(msg.commonLabelDefault);
        labelStdVat.setToolTipText(msg.editorVatNameTooltip);

        stdVat = getDefaultEntry();

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelStdVat);
        //T: VAT Editor: Button description to make this as standard VAT.
        stdComposite = new StdComposite(top, editorVat, stdVat, msg.editorVatDefaultbutton, 1);
        stdComposite.setToolTipText(msg.editorVatDefaultbuttonTooltip);

        // Disable the Standard Button if this is a new VAT;
        // the Button is disabled by default, see Editor.StdComposite
        if (!newVat) {
            stdComposite.stdButton.setEnabled(true);
        }

        oldCat = editorVat.getCategory();

        bindModel();
    }

    private VAT getDefaultEntry() {
        VAT stdVat;
        long stdID = 1L;
        // Get the ID of the standard entity from preferences
        try {
            stdID = defaultValuePrefs.getLong(getDefaultEntryKey());
        } catch (NumberFormatException | NullPointerException e) {
            stdID = 1L;
        } finally {
            stdVat = vatDao.findById(stdID);
        }
        return stdVat;
    }

    @Override
    protected void bindModel() {
        part.getTransientData().put(BIND_MODE_INDICATOR, Boolean.TRUE);

        bindModelValue(editorVat, textName, VAT_.name.getName(), 64);
        fillAndBindCategoryCombo();
        fillAndBindVatCodeCombo();
        bindModelValue(editorVat, textDescription, VAT_.description.getName(), 250);
        bindModelValue(editorVat, textValue, VAT_.taxValue.getName(), 16);
        if (textSalesEqTax != null) {
            bindModelValue(editorVat, textSalesEqTax, VAT_.salesEqualizationTax.getName(), 16);
        }

        part.getTransientData().remove(BIND_MODE_INDICATOR);

    }

    /**
     * creates the combo box for the VAT category
     */
    private void fillAndBindCategoryCombo() {
        // Collect all category strings as a sorted Set
        final TreeSet<VATCategory> categories = new TreeSet<>(new CategoryComparator<>());
        categories.addAll(vatCategoriesDAO.findAll());

        final ComboViewer viewer = new ComboViewer(comboCategory);
        viewer.setContentProvider(new ArrayContentProvider() {
            @Override
            public Object[] getElements(final Object inputElement) {
                return categories.toArray();
            }
        });

        /*
         * FIXME don't temporarily store the category!
         * 
         * Oh no, don't shoot! Let me explain this. If you create
         * a new VAT with a new VatCategory, save it. Now, the category
         * disappears, because
         * it's removed by BeanObserver trigger. You can't stop it (e.g., by
         * removing the
         * Binding, see above). But if you don't update the combo list, any
         * newly created entry is not
         * shown in the list. Therefore we store the category temporarily and
         * add it to the editor
         * after filling the combo box. Ugly, but it works.
         * 
         * If anyone has a better idea how to prevent the data binding from
         * removing
         * the category value let me know.
         */
        final VATCategory tmpKat = editorVat.getCategory();
        //        
        //        // Add all categories to the combo
        viewer.setInput(categories);
        viewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(final Object element) {
                return element instanceof VATCategory ? CommonConverter.getCategoryName((VATCategory) element, "") : null;
            }
        });
        // restore old category
        editorVat.setCategory(tmpKat);

        final UpdateValueStrategy<VATCategory, String> vatCatModel2Target = UpdateValueStrategy.create(new CategoryConverter<>(VATCategory.class));
        final UpdateValueStrategy<String, VATCategory> target2VatcatModel = UpdateValueStrategy
                .create(new StringToCategoryConverter<>(categories, VATCategory.class));
        bindModelValue(editorVat, comboCategory, VAT_.category.getName(), target2VatcatModel, vatCatModel2Target);
    }

    /**
     * creates the combo box for the payment code
     */
    private void fillAndBindVatCodeCombo() {
        final ComboViewer viewer = new ComboViewer(comboTaxCode);
        viewer.setContentProvider(ArrayContentProvider.getInstance());
        final String code = editorVat.getCode().getCode();
        final LinkedHashMap<String, String> values = Arrays.stream(UNTDID5305.values())
                .collect(Collectors.toMap(UNTDID5305::getCode, e -> msg.getMessageFromKey(e.getTranslationKey()), (existing, replacement) -> existing, // handle duplicates
                        LinkedHashMap::new));

        viewer.setInput(values.entrySet());
        final String current = code == null ? UNTDID5305.DEFAULT_VALUE.getCode() : code;
        final Entry<String, String> selection = values.entrySet().stream().filter(e -> current.equalsIgnoreCase(e.getKey())).findFirst().orElseThrow();
        viewer.setSelection(new StructuredSelection(selection));

        viewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(final Object element) {
                if (element instanceof final Entry<?, ?> entry) {
                    return (String) entry.getValue();
                }
                // Customize this method to return the desired text for each element
                return super.getText(element);
            }
        });

        final UpdateValueStrategy<String, UNTDID5305> target2PaymentCodeModel = UpdateValueStrategy
                .create(new UNTDID5305TranslationToPaymentCodeConverter(msg));
        final UpdateValueStrategy<UNTDID5305, String> paymentCodeModel2Target = UpdateValueStrategy
                .create(new PaymentCodeToUNTDID5305TranslationConverter(msg));
        bindModelValue(editorVat, comboTaxCode, VAT_.code.getName(), target2PaymentCodeModel, paymentCodeModel2Target);

    }

    /**
     * If an entity is deleted via list view we have to close a possibly open
     * editor window. Since this is triggered by a UIEvent we named this method
     * "handle*".
     */
    @Inject
    @Optional
    public void handleForceClose(@UIEventTopic(VatEditor.EDITOR_ID + "/forceClose") final Event event) {
        //      sync.syncExec(() -> top.setRedraw(false));
        // the event has already all given params in it since we created them as Map
        final String targetDocumentName = (String) event.getProperty(DocumentEditor.DOCUMENT_ID);
        // at first we have to check if the message is for us
        if (!StringUtils.equals(targetDocumentName, editorVat.getName())) {
            // if not, silently ignore this event
            return;
        }
        partService.hidePart(part, true);
        //  sync.syncExec(() -> top.setRedraw(true));
    }

    @Inject
    @Optional
    public void handleChangeDefaultEntry(@UIEventTopic(VatEditor.EDITOR_ID) final Event event) {
        final String selector = (String) event.getProperty(IEventBroker.DATA);
        switch (selector) {
            case "update/defaultvalue":
                final Collection<MPart> parts = partService.getParts();
                for (final MPart mPart : parts) {
                    if (ID.contentEquals(mPart.getElementId()) && mPart.getObject() != null) {
                        final VatEditor vatEditor = (VatEditor) mPart.getObject();
                        if (vatEditor != this) {
                            final VAT defaultVat = getDefaultEntry();
                            vatEditor.stdComposite.setStdText(defaultVat);
                        }
                    }
                }
                break;
            case "forceClose":
                // tbd.
                break;
            default:
                break;
        }
    }

    @Override
    protected String getDefaultEntryKey() {
        return Constants.DEFAULT_VAT;
    }

    @Override
    protected MDirtyable getMDirtyablePart() {
        return part;
    }

    @Override
    protected Class<VAT> getModelClass() {
        return VAT.class;
    }

    @Override
    protected String getEditorID() {
        return EDITOR_ID;
    }
}
