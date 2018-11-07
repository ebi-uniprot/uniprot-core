package uk.ac.ebi.uniprot.parser.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.parser.HasEvidence;



public class EvidenceCollector implements HasEvidence {
	private Collection<Evidence> evidences = new HashSet<>();
	
	public Collection<Evidence> getEvidences() {
		return evidences;
	}
	@Override
	public void clear() {
		this.evidences.clear();
	}


	@Override
	public void add(Collection<Evidence> evidences) {
		this.evidences.addAll(evidences);
	}

	@Override
	public void addAll(Collection<List<Evidence>> evidences) {
		for(List<Evidence> ids:evidences){
			add(ids);
		}
	}

}
