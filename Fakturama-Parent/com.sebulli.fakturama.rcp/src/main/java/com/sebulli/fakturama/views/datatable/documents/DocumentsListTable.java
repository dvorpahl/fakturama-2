/**
 *
 */
package com.sebulli.fakturama.views.datatable.documents;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.model.application.ui.menu.impl.HandledToolItemImpl;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILazyContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.sebulli.fakturama.dao.AbstractDAO;
import com.sebulli.fakturama.dao.DocumentReceiverDAO;
import com.sebulli.fakturama.dao.DocumentsDAO;
import com.sebulli.fakturama.handlers.CallEditor;
import com.sebulli.fakturama.handlers.CommandIds;
import com.sebulli.fakturama.handlers.StockUpdateHandler;
import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.i18n.MessageRegistry;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.misc.INumberFormatterService;
import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.DocumentReceiver;
import com.sebulli.fakturama.model.DummyStringCategory;
import com.sebulli.fakturama.model.Dunning;
import com.sebulli.fakturama.model.IDocumentAddressManager;
import com.sebulli.fakturama.parts.DocumentEditor;
import com.sebulli.fakturama.parts.Editor;
import com.sebulli.fakturama.resources.core.Icon;
import com.sebulli.fakturama.resources.core.IconSize;
import com.sebulli.fakturama.startup.ConfigurationManager;
import com.sebulli.fakturama.util.ContactUtil;
import com.sebulli.fakturama.util.DocumentTypeUtil;
import com.sebulli.fakturama.views.datatable.AbstractViewDataTable;
import com.sebulli.fakturama.views.datatable.layer.EntityGridListLayer;
import com.sebulli.fakturama.views.datatable.layer.PagedEntityEventList;
import com.sebulli.fakturama.views.datatable.tree.model.TreeObject;
import com.sebulli.fakturama.views.datatable.tree.ui.TopicTreeViewer;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

import ca.odell.glazedlists.GlazedLists;
import jakarta.persistence.PersistenceException;

/**
 * Builds the Document list table.
 * <p>
 * Uses a native SWT.VIRTUAL {@link Table} + JFace {@link ILazyContentProvider} instead of
 * NatTable/GlazedLists (which every other {@code *ListTable} view still uses). This view alone
 * moved off that stack: NatTable's GlazedLists bridge kept forcing full materialisation of a
 * server-paginated list on every reload - confirmed via SQL log + thread dump, a category switch
 * on a ~1500-row category fired ~1500 individual per-row queries even with only ~30 rows visible,
 * regardless of which GlazedLists event layer variant was used or whether the event bridge was
 * skipped entirely (NatTable's own structural-refresh handling still touched every row). A plain
 * SWT.VIRTUAL table has no such layer in between: the OS only ever asks for rows it's about to
 * paint, so {@link #documentListData}'s lazy per-page DB loading (unchanged - see
 * {@link PagedEntityEventList}) actually stays lazy.
 */
public class DocumentsListTable extends AbstractViewDataTable<Document, DummyStringCategory> {

    //  this is for synchronizing the UI thread
    @Inject
    private UISynchronize sync;

    // ID of this view
    public static final String ID = "fakturama.views.documentTable";

    protected static final String POPUP_ID = "com.sebulli.fakturama.document.popup";
    public static final String SELECTED_DELIVERY_ID = "fakturama.deliverylist.selecteddeliveryid";

    @Inject
    private IEclipseContext context;

    @Inject
    private ILocaleService localeUtil;

    @Inject
    private IDocumentAddressManager addressManager;

    // Number of documents fetched per DB round trip - matched to roughly a screenful, not a
    // "buffer" of hundreds of rows nobody's looking at yet. The scrollbar/row count still shows
    // the true total immediately (see PagedEntityEventList), so the table looks fully populated
    // even though only this many rows are actually materialized. Only used in non-dialog mode.
    private static final int DOCUMENT_BATCH_SIZE = 30;
    private static final int SEARCH_DEBOUNCE_MS = 300;

    // null in dialog mode (a bounded delivery-note picker - see createListTable()).
    private PagedEntityEventList<Document> documentListData;

    // Current search/category/sort criteria for documentListData - reapplied on every reload()
    // (debounced search, tree selection, column sort, refresh-after-save).
    private String currentSearchTerm;
    private String currentCategoryName;
    private TreeObjectType currentCategoryType = TreeObjectType.ALL_NODE;
    private String currentSortProperty;
    private boolean currentSortDescending;
    // Reused across keystrokes so Display#timerExec(-1, ...) can cancel a still-pending debounce.
    private final Runnable searchDebounceRunnable = this::applyDebouncedSearchTerm;

    @Inject
    private DocumentsDAO documentsDAO;

    @Inject
    private DocumentReceiverDAO contactsDAO;

    @Inject
    private INumberFormatterService numberFormatterService;

    private TableViewer documentsViewer;
    private Table table;
    private boolean dialogMode;
    private TableColumn sortedColumn;

    private ContactUtil contactUtil;

    private MPart listTablePart;

    private Document selectedObject;

    @Inject
    protected MessageRegistry registry;

    @PostConstruct
    public Control createPartControl(final Composite parent, final MPart listTablePart) {
        log.debug("create Document list part");
        this.listTablePart = listTablePart;
        if (!eclipsePrefs.get(ConfigurationManager.GENERAL_WORKSPACE_REQUEST, "").isEmpty()) {
            return null;
        }
        super.createPartControl(parent, Document.class, true, ID);

        if (!dialogMode) {
            topicTreeViewer.setTable(this);
            // On creating, set the unpaid invoices
            topicTreeViewer
                    .selectItemByName(String.format("%s/%s", msg.getMessageFromKey(DocumentType.INVOICE.getPluralDescription()), msg.documentOrderStateUnpaid));
        }

        GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(top);
        contactUtil = ContextInjectionFactory.make(ContactUtil.class, context);

        return top;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sebulli.fakturama.views.datatable.AbstractViewDataTable#
     * getAdditionalParameters()
     */
    @Override
    protected Map<String, Object> getAdditionalParameters() {
        final Map<String, Object> params = new HashMap<>();
        params.put(CallEditor.PARAM_CATEGORY, selectedObject.getBillingType().getName());
        return params;
    }

    @Override
    protected Class<Document> getEntityClass() {
        return Document.class;
    }

    @Override
    protected Control createListTable(final Composite searchAndTableComposite) {
        dialogMode = this.listTablePart.getTransientData().get(Constants.PROPERTY_DELIVERIES_CLICKHANDLER) != null;

        final Composite tableComposite = new Composite(searchAndTableComposite, SWT.NONE);
        tableColumnLayout = new TableColumnLayout();
        tableComposite.setLayout(tableColumnLayout);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(tableComposite);

        final int style = SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER | (dialogMode ? SWT.NONE : SWT.VIRTUAL);
        table = new Table(tableComposite, style);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyHeaderStyle(table);
        documentsViewer = new TableViewer(table);
        documentsViewer.setUseHashlookup(true);

        createColumns();

        if (dialogMode) {
            // Bounded, typically small result set (delivery notes without an invoice yet) - a
            // plain populated TableViewer with a text filter is simpler and fine at this size.
            documentListData = null;
            documentsViewer.setContentProvider(ArrayContentProvider.getInstance());
            final List<? extends Document> pickerData = documentsDAO.findAllDeliveriesWithoutInvoice();
            documentsViewer.setInput(pickerData);
            documentsViewer.addFilter(new ViewerFilter() {
                @Override
                public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
                    final String term = StringUtils.trimToEmpty(searchText.getTextControl().getText()).toLowerCase();
                    if (term.isEmpty()) {
                        return true;
                    }
                    final Document doc = (Document) element;
                    return containsIgnoreCase(doc.getName(), term) || containsIgnoreCase(doc.getAddressFirstLine(), term)
                            || containsIgnoreCase(doc.getCustomerRef(), term);
                }
            });
            searchText.getTextControl().addModifyListener(e -> documentsViewer.refresh());
        } else {
            documentListData = new PagedEntityEventList<>(DOCUMENT_BATCH_SIZE, this::loadDocumentPage, this::countDocuments);
            documentsViewer.setContentProvider(new ILazyContentProvider() {
                @Override
                public void updateElement(final int index) {
                    final Document document = documentListData.get(index);
                    // Can be null: a stale SetData request for an index beyond the list's new,
                    // just-shrunk size (see PagedEntityEventList#get's javadoc).
                    if (document == null) {
                        return;
                    }
                    documentsViewer.replace(document, index);
                    com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyZebraStripe(table, index);
                }

                @Override
                public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
                    // nothing to do - documentListData is set up before setInput() below
                }
            });
            documentsViewer.setInput(new Object());
            table.setItemCount((int) documentListData.size());
            hookSearchDebounce();
        }

        hookSelectionAndDoubleClick();

        return table;
    }

    private static boolean containsIgnoreCase(final String haystack, final String needleLower) {
        return haystack != null && haystack.toLowerCase().contains(needleLower);
    }

    /**
     * One {@link TableViewerColumn} per {@link DocumentListDescriptor}, sized proportionally via
     * {@link #tableColumnLayout} (the JFace equivalent of NatTable's column-percentage sizing).
     */
    private void createColumns() {
        for (final DocumentListDescriptor descriptor : DocumentListDescriptor.values()) {
            final TableViewerColumn viewerColumn = new TableViewerColumn(documentsViewer, descriptor == DocumentListDescriptor.TOTAL ? SWT.RIGHT : SWT.LEFT);
            final TableColumn column = viewerColumn.getColumn();
            column.setText(descriptor.getMessageKey() != null ? msg.getMessageFromKey(descriptor.getMessageKey()) : "");
            column.setResizable(descriptor != DocumentListDescriptor.ICON);
            viewerColumn.setLabelProvider(createLabelProvider(descriptor));
            if (descriptor.getPropertyName() != null && !descriptor.getPropertyName().startsWith("$")) {
                column.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(final SelectionEvent e) {
                        onColumnSelected(descriptor, column);
                    }
                });
            }
            // Percentage weights matching the old NatTable column widths (defaultWidth summed to
            // 140 across all 8 columns there too - reused verbatim as weights here).
            tableColumnLayout.setColumnData(column, new ColumnWeightData(descriptor.getDefaultWidth(), descriptor == DocumentListDescriptor.ICON ? 24 : 30,
                    descriptor != DocumentListDescriptor.ICON));
        }
    }

    private void onColumnSelected(final DocumentListDescriptor descriptor, final TableColumn column) {
        if (dialogMode) {
            return;
        }
        final boolean descending = column == sortedColumn && !currentSortDescending;
        onSortRequested(descriptor.getPropertyName(), descending);
        table.setSortColumn(column);
        table.setSortDirection(descending ? SWT.DOWN : SWT.UP);
        sortedColumn = column;
    }

    private ColumnLabelProvider createLabelProvider(final DocumentListDescriptor descriptor) {
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, localeUtil.getDefaultLocale());
        return new ColumnLabelProvider() {
            @Override
            public String getText(final Object element) {
                final Document doc = (Document) element;
                switch (descriptor) {
                    case DOCUMENT:
                        return doc.getName();
                    case DATE:
                        return doc.getDocumentDate() != null ? dateFormat.format(doc.getDocumentDate()) : "";
                    case NAME:
                        return StringUtils.substringBefore(doc.getAddressFirstLine(), "\r");
                    case CUSTREF:
                        return doc.getCustomerRef();
                    case TOTAL:
                        return numberFormatterService.doubleToFormattedPrice(doc.getTotalValue());
                    case STATE:
                        return stateText(doc);
                    default:
                        return "";
                }
            }

            @Override
            public Image getImage(final Object element) {
                final Document doc = (Document) element;
                final Icon icon;
                switch (descriptor) {
                    case ICON:
                        icon = iconFor(doc);
                        break;
                    case STATE:
                        icon = stateIconFor(doc);
                        break;
                    case PRINTED:
                        icon = BooleanUtils.toBoolean(doc.getPrinted()) ? Icon.COMMAND_PRINTER
                                : StringUtils.isNotBlank(doc.getOdtPath()) || StringUtils.isNotBlank(doc.getPdfPath()) ? Icon.COMMAND_PRINTER_GREY : null;
                        break;
                    default:
                        icon = null;
                        break;
                }
                return icon != null ? icon.getImage(IconSize.DefaultIconSize) : null;
            }
        };
    }

    private Icon iconFor(final Document doc) {
        final DocumentType type = DocumentTypeUtil.findByBillingType(doc.getBillingType());
        if (type == null) {
            return null;
        }
        switch (type) {
            case LETTER:
                return Icon.ICON_LETTER;
            case OFFER:
                return Icon.ICON_OFFER;
            case ORDER:
                return Icon.ICON_ORDER;
            case CONFIRMATION:
                return Icon.ICON_CONFIRMATION;
            case INVOICE:
                return Icon.ICON_INVOICE;
            case DELIVERY:
                return Icon.ICON_DELIVERY;
            case CREDIT:
                return Icon.ICON_CREDIT;
            case DUNNING:
                return Icon.ICON_DUNNING;
            case PROFORMA:
                return Icon.ICON_PROFORMA;
            default:
                return null;
        }
    }

    private Icon stateIconFor(final Document doc) {
        final DocumentType type = DocumentTypeUtil.findByBillingType(doc.getBillingType());
        if (type == null) {
            return null;
        }
        switch (type) {
            case INVOICE:
            case CREDIT:
                return doc.getPayDate() != null && BooleanUtils.toBoolean(doc.getPaid()) ? Icon.COMMAND_CHECKED : Icon.COMMAND_ERROR;
            case DELIVERY:
                return doc.getInvoiceReference() != null ? Icon.COMMAND_INVOICE : null;
            case DUNNING:
                return doc.getPayDate() != null ? Icon.COMMAND_CHECKED : Icon.COMMAND_ERROR;
            case ORDER:
                final com.sebulli.fakturama.misc.OrderState progress = com.sebulli.fakturama.misc.OrderState
                        .findByProgressValue(java.util.Optional.ofNullable(doc.getProgress()));
                switch (progress) {
                    case NONE:
                    case PENDING:
                        return Icon.COMMAND_ORDER_PENDING;
                    case PROCESSING:
                        return Icon.COMMAND_ORDER_PROCESSING;
                    case SHIPPED:
                        return Icon.COMMAND_ORDER_SHIPPED;
                    case COMPLETED:
                        return Icon.COMMAND_CHECKED;
                    default:
                        return null;
                }
            default:
                return null;
        }
    }

    private String stateText(final Document doc) {
        final DocumentType type = DocumentTypeUtil.findByBillingType(doc.getBillingType());
        if (type == null) {
            return "";
        }
        switch (type) {
            case INVOICE:
            case CREDIT:
                return doc.getPayDate() != null && BooleanUtils.toBoolean(doc.getPaid()) ? msg.documentOrderStatePaid : msg.documentOrderStateUnpaid;
            case DUNNING:
                if (doc.getPayDate() != null) {
                    return msg.documentOrderStatePaid;
                }
                if (doc.getBillingType() == BillingType.DUNNING) {
                    final int dunningLevel = ((Dunning) doc).getDunningLevel();
                    return MessageFormat.format(msg.documentDunningStatemarkerName, dunningLevel);
                }
                return msg.documentOrderStateUnpaid;
            case ORDER:
                final com.sebulli.fakturama.misc.OrderState progress = com.sebulli.fakturama.misc.OrderState
                        .findByProgressValue(java.util.Optional.ofNullable(doc.getProgress()));
                switch (progress) {
                    case SHIPPED:
                    case COMPLETED:
                        return msg.documentOrderStateShipped;
                    default:
                        return msg.documentOrderStateOpen;
                }
            case DELIVERY:
                return doc.getInvoiceReference() != null ? msg.documentDeliveryStateHasinvoice : msg.documentDeliveryStateHasnoinvoice;
            default:
                return "";
        }
    }

    private void hookSelectionAndDoubleClick() {
        documentsViewer.addSelectionChangedListener((final org.eclipse.jface.viewers.SelectionChangedEvent event) -> {
            final IStructuredSelection sel = (IStructuredSelection) event.getSelection();
            selectedObject = (Document) sel.getFirstElement();
            selectionService.setSelection(sel.toList());
            if (selectedObject != null && !dialogMode && topicTreeViewer != null) {
                topicTreeViewer.setTransaction(selectedObject.getTransactionId() != null ? selectedObject.getTransactionId() : -1L);
                topicTreeViewer.setContactFromDocument(selectedObject);
                changePopupEntries(null);
            }
        });

        documentsViewer.addDoubleClickListener((final org.eclipse.jface.viewers.DoubleClickEvent event) -> {
            final Object commandId = this.listTablePart.getTransientData().get(Constants.PROPERTY_DELIVERIES_CLICKHANDLER);
            if (selectedObject == null) {
                return;
            }
            if (commandId != null) {
                final Map<String, Object> eventParams = new HashMap<>();
                eventParams.put(DocumentEditor.DOCUMENT_ID, context.get(DocumentEditor.DOCUMENT_ID));
                eventParams.put(SELECTED_DELIVERY_ID, getSelectedObjects());
                evtBroker.post("DialogAction/CloseDelivery", eventParams);
            } else {
                final Map<String, Object> params = new HashMap<>();
                params.put(CallEditor.PARAM_OBJ_ID, Long.toString(selectedObject.getId()));
                params.put(CallEditor.PARAM_EDITOR_TYPE, getEditorId());
                params.put(CallEditor.PARAM_FOLLOW_UP, null);
                params.putAll(getAdditionalParameters());
                final ParameterizedCommand parameterizedCommand = commandService.createCommand(CommandIds.CMD_CALL_EDITOR, params);
                handlerService.executeHandler(parameterizedCommand);
            }
        });

        // Right-click selects the row under the cursor first (matches the old NatTable
        // ViewportSelectRowAction behaviour) so the context menu's visibility rules see the
        // right-clicked row, not a stale earlier selection.
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(final MouseEvent e) {
                if (e.button == 3) {
                    final TableItem item = table.getItem(new org.eclipse.swt.graphics.Point(e.x, e.y));
                    if (item != null && !java.util.Arrays.asList(table.getSelection()).contains(item)) {
                        table.setSelection(item);
                        documentsViewer.setSelection(new org.eclipse.jface.viewers.StructuredSelection(item.getData()));
                        changePopupEntries(null);
                    }
                }
            }
        });
    }

    /** {@link PagedEntityEventList.PageLoader} for documentListData - current search/category/sort criteria. */
    private List<Document> loadDocumentPage(final int firstResult, final int maxResults) {
        return documentsDAO.findPage(true, currentSearchTerm, currentCategoryName, currentCategoryType, currentSortProperty, currentSortDescending,
                firstResult, maxResults);
    }

    /** Row count for the same criteria as {@link #loadDocumentPage(int, int)}. */
    private long countDocuments() {
        return documentsDAO.countPage(currentSearchTerm, currentCategoryName, currentCategoryType);
    }

    /** Re-runs the current search/category/sort query from the start (a new DB round trip, not a client-side re-filter). */
    private void reloadDocumentList() {
        if (documentListData != null) {
            documentListData.reload(this::loadDocumentPage, this::countDocuments);
            if (table != null && !table.isDisposed()) {
                table.setItemCount((int) documentListData.size());
                documentsViewer.refresh();
            }
        }
    }

    /**
     * Debounces the search box: a DB query per keystroke would be wasteful, unlike the old
     * in-memory filter it replaces. Reuses one {@link Runnable} instance so a still-pending
     * {@link org.eclipse.swt.widgets.Display#timerExec} can be cancelled (matched by reference)
     * before scheduling a new one - the standard SWT debounce idiom.
     */
    private void hookSearchDebounce() {
        searchText.getTextControl().addModifyListener(e -> {
            final Display display = searchText.getTextControl().getDisplay();
            display.timerExec(-1, searchDebounceRunnable);
            display.timerExec(SEARCH_DEBOUNCE_MS, searchDebounceRunnable);
        });
    }

    private void applyDebouncedSearchTerm() {
        if (searchText.getTextControl().isDisposed()) {
            return;
        }
        currentSearchTerm = StringUtils.trimToNull(searchText.getTextControl().getText());
        reloadDocumentList();
    }

    private void onSortRequested(final String propertyName, final boolean descending) {
        currentSortProperty = propertyName;
        currentSortDescending = descending;
        reloadDocumentList();
    }

    @Override
    protected EntityGridListLayer<Document> getGridLayer() {
        // No NatTable/GlazedLists grid layer in this view - see createListTable()'s javadoc.
        // Nothing outside this class calls getGridLayer() on a DocumentsListTable instance.
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TopicTreeViewer<DummyStringCategory> createCategoryTreeViewer(final Composite top) {
        final Object commandId = this.listTablePart.getTransientData().get(Constants.PROPERTY_DELIVERIES_CLICKHANDLER);
        if (commandId != null) { // exactly would it be Constants.COMMAND_SELECTITEM
            topicTreeViewer = null;
        } else {
            context.set("useDocumentAndContactFilter", true);
            context.set("useAll", false);
            try {
                categories = GlazedLists.eventList(documentsDAO.getCategoryStrings());

                context.set(TopicTreeViewer.PARENT_COMPOSITE, top);
                context.set(TopicTreeViewer.USE_DOCUMENT_AND_CONTACT_FILTER, true);
                context.set(TopicTreeViewer.USE_ALL, false);

                topicTreeViewer = ContextInjectionFactory.make(TopicTreeViewer.class, context);
                topicTreeViewer.setAddressManager(addressManager);
                topicTreeViewer.disableSorting();
                topicTreeViewer.setInput(categories);

                final java.util.function.Function<DummyStringCategory, String> categorySummarizer = cat -> {
                    final java.util.Optional<Double> sum = documentsDAO.sumAllDocumentsWithinCategory(cat);
                    return sum.isPresent() ? numberFormatterService.doubleToFormattedPrice(sum.get()) : "--";
                };

                final com.sebulli.fakturama.views.datatable.tree.ui.TreeCategoryLabelProvider treeTableLabelProvider = new com.sebulli.fakturama.views.datatable.tree.ui.TreeCategoryLabelProvider(
                        categorySummarizer);
                ContextInjectionFactory.inject(treeTableLabelProvider, context);
                topicTreeViewer.setLabelProvider(treeTableLabelProvider);
            } catch (final PersistenceException e) {
                // if no database is created an exception occurs at this point
                log.warn("Category tree couldn't be created, perhaps because of initially startup?");
            }
        }
        return topicTreeViewer;
    }

    private ca.odell.glazedlists.EventList<DummyStringCategory> categories;

    /**
     * Handle an incoming refresh command. This could be initiated by an editor
     * which has just saved a new element (document, Document, payment etc).
     * Here we ONLY listen to "DocumentEditor" events.<br />
     * The tree of Categories is not updated because it is a (more or less)
     * static tree.
     *
     * @param message
     *            an incoming message
     */
    @Inject
    @Optional
    public void handleRefreshEvent(@UIEventTopic(DocumentEditor.EDITOR_ID) final String message) {
        if (StringUtils.equals(message, Editor.UPDATE_EVENT) && !top.isDisposed()) {
            sync.syncExec(() -> top.setRedraw(false));
            reloadDocumentList();
            GlazedLists.replaceAll(categories, GlazedLists.eventList(documentsDAO.getCategoryStrings()), false);
            sync.syncExec(() -> top.setRedraw(true));
        }
    }

    /**
     * Set the category filter with a given {@link TreeObjectType}.
     *
     * @param filter
     *            The new filter string
     * @param treeObjectType
     *            the {@link TreeObjectType}
     */
    @Override
    public void setCategoryFilter(final String filter, final TreeObjectType treeObjectType) {
        if (!filter.equals(NO_CATEGORY_LABEL)) {
            String plainCategoryLabel = null;
            if (isHeaderLabelEnabled()) {
                if (filter.endsWith(TreeObjectType.CONTACTS_ROOTNODE.getDefaultName())) {
                    filterLabel.setText(" ");
                } else {
                    if (treeObjectType == TreeObjectType.TRANSACTIONS_ROOTNODE) {
                        filterLabel.setText(msg.topictreeLabelThistransaction);
                        registry.register(filterLabel::setText, () -> msg.topictreeLabelThistransaction);
                    } else {
                        plainCategoryLabel = StringUtils.removeStart(filter, "/");
                        filterLabel.setText(plainCategoryLabel);
                    }
                }
            }

            currentCategoryName = filter;
            currentCategoryType = treeObjectType;
            reloadDocumentList();
            // documentListData.size() is the real total for this filter (from countPage(), not
            // just what's been paged in so far) - we already fetch it, so show it in front of the
            // title instead of making the user count rows or guess.
            if (plainCategoryLabel != null) {
                filterLabel.setText(documentListData.size() + " " + plainCategoryLabel);
            }
        }

        filterLabel.pack(true);
    }

    @Override
    public void changeToolbarItem(final TreeObject treeObject) {
        final MToolBar toolbar = listTablePart.getToolbar();
        for (final MToolBarElement tbElem : toolbar.getChildren()) {
            if (tbElem.getElementId().contentEquals(getToolbarAddItemCommandId())) {
                final HandledToolItemImpl toolItem = (HandledToolItemImpl) tbElem;
                ParameterizedCommand wbCommand = toolItem.getWbCommand();
                @SuppressWarnings("unchecked")
                final Map<String, Object> parameterMap = wbCommand != null ? wbCommand.getParameterMap() : new HashMap<>();
                if (treeObject.getDocType() != null) {
                    toolItem.setTooltip(msg.commandNewTooltip + " " + msg.getMessageFromKey(treeObject.getDocType().getSingularKey()));
                    parameterMap.put(CallEditor.PARAM_CATEGORY, treeObject.getDocType().name());
                } else {
                    toolItem.setTooltip(msg.commandNewTooltip + " " + msg.getMessageFromKey(DocumentType.ORDER.getSingularKey()));
                    parameterMap.put(CallEditor.PARAM_CATEGORY, DocumentType.ORDER.name());
                }
                parameterMap.put(CallEditor.PARAM_FORCE_NEW, Boolean.TRUE);

                if (wbCommand != null) {
                    wbCommand = ParameterizedCommand.generateCommand(wbCommand.getCommand(), parameterMap);
                } else {
                    parameterMap.put(CallEditor.PARAM_EDITOR_TYPE, DocumentEditor.ID);
                    wbCommand = commandService.createCommand(CommandIds.CMD_CALL_EDITOR, parameterMap);
                }
                toolItem.setWbCommand(wbCommand);
            }
        }
        changePopupEntries(treeObject.getDocType());
    }

    protected void changePopupEntries(final DocumentType documentType) {
        final BillingType selectedObjectType = (getSelectedObject() != null) ? getSelectedObject().getBillingType() : BillingType.NONE;

        listTablePart.getMenus().stream().filter(menu -> menu.getElementId().contentEquals(POPUP_ID))
                .forEach(popupMenu -> popupMenu.getChildren().stream().filter(entry -> entry.getTags().contains("orderActive"))
                        .forEach(foundEntry -> foundEntry.setVisible(documentType == DocumentType.ORDER || selectedObjectType == BillingType.ORDER)));
        listTablePart.getMenus().stream().filter(menu -> menu.getElementId().contentEquals(POPUP_ID))
                .forEach(popupMenu -> popupMenu.getChildren().stream().filter(entry -> entry.getTags().contains("deliveryActive"))
                        .forEach(foundEntry -> foundEntry.setVisible(documentType == DocumentType.DELIVERY || selectedObjectType == BillingType.DELIVERY)));

        final boolean canBePaid = java.util.Optional.ofNullable(documentType).orElse(DocumentType.NONE).canBePaid()
                || DocumentType.findByKey(selectedObjectType.getValue()).canBePaid();
        listTablePart.getMenus().stream().filter(menu -> menu.getElementId().contentEquals(POPUP_ID)).forEach(popupMenu -> popupMenu.getChildren().stream()
                .filter(entry -> entry.getTags().contains("canBePaidActive")).forEach(foundEntry -> foundEntry.setVisible(canBePaid)));
    }

    @Override
    public void setContactFilter(final long filter) {
        final DocumentReceiver contact = contactsDAO.findById(filter);
        if (contact != null) {
            setCategoryFilter(contactUtil.getNameWithCompany(contact), TreeObjectType.CONTACTS_ROOTNODE);
        }
    }

    @Override
    public void setTransactionFilter(final long filter, final TreeObject treeObject) {
        setCategoryFilter(Long.toString(filter), TreeObjectType.TRANSACTIONS_ROOTNODE);
    }

    @Override
    protected boolean isHeaderLabelEnabled() {
        return true;
    }

    @Override
    public String getTableId() {
        return ID;
    }

    @Override
    protected String getEditorId() {
        return DocumentEditor.ID;
    }

    @Override
    protected String getEditorTypeId() {
        return DocumentEditor.class.getSimpleName();
    }

    @Override
    public Document[] getSelectedObjects() {
        final IStructuredSelection sel = documentsViewer.getStructuredSelection();
        @SuppressWarnings("unchecked")
        final List<Document> selectedObjects = new ArrayList<>(sel.toList());
        final Document[] retArr = selectedObjects.toArray(new Document[selectedObjects.size()]);
        selectionService.setSelection(selectedObjects);
        return retArr;
    }

    @Override
    public Document getSelectedObject() {
        final Document[] selectedObjects = getSelectedObjects();
        return selectedObjects != null && selectedObjects.length > 0 ? selectedObjects[0] : null;
    }

    @Override
    protected String getPopupId() {
        return POPUP_ID;
    }

    @Override
    protected Document handleCascadeDelete(final Document tmpDocument) {
        // before deletion first update stock
        if (BooleanUtils.isTrue(tmpDocument.getPrinted())) {
            tmpDocument.getItems().stream().forEach(oldItem -> {
                oldItem.setOriginQuantity(oldItem.getQuantity());
                oldItem.setQuantity(null);
            });
        }

        final StockUpdateHandler stockUpdateHandler = ContextInjectionFactory.make(StockUpdateHandler.class, context);
        stockUpdateHandler.updateStockQuantity(top.getShell(), null, tmpDocument);
        return tmpDocument;
    }

    @Override
    protected String getToolbarAddItemCommandId() {
        return CommandIds.LISTTOOLBAR_ADD_DOCUMENT;
    }

    @Override
    protected MToolBar getMToolBar() {
        return listTablePart.getToolbar();
    }

    @Override
    protected AbstractDAO<Document> getEntityDAO() {
        return documentsDAO;
    }
}
