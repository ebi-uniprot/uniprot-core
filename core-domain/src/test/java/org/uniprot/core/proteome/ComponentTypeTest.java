package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ComponentTypeTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(
                ComponentType.SEGMENTED_GENOME.getName(),
                ComponentType.SEGMENTED_GENOME.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(ComponentType.WGS, ComponentType.typeOf("wgs"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(ComponentType.CON, ComponentType.typeOf("CON"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(ComponentType.UNPLACED, ComponentType.typeOf("UNplaCED"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(ComponentType.UNPLACED, ComponentType.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(ComponentType.UNPLACED, ComponentType.typeOf(null));
        }
    }
}
