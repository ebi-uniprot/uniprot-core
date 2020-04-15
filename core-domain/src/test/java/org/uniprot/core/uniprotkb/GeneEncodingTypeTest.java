package org.uniprot.core.uniprotkb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GeneEncodingTypeTest {

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(GeneEncodingType.HYDROGENOSOME, GeneEncodingType.typeOf("hydrogenosome"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(GeneEncodingType.MITOCHONDRION, GeneEncodingType.typeOf("MITOCHONDRION"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(GeneEncodingType.UNKNOWN, GeneEncodingType.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(GeneEncodingType.UNKNOWN, GeneEncodingType.typeOf(null));
        }
    }

    @Test
    void getName_displayName_areSame() {
        assertSame(
                GeneEncodingType.NON_PHOTOSYNTHETIC_PLASTID.getName(),
                GeneEncodingType.NON_PHOTOSYNTHETIC_PLASTID.getDisplayName());
    }
}
