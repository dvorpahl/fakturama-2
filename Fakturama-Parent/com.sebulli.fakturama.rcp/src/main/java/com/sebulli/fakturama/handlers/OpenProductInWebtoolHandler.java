package com.sebulli.fakturama.handlers;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;

import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.model.Product;
import com.sebulli.fakturama.views.datatable.products.ProductListTable;

/** Opens the selected product in the corresponding external Webtool editor. */
public class OpenProductInWebtoolHandler {
    public static final String COMMAND_ID = "com.sebulli.fakturama.command.productWebtoolEditor";
    public static final String HANDLER_ID = "com.sebulli.fakturama.product.webtool.editor.handler";
    public static final String ITEM_ID = "com.sebulli.fakturama.productlist.popup.webtool.editor";

    @Inject private EPartService partService;
    @Inject private IPreferenceStore preferences;

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
        return table != null && table.getSingleSelectedProductForWebtool() != null;
    }

    @Execute
    public void execute(@Optional @Active final MPart activePart) {
        final ProductListTable table = productTable(activePart);
        final Product product = table == null ? null : table.getSingleSelectedProductForWebtool();
        if (product == null) {
            return;
        }
        final String title = "Im Webtool bearbeiten";
        final String configuredUrl = preferences.getString(Constants.PREFERENCES_GENERAL_WEBBROWSER_URL).trim();
        if (configuredUrl.isEmpty()) {
            MessageDialog.openInformation(Display.getDefault().getActiveShell(), title,
                    "Bitte zuerst die Webtool-Adresse in den Browser-Einstellungen eintragen.");
            return;
        }
        try {
            final URI base = URI.create(configuredUrl);
            if (!("http".equalsIgnoreCase(base.getScheme()) || "https".equalsIgnoreCase(base.getScheme()))
                    || base.getHost() == null) {
                throw new IllegalArgumentException("Invalid Webtool URL");
            }
            final String origin = base.getScheme() + "://" + base.getRawAuthority();
            final String url;
            if (table.isAssembly(product)) {
                url = origin + "/bauteil-tabelle/verwalten?item_number="
                        + URLEncoder.encode(product.getItemNumber(), StandardCharsets.UTF_8)
                        + "&id=" + product.getId();
            } else {
                url = origin + "/artikel-editor/" + product.getId();
            }
            if (!Program.launch(url)) {
                throw new IllegalStateException("Could not open browser");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            MessageDialog.openError(Display.getDefault().getActiveShell(), title,
                    "Der Webtool-Editor konnte nicht geöffnet werden. Bitte die Webtool-Adresse prüfen.");
        }
    }
}
