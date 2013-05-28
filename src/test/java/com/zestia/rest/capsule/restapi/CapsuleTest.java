package com.zestia.rest.capsule.restapi;

import com.google.common.collect.ImmutableMap;
import com.zestia.capsule.restapi.*;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.TestServer;

import java.util.Map;

/**
 * @author Mathias Bogaert
 */
public abstract class CapsuleTest {
    protected int testPersonId;

    public FakeApplication fakeApplication() {
        Map<String, String> config = ImmutableMap.of(
                "capsulecrm.url", "https://YOUR-DOMAIN.capsulecrm.com",
                "capsulecrm.token", "YOUR-TOKEN",
                "ehcacheplugin", "disabled",
                "ws.timeout", "30s",
                "promise.akka.actor.typed.timeout", "30s");

        return Helpers.fakeApplication(config);
    }

    public TestServer testServer(int port) {
        return new TestServer(port, fakeApplication());
    }

    protected CPerson createTestPerson() {
        // create a test person
        CPerson testPerson = new CPerson();
        testPerson.title = Title.Dr;
        testPerson.firstName = "firstName";
        testPerson.lastName = "lastName";
        testPerson.jobTitle = "jobTitle";
        testPerson.addContact(new CEmail(null, "testperson123@testing.com"));
        testPerson.addContact(new CPhone(null, "123456789"));
        testPerson.addContact(new CWebsite(null, "www.test123.com"));
        testPerson.addContact(new CAddress(null, "street", "city", "zip", "state", "United Kingdom"));
        testPerson.save().get();

        testPersonId = testPerson.id;
        return testPerson;
    }

    protected void deleteTestPerson() {
        CPerson.byId(testPersonId).get().delete().get();
    }
}
