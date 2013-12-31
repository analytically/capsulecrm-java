package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class HistoryTest extends CapsuleTest {
    @Test
    public void testHistory() throws Exception {
        CHistory history = person.listHistory().get();
        assertThat(history.size).isEqualTo(0);

        person.add(new CHistoryItem("test history item")).get();

        history = person.listHistory().get();
        assertThat(history.size).isEqualTo(1);

        deleteTestPerson();
        assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(0);
    }
}