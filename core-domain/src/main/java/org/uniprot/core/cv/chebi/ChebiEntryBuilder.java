package org.uniprot.core.cv.chebi;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;

/**
 * Created 07/06/19
 *
 * @author Edd
 */
public class ChebiEntryBuilder implements Builder<ChebiEntry> {
    private String id;
    private String name;
    private String inchiKey;

    public ChebiEntryBuilder id(String id) {
        this.id = id;
        return this;
    }

    public ChebiEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ChebiEntryBuilder inchiKey(String inchiKey) {
        this.inchiKey = inchiKey;
        return this;
    }

    public @Nonnull ChebiEntry build() {
        return new ChebiEntryImpl(id, name, inchiKey);
    }
}
