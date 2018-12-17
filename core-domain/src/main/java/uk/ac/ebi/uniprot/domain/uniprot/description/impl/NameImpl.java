package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

import java.util.Collections;
import java.util.List;

public class NameImpl extends EvidencedValueImpl implements Name {

	private NameImpl(){
		super("", Collections.emptyList());
	}

	public NameImpl(String value, List<Evidence> evidences) {
		super(value, evidences);
	}

	@Override
	public boolean isValid() {
		return getValue() != null && !getValue().isEmpty();
	}
}
