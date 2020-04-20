package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.ConditionValue;

public class ConditionValueImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        ConditionValue conditionValue = new ConditionValueImpl();
        assertNotNull(conditionValue);
        assertNull(conditionValue.getCvId());
        assertNull(conditionValue.getValue());
    }

    @Test
    void testCreateObject() {
        String value = "sample value";
        String cvId = "sample cvId";
        ConditionValue conditionValue = new ConditionValueImpl(value, cvId);
        assertNotNull(conditionValue);
        assertEquals(value, conditionValue.getValue());
        assertEquals(cvId, conditionValue.getCvId());
    }
}
