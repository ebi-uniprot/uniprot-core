package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.rc.RcLineObject;
import org.uniprot.core.flatfile.parser.impl.rc.RcLineObject.RcTokenEnum;

class RcLineParserTest {
    @Test
    void test() {
        String rcLines = "RC   STRAIN=Sprague-Dawley; TISSUE=Liver;\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(2, obj.rcs.size());
        verify(
                obj.rcs.get(0),
                RcTokenEnum.STRAIN,
                Arrays.asList(new String[] {"Sprague-Dawley"}),
                null);
        verify(obj.rcs.get(1), RcTokenEnum.TISSUE, Arrays.asList(new String[] {"Liver"}), null);
    }

    private void verify(
            RcLineObject.RC obj, RcTokenEnum token, List<String> values, List<String> evidences) {
        assertEquals(token, obj.tokenType);
        assertEquals(values, obj.values);
        assertEquals(evidences, obj.getEvidenceInfo().getEvidences().get(obj.values.get(0)));
    }

    @Test
    void testMultTissue() {
        String rcLines = "RC   STRAIN=Holstein; TISSUE=Lymph node, and Mammary gland;\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(2, obj.rcs.size());
        verify(obj.rcs.get(0), RcTokenEnum.STRAIN, Arrays.asList(new String[] {"Holstein"}), null);
        verify(
                obj.rcs.get(1),
                RcTokenEnum.TISSUE,
                Arrays.asList(new String[] {"Lymph node", "Mammary gland"}),
                null);
    }

    @Test
    void testMultiLines() {
        String rcLines =
                "RC   STRAIN=AL.012, AZ.026, AZ.180, DC.005, GA.039, GA2181, IL.014, IL2.17,\n"
                        + "RC   IN.018, KY.172, KY2.37, LA.013, MI.035, MN.001, MNb027, and VA.015;\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(1, obj.rcs.size());
        verify(
                obj.rcs.get(0),
                RcTokenEnum.STRAIN,
                Arrays.asList(
                        new String[] {
                            "AL.012", "AZ.026", "AZ.180", "DC.005", "GA.039", "GA2181", "IL.014",
                            "IL2.17", "IN.018", "KY.172", "KY2.37", "LA.013", "MI.035", "MN.001",
                            "MNb027", "VA.015"
                        }),
                null);
        //	verify(obj.rcs.get(1), RcTokenEnum.TISSUE, Arrays.asList(new String[] {"Lymph node",
        // "Mammary gland"}), null);

    }

    @Test
    void testMultiLines2() {
        String rcLines =
                "RC   STRAIN=AL.012, AZ.026, AZ.180, DC.005, GA.039, GA2181, IL.014, IL2.17,\n"
                        + "RC   IN.018, KY.172, KY2.37, LA.013, MI.035, MN.001, MNb027, and VA.015;\n"
                        + "RC   TISSUE=Liver;\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(2, obj.rcs.size());
        verify(
                obj.rcs.get(0),
                RcTokenEnum.STRAIN,
                Arrays.asList(
                        new String[] {
                            "AL.012", "AZ.026", "AZ.180", "DC.005", "GA.039", "GA2181", "IL.014",
                            "IL2.17", "IN.018", "KY.172", "KY2.37", "LA.013", "MI.035", "MN.001",
                            "MNb027", "VA.015"
                        }),
                null);
        verify(obj.rcs.get(1), RcTokenEnum.TISSUE, Arrays.asList(new String[] {"Liver"}), null);
    }

    @Test
    void testMultiLinesWithEvidence() {
        String rcLines =
                "RC   PLASMID=pSd11_G1246 {ECO:0000313|EMBL:BAG16761.1,\n"
                        + "RC   ECO:0000269|PubMed:10433554}, pSd12_G1263\n"
                        + "RC   {ECO:0000269|PubMed:10433554}, pSd13_G1271 {ECO:0000313|PDB:3OW2,\n"
                        + "RC   ECO:0000256|HAMAP-Rule:MF_00205}, pSd4_G1190 {ECO:0000303|Ref.6}, and\n"
                        + "RC   pSd5_G1213 {ECO:0000256|HAMAP-Rule:MF_00205};\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(1, obj.rcs.size());
        verify(
                obj.rcs.get(0),
                RcTokenEnum.PLASMID,
                Arrays.asList(
                        new String[] {
                            "pSd11_G1246", "pSd12_G1263", "pSd13_G1271", "pSd4_G1190", "pSd5_G1213"
                        }),
                Arrays.asList(
                        new String[] {
                            "ECO:0000313|EMBL:BAG16761.1", "ECO:0000269|PubMed:10433554"
                        }));
    }

    @Test
    void testMultiLinesWithEvidence2() {
        String rcLines =
                "RC   STRAIN=439-80 / Serotype O:9 {ECO:0000313|EMBL:BAG16761.1,\n"
                        + "RC   ECO:0000269|PubMed:10433554}, and pSd11_G1246\n"
                        + "RC   {ECO:0000269|PubMed:10433554};\n"
                        + "RC   PLASMID=pYV {ECO:0000313|PDB:3OW2, ECO:0000256|HAMAP-Rule:MF_00205},\n"
                        + "RC   and pSd2_G1252 {ECO:0000303|Ref.6};\n"
                        + "RC   TRANSPOSON=Tn2502 {ECO:0000256|HAMAP-Rule:MF_00205}, and pSd4_G1190\n"
                        + "RC   {ECO:0000256|HAMAP-Rule:MF_00205};\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(3, obj.rcs.size());
        verify(
                obj.rcs.get(0),
                RcTokenEnum.STRAIN,
                Arrays.asList(new String[] {"439-80 / Serotype O:9", "pSd11_G1246"}),
                Arrays.asList(
                        new String[] {
                            "ECO:0000313|EMBL:BAG16761.1", "ECO:0000269|PubMed:10433554"
                        }));
        verify(
                obj.rcs.get(1),
                RcTokenEnum.PLASMID,
                Arrays.asList(new String[] {"pYV", "pSd2_G1252"}),
                Arrays.asList(
                        new String[] {"ECO:0000313|PDB:3OW2", "ECO:0000256|HAMAP-Rule:MF_00205"}));
        verify(
                obj.rcs.get(2),
                RcTokenEnum.TRANSPOSON,
                Arrays.asList(new String[] {"Tn2502", "pSd4_G1190"}),
                Arrays.asList(new String[] {"ECO:0000256|HAMAP-Rule:MF_00205"}));
    }

    @Test
    void testContentChangeLine() {
        String rcLines =
                "RC   STRAIN=ATCC 6260 / CBS 566 / DSM 6381 / JCM 1539 / NBRC 10279 / NRRL\n"
                        + "RC   Y-324;\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(1, obj.rcs.size());
        verify(
                obj.rcs.get(0),
                RcTokenEnum.STRAIN,
                Arrays.asList(
                        new String[] {
                            "ATCC 6260 / CBS 566 / DSM 6381 / JCM 1539 / NBRC 10279 / NRRL Y-324"
                        }),
                null);
    }

    @Test
    void testContentComma() {
        String rcLines = "RC   STRAIN=PP24[03,07,10];\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(1, obj.rcs.size());
        verify(
                obj.rcs.get(0),
                RcTokenEnum.STRAIN,
                Arrays.asList(new String[] {"PP24[03,07,10]"}),
                null);
    }

    void testContentAnd() {
        String rcLines = "RC   STRAIN=Black and white Danish dairy cattle;\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(1, obj.rcs.size());
        verify(
                obj.rcs.get(0),
                RcTokenEnum.STRAIN,
                Arrays.asList(new String[] {"Black and white Danish dairy cattle"}),
                null);
    }

    void testContentComma2() {
        String rcLines = "RC   PLASMID=MCA 2997, and Plasmid pMR3, Mitochondrial;\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(1, obj.rcs.size());
        verify(
                obj.rcs.get(0),
                RcTokenEnum.PLASMID,
                Arrays.asList(new String[] {"MCA 2997", "Plasmid pMR3, Mitochondrial"}),
                null);
    }

    @Test
    void testContentChangeLineWithSlash() {
        String rcLines =
                "RC   STRAIN=ATCC 15692 / DSM 22644 / CIP 104116 / JCM 14847 / LMG 12228 /\n"
                        + "RC   1C / PRS 101 / PAO1;\n";
        UniprotKBLineParser<RcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRcLineParser();
        RcLineObject obj = parser.parse(rcLines);
        assertEquals(1, obj.rcs.size());
        verify(
                obj.rcs.get(0),
                RcTokenEnum.STRAIN,
                Arrays.asList(
                        "ATCC 15692 / DSM 22644 / CIP 104116 / JCM 14847 / LMG 12228 / 1C / PRS 101 / PAO1"),
                null);
    }
}
