package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import com.thoughtworks.xstream.io.xml.DomReader;
import play.libs.F;
import play.libs.WS;

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

    public F.Promise<CParties> listPeople() throws Exception {
        return WS.url(capsuleUrl + "/api/party/" + id + "/people")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, CParties>() {
                    @Override
                    public CParties apply(WS.Response response) throws Throwable {
                        return (CParties) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }
}