package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class MassSpectrometryCommentBuilderTest {

    @Test
    public void testNewInstance() {
        MassSpectrometryCommentBuilder builder1 = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryCommentBuilder builder2 = MassSpectrometryCommentBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1== builder2);
    }
    
    @Test
    public void testSetMassSpectrometryMethod() {
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =
        builder.setMassSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertNull(comment.getMolWeight());
        assertNull(comment.getMolWeightError());
        assertNull(comment.getNote());
        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    public void testSetMolWeight() {
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =
        builder.setMassSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .setMolWeight(0.234)
        .build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234, comment.getMolWeight().doubleValue(), Double.MIN_VALUE);
        assertNull(comment.getMolWeightError());
        assertNull(comment.getNote());
        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    public void testSetMolWeightError() {
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =
        builder.setMassSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .setMolWeight(0.234)
        .setMolWeightError(0.123)
        .build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234, comment.getMolWeight().doubleValue(), Double.MIN_VALUE);
        assertEquals(0.123, comment.getMolWeightError().doubleValue(), Double.MIN_VALUE);
        assertNull(comment.getNote());
        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    public void testSetNote() {
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =
        builder.setMassSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .setMolWeight(0.234)
        .setMolWeightError(0.123)
        .setNote("someNote")
        .build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234, comment.getMolWeight().doubleValue(), Double.MIN_VALUE);
        assertEquals(0.123, comment.getMolWeightError().doubleValue(), Double.MIN_VALUE);
        assertEquals("someNote", comment.getNote());
        assertEquals(0, comment.getEvidences().size());
        assertEquals(0, comment.getRanges().size());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    public void testSetMassSpectrometryRanges() {
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(MassSpectrometryCommentBuilder.createMassSpectrometryRange(12, 21, null));
        ranges.add(MassSpectrometryCommentBuilder.createMassSpectrometryRange(13, 25, "someValue"));
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =
        builder.setMassSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .setMolWeight(0.234)
        .setMolWeightError(0.123)
        .setNote("someNote")
        .setMassSpectrometryRanges(ranges)
        .build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234, comment.getMolWeight().doubleValue(), Double.MIN_VALUE);
        assertEquals(0.123, comment.getMolWeightError().doubleValue(), Double.MIN_VALUE);
        assertEquals("someNote", comment.getNote());
        assertEquals(0, comment.getEvidences().size());
        assertEquals(ranges, comment.getRanges());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    public void testSetEvidences() {
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(MassSpectrometryCommentBuilder.createMassSpectrometryRange(12, 21, null));
        ranges.add(MassSpectrometryCommentBuilder.createMassSpectrometryRange(13, 25, "someValue"));
        List<Evidence> evidences = createEvidences();
        MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
        MassSpectrometryComment comment =
        builder.setMassSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY)
        .setMolWeight(0.234)
        .setMolWeightError(0.123)
        .setNote("someNote")
        .setMassSpectrometryRanges(ranges)
        .setEvidences(evidences)
        .build();
        assertEquals(MassSpectrometryMethod.ELECTROSPRAY, comment.getMethod());
        assertEquals(0.234, comment.getMolWeight().doubleValue(), Double.MIN_VALUE);
        assertEquals(0.123, comment.getMolWeightError().doubleValue(), Double.MIN_VALUE);
        assertEquals("someNote", comment.getNote());
        assertEquals(evidences, comment.getEvidences());
        assertEquals(ranges, comment.getRanges());
        assertEquals(CommentType.MASS_SPECTROMETRY, comment.getCommentType());
    }

    @Test
    public void testCreateMassSpectrometryRange() {
        int start =23;
        int end = 34;
        String isoformId ="some value";
        MassSpectrometryRange range = MassSpectrometryCommentBuilder.createMassSpectrometryRange(start, end, isoformId);
        assertEquals(start, range.getStart().intValue());
        assertEquals(end, range.getEnd().intValue());
        assertEquals(isoformId, range.getIsoformId());
       
    }
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(EvidenceFactory.from("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(EvidenceFactory.from("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

}
