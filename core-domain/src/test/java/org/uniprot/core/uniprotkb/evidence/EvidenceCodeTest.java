package org.uniprot.core.uniprotkb.evidence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class EvidenceCodeTest {

    @ParameterizedTest
    @ValueSource(strings = {"d", "", "PKJ", " "})
    void typeOfWillThrowIllegalArgument(String code) {
        assertThrows(IllegalArgumentException.class, () -> EvidenceCode.typeOf(code));
    }

    @Test
    void displayNameAndCodeIsSame() {
        assertSame(EvidenceCode.ECO_0000250.getCode(), EvidenceCode.ECO_0000250.toDisplayName());
    }

    @Test
    void canGetTheName() {
        assertEquals("Imported information", EvidenceCode.ECO_0000313.getName());
    }

    @Test
    void canGetSource() {
        assertEquals(
                "a PDB entry or literature reference (for large-scale proteomics publications)",
                EvidenceCode.ECO_0000244.getSource());
    }

    @ParameterizedTest
    @EnumSource(EvidenceCode.class)
    void everyEnumShouldHaveAtLeastOneCategory(EvidenceCode enm) {
        assertTrue(enm.getCategories().size() > 0);
    }

    @ParameterizedTest
    @EnumSource(EvidenceCode.class)
    void everyEnumShouldHaveAtLeastOneLabel(EvidenceCode enm) {
        assertTrue(enm.getLabels().size() > 0);
    }
}
