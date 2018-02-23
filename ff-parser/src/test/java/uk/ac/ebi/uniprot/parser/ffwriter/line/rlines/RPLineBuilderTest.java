package uk.ac.ebi.uniprot.parser.ffwriter.line.rlines;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.ffwriter.line.impl.rlines.RPLineBuilder;


public class RPLineBuilderTest {
	private final RPLineBuilder builder = new RPLineBuilder();
	
	@Test 
	public void test1(){
		List<String> css = new ArrayList<>();
		css.add("NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]");

		List<String> lines = builder.buildLine(css, true, true);
		String expected ="RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].";
		assertEquals(1, lines.size());
		assertEquals(expected, lines.get(0));
		
	}
	@Test 
	public void test2(){
		List<String> css = new ArrayList<>();
		css.add("NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]");
		List<String> lines = builder.buildLine(css, false, true);
		String expected ="NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].";
		assertEquals(1, lines.size());
		assertEquals(expected, lines.get(0));
		
	}
	
}
