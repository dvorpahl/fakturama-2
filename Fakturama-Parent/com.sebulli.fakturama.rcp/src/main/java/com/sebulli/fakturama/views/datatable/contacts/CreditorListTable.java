/**
 *
 */
package com.sebulli.fakturama.views.datatable.contacts;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import com.sebulli.fakturama.dao.AbstractDAO;
import com.sebulli.fakturama.dao.CreditorsDAO;
import com.sebulli.fakturama.handlers.CommandIds;
import com.sebulli.fakturama.model.Creditor;
import com.sebulli.fakturama.parts.CreditorEditor;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

/**
 * View with the table of all creditors
 *
 */
public class CreditorListTable extends ContactListTable<Creditor> {

    // ID of this view
    public static final String ID = "fakturama.views.creditorTable";

    private static final String POPUP_ID = "com.sebulli.fakturama.creditorlist.popup";
    public static final String SELECTED_CREDITOR_ID = "fakturama.creditorlist.selectedcreditorid";

    @Inject
    private CreditorsDAO creditorDAO;

    @Override
    public String getTableId() {
        return ID;
    }

    @Override
    protected String getEditorId() {
        return CreditorEditor.ID;
    }

    @Override
    protected String getEditorTypeId() {
        return CreditorEditor.EDITOR_ID;
    }

    @Override
    protected String getToolbarAddItemCommandId() {
        return CommandIds.LISTTOOLBAR_ADD_CREDITOR;
    }

    @Inject
    @Optional
    public void handleRefreshEvent(@UIEventTopic(CreditorEditor.EDITOR_ID) final String message) {
        super.handleRefreshEvent(message);
    }

    @Override
    protected String getPopupId() {
        return POPUP_ID;
    }

    @Override
    protected List<Creditor> loadContactPage(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType,
            final String orderByProperty, final boolean descending, final int firstResult, final int maxResults) {
        return creditorDAO.findPage(searchTerm, categoryName, treeObjectType, orderByProperty, descending, firstResult, maxResults);
    }

    @Override
    protected long countContacts(final String searchTerm, final String categoryName, final TreeObjectType treeObjectType) {
        return creditorDAO.countPage(searchTerm, categoryName, treeObjectType);
    }

    @Override
    protected AbstractDAO<Creditor> getEntityDAO() {
        return creditorDAO;
    }

    @Override
    protected Class<Creditor> getEntityClass() {
        return Creditor.class;
    }
}
