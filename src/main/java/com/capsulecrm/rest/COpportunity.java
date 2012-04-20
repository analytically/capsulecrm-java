package com.capsulecrm.rest;

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
    String readContextPath() {
        return "/opportunity";
    }
}
