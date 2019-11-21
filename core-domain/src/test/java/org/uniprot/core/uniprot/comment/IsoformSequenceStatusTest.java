package org.uniprot.core.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class IsoformSequenceStatusTest {

    @Test
    void getValue_displayName_areNotSame() {
        assertNotSame(
                IsoformSequenceStatus.DISPLAYED.getValue(),
                IsoformSequenceStatus.DISPLAYED.toDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(IsoformSequenceStatus.EXTERNAL, IsoformSequenceStatus.typeOf("external"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(
                    IsoformSequenceStatus.DESCRIBED, IsoformSequenceStatus.typeOf("DESCRIBED"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(
                    IsoformSequenceStatus.NOT_DESCRIBED,
                    IsoformSequenceStatus.typeOf("NOT described"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "abc", "", "  "})
        void willThrowException(String val) {
            assertThrows(IllegalArgumentException.class, () -> IsoformSequenceStatus.typeOf(val));
        }

        @Test
        void willThrowException_null() {
            assertThrows(IllegalArgumentException.class, () -> IsoformSequenceStatus.typeOf(null));
        }
    }
}
