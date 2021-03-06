package org.uniprot.core.uniprotkb.description.impl;

import org.uniprot.core.uniprotkb.description.Flag;
import org.uniprot.core.uniprotkb.description.FlagType;

public class FlagImpl implements Flag {
    private static final long serialVersionUID = -328562072578231072L;
    private FlagType type;

    // no arg constructor for JSON deserialization
    FlagImpl() {}

    FlagImpl(FlagType type) {
        this.type = type;
    }

    @Override
    public FlagType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FlagImpl other = (FlagImpl) obj;
        return type == other.type;
    }
}
