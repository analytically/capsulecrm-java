package uk.co.coen.capsulecrm.client;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.Response;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.IllegalCharsetNameException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.util.concurrent.JdkFutureAdapters.listenInPoolThread;

/**
 * Integration tests or demo's that serve a greater purpose.
 * <ul>
 * <li>Skype: creates Skype social network links from phone numbers of your contacts (ignores fax numbers and deletes duplicate numbers)</li>
 * <li>Twitter: creates Twitter social network links by visiting your contacts' websites and looking for a screen name</li>
 * </ul>
 */
public class SocialNetworkLinks {
    final Logger logger = LoggerFactory.getLogger(SocialNetworkLinks.class);

    public static void main(String[] args) {
        try {
            SocialNetworkLinks links = new SocialNetworkLinks();

            links.addSkypeLinks();
            //links.addTwitterLinks();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addSkypeLinks() throws Exception {
        logger.info("addSkypeLinks - listing all parties...");

        Futures.addCallback(listenInPoolThread(CParty.listModifiedSince(new DateTime().minusWeeks(10))), new FutureCallback<CParties>() {
            @Override
            public void onSuccess(CParties parties) {
                try {

                    logger.info("Found " + parties.size + " parties, adding Skype links from phone numbers...");

                    List<ListenableFuture<Response>> deletePromises = Lists.newArrayList();

                    boolean save = false;

                    for (CParty party : parties) {
                        for (CContact contact : party.contacts) {
                            if (contact instanceof CWebsite) {
                                CWebsite website = (CWebsite) contact;

                                if (website.webService.equals(WebService.SKYPE)) {
                                    if (!CharMatcher.JAVA_DIGIT.retainFrom(website.webAddress).equals(website.webAddress)) {
                                        logger.info("Found illegal characters in skype number for party " + party + " (" + website.webAddress + "). Fixing.");
                                        website.webAddress = CharMatcher.JAVA_DIGIT.retainFrom(website.webAddress);
                                        save = true;
                                    }
                                }
                            }
                        }

                        Set<String> phoneNumbers = Sets.newHashSet();
                        Set<String> skypeNumbers = Sets.newHashSet();

                        List<CContact> contactsToRemove = Lists.newArrayList();

                        for (CContact contact : party.contacts) {
                            try {
                                if (contact instanceof CPhone) {
                                    CPhone phone = (CPhone) contact;

                                    if (!"fax".equalsIgnoreCase(phone.type)) {
                                        String phoneNumber = CharMatcher.JAVA_DIGIT.retainFrom(((CPhone) contact).phoneNumber);

                                        if (phoneNumbers.contains(phoneNumber)) {
                                            logger.info("CParty " + party + " has duplicate phone number (" + ((CPhone) contact).phoneNumber + "). Deleting.");
                                            party.deleteContact(contact); // remove dup
                                            contactsToRemove.add(contact);
                                        } else {
                                            phoneNumbers.add(CharMatcher.JAVA_DIGIT.retainFrom(((CPhone) contact).phoneNumber));
                                        }
                                    }
                                }

                                if (contact instanceof CWebsite) {
                                    if (WebService.SKYPE.equals(((CWebsite) contact).webService)) {
                                        String skypeNumber = ((CWebsite) contact).webAddress;

                                        if (skypeNumbers.contains(skypeNumber)) {
                                            logger.info("CParty " + party + " has duplicate Skype number (" + ((CWebsite) contact).webAddress + "). Deleting.");
                                            party.deleteContact(contact); // remove dup
                                            contactsToRemove.add(contact);
                                        } else {
                                            skypeNumbers.add(skypeNumber);
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        party.contacts.removeAll(contactsToRemove);

                        for (String phoneNumber : phoneNumbers) {
                            if (!skypeNumbers.contains(phoneNumber)) {
                                CWebsite website = new CWebsite(null, WebService.SKYPE, phoneNumber);
                                party.addContact(website);
                                save = true;
                            }
                        }

                        if (save) {
                            logger.info("Saving " + party);
                            party.save();

                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void removeAddressType() throws Exception {
        logger.info("removeAddressType - listing all parties...");
        Futures.addCallback(listenInPoolThread(CParty.listAll()), new FutureCallback<CParties>() {
            @Override
            public void onSuccess(CParties parties) {
                System.out.println("Found " + parties.size + " parties, removing type from address...");

                for (COrganisation organisation : parties.organisations) {
                    boolean save = false;

                    for (CContact contact : organisation.contacts) {
                        if (contact instanceof CAddress) {
                            CAddress address = (CAddress) contact;

                            if (!Strings.isNullOrEmpty(address.type)) {
                                address.type = "";
                                save = true;
                            }
                        }
                    }

                    if (save) {
                        try {
                            System.out.println("Saving " + organisation);

                            Response response = organisation.save().get();
                            if (response.getStatusCode() < 200 || response.getStatusCode() > 206) {
                                logger.warn("Failure saving party " + organisation + ", response "
                                        + response.getStatusCode() + " " + response.getStatusText()
                                        + " " + response.getResponseBody());
                            } else {
                                logger.info("Success saving party " + organisation + ", response " + response.getStatusCode() + " " + response.getStatusText());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void addTwitterLinks() throws Exception {
        logger.info("addTwitterLinks - listing all parties...");

        Futures.addCallback(listenInPoolThread(CParty.listModifiedSince(new DateTime().minusWeeks(10))), new FutureCallback<CParties>() {
            @Override
            public void onSuccess(CParties parties) {
                logger.info("Found " + parties.size + " parties, finding and adding Twitter links...");

                for (CParty party : parties) {
                    boolean hasTwitterLink = false;

                    for (CContact contact : party.contacts) {
                        if (contact instanceof CWebsite) {
                            CWebsite website = (CWebsite) contact;

                            if (WebService.TWITTER.equals(website.webService)) {
                                logger.info("Skipping " + party + " since it already has a twitter link.");

                                hasTwitterLink = true;
                                break;
                            }
                        }
                    }

                    if (!hasTwitterLink) {
                        Set<String> twitterUsers = Sets.newTreeSet(String.CASE_INSENSITIVE_ORDER);

                        for (CContact contact : party.contacts) {
                            if (contact instanceof CWebsite) {
                                CWebsite website = (CWebsite) contact;

                                if (WebService.URL.equals(website.webService) && !website.webAddress.contains("google")) {
                                    logger.info("Visiting website of " + party.getName() + " at " + website.webAddress);

                                    try {
                                        Document doc = Jsoup.connect(website.webAddress.startsWith("http://") ? website.webAddress : "http://" + website.webAddress)
                                                .timeout(15000)
                                                .ignoreHttpErrors(true)
                                                .get();

                                        Elements links = doc.select("a[href]");
                                        for (Element link : links) {
                                            String href = link.attr("href");

                                            if (!href.contains("/search")
                                                    && !href.contains("/share")
                                                    && !href.contains("/home")
                                                    && !href.contains("/intent")) {

                                                Matcher matcher = Pattern.compile("(www\\.)?twitter\\.com/(#!/)?@?([^/]*)").matcher(href);
                                                while (matcher.find()) {
                                                    String twitterUser = CharMatcher.WHITESPACE.trimFrom(href.substring(matcher.start(3), matcher.end(3)));

                                                    if (!"".equals(twitterUser) && !twitterUsers.contains(twitterUser)) {
                                                        twitterUsers.add(twitterUser);
                                                    }
                                                }
                                            }
                                        }
                                    } catch (IllegalCharsetNameException e) { // see https://github.com/jhy/jsoup/commit/2714d6be6cbe465b522a724c2796ddf74df06482#-P0
                                        logger.warn("Illegal charset name for " + website + " of " + party);
                                    } catch (IOException e) {
                                        logger.error("Unable to GET " + website + " of " + party);
                                    }
                                }
                            }
                        }

                        for (String twitterUser : twitterUsers) {
                            logger.info("Found twitter user @" + twitterUser + ", adding it to " + party.getName() + " and saving...");

                            CWebsite twitterLink = new CWebsite(null, WebService.TWITTER, twitterUser);
                            party.addContact(twitterLink);

                            try {
                                Response response = party.save().get();
                                if (response.getStatusCode() < 200 || response.getStatusCode() > 201) {
                                    logger.warn("Failure saving party " + party + ", response " + response.getStatusCode() + " " + response.getStatusText());
                                } else {
                                    logger.info("Success saving party " + party + ", response " + response.getStatusCode() + " " + response.getStatusText());
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
