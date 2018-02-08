package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineObject;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineObject.OgEnum;

public class OgLineParserTest {
	@Test
	public void test() {
		String ogLines = "OG   Plasmid.\n";
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		verify(obj, Arrays.asList(new OgEnum[] {OgEnum.PLASMID}), Collections.emptyList());
	}
	private void verify(OgLineObject obj, List<OgEnum> ogs, List<String> plasmidNames) {
		assertEquals(ogs, obj.ogs);
		assertEquals(plasmidNames, obj.plasmidNames);
	}
	
	@Test
	public void testWithValue() {
		String ogLines = "OG   Plasmid R68.45.\n";
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		verify(obj, Arrays.asList(new OgEnum[] {}),
				Arrays.asList(new String[] {"R68.45"}));
	}
	@Test
	public void testWithValue2() {
		String ogLines = "OG   Plasmid IncFII R1-19 (R1 drd-19).\n";
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		verify(obj, Arrays.asList(new OgEnum[] {}),
				Arrays.asList(new String[] {"IncFII R1-19 (R1 drd-19)"}));
	}
	
	@Test
	public void testWithMultiValues() {
		String ogLines = "OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and\n"
		+ "OG   Plasmid IncFII R1-19 (R1 drd-19).\n"
		;
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		verify(obj, Arrays.asList(new OgEnum[] {}),
				Arrays.asList(new String[] {"R6-5", "IncFII R100 (NR1)", "IncFII R1-19 (R1 drd-19)"}));
	}
	@Test
	public void testWithMultiOgs() {
		String ogLines = "OG   Hydrogenosome.\n"
		+ "OG   Mitochondrion.\n"
		+ "OG   Nucleomorph.\n"
		+ "OG   Plasmid R6-5.\n"
		+ "OG   Plastid.\n"
		+ "OG   Plastid; Apicoplast.\n"
		+ "OG   Plastid; Chloroplast.\n"
		+ "OG   Plastid; Organellar chromatophore.\n"
		+ "OG   Plastid; Cyanelle.\n"
		+ "OG   Plastid; Non-photosynthetic plastid.\n"
		
		;
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		verify(obj, Arrays.asList(new OgEnum[] {OgEnum.HYDROGENOSOME, OgEnum.MITOCHONDRION, OgEnum.NUCLEOMORPH,OgEnum.PLASTID,
				OgEnum.PLASTID_APICOPLAST, OgEnum.PLASTID_CHLOROPLAST, OgEnum.PLASTID_ORGANELLAR_CHROMATOPHORE, OgEnum.PLASTID_CYANELLE, OgEnum.PLASTID_NON_PHOTOSYNTHETIC}),
				Arrays.asList(new String[] {"R6-5"}));
	}
	@Test
	public void testWithMultiOgsEvidence() {
		String ogLines = "OG   Hydrogenosome {ECO:0000001}.\n"
		+ "OG   Mitochondrion {ECO:0000001}.\n"
		+ "OG   Nucleomorph {ECO:0000002|PubMed:1234213}.\n"
		+ "OG   Plasmid R6-5 {ECO:0000001, ECO:0000002|PubMed:1234213}.\n"
		+ "OG   Plastid {ECO:0000001}.\n"
		+ "OG   Plastid; Apicoplast {ECO:0000001}.\n"
		+ "OG   Plastid; Chloroplast {ECO:0000001}.\n"
		+ "OG   Plastid; Organellar chromatophore {ECO:0000001}.\n"
		+ "OG   Plastid; Cyanelle {ECO:0000001}.\n"
		+ "OG   Plastid; Non-photosynthetic plastid {ECO:0000002|PubMed:1234213, ECO:0000003|PubMed:3321222}.\n"
		
		;
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		verify(obj, Arrays.asList(new OgEnum[] {OgEnum.HYDROGENOSOME, OgEnum.MITOCHONDRION, OgEnum.NUCLEOMORPH,OgEnum.PLASTID,
				OgEnum.PLASTID_APICOPLAST, OgEnum.PLASTID_CHLOROPLAST, OgEnum.PLASTID_ORGANELLAR_CHROMATOPHORE, 
				OgEnum.PLASTID_CYANELLE, OgEnum.PLASTID_NON_PHOTOSYNTHETIC}),
				Arrays.asList(new String[] {"R6-5"}));
		verifyEvidence(obj, "R6-5", Arrays.asList(new String[] {"ECO:0000001", "ECO:0000002|PubMed:1234213"}));
		verifyEvidence(obj, OgEnum.HYDROGENOSOME, Arrays.asList(new String[] {"ECO:0000001"}));
		verifyEvidence(obj, OgEnum.MITOCHONDRION, Arrays.asList(new String[] {"ECO:0000001"}));
		verifyEvidence(obj, OgEnum.NUCLEOMORPH, Arrays.asList(new String[] {"ECO:0000002|PubMed:1234213"}));
		verifyEvidence(obj, OgEnum.PLASTID, Arrays.asList(new String[] {"ECO:0000001"}));
		verifyEvidence(obj, OgEnum.PLASTID_APICOPLAST, Arrays.asList(new String[] {"ECO:0000001"}));
		verifyEvidence(obj, OgEnum.PLASTID_CHLOROPLAST, Arrays.asList(new String[] {"ECO:0000001"}));
		verifyEvidence(obj, OgEnum.PLASTID_ORGANELLAR_CHROMATOPHORE, Arrays.asList(new String[] {"ECO:0000001"}));
		verifyEvidence(obj, OgEnum.PLASTID_CYANELLE, Arrays.asList(new String[] {"ECO:0000001"}));
		verifyEvidence(obj, OgEnum.PLASTID_NON_PHOTOSYNTHETIC, Arrays.asList(new String[] {"ECO:0000002|PubMed:1234213","ECO:0000003|PubMed:3321222" }));
		
	}
	
	private void verifyEvidence (OgLineObject ogObj, Object obj, List<String> evidences) {
		assertEquals(evidences, ogObj.evidence.evidences.get(obj));
	}
	
	@Test
	public void testWithMultiOgsEvidence2() {
		String ogLines = "OG   Plasmid R1 (R7268) {ECO:0000313|EMBL:BAG16761.1},\n"
		+ "OG   Plasmid IncF::IncFIA::IncFIB::IncI1-ly {ECO:0000313|EMBL:BAG16761.1,\n"
		+ "OG   ECO:0000269|PubMed:10433554}, Plasmid p013.1IncR {ECO:0000303|Ref.6,\n"
		+ "OG   ECO:0000313|PDB:3OW2}, Plasmid pUD16 {ECO:0000313|PDB:3OW2}, and\n"
		+ "OG   Plasmid IncF::IncL/M {ECO:0000256|HAMAP-Rule:MF_00205}.\n"

		
		;
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		verify(obj, Arrays.asList(new OgEnum[] {}),
				Arrays.asList(new String[] {"R1 (R7268)","IncF::IncFIA::IncFIB::IncI1-ly",
						"p013.1IncR" ,"pUD16", "IncF::IncL/M" }));
		verifyEvidence(obj, "R1 (R7268)", Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}));
		verifyEvidence(obj, "IncF::IncFIA::IncFIB::IncI1-ly", 
				Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1", "ECO:0000269|PubMed:10433554"}));
		verifyEvidence(obj, "p013.1IncR",
				Arrays.asList(new String[] {"ECO:0000303|Ref.6", "ECO:0000313|PDB:3OW2"}));
		verifyEvidence(obj, "pUD16", Arrays.asList(new String[] {"ECO:0000313|PDB:3OW2"}));
		verifyEvidence(obj, "IncF::IncL/M", Arrays.asList(new String[] {"ECO:0000256|HAMAP-Rule:MF_00205"}));
		
	}
	
	@Test
	public void testBigOg() {
		String ogLines = "OG   Plastid; Chloroplast {ECO:0000313|EMBL:BAG16761.1,\n"
		+ "OG   ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2,\n"
		+ "OG   ECO:0000256|HAMAP-Rule:MF_00205}.\n"
		+ "OG   Plasmid IncFII R100 (NR1) {ECO:0000313|EMBL:BAG16761.1,\n"
		+ "OG   ECO:0000269|PubMed:10433554}, Plasmid IncW R388 {ECO:0000303|Ref.6},\n"
		+ "OG   and Plasmid pLMO20 {ECO:0000313|EMBL:BAG16761.1,\n"
		+ "OG   ECO:0000256|HAMAP-Rule:MF_00205}.\n"

		
		;
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		assertNotNull(obj);		
	}
	@Test
	public void testBigOg2() {
		String ogLines = "OG   Plastid; Chloroplast {ECO:0000313|EMBL:BAG16761.1,\n"
		+ "OG   ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2,\n"
		+ "OG   ECO:0000256|HAMAP-Rule:MF_00205}.\n"
		
		;
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		assertNotNull(obj);		
	}
	@Test
	public void testBigOg3() {
		String ogLines = "OG   Plasmid R1 (R7268), Plasmid IncF::IncFIA::IncFIB::IncI1-ly,\n"
		+ "OG   Plasmid p013.1IncR, Plasmid pUD16, and Plasmid IncF::IncL/M.\n"
		
		
		;
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		assertNotNull(obj);		
	}
	@Test
	public void testPlasmdWithEvidence() {
		String ogLines = "OG   Plasmid {ECO:0000313|ProtImp}.\n"
		
		;
		UniprotLineParser<OgLineObject> parser = new DefaultUniprotLineParserFactory().createOgLineParser();
		OgLineObject obj = parser.parse(ogLines);
		verify(obj, Arrays.asList(new OgEnum[] {OgEnum.PLASMID}), Collections.emptyList());
		verifyEvidence(obj, OgEnum.PLASMID, Arrays.asList(new String[] {"ECO:0000313|ProtImp"}));
	}
}

