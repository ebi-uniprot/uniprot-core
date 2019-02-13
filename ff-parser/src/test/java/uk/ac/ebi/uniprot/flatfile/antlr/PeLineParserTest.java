package uk.ac.ebi.uniprot.flatfile.antlr;

import org.junit.Test;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.pe.PeLineObject;

import static org.junit.Assert.assertEquals;

public class PeLineParserTest {
	@Test
	public void test() {
		String peLines = "PE   1: Evidence at protein level;\n";
		UniprotLineParser<PeLineObject> parser = new DefaultUniprotLineParserFactory().createPeLineParser();
		PeLineObject obj = parser.parse(peLines);
		assertEquals(1, obj.level.intValue());
	
	}
}