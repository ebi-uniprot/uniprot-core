package uk.ac.ebi.uniprot.parser;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.Collection;
import java.util.List;

public interface HasEvidence {
	void clear();
	Collection<Evidence> getEvidences();
	void add(Collection<Evidence> ids);
	void addAll(Collection< List<Evidence> > idss);	
}
