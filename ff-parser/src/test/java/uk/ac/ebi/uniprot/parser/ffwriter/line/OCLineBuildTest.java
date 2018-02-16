package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;
import uk.ac.ebi.uniprot.domain.uniprot.factory.TaxonomyFactory;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.impl.OCLineBuilder;


public class OCLineBuildTest {
	OCLineBuilder builder = new OCLineBuilder();
	@Test
	public void testHuman(){
	
		String ocLine ="OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;"+
				"\nOC   Mammalia; Eutheria; Euarchontoglires; Primates; Catarrhini; Hominidae;" +
				"\nOC   Homo.";
		List<TaxonName> taxonNames = new ArrayList<>();
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Eukaryota"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Metazoa"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Chordata"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Craniata"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Vertebrata"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Euteleostomi"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Mammalia"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Eutheria"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Euarchontoglires"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Primates"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Catarrhini"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Hominidae"));
		taxonNames.add(TaxonomyFactory.INSTANCE.createTaxonName("Homo"));
		FFLine ffLine = builder.build(taxonNames);
		String resultString = ffLine.toString();
		assertEquals(ocLine, resultString);
	
	}

	
}
