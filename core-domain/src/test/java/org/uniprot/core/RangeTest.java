package org.uniprot.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RangeTest {

    @Test
    void testRangeTT() {
        Range range = new Range(12, 25);
        verify(range, new Position(12), new Position(25));
    }

    @Test
    void testRangeTTModifier() {
        Range range = new Range(12, 25, PositionModifier.OUTSIDE, PositionModifier.EXACT);
        verify(
                range,
                new Position(12, PositionModifier.OUTSIDE),
                new Position(25, PositionModifier.EXACT));
    }

    @Test
    void testRangePositionOfTPositionOfT() {
        Position start = new Position(null);
        Position end = new Position(25, PositionModifier.UNSURE);
        Range range = new Range(start, end);
        verify(range, start, end);
    }

    @Nested
    class hashCode {
        @Test
        void hasCodeCanInclude(){
            Range range = new Range(1, null);
            verify(range, range.getStart(), range.getEnd());
        }
    }

    private void verify(Range range, Position start, Position end) {
        assertEquals(start, range.getStart());
        assertEquals(end, range.getEnd());
    }
}
