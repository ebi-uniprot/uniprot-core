package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.CofactorItem;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.StructuredCofactor;

public class CcLineCofactorCommentParserTest {
	@Test
	public void test() {
		String lines = "CC   -!- COFACTOR: Isoform 1:\n"
				+"CC       Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000255|HAMAP-Rule:MF_00087};\n"
				+"CC       Name=Co(2+); Xref=ChEBI:CHEBI:48828; Evidence={ECO:0000255|HAMAP-Rule:MF_00088};\n"
				+"CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).\n"
				+"CC       {ECO:0000255|HAMAP-Rule:MF_00086};\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof StructuredCofactor);
		StructuredCofactor ms = (StructuredCofactor)cc.object;
		List<CofactorItem> cofactors =ms.cofactors;
		assertEquals(2, cofactors.size());
		assertEquals("Mg(2+)", cofactors.get(0).name);
		assertEquals("ChEBI:CHEBI:18420", cofactors.get(0).xref);
		assertEquals("ECO:0000255|HAMAP-Rule:MF_00087",  obj.evidenceInfo.evidences.get(cofactors.get(0)).get(0));
		
		assertEquals("Co(2+)", cofactors.get(1).name);
		assertEquals("ChEBI:CHEBI:48828", cofactors.get(1).xref);
		assertEquals("ECO:0000255|HAMAP-Rule:MF_00088",  obj.evidenceInfo.evidences.get(cofactors.get(1)).get(0));
		assertEquals("Isoform 1", ms.molecule);
		assertEquals(1, ms.note.size());
		assertEquals("Binds 2 divalent ions per subunit (magnesium or cobalt).",  ms.note.get(0).value);
		assertEquals("ECO:0000255|HAMAP-Rule:MF_00086",  ms.note.get(0).evidences.get(0));
	}
	
	@Test
	public void test2() {
		String lines = "CC   -!- COFACTOR: Serine protease NS3:\n"
				+"CC       Name=Zn(2+); Xref=ChEBI:CHEBI:29105;\n"
				+"CC         Evidence={ECO:0000269|PubMed:16683188,\n"
				+"CC         ECO:0000269|PubMed:16683189};\n"
				+"CC       Name=A very very looooooooooooong cofactor name with 1 evidence tag;\n"
				+"CC         Xref=ChEBI:CHEBI:12345; Evidence={ECO:0000269|PubMed:16683188};\n"
				+"CC       Name=A very very looooooooooooong cofactor name with X evidence tags;\n"
				+"CC         Xref=ChEBI:CHEBI:54321;\n"
				+"CC         Evidence={ECO:0000269|PubMed:16683188, ECO:0000269|PubMed:16683189};\n"
				+"CC       Note=Binds 2 divalent ions per subunit.\n"
				+"CC       {ECO:0000255|HAMAP-Rule:MF_00086};\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof StructuredCofactor);
		StructuredCofactor ms = (StructuredCofactor)cc.object;
		List<CofactorItem> cofactors =ms.cofactors;
		assertEquals(3, cofactors.size());
		assertEquals("Zn(2+)", cofactors.get(0).name);
		assertEquals("ChEBI:CHEBI:29105", cofactors.get(0).xref);
		assertEquals("ECO:0000269|PubMed:16683188", obj.evidenceInfo.evidences.get(cofactors.get(0)).get(0));
		
		assertEquals("A very very looooooooooooong cofactor name with 1 evidence tag", cofactors.get(1).name);
		assertEquals("ChEBI:CHEBI:12345", cofactors.get(1).xref);
		assertEquals("ECO:0000269|PubMed:16683188", obj.evidenceInfo.evidences.get(cofactors.get(1)).get(0));
		
		assertEquals("A very very looooooooooooong cofactor name with X evidence tags", cofactors.get(2).name);
		assertEquals("ChEBI:CHEBI:54321", cofactors.get(2).xref);
		assertEquals("ECO:0000269|PubMed:16683189",  obj.evidenceInfo.evidences.get(cofactors.get(2)).get(1));
		
		assertEquals("Serine protease NS3", ms.molecule);
		assertEquals(1, ms.note.size());
		assertEquals("Binds 2 divalent ions per subunit.",  ms.note.get(0).value);
		assertEquals("ECO:0000255|HAMAP-Rule:MF_00086",  ms.note.get(0).evidences.get(0));
	}

	
	@Test
	public void testNoteOnly() {
		String lines = "CC   -!- COFACTOR:\n"
				+"CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).\n"
				+"CC       {ECO:0000255|HAMAP-Rule:MF_00086};\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof StructuredCofactor);
		StructuredCofactor ms = (StructuredCofactor)cc.object;
		List<CofactorItem> cofactors =ms.cofactors;
		assertEquals(0, cofactors.size());
		assertEquals(1, ms.note.size());
		assertEquals("Binds 2 divalent ions per subunit (magnesium or cobalt).",  ms.note.get(0).value);
		assertEquals("ECO:0000255|HAMAP-Rule:MF_00086",  ms.note.get(0).evidences.get(0));
	}
	
	@Test
	public void testNoteOnly2() {
		String lines = "CC   -!- COFACTOR:\n"
				+"CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).\n"
				+"CC       Binds 3 divalent ions per subunit (magnesium or cobalt).;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof StructuredCofactor);
		StructuredCofactor ms = (StructuredCofactor)cc.object;
		List<CofactorItem> cofactors =ms.cofactors;
		assertEquals(0, cofactors.size());
		assertEquals(1, ms.note.size());
		assertEquals("Binds 2 divalent ions per subunit (magnesium or cobalt). Binds 3 divalent ions per subunit (magnesium or cobalt).",  ms.note.get(0).value);
		assertEquals(0,  ms.note.get(0).evidences.size());
	}
	
	@Test
	public void testTwoNoteOnly2() {
		String lines = "CC   -!- COFACTOR:\n"
				+"CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).;\n"
				+"CC       Binds 3 divalent ions per subunit (magnesium or cobalt).;\n"
;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof StructuredCofactor);
		StructuredCofactor ms = (StructuredCofactor)cc.object;
		List<CofactorItem> cofactors =ms.cofactors;
		assertEquals(0, cofactors.size());
		assertEquals(2, ms.note.size());
		assertEquals("Binds 2 divalent ions per subunit (magnesium or cobalt).",  ms.note.get(0).value);
		assertEquals("Binds 3 divalent ions per subunit (magnesium or cobalt).",  ms.note.get(1).value);
		assertEquals(0,  ms.note.get(0).evidences.size());
	}


	@Test
	public void testTwoNoteOnlyWithEvidence() {
		String lines = "CC   -!- COFACTOR:\n"
				+"CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt). {ECO:0000255|HAMAP-Rule:MF_00086};\n"
				+"CC       Binds 3 divalent ions per subunit (magnesium or cobalt).;\n"
;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof StructuredCofactor);
		StructuredCofactor ms = (StructuredCofactor)cc.object;
		List<CofactorItem> cofactors =ms.cofactors;
		assertEquals(0, cofactors.size());
		assertEquals(2, ms.note.size());
		assertEquals("Binds 2 divalent ions per subunit (magnesium or cobalt).",  ms.note.get(0).value);
		assertEquals("Binds 3 divalent ions per subunit (magnesium or cobalt).",  ms.note.get(1).value);
		assertEquals(1,  ms.note.get(0).evidences.size());
		assertEquals("ECO:0000255|HAMAP-Rule:MF_00086",  ms.note.get(0).evidences.get(0));
	}

}
