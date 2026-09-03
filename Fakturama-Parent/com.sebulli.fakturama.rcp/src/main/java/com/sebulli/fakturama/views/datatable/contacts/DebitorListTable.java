/**
 *
 */
package com.sebulli.fakturama.views.datatable.contacts;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import com.sebulli.fakturama.dao.AbstractDAO;
import com.sebulli.fakturama.dao.DebitorsDAO;
import com.sebulli.fakturama.handlers.CommandIds;
import com.sebulli.fakturama.model.Debitor;
import com.sebulli.fakturama.parts.DebitorEditor;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

/**
 * View with the table of all contacts
 *
 */
public class DebitorListTable extends ContactListTable<Debitor> {

    // ID of this view
    public static final String ID = "fakturama.views.debitorTable";

    private static final String POPUP_ID = "com.sebulli.fakturama.debitorlist.popup";
    public static final String SELECTED_CREDITOR_ID = "fakturama.debitorlist.selecteddebitorid";

    @Inject
    private DebitorsDAO debitorDAO;

    @Override
    public String getTableId() {
        return ID;
    }

    @Override
    protected String getEditorTypeId() {
        return DebitorEditor.EDITOR_ID;
    }

    @Inject
    @Optional
    public void handleRefreshEvent(@UIEventTopic(DebitorEditor.EDITOR_ID) final String message) {
        super.handleRefreshEvent(message);
    }

    @Override
    protected String getPopupId() {
        return POPUP_ID;
    }

    @Override
    protected String getToolbarAddItemCommandId() {
        return CommandIds.LISTTOOLBAR_ADD_DEBTOR;
    }

    @Override
    protected List<Debitor> loadContactPage(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType,
            final String orderByProperty, final boolean descending, final int firstResult, final int maxResults) {
        return debitorDAO.findPage(searchTerm, categoryName, treeObjectType, orderByProperty, descending, firstResult, maxResults);
    }

    @Override
    protected long countContacts(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType) {
        return debitorDAO.countPage(searchTerm, categoryName, treeObjectType);
    }

    @Override
    protected AbstractDAO<Debitor> getEntityDAO() {
        return debitorDAO;
    }

    @Override
    protected String getEditorId() {
        return DebitorEditor.ID;
    }

    @Override
    protected Class<Debitor> getEntityClass() {
        return Debitor.class;
    }
}
