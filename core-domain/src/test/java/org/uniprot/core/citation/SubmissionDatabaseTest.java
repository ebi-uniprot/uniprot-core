package org.uniprot.core.citation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SubmissionDatabaseTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(
                SubmissionDatabase.EMBL_GENBANK_DDBJ.getName(),
                SubmissionDatabase.EMBL_GENBANK_DDBJ.toDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(SubmissionDatabase.PDB, SubmissionDatabase.typeOf("pdb data bank"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(SubmissionDatabase.UNIPROTKB, SubmissionDatabase.typeOf("UNIPROTKB"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(SubmissionDatabase.PIR, SubmissionDatabase.typeOf("PIR data BANK"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertThrows(IllegalArgumentException.class, () -> SubmissionDatabase.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertThrows(IllegalArgumentException.class, () -> SubmissionDatabase.typeOf(null));
        }
    }
}
