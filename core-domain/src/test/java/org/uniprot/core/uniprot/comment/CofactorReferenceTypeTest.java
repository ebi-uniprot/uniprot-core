package org.uniprot.core.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CofactorReferenceTypeTest {

    @Test
    void nameAndDisplayNameAreSame() {
        CofactorReferenceType cofactorReferenceType = CofactorReferenceType.CHEBI;
        assertEquals(cofactorReferenceType.getName(), cofactorReferenceType.toDisplayName());
    }

    @Test
    void canConvertExact() {
        assertEquals(CofactorReferenceType.CHEBI, CofactorReferenceType.typeOf("ChEBI"));
    }

    @Test
    void canConvertAfterIgnoringCase() {
        assertEquals(CofactorReferenceType.CHEBI, CofactorReferenceType.typeOf("CHEBI"));
    }

    @Test
    void willThorwExceptionWhenNotAbleToConvert() {
        assertThrows(
                IllegalArgumentException.class, () -> CofactorReferenceType.typeOf("not exist"));
    }
}
