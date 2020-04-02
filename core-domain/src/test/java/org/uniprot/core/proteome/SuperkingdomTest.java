package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SuperkingdomTest {

    @Test
    void getName_toDisplayName_areSame() {
        assertSame(Superkingdom.EUKARYOTA.getName(), Superkingdom.EUKARYOTA.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(Superkingdom.ARCHAEA, Superkingdom.typeOf("archaea"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(Superkingdom.VIRUSES, Superkingdom.typeOf("VIRUSES"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(Superkingdom.BACTERIA, Superkingdom.typeOf("BActErIa"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertThrows(IllegalArgumentException.class, () -> Superkingdom.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertThrows(IllegalArgumentException.class, () -> Superkingdom.typeOf(null));
        }
    }
}
