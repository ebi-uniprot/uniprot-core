package org.uniprot.core.json.parser.literature;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.citation.LiteratureTest;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.impl.LiteratureEntryBuilder;

/** @author lgonzales */
class LiteratureEntryTest {

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

    static LiteratureEntry getCompleteLiteratureEntry() {
        return new LiteratureEntryBuilder()
                .citation(LiteratureTest.getCompleteLiterature())
                .statistics(LiteratureStatisticsTest.getCompleteLiteratureStatistics())
                .build();
    }
}
