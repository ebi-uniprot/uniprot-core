package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;
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
    void testSetmethod() {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =
                builder.method(MassSpectrometryMethod.ELECTROSPRAY).build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertNull(comment.getMolWeight());
        assertFalse(comment.getMolWeightError() != null);
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
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
        assertEquals(0, comment.getRanges().size());
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
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    void malWeightError_lessThanFloatNegative() {
        Float err = Float.MIN_VALUE - 2 ;
        MassSpectrometryComment comment = new MassSpectrometryCommentBuilder().molWeightError(err).build();
        assertEquals(err, comment.getMolWeightError(), Double.MIN_VALUE);
    }

    @Test
    void malWeightError_FloatMin_notValidValue() {
        MassSpectrometryComment comment = new MassSpectrometryCommentBuilder().molWeightError(Float.MIN_VALUE).build();
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
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    void testSetranges() {
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
    }

    @Test
    void testSetEvidences() {
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
    }

    @Test
    void testCreateMassSpectrometryRange() {
        int start = 23;
        int end = 34;
        String isoformId = "some value";
        MassSpectrometryRange range = createMassSpectrometryRange(start, end, isoformId);
        assertEquals(start, range.getRange().getStart().getValue().intValue());
        assertEquals(end, range.getRange().getEnd().getValue().intValue());
        assertEquals(isoformId, range.getIsoformId());
    }

  @Test
  void canAddSingleEvidence() {
    MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().addEvidence(createEvidence()).build();
    assertNotNull(obj.getEvidences());
    assertFalse(obj.getEvidences().isEmpty());
    assertTrue(obj.hasEvidences());
  }

  @Test
  void nullEvidence_willBeIgnore() {
    MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().addEvidence(null).build();
    assertNotNull(obj.getEvidences());
    assertTrue(obj.getEvidences().isEmpty());
    assertFalse(obj.hasEvidences());
  }

  @Test
  void evidences_willConvertUnModifiable_toModifiable() {
    MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().evidences(Collections.emptyList()).addEvidence(createEvidence()).build();
    assertNotNull(obj.getEvidences());
    assertFalse(obj.getEvidences().isEmpty());
    assertTrue(obj.hasEvidences());
  }

  @Test
  void canAddListEvidences() {
    MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().evidences(createEvidences()).build();
    assertNotNull(obj.getEvidences());
    assertFalse(obj.getEvidences().isEmpty());
    assertTrue(obj.hasEvidences());
  }

  @Test
  void canAddSingleRange() {
    MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().addRange(createMassSpectrometryRange(1, 2, "isoformId")).build();
    assertNotNull(obj.getRanges());
    assertFalse(obj.getRanges().isEmpty());
    assertTrue(obj.hasRanges());
  }

  @Test
  void nullRange_willBeIgnore() {
    MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().addRange(null).build();
    assertNotNull(obj.getRanges());
    assertTrue(obj.getRanges().isEmpty());
    assertFalse(obj.hasRanges());
  }

  @Test
  void canCreateBuilderFromInstance() {
    MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().build();
    MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder().from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().build();
    MassSpectrometryComment obj2 = new MassSpectrometryCommentBuilder().build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }

    private MassSpectrometryRange createMassSpectrometryRange(
            int start, int end, String isoformId) {
        return new MassSpectrometryRangeBuilder()
                .isoformId(isoformId)
                .range(new Range(start, end))
                .build();
    }
}
