package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author lgonzales
 * @since 15/04/2020
 */
class GenomeAssemblySourceTest {

    @Test
    void getName_toDisplayName_areSame() {
        assertSame(
                GenomeAssemblySource.ENSEMBL.getName(),
                GenomeAssemblySource.ENSEMBL.getDisplayName());
    }

    @Test
    void testToDisplayName() {
        assertSame("EnsemblFungi", GenomeAssemblySource.ENSEMBLFUNGI.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(
                    GenomeAssemblySource.ENSEMBLPLANTS,
                    GenomeAssemblySource.fromValue("ensemblplants"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(GenomeAssemblySource.REFSEQ, GenomeAssemblySource.fromValue("REFSEQ"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(
                    GenomeAssemblySource.REFSEQ, GenomeAssemblySource.fromValue("RefSeq"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnError(String val) {
            assertThrows(IllegalArgumentException.class, () -> GenomeAssemblySource.fromValue(val));
        }

        @Test
        void forNullWillReturnError() {
            assertThrows(
                    IllegalArgumentException.class, () -> GenomeAssemblySource.fromValue(null));
        }
    }
}
