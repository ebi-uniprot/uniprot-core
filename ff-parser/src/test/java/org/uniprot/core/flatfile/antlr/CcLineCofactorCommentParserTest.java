package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CofactorItem;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.StructuredCofactor;

class CcLineCofactorCommentParserTest {
    @Test
    void test() {
        String lines =
                "CC   -!- COFACTOR: [Isoform 1]:\n"
                        + "CC       Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000255|HAMAP-Rule:MF_00087};\n"
                        + "CC       Name=Co(2+); Xref=ChEBI:CHEBI:48828; Evidence={ECO:0000255|HAMAP-Rule:MF_00088};\n"
                        + "CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).\n"
                        + "CC       {ECO:0000255|HAMAP-Rule:MF_00086};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof StructuredCofactor);
        StructuredCofactor ms = (StructuredCofactor) cc.getObject();
        List<CofactorItem> cofactors = ms.getCofactors();
        assertEquals(2, cofactors.size());
        assertEquals("Mg(2+)", cofactors.get(0).getName());
        assertEquals("ChEBI:CHEBI:18420", cofactors.get(0).getXref());
        assertEquals(
                "ECO:0000255|HAMAP-Rule:MF_00087",
                obj.getEvidenceInfo().getEvidences().get(cofactors.get(0)).get(0));

        assertEquals("Co(2+)", cofactors.get(1).getName());
        assertEquals("ChEBI:CHEBI:48828", cofactors.get(1).getXref());
        assertEquals(
                "ECO:0000255|HAMAP-Rule:MF_00088",
                obj.getEvidenceInfo().getEvidences().get(cofactors.get(1)).get(0));
        assertEquals("Isoform 1", ms.getMolecule());
        assertEquals(1, ms.getNote().size());
        assertEquals(
                "Binds 2 divalent ions per subunit (magnesium or cobalt).",
                ms.getNote().get(0).getValue());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00086", ms.getNote().get(0).getEvidences().get(0));
    }

    @Test
    void test2() {
        String lines =
                "CC   -!- COFACTOR: [Serine protease NS3]:\n"
                        + "CC       Name=Zn(2+); Xref=ChEBI:CHEBI:29105;\n"
                        + "CC         Evidence={ECO:0000269|PubMed:16683188,\n"
                        + "CC         ECO:0000269|PubMed:16683189};\n"
                        + "CC       Name=A very very looooooooooooong cofactor name with 1 evidence tag;\n"
                        + "CC         Xref=ChEBI:CHEBI:12345; Evidence={ECO:0000269|PubMed:16683188};\n"
                        + "CC       Name=A very very looooooooooooong cofactor name with X evidence tags;\n"
                        + "CC         Xref=ChEBI:CHEBI:54321;\n"
                        + "CC         Evidence={ECO:0000269|PubMed:16683188, ECO:0000269|PubMed:16683189};\n"
                        + "CC       Note=Binds 2 divalent ions per subunit.\n"
                        + "CC       {ECO:0000255|HAMAP-Rule:MF_00086};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof StructuredCofactor);
        StructuredCofactor ms = (StructuredCofactor) cc.getObject();
        List<CofactorItem> cofactors = ms.getCofactors();
        assertEquals(3, cofactors.size());
        assertEquals("Zn(2+)", cofactors.get(0).getName());
        assertEquals("ChEBI:CHEBI:29105", cofactors.get(0).getXref());
        assertEquals(
                "ECO:0000269|PubMed:16683188",
                obj.getEvidenceInfo().getEvidences().get(cofactors.get(0)).get(0));

        assertEquals(
                "A very very looooooooooooong cofactor name with 1 evidence tag",
                cofactors.get(1).getName());
        assertEquals("ChEBI:CHEBI:12345", cofactors.get(1).getXref());
        assertEquals(
                "ECO:0000269|PubMed:16683188",
                obj.getEvidenceInfo().getEvidences().get(cofactors.get(1)).get(0));

        assertEquals(
                "A very very looooooooooooong cofactor name with X evidence tags",
                cofactors.get(2).getName());
        assertEquals("ChEBI:CHEBI:54321", cofactors.get(2).getXref());
        assertEquals(
                "ECO:0000269|PubMed:16683189",
                obj.getEvidenceInfo().getEvidences().get(cofactors.get(2)).get(1));

        assertEquals("Serine protease NS3", ms.getMolecule());
        assertEquals(1, ms.getNote().size());
        assertEquals("Binds 2 divalent ions per subunit.", ms.getNote().get(0).getValue());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00086", ms.getNote().get(0).getEvidences().get(0));
    }

    @Test
    void testNoteOnly() {
        String lines =
                "CC   -!- COFACTOR:\n"
                        + "CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).\n"
                        + "CC       {ECO:0000255|HAMAP-Rule:MF_00086};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof StructuredCofactor);
        StructuredCofactor ms = (StructuredCofactor) cc.getObject();
        List<CofactorItem> cofactors = ms.getCofactors();
        assertEquals(0, cofactors.size());
        assertEquals(1, ms.getNote().size());
        assertEquals(
                "Binds 2 divalent ions per subunit (magnesium or cobalt).",
                ms.getNote().get(0).getValue());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00086", ms.getNote().get(0).getEvidences().get(0));
    }

    @Test
    void testNoteOnly2() {
        String lines =
                "CC   -!- COFACTOR:\n"
                        + "CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).\n"
                        + "CC       Binds 3 divalent ions per subunit (magnesium or cobalt).;\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof StructuredCofactor);
        StructuredCofactor ms = (StructuredCofactor) cc.getObject();
        List<CofactorItem> cofactors = ms.getCofactors();
        assertEquals(0, cofactors.size());
        assertEquals(1, ms.getNote().size());
        assertEquals(
                "Binds 2 divalent ions per subunit (magnesium or cobalt). Binds 3 divalent ions per subunit (magnesium or cobalt).",
                ms.getNote().get(0).getValue());
        assertEquals(0, ms.getNote().get(0).getEvidences().size());
    }

    @Test
    void testTwoNoteOnly2() {
        String lines =
                "CC   -!- COFACTOR:\n"
                        + "CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).;\n"
                        + "CC       Binds 3 divalent ions per subunit (magnesium or cobalt).;\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof StructuredCofactor);
        StructuredCofactor ms = (StructuredCofactor) cc.getObject();
        List<CofactorItem> cofactors = ms.getCofactors();
        assertEquals(0, cofactors.size());
        assertEquals(2, ms.getNote().size());
        assertEquals(
                "Binds 2 divalent ions per subunit (magnesium or cobalt).",
                ms.getNote().get(0).getValue());
        assertEquals(
                "Binds 3 divalent ions per subunit (magnesium or cobalt).",
                ms.getNote().get(1).getValue());
        assertEquals(0, ms.getNote().get(0).getEvidences().size());
    }

    @Test
    void testTwoNoteOnlyWithEvidence() {
        String lines =
                "CC   -!- COFACTOR:\n"
                        + "CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt). {ECO:0000255|HAMAP-Rule:MF_00086};\n"
                        + "CC       Binds 3 divalent ions per subunit (magnesium or cobalt).;\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof StructuredCofactor);
        StructuredCofactor ms = (StructuredCofactor) cc.getObject();
        List<CofactorItem> cofactors = ms.getCofactors();
        assertEquals(0, cofactors.size());
        assertEquals(2, ms.getNote().size());
        assertEquals(
                "Binds 2 divalent ions per subunit (magnesium or cobalt).",
                ms.getNote().get(0).getValue());
        assertEquals(
                "Binds 3 divalent ions per subunit (magnesium or cobalt).",
                ms.getNote().get(1).getValue());
        assertEquals(1, ms.getNote().get(0).getEvidences().size());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00086", ms.getNote().get(0).getEvidences().get(0));
    }

    @Test
    void testNoHeaderWithEvidence() {

        String ccLineStringEvidence =
                "COFACTOR: [Serine protease NS3]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000269|PubMed:16683188,"
                        + " ECO:0000269|PubMed:16683189};\n"
                        + "Name=A very looooooooooooong cofactor name with 1 evidence tag; "
                        + "Xref=ChEBI:CHEBI:12345; Evidence={ECO:0000269|PubMed:16683188};\n"
                        + "Name=A very very looooooooooooong cofactor name with X evidence tags; "
                        + "Xref=ChEBI:CHEBI:54321; Evidence={ECO:0000269|PubMed:16683188, ECO:0000269|PubMed:16683189};\n"
                        + "Note=Binds 2 divalent ions per subunit. {ECO:0000269|PubMed:16683188, "
                        + "ECO:0000255|HAMAP-Rule:MF_00086}. Another note. {ECO:0000269|PubMed:16683189};";
        CcLineFormater formater = new CcLineFormater();
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineStringEvidence);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeaderWithEvidence2() {

        String ccLineStringEvidence =
                "COFACTOR: [Serine protease NS3]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000269|PubMed:9060645};\n"
                        + "Note=Binds 1 zinc ion. {ECO:0000269|PubMed:9060645};";
        CcLineFormater formater = new CcLineFormater();
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineStringEvidence);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeaderWithEvidence3() {

        String ccLineStringEvidence =
                "COFACTOR: [Non-structural protein 5A]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000250};\n"
                        + "Note=Binds 1 zinc ion in the NS5A N-terminal domain. {ECO:0000250};";
        CcLineFormater formater = new CcLineFormater();
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineStringEvidence);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeaderWithEvidence4() {

        String ccLineStringEvidence =
                "COFACTOR:\n"
                        + "Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000255|HAMAP-Rule:MF_00086};\n"
                        + "Name=Co(2+); Xref=ChEBI:CHEBI:48828; Evidence={ECO:0000255|HAMAP-Rule:MF_00089, ECO:0000269|PubMed:16683189};\n"
                        + "Note=Binds 2 divalent ions per subunit (magnesium or cobalt). "
                        + "A second loosely associated metal ion is visible in the crystal structure. {ECO:0000255|HAMAP-Rule:MF_00082};";
        CcLineFormater formater = new CcLineFormater();
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineStringEvidence);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }
}
