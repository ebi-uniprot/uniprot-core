package org.uniprot.core.flatfile.antlr;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineObject;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
