package org.uniprot.core.uniref;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class UniRefMemberIdTypeTest {

    @ParameterizedTest
    @EnumSource(UniRefMemberIdType.class)
    void displayName(UniRefMemberIdType enm) {
        assertTrue(
                Arrays.asList(
                                "UniProtKB ID",
                                "UniParc",
                                "Reviewed (Swiss-Prot)",
                                "Unreviewed (TrEMBL)")
                        .contains(enm.getDisplayName()));
    }

    @Test
    void testDisplayOrder() {
        assertEquals(0, UniRefMemberIdType.UNIPROTKB_SWISSPROT.getDisplayOrder());
    }

    @Nested
    class typeOf {
        @Test
        void canConvertLowerCase() {
            assertEquals(UniRefMemberIdType.UNIPROTKB, UniRefMemberIdType.typeOf("uniprotkb id"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(UniRefMemberIdType.UNIPARC, UniRefMemberIdType.typeOf("UNIPARC ID"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
        void willThrowException(String val) {
            assertThrows(IllegalArgumentException.class, () -> UniRefMemberIdType.typeOf(val));
        }

        @Test
        void willThrowException_null() {
            assertThrows(IllegalArgumentException.class, () -> UniRefMemberIdType.typeOf(null));
        }
    }
}
