package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.rg.RgLineObject;

public class RgLineParserTest {
	@Test
	public void test() {
		String rgLines = "RG   The mouse genome sequencing consortium;\n";
		UniprotLineParser<RgLineObject> parser = new DefaultUniprotLineParserFactory().createRgLineParser();
		RgLineObject obj = parser.parse(rgLines);
		verify(obj, Arrays.asList(new String[] {"The mouse genome sequencing consortium"}));

	
	}
	private void verify(RgLineObject obj, List<String> groups) {
		assertEquals(groups, obj.reference_groups);
	}
	@Test
	public void test2() {
		String rgLines = "RG   The mouse genome sequencing consortium;\n"
				+"RG   The something else consortium;\n"
				;
		UniprotLineParser<RgLineObject> parser = new DefaultUniprotLineParserFactory().createRgLineParser();
		RgLineObject obj = parser.parse(rgLines);
		verify(obj, Arrays.asList(new String[] {"The mouse genome sequencing consortium",
				"The something else consortium"}));

	
	}
}
