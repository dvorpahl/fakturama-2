package com.sebulli.fakturama.views.datatable.layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.LongSupplier;

import ca.odell.glazedlists.AbstractEventList;
import ca.odell.glazedlists.util.concurrent.LockFactory;

/**
 * A GlazedLists {@link ca.odell.glazedlists.EventList} that never materialises a whole table in
 * memory: {@link #size()} reports the true row count up front (from a cheap {@code COUNT} query)
 * so NatTable's scrollbar looks "full" immediately, but the actual rows are fetched from the DB
 * lazily, one batch at a time, the first time a row in that batch is asked for via {@link
 * #get(int)}. NatTable only ever asks for rows currently in its viewport, so in practice this
 * means one small query when the view opens and one more each time the user scrolls into a batch
 * that hasn't been loaded yet - never the whole table.
 * <p>
 * Replacing the search term, category filter or sort order is not a client-side re-filter/re-sort
 * of what's already loaded (that's exactly the "just buffers everything" behaviour this class
 * exists to get rid of) - it's a brand new query via {@link #reload(PageLoader, LongSupplier)},
 * which throws away the cache and starts loading again from a fresh {@code WHERE}/{@code ORDER
 * BY}.
 *
 * @param <T> the entity type
 */
public class PagedEntityEventList<T> extends AbstractEventList<T> {

    /** Loads one page of rows for the current search/category/sort criteria. */
    @FunctionalInterface
    public interface PageLoader<T> {
        List<T> load(int firstResult, int maxResults);
    }

    private final int batchSize;
    private PageLoader<T> loader;
    private List<T> data;
    private boolean[] pageLoaded;

    /**
     * @param batchSize how many rows to fetch per DB round trip - large enough that a freshly
     *            opened list looks full without scrolling, small enough to stay fast
     * @param loader fetches one page for the current criteria
     * @param countSupplier the total row count for the current criteria
     */
    public PagedEntityEventList(final int batchSize, final PageLoader<T> loader, final LongSupplier countSupplier) {
        super(null);
        // AbstractEventList's constructors never set this - GlazedLists.threadSafeList(...),
        // which every BodyLayerStack wraps its EventList in, needs a real lock here.
        this.readWriteLock = LockFactory.DEFAULT.createReadWriteLock();
        this.batchSize = batchSize;
        resetTo(loader, countSupplier);
    }

    /**
     * Replaces the current search/category/sort criteria and reloads from the start, firing a
     * single "list changed" event so listeners (NatTable's GlazedLists event layer) repaint.
     */
    public void reload(final PageLoader<T> newLoader, final LongSupplier newCountSupplier) {
        final int oldSize = size();
        updates.beginEvent();
        if (oldSize > 0) {
            updates.addDelete(0, oldSize - 1);
        }
        resetTo(newLoader, newCountSupplier);
        if (!data.isEmpty()) {
            updates.addInsert(0, data.size() - 1);
        }
        updates.commitEvent();
    }

    private void resetTo(final PageLoader<T> newLoader, final LongSupplier newCountSupplier) {
        this.loader = newLoader;
        final long count = newCountSupplier.getAsLong();
        final int size = (int) Math.max(0, Math.min(count, Integer.MAX_VALUE - 8));
        this.data = new ArrayList<>(Collections.nCopies(size, null));
        this.pageLoaded = new boolean[size == 0 ? 0 : (size - 1) / batchSize + 1];
        if (size > 0) {
            loadPage(0);
        }
    }

    private void loadPage(final int pageIndex) {
        if (pageIndex < 0 || pageIndex >= pageLoaded.length || pageLoaded[pageIndex]) {
            return;
        }
        final int first = pageIndex * batchSize;
        final int count = Math.min(batchSize, data.size() - first);
        final List<T> page = loader.load(first, count);
        for (int i = 0; i < page.size() && first + i < data.size(); i++) {
            data.set(first + i, page.get(i));
        }
        pageLoaded[pageIndex] = true;
    }

    @Override
    public int size() {
        return data == null ? 0 : data.size();
    }

    @Override
    public T get(final int index) {
        // SWT.VIRTUAL Table#setItemCount(smallerCount) doesn't cancel SetData requests already
        // queued for indices beyond the new count - a search/filter that shrinks the result set
        // can still have a stale updateElement(oldHigherIndex) in flight when this runs. Returning
        // null for it (SWT ignores a null cell value) is correct; throwing crashed the whole
        // dialog for something that isn't actually a bug in the data itself, just async widget
        // catch-up.
        if (data == null || index < 0 || index >= data.size()) {
            return null;
        }
        loadPage(index / batchSize);
        return data.get(index);
    }

    @Override
    public void dispose() {
        // nothing external to release - data/pageLoaded are plain in-memory caches
    }
}
