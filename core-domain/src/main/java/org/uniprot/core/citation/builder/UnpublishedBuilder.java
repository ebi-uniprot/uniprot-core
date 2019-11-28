package org.uniprot.core.citation.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.citation.impl.UnpublishedImpl;

public final class UnpublishedBuilder
        extends AbstractCitationBuilder<UnpublishedBuilder, Unpublished> {
    public Unpublished build() {
        return new UnpublishedImpl(authoringGroups, authors, xrefs, title, publicationDate);
    }

    @Override
    public @Nonnull UnpublishedBuilder from(Unpublished instance) {
        init(instance);
        return this;
    }

    @Override
    protected UnpublishedBuilder getThis() {
        return this;
    }
}
