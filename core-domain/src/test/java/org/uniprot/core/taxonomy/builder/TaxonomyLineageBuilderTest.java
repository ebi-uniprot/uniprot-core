package org.uniprot.core.taxonomy.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;

class TaxonomyLineageBuilderTest {

    @Test
    void testSimpleTaxonomyLineage() {
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        builder.taxonId(9606L);

        TaxonomyLineage taxonomyLineage = builder.build();

        assertTrue(taxonomyLineage.hasTaxonId());
        assertEquals(taxonomyLineage.getTaxonId(), 9606L);

        assertFalse(taxonomyLineage.hasScientificName());
        assertFalse(taxonomyLineage.hasRank());
        assertFalse(taxonomyLineage.hasCommonName());
        assertFalse(taxonomyLineage.hasSynonyms());
    }

    @Test
    void testCompleteTaxonomyLineage() {
        TaxonomyLineage taxonomyLineage = getCompleteTaxonomyLineage();

        assertTrue(taxonomyLineage.hasTaxonId());
        assertEquals(taxonomyLineage.getTaxonId(), 9606L);

        assertTrue(taxonomyLineage.hasRank());
        assertEquals(taxonomyLineage.getRank(), TaxonomyRank.FAMILY);

        assertTrue(taxonomyLineage.hasScientificName());
        assertEquals(taxonomyLineage.getScientificName(), "Scientific Name");
        assertEquals(taxonomyLineage.getCommonName(), "common Name");
        assertEquals(taxonomyLineage.getSynonyms().size(), 1);
        assertEquals(taxonomyLineage.getSynonyms().get(0), "synonyms");
    }

    @Test
    void testFromTaxonomyLineage() {
        TaxonomyLineage taxonomyLineage = getCompleteTaxonomyLineage();

        TaxonomyLineageBuilder builder = TaxonomyLineageBuilder.from(taxonomyLineage);

        TaxonomyLineage other = builder.build();
        assertEquals(taxonomyLineage.toString(), other.toString());

        boolean equals = taxonomyLineage.equals(other);
        assertTrue(equals);
    }

    static TaxonomyLineage getCompleteTaxonomyLineage() {
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        builder.taxonId(9606L)
                .commonName("common Name")
                .addSynonyms("synonyms")
                .scientificName("Scientific Name")
                .hidden(true)
                .rank(TaxonomyRank.FAMILY);
        return builder.build();
    }
}
