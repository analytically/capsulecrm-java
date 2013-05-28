package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

/**
 * @author Mathias Bogaert
 */
public class CCase extends CapsuleEntity {
    public String status;
    public String name;
    public String description;
    public Integer partyId;
    public DateTime closeDate;
    public String owner;

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
                .toString();
    }
}
