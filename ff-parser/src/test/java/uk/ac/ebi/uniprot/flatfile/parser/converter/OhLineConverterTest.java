package uk.ac.ebi.uniprot.flatfile.parser.converter;

import junit.framework.TestCase;
import org.junit.Test;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;

import uk.ac.ebi.uniprot.flatfile.parser.impl.oh.OhLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oh.OhLineObject;

import java.util.List;

public class OhLineConverterTest {
	@Test
	public void test(){
		//"OH   NCBI_TaxID=9598; Pan troglodytes (Chimpanzee).
		OhLineObject ohO = new OhLineObject();
		OhLineObject.OhValue ohV = new OhLineObject.OhValue();
		ohV.tax_id = 9598;
		ohV.hostname ="Pan troglodytes (Chimpanzee)";
		ohO.hosts.add(ohV);
		OhLineConverter converter = new OhLineConverter();
		List<OrganismHost> ohs=converter.convert(ohO);
		TestCase.assertEquals(1, ohs.size());
		OrganismHost oh = ohs.get(0);
		TestCase.assertEquals(9598, oh.getTaxonId());
		
		TestCase.assertEquals("Pan troglodytes", oh.getScientificName());
		TestCase.assertEquals("Chimpanzee", oh.getCommonName());
	}
}
