package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
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
}
