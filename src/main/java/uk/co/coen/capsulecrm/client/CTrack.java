package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import com.thoughtworks.xstream.io.xml.DomReader;
import play.libs.F;
import play.libs.WS;

public class CTrack extends SimpleCapsuleEntity {
    public String description;
    public String captureRule;

    @Override
    protected String readContextPath() {
        return "/tracks";
    }

    public static F.Promise<CTracks> list() {
        WS.WSRequestHolder holder = WS.url(capsuleUrl + "/api/tracks");

        return holder.setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, CTracks>() {
                    @Override
                    public CTracks apply(WS.Response response) throws Throwable {
                        return (CTracks) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("description", description)
                .add("captureRule", captureRule)
                .toString();
    }
}