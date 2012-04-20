package com.capsulecrm.rest;

import com.google.common.base.Objects;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CHistory implements Iterable<CHistoryItem> {
    private int size;
    private List<CHistoryItem> historyItems;

    public CHistory(int size, List<CHistoryItem> historyItems) {
        this.size = size;
        this.historyItems = historyItems;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<CHistoryItem> getHistoryItems() {
        return historyItems;
    }

    public void setHistoryItems(List<CHistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    @Override
    public Iterator<CHistoryItem> iterator() {
        if (size != historyItems.size()) {
            throw new IllegalStateException("size = " + size + " but historyItems = " + historyItems.size());
        }
        return historyItems.iterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("size", size)
                .add("historyItems", historyItems)
                .toString();
    }
}
