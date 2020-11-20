package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.LocationValue;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.SubcullarLocation;

class CcLineSubCellCommentParserTest {
    @Test
    void test1() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Cytoplasm. Endoplasmic reticulum membrane;\n"
                        + "CC       Peripheral membrane protein. Golgi apparatus membrane;"
                        + " Peripheral\n"
                        + "CC       membrane protein.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals(3, sl.getLocations().size());
        verify(sl.getLocations().get(0).getSubcellularLocation(), "Cytoplasm", null);
        verify(
                sl.getLocations().get(1).getSubcellularLocation(),
                "Endoplasmic reticulum membrane",
                null);
        verify(sl.getLocations().get(1).getTopology(), "Peripheral membrane protein", null);

        verify(sl.getLocations().get(2).getSubcellularLocation(), "Golgi apparatus membrane", null);
        verify(sl.getLocations().get(2).getTopology(), "Peripheral membrane protein", null);
    }

    @Test
    void test1WithEvidence() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Cytoplasm {ECO:0000256|HAMAP-Rule:MF_01146}.\n";
        ;
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals(1, sl.getLocations().size());

        verify(sl.getLocations().get(0).getSubcellularLocation(), "Cytoplasm", null);
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_01146",
                obj.getEvidenceInfo()
                        .getEvidences()
                        .get(sl.getLocations().get(0).getSubcellularLocation())
                        .get(0));
    }

    @Test
    void test1WithEvidence2() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Cytoplasm. {ECO:0000256|HAMAP-Rule:MF_01146}.\n";
        ;
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals(1, sl.getLocations().size());

        verify(sl.getLocations().get(0).getSubcellularLocation(), "Cytoplasm", null);
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_01146",
                obj.getEvidenceInfo().getEvidences().get(sl.getLocations().get(0)).get(0));
    }

    private void verify(LocationValue lv, String value, LocationValue.LocationFlagEnum flag) {
        assertEquals(value, lv.getValue());
        assertEquals(flag, lv.getFlag());
    }

    @Test
    void test2() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Cell membrane; Peripheral membrane protein\n"
                        + "CC       (By similarity). Secreted (By similarity). Note=The last 22 C-\n"
                        + "CC       terminal amino acids may participate in cell membrane"
                        + " attachment.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals(
                "The last 22 C-terminal amino acids may participate in cell membrane attachment",
                sl.getNote().get(0).getValue());
        assertEquals(2, sl.getLocations().size());

        verify(sl.getLocations().get(0).getSubcellularLocation(), "Cell membrane", null);
        verify(
                sl.getLocations().get(0).getTopology(),
                "Peripheral membrane protein",
                LocationValue.LocationFlagEnum.BY_SIMILARITY);
        verify(
                sl.getLocations().get(1).getSubcellularLocation(),
                "Secreted",
                LocationValue.LocationFlagEnum.BY_SIMILARITY);
    }

    @Test
    void test3() {
        String lines = "CC   -!- SUBCELLULAR LOCATION: [Isoform 2]: Cytoplasm (Probable).\n"
                //	+"CC       (By similarity). Secreted (By similarity). Note=The last 22 C-\n"
                //	+"CC       terminal amino acids may participate in cell membrane attachment.\n"
                ;
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals("Isoform 2", sl.getMolecule());

        verify(
                sl.getLocations().get(0).getSubcellularLocation(),
                "Cytoplasm",
                LocationValue.LocationFlagEnum.PROBABLE);
        // verify(sl.getLocations().get(0).getTopology(), "Peripheral membrane protein",
        // LocationFlagEnum.By_similarity );
        // verify(sl.getLocations().get(1).subcellular_location, "Secreted",
        // LocationFlagEnum.By_similarity );

    }

    @Test
    void test4() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Golgi apparatus, trans-Golgi network\n"
                        + "CC       membrane; Multi-pass membrane protein (By similarity).\n"
                        + "CC       Note=Predominantly found in the trans-Golgi network (TGN). Not\n"
                        + "CC       redistributed to the plasma membrane in response to elevated\n"
                        + "CC       copper levels.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals(
                "Predominantly found in the trans-Golgi network (TGN). Not redistributed to the"
                        + " plasma membrane in response to elevated copper levels",
                sl.getNote().get(0).getValue());
        assertEquals(1, sl.getLocations().size());

        //	verify(sl.getLocations().get(0).subcellular_location, "Cytoplasm",
        // LocationFlagEnum.Probable
        // );
        // verify(sl.getLocations().get(0).getTopology(), "Peripheral membrane protein",
        // LocationFlagEnum.By_similarity );
        // verify(sl.getLocations().get(1).subcellular_location, "Secreted",
        // LocationFlagEnum.By_similarity );

    }

    @Test
    void test5() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Cell membrane; Multi-pass membrane protein.\n"
                        + "CC       Endosome. Note=Interaction with SNX27 mediates recruitment to\n"
                        + "CC       early endosomes, while interaction with SLC9A3R1 and EZR might\n"
                        + "CC       target the protein to specialized subcellular regions, such as\n"
                        + "CC       microvilli (By similarity). Interacts (via C-terminus) with"
                        + " GRK5;\n"
                        + "CC       this interaction is promoted by 5-HT (serotonin) (By"
                        + " similarity).\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals(
                "Interaction with SNX27 mediates recruitment to "
                        + "early endosomes, while interaction with SLC9A3R1 and EZR might "
                        + "target the protein to specialized subcellular regions, such as "
                        + "microvilli (By similarity). Interacts (via C-terminus) with GRK5; "
                        + "this interaction is promoted by 5-HT (serotonin) (By similarity)",
                sl.getNote().get(0).getValue());
        assertEquals(2, sl.getLocations().size());

        //	verify(sl.getLocations().get(0).subcellular_location, "Cytoplasm",
        // LocationFlagEnum.Probable
        // );
        // verify(sl.getLocations().get(0).getTopology(), "Peripheral membrane protein",
        // LocationFlagEnum.By_similarity );
        // verify(sl.getLocations().get(1).subcellular_location, "Secreted",
        // LocationFlagEnum.By_similarity );

    }

    @Test
    void testIsoformHasDot() {
        String lines = "CC   -!- SUBCELLULAR LOCATION: [Isoform UL12.5]: Host mitochondrion.\n"
                //	+"CC       (By similarity). Secreted (By similarity). Note=The last 22 C-\n"
                //	+"CC       terminal amino acids may participate in cell membrane attachment.\n"
                ;
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals("Isoform UL12.5", sl.getMolecule());

        verify(sl.getLocations().get(0).getSubcellularLocation(), "Host mitochondrion", null);
        // verify(sl.getLocations().get(0).getTopology(), "Peripheral membrane protein",
        // LocationFlagEnum.By_similarity );
        // verify(sl.getLocations().get(1).subcellular_location, "Secreted",
        // LocationFlagEnum.By_similarity );

    }

    @Test
    void testNoteWithDot() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Cytoplasm. Cell junction, tight junction.\n"
                        + "CC       Golgi apparatus. Cytoplasm, cytoskeleton, spindle. Cell\n"
                        + "CC       projection, ruffle membrane. Note=Localizes to the tips of\n"
                        + "CC       cortical microtubules of the mitotic spindle during cell"
                        + " division,\n"
                        + "CC       and is further released upon microtubule depolymerization.\n"
                        + "CC       Recruited into membrane ruffles induced by S.flexneri at tight\n"
                        + "CC       junctions of polarized epithelial cells.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();

        assertEquals(5, sl.getLocations().size());

        //	verify(sl.getLocations().get(0).subcellular_location, "Host mitochondrion", null );
        // verify(sl.getLocations().get(0).getTopology(), "Peripheral membrane protein",
        // LocationFlagEnum.By_similarity );
        // verify(sl.getLocations().get(1).subcellular_location, "Secreted",
        // LocationFlagEnum.By_similarity );

    }

    @Test
    void testIsoformWithComma() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: [Processed beta-1,4-galactosyltransferase 1]:\n"
                        + "CC       Secreted. Note=Soluble form found in body fluids.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals("Processed beta-1,4-galactosyltransferase 1", sl.getMolecule());
        assertEquals(1, sl.getLocations().size());

        //	verify(sl.getLocations().get(0).subcellular_location, "Host mitochondrion", null );
        // verify(sl.getLocations().get(0).getTopology(), "Peripheral membrane protein",
        // LocationFlagEnum.By_similarity );
        // verify(sl.getLocations().get(1).subcellular_location, "Secreted",
        // LocationFlagEnum.By_similarity );

    }
    @Test
    void testWithComma() {
    	String lines ="CC   -!- SUBCELLULAR LOCATION: Cell junction, adherens junction.\n";
    	 UniprotKBLineParser<CcLineObject> parser =
                 new DefaultUniprotKBLineParserFactory().createCcLineParser();
         CcLineObject obj = parser.parse(lines);
         assertEquals(1, obj.getCcs().size());
         CC cc = obj.getCcs().get(0);
         assertTrue(cc.getObject() instanceof SubcullarLocation);
         SubcullarLocation sl = (SubcullarLocation) cc.getObject();
         assertEquals(1, sl.getLocations().size());
         assertEquals("Cell junction, adherens junction", sl.getLocations().get(0).getSubcellularLocation().getValue());
    }

    @Test
    void testSublocatWithEvidence() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass\n"
                        + "CC       membrane protein (By similarity) {ECO:0000002|PubMed:1234213}.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals(1, sl.getLocations().size());

        verify(
                sl.getLocations().get(0).getSubcellularLocation(),
                "Mitochondrion inner membrane",
                null);
        verify(
                sl.getLocations().get(0).getTopology(),
                "Multi-pass membrane protein",
                LocationValue.LocationFlagEnum.BY_SIMILARITY);
        // verify(sl.getLocations().get(1).subcellular_location, "Secreted",
        // LocationFlagEnum.By_similarity );
        assertEquals(
                "ECO:0000002|PubMed:1234213",
                obj.getEvidenceInfo()
                        .getEvidences()
                        .get(sl.getLocations().get(0).getTopology())
                        .get(0));
    }

    @Test
    void testSublocatWithEvidenceNote() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion intermembrane space\n"
                        + "CC       {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554}.\n"
                        + "CC       Note=Loosely associated with the inner membrane.\n"
                        + "CC       {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals(1, sl.getLocations().size());

        verify(
                sl.getLocations().get(0).getSubcellularLocation(),
                "Mitochondrion intermembrane space",
                null);
        // verify(sl.getLocations().get(1).subcellular_location, "Secreted",
        // LocationFlagEnum.By_similarity );
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                obj.getEvidenceInfo()
                        .getEvidences()
                        .get(sl.getLocations().get(0).getSubcellularLocation())
                        .get(0));
        assertEquals(
                "ECO:0000269|PubMed:10433554",
                obj.getEvidenceInfo()
                        .getEvidences()
                        .get(sl.getLocations().get(0).getSubcellularLocation())
                        .get(1));
        assertEquals("Loosely associated with the inner membrane", sl.getNote().get(0).getValue());
        assertEquals("ECO:0000303|Ref.6", sl.getNote().get(0).getEvidences().get(0));
        assertEquals("ECO:0000313|PDB:3OW2", sl.getNote().get(0).getEvidences().get(1));
    }

    @Test
    void testSublocationWithEvidence() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass\n"
                        + "CC       membrane protein (By similarity). {ECO:0000002|PubMed:1234213}.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals(1, sl.getLocations().size());

        verify(
                sl.getLocations().get(0).getSubcellularLocation(),
                "Mitochondrion inner membrane",
                null);
        verify(
                sl.getLocations().get(0).getTopology(),
                "Multi-pass membrane protein",
                LocationValue.LocationFlagEnum.BY_SIMILARITY);
        assertEquals(
                "ECO:0000002|PubMed:1234213",
                obj.getEvidenceInfo().getEvidences().get(sl.getLocations().get(0)).get(0));
    }

    @Test
    void testMultiComments() {
        String lines =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane\n"
                        + "CC       {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554};"
                        + " Multi-\n"
                        + "CC       pass membrane protein (By similarity) {ECO:0000303|Ref.6}.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: Mitochondrion intermembrane space.\n"
                        + "CC       Note=Loosely associated with the inner membrane.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass\n"
                        + "CC       membrane protein (By similarity).\n"
                        + "CC   -!- SUBCELLULAR LOCATION: [Spike protein S2]: Virion membrane;"
                        + " Single-\n"
                        + "CC       pass type I membrane sdssds protein (By similarity). Host\n"
                        + "CC       endoplasmic reticulum-Golgi intermediate compartment membrane;\n"
                        + "CC       Type I me (By similarity); Another top. Note=Accumulates in the\n"
                        + "CC       endoplasmic reticulum-Golgi intermediate compartment, where it\n"
                        + "CC       participates in virus particle assembly. Some S oligomers may be\n"
                        + "CC       transported to the plasma membrane, where they may mediate cell\n"
                        + "CC       fusion (By similarity).\n"
                        + "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass\n"
                        + "CC       membrane protein (By similarity). {ECO:0000313|EMBL:BAG16761.1,\n"
                        + "CC       ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: [Spike protein S2]: Virion membrane\n"
                        + "CC       {ECO:0000313|EMBL:BAG16761.1}; Single-pass type I membrane"
                        + " sdssds\n"
                        + "CC       protein (By similarity) {ECO:0000269|PubMed:10433554}. Host\n"
                        + "CC       endoplasmic reticulum-Golgi intermediate compartment membrane\n"
                        + "CC       {ECO:0000303|Ref.6}; Type I me (By similarity)\n"
                        + "CC       {ECO:0000313|PDB:3OW2}; Another top"
                        + " {ECO:0000313|EMBL:BAG16761.1}.\n"
                        + "CC       Note=Accumulates in the endoplasmic reticulum-Golgi intermediate\n"
                        + "CC       compartment, where it participates in virus particle assembly.\n"
                        + "CC       Some S oligomers may be transported to the plasma membrane,"
                        + " where\n"
                        + "CC       they may mediate cell fusion (By similarity)."
                        + " {ECO:0000256|HAMAP-Rule:MF_00205}.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: Mitochondrion intermembrane space\n"
                        + "CC       {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554}.\n"
                        + "CC       Note=Loosely associated with the inner membrane.\n"
                        + "CC       {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}.\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(7, obj.getCcs().size());
    }

    @Test
    void testNoHeader() {
        String ccLineString =
                "SUBCELLULAR LOCATION: Mitochondrion intermembrane space\n"
                        + "{ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554}.\n"
                        + "Note=Loosely associated with the inner membrane.\n"
                        + "{ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}.";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineFormater formater = new CcLineFormater();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SubcullarLocation);
        SubcullarLocation sl = (SubcullarLocation) cc.getObject();
        assertEquals(1, sl.getLocations().size());

        verify(
                sl.getLocations().get(0).getSubcellularLocation(),
                "Mitochondrion intermembrane space",
                null);
        // verify(sl.getLocations().get(1).subcellular_location, "Secreted",
        // LocationFlagEnum.By_similarity );
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                obj.getEvidenceInfo()
                        .getEvidences()
                        .get(sl.getLocations().get(0).getSubcellularLocation())
                        .get(0));
        assertEquals(
                "ECO:0000269|PubMed:10433554",
                obj.getEvidenceInfo()
                        .getEvidences()
                        .get(sl.getLocations().get(0).getSubcellularLocation())
                        .get(1));
        assertEquals("Loosely associated with the inner membrane", sl.getNote().get(0).getValue());
        assertEquals("ECO:0000303|Ref.6", sl.getNote().get(0).getEvidences().get(0));
        assertEquals("ECO:0000313|PDB:3OW2", sl.getNote().get(0).getEvidences().get(1));
    }
}
