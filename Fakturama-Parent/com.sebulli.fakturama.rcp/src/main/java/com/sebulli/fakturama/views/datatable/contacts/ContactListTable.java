/**
 *
 */
package com.sebulli.fakturama.views.datatable.contacts;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.sebulli.fakturama.dao.ContactCategoriesDAO;
import com.sebulli.fakturama.dao.ContactsDAO;
import com.sebulli.fakturama.handlers.CallEditor;
import com.sebulli.fakturama.handlers.CommandIds;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.model.Address;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.ContactCategory;
import com.sebulli.fakturama.parts.DebitorEditor;
import com.sebulli.fakturama.parts.DocumentEditor;
import com.sebulli.fakturama.parts.Editor;
import com.sebulli.fakturama.views.datatable.AbstractViewDataTable;
import com.sebulli.fakturama.views.datatable.layer.EntityGridListLayer;
import com.sebulli.fakturama.views.datatable.layer.PagedEntityEventList;
import com.sebulli.fakturama.views.datatable.tree.ui.TopicTreeViewer;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeCategoryLabelProvider;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

/**
 * View with the table of all contacts (Debitoren/Kreditoren).
 * <p>
 * Uses the same native SWT.VIRTUAL {@link Table} + JFace {@link ILazyContentProvider} approach as
 * {@code DocumentsListTable}/{@code ProductListTable} - see {@code DocumentsListTable}'s javadoc
 * for why. {@code getListData(true)} used to load the *entire* debitor/creditor table into a
 * GlazedLists EventList on every open/refresh; now only {@link #CONTACT_BATCH_SIZE}-row pages are
 * loaded on demand.
 */
public abstract class ContactListTable<T extends Contact> extends AbstractViewDataTable<T, ContactCategory> {
    @Inject
    protected UISynchronize sync;

    @Inject
    protected IEclipseContext context;

    // ID of this view
    public static final String ID = "fakturama.views.contactTable";

    private static final String POPUP_ID = "com.sebulli.fakturama.contactlist.popup";
    public static final String SELECTED_CONTACT_ID = "fakturama.contactlist.selectedcontactid";

    private static final int CONTACT_BATCH_SIZE = 30;
    private static final int SEARCH_DEBOUNCE_MS = 300;

    protected EventList<ContactCategory> categories;

    @Inject
    private ContactsDAO contactDAO;

    @Inject
    protected ContactCategoriesDAO contactCategoriesDAO;

    protected MPart listTablePart;

    private T selectedObject;

    private TableViewer contactsViewer;
    private Table table;
    private TableColumn sortedColumn;

    private PagedEntityEventList<T> contactListData;
    private String currentSearchTerm;
    private String currentCategoryName;
    private TreeObjectType currentCategoryType = TreeObjectType.ALL_NODE;
    private String currentSortProperty;
    private boolean currentSortDescending;
    private final Runnable searchDebounceRunnable = this::applyDebouncedSearchTerm;

    @PostConstruct
    public Control createPartControl(final Composite parent, final MPart listTablePart) {
        log.debug("create Contact list part");
        this.listTablePart = listTablePart;
        super.createPartControl(parent, Contact.class, true, ID);
        topicTreeViewer.setTable(this);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(top);
        return top;
    }

    @Override
    public T getSelectedObject() {
        return selectedObject;
    }

    @Override
    public T[] getSelectedObjects() {
        final IStructuredSelection sel = contactsViewer.getStructuredSelection();
        @SuppressWarnings("unchecked")
        final List<T> selectedObjects = new java.util.ArrayList<>(sel.toList());
        @SuppressWarnings("unchecked")
        final T[] retArr = selectedObjects.toArray((T[]) new Contact[selectedObjects.size()]);
        selectionService.setSelection(selectedObjects);
        return retArr;
    }

    @Override
    protected Control createListTable(final Composite searchAndTableComposite) {
        final Composite tableComposite = new Composite(searchAndTableComposite, SWT.NONE);
        tableColumnLayout = new TableColumnLayout();
        tableComposite.setLayout(tableColumnLayout);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(tableComposite);

        table = new Table(tableComposite, SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER | SWT.VIRTUAL);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyHeaderStyle(table);
        contactsViewer = new TableViewer(table);
        contactsViewer.setUseHashlookup(true);

        createColumns();

        contactListData = new PagedEntityEventList<>(CONTACT_BATCH_SIZE, this::loadContactPageInternal, this::countContactsInternal);
        contactsViewer.setContentProvider(new ILazyContentProvider() {
            @Override
            public void updateElement(final int index) {
                final T contact = contactListData.get(index);
                // Can be null: a stale SetData request for an index beyond the list's new,
                // just-shrunk size (see PagedEntityEventList#get's javadoc).
                if (contact == null) {
                    return;
                }
                contactsViewer.replace(contact, index);
                com.sebulli.fakturama.views.datatable.common.ModernTableStyle.applyZebraStripe(table, index);
            }

            @Override
            public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
                // nothing to do - contactListData is set up before setInput() below
            }
        });
        contactsViewer.setInput(new Object());
        table.setItemCount((int) contactListData.size());
        hookSearchDebounce();
        hookSelectionAndDoubleClick();

        return table;
    }

    /** One {@link TableViewerColumn} per visible {@link ContactListDescriptor} (matches {@code ContactsDAO#getVisibleProperties}: NO/FIRSTNAME/LASTNAME/COMPANY/ZIP/CITY). */
    private void createColumns() {
        final ContactListDescriptor[] visible = { ContactListDescriptor.NO, ContactListDescriptor.FIRSTNAME, ContactListDescriptor.LASTNAME,
                ContactListDescriptor.COMPANY, ContactListDescriptor.ZIP, ContactListDescriptor.CITY };
        for (final ContactListDescriptor descriptor : visible) {
            final TableViewerColumn viewerColumn = new TableViewerColumn(contactsViewer, SWT.LEFT);
            final TableColumn column = viewerColumn.getColumn();
            column.setText(msg.getMessageFromKey(descriptor.getMessageKey()));
            viewerColumn.setLabelProvider(createLabelProvider(descriptor));
            column.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent e) {
                    onColumnSelected(descriptor, column);
                }
            });
            tableColumnLayout.setColumnData(column, new ColumnWeightData(descriptor.getDefaultWidth(), 30, true));
        }
    }

    private void onColumnSelected(final ContactListDescriptor descriptor, final TableColumn column) {
        // ZIP/CITY are address.* paths, not sortable columns on the Contact entity itself.
        if (descriptor == ContactListDescriptor.ZIP || descriptor == ContactListDescriptor.CITY) {
            return;
        }
        final boolean descending = column == sortedColumn && !currentSortDescending;
        currentSortProperty = descriptor.getPropertyName();
        currentSortDescending = descending;
        reloadContactList();
        table.setSortColumn(column);
        table.setSortDirection(descending ? SWT.DOWN : SWT.UP);
        sortedColumn = column;
    }

    private ColumnLabelProvider createLabelProvider(final ContactListDescriptor descriptor) {
        return new ColumnLabelProvider() {
            @Override
            public String getText(final Object element) {
                @SuppressWarnings("unchecked")
                final T contact = (T) element;
                switch (descriptor) {
                    case NO:
                        return contact.getCustomerNumber();
                    case FIRSTNAME:
                        return contact.getFirstName();
                    case LASTNAME:
                        return contact.getName();
                    case COMPANY:
                        return contact.getCompany() != null ? StringUtils.substringBefore(contact.getCompany(), StringUtils.CR) : "";
                    case ZIP:
                        return firstAddress(contact).map(Address::getZip).orElse("");
                    case CITY:
                        return firstAddress(contact).map(Address::getCity).orElse("");
                    default:
                        return "";
                }
            }
        };
    }

    private Optional<Address> firstAddress(final T contact) {
        return contact.getAddresses().stream().min(Comparator.comparingLong(Address::getId));
    }

    private void hookSelectionAndDoubleClick() {
        contactsViewer.addSelectionChangedListener((final org.eclipse.jface.viewers.SelectionChangedEvent event) -> {
            final IStructuredSelection sel = (IStructuredSelection) event.getSelection();
            @SuppressWarnings("unchecked")
            final T sole = (T) sel.getFirstElement();
            selectedObject = sole;
            selectionService.setSelection(sel.toList());
        });

        contactsViewer.addDoubleClickListener((final org.eclipse.jface.viewers.DoubleClickEvent event) -> {
            if (selectedObject == null) {
                return;
            }
            final Object commandId = this.listTablePart.getTransientData().get(Constants.PROPERTY_CONTACTS_CLICKHANDLER);
            if (commandId != null) {
                final Map<String, Object> eventParams = new HashMap<>();
                eventParams.put(DocumentEditor.DOCUMENT_ID, context.get(DocumentEditor.DOCUMENT_ID));
                eventParams.put(SELECTED_CONTACT_ID, Long.valueOf(selectedObject.getId()));
                evtBroker.post("DialogSelection/Contact", eventParams);
                evtBroker.post("DialogAction/CloseContact", eventParams);
            } else {
                final Map<String, Object> params = new HashMap<>();
                params.put(CallEditor.PARAM_OBJ_ID, Long.toString(selectedObject.getId()));
                params.put(CallEditor.PARAM_EDITOR_TYPE, getEditorId());
                context.getParent().get(ESelectionService.class).setSelection(null);
                final ParameterizedCommand parameterizedCommand = commandService.createCommand(CommandIds.CMD_CALL_EDITOR, params);
                handlerService.executeHandler(parameterizedCommand);
            }
        });
    }

    /** Subclasses delegate to {@code DebitorsDAO#findPage}/{@code CreditorsDAO#findPage}. */
    protected abstract List<T> loadContactPage(String searchTerm, String categoryName, TreeObjectType treeObjectType, String orderByProperty,
            boolean descending, int firstResult, int maxResults);

    /** Subclasses delegate to {@code DebitorsDAO#countPage}/{@code CreditorsDAO#countPage}. */
    protected abstract long countContacts(String searchTerm, String categoryName, TreeObjectType treeObjectType);

    private List<T> loadContactPageInternal(final int firstResult, final int maxResults) {
        return loadContactPage(currentSearchTerm, currentCategoryName, currentCategoryType, currentSortProperty, currentSortDescending, firstResult,
                maxResults);
    }

    private long countContactsInternal() {
        return countContacts(currentSearchTerm, currentCategoryName, currentCategoryType);
    }

    private void reloadContactList() {
        contactListData.reload(this::loadContactPageInternal, this::countContactsInternal);
        if (table != null && !table.isDisposed()) {
            table.setItemCount((int) contactListData.size());
            contactsViewer.refresh();
        }
    }

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
        reloadContactList();
    }

    @Override
    protected T handleCascadeDelete(final T objToDelete) {
        // set all addresses to deleted
        objToDelete.getAddresses().forEach(adr -> adr.setDeleted(true));
        return objToDelete;
    }

    @Override
    public String getTableId() {
        return ID;
    }

    @Override
    protected String getEditorId() {
        return DebitorEditor.ID;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TopicTreeViewer<ContactCategory> createCategoryTreeViewer(final Composite top) {
        context.set(TopicTreeViewer.PARENT_COMPOSITE, top);
        context.set(TopicTreeViewer.USE_DOCUMENT_AND_CONTACT_FILTER, false);
        context.set(TopicTreeViewer.USE_ALL, true);

        topicTreeViewer = (TopicTreeViewer<ContactCategory>) ContextInjectionFactory.make(TopicTreeViewer.class, context);
        categories = GlazedLists.eventList(contactCategoriesDAO.findAll());
        topicTreeViewer.setInput(categories);
        topicTreeViewer.setLabelProvider(new TreeCategoryLabelProvider());
        return topicTreeViewer;
    }

    public void handleRefreshEvent(final String message) {
        if (StringUtils.equals(message, Editor.UPDATE_EVENT) && !top.isDisposed()) {
            sync.asyncExec(() -> {
                top.setRedraw(false);
                reloadContactList();
                GlazedLists.replaceAll(categories, GlazedLists.eventList(contactCategoriesDAO.findAll(true)), false);
                top.setRedraw(true);
            });
        }
    }

    @Override
    public void setCategoryFilter(final String filter, final TreeObjectType treeObjectType) {
        currentCategoryName = filter;
        currentCategoryType = treeObjectType;
        reloadContactList();
        // documentListData.size() is the real total for this filter (from countPage(), not just
        // what's been paged in so far) - prepend it, matching DocumentsListTable's "232 Kunden/..."
        // pattern the user asked to unify onto here too. The root/"all" selection (filter is blank
        // or just "/") shows no category label to prepend a count to.
        final String plainCategoryLabel = StringUtils.stripStart(filter, "/");
        if (StringUtils.isNotBlank(plainCategoryLabel)) {
            filterLabel.setText(contactListData.size() + " " + plainCategoryLabel);
        } else {
            filterLabel.setText(" ");
        }
        filterLabel.pack(true);
    }

    @Override
    protected boolean isHeaderLabelEnabled() {
        return true;
    }

    @Override
    protected EntityGridListLayer<T> getGridLayer() {
        // No NatTable/GlazedLists grid layer in this view - see the class javadoc.
        return null;
    }

    protected String getPopupId() {
        return POPUP_ID;
    }

    @Override
    protected MToolBar getMToolBar() {
        return listTablePart.getToolbar();
    }

    @Focus
    public void focus() {
        if (natTable != null) {
            natTable.setFocus();
        }
    }
}
