package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CUsers implements Iterable<CUser> {
    public List<CUser> users;

    @Override
    public Iterator<CUser> iterator() {
        return users != null ? users.iterator() : new ArrayList<CUser>().iterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("users", users)
                .toString();
    }
}
