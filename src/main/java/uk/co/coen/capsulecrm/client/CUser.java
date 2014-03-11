package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import uk.co.coen.capsulecrm.client.utils.ListenableFutureAdapter;
import uk.co.coen.capsulecrm.client.utils.ThrowOnHttpFailure;
import uk.co.coen.capsulecrm.client.utils.UnmarshalResponseBody;

import java.io.IOException;
import java.util.concurrent.Future;

import static com.google.common.util.concurrent.Futures.transform;

public class CUser extends SimpleCapsuleEntity {
    public String username;
    public String name;
    public String currency;
    public String timezone;
    public String loggedIn;
    public String partyId;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", username)
                .add("name", name)
                .add("currency", currency)
                .add("timezone", timezone)
                .add("loggedIn", loggedIn)
                .add("partyId", partyId)
                .toString();
    }

    @Override
    protected String readContextPath() {
        return "/users";
    }

    public static Future<CUsers> list() throws IOException {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(getCapsuleUrl() + "/api/users")
                .addHeader("Accept", "application/xml")
                .setRealm(getRealm())
                .execute(new ThrowOnHttpFailure())), new UnmarshalResponseBody<CUsers>(xstream));
    }
}
