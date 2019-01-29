package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.Unpublished;
import uk.ac.ebi.uniprot.domain.citation.builder.UnpublishedBuilder;

public class UnpublishedImpl extends AbstractCitationImpl implements Unpublished {

    private UnpublishedImpl() {
        this(new UnpublishedBuilder());
    }

    public UnpublishedImpl(UnpublishedBuilder builder) {
        super(CitationType.UNPUBLISHED, builder.getAuthoringGroups(), builder.getAuthors(), builder.getXrefs(),
              builder.getTitle(), builder.getPublicationDate());
    }
}
