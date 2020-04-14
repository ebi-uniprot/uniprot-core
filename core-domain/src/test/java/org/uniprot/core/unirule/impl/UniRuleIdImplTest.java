package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.UniRuleId;

public class UniRuleIdImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        UniRuleId uniRuleId = new UniRuleIdImpl();
        assertNotNull(uniRuleId);
        assertNull(uniRuleId.getValue());
    }

    @Test
    void testCreateObject() {
        String uniRuleIdVal = "UR123456789";
        UniRuleId uniRuleId = new UniRuleIdImpl(uniRuleIdVal);
        assertNotNull(uniRuleId);
        assertEquals(uniRuleIdVal, uniRuleId.getValue());
    }
}
