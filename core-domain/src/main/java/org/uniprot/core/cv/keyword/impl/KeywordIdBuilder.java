package org.uniprot.core.cv.keyword.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.keyword.KeywordId;

public class KeywordIdBuilder implements Builder<KeywordId> {
    private String id;
    private String accession;

    public @Nonnull KeywordIdBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull KeywordIdBuilder accession(String accession) {
        this.accession = accession;
        return this;
    }

    public @Nonnull KeywordId build() {
        return new KeywordIdImpl(id, accession);
    }

    public static @Nonnull KeywordIdBuilder from(@Nonnull KeywordId instance) {
        return new KeywordIdBuilder().id(instance.getName()).accession(instance.getId());
    }
}
