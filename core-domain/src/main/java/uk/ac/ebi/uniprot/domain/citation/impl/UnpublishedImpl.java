package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.citation.*;

import java.util.Collections;
import java.util.List;

public class UnpublishedImpl extends AbstractCitationImpl implements Unpublished {

    private UnpublishedImpl() {
        super(CitationType.UNPUBLISHED, Collections.emptyList(), Collections.emptyList(),
              null, null, null);
    }

    public UnpublishedImpl(List<String> authoringGroup, List<Author> authors, CitationXrefs citationXrefs,
                           String title, PublicationDate publicationDate) {
        super(CitationType.UNPUBLISHED, authoringGroup, authors, citationXrefs, title, publicationDate);
    }


}
