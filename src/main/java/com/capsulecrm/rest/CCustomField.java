package com.capsulecrm.rest;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

/**
 * @author Mathias Bogaert
 */
public class CCustomField extends CIdentifiable {
    private String tag;
    private String label;
    private String text;
    private DateTime date;
    private String bool;

    public CCustomField(String tag, String label, String text) {
        this.tag = tag;
        this.label = label;
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getBool() {
        return bool;
    }

    public void setBool(String bool) {
        this.bool = bool;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("tag", tag)
                .add("label", label)
                .add("text", text)
                .add("date", date)
                .add("bool", bool)
                .toString();
    }
}
