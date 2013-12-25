package uk.co.coen.capsulecrm.client;

import com.ning.http.client.*;
import com.thoughtworks.xstream.XStream;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import uk.co.coen.capsulecrm.client.utils.JodaDateTimeXStreamConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public abstract class SimpleCapsuleEntity extends CIdentifiable {
    static Config conf = ConfigFactory.load();
    static final String capsuleUrl = conf.getString("capsulecrm.url");
    static final String capsuleToken = conf.getString("capsulecrm.token");

    static final AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder()
            .setAllowPoolingConnection(true)
            .build();

    static final AsyncHttpClient asyncHttpClient = new AsyncHttpClient(config);
    static final Realm realm;

    static final XStream xstream;

    static {
        realm = new Realm.RealmBuilder()
                .setPrincipal(capsuleToken)
                .setUsePreemptiveAuth(true)
                .setScheme(Realm.AuthScheme.BASIC)
                .build();

        xstream = new XStream();
        xstream.registerConverter(new JodaDateTimeXStreamConverter());
        xstream.addDefaultImplementation(ArrayList.class, List.class);

        //xstream.addImplicitCollection(CHistoryItem.class, "attachments", CAttachment.class);
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
        xstream.alias("customFields", CCustomFields.class);
        xstream.aliasAttribute(CCustomFields.class, "size", "size");
        xstream.addImplicitCollection(CCustomFields.class, "customFields", CCustomField.class);

        xstream.alias("opportunities", COpportunities.class);
        xstream.addImplicitCollection(COpportunities.class, "opportunities", COpportunity.class);
        xstream.alias("opportunity", COpportunity.class);

        xstream.alias("milestones", CMilestones.class);
        xstream.addImplicitCollection(CMilestones.class, "milestones", CMilestone.class);
        xstream.alias("milestone", CMilestone.class);
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

    public Future<Response> save() throws IOException {
        if (id != null) {
            System.out.println(xstream.toXML(this));
            return asyncHttpClient.preparePut(capsuleUrl + "/api" + writeContextPath() + "/" + id)
                    .addHeader("Content-Type", "application/xml")
                    .setRealm(realm)
                    .setBodyEncoding("UTF-8")
                    .setBody(xstream.toXML(this))
                    .execute();
        } else {
            return asyncHttpClient.preparePost(capsuleUrl + "/api" + writeContextPath())
                    .addHeader("Content-Type", "application/xml")
                    .setRealm(realm)
                    .setBodyEncoding("UTF-8")
                    .setBody(xstream.toXML(this))
                    .execute(new AsyncCompletionHandler<Response>() {
                        @Override
                        public Response onCompleted(Response response) throws Exception {
                            String location = response.getHeader("Location");
                            if (location == null) {
                                throw new RuntimeException("null location, cannot assign id to " + this + ", status is " + response.getStatusCode() + " " + response.getStatusText());
                            }
                            id = Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));
                            return response;
                        }
                    });
        }
    }

    public Future<Response> delete() throws IOException {
        return asyncHttpClient.prepareDelete(capsuleUrl + "/api" + readContextPath() + "/" + id)
                .setRealm(realm)
                .execute();
    }
}