package com.zestia.capsule.restapi;

import com.google.common.base.Objects;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CTasks implements Iterable<CTask> {
    public int size;
    public List<CTask> tasks;

    @Override
    public Iterator<CTask> iterator() {
        return tasks.iterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("size", size)
                .add("tasks", tasks)
                .toString();
    }
}
