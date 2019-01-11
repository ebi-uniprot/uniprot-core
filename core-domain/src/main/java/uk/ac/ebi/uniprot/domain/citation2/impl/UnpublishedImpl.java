package uk.ac.ebi.uniprot.domain.citation2.impl;

import uk.ac.ebi.uniprot.domain.citation2.CitationType;
import uk.ac.ebi.uniprot.domain.citation2.Unpublished;
import uk.ac.ebi.uniprot.domain.citation2.builder.UnpublishedBuilder;

import java.util.Collections;

public class UnpublishedImpl extends AbstractCitationImpl implements Unpublished {

    private UnpublishedImpl() {
        super(CitationType.UNPUBLISHED, Collections.emptyList(), Collections.emptyList(),
              null, null, null);
    }

    public UnpublishedImpl(UnpublishedBuilder builder) {
        super(CitationType.UNPUBLISHED, builder.getAuthoringGroups(), builder.getAuthors(), builder.getXrefs(),
              builder.getTitle(), builder.getPublicationDate());
    }
}
