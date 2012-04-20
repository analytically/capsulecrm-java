package com.capsulecrm.rest;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CHistory implements Iterable<CHistoryItem> {
    public int size;
    public List<CHistoryItem> historyItems;

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<CHistoryItem> iterator() {
        return historyItems != null ? historyItems.iterator() : null;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("size", size)
                .add("historyItems", historyItems)
                .toString();
    }
}
