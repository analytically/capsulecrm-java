
package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.joda.time.DateTime;
import uk.co.coen.capsulecrm.client.utils.ListenableFutureAdapter;
import uk.co.coen.capsulecrm.client.utils.ThrowOnHttpFailure;
import uk.co.coen.capsulecrm.client.utils.UnmarshalResponseBody;

import java.io.IOException;
import java.util.concurrent.Future;

import static com.google.common.util.concurrent.Futures.transform;

public class CTask extends SimpleCapsuleEntity {
    public String description;
    public String detail;
    public String category;
    public TaskStatus status;
    public DateTime dueDate;
    public DateTime dueDateTime;
    public DateTime completedOn;
    public String owner;

    public Long partyId;
    public String partyName;

    public Long caseId;
    public String caseName;

    public Long opportunityId;
    public String opportunityName;

    public CTask() {
    }

    public CTask(String description, DateTime dueDateTime) {
        this(description, null, dueDateTime, true);
    }

    public CTask(String description, DateTime dueDateTime, boolean saveTime) {
        this(description, null, dueDateTime, saveTime);
    }

    public CTask(String description, String detail, DateTime dueDateTime, boolean saveTime) {
        this.description = description;
        this.detail = detail;
        if (saveTime) {
            this.dueDateTime = dueDateTime;
        } else {
            this.dueDate = dueDateTime;
        }
    }

    @Override
    protected String readContextPath() {
        return "/task";
    }

    public static Future<CTasks> list() throws IOException {
        return list(null, null, null, 0, 0);
    }

    public static Future<CTasks> listByCategory(String category) throws IOException {
        return list(category, null, null, 0, 0);
    }

    public static Future<CTasks> listByUser(String user) throws IOException {
        return list(null, user, null, 0, 0);
    }

    public static Future<CTasks> list(int start, int limit) throws IOException {
        return list(null, null, null, start, limit);
    }

    public static Future<CTasks> list(TaskStatus status) throws IOException {
        return list(null, null, status, 0, 0);
    }

    public static Future<CTasks> list(TaskStatus status, int start, int limit) throws IOException {
        return list(null, null, status, start, limit);
    }

    public static Future<CTasks> list(String category, String user, TaskStatus status, int start, int limit) throws IOException {
        AsyncHttpClient.BoundRequestBuilder request = asyncHttpClient.prepareGet(capsuleUrl + "/api/tasks");

        if (category != null) {
            request.addQueryParameter("category", category);
        }
        if (user != null) {
            request.addQueryParameter("user", user);
        }
        if (status != null) {
            request.addQueryParameter("status", status.name());
        }
        if (start != 0) {
            request.addQueryParameter("start", "" + start);
        }
        if (limit != 0) {
            request.addQueryParameter("limit", "" + limit);
        }

        return transform(new ListenableFutureAdapter<>(request
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CTasks>(xstream));
    }

    public Future<Response> complete() throws IOException {
        return asyncHttpClient.preparePost(capsuleUrl + "/api/task/" + id + "/complete")
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .setBody("")
                .execute(new AsyncCompletionHandler<Response>() {
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        if (response.getStatusCode() == 200) {
                            status = TaskStatus.COMPLETED;
                        }

                        return response;
                    }
                });
    }

    public Future<Response> reopen() throws IOException {
        return asyncHttpClient.preparePost(capsuleUrl + "/api/task/" + id + "/reopen")
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .setBody("")
                .execute(new AsyncCompletionHandler<Response>() {
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        if (response.getStatusCode() == 200) {
                            status = TaskStatus.OPEN;
                        }

                        return response;
                    }
                });
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("description", description)
                .add("detail", detail)
                .add("category", category)
                .add("status", status)
                .add("dueDate", dueDate)
                .add("dueDateTime", dueDateTime)
                .add("completedOn", completedOn)
                .add("owner", owner)
                .add("partyId", partyId)
                .add("partyName", partyName)
                .add("caseId", caseId)
                .add("caseName", caseName)
                .add("opportunityId", opportunityId)
                .add("opportunityName", opportunityName)
                .toString();
    }
}
