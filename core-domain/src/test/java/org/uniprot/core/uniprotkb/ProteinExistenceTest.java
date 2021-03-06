package org.uniprot.core.uniprotkb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProteinExistenceTest {

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(
                    ProteinExistence.PROTEIN_LEVEL,
                    ProteinExistence.typeOf("evidence at protein level"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(ProteinExistence.UNCERTAIN, ProteinExistence.typeOf("UNCERTAIN"));
        }

        @ParameterizedTest
        @ValueSource(
                strings = {"evidenceat protein level", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(ProteinExistence.UNKNOWN, ProteinExistence.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(ProteinExistence.UNKNOWN, ProteinExistence.typeOf(null));
        }
    }

    @Test
    void canGetId() {
        assertEquals(2, ProteinExistence.TRANSCRIPT_LEVEL.getId());
    }

    @Test
    void getValue_displayName_areNotSame() {
        assertNotEquals(
                ProteinExistence.TRANSCRIPT_LEVEL.getName(),
                ProteinExistence.TRANSCRIPT_LEVEL.getDisplayName());
    }

    @Test
    void getValue_toString_areSame() {
        assertSame(ProteinExistence.HOMOLOGY.getName(), ProteinExistence.HOMOLOGY.toString());
    }
}
