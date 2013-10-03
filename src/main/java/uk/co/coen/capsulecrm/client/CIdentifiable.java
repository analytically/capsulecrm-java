package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

public abstract class CIdentifiable {
    public Integer id;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}