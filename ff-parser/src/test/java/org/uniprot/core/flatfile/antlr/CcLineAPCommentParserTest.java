package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.AlternativeProductName;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.AlternativeProducts;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;

class CcLineAPCommentParserTest {
    @Test
    void testParser() {
        String lines =
                "CC   -!- ALTERNATIVE PRODUCTS:\n"
                        + "CC       Event=Alternative splicing; Named isoforms=3;\n"
                        + "CC         Comment=Additional isoforms seem to exist. Experimental\n"
                        + "CC         confirmation may be lacking for some isoforms.;\n"
                        + "CC       Name=1; Synonyms=AIRE-1;\n"
                        + "CC         IsoId=O43918-1; Sequence=Displayed;\n"
                        + "CC       Name=2; Synonyms=AIRE-2;\n"
                        + "CC         IsoId=O43918-2; Sequence=VSP_004089;\n"
                        + "CC       Name=3; Synonyms=AIRE-3;\n"
                        + "CC         IsoId=O43918-3; Sequence=VSP_004089, VSP_004090;\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof AlternativeProducts);
        AlternativeProducts ap = (AlternativeProducts) cc.getObject();
        assertEquals(1, ap.getEvents().size());
        assertEquals("Alternative splicing", ap.getEvents().get(0));
        assertEquals("3", ap.getNamedIsoforms());
        assertEquals(
                "Additional isoforms seem to exist. Experimental confirmation may be lacking for some isoforms.",
                ap.getComment().get(0).getValue());
        assertEquals(3, ap.getNames().size());
        assertEquals("1", ap.getNames().get(0).getName().getValue());
        assertEquals(1, ap.getNames().get(0).getIsoId().size());
        assertEquals("O43918-1", ap.getNames().get(0).getIsoId().get(0));
        assertEquals(
                AlternativeProductName.AlternativeNameSequenceEnum.DISPLAYED,
                ap.getNames().get(0).getSequenceEnum());
        assertTrue(ap.getNames().get(0).getSequenceFTId().isEmpty());
        assertEquals(1, ap.getNames().get(0).getSynNames().size());
        assertEquals("AIRE-1", ap.getNames().get(0).getSynNames().get(0).getValue());

        assertEquals("2", ap.getNames().get(1).getName().getValue());
        assertEquals(1, ap.getNames().get(1).getIsoId().size());
        assertEquals("O43918-2", ap.getNames().get(1).getIsoId().get(0));

        assertEquals(1, ap.getNames().get(1).getSequenceFTId().size());
        assertEquals("VSP_004089", ap.getNames().get(1).getSequenceFTId().get(0));
        assertEquals(1, ap.getNames().get(1).getSynNames().size());
        assertEquals("AIRE-2", ap.getNames().get(1).getSynNames().get(0).getValue());

        assertEquals("3", ap.getNames().get(2).getName().getValue());
        assertEquals(1, ap.getNames().get(2).getIsoId().size());
        assertEquals("O43918-3", ap.getNames().get(2).getIsoId().get(0));
        assertEquals(null, ap.getNames().get(2).getSequenceEnum());
        assertEquals(2, ap.getNames().get(2).getSequenceFTId().size());
        assertEquals("VSP_004089", ap.getNames().get(2).getSequenceFTId().get(0));
        assertEquals("VSP_004090", ap.getNames().get(2).getSequenceFTId().get(1));
        assertEquals(1, ap.getNames().get(2).getSynNames().size());
        assertEquals("AIRE-3", ap.getNames().get(2).getSynNames().get(0).getValue());
    }

    @Test
    void testParser2() {
        String lines =
                "CC   -!- ALTERNATIVE PRODUCTS:\n"
                        + "CC       Event=Alternative promoter usage; Named isoforms=2;\n"
                        + "CC       Name=alpha;\n"
                        + "CC         IsoId=P12544-1; Sequence=Displayed;\n"
                        + "CC       Name=beta;\n"
                        + "CC         IsoId=P12544-2; Sequence=VSP_038571, VSP_038572;\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof AlternativeProducts);
        AlternativeProducts ap = (AlternativeProducts) cc.getObject();
        assertEquals(1, ap.getEvents().size());
        assertEquals("Alternative promoter usage", ap.getEvents().get(0));
        assertEquals("2", ap.getNamedIsoforms());
        assertEquals(2, ap.getNames().size());
        assertEquals("alpha", ap.getNames().get(0).getName().getValue());
        assertEquals(1, ap.getNames().get(0).getIsoId().size());
        assertEquals("P12544-1", ap.getNames().get(0).getIsoId().get(0));
        assertEquals(
                AlternativeProductName.AlternativeNameSequenceEnum.DESCRIBED.DISPLAYED,
                ap.getNames().get(0).getSequenceEnum());
        assertTrue(ap.getNames().get(0).getSequenceFTId().isEmpty());
        assertEquals(0, ap.getNames().get(0).getSynNames().size());

        assertEquals("beta", ap.getNames().get(1).getName().getValue());
        assertEquals(1, ap.getNames().get(1).getIsoId().size());
        assertEquals("P12544-2", ap.getNames().get(1).getIsoId().get(0));

        assertEquals(2, ap.getNames().get(1).getSequenceFTId().size());
        assertEquals("VSP_038571", ap.getNames().get(1).getSequenceFTId().get(0));
        assertEquals("VSP_038572", ap.getNames().get(1).getSequenceFTId().get(1));
        assertEquals(0, ap.getNames().get(1).getSynNames().size());
    }

    @Test
    void testParserSequenceValue() {
        String lines =
                "CC   -!- ALTERNATIVE PRODUCTS:\n"
                        + "CC       Event=Alternative splicing; Named isoforms=6;\n"
                        + "CC       Name=1; Synonyms=A;\n"
                        + "CC         IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "CC       Name=2;\n"
                        + "CC         IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
                        + "CC                                  VSP_000480, VSP_000481;\n"
                        + "CC       Name=3; Synonyms=C;\n"
                        + "CC         IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "CC       Name=4; Synonyms=B;\n"
                        + "CC         IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "CC       Name=5;\n"
                        + "CC         IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "CC         Note=No experimental confirmation available.;\n"
                        + "CC       Name=6; Synonyms=D;\n"
                        + "CC         IsoId=Q9V8R9-6; Sequence=VSP_000478;\n"
                        + "CC         Note=No experimental confirmation available.;\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof AlternativeProducts);
        AlternativeProducts ap = (AlternativeProducts) cc.getObject();
        assertEquals(6, ap.getNames().size());
        assertEquals(5, ap.getNames().get(1).getSequenceFTId().size());
    }

    @Test
    void testParserSynonyms() {
        String lines =
                "CC   -!- ALTERNATIVE PRODUCTS:\n"
                        + "CC       Event=Alternative splicing; Named isoforms=20;\n"
                        + "CC       Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10,\n"
                        + "CC       BimAD, Bim-AD;\n"
                        + "CC         IsoId=O43521-6; Sequence=VSP_035608, VSP_035620;\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof AlternativeProducts);
        AlternativeProducts ap = (AlternativeProducts) cc.getObject();
        assertEquals(1, ap.getNames().size());
        assertEquals(3, ap.getNames().get(0).getSynNames().size());
        assertEquals(
                "BCL2-like 11 transcript variant 10",
                ap.getNames().get(0).getSynNames().get(0).getValue());
        assertEquals("BimAD", ap.getNames().get(0).getSynNames().get(1).getValue());
        assertEquals("Bim-AD", ap.getNames().get(0).getSynNames().get(2).getValue());
    }

    @Test
    void testParserSynonyms2() {
        String lines =
                "CC   -!- ALTERNATIVE PRODUCTS:\n"
                        + "CC       Event=Alternative splicing; Named isoforms=15;\n"
                        + "CC       Name=1; Synonyms=FLIP-L, CLARP1, MRIT alpha-1, CASH alpha, I-FLICE\n"
                        + "CC       1, FLAME-1 gamma, Usurpin alpha;\n"
                        + "CC         IsoId=O15519-1; Sequence=Displayed;\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof AlternativeProducts);
        AlternativeProducts ap = (AlternativeProducts) cc.getObject();
        assertEquals(1, ap.getNames().size());
        assertEquals(7, ap.getNames().get(0).getSynNames().size());
        assertEquals("I-FLICE 1", ap.getNames().get(0).getSynNames().get(4).getValue());
    }

    @Test
    void testParserWithEvidences() {
        String lines =
                "CC   -!- ALTERNATIVE PRODUCTS:\n"
                        + "CC       Event=Alternative splicing; Named isoforms=6;\n"
                        + "CC       Name=1 {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "CC         IsoId=Q9NQ94-1; Sequence=Displayed;\n"
                        + "CC       Name=2 {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "CC         IsoId=Q9NQ94-2; Sequence=VSP_051929;\n"
                        + "CC         Note=Major isoform found in 66-78% of cDNA clones.;\n"
                        + "CC       Name=3;\n"
                        + "CC         IsoId=Q9NQ94-3; Sequence=VSP_051926, VSP_051929;\n"
                        + "CC       Name=4;\n"
                        + "CC         IsoId=Q9NQ94-4; Sequence=VSP_051927, VSP_051929;\n"
                        + "CC         Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
                        + "CC         sequence is in conflict in positions: 33:I->T. No experimental\n"
                        + "CC         confirmation available.;\n"
                        + "CC       Name=5 {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "CC         IsoId=Q9NQ94-5; Sequence=VSP_051925;\n"
                        + "CC         Note=Does not exhibit APOBEC1 complementation activity.;\n"
                        + "CC       Name=6 {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "CC         IsoId=Q9NQ94-6; Sequence=VSP_051928;\n"
                        + "CC         Note=Minor isoform found in 2-3% of cDNA clones. {ECO:0000313|EMBL:BAG16761.1};\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof AlternativeProducts);
        AlternativeProducts ap = (AlternativeProducts) cc.getObject();
        assertEquals(6, ap.getNames().size());
        assertEquals("2", ap.getNames().get(1).getName().getValue());
        assertNotNull(ap.getNames().get(1).getName().getEvidences());
        assertEquals(1, ap.getNames().get(1).getName().getEvidences().size());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                ap.getNames().get(1).getName().getEvidences().get(0));
    }

    @Test
    void testParserWithEvidences2() {
        String lines =
                "CC   -!- ALTERNATIVE PRODUCTS:\n"
                        + "CC       Event=Alternative splicing; Named isoforms=6;\n"
                        + "CC         Comment=Additional isoforms seem to exist.\n"
                        + "CC         {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                        + "CC       Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_002051, ECO:0000313|PDB:3OW2};\n"
                        + "CC         IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "CC         Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
                        + "CC         sequence is in conflict in positions: 33:I->T. No experimental\n"
                        + "CC         confirmation available. {ECO:0000313|PDB:3OW2};\n"
                        + "CC       Name=2;\n"
                        + "CC         IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
                        + "CC                                  VSP_000480, VSP_000481;\n"
                        + "CC       Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript variant 10\n"
                        + "CC       {ECO:0000313|EMBL:BAG16761.1}, Bim-AD\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n"
                        + "CC         IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "CC       Name=4; Synonyms=B;\n"
                        + "CC         IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "CC       Name=5;\n"
                        + "CC         IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "CC         Note=No experimental confirmation available.\n"
                        + "CC         {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
                        + "CC       Name=6; Synonyms=D;\n"
                        + "CC         IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "CC         Note=No experimental confirmation.;\n";
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof AlternativeProducts);
        AlternativeProducts ap = (AlternativeProducts) cc.getObject();
        assertEquals(1, ap.getEvents().size());
        assertEquals("Alternative splicing", ap.getEvents().get(0));
        assertEquals(1, ap.getComment().size());
        assertEquals("Additional isoforms seem to exist.", ap.getComment().get(0).getValue());
        assertEquals("ECO:0000269|PubMed:10433554", ap.getComment().get(0).getEvidences().get(0));
        assertEquals("ECO:0000303|Ref.6", ap.getComment().get(0).getEvidences().get(1));
        assertEquals(6, ap.getNames().size());
        assertEquals("1", ap.getNames().get(0).getName().getValue());
        assertNotNull(ap.getNames().get(0).getName().getEvidences());
        assertEquals(1, ap.getNames().get(0).getName().getEvidences().size());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                ap.getNames().get(0).getName().getEvidences().get(0));
        assertEquals(1, ap.getNames().get(0).getSynNames().size());
        assertEquals("A", ap.getNames().get(0).getSynNames().get(0).getValue());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_002051",
                ap.getNames().get(0).getSynNames().get(0).getEvidences().get(0));
        assertEquals(
                "ECO:0000313|PDB:3OW2",
                ap.getNames().get(0).getSynNames().get(0).getEvidences().get(1));
        assertEquals(
                "Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T. No experimental confirmation available.",
                ap.getNames().get(0).getNote().get(0).getValue());
        assertEquals(
                "ECO:0000313|PDB:3OW2",
                ap.getNames().get(0).getNote().get(0).getEvidences().get(0));

        assertEquals("Bim-alpha3", ap.getNames().get(2).getName().getValue());
        assertNotNull(ap.getNames().get(2).getName().getEvidences());
        assertEquals(2, ap.getNames().get(2).getName().getEvidences().size());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                ap.getNames().get(2).getName().getEvidences().get(0));
        assertEquals("ECO:0000313|PDB:3OW2", ap.getNames().get(2).getName().getEvidences().get(1));
        assertEquals(3, ap.getNames().get(2).getSynNames().size());
        assertEquals(
                "BCL2-like 11 transcript variant 10",
                ap.getNames().get(2).getSynNames().get(0).getValue());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                ap.getNames().get(2).getSynNames().get(0).getEvidences().get(0));

        assertEquals("Bim-AD", ap.getNames().get(2).getSynNames().get(1).getValue());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                ap.getNames().get(2).getSynNames().get(1).getEvidences().get(0));

        assertEquals("BimAD", ap.getNames().get(2).getSynNames().get(2).getValue());
        assertEquals(
                "ECO:0000313|PDB:3OW2",
                ap.getNames().get(2).getSynNames().get(2).getEvidences().get(0));

        assertEquals(
                "No experimental confirmation available.",
                ap.getNames().get(4).getNote().get(0).getValue());
        assertEquals(
                "ECO:0000269|PubMed:10433554",
                ap.getNames().get(4).getNote().get(0).getEvidences().get(0));
    }

    @Test
    void testNoHeaderWithEvidence() {
        String ccLineStringEvidence =
                "ALTERNATIVE PRODUCTS:\n"
                        + "Event=Alternative splicing; Named isoforms=6;\n"
                        + "Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};"
                        + " Another additional isoforms also seem to exist. {ECO:0000269|PubMed:10433554};\n"
                        + "Name=1 {ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}; Synonyms=A {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                        + "  IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "  Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T."
                        + " No experimental confirmation available. {ECO:0000313|PDB:3OW2};\n"
                        + "Name=2;\n"
                        + "  IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n"
                        + "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript"
                        + " variant 10 {ECO:0000313|EMBL:BAG16761.1}, Bim-AD, BimAD {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                        + "  IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "Name=4; Synonyms=B;\n"
                        + "  IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "Name=5;\n"
                        + "  IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "  Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};"
                        + " Another no experimental confirmation also available. {ECO:0000269|PubMed:1043355,"
                        + " ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Name=6; Synonyms=D;\n"
                        + "  IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "  Note=No experimental confirmation.;";
        CcLineFormater formater = new CcLineFormater();
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineStringEvidence);
        System.out.println(lines);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeader() {
        String ccLineString =
                "ALTERNATIVE PRODUCTS:\n"
                        + "Event=Alternative splicing; Named isoforms=6;\n"
                        + "Comment=Additional isoforms seem to exist.;\n"
                        + "Name=1; Synonyms=A;\n"
                        + "IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is"
                        + " in conflict in positions: 33:I->T. No experimental confirmation available.;\n"
                        + "Name=2;\n"
                        + "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n"
                        + "Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10, Bim-AD, BimAD;\n"
                        + "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "Name=4; Synonyms=B;\n"
                        + "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "Name=5;\n"
                        + "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "Note=No experimental confirmation available.;\n"
                        + "Name=6; Synonyms=D;\n"
                        + "IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "Note=No experimental confirmation available.;";
        CcLineFormater formater = new CcLineFormater();
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeaderWithEvidence2() {
        String ccLineStringEvidence =
                "ALTERNATIVE PRODUCTS:\n"
                        + "Event=Alternative splicing; Named isoforms=6;\n"
                        + "Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};"
                        + " Another additional isoforms also seem to exist. {ECO:0000269|PubMed:10433554};\n"
                        + "Name=1 {ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}; Synonyms=A {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                        + "IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T."
                        + " No experimental confirmation available. {ECO:0000313|PDB:3OW2};\n"
                        + "Name=2;\n"
                        + "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n"
                        + "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript"
                        + " variant 10 {ECO:0000313|EMBL:BAG16761.1}, Bim-AD, BimAD {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                        + "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "Name=4; Synonyms=B;\n"
                        + "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "Name=5;\n"
                        + "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};"
                        + " Another no experimental confirmation also available. {ECO:0000269|PubMed:1043355, ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Name=6; Synonyms=D;\n"
                        + "IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "Note=No experimental confirmation.;";
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
                "ALTERNATIVE PRODUCTS:\n"
                        + "Event=Alternative splicing; Named isoforms=6;\n"
                        + "Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};"
                        + " Another additional isoforms also seem to exist. {ECO:0000269|PubMed:10433554};\n"
                        + "Name=1 {ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}; Synonyms=A {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                        + "IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T."
                        + " No experimental confirmation available. {ECO:0000313|PDB:3OW2};\n"
                        + "Name=2;\n"
                        + "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n"
                        + "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript"
                        + " variant 10 {ECO:0000313|EMBL:BAG16761.1}, Bim-AD, BimAD {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                        + "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "Name=4; Synonyms=B;\n"
                        + "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477,\n"
                        + "VSP_000479;\n"
                        + "Name=5;\n"
                        + "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};"
                        + " Another no experimental confirmation also available. {ECO:0000269|PubMed:1043355, ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Name=6; Synonyms=D;\n"
                        + "IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "Note=No experimental confirmation.;";
        CcLineFormater formater = new CcLineFormater();
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineStringEvidence);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeaderWithEvidence5() {
        String ccLineStringEvidence =
                "ALTERNATIVE PRODUCTS:\n"
                        + "Event=Alternative splicing; Named isoforms=6;\n"
                        + " Comment=Additional isoforms seem to exist.\n"
                        + "{ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                        + "Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A\n"
                        + "{ECO:0000256|HAMAP-Rule:MF_002051, ECO:0000313|PDB:3OW2};\n"
                        + "IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
                        + "sequence is in conflict in positions: 33:I->T. No experimental\n"
                        + "confirmation available. {ECO:0000313|PDB:3OW2};\n"
                        + "Name=2;\n"
                        + " IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
                        + "VSP_000480, VSP_000481;\n"
                        + "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript variant 10\n"
                        + "{ECO:0000313|EMBL:BAG16761.1}, Bim-AD\n"
                        + "{ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n"
                        + "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "Name=4; Synonyms=B;\n"
                        + "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "Name=5;\n"
                        + "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "Note=No experimental confirmation available.\n"
                        + "{ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Name=6; Synonyms=D;\n"
                        + "IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "Note=No experimental confirmation.;\n";
        CcLineFormater formater = new CcLineFormater();
        UniprotkbLineParser<CcLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineStringEvidence);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof AlternativeProducts);
        AlternativeProducts ap = (AlternativeProducts) cc.getObject();
        assertEquals(1, ap.getEvents().size());
        assertEquals("Alternative splicing", ap.getEvents().get(0));
        assertEquals(1, ap.getComment().size());
        assertEquals("Additional isoforms seem to exist.", ap.getComment().get(0).getValue());
        assertEquals("ECO:0000269|PubMed:10433554", ap.getComment().get(0).getEvidences().get(0));
        assertEquals("ECO:0000303|Ref.6", ap.getComment().get(0).getEvidences().get(1));
        assertEquals(6, ap.getNames().size());
        assertEquals("1", ap.getNames().get(0).getName().getValue());
        assertNotNull(ap.getNames().get(0).getName().getEvidences());
        assertEquals(1, ap.getNames().get(0).getName().getEvidences().size());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                ap.getNames().get(0).getName().getEvidences().get(0));
        assertEquals(1, ap.getNames().get(0).getSynNames().size());
        assertEquals("A", ap.getNames().get(0).getSynNames().get(0).getValue());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_002051",
                ap.getNames().get(0).getSynNames().get(0).getEvidences().get(0));
        assertEquals(
                "ECO:0000313|PDB:3OW2",
                ap.getNames().get(0).getSynNames().get(0).getEvidences().get(1));
        assertEquals(
                "Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T. No experimental confirmation available.",
                ap.getNames().get(0).getNote().get(0).getValue());
        assertEquals(
                "ECO:0000313|PDB:3OW2",
                ap.getNames().get(0).getNote().get(0).getEvidences().get(0));

        assertEquals("Bim-alpha3", ap.getNames().get(2).getName().getValue());
        assertNotNull(ap.getNames().get(2).getName().getEvidences());
        assertEquals(2, ap.getNames().get(2).getName().getEvidences().size());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                ap.getNames().get(2).getName().getEvidences().get(0));
        assertEquals("ECO:0000313|PDB:3OW2", ap.getNames().get(2).getName().getEvidences().get(1));
        assertEquals(3, ap.getNames().get(2).getSynNames().size());
        assertEquals(
                "BCL2-like 11 transcript variant 10",
                ap.getNames().get(2).getSynNames().get(0).getValue());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                ap.getNames().get(2).getSynNames().get(0).getEvidences().get(0));

        assertEquals("Bim-AD", ap.getNames().get(2).getSynNames().get(1).getValue());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                ap.getNames().get(2).getSynNames().get(1).getEvidences().get(0));

        assertEquals("BimAD", ap.getNames().get(2).getSynNames().get(2).getValue());
        assertEquals(
                "ECO:0000313|PDB:3OW2",
                ap.getNames().get(2).getSynNames().get(2).getEvidences().get(0));

        assertEquals(
                "No experimental confirmation available.",
                ap.getNames().get(4).getNote().get(0).getValue());
        assertEquals(
                "ECO:0000269|PubMed:10433554",
                ap.getNames().get(4).getNote().get(0).getEvidences().get(0));
    }
}
