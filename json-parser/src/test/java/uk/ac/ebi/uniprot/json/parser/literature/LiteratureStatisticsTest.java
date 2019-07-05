package uk.ac.ebi.uniprot.json.parser.literature;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.literature.LiteratureStatistics;
import uk.ac.ebi.uniprot.domain.literature.builder.LiteratureStatisticsBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

/**
 * @author lgonzales
 */
class LiteratureStatisticsTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureStatistics statistics = new LiteratureStatisticsBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(LiteratureJsonConfig.getInstance().getFullObjectMapper(), statistics);
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureStatistics statistics = getCompleteLiteratureStatistics();
        ValidateJson.verifyJsonRoundTripParser(LiteratureJsonConfig.getInstance().getFullObjectMapper(), statistics);
        ValidateJson.verifyEmptyFields(statistics);
    }

    static LiteratureStatistics getCompleteLiteratureStatistics() {
        return new LiteratureStatisticsBuilder()
                .reviewedProteinCount(10)
                .unreviewedProteinCount(20)
                .mappedProteinCount(30)
                .build();
    }
}
