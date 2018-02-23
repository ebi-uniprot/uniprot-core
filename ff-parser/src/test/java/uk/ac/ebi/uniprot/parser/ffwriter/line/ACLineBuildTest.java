package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.parser.impl.ac.ACLineBuilder;



public class ACLineBuildTest {
	UniProtFactory factory =UniProtFactory.INSTANCE;
	ACLineBuilder builder = new ACLineBuilder();
	@Test
	public void test1() {
		String acLine ="AC   P99999; P00001; Q6NUR2; Q6NX69; Q96BV4;";
		List<UniProtAccession> acces = new ArrayList<>();
		acces.add(factory.createUniProtAccession("P99999"));
		acces.add(factory.createUniProtAccession("P00001"));
		acces.add(factory.createUniProtAccession("Q6NUR2"));
		acces.add(factory.createUniProtAccession("Q6NX69"));
		acces.add(factory.createUniProtAccession("Q96BV4"));
		FFLine ffLine = builder.build(acces);
		String resultString = ffLine.toString();
		assertEquals(acLine, resultString);
	}
	@Test
	public void testNewAc() {
		String acLine ="AC   A0A000ACJ5; P00001; Q6NUR2; Q6NX69; Q96BV4;";
		List<UniProtAccession> acces = new ArrayList<>();
		acces.add(factory.createUniProtAccession("A0A000ACJ5"));
		acces.add(factory.createUniProtAccession("P00001"));
		acces.add(factory.createUniProtAccession("Q6NUR2"));
		acces.add(factory.createUniProtAccession("Q6NX69"));
		acces.add(factory.createUniProtAccession("Q96BV4"));
		FFLine ffLine = builder.build(acces);
		String resultString = ffLine.toString();
		assertEquals(acLine, resultString);
		
	}
	@Test
	public void test2() {
		String acLine ="AC   Q96PK6; B0LM41; B3KMN4; D6RGD8; O75932; Q2PYN1; Q53GV1; Q68DQ9;\n" +
					"AC   Q96PK5; A0A000ACJ5; Q96BV4;";
		List<UniProtAccession> acces = new ArrayList<>();
		acces.add(factory.createUniProtAccession("Q96PK6"));
		acces.add(factory.createUniProtAccession("B0LM41"));
		acces.add(factory.createUniProtAccession("B3KMN4"));
		acces.add(factory.createUniProtAccession("D6RGD8"));
		acces.add(factory.createUniProtAccession("O75932"));
		
		acces.add(factory.createUniProtAccession("Q2PYN1"));
		acces.add(factory.createUniProtAccession("Q53GV1"));
		acces.add(factory.createUniProtAccession("Q68DQ9"));
		acces.add(factory.createUniProtAccession("Q96PK5"));
		acces.add(factory.createUniProtAccession("A0A000ACJ5"));
		acces.add(factory.createUniProtAccession("Q96BV4"));
		
		
		FFLine ffLine = builder.build(acces);
		String resultString = ffLine.toString();
		assertEquals(acLine, resultString);
		
	}
}
