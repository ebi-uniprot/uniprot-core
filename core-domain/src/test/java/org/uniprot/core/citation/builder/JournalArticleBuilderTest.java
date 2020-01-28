package org.uniprot.core.citation.builder;

import org.junit.jupiter.api.Test;
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
}
