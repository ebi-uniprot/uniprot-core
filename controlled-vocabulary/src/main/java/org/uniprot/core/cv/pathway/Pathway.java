package org.uniprot.core.cv.pathway;

import org.uniprot.core.cv.disease.CrossReference;

import java.util.List;

public interface Pathway {
	String getAccession();
	String getId();
	String getDefinition();
	String getPathwayClass();
	List<String> getSynonyms();
	List<Pathway> getIsAParents();
	List<Pathway> getPartOfParents();
	List<CrossReference> getCrossReferences();
}
