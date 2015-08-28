package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.ning.http.client.Param;
import org.joda.time.DateTime;
import uk.co.coen.capsulecrm.client.utils.ListenableFutureAdapter;
import uk.co.coen.capsulecrm.client.utils.ThrowOnHttpFailure;
import uk.co.coen.capsulecrm.client.utils.UnmarshalResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static com.google.common.util.concurrent.Futures.transform;

public class COpportunity extends CapsuleEntity {
    public String name;
    public String description;
    public Long partyId;
    public String currency;
    public String value;
    public String durationBasis;
    public Integer duration;
    public DateTime expectedCloseDate;
    public Long milestoneId;
    public String milestone;
    public String probability;
    public String owner;
    public Long trackId;
    public String endDate;

    @Override
    protected String readContextPath() {
        return "/opportunity";
    }

    @Override
    protected String writeContextPath() {
        return "/party/" + partyId + "/opportunity";
    }

    @Override
    protected List<Param> extraQueryParams() {
        List<Param> extraQueryParams = new ArrayList<>();
        if (trackId != null) extraQueryParams.add(new Param("trackId", trackId.toString()));
        if (endDate != null) extraQueryParams.add(new Param("endDate", endDate));
        return extraQueryParams;
    }

    public static Future<CMilestones> listMilestones() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/opportunity/milestones")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CMilestones>(xstream));
    }

    public static Future<COpportunities> listAll() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/opportunity")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<COpportunities>(xstream));
    }

    public static Future<COpportunities> listByTag(String tag) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/opportunity")
                .addQueryParam("tag", tag)
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<COpportunities>(xstream));
    }

    public static Future<COpportunities> listModifiedSince(DateTime modifiedSince) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/opportunity")
                .addQueryParam("lastmodified", modifiedSince.toString("yyyyMMdd'T'HHmmss"))
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<COpportunities>(xstream));
    }

    public static Future<COpportunities> listByMilestone(String milestoneName) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/opportunity")
                .addQueryParam("milestone", milestoneName)
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<COpportunities>(xstream));
    }

    public static Future<COpportunities> listByParty(CParty party) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party/" + party.id + "/opportunity")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<COpportunities>(xstream));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
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
                .add("endDate", endDate)
                .toString();
    }
}
