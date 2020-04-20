package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.unirule.SamTrigger;
import org.uniprot.core.unirule.SamTriggerType;

public class SamTriggerImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        SamTrigger samTrigger = new SamTriggerImpl();
        assertNotNull(samTrigger);
        assertNull(samTrigger.getExpectedHits());
        assertNull(samTrigger.getSamTriggerType());
    }

    @Test
    void testCreateObject() {
        Range expectedHits = new Range(1, 2);
        SamTriggerType samTriggerType = SamTriggerType.COILED_COIL;
        SamTrigger samTrigger = new SamTriggerImpl(samTriggerType, expectedHits);
        assertNotNull(samTrigger);
        assertEquals(expectedHits, samTrigger.getExpectedHits());
        assertEquals(samTriggerType, samTrigger.getSamTriggerType());
    }
}
