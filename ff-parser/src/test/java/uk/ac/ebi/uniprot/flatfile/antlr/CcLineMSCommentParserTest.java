package uk.ac.ebi.uniprot.flatfile.antlr;

import org.junit.Test;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineFormater;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CcLineMSCommentParserTest {
	@Test
	public void testMaldiWithMassAndError() {
		String lines = "CC   -!- MASS SPECTROMETRY: Mass=24948; Mass_error=6; Method=MALDI;\n"
				+"CC       Range=1-228; Evidence={ECO:0000006|PubMed:16629414};\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.MassSpectrometry);
		CcLineObject.MassSpectrometry ms = (CcLineObject.MassSpectrometry) cc.object;
	

		verify(ms, 24948, 6, "MALDI", 1, 1, 228);
		assertEquals(1, ms.sources.size());
		assertEquals("ECO:0000006|PubMed:16629414", ms.sources.get(0));
		assertEquals("ECO:0000006|PubMed:16629414", obj.evidenceInfo.evidences.get(ms).get(0));
	}
	
	private void verify(CcLineObject.MassSpectrometry ms, float mass, float mass_error, String method, int rangeSize, int start, int end) {
		assertEquals(mass, ms.mass, 0.000001f);
		assertEquals(mass_error, ms.massError, 0.000001f);
		assertEquals(method, ms.method);
		assertEquals(rangeSize, ms.ranges.size());
		if(ms.ranges.size()>0) {
		assertEquals(start, ms.ranges.get(0).start);
		assertEquals(end, ms.ranges.get(0).end);
		};
	}

	@Test
	public void testRangeWithIsoform() {
		String lines = "CC   -!- MASS SPECTROMETRY: Mass=13822; Method=MALDI; Range=19-140 (P15522-\n"
				+"CC       2); Evidence={ECO:0000006|PubMed:16629414};\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.MassSpectrometry);
		CcLineObject.MassSpectrometry ms = (CcLineObject.MassSpectrometry) cc.object;

		verify(ms,  13822, 0, "MALDI", 1, 19, 140);
		assertEquals("P15522-2", ms.ranges.get(0).rangeIsoform);
		assertEquals(1, ms.sources.size());
	
		assertEquals("ECO:0000006|PubMed:16629414", ms.sources.get(0));
		assertEquals("ECO:0000006|PubMed:16629414", obj.evidenceInfo.evidences.get(ms).get(0));
	}
	
	@Test
	public void testMultiRange() {
		String lines = "CC   -!- MASS SPECTROMETRY: Mass=514.2; Method=Electrospray; Range=51-54,\n"
				 +"CC       71-74, 91-94, 132-135, 148-151; Note=The measured mass is that of\n"
				+"CC       RPGW-amide.; Evidence={ECO:0000006|PubMed:16629414};\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.MassSpectrometry);
		CcLineObject.MassSpectrometry ms = (CcLineObject.MassSpectrometry) cc.object;

		verify(ms,  514.2f, 0, "Electrospray", 5, 51, 54);
		assertEquals("The measured mass is that of RPGW-amide.", ms.note);
		assertEquals(1, ms.sources.size());
	
		assertEquals("ECO:0000006|PubMed:16629414", ms.sources.get(0));
		assertEquals("ECO:0000006|PubMed:16629414", obj.evidenceInfo.evidences.get(ms).get(0));
	}

	@Test
	public void testRangeWithUnknown() {
		String lines = "CC   -!- MASS SPECTROMETRY: Mass=9571; Method=Electrospray; Range=1-?;\n"
				 +"CC       Evidence={ECO:0000006|PubMed:16629414};\n"
				 ;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.MassSpectrometry);
		CcLineObject.MassSpectrometry ms = (CcLineObject.MassSpectrometry) cc.object;


		verify(ms,  9571, 0, "Electrospray", 1, 1, 0);
		assertTrue(ms.ranges.get(0).endUnknown);
		assertEquals(1, ms.sources.size());
	
		assertEquals("ECO:0000006|PubMed:16629414", ms.sources.get(0));
		assertEquals("ECO:0000006|PubMed:16629414", obj.evidenceInfo.evidences.get(ms).get(0));
	}
	
	@Test
	public void testWithNoteNoError() {
		String lines = "CC   -!- MASS SPECTROMETRY: Mass=7190; Method=MALDI; Range=1-67;\n"
				 +"CC       Note=Variant 6.01; Evidence={ECO:0000006|PubMed:16629414};\n"
				 ;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.MassSpectrometry);
		CcLineObject.MassSpectrometry ms = (CcLineObject.MassSpectrometry) cc.object;


		verify(ms,  7190, 0, "MALDI", 1, 1, 67);
	//	assertTrue(ms.ranges.get(0).end_unknown);
		assertEquals("Variant 6.01", ms.note);
		assertEquals(1, ms.sources.size());
	
		assertEquals("ECO:0000006|PubMed:16629414", ms.sources.get(0));
		assertEquals("ECO:0000006|PubMed:16629414", obj.evidenceInfo.evidences.get(ms).get(0));
	}
	
	@Test
	public void test6() {
		String lines = "CC   -!- MASS SPECTROMETRY: Mass=1200.8; Mass_error=2.0E-4; Method=MALDI;\n"
				 +"CC       Range=24-33; Evidence={ECO:0000006|PubMed:16629414};\n"
				 ;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.MassSpectrometry);
		CcLineObject.MassSpectrometry ms = (CcLineObject.MassSpectrometry) cc.object;


		verify(ms,  1200.8f, 2.0E-4f, "MALDI", 1, 24, 33);
	//	assertTrue(ms.ranges.get(0).end_unknown);
	//	assertEquals("Variant 6.01", ms.note);
		assertEquals(1, ms.sources.size());
	
		assertEquals("ECO:0000006|PubMed:16629414", ms.sources.get(0));
		assertEquals("ECO:0000006|PubMed:16629414", obj.evidenceInfo.evidences.get(ms).get(0));
	}
	
	
	@Test
	public void testMultiEvidences() {
		String lines = "CC   -!- MASS SPECTROMETRY: Mass=3979.9; Method=Electrospray; Range=51-81;\n"
				 +"CC       Evidence={ECO:0000006|PubMed:16629414, ECO:0000006|PubMed:16629415};\n"
				 ;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.MassSpectrometry);
		CcLineObject.MassSpectrometry ms = (CcLineObject.MassSpectrometry) cc.object;


		verify(ms,  3979.9f, 0, "Electrospray", 1, 51, 81);
	//	assertTrue(ms.ranges.get(0).end_unknown);
	//	assertEquals("Variant 6.01", ms.note);
		assertEquals(2, ms.sources.size());
	
		assertEquals("ECO:0000006|PubMed:16629414", ms.sources.get(0));
		assertEquals("ECO:0000006|PubMed:16629415", ms.sources.get(1));
		assertEquals("ECO:0000006|PubMed:16629414", obj.evidenceInfo.evidences.get(ms).get(0));
		assertEquals("ECO:0000006|PubMed:16629415", obj.evidenceInfo.evidences.get(ms).get(1));
	}
	
	@Test
	public void testNoHeader() {
		String ccLineString = "MASS SPECTROMETRY: Mass=3979.9; Method=Electrospray; Range=51-81;\n"
				 +"Evidence={ECO:0000006|PubMed:16629414, ECO:0000006|PubMed:16629415};\n"
				 ;
		CcLineFormater formater  =new CcLineFormater();
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		String lines = formater.format(ccLineString);
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.MassSpectrometry);
		CcLineObject.MassSpectrometry ms = (CcLineObject.MassSpectrometry) cc.object;


		verify(ms,  3979.9f, 0, "Electrospray", 1, 51, 81);
	//	assertTrue(ms.ranges.get(0).end_unknown);
	//	assertEquals("Variant 6.01", ms.note);
		assertEquals(2, ms.sources.size());
	
		assertEquals("ECO:0000006|PubMed:16629414", ms.sources.get(0));
		assertEquals("ECO:0000006|PubMed:16629415", ms.sources.get(1));
		assertEquals("ECO:0000006|PubMed:16629414", obj.evidenceInfo.evidences.get(ms).get(0));
		assertEquals("ECO:0000006|PubMed:16629415", obj.evidenceInfo.evidences.get(ms).get(1));
	}
	
}
