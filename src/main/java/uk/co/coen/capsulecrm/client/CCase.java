package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.ning.http.client.Param;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class CCase extends CapsuleEntity {
    public String status;
    public String name;
    public String description;
    public Long partyId;
    public DateTime closeDate;
    public String owner;
    public Long trackId;
    public String endDate;

    @Override
    protected String readContextPath() {
        return "/kase";
    }

    @Override
    protected List<Param> extraQueryParams() {
        List<Param> extraQueryParams = new ArrayList<>();
        if (trackId != null) extraQueryParams.add(new Param("trackId", trackId.toString()));
        if (endDate != null) extraQueryParams.add(new Param("endDate", endDate));
        return extraQueryParams;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("name", name)
                .add("description", description)
                .add("partyId", partyId)
                .add("closeDate", closeDate)
                .add("owner", owner)
                .add("trackId", trackId)
                .add("endDate", endDate)
                .toString();
    }
}
