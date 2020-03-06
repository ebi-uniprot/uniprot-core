package org.uniprot.core.citation.impl;

import static java.util.Collections.emptyList;

import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;

public class UnpublishedImpl extends AbstractCitationImpl implements Unpublished {
    private static final long serialVersionUID = -5673746978175138190L;

    // no arg constructor for JSON deserialization
    UnpublishedImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null);
    }

    UnpublishedImpl(
            List<String> authoringGroup,
            List<Author> authors,
            List<CrossReference<CitationDatabase>> citationCrossReferences,
            String title,
            PublicationDate publicationDate) {
        super(
                CitationType.UNPUBLISHED,
                authoringGroup,
                authors,
                citationCrossReferences,
                title,
                publicationDate);
    }
}
