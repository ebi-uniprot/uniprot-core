package org.uniprot.core.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyEntry;
import org.uniprot.core.taxonomy.TaxonomyRank;

class TaxonomyEntryBuilderTest {

    @Test
    void testSimpleTaxonomyEntry() {
        TaxonomyEntryBuilder builder = new TaxonomyEntryBuilder();
        builder.taxonId(9606L);

        TaxonomyEntry taxonomyEntry = builder.build();
        assertEquals(9606L, taxonomyEntry.getTaxonId());

        assertFalse(taxonomyEntry.hasParent());
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

        assertTrue(taxonomyEntry.getLineages().isEmpty());
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
        TaxonomyEntry taxonomyEntry = getCompleteTaxonomyEntry();
        validateTaxonomy(taxonomyEntry);
    }

    @Test
    void testAddMethodsTaxonomyEntry() {
        TaxonomyEntry taxonomyEntry = getCompleteTaxonomyEntryUsingAdd();
        validateTaxonomy(taxonomyEntry);
    }

    @Test
    void testFromTaxonomyEntry() {
        TaxonomyEntry taxonomyEntry = getCompleteTaxonomyEntry();

        TaxonomyEntry otherEntry = TaxonomyEntryBuilder.from(taxonomyEntry).build();
        assertEquals(taxonomyEntry.toString(), otherEntry.toString());
        boolean equals = taxonomyEntry.equals(otherEntry);
        assertTrue(equals);
    }

    private void validateTaxonomy(TaxonomyEntry taxonomyEntry) {
        assertEquals(9606L, taxonomyEntry.getTaxonId());

        assertTrue(taxonomyEntry.hasParent());
        assertEquals(getCompleteTaxonomy(), taxonomyEntry.getParent());

        assertTrue(taxonomyEntry.hasRank());
        assertEquals(TaxonomyRank.KINGDOM, taxonomyEntry.getRank());

        assertTrue(taxonomyEntry.hasScientificName());
        assertEquals("scientificName", taxonomyEntry.getScientificName());

        assertTrue(taxonomyEntry.hasCommonName());
        assertEquals("commonName", taxonomyEntry.getCommonName());

        assertTrue(taxonomyEntry.hasMnemonic());
        assertEquals("mnemonic", taxonomyEntry.getMnemonic());

        assertTrue(taxonomyEntry.hasStatistics());
        assertEquals(taxonomyEntry.getStatistics(), getCompleteTaxonomyStatistics());

        assertTrue(taxonomyEntry.hasSynonyms());
        MatcherAssert.assertThat(taxonomyEntry.getSynonyms(), Matchers.contains("synonym"));

        assertTrue(taxonomyEntry.hasHosts());
        MatcherAssert.assertThat(
                taxonomyEntry.getHosts(), Matchers.contains(getCompleteTaxonomy()));

        assertTrue(taxonomyEntry.hasLineage());
        MatcherAssert.assertThat(
                taxonomyEntry.getLineages(), Matchers.contains(getCompleteTaxonomyLineage()));

        assertTrue(taxonomyEntry.hasLinks());
        MatcherAssert.assertThat(taxonomyEntry.getLinks(), Matchers.contains("link"));

        assertTrue(taxonomyEntry.hasOtherNames());
        MatcherAssert.assertThat(taxonomyEntry.getOtherNames(), Matchers.contains("otherName"));

        assertTrue(taxonomyEntry.hasInactiveReason());
        assertEquals(taxonomyEntry.getInactiveReason(), getCompleteTaxonomyInactiveReason());

        assertTrue(taxonomyEntry.hasStrains());
        MatcherAssert.assertThat(
                taxonomyEntry.getStrains(), Matchers.contains(getCompleteTaxonomyStrain()));
    }
}
