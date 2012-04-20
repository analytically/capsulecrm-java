package com.capsulecrm.rest;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CWebsite extends CContact {
    private String webAddress;
    private String webService = "URL";
    private String url;

    public CWebsite(String type, String webAddress) {
        super(type);
        this.webAddress = webAddress;
    }

    public CWebsite(String type, String webAddress, String webService, String url) {
        super(type);
        this.webAddress = webAddress;
        this.webService = webService;
        this.url = url;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getWebService() {
        return webService;
    }

    public void setWebService(String webService) {
        this.webService = webService;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
