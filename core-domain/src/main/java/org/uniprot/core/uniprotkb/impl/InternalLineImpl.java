package org.uniprot.core.uniprotkb.impl;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniprotkb.InternalLine;
import org.uniprot.core.uniprotkb.InternalLineType;

public class InternalLineImpl extends ValueImpl implements InternalLine {
    private static final long serialVersionUID = -5550083478261876596L;
    private InternalLineType type;

    // no arg constructor for JSON deserialization
    InternalLineImpl() {
        super(null);
    }

    InternalLineImpl(InternalLineType type, String value) {
        super(value);
        this.type = type;
    }

    @Override
    public InternalLineType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        InternalLineImpl other = (InternalLineImpl) obj;
        return type == other.type;
    }
}
