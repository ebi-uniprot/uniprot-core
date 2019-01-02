package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class RnaEditingCommentBuilderTest {

    @Test
    public void testNewInstance() {
        RnaEditingCommentBuilder builder1 = RnaEditingCommentBuilder.newInstance();
        RnaEditingCommentBuilder builder2 = RnaEditingCommentBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetRnaEditingLocationType() {

        RnaEditingCommentBuilder builder = RnaEditingCommentBuilder.newInstance();
        RnaEditingComment comment =
                builder.rnaEditingLocationType(RnaEditingLocationType.Known)
                        .build();
        assertEquals(RnaEditingLocationType.Known, comment.getLocationType());
        assertEquals(CommentType.RNA_EDITING, comment.getCommentType());
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getPositions().size());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetLocations() {
        List<RnaEdPosition> positions = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        positions.add(RnaEditingCommentBuilder.createPosition("123", evidences));
        positions.add(RnaEditingCommentBuilder.createPosition("432", evidences));
        RnaEditingCommentBuilder builder = RnaEditingCommentBuilder.newInstance();
        RnaEditingComment comment =
                builder.rnaEditingLocationType(RnaEditingLocationType.Known)
                        .locations(positions)
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
        positions.add(RnaEditingCommentBuilder.createPosition("123", evidences));
        positions.add(RnaEditingCommentBuilder.createPosition("432", evidences));
        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        RnaEditingCommentBuilder builder = RnaEditingCommentBuilder.newInstance();
        RnaEditingComment comment =
                builder.rnaEditingLocationType(RnaEditingLocationType.Known)
                        .locations(positions)
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
        RnaEdPosition position = RnaEditingCommentBuilder.createPosition("123", evidences);
        assertEquals("123", position.getPosition());
        assertEquals(evidences, position.getEvidences());
        TestHelper.verifyJson(position);
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
