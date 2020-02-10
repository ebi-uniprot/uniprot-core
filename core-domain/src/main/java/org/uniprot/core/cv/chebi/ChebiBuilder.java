package org.uniprot.core.cv.chebi;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;

/**
 * Created 07/06/19
 *
 * @author Edd
 */
public class ChebiBuilder implements Builder<Chebi> {
    private String id;
    private String name;
    private String inchiKey;

    public ChebiBuilder id(String id) {
        this.id = id;
        return this;
    }

    public ChebiBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ChebiBuilder inchiKey(String inchiKey) {
        this.inchiKey = inchiKey;
        return this;
    }

    public @Nonnull Chebi build() {
        return new ChebiImpl(id, name, inchiKey);
    }
}
