package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProteomeStatisticsImplTest {
    public static final int REVIEWED_PROTEIN_COUNT = 98;
    public static final int UNREVIEWED_PROTEIN_COUNT = 33;
    public static final int ISOFORM_PROTEIN_COUNT = 7;
    public static final ProteomeStatisticsImpl PROTEOME_STATISTICS =
            new ProteomeStatisticsImpl(
                    REVIEWED_PROTEIN_COUNT, UNREVIEWED_PROTEIN_COUNT, ISOFORM_PROTEIN_COUNT);

    @Test
    void getCountsAreAccurate() {
        assertEquals(REVIEWED_PROTEIN_COUNT, PROTEOME_STATISTICS.getReviewedProteinCount());
        assertEquals(UNREVIEWED_PROTEIN_COUNT, PROTEOME_STATISTICS.getUnreviewedProteinCount());
        assertEquals(ISOFORM_PROTEIN_COUNT, PROTEOME_STATISTICS.getIsoformProteinCount());
    }

    @Test
    void testEquals() {
        ProteomeStatisticsImpl that =
                new ProteomeStatisticsImpl(
                        REVIEWED_PROTEIN_COUNT, UNREVIEWED_PROTEIN_COUNT, ISOFORM_PROTEIN_COUNT);
        assertEquals(PROTEOME_STATISTICS, that);
    }

    @Test
    void testHashCode() {
        ProteomeStatisticsImpl that =
                new ProteomeStatisticsImpl(
                        REVIEWED_PROTEIN_COUNT, UNREVIEWED_PROTEIN_COUNT, ISOFORM_PROTEIN_COUNT);
        assertEquals(PROTEOME_STATISTICS, that);
    }
}
