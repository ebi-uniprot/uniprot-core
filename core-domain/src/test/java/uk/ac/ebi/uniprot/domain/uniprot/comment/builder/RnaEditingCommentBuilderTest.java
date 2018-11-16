package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Position;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingLocationType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.RnaEditingCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class RnaEditingCommentBuilderTest {

    @Test
    public void testNewInstance() {
        RnaEditingCommentBuilder builder1 = RnaEditingCommentBuilder.newInstance();
        RnaEditingCommentBuilder builder2 = RnaEditingCommentBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1== builder2);
    }

    @Test
    public void testSetRnaEditingLocationType() {
        
        RnaEditingCommentBuilder builder= RnaEditingCommentBuilder.newInstance();
        RnaEditingComment comment =
        builder.rnaEditingLocationType(RnaEditingLocationType.Known)
        .build();
        assertEquals(RnaEditingLocationType.Known, comment.getLocationType());
        assertEquals(CommentType.RNA_EDITING, comment.getCommentType());
        assertFalse(comment.getNote().isPresent());
        assertEquals(0, comment.getPositions().size());
    }

    @Test
    public void testSetLocations() {
        List<Position> positions = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        positions.add(RnaEditingCommentBuilder.createPosition("123", evidences));
        positions.add(RnaEditingCommentBuilder.createPosition("432", evidences));
        RnaEditingCommentBuilder builder= RnaEditingCommentBuilder.newInstance();
        RnaEditingComment comment =
        builder.rnaEditingLocationType(RnaEditingLocationType.Known)
        .locations(positions)
        .build();
        assertEquals(RnaEditingLocationType.Known, comment.getLocationType());
        assertEquals(CommentType.RNA_EDITING, comment.getCommentType());
        assertFalse(comment.getNote().isPresent());
        assertEquals(positions, comment.getPositions());
    }

    @Test
    public void testSetNote() {
        List<Position> positions = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        positions.add(RnaEditingCommentBuilder.createPosition("123", evidences));
        positions.add(RnaEditingCommentBuilder.createPosition("432", evidences));
        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        RnaEditingCommentBuilder builder= RnaEditingCommentBuilder.newInstance();
        RnaEditingComment comment =
        builder.rnaEditingLocationType(RnaEditingLocationType.Known)
        .locations(positions)
        .note(note)
        .build();
        assertEquals(RnaEditingLocationType.Known, comment.getLocationType());
        assertEquals(CommentType.RNA_EDITING, comment.getCommentType());
        assertEquals(note, comment.getNote().get());
        assertEquals(positions, comment.getPositions());
    }

    @Test
    public void testCreatePosition() {
        List<Evidence> evidences = createEvidences();
       Position position =RnaEditingCommentBuilder.createPosition("123", evidences);
       assertEquals("123", position.getPosition());
       assertEquals(evidences, position.getEvidences());
    }
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
        return evidencedValues;
    }
}
