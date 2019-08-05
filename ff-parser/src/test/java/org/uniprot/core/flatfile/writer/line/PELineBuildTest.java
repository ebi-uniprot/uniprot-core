package org.uniprot.core.flatfile.writer.line;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.impl.pe.PELineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprot.ProteinExistence;

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
