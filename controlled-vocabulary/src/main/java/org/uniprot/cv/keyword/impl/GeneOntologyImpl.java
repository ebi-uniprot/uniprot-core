package org.uniprot.cv.keyword.impl;

import java.util.Objects;

import org.uniprot.cv.keyword.GeneOntology;

public class GeneOntologyImpl implements GeneOntology {
    private static final long serialVersionUID = -5760727679729608097L;
    private final String goId;
    private final String goTerm;

    private GeneOntologyImpl() {
        this(null, null);
    }

    public static GeneOntology create(String goId, String goTerm) {
        return new GeneOntologyImpl(goId, goTerm);
    }

    public GeneOntologyImpl(String goId, String goTerm) {
        this.goId = goId;
        this.goTerm = goTerm;
    }

    @Override
    public String getGoId() {
        return goId;
    }

    @Override
    public String getGoTerm() {
        return goTerm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.goId, this.goTerm);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GeneOntologyImpl other = (GeneOntologyImpl) obj;
        return Objects.equals(this.goId, other.goId) && Objects.equals(this.goTerm, other.goTerm);
    }
}
