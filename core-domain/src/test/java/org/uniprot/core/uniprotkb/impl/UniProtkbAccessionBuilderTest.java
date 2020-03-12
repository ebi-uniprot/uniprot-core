package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtkbAccession;

class UniProtkbAccessionBuilderTest {

    @Test
    void canCreateBuilderFromInstance() {
        UniProtkbAccession obj = new UniProtkbAccessionBuilder("val").build();
        UniProtkbAccessionBuilder builder = UniProtkbAccessionBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtkbAccession obj = new UniProtkbAccessionBuilder("val").build();
        UniProtkbAccession obj2 = new UniProtkbAccessionBuilder("val").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
