package org.uniprot.core;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    int start;
    int end;

    // no arg constructor for JSON deserialization
    private Location() {}

    public Location(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Location other = (Location) obj;
        return Objects.equals(start, other.start) && Objects.equals(end, other.end);
    }
}
