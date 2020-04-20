package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;

public class ConditionSetImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        ConditionSet conditionSet = new ConditionSetImpl();
        assertNotNull(conditionSet);
        assertTrue(conditionSet.getConditions().isEmpty());
    }

    @Test
    void testCreateObject() {
        Condition condition1 = new ConditionBuilder("type1").build();
        Condition condition2 = new ConditionBuilder("type2").build();
        Condition condition3 = new ConditionBuilder("type3").build();
        List<Condition> conditions = Arrays.asList(condition1, condition2, condition3);
        ConditionSet conditionSet = new ConditionSetImpl(conditions);
        assertNotNull(conditionSet);
        assertNotNull(conditionSet.getConditions());
        assertEquals(conditions, conditionSet.getConditions());
    }
}
