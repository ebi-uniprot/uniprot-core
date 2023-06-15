package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
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
                        + "CC       mediated immune responses. It cleaves after Lys or Arg, or may"
                        + " be\n"
                        + "CC       involved in apoptosis.\n"
                        + "CC   -!- CAUTION: Exons 1a and 1b of the sequence reported in\n"
                        + "CC       PubMed:17180578 are of human origin, however exon 2 shows strong\n"
                        + "CC       similarity to the rat sequence.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
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

        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
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
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
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
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "May play a cri{tical role in virion formation. Essential fo}r {virus} replication"
                        + " in vitro",
                Arrays.asList(new String[] {"ECO:0000313|PDB:3OW2"}));
    }

    @Test
    void testWithEvidenceAndCurly() {
        String lines =
                "CC   -!- FUNCTION: May play a cri{tical role in virion formation. Essential\n"
                        + "CC       fo}r {virus} replication in vitro.\n"
                        + "CC       {ECO:0000313|PDB:3OW2}.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "May play a cri{tical role in virion formation. Essential fo}r {virus} replication"
                        + " in vitro",
                Arrays.asList(new String[] {"ECO:0000313|PDB:3OW2"}));
    }

    @Test
    void testWithDashAtEnd() {
        String lines =
                "CC   -!- PATHWAY: Amino-acid biosynthesis; L-arginine biosynthesis; L-\n"
                        + "CC       arginine from L-ornithine and carbamoyl phosphate: step 2/3.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.PATHWAY, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Amino-acid biosynthesis; L-arginine biosynthesis; L-arginine from L-ornithine and"
                        + " carbamoyl phosphate: step 2/3",
                Arrays.asList(new String[] {}));
    }

    @Test
    void testWithQuote() {
        String lines =
                "CC   -!- FUNCTION: Transfers the 4'-phosphopantetheine moiety from coenzyme\n"
                        + "CC       A to a Ser of acyl-carrier protein (By similarity).\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Transfers the 4'-phosphopantetheine moiety from coenzyme A to a Ser of"
                        + " acyl-carrier protein (By similarity)",
                Arrays.asList(new String[] {}));
    }

    @Test
    void testWithEvidence2() {
        String lines =
                "CC   -!- FUNCTION: Transfers the 4'-phosphopantetheine moiety from coenzyme\n"
                        + "CC       A to a Ser of acyl-carrier protein. {ECO:0000006|PubMed:20858735,"
                        + " ECO:0000006}.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.FUNCTION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Transfers the 4'-phosphopantetheine moiety from coenzyme A to a Ser of"
                        + " acyl-carrier protein",
                Arrays.asList(new String[] {"ECO:0000006|PubMed:20858735", "ECO:0000006"}));
    }

    @Test
    void testBigComments() {
        String lines =
                "CC   -!- FUNCTION: Has immunoglobulin-binding and hemagglutination\n"
                        + "CC       properties, and can bind to mannose. Essential for virulence."
                        + " May\n"
                        + "CC       be involved in LPS biosynthesis or polysaccharide transport.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: Cell membrane; Single-pass membrane"
                        + " protein\n"
                        + "CC       (Potential).\n"
                        + "CC   -!- DISRUPTION PHENOTYPE: Rough phenotype, with an aberrant"
                        + " O-antigen\n"
                        + "CC       profile. Mutants exhibit a reduced ability to colonize mouse\n"
                        + "CC       spleens but are still capable of producing a persistent"
                        + " infection,\n"
                        + "CC       albeit with a low bacterial burden.\n"
                        + "CC   -!- MISCELLANEOUS: Strongly immunoreactive, inducing both humoral"
                        + " and\n"
                        + "CC       cellular immune responses in hosts during the course of"
                        + " infection.\n"
                        + "CC   -!- SIMILARITY: Belongs to the BA14k family.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
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
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.ACTIVITY_REGULATION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "5-carboxyamino-1-(5-phospho-D-ribosyl)imidazole ="
                        + " 5-amino-1-(5-phospho-D-ribosyl)imidazole-4-carboxylate",
                Arrays.asList(new String[] {"ECO:0000256|PIRNR:PIRNR001338"}));
    }

    @Test
    void testLineWrapper2() {

        String lines =
                "CC   -!- ACTIVITY REGULATION: Hydrolysis of proteins to small peptides in\n"
                        + "CC       the presence of ATP and magnesium. Alpha-casein is the usual"
                        + " test\n"
                        + "CC       substrate. In the absence of ATP, only oligopeptides shorter"
                        + " than\n"
                        + "CC       five residues are hydrolyzed (such as succinyl-Leu-Tyr-|-NHMec;\n"
                        + "CC       and Leu-Tyr-Leu-|-Tyr-Trp, in which cleavage of the -Tyr-|-Leu-\n"
                        + "CC       and -Tyr-|-Trp bonds also occurs).\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
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
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
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
                        + "A to a Ser of acyl-carrier protein. {ECO:0000006|PubMed:20858735,"
                        + " ECO:0000006}.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
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
                "Transfers the 4'-phosphopantetheine moiety from coenzyme A to a Ser of"
                        + " acyl-carrier protein",
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
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
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
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
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
                "FUNCTION: This enzyme is necessary for target cell lysis in cell-mediated immune"
                        + " responses. It cleaves after Lys or Arg. May be involved in apoptosis.\n"
                        + "CAUTION: Exons 1a and 1b of the sequence reported in PubMed:17180578 are of"
                        + " human origin, however exon 2 shows strong similarity to the rat"
                        + " sequence.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
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

    @Test
    void testTextEndWithDots() throws Exception {
        String ccLine =
                "CC   -!- PTM: Contains 2 disulfide bonds that can be either 'C1-C3, C2-C4' or\n"
                        + "CC       'C1-C4, C2-C3', since these disulfide connectivities have been observed\n"
                        + "CC       for conotoxins with cysteine framework V (for examples, see AC P0DQQ7\n"
                        + "CC       and AC P81755)... {ECO:0000305}.\n";

        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(ccLine);
        assertNotNull(obj);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.PTM, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        verify(
                texts.getTexts().get(0),
                "Contains 2 disulfide bonds that can be either 'C1-C3, C2-C4' or "
                        + "'C1-C4, C2-C3', since these disulfide connectivities have been observed "
                        + "for conotoxins with cysteine framework V (for examples, see AC P0DQQ7 "
                        + "and AC P81755)..",
                List.of("ECO:0000305"));
    }
    
    @Test
    void testWithCurlyBracket() throws Exception {
        String ccLine = "CC   -!- ACTIVITY REGULATION: Two trans-stilbene derivatives, 4,4'-[(E)-ethene-\n"
                + "CC       1,2 diylbis({5[(phenylcarbonyl)amino]benzene-2,1-diyl}sulfonylimino)]\n"
                + "CC       dibenzoic acid and its methoxy derivative 4,4'-[1,2-ethenediylbis({ 5-\n"
                + "CC       [(4-methoxybenzoyl)amino]-2,1phenylene}sulfonylimino)] dibenzoic acid,\n"
                + "CC       respectively SD1 and SD4, inhibit DNA binding with 50% inhibition at 20\n"
                + "CC       uM for SD1 and 1.7 uM for SD4. SD1 and SD4 have minimal inhibitory\n"
                + "CC       concentrations of 400 and 800 uM on strain H37Ra respectively.\n"
                + "CC       {ECO:0000269|PubMed:24916461}.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(ccLine);
        assertNotNull(obj);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertEquals(CC.CCTopicEnum.ACTIVITY_REGULATION, cc.getTopic());

        assertTrue(cc.getObject() instanceof FreeText);
        FreeText texts = (FreeText) cc.getObject();
        assertEquals(1, texts.getTexts().size());
        String text ="Two trans-stilbene derivatives, 4,4'-[(E)-ethene-"
                + "1,2 diylbis({5[(phenylcarbonyl)amino]benzene-2,1-diyl}sulfonylimino)]"
                + " dibenzoic acid and its methoxy derivative 4,4'-[1,2-ethenediylbis({ 5-"
                + "[(4-methoxybenzoyl)amino]-2,1phenylene}sulfonylimino)] dibenzoic acid,"
                + " respectively SD1 and SD4, inhibit DNA binding with 50% inhibition at 20"
                + " uM for SD1 and 1.7 uM for SD4. SD1 and SD4 have minimal inhibitory"
                + " concentrations of 400 and 800 uM on strain H37Ra respectively";
        List<String> evs = List.of("ECO:0000269|PubMed:24916461");
        
        verify(texts.getTexts().get(0), text, evs);
        

    }
}
