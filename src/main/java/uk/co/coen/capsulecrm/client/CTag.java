package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

public class CTag {
    public String name;

    public CTag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .toString();
    }
}
