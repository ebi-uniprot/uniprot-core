package org.uniprot.core.impl;

import org.uniprot.core.DatabaseType;

public final class DefaultDatabaseType implements DatabaseType {
    private String name;

    // no arg constructor for JSON deserialization
    DefaultDatabaseType() {}

    public DefaultDatabaseType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DefaultDatabaseType other = (DefaultDatabaseType) obj;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }
}
