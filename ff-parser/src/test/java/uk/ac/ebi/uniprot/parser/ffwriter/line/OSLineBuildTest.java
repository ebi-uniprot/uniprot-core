package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.uniprot.factory.OrganismFactory;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.impl.OSLineBuilder;



public class OSLineBuildTest {
	OSLineBuilder builder = new OSLineBuilder();
	private final OrganismFactory factory =OrganismFactory.INSTANCE;
	@Test
	public void test2(){
		String osLine = "OS   Rous sarcoma virus (strain Schmidt-Ruppin B) (RSV-SRB).";
		Organism organism = factory.createFromOrganismLine("Rous sarcoma virus (strain Schmidt-Ruppin B) (RSV-SRB)");
		
		FFLine ffLine = builder.build(organism);

		String resultString = ffLine.toString();
		//System.out.println(text.getText());
		System.out.println(resultString);
		assertEquals(osLine, resultString);
		
		
	}
	
	@Test
	public void test41(){
		
			String osLine ="OS   Methylobacterium extorquens (Methylobacterium dichloromethanicum)\n" +
                           "OS   (Protomonas extorquens).";
			Organism organism = factory.createFromOrganismLine("Methylobacterium extorquens (Methylobacterium dichloromethanicum) (Protomonas extorquens)");
			
			FFLine ffLine = builder.build(organism);

			String resultString = ffLine.toString();
			//System.out.println(text.getText());
			System.out.println(resultString);
			assertEquals(osLine, resultString);
			
	}
}
