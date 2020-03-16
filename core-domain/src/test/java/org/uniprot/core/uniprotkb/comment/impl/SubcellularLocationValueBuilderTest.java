package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationValue;

class SubcellularLocationValueBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        SubcellularLocationValue obj = new SubcellularLocationValueBuilder().build();
        SubcellularLocationValueBuilder builder = SubcellularLocationValueBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        SubcellularLocationValue obj = new SubcellularLocationValueBuilder().build();
        SubcellularLocationValue obj2 = new SubcellularLocationValueBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canSetId() {
        String id = "id";
        SubcellularLocationValue obj = new SubcellularLocationValueBuilder().id(id).build();
        assertEquals(id, obj.getId());
    }
}
