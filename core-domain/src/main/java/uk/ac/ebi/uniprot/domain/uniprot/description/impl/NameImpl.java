package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

public class NameImpl extends EvidencedValueImpl implements Name {
	public NameImpl(String value, List<Evidence> evidences) {
		super(value, evidences);
	}
	@JsonIgnore
	@Override
	public boolean isValid() {
		return !Strings.isNullOrEmpty(getValue());
	}
}
