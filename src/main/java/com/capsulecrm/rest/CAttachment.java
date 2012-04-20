package com.capsulecrm.rest;

/**
 * @author Mathias Bogaert
 */
public class CAttachment extends CIdentifiable {
    private String filename;

    public CAttachment() {
    }

    public CAttachment(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
