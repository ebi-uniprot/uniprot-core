package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Unpublished;
import uk.ac.ebi.uniprot.domain.citation.impl.UnpublishedImpl;

public final class UnpublishedBuilder extends AbstractCitationBuilder<Unpublished> {
    public static UnpublishedBuilder newInstance() {
        return new UnpublishedBuilder();
    }

    public Unpublished build() {
        return new UnpublishedImpl(authoringGroups, authors,
                                   xrefs, title, publicationDate);
    }


}
