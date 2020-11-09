package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProteomeTypeTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(ProteomeType.REDUNDANT.getName(), ProteomeType.REDUNDANT.getDisplayName());
    }

    @Test
    void testToDisplayName() {
        assertSame("Excluded", ProteomeType.EXCLUDED.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(ProteomeType.REFERENCE, ProteomeType.typeOf("reference proteome"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(ProteomeType.REDUNDANT, ProteomeType.typeOf("REDUNDANT PROTEOME"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(ProteomeType.REFERENCE, ProteomeType.typeOf("RefeRENCE PRoteome"));
        }

        @Test
        void forInvalidWillReturnExceptionn() {
            assertThrows(IllegalArgumentException.class, () -> ProteomeType.typeOf("INVALID"));
        }

        @Test
        void forNullWillReturnException() {
            assertThrows(IllegalArgumentException.class, () -> ProteomeType.typeOf(null));
        }
    }
}
