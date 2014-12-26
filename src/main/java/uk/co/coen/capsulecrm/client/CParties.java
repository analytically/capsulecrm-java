package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;

public class CParties implements Iterable<CParty> {
    public int size;

    public List<COrganisation> organisations;
    public List<CPerson> persons;

    public CParties(int size, List<COrganisation> organisations, List<CPerson> persons) {
        this.size = size;
        this.organisations = organisations;
        this.persons = persons;
    }

    @Override
    public Iterator<CParty> iterator() {
        return Iterators.concat(
                organisations != null ? organisations.iterator() : ImmutableSet.<COrganisation>of().iterator(),
                persons != null ? persons.iterator() : ImmutableSet.<CPerson>of().iterator());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .add("organisations", organisations)
                .add("persons", persons)
                .toString();
    }
}
