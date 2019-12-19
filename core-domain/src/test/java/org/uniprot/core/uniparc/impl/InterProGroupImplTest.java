package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.InterProGroup;
import org.uniprot.core.uniparc.builder.InterProGroupBuilder;

class InterProGroupImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        InterProGroup obj = new InterProGroupImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        InterProGroup impl = new InterProGroupImpl("id", "name");
        InterProGroup obj = new InterProGroupBuilder().from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
