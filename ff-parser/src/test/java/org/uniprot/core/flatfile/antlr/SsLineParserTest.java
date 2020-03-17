package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineObject;

class SsLineParserTest {
    @Test
    void test() {
        String ssLines =
                "**\n"
                        + "**   #################    INTERNAL SECTION    ##################\n"
                        + "**EV EI2; ProtImp; -; -; 11-SEP-2009.\n"
                        + "**EV EI3; EMBL; -; ACT41999.1; 22-JUN-2010.\n"
                        + "**EV EI4; EMBL; -; CAQ30614.1; 18-DEC-2010.\n"
                        + "**DG dg-000-000-614_P;\n"
                        + "**ZB YOK, 19-NOV-2002;\n";
        UniprotKBLineParser<SsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createSsLineParser();
        SsLineObject obj = parser.parse(ssLines);
        assertEquals(3, obj.getSsEVLines().size());
        assertEquals(2, obj.getSsIALines().size());
        verify(obj.getSsEVLines().get(0), "EI2", "ProtImp", "-", "-");
        verify(obj.getSsEVLines().get(1), "EI3", "EMBL", "-", "ACT41999.1");
        verify(obj.getSsEVLines().get(2), "EI4", "EMBL", "-", "CAQ30614.1");
        verify(obj.getSsIALines().get(0), "DG", "dg-000-000-614_P;");
        verify(obj.getSsIALines().get(1), "ZB", "YOK, 19-NOV-2002;");
    }

    private void verify(SsLineObject.SsLine obj, String topic, String text) {
        assertEquals(text, obj.getText());
        assertEquals(topic, obj.getTopic());
    }

    private void verify(SsLineObject.EvLine obj, String id, String db, String attr1, String attr2) {
        assertEquals(db, obj.getDb());
        assertEquals(id, obj.getId());
        assertEquals(attr1, obj.getAttr1());
        assertEquals(attr2, obj.getAttr2());
    }

    @Test
    void test2() {
        String ssLines =
                "**\n"
                        + "**   #################    INTERNAL SECTION    ##################\n"
                        + "**EV ECO:0000313; ProtImp:-; -; 07-NOV-2006.\n"
                        + "**EV ECO:0000256; HAMAP-Rule:MF_01417; -; 01-OCT-2010.\n"
                        + "**EV ECO:0000256; SAAS:SAAS022644_004_000329; -; 11-FEB-2014.\n"
                        + "**DG dg-000-000-614_P;\n"
                        + "**ZD YOK, 19-NOV-2004;\n";
        UniprotKBLineParser<SsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createSsLineParser();
        SsLineObject obj = parser.parse(ssLines);
        assertEquals(3, obj.getSsEVLines().size());
        assertEquals(2, obj.getSsIALines().size());
        verify(obj.getSsEVLines().get(0), "ECO:0000313", "ProtImp", "-", "-");
        verify(obj.getSsEVLines().get(1), "ECO:0000256", "HAMAP-Rule", "MF_01417", "-");
        verify(obj.getSsEVLines().get(2), "ECO:0000256", "SAAS", "SAAS022644_004_000329", "-");
        verify(obj.getSsIALines().get(0), "DG", "dg-000-000-614_P;");
        verify(obj.getSsIALines().get(1), "ZD", "YOK, 19-NOV-2004;");
    }

    @Test
    void test3() {
        String ssLines =
                "**\n"
                        + "**   #################    INTERNAL SECTION    ##################\n"
                        + "**EV ECO:0000313; ProtImp; -; 07-NOV-2006.\n";
        UniprotKBLineParser<SsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createSsLineParser();
        SsLineObject obj = parser.parse(ssLines);
        assertEquals(1, obj.getSsEVLines().size());
        assertEquals(0, obj.getSsIALines().size());
        verify(obj.getSsEVLines().get(0), "ECO:0000313", "ProtImp", "", "-");
    }

    @Test
    void test4() {
        String ssLines =
                "**\n"
                        + "**   #################    INTERNAL SECTION    ##################\n"
                        + "**EV ECO:0000269; PubMed:123; XXX; 13-NOV-1978.\n"
                        + "**EV ECO:0000269; Ref.1; XXX; 13-NOV-1978.\n"
                        + "**EV ECO:0000303; PubMed:x; XXX; 13-NOV-1978.\n"
                        + "**EV ECO:0000303; Reference:x; XXX; 13-NOV-1978.\n"
                        + "**EV ECO:0000312; DatabaseName:DatabaseId; XXX; 13-NOV-1978.\n"
                        + "**EV ECO:0000305; -; XXX; 13-NOV-1978.\n";
        UniprotKBLineParser<SsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createSsLineParser();
        SsLineObject obj = parser.parse(ssLines);
        assertEquals(6, obj.getSsEVLines().size());
        assertEquals(0, obj.getSsIALines().size());
        verify(obj.getSsEVLines().get(0), "ECO:0000269", "PubMed", "123", "XXX");
        verify(obj.getSsEVLines().get(1), "ECO:0000269", "Ref.1", "", "XXX");
        verify(obj.getSsEVLines().get(2), "ECO:0000303", "PubMed", "x", "XXX");
        verify(obj.getSsEVLines().get(3), "ECO:0000303", "Reference", "x", "XXX");
        verify(obj.getSsEVLines().get(4), "ECO:0000312", "DatabaseName", "DatabaseId", "XXX");
        verify(obj.getSsEVLines().get(5), "ECO:0000305", "-", "", "XXX");
    }

    @Test
    void testSources() {
        String ssLines =
                "**\n"
                        + "**   #################     SOURCE SECTION     ##################\n"
                        + "**   LOCUS       999163         29 aa\n"
                        + "**               26-SEP-1995\n";
        UniprotKBLineParser<SsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createSsLineParser();
        SsLineObject obj = parser.parse(ssLines);
        assertEquals(0, obj.getSsEVLines().size());
        assertEquals(0, obj.getSsIALines().size());
        assertEquals(2, obj.getSsSourceLines().size());
        assertEquals("LOCUS       999163         29 aa", obj.getSsSourceLines().get(0));
        assertEquals("            26-SEP-1995", obj.getSsSourceLines().get(1));
        //		verify(obj.ssEVLines.get(0),"ECO:0000269", "PubMed", "123", "XXX" );
        //		verify(obj.ssEVLines.get(1),"ECO:0000269", "Ref.1", "", "XXX" );
        //		verify(obj.ssEVLines.get(2),"ECO:0000303", "PubMed", "x", "XXX" );
        //		verify(obj.ssEVLines.get(3),"ECO:0000303", "Reference", "x", "XXX" );
        //		verify(obj.ssEVLines.get(4),"ECO:0000312", "DatabaseName", "DatabaseId", "XXX" );
        //		verify(obj.ssEVLines.get(5),"ECO:0000305", "-", "", "XXX" );
    }

    @Test
    void testSourcesInternalSection() {
        String ssLines =
                "**\n"
                        + "**   #################     SOURCE SECTION     ##################\n"
                        + "**   LOCUS       999163         29 aa\n"
                        + "**               26-SEP-1995\n"
                        + "**   #################    INTERNAL SECTION    ##################\n"
                        + "**EV ECO:0000256; HAMAP-Rule:MF_01417; -; 01-OCT-2010.\n"
                        + "**DG dg-000-000-614_P;\n"
                        + "**ZB YOK, 19-NOV-2002;\n";
        UniprotKBLineParser<SsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createSsLineParser();
        SsLineObject obj = parser.parse(ssLines);
        assertEquals(1, obj.getSsEVLines().size());
        assertEquals(2, obj.getSsIALines().size());
        assertEquals(2, obj.getSsSourceLines().size());
        assertEquals("LOCUS       999163         29 aa", obj.getSsSourceLines().get(0));
        assertEquals("            26-SEP-1995", obj.getSsSourceLines().get(1));
    }
}
