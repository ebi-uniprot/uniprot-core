package org.uniprot.core.uniprotkb.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MichaelisConstantUnitTest {
    @Test
    void getName_displayName_getCompareOn_allAreSame() {
        assertSame(
                MichaelisConstantUnit.MICRO_MOL.getName(),
                MichaelisConstantUnit.MICRO_MOL.getDisplayName());
        assertSame(
                MichaelisConstantUnit.MICRO_MOL.getName(),
                MichaelisConstantUnit.MICRO_MOL.getCompareOn());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertExactCase() {
            assertEquals(MichaelisConstantUnit.MILLI_MOL, MichaelisConstantUnit.typeOf("mM"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"m", "MM", "Mg/ML"})
        void willThrowException_caseSensitive(String val) {
            assertThrows(RuntimeException.class, () -> MichaelisConstantUnit.typeOf(val));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
        void willThrowException(String val) {
            assertThrows(RuntimeException.class, () -> MichaelisConstantUnit.typeOf(val));
        }

        @Test
        void willThrowException_null() {
            assertThrows(RuntimeException.class, () -> MichaelisConstantUnit.typeOf(null));
        }
    }
}
