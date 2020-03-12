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
                MassSpectrometryMethod.ELECTROSPRAY.getValue(),
                MassSpectrometryMethod.ELECTROSPRAY.toDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(MassSpectrometryMethod.FAB, MassSpectrometryMethod.toType("fab"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(MassSpectrometryMethod.LSI, MassSpectrometryMethod.toType("LSI"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(MassSpectrometryMethod.SELDI, MassSpectrometryMethod.toType("sEldI"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
        void willBeUnknown(String val) {
            assertEquals(MassSpectrometryMethod.UNKNOWN, MassSpectrometryMethod.toType(val));
        }

        @Test
        void willBeUnknown_null() {
            assertEquals(MassSpectrometryMethod.UNKNOWN, MassSpectrometryMethod.toType(null));
        }
    }
}
