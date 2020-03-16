package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtkbId;

class UniProtkbIdBuilderTest {

    @Test
    void canCreateBuilderFromInstance() {
        UniProtkbId obj = new UniProtkbIdBuilder("val").build();
        UniProtkbIdBuilder builder = UniProtkbIdBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtkbId obj = new UniProtkbIdBuilder("val").build();
        UniProtkbId obj2 = new UniProtkbIdBuilder("val").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
