package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineFormater;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject.FTType;

class FtLineNoHeaderParserTest {
    @Test
    void testChain() {
        String ftLines =
                "CHAIN 20..873\n/note=\"104 kDa microneme/rhoptry antigen\"\n"
                        + "/id=\"PRO_0000232680\"\n";
        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);
        assertEquals(1, obj.fts.size());
        verify(
                obj.fts.get(0),
                FTType.CHAIN,
                "20",
                "873",
                "104 kDa microneme/rhoptry antigen",
                "PRO_0000232680");
    }

    private void verify(
            FtLineObject.FT ft,
            FTType type,
            String start,
            String end,
            String description,
            String ftid) {
        assertEquals(type, ft.type);
        assertEquals(start, ft.location_start);
        assertEquals(end, ft.location_end);
        assertEquals(description, ft.ft_text);
        assertEquals(ftid, ft.ftId);
    }

    @Test
    void testNonTer() {
        String ftLines = "NON_TER 1";
        ;
        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);
        assertEquals(1, obj.fts.size());
        verify(obj.fts.get(0), FTType.NON_TER, "1", "1", null, null);
    }

    @Test
    void testChain2() {
        String ftLines = "CHAIN ?..121\n/note=\"Potential\"\n" + "/id=\"PRO_5001267722\"";

        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);
        assertEquals(1, obj.fts.size());
        verify(obj.fts.get(0), FTType.CHAIN, "?", "121", "Potential", "PRO_5001267722");
    }

    @Test
    void testBinding() {
        String ftLines = "BINDING 138\n/note=\"NAD(P)HX; via amide nitrogen\"";

        ;
        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);
        assertEquals(1, obj.fts.size());
        verify(obj.fts.get(0), FTType.BINDING, "138", "138", "NAD(P)HX; via amide nitrogen", null);
    }

    @Test
    void testSignal() {
        String ftLines = "SIGNAL <1..33\n/note=\"Potential\"";

        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);
        assertEquals(1, obj.fts.size());
        verify(obj.fts.get(0), FTType.SIGNAL, "<1", "33", "Potential", null);
    }

    @Test
    void testCONFLICT() {
        String ftLines = "CONFLICT 124..127\n/note=\"GLTA -> ESHP (in Ref. 1; AAA98633)\"";

        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);
        assertEquals(1, obj.fts.size());
        verify(
                obj.fts.get(0),
                FTType.CONFLICT,
                "124",
                "127",
                "GLTA -> ESHP (in Ref. 1; AAA98633)",
                null);
    }

    @Test
    void testVARIANT() {
        String ftLines =
                "VARIANT 421\n/note=\"C -> R (in GS; dbSNP:rs28936387)\"\n/id=\"VAR_007115\"";

        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);
        assertEquals(1, obj.fts.size());
        verify(
                obj.fts.get(0),
                FTType.VARIANT,
                "421",
                "421",
                "C -> R (in GS; dbSNP:rs28936387)",
                "VAR_007115");
    }

    @Test
    void testMultiFt() {
        String ftLines =
                "VAR_SEQ 33..83\n/note=\"TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)\"\n"
                        + "/id=\"VSP_004370\"\n"
                        + "MUTAGEN 119\n/note=\"C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity\"\n"
                        + "HELIX 33..83\n"
                        + "TURN 3..33";

        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);

        assertEquals(4, obj.fts.size());
        verify(
                obj.fts.get(0),
                FTType.VAR_SEQ,
                "33",
                "83",
                "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)",
                "VSP_004370");
        verify(
                obj.fts.get(1),
                FTType.MUTAGEN,
                "119",
                "119",
                "C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity",
                null);
        verify(obj.fts.get(2), FTType.HELIX, "33", "83", null, null);
        verify(obj.fts.get(3), FTType.TURN, "3", "33", null, null);
    }

    @Test
    void testWithEvidence3() {
        String ftLines =
                "REGION 237..240\n/note=\"Sulfate 1 binding\"\n"
                        + "REGION 275..277\n/note=\"Phosphate 2 binding\"\n"
                        + "/evidence=\"ECO:0000006|PubMed:20858735, ECO:0000006\"";
        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);
        assertEquals(2, obj.fts.size());
        verify(obj.fts.get(0), FTType.REGION, "237", "240", "Sulfate 1 binding", null);
        verifyEvidences(obj, obj.fts.get(0), null);
        verify(obj.fts.get(1), FTType.REGION, "275", "277", "Phosphate 2 binding", null);
        verifyEvidences(
                obj,
                obj.fts.get(1),
                Arrays.asList(new String[] {"ECO:0000006|PubMed:20858735", "ECO:0000006"}));
    }

    @Test
    void testWithEvidence4() {
        String ftLines =
                "TRANSMEM 57..77\n/note=\"Helical; (Potential)\"\n"
                        + "/evidence=\"ECO:0000257|HAMAP-Rule:MF_03021\"";

        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);
        assertEquals(1, obj.fts.size());
        verify(obj.fts.get(0), FTType.TRANSMEM, "57", "77", "Helical; (Potential)", null);
        verifyEvidences(
                obj,
                obj.fts.get(0),
                Arrays.asList(new String[] {"ECO:0000257|HAMAP-Rule:MF_03021"}));
    }

    @Test
    void testWithEvidence() {
        String ftLines =
                "METAL 186\n/note=\"Calcium; via carbonyl oxygen\"\n"
                        + "/evidence=\"ECO:0000006|PubMed:20858735, ECO:0000006|PubMed:23640942\"";

        UniprotLineParser<FtLineObject> parser =
                new DefaultUniprotLineParserFactory().createFtLineParser();
        FtLineFormater formater = new FtLineFormater();
        String lines = formater.format(ftLines);
        FtLineObject obj = parser.parse(lines);
        assertEquals(1, obj.fts.size());
        verify(obj.fts.get(0), FTType.METAL, "186", "186", "Calcium; via carbonyl oxygen", null);
        verifyEvidences(
                obj,
                obj.fts.get(0),
                Arrays.asList(
                        new String[] {
                            "ECO:0000006|PubMed:20858735", "ECO:0000006|PubMed:23640942"
                        }));
    }

    private void verifyEvidences(FtLineObject obj, Object name, List<String> evidences) {
        List<String> expected = obj.getEvidenceInfo().getEvidences().get(name);
        assertEquals(expected, evidences);
    }
}
