package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBId;

class UniProtKBIdBuilderTest {

    @Test
    void canCreateBuilderFromInstance() {
        UniProtKBId obj = new UniProtKBIdBuilder("val").build();
        UniProtKBIdBuilder builder = UniProtKBIdBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtKBId obj = new UniProtKBIdBuilder("val").build();
        UniProtKBId obj2 = new UniProtKBIdBuilder("val").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
