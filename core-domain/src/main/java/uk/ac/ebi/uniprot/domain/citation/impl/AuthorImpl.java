package uk.ac.ebi.uniprot.domain.citation.impl;

import org.uniprot.core.common.Utils;

import uk.ac.ebi.uniprot.domain.citation.Author;

public class AuthorImpl implements Author {
    private static final long serialVersionUID = -3113315529380049309L;
    private String value;

    private AuthorImpl() {
        this.value = "";
    }

    public AuthorImpl(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean hasValue() {
        return Utils.notEmpty(this.value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        AuthorImpl other = (AuthorImpl) obj;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }

    @Override
    public String toString() {
        return value;
    }
}