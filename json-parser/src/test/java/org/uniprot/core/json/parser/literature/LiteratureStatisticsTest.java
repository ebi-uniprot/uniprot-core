package org.uniprot.core.json.parser.literature;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.impl.LiteratureStatisticsBuilder;

/** @author lgonzales */
class LiteratureStatisticsTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureStatistics statistics = new LiteratureStatisticsBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), statistics);
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureStatistics statistics = getCompleteLiteratureStatistics();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), statistics);
        ValidateJson.verifyEmptyFields(statistics);
    }

    static LiteratureStatistics getCompleteLiteratureStatistics() {
        return new LiteratureStatisticsBuilder()
                .reviewedProteinCount(10)
                .unreviewedProteinCount(20)
                .computationallyMappedProteinCount(30)
                .communityMappedProteinCount(40)
                .build();
    }
}
