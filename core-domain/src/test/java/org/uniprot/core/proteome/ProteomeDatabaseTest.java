package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProteomeDatabaseTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(
                ProteomeDatabase.GENOME_ASSEMBLY.getName(),
                ProteomeDatabase.GENOME_ASSEMBLY.toDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(
                    ProteomeDatabase.GENOME_ANNOTATION,
                    ProteomeDatabase.fromValue("genomeannotation"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(
                    ProteomeDatabase.GENOME_ACCESSION,
                    ProteomeDatabase.fromValue("GENOMEACCESSION"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(ProteomeDatabase.ASSEMBLY_ID, ProteomeDatabase.fromValue("AssEmBLYId"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(ProteomeDatabase.UNKNOWN, ProteomeDatabase.fromValue(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(ProteomeDatabase.UNKNOWN, ProteomeDatabase.fromValue(null));
        }
    }
}
