package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

/**
 * @author Mathias Bogaert
 */
public class CWebsite extends CContact {
    public String webAddress;
    public WebService webService = WebService.URL;
    public String url;

    public CWebsite() {
    }

    public CWebsite(String type, String webAddress) {
        super(type);
        this.webAddress = webAddress;
    }

    /**
     * Creates a website.
     *
     * @param type the type
     * @param webAddress the address
     * @param webService the service, defaults to 'URL'
     */
    public CWebsite(String type, String webAddress, WebService webService) {
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
