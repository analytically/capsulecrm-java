package com.zestia.rest.capsule.restapi;

import com.zestia.capsule.restapi.CHistory;
import com.zestia.capsule.restapi.CHistoryItem;
import com.zestia.capsule.restapi.CPerson;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.running;

public class HistoryTest extends CapsuleTest {
    @Test
    public void testEmptyHistory() {
        running(testServer(8080), new Runnable() {
            public void run() {
                CPerson person = createTestPerson();

                CHistory history = person.listHistory().get();
                assertThat(history.size).isEqualTo(0);

                deleteTestPerson();
                assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(0);
            }
        });
    }

    @Test
    public void testSingleItem() {
        running(testServer(8080), new Runnable() {
            public void run() {
                CPerson person = createTestPerson();
                person.add(new CHistoryItem("test history item"));

                CHistory history = person.listHistory().get();
                assertThat(history.size).isEqualTo(1);

                deleteTestPerson();
                assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(0);
            }
        });
    }
}