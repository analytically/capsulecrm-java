package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CPerson extends CParty {
    public Title title;
    public String firstName;
    public String lastName;
    public String jobTitle;
    public Integer organisationId;
    public String organisationName;

    @Override
    protected String writeContextPath() {
        return "/person";
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("title", title)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("jobTitle", jobTitle)
                .add("organisationId", organisationId)
                .add("organisationName", organisationName)
                .add("name", getName())
                .toString();
    }
}
