package uk.ac.ebi.uniprot.flatfile.parser.ffwriter.line;

import org.junit.Test;
import org.uniprot.core.uniprot.ProteinExistence;

import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.flatfile.parser.impl.pe.PELineBuilder;

import static org.junit.Assert.assertEquals;

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
