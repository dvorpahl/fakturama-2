/*******************************************************************************
 * Copyright (c) 2014 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package com.sebulli.fakturama.views.datatable.products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILazyContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.sebulli.fakturama.dao.AbstractDAO;
import com.sebulli.fakturama.dao.ProductCategoriesDAO;
import com.sebulli.fakturama.dao.ProductsDAO;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.handlers.CallEditor;
import com.sebulli.fakturama.handlers.CommandIds;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.model.Product;
import com.sebulli.fakturama.model.ProductCategory;
import com.sebulli.fakturama.parts.DocumentEditor;
import com.sebulli.fakturama.parts.Editor;
import com.sebulli.fakturama.parts.ProductEditor;
import com.sebulli.fakturama.views.datatable.AbstractViewDataTable;
import com.sebulli.fakturama.views.datatable.layer.EntityGridListLayer;
import com.sebulli.fakturama.views.datatable.layer.PagedEntityEventList;
import com.sebulli.fakturama.views.datatable.tree.ui.TopicTreeViewer;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeCategoryLabelProvider;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

import ca.odell.glazedlists.GlazedLists;

/**
 * Builds the Product list table - and also backs the product-picker dialog ({@code
 * SelectProductDialog}, DIALOG mode via {@code Constants.PROPERTY_PRODUCTS_CLICKHANDLER}), the
 * "Artikelauswahl" the user asked to unify along with this main list.
 * <p>
 * Uses the same native SWT.VIRTUAL {@link Table} + JFace {@link ILazyContentProvider} approach as
 * {@code DocumentsListTable} - see its javadoc for why (NatTable/GlazedLists forced full
 * materialisation of a server-paginated list on reload). {@code productsDAO.findAll(true)} used to
 * load the *entire* product table into a GlazedLists EventList on every open/refresh; now only
 * {@link #PRODUCT_BATCH_SIZE}-row pages are loaded on demand (see {@link PagedEntityEventList}).
 */
public class ProductListTable extends AbstractViewDataTable<Product, ProductCategory> {

    // ID of this view
    public static final String ID = "fakturama.views.productTable";

    protected static final String POPUP_ID = "com.sebulli.fakturama.productlist.popup";
    public static final String SELECTED_PRODUCT_ID = "fakturama.productlist.selectedproductid";

    private static final int PRODUCT_BATCH_SIZE = 30;
    private static final int SEARCH_DEBOUNCE_MS = 300;

    @Inject
    private UISynchronize sync;

    @Inject
    protected IEclipseContext context;

    @Inject
    private ProductsDAO productsDAO;

    @Inject
    private ProductCategoriesDAO productCategoriesDAO;

    @Inject
    private com.sebulli.fakturama.dao.ProductWebshopDAO productWebshopDAO;

    @Inject
    private IPreferenceStore prefStore;

    @Inject
    private com.sebulli.fakturama.misc.INumberFormatterService numberFormatterService;

    private PagedEntityEventList<Product> productListData;
    private ca.odell.glazedlists.EventList<ProductCategory> categories;

    /**
     * Webshop overlay per product ID for whichever page/result set is currently displayed -
     * refreshed alongside every {@link #loadProductPage(int, int)}/{@link
     * #reloadDialogProducts(String)} call (see {@link #refreshWebshopCache(List)}), read by the
     * WEBSHOP_PRICE column's label provider. Batched per page rather than one
     * {@code ProductWebshopDAO#findByProduct} call per row - see
     * {@link com.sebulli.fakturama.dao.ProductWebshopDAO#findByProducts}'s javadoc for why a
     * fetch-join isn't possible here.
     */
    private Map<Long, com.sebulli.fakturama.model.ProductWebshop> webshopCache = Map.of();

    private String currentSearchTerm;
    private String currentCategoryName;
    private TreeObjectType currentCategoryType = TreeObjectType.ALL_NODE;
    private String currentSortProperty;
    private boolean currentSortDescending;
    private final Runnable searchDebounceRunnable = this::applyDebouncedSearchTerm;

    private TableViewer productsViewer;
    private Table table;
    private boolean dialogMode;
    private TableColumn sortedColumn;

    private MPart listTablePart;
    private Product selectedObject;
    private ViewDataTableMode viewDataTableMode;

    @PostConstruct
    public Control createPartControl(final Composite parent, final MPart listTablePart) {
        log.debug("create Product list part");
        this.listTablePart = listTablePart;
        super.createPartControl(parent, Product.class, true, ID);
        final Object commandId = this.listTablePart.getTransientData().get(Constants.PROPERTY_PRODUCTS_CLICKHANDLER);
        viewDataTableMode = commandId != null ? ViewDataTableMode.DIALOG : ViewDataTableMode.LIST;
        if (!dialogMode) {
            topicTreeViewer.setTable(this);
        }
        GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(top);
        return top;
    }

    @Override
    public Product getSelectedObject() {
        return selectedObject;
    }

    @Override
    public Product[] getSelectedObjects() {
        return getSelectedObjects(false);
    }

    @Override
    public Product[] getSelectedObjects(final boolean selectIfSingleRow) {
        final IStructuredSelection sel = productsViewer.getStructuredSelection();
        List<Product> selectedObjects = new ArrayList<>(sel.toList());
        if (selectedObjects.isEmpty() && selectIfSingleRow) {
            final long total = dialogMode ? table.getItemCount() : productListData.size();
            if (total == 1) {
                selectedObject = dialogMode ? (Product) table.getItem(0).getData() : productListData.get(0);
                selectedObjects = Arrays.asList(selectedObject);
            }
        }
        final Product[] retArr = selectedObjects.toArray(new Product[selectedObjects.size()]);
        selectionService.setSelection(selectedObjects);
        return retArr;
    }

    @Override
    protected Control createListTable(final Composite searchAndTableComposite) {
        dialogMode = this.listTablePart.getTransientData().get(Constants.PROPERTY_PRODUCTS_CLICKHANDLER) != null;

        final Composite tableComposite = new Composite(searchAndTableComposite, SWT.NONE);
        tableColumnLayout = new TableColumnLayout();
        tableComposite.setLayout(tableColumnLayout);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(tableComposite);

        final int style = SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER | (dialogMode ? SWT.NONE : SWT.VIRTUAL);
        table = new Table(tableComposite, style);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyHeaderStyle(table);
        com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyFixedRowHeight(table, 1.7f);
        productsViewer = new TableViewer(table);
        productsViewer.setUseHashlookup(true);

        createColumns();

        if (dialogMode) {
            // Bounded by the search box - never opened without a search term narrowing it down in
            // practice, and DIALOG mode auto-closes as soon as exactly one match remains anyway
            // (see the search listener below) - a plain populated TableViewer is fine here.
            productListData = null;
            productsViewer.setContentProvider(ArrayContentProvider.getInstance());
            reloadDialogProducts(null);
            getSearchControl().getTextControl().addModifyListener(e -> {
                final Display display = getSearchControl().getTextControl().getDisplay();
                display.timerExec(-1, searchDebounceRunnable);
                display.timerExec(SEARCH_DEBOUNCE_MS, searchDebounceRunnable);
            });
        } else {
            productListData = new PagedEntityEventList<>(PRODUCT_BATCH_SIZE, this::loadProductPage, this::countProducts);
            productsViewer.setContentProvider(new ILazyContentProvider() {
                @Override
                public void updateElement(final int index) {
                    final Product product = productListData.get(index);
                    // Can be null: a stale SetData request for an index beyond the list's new,
                    // just-shrunk size (see PagedEntityEventList#get's javadoc) - nothing to show
                    // for it, and it'll be re-requested correctly once SWT catches up.
                    if (product == null) {
                        return;
                    }
                    productsViewer.replace(product, index);
                    com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyZebraStripe(table, index);
                }

                @Override
                public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
                    // nothing to do - productListData is set up before setInput() below
                }
            });
            productsViewer.setInput(new Object());
            table.setItemCount((int) productListData.size());
            hookSearchDebounce();
        }

        hookSelectionAndDoubleClick();

        return table;
    }

    /** DIALOG mode: bounded, plain (non-virtual) list - reloaded wholesale per debounced search term. */
    private void reloadDialogProducts(final String searchTerm) {
        final List<Product> results = productsDAO.findPage(searchTerm, null, null, null, false, 0, 200);
        refreshWebshopCache(results);
        productsViewer.setInput(results);
        for (int i = 0; i < table.getItemCount(); i++) {
            com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyZebraStripe(table, i);
        }
        if (results.size() == 1 && prefStore.getBoolean(Constants.PREFERENCES_DOCUMENT_IMMEDIATELY_OVERTAKE_ITEMNUMBER_FROM_PRODUCTS_DIALOG)) {
            selectedObject = results.get(0);
            fireClosingEvent();
        }
    }

    /**
     * One {@link TableViewerColumn} per {@link ProductListDescriptor} that's actually visible per
     * current preferences (use quantity/vat/description/itemnr - see {@code
     * ProductsDAO#getVisibleProperties}), sized proportionally via {@link #tableColumnLayout}.
     */
    private void createColumns() {
        final String[] visibleProperties = productsDAO.getVisibleProperties();
        int descriptionColumnIndex = -1;
        int columnIndex = 0;
        for (final String propertyName : visibleProperties) {
            final ProductListDescriptor descriptor = ProductListDescriptor.getDescriptorForProperty(propertyName).orElse(null);
            if (descriptor == null) {
                continue;
            }
            final boolean rightAligned = descriptor == ProductListDescriptor.PRICE || descriptor == ProductListDescriptor.QUANTITY
                    || descriptor == ProductListDescriptor.VAT || descriptor == ProductListDescriptor.WEBSHOP_PRICE;
            final TableViewerColumn viewerColumn = new TableViewerColumn(productsViewer, rightAligned ? SWT.RIGHT : SWT.LEFT);
            final TableColumn column = viewerColumn.getColumn();
            column.setText(msg.getMessageFromKey(descriptor.getMessageKey()));
            viewerColumn.setLabelProvider(createLabelProvider(descriptor));
            // webshopPrice isn't a real Product/JPA attribute, so onColumnSelected() can't sort
            // it via the generic root.get(orderByProperty) path used for every other column -
            // ProductsDAO#findPage() special-cases this exact propertyName with a correlated
            // subquery instead, grouping every webshop-flagged product above the non-webshop
            // ones (see its javadoc for why).
            column.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent e) {
                    onColumnSelected(descriptor, column);
                }
            });
            tableColumnLayout.setColumnData(column, new ColumnWeightData(descriptor.getDefaultWidth(), 30, true));
            if (descriptor == ProductListDescriptor.DESCRIPTION) {
                descriptionColumnIndex = columnIndex;
            }
            columnIndex++;
        }
        if (descriptionColumnIndex >= 0) {
            // The description column is exactly what applyFixedRowHeight() clips - let a click on
            // it show the full text in a sticky popup instead of only ever seeing a cut-off first
            // line and a bit of the second.
            final int finalDescriptionColumnIndex = descriptionColumnIndex;
            com.sebulli.fakturama.views.datatable.common.ModernTableStyle.addStickyCellPopover(table, finalDescriptionColumnIndex, row -> {
                final TableItem item = table.getItem(row);
                final Object data = item.getData();
                return data instanceof Product product ? product.getDescription() : null;
            });
        }
    }

    private void onColumnSelected(final ProductListDescriptor descriptor, final TableColumn column) {
        if (dialogMode) {
            return;
        }
        final boolean descending = column == sortedColumn && !currentSortDescending;
        currentSortProperty = descriptor.getPropertyName();
        currentSortDescending = descending;
        reloadProductList();
        table.setSortColumn(column);
        table.setSortDirection(descending ? SWT.DOWN : SWT.UP);
        sortedColumn = column;
    }

    private ColumnLabelProvider createLabelProvider(final ProductListDescriptor descriptor) {
        return new ColumnLabelProvider() {
            @Override
            public String getText(final Object element) {
                final Product product = (Product) element;
                switch (descriptor) {
                    case ITEMNO:
                        return product.getItemNumber();
                    case NAME:
                        return product.getName();
                    case DESCRIPTION:
                        return com.sebulli.fakturama.views.datatable.common.ModernTableStyle.singleLineSummary(product.getDescription());
                    case QUANTITY:
                        return product.getQuantity() != null ? numberFormatterService.doubleToFormattedQuantity(product.getQuantity()) : "";
                    case PRICE:
                        final Double price = getEclipsePrefs().getInt(Constants.PREFERENCES_PRODUCT_USE_NET_GROSS,
                                Constants.PRODUCT_USE_NET_AND_GROSS) == Constants.PRODUCT_USE_NET ? product.getPrice1()
                                        : DataUtils.getInstance().CalculateGrossFromNet(product.getPrice1(), product.getVat().getTaxValue());
                        return numberFormatterService.doubleToFormattedPrice(price);
                    case VAT:
                        return product.getVat() != null ? numberFormatterService.DoubleToFormatedPercent(product.getVat().getTaxValue()) : "";
                    case WEBSHOP_PRICE:
                        // Empty unless "im Shop anbieten" is set (a non-deleted ProductWebshop
                        // row exists, see webshopCache's javadoc) - not merely "has a shop price
                        // value", since that column is meant to answer "is this offered in the
                        // shop, and at what price", not double as a generic price display.
                        final com.sebulli.fakturama.model.ProductWebshop webshopData = webshopCache.get(product.getId());
                        return webshopData != null && webshopData.getShopPrice() != null
                                ? numberFormatterService.doubleToFormattedPrice(webshopData.getShopPrice())
                                : "";
                    default:
                        return "";
                }
            }
        };
    }

    private void hookSelectionAndDoubleClick() {
        productsViewer.addSelectionChangedListener((final org.eclipse.jface.viewers.SelectionChangedEvent event) -> {
            final IStructuredSelection sel = (IStructuredSelection) event.getSelection();
            selectedObject = (Product) sel.getFirstElement();
            selectionService.setSelection(sel.toList());
        });

        productsViewer.addDoubleClickListener((final org.eclipse.jface.viewers.DoubleClickEvent event) -> {
            if (selectedObject == null) {
                return;
            }
            if (dialogMode) {
                fireClosingEvent();
            } else {
                final Map<String, Object> params = new HashMap<>();
                params.put(CallEditor.PARAM_OBJ_ID, Long.toString(selectedObject.getId()));
                params.put(CallEditor.PARAM_EDITOR_TYPE, getEditorId());
                context.getParent().get(ESelectionService.class).setSelection(null);
                params.put(CallEditor.PARAM_FOLLOW_UP, null);
                final ParameterizedCommand parameterizedCommand = commandService.createCommand(CommandIds.CMD_CALL_EDITOR, params);
                handlerService.executeHandler(parameterizedCommand);
            }
        });

        if (dialogMode) {
            table.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(final KeyEvent e) {
                    if (e.keyCode == SWT.CR && selectedObject != null) {
                        fireClosingEvent();
                    }
                }
            });
        }
    }

    private void fireClosingEvent() {
        final Map<String, Object> eventParams = new HashMap<>();
        eventParams.put(DocumentEditor.DOCUMENT_ID, context.get(DocumentEditor.DOCUMENT_ID));
        eventParams.put(SELECTED_PRODUCT_ID, Arrays.asList(Long.valueOf(selectedObject.getId())));
        // selecting an entry and closing the dialog are two different actions.
        // the "CloseProduct" event is caught by SelectProductDialog#handleDialogDoubleClickClose.
        evtBroker.post("DialogSelection/Product", eventParams);
        evtBroker.post("DialogAction/CloseProduct", eventParams);
    }

    /** {@link PagedEntityEventList.PageLoader} for productListData - current search/category/sort criteria. */
    private List<Product> loadProductPage(final int firstResult, final int maxResults) {
        final List<Product> page = productsDAO.findPage(currentSearchTerm, currentCategoryName, currentCategoryType, currentSortProperty,
                currentSortDescending, firstResult, maxResults);
        refreshWebshopCache(page);
        return page;
    }

    /** Refreshes {@link #webshopCache} for a newly loaded page/result set - see its javadoc. */
    private void refreshWebshopCache(final List<Product> products) {
        webshopCache = productWebshopDAO.findByProducts(products);
    }

    /** Row count for the same criteria as {@link #loadProductPage(int, int)}. */
    private long countProducts() {
        return productsDAO.countPage(currentSearchTerm, currentCategoryName, currentCategoryType);
    }

    /** Re-runs the current search/category/sort query from the start (a new DB round trip, not a client-side re-filter). */
    private void reloadProductList() {
        if (productListData != null) {
            productListData.reload(this::loadProductPage, this::countProducts);
            if (table != null && !table.isDisposed()) {
                table.setItemCount((int) productListData.size());
                productsViewer.refresh();
            }
        }
    }

    private void hookSearchDebounce() {
        getSearchControl().getTextControl().addModifyListener(e -> {
            final Display display = getSearchControl().getTextControl().getDisplay();
            display.timerExec(-1, searchDebounceRunnable);
            display.timerExec(SEARCH_DEBOUNCE_MS, searchDebounceRunnable);
        });
    }

    private void applyDebouncedSearchTerm() {
        if (getSearchControl().getTextControl().isDisposed()) {
            return;
        }
        final String term = StringUtils.trimToNull(getSearchControl().getTextControl().getText());
        if (dialogMode) {
            reloadDialogProducts(term);
        } else {
            currentSearchTerm = term;
            reloadProductList();
        }
    }

    @Override
    protected EntityGridListLayer<Product> getGridLayer() {
        // No NatTable/GlazedLists grid layer in this view - see the class javadoc.
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TopicTreeViewer<ProductCategory> createCategoryTreeViewer(final Composite top) {
        if (this.listTablePart.getTransientData().get(Constants.PROPERTY_PRODUCTS_CLICKHANDLER) != null) {
            // DIALOG mode: search-only picker, no category tree pane.
            return null;
        }
        context.set(TopicTreeViewer.PARENT_COMPOSITE, top);
        context.set(TopicTreeViewer.USE_DOCUMENT_AND_CONTACT_FILTER, false);
        context.set(TopicTreeViewer.USE_ALL, true);
        topicTreeViewer = (TopicTreeViewer<ProductCategory>) ContextInjectionFactory.make(TopicTreeViewer.class, context);
        categories = GlazedLists.eventList(productCategoriesDAO.findAll());
        topicTreeViewer.setInput(categories);
        topicTreeViewer.setLabelProvider(new TreeCategoryLabelProvider());
        return topicTreeViewer;
    }

    /**
     * Handle an incoming refresh command from ProductEditor. The category tree still uses a
     * GlazedLists EventList (it's small and unrelated to the N+1/full-materialisation issue this
     * class otherwise moved off GlazedLists for).
     */
    @Inject
    @Optional
    public void handleRefreshEvent(@UIEventTopic(ProductEditor.EDITOR_ID) final String message) {
        if (StringUtils.equals(message, Editor.UPDATE_EVENT) && !top.isDisposed()) {
            sync.syncExec(() -> top.setRedraw(false));
            reloadProductList();
            if (categories != null) {
                GlazedLists.replaceAll(categories, GlazedLists.eventList(productCategoriesDAO.findAll(true)), false);
            }
            sync.syncExec(() -> top.setRedraw(true));
        }
    }

    @Override
    protected void handleAfterDeletion(final Product objToDelete) {
        // check if we can delete the old category (if it's empty)
        if (objToDelete != null) {
            try {
                final long countOfEntriesInCategory = productsDAO.countByCategory(objToDelete.getCategories());
                if (countOfEntriesInCategory == 0) {
                    /* the category has to be set to null since the objToDelete isn't "really" deleted
                     * but only marked as "invisible". The reference to the category still remains,
                     * therefore we have to update it.
                     */
                    productCategoriesDAO.deleteEmptyCategory(objToDelete.getCategories());
                }
            } catch (final FakturamaStoringException e) {
                log.error(e, "can't delete empty category from object " + objToDelete.getName());
            }
        }
    }

    /**
     * Set the category filter with a given {@link TreeObjectType}.
     */
    @Override
    public void setCategoryFilter(final String filter, final TreeObjectType treeObjectType) {
        currentCategoryName = filter;
        currentCategoryType = treeObjectType;
        reloadProductList();
    }

    @Override
    protected boolean isHeaderLabelEnabled() {
        return false;
    }

    @Override
    public String getTableId() {
        return ID;
    }

    @Override
    protected MToolBar getMToolBar() {
        return listTablePart.getToolbar();
    }

    @Override
    protected String getToolbarAddItemCommandId() {
        return CommandIds.LISTTOOLBAR_ADD_PRODUCT;
    }

    @Override
    protected String getEditorId() {
        return ProductEditor.ID;
    }

    @Override
    protected String getEditorTypeId() {
        return ProductEditor.class.getSimpleName();
    }

    @Override
    protected String getPopupId() {
        return POPUP_ID;
    }

    @Override
    protected AbstractDAO<Product> getEntityDAO() {
        return productsDAO;
    }

    @Focus
    public void focus() {
        if (natTable != null) {
            natTable.setFocus();
        }
    }

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }
}
