package org.uniprot.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void nullValueWithModifierAreEqual() {
        Position nullP1 = new Position(null, PositionModifier.UNSURE);
        Position nullP2 = new Position(null, PositionModifier.UNSURE);
        assertTrue(nullP1.equals(nullP2));
    }

    @Nested
    class compareTo {
        @Test
        void positionCanBeSort_becauseTheyImplementCompareTo_positionModifier() {
            Position exact = new Position(2);
            Position unknown = new Position(2, PositionModifier.UNKNOWN);
            Position unsure = new Position(2, PositionModifier.UNSURE);
            List<Position> propList = Arrays.asList(unknown, exact, unsure);
            Collections.sort(propList);
            assertAll(
                    () -> assertEquals(PositionModifier.EXACT, propList.get(0).getModifier()),
                    () -> assertEquals(PositionModifier.UNSURE, propList.get(1).getModifier()),
                    () -> assertEquals(PositionModifier.UNKNOWN, propList.get(2).getModifier()));
        }

        @Test
        void positionCanBeSort_whenAllUnknown_retainOrder() {
            Position two = new Position(2, PositionModifier.UNKNOWN);
            Position three = new Position(3, PositionModifier.UNKNOWN);
            Position five = new Position(5, PositionModifier.UNKNOWN);
            List<Position> propList = Arrays.asList(five, two, three);
            Collections.sort(propList);
            assertAll(
                    () -> assertEquals(5, propList.get(0).getValue().intValue()),
                    () -> assertEquals(2, propList.get(1).getValue().intValue()),
                    () -> assertEquals(3, propList.get(2).getValue().intValue()));
        }

        @Test
        void positionCanBeSort_becauseTheyImplementCompareTo() {
            Position two = new Position(2);
            Position one = new Position(1);
            Position three = new Position(3);
            List<Position> propList = Arrays.asList(two, one, three);
            Collections.sort(propList);
            assertAll(
                    () -> assertEquals(1, propList.get(0).getValue().intValue()),
                    () -> assertEquals(2, propList.get(1).getValue().intValue()),
                    () -> assertEquals(3, propList.get(2).getValue().intValue()));
        }

        @Test
        void positionCanBeSort_unknownWillHaveLowestPriority() {
            Position two = new Position(2, PositionModifier.UNSURE);
            Position five = new Position(5, PositionModifier.UNKNOWN);
            Position one = new Position(1);
            List<Position> propList = Arrays.asList(two, five, one);
            Collections.sort(propList);
            assertAll(
              () -> assertEquals(1, propList.get(0).getValue().intValue()),
              () -> assertEquals(5, propList.get(2).getValue().intValue())
            );
        }
    }

    private void verify(Position position, Integer value, PositionModifier modifier) {
        assertEquals(value, position.getValue());
        assertEquals(modifier, position.getModifier());
    }
}
