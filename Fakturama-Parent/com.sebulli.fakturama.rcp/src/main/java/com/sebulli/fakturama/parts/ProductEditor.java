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
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.text.NumberFormat;
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
import org.eclipse.core.databinding.conversion.text.NumberToStringConverter;
import org.eclipse.core.databinding.conversion.text.StringToNumberConverter;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
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
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.javamoney.moneta.Money;
import org.osgi.service.event.Event;

import com.sebulli.fakturama.converter.CommonConverter;
import com.sebulli.fakturama.dao.ProductCategoriesDAO;
import com.sebulli.fakturama.dao.ProductWebshopDAO;
import com.sebulli.fakturama.dao.ProductsDAO;
import com.sebulli.fakturama.dao.VatsDAO;
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
    private Text textGtin, textSupplierItemNumber;
    private Text textDescription;
    private Text udf01, udf02, udf03;
    private Combo comboVat;
    private FormattedText textWeight;
    private FormattedText textQuantity;
    private Button checkboxStockManaged;
    private FormattedText costPrice;
    private Text textQuantityUnit, allowance;
    private CCombo comboCategory;
    private ProductCategory oldCat;
    private FakturamaPictureControl labelProductPicture;
    private Composite photoComposite;
    private Text note;

    // Webshop overlay (FKT_PRODUCTWEBSHOP) - the "im Shop" checkbox and its fields
    private ProductWebshop editorProductWebshop;
    // whether a persisted, non-deleted ProductWebshop row existed for this product when the
    // editor was opened - needed at save time to tell "never had shop data, still doesn't" (skip
    // saving anything) apart from "had shop data, user just unchecked it" (soft-delete the row)
    private boolean productHadWebshopData;
    private Button checkboxInShop;
    private Composite webshopFieldsComposite;
    private Text textShopPrice, textShopSalePrice, textShopStockQuantity, textShopLowStockAmount, textDeliveryTime;
    private CDateTime dtShopSaleFrom, dtShopSaleTo;
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

        String tmpObjId = (String) part.getTransientData().get(CallEditor.PARAM_OBJ_ID);
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

		Image image = null;
		// Display the picture, if a product picture is set.
		if (editorProduct.getPicture() != null) {

			// Load the image, based on the picture name, save to image registry
			labelProductPicture.setMaxImageWidth(250);
			image = JFaceResources.getImageRegistry().get("prodimg_" + editorProduct.getItemNumber());
			if (image == null) {
				try (ByteArrayInputStream bais = new ByteArrayInputStream(editorProduct.getPicture())) {
					ImageData imageData = new ImageData(bais);
					image = new Image(Display.getCurrent(), imageData);
					JFaceResources.getImageRegistry().put("prodimg_" + editorProduct.getItemNumber(), image);
				} catch (Exception e) {
					// catch all exceptions here since we check for errors later
					log.error(e, "Icon not found");
				}
			}
			labelProductPicture.setImageByteArray(editorProduct.getPicture());
		} else {
			labelProductPicture.setImageByteArray(null);
		}
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

        // Create the top Composite
        top = new Composite(scrollcomposite, SWT.SCROLLBAR_OVERLAY | SWT.NONE); //was parent before 
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(top);

        scrollcomposite.setContent(top);
        scrollcomposite.setMinSize(1000, 600); // 2nd entry should be adjusted to higher value when new fields will be added to composite 
        scrollcomposite.setExpandHorizontal(true);
        scrollcomposite.setExpandVertical(true);
        scrollcomposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

        // Create an invisible container for all hidden components
        Composite invisible = new Composite(top, SWT.NONE);
        invisible.setVisible(false);
        GridDataFactory.fillDefaults().hint(0, 0).span(2, 1).applyTo(invisible);
        GridLayoutFactory.swtDefaults().margins(0, 0).applyTo(invisible);

        // Add context help reference 
        //		PlatformUI.getWorkbench().getHelpSystem().setHelp(top, ContextHelpConstants.PRODUCT_EDITOR);

        // Description and picture side by side, but as a SashForm instead of two
        // fixed GridLayout columns - lets the user drag to give the (now much
        // bigger, see FakturamaPictureControl#init) picture more or less room; the
        // picture shrinks/grows with its pane since FakturamaPictureControl scales
        // to its available width via setMaxImageWidth/-Height.
        SashForm descriptionAndPictureSash = new SashForm(top, SWT.HORIZONTAL);
        GridDataFactory.fillDefaults().grab(true, true).span(2, 1).applyTo(descriptionAndPictureSash);
        // default.css styles every SashForm with a light blue background (#c1d5ef) -
        // fine for the one that already used it, but this one sits directly behind
        // the description/picture groups with nothing opaque covering the gutter/
        // edges, so it bled through as an unwanted all-over blue tint. The CSS class
        // wins even after the CSS engine re-styles (dynamic CSS is on, so a plain
        // setBackground() call gets overridden again) - see .no-blue-sash in
        // default.css. Also set directly for the first paint, before the engine has
        // run at all - same idea as photoComposite's own setBackground() below.
        descriptionAndPictureSash.setData("org.eclipse.e4.ui.css.CssClassName", "no-blue-sash");
        descriptionAndPictureSash.setBackground(new Color(descriptionAndPictureSash.getDisplay(), 246, 245, 244));

        // Group: Product description
        Group productDescGroup = new Group(descriptionAndPictureSash, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(productDescGroup);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(productDescGroup);

        productDescGroup.setText(msg.commonFieldDescription);

        // Item number
        Label labelItemNr = new Label(useItemNr ? productDescGroup : invisible, SWT.NONE);
        labelItemNr.setText(msg.exporterDataItemnumber);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelItemNr);
        textItemNr = new Text(useItemNr ? productDescGroup : invisible, SWT.BORDER);
        textItemNr.addKeyListener(new ReturnKeyAdapter(textItemNr));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textItemNr);

        // Product name
        Label labelName = new Label(productDescGroup, SWT.NONE);
        labelName.setText(msg.commonFieldName);
        labelName.setToolTipText(msg.editorProductNameTooltip);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelName);
        textName = new Text(productDescGroup, SWT.BORDER);
        textName.setToolTipText(labelName.getToolTipText());
        textName.addKeyListener(new ReturnKeyAdapter(textName));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textName);

        // Product category
        Label labelCategory = new Label(productDescGroup, SWT.NONE);
        labelCategory.setText(msg.commonFieldCategory);
        labelCategory.setToolTipText(msg.editorProductCategoryTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelCategory);

        comboCategory = new CCombo(productDescGroup, SWT.BORDER);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(comboCategory);

        // GTIN
        Label labelGtin = new Label(productDescGroup, SWT.NONE);
        labelGtin.setText(msg.editorProductFieldGtin);
        //		labelGtin.setToolTipText(msg.editorProductFieldGtinTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelGtin);

        textGtin = new Text(productDescGroup, SWT.BORDER);
        textGtin.addKeyListener(new ReturnKeyAdapter(textGtin));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textGtin);

        // supplier's product number
        Label labelSupplierItemNumber = new Label(productDescGroup, SWT.NONE);
        labelSupplierItemNumber.setText(msg.editorProductFieldSupplierItemnumber);
        labelSupplierItemNumber.setToolTipText(msg.editorProductFieldSupplierItemnumberTooltip);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelSupplierItemNumber);

        textSupplierItemNumber = new Text(productDescGroup, SWT.BORDER);
        textSupplierItemNumber.addKeyListener(new ReturnKeyAdapter(textSupplierItemNumber));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textSupplierItemNumber);

        // for correct tab-order (see FAK-465)
        Control nextWidget = null;

        // Product description
        Label labelDescription = new Label(useDescription ? productDescGroup : invisible, SWT.NONE);
        labelDescription.setText(msg.commonFieldDescription);
        //T: Tool Tip Text
        labelDescription.setToolTipText(msg.editorProductAdddescriptionTooltip);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelDescription);
        textDescription = new Text(useDescription ? productDescGroup : invisible, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        //		textDescription.setText(DataUtils.makeOSLineFeeds(editorProduct.getStringValueByKey("description")));
        textDescription.setToolTipText(labelDescription.getToolTipText());
        GridDataFactory.fillDefaults().hint(10, 80).grab(true, false).applyTo(textDescription);

        // Product quantity
        Label labelQuantityUnit = new Label(useQuantityUnit ? productDescGroup : invisible, SWT.NONE);
        //T: Product Editor - Label Product quantity unit
        labelQuantityUnit.setText(msg.editorProductFieldQuantityunitName);
        GridDataFactory.defaultsFor(labelQuantityUnit).indent(-20, 0).align(SWT.END, SWT.CENTER).applyTo(labelQuantityUnit);

        if (useQuantityUnit) {
            textQuantityUnit = new Text(productDescGroup, SWT.BORDER);
            textQuantityUnit.addKeyListener(new ReturnKeyAdapter(textQuantityUnit));
            nextWidget = textQuantityUnit;
        } else {
            textQuantityUnit = new Text(invisible, SWT.BORDER);
        }
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textQuantityUnit);

        // Product price
        Label labelPrice = new Label(productDescGroup, SWT.NONE);

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
        Composite pricetable = new Composite(productDescGroup, SWT.NONE);
        GridLayoutFactory.swtDefaults().margins(0, 0).numColumns((scaledPrices > 1) ? (useNet && !useGross) ? 3 : 4 : 2).applyTo(pricetable);

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
        context.set(Constants.CONTEXT_VATVALUE, editorProduct.getVat().getTaxValue());
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

        // cost price (ALWAYS a net price!)
        Label labelCostPrice = new Label(productDescGroup, SWT.NONE);
        labelCostPrice.setText(msg.editorProductFieldCostprice);

        Composite costAndAllowance = new Composite(productDescGroup, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(3).margins(0, 0).applyTo(costAndAllowance);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(costAndAllowance);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelCostPrice);
        costPrice = new FormattedText(costAndAllowance, SWT.BORDER);
        MoneyFormatter costPriceFormatter = ContextInjectionFactory.make(MoneyFormatter.class, context);
        costPrice.setFormatter(costPriceFormatter);
        costPrice.getControl().addKeyListener(new ReturnKeyAdapter(costPrice.getControl()));
        GridDataFactory.swtDefaults().hint(120, SWT.DEFAULT).applyTo(costPrice.getControl());

        // cost price (ALWAYS a net price!)
        Label labelAllowance = new Label(costAndAllowance, SWT.NONE);
        labelAllowance.setText(msg.editorProductFieldAllowance);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelAllowance);

        allowance = new Text(costAndAllowance, SWT.BORDER);
        allowance.addKeyListener(new ReturnKeyAdapter(allowance));
        GridDataFactory.swtDefaults().hint(120, SWT.DEFAULT).applyTo(allowance);

        // product VAT
        Label labelVat = new Label(useVat ? productDescGroup : invisible, SWT.NONE);
        labelVat.setText(msg.commonFieldVat);
        labelVat.setToolTipText(msg.editorProductVatName);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelVat);
        //		
        // VAT combo list
        comboVat = new Combo(useVat ? productDescGroup : invisible, SWT.BORDER | SWT.READ_ONLY);
        comboVat.setToolTipText(labelVat.getToolTipText());
        //		GridDataFactory.swtDefaults().grab(true, false).applyTo(comboVat);

        // Product weight
        Label labelWeight = new Label(useWeight ? productDescGroup : invisible, SWT.NONE);
        //T: Product Editor - Label Product Weight with unit (kg)
        labelWeight.setText(msg.exporterDataWeight);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelWeight);
        textWeight = new FormattedText(useWeight ? productDescGroup : invisible, SWT.BORDER);
        textWeight.setFormatter(new DoubleFormatter());
        textWeight.getControl().addKeyListener(new ReturnKeyAdapter(textWeight.getControl()));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textWeight.getControl());

        // Product quantity
        Label labelQuantity = new Label(useQuantity ? productDescGroup : invisible, SWT.NONE);
        //T: Product Editor - Label Product quantity
        labelQuantity.setText(msg.commonFieldQuantity);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelQuantity);
        Composite quantityComposite = new Composite(useQuantity ? productDescGroup : invisible, SWT.NONE);
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(quantityComposite);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(quantityComposite);
        if (useQuantity) {
            DoubleValueFormatter quantityFormatter = ContextInjectionFactory.make(DoubleValueFormatter.class, context);
            textQuantity = new FormattedText(quantityComposite, SWT.BORDER);
            textQuantity.setFormatter(quantityFormatter);
            textQuantity.getControl().addKeyListener(new ReturnKeyAdapter(textQuantity.getControl()));
            textQuantity.getControl().setToolTipText(msg.commonFieldQuantityTooltip);
            nextWidget = textQuantityUnit;

            // Track inventory: enables/disables the stock quantity field. Turning it
            // off also clears a non-zero stock value (after confirming, since that's
            // silently throwing away a real number) - a zero/null value is cleared
            // straight away, nothing to confirm there.
            checkboxStockManaged = new Button(quantityComposite, SWT.CHECK);
            checkboxStockManaged.setText(msg.editorProductFieldStockmanagedName);
            checkboxStockManaged.setToolTipText(msg.editorProductFieldStockmanagedTooltip);
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
                                editorProduct.setQuantity(0.0);
                                textQuantity.setValue(0.0);
                            }
                        } else {
                            editorProduct.setQuantity(0.0);
                            textQuantity.setValue(0.0);
                        }
                    }
                    textQuantity.getControl().setEnabled(nowManaged);
                }
            });
        } else {
            textQuantity = new FormattedText(invisible, SWT.BORDER);
            checkboxStockManaged = new Button(invisible, SWT.CHECK);
        }
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textQuantity.getControl());

        // user defined fields
        // #1
        Label udf01Lbl = new Label(productDescGroup, SWT.NONE);
        udf01Lbl.setText(msg.editorProductFieldUdf01);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(udf01Lbl);
        udf01 = new Text(productDescGroup, SWT.BORDER);
        udf01.addKeyListener(new ReturnKeyAdapter(udf01));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(udf01);

        // #2
        Label udf02Lbl = new Label(productDescGroup, SWT.NONE);
        udf02Lbl.setText(msg.editorProductFieldUdf02);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(udf02Lbl);
        udf02 = new Text(productDescGroup, SWT.BORDER);
        udf02.addKeyListener(new ReturnKeyAdapter(udf02));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(udf02);

        // #3
        Label udf03Lbl = new Label(productDescGroup, SWT.NONE);
        udf03Lbl.setText(msg.editorProductFieldUdf03);

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(udf03Lbl);
        udf03 = new Text(productDescGroup, SWT.BORDER);
        udf03.addKeyListener(new ReturnKeyAdapter(udf03));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(udf03);

        // Group: Product picture
        Group productPictureGroup = new Group(usePicture ? descriptionAndPictureSash : invisible, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(1).applyTo(productPictureGroup);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(productPictureGroup);
        productPictureGroup.setText(msg.exporterDataPicture);

        if (usePicture) {
            restoreDescriptionPictureSashWeights(descriptionAndPictureSash);
            descriptionAndPictureSash.addDisposeListener(e -> saveDescriptionPictureSashWeights(descriptionAndPictureSash));
        }

        // The photo
        photoComposite = new Composite(productPictureGroup, SWT.BORDER);
        GridLayoutFactory.swtDefaults().margins(10, 10).numColumns(1).applyTo(photoComposite);
        GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false).applyTo(photoComposite);
        photoComposite.setBackground(new Color(null, 255, 255, 255));

        // The picture name label
        labelProductPicture = new FakturamaPictureControl(photoComposite);
        createAndSetDefaultImage();
        ContextInjectionFactory.inject(labelProductPicture, context);
        setPicture();

        labelProductPicture.addPropertyChangeListener(FakturamaPictureControl.IMAGE_BYTEARRAY_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                byte[] newImage = (byte[]) event.getNewValue();
                final String imageKey = "prodimg_" + Objects.toString(editorProduct.getItemNumber(), Long.toString(editorProduct.getId()));
                JFaceResources.getImageRegistry().remove(imageKey);
                // if image was deleted we use the default image
                editorProduct.setPicture(newImage);
                if (newImage != null) {
                    Display display = Display.getCurrent();
                    ByteArrayInputStream bais = new ByteArrayInputStream(newImage);
                    Image image = new Image(display, bais);	
                    JFaceResources.getImageRegistry().put(imageKey, image);
                }
                getMDirtyablePart().setDirty(true);
            }
        });

        // Product note
        Group noteGroup = new Group(top, SWT.NONE);
        noteGroup.setText(msg.editorContactLabelNotice);
        GridLayoutFactory.swtDefaults().applyTo(noteGroup);
        GridDataFactory.fillDefaults().span(2, 1).grab(true, true).applyTo(noteGroup);

        note = new Text(noteGroup, SWT.BORDER | SWT.MULTI);
        note.addKeyListener(new ReturnKeyAdapter(note));
        GridDataFactory.fillDefaults().grab(true, true).applyTo(note);

        createWebshopGroup(top);

        oldCat = editorProduct.getCategories();

        bindModel();
    }

    /**
     * Creates the "Webshop" group: an "im Shop" checkbox plus the
     * FKT_PRODUCTWEBSHOP fields (shop price/stock, sale window, delivery
     * time). The fields are only meaningful while the checkbox is checked,
     * so they're grouped into their own composite that gets enabled/disabled
     * together with it (see {@link #setWebshopFieldsEnabled(boolean)}).
     */
    private void createWebshopGroup(final Composite parent) {
        Group webshopGroup = new Group(parent, SWT.NONE);
        webshopGroup.setText(msg.editorProductLabelWebshop);
        GridLayoutFactory.swtDefaults().numColumns(1).applyTo(webshopGroup);
        GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(webshopGroup);

        checkboxInShop = new Button(webshopGroup, SWT.CHECK);
        checkboxInShop.setText(msg.editorProductFieldInshopName);
        checkboxInShop.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                setWebshopFieldsEnabled(checkboxInShop.getSelection());
            }
        });

        webshopFieldsComposite = new Composite(webshopGroup, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(2).margins(0, 0).applyTo(webshopFieldsComposite);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(webshopFieldsComposite);

        Label labelShopPrice = new Label(webshopFieldsComposite, SWT.NONE);
        labelShopPrice.setText(msg.editorProductFieldShoppriceName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopPrice);
        textShopPrice = new Text(webshopFieldsComposite, SWT.BORDER);
        textShopPrice.addKeyListener(new ReturnKeyAdapter(textShopPrice));
        GridDataFactory.swtDefaults().hint(120, SWT.DEFAULT).applyTo(textShopPrice);

        Label labelShopSalePrice = new Label(webshopFieldsComposite, SWT.NONE);
        labelShopSalePrice.setText(msg.editorProductFieldShopsalepriceName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopSalePrice);
        textShopSalePrice = new Text(webshopFieldsComposite, SWT.BORDER);
        textShopSalePrice.addKeyListener(new ReturnKeyAdapter(textShopSalePrice));
        GridDataFactory.swtDefaults().hint(120, SWT.DEFAULT).applyTo(textShopSalePrice);

        Label labelShopSaleFrom = new Label(webshopFieldsComposite, SWT.NONE);
        labelShopSaleFrom.setText(msg.editorProductFieldShopsalefromName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopSaleFrom);
        dtShopSaleFrom = new CDateTime(webshopFieldsComposite, CDT.BORDER | CDT.DROP_DOWN);
        dtShopSaleFrom.setFormat(CDT.DATE_MEDIUM);
        GridDataFactory.swtDefaults().hint(150, SWT.DEFAULT).applyTo(dtShopSaleFrom);

        Label labelShopSaleTo = new Label(webshopFieldsComposite, SWT.NONE);
        labelShopSaleTo.setText(msg.editorProductFieldShopsaletoName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopSaleTo);
        dtShopSaleTo = new CDateTime(webshopFieldsComposite, CDT.BORDER | CDT.DROP_DOWN);
        dtShopSaleTo.setFormat(CDT.DATE_MEDIUM);
        GridDataFactory.swtDefaults().hint(150, SWT.DEFAULT).applyTo(dtShopSaleTo);

        Label labelShopStockQuantity = new Label(webshopFieldsComposite, SWT.NONE);
        labelShopStockQuantity.setText(msg.editorProductFieldShopstockquantityName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopStockQuantity);
        textShopStockQuantity = new Text(webshopFieldsComposite, SWT.BORDER);
        textShopStockQuantity.addKeyListener(new ReturnKeyAdapter(textShopStockQuantity));
        GridDataFactory.swtDefaults().hint(120, SWT.DEFAULT).applyTo(textShopStockQuantity);

        Label labelShopStockStatus = new Label(webshopFieldsComposite, SWT.NONE);
        labelShopStockStatus.setText(msg.editorProductFieldShopstockstatusName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopStockStatus);
        comboShopStockStatus = new Combo(webshopFieldsComposite, SWT.BORDER | SWT.READ_ONLY);
        // WooCommerce's own fixed vocabulary for stock_status, kept untranslated since these are
        // exactly the values sent/received over the REST API - a blank first entry means "not set".
        comboShopStockStatus.setItems(new String[] { "", "instock", "outofstock", "onbackorder" });
        GridDataFactory.swtDefaults().hint(150, SWT.DEFAULT).applyTo(comboShopStockStatus);

        Label labelShopBackorders = new Label(webshopFieldsComposite, SWT.NONE);
        labelShopBackorders.setText(msg.editorProductFieldShopbackordersName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopBackorders);
        comboShopBackorders = new Combo(webshopFieldsComposite, SWT.BORDER | SWT.READ_ONLY);
        // WooCommerce's own fixed vocabulary for backorders - see comboShopStockStatus above.
        comboShopBackorders.setItems(new String[] { "", "no", "notify", "yes" });
        GridDataFactory.swtDefaults().hint(150, SWT.DEFAULT).applyTo(comboShopBackorders);

        Label labelShopLowStockAmount = new Label(webshopFieldsComposite, SWT.NONE);
        labelShopLowStockAmount.setText(msg.editorProductFieldShoplowstockamountName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelShopLowStockAmount);
        textShopLowStockAmount = new Text(webshopFieldsComposite, SWT.BORDER);
        textShopLowStockAmount.addKeyListener(new ReturnKeyAdapter(textShopLowStockAmount));
        GridDataFactory.swtDefaults().hint(120, SWT.DEFAULT).applyTo(textShopLowStockAmount);

        Label labelDeliveryTime = new Label(webshopFieldsComposite, SWT.NONE);
        labelDeliveryTime.setText(msg.editorProductFieldDeliverytimeName);
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelDeliveryTime);
        textDeliveryTime = new Text(webshopFieldsComposite, SWT.BORDER);
        textDeliveryTime.addKeyListener(new ReturnKeyAdapter(textDeliveryTime));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(textDeliveryTime);
    }

    /**
     * Enables/disables every field in {@link #webshopFieldsComposite} at
     * once, following the "im Shop" checkbox - mirrors how
     * {@code checkboxStockManaged} above toggles {@code textQuantity}, just
     * for a whole group of controls instead of one.
     */
    private void setWebshopFieldsEnabled(final boolean enabled) {
        for (final Control child : webshopFieldsComposite.getChildren()) {
            child.setEnabled(enabled);
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
        bindModelValue(editorProduct, allowance, Product_.allowance.getName(), 16);
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
     * fine since {@code bindModelValue}'s generic overloads key off the
     * passed-in target's own class (only its {@code FormattedText} overload
     * hardcodes {@code getModelClass()}==Product, which is why none of these
     * fields use {@code FormattedText}).
     */
    private void bindWebshopFields() {
        checkboxInShop.setSelection(productHadWebshopData);
        setWebshopFieldsEnabled(productHadWebshopData);

        final NumberFormat doubleFormat = NumberFormat.getNumberInstance();
        final UpdateValueStrategy<Object, String> doubleToStringStrategy = UpdateValueStrategy.create(NumberToStringConverter.fromDouble(doubleFormat, false));
        final UpdateValueStrategy<Object, Double> stringToDoubleStrategy = UpdateValueStrategy.create(StringToNumberConverter.toDouble(false));
        final UpdateValueStrategy<Object, String> intToStringStrategy = UpdateValueStrategy.create(NumberToStringConverter.fromInteger(doubleFormat, false));
        final UpdateValueStrategy<Object, Integer> stringToIntStrategy = UpdateValueStrategy.create(StringToNumberConverter.toInteger(false));

        bindModelValue(editorProductWebshop, textShopPrice, ProductWebshop_.shopPrice.getName(), 16, stringToDoubleStrategy, doubleToStringStrategy);
        bindModelValue(editorProductWebshop, textShopSalePrice, ProductWebshop_.shopSalePrice.getName(), 16, stringToDoubleStrategy, doubleToStringStrategy);
        bindModelValue(editorProductWebshop, textShopStockQuantity, ProductWebshop_.shopStockQuantity.getName(), 16, stringToDoubleStrategy,
                doubleToStringStrategy);
        bindModelValue(editorProductWebshop, textShopLowStockAmount, ProductWebshop_.shopLowStockAmount.getName(), 8, stringToIntStrategy, intToStringStrategy);
        bindModelValue(editorProductWebshop, textDeliveryTime, ProductWebshop_.deliveryTime.getName(), 255);

        bindModelValue(editorProductWebshop, dtShopSaleFrom, ProductWebshop_.shopSaleFrom.getName());
        bindModelValue(editorProductWebshop, dtShopSaleTo, ProductWebshop_.shopSaleTo.getName());

        bindModelValue(editorProductWebshop, comboShopStockStatus, ProductWebshop_.shopStockStatus.getName());
        bindModelValue(editorProductWebshop, comboShopBackorders, ProductWebshop_.shopBackorders.getName());
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

    /**
     * Restores the user's last chosen description/picture split, persisted via
     * {@link #saveDescriptionPictureSashWeights(SashForm)}. Falls back to a 3:2
     * ratio (a bit more room for the description form than the picture) the first
     * time, or if the saved value doesn't match the current number of panes.
     */
    private void restoreDescriptionPictureSashWeights(final SashForm sashForm) {
        final String[] saved = getDialogSettings("SASH").getArray("PRODUCT_DESCRIPTION_PICTURE_SASH");
        int[] weights = new int[] { 3, 2 };
        if (saved != null && saved.length == sashForm.getChildren().length) {
            try {
                weights = Arrays.stream(saved).mapToInt(Integer::parseInt).toArray();
            } catch (final NumberFormatException e) {
                weights = new int[] { 3, 2 };
            }
        }
        sashForm.setWeights(weights);
    }

    private void saveDescriptionPictureSashWeights(final SashForm sashForm) {
        final String[] weights = Arrays.stream(sashForm.getWeights()).mapToObj(Integer::toString).toArray(String[]::new);
        getDialogSettings("SASH").put("PRODUCT_DESCRIPTION_PICTURE_SASH", weights);
    }

    private IDialogSettings getDialogSettings(final String section) {
        if (settings.getSection(section) == null) {
            settings.addNewSection(section);
        }
        return settings.getSection(section);
    }
}
