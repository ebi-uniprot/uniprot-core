package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.SequenceFeatureLocation;

class SequenceFeatureLocationImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        SequenceFeatureLocation obj = new SequenceFeatureLocationImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        SequenceFeatureLocationImpl impl = new SequenceFeatureLocationImpl(10, 20, "component");
        SequenceFeatureLocation obj = SequenceFeatureLocationBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
