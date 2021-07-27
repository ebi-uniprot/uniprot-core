package org.uniprot.core.json.parser.literature;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.citation.ElectronicArticleTest;
import org.uniprot.core.json.parser.uniprot.citation.LiteratureTest;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.impl.LiteratureEntryBuilder;

/** @author lgonzales */
public class LiteratureEntryTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureEntryBuilder builder = new LiteratureEntryBuilder();

        LiteratureEntry literatureEntry = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureEntry);
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureEntry literatureEntry = getCompleteLiteratureEntry();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureEntry);
        ValidateJson.verifyEmptyFields(literatureEntry);
    }

    @Test
    void testCompleteLiteratureEntryWithElectronicArticleCitation() {
        LiteratureEntry literatureEntry =
                new LiteratureEntryBuilder()
                        .citation(ElectronicArticleTest.getElectronicArticle())
                        .statistics(LiteratureStatisticsTest.getCompleteLiteratureStatistics())
                        .build();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureEntry);
        ValidateJson.verifyEmptyFields(literatureEntry);
    }

    public static LiteratureEntry getCompleteLiteratureEntry() {
        return new LiteratureEntryBuilder()
                .citation(LiteratureTest.getCompleteLiterature())
                .statistics(LiteratureStatisticsTest.getCompleteLiteratureStatistics())
                .build();
    }
}
