package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.interpro.Abstract;

/**
 * @author jluo
 * @date: 12 Apr 2021
 */
class AbstractImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Abstract obj = new AbstractImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Abstract impl = new AbstractImpl("VAL");
        Abstract obj = AbstractBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
