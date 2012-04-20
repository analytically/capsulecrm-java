package com.capsulecrm.rest;

import com.capsulecrm.rest.utils.JodaDateTimeXStreamConverter;
import com.ning.http.client.Realm;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.DomReader;
import org.joda.time.DateTime;
import play.Logger;
import play.Play;
import play.libs.F;
import play.libs.WS;

/**
 * @author Mathias Bogaert
 */
public abstract class CapsuleEntity extends CIdentifiable {
    static final XStream xstream;

    static {
        xstream = new XStream(new DomDriver("UTF-8"));
        xstream.registerConverter(new JodaDateTimeXStreamConverter());

        xstream.alias("address", CAddress.class);
        xstream.alias("website", CWebsite.class);
        xstream.alias("email", CEmail.class);
        xstream.alias("phone", CPhone.class);

        xstream.alias("tags", CTags.class);
        xstream.aliasAttribute(CTags.class, "size", "size");
        xstream.addImplicitCollection(CTags.class, "tags", CTag.class);
        xstream.alias("tag", CTag.class);

        xstream.alias("history", CHistory.class);
        xstream.aliasAttribute(CHistory.class, "size", "size");
        xstream.addImplicitCollection(CHistory.class, "historyItems", CHistoryItem.class);
        xstream.alias("historyItem", CHistoryItem.class);

        //xstream.addImplicitCollection(CHistoryItem.class, "attachments", CAttachment.class);
        xstream.alias("attachment", CAttachment.class);

        xstream.alias("organisation", COrganisation.class);
        xstream.alias("person", CPerson.class);

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
    abstract String readContextPath();

    /**
     * @return The context path where to submit POST and PUT requests.
     */
    protected String writeContextPath() {
        return readContextPath();
    }

    public DateTime createdOn;
    public DateTime updatedOn;

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    public DateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(DateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public F.Promise<WS.Response> save() {
        if (getId() != null) {
            return WS.url(capsuleUrl + "/api" + writeContextPath() + "/" + getId())
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                    .put(xstream.toXML(this));

        } else {
            return WS.url(capsuleUrl + "/api" + writeContextPath())
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                    .post(xstream.toXML(this)).map(new F.Function<WS.Response, WS.Response>() {
                        @Override
                        public WS.Response apply(WS.Response response) throws Throwable {
                            String location = response.getHeader("Location");
                            if (location == null) {
                                Logger.error("null location, cannot assign id to " + this + ", status is " + response.getStatus());
                                System.exit(-1); // MASSIVE BUG
                            }
                            setId(Integer.parseInt(location.substring(location.lastIndexOf("/") + 1)));
                            return response;
                        }
                    });
        }
    }

    public F.Promise<WS.Response> delete() {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId())
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                .delete();
    }

    public F.Promise<CCustomFields> getCustomFields() {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId() + "/customfield")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                .get().map(new F.Function<WS.Response, CCustomFields>() {
                    @Override
                    public CCustomFields apply(WS.Response response) throws Throwable {
                        return (CCustomFields) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public F.Promise<WS.Response> add(final CCustomField customField) {
        if (customField.getId() != null) {
            return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId() + "/customfield/" + customField.getId())
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                    .put(xstream.toXML(customField));
        } else {
            return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId() + "/customfield")
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                    .post(xstream.toXML(customField)).map(new F.Function<WS.Response, WS.Response>() {
                        @Override
                        public WS.Response apply(WS.Response response) throws Throwable {
                            // extract ID from location
                            String location = response.getHeader("Location");
                            if (location == null) {
                                Logger.error("null location, cannot assign id to custom field " + this + ", status is " + response.getStatus());
                                System.exit(-1); // BUG
                            }
                            customField.setId(Integer.parseInt(location.substring(location.lastIndexOf("/") + 1)));

                            return response;
                        }
                    });
        }
    }

    public F.Promise<WS.Response> remove(CCustomField customField) {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId() + "/customfield/" + customField.getId())
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                .delete();
    }

    public F.Promise<CHistory> getHistory() {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId() + "/history")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.NONE)
                .get().map(new F.Function<WS.Response, CHistory>() {
                    @Override
                    public CHistory apply(WS.Response response) throws Throwable {
                        return (CHistory) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public F.Promise<WS.Response> add(final CHistoryItem item) {
        if (item.getId() != null) {
            return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId() + "/history/" + item.getId())
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                    .put(xstream.toXML(item));
        } else {
            return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId() + "/history")
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                    .post(xstream.toXML(item)).map(new F.Function<WS.Response, WS.Response>() {
                        @Override
                        public WS.Response apply(WS.Response response) throws Throwable {
                            // extract ID from location
                            String location = response.getHeader("Location");
                            if (location == null) {
                                Logger.error("null location, cannot assign id to custom field " + this + ", status is " + response.getStatus());
                                System.exit(-1); // BUG
                            }
                            item.setId(Integer.parseInt(location.substring(location.lastIndexOf("/"))));

                            return response;
                        }
                    });
        }
    }

    public F.Promise<WS.Response> add(CTag tag) {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId() + "/tag/" + tag.getName())
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                .post(xstream.toXML(tag));
    }

    public F.Promise<WS.Response> remove(CTag tag) {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId() + "/tag/" + tag.getName())
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                .delete();
    }

    public F.Promise<WS.Response> add(final CTask task) {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + getId() + "/task")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                .post(xstream.toXML(task)).map(new F.Function<WS.Response, WS.Response>() {
                    @Override
                    public WS.Response apply(WS.Response response) throws Throwable {
                        // extract ID from location
                        String location = response.getHeader("Location");
                        if (location == null) {
                            Logger.error("null location, cannot assign id to custom field " + this + ", status is " + response.getStatus());
                            System.exit(-1); // BUG
                        }
                        task.setId(Integer.parseInt(location.substring(location.lastIndexOf("/"))));

                        return response;
                    }
                });
    }
}
