package com.capsulecrm.rest;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CHistoryItem extends CIdentifiable {
    private String type = "Note";
    private DateTime entryDate;
    private String creator;
    private String subject;
    private String note;

    private List<CAttachment> attachments;

    public CHistoryItem(String note) {
        this.note = note;
    }

    public CHistoryItem(String subject, String note) {
        this.subject = subject;
        this.note = note;
    }

    public CHistoryItem(String subject, String note, DateTime entryDate) {
        this.subject = subject;
        this.note = note;
        this.entryDate = entryDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<CAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CAttachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("entryDate", entryDate)
                .add("creator", creator)
                .add("subject", subject)
                .add("note", note)
                .add("attachments", attachments)
                .toString();
    }
}
