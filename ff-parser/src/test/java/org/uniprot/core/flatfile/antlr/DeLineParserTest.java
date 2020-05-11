package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject.FlagType;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject.NameBlock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class DeLineParserTest {
    @Test
    void test() {
        String deLines =
                "DE   RecName: Full=Annexin A5;\n"
                        + "DE            Short=Annexin-5;\n"
                        + "DE   AltName: Full=Annexin V;\n"
                        + "DE   AltName: Full=Lipocortin V;\n"
                        + "DE   AltName: Full=Placental anticoagulant protein I;\n"
                        + "DE            Short=PAP-I;\n"
                        + "DE   AltName: Full=PP4;\n"
                        + "DE   AltName: Full=Thromboplastin inhibitor;\n"
                        + "DE   AltName: Full=Vascular anticoagulant-alpha;\n"
                        + "DE            Short=VAC-alpha;\n"
                        + "DE   AltName: Full=Anchorin CII;\n"
                        + "DE   Flags: Precursor;\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);
        assertEquals(FlagType.Precursor, obj.getFlags().get(0));
        verify(
                obj.getRecName(),
                "Annexin A5",
                Arrays.asList(new String[] {"Annexin-5"}),
                Collections.emptyList());
        assertEquals(7, obj.getAltNames().size());
        verify(
                obj.getAltNames().get(0),
                "Annexin V",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        verify(
                obj.getAltNames().get(1),
                "Lipocortin V",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        verify(
                obj.getAltNames().get(2),
                "Placental anticoagulant protein I",
                Arrays.asList(new String[] {"PAP-I"}),
                Collections.emptyList());
        verify(
                obj.getAltNames().get(3),
                "PP4",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        verify(
                obj.getAltNames().get(4),
                "Thromboplastin inhibitor",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        verify(
                obj.getAltNames().get(5),
                "Vascular anticoagulant-alpha",
                Arrays.asList(new String[] {"VAC-alpha"}),
                Collections.emptyList());
        verify(
                obj.getAltNames().get(6),
                "Anchorin CII",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
    }

    private void verify(
            DeLineObject.Name name, String fullName, List<String> shortNames, List<String> ecs) {
        assertEquals(fullName, name.getFullName());
        assertEquals(shortNames, name.getShortNames());
        assertEquals(ecs, name.getEcs());
    }

    private void verifyEvidences(DeLineObject obj, Object name, List<String> evidences) {
        List<String> expected = obj.getEvidenceInfo().getEvidences().get(name);
        assertEquals(expected, evidences);
    }

    @Test
    void testWithEvidence() {
        String deLines =
                "DE   RecName: Full=Annexin A5 {ECO:0000256|PIRNR:PIRNR038994};\n"
                        + "DE            Short=Annexin-5 {ECO:0000256|PIRNR:PIRNR038994,"
                        + " ECO:0000256|PIRNR:PIRNR038995};\n"
                        + "DE   AltName: Full=Annexin V {ECO:0000256|PIRNR:PIRNR038994};\n"
                        + "DE   AltName: Full=Lipocortin V {ECO:0000256|PIRNR:PIRNR038994};\n"
                        + "DE   AltName: Full=Placental anticoagulant protein I"
                        + " {ECO:0000256|PIRNR:PIRNR038994};\n"
                        + "DE            Short=PAP-I {ECO:0000256|PIRNR:PIRNR038994};\n"
                        + "DE   AltName: Full=PP4 {ECO:0000256|PIRNR:PIRNR038994};\n"
                        + "DE   AltName: Full=Thromboplastin inhibitor"
                        + " {ECO:0000256|PIRNR:PIRNR038995};\n"
                        + "DE   AltName: Full=Vascular anticoagulant-alpha"
                        + " {ECO:0000256|PIRNR:PIRNR038995};\n"
                        + "DE            Short=VAC-alpha {ECO:0000256|PIRNR:PIRNR038994};\n"
                        + "DE   AltName: Full=Anchorin CII {ECO:0000256|PIRNR:PIRNR038996};\n"
                        + "DE   Flags: Precursor {ECO:0000256|PIRNR:PIRNR038994,"
                        + " ECO:0000256|PIRNR:PIRNR038995, ECO:0000256|PIRNR:PIRNR038998};\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);
        assertEquals(FlagType.Precursor, obj.getFlags().get(0));
        verifyEvidences(
                obj,
                FlagType.Precursor,
                Arrays.asList(
                        new String[] {
                            "ECO:0000256|PIRNR:PIRNR038994",
                            "ECO:0000256|PIRNR:PIRNR038995",
                            "ECO:0000256|PIRNR:PIRNR038998"
                        }));
        verify(
                obj.getRecName(),
                "Annexin A5",
                Arrays.asList(new String[] {"Annexin-5"}),
                Collections.emptyList());
        verifyEvidences(
                obj, "Annexin A5", Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR038994"}));

        assertEquals(7, obj.getAltNames().size());
        verify(
                obj.getAltNames().get(0),
                "Annexin V",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        verifyEvidences(
                obj, "Annexin V", Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR038994"}));
        verify(
                obj.getAltNames().get(1),
                "Lipocortin V",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        verifyEvidences(
                obj, "Lipocortin V", Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR038994"}));

        verify(
                obj.getAltNames().get(2),
                "Placental anticoagulant protein I",
                Arrays.asList(new String[] {"PAP-I"}),
                Collections.emptyList());
        verifyEvidences(
                obj,
                "Placental anticoagulant protein I",
                Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR038994"}));
        verifyEvidences(
                obj, "PAP-I", Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR038994"}));
        verify(
                obj.getAltNames().get(3),
                "PP4",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        verifyEvidences(obj, "PP4", Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR038994"}));
        verify(
                obj.getAltNames().get(4),
                "Thromboplastin inhibitor",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        verifyEvidences(
                obj,
                "Thromboplastin inhibitor",
                Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR038995"}));
        verify(
                obj.getAltNames().get(5),
                "Vascular anticoagulant-alpha",
                Arrays.asList(new String[] {"VAC-alpha"}),
                Collections.emptyList());
        verifyEvidences(
                obj,
                "Vascular anticoagulant-alpha",
                Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR038995"}));
        verifyEvidences(
                obj, "VAC-alpha", Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR038994"}));
        verify(
                obj.getAltNames().get(6),
                "Anchorin CII",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        verifyEvidences(
                obj, "Anchorin CII", Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR038996"}));
    }

    @Test
    void testWithNewEvidence() {
        String deLines =
                "DE   RecName: Full=Annexin A5 {ECO:0000006|PubMed:20858735,"
                        + " ECO:0000006|PubMed:23640942};\n"
                        + "DE            Short=Annexin-5 {ECO:0000006|PubMed:20858735,"
                        + " ECO:0000006|PubMed:23640943};\n"
                        + "DE   Flags: Precursor {ECO:0000006|PubMed:20858735,"
                        + " ECO:0000006|PubMed:23640942, ECO:0000001};\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);
        assertEquals(FlagType.Precursor, obj.getFlags().get(0));
        verifyEvidences(
                obj,
                FlagType.Precursor,
                Arrays.asList(
                        new String[] {
                            "ECO:0000006|PubMed:20858735",
                            "ECO:0000006|PubMed:23640942",
                            "ECO:0000001"
                        }));
        verify(
                obj.getRecName(),
                "Annexin A5",
                Arrays.asList(new String[] {"Annexin-5"}),
                Collections.emptyList());
        verifyEvidences(
                obj,
                "Annexin A5",
                Arrays.asList(
                        new String[] {
                            "ECO:0000006|PubMed:20858735", "ECO:0000006|PubMed:23640942"
                        }));
        verifyEvidences(
                obj,
                "Annexin-5",
                Arrays.asList(
                        new String[] {
                            "ECO:0000006|PubMed:20858735", "ECO:0000006|PubMed:23640943"
                        }));
    }

    @Test
    void testWithEC() {
        String deLines =
                "DE   AltName: Short=PAP-I;\n"
                        + "DE            EC=1.1.1.1;\n"
                        + "DE            EC=1.1.1.2;\n"
                        + "DE   AltName: EC=1.1.1.1;\n"
                        + "DE   AltName: Short=PAP-I;\n"
                        + "DE            Short=PAP.1;\n"
                        + "DE            Short=PAP.2;\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);

        verify(
                obj.getAltNames().get(0),
                null,
                Arrays.asList(new String[] {"PAP-I"}),
                Arrays.asList(new String[] {"1.1.1.1", "1.1.1.2"}));
        verify(
                obj.getAltNames().get(1),
                null,
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {"1.1.1.1"}));
        verify(
                obj.getAltNames().get(2),
                null,
                Arrays.asList(new String[] {"PAP-I", "PAP.1", "PAP.2"}),
                Collections.emptyList());
    }

    @Test
    void testWithContainInclude() {
        String deLines =
                "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;\n"
                    + "DE   Includes:\n"
                    + "DE     RecName: Full=Glutamate N-acetyltransferase;\n"
                    + "DE              EC=2.3.1.35;\n"
                    + "DE     AltName: Full=Ornithine acetyltransferase;\n"
                    + "DE              Short=OATase;\n"
                    + "DE     AltName: Full=Ornithine transacetylase;\n"
                    + "DE   Includes:\n"
                    + "DE     RecName: Full=Amino-acid acetyltransferase;\n"
                    + "DE              EC=2.3.1.-;\n"
                    + "DE     AltName: Full=N-acetylglutamate synthase;\n"
                    + "DE              Short=AGS;\n"
                    + "DE   Contains:\n"
                    + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha"
                    + " chain;\n"
                    + "DE   Contains:\n"
                    + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta"
                    + " chain;\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);

        verify(
                obj.getRecName(),
                "Arginine biosynthesis bifunctional protein argJ",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        assertEquals(2, obj.getIncludedNames().size());
        NameBlock include1 = obj.getIncludedNames().get(0);
        verify(
                include1.getRecName(),
                "Glutamate N-acetyltransferase",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {"2.3.1.35"}));
        verify(
                include1.getAltNames().get(0),
                "Ornithine acetyltransferase",
                Arrays.asList(new String[] {"OATase"}),
                Arrays.asList(new String[] {}));
        verify(
                include1.getAltNames().get(1),
                "Ornithine transacetylase",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {}));
        NameBlock include2 = obj.getIncludedNames().get(1);
        verify(
                include2.getRecName(),
                "Amino-acid acetyltransferase",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {"2.3.1.-"}));
        verify(
                include2.getAltNames().get(0),
                "N-acetylglutamate synthase",
                Arrays.asList(new String[] {"AGS"}),
                Arrays.asList(new String[] {}));

        assertEquals(2, obj.getContainedNames().size());
        NameBlock contains1 = obj.getContainedNames().get(0);
        NameBlock contains2 = obj.getContainedNames().get(1);
        verify(
                contains1.getRecName(),
                "Arginine biosynthesis bifunctional protein argJ alpha chain",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {}));
        verify(
                contains2.getRecName(),
                "Arginine biosynthesis bifunctional protein argJ beta chain",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {}));
    }

    @Test
    void testFlags() {
        String deLines = "DE   RecName: Full=UI;\n" + "DE   Flags: Precursor; Fragments;\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);
        verify(
                obj.getRecName(),
                "UI",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {}));
        assertEquals(FlagType.Precursor, obj.getFlags().get(0));
        assertEquals(FlagType.Fragments, obj.getFlags().get(1));
    }

    @Test
    void testAllergen() {
        String deLines =
                "DE   RecName: Full=13S globulin seed storage protein 3;\n"
                        + "DE   AltName: Full=Legumin-like protein 3;\n"
                        + "DE   AltName: Allergen=Fag e 1;\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);
        verify(
                obj.getRecName(),
                "13S globulin seed storage protein 3",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        verify(
                obj.getAltNames().get(0),
                "Legumin-like protein 3",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
        assertEquals("Fag e 1", obj.getAltAllergen());
    }

    @Test
    void testEcn2() {
        String deLines =
                "DE   RecName: Full=Amino acid--[acyl-carrier-protein] ligase 1;\n"
                        + "DE            EC=6.2.1.n2;\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);
        verify(
                obj.getRecName(),
                "Amino acid--[acyl-carrier-protein] ligase 1",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {"6.2.1.n2"}));
    }

    @Test
    void testWithSemiColon() {
        String deLines =
                "DE   RecName: Full=Dual specificity phosphatase Cdc25;\n"
                        + "DE            EC=3.1.3.48;\n"
                        + "DE   AltName: Full=Arath;CDC25;\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);
        verify(
                obj.getRecName(),
                "Dual specificity phosphatase Cdc25",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {"3.1.3.48"}));
        verify(
                obj.getAltNames().get(0),
                "Arath;CDC25",
                Arrays.asList(new String[] {}),
                Collections.emptyList());
    }

    @Test
    void testNamewithSemiColonEcEvidence() {
        String deLines =
                "DE   SubName: Full=Cryptic phospho-beta-glucosidase; cryptic"
                        + " {ECO:0000313|EMBL:CSQ00014.1};\n"
                        + "DE            EC=3.2.1.86 {ECO:0000313|EMBL:CSQ00014.1};\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);
        verify(
                obj.getSubNames().get(0),
                "Cryptic phospho-beta-glucosidase; cryptic",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {"3.2.1.86"}));
        verifyEvidences(
                obj,
                "Cryptic phospho-beta-glucosidase; cryptic",
                Arrays.asList(new String[] {"ECO:0000313|EMBL:CSQ00014.1"}));
        DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
        ecEvidence.setEcValue("3.2.1.86");
        ecEvidence.setNameECBelong(obj.getSubNames().get(0));

        verifyEvidences(
                obj, ecEvidence, Arrays.asList(new String[] {"ECO:0000313|EMBL:CSQ00014.1"}));
    }

    @Test
    void testSubnames() {
        String deLines =
                "DE   SubName: Full=Conjugal transfer protein;\n"
                        + "DE   SubName: Full=Conjugative transfer protein;\n"
                        + "DE   SubName: Full=TraR;\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);
        verify(
                obj.getSubNames().get(0),
                "Conjugal transfer protein",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {}));
        verify(
                obj.getSubNames().get(1),
                "Conjugative transfer protein",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {}));
        verify(
                obj.getSubNames().get(2),
                "TraR",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {}));
    }

    @Test
    void testWithContainIncludeEvidence() {
        String deLines =
                "DE   RecName: Full=Some name {ECO:12345|Ref.1,"
                        + " ECO:0000256|HAMAP-Rule:MF_00205};\n"
                        + "DE            Short=SN {ECO:12345|Ref.2,"
                        + " ECO:0000256|HAMAP-Rule:MF_00206};\n"
                        + "DE            EC=3.4.21.10 {ECO:12345|Ref.3,"
                        + " ECO:0000256|HAMAP-Rule:MF_00205};\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Chain A {ECO:12345|Ref.1};\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Chain B {ECO:12345|Ref.2};\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);

        verify(
                obj.getRecName(),
                "Some name",
                Arrays.asList(new String[] {"SN"}),
                Arrays.asList(new String[] {"3.4.21.10"}));
        verifyEvidences(
                obj,
                "Some name",
                Arrays.asList(new String[] {"ECO:12345|Ref.1", "ECO:0000256|HAMAP-Rule:MF_00205"}));
        verifyEvidences(
                obj,
                "SN",
                Arrays.asList(new String[] {"ECO:12345|Ref.2", "ECO:0000256|HAMAP-Rule:MF_00206"}));
        DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
        ecEvidence.setEcValue("3.4.21.10");
        ecEvidence.setNameECBelong(obj.getRecName());

        verifyEvidences(
                obj,
                ecEvidence,
                Arrays.asList(new String[] {"ECO:12345|Ref.3", "ECO:0000256|HAMAP-Rule:MF_00205"}));

        assertEquals(2, obj.getContainedNames().size());
        NameBlock contains1 = obj.getContainedNames().get(0);
        NameBlock contains2 = obj.getContainedNames().get(1);
        verify(
                contains1.getRecName(),
                "Chain A",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {}));
        verify(
                contains2.getRecName(),
                "Chain B",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {}));
        verifyEvidences(obj, "Chain A", Arrays.asList(new String[] {"ECO:12345|Ref.1"}));
        verifyEvidences(obj, "Chain B", Arrays.asList(new String[] {"ECO:12345|Ref.2"}));
    }

    @Test
    void testWithCurlyBracket() {
        String deLines =
                "DE   RecName:"
                    + " Full=(4-*{*4-[2-(gamma-L-glutamylamino)ethyl]phenoxymethyl*}*furan-2-yl)methanamine"
                    + " synthase {ECO:12345|Ref.1};\n";
        UniprotKBLineParser<DeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDeLineParser();
        DeLineObject obj = parser.parse(deLines);

        verify(
                obj.getRecName(),
                "(4-*{*4-[2-(gamma-L-glutamylamino)ethyl]phenoxymethyl*}*furan-2-yl)methanamine"
                        + " synthase",
                Arrays.asList(new String[] {}),
                Arrays.asList(new String[] {}));
        verifyEvidences(
                obj,
                "(4-*{*4-[2-(gamma-L-glutamylamino)ethyl]phenoxymethyl*}*furan-2-yl)methanamine"
                        + " synthase",
                Arrays.asList(new String[] {"ECO:12345|Ref.1"}));
    }
}

//  test("test with curly bracket inside") {
//    val deLines = """DE   RecName:
// Full=(4-*{*4-[2-(gamma-L-glutamylamino)ethyl]phenoxymethyl*}*furan-2-yl)methanamine synthase
// {ECO:12345|Ref.1};
//                    |""".stripMargin.replace("\r", "");
//
//    val obj = (new DefaultUniprotKBLineParserFactory).createDeLineParser().parse(deLines)
//
//    obj should not be null
//    obj.recName.fullName should equal
// ("(4-*{*4-[2-(gamma-L-glutamylamino)ethyl]phenoxymethyl*}*furan-2-yl)methanamine synthase")
//    obj.getEvidenceInfo.getEvidences().get(obj.recName.fullName) should contain
// ("ECO:12345|Ref.1")
//
//  }
//
//
// }
