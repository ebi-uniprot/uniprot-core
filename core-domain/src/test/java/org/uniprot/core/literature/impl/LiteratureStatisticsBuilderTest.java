package org.uniprot.core.literature.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createCompleteLiteratureStatistics;

import org.junit.jupiter.api.Test;
import org.uniprot.core.literature.LiteratureStatistics;

/** @author lgonzales */
class LiteratureStatisticsBuilderTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureStatistics statistics = new LiteratureStatisticsBuilder().build();
        assertFalse(statistics.hasReviewedProteinCount());
        assertFalse(statistics.hasUnreviewedProteinCount());
        assertFalse(statistics.hasComputationallyMappedProteinCount());
        assertFalse(statistics.hasCommunityMappedProteinCount());
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureStatistics statistics = createCompleteLiteratureStatistics();

        assertTrue(statistics.hasReviewedProteinCount());
        assertEquals(statistics.getReviewedProteinCount(), 10L);

        assertTrue(statistics.hasUnreviewedProteinCount());
        assertEquals(statistics.getUnreviewedProteinCount(), 20L);

        assertTrue(statistics.hasComputationallyMappedProteinCount());
        assertEquals(statistics.getComputationallyMappedProteinCount(), 30L);

        assertTrue(statistics.hasCommunityMappedProteinCount());
        assertEquals(statistics.getCommunityMappedProteinCount(), 40L);
    }
}
