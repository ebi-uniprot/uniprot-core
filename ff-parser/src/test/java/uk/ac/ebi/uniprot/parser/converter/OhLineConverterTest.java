package uk.ac.ebi.uniprot.parser.converter;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.parser.impl.oh.OhLineConverter;
import uk.ac.ebi.uniprot.parser.impl.oh.OhLineObject;

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
		TestCase.assertEquals(9598, oh.getTaxonId().getTaxonId());
		
		TestCase.assertEquals("Pan troglodytes",
				oh.getOrganism().getScientificName());
		TestCase.assertEquals("Chimpanzee",
				oh.getOrganism().getCommonName());
	}
}
