package uk.co.coen.capsulecrm.client;

import com.thoughtworks.xstream.io.xml.DomReader;
import org.joda.time.DateTime;
import play.libs.F;
import play.libs.WS;

/**
 * @author Mathias Bogaert
 */
public class CTask extends SimpleCapsuleEntity {
    public String description;
    public String detail;
    public String category;
    public String status;
    public DateTime dueDate;
    public DateTime dueDateTime;
    public String owner;

    public Integer partyId;
    public String partyName;

    public Integer caseId;
    public String caseName;

    public Integer opportunityId;
    public String opportunityName;

    public CTask() {
    }

    public CTask(String description, DateTime dueDateTime, boolean saveTime) {
        this(description, null, dueDateTime, saveTime);
    }

    public CTask(String description, String detail, DateTime dueDateTime, boolean saveTime) {
        this.description = description;
        this.detail = detail;
        if (saveTime) {
            this.dueDateTime = dueDateTime;
        } else {
            this.dueDate = dueDateTime;
        }
    }

    @Override
    protected String readContextPath() {
        return "/task";
    }

    public static F.Promise<CTasks> list() {
        return list(null, null);
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
                .setAuth(capsuleToken, "")
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
                .setAuth(capsuleToken, "")
                .post("");
    }

    public F.Promise<WS.Response> reopen() {
        return WS.url(capsuleUrl + "/api/task/" + id + "/reopen")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .post("");
    }
}
