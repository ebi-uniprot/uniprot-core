package org.uniprot.core.cv.keyword.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.keyword.KeywordId;

public class KeywordEntryKeywordBuilder implements Builder<KeywordId> {
    private String id;
    private String accession;

    public @Nonnull KeywordEntryKeywordBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull KeywordEntryKeywordBuilder accession(String accession) {
        this.accession = accession;
        return this;
    }

    public @Nonnull
    KeywordId build() {
        return new KeywordIdImpl(id, accession);
    }

    public static @Nonnull KeywordEntryKeywordBuilder from(@Nonnull KeywordId instance) {
        return new KeywordEntryKeywordBuilder()
                .id(instance.getName())
                .accession(instance.getId());
    }
}
