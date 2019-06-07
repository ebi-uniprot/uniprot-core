package uk.ac.ebi.uniprot.cv.chebi.impl;

import uk.ac.ebi.uniprot.cv.chebi.Chebi;

/**
 * Created 15/03/19
 *
 * @author Edd
 */
public class ChebiImpl implements Chebi {
    private final String id;
    private final String inchiKey;
    private final String name;

    public ChebiImpl(String id, String name, String inchiKey) {
        this.id = id;
        this.name = name;
        this.inchiKey= inchiKey;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String inchiKey() {
        return inchiKey;
    }

    @Override
    public String toString() {
        return "ChebiImpl{" +
                "id='" + id + '\'' +
                ", inchiKey='" + inchiKey + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
