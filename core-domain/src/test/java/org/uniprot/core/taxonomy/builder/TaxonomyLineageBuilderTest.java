package org.uniprot.core.taxonomy.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.getCompleteTaxonomyLineage;

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
    }

    @Test
    void testFromTaxonomyLineage() {
        TaxonomyLineage taxonomyLineage = getCompleteTaxonomyLineage();

        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder().from(taxonomyLineage);

        TaxonomyLineage other = builder.build();
        assertEquals(taxonomyLineage.toString(), other.toString());

        boolean equals = taxonomyLineage.equals(other);
        assertTrue(equals);
    }
}
