package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import java.util.Iterator;
import java.util.List;

public class CTags implements Iterable<CTag> {
    public int size;
    public List<CTag> tags;

    @Override
    public Iterator<CTag> iterator() {
        return tags != null ? tags.iterator() : ImmutableSet.<CTag>of().iterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .add("tags", tags)
                .toString();
    }
}
