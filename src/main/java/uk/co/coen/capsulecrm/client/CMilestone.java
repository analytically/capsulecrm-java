package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;

public class CMilestone extends CIdentifiable {
    public String name;
    public String description;
    public int probability;
    public boolean complete;

    public CMilestone() {
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .add("probability", probability)
                .add("complete", complete)
                .toString();
    }
}
