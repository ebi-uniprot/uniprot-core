package org.uniprot.core.uniprot.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtAccession;

class UniProtAccessionBuilderTest {

    @Test
    void canCreateBuilderFromInstance() {
        UniProtAccession obj = new UniProtAccessionBuilder("val").build();
        UniProtAccessionBuilder builder = new UniProtAccessionBuilder("val").from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtAccession obj = new UniProtAccessionBuilder("val").build();
        UniProtAccession obj2 = new UniProtAccessionBuilder("val").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
