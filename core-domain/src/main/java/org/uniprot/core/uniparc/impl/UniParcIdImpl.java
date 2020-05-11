package org.uniprot.core.uniparc.impl;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniparc.UniParcId;

import java.util.regex.Pattern;

/**
 * @author jluo
 * @date: 22 May 2019
 */
public class UniParcIdImpl extends ValueImpl implements UniParcId {

    /** */
    private static final long serialVersionUID = -6122456799951248540L;

    private static final String UNIPARC_ACC_REX = "^UPI\\w{10}";
    private static final Pattern UNIPARC_ACC_PATTERN =
            Pattern.compile(UNIPARC_ACC_REX, Pattern.CASE_INSENSITIVE);

    UniParcIdImpl() {
        super(null);
    }

    UniParcIdImpl(String value) {
        super(value);
    }

    @Override
    public boolean isValidId() {
        return UNIPARC_ACC_PATTERN.matcher(this.getValue()).matches();
    }
}
