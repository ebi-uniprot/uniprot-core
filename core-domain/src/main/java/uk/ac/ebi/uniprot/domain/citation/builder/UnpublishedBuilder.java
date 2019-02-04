package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Unpublished;
import uk.ac.ebi.uniprot.domain.citation.impl.UnpublishedImpl;

public final class UnpublishedBuilder extends AbstractCitationBuilder<UnpublishedBuilder, Unpublished> {
    public Unpublished build() {
        return new UnpublishedImpl(authoringGroups, authors, xrefs, title, publicationDate);
    }

    @Override
    public UnpublishedBuilder from(Unpublished instance) {
        init(instance);
        return this;
    }

    @Override
    protected UnpublishedBuilder getThis() {
        return this;
    }
}
