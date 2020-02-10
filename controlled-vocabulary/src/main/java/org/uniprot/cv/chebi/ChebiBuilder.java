package org.uniprot.cv.chebi;

import org.uniprot.cv.chebi.impl.ChebiImpl;

/**
 * Created 07/06/19
 *
 * @author Edd
 */
public class ChebiBuilder {
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

    public Chebi build() {
        return new ChebiImpl(id, name, inchiKey);
    }
}
