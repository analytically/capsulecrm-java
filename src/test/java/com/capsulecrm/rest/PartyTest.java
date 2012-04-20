package com.capsulecrm.rest;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.running;

/**
 * @author Mathias Bogaert
 */
public class PartyTest extends CapsuleTest {
    private int testPersonId;

    @Test
    public void testSave() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                CPerson person = createTestPerson();
                assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(1);
                
                CPerson fetchedPerson = CPerson.listByEmailAddress(person.firstEmail().emailAddress).get().persons.iterator().next();
                assertThat(fetchedPerson.title).isEqualTo(person.title);
                assertThat(fetchedPerson.firstName).isEqualTo(person.firstName);
                assertThat(fetchedPerson.lastName).isEqualTo(person.lastName);
                assertThat(fetchedPerson.jobTitle).isEqualTo(person.jobTitle);
                assertThat(fetchedPerson.organisationId).isEqualTo(person.organisationId);
                assertThat(fetchedPerson.organisationName).isEqualTo(person.organisationName);

                deleteTestPerson();
                assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(0);
            }
        });
    }

    @Test
    public void testListByTag() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                CPerson person = createTestPerson();
                person.add(new CTag("testpersontag123"));
                assertThat(CPerson.listByTag("testpersontag123").get()).hasSize(1);

                deleteTestPerson();
                assertThat(CPerson.listByTag("testpersontag123").get()).hasSize(0);
            }
        });
    }

    private CPerson createTestPerson() {
        // create a test person
        CPerson testPerson = new CPerson();
        testPerson.title = "Mr";
        testPerson.firstName = "firstName";
        testPerson.lastName = "lastName";
        testPerson.jobTitle = "jobTitle";
        testPerson.addContact(new CEmail(null, "testperson123@testing.com"));
        testPerson.addContact(new CPhone(null, "123456789"));
        testPerson.addContact(new CWebsite(null, "www.test123.com"));
        testPerson.addContact(new CAddress(null, "street", "city", "zip", "state", "country"));
        testPerson.save().get();

        testPersonId = testPerson.id;
        return testPerson;
    }

    private void deleteTestPerson() {
        CPerson.byId(testPersonId).get().delete().get();
    }
}
