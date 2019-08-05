package org.uniprot.core.json.parser.subcell;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.subcell.SubcellularLocationStatistics;
import org.uniprot.core.cv.subcell.impl.SubcellularLocationStatisticsImpl;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.subcell.SubcellularLocationJsonConfig;

/**
 * @author lgonzales
 * @since 2019-07-11
 */
class SubcellularLocationStatisticsTest {

    @Test
    void testCompleteSubcellularLocationEntry() {
        SubcellularLocationStatistics statistics = new SubcellularLocationStatisticsImpl(10L, 20L);
        ValidateJson.verifyJsonRoundTripParser(SubcellularLocationJsonConfig.getInstance().getFullObjectMapper(), statistics);
        ValidateJson.verifyEmptyFields(statistics);
    }

}
