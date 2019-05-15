package uk.ac.ebi.uniprot.domain.taxonomy.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyStatistics;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyStatisticsBuilderTest {

    @Test
    void testSimpleTaxonomyStatistics(){
        TaxonomyStatistics taxonomyStatistics = new TaxonomyStatisticsBuilder().build();

        assertFalse(taxonomyStatistics.hasReviewedProteinCount());
        assertEquals(taxonomyStatistics.getReviewedProteinCount(),0L);

        assertFalse(taxonomyStatistics.hasUnreviewedProteinCount());
        assertEquals(taxonomyStatistics.getUnreviewedProteinCount(),0L);

        assertFalse(taxonomyStatistics.hasReferenceProteomeCount());
        assertEquals(taxonomyStatistics.getReferenceProteomeCount(),0L);

        assertFalse(taxonomyStatistics.hasCompleteProteomeCount());
        assertEquals(taxonomyStatistics.getCompleteProteomeCount(),0L);
    }

    @Test
    void testCompleteTaxonomyStatistics(){
        TaxonomyStatistics taxonomyStatistics = getCompleteTaxonomyStatistics();

        assertTrue(taxonomyStatistics.hasReviewedProteinCount());
        assertEquals(taxonomyStatistics.getReviewedProteinCount(),10L);

        assertTrue(taxonomyStatistics.hasUnreviewedProteinCount());
        assertEquals(taxonomyStatistics.getUnreviewedProteinCount(),20L);

        assertTrue(taxonomyStatistics.hasReferenceProteomeCount());
        assertEquals(taxonomyStatistics.getReferenceProteomeCount(),1L);

        assertTrue(taxonomyStatistics.hasCompleteProteomeCount());
        assertEquals(taxonomyStatistics.getCompleteProteomeCount(),2L);
    }

    @Test
    void testFromTaxonomyStrain(){
        TaxonomyStatistics taxonomyStatistics = getCompleteTaxonomyStatistics();

        TaxonomyStatisticsBuilder builder = new TaxonomyStatisticsBuilder().from(taxonomyStatistics);

        TaxonomyStatistics other = builder.build();
        assertEquals(taxonomyStatistics.toString(), other.toString());

        boolean equals = taxonomyStatistics.equals(other);
        assertTrue(equals);
    }


    public static TaxonomyStatistics getCompleteTaxonomyStatistics() {
        return new TaxonomyStatisticsBuilder()
                .reviewedProteinCount(10)
                .unreviewedProteinCount(20)
                .referenceProteomeCount(1)
                .completeProteomeCount(2)
                .build();
    }
}