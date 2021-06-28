package org.uniprot.core.uniprotkb.evidence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode.Category;

class EvidenceCodeTest {

    @ParameterizedTest
    @ValueSource(strings = {"d", "", "PKJ", " "})
    void typeOfWillThrowIllegalArgument(String code) {
        assertThrows(IllegalArgumentException.class, () -> EvidenceCode.typeOf(code));
    }

    @Test
    void displayNameAndCodeIsSame() {
        assertSame(EvidenceCode.ECO_0000250.getCode(), EvidenceCode.ECO_0000250.getDisplayName());
    }

    @Test
    void canGetTheName() {
        assertEquals("Imported information", EvidenceCode.ECO_0000313.getName());
    }

    @Test
    void canGetSource() {
        assertEquals(
                "combinatorial computational and experimental evidence used in manual assertion",
                EvidenceCode.ECO_0007744.getSource());
    }
    
    @Test
    void canGetCategory() {  	
    	  assertEquals(
    			  EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL),
                  EvidenceCode.ECO_0000314.getCategories());
    	
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
