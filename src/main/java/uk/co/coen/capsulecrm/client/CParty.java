package uk.co.coen.capsulecrm.client;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.joda.time.DateTime;
import uk.co.coen.capsulecrm.client.utils.ListenableFutureAdapter;
import uk.co.coen.capsulecrm.client.utils.ThrowOnHttpFailure;
import uk.co.coen.capsulecrm.client.utils.UnmarshalResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import static com.google.common.util.concurrent.Futures.transform;

public abstract class CParty extends CapsuleEntity {
    public List<CContact> contacts; // mixes Address, Phone, Email, Website
    public List<CTag> tags;

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
            if (contact instanceof CEmail && !Strings.isNullOrEmpty(((CEmail) contact).emailAddress)) {
                return (CEmail) contact;
            }
        }
        return null;
    }

    public CPhone firstPhone() {
        for (CContact contact : contacts) {
            if (contact instanceof CPhone && !Strings.isNullOrEmpty(((CPhone) contact).phoneNumber)) {
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

                if ((webService == null || webService == website.webService) && !Strings.isNullOrEmpty(((CWebsite) contact).webAddress)) {
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

    public Future<CTasks> listTasks() throws IOException {
        return listTasks(null);
    }

    public Future<CTasks> listTasks(TaskStatus status) throws IOException {
        AsyncHttpClient.BoundRequestBuilder request = asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/tasks");

        if (status != null) {
            request.addQueryParam("status", status.name());
        }

        return transform(new ListenableFutureAdapter<>(request
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CTasks>(xstream) {
            @Override
            public CTasks apply(Response response) {
                CTasks tasks = super.apply(response);

                tasks.tasks = Lists.newArrayList(Collections2.filter(tasks.tasks, new Predicate<CTask>() {
                    @Override
                    public boolean apply(CTask task) {
                        return task.partyId != null && task.partyId.equals(id);
                    }
                }));
                tasks.size = tasks.tasks.size();

                return tasks;
            }
        });
    }

    public static Future<CParties> search(String query) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party")
                .addQueryParam("q", query)
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CParties>(xstream));
    }

    public static Future<CParties> search(String query, int start, int limit) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party")
                .addQueryParam("q", query)
                .addQueryParam("start", "" + start)
                .addQueryParam("limit", "" + limit)
                .addQueryParam("embed", "tags")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CParties>(xstream));
    }

    public static Future<CParties> listAll() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party")
                .addHeader("Accept", "application/xml")
                .addQueryParam("embed", "tags")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CParties>(xstream));
    }

    public static Future<CParties> listModifiedSince(DateTime modifiedSince) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party")
                .addQueryParam("lastmodified", modifiedSince.toString("yyyyMMdd'T'HHmmss"))
                .addQueryParam("embed", "tags")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CParties>(xstream));
    }

    public static Future<CParties> listModifiedSince(DateTime modifiedSince, int start, int limit) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party")
                .addQueryParam("lastmodified", modifiedSince.toString("yyyyMMdd'T'HHmmss"))
                .addQueryParam("start", "" + start)
                .addQueryParam("limit", "" + limit)
                .addQueryParam("embed", "tags")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CParties>(xstream));
    }

    public static Future<CParties> listByEmailAddress(String emailAddress) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party")
                .addQueryParam("email", emailAddress)
                .addQueryParam("embed", "tags")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CParties>(xstream));
    }

    public static Future<CParties> listByEmailAddress(String emailAddress, int start, int limit) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party")
                .addQueryParam("email", emailAddress)
                .addQueryParam("start", "" + start)
                .addQueryParam("limit", "" + limit)
                .addQueryParam("embed", "tags")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CParties>(xstream));
    }

    public static Future<CParties> listByTag(String tag) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party")
                .addQueryParam("tag", tag)
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CParties>(xstream));
    }

    public static Future<CParties> listByTag(String tag, int start, int limit) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party")
                .addQueryParam("tag", tag)
                .addQueryParam("start", "" + start)
                .addQueryParam("limit", "" + limit)
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CParties>(xstream));
    }

    public static Future<CParty> byId(Long id) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/party/" + id)
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CParty>(xstream));
    }

    public Future<Response> deleteContact(CContact contact) throws IOException {
        return asyncHttpClient.prepareDelete(getCapsuleUrl() + "/api/party/" + id + "/contact/" + contact.id)
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure());
    }
}