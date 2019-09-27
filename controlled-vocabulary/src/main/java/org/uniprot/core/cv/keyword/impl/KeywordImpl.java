package org.uniprot.core.cv.keyword.impl;

import java.util.Objects;

import org.uniprot.core.cv.keyword.Keyword;

public class KeywordImpl implements Keyword {
    private final String id;
    private final String accession;

    private KeywordImpl() {
        this(null, null);
    }

    public KeywordImpl(String id, String accession) {
        super();
        this.id = id;
        this.accession = accession;
    }

    public String getId() {
        return id;
    }

    public String getAccession() {
        return accession;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.accession);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        KeywordImpl other = (KeywordImpl) obj;

        return Objects.equals(this.id, other.id) && Objects.equals(this.accession, other.accession);
    }
}
