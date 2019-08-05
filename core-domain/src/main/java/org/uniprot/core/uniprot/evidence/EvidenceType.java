package org.uniprot.core.uniprot.evidence;

import java.io.Serializable;

import org.uniprot.core.DatabaseType;

public final class EvidenceType implements DatabaseType, Serializable {
    private static final long serialVersionUID = 799945684184000909L;
    private String name;

    private EvidenceType() {
    }

    public EvidenceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public EvidenceTypeDetail getDetail() {
        return EvidenceTypes.INSTANCE.getType(name);
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EvidenceType other = (EvidenceType) obj;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }


}
