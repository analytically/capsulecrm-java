capsulecrm-java
===============

Unofficial [Capsule CRM](http://capsulecrm.com/) API plugin for [PlayFramework 2.0](http://www.playframework.org/), written in Java.
Uses [the Play WS API](https://github.com/playframework/Play20/wiki/JavaWS), [XStream](http://xstream.codehaus.org/) and [Joda-Time](http://joda-time.sourceforge.net/). Still under development.

USE WITH CAUTION.

Developed for [Pearsons Associates Ltd](http://www.pearsonsltd.com/).


Configuration
-------------

In application.conf, include these parameters:

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
CParty.findAll().onRedeem(new F.Callback<CParties>() {
    @Override
    public void invoke(CParties parties) throws Throwable {
        log.info("Found " + parties.getSize() + " parties...");

        for (CParty party : parties) {
            // do something with the party
            party.setAbout("123");

            if (party instanceof COrganisation) {
                COrganisation org = (COrganisation) party;

                org.setName("blah 123");
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

Enjoy!