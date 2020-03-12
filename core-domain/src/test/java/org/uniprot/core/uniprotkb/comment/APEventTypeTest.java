package org.uniprot.core.uniprotkb.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class APEventTypeTest {
    @Test
    void nameAndDisplayNameAreSame() {
        APEventType alternativeInitiation = APEventType.ALTERNATIVE_INITIATION;
        assertEquals(alternativeInitiation.getName(), alternativeInitiation.toDisplayName());
    }

    @Test
    void canConvertExact() {
        assertEquals(
                APEventType.ALTERNATIVE_PROMOTER_USAGE,
                APEventType.typeOf("Alternative promoter usage"));
    }

    @Test
    void canConvertAfterIgnoringCase() {
        assertEquals(
                APEventType.RIBOSOMAL_FRAMESHIFTING, APEventType.typeOf("RIBOSOMAL FRAMESHIFTING"));
    }

    @Test
    void willThorwExceptionWhenNotAbleToConvert() {
        assertThrows(IllegalArgumentException.class, () -> APEventType.typeOf("not exist"));
    }
}
