package org.uniprot.core.uniref.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.OverlapRegion;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
class OverlapRegionBuilderTest {

    @Test
    void testFrom() {

        int start = 50;
        int end = 65;

        OverlapRegion overlapRegion = new OverlapRegionBuilder().start(start).end(end).build();
        OverlapRegion overlapRegion2 = OverlapRegionBuilder.from(overlapRegion).build();
        assertEquals(overlapRegion, overlapRegion2);
    }

    @Test
    void testStartEnd() {

        int start = 50;
        int end = 65;

        OverlapRegion overlapRegion = new OverlapRegionBuilder().start(start).end(end).build();
        assertEquals(start, overlapRegion.getStart());
        assertEquals(end, overlapRegion.getEnd());
    }

    @Test
    void twoDifferentObjects_defaultBuild_equal() {
        OverlapRegion or1 = new OverlapRegionBuilder().build();
        OverlapRegion or2 = new OverlapRegionBuilder().build();
        assertTrue(or1.equals(or2) && or2.equals(or1));
        assertEquals(or1.hashCode(), or2.hashCode());
    }
}
