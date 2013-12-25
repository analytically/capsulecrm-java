package uk.co.coen.capsulecrm.client;

import com.google.common.collect.Lists;
import com.ning.http.client.Response;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Futures.transform;

public abstract class CParty extends CapsuleEntity {
    public List<CContact> contacts; // mixes Address, Phone, Email, Website

    public String about;
    public String pictureURL;
    public DateTime lastContactedOn;

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
        return firstWebsite(null);
    }

    public CWebsite firstWebsite(WebService webService) {
        for (CContact contact : contacts) {
            if (contact instanceof CWebsite) {
                CWebsite website = (CWebsite) contact;

                if (webService == null || webService == website.webService) {
                    return (CWebsite) contact;
                }
            }
        }
        return null;
    }

    @Override
    public String readContextPath() {
        return "/party";
    }

    public static Future<CParties> search(String query) throws IOException {
        return search(query, 12, TimeUnit.SECONDS);
    }

    public static Future<CParties> search(String query, long time, TimeUnit unit) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/party")
                .addQueryParameter("q", query)
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()), new TransformHttpResponse<CParties>(xstream));
    }

    public static Future<CParties> listAll() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/party")
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()), new TransformHttpResponse<CParties>(xstream));
    }

    public static Future<CParties> listModifiedSince(DateTime modifiedSince) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/party")
                .addQueryParameter("lastmodified", modifiedSince.toString("yyyyMMdd'T'HHmmss"))
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()), new TransformHttpResponse<CParties>(xstream));
    }

    public static Future<CParties> listByEmailAddress(String emailAddress) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/party")
                .addQueryParameter("email", emailAddress)
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()), new TransformHttpResponse<CParties>(xstream));
    }

    public static Future<CParties> listByTag(String tag) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/party")
                .addQueryParameter("tag", tag)
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()), new TransformHttpResponse<CParties>(xstream));
    }

    public static Future<CParty> byId(Integer id) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/party/" + id)
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()), new TransformHttpResponse<CParty>(xstream));
    }

    public Future<Response> deleteContact(CContact contact) throws IOException {
        return asyncHttpClient.prepareDelete(capsuleUrl + "/api/party/" + id + "/contact/" + contact.id)
                .setRealm(realm)
                .execute();
    }
}