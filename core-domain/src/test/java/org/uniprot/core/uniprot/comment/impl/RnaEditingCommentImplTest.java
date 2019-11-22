package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;
import static org.uniprot.core.ObjectsForTests.createNote;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.RnaEdPosition;
import org.uniprot.core.uniprot.comment.RnaEditingComment;
import org.uniprot.core.uniprot.comment.RnaEditingLocationType;
import org.uniprot.core.uniprot.comment.builder.RnaEditingCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.RnaEditingPositionBuilder;

class RnaEditingCommentImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        RnaEditingComment obj = new RnaEditingCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_edPosition() {
        RnaEditingCommentImpl.RnaEdPositionImpl obj = new RnaEditingCommentImpl.RnaEdPositionImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        RnaEditingCommentImpl.RnaEdPositionImpl pos =
                new RnaEditingCommentImpl.RnaEdPositionImpl("pos", createEvidences());
        RnaEdPosition posBuild =
                new RnaEditingPositionBuilder()
                        .position("pos1")
                        .evidences(createEvidences())
                        .from(pos)
                        .build();

        assertTrue(pos.hasEvidences());
        assertTrue(pos.equals(posBuild) && posBuild.equals(pos));
        assertEquals(pos.hashCode(), posBuild.hashCode());

        RnaEditingComment impl =
                new RnaEditingCommentImpl("molecule",
                        RnaEditingLocationType.Undetermined,
                        Collections.singletonList(pos),
                        createNote());
        RnaEditingComment obj = new RnaEditingCommentBuilder().from(impl).build();

        assertTrue(impl.hasPositions());
        assertTrue(impl.hasLocationType());
        assertTrue(impl.hasNote());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
