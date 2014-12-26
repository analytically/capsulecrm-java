package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import uk.co.coen.capsulecrm.client.utils.ListenableFutureAdapter;
import uk.co.coen.capsulecrm.client.utils.ThrowOnHttpFailure;
import uk.co.coen.capsulecrm.client.utils.UnmarshalResponseBody;

import java.io.IOException;
import java.util.concurrent.Future;

import static com.google.common.util.concurrent.Futures.transform;

public class CTrack extends SimpleCapsuleEntity {
    public String description;
    public String captureRule;

    @Override
    protected String readContextPath() {
        return "/tracks";
    }

    public static Future<CTracks> list() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/tracks")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CTracks>(xstream));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("description", description)
                .add("captureRule", captureRule)
                .toString();
    }
}