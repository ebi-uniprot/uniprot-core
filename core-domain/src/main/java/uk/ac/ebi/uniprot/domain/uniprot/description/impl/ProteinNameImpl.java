package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

public class ProteinNameImpl extends EvidencedValueImpl implements ProteinName {
	public ProteinNameImpl(String value, List<Evidence> evidences) {
		super(value, evidences);
	}
}
