package org.uniprot.core.uniprotkb.impl;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.util.regex.Pattern;

public class UniProtKBAccessionImpl extends ValueImpl implements UniProtKBAccession {
    private static final long serialVersionUID = 5516352352872264537L;
    private static final String UNIPROT_ACC_REX =
            "([O,P,Q][0-9][A-Z, 0-9]{3}[0-9]|[A-N,R-Z]([0-9][A-Z][A-Z, 0-9]{2}){1,2}[0-9])(-\\d+)*";
    private static final Pattern UNIPROT_ACC_PATTERN =
            Pattern.compile(UNIPROT_ACC_REX, Pattern.CASE_INSENSITIVE);

    // no arg constructor for JSON deserialization
    UniProtKBAccessionImpl() {
        super(null);
    }

    UniProtKBAccessionImpl(String value) {
        super(value);
    }

    @Override
    public boolean isValidAccession() {
        return UNIPROT_ACC_PATTERN.matcher(this.getValue()).matches();
    }
}
