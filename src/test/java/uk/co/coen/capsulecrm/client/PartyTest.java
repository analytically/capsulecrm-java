package uk.co.coen.capsulecrm.client;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.running;

public class PartyTest extends CapsuleTest {
    @Test
    public void testSaveDelete() {
        running(testServer(8080), new Runnable() {
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

                assertThat(fetchedPerson.about).isEqualTo(person.about);

                assertThat(fetchedPerson.createdOn).isNotNull();
                assertThat(fetchedPerson.pictureURL).isNotNull();

                assertThat(fetchedPerson.contacts).hasSize(4);
                assertThat(fetchedPerson.firstAddress().street).isEqualTo(person.firstAddress().street);
                assertThat(fetchedPerson.firstAddress().city).isEqualTo(person.firstAddress().city);
                assertThat(fetchedPerson.firstAddress().zip).isEqualTo(person.firstAddress().zip);
                assertThat(fetchedPerson.firstAddress().state).isEqualTo(person.firstAddress().state);
                assertThat(fetchedPerson.firstAddress().country).isEqualTo(person.firstAddress().country);

                assertThat(fetchedPerson.firstEmail().emailAddress).isEqualTo(person.firstEmail().emailAddress);

                assertThat(fetchedPerson.firstPhone().phoneNumber).isEqualTo(person.firstPhone().phoneNumber);

                assertThat(fetchedPerson.firstWebsite().webAddress).isEqualTo(person.firstWebsite().webAddress);
                assertThat(fetchedPerson.firstWebsite().webService).isEqualTo(person.firstWebsite().webService);

                deleteTestPerson();
                assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(0);
            }
        });
    }

    @Test
    public void testListByTag() {
        running(testServer(8080), new Runnable() {
            public void run() {
                CPerson person = createTestPerson();
                person.add(new CTag("testpersontag123")).get();
                assertThat(CPerson.listByTag("testpersontag123").get()).hasSize(1);

                person.remove(new CTag("testpersontag123")).get();
                assertThat(CPerson.listByTag("testpersontag123").get()).hasSize(0);

                deleteTestPerson();
                assertThat(CPerson.listByTag("testpersontag123").get()).hasSize(0);
            }
        });
    }

    @Test
    public void testNotes() {
        running(testServer(8080), new Runnable() {
            public void run() {
                CPerson person = createTestPerson();
                DateTime entryDateTime = new DateTime().minusDays(2);
                person.add(new CHistoryItem("test note", entryDateTime)).get();

                assertThat(person.listHistory().get()).hasSize(1);

                CHistoryItem historyItem = person.listHistory().get().iterator().next();
                assertThat(historyItem.note).isEqualTo("test note");
                assertThat(historyItem.entryDate).isEqualTo(entryDateTime.withMillisOfSecond(0));

                deleteTestPerson();
                assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(0);
            }
        });
    }

    @Test
    public void testTasks() {
        running(testServer(8080), new Runnable() {
            public void run() {
                CPerson person = createTestPerson();
                DateTime dueDateTime = new DateTime().plus(Days.days(2));
                person.add(new CTask("test task", dueDateTime, true)).get();

                assertThat(person.listTasks().get()).hasSize(1);

                CTask task = person.listTasks().get().iterator().next();
                assertThat(task.description).isEqualTo("test task");
                assertThat(task.dueDateTime).isEqualTo(dueDateTime.withSecondOfMinute(0).withMillisOfSecond(0));
                assertThat(task.partyId).isEqualTo(person.id);

                task.complete().get();

                task.delete().get();
                assertThat(person.listTasks().get()).hasSize(0);

                deleteTestPerson();
                assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(0);
            }
        });
    }
}
