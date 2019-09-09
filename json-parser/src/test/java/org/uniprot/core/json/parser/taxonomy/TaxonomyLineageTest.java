package org.uniprot.core.json.parser.taxonomy;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.taxonomy.TaxonomyJsonConfig;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.builder.TaxonomyLineageBuilder;

class TaxonomyLineageTest {

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
