package uk.ac.ebi.uniprot.parser.ffwriter.line;

import org.junit.Test;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.impl.oc.OCLineBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class OCLineBuildTest {
	OCLineBuilder builder = new OCLineBuilder();
	@Test
	public void testHuman(){
	
		String ocLine ="OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;"+
				"\nOC   Mammalia; Eutheria; Euarchontoglires; Primates; Catarrhini; Hominidae;" +
				"\nOC   Homo.";
		List<String> taxonNames = new ArrayList<>();
		taxonNames.add("Eukaryota");
		taxonNames.add("Metazoa");
		taxonNames.add("Chordata");
		taxonNames.add("Craniata");
		taxonNames.add("Vertebrata");
		taxonNames.add("Euteleostomi");
		taxonNames.add("Mammalia");
		taxonNames.add("Eutheria");
		taxonNames.add("Euarchontoglires");
		taxonNames.add("Primates");
		taxonNames.add("Catarrhini");
		taxonNames.add("Hominidae");
		taxonNames.add("Homo");
		FFLine ffLine = builder.build(taxonNames);
		String resultString = ffLine.toString();
		assertEquals(ocLine, resultString);
	
	}

	
}
