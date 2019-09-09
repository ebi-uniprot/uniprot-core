package org.uniprot.core.flatfile.antlr;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject.LocationFlagEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CcLineSubCellCommentParserTest {
	@Test
	public void test1() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Cytoplasm. Endoplasmic reticulum membrane;\n"
				+"CC       Peripheral membrane protein. Golgi apparatus membrane; Peripheral\n"
				+"CC       membrane protein.\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals(3, sl.locations.size());
		verify(sl.locations.get(0).subcellularLocation, "Cytoplasm", null );
		verify(sl.locations.get(1).subcellularLocation, "Endoplasmic reticulum membrane", null );
		verify(sl.locations.get(1).topology, "Peripheral membrane protein", null );
		
		verify(sl.locations.get(2).subcellularLocation, "Golgi apparatus membrane", null );
		verify(sl.locations.get(2).topology, "Peripheral membrane protein", null );
	}
	@Test
	public void test1WithEvidence() {
		String lines =  "CC   -!- SUBCELLULAR LOCATION: Cytoplasm {ECO:0000256|HAMAP-Rule:MF_01146}.\n" ;
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals(1, sl.locations.size());
		
		verify(sl.locations.get(0).subcellularLocation, "Cytoplasm", null );
		assertEquals("ECO:0000256|HAMAP-Rule:MF_01146", obj.evidenceInfo.evidences.get(sl.locations.get(0).subcellularLocation).get(0) );
	}
	
	@Test
	public void test1WithEvidence2() {
		String lines =  "CC   -!- SUBCELLULAR LOCATION: Cytoplasm. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" ;
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals(1, sl.locations.size());
		
		verify(sl.locations.get(0).subcellularLocation, "Cytoplasm", null );
		assertEquals("ECO:0000256|HAMAP-Rule:MF_01146", obj.evidenceInfo.evidences.get(sl.locations.get(0)).get(0) );
	}
	
	private void verify(CcLineObject.LocationValue lv, String value, LocationFlagEnum flag) {
		assertEquals(value, lv.value);
		assertEquals(flag, lv.flag);		
	}
	
	@Test
	public void test2() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Cell membrane; Peripheral membrane protein\n"
				+"CC       (By similarity). Secreted (By similarity). Note=The last 22 C-\n"
				+"CC       terminal amino acids may participate in cell membrane attachment.\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals("The last 22 C-terminal amino acids may participate in cell membrane attachment", sl.note.get(0).value);
		assertEquals(2, sl.locations.size());
		
		
		verify(sl.locations.get(0).subcellularLocation, "Cell membrane", null );
		verify(sl.locations.get(0).topology, "Peripheral membrane protein", LocationFlagEnum.BY_SIMILARITY );
		verify(sl.locations.get(1).subcellularLocation, "Secreted",  LocationFlagEnum.BY_SIMILARITY );

	}
	
	@Test
	public void test3() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Isoform 2: Cytoplasm (Probable).\n"
			//	+"CC       (By similarity). Secreted (By similarity). Note=The last 22 C-\n"
			//	+"CC       terminal amino acids may participate in cell membrane attachment.\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals("Isoform 2", sl.molecule);
		
		
		verify(sl.locations.get(0).subcellularLocation, "Cytoplasm", LocationFlagEnum.PROBABLE );
		//verify(sl.locations.get(0).topology, "Peripheral membrane protein", LocationFlagEnum.By_similarity );
		//verify(sl.locations.get(1).subcellular_location, "Secreted",  LocationFlagEnum.By_similarity );

	}
	
	@Test
	public void test4() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Golgi apparatus, trans-Golgi network\n"
				+"CC       membrane; Multi-pass membrane protein (By similarity).\n"
				+"CC       Note=Predominantly found in the trans-Golgi network (TGN). Not\n"
				+"CC       redistributed to the plasma membrane in response to elevated\n"
				+"CC       copper levels.\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals("Predominantly found in the trans-Golgi network (TGN). " +
			      "Not redistributed to the plasma membrane in response to elevated copper levels", sl.note.get(0).value);
		assertEquals(1, sl.locations.size());
		
		
	//	verify(sl.locations.get(0).subcellular_location, "Cytoplasm", LocationFlagEnum.Probable );
		//verify(sl.locations.get(0).topology, "Peripheral membrane protein", LocationFlagEnum.By_similarity );
		//verify(sl.locations.get(1).subcellular_location, "Secreted",  LocationFlagEnum.By_similarity );

	}
	
	@Test
	public void test5() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Cell membrane; Multi-pass membrane protein.\n"
       +"CC       Endosome. Note=Interaction with SNX27 mediates recruitment to\n"
        +"CC       early endosomes, while interaction with SLC9A3R1 and EZR might\n"
        +"CC       target the protein to specialized subcellular regions, such as\n"
        +"CC       microvilli (By similarity). Interacts (via C-terminus) with GRK5;\n" 
        +"CC       this interaction is promoted by 5-HT (serotonin) (By similarity).\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals("Interaction with SNX27 mediates recruitment to "
			      + "early endosomes, while interaction with SLC9A3R1 and EZR might "
			      + "target the protein to specialized subcellular regions, such as "
			      + "microvilli (By similarity). Interacts (via C-terminus) with GRK5; "
			      +"this interaction is promoted by 5-HT (serotonin) (By similarity)", sl.note.get(0).value);
		assertEquals(2, sl.locations.size());
		
		
	//	verify(sl.locations.get(0).subcellular_location, "Cytoplasm", LocationFlagEnum.Probable );
		//verify(sl.locations.get(0).topology, "Peripheral membrane protein", LocationFlagEnum.By_similarity );
		//verify(sl.locations.get(1).subcellular_location, "Secreted",  LocationFlagEnum.By_similarity );

	}
	@Test
	public void testIsoformHasDot() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Isoform UL12.5: Host mitochondrion.\n"
			//	+"CC       (By similarity). Secreted (By similarity). Note=The last 22 C-\n"
			//	+"CC       terminal amino acids may participate in cell membrane attachment.\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals("Isoform UL12.5", sl.molecule);
		
		
		verify(sl.locations.get(0).subcellularLocation, "Host mitochondrion", null );
		//verify(sl.locations.get(0).topology, "Peripheral membrane protein", LocationFlagEnum.By_similarity );
		//verify(sl.locations.get(1).subcellular_location, "Secreted",  LocationFlagEnum.By_similarity );

	}
	
	@Test
	public void testNoteWithDot() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Cytoplasm. Cell junction, tight junction.\n"
				+"CC       Golgi apparatus. Cytoplasm, cytoskeleton, spindle. Cell\n"
				+"CC       projection, ruffle membrane. Note=Localizes to the tips of\n"
				+"CC       cortical microtubules of the mitotic spindle during cell division,\n"
				+"CC       and is further released upon microtubule depolymerization.\n"
				+"CC       Recruited into membrane ruffles induced by S.flexneri at tight\n"
				+"CC       junctions of polarized epithelial cells.\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		
		assertEquals(5, sl.locations.size());
		
	//	verify(sl.locations.get(0).subcellular_location, "Host mitochondrion", null );
		//verify(sl.locations.get(0).topology, "Peripheral membrane protein", LocationFlagEnum.By_similarity );
		//verify(sl.locations.get(1).subcellular_location, "Secreted",  LocationFlagEnum.By_similarity );

	}
	@Test
	public void testIsoformWithComma() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Processed beta-1,4-galactosyltransferase 1:\n"
				+"CC       Secreted. Note=Soluble form found in body fluids.\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals("Processed beta-1,4-galactosyltransferase 1", sl.molecule);
		assertEquals(1, sl.locations.size());
		
	//	verify(sl.locations.get(0).subcellular_location, "Host mitochondrion", null );
		//verify(sl.locations.get(0).topology, "Peripheral membrane protein", LocationFlagEnum.By_similarity );
		//verify(sl.locations.get(1).subcellular_location, "Secreted",  LocationFlagEnum.By_similarity );

	}
	@Test
	public void testSublocatWithEvidence() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass\n"
				+"CC       membrane protein (By similarity) {ECO:0000002|PubMed:1234213}.\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals(1, sl.locations.size());
		
		verify(sl.locations.get(0).subcellularLocation, "Mitochondrion inner membrane", null );
		verify(sl.locations.get(0).topology, "Multi-pass membrane protein", LocationFlagEnum.BY_SIMILARITY );
		//verify(sl.locations.get(1).subcellular_location, "Secreted",  LocationFlagEnum.By_similarity );
		assertEquals("ECO:0000002|PubMed:1234213", obj.evidenceInfo.evidences.get(sl.locations.get(0).topology).get(0) );

	}
	@Test
	public void testSublocatWithEvidenceNote() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Mitochondrion intermembrane space\n"
				+"CC       {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554}.\n"
				+"CC       Note=Loosely associated with the inner membrane.\n"
				+"CC       {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}.\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals(1, sl.locations.size());
		
		verify(sl.locations.get(0).subcellularLocation, "Mitochondrion intermembrane space", null );
		//verify(sl.locations.get(1).subcellular_location, "Secreted",  LocationFlagEnum.By_similarity );
		assertEquals("ECO:0000313|EMBL:BAG16761.1", obj.evidenceInfo.evidences.get(sl.locations.get(0).subcellularLocation).get(0) );
		assertEquals("ECO:0000269|PubMed:10433554", obj.evidenceInfo.evidences.get(sl.locations.get(0).subcellularLocation).get(1) );
		assertEquals("Loosely associated with the inner membrane", sl.note.get(0).value );
		assertEquals("ECO:0000303|Ref.6", sl.note.get(0).evidences.get(0) );
		assertEquals("ECO:0000313|PDB:3OW2", sl.note.get(0).evidences.get(1) );
	}
	
	@Test
	public void testSublocationWithEvidence() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass\n"
				+"CC       membrane protein (By similarity). {ECO:0000002|PubMed:1234213}.\n"

				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals(1, sl.locations.size());
		
		verify(sl.locations.get(0).subcellularLocation, "Mitochondrion inner membrane", null );
		verify(sl.locations.get(0).topology, "Multi-pass membrane protein",  LocationFlagEnum.BY_SIMILARITY );
		assertEquals("ECO:0000002|PubMed:1234213", obj.evidenceInfo.evidences.get(sl.locations.get(0)).get(0) );

	}
	
	@Test
	public void testMultiComments() {
		String lines = "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane\n" + 
				"CC       {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554}; Multi-\n" + 
				"CC       pass membrane protein (By similarity) {ECO:0000303|Ref.6}.\n" + 
				"CC   -!- SUBCELLULAR LOCATION: Mitochondrion intermembrane space.\n" + 
				"CC       Note=Loosely associated with the inner membrane.\n" +
				"CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass\n" + 
				"CC       membrane protein (By similarity).\n" +
				"CC   -!- SUBCELLULAR LOCATION: Spike protein S2: Virion membrane; Single-\n" + 
				"CC       pass type I membrane sdssds protein (By similarity). Host\n" + 
				"CC       endoplasmic reticulum-Golgi intermediate compartment membrane;\n" + 
				"CC       Type I me (By similarity); Another top. Note=Accumulates in the\n" + 
				"CC       endoplasmic reticulum-Golgi intermediate compartment, where it\n" + 
				"CC       participates in virus particle assembly. Some S oligomers may be\n" + 
				"CC       transported to the plasma membrane, where they may mediate cell\n" + 
				"CC       fusion (By similarity).\n" +
				"CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass\n" + 
				"CC       membrane protein (By similarity). {ECO:0000313|EMBL:BAG16761.1,\n" + 
				"CC       ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}.\n" + 
				"CC   -!- SUBCELLULAR LOCATION: Spike protein S2: Virion membrane\n" + 
				"CC       {ECO:0000313|EMBL:BAG16761.1}; Single-pass type I membrane sdssds\n" + 
				"CC       protein (By similarity) {ECO:0000269|PubMed:10433554}. Host\n" + 
				"CC       endoplasmic reticulum-Golgi intermediate compartment membrane\n" + 
				"CC       {ECO:0000303|Ref.6}; Type I me (By similarity)\n" + 
				"CC       {ECO:0000313|PDB:3OW2}; Another top {ECO:0000313|EMBL:BAG16761.1}.\n" + 
				"CC       Note=Accumulates in the endoplasmic reticulum-Golgi intermediate\n" + 
				"CC       compartment, where it participates in virus particle assembly.\n" + 
				"CC       Some S oligomers may be transported to the plasma membrane, where\n" + 
				"CC       they may mediate cell fusion (By similarity). {ECO:0000256|HAMAP-Rule:MF_00205}.\n" + 
				"CC   -!- SUBCELLULAR LOCATION: Mitochondrion intermembrane space\n" + 
				"CC       {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554}.\n" + 
				"CC       Note=Loosely associated with the inner membrane.\n" + 
				"CC       {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}.\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(7, obj.ccs.size());
	}
	
	@Test
	public void testNoHeader() {
		String ccLineString = "SUBCELLULAR LOCATION: Mitochondrion intermembrane space\n"
				+"{ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554}.\n"
				+"Note=Loosely associated with the inner membrane.\n"
				+"{ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}."
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineFormater formater  =new CcLineFormater();
		String lines = formater.format(ccLineString);
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SubcullarLocation);
		CcLineObject.SubcullarLocation sl = (CcLineObject.SubcullarLocation) cc.object;
		assertEquals(1, sl.locations.size());
		
		verify(sl.locations.get(0).subcellularLocation, "Mitochondrion intermembrane space", null );
		//verify(sl.locations.get(1).subcellular_location, "Secreted",  LocationFlagEnum.By_similarity );
		assertEquals("ECO:0000313|EMBL:BAG16761.1", obj.evidenceInfo.evidences.get(sl.locations.get(0).subcellularLocation).get(0) );
		assertEquals("ECO:0000269|PubMed:10433554", obj.evidenceInfo.evidences.get(sl.locations.get(0).subcellularLocation).get(1) );
		assertEquals("Loosely associated with the inner membrane", sl.note.get(0).value );
		assertEquals("ECO:0000303|Ref.6", sl.note.get(0).evidences.get(0) );
		assertEquals("ECO:0000313|PDB:3OW2", sl.note.get(0).evidences.get(1) );
	}
	
}
