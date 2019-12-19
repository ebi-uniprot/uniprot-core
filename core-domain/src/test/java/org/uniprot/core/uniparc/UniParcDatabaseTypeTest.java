package org.uniprot.core.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UniParcDatabaseTypeTest {
    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(
                    UniParcDatabaseType.ENSEMBL_VERTEBRATE, UniParcDatabaseType.typeOf("ensembl"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(UniParcDatabaseType.IPI, UniParcDatabaseType.typeOf("IPI"));
        }

        @Test
        void canConvertBothCases() {
            assertEquals(UniParcDatabaseType.SGD, UniParcDatabaseType.typeOf("SgD"));
        }

        @ParameterizedTest
        @ValueSource(
                strings = {"evidenceat protein level", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertThrows(IllegalArgumentException.class, () -> UniParcDatabaseType.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertThrows(IllegalArgumentException.class, () -> UniParcDatabaseType.typeOf(null));
        }
    }

    @Test
    void getName_toDisplayName_areSame() {
        assertSame(UniParcDatabaseType.VEGA.getName(), UniParcDatabaseType.VEGA.toDisplayName());
    }

    @Test
    void canTellWhichDataBaseAlive() {
        assertTrue(UniParcDatabaseType.TAIR_ARABIDOPSIS.isAlive());
    }
}
