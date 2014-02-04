package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TagsTest extends CapsuleTest {
    @Test
    public void testTags() throws Exception {
        assertThat(person.listTags().get().size).isEqualTo(0);

        CTag tag = new CTag("test");
        person.add(tag).get();
        assertThat(person.listTags().get().size).isEqualTo(1);

        person.addTag("test2").get();
        assertThat(person.listTags().get().size).isEqualTo(2);

        person.removeTag("test2").get();
        assertThat(person.listTags().get().size).isEqualTo(1);

        person.remove(tag).get();
        assertThat(person.listTags().get().size).isEqualTo(0);
    }
}