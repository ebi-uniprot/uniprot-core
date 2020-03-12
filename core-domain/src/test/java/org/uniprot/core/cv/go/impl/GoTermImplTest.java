package org.uniprot.core.cv.go.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GoTerm;

class GoTermImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        GoTerm obj = new GoTermImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        GoTerm impl = new GoTermImpl("id", "name");
        GoTerm obj = GoTermBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
