package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

public class CCase extends CapsuleEntity {
    public String status;
    public String name;
    public String description;
    public Long partyId;
    public DateTime closeDate;
    public String owner;
    public Long trackId;

    @Override
    protected String readContextPath() {
        return "/kase";
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("status", status)
                .add("name", name)
                .add("description", description)
                .add("partyId", partyId)
                .add("closeDate", closeDate)
                .add("owner", owner)
                .add("trackId", trackId)
                .toString();
    }
}
