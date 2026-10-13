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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ILazyContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.service.prefs.BackingStoreException;

import com.sebulli.fakturama.dao.AbstractDAO;
import com.sebulli.fakturama.dao.ProductCategoriesDAO;
import com.sebulli.fakturama.dao.ProductsDAO;
import com.sebulli.fakturama.dialogs.SelectProductDialog;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.handlers.CallEditor;
import com.sebulli.fakturama.handlers.OpenProductInWebtoolHandler;
import com.sebulli.fakturama.handlers.CommandIds;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.model.Product;
import com.sebulli.fakturama.model.ProductCategory;
import com.sebulli.fakturama.model.ProductWebshop;
import com.sebulli.fakturama.parts.DocumentEditor;
import com.sebulli.fakturama.parts.Editor;
import com.sebulli.fakturama.parts.ProductEditor;
import com.sebulli.fakturama.resources.core.Icon;
import com.sebulli.fakturama.resources.core.IconSize;
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
    private static final long ASSEMBLY_RETRY_MS = 10_000;
    private static final String ASSEMBLY_FILTER_PREF = "productList.filter.assembly";
    private static final String WEBSHOP_FILTER_PREF = "productList.filter.webshop";
    private static final String OUTDATED_FILTER_PREF = "productList.filter.outdated";
    private static final String FILTER_EVENT = "ProductListTable/FiltersChanged";

    @Inject
    private UISynchronize sync;

    @Inject
    protected IEclipseContext context;

    @Inject
    private ProductsDAO productsDAO;

    @Inject
    private EPartService partService;

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
    private final Map<Long, com.sebulli.fakturama.model.ProductWebshop> webshopCache = new HashMap<>();
    private Map<String, Double> activeReservedQuantities = Map.of();

    private String currentSearchTerm;
    private String currentCategoryName;
    private TreeObjectType currentCategoryType = TreeObjectType.ALL_NODE;
    private String currentSortProperty;
    private boolean currentSortDescending;
    private ProductIconFilter assemblyFilter = ProductIconFilter.ALL;
    private ProductIconFilter webshopFilter = ProductIconFilter.ALL;
    private ProductIconFilter outdatedFilter = ProductIconFilter.ALL;
    private final Runnable searchDebounceRunnable = this::applyDebouncedSearchTerm;

    private TableViewer productsViewer;
    private Table table;
    private boolean dialogMode;
    private TableColumn sortedColumn;
    private final Map<TableColumn, Image> filterHeaderImages = new HashMap<>();

    private MPart listTablePart;
    private Product selectedObject;
    private ViewDataTableMode viewDataTableMode;
    private Set<String> assemblyItemNumbers = java.util.Collections.emptySet();
    private boolean assemblyLookupAvailable = true;
    private String assemblyLookupError;
    private long nextAssemblyLookupAttempt;

    @PostConstruct
    public Control createPartControl(final Composite parent, final MPart listTablePart) {
        log.debug("create Product list part");
        this.listTablePart = listTablePart;
        super.createPartControl(parent, Product.class, true, ID);
        final Object commandId = this.listTablePart.getTransientData().get(Constants.PROPERTY_PRODUCTS_CLICKHANDLER);
        viewDataTableMode = commandId != null ? ViewDataTableMode.DIALOG : ViewDataTableMode.LIST;
        topicTreeViewer.setTable(this);
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

    public boolean hasSelection() {
        return productsViewer != null && !productsViewer.getStructuredSelection().isEmpty();
    }

    public Product getSingleSelectedProductForWebtool() {
        if (!assemblyLookupAvailable || productsViewer == null || productsViewer.getStructuredSelection().size() != 1) {
            return null;
        }
        final Object selected = productsViewer.getStructuredSelection().getFirstElement();
        return selected instanceof Product product && product.getId() > 0 ? product : null;
    }

    public boolean isAssembly(final Product product) {
        return assemblyItemNumbers.contains(ProductsDAO.normalizeAssemblyItemNumber(product.getItemNumber()));
    }

    private void updateWebtoolMenuLabel() {
        final Product product = getSingleSelectedProductForWebtool();
        listTablePart.getMenus().stream()
                .flatMap(menu -> menu.getChildren().stream())
                .filter(MHandledMenuItem.class::isInstance)
                .map(MHandledMenuItem.class::cast)
                .filter(item -> OpenProductInWebtoolHandler.ITEM_ID.equals(item.getElementId()))
                .findFirst()
                .ifPresent(item -> item.setLabel(product == null ? "Im Webtool bearbeiten…"
                        : isAssembly(product) ? "Im Webtool Stückliste bearbeiten…"
                        : "Im Webtool Produkt bearbeiten…"));
    }

    private void refreshAssemblyItemNumbers() {
        final ProductsDAO.AssemblyLookupResult result = productsDAO.findAssemblyItemNumbersWithStatus();
        assemblyItemNumbers = result.itemNumbers();
        assemblyLookupAvailable = result.available();
        assemblyLookupError = result.errorMessage();
        nextAssemblyLookupAttempt = System.currentTimeMillis() + ASSEMBLY_RETRY_MS;
        if (table != null && !table.isDisposed()) {
            for (final TableColumn column : table.getColumns()) {
                if (column.getData("productFilterDescriptor") == ProductListDescriptor.ICON_ASSEMBLY) {
                    updateFilterHeader(column, ProductListDescriptor.ICON_ASSEMBLY);
                }
            }
        }
    }

    private void retryAssemblyLookupIfNeeded() {
        if (!assemblyLookupAvailable && System.currentTimeMillis() >= nextAssemblyLookupAttempt) {
            refreshAssemblyItemNumbers();
        }
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
        refreshAssemblyItemNumbers();
        loadIconFilters();

        final Composite tableComposite = new Composite(searchAndTableComposite, SWT.NONE);
        tableColumnLayout = new TableColumnLayout();
        tableComposite.setLayout(tableColumnLayout);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(tableComposite);

        final int style = SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER | (dialogMode ? SWT.NONE : SWT.VIRTUAL);
        table = new Table(tableComposite, style);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.addDisposeListener(e -> filterHeaderImages.values().forEach(image -> {
            if (!image.isDisposed()) {
                image.dispose();
            }
        }));
        com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyHeaderStyle(table);
        com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyFixedRowHeight(table, 1.7f,
                column -> column >= 0 && column < table.getColumnCount()
                        && table.getColumn(column).getData("productFilterDescriptor") != null);
        productsViewer = new TableViewer(table);
        productsViewer.setUseHashlookup(true);
        ColumnViewerToolTipSupport.enableFor(productsViewer);
        table.addListener(SWT.MenuDetect, event -> {
            if (dialogMode) {
                return;
            }
            final TableItem clicked = table.getItem(table.toControl(new Point(event.x, event.y)));
            if (clicked != null && !table.isSelected(table.indexOf(clicked))) {
                productsViewer.setSelection(new StructuredSelection(clicked.getData()), true);
            }
            partService.activate(listTablePart);
            updateWebtoolMenuLabel();
        });

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
                    applyActiveSaleColor(table.getItem(index), product);
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
        retryAssemblyLookupIfNeeded();
        currentSearchTerm = searchTerm;
        final List<Product> results = productsDAO.findPage(searchTerm, currentCategoryName, currentCategoryType, null, false, 0, 200,
                assemblyFilter, webshopFilter, outdatedFilter, assemblyItemNumbers);
        webshopCache.clear();
        refreshWebshopCache(results);
        activeReservedQuantities = productsDAO.findActiveReservedQuantities();
        productsViewer.setInput(results);
        for (int i = 0; i < table.getItemCount(); i++) {
            com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyZebraStripe(table, i);
            applyAvailabilityColor(table.getItem(i), results.get(i));
            applyActiveSaleColor(table.getItem(i), results.get(i));
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
        final List<String> properties = new ArrayList<>();
        properties.add(ProductListDescriptor.ICON_ASSEMBLY.getPropertyName());
        properties.add(ProductListDescriptor.ICON_WEBSHOP.getPropertyName());
        properties.add(ProductListDescriptor.ICON_OUTDATED.getPropertyName());
        properties.addAll(Arrays.asList(visibleProperties));
        for (final String propertyName : properties) {
            final ProductListDescriptor descriptor = ProductListDescriptor.getDescriptorForProperty(propertyName).orElse(null);
            if (descriptor == null) {
                continue;
            }
            final boolean rightAligned = descriptor == ProductListDescriptor.PRICE || descriptor == ProductListDescriptor.QUANTITY
                    || descriptor == ProductListDescriptor.VAT || descriptor == ProductListDescriptor.WEBSHOP_PRICE;
            final TableViewerColumn viewerColumn = new TableViewerColumn(productsViewer, rightAligned ? SWT.RIGHT : SWT.LEFT);
            final TableColumn column = viewerColumn.getColumn();
            final boolean iconColumn = descriptor == ProductListDescriptor.ICON_ASSEMBLY
                    || descriptor == ProductListDescriptor.ICON_WEBSHOP || descriptor == ProductListDescriptor.ICON_OUTDATED;
            column.setText(iconColumn ? "" : msg.getMessageFromKey(descriptor.getMessageKey()));
            column.setResizable(!iconColumn);
            viewerColumn.setLabelProvider(createLabelProvider(descriptor));
            if (iconColumn) {
                column.setData("productFilterDescriptor", descriptor);
                updateFilterHeader(column, descriptor);
                column.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> cycleIconFilter(column, descriptor)));
            }
            // webshopPrice isn't a Product/JPA attribute (ProductWebshop only points to Product),
            // so it cannot safely participate in the paged CriteriaQuery sort.
            if (!iconColumn && descriptor != ProductListDescriptor.WEBSHOP_PRICE) {
                column.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(final SelectionEvent e) {
                        onColumnSelected(descriptor, column);
                    }
                });
            }
            tableColumnLayout.setColumnData(column, new ColumnWeightData(descriptor.getDefaultWidth(), iconColumn ? 36 : 30, !iconColumn));
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
        if (dialogMode) {
            final TableViewerColumn availabilityColumn = new TableViewerColumn(productsViewer, SWT.RIGHT);
            final TableColumn column = availabilityColumn.getColumn();
            column.setText("Frei");
            availabilityColumn.setLabelProvider(new ColumnLabelProvider() {
                @Override
                public String getText(final Object element) {
                    final Product product = (Product) element;
                    return Boolean.TRUE.equals(product.getStockManaged())
                            ? numberFormatterService.doubleToFormattedQuantity(getAvailableQuantity(product)) : "";
                }
            });
            tableColumnLayout.setColumnData(column, new ColumnWeightData(75, 55, true));
        }
    }

    private ProductIconFilter iconFilter(final ProductListDescriptor descriptor) {
        return switch (descriptor) {
            case ICON_ASSEMBLY -> assemblyFilter;
            case ICON_WEBSHOP -> webshopFilter;
            case ICON_OUTDATED -> outdatedFilter;
            default -> ProductIconFilter.ALL;
        };
    }

    private boolean loadIconFilters() {
        final ProductIconFilter newAssembly = ProductIconFilter.fromPreference(eclipsePrefs.get(ASSEMBLY_FILTER_PREF, ProductIconFilter.ALL.name()));
        final ProductIconFilter newWebshop = ProductIconFilter.fromPreference(eclipsePrefs.get(WEBSHOP_FILTER_PREF, ProductIconFilter.ALL.name()));
        final ProductIconFilter newOutdated = ProductIconFilter.fromPreference(eclipsePrefs.get(OUTDATED_FILTER_PREF, ProductIconFilter.ALL.name()));
        final boolean changed = assemblyFilter != newAssembly || webshopFilter != newWebshop || outdatedFilter != newOutdated;
        assemblyFilter = newAssembly;
        webshopFilter = newWebshop;
        outdatedFilter = newOutdated;
        return changed;
    }

    @Inject
    @Optional
    public void handleIconFilterChange(@UIEventTopic(FILTER_EVENT) final String ignored) {
        if (table == null || table.isDisposed() || !loadIconFilters()) {
            return;
        }
        for (final TableColumn column : table.getColumns()) {
            if (column.getData("productFilterDescriptor") instanceof ProductListDescriptor descriptor) {
                updateFilterHeader(column, descriptor);
            }
        }
        if (dialogMode) {
            reloadDialogProducts(currentSearchTerm);
        } else {
            reloadProductList();
        }
    }

    private void cycleIconFilter(final TableColumn column, final ProductListDescriptor descriptor) {
        final ProductIconFilter next = iconFilter(descriptor).next();
        final String preferenceKey;
        switch (descriptor) {
            case ICON_ASSEMBLY -> {
                assemblyFilter = next;
                preferenceKey = ASSEMBLY_FILTER_PREF;
            }
            case ICON_WEBSHOP -> {
                webshopFilter = next;
                preferenceKey = WEBSHOP_FILTER_PREF;
            }
            case ICON_OUTDATED -> {
                outdatedFilter = next;
                preferenceKey = OUTDATED_FILTER_PREF;
            }
            default -> { return; }
        }
        eclipsePrefs.put(preferenceKey, next.name());
        try {
            eclipsePrefs.flush();
        } catch (BackingStoreException e) {
            log.error(e, "Could not save product list icon filter");
        }
        updateFilterHeader(column, descriptor);
        if (dialogMode) {
            reloadDialogProducts(currentSearchTerm);
        } else {
            reloadProductList();
        }
        evtBroker.post(FILTER_EVENT, next.name());
    }

    private void updateFilterHeader(final TableColumn column, final ProductListDescriptor descriptor) {
        final ProductIconFilter filter = iconFilter(descriptor);
        final Icon icon = switch (descriptor) {
            case ICON_ASSEMBLY -> Icon.COMMAND_STUECKLISTE;
            case ICON_WEBSHOP -> Icon.COMMAND_SHOP;
            case ICON_OUTDATED -> Icon.COMMAND_PRODUCT_OUTDATED;
            default -> null;
        };
        if (icon == null) {
            return;
        }
        final boolean assemblyUnavailable = descriptor == ProductListDescriptor.ICON_ASSEMBLY && !assemblyLookupAvailable;
        final Image headerImage = createFilterHeaderImage(icon.getImage(IconSize.DefaultIconSize), filter, assemblyUnavailable);
        final Image oldImage = filterHeaderImages.put(column, headerImage);
        column.setImage(headerImage);
        if (oldImage != null) {
            oldImage.dispose();
        }
        final String subject = switch (descriptor) {
            case ICON_ASSEMBLY -> "Stücklisten";
            case ICON_WEBSHOP -> "Webshop-Artikel";
            case ICON_OUTDATED -> "ersetzte und Auslaufartikel";
            default -> "Artikel";
        };
        final String state = switch (filter) {
            case ALL -> "alle anzeigen";
            case ONLY -> "nur " + subject + " anzeigen";
            case EXCLUDE -> subject + " ausblenden";
        };
        column.setToolTipText(assemblyUnavailable
                ? "Stücklistenstatus nicht verfügbar (PART_LI): " + assemblyLookupError + " · Erneuter Versuch beim nächsten Aufruf"
                : state + (descriptor == ProductListDescriptor.ICON_ASSEMBLY
                        ? " · " + assemblyItemNumbers.size() + " Stücklistenkennungen geladen" : "") + " · Klicken zum Wechseln");
    }

    private Image createFilterHeaderImage(final Image symbol, final ProductIconFilter filter, final boolean warning) {
        final PaletteData palette = new PaletteData(0xFF0000, 0x00FF00, 0x0000FF);
        final ImageData result = new ImageData(28, 18, 24, palette);
        result.alphaData = new byte[28 * 18];
        final RGB tint = switch (filter) {
            case ALL -> warning ? new RGB(255, 244, 215) : null;
            case ONLY -> new RGB(231, 221, 247);
            case EXCLUDE -> new RGB(250, 225, 225);
        };
        if (tint != null) {
            final int tintPixel = palette.getPixel(tint);
            for (int y = 0; y < result.height; y++) {
                for (int x = 0; x < result.width; x++) {
                    result.setPixel(x, y, tintPixel);
                    result.setAlpha(x, y, 255);
                }
            }
        }
        if (symbol != null) {
            final ImageData source = symbol.getImageData();
            for (int y = 0; y < Math.min(16, source.height); y++) {
                for (int x = 0; x < Math.min(16, source.width); x++) {
                    final int sourcePixel = source.getPixel(x, y);
                    final int alpha = source.transparentPixel == sourcePixel ? 0 : source.getAlpha(x, y);
                    if (alpha == 0) {
                        continue;
                    }
                    final RGB foreground = source.palette.getRGB(sourcePixel);
                    final RGB color = tint == null || alpha == 255 ? foreground : new RGB(
                            (foreground.red * alpha + tint.red * (255 - alpha)) / 255,
                            (foreground.green * alpha + tint.green * (255 - alpha)) / 255,
                            (foreground.blue * alpha + tint.blue * (255 - alpha)) / 255);
                    result.setPixel(x + 1, y + 1, palette.getPixel(color));
                    result.setAlpha(x + 1, y + 1, tint == null ? alpha : 255);
                }
            }
        }
        if (filter != ProductIconFilter.ALL || warning) {
            final int marker = palette.getPixel(warning ? new RGB(134, 84, 19)
                    : filter == ProductIconFilter.ONLY ? new RGB(91, 55, 129) : new RGB(151, 58, 58));
            if (!warning) {
                for (int x = 20; x <= 25; x++) {
                    result.setPixel(x, 8, marker);
                    result.setAlpha(x, 8, 255);
                }
            }
            if (warning || filter == ProductIconFilter.ONLY) {
                for (int y = 5; y <= 11; y++) {
                    result.setPixel(22, y, marker);
                    result.setAlpha(22, y, 255);
                }
            }
            if (warning) {
                result.setPixel(22, 13, marker);
                result.setAlpha(22, 13, 255);
            }
        }
        return new Image(table.getDisplay(), result);
    }

    private double getAvailableQuantity(final Product product) {
        final double stock = product.getQuantity() != null ? product.getQuantity() : 0.0d;
        return stock - activeReservedQuantities.getOrDefault(product.getItemNumber(), 0.0d);
    }

    private void applyAvailabilityColor(final TableItem item, final Product product) {
        if (!dialogMode || !Boolean.TRUE.equals(product.getStockManaged())) {
            return;
        }
        final double available = getAvailableQuantity(product);
        if (available <= 0.0d) {
            item.setBackground(GUIHelper.getColor(255, 247, 247));
        } else if (available < 1.0d) {
            item.setBackground(GUIHelper.getColor(255, 248, 210));
        }
    }

    private void applyActiveSaleColor(final TableItem item, final Product product) {
        if (dialogMode || item == null || item.isDisposed() || !isActiveSale(webshopCache.get(product.getId()))) {
            return;
        }
        item.setBackground(GUIHelper.getColor(225, 245, 225));
    }

    private boolean isActiveSale(final ProductWebshop webshop) {
        if (webshop == null || webshop.getShopSalePrice() == null) {
            return false;
        }
        final LocalDate today = LocalDate.now();
        final LocalDate from = toLocalDate(webshop.getShopSaleFrom());
        final LocalDate to = toLocalDate(webshop.getShopSaleTo());
        return (from == null || !today.isBefore(from)) && (to == null || !today.isAfter(to));
    }

    private LocalDate toLocalDate(final Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void refreshVisibleRowColors() {
        if (table == null || table.isDisposed() || dialogMode) {
            return;
        }
        for (final TableItem item : table.getItems()) {
            if (item.getData() instanceof Product product) {
                applyActiveSaleColor(item, product);
            }
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

            @Override
            public org.eclipse.swt.graphics.Image getImage(final Object element) {
                final Product product = (Product) element;
                final Icon icon = switch (descriptor) {
                    case ICON_ASSEMBLY -> assemblyItemNumbers.contains(ProductsDAO.normalizeAssemblyItemNumber(product.getItemNumber()))
                            ? Icon.COMMAND_STUECKLISTE : null;
                    case ICON_WEBSHOP -> webshopCache.containsKey(product.getId()) ? Icon.COMMAND_SHOP : null;
                    case ICON_OUTDATED -> product.getReplacedByProductId() != null ? Icon.COMMAND_PRODUCT_OUTDATED : null;
                    default -> null;
                };
                return icon != null ? icon.getImage(IconSize.DefaultIconSize) : null;
            }

            @Override
            public String getToolTipText(final Object element) {
                final Product product = (Product) element;
                return switch (descriptor) {
                    case ICON_ASSEMBLY -> assemblyItemNumbers.contains(ProductsDAO.normalizeAssemblyItemNumber(product.getItemNumber()))
                            ? "Stückliste" : null;
                    case ICON_WEBSHOP -> webshopCache.containsKey(product.getId()) ? "Für Web-Shop aktiviert" : null;
                    case ICON_OUTDATED -> product.getReplacedByProductId() == null ? null
                            : product.getReplacedByProductId() == 0L ? "Auslaufartikel ohne Nachfolger"
                                    : "Ersetzt durch Artikel-ID " + product.getReplacedByProductId();
                    default -> null;
                };
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
        if (!Boolean.TRUE.equals(listTablePart.getTransientData().get(SelectProductDialog.SELECTION_ONLY))) {
            evtBroker.post("DialogSelection/Product", eventParams);
        }
        evtBroker.post("DialogAction/CloseProduct", eventParams);
    }

    /** {@link PagedEntityEventList.PageLoader} for productListData - current search/category/sort criteria. */
    private List<Product> loadProductPage(final int firstResult, final int maxResults) {
        final List<Product> page = productsDAO.findPage(currentSearchTerm, currentCategoryName, currentCategoryType, currentSortProperty,
                currentSortDescending, firstResult, maxResults, assemblyFilter, webshopFilter, outdatedFilter, assemblyItemNumbers);
        refreshWebshopCache(page);
        return page;
    }

    /** Refreshes {@link #webshopCache} for a newly loaded page/result set - see its javadoc. */
    private void refreshWebshopCache(final List<Product> products) {
        webshopCache.putAll(productWebshopDAO.findByProducts(products));
    }

    /** Row count for the same criteria as {@link #loadProductPage(int, int)}. */
    private long countProducts() {
        return productsDAO.countPage(currentSearchTerm, currentCategoryName, currentCategoryType,
                assemblyFilter, webshopFilter, outdatedFilter, assemblyItemNumbers);
    }

    /** Re-runs the current search/category/sort query from the start (a new DB round trip, not a client-side re-filter). */
    private void reloadProductList() {
        if (productListData != null) {
            retryAssemblyLookupIfNeeded();
            webshopCache.clear();
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
            refreshAssemblyItemNumbers();
            reloadProductList();
            productsViewer.refresh();
            refreshVisibleRowColors();
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
        if (dialogMode) {
            reloadDialogProducts(currentSearchTerm);
        } else {
            reloadProductList();
        }
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
            if (!assemblyLookupAvailable && System.currentTimeMillis() >= nextAssemblyLookupAttempt) {
                if (dialogMode) {
                    reloadDialogProducts(currentSearchTerm);
                } else {
                    reloadProductList();
                }
            }
            natTable.setFocus();
        }
    }

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }
}
