package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;

public class CCustomField extends CIdentifiable {
    public String tag;
    public String label;
    public String text;
    public DateTime date;
    public Boolean bool;
    public Integer number;

    public CCustomField(String tag, String label, String text) {
        this.tag = tag;
        this.label = label;
        this.text = text;
    }

    public CCustomField(String tag, String label, boolean bool) {
        this.tag = tag;
        this.label = label;
        this.bool = bool;
    }

    public CCustomField(String tag, String label, Integer number) {
        this.tag = tag;
        this.label = label;
        this.number = number;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tag", tag)
                .add("label", label)
                .add("text", text)
                .add("date", date)
                .add("boolean", bool)
                .add("number", number)
                .toString();
    }
}
