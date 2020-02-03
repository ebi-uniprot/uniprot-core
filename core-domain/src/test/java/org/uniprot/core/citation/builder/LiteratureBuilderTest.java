package org.uniprot.core.citation.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.Literature;

/**
 * @author lgonzales
 * @since 2020-01-28
 */
class LiteratureBuilderTest extends AbstractJournalArticleBuilderTest {

    @Test
    void testBuildJournalArticle() {
        LiteratureBuilder builder = new LiteratureBuilder();
        this.buildJournalArticleParameters(builder);
        JournalArticle citation = builder.build();
        this.verifyJournalArticle(citation, CitationType.LITERATURE);
    }

    @Test
    void testAddCompleteAuthorList() {
        LiteratureBuilder builder = new LiteratureBuilder();
        this.buildJournalArticleParameters(builder);
        builder.completeAuthorList(true);
        Literature literature = builder.build();
        this.verifyJournalArticle(literature, CitationType.LITERATURE);
        assertTrue(literature.isCompleteAuthorList());
    }

    @Test
    void testAddLiteratureAbstract() {
        LiteratureBuilder builder = new LiteratureBuilder();
        this.buildJournalArticleParameters(builder);
        String literatureAbstract = "The abstract";
        builder.literatureAbstract(literatureAbstract);
        Literature literature = builder.build();
        this.verifyJournalArticle(literature, CitationType.LITERATURE);
        assertEquals(literatureAbstract, literature.getLiteratureAbstract());
    }
}
