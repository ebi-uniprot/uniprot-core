package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NameImpl extends EvidencedValueImpl implements Name {
	@JsonCreator
	public NameImpl(
			@JsonProperty("value") String value, 
			@JsonProperty("evidences") List<Evidence> evidences) {
		super(value, evidences);
	}
	@JsonIgnore
	@Override
	public boolean isValid() {
		return !Strings.isNullOrEmpty(getValue());
	}
}
