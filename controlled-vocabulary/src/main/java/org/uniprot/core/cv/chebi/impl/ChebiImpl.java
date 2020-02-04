package org.uniprot.core.cv.chebi.impl;

import org.uniprot.core.cv.chebi.Chebi;

/**
 * Created 15/03/19
 *
 * @author Edd
 */
public class ChebiImpl implements Chebi {

    private static final long serialVersionUID = 7578992410951294303L;
    private final String id;
    private final String inchiKey;
    private final String name;

    public ChebiImpl(String id, String name, String inchiKey) {
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
        return "ChebiImpl{"
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
}
