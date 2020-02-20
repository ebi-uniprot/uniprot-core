package org.uniprot.core.cv.keyword.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.keyword.KeywordEntryKeyword;

public class KeywordEntryKeywordBuilder implements Builder<KeywordEntryKeyword> {
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

    public @Nonnull KeywordEntryKeyword build() {
        return new KeywordEntryKeywordImpl(id, accession);
    }

    public static @Nonnull KeywordEntryKeywordBuilder from(@Nonnull KeywordEntryKeyword instance) {
        return new KeywordEntryKeywordBuilder()
                .id(instance.getId())
                .accession(instance.getAccession());
    }
}
