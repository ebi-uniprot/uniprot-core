package org.uniprot.core.uniprotkb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UniProtKBEntryTypeTest {

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(
                    UniProtKBEntryType.TREMBL,
                    UniProtKBEntryType.typeOf("uniprotkb unreviewed (trembl)"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(UniProtKBEntryType.INACTIVE, UniProtKBEntryType.typeOf("INACTIVE"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertEquals(UniProtKBEntryType.UNKNOWN, UniProtKBEntryType.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertEquals(UniProtKBEntryType.UNKNOWN, UniProtKBEntryType.typeOf(null));
        }
    }

    @Test
    void getValue_displayName_areSame() {
        assertSame(
                UniProtKBEntryType.SWISSPROT.getValue(),
                UniProtKBEntryType.SWISSPROT.toDisplayName());
    }
}
