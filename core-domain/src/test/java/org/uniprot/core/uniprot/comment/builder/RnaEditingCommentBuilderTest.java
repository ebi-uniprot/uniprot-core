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
        positions.add(new RnaEditingPositionBuilder("123", evidences).build());
        positions.add(new RnaEditingPositionBuilder("432", evidences).build());
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
        positions.add(new RnaEditingPositionBuilder("123", evidences).build());
        positions.add(new RnaEditingPositionBuilder("432", evidences).build());
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
        RnaEdPosition position = new RnaEditingPositionBuilder("123", evidences).build();
        assertEquals("123", position.getPosition());
        assertEquals(evidences, position.getEvidences());
    }
}
