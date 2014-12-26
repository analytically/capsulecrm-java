package uk.co.coen.capsulecrm.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import java.util.Iterator;
import java.util.List;

public class CTracks implements Iterable<CTrack> {
    public List<CTrack> tracks;

    @Override
    public Iterator<CTrack> iterator() {
        return tracks != null ? tracks.iterator() : ImmutableSet.<CTrack>of().iterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tracks", tracks)
                .toString();
    }
}