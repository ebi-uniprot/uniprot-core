package org.uniprot.core.citation.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.builder.CrossReferenceBuilder;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.JournalArticle;

class JournalArticleBuilderTest extends AbstractJournalArticleBuilderTest {
    @Test
    void testBuildJournalArticle() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        this.buildJournalArticleParameters(builder);
        JournalArticle citation = builder.build();
        this.verifyJournalArticle(citation, CitationType.JOURNAL_ARTICLE);
    }

    @Test
    void canAddSingleCitation() {
        CrossReference<CitationDatabase> citation =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("pubmedId")
                        .build();
        JournalArticle article =
                new JournalArticleBuilder().citationCrossReferencesAdd(citation).build();
        assertEquals(citation, article.getCitationCrossReferences().get(0));
    }
}
