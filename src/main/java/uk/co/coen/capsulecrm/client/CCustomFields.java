package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CCustomFields implements Iterable<CCustomField> {
    public int size;
    public List<CCustomField> customFields;

    public CCustomFields(CCustomField... customFields) {
        this.customFields = new ArrayList<>(Arrays.asList(customFields));
    }

    @Override
    public Iterator<CCustomField> iterator() {
        return customFields != null ? customFields.iterator() : Iterators.<CCustomField>emptyIterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .add("customFields", customFields)
                .toString();
    }
}
