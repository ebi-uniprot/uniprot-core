package uk.ac.ebi.uniprot.domain.uniprot.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SourceLineImpl  extends ValueImpl implements SourceLine{
	 @JsonCreator
    public SourceLineImpl(@JsonProperty("value") String value) {
        super(value);
    }
}
