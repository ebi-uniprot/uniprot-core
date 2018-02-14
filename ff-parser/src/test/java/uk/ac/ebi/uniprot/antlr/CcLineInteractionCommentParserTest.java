package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineFormater;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.InteractionObject;

public class CcLineInteractionCommentParserTest {
	@Test
	public void test1() {
		String lines = "CC   -!- INTERACTION:\n" + "CC       P11450:fcp3c; NbExp=1; IntAct=EBI-126914, EBI-159556;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(1, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-126914", "EBI-159556", "fcp3c", "P11450", false, false, 1);
	}

	private void verify(InteractionObject io, String firstId, String secondId, String gene, String spAc, boolean isSelf,
			boolean xeno, int nbexp) {
		assertEquals(firstId, io.firstId);
		assertEquals(secondId, io.secondId);
		assertEquals(gene, io.gene);
		assertEquals(spAc, io.spAc);
		assertEquals(isSelf, io.isSelf);
		assertEquals(xeno, io.xeno);
		assertEquals(nbexp, io.nbexp);
	}

	@Test
	public void test2() {
		String lines = "CC   -!- INTERACTION:\n"
				+ "CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(1, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-133844", "EBI-212772", "CG11299", "Q9W1K5-1", false, false, 1);
	}

	@Test
	public void test3() {
		String lines = "CC   -!- INTERACTION:\n" + "CC       Q8NI08:-; NbExp=1; IntAct=EBI-80809, EBI-80799;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(1, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-80809", "EBI-80799", "-", "Q8NI08", false, false, 1);
	}

	@Test
	public void test4() {
		String lines = "CC   -!- INTERACTION:\n" + "CC       Self; NbExp=1; IntAct=EBI-123485, EBI-123485;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(1, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-123485", "EBI-123485", null, null, true, false, 1);
	}

	@Test
	public void test5() {
		String lines = "CC   -!- INTERACTION:\n"
				+ "CC       Q8C1S0:2410018M14Rik (xeno); NbExp=1; IntAct=EBI-394562, EBI-398761;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(1, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-394562", "EBI-398761", "2410018M14Rik", "Q8C1S0", false, true, 1);
	}

	@Test
	public void test6() {
		String lines = "CC   -!- INTERACTION:\n" + "CC       P51618:IRAK1; NbExp=2; IntAct=EBI-448466, EBI-358664;\n"
				+ "CC       P51617:IRAK2; NbExp=3; IntAct=EBI-448472, EBI-358664;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(2, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-448466", "EBI-358664", "IRAK1", "P51618", false, false, 2);
		verify(ir.interactions.get(1), "EBI-448472", "EBI-358664", "IRAK2", "P51617", false, false, 3);
	}

	@Test
	public void test7() {
		String lines = "CC   -!- INTERACTION:\n" + "CC       G5EC23:hcf-1; NbExp=2; IntAct=EBI-318108, EBI-4480523;\n"
				+ "CC       Q11184:let-756; NbExp=3; IntAct=EBI-318108, EBI-3843983;\n"
				+ "CC       Q10666:pop-1; NbExp=2; IntAct=EBI-318108, EBI-317870;\n"
				+ "CC       Q21921:sir-2.1; NbExp=3; IntAct=EBI-318108, EBI-966082;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(4, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-318108", "EBI-4480523", "hcf-1", "G5EC23", false, false, 2);
		verify(ir.interactions.get(1), "EBI-318108", "EBI-3843983", "let-756", "Q11184", false, false, 3);

		verify(ir.interactions.get(2), "EBI-318108", "EBI-317870", "pop-1", "Q10666", false, false, 2);
		verify(ir.interactions.get(3), "EBI-318108", "EBI-966082", "sir-2.1", "Q21921", false, false, 3);
	}

	@Test
	public void test8() {
		String lines = "CC   -!- INTERACTION:\n"
				+ "CC       Q9W4W2:fs(1)Yb; NbExp=4; IntAct=EBI-2890374, EBI-3424083;\n"
				+ "CC       Q9VKM1:piwi; NbExp=4; IntAct=EBI-2890374, EBI-3406276;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(2, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-2890374", "EBI-3424083", "fs(1)Yb", "Q9W4W2", false, false, 4);
		verify(ir.interactions.get(1), "EBI-2890374", "EBI-3406276", "piwi", "Q9VKM1", false, false, 4);

	}

	@Test
	public void test9() {
		String lines = "CC   -!- INTERACTION:\n"
				+ "CC       Q67XQ1:At1g03430; NbExp=2; IntAct=EBI-1100967, EBI-1100725;\n"
				+ "CC       Q9C5A5:At5g08720/T2K12_70; NbExp=3; IntAct=EBI-1100967, EBI-1998000;\n"
				+ "CC       Q9SSW0:AZF3; NbExp=2; IntAct=EBI-1100967, EBI-1807790;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(3, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-1100967", "EBI-1100725", "At1g03430", "Q67XQ1", false, false, 2);
		verify(ir.interactions.get(1), "EBI-1100967", "EBI-1998000", "At5g08720/T2K12_70", "Q9C5A5", false, false, 3);
		verify(ir.interactions.get(2), "EBI-1100967", "EBI-1807790", "AZF3", "Q9SSW0", false, false, 2);

	}

	@Test
	public void test10() {
		String lines = "CC   -!- INTERACTION:\n"
				+ "CC       Q9V3G9:EG:BACR37P7.5; NbExp=1; IntAct=EBI-175067, EBI-162998;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(1, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-175067", "EBI-162998", "EG:BACR37P7.5", "Q9V3G9", false, false, 1);

	}

	@Test
	public void test11() {
		String lines = "CC   -!- INTERACTION:\n"
				+ "CC       Q9SZI2:NAP1;1; NbExp=4; IntAct=EBI-6913662, EBI-4424361;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(1, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-6913662", "EBI-4424361", "NAP1;1", "Q9SZI2", false, false, 4);

	}

	@Test
	public void test12() {
		String lines = "CC   -!- INTERACTION:\n"
				+ "CC       A1Z199:BCR/ABL fusion; NbExp=2; IntAct=EBI-491549, EBI-7286259;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.Interaction);
		CcLineObject.Interaction ir = (CcLineObject.Interaction) cc.object;
		assertEquals(1, ir.interactions.size());

		verify(ir.interactions.get(0), "EBI-491549", "EBI-7286259", "BCR/ABL fusion", "A1Z199", false, false, 2);

	}
	@Test
	public void testNoHeader() {
		String ccLineString =("INTERACTION:\n" +
				"Self; NbExp=1; IntAct=EBI-123485, EBI-123485;\n" +
				"Q9W158:CG4612; NbExp=1; IntAct=EBI-123485, EBI-89895;\n" +
				"Q9VYI0:fne; NbExp=1; IntAct=EBI-123485, EBI-126770;");
		
			CcLineFormater formater  =new CcLineFormater();
			UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
			String lines = formater.format(ccLineString);
			CcLineObject obj = parser.parse(lines);
			assertNotNull(obj);
	}

	@Test
	public void testNoHeader2() {
		String ccLineString =("INTERACTION:\n" +
				"Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n" +
				"O96017:CHEK2; NbExp=4; IntAct=EBI-372428, EBI-1180783;\n" +
				"Q6ZWQ9:Myl12a (xeno); NbExp=3; IntAct=EBI-372428, EBI-8034418;");
		
			CcLineFormater formater  =new CcLineFormater();
			UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
			String lines = formater.format(ccLineString);
			CcLineObject obj = parser.parse(lines);
			assertNotNull(obj);
	}

}
