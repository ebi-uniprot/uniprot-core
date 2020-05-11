package org.uniprot.core.cv.subcell.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.impl.StatisticsBuilder;

import java.util.concurrent.ThreadLocalRandom;

public class SubcellularStatisticsImplTest {

    private long reviewed;
    private long unreviewed;

    @BeforeEach
    void setUp() {
        this.reviewed = ThreadLocalRandom.current().nextLong(100000);
        this.unreviewed = ThreadLocalRandom.current().nextLong(100000);
    }

    @Test
    void testCreateKeywordStats() {
        Statistics kw = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Assertions.assertEquals(this.reviewed, kw.getReviewedProteinCount());
        Assertions.assertEquals(this.unreviewed, kw.getUnreviewedProteinCount());
    }

    @Test
    void testValueEqual() {
        Statistics sc1 = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Statistics sc2 = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Assertions.assertTrue(sc1.equals(sc2));
        Assertions.assertTrue(sc1.hashCode() == sc2.hashCode());
    }

    @Test
    void testValueNotEqual() {
        Statistics sc1 = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        this.unreviewed = this.unreviewed - 1;
        Statistics kw2 = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Assertions.assertFalse(sc1.equals(kw2));
    }

    @Test
    void testRefEqual() {
        Statistics sc1 = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Assertions.assertTrue(sc1.equals(sc1));
        Assertions.assertTrue(sc1.hashCode() == sc1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        Statistics sc = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Assertions.assertFalse(sc.equals(null));
    }

    public static Statistics createSubcellularLocationStatistics(long reviewed, long unreviewed) {
        return new StatisticsBuilder()
                .reviewedProteinCount(reviewed)
                .unreviewedProteinCount(unreviewed)
                .build();
    }
}
