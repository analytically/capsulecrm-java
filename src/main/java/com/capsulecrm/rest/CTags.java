package com.capsulecrm.rest;

import com.google.common.base.Objects;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CTags implements Iterable<CTag> {
    public int size;
    public List<CTag> tags;

    @Override
    public Iterator<CTag> iterator() {
        return tags.iterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("size", size)
                .add("tags", tags)
                .toString();
    }
}
