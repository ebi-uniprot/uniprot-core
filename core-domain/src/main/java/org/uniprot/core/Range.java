package org.uniprot.core;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Range implements Serializable {
    private static final long serialVersionUID = 8921458888683701817L;
    private Position start;
    private Position end;

    // no arg constructor for JSON deserialization
    Range() {}

    public Range(@Nullable Integer start, @Nullable Integer end) {
        this(new Position(start), new Position(end));
    }

    public Range(
            @Nullable Integer start,
            @Nullable Integer end,
            @Nonnull PositionModifier sModifier,
            @Nonnull PositionModifier eModifier) {
        this(new Position(start, sModifier), new Position(end, eModifier));
    }

    public Range(@Nonnull Position start, @Nonnull Position end) {
        this.start = start;
        this.end = end;
    }

    public @Nonnull Position getStart() {
        return start;
    }

    public @Nonnull Position getEnd() {
        return end;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((end == null) ? 0 : end.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Range other = (Range) obj;
        if (end == null) {
            if (other.end != null) return false;
        } else if (!end.equals(other.end)) return false;
        if (start == null) {
            return other.start == null;
        } else return start.equals(other.start);
    }
}
