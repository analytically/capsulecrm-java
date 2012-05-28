package com.zestia.rest.capsule.restapi;

import com.google.common.collect.ImmutableMap;
import com.zestia.capsule.restapi.*;
import play.mvc.Http;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathias Bogaert
 */
public abstract class CapsuleTest {
    protected int testPersonId;

    public FakeApplication fakeApplication() {
        Map<String, String> config = ImmutableMap.of(
                "capsulecrm.url", "https://YOURS.capsulecrm.com",
                "capsulecrm.token", "YOUR-TOKEN",
                "ws.timeout", "30s",
                "promise.akka.actor.typed.timeout", "30s");

        // needed since https://github.com/playframework/Play20/commit/e8ff62bb77f4856a02ab39519d519322a4bcd2db
        Http.Context context = new Http.Context(null, new HashMap<String, String>(), new HashMap<String, String>());
        Http.Context.current.set(context);

        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(), config, new ArrayList<String>());
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
