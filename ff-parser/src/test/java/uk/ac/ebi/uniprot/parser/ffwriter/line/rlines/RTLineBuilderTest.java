package uk.ac.ebi.uniprot.parser.ffwriter.line.rlines;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.ffwriter.line.impl.rlines.RTLineBuilder;


public class RTLineBuilderTest {
	private final RTLineBuilder builder = new RTLineBuilder();
	@Test
	public void test1(){
		String title = "Comparative genomic analyses of frog virus 3, type species of the genus Ranavirus (family Iridoviridae).";
		List<String> lines = builder.buildLine(title, true, true);
		assertEquals(2, lines.size());
		String expected= "RT   genus Ranavirus (family Iridoviridae).\";";
		assertEquals(expected, lines.get(1));
	}
	@Test
	public void test2(){
		String title = "Comparative genomic analyses of frog virus 3, type species of the genus Ranavirus (family Iridoviridae).";
		List<String> lines = builder.buildLine(title, false, true);
		assertEquals(1, lines.size());
		String expected= "\"Comparative genomic analyses of frog virus 3, type species of the genus Ranavirus (family Iridoviridae).\";";
		assertEquals(expected, lines.get(0));
	}
	@Test
	public void testLineWrapDash(){
	    String title ="Characterization and organization of the genes encoding the A-, B- and C-chains of human complement subcomponent C1q. The complete derived amino acid sequence of human C1q.";
	    List<String> lines = builder.buildLine(title, true, true);
	    assertEquals("RT   \"Characterization and organization of the genes encoding the A-,", lines.get(0));
	}
}
