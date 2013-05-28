package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CMilestone extends CIdentifiable {
    public String name;
    public String description;
    public int probability;
    public boolean complete;

    public CMilestone() {
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .add("probability", probability)
                .add("complete", complete)
                .toString();
    }
}
