package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.EvidencedString;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.FreeText;

class CcLineTextCommentParserTest {
    @Test
    void test1() {
        String lines =
                "CC   -!- FUNCTION: This enzyme is necessary for target cell lysis in cell-\n"
                        + "CC       mediated immune responses. It cleaves after Lys or Arg, or may be\n"
                        + "CC       involved in apoptosis.\n"
                        + "CC   -!- CAUTION: Exons 1a and 1b of the sequence reported in\n"
                        + "CC       PubMed:17180578 are of human origin, however exon 2 shows strong\n"
                        + "CC       similarity to the rat sequence.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(2, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "This enzyme is necessary for target cell lysis in cell-mediated immune responses. "
                        + "It cleaves after Lys or Arg, or may be involved in apoptosis",
                Collections.emptyList());

        cc = obj.getCcs().get(1);
        assertEquals(CC.CCTopicEnum.ACTIVITY_REGULATION.CAUTION, cc.getTopic());
        assertTrue(cc.getObject() instanceof FreeText);
        texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Exons 1a and 1b of the sequence reported in "
                        + "PubMed:17180578 are of human origin, however exon 2 shows strong "
                        + "similarity to the rat sequence",
                Collections.emptyList());
    }

    private void verify(EvidencedString vStr, String text, List<String> evidences) {
        assertEquals(text, vStr.getValue());
        assertEquals(evidences, vStr.getEvidences());
    }

    @Test
    void testDotInSide() {
        String lines = "CC   -!- SUBUNIT: Interacts with daf-16 and sir-2.1.\n";

        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.SUBUNIT, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Interacts with daf-16 and sir-2.1",
                Collections.emptyList());
    }

    @Test
    void testDotInSide2() {
        String lines =
                "CC   -!- SUBUNIT: Interacts (via OIR domain) with ORC1 (via BAH domain).\n"
                        + "CC       Interacts with SIR4. Interacts with CAC1.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.SUBUNIT, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Interacts (via OIR domain) with ORC1 (via BAH domain). "
                        + "Interacts with SIR4. "
                        + "Interacts with CAC1",
                Collections.emptyList());
    }

    @Test
    void testWithEvidence() {
        String lines =
                "CC   -!- FUNCTION: May play a cri{tical role in virion formation. Essential\n"
                        + "CC       fo}r {virus} replication in vitro. {ECO:0000313|PDB:3OW2}.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "May play a cri{tical role in virion formation. Essential fo}r {virus} replication in vitro",
                Arrays.asList(new String[] {"ECO:0000313|PDB:3OW2"}));
    }

    @Test
    void testWithEvidenceAndCurly() {
        String lines =
                "CC   -!- FUNCTION: May play a cri{tical role in virion formation. Essential\n"
                        + "CC       fo}r {virus} replication in vitro.\n"
                        + "CC       {ECO:0000313|PDB:3OW2}.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "May play a cri{tical role in virion formation. Essential fo}r {virus} replication in vitro",
                Arrays.asList(new String[] {"ECO:0000313|PDB:3OW2"}));
    }

    @Test
    void testWithDashAtEnd() {
        String lines =
                "CC   -!- PATHWAY: Amino-acid biosynthesis; L-arginine biosynthesis; L-\n"
                        + "CC       arginine from L-ornithine and carbamoyl phosphate: step 2/3.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.PATHWAY, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Amino-acid biosynthesis; L-arginine biosynthesis; L-arginine from L-ornithine and carbamoyl phosphate: step 2/3",
                Arrays.asList(new String[] {}));
    }

    @Test
    void testWithQuote() {
        String lines =
                "CC   -!- FUNCTION: Transfers the 4'-phosphopantetheine moiety from coenzyme\n"
                        + "CC       A to a Ser of acyl-carrier protein (By similarity).\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Transfers the 4'-phosphopantetheine moiety from coenzyme A to a Ser of acyl-carrier protein (By similarity)",
                Arrays.asList(new String[] {}));
    }

    @Test
    void testWithEvidence2() {
        String lines =
                "CC   -!- FUNCTION: Transfers the 4'-phosphopantetheine moiety from coenzyme\n"
                        + "CC       A to a Ser of acyl-carrier protein. {ECO:0000006|PubMed:20858735, ECO:0000006}.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Transfers the 4'-phosphopantetheine moiety from coenzyme A to a Ser of acyl-carrier protein",
                Arrays.asList(new String[] {"ECO:0000006|PubMed:20858735", "ECO:0000006"}));
    }

    @Test
    void testBigComments() {
        String lines =
                "CC   -!- FUNCTION: Has immunoglobulin-binding and hemagglutination\n"
                        + "CC       properties, and can bind to mannose. Essential for virulence. May\n"
                        + "CC       be involved in LPS biosynthesis or polysaccharide transport.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: Cell membrane; Single-pass membrane protein\n"
                        + "CC       (Potential).\n"
                        + "CC   -!- DISRUPTION PHENOTYPE: Rough phenotype, with an aberrant O-antigen\n"
                        + "CC       profile. Mutants exhibit a reduced ability to colonize mouse\n"
                        + "CC       spleens but are still capable of producing a persistent infection,\n"
                        + "CC       albeit with a low bacterial burden.\n"
                        + "CC   -!- MISCELLANEOUS: Strongly immunoreactive, inducing both humoral and\n"
                        + "CC       cellular immune responses in hosts during the course of infection.\n"
                        + "CC   -!- SIMILARITY: Belongs to the BA14k family.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(5, obj.getCcs().size());
    }

    @Test
    void testLineWrapper() {

        String lines =
                "CC   -!- ACTIVITY REGULATION:\n"
                        + "CC       5-carboxyamino-1-(5-phospho-D-ribosyl)imidazole =\n"
                        + "CC       5-amino-1-(5-phospho-D-ribosyl)imidazole-4-carboxylate.\n"
                        + "CC       {ECO:0000256|PIRNR:PIRNR001338}.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.ACTIVITY_REGULATION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "5-carboxyamino-1-(5-phospho-D-ribosyl)imidazole = 5-amino-1-(5-phospho-D-ribosyl)imidazole-4-carboxylate",
                Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR001338"}));
    }

    @Test
    void testLineWrapper2() {

        String lines =
                "CC   -!- ACTIVITY REGULATION: Hydrolysis of proteins to small peptides in\n"
                        + "CC       the presence of ATP and magnesium. Alpha-casein is the usual test\n"
                        + "CC       substrate. In the absence of ATP, only oligopeptides shorter than\n"
                        + "CC       five residues are hydrolyzed (such as succinyl-Leu-Tyr-|-NHMec;\n"
                        + "CC       and Leu-Tyr-Leu-|-Tyr-Trp, in which cleavage of the -Tyr-|-Leu-\n"
                        + "CC       and -Tyr-|-Trp bonds also occurs).\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.ACTIVITY_REGULATION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Hydrolysis of proteins to small peptides in "
                        + "the presence of ATP and magnesium. "
                        + "Alpha-casein is the usual test substrate. "
                        + "In the absence of ATP, only oligopeptides shorter than "
                        + "five residues are hydrolyzed (such as succinyl-Leu-Tyr-|-NHMec; "
                        + "and Leu-Tyr-Leu-|-Tyr-Trp, in which cleavage of the -Tyr-|-Leu- "
                        + "and -Tyr-|-Trp bonds also occurs)",
                Collections.emptyList());
    }

    @Test
    void testLineWrapper3() {
        String lines =
                "CC   -!- SIMILARITY: Contains 1 MIT domain. {ECO:0000255|HAMAP-\n"
                        + "CC       Rule:MF_03021}.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.SIMILARITY, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Contains 1 MIT domain",
                Arrays.asList(new String[] {"ECO:0000255|HAMAP-Rule:MF_03021"}));
    }

    @Test
    void testNoHeaderWithEvidence() {
        String ccLineString =
                "FUNCTION: Transfers the 4'-phosphopantetheine moiety from coenzyme\n"
                        + "A to a Ser of acyl-carrier protein. {ECO:0000006|PubMed:20858735, ECO:0000006}.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineFormater formater = new CcLineFormater();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Transfers the 4'-phosphopantetheine moiety from coenzyme A to a Ser of acyl-carrier protein",
                Arrays.asList(new String[] {"ECO:0000006|PubMed:20858735", "ECO:0000006"}));
    }

    @Test
    void testNoHeader2() {

        String ccLineString =
                "ACTIVITY REGULATION: Hydrolysis of proteins to small peptides in\n"
                        + "the presence of ATP and magnesium. Alpha-casein is the usual test\n"
                        + "substrate. In the absence of ATP, only oligopeptides shorter than\n"
                        + "five residues are hydrolyzed (such as succinyl-Leu-Tyr-|-NHMec;\n"
                        + "and Leu-Tyr-Leu-|-Tyr-Trp, in which cleavage of the -Tyr-|-Leu-\n"
                        + "and -Tyr-|-Trp bonds also occurs).\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineFormater formater = new CcLineFormater();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.ACTIVITY_REGULATION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Hydrolysis of proteins to small peptides in "
                        + "the presence of ATP and magnesium. "
                        + "Alpha-casein is the usual test substrate. "
                        + "In the absence of ATP, only oligopeptides shorter than "
                        + "five residues are hydrolyzed (such as succinyl-Leu-Tyr-|-NHMec; "
                        + "and Leu-Tyr-Leu-|-Tyr-Trp, in which cleavage of the -Tyr-|-Leu- "
                        + "and -Tyr-|-Trp bonds also occurs)",
                Collections.emptyList());
    }

    @Test
    void testNoHeader3() {
        String ccLineString =
                "SIMILARITY: Contains 1 MIT domain. {ECO:0000255|HAMAP-\n" + "Rule:MF_03021}.";
        CcLineFormater formater = new CcLineFormater();
        String lines = formater.format(ccLineString);
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.SIMILARITY, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Contains 1 MIT domain",
                Arrays.asList(new String[] {"ECO:0000255|HAMAP-Rule:MF_03021"}));
    }

    @Test
    void testCcWithHeader() {
        String ccLineString =
                "FUNCTION: This enzyme is necessary for target cell lysis in cell-mediated immune responses. It cleaves after Lys or Arg. May be involved in apoptosis.\n"
                        + "CAUTION: Exons 1a and 1b of the sequence reported in PubMed:17180578 are of human origin, however exon 2 shows strong similarity to the rat sequence.\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineFormater formater = new CcLineFormater();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertEquals(2, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "This enzyme is necessary for target cell lysis in cell-mediated immune responses. "
                        + "It cleaves after Lys or Arg. "
                        + "May be involved in apoptosis",
                Collections.emptyList());

        cc = obj.getCcs().get(1);
        assertEquals(CC.CCTopicEnum.CAUTION, cc.getTopic());
        assertTrue(cc.getObject() instanceof FreeText);
        texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Exons 1a and 1b of the sequence reported in "
                        + "PubMed:17180578 are of human origin, however exon 2 shows strong "
                        + "similarity to the rat sequence",
                Collections.emptyList());
    }
}
