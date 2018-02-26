package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SequenceCautionType;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class SequenceCautionCommentBuilderTest {

    @Test
    public void testNewInstance() {
        SequenceCautionCommentBuilder builder1 = SequenceCautionCommentBuilder.newInstance();
        SequenceCautionCommentBuilder builder2 = SequenceCautionCommentBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetSequenceCautionType() {
        SequenceCautionCommentBuilder builder = SequenceCautionCommentBuilder.newInstance();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                        .build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertFalse(comment.getNote().isPresent());
        assertEquals(0, comment.getPositions().size());
        assertNull(comment.getSequence());
    }

    @Test
    public void testSetSequence() {
        SequenceCautionCommentBuilder builder = SequenceCautionCommentBuilder.newInstance();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                .sequence("somesequence")
                        .build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertFalse(comment.getNote().isPresent());
        assertEquals(0, comment.getPositions().size());
        assertEquals("somesequence", comment.getSequence());
    }

    @Test
    public void testSetPositions() {
        List<String> positions = Arrays.asList(new String[]{"P1", "P2"});
        SequenceCautionCommentBuilder builder = SequenceCautionCommentBuilder.newInstance();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                .sequence("somesequence")
                .positions(positions)
                        .build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertFalse(comment.getNote().isPresent());
        assertEquals(positions, comment.getPositions());
        assertEquals("somesequence", comment.getSequence());
    }

    @Test
    public void testSetNote() {
        List<String> positions = Arrays.asList(new String[]{"P1", "P2"});
        SequenceCautionCommentBuilder builder = SequenceCautionCommentBuilder.newInstance();
        SequenceCautionComment comment =
                builder.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                .sequence("somesequence")
                .positions(positions)
                .note("some note")
                        .build();
        assertEquals(SequenceCautionType.ERRONEOUS_INITIATION, comment.getSequenceCautionType());
        assertEquals(CommentType.SEQUENCE_CAUTION, comment.getCommentType());
        assertEquals("some note", comment.getNote().get());
        assertEquals(positions, comment.getPositions());
        assertEquals("somesequence", comment.getSequence());
    }

}
