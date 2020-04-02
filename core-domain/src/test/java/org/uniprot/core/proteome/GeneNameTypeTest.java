package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GeneNameTypeTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(GeneNameType.ENSEMBL.getName(), GeneNameType.ENSEMBL.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(GeneNameType.ORF, GeneNameType.typeOf("orf"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(GeneNameType.MOD, GeneNameType.typeOf("MOD"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(GeneNameType.GENE_NAME, GeneNameType.typeOf("genE Name"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(GeneNameType.MISSING, GeneNameType.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(GeneNameType.MISSING, GeneNameType.typeOf(null));
        }
    }
}
