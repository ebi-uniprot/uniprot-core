package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidenceValuesWithoutEvidences;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.evidence.Evidence;

class RnaEditingCommentBuilderTest {
    @Test
    void testNewInstance() {
        RnaEditingCommentBuilder builder1 = new RnaEditingCommentBuilder();
        RnaEditingCommentBuilder builder2 = new RnaEditingCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    void testSetlocationType() {

        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        RnaEditingComment comment = builder.locationType(RnaEditingLocationType.Known).build();
        assertEquals(RnaEditingLocationType.Known, comment.getLocationType());
        assertEquals(CommentType.RNA_EDITING, comment.getCommentType());
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getPositions().size());
    }

    @Test
    void testSetpositions() {
        List<RnaEdPosition> positions = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        positions.add(new RnaEditingPositionBuilder().position("123").evidences(evidences).build());
        positions.add(new RnaEditingPositionBuilder().position("432").evidences(evidences).build());
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        RnaEditingComment comment =
                builder.locationType(RnaEditingLocationType.Known).positions(positions).build();
        assertEquals(RnaEditingLocationType.Known, comment.getLocationType());
        assertEquals(CommentType.RNA_EDITING, comment.getCommentType());
        assertFalse(comment.getNote() != null);
        assertEquals(positions, comment.getPositions());
    }

    @Test
    void testSetNote() {
        List<RnaEdPosition> positions = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        positions.add(new RnaEditingPositionBuilder().position("123").evidences(evidences).build());
        positions.add(new RnaEditingPositionBuilder().position("432").evidences(evidences).build());
        Note note = new NoteBuilder(createEvidenceValuesWithoutEvidences()).build();
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        RnaEditingComment comment =
                builder.locationType(RnaEditingLocationType.Known)
                        .positions(positions)
                        .note(note)
                        .build();
        assertEquals(RnaEditingLocationType.Known, comment.getLocationType());
        assertEquals(CommentType.RNA_EDITING, comment.getCommentType());
        assertEquals(note, comment.getNote());
        assertEquals(positions, comment.getPositions());
    }

    @Test
    void testCreatePosition() {
        List<Evidence> evidences = createEvidences();
        RnaEdPosition position =
                new RnaEditingPositionBuilder().position("123").evidences(evidences).build();
        assertEquals("123", position.getPosition());
        assertEquals(evidences, position.getEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        RnaEditingComment obj = new RnaEditingCommentBuilder().build();
        RnaEditingCommentBuilder builder = RnaEditingCommentBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        RnaEditingComment obj = new RnaEditingCommentBuilder().build();
        RnaEditingComment obj2 = new RnaEditingCommentBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canAddRnaPosition() {
        RnaEditingComment obj =
                new RnaEditingCommentBuilder()
                        .addPosition(
                                new RnaEditingPositionBuilder()
                                        .position("123")
                                        .evidences(createEvidences())
                                        .build())
                        .build();
        assertTrue(obj.hasPositions());
    }
}
