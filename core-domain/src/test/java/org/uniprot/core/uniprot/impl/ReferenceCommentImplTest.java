package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;

class ReferenceCommentImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ReferenceComment obj = new ReferenceCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ReferenceCommentImpl impl =
                new ReferenceCommentImpl(
                        ReferenceCommentType.PLASMID, "val", Collections.emptyList());
        ReferenceComment obj = ReferenceCommentBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
