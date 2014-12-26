package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;

public class CCustomField extends CIdentifiable {
    public String tag;
    public String label;
    public String text;
    public DateTime date;
    public Boolean bool;

    public CCustomField(String tag, String label, String text) {
        this.tag = tag;
        this.label = label;
        this.text = text;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tag", tag)
                .add("label", label)
                .add("text", text)
                .add("date", date)
                .add("bool", bool)
                .toString();
    }
}
