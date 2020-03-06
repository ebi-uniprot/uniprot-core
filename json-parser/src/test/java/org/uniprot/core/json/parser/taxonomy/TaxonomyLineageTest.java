package org.uniprot.core.json.parser.taxonomy;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageBuilder;

public class TaxonomyLineageTest {

    @Test
    void testSimpleTaxonomyLineage() {
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        builder.taxonId(9606);

        TaxonomyLineage taxonomyLineage = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                TaxonomyJsonConfig.getInstance().getFullObjectMapper(), taxonomyLineage);
    }

    @Test
    void testCompleteTaxonomyLineage() {
        TaxonomyLineage taxonomyLineage = getCompleteTaxonomyLineage();
        ValidateJson.verifyJsonRoundTripParser(
                TaxonomyJsonConfig.getInstance().getFullObjectMapper(), taxonomyLineage);
        ValidateJson.verifyEmptyFields(taxonomyLineage);
    }

    public static TaxonomyLineage getCompleteTaxonomyLineage() {
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        builder.taxonId(9606L)
                .scientificName("Scientific Name")
                .commonName("Common Name")
                .synonymsAdd("synonym")
                .hidden(true)
                .rank(TaxonomyRank.FAMILY);
        return builder.build();
    }
}
