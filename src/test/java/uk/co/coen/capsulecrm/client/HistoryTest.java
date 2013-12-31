package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class HistoryTest extends CapsuleTest {
    @Test
    public void testEmptyHistory() throws Exception {
        CHistory history = person.listHistory().get();
        assertThat(history.size).isEqualTo(0);

        deleteTestPerson();
        assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(0);
    }

    @Test
    public void testSingleItem() throws Exception {
        person.add(new CHistoryItem("test history item"));

        CHistory history = person.listHistory().get();
        assertThat(history.size).isEqualTo(1);

        deleteTestPerson();
        assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(0);
    }
}