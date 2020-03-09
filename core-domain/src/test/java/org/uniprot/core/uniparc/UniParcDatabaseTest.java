package org.uniprot.core.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UniParcDatabaseTest {
    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(UniParcDatabase.ENSEMBL_VERTEBRATE, UniParcDatabase.typeOf("ensembl"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(UniParcDatabase.IPI, UniParcDatabase.typeOf("IPI"));
        }

        @Test
        void canConvertBothCases() {
            assertEquals(UniParcDatabase.SGD, UniParcDatabase.typeOf("SgD"));
        }

        @ParameterizedTest
        @ValueSource(
                strings = {"evidenceat protein level", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertThrows(IllegalArgumentException.class, () -> UniParcDatabase.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertThrows(IllegalArgumentException.class, () -> UniParcDatabase.typeOf(null));
        }
    }

    @Test
    void getName_toDisplayName_areSame() {
        assertSame(UniParcDatabase.VEGA.getName(), UniParcDatabase.VEGA.toDisplayName());
    }

    @Test
    void canTellWhichDataBaseAlive() {
        assertTrue(UniParcDatabase.TAIR_ARABIDOPSIS.isAlive());
    }
}
