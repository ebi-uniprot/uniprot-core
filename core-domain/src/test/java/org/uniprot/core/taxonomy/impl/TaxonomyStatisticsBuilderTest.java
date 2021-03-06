package org.uniprot.core.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.getCompleteTaxonomyStatistics;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyStatistics;

class TaxonomyStatisticsBuilderTest {

    @Test
    void testSimpleTaxonomyStatistics() {
        TaxonomyStatistics taxonomyStatistics = new TaxonomyStatisticsBuilder().build();

        assertFalse(taxonomyStatistics.hasReviewedProteinCount());
        assertEquals(taxonomyStatistics.getReviewedProteinCount(), 0L);

        assertFalse(taxonomyStatistics.hasUnreviewedProteinCount());
        assertEquals(taxonomyStatistics.getUnreviewedProteinCount(), 0L);

        assertFalse(taxonomyStatistics.hasReferenceProteomeCount());
        assertEquals(taxonomyStatistics.getReferenceProteomeCount(), 0L);

        assertFalse(taxonomyStatistics.hasProteomeCount());
        assertEquals(taxonomyStatistics.getProteomeCount(), 0L);
    }

    @Test
    void testCompleteTaxonomyStatistics() {
        TaxonomyStatistics taxonomyStatistics = getCompleteTaxonomyStatistics();

        assertTrue(taxonomyStatistics.hasReviewedProteinCount());
        assertEquals(taxonomyStatistics.getReviewedProteinCount(), 10L);

        assertTrue(taxonomyStatistics.hasUnreviewedProteinCount());
        assertEquals(taxonomyStatistics.getUnreviewedProteinCount(), 20L);

        assertTrue(taxonomyStatistics.hasReferenceProteomeCount());
        assertEquals(taxonomyStatistics.getReferenceProteomeCount(), 1L);

        assertTrue(taxonomyStatistics.hasProteomeCount());
        assertEquals(taxonomyStatistics.getProteomeCount(), 2L);
    }

    @Test
    void testFromTaxonomyStrain() {
        TaxonomyStatistics taxonomyStatistics = getCompleteTaxonomyStatistics();

        TaxonomyStatisticsBuilder builder = TaxonomyStatisticsBuilder.from(taxonomyStatistics);

        TaxonomyStatistics other = builder.build();
        assertEquals(taxonomyStatistics.toString(), other.toString());

        boolean equals = taxonomyStatistics.equals(other);
        assertTrue(equals);
    }
}
