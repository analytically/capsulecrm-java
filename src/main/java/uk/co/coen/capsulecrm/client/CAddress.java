package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;

public class CAddress extends CContact {
    public String street;
    public String city;
    public String zip;
    public String state;
    public String country;

    public CAddress() {
    }

    public CAddress(String type, String street, String city, String zip, String state, String country) {
        super(type);
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.state = state;
        this.country = country;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("street", street)
                .add("city", city)
                .add("zip", zip)
                .add("state", state)
                .add("country", country)
                .toString();
    }
}
