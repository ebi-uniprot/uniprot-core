package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.ac.AcLineObject;

public class AcLineParserTest {
	@Test
	public void validOneLineOneAcc() {
		String ac_one_line = "AC   Q6GZX4;\n";
		UniprotLineParser<AcLineObject> parser = new DefaultUniprotLineParserFactory().createAcLineParser();
		AcLineObject obj = parser.parse(ac_one_line);
		assertEquals("Q6GZX4", obj.primaryAcc);
		assertTrue(obj.secondaryAcc.isEmpty());
	}

	@Test(expected = RuntimeException.class)
	public void inValidOneLineOneAcc() {
		String ac_one_line = "AC   Q6GDDZX4;\n";
		UniprotLineParser<AcLineObject> parser = new DefaultUniprotLineParserFactory().createAcLineParser();
	 parser.parse(ac_one_line);

	}

	@Test
	public void validOneLineMoreAcc() {
		String ac_one_line = "AC   Q6GZX4; Q6GZX5; Q6GZX6;\n";
		UniprotLineParser<AcLineObject> parser = new DefaultUniprotLineParserFactory().createAcLineParser();
		AcLineObject obj = parser.parse(ac_one_line);
		assertEquals("Q6GZX4", obj.primaryAcc);
		List<String> expected = Arrays.asList(new String[] { "Q6GZX5", "Q6GZX6" });
		assertEquals(expected, obj.secondaryAcc);
	}

	@Test
	public void validMultiLineMoreAcc() {
		String ac_one_line = "AC   Q6GZX4; Q6GZX5; Q6GZX6;\n" + "AC   Q6GZX7; Q6GZX8; Q6GZX9;\n" + "AC   Q6GZX0;\n";
		UniprotLineParser<AcLineObject> parser = new DefaultUniprotLineParserFactory().createAcLineParser();
		AcLineObject obj = parser.parse(ac_one_line);
		assertEquals("Q6GZX4", obj.primaryAcc);
		List<String> expected = Arrays.asList(new String[] { "Q6GZX5", "Q6GZX6", "Q6GZX7", "Q6GZX8", "Q6GZX9", "Q6GZX0" });
		assertEquals(expected, obj.secondaryAcc);
	}
	@Test
	public void validOneLineMoreNewAcc() {
		String ac_one_line = "AC   A0A000A000; Q6GZX5; Q6GZX6;\n";
		UniprotLineParser<AcLineObject> parser = new DefaultUniprotLineParserFactory().createAcLineParser();
		AcLineObject obj = parser.parse(ac_one_line);
		assertEquals("A0A000A000", obj.primaryAcc);
		List<String> expected = Arrays.asList(new String[] { "Q6GZX5", "Q6GZX6" });
		assertEquals(expected, obj.secondaryAcc);
	}

	@Test
	public void validOneLineMoreIsoformAcc() {
		String ac_one_line = "AC   A0A000A000-1; Q6GZX5-11; Q6GZX6-10;\n";
		UniprotLineParser<AcLineObject> parser = new DefaultUniprotLineParserFactory().createAcLineParser();
		AcLineObject obj = parser.parse(ac_one_line);
		assertEquals("A0A000A000-1", obj.primaryAcc);
		List<String> expected = Arrays.asList(new String[] { "Q6GZX5-11", "Q6GZX6-10" });
		assertEquals(expected, obj.secondaryAcc);
	}

	
}
