package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.evidenceValues;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.TemperatureDependence;

class TemperatureDependenceBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        TemperatureDependence obj = new TemperatureDependenceBuilder(evidenceValues()).build();
        TemperatureDependenceBuilder builder = TemperatureDependenceBuilder.from(obj);
        assertNotNull(builder);

        assertIterableEquals(evidenceValues(), builder.build().getTexts());
    }

    @Test
    void defaultBuild_objsAreEqual() {
        TemperatureDependence obj = new TemperatureDependenceBuilder(evidenceValues()).build();
        TemperatureDependence obj2 = new TemperatureDependenceBuilder(evidenceValues()).build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
