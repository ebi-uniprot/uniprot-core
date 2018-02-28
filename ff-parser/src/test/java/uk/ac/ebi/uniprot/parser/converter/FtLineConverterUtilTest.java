package uk.ac.ebi.uniprot.parser.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.impl.ft.ftLineConverterUtil;

public class FtLineConverterUtilTest {
	@Test
	public void testCardohydNoDescr() {
		String val = "O-linked (GalNAc...) serine";
		Matcher matcher = ftLineConverterUtil.CARBOHYD_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
		assertEquals("O-linked", matcher.group(1));
		assertEquals("(GalNAc...) serine", matcher.group(2));
		assertEquals(null, matcher.group(5));
		
	}
	@Test
	public void testCardohydSingleDes() {
		String val = "O-linked (GlcNAc) tyrosine; by Photorhabdus PAU_02230";
		Matcher matcher = ftLineConverterUtil.CARBOHYD_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
		assertEquals("O-linked", matcher.group(1));
		assertEquals("(GlcNAc) tyrosine", matcher.group(2));
		assertEquals("by Photorhabdus PAU_02230", matcher.group(5));
	}
	@Test
	public void testCardohydMultiDes() {
		String val = "N-linked (GlcNAc...) asparagine; atypical; partial";
		Matcher matcher = ftLineConverterUtil.CARBOHYD_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
		assertEquals("N-linked", matcher.group(1));
		assertEquals("(GlcNAc...) asparagine", matcher.group(2));
		assertEquals("atypical; partial", matcher.group(5));
	}
	
	@Test
	public void testVarSeqMissing() {
		String val = "Missing (in isoform Beta)";
		Matcher matcher = ftLineConverterUtil.VAR_SEQ_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
	
		assertEquals("Missing", matcher.group(1));
		assertEquals("isoform Beta", matcher.group(10));
	//	String regex =", isoform | and isoform ";
	//	String[] tokens =  matcher.group(8).substring("isoform ".length()).split(regex);
		
		
	}
	@Test
	public void testVarSeq2() {
		String val = "KIGTTLPEVPT -> RNWHRPCLRCQR (in isoform 2 and isoform 3)";
		Matcher matcher = ftLineConverterUtil.VAR_SEQ_DESC_PATTERN.matcher(val);
	
		assertTrue(matcher.matches());
	
		assertEquals("KIGTTLPEVPT -> RNWHRPCLRCQR", matcher.group(1));
		String original = matcher.group(3).replaceAll(" ", "");
		String other = matcher.group(7).replaceAll(" ", "");
		assertEquals("KIGTTLPEVPT", original);
		assertEquals("RNWHRPCLRCQR", other);
		assertEquals("isoform 2 and isoform 3", matcher.group(10));
		
	}
	@Test
	public void testVarSeq3() {
		String val = "GEARPARAQKPAQL -> V (in isoform SV1, isoform 2, isoform SV5, isoform 8, isoform SV10 and isoform SV11)";
		Matcher matcher = ftLineConverterUtil.VAR_SEQ_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
		String original = matcher.group(3).replaceAll(" ", "");
		String other = matcher.group(7).replaceAll(" ", "");
		assertEquals("GEARPARAQKPAQL -> V", matcher.group(1));
		assertEquals("GEARPARAQKPAQL", original);
		assertEquals("V", other);
		assertEquals("isoform SV1, isoform 2, isoform SV5, isoform 8, isoform SV10 and isoform SV11", matcher.group(10));
	
	}
	
	@Test
	public void testVarSeq4() {
		String original = 
				"MVADPPRDSKGLAAAEPTANGGLALASIEDQGAAAGGYCGSRDQVRRCLRANLLVLLTVVAVVAGVALGLGVSGAGGALALGPERLSAFVFPGELLL"
						+ "RLLRMIILPLVVCSLIGGAASLDPGALGRLGAW"
						+ "ALLFFLVTTLLASALGVGLALALQPGAASAAINASVGAAGSAENAPSKEVLDSFLDLARNIFPSNLVSAAFRS";
		String val ="MVADPPRDSKGLAAAEPTANGGLALASIEDQGAAAGGYCGSRDQVRRCLRANLLVLLTVVAVVAGVALGLGVSGAGGALALGPERLSAFVFPGELLL"
				+ "RLLRMIILPLVVCSLIGGAASLDPGALGRLGAW"
				+ "ALLFFLVTTLLASALGVGLALALQPGAASAAINASVGAAGSAENAPSKEVLDSFLDLARNIFPSNLVSAAFRS-> M (in isoform 2)";
		
		Matcher matcher = ftLineConverterUtil.VAR_SEQ_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
		assertEquals( original + "-> M", matcher.group(1));
		String original1 = matcher.group(3).replaceAll(" ", "");
		String other = matcher.group(7).replaceAll(" ", "");
		assertEquals(original, original1);
		assertEquals("M", other);
		assertEquals("isoform 2", matcher.group(10));
	}
	
	@Test
	public void testVarSeq5() {
		String original = 
				"APLVPIFSFGENDLFDQIPNSSGSWLRYIQNRLQKIMGISLPLFHGRGVFQYSFGLIPYRRPITTVV";
		String val ="APLVPIFSFGENDLFDQIPNSSGSWLRYIQNRLQKIMGISLPLFHGRGVFQYSFGLIPYRRPITTVV"
				+ " -> YQASGKSTLGS VGNWQGFYFGGKMAETNADSILVEIFSPFTIKIIFWCLMPKYLEKFPQRRLSDLRN (in isoform 3)";
		String val1 = "APLVPIFSFGENDLFDQIPNSSGSWLRYIQNRLQKIMGISLPLFHGRGVFQYSFGLIPYRRPITTVV"
				+ " -> YQASGKSTLGS VGNWQGFYFGGKMAETNADSILVEIFSPFTIKIIFWCLMPKYLEKFPQRRLSDLRN";
		String other = "YQASGKSTLGSVGNWQGFYFGGKMAETNADSILVEIFSPFTIKIIFWCLMPKYLEKFPQRRLSDLRN";
		Matcher matcher = ftLineConverterUtil.VAR_SEQ_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
		assertEquals( val1, matcher.group(1));
		String original1 = matcher.group(3).replaceAll(" ", "");
		String other1 = matcher.group(7).replaceAll(" ", "");
		assertEquals(original,  original1);
		assertEquals(other, other1);
		assertEquals("isoform 3", matcher.group(10));
	}
	
	
	@Test
	public void testVariantMissing() {
		String val = "Missing (in strain: Isolate clinical 49A)";
		Matcher matcher = ftLineConverterUtil.VAIANT_DESC_PATTERN.matcher(val);
	
		assertTrue(matcher.matches());
		assertEquals("Missing", matcher.group(1));
		assertEquals(null, matcher.group(3));
		assertEquals(null, matcher.group(5));
		assertEquals("in strain: Isolate clinical 49A", matcher.group(8));

	}
	
	@Test
	public void testVariantStandard() {
		String val = "Y -> C (in dbSNP:rs56074660)";
		Matcher matcher = ftLineConverterUtil.VAIANT_DESC_PATTERN.matcher(val);
		
		assertTrue(matcher.matches());
		assertEquals("Y -> C", matcher.group(1));
		assertEquals("Y", matcher.group(3));
		assertEquals("C", matcher.group(5));
		assertEquals("in dbSNP:rs56074660", matcher.group(8));

	}
	@Test
	public void testVariantEmpty() {
		String val = "PL -> GE ()";
		Matcher matcher = ftLineConverterUtil.VAIANT_DESC_PATTERN.matcher(val);
		
		assertTrue(matcher.matches());
		assertEquals("PL -> GE", matcher.group(1));
		assertEquals("PL", matcher.group(3));
		assertEquals("GE", matcher.group(5));
		assertEquals(null, matcher.group(8));

	}
	@Test
	public void testVariantEmpty2() {
		String val = "R -> K";
		Matcher matcher = ftLineConverterUtil.VAIANT_DESC_PATTERN.matcher(val);
		
		assertTrue(matcher.matches());
		assertEquals("R -> K", matcher.group(1));
		assertEquals("R", matcher.group(3));
		assertEquals("K", matcher.group(5));
		assertEquals(null, matcher.group(8));

	}
	@Test
	public void testVariantMissingEmpty() {
		String val = "Missing";
		Matcher matcher = ftLineConverterUtil.VAIANT_DESC_PATTERN.matcher(val);
		
		assertTrue(matcher.matches());
		assertEquals("Missing", matcher.group(1));
		assertEquals(null, matcher.group(3));
		assertEquals(null, matcher.group(5));
		assertEquals(null, matcher.group(8));

	}

	
	@Test
	public void testMutagenMissing() {
		String val = "Missing: Leads to hyper-activity and constitutive expression of MDR1";
		Matcher matcher = ftLineConverterUtil.MUTAGEN_DESC_PATTERN.matcher(val);
		
		assertTrue(matcher.matches());
		assertEquals("Missing", matcher.group(1));
		assertEquals(null, matcher.group(3));
		assertEquals(null, matcher.group(5));
		assertEquals("Leads to hyper-activity and constitutive expression of MDR1", matcher.group(8));

	}
	
	@Test
	public void testMutagenSingle() {
		String val = "H->A: Decreased activity";
		Matcher matcher = ftLineConverterUtil.MUTAGEN_DESC_PATTERN.matcher(val);
		
		assertTrue(matcher.matches());
		assertEquals("H->A", matcher.group(1));
		assertEquals("H", matcher.group(3));
		assertEquals("A", matcher.group(5));
		assertEquals("Decreased activity", matcher.group(8));
	
	}
	
	@Test
	public void testMutagenMulti() {
		String val = "W->D,N,G: Strongly reduced enzyme activity but does not affect UDP-binding";
		Matcher matcher = ftLineConverterUtil.MUTAGEN_DESC_PATTERN.matcher(val);
		
		assertTrue(matcher.matches());
		assertEquals("W->D,N,G", matcher.group(1));
		assertEquals("W", matcher.group(3));
		assertEquals("D,N,G", matcher.group(5));
	//	String [] tokens =matcher.group(5).split("\\,");
		
		assertEquals("Strongly reduced enzyme activity but does not affect UDP-binding", matcher.group(8));

	}
	@Test
	public void testMutagenMultiSpace() {
		String val = "W -> D,N,G: Strongly reduced enzyme activity but does not affect UDP-binding";
		Matcher matcher = ftLineConverterUtil.MUTAGEN_DESC_PATTERN.matcher(val);
		
		assertTrue(matcher.matches());
		assertEquals("W -> D,N,G", matcher.group(1));
		assertEquals("W", matcher.group(3).trim());
		assertEquals("D,N,G", matcher.group(5).trim());
	//	String [] tokens =matcher.group(5).split("\\,");
		
		assertEquals("Strongly reduced enzyme activity but does not affect UDP-binding", matcher.group(8));

	}
	@Test
	public void testConflictDescMissing() {
		String val="Missing (in Ref. 3; AA sequence)";
		Matcher matcher = ftLineConverterUtil.CONFLICT_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
		assertEquals("Missing", matcher.group(1));
		assertEquals(null, matcher.group(3));
		assertEquals(null, matcher.group(5));
		assertEquals("3; AA sequence", matcher.group(10));
		String regex =", | and ";
		String[] tokens = matcher.group(10).split(regex);
		assertEquals(1, tokens.length);
		assertEquals("3; AA sequence", tokens[0]);
	}
	
	@Test
	public void testConflictDescSingle() {
		String val="S -> G (in Ref. 4; AAA42118/AAA42303)";
		Matcher matcher = ftLineConverterUtil.CONFLICT_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
		assertEquals("S -> G", matcher.group(1));
		assertEquals("S", matcher.group(3));
		assertEquals("G", matcher.group(7));
		assertEquals("4; AAA42118/AAA42303", matcher.group(10));
		String regex =", | and ";
		String[] tokens = matcher.group(10).split(regex);
		assertEquals(1, tokens.length);
		assertEquals("4; AAA42118/AAA42303", tokens[0]);
	
	}
	@Test
	public void testConflictDescTwo() {
		String val="L -> V (in Ref. 3; CAA49505 and 7; AAO89504/AAO89505)";
		Matcher matcher = ftLineConverterUtil.CONFLICT_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
		assertEquals("L -> V", matcher.group(1));
		assertEquals("L", matcher.group(3));
		assertEquals("V", matcher.group(7));
		assertEquals("3; CAA49505 and 7; AAO89504/AAO89505", matcher.group(10));
		String regex =", | and ";
		String[] tokens = matcher.group(10).split(regex);
		assertEquals(2, tokens.length);
		assertEquals("3; CAA49505", tokens[0]);
		assertEquals("7; AAO89504/AAO89505", tokens[1]);
	
	}
	@Test
	public void testConflictDescMulti() {
		String val="Q -> K (in Ref. 1; AAO59377, 2; ABO40479 and 6; AAH63566)";
		Matcher matcher = ftLineConverterUtil.CONFLICT_DESC_PATTERN.matcher(val);
		assertTrue(matcher.matches());
		assertEquals("Q -> K", matcher.group(1));
		assertEquals("Q", matcher.group(3));
		assertEquals("K", matcher.group(7));
		assertEquals("1; AAO59377, 2; ABO40479 and 6; AAH63566", matcher.group(10));
		String regex =", | and ";
		String[] tokens = matcher.group(10).split(regex);
		assertEquals(3, tokens.length);
		assertEquals("1; AAO59377", tokens[0]);
		assertEquals("2; ABO40479", tokens[1]);
		assertEquals("6; AAH63566", tokens[2]);
	}
}
