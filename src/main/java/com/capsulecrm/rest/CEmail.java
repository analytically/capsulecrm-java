package com.capsulecrm.rest;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CEmail extends CContact {
    private String emailAddress;

    public CEmail(String type) {
        super(type);
    }

    public CEmail(String type, String emailAddress) {
        super(type);
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("emailAddress", emailAddress)
                .toString();
    }
}
