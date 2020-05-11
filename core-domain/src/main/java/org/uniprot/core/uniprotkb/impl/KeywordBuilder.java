package org.uniprot.core.uniprotkb.impl;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractHasEvidencesBuilder;

import javax.annotation.Nonnull;

/**
 * Created 23/01/19
 *
 * @author Edd
 */
public class KeywordBuilder extends AbstractHasEvidencesBuilder<KeywordBuilder, Keyword> {
    private String id;
    private KeywordCategory category;
    private String name;

    public KeywordBuilder() {}

    @Override
    public @Nonnull Keyword build() {
        return new KeywordImpl(id, name, category, evidences);
    }

    @Override
    protected @Nonnull KeywordBuilder getThis() {
        return this;
    }

    public static @Nonnull KeywordBuilder from(@Nonnull Keyword instance) {
        KeywordBuilder builder = new KeywordBuilder();
        AbstractHasEvidencesBuilder.init(builder, instance);
        return builder.id(instance.getId())
                .category(instance.getCategory())
                .name(instance.getName());
    }

    public @Nonnull KeywordBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull KeywordBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull KeywordBuilder category(KeywordCategory category) {
        this.category = category;
        return this;
    }
}
