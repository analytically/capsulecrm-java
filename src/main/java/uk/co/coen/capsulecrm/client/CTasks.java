package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import java.util.Iterator;
import java.util.List;

public class CTasks implements Iterable<CTask> {
    public int size;
    public List<CTask> tasks;

    @Override
    public Iterator<CTask> iterator() {
        return tasks != null ? tasks.iterator() : ImmutableSet.<CTask>of().iterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .add("tasks", tasks)
                .toString();
    }
}
