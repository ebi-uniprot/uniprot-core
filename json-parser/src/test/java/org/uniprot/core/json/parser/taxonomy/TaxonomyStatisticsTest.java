package org.uniprot.core.json.parser.taxonomy;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.taxonomy.TaxonomyStatistics;
import org.uniprot.core.taxonomy.impl.TaxonomyStatisticsBuilder;

class TaxonomyStatisticsTest {

    @Test
    void testSimpleTaxonomyStatistics() {
        TaxonomyStatisticsBuilder builder = new TaxonomyStatisticsBuilder();

        TaxonomyStatistics taxonomyStatistics = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                TaxonomyJsonConfig.getInstance().getFullObjectMapper(), taxonomyStatistics);
    }

    @Test
    void testCompleteTaxonomyStatistics() {
        TaxonomyStatistics taxonomyStatistics = getCompleteTaxonomyStatistics();
        ValidateJson.verifyJsonRoundTripParser(
                TaxonomyJsonConfig.getInstance().getFullObjectMapper(), taxonomyStatistics);
        ValidateJson.verifyEmptyFields(taxonomyStatistics);
    }

    static TaxonomyStatistics getCompleteTaxonomyStatistics() {
        return new TaxonomyStatisticsBuilder()
                .reviewedProteinCount(10)
                .unreviewedProteinCount(20)
                .referenceProteomeCount(2)
                .proteomeCount(1)
                .build();
    }
}
