package uk.ac.ebi.uniprot.parser;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;



public interface HasEvidence {
	void clear();
	Collection<Evidence> getEvidences();
	void add(Collection<Evidence> ids);
	void addAll(Collection< List<Evidence> > idss);	
}
