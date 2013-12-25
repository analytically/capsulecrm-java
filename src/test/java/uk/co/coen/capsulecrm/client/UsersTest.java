package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class UsersTest extends CapsuleTest {
    @Test
    public void testUsers() throws Exception {
        assertThat(CUser.list().get().users.size()).isEqualTo(3);
    }
}
