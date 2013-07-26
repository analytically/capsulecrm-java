package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CTracks implements Iterable<CTrack> {
    public List<CTrack> tracks;

    @Override
    public Iterator<CTrack> iterator() {
        return tracks != null ? tracks.iterator() : new ArrayList<CTrack>().iterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("tracks", tracks)
                .toString();
    }
}