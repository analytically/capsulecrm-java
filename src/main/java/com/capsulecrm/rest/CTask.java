package com.capsulecrm.rest;

import com.ning.http.client.Realm;
import org.joda.time.DateTime;
import play.libs.F;
import play.libs.WS;

/**
 * @author Mathias Bogaert
 */
public class CTask extends CapsuleEntity {
    private String description;
    private String category;
    private DateTime dueDate;
    private DateTime dueDateTime;
    private String owner;
    private Integer partyId;
    private String partyName;
    private Integer opportunityId;
    private String opportunityName;
    private Integer caseId;
    private String caseName;

    public CTask() {
    }

    public CTask(String description, DateTime dueDate) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public DateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(DateTime dueDate) {
        this.dueDate = dueDate;
    }

    public DateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(DateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Integer getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Integer opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    @Override
    String readContextPath() {
        return "/task";
    }

    public F.Promise<WS.Response> complete() {
        return WS.url(capsuleUrl + "/api/task/" + getId() + "/complete")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                .post("");
    }

    public F.Promise<WS.Response> reopen() {
        return WS.url(capsuleUrl + "/api/task/" + getId() + "/reopen")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                .post("");
    }
}
