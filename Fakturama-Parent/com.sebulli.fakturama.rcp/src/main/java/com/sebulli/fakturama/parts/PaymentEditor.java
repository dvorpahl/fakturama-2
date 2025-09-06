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

import java.awt.MouseInfo;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.formattedtext.IntegerFormatter;
import org.eclipse.nebula.widgets.formattedtext.PercentFormatter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

import com.sebulli.fakturama.converter.CommonConverter;
import com.sebulli.fakturama.dao.PaymentsDAO;
import com.sebulli.fakturama.dao.VoucherCategoriesDAO;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.handlers.CallEditor;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.UNTDID4461;
import com.sebulli.fakturama.model.CategoryComparator;
import com.sebulli.fakturama.model.FakturamaModelPackage;
import com.sebulli.fakturama.model.Payment;
import com.sebulli.fakturama.model.Payment_;
import com.sebulli.fakturama.model.VoucherCategory;
import com.sebulli.fakturama.parts.converter.CategoryConverter;
import com.sebulli.fakturama.parts.converter.PaymentCodeToUNTDID4461TranslationConverter;
import com.sebulli.fakturama.parts.converter.StringToCategoryConverter;
import com.sebulli.fakturama.parts.converter.UNTDID4461TranslationToPaymentCodeConverter;
import com.sebulli.fakturama.resources.core.Icon;

/**
 * The payment editor
 * 
 */
public class PaymentEditor extends Editor<Payment> {

    // Editor's ID
    public static final String EDITOR_ID = "PaymentEditor";

    //    private EventList<Payment> paymentListData;
    //    private EventList<Account> categories;

    @Inject
    private PaymentsDAO paymentsDAO;

    @Inject
    private VoucherCategoriesDAO accountDAO;

    public static final String ID = "com.sebulli.fakturama.editors.paymentEditor";

    // This UniDataSet represents the editor's input 
    private Payment payment;

    // SWT widgets of the editor
    private Composite top;
    private Text textName;
    private Text textDescription;
    private FormattedText textDiscountValue;
    private FormattedText textDiscountDays;
    private FormattedText textNetDays;
    private Text textPaid;
    private Text textDepositPaid;
    private Text textUnpaid;
    private CCombo comboCategory;

    private Combo comboPaymentCode;
    private final List<UNTDID4461> usedCodes = Arrays.stream(UNTDID4461.values()).filter(UNTDID4461::getUsed).toList();

    // defines if the payment is newly created
    private boolean newPayment;
    private MPart part;

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

        // Always set the editor's data set to "undeleted"
        payment.setDeleted(Boolean.FALSE);

        // Set the payment data
        // ... done through databinding...

        // save the new or updated payment
        try {
            // at first, check the category for a new entry
            // (the user could have written a new one into the combo field)
            final String testCat = comboCategory.getText();
            // if there's no category we can skip this step
            if (StringUtils.isNotBlank(testCat)) {
                final VoucherCategory parentCategory = accountDAO.getOrCreateCategory(testCat, true);
                // parentCategory now has the last found Category
                payment.setCategory(parentCategory);
            }

            // we have to truncate the shipping value (because of calculations between gross and net)
            final MathContext mc = new MathContext(16, RoundingMode.HALF_UP);
            final BigDecimal val = BigDecimal.valueOf(payment.getDiscountValue()).round(mc).setScale(5, RoundingMode.HALF_UP);
            payment.setDiscountValue(val.doubleValue());
            payment = paymentsDAO.update(payment);
        } catch (final FakturamaStoringException e) {
            log.error(e, "can't save the current Payment: " + payment.toString());
            return Boolean.FALSE;
        }

        // If it is a new payment, add it to the payment list and
        // to the data base
        if (newPayment) {
            newPayment = false;
            final String category = (String) part.getTransientData().get(CallEditor.PARAM_CATEGORY);
            if (StringUtils.isNotEmpty(category)) {
                final VoucherCategory newCat = accountDAO.findCategoryByName(category);
                payment.setCategory(newCat);
            }
            stdComposite.stdButton.setEnabled(true);
        }

        // Set the Editor's name to the payment name...
        part.setLabel(payment.getName());

        // ...and "mark" it with current objectId (though it can be find by 
        // CallEditor if one tries to open it immediately from list view)
        part.getTransientData().put(CallEditor.PARAM_OBJ_ID, Long.toString(payment.getId()));

        // Refresh the table view of all payments
        evtBroker.post(EDITOR_ID, Editor.UPDATE_EVENT);

        bindModel();

        // reset dirty flag
        getMDirtyablePart().setDirty(false);
        return Boolean.TRUE;
    }

    /**
     * Initializes the editor. If an existing data set is opened, the local
     * variable "payment" is set to this data set. If the editor is opened to
     * create a new one, a new data set is created and the local variable
     * "payment" is set to this one.
     * 
     * @param parent
     *            the parent control
     */
    @PostConstruct
    public void createPartControl(final Composite parent) {
        Long objId = null;
        Payment stdPayment = null;
        long stdID = 1L;
        this.part = (MPart) parent.getData("modelElement");
        this.part.setIconURI(Icon.COMMAND_PAYMENT.getIconURI());
        final String tmpObjId = (String) part.getTransientData().get(CallEditor.PARAM_OBJ_ID);
        if (StringUtils.isNumeric(tmpObjId)) {
            objId = Long.valueOf(tmpObjId);
            // Set the editor's data set to the editor's input
            payment = paymentsDAO.findById(objId, true);
        }

        // test, if the editor is opened to create a new data set. This is,
        // if there is no input set.
        newPayment = (payment == null);

        // If new ..
        if (newPayment) {

            // Create a new data set
            payment = FakturamaModelPackage.MODELFACTORY.createPayment();
            //			payment.setCode(Constants.TAX_DEFAULT_CODE);
            final String category = (String) part.getTransientData().get(CallEditor.PARAM_CATEGORY);
            if (StringUtils.isNotEmpty(category)) {
                final VoucherCategory newCat = accountDAO.findCategoryByName(category);
                payment.setCategory(newCat);
            }

            //T: Payment Editor: Part Name of a new payment
            part.setLabel(msg.mainMenuNewPayment);
            getMDirtyablePart().setDirty(true);
        } else {

            // Set the Editor's name to the payment name.
            part.setLabel(payment.getName());
        }

        // Create the top Composite
        top = new Composite(parent, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(3).applyTo(top);

        // Add context help reference 
        //		PlatformUI.getWorkbench().getHelpSystem().setHelp(top, ContextHelpConstants.PAYMENT_EDITOR);

        // Large payment label
        final Label labelTitle = new Label(top, SWT.NONE);
        //T: Payment Editor: Title
        labelTitle.setText(msg.editorContactFieldPaymentName);
        GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false).span(3, 1).applyTo(labelTitle);
        makeLargeLabel(labelTitle);

        // Payment name
        final Label labelName = new Label(top, SWT.NONE);
        labelName.setText(msg.commonFieldName);
        labelName.setToolTipText(msg.editorPaymentNameTooltip);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelName);
        textName = new Text(top, SWT.BORDER);
        //		textName.setText(StringUtils.defaultString(payment.getName()));
        textName.setToolTipText(labelName.getToolTipText());
        GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(textName);

        // Payment category
        final Label labelCategory = new Label(top, SWT.NONE);
        //T: Payment Editor - category
        labelCategory.setText(msg.commonFieldAccount);
        labelCategory.setToolTipText(msg.editorPaymentAccountTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelCategory);

        // Collect all category strings
        comboCategory = new CCombo(top, SWT.BORDER);
        GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(comboCategory);

        // Payment description
        final Label labelDescription = new Label(top, SWT.NONE);
        labelDescription.setText(msg.commonFieldDescription);
        labelDescription.setToolTipText(msg.editorVatDescriptionTooltip);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelDescription);
        textDescription = new Text(top, SWT.BORDER);
        textDescription.setToolTipText(labelDescription.getToolTipText());
        GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(textDescription);

        // payment code for einvoice
        final Label labelPaymentCode = new Label(top, SWT.NONE);
        labelPaymentCode.setText(msg.editorPaymentPaymentcode);
        comboPaymentCode = new Combo(top, SWT.BORDER | SWT.READ_ONLY);
        GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(comboPaymentCode);

        // Payment discount value
        final Label labelDiscountValue = new Label(top, SWT.NONE);
        labelDiscountValue.setText(msg.editorPaymentDiscount);
        labelDiscountValue.setToolTipText(msg.editorPaymentDiscountTooltip);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelDiscountValue);
        textDiscountValue = new FormattedText(top, SWT.BORDER | SWT.SINGLE);
        textDiscountValue.setFormatter(new PercentFormatter());
        textDiscountValue.getControl().setToolTipText(labelDiscountValue.getToolTipText());
        GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(textDiscountValue.getControl());

        // Payment days to pay the discount
        final Label labelDiscountDays = new Label(top, SWT.NONE);
        //T: Label in the payment editor
        labelDiscountDays.setText(msg.editorPaymentDiscountDays);
        labelDiscountDays.setToolTipText(msg.editorPaymentDiscountDaysTooltip);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelDiscountDays);
        textDiscountDays = new FormattedText(top, SWT.BORDER | SWT.SINGLE);
        textDiscountDays.setFormatter(new IntegerFormatter());
        textDiscountDays.getControl().setToolTipText(labelDiscountDays.getToolTipText());
        GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(textDiscountDays.getControl());

        // Payment days to pay the net value
        final Label labelNetDays = new Label(top, SWT.NONE);
        //T: Label in the payment editor
        labelNetDays.setText(msg.commonFieldNetDays);
        labelNetDays.setToolTipText(msg.editorPaymentNetdaysTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelNetDays);
        textNetDays = new FormattedText(top, SWT.BORDER | SWT.SINGLE);
        textNetDays.setFormatter(new IntegerFormatter());
        textNetDays.getControl().setToolTipText(labelNetDays.getToolTipText());
        GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(textNetDays.getControl());

        //T: Label in the payment editor
        final String[] possiblePlaceholders = new String[] { "BANK.BIC", "BANK.IBAN", "BANK.IBAN.CENSORED", "BANK.NAME", "DEBITOR.BANK.ACCOUNT.HOLDER",
                "DEBITOR.BANK.BIC", "DEBITOR.BANK.IBAN", "DEBITOR.BANK.IBAN.CENSORED", "DEBITOR.BANK.NAME", "DEBITOR.MANDATREF", "DOCUMENT.TOTAL", "DUE.DATE",
                "DUE.DAYS", "DUE.DISCOUNT.DATE", "DUE.DISCOUNT.DAYS", "DUE.DISCOUNT.PERCENT", "DUE.DISCOUNT.VALUE", "PAID.DATE", "PAID.VALUE",
                "YOURCOMPANY.CREDITORID" };

        // Label for the "unpaid" text message
        final Label labelUnpaid = new Label(top, SWT.NONE);
        //T: Payment Editor: Label for the text unpaid
        labelUnpaid.setText(msg.editorPaymentUnpaidName);
        labelUnpaid.setToolTipText(msg.editorPaymentUnpaidTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelUnpaid);

        // Create text field for "unpaid" text message
        textUnpaid = new Text(top, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        textUnpaid.setToolTipText(labelUnpaid.getToolTipText());
        GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 200).grab(true, true).applyTo(textUnpaid);

        final Button placeholderUnpaidBtn = new Button(top, SWT.BORDER | SWT.ARROW | SWT.DOWN);
        placeholderUnpaidBtn.setToolTipText(msg.editorPaymentPlaceholderInfo);
        placeholderUnpaidBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                final Menu popupMenu = new Menu(placeholderUnpaidBtn);

                Arrays.stream(possiblePlaceholders).forEach(entry -> addPlaceholderPopupItem(popupMenu, textUnpaid, entry));

                final java.awt.Point location = MouseInfo.getPointerInfo().getLocation();
                popupMenu.setLocation(location.x - 100, location.y);
                popupMenu.setVisible(true);
            }
        });
        GridDataFactory.fillDefaults().hint(35, SWT.DEFAULT).align(SWT.BEGINNING, SWT.TOP).applyTo(placeholderUnpaidBtn);

        // Label for the "depositpaid" text message
        final Label labelDepositPaid = new Label(top, SWT.NONE);
        //T: Payment Editor: Label for the text paid
        labelDepositPaid.setText(msg.editorPaymentDepositName);
        labelDepositPaid.setToolTipText(msg.editorPaymentDepositTooltip);
        GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).applyTo(labelDepositPaid);

        // Create text field for the "depositpaid" text message
        textDepositPaid = new Text(top, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        textDepositPaid.setToolTipText(labelDepositPaid.getToolTipText());
        GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 200).grab(true, true).applyTo(textDepositPaid);

        final Button placeholderDepositBtn = new Button(top, SWT.BORDER | SWT.ARROW | SWT.DOWN);
        placeholderDepositBtn.setToolTipText(msg.editorPaymentPlaceholderInfo);
        placeholderDepositBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                final Menu popupMenu = new Menu(placeholderDepositBtn);

                Arrays.stream(possiblePlaceholders).forEach(entry -> addPlaceholderPopupItem(popupMenu, textDepositPaid, entry));

                final java.awt.Point location = MouseInfo.getPointerInfo().getLocation();
                popupMenu.setLocation(location.x - 100, location.y);
                popupMenu.setVisible(true);
            }
        });
        GridDataFactory.fillDefaults().hint(35, SWT.DEFAULT).align(SWT.BEGINNING, SWT.TOP).applyTo(placeholderDepositBtn);

        // Label for the "paid" text message
        final Label labelPaid = new Label(top, SWT.NONE);
        //T: Payment Editor: Label for the text paid
        labelPaid.setText(msg.editorPaymentPaidName);
        labelPaid.setToolTipText(msg.editorPaymentPaidTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelPaid);

        // Create text field for the "paid" text message
        textPaid = new Text(top, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        textPaid.setToolTipText(labelPaid.getToolTipText());
        GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 200).grab(true, true).applyTo(textPaid);

        final Button placeholderPaidBtn = new Button(top, SWT.BORDER | SWT.ARROW | SWT.DOWN);
        placeholderPaidBtn.setToolTipText(msg.editorPaymentPlaceholderInfo);
        placeholderPaidBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                final Menu popupMenu = new Menu(placeholderPaidBtn);

                Arrays.stream(possiblePlaceholders).forEach(entry -> addPlaceholderPopupItem(popupMenu, textPaid, entry));

                final java.awt.Point location = MouseInfo.getPointerInfo().getLocation();
                popupMenu.setLocation(location.x - 100, location.y);
                popupMenu.setVisible(true);
            }
        });
        GridDataFactory.fillDefaults().hint(35, SWT.DEFAULT).align(SWT.BEGINNING, SWT.TOP).applyTo(placeholderPaidBtn);

        // Create the composite to make this payment to the standard payment. 
        final Label labelStd = new Label(top, SWT.NONE);
        //T: Label in the payment editor
        labelStd.setText(msg.commonLabelDefault);
        labelStd.setToolTipText(msg.editorPaymentDefaultTooltip);

        // Get the ID of the standard entity from preferences
        try {
            stdID = defaultValuePrefs.getLong(getDefaultEntryKey());
            stdPayment = paymentsDAO.findById(stdID);
        } catch (NumberFormatException | NullPointerException e) {
            stdID = 1L;
            stdPayment = paymentsDAO.findById(stdID);
        }

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelStd);
        //T: Payment Editor: Button description to make this as standard payment.
        stdComposite = new StdComposite(top, payment, stdPayment, msg.editorPaymentDefaultButtonName, 2);
        stdComposite.setToolTipText(msg.editorPaymentDefaultButtonHint);

        // disable the Standard Button, if this is a new payment
        if (!newPayment) {
            stdComposite.stdButton.setEnabled(true);
        }

        bindModel();
    }

    @Override
    protected void bindModel() {
        part.getTransientData().put(BIND_MODE_INDICATOR, Boolean.TRUE);

        bindModelValue(payment, textName, Payment_.name.getName(), 255);
        fillAndBindCategoryCombo();
        fillAndBindPaymentCodeCombo();
        bindModelValue(payment, textDescription, Payment_.description.getName(), 255);
        bindModelValue(payment, textDiscountValue, Payment_.discountValue.getName(), 12);
        bindModelValue(payment, textDiscountDays, Payment_.discountDays.getName(), 8);
        bindModelValue(payment, textNetDays, Payment_.netDays.getName(), 8);
        bindModelValue(payment, textPaid, Payment_.paidText.getName(), 1024);
        bindModelValue(payment, textDepositPaid, Payment_.depositText.getName(), 255);
        bindModelValue(payment, textUnpaid, Payment_.unpaidText.getName(), 1024);

        part.getTransientData().remove(BIND_MODE_INDICATOR);
    }

    /**
     * creates the combo box for the payment code
     */
    private void fillAndBindPaymentCodeCombo() {
        final ComboViewer viewer = new ComboViewer(comboPaymentCode);
        viewer.setContentProvider(ArrayContentProvider.getInstance());
        final String code = payment.getCode();
        final LinkedHashMap<String, String> values = usedCodes.stream()
                .collect(Collectors.toMap(UNTDID4461::getCode, e -> msg.getMessageFromKey(e.getTranslationKey()), (existing, replacement) -> existing, // handle duplicates
                        LinkedHashMap::new));

        viewer.setInput(values.entrySet());
        final String current = code == null ? UNTDID4461.DEFAULT_VALUE.getCode() : code;
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

        final UpdateValueStrategy<String, String> target2PaymentCodeModel = UpdateValueStrategy.create(new UNTDID4461TranslationToPaymentCodeConverter(msg));
        final UpdateValueStrategy<String, String> paymentCodeModel2Target = UpdateValueStrategy.create(new PaymentCodeToUNTDID4461TranslationConverter(msg));
        bindModelValue(payment, comboPaymentCode, Payment_.code.getName(), target2PaymentCodeModel, paymentCodeModel2Target);

    }

    /**
     * creates the combo box for the payment account
     */
    private void fillAndBindCategoryCombo() {
        // Collect all category strings as a sorted Set
        final TreeSet<VoucherCategory> categories = new TreeSet<>(new CategoryComparator<>());
        categories.addAll(accountDAO.findAll());

        final ComboViewer viewer = new ComboViewer(comboCategory);
        viewer.setContentProvider(new ArrayContentProvider() {
            @Override
            public Object[] getElements(final Object inputElement) {
                return categories.toArray();
            }
        });

        final VoucherCategory tmpCategory = payment.getCategory();
        // Add all categories to the combo
        viewer.setInput(categories);
        viewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(final Object element) {
                return element instanceof VoucherCategory ? CommonConverter.getCategoryName((VoucherCategory) element, "") : null;
            }
        });
        payment.setCategory(tmpCategory);

        final UpdateValueStrategy<VoucherCategory, String> paymentCatModel2Target = UpdateValueStrategy.create(new CategoryConverter<>(VoucherCategory.class));
        final UpdateValueStrategy<String, VoucherCategory> target2PaymentCatModel = UpdateValueStrategy
                .create(new StringToCategoryConverter<>(categories, VoucherCategory.class));
        bindModelValue(payment, comboCategory, Payment_.category.getName(), target2PaymentCatModel, paymentCatModel2Target);
    }

    @Override
    protected String getDefaultEntryKey() {
        return Constants.DEFAULT_PAYMENT;
    }

    @Override
    protected MDirtyable getMDirtyablePart() {
        return part;
    }

    @Override
    protected String getEditorID() {
        return EDITOR_ID;
    }

    @Override
    protected Class<Payment> getModelClass() {
        return Payment.class;
    }

    /**
     * Adds a new {@link MenuItem} to the popup menu for the placeholder menu.
     * 
     * @param popupMenu
     * @param textPaid
     * @param placeholderText
     */
    private void addPlaceholderPopupItem(final Menu popupMenu, final Text textPaid, final String placeholderText) {
        final MenuItem newItem = new MenuItem(popupMenu, SWT.NONE);
        newItem.setText(placeholderText);
        newItem.addSelectionListener(new PlaceholderItemSelection(textPaid, placeholderText));
    }

    /**
     * Convenience class for a selection handler for placeholders.
     * 
     */
    private static class PlaceholderItemSelection extends SelectionAdapter {
        private final Text targetWidget;
        private final String placeholderString;

        /**
         * @param targetWidget
         * @param placeholderString
         */
        public PlaceholderItemSelection(final Text targetWidget, final String placeholderString) {
            this.targetWidget = targetWidget;
            this.placeholderString = placeholderString;
        }

        @Override
        public void widgetSelected(final SelectionEvent e) {
            // Insert the selected text in the message text (selected widget is set in the
            // calling method)
            final int begin = targetWidget.getSelection().x;
            final int end = targetWidget.getSelection().y;
            final String s = targetWidget.getText();
            final String s1 = s.substring(0, begin);
            final String s2 = placeholderString;

            targetWidget.setText(String.format("%s <%s> %s", s1, s2, s.substring(end, s.length())));
            targetWidget.setSelection(s1.length() + s2.length());
        }
    }
}
