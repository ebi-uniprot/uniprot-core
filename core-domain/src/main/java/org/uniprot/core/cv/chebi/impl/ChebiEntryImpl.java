package org.uniprot.core.cv.chebi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.cv.chebi.ChebiEntry;

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
    private final List<ChebiEntry> relatedIds;

    ChebiEntryImpl() {
        this(null, null, null, new ArrayList<>());
    }

    ChebiEntryImpl(String id, String name, String inchiKey, List<ChebiEntry> relatedIds) {
        this.id = id;
        this.name = name;
        this.inchiKey = inchiKey;
        this.relatedIds = relatedIds;
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
    public List<ChebiEntry> getRelatedIds() {
        return relatedIds;
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
                + "', relatedIds=" + relatedIds
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
