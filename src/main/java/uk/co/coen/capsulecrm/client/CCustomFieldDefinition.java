package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

public class CCustomFieldDefinition extends CIdentifiable {
    public String tag;
    public String label;
    public String type;
    public String options;
    public String forPartyClass;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("tag", tag)
                .add("label", label)
                .add("type", type)
                .add("options", options)
                .add("forPartyClass", forPartyClass)
                .toString();
    }
}
