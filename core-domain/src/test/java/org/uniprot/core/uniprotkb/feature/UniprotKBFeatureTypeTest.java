package org.uniprot.core.uniprotkb.feature;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @ParameterizedTest(name = "Starts with capital? : {0}")
    @MethodSource("featureTypeDisplayNames")
    void displayNameAlwaysStartsWithCapital(String featureTypeDisplayName) {
        char firstChar = featureTypeDisplayName.charAt(0);
        assertThat(Character.isUpperCase(firstChar), is(true));
    }

    private static Stream<Arguments> featureTypeDisplayNames() {
        return Stream.of(UniprotKBFeatureType.values())
                .map(UniprotKBFeatureType::getDisplayName)
                .map(Arguments::of);
    }
}
