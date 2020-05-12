package org.uniprot.core.antigen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author lgonzales
 * @since 11/05/2020
 */
class AntigenDatabaseTest {

    @Test
    void canUseTypeOf() {
        assertEquals(AntigenDatabase.ENSEMBL, AntigenDatabase.typeOf("ENSEMBL"));
    }

    @Test
    void canUseTypeOfEnsembl() {
        assertEquals(AntigenDatabase.ENSEMBL, AntigenDatabase.typeOf("Ensembl"));
    }
}
