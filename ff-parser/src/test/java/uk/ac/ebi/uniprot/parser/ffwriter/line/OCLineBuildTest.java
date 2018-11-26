package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.factory.TaxonomyFactory;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.impl.oc.OCLineBuilder;


public class OCLineBuildTest {
	OCLineBuilder builder = new OCLineBuilder();
	@Test
	public void testHuman(){
	
		String ocLine ="OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;"+
				"\nOC   Mammalia; Eutheria; Euarchontoglires; Primates; Catarrhini; Hominidae;" +
				"\nOC   Homo.";
		List<OrganismName> taxonNames = new ArrayList<>();
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Eukaryota"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Metazoa"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Chordata"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Craniata"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Vertebrata"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Euteleostomi"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Mammalia"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Eutheria"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Euarchontoglires"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Primates"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Catarrhini"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Hominidae"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createOrganismName("Homo"));
		FFLine ffLine = builder.build(taxonNames);
		String resultString = ffLine.toString();
		assertEquals(ocLine, resultString);
	
	}

	
}
