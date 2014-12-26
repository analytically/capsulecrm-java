capsulecrm-java [![Build Status](https://travis-ci.org/analytically/capsulecrm-java.svg?branch=master)](https://travis-ci.org/analytically/capsulecrm-java)
===============

Unofficial [Capsule CRM API](http://developer.capsulecrm.com/) Java Client.

Depends on [Async Http Client](https://github.com/AsyncHttpClient/async-http-client),
[Google Guava](https://code.google.com/p/guava-libraries/), [XStream](http://xstream.codehaus.org/) and
[Joda-Time](http://www.joda.org/joda-time/). Development sponsored by [Coen Recruitment](http://www.coen.co.uk).
Follow [@analytically](http://twitter.com/analytically) for updates.

### Requirements

Java 7 or later. A Capsule CRM account and token.

### Using with Maven

Add this dependency to your project's POM file:

```xml
<dependency>
  <groupId>uk.co.coen</groupId>
  <artifactId>capsulecrm-java</artifactId>
  <version>1.2.6</version>
</dependency>
```

### Using with SBT

Add this dependency to your project's `build.sbt` or `project/Build.scala` file:

```scala
libraryDependencies += "uk.co.coen" % "capsulecrm-java" % "1.2.6"
```

### Configuration

Add an `application.conf` property file to your application's classpath with the Capsule CRM url and token.
Capsule CRM users can find their API token by visiting `My Preferences` via their username menu in the Capsule navigation bar.

```ruby
capsulecrm.url="https://<yourdomain>.capsulecrm.com"
capsulecrm.token="<your token here>"
```

### Google Custom Search Engine for your Capsule CRM contact's websites

If you need a [Google Custom Search](http://www.google.co.uk/cse/) searching all websites of your contacts, see the `gcse`
directory for a Play Framework application hosting custom search annotations files. Point Google Custom Search to a server hosting this application.

* In `gcse/conf/application.conf`, change `capsulecrm.url` and `capsulecrm.token` to your Capsule CRM account details
* In `gcse/conf/application.conf`, change `gcs.label` to your Custom Search Engine label.
* Run the application by using the `play run` command, see [here](http://www.playframework.com/documentation/2.0/PlayConsole) for more information.
* Under `Control panel > Advanced > Add annotations feed`, add `http://yourhost/cse/persons` for
all person annotations, or `http://yourhost/cse/organisations` for all organisation annotations.

See [Hosting the Annotation Files Yourself](https://developers.google.com/custom-search/docs/annotations#host) for more details.

### Usage Examples

Start by importing the client package and the necessary classes:

```java
import java.util.concurrent.Future;
import uk.co.coen.capsulecrm.client.*

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import static com.google.common.util.concurrent.JdkFutureAdapters.listenInPoolThread;
```

Fetch all parties, change something and save - [asynchronous](http://sonatype.github.io/async-http-client/request.html):

```java
Futures.addCallback(listenInPoolThread(CParty.listAll()), new FutureCallback<CParties>() {
    @Override
    public void onSuccess(CParties parties) {
        for (CParty party : parties) {
            party.about = "...";

            if (party instanceof COrganisation) {
                COrganisation org = (COrganisation) party;

                // if it's an organisation, change it's name
                org.name = "...";
            }

            // save changes - blocking
            Response response = party.save().get();
            if (response.getStatusCode() < 200 || response.getStatusCode() > 206) {
                log.info("Failure saving party " + party + ", response " + response.getStatusCode() + " " + response.getStatusText());
            }
        }

    }
});
```

Add a tag to a party:

```java
party.add(new CTag("iamatag"));
```

Add a note to a party:

```java
party.add(new CHistoryItem("hello I'm a note"));
```

Add a task to a party:

```java
party.add(new CTask("do this in two days", DateTime.now().plus(2)));
```

Click [here](https://github.com/analytically/capsulecrm-java/tree/master/src/test/java/uk/co/coen/capsulecrm/client) for more examples.

### License

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

Copyright 2011-2015 [Mathias Bogaert](mailto:mathias.bogaert@gmail.com).
