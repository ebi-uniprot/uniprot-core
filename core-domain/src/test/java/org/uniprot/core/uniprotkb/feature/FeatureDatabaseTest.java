package org.uniprot.core.uniprotkb.feature;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FeatureDatabaseTest {
    @Test
    void nameAndDisplayNameAreSame() {
        assertSame(FeatureDatabase.DBSNP.getName(), FeatureDatabase.DBSNP.getDisplayName());
    }

    @Test
    void canCreateFromLowerCase() {
        assertEquals(FeatureDatabase.DBSNP, FeatureDatabase.typeOf("dbsnp"));
    }

    @Test
    void canCreateFromUpperCaseName() {
        assertEquals(FeatureDatabase.DBSNP, FeatureDatabase.typeOf("DBSNP"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "   "})
    void throwsIllegalForOthers(String val) {
        assertThrows(IllegalArgumentException.class, () -> FeatureDatabase.typeOf(val));
    }

    @Test
    void nameIs() {
        assertEquals("dbSNP", FeatureDatabase.DBSNP.getName());
    }
}
