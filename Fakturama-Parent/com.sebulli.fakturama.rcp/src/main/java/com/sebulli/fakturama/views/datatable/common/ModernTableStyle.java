package com.sebulli.fakturama.views.datatable.common;

import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * Shared look for the native SWT.VIRTUAL {@link Table}s that replaced NatTable in
 * DocumentsListTable/ProductListTable/ContactListTable/ContactTreeListTable - a subtle grey
 * header plus alternating ("zebra") row backgrounds, applied uniformly across all four so the
 * unification the user asked for extends to appearance too, not just the loading architecture.
 * <p>
 * Uses {@link GUIHelper#getColor(int, int, int)} rather than {@code new Color(...)} - it caches
 * and disposes colors keyed by RGB, so nothing here needs manual disposal management even though
 * this class has no lifecycle of its own.
 */
public final class ModernTableStyle {

    private static final Color HEADER_BACKGROUND = GUIHelper.getColor(238, 238, 238);
    private static final Color HEADER_FOREGROUND = GUIHelper.getColor(60, 60, 60);
    private static final Color ROW_BACKGROUND = GUIHelper.COLOR_WHITE;
    private static final Color ROW_BACKGROUND_ALT = GUIHelper.getColor(246, 246, 246);

    private ModernTableStyle() {
    }

    /** Call once right after creating the {@link Table}. */
    public static void applyHeaderStyle(final Table table) {
        table.setHeaderBackground(HEADER_BACKGROUND);
        table.setHeaderForeground(HEADER_FOREGROUND);
    }

    /**
     * Call once per row after its data is populated (i.e. at the end of an {@code
     * ILazyContentProvider#updateElement(int)}, or right after {@code TableViewer#setInput(...)}
     * for a plain, non-virtual table) - zebra striping needs each row's absolute index, which
     * isn't available from a {@code ColumnLabelProvider} alone.
     */
    public static void applyZebraStripe(final Table table, final int index) {
        if (index >= 0 && index < table.getItemCount()) {
            final TableItem item = table.getItem(index);
            item.setBackground(index % 2 == 0 ? ROW_BACKGROUND : ROW_BACKGROUND_ALT);
        }
    }
}
