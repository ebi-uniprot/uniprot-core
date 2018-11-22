package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeywordImpl extends EvidencedValueImpl implements Keyword {

	@JsonCreator
	public KeywordImpl(
			@JsonProperty("value") String value, 
			@JsonProperty("evidences") List<Evidence> evidences)  {
        super(value, evidences);
    }

}
