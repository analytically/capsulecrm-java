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
import org.junit.Test;

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
public class SocialNetworkLinks extends CapsuleTest {
    public void addSkypeLinks() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);

        System.out.println("addSkypeLinks - listing all parties...");

        Futures.addCallback(listenInPoolThread(CParty.listAll()), new FutureCallback<CParties>() {
            @Override
            public void onSuccess(CParties parties) {
                System.out.println("Found " + parties.size + " parties, adding Skype links from phone numbers...");

                List<ListenableFuture<Response>> deletePromises = Lists.newArrayList();

                for (CParty party : parties) {
                    Set<String> phoneNumbers = Sets.newHashSet();
                    Set<String> skypeNumbers = Sets.newHashSet();

                    for (CContact contact : party.contacts) {
                        try {
                            if (contact instanceof CPhone) {
                                CPhone phone = (CPhone) contact;

                                if (!"fax".equalsIgnoreCase(phone.type)) {
                                    String phoneNumber = CharMatcher.WHITESPACE.removeFrom(((CPhone) contact).phoneNumber);

                                    if (phoneNumbers.contains(phoneNumber)) {
                                        System.out.println("CParty " + party + " has duplicate phone number (" + ((CPhone) contact).phoneNumber + "). Deleting.");
                                        deletePromises.add(listenInPoolThread(party.deleteContact(contact))); // remove dup
                                    } else {
                                        phoneNumbers.add(CharMatcher.WHITESPACE.removeFrom(((CPhone) contact).phoneNumber));
                                    }
                                }
                            }

                            if (contact instanceof CWebsite) {
                                if (WebService.SKYPE.equals(((CWebsite) contact).webService)) {
                                    String skypeNumber = ((CWebsite) contact).webAddress;

                                    if (skypeNumbers.contains(skypeNumber)) {
                                        System.out.println("CParty " + party + " has duplicate Skype number (" + ((CWebsite) contact).webAddress + "). Deleting.");
                                        deletePromises.add(listenInPoolThread(party.deleteContact(contact))); // remove dup
                                    } else {
                                        skypeNumbers.add(skypeNumber);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    boolean save = false;
                    for (String phoneNumber : phoneNumbers) {
                        if (!skypeNumbers.contains(phoneNumber)) {
                            CWebsite website = new CWebsite(null, phoneNumber, WebService.SKYPE);
                            party.addContact(website);

                            save = true;
                        }
                    }

                    if (save) {
                        try {
                            Futures.allAsList(deletePromises).get();

                            System.out.println("Saving " + party);

                            Response response = party.save().get();
                            if (response.getStatusCode() < 200 || response.getStatusCode() > 206) {
                                System.out.println("Failure saving party " + party + ", response "
                                        + response.getStatusCode() + " " + response.getStatusText()
                                        + " " + response.getResponseBody());
                            } else {
                                System.out.println("Success saving party " + party + ", response " + response.getStatusCode() + " " + response.getStatusText());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                lock.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        lock.await();
    }

    public void removeAddressType() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);

        System.out.println("removeAddressType - listing all parties...");
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
                                System.out.println("Failure saving party " + organisation + ", response "
                                        + response.getStatusCode() + " " + response.getStatusText()
                                        + " " + response.getResponseBody());
                            } else {
                                System.out.println("Success saving party " + organisation + ", response " + response.getStatusCode() + " " + response.getStatusText());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                lock.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        lock.await();
    }

    @Test
    public void addTwitterLinks() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);

        System.out.println("addTwitterLinks - listing all parties...");

        Futures.addCallback(listenInPoolThread(CParty.listModifiedSince(new DateTime().minusWeeks(10))), new FutureCallback<CParties>() {
            @Override
            public void onSuccess(CParties parties) {

                System.out.println("Found " + parties.size + " parties, finding and adding Twitter links...");
                for (CParty party : parties) {
                    boolean hasTwitterLink = false;

                    for (CContact contact : party.contacts) {
                        if (contact instanceof CWebsite) {
                            CWebsite website = (CWebsite) contact;

                            if (WebService.TWITTER.equals(website.webService)) {
                                System.out.println("Skipping " + party + " since it already has a twitter link.");

                                hasTwitterLink = true;
                            }
                        }
                    }

                    if (!hasTwitterLink) {
                        Set<String> twitterUsers = Sets.newTreeSet(String.CASE_INSENSITIVE_ORDER);

                        for (CContact contact : party.contacts) {
                            if (contact instanceof CWebsite) {
                                CWebsite website = (CWebsite) contact;

                                if (WebService.URL.equals(website.webService) && !website.url.contains("google")) {
                                    System.out.println("Visiting website of " + party.getName() + " at " + website.url);

                                    try {
                                        Document doc = Jsoup.connect(website.url)
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

                                                    if (!"".equals(twitterUser)) {
                                                        twitterUsers.add(twitterUser);
                                                    }
                                                }
                                            }
                                        }
                                    } catch (IllegalCharsetNameException e) { // see https://github.com/jhy/jsoup/commit/2714d6be6cbe465b522a724c2796ddf74df06482#-P0
                                        System.out.println("Illegal charset name for " + website + " of " + party);
                                    } catch (IOException e) {
                                        System.out.println("Unable to GET " + website + " of " + party);
                                    }
                                }
                            }
                        }

                        for (String twitterUser : twitterUsers) {
                            System.out.println("Found twitter user @" + twitterUser + ", adding it to " + party.getName() + " and saving...");

                            CWebsite twitterLink = new CWebsite(null, twitterUser, WebService.TWITTER);
                            party.addContact(twitterLink);

                            try {
                                Response response = party.save().get();
                                if (response.getStatusCode() < 200 || response.getStatusCode() > 201) {
                                    System.out.println("Failure saving party " + party + ", response " + response.getStatusCode() + " " + response.getStatusText());
                                } else {
                                    System.out.println("Success saving party " + party + ", response " + response.getStatusCode() + " " + response.getStatusText());
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }

                lock.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        lock.await();
    }
}
