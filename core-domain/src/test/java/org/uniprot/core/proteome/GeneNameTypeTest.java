package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GeneNameTypeTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(GeneNameType.ENSEMBL.getName(), GeneNameType.ENSEMBL.toDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(GeneNameType.ORF, GeneNameType.fromValue("orf"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(GeneNameType.MOD, GeneNameType.fromValue("MOD"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(GeneNameType.GENE_NAME, GeneNameType.fromValue("genE Name"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(GeneNameType.MISSING, GeneNameType.fromValue(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(GeneNameType.MISSING, GeneNameType.fromValue(null));
        }
    }
}
