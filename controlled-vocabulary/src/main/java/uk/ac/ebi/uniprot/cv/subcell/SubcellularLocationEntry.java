package uk.ac.ebi.uniprot.cv.subcell;

import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;

import java.util.List;
import java.util.Optional;

public interface SubcellularLocationEntry {
	SubcellLocationCategory getCategory();
	String getId();
	String getAccession();
	String getDefinition();
	String getContent();
	List<String> getSynonyms();
	Optional<Keyword> getKeyword();
	List<GeneOntology> getGeneOntologies();
	String getNote();
	List<String> getReferences();
	List<String> getLinks();

	List<SubcellularLocationEntry> getIsA();

	List<SubcellularLocationEntry> getPartOf();

	SubcellularLocationStatistics getStatistics();
}
