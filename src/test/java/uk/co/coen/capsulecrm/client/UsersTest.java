package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.running;

public class UsersTest extends CapsuleTest {
    @Test
    public void testUsers() {
        running(testServer(8080), new Runnable() {
            public void run() {
                assertThat(CUser.list().get().users.size()).isEqualTo(3);
            }
        });
    }
}
