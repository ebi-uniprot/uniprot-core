package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

class InternalSectionImplTest {

    @Test
    void testCreateSourceLine() {
        String value = "some source value";
        SourceLine sourceLine = new SourceLineBuilder(value).build();
        assertEquals(value, sourceLine.getValue());
    }

    @Test
    void testInternalSectionImplOnlyIL() {
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
    void testInternalSectionImplOnlyEL() {
        List<EvidenceLine> evlines = new ArrayList<>();
        LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
        String curator = "som curator";
        evlines.add(
                new EvidenceLineBuilder()
                        .evidence("ECO:0000269|PubMed:22481068")
                        .creationDate(createDate)
                        .curator(curator)
                        .build());
        InternalSection is = new InternalSectionBuilder().evidenceLines(evlines).build();
        assertTrue(is.getSourceLines().isEmpty());
        assertTrue(is.getInternalLines().isEmpty());
        assertEquals(evlines, is.getEvidenceLines());
    }

    @Test
    void testInternalSectionImpl() {
        List<InternalLine> internalLines = new ArrayList<>();
        List<SourceLine> sourceLines = new ArrayList<>();
        internalLines.add(new InternalLineBuilder(InternalLineType.CX, "value1").build());
        internalLines.add(new InternalLineBuilder(InternalLineType.EV, "value2").build());
        sourceLines.add(new SourceLineBuilder("some value").build());

        List<EvidenceLine> evlines = new ArrayList<>();
        LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
        String curator = "som curator";
        evlines.add(
                new EvidenceLineBuilder()
                        .evidence("ECO:0000269|PubMed:22481068")
                        .creationDate(createDate)
                        .curator(curator)
                        .build());
        InternalSection is =
                new InternalSectionBuilder()
                        .internalLines(internalLines)
                        .evidenceLines(evlines)
                        .sourceLines(sourceLines)
                        .build();
        assertEquals(internalLines, is.getInternalLines());
        assertEquals(sourceLines, is.getSourceLines());
        assertEquals(evlines, is.getEvidenceLines());
    }

    @Test
    void testEvidenceLineImpl() {
        LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
        String curator = "som curator";
        String evidence = "ECO:0000269|PubMed:22481068";
        EvidenceLine evline =
                new EvidenceLineBuilder()
                        .evidence(evidence)
                        .creationDate(createDate)
                        .curator(curator)
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

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        InternalSection obj = new InternalSectionImpl();
        assertNotNull(obj);
        assertTrue(obj.getInternalLines().isEmpty());
        assertTrue(obj.getEvidenceLines().isEmpty());
        assertTrue(obj.getSourceLines().isEmpty());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        InternalSectionImpl impl =
                new InternalSectionImpl(
                        Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        InternalSection obj = InternalSectionBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
