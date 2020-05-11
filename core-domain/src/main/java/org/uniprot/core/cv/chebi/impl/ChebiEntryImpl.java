package org.uniprot.core.cv.chebi.impl;

import org.uniprot.core.cv.chebi.ChebiEntry;

import java.util.Objects;

/**
 * Created 15/03/19
 *
 * @author Edd
 */
public class ChebiEntryImpl implements ChebiEntry {

    private static final long serialVersionUID = 7578992410951294303L;
    private final String id;
    private final String inchiKey;
    private final String name;

    ChebiEntryImpl() {
        this(null, null, null);
    }

    ChebiEntryImpl(String id, String name, String inchiKey) {
        this.id = id;
        this.name = name;
        this.inchiKey = inchiKey;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInchiKey() {
        return inchiKey;
    }

    @Override
    public String toString() {
        return "ChebiEntryImpl{"
                + "id='"
                + id
                + '\''
                + ", inchiKey='"
                + inchiKey
                + '\''
                + ", name='"
                + name
                + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChebiEntryImpl that = (ChebiEntryImpl) o;
        return Objects.equals(id, that.id)
                && Objects.equals(inchiKey, that.inchiKey)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inchiKey, name);
    }
}
