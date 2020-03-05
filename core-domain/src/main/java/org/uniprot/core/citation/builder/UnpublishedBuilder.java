package org.uniprot.core.citation.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.citation.impl.UnpublishedImpl;

public final class UnpublishedBuilder
        extends AbstractCitationBuilder<UnpublishedBuilder, Unpublished> {
    public @Nonnull Unpublished build() {
        return new UnpublishedImpl(
                authoringGroups, authors, citationCrossReferences, title, publicationDate);
    }

    public static @Nonnull UnpublishedBuilder from(Unpublished instance) {
        UnpublishedBuilder builder = new UnpublishedBuilder();
        init(builder, instance);
        return builder;
    }

    @Override
    protected @Nonnull UnpublishedBuilder getThis() {
        return this;
    }
}
