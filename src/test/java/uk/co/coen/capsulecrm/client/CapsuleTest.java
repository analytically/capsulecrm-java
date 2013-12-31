package uk.co.coen.capsulecrm.client;

public abstract class CapsuleTest {
    protected int testPersonId;

    protected CPerson createTestPerson() throws Exception {
        // create a test person
        CPerson testPerson = new CPerson();
        testPerson.title = Title.Dr;
        testPerson.firstName = "firstName";
        testPerson.lastName = "lastName";
        testPerson.jobTitle = "jobTitle";
        testPerson.about = "about";
        testPerson.addContact(new CEmail(null, "testperson123@testing.com"));
        testPerson.addContact(new CPhone(null, "123456789"));
        testPerson.addContact(new CWebsite(null, "www.test123.com"));
        testPerson.addContact(new CAddress(null, "street", "city", "zip", "state", "United Kingdom"));
        testPerson.save().get();

        testPersonId = testPerson.id;
        return testPerson;
    }

    protected void deleteTestPerson() throws Exception {
        CPerson.byId(testPersonId).get().delete().get();
    }
}
