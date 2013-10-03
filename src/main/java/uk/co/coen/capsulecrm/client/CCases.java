package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

import java.util.Iterator;
import java.util.List;

public class CCases implements Iterable<CCase> {
    public int size;
    public List<CCase> cases;

    @Override
    public Iterator<CCase> iterator() {
        return cases.iterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("size", size)
                .add("cases", cases)
                .toString();
    }
}
