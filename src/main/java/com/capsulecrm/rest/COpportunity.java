package com.capsulecrm.rest;

import org.joda.time.DateTime;

/**
 * @author Mathias Bogaert
 */
public class COpportunity extends CapsuleEntity {

    private String name;
    private String description;
    private Integer partyId;
    private String currency;
    private String value;
    private String durationBasis;
    private Integer duration;
    private DateTime expectedCloseDate;
    private String milestone;
    private String probability;
    private String owner;

    @Override
    String readContextPath() {
        return "/opportunity";
    }
}
