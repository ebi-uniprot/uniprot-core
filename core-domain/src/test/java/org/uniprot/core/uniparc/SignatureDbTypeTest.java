package org.uniprot.core.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SignatureDbTypeTest {
    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(SignatureDbType.GENE3D, SignatureDbType.typeOf("gene3d"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(SignatureDbType.PANTHER, SignatureDbType.typeOf("PANTHER"));
        }

        @Test
        void canConvertBothCases() {
            assertEquals(SignatureDbType.PIRSF, SignatureDbType.typeOf("PIrsF"));
        }

        @ParameterizedTest
        @ValueSource(
                strings = {"evidenceat protein level", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertThrows(IllegalArgumentException.class, () -> SignatureDbType.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertThrows(IllegalArgumentException.class, () -> SignatureDbType.typeOf(null));
        }
    }

    @Test
    void toDisplayName_willReturnName() {
        assertEquals("Pfam", SignatureDbType.PFAM.getDisplayName());
    }
}
