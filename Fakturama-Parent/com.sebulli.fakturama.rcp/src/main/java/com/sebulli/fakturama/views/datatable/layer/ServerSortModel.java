package com.sebulli.fakturama.views.datatable.layer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntFunction;

import org.eclipse.nebula.widgets.nattable.sort.ISortModel;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;

/**
 * An {@link ISortModel} for {@link PagedEntityEventList}-backed views: a column-header click does
 * not resort what happens to be loaded in memory (there's a {@link ca.odell.glazedlists.SortedList}
 * for that, see {@link BodyLayerStack}, but it needs the *whole* list to sort correctly - exactly
 * what a paginated view doesn't have). Instead it asks {@link SortRequestListener} to reload the
 * list from the DB with a matching {@code ORDER BY}.
 * <p>
 * Wired in by {@link GlazedListsColumnHeaderLayerStack} in place of the default {@code
 * GlazedListsSortModel} whenever a view is built with a server-sorted {@link BodyLayerStack}.
 *
 * @param <T> the row type (unused directly, kept for symmetry with the sort-model type the
 *            replaced {@code GlazedListsSortModel<T>} carried)
 */
public class ServerSortModel<T> implements ISortModel {

    /** Notified when a column header click should translate into a new DB query. */
    @FunctionalInterface
    public interface SortRequestListener {
        /**
         * @param propertyName the entity attribute to order by, or {@code null} to fall back to
         *            the view's default order (a repeated click past DESC clears the sort)
         * @param descending sort direction, ignored when {@code propertyName} is {@code null}
         */
        void onSortRequested(String propertyName, boolean descending);
    }

    /** Column index -> sortable entity attribute name, or {@code null} if that column can't be sorted server-side. */
    private final IntFunction<String> columnToProperty;
    private final SortRequestListener listener;

    private int sortedColumnIndex = -1;
    private SortDirectionEnum sortDirection = SortDirectionEnum.NONE;

    public ServerSortModel(final IntFunction<String> columnToProperty, final SortRequestListener listener) {
        this.columnToProperty = columnToProperty;
        this.listener = listener;
    }

    @Override
    public List<Integer> getSortedColumnIndexes() {
        return sortedColumnIndex < 0 ? Collections.emptyList() : Collections.singletonList(sortedColumnIndex);
    }

    @Override
    public boolean isColumnIndexSorted(final int columnIndex) {
        return columnIndex == sortedColumnIndex;
    }

    @Override
    public SortDirectionEnum getSortDirection(final int columnIndex) {
        return columnIndex == sortedColumnIndex ? sortDirection : SortDirectionEnum.NONE;
    }

    @Override
    public int getSortOrder(final int columnIndex) {
        return columnIndex == sortedColumnIndex ? 0 : -1;
    }

    @Override
    public List<Comparator> getComparatorsForColumnIndex(final int columnIndex) {
        // Ordering happens in the DB query, not via an in-memory Comparator.
        return Collections.emptyList();
    }

    @Override
    public Comparator<?> getColumnComparator(final int columnIndex) {
        return null;
    }

    @Override
    public void sort(final int columnIndex, final SortDirectionEnum direction, final boolean accumulate) {
        final String propertyName = columnToProperty.apply(columnIndex);
        if (propertyName == null || direction == null || direction == SortDirectionEnum.NONE) {
            clear();
            listener.onSortRequested(null, false);
            return;
        }
        sortedColumnIndex = columnIndex;
        sortDirection = direction;
        listener.onSortRequested(propertyName, direction == SortDirectionEnum.DESC);
    }

    @Override
    public void clear() {
        sortedColumnIndex = -1;
        sortDirection = SortDirectionEnum.NONE;
    }
}
