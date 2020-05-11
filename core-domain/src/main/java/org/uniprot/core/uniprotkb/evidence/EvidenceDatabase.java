package org.uniprot.core.uniprotkb.evidence;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;

public final class EvidenceDatabase implements Database, Serializable {
    private static final long serialVersionUID = 799945684184000909L;
    private String name;

    // no arg constructor for JSON deserialization
    EvidenceDatabase() {}

    public EvidenceDatabase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public @Nonnull EvidenceDatabaseDetail getEvidenceDatabaseDetail() {
        return EvidenceDatabaseTypes.INSTANCE.getType(name);
    }

    public boolean isReference() {
        return "Reference".equals(name);
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
        EvidenceDatabase other = (EvidenceDatabase) obj;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }
}
