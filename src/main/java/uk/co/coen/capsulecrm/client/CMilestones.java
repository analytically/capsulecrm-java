package uk.co.coen.capsulecrm.client;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class CMilestones implements Iterable<CMilestone> {
    public List<CMilestone> milestones;

    public CMilestones(List<CMilestone> milestones) {
        this.milestones = milestones;
    }

    @Override
    public Iterator<CMilestone> iterator() {
        return milestones.iterator();
    }
}
