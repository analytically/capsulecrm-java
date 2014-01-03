package uk.co.coen.capsulecrm.client;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TasksTest extends CapsuleTest {
    @Test
    public void testTasks() throws Exception {
        assertThat(person.listTasks().get().size).isEqualTo(0);

        CTask task = new CTask("test", DateTime.now(), true);
        person.add(task).get();
        assertThat(person.listTasks().get().size).isEqualTo(1);

        task.complete().get();
        assertThat(task.status).isEqualTo(TaskStatus.COMPLETED);
        assertThat(person.listTasks().get().size).isEqualTo(0);
        assertThat(person.listTasks(TaskStatus.COMPLETED).get().size).isEqualTo(1);

        task.reopen().get();
        assertThat(task.status).isEqualTo(TaskStatus.OPEN);
        assertThat(person.listTasks().get().size).isEqualTo(1);

        task.delete().get();
        assertThat(person.listTasks().get().size).isEqualTo(0);
    }


}
