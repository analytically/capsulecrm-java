capsulecrm-java
===============

Unofficial Capsule CRM API plugin for PlayFramework 2.0, written in Java. Still under development. USE WITH CAUTION.

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
                log.info("Failure saving party " + party + ", response " + response.getStatus()); // in Play 2.1, use response.getStatusText()
            }
        }
    }
});
```

Add a tag to a party:

```
party.add(new CTag("iamatag")).get();
```

Enjoy!