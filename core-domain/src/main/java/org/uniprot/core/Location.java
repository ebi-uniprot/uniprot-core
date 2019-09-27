package org.uniprot.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class Location implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    int start;
    int end;

    protected Location() {}

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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Location other = (Location) obj;
        return Objects.equals(start, other.start) && Objects.equals(end, other.end);
    }
}
