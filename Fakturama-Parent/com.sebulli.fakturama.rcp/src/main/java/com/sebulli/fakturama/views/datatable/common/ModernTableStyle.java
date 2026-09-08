package com.sebulli.fakturama.views.datatable.common;

import java.util.function.IntFunction;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

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
     * Caps every row to a fixed height of {@code lineMultiple} lines of the table's own font,
     * regardless of how many embedded newlines a cell's text contains. Without this, a native SWT
     * {@link Table} auto-grows each row to fit the full height of its tallest cell's wrapped/
     * multi-line text - fine for a one-line description, but a six-line one turns the whole row
     * into a wall of text and pushes every other row out of view. Deliberately not sized to fit a
     * whole line plus a bit more - the point of {@code lineMultiple} being e.g. 1.7 rather than 1.0
     * or 2.0 is that a cell with more text than fits gets its second line visibly cut off partway,
     * signalling "there's more here" (open the row / widen the column) rather than silently hiding
     * it behind a clean one-line edge or wasting space reserving a full second line for every row.
     * <p>
     * Call once right after creating the {@link Table}. {@code Table#setItemHeight(int)} isn't
     * reliably honored on GTK, so this uses the standard SWT owner-draw trick instead - hooking
     * {@code SWT.MeasureItem} to override the row height, with {@code SWT.EraseItem}/{@code
     * SWT.PaintItem} left as no-ops (default background/text painting still happens) because some
     * platforms only apply a custom {@code MeasureItem} height when all three are hooked together.
     */
    public static void applyFixedRowHeight(final Table table, final float lineMultiple) {
        final GC gc = new GC(table);
        final int lineHeight = gc.getFontMetrics().getHeight();
        gc.dispose();
        final int rowHeight = Math.round(lineHeight * lineMultiple);
        table.addListener(SWT.MeasureItem, event -> event.height = rowHeight);
        table.addListener(SWT.EraseItem, event -> {
            // no-op: keep default background painting, only here so MeasureItem's height sticks
        });
        table.addListener(SWT.PaintItem, event -> {
            // no-op: keep default text painting, only here so MeasureItem's height sticks
        });
    }

    /**
     * NatTable equivalent of {@link #applyFixedRowHeight(Table, float)} - DocumentItemListTable
     * (the item grid inside Angebot/Auftrag/Rechnung/Lieferschein editors) is still NatTable-based,
     * not one of the native SWT.VIRTUAL {@link Table}s this class otherwise targets, so the
     * {@code SWT.MeasureItem} trick doesn't apply here; NatTable has its own, more direct API for
     * a uniform row height ({@link DataLayer#setDefaultRowHeight(int)}) that doesn't need it.
     * <p>
     * Same {@code lineMultiple} rationale as the SWT version: caps every row to a fixed multiple of
     * the table's own font-line height regardless of how many embedded newlines a cell's text
     * contains, so a multi-line hint/description visibly signals "there's more here" (partially cut
     * off) rather than either silently hiding it behind a single line or blowing the row out to fit
     * it in full and pushing every other row out of view.
     * <p>
     * Call once right after creating the {@link NatTable}, passing its body {@link DataLayer}
     * (e.g. {@code EntityGridListLayer#getBodyDataLayer()}).
     */
    public static void applyFixedRowHeight(final NatTable natTable, final DataLayer bodyDataLayer, final float lineMultiple) {
        final GC gc = new GC(natTable);
        final int lineHeight = gc.getFontMetrics().getHeight();
        gc.dispose();
        bodyDataLayer.setDefaultRowHeight(Math.round(lineHeight * lineMultiple));
    }

    /**
     * Collapses a possibly multi-line cell value (embedded {@code \n}/{@code \r\n}) down to its
     * first line, with a trailing ellipsis appended if anything followed. {@link #applyFixedRowHeight}
     * alone does NOT reliably cap a row's height when the cell text itself contains real newlines -
     * SWT's {@code MeasureItem} height is ignored on several platform/GTK combinations once the
     * default {@code PaintItem} text painter has multi-line content to lay out, so a six-line
     * description still blows the row out to six lines regardless of the requested height. Passing
     * the label provider's text through this method first is what actually keeps rows compact; use
     * {@link #addStickyCellPopover} on the same column so the truncated text is still readable in
     * full on click.
     */
    public static String singleLineSummary(final String text) {
        if (text == null) {
            return "";
        }
        final int newlineIndex = text.indexOf('\n');
        if (newlineIndex < 0) {
            return text;
        }
        final String firstLine = text.substring(0, newlineIndex).stripTrailing();
        return firstLine.isEmpty() ? "…" : firstLine + " …";
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

    /**
     * Lets a cell whose text is clipped by {@link #applyFixedRowHeight} be read in full: clicking
     * that column in a row opens a borderless popup showing {@code fullTextProvider}'s text for
     * that row, positioned right under the cell. The popup is "sticky" - it stays open (survives
     * mouse-away, since it's opened by click rather than hover) until the user clicks the same
     * cell again, clicks a different cell (which replaces it), or clicks anywhere outside it -
     * rather than a plain {@code setToolTipText}, which is hover-only and disappears the moment
     * the mouse leaves, too fleeting to actually read a multi-line description.
     *
     * @param table
     *            the table to watch clicks on
     * @param columnIndex
     *            which column triggers the popup
     * @param fullTextProvider
     *            given a row index, returns the full text to show, or {@code null}/blank for "no
     *            popup for this row" (e.g. an empty description)
     */
    public static void addStickyCellPopover(final Table table, final int columnIndex, final IntFunction<String> fullTextProvider) {
        final Shell[] openPopup = { null };
        final int[] openRow = { -1 };

        final Runnable closePopup = () -> {
            if (openPopup[0] != null && !openPopup[0].isDisposed()) {
                openPopup[0].dispose();
            }
            openPopup[0] = null;
            openRow[0] = -1;
        };

        table.addListener(SWT.MouseDown, (Event event) -> {
            final TableItem item = table.getItem(new Point(event.x, event.y));
            final boolean wasOpen = openPopup[0] != null;
            final int previousRow = openRow[0];
            closePopup.run();
            if (item == null) {
                return;
            }
            final Rectangle cellBounds = item.getBounds(columnIndex);
            if (!cellBounds.contains(event.x, event.y)) {
                return;
            }
            final int row = table.indexOf(item);
            if (wasOpen && row == previousRow) {
                // second click on the same cell: toggle it closed, already done above
                return;
            }
            final String text = fullTextProvider.apply(row);
            if (text == null || text.isBlank()) {
                return;
            }

            final Shell popup = new Shell(table.getShell(), SWT.ON_TOP | SWT.TOOL | SWT.RESIZE);
            popup.setLayout(new FillLayout());
            final Text textControl = new Text(popup, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY | SWT.V_SCROLL);
            textControl.setText(text);
            textControl.setBackground(table.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
            textControl.setForeground(table.getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND));

            final Point location = table.toDisplay(cellBounds.x, cellBounds.y + cellBounds.height);
            popup.setBounds(location.x, location.y, Math.max(cellBounds.width, 320), 220);
            popup.addListener(SWT.Deactivate, e -> closePopup.run());
            popup.open();

            openPopup[0] = popup;
            openRow[0] = row;
        });
        table.addListener(SWT.Dispose, event -> closePopup.run());
    }
}
