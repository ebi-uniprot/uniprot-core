package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProteomeXReferenceTypeTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(
                ProteomeXReferenceType.GENOME_ASSEMBLY.getName(),
                ProteomeXReferenceType.GENOME_ASSEMBLY.toDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(
                    ProteomeXReferenceType.GENOME_ANNOTATION,
                    ProteomeXReferenceType.fromValue("genomeannotation"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(
                    ProteomeXReferenceType.GENOME_ACCESSION,
                    ProteomeXReferenceType.fromValue("GENOMEACCESSION"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(
                    ProteomeXReferenceType.ASSEMBLY_ID,
                    ProteomeXReferenceType.fromValue("AssEmBLYId"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(ProteomeXReferenceType.UNKNOWN, ProteomeXReferenceType.fromValue(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(ProteomeXReferenceType.UNKNOWN, ProteomeXReferenceType.fromValue(null));
        }
    }
}
