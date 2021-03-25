package org.uniprot.core.citation.impl;

import static java.util.Collections.emptyList;

import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;

public class JournalArticleImpl extends AbstractJournalArticleImpl implements JournalArticle {
    private static final long serialVersionUID = -1925700851366460680L;

    // no arg constructor for JSON deserialization
    JournalArticleImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null, null, null, null);
    }

    JournalArticleImpl(
            List<String> authoringGroup,
            List<Author> authors,
            List<CrossReference<CitationDatabase>> citationCrossReferences,
            String title,
            PublicationDate publicationDate,
            String journalName,
            String firstPage,
            String lastPage,
            String volume) {
        super(
                CitationType.JOURNAL_ARTICLE,
                authoringGroup,
                authors,
                citationCrossReferences,
                title,
                publicationDate,
                journalName,
                firstPage,
                lastPage,
                volume);
        super.id = generateId();
    }

    @Override
    public String toString() {
        return "JournalArticleImpl{} " + super.toString();
    }
}
