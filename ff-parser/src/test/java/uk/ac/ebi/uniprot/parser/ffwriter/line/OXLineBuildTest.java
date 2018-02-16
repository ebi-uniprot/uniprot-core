package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.impl.OXLineBuilder;



public class OXLineBuildTest {
	OXLineBuilder builder = new OXLineBuilder();
	@Test
	public void test(){
		String oxLine ="OX   NCBI_TaxID=9606;";
		
		UniProtTaxonId taxId =UniProtFactory.INSTANCE.createUniProtTaxonId(9606, Collections.emptyList());
		
		FFLine ffLine = builder.build(taxId);
		
		String resultString = ffLine.toString();
		//System.out.println(text.getText());
		System.out.println(resultString);
		assertEquals(oxLine, resultString);
	}
}
