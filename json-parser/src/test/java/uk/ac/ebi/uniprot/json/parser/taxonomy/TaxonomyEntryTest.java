package uk.ac.ebi.uniprot.json.parser.taxonomy;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyEntry;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.TaxonomyEntryBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder.TaxonomyBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

class TaxonomyEntryTest {

    @Test
    void testSimpleTaxonomyEntry(){
        TaxonomyEntryBuilder builder = new TaxonomyEntryBuilder();

        TaxonomyEntry taxonomyEntry = builder.build();
        ValidateJson.verifyJsonRoundTripParser(TaxonomyJsonConfig.getInstance().getFullObjectMapper(),taxonomyEntry);
    }

    @Test
    void testCompleteTaxonomyEntry(){
        TaxonomyEntry taxonomyEntry = getCompleteTaxonomyEntry();
        ValidateJson.verifyJsonRoundTripParser(TaxonomyJsonConfig.getInstance().getFullObjectMapper(),taxonomyEntry);
    }

    private TaxonomyEntry getCompleteTaxonomyEntry(){
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

        builder.addSynonyms("synonym");
        builder.addOtherNames("otherName");
        builder.addLineage(TaxonomyLineageTest.getCompleteTaxonomyLineage());
        builder.addStrain(TaxonomyStrainTest.getCompleteTaxonomyStrain());
        builder.addHost(getCompleteTaxonomy());
        builder.addLink("link");

        return builder.build();

    }

    private Taxonomy getCompleteTaxonomy() {
        return TaxonomyBuilder.newInstance().taxonId(9606)
                .scientificName("Homo sapiens")
                .commonName("Human")
                .synonyms(Collections.singletonList("Some name"))
                .mnemonic("HUMAN").build();
    }

}