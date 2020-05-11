package org.uniprot.core.uniprotkb.feature;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UniprotKBFeatureTypeTest {

    @Test
    void canCreateFromLowerCaseValue() {
        assertEquals(
                UniprotKBFeatureType.CARBOHYD, UniprotKBFeatureType.typeOf("glycosylation site"));
    }

    @Test
    void canCreateFromUpperCaseValue() {
        assertEquals(UniprotKBFeatureType.DOMAIN, UniprotKBFeatureType.typeOf("DOMAIN"));
    }

    @Test
    void canCreateFromLowerCaseName() {
        assertEquals(UniprotKBFeatureType.HELIX, UniprotKBFeatureType.typeOf("helix"));
    }

    @Test
    void canCreateFromUpperCaseName() {
        assertEquals(UniprotKBFeatureType.NON_CONS, UniprotKBFeatureType.typeOf("NON_CONS"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "   "})
    void throwsIllegalForOthers(String val) {
        assertThrows(IllegalArgumentException.class, () -> UniprotKBFeatureType.typeOf(val));
    }

    @Test
    void valueAndDisplayNameAreEqual() {
        assertEquals(
                UniprotKBFeatureType.CHAIN.getDisplayName(), UniprotKBFeatureType.CHAIN.getValue());
    }

    @Test
    void nameAndDisplayNameAreNotEqual() {
        assertNotEquals(
                UniprotKBFeatureType.CHAIN.getDisplayName(), UniprotKBFeatureType.CHAIN.getName());
    }

    @Test
    void canReturnCategory() {
        assertEquals(FeatureCategory.MOLECULE_PROCESSING, UniprotKBFeatureType.CHAIN.getCategory());
    }

    @Test
    void canFindTypeOfWithName() {
        assertEquals(UniprotKBFeatureType.CA_BIND, UniprotKBFeatureType.typeOf("CA_BIND"));
    }

    @Test
    void canFindTypeOfWithValue() {
        assertEquals(UniprotKBFeatureType.ACT_SITE, UniprotKBFeatureType.typeOf("active site"));
    }

    @Test
    void compareOnIsEqualsValue() {
        assertEquals(
                UniprotKBFeatureType.CHAIN.getValue(), UniprotKBFeatureType.CHAIN.getCompareOn());
    }
}
