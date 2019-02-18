package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.*;

import java.util.List;

import static java.util.Collections.emptyList;

public class UnpublishedImpl extends AbstractCitationImpl implements Unpublished {
    private static final long serialVersionUID = -5673746978175138190L;

    private UnpublishedImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null);
    }

    public UnpublishedImpl(List<String> authoringGroup, List<Author> authors, List<DBCrossReference<CitationXrefType>> citationXrefs,
                           String title, PublicationDate publicationDate) {
        super(CitationType.UNPUBLISHED, authoringGroup, authors, citationXrefs, title, publicationDate);
    }
}
