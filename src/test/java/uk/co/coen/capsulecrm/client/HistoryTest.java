package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class HistoryTest extends CapsuleTest {
    @Test
    public void testHistory() throws Exception {
        assertThat(person.listHistory().get().size).isEqualTo(0);

        CHistoryItem historyItem = new CHistoryItem("test history item");

        person.add(historyItem).get();

        CHistory history = person.listHistory().get();
        assertThat(history.size).isEqualTo(1);
        assertThat(history.iterator().next().note).isEqualTo("test history item");
        assertThat(history.iterator().next().partyId).isEqualTo(person.id);

        person.remove(historyItem).get();
        assertThat(person.listHistory().get().size).isEqualTo(0);
    }
}