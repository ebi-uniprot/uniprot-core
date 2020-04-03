package org.uniprot.core.uniprotkb.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RnaEditingLocationTypeTest {
    @Test
    void toDisplayNameIsSame_name() {
        assertSame(
                RnaEditingLocationType.Undetermined.name(),
                RnaEditingLocationType.Undetermined.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertExactCase() {
            assertEquals(
                    RnaEditingLocationType.Undetermined,
                    RnaEditingLocationType.typeOf("Undetermined"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"0", "1", "2", "3", "4", "0abc", "8DSF"})
        void canConvertFirstDigitToKnow(String val) {
            assertEquals(RnaEditingLocationType.Known, RnaEditingLocationType.typeOf(val));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
        void willReturnKnown(String val) {
            assertEquals(RnaEditingLocationType.Unknown, RnaEditingLocationType.typeOf(val));
        }

        @ParameterizedTest
        @ValueSource(strings = {"NOT_applicable", "NOT_APPLICABLE", "not_applicable"})
        void willReturnKnown_caseSensitive(String val) {
            assertEquals(RnaEditingLocationType.Not_applicable, RnaEditingLocationType.typeOf(val));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "n", "NOT-EXIST"})
        void willReturnKnown_whenNotMatched(String val) {
            assertEquals(RnaEditingLocationType.Unknown, RnaEditingLocationType.typeOf(val));
        }

        @Test
        void willReturnKnown_null() {
            assertEquals(RnaEditingLocationType.Unknown, RnaEditingLocationType.typeOf(null));
        }
    }
}
