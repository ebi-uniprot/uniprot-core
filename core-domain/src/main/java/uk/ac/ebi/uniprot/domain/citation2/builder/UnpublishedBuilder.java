package uk.ac.ebi.uniprot.domain.citation2.builder;

import uk.ac.ebi.uniprot.domain.citation2.Unpublished;
import uk.ac.ebi.uniprot.domain.citation2.impl.UnpublishedImpl;

public final class UnpublishedBuilder extends AbstractCitationBuilder<UnpublishedBuilder, Unpublished> {
    public Unpublished build() {
        return new UnpublishedImpl(this);
    }

    @Override
    public UnpublishedBuilder from(Unpublished instance) {
        UnpublishedBuilder builder = new UnpublishedBuilder();
        init(builder, instance);
        return builder;
    }
}
