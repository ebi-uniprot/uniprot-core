package org.uniprot.core.cv.keyword.builder;

import java.util.Objects;

import org.uniprot.core.cv.keyword.KeywordId;

public class KeywordIdImpl implements KeywordId {
    private static final long serialVersionUID = -1281635311660829402L;
    private final String id;
    private final String accession;

    KeywordIdImpl() {
        this(null, null);
    }

    KeywordIdImpl(String id, String accession) {
        super();
        this.id = id;
        this.accession = accession;
    }

    public String getName() {
        return id;
    }

    public String getId() {
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

        KeywordIdImpl other = (KeywordIdImpl) obj;

        return Objects.equals(this.id, other.id) && Objects.equals(this.accession, other.accession);
    }
}
