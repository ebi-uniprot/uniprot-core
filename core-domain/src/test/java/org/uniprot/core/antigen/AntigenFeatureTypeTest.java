package org.uniprot.core.antigen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author lgonzales
 * @since 11/05/2020
 */
class AntigenFeatureTypeTest {

    @Test
    void canCreateFromLowerCaseValue() {
        assertEquals(AntigenFeatureType.ANTIGEN, AntigenFeatureType.typeOf("antigen"));
    }

    @Test
    void canCreateFromUpperCaseValue() {
        assertEquals(AntigenFeatureType.ANTIGEN, AntigenFeatureType.typeOf("ANTIGEN"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "   "})
    void throwsIllegalForOthers(String val) {
        assertThrows(IllegalArgumentException.class, () -> AntigenFeatureType.typeOf(val));
    }

    @Test
    void nameAndDisplayNameAreNotEqual() {
        assertNotEquals(
                AntigenFeatureType.ANTIGEN.getDisplayName(), AntigenFeatureType.ANTIGEN.getName());
    }

    @Test
    void compareOnIsEqualsValue() {
        assertEquals(
                AntigenFeatureType.ANTIGEN.getValue(), AntigenFeatureType.ANTIGEN.getCompareOn());
    }
}
