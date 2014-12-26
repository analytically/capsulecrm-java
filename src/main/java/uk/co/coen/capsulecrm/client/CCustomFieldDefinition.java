package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;

public class CCustomFieldDefinition extends CIdentifiable {
    public String tag;
    public String label;
    public String type;
    public String options;
    public String forPartyClass;
    public String displayOrder;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tag", tag)
                .add("label", label)
                .add("type", type)
                .add("options", options)
                .add("forPartyClass", forPartyClass)
                .add("displayOrder", displayOrder)
                .toString();
    }
}
