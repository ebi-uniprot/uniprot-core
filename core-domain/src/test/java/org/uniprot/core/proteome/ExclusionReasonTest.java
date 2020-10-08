package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author lgonzales
 * @since 07/10/2020
 */
class ExclusionReasonTest {

    @Test
    void getName_toDisplayName_areSame() {
        assertSame(
                ExclusionReason.CONTAMINATED.getName(),
                ExclusionReason.CONTAMINATED.getDisplayName());
    }

    @Test
    void testToDisplayName() {
        assertSame("contaminated", ExclusionReason.CONTAMINATED.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(
                    ExclusionReason.DERIVED_FROM_METAGENOME,
                    ExclusionReason.typeOf("derived from metagenome"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(ExclusionReason.MIXED_CULTURE, ExclusionReason.typeOf("MIXED CULTURE"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(ExclusionReason.METAGENOME, ExclusionReason.typeOf("mETaGEnoME"));
        }

        @Test
        void forInvalidWillReturnExceptionn() {
            assertThrows(IllegalArgumentException.class, () -> ExclusionReason.typeOf("INVALID"));
        }

        @Test
        void forNullWillReturnException() {
            assertThrows(IllegalArgumentException.class, () -> ExclusionReason.typeOf(null));
        }
    }
}
