package com.capsulecrm.rest;

import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CTags {
    private int size;

    private List<CTag> tags;

    public CTags(int size, List<CTag> tags) {
        this.size = size;
        this.tags = tags;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<CTag> getTags() {
        return tags;
    }

    public void setTags(List<CTag> tags) {
        this.tags = tags;
    }
}
