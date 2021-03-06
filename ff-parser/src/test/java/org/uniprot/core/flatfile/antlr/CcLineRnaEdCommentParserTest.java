package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.RnaEditing;

class CcLineRnaEdCommentParserTest {
    @Test
    void test1() {
        String lines =
                "CC   -!- RNA EDITING: Modified_positions=59, 78, 94, 98, 102, 121; Note=The\n"
                        + "CC       nonsense codon at position 59 is modified to a sense codon. The\n"
                        + "CC       stop codon at position 121 is created by RNA editing.;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof RnaEditing);
        RnaEditing re = (RnaEditing) cc.getObject();
        assertEquals(6, re.getLocations().size());
        assertEquals(
                "The nonsense codon at position 59 is modified to a sense codon. "
                        + "The stop codon at position 121 is created by RNA editing.",
                re.getNote().get(0).getValue());
    }

    @Test
    void test2() {
        String lines = "CC   -!- RNA EDITING: Modified_positions=11, 62, 72, 97, 117;\n";

        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof RnaEditing);
        RnaEditing re = (RnaEditing) cc.getObject();
        assertEquals(5, re.getLocations().size());
        assertEquals(11, re.getLocations().get(0).intValue());
        assertEquals(62, re.getLocations().get(1).intValue());
    }

    @Test
    void test3() {
        String lines =
                "CC   -!- RNA EDITING: Modified_positions=1, 56, 89, 103, 126, 164, 165,\n"
                        + "CC       167, 179, 191, 194, 212, 225, 242, 248, 252, 275, 300, 310, 313;\n"
                        + "CC       Note=The initiator methionine is created by RNA editing.;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof RnaEditing);
        RnaEditing re = (RnaEditing) cc.getObject();
        assertEquals(20, re.getLocations().size());
        assertEquals(1, re.getLocations().get(0).intValue());
        assertEquals(313, re.getLocations().get(19).intValue());
        assertEquals(
                "The initiator methionine is created by RNA editing.",
                re.getNote().get(0).getValue());
    }

    @Test
    void test4() {
        String lines =
                "CC   -!- RNA EDITING: Modified_positions=Undetermined; Note=Partially\n"
                        + "CC       edited. 11 sites are edited by Adar.;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof RnaEditing);
        RnaEditing re = (RnaEditing) cc.getObject();
        assertEquals(0, re.getLocations().size());
        assertEquals(
                RnaEditing.RnaEditingLocationEnum.NOT_APPLICABLE.UNDETERMINED,
                re.getLocationEnum());
        assertEquals(
                "Partially edited. 11 sites are edited by Adar.", re.getNote().get(0).getValue());
    }

    @Test
    void testWithEvidence() {
        String lines =
                "CC   -!- RNA EDITING: Modified_positions=59 {ECO:0000313|EMBL:BAG16761.1}, 78,"
                        + " 94, 98, 102, 121; Note=The\n"
                        + "CC       nonsense codon at position 59 is modified to a sense codon. The\n"
                        + "CC       stop codon at position 121 is created by RNA editing."
                        + " {ECO:0000313|PDB:3OW2,\n"
                        + "CC       ECO:0000256|HAMAP-Rule:MF_00205};\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof RnaEditing);
        RnaEditing re = (RnaEditing) cc.getObject();
        assertEquals(6, re.getLocations().size());
        assertEquals(59, re.getLocations().get(0).intValue());
        assertEquals(
                "The nonsense codon at position 59 is modified to a sense codon. "
                        + "The stop codon at position 121 is created by RNA editing.",
                re.getNote().get(0).getValue());
        assertEquals("ECO:0000313|PDB:3OW2", re.getNote().get(0).getEvidences().get(0));
        assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", re.getNote().get(0).getEvidences().get(1));
    }

    @Test
    void testBigEditWithEvidence() {
        String lines =
                "CC   -!- RNA EDITING: Modified_positions=156 {ECO:0000313|EMBL:BAG16761.1},\n"
                        + "CC       158 {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160\n"
                        + "CC       {ECO:0000303|Ref.6}; Note=Partially edited. RNA editing"
                        + " generates\n"
                        + "CC       receptor isoforms that differ in their ability to interact with\n"
                        + "CC       the phospholipase C signaling cascade in a transfected cell"
                        + " line,\n"
                        + "CC       suggesting that this RNA processing event may contribute to the\n"
                        + "CC       modulation of serotonergic neurotransmission in the central\n"
                        + "CC       nervous system. {ECO:0000313|PDB:3OW2,\n"
                        + "CC       ECO:0000256|HAMAP-Rule:MF_00205};\n"
                        + "CC   -!- RNA EDITING: Modified_positions=156"
                        + " {ECO:0000313|EMBL:BAG16761.1},\n"
                        + "CC       158 {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160\n"
                        + "CC       {ECO:0000303|Ref.6}; Note=Partially edited. RNA editing"
                        + " generates\n"
                        + "CC       receptor isoforms that differ in their ability to interact with\n"
                        + "CC       the phospholipase C signaling cascade in a transfected cell"
                        + " line,\n"
                        + "CC       suggesting that this RNA processing event may contribute to the\n"
                        + "CC       modulation of serotonergic neurotransmission in the central\n"
                        + "CC       nervous system. {ECO:0000313|PDB:3OW2,\n"
                        + "CC       ECO:0000256|HAMAP-Rule:MF_00205};\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(2, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof RnaEditing);
    }

    @Test
    void testNoHeader2() {
        String ccLineString =
                "RNA EDITING: Modified_positions=46 {ECO:0000269|PubMed:12527781,"
                        + " ECO:0000269|PubMed:12711687}, 1052 {ECO:0000269|PubMed:12527781,"
                        + " ECO:0000269|PubMed:12711687}; Note=The nonsense codons at positions 46,"
                        + " 421, 973, 984 and 1048 are modified to sense codons.;";

        CcLineFormater formater = new CcLineFormater();
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }
}
