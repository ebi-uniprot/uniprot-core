package uk.ac.ebi.uniprot.flatfile.parser.ffwriter.line;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ox.OXLineBuilder;

import static org.junit.Assert.assertEquals;



public class OXLineBuildTest {
	OXLineBuilder builder = new OXLineBuilder();
	@Test
	public void test(){
		String oxLine ="OX   NCBI_TaxID=9606;";
		
		Organism taxId = new OrganismBuilder().taxonId(9606).build();
		
		FFLine ffLine = builder.build(taxId);
		
		String resultString = ffLine.toString();
		//System.out.println(text.getText());
		System.out.println(resultString);
		assertEquals(oxLine, resultString);
	}
}
