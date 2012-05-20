package com.zestia.capsule.restapi;

import com.google.common.io.ByteStreams;
import com.ning.http.client.Realm;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zestia.capsule.restapi.utils.JodaDateTimeXStreamConverter;
import play.Play;
import play.libs.F;
import play.libs.WS;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public abstract class SimpleCapsuleEntity extends CIdentifiable {
    static final XStream xstream;

    static {
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
    }

    static final String capsuleUrl = Play.application().configuration().getString("capsulecrm.url");
    static final String capsuleToken = Play.application().configuration().getString("capsulecrm.token");

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

    public F.Promise<WS.Response> save() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer;
        try {
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        xstream.toXML(this, writer);

        if (id != null) {
            return WS.url(capsuleUrl + "/api" + writeContextPath() + "/" + id)
                    .setHeader("Content-Type", "text/xml; charset=\"UTF-8\"")
                    .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                    .put(new ByteArrayInputStream(outputStream.toByteArray()));
        } else {
            return WS.url(capsuleUrl + "/api" + writeContextPath())
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                    .post(new ByteArrayInputStream(outputStream.toByteArray())).map(new F.Function<WS.Response, WS.Response>() {
                        @Override
                        public WS.Response apply(WS.Response response) throws Throwable {
                            String location = response.getHeader("Location");
                            if (location == null) {
                                throw new RuntimeException("null location, cannot assign id to " + this + ", status is " + response.getStatus() + " " + response.getStatusText());
                            }
                            id = Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));
                            return response;
                        }
                    });
        }
    }

    public F.Promise<WS.Response> delete() {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id)
                .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                .delete();
    }
}