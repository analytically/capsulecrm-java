package com.capsulecrm.rest;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CAttachment extends CIdentifiable {
    public String filename;

    public CAttachment() {
    }

    public CAttachment(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("filename", filename)
                .toString();
    }
}
