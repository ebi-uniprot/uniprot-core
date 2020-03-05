package org.uniprot.core.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiseaseDatabaseTest {

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(DiseaseDatabase.MIM, DiseaseDatabase.typeOf("mim"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(DiseaseDatabase.MIM, DiseaseDatabase.typeOf("MIM"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(DiseaseDatabase.MIM, DiseaseDatabase.typeOf("miM"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "abc"})
        void willThrowException(String val) {
            assertThrows(IllegalArgumentException.class, () -> DiseaseDatabase.typeOf(val));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "  "})
        void emptyAndSpacesAreNone(String val) {
            assertEquals(DiseaseDatabase.NONE, DiseaseDatabase.typeOf(val));
        }

        @Test
        void willThrowException_null() {
            assertThrows(IllegalArgumentException.class, () -> DiseaseDatabase.typeOf(null));
        }
    }

    @Test
    void getName_displayName_areSame() {
        assertSame(DiseaseDatabase.MIM.getName(), DiseaseDatabase.MIM.toDisplayName());
    }
}
