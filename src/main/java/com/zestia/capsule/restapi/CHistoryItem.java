package com.zestia.capsule.restapi;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CHistoryItem extends CIdentifiable {
    public String type = "Note";
    public DateTime entryDate;
    public String creator;
    public String creatorName;
    public String subject;
    public String note;

    public List<CAttachment> attachments;

    public Integer partyId;
    public String partyName;
    public String partyPictureURL;

    public Integer caseId;
    public String caseName;

    public Integer opportunityId;
    public String opportunityName;

    public CHistoryItem(String note) {
        this.note = note;
    }

    public CHistoryItem(String note, DateTime entryDate) {
        this.note = note;
        this.entryDate = entryDate;
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("entryDate", entryDate)
                .add("creator", creator)
                .add("creatorName", creatorName)
                .add("subject", subject)
                .add("note", note)
                .add("attachments", attachments)
                .add("partyId", partyId)
                .add("partyName", partyName)
                .add("partyPictureURL", partyPictureURL)
                .add("caseId", caseId)
                .add("caseName", caseName)
                .add("opportunityId", opportunityId)
                .add("opportunityName", opportunityName)
                .toString();
    }
}
