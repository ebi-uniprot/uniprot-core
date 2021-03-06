package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryComment;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryMethod;

class MassSpectrometryCommentImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        MassSpectrometryComment obj = new MassSpectrometryCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        MassSpectrometryComment impl =
                new MassSpectrometryCommentImpl(
                        "mol",
                        MassSpectrometryMethod.ELECTROSPRAY,
                        0.1F,
                        0.2F,
                        "note",
                        createEvidences());
        MassSpectrometryComment obj = MassSpectrometryCommentBuilder.from(impl).build();

        assertTrue(impl.hasMolWeight());
        assertTrue(impl.hasMolWeightError());
        assertTrue(impl.hasNote());
        assertTrue(impl.hasMethod());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
