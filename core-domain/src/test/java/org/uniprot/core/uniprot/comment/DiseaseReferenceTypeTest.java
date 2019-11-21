package org.uniprot.core.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiseaseReferenceTypeTest {

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(DiseaseReferenceType.MIM, DiseaseReferenceType.typeOf("mim"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(DiseaseReferenceType.MIM, DiseaseReferenceType.typeOf("MIM"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(DiseaseReferenceType.MIM, DiseaseReferenceType.typeOf("miM"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "abc"})
        void willThrowException(String val) {
            assertThrows(IllegalArgumentException.class, () -> DiseaseReferenceType.typeOf(val));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "  "})
        void emptyAndSpacesAreNone(String val) {
            assertEquals(DiseaseReferenceType.NONE, DiseaseReferenceType.typeOf(val));
        }

        @Test
        void willThrowException_null() {
            assertThrows(IllegalArgumentException.class, () -> DiseaseReferenceType.typeOf(null));
        }
    }

    @Test
    void getName_displayName_areSame() {
        assertSame(DiseaseReferenceType.MIM.getName(), DiseaseReferenceType.MIM.toDisplayName());
    }
}
