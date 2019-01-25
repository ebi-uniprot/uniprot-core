package uk.ac.ebi.uniprot.domain.uniprot.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.builder.EvidenceLineBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.InternalLineBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.InternalSectionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.SourceLineBuilder;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class InternalSectionImplTest {

    @Test
    public void testCreateSourceLine() {
        String value = "some source value";
        SourceLine sourceLine = new SourceLineBuilder().setValue(value).createSourceLineImpl();
        assertEquals(value, sourceLine.getValue());
        TestHelper.verifyJson(sourceLine);
    }

    @Test
    public void testInternalSectionImplOnlyIL() {
        List<InternalLine> internalLines = new ArrayList<>();
        List<SourceLine> sourceLines = new ArrayList<>();
        internalLines.add(new InternalLineBuilder().setType(InternalLineType.CX).setValue("value1").createInternalLineImpl());
        internalLines.add(new InternalLineBuilder().setType(InternalLineType.EV).setValue("value2").createInternalLineImpl());
        sourceLines.add(new SourceLineBuilder().setValue("some value").createSourceLineImpl());
        InternalSection is = new InternalSectionBuilder().setInternalLines(internalLines).setEvidenceLines(null).setSourceLines(null).createInternalSectionImpl();
        assertEquals(internalLines, is.getInternalLines());
        assertTrue(is.getSourceLines().isEmpty());
        assertTrue(is.getEvidenceLines().isEmpty());
        TestHelper.verifyJson(is);
    }

    @Test
    public void testInternalSectionImplOnlyEL() {
        List<EvidenceLine> evlines = new ArrayList<>();
        LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
        String curator = "som curator";
        evlines.add(new EvidenceLineBuilder().setEvidence("ECO:0000269|PubMed:22481068").setCreateDate(createDate).setCurator(curator).createEvidenceLineImpl());
        InternalSection is = new InternalSectionBuilder().setInternalLines(null).setEvidenceLines(evlines).setSourceLines(null).createInternalSectionImpl();
        assertTrue(is.getSourceLines().isEmpty());
        assertTrue(is.getInternalLines().isEmpty());
        assertEquals(evlines, is.getEvidenceLines());
        TestHelper.verifyJson(is);
    }

    @Test
    public void testInternalSectionImpl() {
        List<InternalLine> internalLines = new ArrayList<>();
        List<SourceLine> sourceLines = new ArrayList<>();
        internalLines.add(new InternalLineBuilder().setType(InternalLineType.CX).setValue("value1").createInternalLineImpl());
        internalLines.add(new InternalLineBuilder().setType(InternalLineType.EV).setValue("value2").createInternalLineImpl());
        sourceLines.add(new SourceLineBuilder().setValue("some value").createSourceLineImpl());

        List<EvidenceLine> evlines = new ArrayList<>();
        LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
        String curator = "som curator";
        evlines.add(new EvidenceLineBuilder().setEvidence("ECO:0000269|PubMed:22481068").setCreateDate(createDate).setCurator(curator).createEvidenceLineImpl());
        InternalSection is = new InternalSectionBuilder().setInternalLines(internalLines).setEvidenceLines(evlines).setSourceLines(sourceLines).createInternalSectionImpl();
        assertEquals(internalLines, is.getInternalLines());
        assertEquals(sourceLines, is.getSourceLines());
        assertEquals(evlines, is.getEvidenceLines());
        TestHelper.verifyJson(is);
    }

    @Test
    void testEvidenceLineImpl() {
        LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
        String curator = "som curator";
        String evidence = "ECO:0000269|PubMed:22481068";
        EvidenceLine evline = new EvidenceLineBuilder().setEvidence(evidence).setCreateDate(createDate).setCurator(curator).createEvidenceLineImpl();
        assertEquals(createDate, evline.getCreateDate());
        assertEquals(curator, evline.getCurator());
        assertEquals(evidence, evline.getEvidence());
        assertEquals(evidence, evline.toEvidence().getValue());
        TestHelper.verifyJson(evline);

    }

    @Test
    void testCreateInternalLine() {
        InternalLineType type = InternalLineType.CP;
        String value = "some value";
        InternalLine internalLine = new InternalLineBuilder().setType(type).setValue(value).createInternalLineImpl();
        assertEquals(type, internalLine.getType());
        assertEquals(value, internalLine.getValue());
        TestHelper.verifyJson(internalLine);
    }
}
