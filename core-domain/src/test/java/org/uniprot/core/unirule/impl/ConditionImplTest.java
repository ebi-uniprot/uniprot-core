package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprotkb.feature.FeatureLocation;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionValue;

public class ConditionImplTest {

    @Test
    void testCreateObjectByNoArgConstructor() {
        Condition condition = new ConditionImpl();
        assertNotNull(condition);
        assertNull(condition.getRange());
        assertNull(condition.getType());
        assertFalse(condition.isNegative());
        assertTrue(condition.getConditionValues().isEmpty());
    }

    @Test
    void testCreateObject() {
        String type = "sample type";
        boolean isNegative = true;
        Range range = new FeatureLocation(1, 2);
        String value1 = "sample value1";
        String value2 = "sample value2";
        ConditionValue cv1 = new ConditionValueBuilder(value1).build();
        ConditionValue cv2 = new ConditionValueBuilder(value2).build();
        List<ConditionValue> cvs = Arrays.asList(cv1, cv2);

        Condition condition = new ConditionImpl(cvs, type, isNegative, range);
        assertNotNull(condition);
        assertEquals(type, condition.getType());
        assertEquals(isNegative, condition.isNegative());
        assertEquals(range, condition.getRange());
        assertEquals(cvs, condition.getConditionValues());
    }

    @Test
    void testCreateObjectWithoutMandatoryParam() {
        boolean isNegative = true;
        Range range = new FeatureLocation(1, 2);
        String value1 = "sample value1";
        String value2 = "sample value2";
        ConditionValue cv1 = new ConditionValueBuilder(value1).build();
        ConditionValue cv2 = new ConditionValueBuilder(value2).build();
        List<ConditionValue> cvs = Arrays.asList(cv1, cv2);

        assertThrows(
                IllegalArgumentException.class,
                () -> new ConditionImpl(cvs, null, isNegative, range));
    }
}
