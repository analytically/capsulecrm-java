package com.capsulecrm.rest;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CPhone extends CContact {
    private String phoneNumber;

    public CPhone(String type, String phoneNumber) {
        super(type);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("phoneNumber", phoneNumber)
                .toString();
    }
}
