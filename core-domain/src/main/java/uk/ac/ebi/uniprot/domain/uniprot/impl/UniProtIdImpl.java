package uk.ac.ebi.uniprot.domain.uniprot.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UniProtIdImpl extends ValueImpl implements UniProtId {
	@JsonCreator
    public UniProtIdImpl(@JsonProperty("value") String value) {
        super(value);
    }

}
