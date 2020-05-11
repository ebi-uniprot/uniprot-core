package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryComment;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.util.Collections;
import java.util.List;

class MassSpectrometryCommentBuilderTest {
    @Test
    void testNewInstance() {
        MassSpectrometryCommentBuilder builder1 = new MassSpectrometryCommentBuilder();
        MassSpectrometryCommentBuilder builder2 = new MassSpectrometryCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    void testSetMolecule() {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment = builder.molecule("Isoform 2").build();
        assertEquals(null, comment.getMethod());
        assertNull(comment.getMolWeight());
        assertFalse(comment.getMolWeightError() != null);
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getEvidences().size());
        assertEquals("Isoform 2", comment.getMolecule());
    }

    @Test
    void testSetmethod() {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =
                builder.method(MassSpectrometryMethod.ELECTROSPRAY).build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertNull(comment.getMolWeight());
        assertNull(comment.getMolecule());
        assertFalse(comment.getMolWeightError() != null);
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getEvidences().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    void testSetMolWeight() {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =
                builder.method(MassSpectrometryMethod.ELECTROSPRAY).molWeight(0.234f).build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());

        assertEquals(0.234f, comment.getMolWeight(), Float.MIN_VALUE);
        assertFalse(comment.getMolWeightError() != null);
        assertFalse(comment.getNote() != null);

        assertEquals(0, comment.getEvidences().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    void testSetMolWeightError() {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();

        MassSpectrometryComment comment =
                builder.method(MassSpectrometryMethod.ELECTROSPRAY)
                        .molWeight(0.234f)
                        .molWeightError(0.123f)
                        .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());

        assertEquals(0.234f, comment.getMolWeight(), Float.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError(), Double.MIN_VALUE);
        assertFalse(comment.getNote() != null);

        assertEquals(0, comment.getEvidences().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    void malWeightError_lessThanFloatNegative() {
        Float err = Float.MIN_VALUE - 2;
        MassSpectrometryComment comment =
                new MassSpectrometryCommentBuilder().molWeightError(err).build();
        assertEquals(err, comment.getMolWeightError(), Double.MIN_VALUE);
    }

    @Test
    void malWeightError_FloatMin_notValidValue() {
        MassSpectrometryComment comment =
                new MassSpectrometryCommentBuilder().molWeightError(Float.MIN_VALUE).build();
        assertNotNull(comment.getMolWeightError());
    }

    @Test
    void testSetNote() {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =
                builder.method(MassSpectrometryMethod.ELECTROSPRAY)
                        .molWeight(0.234f)
                        .molWeightError(0.123f)
                        .note("someNote")
                        .build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234f, comment.getMolWeight(), Double.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError(), Double.MIN_VALUE);
        assertEquals("someNote", comment.getNote());
        assertEquals(0, comment.getEvidences().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    void testSetEvidences() {
        List<Evidence> evidences = createEvidences();
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =
                builder.method(MassSpectrometryMethod.ELECTROSPRAY)
                        .molWeight(0.234f)
                        .molWeightError(0.123f)
                        .note("someNote")
                        .evidencesSet(evidences)
                        .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234f, comment.getMolWeight(), Double.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError(), Double.MIN_VALUE);
        assertEquals("someNote", comment.getNote());
        assertEquals(evidences, comment.getEvidences());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    void canAddSingleEvidence() {
        MassSpectrometryComment obj =
                new MassSpectrometryCommentBuilder().evidencesAdd(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        MassSpectrometryComment obj =
                new MassSpectrometryCommentBuilder().evidencesAdd(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        MassSpectrometryComment obj =
                new MassSpectrometryCommentBuilder()
                        .evidencesSet(Collections.emptyList())
                        .evidencesAdd(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        MassSpectrometryComment obj =
                new MassSpectrometryCommentBuilder().evidencesSet(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().build();
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().build();
        MassSpectrometryComment obj2 = new MassSpectrometryCommentBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
