package com.capsulecrm.rest;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public abstract class CContact extends CIdentifiable {
    public String type;

    protected CContact(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .toString();
    }
}
