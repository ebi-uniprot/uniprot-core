package org.uniprot.core;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Position;
import org.uniprot.core.PositionModifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {

    @Test
    void testPositionInt() {
        Position position = new Position(12);
        verify(position, 12, PositionModifier.EXACT);
        position = new Position(null);
        verify(position, null, PositionModifier.UNKNOWN);

    }

    @Test
    void testPositionMinus() {
        Position position = new Position(-12);
        verify(position, -12, PositionModifier.UNKNOWN);

    }

    @Test
    void testPositionPositionModifier() {
        Position position = new Position(12, PositionModifier.UNSURE);
        verify(position, 12, PositionModifier.UNSURE);
        position = new Position(12, PositionModifier.OUTSIDE);
        verify(position, 12, PositionModifier.OUTSIDE);
    }

    private void verify(Position position, Integer value, PositionModifier modifier) {
        assertEquals(value, position.getValue());
        assertEquals(modifier, position.getModifier());
        TestHelper.verifyJson(position);
    }
}
