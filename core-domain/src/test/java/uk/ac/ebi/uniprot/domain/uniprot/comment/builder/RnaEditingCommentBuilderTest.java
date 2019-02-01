package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;

public class RnaEditingCommentBuilderTest {
    @Test
    public void testNewInstance() {
        RnaEditingCommentBuilder builder1 = new RnaEditingCommentBuilder();
        RnaEditingCommentBuilder builder2 = new RnaEditingCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetlocationType() {

        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        RnaEditingComment comment =
                builder.locationType(RnaEditingLocationType.Known)
                        .build();
        assertEquals(RnaEditingLocationType.Known, comment.getLocationType());
        assertEquals(CommentType.RNA_EDITING, comment.getCommentType());
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getPositions().size());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetpositions() {
        List<RnaEdPosition> positions = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        positions.add(new RnaEditingPositionBuilder("123", evidences).build());
        positions.add(new RnaEditingPositionBuilder("432", evidences).build());
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        RnaEditingComment comment =
                builder.locationType(RnaEditingLocationType.Known)
                        .positions(positions)
                        .build();
        assertEquals(RnaEditingLocationType.Known, comment.getLocationType());
        assertEquals(CommentType.RNA_EDITING, comment.getCommentType());
        assertFalse(comment.getNote() != null);
        assertEquals(positions, comment.getPositions());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetNote() {
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
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testCreatePosition() {
        List<Evidence> evidences = createEvidences();
        RnaEdPosition position = new RnaEditingPositionBuilder("123", evidences).build();
        assertEquals("123", position.getPosition());
        assertEquals(evidences, position.getEvidences());
        TestHelper.verifyJson(position);
    }
}
