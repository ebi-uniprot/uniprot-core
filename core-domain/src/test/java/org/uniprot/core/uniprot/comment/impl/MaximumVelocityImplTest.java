package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.MaximumVelocity;
import org.uniprot.core.uniprot.comment.builder.MaximumVelocityBuilder;

class MaximumVelocityImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        MaximumVelocity obj = new MaximumVelocityImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        MaximumVelocity impl = new MaximumVelocityImpl(0.7, "ab", "cd", createEvidences());
        MaximumVelocity obj = new MaximumVelocityBuilder().from(impl).build();

        assertTrue(impl.hasEvidences());
        assertTrue(impl.hasVelocity());
        assertTrue(impl.hasEnzyme());
        assertTrue(impl.hasUnit());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
