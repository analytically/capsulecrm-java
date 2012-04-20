capsulecrm-java
===============

Unofficial and unsupported [Capsule CRM](http://capsulecrm.com/) API plugin for [PlayFramework 2.0](http://www.playframework.org/).
Uses [the Play WS API](https://github.com/playframework/Play20/wiki/JavaWS), [XStream](http://xstream.codehaus.org/) and [Joda-Time](http://joda-time.sourceforge.net/). Under development.

USE WITH CAUTION!

See [Capsule API](http://capsulecrm.com/help/page/api_gettingstarted) for more information on Capsule CRM's REST API.
Developed for [Pearsons Associates Ltd](http://www.pearsonsltd.com/). Follow [@analytically](http://twitter.com/analytically) on Twitter for updates.

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

Usage Examples
--------------

Fetch all parties, change something and save:

```
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
            if (response.getStatus() != 200 || response.getStatus() != 201) {
                // in Play Framework 2.1, use response.getStatusText() here
                log.info("Failure saving party " + party + ", response " + response.getStatus());
            }
        }
    }
});
```

Add a tag to a party:

```
party.add(new CTag("iamatag")).get();
```

Add a note to a party:

```
party.add(new CHistoryItem("hello I'm a note"));
```

Add a task to a party:

```
party.add(new CTask("do this in two days", DateTime.now().plus(2)));
```

License
-------

Licensed under the [WTFPL](http://en.wikipedia.org/wiki/WTFPL).

Todo
----

* Test, test, test...
* Package and publish to TypeSafe repository?

Enjoy!