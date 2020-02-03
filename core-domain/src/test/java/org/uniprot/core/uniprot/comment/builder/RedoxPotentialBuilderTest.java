package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.evidenceValues;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.RedoxPotential;

class RedoxPotentialBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        RedoxPotential obj = new RedoxPotentialBuilder(evidenceValues()).build();
        RedoxPotentialBuilder builder = RedoxPotentialBuilder.from(obj);
        assertNotNull(builder);

        assertIterableEquals(evidenceValues(), builder.build().getTexts());
    }

    @Test
    void defaultBuild_objsAreEqual() {
        RedoxPotential obj = new RedoxPotentialBuilder(evidenceValues()).build();
        RedoxPotential obj2 = new RedoxPotentialBuilder(evidenceValues()).build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
