package org.uniprot.core.uniprot.feature;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FeatureXDbTypeTest {
    @Test
    void nameAndDisplayNameAreSame() {
        assertSame(FeatureXDbType.DBSNP.getName(), FeatureXDbType.DBSNP.toDisplayName());
    }

    @Test
    void canCreateFromLowerCase() {
        assertEquals(FeatureXDbType.DBSNP, FeatureXDbType.typeOf("dbsnp"));
    }

    @Test
    void canCreateFromUpperCaseName() {
        assertEquals(FeatureXDbType.DBSNP, FeatureXDbType.typeOf("DBSNP"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "bc", "   "})
    void throwsIllegalForOthers(String val) {
        assertThrows(IllegalArgumentException.class, () -> FeatureXDbType.typeOf(val));
    }

    @Test
    void nameIs() {
        assertEquals("dbSNP", FeatureXDbType.DBSNP.getName());
    }
}
