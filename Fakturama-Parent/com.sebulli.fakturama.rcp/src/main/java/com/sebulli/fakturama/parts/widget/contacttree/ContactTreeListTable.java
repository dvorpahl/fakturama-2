/**
 *
 */
package com.sebulli.fakturama.parts.widget.contacttree;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILazyContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.sebulli.fakturama.Activator;
import com.sebulli.fakturama.dao.AbstractDAO;
import com.sebulli.fakturama.dao.ContactCategoriesDAO;
import com.sebulli.fakturama.dao.DebitorAddress;
import com.sebulli.fakturama.handlers.CallEditor;
import com.sebulli.fakturama.handlers.CommandIds;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.ContactCategory;
import com.sebulli.fakturama.model.ContactType;
import com.sebulli.fakturama.parts.DebitorEditor;
import com.sebulli.fakturama.parts.DocumentEditor;
import com.sebulli.fakturama.parts.Editor;
import com.sebulli.fakturama.parts.widget.search.TextSearchControl;
import com.sebulli.fakturama.views.datatable.contacts.ContactListDescriptor;
import com.sebulli.fakturama.views.datatable.layer.PagedEntityEventList;
import com.sebulli.fakturama.views.datatable.tree.ui.TopicTreeViewer;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

/**
 * "Adresse ausw&auml;hlen" dialog base class: a search-driven contact/address picker.
 * <p>
 * Uses the same native SWT.VIRTUAL {@link Table} + JFace {@link ILazyContentProvider} approach as
 * {@code DocumentsListTable}/{@code ProductListTable}/{@code ContactListTable} - unified onto one
 * pattern per the user's request, replacing the previous "background-thread progressively loads
 * the *entire* table in batches right after opening" approach (which, while off the UI thread,
 * still eagerly pulled every debitor/creditor into memory regardless of whether the user ever
 * scrolled that far - not actually lazy, just asynchronous). {@link #loadContactPage} already
 * queries the DB directly per visible page (fixed earlier - see {@code DebitorsDAO#findForTreeListView}),
 * so this widget now only ever asks for rows it's about to paint.
 */
@SuppressWarnings("unchecked")
public abstract class ContactTreeListTable<K extends DebitorAddress> {
    @Inject
    private IPreferenceStore eclipsePrefs;

    @Inject
    protected UISynchronize sync;

    @Inject
    protected IEclipseContext context;

    @Inject
    protected ESelectionService selectionService;

    @Inject
    protected ILogger log;

    @Inject
    @Translation
    protected Messages msg;

    @Inject
    protected EHandlerService handlerService;

    @Inject
    protected ECommandService commandService;

    /**
     * Event Broker for sending update events from the list table
     */
    @Inject
    protected IEventBroker evtBroker;

    @Inject
    protected EMenuService menuService;

    //The top composite
    protected Composite top;

    protected TableColumnLayout tableColumnLayout;

    /**
     * a new sophisticated search control which displays a magnifying glass and
     * an eraser icon.
     * This is the default under Linux and Mac OS, but not under Windows. Here
     * we have a nice
     * widget for all platforms.
     */
    protected TextSearchControl searchText;

    // ID of this view
    public static final String ID = "fakturama.views.contactTreeTable";

    private static final String POPUP_ID = "com.sebulli.fakturama.contactlist.popup";
    public static final String SELECTED_CONTACT_ID = "fakturama.treecontactlist.selectedcontactid";
    public static final String SELECTED_ADDRESS_ID = "fakturama.treecontactlist.selectedaddressid";

    protected EventList<ContactCategory> categories;

    @Inject
    protected ContactCategoriesDAO contactCategoriesDAO;

    protected MPart listTablePart;

    private K selectedObject;
    private ContactType contactType;

    // The topic tree viewer displays the categories of the UniDataSets
    protected TopicTreeViewer<ContactCategory> topicTreeViewer;

    // The standard UniDataSet
    protected String stdPropertyKey = null;

    protected Control natTable;

    private static final int BATCH_SIZE = 30;
    private static final int SEARCH_DEBOUNCE_SHORT_MS = 500;
    private static final int SEARCH_DEBOUNCE_LONG_MS = 300;

    private TableViewer contactsViewer;
    private Table table;
    private PagedEntityEventList<K> contactListData;
    private String currentSearchTerm;
    private final Runnable searchDebounceRunnable = this::applyDebouncedSearch;

    @PostConstruct
    public Control createPartControl(final Composite parent, final MPart listTablePart) {
        // Create the top composite
        top = new Composite(parent, SWT.NONE);
        GridLayoutFactory.fillDefaults().margins(0, 0).numColumns(2).applyTo(top);

        Composite searchAndTableComposite = top;
        // Create the composite that contains the search field and the table
        searchAndTableComposite = createSearchAndTableComposite(top);
        natTable = createListTable(searchAndTableComposite);

        natTable.addDisposeListener(e -> onStop(natTable));

        this.listTablePart = listTablePart;
        // if another click handler is set we use it
        final Object commandId = this.listTablePart.getTransientData().get(Constants.PROPERTY_CONTACTS_CLICKHANDLER);
        hookDoubleClickCommand(commandId != null ? (String) commandId : null);

        GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(top);
        return top;
    }

    /**
     * Component for the Search field and the item table
     */
    private Composite createSearchAndTableComposite(final Composite parent) {
        final Composite searchAndTableComposite = new Composite(parent, SWT.NONE);
        GridLayoutFactory.swtDefaults().margins(0, 0).numColumns(1).applyTo(searchAndTableComposite);
        GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(searchAndTableComposite);

        // Create the composite that contains the search field and the toolbar
        final Composite searchAndToolbarComposite = new Composite(searchAndTableComposite, SWT.NONE);
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(searchAndToolbarComposite);
        GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(searchAndToolbarComposite);

        // The search composite
        final Composite searchComposite = new Composite(searchAndToolbarComposite, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(searchComposite);
        GridDataFactory.fillDefaults().grab(true, true).align(SWT.END, SWT.CENTER).applyTo(searchComposite);

        // Search label an search field
        final Label searchLabel = new Label(searchComposite, SWT.NONE);
        searchLabel.setText(msg.commonLabelSearchfield);
        GridDataFactory.swtDefaults().applyTo(searchLabel);

        searchText = new TextSearchControl(searchComposite, false, msg);
        hookSearchDebounce();

        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).hint(150, -1).applyTo(searchText);
        return searchAndTableComposite;
    }

    public TextSearchControl getSearchControl() {
        return searchText;
    }

    protected String createRootNodeDescriptor(final String filter) {
        String rootNode = "";
        final String[] splittedString = filter.split("/");
        if (splittedString.length > 1) {
            rootNode = splittedString[1];
        }
        return rootNode;
    }

    protected Control createListTable(final Composite searchAndTableComposite) {
        // Was a local variable shadowing the "contactType" field above, so the field itself -
        // which handleRefreshEvent() reads - was never actually set and stayed null forever.
        final BillingType currentBillingType = (BillingType) context.get("ADDRESS_TYPE");
        switch (currentBillingType) {
            case INVOICE:
                contactType = ContactType.BILLING;
                break;
            case DELIVERY:
                contactType = ContactType.DELIVERY;
                break;
            default:
                contactType = ContactType.BILLING;
                break;
        }

        final Composite tableComposite = new Composite(searchAndTableComposite, SWT.NONE);
        tableColumnLayout = new TableColumnLayout();
        tableComposite.setLayout(tableColumnLayout);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(tableComposite);

        table = new Table(tableComposite, SWT.FULL_SELECTION | SWT.BORDER | SWT.VIRTUAL);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyHeaderStyle(table);
        com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyFixedRowHeight(table, 1.7f);
        contactsViewer = new TableViewer(table);
        contactsViewer.setUseHashlookup(true);

        createColumns();

        contactListData = new PagedEntityEventList<>(BATCH_SIZE, this::loadContactPageInternal, this::countContactsInternal);
        contactsViewer.setContentProvider(new ILazyContentProvider() {
            @Override
            public void updateElement(final int index) {
                final K item = contactListData.get(index);
                // Can be null: a stale SetData request for an index beyond the list's new,
                // just-shrunk size (see PagedEntityEventList#get's javadoc).
                if (item == null) {
                    return;
                }
                contactsViewer.replace(item, index);
                com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyZebraStripe(table, index);
            }

            @Override
            public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
                // nothing to do - contactListData is set up before setInput() below
            }
        });
        contactsViewer.setInput(new Object());
        table.setItemCount((int) contactListData.size());

        hookSelection();

        return table;
    }

    /** Percentages for all 9 columns from {@link ContactListDescriptor#getContactPropertyNames()}. */
    private void createColumns() {
        for (final ContactListDescriptor descriptor : ContactListDescriptor.values()) {
            final TableViewerColumn viewerColumn = new TableViewerColumn(contactsViewer, SWT.LEFT);
            final TableColumn column = viewerColumn.getColumn();
            column.setText(msg.getMessageFromKey(descriptor.getMessageKey()));
            viewerColumn.setLabelProvider(createLabelProvider(descriptor));
            tableColumnLayout.setColumnData(column, new ColumnWeightData(descriptor.getDefaultWidth(), 20, true));
        }
    }

    private ColumnLabelProvider createLabelProvider(final ContactListDescriptor descriptor) {
        return new ColumnLabelProvider() {
            @Override
            public String getText(final Object element) {
                final K item = (K) element;
                switch (descriptor) {
                    case NO:
                        return item.getCustomerNumber();
                    case FIRSTNAME:
                        return item.getFirstName();
                    case LASTNAME:
                        return item.getName();
                    case COMPANY:
                        return item.getCompany() != null ? StringUtils.substringBefore(item.getCompany(), StringUtils.CR) : "";
                    case ZIP:
                        return item.getAddress() != null ? item.getAddress().getZip() : "";
                    case CITY:
                        return item.getAddress() != null ? item.getAddress().getCity() : "";
                    case TYPE:
                        return item.getAddress() != null
                                ? item.getAddress().getContactTypes().stream().map(ContactType::getName).collect(java.util.stream.Collectors.joining(","))
                                : "";
                    case NAMEADDON:
                        return item.getAddress() != null ? item.getAddress().getName() : "";
                    case LOCALCONSULTANT:
                        return item.getAddress() != null ? item.getAddress().getLocalConsultant() : "";
                    default:
                        return "";
                }
            }
        };
    }

    private void hookSelection() {
        contactsViewer.addSelectionChangedListener((final org.eclipse.jface.viewers.SelectionChangedEvent event) -> {
            final IStructuredSelection sel = (IStructuredSelection) event.getSelection();
            selectedObject = (K) sel.getFirstElement();
        });
    }

    private void hookDoubleClickCommand(final String commandId) {
        contactsViewer.addDoubleClickListener((final org.eclipse.jface.viewers.DoubleClickEvent event) -> {
            if (selectedObject == null) {
                return;
            }
            final Map<String, Object> params = new HashMap<>();
            ParameterizedCommand parameterizedCommand;
            if (commandId != null) {
                final Map<String, Object> eventParams = new HashMap<>();
                eventParams.put(DocumentEditor.DOCUMENT_ID, context.get(DocumentEditor.DOCUMENT_ID));
                eventParams.put(SELECTED_ADDRESS_ID, Long.valueOf(selectedObject.getAddress().getId()));
                eventParams.put(SELECTED_CONTACT_ID, Long.valueOf(selectedObject.getContactId()));
                evtBroker.post("DialogSelection/Contact", eventParams);
                evtBroker.post("DialogAction/CloseContact", eventParams);
            } else {
                params.put(CallEditor.PARAM_EDITOR_TYPE, getEditorId());
                parameterizedCommand = commandService.createCommand(CommandIds.CMD_CALL_EDITOR, params);
                handlerService.executeHandler(parameterizedCommand);
            }
        });
    }

    public K getSelectedObject() {
        return selectedObject;
    }

    /** {@link PagedEntityEventList.PageLoader} for contactListData - the current (debounced) search term. */
    private List<K> loadContactPageInternal(final int firstResult, final int maxResults) {
        return loadContactPage(contactType, currentSearchTerm, firstResult, maxResults);
    }

    private long countContactsInternal() {
        return countContacts(contactType, currentSearchTerm);
    }

    private void reloadContactList() {
        contactListData.reload(this::loadContactPageInternal, this::countContactsInternal);
        if (table != null && !table.isDisposed()) {
            table.setItemCount((int) contactListData.size());
            contactsViewer.refresh();
        }
    }

    /**
     * Debounces the search box: a DB query per keystroke would be wasteful. Waits {@link
     * #SEARCH_DEBOUNCE_SHORT_MS} while the search term is still short (likely mid-typing) and only
     * {@link #SEARCH_DEBOUNCE_LONG_MS} once it's 3+ characters (specific enough to act on quickly).
     */
    private void hookSearchDebounce() {
        searchText.getTextControl().addModifyListener(e -> {
            final Display display = searchText.getTextControl().getDisplay();
            final int debounceMs = searchText.getTextControl().getText().length() >= 3 ? SEARCH_DEBOUNCE_LONG_MS : SEARCH_DEBOUNCE_SHORT_MS;
            display.timerExec(-1, searchDebounceRunnable);
            display.timerExec(debounceMs, searchDebounceRunnable);
        });
    }

    private void applyDebouncedSearch() {
        if (searchText.getTextControl().isDisposed()) {
            return;
        }
        currentSearchTerm = StringUtils.trimToNull(searchText.getTextControl().getText());
        reloadContactList();
    }

    protected abstract String getEditorTypeId();

    /**
     * One page of debitors/creditors matching {@code searchTerm} (or every one, if blank/null),
     * ordered the same way the DAO's tree-view query does. A real {@code WHERE}/{@code LIMIT}
     * query either way, never client-side filtering of an already-loaded list.
     */
    protected abstract List<K> loadContactPage(ContactType contactType, String searchTerm, int firstResult, int maxResults);

    /** Row count for the same criteria as {@link #loadContactPage}, without loading entities. */
    protected abstract long countContacts(ContactType contactType, String searchTerm);

    protected abstract Class<K> getEntityClass();

    protected abstract AbstractDAO<? extends Contact> getEntityDAO();

    public String getTableId() {
        return ID;
    }

    protected String getEditorId() {
        return DebitorEditor.ID;
    }

    public void handleRefreshEvent(final String message) {
        if (StringUtils.equals(message, Editor.UPDATE_EVENT) && !top.isDisposed()) {
            sync.syncExec(() -> top.setRedraw(false));
            reloadContactList();
            GlazedLists.replaceAll(categories, GlazedLists.eventList(contactCategoriesDAO.findAll(true)), false);
            sync.syncExec(() -> top.setRedraw(true));
        }
    }

    protected boolean isHeaderLabelEnabled() {
        return false;
    }

    /**
     * Loads the table settings (layout and such stuff) from a properties file.
     */
    public void onStart(final Control control) {
        final Properties properties = new Properties();
        final String requestedWorkspace = getEclipsePrefs().getString(Constants.GENERAL_WORKSPACE);
        final Path propertiesFile = Paths.get(requestedWorkspace, Constants.VIEWTABLE_PREFERENCES_FILE);

        try (InputStream propertiesInputStream = Files.newInputStream(propertiesFile, StandardOpenOption.READ);) {
            properties.load(propertiesInputStream);
            log.debug("Loading state from " + Constants.VIEWTABLE_PREFERENCES_FILE);
        } catch (final IOException e) {
            log.warn(Constants.VIEWTABLE_PREFERENCES_FILE + " not found, skipping load");
            if (Files.notExists(propertiesFile)) {
                try {
                    Files.createFile(propertiesFile);
                } catch (final IOException ioex) {
                    log.error(ioex, Constants.VIEWTABLE_PREFERENCES_FILE + " could not be created.");
                }
            }
        }
    }

    /**
     * Before the widget is disposed - kept as a no-op hook point (NatTable-specific column
     * width/sort-state persistence no longer applies to a plain SWT Table).
     */
    public void onStop(final Control control) {
        // intentionally empty - see javadoc
    }

    protected String getPopupId() {
        return POPUP_ID;
    }

    protected String getToolbarAddItemCommandId() {
        return CommandIds.LISTTOOLBAR_ADD_CONTACT;
    }

    protected MToolBar getMToolBar() {
        return listTablePart.getToolbar();
    }

    @Focus
    public void focus() {
        if (natTable != null) {
            natTable.setFocus();
        }
    }

    /**
     * @return the eclipsePrefs
     */
    protected IPreferenceStore getEclipsePrefs() {
        if (eclipsePrefs == null) {
            eclipsePrefs = EclipseContextFactory.getServiceContext(Activator.getContext()).get(IPreferenceStore.class);
        }
        return eclipsePrefs;
    }

}
