package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.evidenceValues;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.PhDependence;

class PhDependenceBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        PhDependence obj = new PhDependenceBuilder(evidenceValues()).build();
        PhDependenceBuilder builder = PhDependenceBuilder.from(obj);
        assertNotNull(builder);

        assertIterableEquals(evidenceValues(), builder.build().getTexts());
    }

    @Test
    void defaultBuild_objsAreEqual() {
        PhDependence obj = new PhDependenceBuilder(evidenceValues()).build();
        PhDependence obj2 = new PhDependenceBuilder(evidenceValues()).build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
