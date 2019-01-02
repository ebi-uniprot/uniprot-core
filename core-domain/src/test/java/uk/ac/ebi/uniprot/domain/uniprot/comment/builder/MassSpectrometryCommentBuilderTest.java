package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MassSpectrometryCommentBuilderTest {

    @Test
    public void testNewInstance() {
        MassSpectrometryCommentBuilder builder1 = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryCommentBuilder builder2 = MassSpectrometryCommentBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetMassSpectrometryMethod() {
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =
                builder.massSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
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
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =

        builder.massSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .molWeight(0.234f)
        .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());

        assertEquals(0.234f, comment.getMolWeight().floatValue(), Float.MIN_VALUE);
        assertFalse(comment.getMolWeightError() !=null);
        assertFalse(comment.getNote() !=null);

        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetMolWeightError() {
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
 
        MassSpectrometryComment comment =

        builder.
        massSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .molWeight(0.234f)
        .molWeightError(0.123f)
        .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());

        assertEquals(0.234f, comment.getMolWeight().floatValue(), Float.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError().floatValue(), Double.MIN_VALUE);
        assertFalse(comment.getNote() !=null);

        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetNote() {
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =
        builder.massSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .molWeight(0.234f)
        .molWeightError(0.123f)
        .note("someNote")
        .build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234f, comment.getMolWeight().floatValue(), Double.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError().floatValue(), Double.MIN_VALUE);
        assertEquals("someNote", comment.getNote());
        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetMassSpectrometryRanges() {
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(MassSpectrometryCommentBuilder.createMassSpectrometryRange(12, 21, null));
        ranges.add(MassSpectrometryCommentBuilder.createMassSpectrometryRange(13, 25, "someValue"));
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =

        builder.massSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .molWeight(0.234f)
        .molWeightError(0.123f)
        .note("someNote")
        .massSpectrometryRanges(ranges)
        .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234f, comment.getMolWeight().floatValue(), Double.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError().floatValue(), Double.MIN_VALUE);
        assertEquals("someNote", comment.getNote());
        assertEquals(0, comment.getEvidences().size());
        assertEquals(ranges, comment.getRanges());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetEvidences() {
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(MassSpectrometryCommentBuilder.createMassSpectrometryRange(12, 21, null));
        ranges.add(MassSpectrometryCommentBuilder.createMassSpectrometryRange(13, 25, "someValue"));
        List<Evidence> evidences = createEvidences();
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =

        builder.massSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .molWeight(0.234f)
        .molWeightError(0.123f)
        .note("someNote")
        .massSpectrometryRanges(ranges)
        .evidences(evidences)
        .build();

        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234f, comment.getMolWeight().floatValue(), Double.MIN_VALUE);
        assertEquals(0.123f, comment.getMolWeightError().floatValue(), Double.MIN_VALUE);
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
        MassSpectrometryRange range = MassSpectrometryCommentBuilder.createMassSpectrometryRange(start, end, isoformId);
        assertEquals(start, range.getRange().getStart().getValue().intValue());
        assertEquals(end, range.getRange().getEnd().getValue().intValue());
        assertEquals(isoformId, range.getIsoformId());

    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

}
