package org.uniprot.core.taxonomy.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.taxonomy.TaxonomyEntry;
import org.uniprot.core.taxonomy.TaxonomyRank;

class TaxonomyEntryBuilderTest {

    @Test
    void testSimpleTaxonomyEntry() {
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
    void testCompleteTaxonomyEntry() {
        TaxonomyEntry taxonomyEntry = ObjectsForTests.getCompleteTaxonomyEntry();
        validateTaxonomy(taxonomyEntry);
    }

    @Test
    void testAddMethodsTaxonomyEntry() {
        TaxonomyEntry taxonomyEntry = ObjectsForTests.getCompleteTaxonomyEntryUsingAdd();
        validateTaxonomy(taxonomyEntry);
    }

    @Test
    void testFromTaxonomyEntry() {
        TaxonomyEntry taxonomyEntry = ObjectsForTests.getCompleteTaxonomyEntry();

        TaxonomyEntry otherEntry = new TaxonomyEntryBuilder().from(taxonomyEntry).build();
        assertEquals(taxonomyEntry.toString(), otherEntry.toString());
        boolean equals = taxonomyEntry.equals(otherEntry);
        assertTrue(equals);
    }

    private void validateTaxonomy(TaxonomyEntry taxonomyEntry) {
        assertEquals(taxonomyEntry.getTaxonId(), 9606L);

        assertTrue(taxonomyEntry.hasParentId());
        assertEquals(taxonomyEntry.getParentId(), Long.valueOf(9605L));

        assertTrue(taxonomyEntry.hasRank());
        assertEquals(taxonomyEntry.getRank(), TaxonomyRank.KINGDOM);

        assertTrue(taxonomyEntry.hasScientificName());
        assertEquals(taxonomyEntry.getScientificName(), "scientificName");

        assertTrue(taxonomyEntry.hasCommonName());
        assertEquals(taxonomyEntry.getCommonName(), "commonName");

        assertTrue(taxonomyEntry.hasMnemonic());
        assertEquals(taxonomyEntry.getMnemonic(), "mnemonic");

        assertTrue(taxonomyEntry.hasStatistics());
        assertEquals(
                taxonomyEntry.getStatistics(), ObjectsForTests.getCompleteTaxonomyStatistics());

        assertTrue(taxonomyEntry.hasSynonyms());
        MatcherAssert.assertThat(taxonomyEntry.getSynonyms(), Matchers.contains("synonym"));

        assertTrue(taxonomyEntry.hasHosts());
        MatcherAssert.assertThat(
                taxonomyEntry.getHosts(), Matchers.contains(ObjectsForTests.getCompleteTaxonomy()));

        assertTrue(taxonomyEntry.hasLineage());
        MatcherAssert.assertThat(
                taxonomyEntry.getLineage(),
                Matchers.contains(ObjectsForTests.getCompleteTaxonomyLineage()));

        assertTrue(taxonomyEntry.hasLinks());
        MatcherAssert.assertThat(taxonomyEntry.getLinks(), Matchers.contains("link"));

        assertTrue(taxonomyEntry.hasOtherNames());
        MatcherAssert.assertThat(taxonomyEntry.getOtherNames(), Matchers.contains("otherName"));

        assertTrue(taxonomyEntry.hasInactiveReason());
        assertEquals(
                taxonomyEntry.getInactiveReason(),
                ObjectsForTests.getCompleteTaxonomyInactiveReason());

        assertTrue(taxonomyEntry.hasStrains());
        MatcherAssert.assertThat(
                taxonomyEntry.getStrains(),
                Matchers.contains(ObjectsForTests.getCompleteTaxonomyStrain()));
    }
}
