package com.zestia.capsule.restapi;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CEmail extends CContact {
    public String emailAddress;

    public CEmail(String type) {
        super(type);
    }

    public CEmail(String type, String emailAddress) {
        super(type);
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("emailAddress", emailAddress)
                .toString();
    }
}
