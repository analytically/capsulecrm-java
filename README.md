capsulecrm-java [![Build Status](https://travis-ci.org/coenrecruitment/capsulecrm-java.png)](https://travis-ci.org/coenrecruitment/capsulecrm-java)
===============

Unofficial [Capsule CRM API](http://developer.capsulecrm.com/) Java Client.
Depends on [the Play WS API](http://www.playframework.com/documentation/2.1.1/JavaWS), [XStream](http://xstream.codehaus.org/) and [Joda-Time](http://joda-time.sourceforge.net/).

Development sponsored by [Coen Recruitment](http://www.coen.co.uk). Follow [@analytically](http://twitter.com/analytically) for updates.

### Requirements

Java 6 or later. A Capsule CRM account and token.

### Using with Maven

Add this dependency to your project's POM file:

```xml
<dependency>
  <groupId>uk.co.coen</groupId>
  <artifactId>capsulecrm-java</artifactId>
  <version>1.0.3</version>
</dependency>
```

### Using with SBT

Add this dependency to your project's `build.sbt` or `project/Build.scala` file:

```scala
libraryDependencies += "uk.co.coen" % "capsulecrm-java" % "1.0.3"
```

### Configuration

In your Play! application's `conf/application.conf`, add your URL and Capsule CRM API token.
Capsule CRM users can find their API token by visiting `My Preferences` via their username menu in the Capsule navigation bar.

```ruby
capsulecrm.url="https://<yourdomain>.capsulecrm.com"
capsulecrm.token="<your token here>"
```

When fetching large amounts of parties, make sure you set the timeout high enough:

```ruby
# Connection timeout in ms (default 120000)
ws.timeout=400000
```

### Google Custom Search Engine for your Capsule CRM contact's websites

If you need a [Google Custom Search](http://www.google.co.uk/cse/) searching all websites of your contacts, see the `sample`
directory for a Play! application hosting custom search annotations files. Point Google Custom Search to a server hosting this application.

* In `sample/conf/application.conf`, change `capsulecrm.url` and `capsulecrm.token` to your Capsule CRM account details
* In `sample/conf/application.conf`, change `gcs.label` to your Custom Search Engine label.
* Run the application by using the `play run` command, see [here](http://www.playframework.com/documentation/2.0/PlayConsole) for more information.
* Under `Control panel > Advanced > Add annotations feed`, add `http://yourhost/cse/persons` for
all person annotations, or `http://yourhost/cse/organisations` for all organisation annotations.

See [Hosting the Annotation Files Yourself](https://developers.google.com/custom-search/docs/annotations#host) for more details.

### Usage Examples

Start by importing the client package and the necessary Play! classes:

```java
import uk.co.coen.capsulecrm.client.*
import play.libs.F;
import play.libs.WS;
```

Fetch all parties, change something and save - [asynchronous](http://www.playframework.com/documentation/2.1.1/JavaAsync):

```java
// perform callback when the list of parties is 'redeemed'
CParty.listAll().onRedeem(new F.Callback<CParties>() {
    @Override
    public void invoke(CParties parties) throws Throwable {

        for (CParty party : parties) {
            party.about = "...";

            if (party instanceof COrganisation) {
                COrganisation org = (COrganisation) party;

                // if it's an organisation, change it's name
                org.name = "...";
            }

            // save changes - blocking
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

Click [here](https://github.com/coenrecruitment/capsulecrm-java/tree/master/src/test/java/uk/co/coen/capsulecrm/client) for more examples.

### License

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

Copyright 2013 Coen Recruitment Ltd - www.coen.co.uk.