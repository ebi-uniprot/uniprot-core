package uk.ac.ebi.uniprot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RangeTest {

    @Test
    void testRangeTT() {
        Range range = new Range(12, 25);
        verify(range, new Position(12), new Position(25));
    }


    @Test
    void testRangeTTModifier() {
        Range range = new Range(12, 25, PositionModifier.OUTSIDE, PositionModifier.EXACT);
        verify(range, new Position(12, PositionModifier.OUTSIDE), new Position(25, PositionModifier.EXACT));
    }

    @Test
    void testRangePositionOfTPositionOfT() {
        Position start = new Position(null);
        Position end = new Position(25, PositionModifier.UNSURE);
        Range range = new Range(start, end);
        verify(range, start, end);
    }

    private void verify(Range range, Position start, Position end) {
        assertEquals(start, range.getStart());
        assertEquals(end, range.getEnd());
        TestHelper.verifyJson(range);
    }
}
