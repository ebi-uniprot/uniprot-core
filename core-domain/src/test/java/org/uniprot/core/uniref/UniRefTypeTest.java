package org.uniprot.core.uniref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class UniRefTypeTest {

    @ParameterizedTest
    @EnumSource(UniRefType.class)
    void displayName(UniRefType enm) {
        assertEquals(enm.toString(), enm.getDisplayName());
    }

    @Test
    void testGetName(){
        assertEquals("1.0", UniRefType.UniRef100.getName());
    }

    @Nested
    class getIdentity {
        @Test
        void UniRef100() {
            assertEquals("1.0", UniRefType.UniRef100.getIdentity());
        }

        @Test
        void UniRef90() {
            assertEquals("0.9", UniRefType.UniRef90.getIdentity());
        }

        @Test
        void UniRef50() {
            assertEquals("0.5", UniRefType.UniRef50.getIdentity());
        }
    }

    @Nested
    class typeOf {

        @Test
        void canConvert() {
            assertEquals(UniRefType.UniRef100, UniRefType.typeOf("1.0"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
        void willThrowException(String val) {
            assertThrows(IllegalArgumentException.class, () -> UniRefType.typeOf(val));
        }

        @Test
        void willThrowException_null() {
            assertThrows(IllegalArgumentException.class, () -> UniRefType.typeOf(null));
        }
    }
}
