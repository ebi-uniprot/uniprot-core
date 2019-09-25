package org.uniprot.core.cv.keyword.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordStatistics;

import java.util.concurrent.ThreadLocalRandom;

public class KeywordStatisticsImplTest {

    private long reviewed;
    private long unreviewed;

    @BeforeEach
    void setUp(){
        this.reviewed = ThreadLocalRandom.current().nextLong(100000);
        this.unreviewed = ThreadLocalRandom.current().nextLong(100000);
    }

    @Test
    void testCreateKeywordStats(){
        KeywordStatistics kw = createKeywordStatistics(this.reviewed, this.unreviewed);
        Assertions.assertEquals(this.reviewed, kw.getReviewedProteinCount());
        Assertions.assertEquals(this.unreviewed, kw.getUnreviewedProteinCount());
    }

    @Test
    void testValueEqual(){
        KeywordStatistics kw1 = createKeywordStatistics(this.reviewed, this.unreviewed);
        KeywordStatistics kw2 = createKeywordStatistics(this.reviewed, this.unreviewed);
        Assertions.assertTrue(kw1.equals(kw2));
        Assertions.assertTrue(kw1.hashCode() == kw2.hashCode());
    }

    @Test
    void testValueNotEqual(){
        KeywordStatistics kw1 = createKeywordStatistics(this.reviewed, this.unreviewed);
        this.unreviewed = this.unreviewed - 1;
        KeywordStatistics kw2 = createKeywordStatistics(this.reviewed, this.unreviewed);
        Assertions.assertFalse(kw1.equals(kw2));
    }

    @Test
    void testRefEqual(){
        KeywordStatistics kw1 = createKeywordStatistics(this.reviewed, this.unreviewed);
        Assertions.assertTrue(kw1.equals(kw1));
        Assertions.assertTrue(kw1.hashCode() == kw1.hashCode());
    }

    @Test
    void testEqualWithNull(){
        KeywordStatistics kw = createKeywordStatistics(this.reviewed, this.unreviewed);
        Assertions.assertFalse(kw.equals(null));
    }


    public static KeywordStatistics createKeywordStatistics(long reviewed, long unreviewed) {
        return new KeywordStatisticsImpl(reviewed, unreviewed);
    }

}
