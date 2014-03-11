package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

public class CEmail extends CContact {
    public String emailAddress;

    public CEmail() {
    }

    public CEmail(String emailAddress) {
        this.emailAddress = emailAddress;
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
