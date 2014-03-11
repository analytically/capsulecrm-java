package uk.co.coen.capsulecrm.client;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import org.joda.time.DateTime;
import uk.co.coen.capsulecrm.client.utils.ThrowOnHttpFailure;
import uk.co.coen.capsulecrm.client.utils.ListenableFutureAdapter;
import uk.co.coen.capsulecrm.client.utils.UnmarshalResponseBody;

import java.io.IOException;
import java.util.concurrent.Future;

import static com.google.common.util.concurrent.Futures.transform;

public abstract class CapsuleEntity extends SimpleCapsuleEntity {
    public DateTime createdOn;
    public DateTime updatedOn;

    public Future<CCustomFields> listCustomFields() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/customfields")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CCustomFields>(xstream));
    }

    public Future<Response> add(final CCustomField customField) throws IOException {
        return asyncHttpClient.preparePut(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/customfields")
                .addHeader("Content-Type", "application/xml")
                .setRealm(getRealm())
                .setBodyEncoding("UTF-8")
                .setBody(xstream.toXML(new CCustomFields(customField)))
                .execute(new ThrowOnHttpFailure());
    }

    public Future<Response> remove(CCustomField customField) throws IOException {
        customField.text = null;
        customField.date = null;
        customField.bool = null;

        return asyncHttpClient.preparePut(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/customfields")
                .addHeader("Content-Type", "application/xml")
                .setRealm(getRealm())
                .setBodyEncoding("UTF-8")
                .setBody(xstream.toXML(new CCustomFields(customField)))
                .execute(new ThrowOnHttpFailure());
    }

    public Future<CHistory> listHistory() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/history")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CHistory>(xstream));
    }

    public Future<Response> add(final CHistoryItem item) throws IOException {
        if (item.id != null) {
            return asyncHttpClient.preparePut(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/history/" + item.id)
                    .addHeader("Content-Type", "application/xml")
                    .setRealm(getRealm())
                    .setBodyEncoding("UTF-8")
                    .setBody(xstream.toXML(item))
                    .execute(new ThrowOnHttpFailure());
        } else {
            return asyncHttpClient.preparePost(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/history")
                    .addHeader("Content-Type", "application/xml")
                    .setRealm(getRealm())
                    .setBodyEncoding("UTF-8")
                    .setBody(xstream.toXML(item))
                    .execute(new ThrowOnHttpFailure() {
                        @Override
                        public Response onCompleted(Response response) throws Exception {
                            response = super.onCompleted(response);

                            // extract ID from location
                            String location = response.getHeader("Location");
                            if (location == null) {
                                throw new RuntimeException("null location, cannot assign id to history item " + this + ", status is " + response.getStatusCode() + " " + response.getStatusText());
                            }
                            item.id = Long.parseLong(location.substring(location.lastIndexOf("/") + 1));
                            return response;
                        }
                    });
        }
    }

    public Future<Response> remove(final CHistoryItem item) throws IOException {
        return asyncHttpClient.prepareDelete(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/history/" + item.id)
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure());
    }

    public Future<CTags> listTags() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/tag")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CTags>(xstream));
    }

    public Future<Response> add(CTag tag) throws IOException {
        return asyncHttpClient.preparePost(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/tag/" + tag.name)
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure());
    }

    public Future<Response> addTag(String tagName) throws IOException {
        return asyncHttpClient.preparePost(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/tag/" + tagName)
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure());
    }

    public Future<Response> remove(CTag tag) throws IOException {
        return asyncHttpClient.prepareDelete(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/tag/" + tag.name)
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure());
    }

    public Future<Response> removeTag(String tagName) throws IOException {
        return asyncHttpClient.prepareDelete(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/tag/" + tagName)
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure());
    }

    public Future<Response> add(final CTask task) throws IOException {
        return asyncHttpClient.preparePost(getCapsuleUrl() + "/api" + readContextPath() + "/" + id + "/task")
                .addHeader("Content-Type", "application/xml")
                .setRealm(getRealm())
                .setBodyEncoding("UTF-8")
                .setBody(xstream.toXML(task))
                .execute(new ThrowOnHttpFailure() {
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        response = super.onCompleted(response);

                        // extract ID from location
                        String location = response.getHeader("Location");
                        if (location == null) {
                            throw new RuntimeException("null location, cannot assign id to task " + this + ", status is " + response.getStatusCode() + " " + response.getStatusText());
                        }
                        task.id = Long.parseLong(location.substring(location.lastIndexOf("/") + 1));
                        return response;
                    }
                });
    }
}