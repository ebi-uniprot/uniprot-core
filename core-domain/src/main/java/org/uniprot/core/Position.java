package org.uniprot.core;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Position implements Comparable<Position>, Serializable {
    private static final long serialVersionUID = 894962443138290579L;
    private Integer value;
    private PositionModifier modifier;

    // no arg constructor for JSON deserialization
    private Position() {}

    public Position(@Nullable Integer value) {
        this(value, getValueModifier(value));
    }

    public Position(@Nullable Integer value, @Nonnull PositionModifier modifier) {
        this.value = value;
        if (value == null) {
            this.modifier = PositionModifier.UNKNOWN;
        } else this.modifier = modifier;
    }

    public @Nullable Integer getValue() {
        return value;
    }

    public @Nonnull PositionModifier getModifier() {
        return modifier;
    }

    @Override
    public int compareTo(@Nonnull Position o) {
        if (modifier == PositionModifier.UNKNOWN) {
            if (o.getModifier() == PositionModifier.UNKNOWN) {
                return 0;
            } else {
                return 1;
            }
        } else if (o.getModifier() == PositionModifier.UNKNOWN) {
            return -1;
        } else {
            return this.getValue().compareTo(o.getValue());
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((modifier == null) ? 0 : modifier.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        if (modifier != other.modifier) return false;
        if (modifier == PositionModifier.UNKNOWN) return true;
        return value.equals(other.value);
    }

    private static PositionModifier getValueModifier(Integer value) {
        PositionModifier modifier = PositionModifier.EXACT;
        if (value == null) {
            modifier = PositionModifier.UNKNOWN;
        } else if (value < 0) {
            modifier = PositionModifier.UNKNOWN;
        }
        return modifier;
    }
}
