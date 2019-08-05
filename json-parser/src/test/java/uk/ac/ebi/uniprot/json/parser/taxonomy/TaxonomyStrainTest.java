package uk.ac.ebi.uniprot.json.parser.taxonomy;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyStrain;
import org.uniprot.core.taxonomy.builder.TaxonomyStrainBuilder;

import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

class TaxonomyStrainTest {

    @Test
    void testSimpleTaxonomyStrain(){
        TaxonomyStrainBuilder builder = new TaxonomyStrainBuilder();

        TaxonomyStrain taxonomyStrain = builder.build();
        ValidateJson.verifyJsonRoundTripParser(TaxonomyJsonConfig.getInstance().getFullObjectMapper(),taxonomyStrain);
    }

    @Test
    void testCompleteTaxonomyStrain(){
        TaxonomyStrain taxonomyStrain = getCompleteTaxonomyStrain();
        ValidateJson.verifyJsonRoundTripParser(TaxonomyJsonConfig.getInstance().getFullObjectMapper(),taxonomyStrain);
        ValidateJson.verifyEmptyFields(taxonomyStrain);
    }

    static TaxonomyStrain getCompleteTaxonomyStrain(){
        TaxonomyStrainBuilder builder = new TaxonomyStrainBuilder();
        builder.synonyms(Collections.singletonList("synonym"));
        builder.name("name");
        return builder.build();
    }
}
