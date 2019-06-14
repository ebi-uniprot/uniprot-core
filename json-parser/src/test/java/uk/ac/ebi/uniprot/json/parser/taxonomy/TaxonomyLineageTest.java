package uk.ac.ebi.uniprot.json.parser.taxonomy;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyLineage;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.TaxonomyLineageBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

public class TaxonomyLineageTest {

    @Test
    void testSimpleTaxonomyLineage(){
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        builder.taxonId(9606);

        TaxonomyLineage taxonomyLineage = builder.build();
        ValidateJson.verifyJsonRoundTripParser(TaxonomyJsonConfig.getInstance().getFullObjectMapper(),taxonomyLineage);
    }

    @Test
    void testCompleteTaxonomyLineage(){
        TaxonomyLineage taxonomyLineage = getCompleteTaxonomyLineage();
        ValidateJson.verifyJsonRoundTripParser(TaxonomyJsonConfig.getInstance().getFullObjectMapper(),taxonomyLineage);
        ValidateJson.verifyEmptyFields(taxonomyLineage);
    }

    static TaxonomyLineage getCompleteTaxonomyLineage(){
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        builder.taxonId(9606L).scientificName("Scientific Name").hidden(true).rank(TaxonomyRank.FAMILY);
        return builder.build();
    }
}
