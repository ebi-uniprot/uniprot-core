package org.uniprot.core;

import java.io.Serializable;

public final class Range implements Serializable {
    private static final long serialVersionUID = 8921458888683701817L;
    private Position start;
    private Position end;

    public Range(Integer start, Integer end) {
        this(new Position(start), new Position(end));
    }

    public Range(Integer start, Integer end, PositionModifier sModifier, PositionModifier eModifier) {
        this(new Position(start, sModifier), new Position(end, eModifier));
    }

    private Range() {

    }

    public Range(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public static Range create(Integer start, Integer end) {
        return new Range(start, end);
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Range other = (Range) obj;
        if (end == null) {
            if (other.end != null)
                return false;
        } else if (!end.equals(other.end))
            return false;
        if (start == null) {
            return other.start == null;
        } else return start.equals(other.start);
    }


}