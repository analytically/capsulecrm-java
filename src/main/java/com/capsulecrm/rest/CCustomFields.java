package com.capsulecrm.rest;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CCustomFields implements Iterable<CCustomField> {
    private int size;
    private List<CCustomField> customFields;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<CCustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CCustomField> customFields) {
        this.customFields = customFields;
    }

    @Override
    public Iterator<CCustomField> iterator() {
        return customFields.iterator();
    }
}
