package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UniProtAccessionImpl extends ValueImpl implements UniProtAccession {
    private static final String UNIPROT_ACC_REX =
            "([O,P,Q][0-9][A-Z, 0-9]{3}[0-9]|[A-N,R-Z]([0-9][A-Z][A-Z, 0-9]{2}){1,2}[0-9])(-\\d+)*";
    private static final Pattern UNIPROT_ACC_PATTERN = Pattern.compile(UNIPROT_ACC_REX, Pattern.CASE_INSENSITIVE);
    
	@JsonCreator
    public UniProtAccessionImpl(@JsonProperty("value") String value) {
        super(value);
    }
	@JsonIgnore
    @Override
    public boolean isValidAccession() {
        return UNIPROT_ACC_PATTERN.matcher(this.getValue()).matches();
    }

}
