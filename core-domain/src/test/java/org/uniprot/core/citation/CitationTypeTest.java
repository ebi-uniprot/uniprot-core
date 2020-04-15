package org.uniprot.core.citation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class CitationTypeTest {

    @ParameterizedTest
    @EnumSource(CitationType.class)
    void getName_getCompareOn_areSame(CitationType CT) {
        assertSame(CT.getName(), CT.getCompareOn());
    }

    @Test
    void forSUBMISSION_getDescription_getDisplayName_notEqual() {
        assertNotEquals(
                CitationType.SUBMISSION.getDescription(), CitationType.SUBMISSION.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(CitationType.BOOK, CitationType.typeOf("book"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(CitationType.PATENT, CitationType.typeOf("PATENT"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(CitationType.THESIS, CitationType.typeOf("ThEsiS"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertThrows(IllegalArgumentException.class, () -> CitationType.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertThrows(IllegalArgumentException.class, () -> CitationType.typeOf(null));
        }
    }
}
