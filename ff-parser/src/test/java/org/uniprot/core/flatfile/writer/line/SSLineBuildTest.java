package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.ss.SSEvidenceLineBuilder;
import org.uniprot.core.flatfile.parser.impl.ss.SSInternalLineBuilder;
import org.uniprot.core.flatfile.parser.impl.ss.SSLineBuilder;
import org.uniprot.core.flatfile.parser.impl.ss.SSSourceLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalLineType;
import org.uniprot.core.uniprot.InternalSection;
import org.uniprot.core.uniprot.SourceLine;
import org.uniprot.core.uniprot.builder.InternalLineBuilder;
import org.uniprot.core.uniprot.builder.InternalSectionBuilder;
import org.uniprot.core.uniprot.builder.SourceLineBuilder;
import org.uniprot.core.uniprot.evidence.EvidenceLine;
import org.uniprot.core.uniprot.evidence.builder.EvidenceLineBuilder;

class SSLineBuildTest {
    @Test
    void testEvidenceLines() {
        String ssLines =
                "**EV ECO:0000250; -; XXX; 01-JAN-1900.\n"
                        + "**EV ECO:0000313; Ensembl:ENSP00000483257; -; 05-JUN-2015.";
        String evidence1 = "ECO:0000250";
        List<EvidenceLine> evidenceLines = new ArrayList<>();
        evidenceLines.add(createEvidenceLine(evidence1, LocalDate.of(1900, 1, 1), "XXX"));
        String evidence2 = "ECO:0000313|Ensembl:ENSP00000483257";
        evidenceLines.add(createEvidenceLine(evidence2, LocalDate.of(2015, 6, 5), "-"));
        SSEvidenceLineBuilder builder = new SSEvidenceLineBuilder();
        FFLine ffLine = builder.build(evidenceLines);

        String resultString = ffLine.toString();
        // System.out.println(text.getText());
        System.out.println(resultString);
        assertEquals(ssLines, resultString);
    }

    @Test
    void testInternalLines() {
        String ssLines =
                "**SO UPD; 37856; 05-DEC-2008.\n"
                        + "**ZB LYG, 07-FEB-2006; LIB, 17-MAR-2006; LYG, 04-MAY-2006; ISC, 14-DEC-2006;\n"
                        + "**ZB ALG/LYG, 08-JAN-2007; LYG, 09-JAN-2007; MCB, 09-JAN-2007; RUE, 05-JUN-2007;";
        List<InternalLine> internalLines = new ArrayList<>();
        internalLines.add(createInternalLine(InternalLineType.SO, "UPD; 37856; 05-DEC-2008."));
        internalLines.add(
                createInternalLine(
                        InternalLineType.ZB,
                        "LYG, 07-FEB-2006; LIB, 17-MAR-2006; LYG, 04-MAY-2006; ISC, 14-DEC-2006;"));
        internalLines.add(
                createInternalLine(
                        InternalLineType.ZB,
                        "ALG/LYG, 08-JAN-2007; LYG, 09-JAN-2007; MCB, 09-JAN-2007; RUE, 05-JUN-2007;"));
        SSInternalLineBuilder builder = new SSInternalLineBuilder();
        FFLine ffLine = builder.build(internalLines);

        String resultString = ffLine.toString();
        // System.out.println(text.getText());
        System.out.println(resultString);
        assertEquals(ssLines, resultString);
    }

    @Test
    void testSourceLine() {
        String ssLines =
                "**   #################     SOURCE SECTION     ##################\n"
                        + "**   LOCUS       999163         29 aa\n"
                        + "**               26-SEP-1995";
        List<SourceLine> sourceLines = new ArrayList<>();
        sourceLines.add(createSourceLine("LOCUS       999163         29 aa"));
        sourceLines.add(createSourceLine("            26-SEP-1995"));
        SSSourceLineBuilder builder = new SSSourceLineBuilder();
        FFLine ffLine = builder.build(sourceLines);

        String resultString = ffLine.toString();
        // System.out.println(text.getText());
        System.out.println(resultString);
        assertEquals(ssLines, resultString);
    }

    @Test
    void testInterSection1() {
        String ssLines =
                "**\n"
                        + "**   #################    INTERNAL SECTION    ##################\n"
                        + "**EV ECO:0000313; ProtImp:UP123; -; 07-NOV-2006.\n"
                        + "**EV ECO:0000256; HAMAP-Rule:MF_01417; -; 01-OCT-2010.\n"
                        + "**EV ECO:0000256; SAAS:SAAS022644_004_000329; -; 11-FEB-2014.\n"
                        + "**DG dg-000-000-614_P;\n"
                        + "**ZD YOK, 19-NOV-2004;";
        String evidence1 = "ECO:0000313|ProtImp:UP123";
        List<EvidenceLine> evidenceLines = new ArrayList<>();
        evidenceLines.add(createEvidenceLine(evidence1, LocalDate.of(2006, 11, 7), "-"));
        String evidence2 = "ECO:0000256|HAMAP-Rule:MF_01417";
        evidenceLines.add(createEvidenceLine(evidence2, LocalDate.of(2010, 10, 1), "-"));
        String evidence3 = "ECO:0000256|SAAS:SAAS022644_004_000329";
        evidenceLines.add(createEvidenceLine(evidence3, LocalDate.of(2014, 2, 11), "-"));
        List<InternalLine> internalLines = new ArrayList<>();
        internalLines.add(createInternalLine(InternalLineType.DG, "dg-000-000-614_P;"));
        internalLines.add(createInternalLine(InternalLineType.ZD, "YOK, 19-NOV-2004;"));
        InternalSection is = createInternalSection(internalLines, evidenceLines, null);
        SSLineBuilder builder = new SSLineBuilder();

        FFLine ffLine = builder.build(is);

        String resultString = ffLine.toString();
        // System.out.println(text.getText());
        System.out.println(resultString);
        assertEquals(ssLines, resultString);
    }

    @Test
    void testInterSectionWithSource() {
        String ssLines =
                "**\n"
                        + "**   #################     SOURCE SECTION     ##################\n"
                        + "**   LOCUS       999163         29 aa\n"
                        + "**               26-SEP-1995\n"
                        + "**   #################    INTERNAL SECTION    ##################\n"
                        + "**EV ECO:0000313; ProtImp:UP123; -; 07-NOV-2006.\n"
                        + "**EV ECO:0000256; HAMAP-Rule:MF_01417; -; 01-OCT-2010.\n"
                        + "**EV ECO:0000256; SAAS:SAAS022644_004_000329; -; 11-FEB-2014.\n"
                        + "**DG dg-000-000-614_P;\n"
                        + "**ZD YOK, 19-NOV-2004;";
        String evidence1 = "ECO:0000313|ProtImp:UP123";
        List<EvidenceLine> evidenceLines = new ArrayList<>();
        evidenceLines.add(createEvidenceLine(evidence1, LocalDate.of(2006, 11, 7), "-"));
        String evidence2 = "ECO:0000256|HAMAP-Rule:MF_01417";
        evidenceLines.add(createEvidenceLine(evidence2, LocalDate.of(2010, 10, 1), "-"));
        String evidence3 = "ECO:0000256|SAAS:SAAS022644_004_000329";

        evidenceLines.add(createEvidenceLine(evidence3, LocalDate.of(2014, 2, 11), "-"));
        List<InternalLine> internalLines = new ArrayList<>();
        internalLines.add(createInternalLine(InternalLineType.DG, "dg-000-000-614_P;"));
        internalLines.add(createInternalLine(InternalLineType.ZD, "YOK, 19-NOV-2004;"));

        List<SourceLine> sourceLines = new ArrayList<>();
        sourceLines.add(createSourceLine("LOCUS       999163         29 aa"));
        sourceLines.add(createSourceLine("            26-SEP-1995"));
        InternalSection is = createInternalSection(internalLines, evidenceLines, sourceLines);
        SSLineBuilder builder = new SSLineBuilder();

        FFLine ffLine = builder.build(is);

        String resultString = ffLine.toString();
        // System.out.println(text.getText());
        System.out.println(resultString);
        assertEquals(ssLines, resultString);
    }

    private InternalLine createInternalLine(InternalLineType type, String str) {
        return new InternalLineBuilder(type, str).build();
    }

    private SourceLine createSourceLine(String str) {
        return new SourceLineBuilder(str).build();
    }

    private InternalSection createInternalSection(
            List<InternalLine> internalLines,
            List<EvidenceLine> evidenceLines,
            List<SourceLine> sourceLines) {
        return new InternalSectionBuilder()
                .internalLines(internalLines)
                .evidenceLines(evidenceLines)
                .sourceLines(sourceLines)
                .build();
    }

    private EvidenceLine createEvidenceLine(String evidence, LocalDate date, String curator) {
        return new EvidenceLineBuilder()
                .evidence(evidence)
                .creationDate(date)
                .curator(curator)
                .build();
    }
}
