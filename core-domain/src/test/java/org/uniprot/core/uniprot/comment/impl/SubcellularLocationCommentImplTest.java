package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createNote;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.SubcellularLocationComment;

class SubcellularLocationCommentImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        SubcellularLocationComment obj = new SubcellularLocationCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        SubcellularLocationComment impl =
                new SubcellularLocationCommentImpl(
                        "mol",
                        Collections.singletonList(new SubcellularLocationBuilder().build()),
                        createNote());
        SubcellularLocationComment obj = SubcellularLocationCommentBuilder.from(impl).build();

        assertTrue(impl.hasSubcellularLocations());
        assertTrue(impl.hasMolecule());
        assertTrue(impl.hasNote());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
