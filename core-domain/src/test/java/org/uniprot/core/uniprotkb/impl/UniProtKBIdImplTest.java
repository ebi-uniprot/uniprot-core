package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBId;

class UniProtKBIdImplTest {

    @Test
    void testUniProtIdImpl() {
        String val = "P12345_HUMAN";
        UniProtKBId uniprotId = new UniProtKBIdImpl(val);
        assertEquals(val, uniprotId.getValue());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtKBId obj = new UniProtKBIdImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtKBId impl = new UniProtKBIdImpl("VAL");
        UniProtKBId obj = UniProtKBIdBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
