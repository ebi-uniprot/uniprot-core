package org.uniprot.core.cv.keyword.builder;

import java.util.Objects;

import org.uniprot.core.cv.keyword.KeywordGeneOntology;

public class KeywordGeneOntologyImpl implements KeywordGeneOntology {
    private static final long serialVersionUID = -5760727679729608097L;
    private final String goId;
    private final String goTerm;

    KeywordGeneOntologyImpl() {
        this(null, null);
    }

    KeywordGeneOntologyImpl(String goId, String goTerm) {
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
        KeywordGeneOntologyImpl other = (KeywordGeneOntologyImpl) obj;
        return Objects.equals(this.goId, other.goId) && Objects.equals(this.goTerm, other.goTerm);
    }
}
