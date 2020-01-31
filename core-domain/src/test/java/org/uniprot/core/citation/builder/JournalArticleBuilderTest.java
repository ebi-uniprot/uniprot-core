package org.uniprot.core.citation.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.CitationXrefType;
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
        DBCrossReference<CitationXrefType> citation =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
                        .id("pubmedId")
                        .build();
        JournalArticle article = new JournalArticleBuilder().addCitationXrefs(citation).build();
        assertEquals(citation, article.getCitationXrefs().get(0));
    }
}
