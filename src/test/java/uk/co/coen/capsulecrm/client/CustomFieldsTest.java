package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CustomFieldsTest extends CapsuleTest {
    @Test
    public void testCustomFields() throws Exception {
        assertThat(person.listCustomFields().get().size).isEqualTo(0);
        assertThat(person.listCustomFieldDefinitions().get().customFieldDefinitions.size()).isEqualTo(5);

        person.add(new CCustomField(null, "TextField", "testText")).get();
        assertThat(person.listCustomFields().get().size).isEqualTo(1);

        person.add(new CCustomField(null, "NumberField", 123)).get();
        assertThat(person.listCustomFields().get().size).isEqualTo(2);

        person.remove(new CCustomField(null, "TextField", "testText")).get();
        person.remove(new CCustomField(null, "NumberField", 456)).get();
        assertThat(person.listCustomFields().get().size).isEqualTo(0);
    }
}
