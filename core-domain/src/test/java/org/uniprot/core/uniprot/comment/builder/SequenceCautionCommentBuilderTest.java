package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        assertEquals("somesequence", comment.getSequence());
        assertNull(comment.getMolecule());
    }

    @Test
    void testSetMolecule() {
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                        .sequence("somesequence")
                        .molecule("Isoform 3")
                        .build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertFalse(comment.getNote() != null);

        assertEquals("somesequence", comment.getSequence());
        assertEquals("Isoform 3", comment.getMolecule());
    }

    @Test
    void testSetNote() {

        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                        .sequence("somesequence")
                        .note("some note")
                        .build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertEquals("some note", comment.getNote());
        assertEquals("somesequence", comment.getSequence());
    }
}
