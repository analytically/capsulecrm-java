package com.capsulecrm.rest;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class COrganisation extends CParty {
    private String name;

    @Override
    protected String writeContextPath() {
        return "/organisation";
    }

    public COrganisation(String name) {
        this.name = name;
    }

    public COrganisation(String name, String about) {
        this.name = name;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .toString();
    }
}
