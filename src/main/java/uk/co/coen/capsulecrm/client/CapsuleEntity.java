package uk.co.coen.capsulecrm.client;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.concurrent.Future;

import static com.google.common.util.concurrent.Futures.transform;

public abstract class CapsuleEntity extends SimpleCapsuleEntity {
    public DateTime createdOn;
    public DateTime updatedOn;

    public Future<CCustomFields> listCustomFields() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api" + readContextPath() + "/" + id + "/customfield")
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()), new TransformHttpResponse<CCustomFields>(xstream));
    }

    public Future<Response> add(final CCustomField customField) throws IOException {
        if (customField.id != null) {
            return asyncHttpClient.preparePut(capsuleUrl + "/api" + readContextPath() + "/" + id + "/customfield/" + customField.id)
                    .addHeader("Accept", "application/xml")
                    .setRealm(realm)
                    .setBody(xstream.toXML(customField))
                    .execute();
        } else {
            return asyncHttpClient.preparePost(capsuleUrl + "/api" + readContextPath() + "/" + id + "/customfield")
                    .addHeader("Content-Type", "application/xml")
                    .setRealm(realm)
                    .setBody(xstream.toXML(customField))
                    .execute(new AsyncCompletionHandler<Response>() {
                        @Override
                        public Response onCompleted(Response response) throws Exception {
                            // extract ID from location
                            String location = response.getHeader("Location");
                            if (location == null) {
                                throw new RuntimeException("null location, cannot assign id to custom field " + this + ", status is " + response.getStatusCode() + " " + response.getStatusText());
                            }
                            customField.id = Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));
                            return response;
                        }
                    });
        }
    }

    public Future<Response> remove(CCustomField customField) throws IOException {
        return asyncHttpClient.prepareDelete(capsuleUrl + "/api" + readContextPath() + "/" + id + "/customfield/" + customField.id)
                .setRealm(realm)
                .execute();
    }

    public Future<CHistory> listHistory() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api" + readContextPath() + "/" + id + "/history")
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()),
                new TransformHttpResponse<CHistory>(xstream));
    }

    public Future<Response> add(final CHistoryItem item) throws IOException {
        if (item.id != null) {
            return asyncHttpClient.preparePut(capsuleUrl + "/api" + readContextPath() + "/" + id + "/history/" + item.id)
                    .addHeader("Accept", "application/xml")
                    .setRealm(realm)
                    .setBody(xstream.toXML(item))
                    .execute();
        } else {
            return asyncHttpClient.preparePost(capsuleUrl + "/api" + readContextPath() + "/" + id + "/history")
                    .addHeader("Content-Type", "application/xml")
                    .setRealm(realm)
                    .setBody(xstream.toXML(item))
                    .execute(new AsyncCompletionHandler<Response>() {
                        @Override
                        public Response onCompleted(Response response) throws Exception {
                            // extract ID from location
                            String location = response.getHeader("Location");
                            if (location == null) {
                                throw new RuntimeException("null location, cannot assign id to history item " + this + ", status is " + response.getStatusCode() + " " + response.getStatusText());
                            }
                            item.id = Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));
                            return response;
                        }
                    });
        }
    }

    public Future<Response> remove(final CHistoryItem item) throws IOException {
        return asyncHttpClient.prepareDelete(capsuleUrl + "/api" + readContextPath() + "/" + id + "/history/" + item.id)
                .setRealm(realm)
                .execute();
    }

    public Future<Response> add(CTag tag) throws IOException {
        return asyncHttpClient.preparePost(capsuleUrl + "/api" + readContextPath() + "/" + id + "/tag/" + tag.name)
                .addHeader("Content-Type", "application/xml")
                .setRealm(realm)
                .setBody(xstream.toXML(tag))
                .execute();
    }

    public Future<Response> remove(CTag tag) throws IOException {
        return asyncHttpClient.prepareDelete(capsuleUrl + "/api" + readContextPath() + "/" + id + "/tag/" + tag.name)
                .setRealm(realm)
                .execute();
    }

    public Future<CTasks> listTasks() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api" + readContextPath() + "/" + id + "/tasks")
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()),
                new TransformHttpResponse<CTasks>(xstream));
    }

    public Future<CTasks> listTasks(TaskStatus status) throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api" + readContextPath() + "/" + id + "/tasks")
                .addQueryParameter("status", status.name())
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()),
                new TransformHttpResponse<CTasks>(xstream));
    }

    public Future<Response> add(final CTask task) throws IOException {
        return asyncHttpClient.preparePost(capsuleUrl + "/api" + readContextPath() + "/" + id + "/task")
                .addHeader("Content-Type", "application/xml")
                .setRealm(realm)
                .setBody(xstream.toXML(task))
                .execute(new AsyncCompletionHandler<Response>() {
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        // extract ID from location
                        String location = response.getHeader("Location");
                        if (location == null) {
                            throw new RuntimeException("null location, cannot assign id to task " + this + ", status is " + response.getStatusCode() + " " + response.getStatusText());
                        }
                        task.id = Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));
                        return response;
                    }
                });
    }
}