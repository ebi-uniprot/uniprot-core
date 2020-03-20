package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.SequenceCautionComment;
import org.uniprot.core.uniprotkb.comment.SequenceCautionType;

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

    @Test
    void canAddSingleEvidence() {
        SequenceCautionComment obj =
                new SequenceCautionCommentBuilder().evidencesAdd(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().evidencesAdd(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        SequenceCautionComment obj =
                new SequenceCautionCommentBuilder()
                        .evidencesSet(Collections.emptyList())
                        .evidencesAdd(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        SequenceCautionComment obj =
                new SequenceCautionCommentBuilder().evidencesSet(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().build();
        SequenceCautionCommentBuilder builder = SequenceCautionCommentBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        SequenceCautionComment obj = new SequenceCautionCommentBuilder().build();
        SequenceCautionComment obj2 = new SequenceCautionCommentBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
