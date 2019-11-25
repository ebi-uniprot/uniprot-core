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
                ComponentType.SEGMENTED_GENOME.toDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(ComponentType.WGS, ComponentType.fromValue("wgs"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(ComponentType.CON, ComponentType.fromValue("CON"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(ComponentType.UNPLACED, ComponentType.fromValue("UNplaCED"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(ComponentType.UNPLACED, ComponentType.fromValue(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(ComponentType.UNPLACED, ComponentType.fromValue(null));
        }
    }
}
