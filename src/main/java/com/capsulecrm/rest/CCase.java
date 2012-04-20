package com.capsulecrm.rest;

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
    String readContextPath() {
        return "/kase";
    }


}
