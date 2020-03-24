package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.*;

class CcLineInteractionCommentParserTest {
    @Test
    void test1() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P17980; P11450: fcp3c; NbExp=1; IntAct=EBI-126914, EBI-159556;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(1, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-126914",
                "EBI-159556",
                "fcp3c",
                "P17980",
                "P11450",
                null,
                false,
                1);
    }

    private void verify(
            InteractionObject io,
            String firstId,
            String secondId,
            String gene,
            String firstInteractant,
            String secondInteractant,
            String secondParent,
            boolean xeno,
            int nbexp) {
        assertEquals(firstId, io.getFirstId());
        assertEquals(secondId, io.getSecondId());
        assertEquals(gene, io.getGene());
        assertEquals(firstInteractant, io.getFirstInteractant());
        assertEquals(secondInteractant, io.getSecondInteractant());
        assertEquals(secondParent, io.getSecondInteractantParent());
        assertEquals(xeno, io.isXeno());
        assertEquals(nbexp, io.getNbexp());
    }

    @Test
    void test2() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P17980; Q9W1K5-1: CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(1, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-133844",
                "EBI-212772",
                "CG11299",
                "P17980",
                "Q9W1K5-1",
                null,
                false,
                1);
    }

    @Test
    void test3() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P17980; Q8NI08: -; NbExp=1; IntAct=EBI-80809, EBI-80799;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(1, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-80809",
                "EBI-80799",
                "-",
                "P17980",
                "Q8NI08",
                null,
                false,
                1);
    }

    @Test
    void testWithParent() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       D3ZAR1; PRO_0000017322 [P98158]: Lrp2; NbExp=3; IntAct=EBI-9250714, EBI-9251342;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(1, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-9250714",
                "EBI-9251342",
                "Lrp2",
                "D3ZAR1",
                "PRO_0000017322",
                "P98158",
                false,
                3);
    }

    @Test
    void testChainIdWithParent() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       PRO_0000033156; PRO_0000000092 [P05067]: APP; NbExp=4; IntAct=EBI-20824092, EBI-821758;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(1, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-20824092",
                "EBI-821758",
                "APP",
                "PRO_0000033156",
                "PRO_0000000092",
                "P05067",
                false,
                4);
    }

    @Test
    void test5() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P12345; P84198-1: VIM; Xeno; NbExp=4; IntAct=EBI-356498, EBI-457639;\n";
        ;
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(1, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-356498",
                "EBI-457639",
                "VIM",
                "P12345",
                "P84198-1",
                null,
                true,
                4);
    }

    @Test
    void test6() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P12345; P51618: IRAK1; NbExp=2; IntAct=EBI-448466, EBI-358664;\n"
                        + "CC       P12345; P51617: IRAK2; NbExp=3; IntAct=EBI-448472, EBI-358664;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(2, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-448466",
                "EBI-358664",
                "IRAK1",
                "P12345",
                "P51618",
                null,
                false,
                2);
        verify(
                ir.getInteractions().get(1),
                "EBI-448472",
                "EBI-358664",
                "IRAK2",
                "P12345",
                "P51617",
                null,
                false,
                3);
    }

    @Test
    void test7() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P12345; G5EC23: hcf-1; NbExp=2; IntAct=EBI-318108, EBI-4480523;\n"
                        + "CC       P12345; Q11184: let-756; NbExp=3; IntAct=EBI-318108, EBI-3843983;\n"
                        + "CC       P12345; Q10666: pop-1; Xeno; NbExp=2; IntAct=EBI-318108, EBI-317870;\n"
                        + "CC       P12345; Q21921: sir-2.1; NbExp=3; IntAct=EBI-318108, EBI-966082;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(4, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-318108",
                "EBI-4480523",
                "hcf-1",
                "P12345",
                "G5EC23",
                null,
                false,
                2);
        verify(
                ir.getInteractions().get(1),
                "EBI-318108",
                "EBI-3843983",
                "let-756",
                "P12345",
                "Q11184",
                null,
                false,
                3);

        verify(
                ir.getInteractions().get(2),
                "EBI-318108",
                "EBI-317870",
                "pop-1",
                "P12345",
                "Q10666",
                null,
                true,
                2);
        verify(
                ir.getInteractions().get(3),
                "EBI-318108",
                "EBI-966082",
                "sir-2.1",
                "P12345",
                "Q21921",
                null,
                false,
                3);
    }

    @Test
    void test8() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P12345; Q9W4W2: fs(1)Yb; NbExp=4; IntAct=EBI-2890374, EBI-3424083;\n"
                        + "CC       P12345; Q9VKM1: piwi; NbExp=4; IntAct=EBI-2890374, EBI-3406276;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(2, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-2890374",
                "EBI-3424083",
                "fs(1)Yb",
                "P12345",
                "Q9W4W2",
                null,
                false,
                4);
        verify(
                ir.getInteractions().get(1),
                "EBI-2890374",
                "EBI-3406276",
                "piwi",
                "P12345",
                "Q9VKM1",
                null,
                false,
                4);
    }

    @Test
    void test9() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P12345; Q67XQ1: At1g03430; NbExp=2; IntAct=EBI-1100967, EBI-1100725;\n"
                        + "CC       P12345; Q9C5A5: At5g08720/T2K12_70; NbExp=3; IntAct=EBI-1100967, EBI-1998000;\n"
                        + "CC       P12345; Q9SSW0: AZF3; NbExp=2; IntAct=EBI-1100967, EBI-1807790;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(3, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-1100967",
                "EBI-1100725",
                "At1g03430",
                "P12345",
                "Q67XQ1",
                null,
                false,
                2);
        verify(
                ir.getInteractions().get(1),
                "EBI-1100967",
                "EBI-1998000",
                "At5g08720/T2K12_70",
                "P12345",
                "Q9C5A5",
                null,
                false,
                3);
        verify(
                ir.getInteractions().get(2),
                "EBI-1100967",
                "EBI-1807790",
                "AZF3",
                "P12345",
                "Q9SSW0",
                null,
                false,
                2);
    }

    @Test
    void test10() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P12345; Q9V3G9: EG:BACR37P7.5; NbExp=1; IntAct=EBI-175067, EBI-162998;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(1, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-175067",
                "EBI-162998",
                "EG:BACR37P7.5",
                "P12345",
                "Q9V3G9",
                null,
                false,
                1);
    }

    @Test
    void test11() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P12345; Q9SZI2: NAP1;1; NbExp=4; IntAct=EBI-6913662, EBI-4424361;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(1, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-6913662",
                "EBI-4424361",
                "NAP1;1",
                "P12345",
                "Q9SZI2",
                null,
                false,
                4);
    }

    @Test
    void test12() {
        String lines =
                "CC   -!- INTERACTION:\n"
                        + "CC       P12345; A1Z199: BCR/ABL fusion; NbExp=2; IntAct=EBI-491549, EBI-7286259;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof Interaction);
        Interaction ir = (Interaction) cc.getObject();
        assertEquals(1, ir.getInteractions().size());

        verify(
                ir.getInteractions().get(0),
                "EBI-491549",
                "EBI-7286259",
                "BCR/ABL fusion",
                "P12345",
                "A1Z199",
                null,
                false,
                2);
    }

    @Test
    void testNoHeader() {
        String ccLineString =
                ("INTERACTION:\n"
                        + "P12345; Q9W158: CG4612; NbExp=1; IntAct=EBI-123485, EBI-89895;\n"
                        + "P12345; Q9VYI0: fne; NbExp=1; IntAct=EBI-123485, EBI-126770;");

        CcLineFormater formater = new CcLineFormater();
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeader2() {
        String ccLineString =
                ("INTERACTION:\n"
                        + "P12345; Q9W1K5-1: CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n"
                        + "P12345; O96017: CHEK2; NbExp=4; IntAct=EBI-372428, EBI-1180783;\n"
                        + "P12345; Q6ZWQ9: Myl12a (xeno); NbExp=3; IntAct=EBI-372428, EBI-8034418;");

        CcLineFormater formater = new CcLineFormater();
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }
}
