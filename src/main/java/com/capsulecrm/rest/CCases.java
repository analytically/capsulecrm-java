package com.capsulecrm.rest;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CCases implements Iterable<CCase> {
    private int size;
    private List<CCase> cases;

    public CCases(int size, List<CCase> cases) {
        this.size = size;
        this.cases = cases;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<CCase> getCases() {
        return cases;
    }

    public void setCases(List<CCase> cases) {
        this.cases = cases;
    }

    @Override
    public Iterator<CCase> iterator() {
        return cases.iterator();
    }
}
