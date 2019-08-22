package org.uniprot.core.uniprot.comment.builder;

import org.junit.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;
import org.uniprot.core.uniprot.comment.builder.SequenceCautionCommentBuilder;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SequenceCautionCommentBuilderTest {

    @Test
    public void testNewInstance() {
        SequenceCautionCommentBuilder builder1 = new SequenceCautionCommentBuilder();
        SequenceCautionCommentBuilder builder2 = new SequenceCautionCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetSequenceCautionType() {
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                        .build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getPositions().size());
        assertNull(comment.getSequence());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetSequence() {
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
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetPositions() {
        List<String> positions = Arrays.asList(new String[]{"P1", "P2"});
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
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetNote() {
        List<String> positions = Arrays.asList(new String[]{"P1", "P2"});
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
        TestHelper.verifyJson(comment);
    }
}