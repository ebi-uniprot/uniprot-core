package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

public class NameImpl extends EvidencedValueImpl implements Name {
	public NameImpl(String value, List<Evidence> evidences) {
		super(value, evidences);
	}
}
