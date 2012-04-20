package com.capsulecrm.rest;

import org.joda.time.DateTime;

/**
 * @author Mathias Bogaert
 */
public class CCase extends CapsuleEntity {

    private String status;
    private String name;
    private String description;
    private Integer partyId;
    private DateTime closeDate;
    private String owner;

    @Override
    String readContextPath() {
        return "/kase";
    }


}
