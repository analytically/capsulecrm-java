package uk.co.coen.capsulecrm.client;

import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;

public class CMilestones implements Iterable<CMilestone> {
    public List<CMilestone> milestones;

    public CMilestones(List<CMilestone> milestones) {
        this.milestones = milestones;
    }

    @Override
    public Iterator<CMilestone> iterator() {
        return milestones != null ? milestones.iterator() : Iterators.<CMilestone>emptyIterator();
    }
}
