package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProteomeStatisticsImplTest {
    public static final int REVIEWED_PROTEIN_COUNT = 98;
    public static final int UNREVIEWED_PROTEIN_COUNT = 33;
    public static final int ISOFORM_PROTEIN_COUNT = 7;
    private static final long ISOFORM_PROTEIN_COUNT_DIFF = 70;
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
    void defaultConstructor() {
        ProteomeStatisticsImpl that =
                new ProteomeStatisticsImpl();
        assertEquals(new ProteomeStatisticsImpl(0, 0, 0), that);
    }

    @Test
    void equals_whenTrue() {
        ProteomeStatisticsImpl that =
                new ProteomeStatisticsImpl(
                        REVIEWED_PROTEIN_COUNT, UNREVIEWED_PROTEIN_COUNT, ISOFORM_PROTEIN_COUNT);
        assertEquals(PROTEOME_STATISTICS, that);
    }

    @Test
    void equals_whenFalse() {
        ProteomeStatisticsImpl that =
                new ProteomeStatisticsImpl(
                        REVIEWED_PROTEIN_COUNT,
                        UNREVIEWED_PROTEIN_COUNT,
                        ISOFORM_PROTEIN_COUNT_DIFF);
        assertNotEquals(PROTEOME_STATISTICS, that);
    }

    @Test
    void hashCode_whenTrue() {
        ProteomeStatisticsImpl that =
                new ProteomeStatisticsImpl(
                        REVIEWED_PROTEIN_COUNT, UNREVIEWED_PROTEIN_COUNT, ISOFORM_PROTEIN_COUNT);
        assertEquals(PROTEOME_STATISTICS.hashCode(), that.hashCode());
    }

    @Test
    void hashCode_whenFalse() {
        ProteomeStatisticsImpl that =
                new ProteomeStatisticsImpl(
                        REVIEWED_PROTEIN_COUNT,
                        UNREVIEWED_PROTEIN_COUNT,
                        ISOFORM_PROTEIN_COUNT_DIFF);
        assertNotEquals(PROTEOME_STATISTICS.hashCode(), that.hashCode());
    }
}
