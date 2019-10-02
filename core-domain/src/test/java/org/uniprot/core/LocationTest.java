package org.uniprot.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 23 May 2019
 */
class LocationTest {

    @Test
    void testLocationIntInt() {
        Location location = new Location(23, 91);
        assertEquals(23, location.getStart());
        assertEquals(91, location.getEnd());
    }

    @Test
    void start_end_partOfHashCode() {
        Location location = new Location(1, 2);
        assertEquals(994, location.hashCode());
    }

    @Nested
    class equals {
        Location location = new Location(-123, 25);

        @Test
        void location_equal_null() {
            assertNotEquals(location, null);
        }

        @Test
        void location_equal_obj() {
            assertNotEquals(location, new Object());
        }

        @Test
        void location_equal_itself() {
            assertEquals(location, location);
        }

        @Test
        void location_equal_otherLocation() {
            assertEquals(location, new Location(-123, 25));
        }
    }
}
