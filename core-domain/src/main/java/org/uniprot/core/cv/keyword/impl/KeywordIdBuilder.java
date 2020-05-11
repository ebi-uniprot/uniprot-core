package org.uniprot.core.cv.keyword.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.keyword.KeywordId;

public class KeywordIdBuilder implements Builder<KeywordId> {
    private String name;
    private String id;

    public @Nonnull KeywordIdBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull KeywordIdBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull KeywordId build() {
        return new KeywordIdImpl(name, id);
    }

    public static @Nonnull KeywordIdBuilder from(@Nonnull KeywordId instance) {
        return new KeywordIdBuilder().name(instance.getName()).id(instance.getId());
    }
}
