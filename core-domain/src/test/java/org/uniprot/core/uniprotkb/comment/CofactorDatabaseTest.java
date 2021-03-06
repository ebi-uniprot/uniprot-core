package org.uniprot.core.uniprotkb.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CofactorDatabaseTest {

    @Test
    void nameAndDisplayNameAreSame() {
        CofactorDatabase cofactorDatabase = CofactorDatabase.CHEBI;
        assertEquals(cofactorDatabase.getName(), cofactorDatabase.getDisplayName());
    }

    @Test
    void canConvertExact() {
        assertEquals(CofactorDatabase.CHEBI, CofactorDatabase.typeOf("ChEBI"));
    }

    @Test
    void canConvertAfterIgnoringCase() {
        assertEquals(CofactorDatabase.CHEBI, CofactorDatabase.typeOf("CHEBI"));
    }

    @Test
    void willThorwExceptionWhenNotAbleToConvert() {
        assertThrows(IllegalArgumentException.class, () -> CofactorDatabase.typeOf("not exist"));
    }
}
