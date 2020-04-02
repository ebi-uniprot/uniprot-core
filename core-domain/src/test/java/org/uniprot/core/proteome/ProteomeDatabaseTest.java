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
                ProteomeDatabase.GENOME_ASSEMBLY.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(
                    ProteomeDatabase.GENOME_ANNOTATION,
                    ProteomeDatabase.typeOf("genomeannotation"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(
                    ProteomeDatabase.GENOME_ACCESSION, ProteomeDatabase.typeOf("GENOMEACCESSION"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(ProteomeDatabase.ASSEMBLY_ID, ProteomeDatabase.typeOf("AssEmBLYId"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillThrowException(String val) {
            assertThrows(IllegalArgumentException.class, () -> ProteomeDatabase.typeOf(val));
        }

        @Test
        void forNullWillThrowException() {
            assertThrows(IllegalArgumentException.class, () -> ProteomeDatabase.typeOf(null));
        }
    }
}
