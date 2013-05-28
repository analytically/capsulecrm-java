package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
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
                organisations != null ? organisations.iterator() : new ArrayList<COrganisation>().iterator(),
                persons != null ? persons.iterator() : new ArrayList<CPerson>().iterator());
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