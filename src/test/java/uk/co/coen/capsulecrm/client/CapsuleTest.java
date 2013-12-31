package uk.co.coen.capsulecrm.client;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CapsuleTest {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected CPerson person;

    @Before
    public final void createTestPerson() throws Exception {
        logger.info("Creating test person...");

        // create a test person
        person = new CPerson();
        person.title = Title.Dr;
        person.firstName = "firstName";
        person.lastName = "lastName";
        person.jobTitle = "jobTitle";
        person.about = "about";
        person.addContact(new CEmail(null, "testperson123@testing.com"));
        person.addContact(new CPhone(null, "123456789"));
        person.addContact(new CWebsite(null, "www.test123.com"));
        person.addContact(new CAddress(null, "street", "city", "zip", "state", "United Kingdom"));
        person.save().get();

        logger.info("Test person created, id is " + person.id);
    }

    @After
    public final void deleteTestPerson() throws Exception {
        if (person.id != null) {
            logger.info("Deleting test person...");

            CPerson.byId(person.id).get().delete().get();
            person.id = null;
        }
    }
}