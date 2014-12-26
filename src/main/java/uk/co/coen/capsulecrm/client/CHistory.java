package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import java.util.Iterator;
import java.util.List;

public class CHistory implements Iterable<CHistoryItem> {
    public int size;
    public List<CHistoryItem> historyItems;

    @Override
    public Iterator<CHistoryItem> iterator() {
        return historyItems != null ? historyItems.iterator() : ImmutableSet.<CHistoryItem>of().iterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .add("historyItems", historyItems)
                .toString();
    }
}
