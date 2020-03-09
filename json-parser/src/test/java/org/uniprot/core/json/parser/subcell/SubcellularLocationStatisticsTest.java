package org.uniprot.core.json.parser.subcell;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.impl.StatisticsBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/**
 * @author lgonzales
 * @since 2019-07-11
 */
class SubcellularLocationStatisticsTest {

    @Test
    void testCompleteSubcellularLocationEntry() {
        Statistics statistics =
                new StatisticsBuilder().reviewedProteinCount(10).unreviewedProteinCount(20).build();
        ValidateJson.verifyJsonRoundTripParser(
                SubcellularLocationJsonConfig.getInstance().getFullObjectMapper(), statistics);
        ValidateJson.verifyEmptyFields(statistics);
    }
}
