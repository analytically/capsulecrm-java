package com.capsulecrm.rest;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CParties implements Iterable<CParty> {
    private int size;

    private List<COrganisation> organisations;
    private List<CPerson> persons;

    public CParties(int size, List<COrganisation> organisations, List<CPerson> persons) {
        this.size = size;
        this.organisations = organisations;
        this.persons = persons;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<COrganisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<COrganisation> organisations) {
        this.organisations = organisations;
    }

    public List<CPerson> getPersons() {
        return persons;
    }

    public void setPersons(List<CPerson> persons) {
        this.persons = persons;
    }

    @Override
    public Iterator<CParty> iterator() {
        if (size != organisations.size() + persons.size()) {
            throw new IllegalStateException("size = " + size + " but organisations + persons = " + organisations.size() + persons.size());
        }
        return Iterators.concat(organisations.iterator(), persons.iterator());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("size", size)
                .add("organisations", organisations)
                .add("persons", persons)
                .toString();
    }
}
