package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CustomFieldsTest extends CapsuleTest {
    @Test
    public void testCustomFields() throws Exception {
        assertThat(person.listCustomFields().get().size).isEqualTo(0);

        person.add(new CCustomField(null, "Reference", "testText")).get();
        assertThat(person.listCustomFields().get().size).isEqualTo(1);

        person.remove(new CCustomField(null, "Reference", "testText")).get();
        assertThat(person.listCustomFields().get().size).isEqualTo(0);
    }
}
