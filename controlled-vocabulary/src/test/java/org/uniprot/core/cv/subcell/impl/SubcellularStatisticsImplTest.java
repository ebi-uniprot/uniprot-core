package org.uniprot.core.cv.subcell.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.subcell.SubcellularLocationStatistics;

import java.util.concurrent.ThreadLocalRandom;

public class SubcellularStatisticsImplTest {

    private long reviewed;
    private long unreviewed;

    @BeforeEach
    void setUp(){
        this.reviewed = ThreadLocalRandom.current().nextLong(100000);
        this.unreviewed = ThreadLocalRandom.current().nextLong(100000);
    }

    @Test
    void testCreateKeywordStats(){
        SubcellularLocationStatistics kw = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Assertions.assertEquals(this.reviewed, kw.getReviewedProteinCount());
        Assertions.assertEquals(this.unreviewed, kw.getUnreviewedProteinCount());
    }

    @Test
    void testValueEqual(){
        SubcellularLocationStatistics sc1 = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        SubcellularLocationStatistics sc2 = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Assertions.assertTrue(sc1.equals(sc2));
        Assertions.assertTrue(sc1.hashCode() == sc2.hashCode());
    }

    @Test
    void testValueNotEqual(){
        SubcellularLocationStatistics sc1 = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        this.unreviewed = this.unreviewed - 1;
        SubcellularLocationStatistics kw2 = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Assertions.assertFalse(sc1.equals(kw2));
    }

    @Test
    void testRefEqual(){
        SubcellularLocationStatistics sc1 = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Assertions.assertTrue(sc1.equals(sc1));
        Assertions.assertTrue(sc1.hashCode() == sc1.hashCode());
    }

    @Test
    void testEqualWithNull(){
        SubcellularLocationStatistics sc = createSubcellularLocationStatistics(this.reviewed, this.unreviewed);
        Assertions.assertFalse(sc.equals(null));
    }


    public static SubcellularLocationStatistics createSubcellularLocationStatistics(long reviewed, long unreviewed) {
        return new SubcellularLocationStatisticsImpl(reviewed, unreviewed);
    }

}
