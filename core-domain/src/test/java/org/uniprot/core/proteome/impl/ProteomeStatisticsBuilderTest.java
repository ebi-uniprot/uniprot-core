package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.ProteomeStatistics;

class ProteomeStatisticsBuilderTest {
    public static final int REVIEWED_PROTEIN_COUNT = 32;
    public static final int UNREVIEWED_PROTEIN_COUNT = 9999;
    public static final int ISOFORM_PROTEIN_COUNT = 10;
    private static final ProteomeStatistics proteomeStatistics =
            new ProteomeStatisticsBuilder()
                    .reviewedProteinCount(REVIEWED_PROTEIN_COUNT)
                    .unreviewedProteinCount(UNREVIEWED_PROTEIN_COUNT)
                    .isoformProteinCount(ISOFORM_PROTEIN_COUNT)
                    .build();

    @Test
    void reviewedProteinCount() {
        assertEquals(REVIEWED_PROTEIN_COUNT, proteomeStatistics.getReviewedProteinCount());
    }

    @Test
    void unreviewedProteinCount() {
        assertEquals(UNREVIEWED_PROTEIN_COUNT, proteomeStatistics.getUnreviewedProteinCount());
    }

    @Test
    void isoformProteinCount() {
        assertEquals(ISOFORM_PROTEIN_COUNT, proteomeStatistics.getIsoformProteinCount());
    }

    @Test
    void build() {
        ProteomeStatistics build =
                new ProteomeStatisticsBuilder()
                        .reviewedProteinCount(REVIEWED_PROTEIN_COUNT)
                        .unreviewedProteinCount(UNREVIEWED_PROTEIN_COUNT)
                        .isoformProteinCount(ISOFORM_PROTEIN_COUNT)
                        .build();
        assertNotNull(build);
    }
}
