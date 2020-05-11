package org.uniprot.core.cv.keyword.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.impl.StatisticsBuilder;

import java.util.concurrent.ThreadLocalRandom;

public class KeywordStatisticsImplTest {

    private long reviewed;
    private long unreviewed;

    @BeforeEach
    void setUp() {
        this.reviewed = ThreadLocalRandom.current().nextLong(100000);
        this.unreviewed = ThreadLocalRandom.current().nextLong(100000);
    }

    @Test
    void testCreateKeywordStats() {
        Statistics kw = createKeywordStatistics(this.reviewed, this.unreviewed);
        Assertions.assertEquals(this.reviewed, kw.getReviewedProteinCount());
        Assertions.assertEquals(this.unreviewed, kw.getUnreviewedProteinCount());
    }

    @Test
    void testValueEqual() {
        Statistics kw1 = createKeywordStatistics(this.reviewed, this.unreviewed);
        Statistics kw2 = createKeywordStatistics(this.reviewed, this.unreviewed);
        Assertions.assertTrue(kw1.equals(kw2));
        Assertions.assertTrue(kw1.hashCode() == kw2.hashCode());
    }

    @Test
    void testValueNotEqual() {
        Statistics kw1 = createKeywordStatistics(this.reviewed, this.unreviewed);
        this.unreviewed = this.unreviewed - 1;
        Statistics kw2 = createKeywordStatistics(this.reviewed, this.unreviewed);
        Assertions.assertFalse(kw1.equals(kw2));
    }

    @Test
    void testRefEqual() {
        Statistics kw1 = createKeywordStatistics(this.reviewed, this.unreviewed);
        Assertions.assertTrue(kw1.equals(kw1));
        Assertions.assertTrue(kw1.hashCode() == kw1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        Statistics kw = createKeywordStatistics(this.reviewed, this.unreviewed);
        Assertions.assertFalse(kw.equals(null));
    }

    public static Statistics createKeywordStatistics(long reviewed, long unreviewed) {
        return new StatisticsBuilder()
                .reviewedProteinCount(reviewed)
                .unreviewedProteinCount(unreviewed)
                .build();
    }
}
