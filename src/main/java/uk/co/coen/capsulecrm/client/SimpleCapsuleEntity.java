package uk.co.coen.capsulecrm.client;

import com.ning.http.client.*;
import com.ning.http.client.extra.ThrottleRequestFilter;
import com.thoughtworks.xstream.XStream;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import uk.co.coen.capsulecrm.client.utils.JodaDateTimeXStreamConverter;
import uk.co.coen.capsulecrm.client.utils.ThrowOnHttpFailure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

public abstract class SimpleCapsuleEntity extends CIdentifiable {
    static final Config conf = ConfigFactory.load();

    static final AsyncHttpClientConfig asyncHttpClientConfig = new AsyncHttpClientConfig.Builder()
            .addRequestFilter(new ThrottleRequestFilter(16))
            .setRequestTimeout(60000)
            .setCompressionEnforced(true)
            .build();

    static final AsyncHttpClient asyncHttpClient = new AsyncHttpClient(asyncHttpClientConfig);

    static final XStream xstream = new XStream();
    static {
        xstream.registerConverter(new JodaDateTimeXStreamConverter());
        xstream.addDefaultImplementation(ArrayList.class, List.class);

        xstream.addImplicitCollection(CHistoryItem.class, "attachments", CAttachment.class);
        xstream.alias("attachment", CAttachment.class);

        xstream.alias("organisation", COrganisation.class);
        xstream.alias("person", CPerson.class);

        xstream.alias("address", CAddress.class);
        xstream.alias("website", CWebsite.class);
        xstream.alias("email", CEmail.class);
        xstream.alias("phone", CPhone.class);

        xstream.alias("tags", CTags.class);
        xstream.aliasAttribute(CTags.class, "size", "size");
        xstream.addImplicitCollection(CTags.class, "tags", CTag.class);
        xstream.alias("tag", CTag.class);

        xstream.alias("tasks", CTasks.class);
        xstream.aliasAttribute(CTasks.class, "size", "size");
        xstream.addImplicitCollection(CTasks.class, "tasks", CTask.class);
        xstream.alias("task", CTask.class);

        xstream.alias("tracks", CTracks.class);
        xstream.addImplicitCollection(CTracks.class, "tracks", CTrack.class);
        xstream.alias("track", CTrack.class);

        xstream.alias("users", CUsers.class);
        xstream.addImplicitCollection(CUsers.class, "users", CUser.class);
        xstream.alias("user", CUser.class);

        xstream.alias("history", CHistory.class);
        xstream.aliasAttribute(CHistory.class, "size", "size");
        xstream.addImplicitCollection(CHistory.class, "historyItems", CHistoryItem.class);
        xstream.alias("historyItem", CHistoryItem.class);

        xstream.alias("parties", CParties.class);
        xstream.aliasAttribute(CParties.class, "size", "size");
        xstream.addImplicitCollection(CParties.class, "organisations", COrganisation.class);
        xstream.addImplicitCollection(CParties.class, "persons", CPerson.class);

        xstream.alias("customField", CCustomField.class);
        xstream.aliasField("boolean", CCustomField.class, "bool");
        xstream.alias("customFields", CCustomFields.class);
        xstream.aliasAttribute(CCustomFields.class, "size", "size");
        xstream.addImplicitCollection(CCustomFields.class, "customFields", CCustomField.class);

        xstream.alias("customFieldDefinition", CCustomFieldDefinition.class);
        xstream.alias("customFieldDefinitions", CCustomFieldDefinitions.class);
        xstream.addImplicitCollection(CCustomFieldDefinitions.class, "customFieldDefinitions", CCustomFieldDefinition.class);

        xstream.alias("opportunities", COpportunities.class);
        xstream.addImplicitCollection(COpportunities.class, "opportunities", COpportunity.class);
        xstream.alias("opportunity", COpportunity.class);

        xstream.alias("milestones", CMilestones.class);
        xstream.addImplicitCollection(CMilestones.class, "milestones", CMilestone.class);
        xstream.alias("milestone", CMilestone.class);
    }

    static String getCapsuleUrl() {
        return conf.getString("capsulecrm.url");
    }

    static Realm getRealm() {
        return new Realm.RealmBuilder()
                .setPrincipal(conf.getString("capsulecrm.token"))
                .setUsePreemptiveAuth(true)
                .setScheme(Realm.AuthScheme.BASIC)
                .build();
    }

    /**
     * @return The context path where to submit GET and DELETE requests.
     */
    protected abstract String readContextPath();

    /**
     * @return The context path where to submit POST and PUT requests.
     */
    protected String writeContextPath() {
        return readContextPath();
    }

    protected List<Param> extraQueryParams() {
        return Collections.emptyList();
    }

    public Future<Response> save() throws IOException {
        if (id != null) {
            return asyncHttpClient.preparePut(getCapsuleUrl() + "/api" + writeContextPath() + "/" + id)
                    .addHeader("Content-Type", "application/xml")
                    .setRealm(getRealm())
                    .setBodyEncoding("UTF-8")
                    .setBody(xstream.toXML(this))
                    .execute(new ThrowOnHttpFailure());
        } else {
            return asyncHttpClient.preparePost(getCapsuleUrl() + "/api" + writeContextPath())
                    .addHeader("Content-Type", "application/xml")
                    .addQueryParams(extraQueryParams())
                    .setRealm(getRealm())
                    .setBodyEncoding("UTF-8")
                    .setBody(xstream.toXML(this))
                    .execute(new ThrowOnHttpFailure() {
                        @Override
                        public Response onCompleted(Response response) throws Exception {
                            response = super.onCompleted(response);

                            String location = response.getHeader("Location");
                            if (location == null) {
                                throw new RuntimeException("null location, cannot assign id to " + this + ", status is " + response.getStatusCode() + " " + response.getStatusText());
                            }
                            id = Long.parseLong(location.substring(location.lastIndexOf("/") + 1));
                            return response;
                        }
                    });
        }
    }

    public Future<Response> delete() throws IOException {
        return asyncHttpClient.prepareDelete(getCapsuleUrl() + "/api" + readContextPath() + "/" + id)
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure());
    }
}