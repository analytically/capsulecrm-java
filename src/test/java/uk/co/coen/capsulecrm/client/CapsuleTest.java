package uk.co.coen.capsulecrm.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public abstract class CapsuleTest {
    final Random random = new Random();
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            logger.info("Running test {} ...", description.getMethodName());
        }
    };

    protected CPerson person;

    @Before
    public final void createTestPerson() throws Exception {
        logger.info("Creating test person 'Dr firstName lastName' ...");

        // create a test person
        person = new CPerson();
        person.title = Title.Dr;
        person.firstName = "firstName";
        person.lastName = "lastName";
        person.jobTitle = "jobTitle";
        person.about = "about";
        person.addContact(new CEmail(null, "testperson" + random.nextInt(10) + "@testing.com"));
        person.addContact(new CPhone(null, "123456789"));
        person.addContact(new CWebsite(null, "www.test123.com"));
        person.addContact(new CAddress(null, "street", "city", "zip", "state", "United Kingdom"));
        person.save().get();

        logger.info("Test person created, id is " + person.id);
    }

    @After
    public final void deleteTestPerson() throws Exception {
        if (person != null && person.id != null) {
            logger.info("Deleting test person with ID " + person.id + " ...");

            person.delete().get();
            person.id = null;
        }
    }
}