package org.uniprot.core.json.parser.taxonomy;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.taxonomy.TaxonomyEntry;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.impl.TaxonomyEntryBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

import java.util.Collections;

public class TaxonomyEntryTest {

    @Test
    void testSimpleTaxonomyEntry() {
        TaxonomyEntryBuilder builder = new TaxonomyEntryBuilder();

        TaxonomyEntry taxonomyEntry = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                TaxonomyJsonConfig.getInstance().getFullObjectMapper(), taxonomyEntry);
    }

    @Test
    void testCompleteTaxonomyEntry() {
        TaxonomyEntry taxonomyEntry = getCompleteTaxonomyEntry();
        ValidateJson.verifyJsonRoundTripParser(
                TaxonomyJsonConfig.getInstance().getFullObjectMapper(), taxonomyEntry);
        ValidateJson.verifyEmptyFields(taxonomyEntry);
    }

    public static TaxonomyEntry getCompleteTaxonomyEntry() {
        TaxonomyEntryBuilder builder = new TaxonomyEntryBuilder();
        builder.taxonId(9606L);
        builder.scientificName("scientificName");
        builder.commonName("commonName");
        builder.mnemonic("mnemonic");
        builder.parentId(9605L);
        builder.rank(TaxonomyRank.KINGDOM);
        builder.hidden(true);
        builder.active(true);
        builder.statistics(TaxonomyStatisticsTest.getCompleteTaxonomyStatistics());

        builder.synonymsAdd("synonym");
        builder.otherNamesAdd("otherName");
        builder.lineagesAdd(TaxonomyLineageTest.getCompleteTaxonomyLineage());
        builder.strainsAdd(TaxonomyStrainTest.getCompleteTaxonomyStrain());
        builder.inactiveReason(TaxonomyInactiveReasonTest.getCompleteTaxonomyInactiveReason());
        builder.hostsAdd(getCompleteTaxonomy());
        builder.linksAdd("link");

        return builder.build();
    }

    private static Taxonomy getCompleteTaxonomy() {
        return new TaxonomyBuilder()
                .taxonId(9606)
                .scientificName("Homo sapiens")
                .commonName("Human")
                .synonymsSet(Collections.singletonList("Some name"))
                .mnemonic("HUMAN")
                .build();
    }
}
