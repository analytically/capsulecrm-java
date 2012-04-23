package com.zestia.capsule.restapi;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public abstract class CIdentifiable {
    public Integer id;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}