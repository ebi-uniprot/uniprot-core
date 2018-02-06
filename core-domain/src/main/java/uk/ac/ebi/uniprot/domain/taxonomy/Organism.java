package uk.ac.ebi.uniprot.domain.taxonomy;

import java.util.List;

public interface Organism {
	    String getScientificName();
	    String getCommonName();
	    List<String> getSynonyms();
}
