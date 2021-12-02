package org.uniprot.core.uniprotkb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class UniProtKBEntryTypeTest {

    @Nested
    class TypeOf {
        @ParameterizedTest
        @CsvSource({
            "uniprotkb unreviewed (trembl),trembl",
            "UniProtKB unreviewed (TrEMBL),trembl",
            "trembl,trembl",
            "TrEMBL,trembl",
            "uniprotkb reviewed (swiss-prot),swiss-prot",
            "uniprotKB reviewed (Swiss-Prot),swiss-prot",
            "swiss-prot,swiss-prot",
            "Swiss-Prot,swiss-prot",
            "Inactive,inactive",
            "inactive,inactive",
            "sausage-monkey,unknown"
        })
        void canConvertDatasetCorrectly(String input, String expected) {
            UniProtKBEntryType expectedType = UniProtKBEntryType.UNKNOWN;
            if ("trembl".equalsIgnoreCase(expected)) {
                expectedType = UniProtKBEntryType.TREMBL;
            } else if ("swiss-prot".equalsIgnoreCase(expected)) {
                expectedType = UniProtKBEntryType.SWISSPROT;
            } else if ("inactive".equalsIgnoreCase(expected)) {
                expectedType = UniProtKBEntryType.INACTIVE;
            }

            assertEquals(expectedType, UniProtKBEntryType.typeOf(input));
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
                UniProtKBEntryType.SWISSPROT.getName(),
                UniProtKBEntryType.SWISSPROT.getDisplayName());
    }
}
