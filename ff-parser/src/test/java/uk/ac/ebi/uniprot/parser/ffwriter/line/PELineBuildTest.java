package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.impl.PELineBuilder;

public class PELineBuildTest {
	@Test
	public void test() {
		String peLine = "PE   1: Evidence at protein level;";
		PELineBuilder builder = new PELineBuilder();
		FFLine ffLine = builder.build(ProteinExistence.PROTEIN_LEVEL);
		String resultString = ffLine.toString();
		assertEquals(peLine, resultString);
	}
}
