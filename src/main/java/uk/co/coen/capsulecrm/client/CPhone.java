package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CPhone extends CContact {
    public String phoneNumber;

    public CPhone() {
    }

    public CPhone(String type, String phoneNumber) {
        super(type);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("phoneNumber", phoneNumber)
                .toString();
    }
}
