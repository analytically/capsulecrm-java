package com.zestia.capsule.restapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class COpportunities implements Iterable<COpportunity> {
    public List<COpportunity> opportunities;

    public COpportunities(List<COpportunity> opportunities) {
        this.opportunities = opportunities;
    }

    @Override
    public Iterator<COpportunity> iterator() {
        return opportunities != null ? opportunities.iterator() : new ArrayList<COpportunity>().iterator();
    }
}
