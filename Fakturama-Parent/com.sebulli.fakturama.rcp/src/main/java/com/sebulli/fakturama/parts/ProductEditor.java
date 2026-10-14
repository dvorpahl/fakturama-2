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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.money.MonetaryAmount;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.text.NumberToStringConverter;
import org.eclipse.core.databinding.conversion.text.StringToNumberConverter;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.cdatetime.CDT;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.nebula.widgets.formattedtext.DoubleFormatter;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Text;
import org.javamoney.moneta.Money;
import org.osgi.service.event.Event;

import com.sebulli.fakturama.converter.CommonConverter;
import com.sebulli.fakturama.dao.ProductCategoriesDAO;
import com.sebulli.fakturama.dao.ProductWebshopDAO;
import com.sebulli.fakturama.dao.ProductsDAO;
import com.sebulli.fakturama.dao.VatsDAO;
import com.sebulli.fakturama.dialogs.SelectProductDialog;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.handlers.CallEditor;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.model.CategoryComparator;
import com.sebulli.fakturama.model.ObjectDuplicator;
import com.sebulli.fakturama.model.Product;
import com.sebulli.fakturama.model.ProductCategory;
import com.sebulli.fakturama.model.ProductWebshop;
import com.sebulli.fakturama.model.ProductWebshop_;
import com.sebulli.fakturama.model.Product_;
import com.sebulli.fakturama.model.VAT;
import com.sebulli.fakturama.parts.converter.CategoryConverter;
import com.sebulli.fakturama.parts.converter.EntityConverter;
import com.sebulli.fakturama.parts.converter.StringToCategoryConverter;
import com.sebulli.fakturama.parts.converter.StringToEntityConverter;
import com.sebulli.fakturama.parts.widget.FakturamaPictureControl;
import com.sebulli.fakturama.parts.widget.GrossText;
import com.sebulli.fakturama.parts.widget.NetText;
import com.sebulli.fakturama.parts.widget.contentprovider.EntityComboProvider;
import com.sebulli.fakturama.parts.widget.formatter.DoubleValueFormatter;
import com.sebulli.fakturama.parts.widget.formatter.MoneyFormatter;
import com.sebulli.fakturama.parts.widget.labelprovider.EntityLabelProvider;
import com.sebulli.fakturama.resources.ITemplateResourceManager;
import com.sebulli.fakturama.resources.core.Icon;
import com.sebulli.fakturama.resources.core.ProgramImages;

import jakarta.persistence.metamodel.SingularAttribute;

/**
 * The product editor
 */
public class ProductEditor extends Editor<Product> {

    /** The number of prices. */
    private static final int MAX_NUMBER_OF_PRICES = 5;

    @Inject
    protected VatsDAO vatDao;

    @Inject
    protected ProductCategoriesDAO productCategoriesDAO;

    @Inject
    private ITemplateResourceManager resourceManager;

    @Inject
    private EPartService partService;

    // Editor's ID
    public static final String ID = "com.sebulli.fakturama.editors.productEditor";

    public static final String EDITOR_ID = "ProductEditor";

    // This UniDataSet represents the editor's input 
    private Product editorProduct;

    private MPart part;

    @Inject
    private ProductsDAO productsDAO;

    @Inject
    private ProductWebshopDAO productWebshopDAO;

    @Inject
    protected IEclipseContext context;

    @Inject
    private IDialogSettings settings;

    // SWT widgets of the editor
    private Composite top;
    private Text textItemNr;
    private Text textName;
    private Text textGtin, textSupplierItemNumber, textReplacedByProductId;
    private Text textDescription;
    private Text udf01, udf02, udf03;
    private Combo comboVat;
    private FormattedText textWeight;
    private FormattedText textQuantity;
    private Button checkboxStockManaged;
    private FormattedText costPrice;
    private Text textQuantityUnit;
    private CCombo comboCategory;
    private ProductCategory oldCat;
    private FakturamaPictureControl labelProductPicture;
    private Label labelPictureVariants;
    private Composite photoComposite;
    private boolean refreshingPicture;
    private Text note;

    // Webshop overlay (FKT_PRODUCTWEBSHOP) - the "im Shop" checkbox and its fields
    private ProductWebshop editorProductWebshop;
    // whether a persisted, non-deleted ProductWebshop row existed for this product when the
    // editor was opened - needed at save time to tell "never had shop data, still doesn't" (skip
    // saving anything) apart from "had shop data, user just unchecked it" (soft-delete the row)
    private boolean productHadWebshopData;
    private Button checkboxInShop;
    private FormattedText textShopPrice;
    private Text textShopStockQuantity, textShopLowStockAmount, textDeliveryTime;
    private Combo comboShopStockStatus, comboShopBackorders;

    // Widgets (and variables) for the scaled price.
    private Label[] labelBlock = new Label[MAX_NUMBER_OF_PRICES];
    private Text[] textBlock = new Text[MAX_NUMBER_OF_PRICES];
    private NetText[] netText = new NetText[MAX_NUMBER_OF_PRICES];
    private GrossText[] grossText = new GrossText[MAX_NUMBER_OF_PRICES];
    private MonetaryAmount[] net;
    private MonetaryAmount defaultPrice = Money.of(Double.valueOf(0.0), DataUtils.getInstance().getDefaultCurrencyUnit());
    private int scaledPrices;

    // These flags are set by the preference settings.
    // They define if elements of the editor are displayed or not.
    private boolean useWeight;
    private boolean useQuantity;
    private boolean useQuantityUnit;
    private boolean useItemNr;
    private boolean useNet;
    private boolean useGross;
    private boolean useVat;
    private boolean useDescription;
    private boolean usePicture;

    // These are (non visible) values of the document
    //    private VAT vat = null;
    private Display display;

    // defines, if the product is new created
    private boolean newProduct;

    private Map<Integer, PriceBlock> priceBlocks;

    /**
     * Saves the contents of this part
     * 
     * @param monitor
     *            Progress monitor
     */
    @Persist
    public Boolean doSave(final IProgressMonitor monitor) {

        final String replacementError = replacementValidationError(textReplacedByProductId.getText());
        if (replacementError != null) {
            MessageDialog.openError(top.getShell(), "Nachfolgeartikel", replacementError);
            textReplacedByProductId.setFocus();
            return Boolean.FALSE;
        }

        /*
         * the following parameters are not saved:
         * - id (constant) 
         * - options (not yet implemented)
         * - date_added (not modified by editor)
         */

        // at first, check the category for a new entry
        // (the user could have written a new one into the combo field)
        String testCat = comboCategory.getText();
        // if there's no category we can skip this step
        if (StringUtils.isNotBlank(testCat)) {
            ProductCategory productCategory = productCategoriesDAO.getCategory(testCat, true);
            // parentCategory now has the last found Category
            editorProduct.setCategories(productCategory);
        }

        if (newProduct) {
            // Check, if the item number is the next one
            int result = getNumberGenerator().setNextFreeNumberInPrefStore(textItemNr.getText(), getEditorID());
            if (result == ERROR_NOT_NEXT_ID) {
                // It's not the next free ID
                // Display an error message
                MessageDialog.openError(top.getShell(),

                        //T: Title of the dialog that appears if the item/product number is not valid.
                        msg.editorProductErrorItemnumberTitle,

                        //T: Text of the dialog that appears if the item/product number is not valid.
                        MessageFormat.format(msg.editorProductErrorItemnumberNotnextfree, textItemNr.getText()) + "\n" +
                        //T: Text of the dialog that appears if the number is not valid.
                                msg.editorContactHintSeepreferences);
            }

        }

        // Always set the editor's data set to "undeleted"
        editorProduct.setDeleted(Boolean.FALSE);

        // Set the product data
        // ... done through databinding...

        try {
            int i;
            double lastScaledPrice = 0.0;

            // fill all remaining prices with last scaled price
            for (i = 0; i < scaledPrices; i++) {
                // at first look for the highest scaled price...
                 String methodName = String.format("getPrice%d", i + 1);
                 Object obj = MethodUtils.invokeExactMethod(editorProduct, methodName);
                 lastScaledPrice = (Double) obj;
            }

            // if not all 5 scales are set we set the remaining prices to the last scaled price
            for (; i < 5; i++) {
                String methodName = String.format("setPrice%d", i + 1);
                MethodUtils.invokeExactMethod(editorProduct, methodName, lastScaledPrice);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return Boolean.FALSE;
        }

        // Set the product data
        // ... done through databinding...

        // If it is a new product, add it to the product list and
        // to the data base
        if (newProduct) {
            newProduct = false;

        }
        // If it's not new, update at least the data base
        else {
            //			Data.INSTANCE.getProducts().updateDataSet(product);
        }

        try {
            editorProduct = productsDAO.save(editorProduct);

            // check if we can delete the old category (if it's empty)
            if (oldCat != null && oldCat != editorProduct.getCategories() && !isParent(oldCat, editorProduct.getCategories())) {
                long countOfEntriesInCategory = productsDAO.countByCategory(oldCat);
                if (countOfEntriesInCategory == 0) {
                    productCategoriesDAO.deleteEmptyCategory(oldCat);
                }
            }

            oldCat = editorProduct.getCategories();
        } catch (FakturamaStoringException e) {
            log.error(e);
            return Boolean.FALSE;
        }

        // Webshop overlay: only persist something if it's currently checked, or a row already
        // existed and now needs to be soft-deleted (user unchecked it) - most products never get
        // a FKT_PRODUCTWEBSHOP row at all, and this avoids creating an empty "deleted" one for them.
        final boolean inShop = checkboxInShop.getSelection();
        if (inShop || productHadWebshopData) {
            try {
                editorProductWebshop.setProduct(editorProduct);
                editorProductWebshop.setDeleted(!inShop);
                editorProductWebshop = productWebshopDAO.save(editorProductWebshop);
                productHadWebshopData = inShop;
            } catch (FakturamaStoringException e) {
                log.error(e);
                return Boolean.FALSE;
            }
        }

        // Set the Editor's name to the product name...
        this.part.setLabel(editorProduct.getName());

        // ...and "mark" it with current objectId (though it can be find by 
        // CallEditor if one tries to open it immediately from list view)
        part.getTransientData().put(CallEditor.PARAM_OBJ_ID, Long.toString(editorProduct.getId()));

        bindModel();

        // Refresh the table view of all contacts
        evtBroker.post(EDITOR_ID, Editor.UPDATE_EVENT);

        // reset dirty flag
        getMDirtyablePart().setDirty(false);
        return Boolean.TRUE;
    }

    private boolean isParent(final ProductCategory testParent, final ProductCategory category) {
        if (testParent != null && category != null && category.getParent() != null) {
            if (category.getParent() == testParent) {
                return true;
            } else {
                return isParent(testParent, (ProductCategory) category.getParent());
            }
        }
        return false;
    }

    /**
     * Initializes the editor. If an existing data set is opened, the local
     * variable "product" is set to This data set. If the editor is opened to
     * create a new one, a new data set is created and the local variable
     * "product" is set to this one.
     * 
     * @param input
     *            The editor's input
     * @param site
     *            The editor's site
     */
    @PostConstruct
    public void init(final Composite parent) {
        this.part = (MPart) parent.getData("modelElement");
        this.part.setIconURI(Icon.COMMAND_PRODUCT.getIconURI());

        String tmpObjId = CallEditor.resolveParam(part, CallEditor.PARAM_OBJ_ID);
        if (StringUtils.isNumeric(tmpObjId)) {
            Long objId = Long.valueOf(tmpObjId);

            // Set the editor's data set to the editor's input
            this.editorProduct = productsDAO.findById(objId);

            // if a copy should be created, create one and take the objId as a "template"
            if (BooleanUtils.toBoolean((String) part.getTransientData().get(CallEditor.PARAM_COPY))) {
                // clone the product and use it as new one
                editorProduct = new ObjectDuplicator().duplicateProduct(editorProduct);
                editorProduct.setItemNumber(getNumberGenerator().getNextNr(getEditorID()));
                getMDirtyablePart().setDirty(true);
            }
        }

        // initialize prices
        net = new MonetaryAmount[MAX_NUMBER_OF_PRICES];
        Arrays.fill(net, defaultPrice);

        // Test if the editor is opened to create a new data set. This is,
        // if there is no input set.
        newProduct = (editorProduct == null);

        // If new ..
        if (newProduct) {

            // Create a new data set
            editorProduct = modelFactory.createProduct();
            String category = (String) part.getTransientData().get(CallEditor.PARAM_CATEGORY);
            if (StringUtils.isNotEmpty(category)) {
                ProductCategory newCat = productCategoriesDAO.getCategory(category, false);
                editorProduct.setCategories(newCat);
            }

            //T: Header of product editor
            part.setLabel(msg.commandNewProductName);

            // Set the vat to the standard value
            long vatId = defaultValuePrefs.getLong(Constants.DEFAULT_VAT);
            VAT vat = vatDao.findById(vatId); // initially set default VAT
            if (vat.getTaxValue() == null) {
                MessageDialog.openWarning(parent.getShell(), msg.dialogMessageboxTitleWarning,
                        "No Tax value for VAT defined, please update your VAT settings!");
                vat.setTaxValue(Double.valueOf(0));
            }
            editorProduct.setVat(vat);

            // Get the next item number
            editorProduct.setItemNumber(getNumberGenerator().getNextNr(getEditorID()));
        } else {

            // Set the Editor's name to the product name.
            part.setLabel(editorProduct.getName());
        }

        // Load the webshop overlay for this product, if one exists - most products won't have
        // one, since "im Shop" is opt-in per article.
        editorProductWebshop = newProduct ? null : productWebshopDAO.findByProduct(editorProduct);
        productHadWebshopData = (editorProductWebshop != null);
        if (editorProductWebshop == null) {
            editorProductWebshop = new ProductWebshop();
            editorProductWebshop.setProduct(editorProduct);
        }

        createPartControl(parent);
    }

    /**
     * Reload the product picture
     */
	private void setPicture() {
		// The webtool owns product pictures. Query its view for each editor opening so
		// externally replaced or deleted pictures are reflected in the preview.
		refreshingPicture = true;
		try {
			labelProductPicture.setImageByteArray(newProduct ? null
					: productsDAO.findPictureBytesForItemNumber(editorProduct.getItemNumber()));
		} finally {
			refreshingPicture = false;
		}
		updatePictureVariantsBadge();
	}

	@Override
	@Focus
	public void setFocus() {
		if (labelProductPicture != null && !labelProductPicture.isDisposed()) {
			setPicture();
		}
		super.setFocus();
	}

	/** Show the three image destinations used by the webtool's picture catalog. */
	private void updatePictureVariantsBadge() {
		final ProductsDAO.PictureVariantFlags flags = productsDAO.findPictureVariantFlagsForItemNumber(editorProduct.getItemNumber());
		labelPictureVariants.setText("FAKT " + (flags.hasFaktPicture() ? "✓" : "–")
				+ "   SHOP " + (flags.hasShopPicture() ? "✓" : "–")
				+ "   HIRES " + (flags.hasHiresPicture() ? "✓" : "–"));
		labelPictureVariants.setToolTipText(msg.editorProductPictureStatusTooltip);
	}

	private void openPictureEditor() {
		final String configuredUrl = defaultValuePrefs.getString(Constants.PREFERENCES_GENERAL_WEBBROWSER_URL).trim();
		final String itemNumber = editorProduct.getItemNumber();
		if (configuredUrl.isEmpty() || StringUtils.isBlank(itemNumber)) {
			MessageDialog.openInformation(top.getShell(), msg.editorProductPictureEditorButton,
					msg.editorProductPictureEditorMissingUrl);
			return;
		}
		try {
			final URI base = URI.create(configuredUrl);
			if (!("http".equalsIgnoreCase(base.getScheme()) || "https".equalsIgnoreCase(base.getScheme()))
					|| base.getHost() == null) {
				throw new IllegalArgumentException("Invalid webtool URL");
			}
			final String origin = base.getScheme() + "://" + base.getRawAuthority();
			final String url = origin + "/product-pictures/review?item_number="
					+ URLEncoder.encode(itemNumber, StandardCharsets.UTF_8);
			if (!Program.launch(url)) {
				throw new IllegalStateException("Could not open browser");
			}
		} catch (IllegalArgumentException | IllegalStateException e) {
			log.error(e, "Could not open picture editor");
			MessageDialog.openError(top.getShell(), msg.editorProductPictureEditorButton,
					msg.editorProductPictureEditorOpenError);
		}
	}
	
	private void selectReplacementProduct() {
		final MPart currentPart = context.get(MPart.class);
		try {
			final SelectProductDialog dialog = ContextInjectionFactory.make(SelectProductDialog.class, context);
			dialog.setSelectionOnly(true);
			dialog.setDialogBoundsSettings(getDialogSettings("SelectProductDialog"),
					Dialog.DIALOG_PERSISTSIZE | Dialog.DIALOG_PERSISTLOCATION);
			if (dialog.open() == Dialog.OK && dialog.getSelectedProduct() != null) {
				textReplacedByProductId.setText(dialog.getSelectedProduct().getItemNumber());
			}
		} finally {
			context.set(MPart.class, currentPart);
		}
	}

    private String replacementValidationError(final String value) {
        final String itemNumber = StringUtils.trimToEmpty(value);
        if (itemNumber.isEmpty() || "0".equals(itemNumber)) {
            return null;
        }
        final Product replacement = productsDAO.findByItemNumber(itemNumber);
        if (replacement == null) {
            return "Nachfolgeartikel mit dieser Artikelnummer nicht gefunden.";
        }
        return editorProduct.getId() > 0 && editorProduct.getId() == replacement.getId()
                ? "Ein Artikel kann sich nicht selbst ersetzen." : null;
    }

	private void createAndSetDefaultImage() {
		// Display an empty background if no picture is set or picture is not found.
		try {
			Image image = null;
			ImageDescriptor imageDesc = JFaceResources.getImageRegistry()
					.getDescriptor(ProgramImages.NO_PICTURE.name());
			if (imageDesc == null) {
				image = resourceManager.getProgramImage(display, ProgramImages.NO_PICTURE);
				JFaceResources.getImageRegistry().put(ProgramImages.NO_PICTURE.name(), image);
			} else {
				image = JFaceResources.getImageRegistry().get(ProgramImages.NO_PICTURE.name());
			}
			labelProductPicture.setDefaultImage(image);
		} catch (Exception e1) {
			log.error(e1, "Icon not found");
		}
	}

    /**
     * Creates the SWT controls for this workbench part
     * 
     * @param the
     *            parent control
     */
    public void createPartControl(final Composite parent) {
        //		final LayoutSpyDialog popupDialog = new LayoutSpyDialog(shell);
        //		popupDialog.open();
        // Get a reference to the display
        display = parent.getDisplay();

        // Some of these editor's control elements can be hidden.
        // Get these settings from the preference store
        useItemNr = defaultValuePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_ITEMNR);
        useDescription = defaultValuePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_DESCRIPTION);
        scaledPrices = defaultValuePrefs.getInt(Constants.PREFERENCES_PRODUCT_SCALED_PRICES);
        useWeight = defaultValuePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_WEIGHT);
        useNet = (defaultValuePrefs.getInt(Constants.PREFERENCES_PRODUCT_USE_NET_GROSS) != 2);
        useGross = (defaultValuePrefs.getInt(Constants.PREFERENCES_PRODUCT_USE_NET_GROSS) != 1);
        useVat = defaultValuePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_VAT);
        usePicture = defaultValuePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_PICTURE);
        useQuantity = defaultValuePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_QUANTITY);
        useQuantityUnit = defaultValuePrefs.getBoolean(Constants.PREFERENCES_PRODUCT_USE_QUNIT);

        // Get the product VAT
        //	done by databinding

        // Create the ScrolledComposite to scroll horizontally and vertically
        ScrolledComposite scrollcomposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);

        // Create the top Composite - just a plain single-column wrapper now;
        // the actual layout lives in the nested SashForms below.
        top = new Composite(scrollcomposite, SWT.SCROLLBAR_OVERLAY | SWT.NONE); //was parent before
        GridLayoutFactory.swtDefaults().numColumns(1).margins(0, 0).applyTo(top);

        scrollcomposite.setContent(top);
        scrollcomposite.setMinSize(1000, 600); // 2nd entry should be adjusted to higher value when new fields will be added to composite
        scrollcomposite.setExpandHorizontal(true);
        scrollcomposite.setExpandVertical(true);
        scrollcomposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

        // Create an invisible container for all hidden components - deliberately NOT a
        // child of any SashForm below (a SashForm treats every direct child as a pane
        // needing its own sash, which this must not become).
        Composite invisible = new Composite(top, SWT.NONE);
        invisible.setVisible(false);
        GridDataFactory.fillDefaults().hint(0, 0).applyTo(invisible);
        GridLayoutFactory.swtDefaults().margins(0, 0).applyTo(invisible);

        // Add context help reference
        //		PlatformUI.getWorkbench().getHelpSystem().setHelp(top, ContextHelpConstants.PRODUCT_EDITOR);

        // The outer sash separates the product fields from the note area. A second,
        // horizontal sash below separates master data from the picture preview.
        SashForm rowsAndNoteSash = new SashForm(top, SWT.VERTICAL);
        styleSash(rowsAndNoteSash);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(rowsAndNoteSash);

        SashForm topBlock = new SashForm(rowsAndNoteSash, SWT.VERTICAL);
        styleSash(topBlock);

        SashForm masterPictureSash = new SashForm(topBlock, SWT.HORIZONTAL);
        styleSash(masterPictureSash);

        // Group: Stammdaten
        Group stammdatenGroup = new Group(masterPictureSash, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(4).applyTo(stammdatenGroup);
        stammdatenGroup.setText("\u00A0\u00A0" + msg.editorProductLabelMasterdata);

        // Left column (top to bottom): Artikelnummer, Name, Kategorie
        // Right column (top to bottom): GTIN, Art.-Nr. Lieferant, Einheit

        // Row: Artikelnummer | GTIN
        Label labelItemNr = new Label(useItemNr ? stammdatenGroup : invisible, SWT.NONE);
        labelItemNr.setText(msg.exporterDataItemnumber);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelItemNr);
        textItemNr = new Text(useItemNr ? stammdatenGroup : invisible, SWT.BORDER);
        textItemNr.addKeyListener(new ReturnKeyAdapter(textItemNr));
        GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false).applyTo(textItemNr);
        if (!useItemNr) {
            // filler: keep this row's left-hand cells occupied even when
            // Artikelnummer is hidden, so "GTIN" doesn't slide into its place
            new Label(stammdatenGroup, SWT.NONE);
            new Label(stammdatenGroup, SWT.NONE);
        }

        Label labelGtin = new Label(stammdatenGroup, SWT.NONE);
        labelGtin.setText(msg.editorProductFieldGtin);
        //		labelGtin.setToolTipText(msg.editorProductFieldGtinTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelGtin);
        textGtin = new Text(stammdatenGroup, SWT.BORDER);
        textGtin.addKeyListener(new ReturnKeyAdapter(textGtin));
        GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false).applyTo(textGtin);

        // Row: Name | Art.-Nr. Lieferant
        Label labelName = new Label(stammdatenGroup, SWT.NONE);
        labelName.setText(msg.commonFieldName);
        labelName.setToolTipText(msg.editorProductNameTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelName);
        textName = new Text(stammdatenGroup, SWT.BORDER);
        textName.setToolTipText(labelName.getToolTipText());
        textName.addKeyListener(new ReturnKeyAdapter(textName));
        GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false).applyTo(textName);

        Label labelSupplierItemNumber = new Label(stammdatenGroup, SWT.NONE);
        labelSupplierItemNumber.setText(msg.editorProductFieldSupplierItemnumber);
        labelSupplierItemNumber.setToolTipText(msg.editorProductFieldSupplierItemnumberTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelSupplierItemNumber);
        textSupplierItemNumber = new Text(stammdatenGroup, SWT.BORDER);
        textSupplierItemNumber.addKeyListener(new ReturnKeyAdapter(textSupplierItemNumber));
        GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false).applyTo(textSupplierItemNumber);

        // for correct tab-order (see FAK-465)
        Control nextWidget = null;

        // Row: Kategorie | Einheit
        Label labelCategory = new Label(stammdatenGroup, SWT.NONE);
        labelCategory.setText(msg.commonFieldCategory);
        labelCategory.setToolTipText(msg.editorProductCategoryTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelCategory);
        comboCategory = new CCombo(stammdatenGroup, SWT.BORDER);
        GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false).applyTo(comboCategory);

        Label labelQuantityUnit = new Label(useQuantityUnit ? stammdatenGroup : invisible, SWT.NONE);
        //T: Product Editor - Label Product quantity unit
        labelQuantityUnit.setText(msg.editorProductFieldQuantityunitName);
        GridDataFactory.defaultsFor(labelQuantityUnit).indent(-20, 0).align(SWT.END, SWT.CENTER).applyTo(labelQuantityUnit);

        if (useQuantityUnit) {
            textQuantityUnit = new Text(stammdatenGroup, SWT.BORDER);
            textQuantityUnit.addKeyListener(new ReturnKeyAdapter(textQuantityUnit));
            nextWidget = textQuantityUnit;
        } else {
            textQuantityUnit = new Text(invisible, SWT.BORDER);
            // filler: keep this row's cell count at 4 even when Einheit is
            // hidden, so "Beschreibung" below doesn't slide into its place
            new Label(stammdatenGroup, SWT.NONE);
            new Label(stammdatenGroup, SWT.NONE);
        }
        GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false).applyTo(textQuantityUnit);

        Label labelReplacedBy = new Label(stammdatenGroup, SWT.NONE);
        labelReplacedBy.setText("Ersetzt durch ArtNr (0=ohne Bezug)");
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelReplacedBy);
        Composite replacementInput = new Composite(stammdatenGroup, SWT.NONE);
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(replacementInput);
        GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false).applyTo(replacementInput);
        textReplacedByProductId = new Text(replacementInput, SWT.BORDER);
        textReplacedByProductId.setToolTipText("Leer: aktiv; 0: Auslaufartikel ohne Nachfolger; sonst: Artikelnummer des Nachfolgers");
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textReplacedByProductId);
        Button selectReplacementButton = new Button(replacementInput, SWT.PUSH);
        selectReplacementButton.setText("…");
        selectReplacementButton.setToolTipText("Nachfolgeartikel auswählen");
        selectReplacementButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> selectReplacementProduct()));
        Label replacedByHint = new Label(stammdatenGroup, SWT.NONE);
        replacedByHint.setText("0 = Auslaufartikel ohne Nachfolger");
        GridDataFactory.swtDefaults().span(2, 1).applyTo(replacedByHint);

        // Row: Beschreibung, spanning the group's full width - kept fairly
        // short (it can still grow if the window does), the picture preview
        // below matches whatever height this row ends up with
        Label labelDescription = new Label(useDescription ? stammdatenGroup : invisible, SWT.NONE);
        labelDescription.setText(msg.commonFieldDescription);
        //T: Tool Tip Text
        labelDescription.setToolTipText(msg.editorProductAdddescriptionTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelDescription);
        textDescription = new Text(useDescription ? stammdatenGroup : invisible, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        //		textDescription.setText(DataUtils.makeOSLineFeeds(editorProduct.getStringValueByKey("description")));
        textDescription.setToolTipText(labelDescription.getToolTipText());
        GridDataFactory.fillDefaults().span(3, 1).hint(10, 50).grab(true, true).applyTo(textDescription);

        // Group: Produktbild - beside Stammdaten
        Group productPictureGroup = new Group(usePicture ? masterPictureSash : invisible, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(1).applyTo(productPictureGroup);
        productPictureGroup.setText("\u00A0\u00A0" + msg.exporterDataPicture);
        if (!usePicture) {
            new Composite(masterPictureSash, SWT.NONE);
        }

        // Fixed size hints keep picture dimensions out of the sash's preferred size.
        // Both controls still expand into the width the user assigns to the preview.
        photoComposite = new Composite(productPictureGroup, SWT.BORDER);
        GridLayoutFactory.swtDefaults().margins(10, 10).numColumns(1).applyTo(photoComposite);
        GridDataFactory.fillDefaults().hint(120, 120).grab(true, true).applyTo(photoComposite);
        photoComposite.setBackground(new Color(null, 255, 255, 255));

        // The picture name label
        labelProductPicture = new FakturamaPictureControl(photoComposite);
        ContextInjectionFactory.inject(labelProductPicture, context);
        GridDataFactory.fillDefaults().hint(100, 100).grab(true, true).applyTo(labelProductPicture);

        // Availability for Fakturama, shop and high-resolution pictures.
        labelPictureVariants = new Label(photoComposite, SWT.NONE);
        GridDataFactory.swtDefaults().grab(true, false).align(SWT.CENTER, SWT.CENTER).applyTo(labelPictureVariants);

        Button openPictureEditorButton = new Button(productPictureGroup, SWT.PUSH);
        openPictureEditorButton.setText(msg.editorProductPictureEditorButton);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(openPictureEditorButton);
        openPictureEditorButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> openPictureEditor()));

        createAndSetDefaultImage();
        setPicture();

        // Keep the preview within the user-assigned pane. The Nebula control only
        // rescales bitmap data when it is assigned, so debounce that work while dragging.
        final Runnable rescalePicture = () -> {
            if (labelProductPicture.isDisposed()) {
                return;
            }
            final byte[] picture = labelProductPicture.getImageByteArray();
            if (picture != null) {
                refreshingPicture = true;
                try {
                    labelProductPicture.setImageByteArray(picture);
                } finally {
                    refreshingPicture = false;
                }
            }
            labelProductPicture.layout(true, true);
        };
        final int[] lastAppliedSize = { -1, -1 };
        photoComposite.addControlListener(ControlListener.controlResizedAdapter(e -> {
            final Rectangle area = photoComposite.getClientArea();
            final int margin = 10; // matches photoComposite's own GridLayout margins above
            final int newWidth = area.width - 2 * margin;
            final int newHeight = area.height - 2 * margin;
            if (newWidth > 0 && newHeight > 0 && (newWidth != lastAppliedSize[0] || newHeight != lastAppliedSize[1])) {
                lastAppliedSize[0] = newWidth;
                lastAppliedSize[1] = newHeight;
                labelProductPicture.setMaxImageWidth(newWidth);
                labelProductPicture.setMaxImageHeight(newHeight);
                labelProductPicture.layout(true, true);
                photoComposite.getDisplay().timerExec(-1, rescalePicture);
                photoComposite.getDisplay().timerExec(120, rescalePicture);
            }
        }));

        labelProductPicture.addPropertyChangeListener(FakturamaPictureControl.IMAGE_BYTEARRAY_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
				if (refreshingPicture) {
					return;
				}
                byte[] newImage = (byte[]) event.getNewValue();
                final String imageKey = "prodimg_" + Objects.toString(editorProduct.getItemNumber(), Long.toString(editorProduct.getId()));
                JFaceResources.getImageRegistry().remove(imageKey);
                // if image was deleted we use the default image
                editorProduct.setPicture(newImage);
                updatePictureVariantsBadge();
                if (newImage != null) {
                    Display display = Display.getCurrent();
                    ByteArrayInputStream bais = new ByteArrayInputStream(newImage);
                    Image image = new Image(display, bais);
                    JFaceResources.getImageRegistry().put(imageKey, image);
                }
                getMDirtyablePart().setDirty(true);
            }
        });

        // ========= Row 2: Preise & Steuer | Lager & Versand | Webshop =========
        SashForm detailColumnsSash = new SashForm(topBlock, SWT.HORIZONTAL);
        styleSash(detailColumnsSash);

        // Group: Preise & Steuer
        Group pricingGroup = new Group(detailColumnsSash, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(4).applyTo(pricingGroup);
        GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(pricingGroup);
        pricingGroup.setText("\u00A0\u00A0" + msg.editorProductLabelPricing);

        // Row: Einkaufspreis (netto) | MwSt.
        Label labelCostPrice = new Label(pricingGroup, SWT.NONE);
        labelCostPrice.setText(msg.editorProductFieldCostprice);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelCostPrice);
        costPrice = new FormattedText(pricingGroup, SWT.BORDER);
        MoneyFormatter costPriceFormatter = ContextInjectionFactory.make(MoneyFormatter.class, context);
        costPrice.setFormatter(costPriceFormatter);
        costPrice.getControl().addKeyListener(new ReturnKeyAdapter(costPrice.getControl()));
        GridDataFactory.swtDefaults().hint(120, SWT.DEFAULT).applyTo(costPrice.getControl());

        Label labelVat = new Label(useVat ? pricingGroup : invisible, SWT.NONE);
        labelVat.setText(msg.commonFieldVat);
        labelVat.setToolTipText(msg.editorProductVatName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelVat);
        comboVat = new Combo(useVat ? pricingGroup : invisible, SWT.BORDER | SWT.READ_ONLY);
        comboVat.setToolTipText(labelVat.getToolTipText());
        if (!useVat) {
            // filler: keep this row's cell count at 4 even when MwSt is hidden,
            // so the price table below doesn't slide into its place
            new Label(invisible, SWT.NONE);
            new Label(invisible, SWT.NONE);
        }

        // Row: Staffelpreise - the existing scaled-price control (label plus a
        // per-tier "ab <qty> -> price" table), unchanged internally, just
        // relocated here.
        Label labelPrice = new Label(pricingGroup, SWT.NONE);

        // Use net or gross price
        if (useNet && useGross) {
            //T: Label in the product editor
            labelPrice.setText(msg.commonFieldPrice);
        } else if (useNet) {
            //T: Label in the product editor
            labelPrice.setText(msg.editorProductFieldPriceName);
        } else if (useGross) {
            //T: Label in the product editor
            labelPrice.setText(msg.editorProductFieldGrosspriceName);
        }

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelPrice);

        // Create a container composite for the scaled price
        Composite pricetable = new Composite(pricingGroup, SWT.NONE);
        GridLayoutFactory.swtDefaults().margins(0, 0).numColumns((scaledPrices > 1) ? (useNet && !useGross) ? 3 : 4 : 2).applyTo(pricetable);
        GridDataFactory.fillDefaults().span(3, 1).grab(true, false).applyTo(pricetable);

        // If there is a net and gross column, and 2 columns for the quantity
        // there are 2 cells in the top left corner, that are empty
        if (scaledPrices >= 2 && useNet && useGross) {
            new Label(pricetable, SWT.NONE);
            new Label(pricetable, SWT.NONE);
        }

        // Display the heading for the net and gross columns
        if (useNet && useGross) {
            Label labelNet = new Label(pricetable, SWT.CENTER);
            labelNet.setText(msg.productDataNet);
            Label labelGross = new Label(pricetable, SWT.CENTER);
            labelGross.setText(msg.productDataGross);
        }

        createPriceBlocks();

        // Create a row for each entry of the scaled price table
        context.set(Constants.CONTEXT_STYLE, SWT.BORDER | SWT.RIGHT);
        // Older products can have no VAT assigned. Keep the VAT selector empty and
        // use 0% only for the price widgets until the user chooses a VAT rate.
        final VAT productVat = editorProduct.getVat();
        context.set(Constants.CONTEXT_VATVALUE,
                productVat != null && productVat.getTaxValue() != null ? productVat.getTaxValue() : Double.valueOf(0.0));
        Object priceObj;
        for (int i = 0; i < MAX_NUMBER_OF_PRICES; i++) {
            try {

                // Get the net price scaled price
                String methodName = String.format("getPrice%d", i + 1);
                priceObj = MethodUtils.invokeExactMethod(editorProduct, methodName);
                net[i] = (priceObj != null) ? Money.of((Double) priceObj, DataUtils.getInstance().getDefaultCurrencyUnit()) : Money.from(defaultPrice);

                // Create the columns for the quantity
                labelBlock[i] = new Label(i < scaledPrices && scaledPrices >= 2 ? pricetable : invisible, SWT.NONE);
                // T: Product Editor - Label Scaled Prices "from" .. Quantity
                // the price is ..
                labelBlock[i].setText(msg.editorProductLabelFrom);

                textBlock[i] = new Text(i < scaledPrices && scaledPrices >= 2 ? pricetable : invisible, SWT.BORDER | SWT.RIGHT);
                methodName = String.format("getBlock%d", i + 1);
                priceObj = MethodUtils.invokeExactMethod(editorProduct, methodName);
                textBlock[i].setText(Integer.toString(priceObj != null ? (Integer) priceObj : 0));
                GridDataFactory.swtDefaults().hint(40, SWT.DEFAULT).applyTo(textBlock[i]);

                // Create the net columns
                context.set(Constants.CONTEXT_NETVALUE, net[i]);
                context.set(Constants.CONTEXT_CANVAS, (i < scaledPrices) ? pricetable : invisible);
                if (useNet) {
                    netText[i] = ContextInjectionFactory.make(NetText.class, context);
                    GridDataFactory.swtDefaults().hint(120, SWT.DEFAULT).applyTo(netText[i].getNetText().getControl());
                    if (i == 0 && nextWidget == null) { // only for the first iteration
                        nextWidget = netText[i].getNetText().getControl();
                    }
                }

                // Create the gross columns
                if (useGross) {
                    if (!useNet) {
                        // create hidden net field if no one is given
                        netText[i] = ContextInjectionFactory.make(NetText.class, context);
                        netText[i].getNetText().getControl().setVisible(false);
                        GridDataFactory.swtDefaults().hint(0, SWT.DEFAULT).applyTo(netText[i].getNetText().getControl());
                    }
                    grossText[i] = ContextInjectionFactory.make(GrossText.class, context);
                    grossText[i].setNetText(netText[i]);
                    GridDataFactory.swtDefaults().hint(120, SWT.DEFAULT).applyTo(grossText[i].getGrossText().getControl());
                    if (i == 0 && nextWidget == null) { // only for the first iteration
                        nextWidget = grossText[i].getGrossText().getControl();
                    }
                }

                // If a net and gross column was created, link both together,
                // so, if one is modified, the other will be recalculated.
                if (useNet && useGross) {
                    netText[i].setGrossText(grossText[i]);
                    grossText[i].setNetText(netText[i]);
                    if (i == 0 && nextWidget == null) { // only for the first iteration
                        nextWidget = grossText[i].getGrossText().getControl();
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.error(e, "error while creating the ProductEditor part");
            }
        }

        // Set the tab order
        setTabOrder(textDescription, nextWidget);

        // Group: Lager & Versand
        Group stockShippingGroup = new Group(detailColumnsSash, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(stockShippingGroup);
        GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(stockShippingGroup);
        stockShippingGroup.setText("\u00A0\u00A0" + msg.editorProductLabelStockshipping);

        // Row: Bestand führen (checkbox alone, spanning both columns)
        checkboxStockManaged = new Button(useQuantity ? stockShippingGroup : invisible, SWT.CHECK);
        checkboxStockManaged.setText(msg.editorProductFieldStockmanagedName);
        checkboxStockManaged.setToolTipText(msg.editorProductFieldStockmanagedTooltip);
        GridDataFactory.swtDefaults().span(2, 1).applyTo(checkboxStockManaged);

        // Row: Lagerbestand
        Label labelQuantity = new Label(useQuantity ? stockShippingGroup : invisible, SWT.NONE);
        //T: Product Editor - Label Product quantity
        labelQuantity.setText(msg.commonFieldQuantity);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelQuantity);
        if (useQuantity) {
            DoubleValueFormatter quantityFormatter = ContextInjectionFactory.make(DoubleValueFormatter.class, context);
            textQuantity = new FormattedText(stockShippingGroup, SWT.BORDER);
            textQuantity.setFormatter(quantityFormatter);
            textQuantity.getControl().addKeyListener(new ReturnKeyAdapter(textQuantity.getControl()));
            textQuantity.getControl().setToolTipText(msg.commonFieldQuantityTooltip);
            nextWidget = textQuantityUnit;

            // Track inventory: enables/disables the stock quantity field. Turning it
            // off also clears a non-zero stock value (after confirming, since that's
            // silently throwing away a real number) - a zero/null value is cleared
            // straight away, nothing to confirm there.
            //
            // Cleared to null, not 0.0: a not-managed article has NO stock figure at all,
            // as opposed to a managed one that's really down to zero - ProductListTable's
            // QUANTITY column already treats null as "show nothing" vs. 0.0 as "show 0,00"
            // (see its label provider), so this is what actually makes the 0,00 disappear
            // from the product list for articles marked "nicht lagerrelevant".
            checkboxStockManaged.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent e) {
                    final boolean nowManaged = checkboxStockManaged.getSelection();
                    if (!nowManaged) {
                        final Double currentQuantity = editorProduct.getQuantity();
                        if (currentQuantity != null && currentQuantity != 0.0) {
                            final NumberFormat quantityFormat = NumberFormat.getNumberInstance();
                            quantityFormat.setMinimumFractionDigits(2);
                            quantityFormat.setMaximumFractionDigits(2);
                            final boolean clear = MessageDialog.openQuestion(top.getShell(), msg.editorProductFieldStockmanagedClearconfirmTitle,
                                    msg.editorProductFieldStockmanagedClearconfirmMessage + " " + quantityFormat.format(currentQuantity));
                            if (clear) {
                                editorProduct.setQuantity(null);
                                textQuantity.setValue(null);
                            }
                        } else {
                            editorProduct.setQuantity(null);
                            textQuantity.setValue(null);
                        }
                    }
                    textQuantity.getControl().setEnabled(nowManaged);
                }
            });
        } else {
            textQuantity = new FormattedText(invisible, SWT.BORDER);
        }
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textQuantity.getControl());

        // Row: Gewicht (kg)
        Label labelWeight = new Label(useWeight ? stockShippingGroup : invisible, SWT.NONE);
        //T: Product Editor - Label Product Weight with unit (kg)
        labelWeight.setText(msg.exporterDataWeight);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelWeight);
        textWeight = new FormattedText(useWeight ? stockShippingGroup : invisible, SWT.BORDER);
        textWeight.setFormatter(new DoubleFormatter());
        textWeight.getControl().addKeyListener(new ReturnKeyAdapter(textWeight.getControl()));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textWeight.getControl());

        // Group: Webshop
        createWebshopGroup(detailColumnsSash);

        // ================= Zusatzdaten + Notiz, with their own lower splitter =================

        SashForm bottomBlock = new SashForm(rowsAndNoteSash, SWT.VERTICAL);
        styleSash(bottomBlock);

        Group additionalDataGroup = new Group(bottomBlock, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(6).applyTo(additionalDataGroup);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(additionalDataGroup);
        additionalDataGroup.setText("\u00A0\u00A0" + msg.editorProductLabelAdditionaldata);

        Label udf01Lbl = new Label(additionalDataGroup, SWT.NONE);
        udf01Lbl.setText(msg.editorProductFieldUdf01);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(udf01Lbl);
        udf01 = new Text(additionalDataGroup, SWT.BORDER);
        udf01.addKeyListener(new ReturnKeyAdapter(udf01));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(udf01);

        Label udf02Lbl = new Label(additionalDataGroup, SWT.NONE);
        udf02Lbl.setText(msg.editorProductFieldUdf02);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(udf02Lbl);
        udf02 = new Text(additionalDataGroup, SWT.BORDER);
        udf02.addKeyListener(new ReturnKeyAdapter(udf02));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(udf02);

        Label udf03Lbl = new Label(additionalDataGroup, SWT.NONE);
        udf03Lbl.setText(msg.editorProductFieldUdf03);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(udf03Lbl);
        udf03 = new Text(additionalDataGroup, SWT.BORDER);
        udf03.addKeyListener(new ReturnKeyAdapter(udf03));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(udf03);

        // ================= Notiz =================

        Group noteGroup = new Group(bottomBlock, SWT.NONE);
        noteGroup.setText("\u00A0\u00A0" + msg.editorContactLabelNotice);
        GridLayoutFactory.swtDefaults().applyTo(noteGroup);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(noteGroup);

        note = new Text(noteGroup, SWT.BORDER | SWT.MULTI);
        note.addKeyListener(new ReturnKeyAdapter(note));
        GridDataFactory.fillDefaults().grab(true, true).applyTo(note);

        if (usePicture) {
            masterPictureSash.setWeights(loadSashWeights(PICTURE_WEIGHTS_KEY, new int[] { 70, 30 }));
            persistSashWeights(masterPictureSash, PICTURE_WEIGHTS_KEY);
        } else {
            masterPictureSash.setMaximizedControl(stammdatenGroup);
        }
        detailColumnsSash.setWeights(loadSashWeights(DETAIL_WEIGHTS_KEY, new int[] { 40, 30, 30 }));
        persistSashWeights(detailColumnsSash, DETAIL_WEIGHTS_KEY);
        topBlock.setWeights(loadSashWeights(UPPER_ROWS_WEIGHTS_KEY, new int[] { 45, 55 }));
        persistSashWeights(topBlock, UPPER_ROWS_WEIGHTS_KEY);
        bottomBlock.setWeights(loadSashWeights(LOWER_ROWS_WEIGHTS_KEY, new int[] { 25, 75 }));
        persistSashWeights(bottomBlock, LOWER_ROWS_WEIGHTS_KEY);

        // Give topBlock a weight that approximates its own natural/minimum height on
        // a typical window (constrainSash below is what actually enforces the floor
        // on a tall one) and let Notiz have the rest - unless the user already dragged
        // it somewhere else in a previous session (see persistSashWeights below).
        rowsAndNoteSash.setWeights(loadSashWeights(SASH_WEIGHTS_KEY, new int[] { 75, 25 }));

        // Keep the drag from squeezing topBlock below what it needs to show
        // everything, or Notiz down to nothing.
        constrainSash(rowsAndNoteSash, topBlock, 130);
        persistSashWeights(rowsAndNoteSash, SASH_WEIGHTS_KEY);
        // constrainSash only clamps a live drag - it does nothing for the weights just applied
        // above, whether that's the hardcoded default or a restored-but-now-stale value (e.g. from
        // a previously bigger window). Enforce the same content-derived floor here too, both once
        // up front and on every subsequent resize.
        enforceMinimumFirstPane(rowsAndNoteSash, topBlock, 130);

        oldCat = editorProduct.getCategories();

        bindModel();
    }

    /**
     * Widens the sash (default 3px is a fiddly drag target) and neutralizes
     * default.css's blanket light-blue SashForm background (#c1d5ef) - fine
     * for the one place that already wanted it, but here it just bleeds
     * across every pane. Same fix as {@code AbstractViewDataTable}'s
     * category-tree/table split and {@code DocumentEditor}'s equivalent -
     * see their identical {@code no-blue-sash} usage. A plain
     * {@code setBackground()} alone isn't enough: dynamic CSS is on, so the
     * CSS engine re-applies the class's rule after every re-style; the class
     * has to opt out via {@code .no-blue-sash} in default.css instead.
     */
    private void styleSash(final SashForm sashForm) {
        sashForm.SASH_WIDTH = 6;
        sashForm.setData("org.eclipse.e4.ui.css.CssClassName", "no-blue-sash");
        sashForm.setBackground(new Color(sashForm.getDisplay(), 246, 245, 244));
    }

    /**
     * The pane before the sash (topBlock: Stammdaten/Produktbild/Preise/Lager/Webshop) must never
     * be squeezed below what its own groups actually need to show every field - a fixed pixel
     * guess drifts wrong under a different font, DPI or locale (translated labels are wider/
     * narrower), so this asks the content itself via {@link Composite#computeSize} instead, at
     * its current width (groups wrap their rows to whatever width they're given, so height is
     * still meaningfully width-dependent).
     */
    private int computeMinFirstPane(final SashForm sashForm, final Composite topBlockContent) {
        final boolean vertical = (sashForm.getOrientation() == SWT.VERTICAL);
        final Rectangle area = sashForm.getClientArea();
        final Point pref = vertical ? topBlockContent.computeSize(area.width, SWT.DEFAULT)
                : topBlockContent.computeSize(SWT.DEFAULT, area.height);
        return vertical ? pref.y : pref.x;
    }

    /**
     * Clamps a two-pane {@link SashForm}'s drag range so neither pane can be
     * resized below a sensible minimum - SWT's Sash fires a plain
     * {@code SWT.Selection} with the proposed new position in {@code event.x}/
     * {@code event.y}, which this adjusts in place before the SashForm applies
     * it. There's no public API for this on SashForm itself; finding the
     * {@link Sash} among its children and listening there is the standard
     * approach (used by JFace's own JDT UI for the same reason).
     *
     * @param sashForm
     *            the two-pane form to constrain
     * @param topBlockContent
     *            the pane before the sash - its own {@link Composite#computeSize} is the minimum,
     *            see {@link #computeMinFirstPane}
     * @param minSecondPane
     *            minimum size (px) of the pane after the sash
     */
    private void constrainSash(final SashForm sashForm, final Composite topBlockContent, final int minSecondPane) {
        final boolean vertical = (sashForm.getOrientation() == SWT.VERTICAL);
        for (final Control control : sashForm.getChildren()) {
            if (control instanceof Sash) {
                final Sash sash = (Sash) control;
                sash.addListener(SWT.Selection, event -> {
                    final Rectangle area = sashForm.getClientArea();
                    final int total = vertical ? area.height : area.width;
                    final int sashSize = vertical ? sash.getBounds().height : sash.getBounds().width;
                    final int proposed = vertical ? event.y : event.x;
                    final int minFirstPane = computeMinFirstPane(sashForm, topBlockContent);
                    final int clamped = Math.max(minFirstPane, Math.min(proposed, total - sashSize - minSecondPane));
                    if (vertical) {
                        event.y = clamped;
                    } else {
                        event.x = clamped;
                    }
                });
            }
        }
    }

    /**
     * Unlike {@link #constrainSash}, which only clamps a live drag, this enforces the same
     * content-derived floor (see {@link #computeMinFirstPane}) on whatever weights are in effect
     * right now - the hardcoded default, a value just restored from a previous, possibly
     * differently-sized window (see {@link #loadSashWeights}), or a later window resize that would
     * otherwise squeeze the top pane below it. Registered both as a one-off (via {@code
     * asyncExec}, since the SashForm has no real pixel size yet at part-creation time) and as an
     * ongoing resize listener; safe to call repeatedly since a correction that already satisfies
     * the floor is a no-op, and {@link SashForm#setWeights} resizes the SashForm's children, not
     * the SashForm control itself, so it cannot re-trigger this same listener.
     */
    private void enforceMinimumFirstPane(final SashForm sashForm, final Composite topBlockContent, final int minSecondPane) {
        final boolean vertical = (sashForm.getOrientation() == SWT.VERTICAL);
        final Runnable clamp = () -> {
            if (sashForm.isDisposed()) {
                return;
            }
            final Rectangle area = sashForm.getClientArea();
            final int total = vertical ? area.height : area.width;
            final int minFirstPane = computeMinFirstPane(sashForm, topBlockContent);
            if (total < minFirstPane + minSecondPane) {
                return;
            }
            final int[] weights = sashForm.getWeights();
            final int sum = Arrays.stream(weights).sum();
            if (sum <= 0) {
                return;
            }
            final int firstPanePx = (int) ((long) weights[0] * total / sum);
            if (firstPanePx < minFirstPane) {
                sashForm.setWeights(new int[] { minFirstPane, total - minFirstPane });
            }
        };
        sashForm.getDisplay().asyncExec(clamp);
        sashForm.addControlListener(ControlListener.controlResizedAdapter(e -> clamp.run()));
    }

    private static final String SASH_SETTINGS_SECTION = "ProductEditorSash";
    private static final String SASH_WEIGHTS_KEY = "rowsAndNoteWeights";
    private static final String PICTURE_WEIGHTS_KEY = "masterPictureWeights";
    private static final String UPPER_ROWS_WEIGHTS_KEY = "upperRowsWeights";
    private static final String DETAIL_WEIGHTS_KEY = "detailColumnsWeights";
    private static final String LOWER_ROWS_WEIGHTS_KEY = "lowerRowsWeights";

    /**
     * A single, global (not per-product) remembered split position for
     * {@code rowsAndNoteSash}, the same way {@code DocumentEditor} remembers its
     * own sash per billing type - stored via the shared "Workbench" IDialogSettings,
     * one section per widget rather than per opened record.
     */
    private IDialogSettings getDialogSettings(final String section) {
        if (settings.getSection(section) == null) {
            settings.addNewSection(section);
        }
        return settings.getSection(section);
    }

    private int[] loadSashWeights(final String key, final int[] defaultWeights) {
        final String[] saved = getDialogSettings(SASH_SETTINGS_SECTION).getArray(key);
        if (saved == null || saved.length != defaultWeights.length) {
            return defaultWeights;
        }
        try {
            return Arrays.stream(saved).mapToInt(Integer::parseInt).toArray();
        } catch (final NumberFormatException e) {
            return defaultWeights;
        }
    }

    private void saveSashWeights(final SashForm sashForm, final String key) {
        final String[] asStrings = Arrays.stream(sashForm.getWeights()).mapToObj(Integer::toString).toArray(String[]::new);
        getDialogSettings(SASH_SETTINGS_SECTION).put(key, asStrings);
    }

    /**
     * Saves the sash's weights every time the user finishes dragging it. The drag itself is
     * handled by the SashForm's own listener (registered when the Sash was created, before this
     * one) and by {@link #constrainSash}'s clamping listener - both already ran by the time this
     * fires, but {@link SashForm#getWeights()} only reflects the new layout once SWT has finished
     * processing this same event, hence the asyncExec rather than reading it inline here.
     */
    private void persistSashWeights(final SashForm sashForm, final String key) {
        for (final Control control : sashForm.getChildren()) {
            if (control instanceof Sash) {
                control.addListener(SWT.Selection, event -> Display.getDefault().asyncExec(() -> {
                    if (!sashForm.isDisposed()) {
                        saveSashWeights(sashForm, key);
                    }
                }));
            }
        }
    }

    /** Creates the controls backed by FKT_PRODUCTWEBSHOP. */
    private void createWebshopGroup(final Composite parent) {
        Group webshopGroup = new Group(parent, SWT.NONE);
        webshopGroup.setText("\u00A0\u00A0" + msg.editorProductLabelWebshop);
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(webshopGroup);
        GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(webshopGroup);

        checkboxInShop = new Button(webshopGroup, SWT.CHECK);
        checkboxInShop.setText(msg.editorProductFieldInshopName);
        checkboxInShop.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                final boolean nowInShop = checkboxInShop.getSelection();
                setWebshopFieldsEnabled(nowInShop);
                if (nowInShop && !productHadWebshopData) {
                    prefillWebshopDefaults();
                }
                // Not bound via bindModelValue (its checked state is derived from
                // productHadWebshopData/deleted, not a single model property - see
                // doSave()), so toggling it doesn't trip data-binding's own dirty
                // tracking the way every other field here does; set it explicitly,
                // same as the picture picker below.
                getMDirtyablePart().setDirty(true);
            }
        });
        GridDataFactory.swtDefaults().span(2, 1).applyTo(checkboxInShop);

        Label labelShopPrice = new Label(webshopGroup, SWT.NONE);
        labelShopPrice.setText(msg.editorProductFieldShoppriceName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopPrice);
        textShopPrice = new FormattedText(webshopGroup, SWT.BORDER);
        MoneyFormatter shopPriceFormatter = ContextInjectionFactory.make(MoneyFormatter.class, context);
        textShopPrice.setFormatter(shopPriceFormatter);
        textShopPrice.getControl().addKeyListener(new ReturnKeyAdapter(textShopPrice.getControl()));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textShopPrice.getControl());

        Label labelShopStockQuantity = new Label(webshopGroup, SWT.NONE);
        labelShopStockQuantity.setText(msg.editorProductFieldShopstockquantityName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopStockQuantity);
        textShopStockQuantity = new Text(webshopGroup, SWT.BORDER);
        textShopStockQuantity.addKeyListener(new ReturnKeyAdapter(textShopStockQuantity));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textShopStockQuantity);

        Label labelShopLowStockAmount = new Label(webshopGroup, SWT.NONE);
        labelShopLowStockAmount.setText(msg.editorProductFieldShoplowstockamountName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopLowStockAmount);
        textShopLowStockAmount = new Text(webshopGroup, SWT.BORDER);
        textShopLowStockAmount.addKeyListener(new ReturnKeyAdapter(textShopLowStockAmount));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textShopLowStockAmount);

        Label labelShopStockStatus = new Label(webshopGroup, SWT.NONE);
        labelShopStockStatus.setText(msg.editorProductFieldShopstockstatusName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopStockStatus);
        comboShopStockStatus = new Combo(webshopGroup, SWT.BORDER | SWT.READ_ONLY);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(comboShopStockStatus);

        Label labelShopBackorders = new Label(webshopGroup, SWT.NONE);
        labelShopBackorders.setText(msg.editorProductFieldShopbackordersName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopBackorders);
        comboShopBackorders = new Combo(webshopGroup, SWT.BORDER | SWT.READ_ONLY);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(comboShopBackorders);

        Label labelDeliveryTime = new Label(webshopGroup, SWT.NONE);
        labelDeliveryTime.setText(msg.editorProductFieldDeliverytimeName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelDeliveryTime);
        textDeliveryTime = new Text(webshopGroup, SWT.BORDER);
        textDeliveryTime.addKeyListener(new ReturnKeyAdapter(textDeliveryTime));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textDeliveryTime);

        final Button openShopProductButton = new Button(webshopGroup, SWT.PUSH);
        openShopProductButton.setText("Im Webshop bearbeiten…");
        GridDataFactory.swtDefaults().span(2, 1).align(SWT.END, SWT.CENTER).applyTo(openShopProductButton);
        openShopProductButton.setEnabled(false);
        final Long webshopId = editorProduct.getWebshopId();
        final String adminUrl = defaultValuePrefs.getString(Constants.PREFERENCES_PRESTASHOP_ADMIN_URL).trim();
        if (webshopId == null || webshopId <= 0) {
            openShopProductButton.setToolTipText("Noch nicht mit einem Webshop-Produkt verknüpft.");
        } else if (adminUrl.isEmpty()) {
            openShopProductButton.setToolTipText("PrestaShop-Admin-URL in den Webshop-Einstellungen eintragen.");
        } else {
            try {
                final PrestaShopProductLink productLink = new PrestaShopProductLink(adminUrl, webshopId);
                openShopProductButton.setEnabled(true);
                openShopProductButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
                    if (!Program.launch(productLink.adminEditorUrl())) {
                        MessageDialog.openError(openShopProductButton.getShell(), "Im Webshop bearbeiten",
                                "Der PrestaShop-Produkteditor konnte nicht geöffnet werden.");
                    }
                }));
            } catch (IllegalArgumentException e) {
                openShopProductButton.setToolTipText("PrestaShop-Admin-URL ist nicht gültig.");
            }
        }
    }

    /**
     * Enables/disables every FKT_PRODUCTWEBSHOP-bound field at once,
     * following the "im Shop anbieten" checkbox - mirrors how
     * {@code checkboxStockManaged} above toggles {@code textQuantity}, just
     * for the whole set of webshop fields.
     */
    private void setWebshopFieldsEnabled(final boolean enabled) {
        textShopPrice.getControl().setEnabled(enabled);
        textShopStockQuantity.setEnabled(enabled);
        comboShopStockStatus.setEnabled(enabled);
        comboShopBackorders.setEnabled(enabled);
        textShopLowStockAmount.setEnabled(enabled);
        textDeliveryTime.setEnabled(enabled);
    }

    /**
     * The first time a product is offered in the shop (no {@link ProductWebshop}
     * row existed yet when this editor opened), seed a few webshop fields from
     * the product's own data instead of leaving them blank - the regular price
     * and current stock are the obvious starting point for what the shop
     * should show. Mutates {@link #editorProductWebshop} directly rather than
     * the widgets: it's already bound (see {@link #bindWebshopFields()}), so
     * the bidirectional binding reflects the new values in the UI itself.
     * Only touches fields that are still empty, so re-checking the box later
     * in the same session never clobbers something the user already typed.
     * <p>
     * Stock quantity/status are only seeded for a stock-managed article ({@link
     * Product#getStockManaged()}) - a not-managed one (e.g. a service/maintenance package)
     * has no {@link Product#getQuantity()} to seed from in the first place (see
     * checkboxStockManaged's listener above, which clears it to {@code null} rather than
     * 0.0 exactly so it isn't mistaken for "really zero stock"), and defaulting its shop
     * stock status to "outofstock" here would be actively wrong for what WooCommerce should
     * treat as a virtual product with no stock tracking at all, not a physical one that
     * merely happens to be depleted.
     */
    private void prefillWebshopDefaults() {
        if (editorProductWebshop.getShopPrice() == null && editorProduct.getPrice1() != null) {
            // WooCommerce receives net prices; product VAT is applied by the shop.
            editorProductWebshop.setShopPrice(editorProduct.getPrice1());
        }
        if (Boolean.TRUE.equals(editorProduct.getStockManaged())) {
            if (editorProductWebshop.getShopStockQuantity() == null && editorProduct.getQuantity() != null) {
                editorProductWebshop.setShopStockQuantity(editorProduct.getQuantity());
            }
            if (StringUtils.isBlank(editorProductWebshop.getShopStockStatus())) {
                final boolean inStock = editorProduct.getQuantity() != null && editorProduct.getQuantity() > 0;
                editorProductWebshop.setShopStockStatus(inStock ? "instock" : "outofstock");
            }
        }
    }

    private Map<Integer, PriceBlock> createPriceBlocks() {
        if (priceBlocks == null) {
            int index = 0;
            priceBlocks = new HashMap<>();
            priceBlocks.put(index++, new PriceBlock(Product_.block1, Product_.price1));
            priceBlocks.put(index++, new PriceBlock(Product_.block2, Product_.price2));
            priceBlocks.put(index++, new PriceBlock(Product_.block3, Product_.price3));
            priceBlocks.put(index++, new PriceBlock(Product_.block4, Product_.price4));
            priceBlocks.put(index++, new PriceBlock(Product_.block5, Product_.price5));
        }
        return priceBlocks;
    }

    @Override
    protected void bindModel() {
        part.getTransientData().put(BIND_MODE_INDICATOR, Boolean.TRUE);

        bindModelValue(editorProduct, textItemNr, Product_.itemNumber.getName(), 64);
        bindModelValue(editorProduct, textName, Product_.name.getName(), 64);
        fillAndBindCategoryCombo();

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        UpdateValueStrategy<Object, String> numbertoStringStrategy = UpdateValueStrategy.create(NumberToStringConverter.fromLong(numberFormat, false));
        UpdateValueStrategy<Object, Long> stringToNumberStrategy = UpdateValueStrategy.create(StringToNumberConverter.toLong(false));
        Binding binding = bindModelValue(editorProduct, textGtin, Product_.gtin.getName(), 64, stringToNumberStrategy, numbertoStringStrategy);
        ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);

        bindModelValue(editorProduct, textSupplierItemNumber, Product_.supplierItemNumber.getName(), 64);
        final UpdateValueStrategy<String, Long> replacementToModel = UpdateValueStrategy.create(new Converter<String, Long>(String.class, Long.class) {
            @Override
            public Long convert(final String value) {
                final String itemNumber = StringUtils.trimToEmpty(value);
                if (itemNumber.isEmpty()) {
                    return null;
                }
                if ("0".equals(itemNumber)) {
                    return 0L;
                }
                final Product replacement = productsDAO.findByItemNumber(itemNumber);
                return replacement == null ? null : replacement.getId();
            }
        });
        replacementToModel.setBeforeSetValidator(value -> {
            final String error = replacementValidationError(textReplacedByProductId.getText());
            return error == null ? ValidationStatus.ok() : ValidationStatus.error(error);
        });
        final UpdateValueStrategy<Long, String> replacementToText = UpdateValueStrategy.create(new Converter<Long, String>(Long.class, String.class) {
            @Override
            public String convert(final Long id) {
                if (id == null) {
                    return "";
                }
                if (id == 0L) {
                    return "0";
                }
                final Product replacement = productsDAO.findById(id);
                return replacement == null ? "" : replacement.getItemNumber();
            }
        });
        final Binding replacementBinding = bindModelValue(editorProduct, textReplacedByProductId, "replacedByProductId", 20,
                replacementToModel, replacementToText);
        ControlDecorationSupport.create(replacementBinding, SWT.TOP | SWT.LEFT);
        bindModelValue(editorProduct, textDescription, Product_.description.getName(), 0); // no limit
        if (useQuantityUnit) {
            UpdateValueStrategy<Text, String> strategy = new UpdateValueStrategy<>();
            strategy.setBeforeSetValidator((final Object value) -> {
                String quantityUnit = (String) value;
                if (isQuantityUnitValid(quantityUnit)) {
                    return ValidationStatus.ok();
                } else {
                    return ValidationStatus.error(msg.editorProductFieldQuantityunitInvalid);
                }
            });

            Binding bindingQtu = bindModelValue(editorProduct, textQuantityUnit, Product_.quantityUnit.getName(), 80, strategy, null);
            ControlDecorationSupport.create(bindingQtu, SWT.TOP | SWT.LEFT);
        }
        // bind the scaled prices widgets
        for (int i = 0; i < grossText.length; i++) {
            bindModelValue(editorProduct, textBlock[i], priceBlocks.get(i).getBlock().getName(), 8);
            if (useGross && !useNet) {
                bindModelValue(editorProduct, grossText[i].getNetText().getNetText(), priceBlocks.get(i).getPrice().getName(), 16);
            } else {
                bindModelValue(editorProduct, netText[i].getNetText(), priceBlocks.get(i).getPrice().getName(), 16);
            }
        }

        bindModelValue(editorProduct, costPrice, Product_.costPrice.getName(), 16);
        fillAndBindVatCombo();
        bindModelValue(editorProduct, textWeight, Product_.weight.getName(), 16);
        bindModelValue(editorProduct, textQuantity, Product_.quantity.getName(), 0);
        if (useQuantity) {
            bindModelValue(editorProduct, checkboxStockManaged, Product_.stockManaged.getName());
            // the checkbox binding above doesn't fire a widget SelectionEvent, so the
            // quantity field's enabled state has to be synced explicitly after binding
            textQuantity.getControl().setEnabled(checkboxStockManaged.getSelection());
        }
        bindModelValue(editorProduct, udf01, Product_.cdf01.getName(), 64);
        bindModelValue(editorProduct, udf02, Product_.cdf02.getName(), 64);
        bindModelValue(editorProduct, udf03, Product_.cdf03.getName(), 64);
        bindModelValue(editorProduct, note, Product_.note.getName(), 2048);

        // TODO das sollte perspektivisch über binding abgehandelt werden!
        // bindModelValue(editorProduct, labelProductPicture, Product_.picture.getName(), null,null);

        bindWebshopFields();

        part.getTransientData().remove(BIND_MODE_INDICATOR);
    }

    /**
     * Binds the "Webshop" group's controls to {@link #editorProductWebshop} -
     * a different entity than the rest of this editor's fields, which is
     * fine since {@code bindModelValue}'s overloads (including the
     * {@code FormattedText} one, since it was fixed to key off the target's
     * own runtime class instead of a hardcoded {@code Product}) all key off
     * the passed-in target's own class. {@code textShopPrice}/
     * {@code textShopPrice} uses the {@code FormattedText}+
     * {@code MoneyFormatter} overload so it displays as a Euro amount like
     * every other price field in this editor; the remaining numeric fields
     * here are plain {@code Text} with a generic (non-currency) number
     * strategy.
     */
    private void bindWebshopFields() {
        checkboxInShop.setSelection(productHadWebshopData);
        setWebshopFieldsEnabled(productHadWebshopData);

        final NumberFormat doubleFormat = NumberFormat.getNumberInstance();
        final UpdateValueStrategy<Object, String> doubleToStringStrategy = UpdateValueStrategy.create(NumberToStringConverter.fromDouble(doubleFormat, false));
        final UpdateValueStrategy<Object, Double> stringToDoubleStrategy = UpdateValueStrategy.create(StringToNumberConverter.toDouble(false));
        final UpdateValueStrategy<Object, String> intToStringStrategy = UpdateValueStrategy.create(NumberToStringConverter.fromInteger(doubleFormat, false));
        final UpdateValueStrategy<Object, Integer> stringToIntStrategy = UpdateValueStrategy.create(StringToNumberConverter.toInteger(false));

        bindModelValue(editorProductWebshop, textShopPrice, ProductWebshop_.shopPrice.getName(), 16);
        bindModelValue(editorProductWebshop, textShopStockQuantity, ProductWebshop_.shopStockQuantity.getName(), 16, stringToDoubleStrategy,
                doubleToStringStrategy);
        bindModelValue(editorProductWebshop, textShopLowStockAmount, ProductWebshop_.shopLowStockAmount.getName(), 8, stringToIntStrategy, intToStringStrategy);
        bindModelValue(editorProductWebshop, textDeliveryTime, ProductWebshop_.deliveryTime.getName(), 255);

        bindShopOption(comboShopStockStatus, ProductWebshop_.shopStockStatus.getName(),
                editorProductWebshop.getShopStockStatus(),
                new String[] { "", "instock", "outofstock", "onbackorder" },
                new String[] { msg.editorProductFieldShopoptionUnset, msg.editorProductFieldShopstatusInstock,
                        msg.editorProductFieldShopstatusOutofstock, msg.editorProductFieldShopstatusOnbackorder });
        bindShopOption(comboShopBackorders, ProductWebshop_.shopBackorders.getName(),
                editorProductWebshop.getShopBackorders(),
                new String[] { "", "no", "notify", "yes" },
                new String[] { msg.editorProductFieldShopoptionUnset, msg.editorProductFieldShopbackordersNo,
                        msg.editorProductFieldShopbackordersNotify, msg.editorProductFieldShopbackordersYes });
    }

    private void bindShopOption(final Combo combo, final String property, final String currentCode,
            final String[] codes, final String[] captions) {
        final List<String> codeList = new ArrayList<>(Arrays.asList(codes));
        final List<String> captionList = new ArrayList<>(Arrays.asList(captions));
        if (StringUtils.isNotBlank(currentCode) && !codeList.contains(currentCode)) {
            codeList.add(currentCode);
            captionList.add(currentCode);
        }
        combo.setItems(captionList.toArray(new String[0]));
        final UpdateValueStrategy<String, String> toModel = UpdateValueStrategy.create(
                new Converter<String, String>(String.class, String.class) {
                    @Override
                    public String convert(final String caption) {
                        return codeList.get(Math.max(0, captionList.indexOf(caption)));
                    }
                });
        final UpdateValueStrategy<String, String> toDisplay = UpdateValueStrategy.create(
                new Converter<String, String>(String.class, String.class) {
                    @Override
                    public String convert(final String code) {
                        return captionList.get(Math.max(0, codeList.indexOf(StringUtils.defaultString(code))));
                    }
                });
        bindModelValue(editorProductWebshop, combo, property, toModel, toDisplay);
    }

    /**
     * @param quantityUnit
     * @return
     */
    private boolean isQuantityUnitValid(final String quantityUnit) {
        boolean retval = false;
        if (StringUtils.isBlank(quantityUnit) || quantityUnit.matches("(?U)\\w+\\p{No}*\\.*")) {
            retval = true;
        } else {
            // Pattern: n#name1|n#name2|n#name3
            // only pairs of such blocks are valid!
            if (quantityUnit.matches("(?U)(\\d+#\\w+\\p{No}*\\.*\\|?)+")) {
                retval = true;
            }
        }
        return retval;
    }

    private void fillAndBindVatCombo() {
        VAT tmpVat = editorProduct.getVat();
        List<VAT> allVATs = vatDao.findAll();
        ComboViewer comboViewer = new ComboViewer(comboVat);
        comboViewer.setContentProvider(new EntityComboProvider());
        comboViewer.setLabelProvider(new EntityLabelProvider());
        comboViewer.addSelectionChangedListener(event -> {

            // Handle selection changed event 
            IStructuredSelection structuredSelection = event.getStructuredSelection();
            if (!structuredSelection.isEmpty()) {
                // Get the first element ...
                // Get the selected VAT
                final VAT selectedVat = (VAT) structuredSelection.getFirstElement();

                // Recalculate all the price values
                for (int i = 0; i < scaledPrices; i++) {

                    // Recalculate the price values if gross is selected,
                    // So the gross value will stay constant.
                    if (!useNet) {
                    	final MonetaryAmount newNetValue = DataUtils.getInstance().calculateNetFromGross(
                    			(Double)grossText[i].getGrossText().getValue(), 
                    			selectedVat.getTaxValue());
						grossText[i].setNetValue(newNetValue);
                    	grossText[i].getNetText().setNetValue(newNetValue);
                    }

                    // Update net and gross text widget
                    if (netText[i] != null) {
                        netText[i].setVatValue(selectedVat.getTaxValue());
                    }
                    if (grossText[i] != null) {
                        grossText[i].setVatValue(selectedVat.getTaxValue());
                    }
                }
            }
        });

        // Create a JFace combo viewer for the VAT list
        comboViewer.setInput(allVATs);
        editorProduct.setVat(tmpVat);

        UpdateValueStrategy<VAT, String> vatModel2Target = UpdateValueStrategy.create(new EntityConverter<>(VAT.class));
        UpdateValueStrategy<String, VAT> target2VatModel = UpdateValueStrategy.create(new StringToEntityConverter<>(allVATs, VAT.class));
        bindModelValue(editorProduct, comboVat, Product_.vat.getName(), target2VatModel, vatModel2Target);
    }

    /**
     * If an entity is deleted via list view we have to close a possibly open
     * editor window. Since this is triggered by a UIEvent we named this method
     * "handle*".
     */
    @Inject
    @Optional
    public void handleForceClose(@UIEventTopic(ProductEditor.EDITOR_ID + UIEvents.TOPIC_SEP + "forceClose") final Event event) {
        // the event has already all given params in it since we created them as Map
        String targetDocumentName = (String) event.getProperty(Editor.OBJECT_ID);
        // at first we have to check if the message is for us
        if (!StringUtils.equals(targetDocumentName, editorProduct.getName())) {
            // if not, silently ignore this event
            return;
        }
        partService.hidePart(part, true);
    }

    /**
     * creates the combo box for the Product category
     * 
     * @param parent
     */
    private void fillAndBindCategoryCombo() {
        // Collect all category strings as a sorted Set
        final TreeSet<ProductCategory> categories = new TreeSet<>(new CategoryComparator<>());
        categories.addAll(productCategoriesDAO.findAll());

        ComboViewer viewer = new ComboViewer(comboCategory);
        viewer.setContentProvider(new ArrayContentProvider() {
            @Override
            public Object[] getElements(final Object inputElement) {
                return categories.toArray();
            }
        });

        ProductCategory tmpCat = editorProduct.getCategories();
        // Add all categories to the combo
        viewer.setInput(categories);
        viewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(final Object element) {
                return element instanceof ProductCategory ? CommonConverter.getCategoryName((ProductCategory) element, "") : null;
            }
        });
        editorProduct.setCategories(tmpCat);

        UpdateValueStrategy<ProductCategory, String> productCatModel2Target = UpdateValueStrategy.create(new CategoryConverter<>(ProductCategory.class));
        UpdateValueStrategy<String, ProductCategory> target2productCatModel = UpdateValueStrategy
                .create(new StringToCategoryConverter<>(categories, ProductCategory.class));
        bindModelValue(editorProduct, comboCategory, Product_.categories.getName(), target2productCatModel, productCatModel2Target);
    }

    /**
     * Test, if there is a document with the same number
     * 
     * @return TRUE, if one with the same number is found
     */
    private boolean thereIsOneWithSameNumber() {

        // Cancel, if there is already a document with the same ID
        if (productsDAO.existsOther(editorProduct)) {
            // Display an error message
            // T: Title of the dialog that appears if the item/product number is not valid.
            MessageDialog.openError(top.getShell(), msg.editorProductErrorItemnumberTitle, msg.editorProductWarningDuplicatearticle + " " + textName.getText());

            return true;
        }
        // Cancel, if there is already a document with the same ID
        if (productsDAO.existsOther(editorProduct)) {
            // Display an error message
            MessageDialog.openError(top.getShell(), msg.editorDocumentErrorDocnumberTitle,
                    msg.editorDocumentDialogWarningDocumentexists + " " + textName.getText());
            return true;
        }

        return false;
    }

    /**
     * Returns, if save is allowed
     * 
     * @return TRUE, if save is allowed
     * 
     * @see com.sebulli.fakturama.editors.Editor#saveAllowed()
     */
    @Override
    protected boolean saveAllowed() {
        // Save is allowed, if there is no product with the same number
        return !thereIsOneWithSameNumber();
    }

    @Override
    protected MDirtyable getMDirtyablePart() {
        return part;
    }

    @Override
    protected Class<Product> getModelClass() {
        return Product.class;
    }

    @Override
    protected String getEditorID() {
        return "Product";
    }

    private final class ReturnKeyAdapter extends KeyAdapter {
        private final Text control;

        public ReturnKeyAdapter(final Text control) {
            this.control = control;
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            if (e.keyCode == 13 || e.keyCode == SWT.KEYPAD_CR) {
                control.traverse(SWT.TRAVERSE_TAB_NEXT);
            }
        }
    }

    /**
     * Helper class for connecting blocks with prices in a correct order.
     * 
     */
    class PriceBlock {
        private SingularAttribute<Product, Integer> block;
        private SingularAttribute<Product, Double> price;

        /**
         * @param block
         * @param price
         */
        public PriceBlock(final SingularAttribute<Product, Integer> block, final SingularAttribute<Product, Double> price) {
            this.block = block;
            this.price = price;
        }

        /**
         * @return the block
         */
        public final SingularAttribute<Product, Integer> getBlock() {
            return block;
        }

        /**
         * @return the price
         */
        public final SingularAttribute<Product, Double> getPrice() {
            return price;
        }
    }

}
