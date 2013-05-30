package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import com.thoughtworks.xstream.io.xml.DomReader;
import play.libs.F;
import play.libs.WS;

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

    public static F.Promise<CUsers> list() {
        WS.WSRequestHolder holder = WS.url(capsuleUrl + "/api/users");

        return holder.setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, CUsers>() {
                    @Override
                    public CUsers apply(WS.Response response) throws Throwable {
                        return (CUsers) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }
}
