package org.fakturama.addon.purchaseorders.example.ui;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.fakturama.addon.purchaseorders.example.model.PurchaseOrder;
import org.fakturama.addon.purchaseorders.example.repository.PurchaseOrderRepository;

final class PurchaseOrdersPage extends WizardPage {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String[] COLUMN_NAMES = {
            "ID", "Lieferant", "Status", "Erstellt", "Bestellt", "WE geplant", "Positionen", "Menge", "Eingang"
    };
    private static final int[] COLUMN_WIDTHS = { 65, 170, 110, 90, 90, 90, 80, 85, 85 };

    private final PurchaseOrderRepository repository;
    private Table table;
    private Label summary;

    PurchaseOrdersPage(PurchaseOrderRepository repository) {
        super("purchaseOrders");
        this.repository = repository;
        setTitle("Warenbestellungen aus fakturama-tool");
        setDescription("Read-only-Vorschau der Tabellen PART_BESTELLUNG und PART_BESTELLPOSITION.");
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(2, false));

        summary = new Label(container, SWT.NONE);
        summary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Button reload = new Button(container, SWT.PUSH);
        reload.setText("Neu laden");
        reload.addListener(SWT.Selection, event -> reload());

        table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        for (int i = 0; i < COLUMN_NAMES.length; i++) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(COLUMN_NAMES[i]);
            column.setWidth(COLUMN_WIDTHS[i]);
        }

        setControl(container);
        reload();
    }

    private void reload() {
        try {
            List<PurchaseOrder> orders = repository.findAll();
            table.removeAll();
            for (PurchaseOrder order : orders) {
                TableItem item = new TableItem(table, SWT.NONE);
                item.setText(new String[] {
                        Long.toString(order.id()),
                        text(order.supplier()),
                        order.status().databaseValue(),
                        date(order.createdOn()),
                        date(order.orderedOn()),
                        date(order.plannedReceiptOn()),
                        Integer.toString(order.items().size()),
                        number(order.orderedQuantity()),
                        number(order.receivedQuantity())
                });
            }
            int itemCount = orders.stream().mapToInt(order -> order.items().size()).sum();
            summary.setText(orders.size() + " Bestellungen, " + itemCount + " Positionen");
            setErrorMessage(null);
            setPageComplete(true);
        } catch (RuntimeException exception) {
            summary.setText("Keine Daten geladen");
            setErrorMessage("Warenbestellungen konnten nicht geladen werden.");
            setPageComplete(false);
            MessageDialog.openError(getShell(), "Warenbestellungen", errorMessage(exception));
        }
        summary.getParent().layout(true, true);
    }

    private static String text(String value) {
        return value == null ? "" : value;
    }

    private static String date(java.time.LocalDate value) {
        return value == null ? "" : DATE_FORMAT.format(value);
    }

    private static String number(double value) {
        return String.format("%.2f", value);
    }

    private static String errorMessage(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage() == null ? cause.getClass().getSimpleName() : cause.getMessage();
    }
}
