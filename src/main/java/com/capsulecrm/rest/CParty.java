package com.capsulecrm.rest;

import com.google.common.collect.Lists;
import com.ning.http.client.Realm;
import com.thoughtworks.xstream.io.xml.DomReader;
import org.joda.time.DateTime;
import play.libs.F;
import play.libs.WS;

import java.util.List;

/**
 * @author Mathias Bogaert
 */
public abstract class CParty extends CapsuleEntity {
    public List<CContact> contacts; // mixes Address, Phone, Email, Website

    public String about;
    public String pictureURL;

    public abstract String getName();

    public void addContact(CContact contact) {
        if (contacts == null) {
            contacts = Lists.newArrayList();
        }
        contacts.add(contact);
    }

    public CAddress firstAddress() {
        for (CContact contact : contacts) {
            if (contact instanceof CAddress) {
                return (CAddress) contact;
            }
        }
        return null;
    }

    public CEmail firstEmail() {
        for (CContact contact : contacts) {
            if (contact instanceof CEmail) {
                return (CEmail) contact;
            }
        }
        return null;
    }

    public CPhone firstPhone() {
        for (CContact contact : contacts) {
            if (contact instanceof CPhone) {
                return (CPhone) contact;
            }
        }
        return null;
    }

    public CWebsite firstWebsite() {
        for (CContact contact : contacts) {
            if (contact instanceof CWebsite) {
                return (CWebsite) contact;
            }
        }
        return null;
    }

    @Override
    public String readContextPath() {
        return "/party";
    }

    public static F.Promise<CParties> search(String query) {
        return WS.url(capsuleUrl + "/api/party")
                .setQueryParameter("q", query)
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.NONE)
                .get().map(new F.Function<WS.Response, CParties>() {
                    @Override
                    public CParties apply(WS.Response response) throws Throwable {
                        return (CParties) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public static F.Promise<CParties> listAll() {
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

    public static F.Promise<CParties> listModifiedSince(DateTime modifiedSince) {
        return WS.url(capsuleUrl + "/api/party")
                .setQueryParameter("lastmodified", modifiedSince.toString("yyyyMMdd'T'HHmmss"))
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.NONE)
                .get().map(new F.Function<WS.Response, CParties>() {
                    @Override
                    public CParties apply(WS.Response response) throws Throwable {
                        return (CParties) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public static F.Promise<CParties> listByEmailAddress(String emailAddress) {
        return WS.url(capsuleUrl + "/api/party")
                .setQueryParameter("email", emailAddress)
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.NONE)
                .get().map(new F.Function<WS.Response, CParties>() {
                    @Override
                    public CParties apply(WS.Response response) throws Throwable {
                        return (CParties) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public static F.Promise<CParties> listByTag(String tag) {
        return WS.url(capsuleUrl + "/api/party")
                .setQueryParameter("tag", tag)
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.NONE)
                .get().map(new F.Function<WS.Response, CParties>() {
                    @Override
                    public CParties apply(WS.Response response) throws Throwable {
                        return (CParties) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
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

    public F.Promise<WS.Response> deleteContact(CContact contact) {
        return WS.url(capsuleUrl + "/api/party/" + id + "/contact/" + contact.id)
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "x", Realm.AuthScheme.BASIC)
                .delete();
    }
}
