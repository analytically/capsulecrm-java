package com.capsulecrm.rest;

import com.ning.http.client.Realm;
import com.thoughtworks.xstream.io.xml.DomReader;
import org.joda.time.DateTime;
import play.libs.F;
import play.libs.WS;

/**
 * @author Mathias Bogaert
 */
public class CTask extends SimpleCapsuleEntity {
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

    public static F.Promise<CTasks> list(String category, String user) {
        WS.WSRequestHolder holder = WS.url(capsuleUrl + "/api/tasks");

        if (category != null) {
            holder.setQueryParameter("category", category);
        }
        if (user != null) {
            holder.setQueryParameter("user", user);
        }

        return holder.setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.NONE)
                .get().map(new F.Function<WS.Response, CTasks>() {
                    @Override
                    public CTasks apply(WS.Response response) throws Throwable {
                        return (CTasks) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public F.Promise<WS.Response> complete() {
        return WS.url(capsuleUrl + "/api/task/" + id + "/complete")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                .post("");
    }

    public F.Promise<WS.Response> reopen() {
        return WS.url(capsuleUrl + "/api/task/" + id + "/reopen")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                .post("");
    }
}
