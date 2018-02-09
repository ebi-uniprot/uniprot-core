package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.pe.PeLineObject;

public class PeLineParserTest {
	@Test
	public void test() {
		String peLines = "PE   1: Evidence at protein level;\n";
		UniprotLineParser<PeLineObject> parser = new DefaultUniprotLineParserFactory().createPeLineParser();
		PeLineObject obj = parser.parse(peLines);
		assertEquals(1, obj.level.intValue());
	
	}
}