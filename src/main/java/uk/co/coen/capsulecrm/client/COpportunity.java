package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import org.joda.time.DateTime;
import uk.co.coen.capsulecrm.client.utils.ListenableFutureAdapter;
import uk.co.coen.capsulecrm.client.utils.ThrowOnHttpFailure;
import uk.co.coen.capsulecrm.client.utils.UnmarshalResponseBody;

import java.io.IOException;
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

    @Override
    protected String readContextPath() {
        return "/opportunity";
    }

    @Override
    protected String writeContextPath() {
        return "/party/" + partyId + "/opportunity";
    }

    public static Future<CMilestones> listMilestones() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/opportunity/milestones")
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CMilestones>(xstream));
    }

    public static Future<COpportunities> listAll() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/opportunity")
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<COpportunities>(xstream));
    }

    public static Future<COpportunities> listByTag(String tag) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/opportunity")
                .addQueryParameter("tag", tag)
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<COpportunities>(xstream));
    }

    public static Future<COpportunities> listModifiedSince(DateTime modifiedSince) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/opportunity")
                .addQueryParameter("lastmodified", modifiedSince.toString("yyyyMMdd'T'HHmmss"))
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<COpportunities>(xstream));
    }

    public static Future<COpportunities> listByMilestone(String milestoneName) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/opportunity")
                .addQueryParameter("milestone", milestoneName)
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<COpportunities>(xstream));
    }

    public static Future<COpportunities> listByParty(CParty party) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/party/" + party.id + "/opportunity")
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<COpportunities>(xstream));
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
