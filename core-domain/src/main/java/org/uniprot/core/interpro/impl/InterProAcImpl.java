package org.uniprot.core.interpro.impl;

import java.util.regex.Pattern;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.interpro.InterProAc;

/**
 * @author jluo
 * @date: 9 Apr 2021
 */
public class InterProAcImpl extends ValueImpl implements InterProAc {

    /** */
    private static final long serialVersionUID = -382302928805437261L;

    private static final String INTERPRO_ACC_REX = "^IPR\\d{6}";

    private static final Pattern INTERPRO_ACC_PATTERN =
            Pattern.compile(INTERPRO_ACC_REX, Pattern.CASE_INSENSITIVE);

    InterProAcImpl() {
        super(null);
    }

    InterProAcImpl(String value) {
        super(value);
    }

    @Override
    public boolean isValidId() {
        return INTERPRO_ACC_PATTERN.matcher(this.getValue()).matches();
    }
}
