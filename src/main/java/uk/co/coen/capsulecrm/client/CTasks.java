package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CTasks implements Iterable<CTask> {
    public int size;
    public List<CTask> tasks;

    @Override
    public Iterator<CTask> iterator() {
        return tasks != null ? tasks.iterator() : new ArrayList<CTask>().iterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("size", size)
                .add("tasks", tasks)
                .toString();
    }
}
