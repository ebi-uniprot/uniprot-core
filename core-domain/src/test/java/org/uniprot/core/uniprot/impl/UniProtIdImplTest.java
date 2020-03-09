package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtId;

class UniProtIdImplTest {

    @Test
    void testUniProtIdImpl() {
        String val = "P12345_HUMAN";
        UniProtId uniprotId = new UniProtIdImpl(val);
        assertEquals(val, uniprotId.getValue());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtId obj = new UniProtIdImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtId impl = new UniProtIdImpl("VAL");
        UniProtId obj = UniProtIdBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
