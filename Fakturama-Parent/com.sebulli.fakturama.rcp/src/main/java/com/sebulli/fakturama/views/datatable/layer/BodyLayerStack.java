package com.sebulli.fakturama.views.datatable.layer;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataCommandHandler;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IRowIdAccessor;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.DetailGlazedListsEventLayer;
import org.eclipse.nebula.widgets.nattable.grid.cell.AlternatingRowConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.AbstractIndexLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.IUniqueIndexLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.reorder.RowReorderLayer;
import org.eclipse.nebula.widgets.nattable.selection.RowSelectionModel;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.config.RowOnlySelectionConfiguration;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;

import com.sebulli.fakturama.model.IEntity;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TransformedList;

/**
 * Always encapsulate the body layer stack in an AbstractLayerTransform to
 * ensure that the index transformations are performed in later commands.
 * 
 * @param <T>
 */
public class BodyLayerStack<T extends IEntity> extends AbstractIndexLayerTransform {

    private ListDataProvider<T> bodyDataProvider;
    private DataLayer bodyDataLayer;
    private final SelectionLayer selectionLayer;
    private SortedList<T> sortedList;
    private RowReorderLayer rowReorderLayer;
	private ViewportLayer viewportLayer;

    public BodyLayerStack(EventList<T> eventList, IColumnPropertyAccessor<T> columnPropertyAccessor) {
        this(eventList, columnPropertyAccessor, new IRowIdAccessor<T>() {
            @Override
            public Serializable getRowId(T rowObject) {
                // default implementation uses entity id as row id
                return rowObject.getId();
            }
        });
    }

    public BodyLayerStack(EventList<T> eventList, IColumnPropertyAccessor<T> columnPropertyAccessor, IRowIdAccessor<T> rowIdAccessor) {
        this(eventList, columnPropertyAccessor, rowIdAccessor, false);
    }

    /**
     * @param serverSorted when {@code true}, skips wrapping {@code eventList} in a {@link
     *            SortedList} - used for {@link PagedEntityEventList}-backed views, where the DB
     *            query's {@code ORDER BY} is the only correct ordering (a {@link SortedList} can
     *            only sort what it can see, which for a paginated list is never the whole table).
     *            {@link #getSortedList()} returns {@code null} in this mode; pair with a {@link
     *            ServerSortModel} instead of the default {@code GlazedListsSortModel} for column
     *            header sorting.
     *            <p>
     *            Also skips the GlazedLists event bridge entirely in this mode (see below) -
     *            callers are responsible for calling {@code NatTable#refresh()} themselves after
     *            reloading the backing list.
     */
    public BodyLayerStack(EventList<T> eventList, IColumnPropertyAccessor<T> columnPropertyAccessor, IRowIdAccessor<T> rowIdAccessor,
            boolean serverSorted) {

        final List<T> displayList;
        if (serverSorted) {
            this.sortedList = null;
            displayList = eventList;
        } else {
            //wrapping of the list to show into GlazedLists
            //see http://publicobject.com/glazedlists/ for further information
            TransformedList<T, T> rowObjectsGlazedList = GlazedLists.threadSafeList(eventList);
            //default sort: newest record (highest id) first, so freshly created entries are
            //immediately visible without having to click a column header. A user's own column
            //sort (via GlazedListsSortModel/SortHeaderLayer) still overrides this once they click one.
            this.sortedList = new SortedList<T>(rowObjectsGlazedList, Comparator.comparingLong(IEntity::getId).reversed());
            displayList = sortedList;
        }

        this.bodyDataProvider = new ListDataProvider<T>(displayList, columnPropertyAccessor);
        this.bodyDataLayer = new DataLayer(getBodyDataProvider());

        // add a label accumulator to be able to register converter
        // this is crucial for using custom values display
        this.bodyDataLayer.setConfigLabelAccumulator(new ColumnLabelAccumulator());

//        HoverLayer hoverLayer = new HoverLayer(bodyDataLayer);

        // For a server-paginated (serverSorted) list, skip the GlazedLists event bridge (both
        // Detail- and plain GlazedListsEventLayer) entirely rather than just picking the coarser
        // of the two - neither is needed, and both cost something for nothing: PagedEntityEventList
        // .reload() replaces the whole list in one shot, e.g. a category switch jumping from 30 to
        // 1500 rows. Confirmed via SQL log + thread dump: any event bridge here reacts to that by
        // touching rows well beyond the viewport, which for a lazy PagedEntityEventList means
        // loading every page immediately - defeating pagination entirely (the exact "always blocks
        // on category switch" behaviour reported). ListDataProvider only ever calls get(rowIndex)
        // for cells NatTable is actually about to paint, so wiring bodyDataLayer straight into
        // RowReorderLayer keeps that laziness intact; the view calls NatTable#refresh() itself
        // after reload() to pick up the new row count/content. The small in-memory
        // (non-serverSorted) lists keep DetailGlazedListsEventLayer - they're not lazy, so
        // touching every row costs nothing, and they rely on its fine-grained events.
        final IUniqueIndexLayer rowReorderInput = serverSorted ? this.bodyDataLayer
                : new DetailGlazedListsEventLayer<>(this.bodyDataLayer, (EventList<T>) displayList);
        rowReorderLayer = new RowReorderLayer(rowReorderInput);
        // this is for the correct coloring of alternating rows
        rowReorderLayer.setConfigLabelAccumulator(new AlternatingRowConfigLabelAccumulator());
        
        this.selectionLayer = new SelectionLayer(rowReorderLayer);

        //use a RowSelectionModel that will perform row selections and is able to identify a row via unique ID
        RowSelectionModel<T> selectionModel = new RowSelectionModel<>(selectionLayer, bodyDataProvider, rowIdAccessor, false);
        selectionLayer.setSelectionModel(selectionModel);
        // Select complete rows
        selectionLayer.addConfiguration(new RowOnlySelectionConfiguration());
        
        viewportLayer = new ViewportLayer(this.selectionLayer);
        setUnderlyingLayer(viewportLayer);

        registerCommandHandler(new CopyDataCommandHandler(this.selectionLayer));
    }

    /**
     * @return the sortedList
     */
    public SortedList<T> getSortedList() {
        return sortedList;
    }

    protected SelectionLayer getSelectionLayer() {
        return selectionLayer;
    }

    protected ListDataProvider<T> getBodyDataProvider() {
        return bodyDataProvider;
    }

    /**
     * @return the bodyDataLayer
     */
    protected DataLayer getBodyDataLayer() {
        return bodyDataLayer;
    }

    /**
     * @return the rowReorderLayer
     */
    public RowReorderLayer getRowReorderLayer() {
        return rowReorderLayer;
    }

	/**
	 * @return the viewportLayer
	 */
	public final ViewportLayer getViewportLayer() {
		return viewportLayer;
	}
}