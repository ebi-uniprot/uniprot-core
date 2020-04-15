package org.uniprot.core.unirule.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConditionSetBuilderTest {

    @Test
    void testCreateObjectUpdateList(){
        ConditionSet conditionSet = createObject();
        ConditionSetBuilder builder = ConditionSetBuilder.from(conditionSet);
        Condition condition = ConditionBuilderTest.createObject();
        // add another condition value
        builder.conditionsAdd(condition);
        ConditionSet updatedConditionSet = builder.build();

        assertNotNull(updatedConditionSet);
        assertEquals(conditionSet.getConditions().size() + 1, updatedConditionSet.getConditions().size());
    }


    public static ConditionSet createObject() {
        List<Condition> conditions = ConditionBuilderTest.createObjects(3);
        ConditionSetBuilder builder = new ConditionSetBuilder();
        builder.conditionsSet(conditions);
        ConditionSet conditionSet = builder.build();
        assertNotNull(conditionSet);
        assertEquals(conditions, conditionSet.getConditions());
        return conditionSet;
    }

    public static List<ConditionSet> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
