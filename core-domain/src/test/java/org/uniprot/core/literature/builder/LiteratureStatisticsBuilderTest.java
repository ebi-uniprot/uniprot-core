package org.uniprot.core.literature.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.builder.LiteratureStatisticsBuilder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 */
class LiteratureStatisticsBuilderTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureStatistics statistics = new LiteratureStatisticsBuilder().build();
        assertFalse(statistics.hasReviewedProteinCount());
        assertFalse(statistics.hasUnreviewedProteinCount());
        assertFalse(statistics.hasMappedProteinCount());
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureStatistics statistics = createCompleteLiteratureStatistics();

        assertTrue(statistics.hasReviewedProteinCount());
        assertEquals(statistics.getReviewedProteinCount(), 10L);

        assertTrue(statistics.hasUnreviewedProteinCount());
        assertEquals(statistics.getUnreviewedProteinCount(), 20L);

        assertTrue(statistics.hasMappedProteinCount());
        assertEquals(statistics.getMappedProteinCount(), 30L);
    }

    static LiteratureStatistics createCompleteLiteratureStatistics() {
        return new LiteratureStatisticsBuilder()
                .reviewedProteinCount(10)
                .unreviewedProteinCount(20)
                .mappedProteinCount(30)
                .build();
    }

}