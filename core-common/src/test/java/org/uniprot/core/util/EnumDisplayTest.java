package org.uniprot.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

public class EnumDisplayTest {
    enum FakeEnumDisplay implements EnumDisplay {
        ENUM_1("enum1"),
        ENUM_2("enum2"),
        ENUM_3("enum3");

        private String name;

        FakeEnumDisplay(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }
    }

    @Nested
    class typeOf {
        @Test
        void testTypeOf() {
            FakeEnumDisplay enumConst = EnumDisplay.typeOf("enum1", FakeEnumDisplay.class);
            Assertions.assertEquals(enumConst, FakeEnumDisplay.ENUM_1);
        }

        @Test
        void testTypeOfWithSpaceInName() {
            FakeEnumDisplay enumConst = EnumDisplay.typeOf(" enum2 ", FakeEnumDisplay.class);
            Assertions.assertEquals(enumConst, FakeEnumDisplay.ENUM_2);
        }

        @Test
        void testTypeOfWithMixedCaseName() {
            FakeEnumDisplay enumConst = EnumDisplay.typeOf("EnUm3", FakeEnumDisplay.class);
            Assertions.assertEquals(enumConst, FakeEnumDisplay.ENUM_3);
        }

        @Test
        void testTypeOfWithMixedCaseAndSpaceInName() {
            FakeEnumDisplay enumConst = EnumDisplay.typeOf("EnUm2 ", FakeEnumDisplay.class);
            Assertions.assertEquals(enumConst, FakeEnumDisplay.ENUM_2);
        }

        @Test
        void testTypeOfWithNullName() {
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> EnumDisplay.typeOf(null, FakeEnumDisplay.class));
        }

        @Test
        void testTypeOfWithNullEnumClass() {
            Assertions.assertThrows(
                    IllegalArgumentException.class, () -> EnumDisplay.typeOf("enum1", null));
        }

        @Test
        void testTypeOfWithNonMatchingName() {
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> EnumDisplay.typeOf("random ", FakeEnumDisplay.class));
        }
    }

    @Nested
    class typeOfWithDefault {
        @Test
        void testTypeOf() {
            FakeEnumDisplay enumConst =
                    EnumDisplay.typeOf("enum1", FakeEnumDisplay.class, FakeEnumDisplay.ENUM_3);
            Assertions.assertEquals(enumConst, FakeEnumDisplay.ENUM_1);
        }

        @Test
        void testTypeOfWithNonMatchingName() {
            FakeEnumDisplay enumConst =
                    EnumDisplay.typeOf("random", FakeEnumDisplay.class, FakeEnumDisplay.ENUM_3);
            Assertions.assertEquals(enumConst, FakeEnumDisplay.ENUM_3);
        }

        @Test
        void testTypeOfWithNullName() {
            FakeEnumDisplay enumConst =
                    EnumDisplay.typeOf(null, FakeEnumDisplay.class, FakeEnumDisplay.ENUM_1);
            Assertions.assertEquals(enumConst, FakeEnumDisplay.ENUM_1);
        }

        @Test
        void testTypeOfWithNullEnumClass() {
            FakeEnumDisplay enumConst = EnumDisplay.typeOf("enum1", null, FakeEnumDisplay.ENUM_2);
            Assertions.assertEquals(enumConst, FakeEnumDisplay.ENUM_2);
        }

        @Test
        void testTypeOfWithNullDefault() {
            FakeEnumDisplay enumConst = EnumDisplay.typeOf("random", FakeEnumDisplay.class, null);
            Assertions.assertNull(enumConst);
        }
    }
}
