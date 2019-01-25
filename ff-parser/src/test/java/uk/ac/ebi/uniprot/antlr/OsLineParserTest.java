package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineObject;

public class OsLineParserTest {
	@Test
	public void test() {
		String osLines = "OS   Solanum melongena (Eggplant) (Aubergine).\n";
		UniprotLineParser<OsLineObject> parser = new DefaultUniprotLineParserFactory().createOsLineParser();
		OsLineObject obj = parser.parse(osLines);
		assertEquals("Solanum melongena (Eggplant) (Aubergine)", obj.organism_species);
	
	}
	@Test
	public void testTwoLine() {
		String osLines = "OS   Rous (strain Schmidt-Ruppin A) (Avian leukosis\n"
				+"OS   virus-RSA).\n"
				;
		UniprotLineParser<OsLineObject> parser = new DefaultUniprotLineParserFactory().createOsLineParser();
		OsLineObject obj = parser.parse(osLines);
		assertEquals("Rous (strain Schmidt-Ruppin A) (Avian leukosis virus-RSA)", obj.organism_species);
	
	}
	
	@Test
	public void testThreeLine() {
		String osLines = "OS   Rous (strain Schmidt-Ruppin A)\n"
				+"OS   (Avian leukosis\n"
				+"OS   virus-RSA).\n"
				;
		UniprotLineParser<OsLineObject> parser = new DefaultUniprotLineParserFactory().createOsLineParser();
		OsLineObject obj = parser.parse(osLines);
		assertEquals("Rous (strain Schmidt-Ruppin A) (Avian leukosis virus-RSA)", obj.organism_species);
	
	}
	@Test
	public void testWithSlash() {
		String osLines = "OS   African swine fever virus (isolate Pig/Kenya/KEN-50/1950) (ASFV).\n"
				;
		UniprotLineParser<OsLineObject> parser = new DefaultUniprotLineParserFactory().createOsLineParser();
		OsLineObject obj = parser.parse(osLines);
		assertEquals("African swine fever virus (isolate Pig/Kenya/KEN-50/1950) (ASFV)", obj.organism_species);
	
	}
	@Test
	public void testWithDot() {
		String osLines = "OS   Salmonella enterica subsp. enterica serovar Heidelberg str.\n"
				;
		UniprotLineParser<OsLineObject> parser = new DefaultUniprotLineParserFactory().createOsLineParser();
		OsLineObject obj = parser.parse(osLines);
		assertEquals("Salmonella enterica subsp. enterica serovar Heidelberg str", obj.organism_species);
	
	}
	@Test
	public void testWithDot2() {
		String osLines = "OS   Aeromonas sp. E6(2011).\n"
				;
		UniprotLineParser<OsLineObject> parser = new DefaultUniprotLineParserFactory().createOsLineParser();
		OsLineObject obj = parser.parse(osLines);
		assertEquals("Aeromonas sp. E6(2011)", obj.organism_species);
	
	}
	
	@Test
	public void testVirus() {
		String osLines = "OS   Frog virus 3 (isolate Goorha) (FV-3).\n"
				;
		UniprotLineParser<OsLineObject> parser = new DefaultUniprotLineParserFactory().createOsLineParser();
		OsLineObject obj = parser.parse(osLines);
		assertEquals("Frog virus 3 (isolate Goorha) (FV-3)", obj.organism_species);
	}
}
