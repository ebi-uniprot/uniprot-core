package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.MichaelisConstant;

class MichaelisConstantImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        MichaelisConstant obj = new MichaelisConstantImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        MichaelisConstant impl =
                new MichaelisConstantImpl(
                        8.2, MichaelisConstant.NORMALIZED_UNIT, "sub", createEvidences());
        MichaelisConstant obj = MichaelisConstantBuilder.from(impl).build();

        assertTrue(impl.hasEvidences());
        assertTrue(impl.hasConstant());
        assertTrue(impl.hasUnit());
        assertTrue(impl.hasSubstrate());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
