package org.uniprot.core.uniprotkb.feature;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UniprotKBFeatureDatabaseTest {
    @Test
    void nameAndDisplayNameAreSame() {
        assertSame(
                UniprotKBFeatureDatabase.DBSNP.getName(),
                UniprotKBFeatureDatabase.DBSNP.getDisplayName());
    }

    @Test
    void canCreateFromLowerCase() {
        assertEquals(UniprotKBFeatureDatabase.DBSNP, UniprotKBFeatureDatabase.typeOf("dbsnp"));
    }

    @Test
    void canCreateFromUpperCaseName() {
        assertEquals(UniprotKBFeatureDatabase.DBSNP, UniprotKBFeatureDatabase.typeOf("DBSNP"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "   "})
    void throwsIllegalForOthers(String val) {
        assertThrows(IllegalArgumentException.class, () -> UniprotKBFeatureDatabase.typeOf(val));
    }

    @Test
    void nameIs() {
        assertEquals("dbSNP", UniprotKBFeatureDatabase.DBSNP.getName());
    }

    @Test
    void canUseTypeOf() {
        assertEquals(UniprotKBFeatureDatabase.DBSNP, UniprotKBFeatureDatabase.typeOf("dbSNP"));
    }
}
