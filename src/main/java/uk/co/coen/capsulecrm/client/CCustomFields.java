package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CCustomFields implements Iterable<CCustomField> {
    public int size;
    public List<CCustomField> customFields;

    @Override
    public Iterator<CCustomField> iterator() {
        return customFields.iterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("size", size)
                .add("customFields", customFields)
                .toString();
    }
}
