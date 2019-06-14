package uk.ac.ebi.uniprot.json.parser.taxonomy;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyStatistics;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.TaxonomyStatisticsBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

class TaxonomyStatisticsTest {

    @Test
    void testSimpleTaxonomyStatistics(){
        TaxonomyStatisticsBuilder builder = new TaxonomyStatisticsBuilder();

        TaxonomyStatistics taxonomyStatistics = builder.build();
        ValidateJson.verifyJsonRoundTripParser(TaxonomyJsonConfig.getInstance().getFullObjectMapper(),taxonomyStatistics);
    }

    @Test
    void testCompleteTaxonomyStatistics(){
        TaxonomyStatistics taxonomyStatistics = getCompleteTaxonomyStatistics();
        ValidateJson.verifyJsonRoundTripParser(TaxonomyJsonConfig.getInstance().getFullObjectMapper(),taxonomyStatistics);
        ValidateJson.verifyEmptyFields(taxonomyStatistics);
    }

    static TaxonomyStatistics getCompleteTaxonomyStatistics() {
        return new TaxonomyStatisticsBuilder()
                .reviewedProteinCount(10)
                .unreviewedProteinCount(20)
                .referenceProteomeCount(2)
                .completeProteomeCount(1)
                .build();
    }
}
