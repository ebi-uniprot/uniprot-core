package org.uniprot.core.taxonomy.builder;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyEntry;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.builder.TaxonomyEntryBuilder;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyEntryBuilderTest {

    @Test
    void testSimpleTaxonomyEntry(){
        TaxonomyEntryBuilder builder = new TaxonomyEntryBuilder();
        builder.taxonId(9606L);

        TaxonomyEntry taxonomyEntry = builder.build();
        assertEquals(taxonomyEntry.getTaxonId(), 9606L);

        assertFalse(taxonomyEntry.hasParentId());
        assertFalse(taxonomyEntry.hasRank());
        assertFalse(taxonomyEntry.hasScientificName());
        assertFalse(taxonomyEntry.hasCommonName());
        assertFalse(taxonomyEntry.hasMnemonic());
        assertFalse(taxonomyEntry.hasStatistics());
        assertFalse(taxonomyEntry.hasInactiveReason());

        assertTrue(taxonomyEntry.getSynonyms().isEmpty());
        assertFalse(taxonomyEntry.hasSynonyms());

        assertTrue(taxonomyEntry.getHosts().isEmpty());
        assertFalse(taxonomyEntry.hasHosts());

        assertTrue(taxonomyEntry.getLineage().isEmpty());
        assertFalse(taxonomyEntry.hasLineage());

        assertTrue(taxonomyEntry.getLinks().isEmpty());
        assertFalse(taxonomyEntry.hasLinks());

        assertTrue(taxonomyEntry.getOtherNames().isEmpty());
        assertFalse(taxonomyEntry.hasOtherNames());

        assertTrue(taxonomyEntry.getStrains().isEmpty());
        assertFalse(taxonomyEntry.hasStrains());
    }

    @Test
    void testCompleteTaxonomyEntry(){
        TaxonomyEntry taxonomyEntry = getCompleteTaxonomyEntry();
        validateTaxonomy(taxonomyEntry);
    }

    @Test
    void testAddMethodsTaxonomyEntry(){
        TaxonomyEntry taxonomyEntry = getCompleteTaxonomyEntryUsingAdd();
        validateTaxonomy(taxonomyEntry);
    }

    @Test
    void testFromTaxonomyEntry(){
        TaxonomyEntry taxonomyEntry = getCompleteTaxonomyEntry();

        TaxonomyEntry otherEntry = new TaxonomyEntryBuilder().from(taxonomyEntry).build();
        assertEquals(taxonomyEntry.toString(),otherEntry.toString());
        boolean equals = taxonomyEntry.equals(otherEntry);
        assertTrue(equals);
    }

    private void validateTaxonomy(TaxonomyEntry taxonomyEntry) {
        assertEquals(taxonomyEntry.getTaxonId(), 9606L);

        assertTrue(taxonomyEntry.hasParentId());
        assertEquals(taxonomyEntry.getParentId(),Long.valueOf(9605L));

        assertTrue(taxonomyEntry.hasRank());
        assertEquals(taxonomyEntry.getRank(),TaxonomyRank.KINGDOM);

        assertTrue(taxonomyEntry.hasScientificName());
        assertEquals(taxonomyEntry.getScientificName(),"scientificName");

        assertTrue(taxonomyEntry.hasCommonName());
        assertEquals(taxonomyEntry.getCommonName(),"commonName");

        assertTrue(taxonomyEntry.hasMnemonic());
        assertEquals(taxonomyEntry.getMnemonic(),"mnemonic");

        assertTrue(taxonomyEntry.hasStatistics());
        assertEquals(taxonomyEntry.getStatistics(),TaxonomyStatisticsBuilderTest.getCompleteTaxonomyStatistics());

        assertTrue(taxonomyEntry.hasSynonyms());
        MatcherAssert.assertThat(taxonomyEntry.getSynonyms(), Matchers.contains("synonym"));

        assertTrue(taxonomyEntry.hasHosts());
        MatcherAssert.assertThat(taxonomyEntry.getHosts(),Matchers.contains(getCompleteTaxonomy()));

        assertTrue(taxonomyEntry.hasLineage());
        MatcherAssert.assertThat(taxonomyEntry.getLineage(),Matchers.contains(TaxonomyLineageBuilderTest.getCompleteTaxonomyLineage()));

        assertTrue(taxonomyEntry.hasLinks());
        MatcherAssert.assertThat(taxonomyEntry.getLinks(),Matchers.contains("link"));

        assertTrue(taxonomyEntry.hasOtherNames());
        MatcherAssert.assertThat(taxonomyEntry.getOtherNames(),Matchers.contains("otherName"));

        assertTrue(taxonomyEntry.hasInactiveReason());
        assertEquals(taxonomyEntry.getInactiveReason(), TaxonomyInactiveReasonBuilderTest.getCompleteTaxonomyInactiveReason());

        assertTrue(taxonomyEntry.hasStrains());
        MatcherAssert.assertThat(taxonomyEntry.getStrains(),Matchers.contains(TaxonomyStrainBuilderTest.getCompleteTaxonomyStrain()));
    }

    private TaxonomyEntry getCompleteTaxonomyEntryUsingAdd(){
        TaxonomyEntryBuilder builder = getTaxonomyEntryBuilderWithBasicData();

        builder.addSynonyms("synonym");
        builder.addOtherNames("otherName");
        builder.addLineage(TaxonomyLineageBuilderTest.getCompleteTaxonomyLineage());
        builder.addStrain(TaxonomyStrainBuilderTest.getCompleteTaxonomyStrain());
        builder.addHost(getCompleteTaxonomy());
        builder.addLink("link");

        return builder.build();

    }

    private TaxonomyEntry getCompleteTaxonomyEntry(){
        TaxonomyEntryBuilder builder = getTaxonomyEntryBuilderWithBasicData();

        builder.synonyms(Collections.singletonList("synonym"));
        builder.otherNames(Collections.singletonList("otherName"));
        builder.lineage(Collections.singletonList(TaxonomyLineageBuilderTest.getCompleteTaxonomyLineage()));
        builder.strains(Collections.singletonList(TaxonomyStrainBuilderTest.getCompleteTaxonomyStrain()));
        builder.hosts(Collections.singletonList(getCompleteTaxonomy()));
        builder.links(Collections.singletonList("link"));

        return builder.build();
    }

    private TaxonomyEntryBuilder getTaxonomyEntryBuilderWithBasicData() {
        TaxonomyEntryBuilder builder = new TaxonomyEntryBuilder();
        builder.taxonId(9606L);
        builder.scientificName("scientificName");
        builder.commonName("commonName");
        builder.mnemonic("mnemonic");
        builder.parentId(9605L);
        builder.rank(TaxonomyRank.KINGDOM);
        builder.hidden(true);
        builder.active(true);
        builder.inactiveReason(TaxonomyInactiveReasonBuilderTest.getCompleteTaxonomyInactiveReason());
        builder.statistics(TaxonomyStatisticsBuilderTest.getCompleteTaxonomyStatistics());
        return builder;
    }

    private Taxonomy getCompleteTaxonomy() {
        return TaxonomyBuilder.newInstance().taxonId(9606)
                .scientificName("Homo sapiens")
                .commonName("Human")
                .synonyms(Collections.singletonList("Some name"))
                .mnemonic("HUMAN").build();
    }

}