package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;

public class CTracks implements Iterable<CTrack> {
    public List<CTrack> tracks;

    @Override
    public Iterator<CTrack> iterator() {
        return tracks != null ? tracks.iterator() : Iterators.<CTrack>emptyIterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("tracks", tracks)
                .toString();
    }
}