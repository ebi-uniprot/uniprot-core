package org.uniprot.core.uniprot.impl;

import org.junit.jupiter.api.Test;

import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalLineType;
import org.uniprot.core.uniprot.InternalSection;
import org.uniprot.core.uniprot.SourceLine;
import org.uniprot.core.uniprot.builder.InternalLineBuilder;
import org.uniprot.core.uniprot.builder.InternalSectionBuilder;
import org.uniprot.core.uniprot.builder.SourceLineBuilder;
import org.uniprot.core.uniprot.evidence.EvidenceLine;
import org.uniprot.core.uniprot.evidence.builder.EvidenceLineBuilder;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InternalSectionImplTest {

    @Test
    public void testCreateSourceLine() {
        String value = "some source value";
        SourceLine sourceLine = new SourceLineBuilder(value).build();
        assertEquals(value, sourceLine.getValue());
    }

    @Test
    public void testInternalSectionImplOnlyIL() {
        List<InternalLine> internalLines = new ArrayList<>();
        List<SourceLine> sourceLines = new ArrayList<>();
        internalLines.add(new InternalLineBuilder(InternalLineType.CX, "value1").build());
        internalLines.add(new InternalLineBuilder(InternalLineType.EV, "value2").build());
        sourceLines.add(new SourceLineBuilder("some value").build());
        InternalSection is = new InternalSectionBuilder().internalLines(internalLines).build();
        assertEquals(internalLines, is.getInternalLines());
        assertTrue(is.getSourceLines().isEmpty());
        assertTrue(is.getEvidenceLines().isEmpty());
    }

    @Test
    public void testInternalSectionImplOnlyEL() {
        List<EvidenceLine> evlines = new ArrayList<>();
        LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
        String curator = "som curator";
        evlines.add(new EvidenceLineBuilder().evidence("ECO:0000269|PubMed:22481068").creationDate(createDate)
                            .curator(curator).build());
        InternalSection is = new InternalSectionBuilder().evidenceLines(evlines).build();
        assertTrue(is.getSourceLines().isEmpty());
        assertTrue(is.getInternalLines().isEmpty());
        assertEquals(evlines, is.getEvidenceLines());
    }

    @Test
    public void testInternalSectionImpl() {
        List<InternalLine> internalLines = new ArrayList<>();
        List<SourceLine> sourceLines = new ArrayList<>();
        internalLines.add(new InternalLineBuilder(InternalLineType.CX, "value1").build());
        internalLines.add(new InternalLineBuilder(InternalLineType.EV, "value2").build());
        sourceLines.add(new SourceLineBuilder("some value").build());

        List<EvidenceLine> evlines = new ArrayList<>();
        LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
        String curator = "som curator";
        evlines.add(new EvidenceLineBuilder().evidence("ECO:0000269|PubMed:22481068").creationDate(createDate)
                            .curator(curator).build());
        InternalSection is = new InternalSectionBuilder().internalLines(internalLines).evidenceLines(evlines)
                .sourceLines(sourceLines).build();
        assertEquals(internalLines, is.getInternalLines());
        assertEquals(sourceLines, is.getSourceLines());
        assertEquals(evlines, is.getEvidenceLines());
    }

    @Test
    void testEvidenceLineImpl() {
        LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
        String curator = "som curator";
        String evidence = "ECO:0000269|PubMed:22481068";
        EvidenceLine evline = new EvidenceLineBuilder().evidence(evidence).creationDate(createDate).curator(curator)
                .build();
        assertEquals(createDate, evline.getCreateDate());
        assertEquals(curator, evline.getCurator());
        assertEquals(evidence, evline.getEvidence());
        assertEquals(evidence, evline.toEvidence().getValue());
    }

    @Test
    void testCreateInternalLine() {
        InternalLineType type = InternalLineType.CP;
        String value = "some value";
        InternalLine internalLine = new InternalLineBuilder(type, value).build();
        assertEquals(type, internalLine.getType());
        assertEquals(value, internalLine.getValue());
    }
}
