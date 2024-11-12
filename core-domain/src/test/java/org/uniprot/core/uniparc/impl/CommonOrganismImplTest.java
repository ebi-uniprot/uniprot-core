package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.CommonOrganism;
import org.uniprot.core.uniparc.InterProGroup;

import static org.junit.jupiter.api.Assertions.*;

class CommonOrganismImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        CommonOrganism obj = new CommonOrganismImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CommonOrganism impl = new CommonOrganismImpl("id", "name", 123L);
        CommonOrganism obj = CommonOrganismBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}