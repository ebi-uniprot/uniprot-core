package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.*;

class CcLineMSCommentParserTest {
    @Test
    void testMaldiWithMassAndError() {
        String lines =
                "CC   -!- MASS SPECTROMETRY: Mass=24948; Mass_error=6; Method=MALDI;\n"
                        + "CC       Evidence={ECO:0000006|PubMed:16629414};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof MassSpectrometry);
        MassSpectrometry ms = (MassSpectrometry) cc.getObject();

        verify(ms, 24948, 6, "MALDI");
        assertEquals(1, ms.getSources().size());
        assertEquals("ECO:0000006|PubMed:16629414", ms.getSources().get(0));
        assertEquals(
                "ECO:0000006|PubMed:16629414", obj.getEvidenceInfo().getEvidences().get(ms).get(0));
    }

    private void verify(MassSpectrometry ms, float mass, float mass_error, String method) {
        assertEquals(mass, ms.getMass(), 0.000001f);
        assertEquals(mass_error, ms.getMassError(), 0.000001f);
        assertEquals(method, ms.getMethod());

        ;
    }

    @Test
    void testRangeWithIsoform() {
        String lines =
                "CC   -!- MASS SPECTROMETRY: [P15522-2]: Mass=13822; Method=MALDI; Range=19-140;\n"
                        + "CC       Evidence={ECO:0000006|PubMed:16629414};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof MassSpectrometry);
        MassSpectrometry ms = (MassSpectrometry) cc.getObject();

        verify(ms, 13822, 0, "MALDI");
        assertEquals("P15522-2", ms.getMolecule());
        assertEquals(1, ms.getSources().size());

        assertEquals("ECO:0000006|PubMed:16629414", ms.getSources().get(0));
        assertEquals(
                "ECO:0000006|PubMed:16629414", obj.getEvidenceInfo().getEvidences().get(ms).get(0));
    }

    @Test
    void testMultiRange() {
        String lines =
                "CC   -!- MASS SPECTROMETRY: Mass=514.2; Method=Electrospray;\n"
                        + "CC       Note=The measured mass is that of\n"
                        + "CC       RPGW-amide.; Evidence={ECO:0000006|PubMed:16629414};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof MassSpectrometry);
        MassSpectrometry ms = (MassSpectrometry) cc.getObject();

        verify(ms, 514.2f, 0, "Electrospray");
        assertEquals("The measured mass is that of RPGW-amide.", ms.getNote());
        assertEquals(1, ms.getSources().size());

        assertEquals("ECO:0000006|PubMed:16629414", ms.getSources().get(0));
        assertEquals(
                "ECO:0000006|PubMed:16629414", obj.getEvidenceInfo().getEvidences().get(ms).get(0));
    }

    @Test
    void testRangeWithUnknown() {
        String lines =
                "CC   -!- MASS SPECTROMETRY: Mass=9571; Method=Electrospray;\n"
                        + "CC       Evidence={ECO:0000006|PubMed:16629414};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof MassSpectrometry);
        MassSpectrometry ms = (MassSpectrometry) cc.getObject();

        verify(ms, 9571, 0, "Electrospray");
        assertEquals(1, ms.getSources().size());

        assertEquals("ECO:0000006|PubMed:16629414", ms.getSources().get(0));
        assertEquals(
                "ECO:0000006|PubMed:16629414", obj.getEvidenceInfo().getEvidences().get(ms).get(0));
    }

    @Test
    void testWithNoteNoError() {
        String lines =
                "CC   -!- MASS SPECTROMETRY: Mass=7190; Method=MALDI;\n"
                        + "CC       Note=Variant 6.01; Evidence={ECO:0000006|PubMed:16629414};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof MassSpectrometry);
        MassSpectrometry ms = (MassSpectrometry) cc.getObject();

        verify(ms, 7190, 0, "MALDI");
        //	assertTrue(ms.ranges.get(0).end_unknown);
        assertEquals("Variant 6.01", ms.getNote());
        assertEquals(1, ms.getSources().size());

        assertEquals("ECO:0000006|PubMed:16629414", ms.getSources().get(0));
        assertEquals(
                "ECO:0000006|PubMed:16629414", obj.getEvidenceInfo().getEvidences().get(ms).get(0));
    }

    @Test
    void test6() {
        String lines =
                "CC   -!- MASS SPECTROMETRY: Mass=1200.8; Mass_error=2.0E-4; Method=MALDI;\n"
                        + "CC       Evidence={ECO:0000006|PubMed:16629414};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof MassSpectrometry);
        MassSpectrometry ms = (MassSpectrometry) cc.getObject();

        verify(ms, 1200.8f, 2.0E-4f, "MALDI");
        //	assertTrue(ms.ranges.get(0).end_unknown);
        //	assertEquals("Variant 6.01", ms.note);
        assertEquals(1, ms.getSources().size());

        assertEquals("ECO:0000006|PubMed:16629414", ms.getSources().get(0));
        assertEquals(
                "ECO:0000006|PubMed:16629414", obj.getEvidenceInfo().getEvidences().get(ms).get(0));
    }

    @Test
    void testMultiEvidences() {
        String lines =
                "CC   -!- MASS SPECTROMETRY: Mass=3979.9; Method=Electrospray;\n"
                        + "CC       Evidence={ECO:0000006|PubMed:16629414, ECO:0000006|PubMed:16629415};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof MassSpectrometry);
        MassSpectrometry ms = (MassSpectrometry) cc.getObject();

        verify(ms, 3979.9f, 0, "Electrospray");
        //	assertTrue(ms.ranges.get(0).end_unknown);
        //	assertEquals("Variant 6.01", ms.note);
        assertEquals(2, ms.getSources().size());

        assertEquals("ECO:0000006|PubMed:16629414", ms.getSources().get(0));
        assertEquals("ECO:0000006|PubMed:16629415", ms.getSources().get(1));
        assertEquals(
                "ECO:0000006|PubMed:16629414", obj.getEvidenceInfo().getEvidences().get(ms).get(0));
        assertEquals(
                "ECO:0000006|PubMed:16629415", obj.getEvidenceInfo().getEvidences().get(ms).get(1));
    }

    @Test
    void testNoHeader() {
        String ccLineString =
                "MASS SPECTROMETRY: Mass=3979.9; Method=Electrospray;\n"
                        + "Evidence={ECO:0000006|PubMed:16629414, ECO:0000006|PubMed:16629415};\n";
        CcLineFormater formater = new CcLineFormater();
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof MassSpectrometry);
        MassSpectrometry ms = (MassSpectrometry) cc.getObject();

        verify(ms, 3979.9f, 0, "Electrospray");
        //	assertTrue(ms.ranges.get(0).end_unknown);
        //	assertEquals("Variant 6.01", ms.note);
        assertEquals(2, ms.getSources().size());

        assertEquals("ECO:0000006|PubMed:16629414", ms.getSources().get(0));
        assertEquals("ECO:0000006|PubMed:16629415", ms.getSources().get(1));
        assertEquals(
                "ECO:0000006|PubMed:16629414", obj.getEvidenceInfo().getEvidences().get(ms).get(0));
        assertEquals(
                "ECO:0000006|PubMed:16629415", obj.getEvidenceInfo().getEvidences().get(ms).get(1));
    }
}
