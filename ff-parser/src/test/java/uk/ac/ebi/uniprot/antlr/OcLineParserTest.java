package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.oc.OcLineObject;

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
