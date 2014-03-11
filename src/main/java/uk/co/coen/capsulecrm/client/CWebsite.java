package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

import java.net.MalformedURLException;
import java.net.URL;

public class CWebsite extends CContact {
    public WebService webService;
    public String webAddress;
    public String url;

    public CWebsite() {
    }

    public CWebsite(String type, String webAddress) {
        super(type);
        this.webService = WebService.URL;
        this.webAddress = webAddress;
    }

    /**
     * Creates a website.
     *
     * @param type       the type
     * @param webAddress the address
     * @param webService the service, defaults to 'URL'
     */
    public CWebsite(String type, WebService webService, String webAddress) {
        super(type);
        this.webService = webService;
        this.webAddress = webAddress;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("webAddress", webAddress)
                .add("webService", webService)
                .add("url", url)
                .toString();
    }

    public URL toURL() throws MalformedURLException {
        return new URL(webAddress);
    }
}