capsulecrm-java
===============

Unofficial [Capsule CRM](http://capsulecrm.com/) API Java Client.
Depends on [the Play WS API](http://www.playframework.com/documentation/2.0/JavaWS), [XStream](http://xstream.codehaus.org/) and [Joda-Time](http://joda-time.sourceforge.net/).

Development sponsored by [Coen Recruitment](http://www.coen.co.uk). Follow [@analytically](http://twitter.com/analytically) on Twitter for updates.

[![Build Status](https://travis-ci.org/coenrecruitment/capsulecrm-java.png)](https://travis-ci.org/coenrecruitment/capsulecrm-java)

Capsule CRM
-----------

See [Capsule API](http://developer.capsulecrm.com/) for more information on Capsule CRM's REST API.

Configuration
-------------

In `application.conf`, add your URL and Capsule CRM API token. Users can find their API token by visiting My Preferences via
their username menu in the Capsule navigation bar.

```
capsulecrm.url="https://<yourdomain>.capsulecrm.com"
capsulecrm.token="<your token here>"
```

When fetching large amounts of parties, make sure you set the timeout high enough:

```
# Connection timeout in ms (default 120000)
ws.timeout=400000
```

Google Custom Search
--------------------

If you need a [Google Custom Search](http://www.google.co.uk/cse/) searching all websites of your contacts, see the `sample` directory. Point Google Custom Search
to a server hosting this application.

* In `application.conf`, change `gcs.label` to your Custom Search Engine label.
* Under `Control panel > Advanced > Add annotations feed`, add `http://yourhost/cse/persons` for all person annotations, or `http://yourhost/cse/organisations` for all organisation annotations.

See [Hosting the Annotation Files Yourself](https://developers.google.com/custom-search/docs/annotations#host) for more details.

Usage Examples
--------------

Fetch all parties, change something and save:

```java
CParty.listAll().onRedeem(new F.Callback<CParties>() {
    @Override
    public void invoke(CParties parties) throws Throwable {
        log.info("Found " + parties.size + " parties...");

        for (CParty party : parties) {
            // do something with the party
            party.about = "123";

            if (party instanceof COrganisation) {
                COrganisation org = (COrganisation) party;

                org.name = "blah 123";
            }

            // save changes
            WS.Response response = party.save().get();
            if (response.getStatus() < 200 || response.getStatus() > 206) {
                log.info("Failure saving party " + party + ", response " + response.getStatus() + " " + response.getStatusText());
            }
        }
    }
});
```

Add a tag to a party:

```java
party.add(new CTag("iamatag")).get();
```

Add a note to a party:

```java
party.add(new CHistoryItem("hello I'm a note"));
```

Add a task to a party:

```java
party.add(new CTask("do this in two days", DateTime.now().plus(2)));
```

Click [here](https://github.com/coenrecruitment/capsulecrm-java/tree/master/src/test/java/com/zestia/rest/capsule/restapi) for more examples.

License
-------

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).