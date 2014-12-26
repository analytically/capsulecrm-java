package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;

public abstract class CIdentifiable {
    public Long id;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}