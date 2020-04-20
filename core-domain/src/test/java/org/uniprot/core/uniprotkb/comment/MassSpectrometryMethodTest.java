package org.uniprot.core.uniprotkb.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MassSpectrometryMethodTest {

    @Test
    void getValue_displayName_areSame() {
        assertSame(
                MassSpectrometryMethod.ELECTROSPRAY.getName(),
                MassSpectrometryMethod.ELECTROSPRAY.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(MassSpectrometryMethod.FAB, MassSpectrometryMethod.typeOf("fab"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(MassSpectrometryMethod.LSI, MassSpectrometryMethod.typeOf("LSI"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(MassSpectrometryMethod.SELDI, MassSpectrometryMethod.typeOf("sEldI"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
        void whileConvertingFromFlatFile_anythingElse_willBeUnknown(String val) {
            assertEquals(MassSpectrometryMethod.UNKNOWN, MassSpectrometryMethod.typeOf(val));
        }

        @Test
        void whileConvertingFromFlatFile_null_willBeUnknown() {
            assertEquals(MassSpectrometryMethod.UNKNOWN, MassSpectrometryMethod.typeOf(null));
        }

        @Test
        void flatFileCanConvertFromAPI() {
            assertEquals(MassSpectrometryMethod.API, MassSpectrometryMethod.typeOf("API"));
        }
    }
}
