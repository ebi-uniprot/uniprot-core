package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprot.evidence.Evidence;

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
        MassSpectrometryComment comment =
                builder.molecule("Isoform 2").build();
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
                        .evidences(evidences)
                        .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234f, comment.getMolWeight(), Double.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError(), Double.MIN_VALUE);
        assertEquals("someNote", comment.getNote());
        assertEquals(evidences, comment.getEvidences());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

  
}
