package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.factory.TaxonomyFactory;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.impl.os.OSLineBuilder;



public class OSLineBuildTest {
	OSLineBuilder builder = new OSLineBuilder();
	private final TaxonomyFactory factory =TaxonomyFactory.INSTANCE;
	@Test
	public void test2(){
		String osLine = "OS   Rous sarcoma virus (strain Schmidt-Ruppin B) (RSV-SRB).";
		OrganismName organism = factory.createFromOrganismLine("Rous sarcoma virus (strain Schmidt-Ruppin B) (RSV-SRB)");
		
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
			OrganismName organism = factory.createFromOrganismLine("Methylobacterium extorquens (Methylobacterium dichloromethanicum) (Protomonas extorquens)");
			
			FFLine ffLine = builder.build(organism);

			String resultString = ffLine.toString();
			//System.out.println(text.getText());
			System.out.println(resultString);
			assertEquals(osLine, resultString);
			
	}
}
