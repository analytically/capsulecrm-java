package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import uk.co.coen.capsulecrm.client.utils.ListenableFutureAdapter;
import uk.co.coen.capsulecrm.client.utils.UnmarshalResponseBody;

import java.util.concurrent.Future;

import static com.google.common.util.concurrent.Futures.transform;

public class COrganisation extends CParty {
    public String name;

    @Override
    protected String writeContextPath() {
        return "/organisation";
    }

    public COrganisation(String name) {
        this.name = name;
    }

    public COrganisation(String name, String about) {
        this.name = name;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .toString();
    }

    public Future<CParties> listPeople() throws Exception {
        return transform(new ListenableFutureAdapter<>(asyncHttpClient.prepareGet(capsuleUrl + "/api/party/" + id + "/people")
                .addHeader("Accept", "application/xml")
                .setRealm(realm)
                .execute()),
                new UnmarshalResponseBody<CParties>(xstream));
    }
}