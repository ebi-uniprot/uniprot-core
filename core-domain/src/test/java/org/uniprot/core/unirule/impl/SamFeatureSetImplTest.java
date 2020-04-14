package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.builder.AnnotationBuilder;
import org.uniprot.core.unirule.builder.ConditionBuilder;
import org.uniprot.core.unirule.builder.SamTriggerBuilder;

public class SamFeatureSetImplTest {

    @Test
    void testCreateObjectByNoArgConstructor() {
        SamFeatureSet samFeatureSet = new SamFeatureSetImpl();
        assertNotNull(samFeatureSet);
        assertTrue(samFeatureSet.getAnnotations().isEmpty());
        assertTrue(samFeatureSet.getConditions().isEmpty());
        assertNull(samFeatureSet.getSamTrigger());
    }

    @Test
    void testCreateObject() {
        Condition condition1 = new ConditionBuilder().type("type1").build();
        Condition condition2 = new ConditionBuilder().type("type2").build();
        Condition condition3 = new ConditionBuilder().type("type3").build();
        List<Condition> conditions = Arrays.asList(condition1, condition2, condition3);

        Annotation annotation1 = new AnnotationBuilder().build();
        Annotation annotation2 = new AnnotationBuilder().build();
        List<Annotation> annotations = Arrays.asList(annotation1, annotation2);
        SamTrigger samTrigger =
                new SamTriggerBuilder().samTriggerType(SamTriggerType.SIGNAL).build();
        SamFeatureSet samFeatureSet = new SamFeatureSetImpl(conditions, annotations, samTrigger);

        assertNotNull(samFeatureSet);
        assertEquals(conditions, samFeatureSet.getConditions());
        assertEquals(annotations, samFeatureSet.getAnnotations());
        assertEquals(samTrigger, samFeatureSet.getSamTrigger());
    }
}
