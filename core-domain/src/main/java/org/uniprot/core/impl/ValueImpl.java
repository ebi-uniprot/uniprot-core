package org.uniprot.core.impl;

import org.uniprot.core.Value;
import org.uniprot.core.util.Utils;

public class ValueImpl implements Value {
    private static final long serialVersionUID = 1046531902098372084L;
    private String value;

    private ValueImpl() {

    }

    public ValueImpl(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean hasValue() {
        return Utils.notNullOrEmpty(this.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        ValueImpl other = (ValueImpl) obj;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }
}
