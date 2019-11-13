package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;

class SequenceCautionCommentBuilderTest {

    @Test
    void testNewInstance() {
        SequenceCautionCommentBuilder builder1 = new SequenceCautionCommentBuilder();
        SequenceCautionCommentBuilder builder2 = new SequenceCautionCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    void testSetSequenceCautionType() {
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION).build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getPositions().size());
        assertNull(comment.getSequence());
    }

    @Test
    void testSetSequence() {
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                        .sequence("somesequence")
                        .build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getPositions().size());
        assertEquals("somesequence", comment.getSequence());
    }

    @Test
    void testSetPositions() {
        List<String> positions = Arrays.asList(new String[] {"P1", "P2"});
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                        .sequence("somesequence")
                        .positions(positions)
                        .build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertFalse(comment.getNote() != null);
        assertEquals(positions, comment.getPositions());
        assertEquals("somesequence", comment.getSequence());
    }

    @Test
    void testSetNote() {
        List<String> positions = Arrays.asList(new String[] {"P1", "P2"});
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                        .sequence("somesequence")
                        .positions(positions)
                        .note("some note")
                        .build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertEquals("some note", comment.getNote());
        assertEquals(positions, comment.getPositions());
        assertEquals("somesequence", comment.getSequence());
    }

    @Test
    void canAddSingleEvidence() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().addEvidence(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().addEvidence(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().evidences(Collections.emptyList()).addEvidence(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().evidences(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().build();
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder().from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().build();
        SequenceCautionComment obj2 = new SequenceCautionCommentBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canAddSinglePosition() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().addPosition("pos").build();
        assertNotNull(obj.getPositions());
        assertFalse(obj.getPositions().isEmpty());
        assertTrue(obj.hasPositions());
    }

    @Test
    void nullPosition_willBeIgnore() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().addPosition(null).build();
        assertNotNull(obj.getPositions());
        assertTrue(obj.getPositions().isEmpty());
        assertFalse(obj.hasPositions());
    }
}
