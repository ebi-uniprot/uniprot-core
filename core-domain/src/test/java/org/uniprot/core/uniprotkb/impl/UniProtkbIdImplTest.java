package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtkbId;

class UniProtkbIdImplTest {

    @Test
    void testUniProtIdImpl() {
        String val = "P12345_HUMAN";
        UniProtkbId uniprotId = new UniProtkbIdImpl(val);
        assertEquals(val, uniprotId.getValue());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtkbId obj = new UniProtkbIdImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtkbId impl = new UniProtkbIdImpl("VAL");
        UniProtkbId obj = UniProtkbIdBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
