package org.uniprot.core.literature.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.uniprotkb.impl.UniProtkbAccessionBuilder;

class LiteratureMappedReferenceImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        LiteratureMappedReference obj = new LiteratureMappedReferenceImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        LiteratureMappedReference impl =
                new LiteratureMappedReferenceImpl(
                        new UniProtkbAccessionBuilder("acc").build(),
                        "sou",
                        "sid",
                        Collections.singletonList("sou cat"),
                        "anno");
        LiteratureMappedReference obj = LiteratureMappedReferenceBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
