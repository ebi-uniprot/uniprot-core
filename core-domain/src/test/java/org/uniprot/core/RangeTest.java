package org.uniprot.core;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        Integer tmp = null;
        Range endNull = new Range(1, tmp);
        Range startNull = new Range(tmp, 2);
        Range bothNull = new Range(tmp, tmp);
        Range range = new Range(1, 2);

        @Test
        void hasCodeCanIncludeStart(){
            assertAll(
              () -> assertNotEquals(endNull.hashCode(), startNull.hashCode()),
              () -> assertNotEquals(endNull.hashCode(), bothNull.hashCode()),
              () -> assertNotEquals(endNull.hashCode(), range.hashCode())
            );
        }

      @Test
      void hasCodeCanIncludeEnd(){
        assertAll(
          () -> assertNotEquals(startNull.hashCode(), endNull.hashCode()),
          () -> assertNotEquals(startNull.hashCode(), bothNull.hashCode()),
          () -> assertNotEquals(startNull.hashCode(), range.hashCode())
        );
      }

      @Test
      void hasCodeCanBeComputed_everyThingIsNull(){
        assertAll(
          () -> assertNotEquals(bothNull.hashCode(), endNull.hashCode()),
          () -> assertNotEquals(bothNull.hashCode(), startNull.hashCode()),
          () -> assertNotEquals(bothNull.hashCode(), range.hashCode())
        );
      }

      @Test
      void hasCodeCanIncludeBothStartAndEnd(){
        assertAll(
          () -> assertNotEquals(range.hashCode(), endNull.hashCode()),
          () -> assertNotEquals(range.hashCode(), bothNull.hashCode()),
          () -> assertNotEquals(range.hashCode(), startNull.hashCode())
        );
      }
    }

    @Nested
    class equal{
      Position one = new Position(1);
      Position two = new Position(2);
      Position nul = null;
      @Test
      void end_null_notNull(){
        Range r1 = new Range(one,two);
        Range r2 = new Range(one, nul);
        assertFalse(r2.equals(r1));
      }

      @Test
      void end_different(){
        Range r1 = new Range(one,two);
        Range r2 = new Range(one, one);
        assertFalse(r2.equals(r1));
      }

      @Test
      void start_null_notNull(){
        Range r1 = new Range(nul,two);
        Range r2 = new Range(one, two);
        assertFalse(r1.equals(r2));
      }

      @Test
      void start_different(){
        Range r1 = new Range(one,two);
        Range r2 = new Range(two, two);
        assertFalse(r2.equals(r1));
      }
    }

    private void verify(Range range, Position start, Position end) {
        assertEquals(start, range.getStart());
        assertEquals(end, range.getEnd());
    }
}
