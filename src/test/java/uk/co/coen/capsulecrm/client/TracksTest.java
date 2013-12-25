package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TracksTest extends CapsuleTest {
    @Test
    public void testTracks() throws Exception {
        assertThat(CTrack.list().get().tracks.size()).isEqualTo(1);
    }
}
