package org.uniprot.core.uniprotkb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UniProtkbEntryTypeTest {

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(UniProtkbEntryType.TREMBL, UniProtkbEntryType.typeOf("trembl"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(UniProtkbEntryType.INACTIVE, UniProtkbEntryType.typeOf("INACTIVE"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(UniProtkbEntryType.UNKNOWN, UniProtkbEntryType.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(UniProtkbEntryType.UNKNOWN, UniProtkbEntryType.typeOf(null));
        }
    }

    @Test
    void getValue_displayName_areSame() {
        assertSame(
                UniProtkbEntryType.SWISSPROT.getValue(),
                UniProtkbEntryType.SWISSPROT.toDisplayName());
    }
}
