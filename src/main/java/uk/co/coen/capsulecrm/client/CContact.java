package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;

public abstract class CContact extends CIdentifiable {
    public String type;

    protected CContact() {
    }

    protected CContact(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .toString();
    }
}
