package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

public class ECNumberImpl extends EvidencedValueImpl implements ECNumber {

	public ECNumberImpl(String value, List<Evidence> evidences) {
		super(value, evidences);
	}

}
