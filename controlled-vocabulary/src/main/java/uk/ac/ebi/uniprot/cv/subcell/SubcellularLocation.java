package uk.ac.ebi.uniprot.cv.subcell;

import java.util.List;
import java.util.Optional;

import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;

public interface SubcellularLocation {
	SubcellLocationType getType();
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
	List<SubcellularLocation> getIsA();
	List<SubcellularLocation> getPartOf();
	
}
