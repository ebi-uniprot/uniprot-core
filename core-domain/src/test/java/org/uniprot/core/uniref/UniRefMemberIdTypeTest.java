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
                                "UniProtKB Reviewed (Swiss-Prot)",
                                "UniProtKB Unreviewed (TrEMBL)")
                        .contains(enm.getDisplayName()));
    }

    @Test
    void testGetMemberIdTypeId() {
        assertEquals(0, UniRefMemberIdType.UNIPROTKB_SWISSPROT.getMemberIdTypeId());
    }

    @Test
    void testFromMemberTypeId() {
        assertEquals(
                UniRefMemberIdType.UNIPROTKB_SWISSPROT, UniRefMemberIdType.fromMemberTypeId("0"));
    }

    @Test
    void testFromInvalidMemberTypeIdWillThrowException() {
        assertThrows(
                IllegalArgumentException.class, () -> UniRefMemberIdType.fromMemberTypeId("5"));
    }

    @Test
    void testXmlName() {
        assertEquals("UniParc ID", UniRefMemberIdType.UNIPARC.getXmlName());
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
