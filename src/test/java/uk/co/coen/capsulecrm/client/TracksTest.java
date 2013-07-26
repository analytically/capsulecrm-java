package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.running;

public class TracksTest extends CapsuleTest {
    @Test
    public void testTracks() {
        running(testServer(8080), new Runnable() {
            public void run() {
                assertThat(CTrack.list().get().tracks.size()).isEqualTo(1);
            }
        });
    }
}
