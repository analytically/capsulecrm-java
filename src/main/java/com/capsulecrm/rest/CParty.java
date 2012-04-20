package com.capsulecrm.rest;

import com.google.common.collect.Lists;
import com.ning.http.client.Realm;
import com.thoughtworks.xstream.io.xml.DomReader;
import play.libs.F;
import play.libs.WS;

import java.util.List;

/**
 * @author Mathias Bogaert
 */
public abstract class CParty extends CapsuleEntity implements HasContacts {
    public List<CContact> contacts; // mixes Address, Phone, Email, Website

    public String about;
    public String pictureURL;

    public abstract String getName();

    public List<CContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<CContact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(CContact contact) {
        if (contacts == null) {
            contacts = Lists.newArrayList();
        }
        contacts.add(contact);
    }

    public CAddress getFirstAddress() {
        for (CContact contact : contacts) {
            if (contact instanceof CAddress) {
                return (CAddress) contact;
            }
        }
        return null;
    }

    public CEmail getFirstEmailAddress() {
        for (CContact contact : contacts) {
            if (contact instanceof CEmail) {
                return (CEmail) contact;
            }
        }
        return null;
    }

    public CPhone getFirstPhone() {
        for (CContact contact : contacts) {
            if (contact instanceof CPhone) {
                return (CPhone) contact;
            }
        }
        return null;
    }
    
    public CWebsite getFirstWebsite() {
        for (CContact contact : contacts) {
            if (contact instanceof CWebsite) {
                return (CWebsite) contact;
            }
        }
        return null;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    @Override
    public String readContextPath() {
        return "/party";
    }

    public static F.Promise<CParties> findAll() {
        return WS.url(capsuleUrl + "/api/party")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.NONE)
                .get().map(new F.Function<WS.Response, CParties>() {
                    @Override
                    public CParties apply(WS.Response response) throws Throwable {
                        return (CParties) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }
    
    public static F.Promise<CParties> findByTag(String tag) {
        return WS.url(capsuleUrl + "/api/party?tag=" + tag)
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.NONE)
                .get().map(new F.Function<WS.Response, CParties>() {
                    @Override
                    public CParties apply(WS.Response response) throws Throwable {
                        return (CParties) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public static F.Promise<CParty> byId(String id) {
        return byId(Integer.parseInt(id));
    }

    public static F.Promise<CParty> byId(Integer id) {
        return WS.url(capsuleUrl + "/api/party/" + id)
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.NONE)
                .get().map(new F.Function<WS.Response, CParty>() {
                    @Override
                    public CParty apply(WS.Response response) throws Throwable {
                        if (response.getStatus() < 200 || response.getStatus() > 299)
                            throw new IllegalStateException("Response is not OK: " + response.getStatus()); // + " - " + response.getStatusText());

                        return (CParty) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public static F.Promise<CParties> findPeople(CParty party) throws Exception {
        return WS.url(capsuleUrl + "/api/party/" + party.getId() + "/people")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "", Realm.AuthScheme.BASIC)
                .get().map(new F.Function<WS.Response, CParties>() {
                    @Override
                    public CParties apply(WS.Response response) throws Throwable {
                        return (CParties) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public F.Promise<WS.Response> deleteContact(CContact contact) {
        return WS.url(capsuleUrl + "/api/party/" + getId() + "/contact/" + contact.getId())
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                .delete();
    }
}
