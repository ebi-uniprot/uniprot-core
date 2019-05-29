package uk.ac.ebi.uniprot.json.parser.taxonomy;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyInactiveReason;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyInactiveReasonType;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.TaxonomyInactiveReasonBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

/**
 *
 * @author lgonzales
 */
public class TaxonomyInactiveReasonTest {

    @Test
    void testSimpleTaxonomyInactiveReason(){
        TaxonomyInactiveReasonBuilder builder = new TaxonomyInactiveReasonBuilder();

        TaxonomyInactiveReason taxonomyInactiveReason = builder.build();
        ValidateJson.verifyJsonRoundTripParser(TaxonomyJsonConfig.getInstance().getFullObjectMapper(),taxonomyInactiveReason);
    }

    @Test
    void testCompleteTaxonomyInactiveReason(){
        TaxonomyInactiveReason taxonomyInactiveReason = getCompleteTaxonomyInactiveReason();
        ValidateJson.verifyJsonRoundTripParser(TaxonomyJsonConfig.getInstance().getFullObjectMapper(),taxonomyInactiveReason);
    }

    static TaxonomyInactiveReason getCompleteTaxonomyInactiveReason() {
        return new TaxonomyInactiveReasonBuilder()
                .inactiveReasonType(TaxonomyInactiveReasonType.MERGED)
                .mergedTo(2)
                .build();
    }
}
