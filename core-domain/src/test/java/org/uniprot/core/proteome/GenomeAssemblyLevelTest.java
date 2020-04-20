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
class GenomeAssemblyLevelTest {

    @Test
    void getName_toDisplayName_areSame() {
        assertSame(GenomeAssemblyLevel.FULL.getName(), GenomeAssemblyLevel.FULL.getDisplayName());
    }

    @Test
    void testToDisplayName() {
        assertSame("partial", GenomeAssemblyLevel.PARTIAL.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(GenomeAssemblyLevel.FULL, GenomeAssemblyLevel.fromValue("full"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(GenomeAssemblyLevel.PARTIAL, GenomeAssemblyLevel.fromValue("PARTIAL"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(GenomeAssemblyLevel.PARTIAL, GenomeAssemblyLevel.fromValue("ParTiaL"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnError(String val) {
            assertThrows(IllegalArgumentException.class, () -> GenomeAssemblyLevel.fromValue(val));
        }

        @Test
        void forNullWillReturnError() {
            assertThrows(IllegalArgumentException.class, () -> GenomeAssemblyLevel.fromValue(null));
        }
    }
}
