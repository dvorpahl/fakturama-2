package com.sebulli.fakturama.parts.widget.contacttree;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.sebulli.fakturama.dao.AbstractDAO;
import com.sebulli.fakturama.dao.DebitorAddress;
import com.sebulli.fakturama.dao.DebitorsDAO;
import com.sebulli.fakturama.model.ContactType;
import com.sebulli.fakturama.model.Debitor;
import com.sebulli.fakturama.parts.DebitorEditor;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

public class DebitorTreeListTable extends ContactTreeListTable<DebitorAddress> {

    // ID of this view
    public static final String ID = "fakturama.views.debitorTreeTable";

    private static final String POPUP_ID = "com.sebulli.fakturama.debitorlist.popup";
    public static final String SELECTED_DEDITOR_ID = "fakturama.debitorlist.selecteddebitorid";

	@Inject
	private DebitorsDAO debitorDAO;
	
    @Override
    public String getTableId() {
        return ID;
    }
    @PostConstruct
    public Control createPartControl(Composite parent, MPart listTablePart) {
        return super.createPartControl(parent, listTablePart);
    }
    
    @Override
    protected String getEditorTypeId() {
        return DebitorEditor.EDITOR_ID;
    }
    
    protected String getPopupId() {
        return POPUP_ID;
    }

    @Override
    protected List<DebitorAddress> loadContactPage(ContactType contactType, String searchTerm, int firstResult, int maxResults) {
        return debitorDAO.findForTreeListView(contactType, searchTerm, firstResult, maxResults);
    }

    @Override
    protected long countContacts(ContactType contactType, String searchTerm) {
        return debitorDAO.countForTreeListView(searchTerm);
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
    protected Class<DebitorAddress> getEntityClass() {
    	return DebitorAddress.class;
    }
}
