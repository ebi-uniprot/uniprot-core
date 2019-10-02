package org.uniprot.core;

import java.io.Serializable;

import org.uniprot.core.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Property implements Pair<String, String>, Comparable<Property>, Serializable {
    private static final long serialVersionUID = 2383267527069888292L;
    private String key;
    private String value;

    // Need by object mapper json conversions
    private Property() {}

    public Property(@Nullable String key, @Nullable String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Property other = (Property) obj;
        if (key == null) {
            if (other.key != null) return false;
        } else if (!key.equals(other.key)) return false;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }

    @Override
    public int compareTo(@Nonnull Property o) {
        return this.key.compareTo(o.getKey());
    }
}
