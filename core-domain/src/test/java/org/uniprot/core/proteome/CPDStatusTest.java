package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author lgonzales
 * @since 15/04/2020
 */
class CPDStatusTest {

    @Test
    void getName_toDisplayName_areSame() {
        assertSame(CPDStatus.STANDARD.getName(), CPDStatus.STANDARD.getDisplayName());
    }

    @Test
    void testToDisplayName() {
        assertSame("Standard", CPDStatus.STANDARD.getDisplayName());
    }

    @Test
    void testGetId() {
        assertSame(4, CPDStatus.OUTLIER_HIGH.getId());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(CPDStatus.CLOSE_TO_STANDARD_HIGH, CPDStatus.fromValue("close to standard (high value)"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(CPDStatus.OUTLIER_HIGH, CPDStatus.fromValue("OUTLIER (high value)"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(CPDStatus.STANDARD, CPDStatus.fromValue("StAnDarD"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(CPDStatus.UNKNOWN, CPDStatus.fromValue(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(CPDStatus.UNKNOWN, CPDStatus.fromValue(null));
        }
    }
}
