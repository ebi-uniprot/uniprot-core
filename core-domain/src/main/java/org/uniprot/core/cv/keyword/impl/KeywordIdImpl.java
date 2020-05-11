package org.uniprot.core.cv.keyword.impl;

import java.util.Objects;

import org.uniprot.core.cv.keyword.KeywordId;

public class KeywordIdImpl implements KeywordId {
    private static final long serialVersionUID = -1281635311660829402L;
    private final String name;
    // id=accession
    private final String id;

    KeywordIdImpl() {
        this(null, null);
    }

    KeywordIdImpl(String name, String id) {
        super();
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.id);
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

        return Objects.equals(this.name, other.name) && Objects.equals(this.id, other.id);
    }
}
