package org.uniprot.core.uniprot.builder;



import javax.annotation.Nonnull;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.KeywordImpl;

/**
 * Created 23/01/19
 *
 * @author Edd
 */
public class KeywordBuilder extends AbstractEvidencedValueBuilder<KeywordBuilder, Keyword> {
    private String id;
    private KeywordCategory category;

    public KeywordBuilder() {}

    @Override
    public @Nonnull Keyword build() {
        return new KeywordImpl(id, value, category, evidences);
    }

    @Override
    protected KeywordBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull KeywordBuilder from(@Nonnull Keyword instance) {
        return super.from(instance).id(instance.getId()).category(instance.getCategory());
    }

    public KeywordBuilder id(String id) {
        this.id = id;
        return this;
    }

    public KeywordBuilder category(KeywordCategory category) {
        this.category = category;
        return this;
    }
}
