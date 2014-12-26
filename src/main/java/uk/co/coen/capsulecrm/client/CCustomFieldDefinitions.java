package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;

public class CCustomFieldDefinitions implements Iterable<CCustomFieldDefinition> {
    public List<CCustomFieldDefinition> customFieldDefinitions;

    @Override
    public Iterator<CCustomFieldDefinition> iterator() {
        return customFieldDefinitions != null ? customFieldDefinitions.iterator() : Iterators.<CCustomFieldDefinition>emptyIterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("customFieldDefinitions", customFieldDefinitions)
                .toString();
    }
}
