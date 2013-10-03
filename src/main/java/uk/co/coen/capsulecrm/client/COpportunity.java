package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import com.thoughtworks.xstream.io.xml.DomReader;
import org.joda.time.DateTime;
import play.libs.F;
import play.libs.WS;

import java.util.concurrent.TimeUnit;

public class COpportunity extends CapsuleEntity {
    public String name;
    public String description;
    public Integer partyId;
    public String currency;
    public String value;
    public String durationBasis;
    public Integer duration;
    public DateTime expectedCloseDate;
    public Integer milestoneId;
    public String milestone;
    public String probability;
    public String owner;
    public Integer trackId;

    @Override
    protected String readContextPath() {
        return "/opportunity";
    }

    @Override
    protected String writeContextPath() {
        return "/party/" + partyId + "/opportunity";
    }

    public static F.Promise<CMilestones> listMilestones() {
        return WS.url(capsuleUrl + "/api/opportunity/milestones")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, CMilestones>() {
                    @Override
                    public CMilestones apply(WS.Response response) throws Throwable {
                        return (CMilestones) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public static F.Promise<COpportunities> listAll() {
        return listAll(12, TimeUnit.SECONDS);
    }

    public static F.Promise<COpportunities> listAll(long time, TimeUnit unit) {
        return WS.url(capsuleUrl + "/api/opportunity")
                .setTimeout((int) unit.toMillis(time))
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, COpportunities>() {
                    @Override
                    public COpportunities apply(WS.Response response) throws Throwable {
                        if (response.getStatus() == 401)
                            throw new RuntimeException("Not Authorized, check your Play configuration.");
                        return (COpportunities) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public static F.Promise<COpportunities> listByTag(String tag) {
        return listByTag(tag, 12, TimeUnit.SECONDS);
    }

    public static F.Promise<COpportunities> listByTag(String tag, long time, TimeUnit unit) {
        return WS.url(capsuleUrl + "/api/opportunity")
                .setTimeout((int) unit.toMillis(time))
                .setQueryParameter("tag", tag)
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, COpportunities>() {
                    @Override
                    public COpportunities apply(WS.Response response) throws Throwable {
                        return (COpportunities) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public static F.Promise<COpportunities> listModifiedSince(DateTime modifiedSince) {
        return listModifiedSince(modifiedSince, 12, TimeUnit.SECONDS);
    }

    public static F.Promise<COpportunities> listModifiedSince(DateTime modifiedSince, long time, TimeUnit unit) {
        return WS.url(capsuleUrl + "/api/opportunity")
                .setTimeout((int) unit.toMillis(time))
                .setQueryParameter("lastmodified", modifiedSince.toString("yyyyMMdd'T'HHmmss"))
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, COpportunities>() {
                    @Override
                    public COpportunities apply(WS.Response response) throws Throwable {
                        return (COpportunities) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public static F.Promise<COpportunities> listByParty(CParty party) {
        return listByParty(party, 12, TimeUnit.SECONDS);
    }

    public static F.Promise<COpportunities> listByParty(CParty party, long time, TimeUnit unit) {
        return WS.url(capsuleUrl + "/api/party/" + party.id + "/opportunity")
                .setTimeout((int) unit.toMillis(time))
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, COpportunities>() {
                    @Override
                    public COpportunities apply(WS.Response response) throws Throwable {
                        return (COpportunities) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .add("partyId", partyId)
                .add("currency", currency)
                .add("value", value)
                .add("durationBasis", durationBasis)
                .add("duration", duration)
                .add("expectedCloseDate", expectedCloseDate)
                .add("milestoneId", milestoneId)
                .add("milestone", milestone)
                .add("probability", probability)
                .add("owner", owner)
                .add("trackId", trackId)
                .toString();
    }
}
