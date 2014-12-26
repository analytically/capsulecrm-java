package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;

public class CUsers implements Iterable<CUser> {
    public List<CUser> users;

    @Override
    public Iterator<CUser> iterator() {
        return users != null ? users.iterator() : Iterators.<CUser>emptyIterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("users", users)
                .toString();
    }
}
