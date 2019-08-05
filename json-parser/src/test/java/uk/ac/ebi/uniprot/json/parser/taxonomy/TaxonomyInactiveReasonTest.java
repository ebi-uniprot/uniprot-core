package uk.ac.ebi.uniprot.json.parser.taxonomy;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyInactiveReason;
import org.uniprot.core.taxonomy.TaxonomyInactiveReasonType;
import org.uniprot.core.taxonomy.builder.TaxonomyInactiveReasonBuilder;

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
        ValidateJson.verifyEmptyFields(taxonomyInactiveReason);
    }

    static TaxonomyInactiveReason getCompleteTaxonomyInactiveReason() {
        return new TaxonomyInactiveReasonBuilder()
                .inactiveReasonType(TaxonomyInactiveReasonType.MERGED)
                .mergedTo(2)
                .build();
    }
}
