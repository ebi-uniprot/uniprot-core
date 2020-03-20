package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

class UniProtKBAccessionBuilderTest {

    @Test
    void canCreateBuilderFromInstance() {
        UniProtKBAccession obj = new UniProtKBAccessionBuilder("val").build();
        UniProtKBAccessionBuilder builder = UniProtKBAccessionBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtKBAccession obj = new UniProtKBAccessionBuilder("val").build();
        UniProtKBAccession obj2 = new UniProtKBAccessionBuilder("val").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
