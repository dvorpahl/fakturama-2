package com.sebulli.fakturama.handlers;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import com.sebulli.fakturama.dao.ProductWebshopDAO;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.parts.Editor;
import com.sebulli.fakturama.parts.ProductEditor;
import com.sebulli.fakturama.views.datatable.products.ProductListTable;

/** Handles bulk enabling/disabling of products for the webshop. */
public class SetProductWebshopHandler {
    public static final String COMMAND_ID = "com.sebulli.fakturama.command.productWebshop";
    public static final String HANDLER_ID = "com.sebulli.fakturama.product.webshop.handler";
    public static final String POPUP_ID = "com.sebulli.fakturama.productlist.popup";
    public static final String ENABLE_ITEM_ID = "com.sebulli.fakturama.productlist.popup.webshop.enable";
    public static final String DISABLE_ITEM_ID = "com.sebulli.fakturama.productlist.popup.webshop.disable";
    public static final String PARAM_ENABLED = "com.sebulli.fakturama.product.webshop.enabled";

    @Inject
    private ProductWebshopDAO productWebshopDAO;

    @Inject
    private IEventBroker eventBroker;

    @Inject
    private EPartService partService;

    private ProductListTable productTable(final MPart activePart) {
        if (activePart != null && ProductListTable.ID.equals(activePart.getElementId())
                && activePart.getObject() instanceof ProductListTable table) {
            return table;
        }
        return partService.getParts().stream()
                .filter(part -> ProductListTable.ID.equals(part.getElementId()))
                .map(MPart::getObject)
                .filter(ProductListTable.class::isInstance)
                .map(ProductListTable.class::cast)
                .findFirst().orElse(null);
    }

    @CanExecute
    public boolean canExecute(@Optional @Active final MPart activePart) {
        final ProductListTable table = productTable(activePart);
        return table != null && table.hasSelection();
    }

    @Execute
    public void execute(@Optional @Active final MPart activePart, @Named(PARAM_ENABLED) final Boolean enabled)
            throws FakturamaStoringException {
        final ProductListTable table = productTable(activePart);
        if (table == null || !table.hasSelection()) {
            return;
        }
        productWebshopDAO.setEnabled(Arrays.asList(table.getSelectedObjects()), Boolean.TRUE.equals(enabled));
        eventBroker.post(ProductEditor.EDITOR_ID, Editor.UPDATE_EVENT);
    }
}
