package org.uniprot.core.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SequenceCautionTypeTest {
    @Test
    void displayNameReturnValue() {
        assertEquals(
                "Erroneous gene model prediction",
                SequenceCautionType.ERRONEOUS_PREDICTION.toDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(
                    SequenceCautionType.ERRONEOUS_INITIATION,
                    SequenceCautionType.typeOf("erroneous initiation"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(SequenceCautionType.FRAMESHIFT, SequenceCautionType.typeOf("FRAMESHIFT"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(
                    SequenceCautionType.ERRONEOUS_TERMINATION,
                    SequenceCautionType.typeOf("erROneOUS TerMINAtioN"));
        }

        @ParameterizedTest
        @ValueSource(
                strings = {"ERRONEOUS TERMIINATION", "uniprotkbid", "UNIPARCID", "", "abc", "  "})
        void willThrowException(String val) {
            assertThrows(IllegalArgumentException.class, () -> SequenceCautionType.typeOf(val));
        }

        @Test
        void willThrowException_null() {
            assertThrows(IllegalArgumentException.class, () -> SequenceCautionType.typeOf(null));
        }
    }
}
