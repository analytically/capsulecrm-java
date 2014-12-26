package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import java.util.Iterator;
import java.util.List;

public class CCases implements Iterable<CCase> {
    public int size;
    public List<CCase> cases;

    @Override
    public Iterator<CCase> iterator() {
        return cases != null ? cases.iterator() : ImmutableSet.<CCase>of().iterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .add("cases", cases)
                .toString();
    }
}
