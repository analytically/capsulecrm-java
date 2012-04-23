package com.zestia.capsule.restapi;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

/**
 * @author Mathias Bogaert
 */
public class COpportunity extends CapsuleEntity {
    public String name;
    public String description;
    public Integer partyId;
    public String currency;
    public String value;
    public String durationBasis;
    public Integer duration;
    public DateTime expectedCloseDate;
    public String milestone;
    public String probability;
    public String owner;

    @Override
    protected String readContextPath() {
        return "/opportunity";
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .add("partyId", partyId)
                .add("currency", currency)
                .add("value", value)
                .add("durationBasis", durationBasis)
                .add("duration", duration)
                .add("expectedCloseDate", expectedCloseDate)
                .add("milestone", milestone)
                .add("probability", probability)
                .add("owner", owner)
                .toString();
    }
}
