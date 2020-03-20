package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.SubcellularLocation;

class SubcellularLocationImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        SubcellularLocation obj = new SubcellularLocationImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_SubcellularLocationValue() {
        SubcellularLocationImpl.SubcellularLocationValueImpl obj =
                new SubcellularLocationImpl.SubcellularLocationValueImpl();
        assertNotNull(obj);
        assertNull(obj.getId());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        SubcellularLocationImpl.SubcellularLocationValueImpl val =
                new SubcellularLocationImpl.SubcellularLocationValueImpl();
        SubcellularLocation impl = new SubcellularLocationImpl(val, val, val);
        SubcellularLocation obj = SubcellularLocationBuilder.from(impl).build();

        assertTrue(impl.hasLocation());
        assertTrue(impl.hasOrientation());
        assertTrue(impl.hasTopology());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
