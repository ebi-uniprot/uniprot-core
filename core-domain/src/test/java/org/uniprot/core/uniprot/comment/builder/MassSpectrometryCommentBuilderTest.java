package org.uniprot.core.uniprot.comment.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

public class MassSpectrometryCommentBuilderTest {
    @Test
    public void testNewInstance() {
        MassSpectrometryCommentBuilder builder1 = new MassSpectrometryCommentBuilder();
        MassSpectrometryCommentBuilder builder2 = new MassSpectrometryCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetmethod() {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =
                builder.method(MassSpectrometryMethod.ELECTROSPRAY)
                        .build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertNull(comment.getMolWeight());
        assertFalse(comment.getMolWeightError() != null);
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetMolWeight() {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =

                builder.method(MassSpectrometryMethod.ELECTROSPRAY)
                        .molWeight(0.234f)
                        .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());

        assertEquals(0.234f, comment.getMolWeight(), Float.MIN_VALUE);
        assertFalse(comment.getMolWeightError() != null);
        assertFalse(comment.getNote() != null);

        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetMolWeightError() {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();

        MassSpectrometryComment comment = builder.method(MassSpectrometryMethod.ELECTROSPRAY)
                .molWeight(0.234f)
                .molWeightError(0.123f)
                .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());

        assertEquals(0.234f, comment.getMolWeight(), Float.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError(), Double.MIN_VALUE);
        assertFalse(comment.getNote() != null);

        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetNote() {
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
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetranges() {
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(createMassSpectrometryRange(12, 21, null));
        ranges.add(createMassSpectrometryRange(13, 25, "someValue"));
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =

                builder.method(MassSpectrometryMethod.ELECTROSPRAY)
                        .molWeight(0.234f)
                        .molWeightError(0.123f)
                        .note("someNote")
                        .ranges(ranges)
                        .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234f, comment.getMolWeight(), Double.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError(), Double.MIN_VALUE);
        assertEquals("someNote", comment.getNote());
        assertEquals(0, comment.getEvidences().size());
        assertEquals(ranges, comment.getRanges());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetEvidences() {
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(createMassSpectrometryRange(12, 21, null));
        ranges.add(createMassSpectrometryRange(13, 25, "someValue"));
        List<Evidence> evidences = createEvidences();
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =

                builder.method(MassSpectrometryMethod.ELECTROSPRAY)
                        .molWeight(0.234f)
                        .molWeightError(0.123f)
                        .note("someNote")
                        .ranges(ranges)
                        .evidences(evidences)
                        .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234f, comment.getMolWeight(), Double.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError(), Double.MIN_VALUE);
        assertEquals("someNote", comment.getNote());
        assertEquals(evidences, comment.getEvidences());
        assertEquals(ranges, comment.getRanges());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testCreateMassSpectrometryRange() {
        int start = 23;
        int end = 34;
        String isoformId = "some value";
        MassSpectrometryRange range = createMassSpectrometryRange(start, end, isoformId);
        assertEquals(start, range.getRange().getStart().getValue().intValue());
        assertEquals(end, range.getRange().getEnd().getValue().intValue());
        assertEquals(isoformId, range.getIsoformId());
    }

    private MassSpectrometryRange createMassSpectrometryRange(int start, int end, String isoformId) {
        return new MassSpectrometryRangeBuilder()
                .isoformId(isoformId)
                .range(new Range(start, end))
                .build();
    }
}
