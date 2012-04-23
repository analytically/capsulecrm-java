package com.zestia.capsule.restapi;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CWebsite extends CContact {
    public String webAddress;
    public String webService = "URL";
    public String url;

    public CWebsite(String type, String webAddress) {
        super(type);
        this.webAddress = webAddress;
    }

    public CWebsite(String type, String webAddress, String webService) {
        super(type);
        this.webAddress = webAddress;
        this.webService = webService;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("webAddress", webAddress)
                .add("webService", webService)
                .add("url", url)
                .toString();
    }
}
