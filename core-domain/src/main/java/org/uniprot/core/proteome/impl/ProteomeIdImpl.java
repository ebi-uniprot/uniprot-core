package org.uniprot.core.proteome.impl;

import java.util.regex.Pattern;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.proteome.ProteomeId;

public class ProteomeIdImpl extends ValueImpl implements ProteomeId {

    private static final String PROTEOME_ID_REX = "UP[0-9]{9}";
    private static final Pattern PROTEOME_ID_PATTERN =
            Pattern.compile(PROTEOME_ID_REX, Pattern.CASE_INSENSITIVE);

    private static final long serialVersionUID = -2119733175394446567L;

    private ProteomeIdImpl() {
        super(null);
    }

    public ProteomeIdImpl(String value) {
        super(value);
    }

    @Override
    public boolean isValidId() {
        return PROTEOME_ID_PATTERN.matcher(this.getValue()).matches();
    }
}
