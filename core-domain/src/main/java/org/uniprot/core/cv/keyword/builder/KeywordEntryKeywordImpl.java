package org.uniprot.core.cv.keyword.builder;

import java.util.Objects;

import org.uniprot.core.cv.keyword.KeywordEntryKeyword;

public class KeywordEntryKeywordImpl implements KeywordEntryKeyword {
    private static final long serialVersionUID = -1281635311660829402L;
    private final String id;
    private final String accession;

    KeywordEntryKeywordImpl() {
        this(null, null);
    }

    KeywordEntryKeywordImpl(String id, String accession) {
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

        KeywordEntryKeywordImpl other = (KeywordEntryKeywordImpl) obj;

        return Objects.equals(this.id, other.id) && Objects.equals(this.accession, other.accession);
    }
}
