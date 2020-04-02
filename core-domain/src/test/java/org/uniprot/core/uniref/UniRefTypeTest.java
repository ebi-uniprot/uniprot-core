package org.uniprot.core.uniref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class UniRefTypeTest {

    @ParameterizedTest
    @EnumSource(UniRefType.class)
    void displayName(UniRefType enm) {
        assertEquals(enm.toString(), enm.getDisplayName());
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
}
