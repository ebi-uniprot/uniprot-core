package uk.ac.ebi.uniprot.flatfile.parser.ffwriter.line;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.flatfile.parser.impl.OrganismNameLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.os.OSLineBuilder;

import static org.junit.Assert.assertEquals;



public class OSLineBuildTest {
	OSLineBuilder builder = new OSLineBuilder();
	@Test
	public void test2(){
		String osLine = "OS   Rous sarcoma virus (strain Schmidt-Ruppin B) (RSV-SRB).";
		OrganismName organism = OrganismNameLineParser.createFromOrganismLine("Rous sarcoma virus (strain Schmidt-Ruppin B) (RSV-SRB)");
		
		FFLine ffLine = builder.build(new OrganismBuilder().from(organism).build());

		String resultString = ffLine.toString();
		//System.out.println(text.getText());
		System.out.println(resultString);
		assertEquals(osLine, resultString);
		
		
	}
	
	@Test
	public void test41(){
		
			String osLine ="OS   Methylobacterium extorquens (Methylobacterium dichloromethanicum)\n" +
                           "OS   (Protomonas extorquens).";
			OrganismName organism = OrganismNameLineParser.createFromOrganismLine("Methylobacterium extorquens (Methylobacterium dichloromethanicum) (Protomonas extorquens)");
			
			FFLine ffLine = builder.build(new OrganismBuilder().from(organism).build());

			String resultString = ffLine.toString();
			//System.out.println(text.getText());
			System.out.println(resultString);
			assertEquals(osLine, resultString);
			
	}
}
