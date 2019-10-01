package org.uniprot.core;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PropertyTest {
    private final Property keyNull = new Property(null, "b");
    private final Property valueNull = new Property("a", null);
    private final Property bothNull = new Property(null, null);
    private final Property prop = new Property("a", "b");
    private final Property PROP = new Property("A", "B");

    @Test
    void testProperty() {
        Property pt = new Property("some key", "some value");
        assertEquals("some key", pt.getKey());
        assertEquals("some value", pt.getValue());
    }

    @Nested
    class compareTo {
        @Test
        void propertiesCanBeSort_becauseTheyImplementCompareTo() {
            Property two = new Property("2", "1");
            Property a = new Property("a", "50");
            Property z = new Property("z", "0");
            List<Property> propList = Arrays.asList(z, two, a);
            Collections.sort(propList);
            assertAll(
                    () -> assertEquals("2", propList.get(0).getKey()),
                    () -> assertEquals("a", propList.get(1).getKey()),
                    () -> assertEquals("z", propList.get(2).getKey()));
        }
    }

    @Nested
    class hashCode {

        @Test
        void hasCodeCan_copWithNullKey() {
            assertAll(
                    () -> assertNotEquals(keyNull.hashCode(), valueNull.hashCode()),
                    () -> assertNotEquals(keyNull.hashCode(), bothNull.hashCode()),
                    () -> assertNotEquals(keyNull.hashCode(), prop.hashCode()));
        }

        @Test
        void hasCodeCan_copWithNullValue() {
            assertAll(
                    () -> assertNotEquals(valueNull.hashCode(), keyNull.hashCode()),
                    () -> assertNotEquals(valueNull.hashCode(), bothNull.hashCode()),
                    () -> assertNotEquals(valueNull.hashCode(), prop.hashCode()));
        }

        @Test
        void hasCodeCan_copWithBothNull() {
            assertAll(
                    () -> assertNotEquals(bothNull.hashCode(), keyNull.hashCode()),
                    () -> assertNotEquals(bothNull.hashCode(), valueNull.hashCode()),
                    () -> assertNotEquals(bothNull.hashCode(), prop.hashCode()));
        }

        @Test
        void hasCodeCanIncludeBothStartAndEnd() {
            assertAll(
                    () -> assertNotEquals(prop.hashCode(), keyNull.hashCode()),
                    () -> assertNotEquals(prop.hashCode(), bothNull.hashCode()),
                    () -> assertNotEquals(prop.hashCode(), valueNull.hashCode()));
        }
    }

    @Nested
    class equal {

        @Test
        void caseTest() {
            assertNotEquals(prop, PROP);
        }

        @Test
        void key_null_notNull() {
            assertFalse(keyNull.equals(prop));
        }

        @Test
        void value_different() {
            Property p = new Property("a", "c");
            assertFalse(prop.equals(p));
        }

        @Test
        void value_null_notNull() {
            assertFalse(valueNull.equals(prop));
        }

        @Test
        void start_different() {
            Property p = new Property("d", "b");
            assertFalse(prop.equals(p));
        }
    }
}
