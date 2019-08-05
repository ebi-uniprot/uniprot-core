package org.uniprot.core.flatfile.antlr;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.oc.OcLineObject;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OcLineParserTest {
	@Test
	public void test() {
		String ocLines = "OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi.\n";
		UniprotLineParser<OcLineObject> parser = new DefaultUniprotLineParserFactory().createOcLineParser();
		OcLineObject obj = parser.parse(ocLines);
		verify(obj, Arrays.asList(new String[] {"Eukaryota", "Metazoa", "Chordata", "Craniata", "Vertebrata", "Euteleostomi"}));

	}
	private  void verify(OcLineObject obj, List<String> nodes) {
		assertEquals(nodes, obj.nodes);
	}
	@Test
	public void test2() {
		String ocLines = "OC   Eukaryota; Metazoa; Chordata; Craniata;\n"
				+"OC   Vertebrata; Euteleostomi.\n";
				;
		UniprotLineParser<OcLineObject> parser = new DefaultUniprotLineParserFactory().createOcLineParser();
		OcLineObject obj = parser.parse(ocLines);
		verify(obj, Arrays.asList(new String[] {"Eukaryota", "Metazoa", "Chordata", "Craniata", "Vertebrata", "Euteleostomi"}));

	}
	@Test
	public void test3() {
		String ocLines = "OC   Viruses; dsDNA viruses, no RNA stage; Iridoviridae; Ranavirus.\n"
			
				;
		UniprotLineParser<OcLineObject> parser = new DefaultUniprotLineParserFactory().createOcLineParser();
		OcLineObject obj = parser.parse(ocLines);
		verify(obj, Arrays.asList(new String[] {"Viruses", "dsDNA viruses, no RNA stage", "Iridoviridae", "Ranavirus"}));

	}
	@Test
	public void test4() {
		String ocLines = "OC   Bacteria; Bacteroidetes; Bacteroidetes Order II. Incertae sedis;\n"
			+"OC   Rhodothermaceae; Salinibacter.\n"
				;
		UniprotLineParser<OcLineObject> parser = new DefaultUniprotLineParserFactory().createOcLineParser();
		OcLineObject obj = parser.parse(ocLines);
		verify(obj, Arrays.asList(new String[] {"Bacteria", "Bacteroidetes", "Bacteroidetes Order II. Incertae sedis", 
				"Rhodothermaceae", "Salinibacter"}));

	}
}
