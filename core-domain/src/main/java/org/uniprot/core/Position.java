package org.uniprot.core;

import java.io.Serializable;

public final class Position implements Comparable<Position>, Serializable {
    private static final long serialVersionUID = 894962443138290579L;
    private Integer value;
    private PositionModifier modifier;

    private Position() {

    }

    public Position(Integer value) {
        this(value, getValueModifier(value));

    }


    public Position(Integer value, PositionModifier modifier) {
        this.value = value;
        if (value == null) {
            this.modifier = PositionModifier.UNKNOWN;
        } else

            this.modifier = modifier;
    }

    public Integer getValue() {
        return value;
    }

    public PositionModifier getModifier() {
        return modifier;
    }

    @Override
    public int compareTo(Position o) {
        if (modifier == PositionModifier.UNKNOWN) {
            if (o.getModifier() == PositionModifier.UNKNOWN) {
                return -1;
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (modifier != other.modifier)
            return false;
        if(modifier ==PositionModifier.UNKNOWN )
        	return true;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }

    private static PositionModifier getValueModifier(Integer value) {
        PositionModifier modifier = PositionModifier.EXACT;
        if (value == null) {
            modifier = PositionModifier.UNKNOWN;
        } else if (value.intValue() < 0) {
            modifier = PositionModifier.UNKNOWN;
        }
        return modifier;
    }



}
