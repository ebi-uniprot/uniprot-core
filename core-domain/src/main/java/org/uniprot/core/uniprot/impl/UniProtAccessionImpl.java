package org.uniprot.core.uniprot.impl;

import java.util.regex.Pattern;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniprot.UniProtAccession;

public class UniProtAccessionImpl extends ValueImpl implements UniProtAccession {
    private static final long serialVersionUID = 5516352352872264537L;
    private static final String UNIPROT_ACC_REX =
            "([O,P,Q][0-9][A-Z, 0-9]{3}[0-9]|[A-N,R-Z]([0-9][A-Z][A-Z, 0-9]{2}){1,2}[0-9])(-\\d+)*";
    private static final Pattern UNIPROT_ACC_PATTERN =
            Pattern.compile(UNIPROT_ACC_REX, Pattern.CASE_INSENSITIVE);

    // no arg constructor for JSON deserialization
    UniProtAccessionImpl() {
        super(null);
    }

    public UniProtAccessionImpl(String value) {
        super(value);
    }

    @Override
    public boolean isValidAccession() {
        return UNIPROT_ACC_PATTERN.matcher(this.getValue()).matches();
    }
}
