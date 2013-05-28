package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CEmail extends CContact {
    public String emailAddress;

    public CEmail() {
    }

    public CEmail(String type, String emailAddress) {
        super(type);
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("emailAddress", emailAddress)
                .toString();
    }
}
