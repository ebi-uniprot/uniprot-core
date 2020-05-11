package org.uniprot.core.uniprotkb.description.impl;

import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueImpl;
import org.uniprot.core.util.Utils;

import java.util.Collections;
import java.util.List;

public class NameImpl extends EvidencedValueImpl implements Name {
    private static final long serialVersionUID = 6851897442612438068L;

    // no arg constructor for JSON deserialization
    NameImpl() {
        super("", Collections.emptyList());
    }

    NameImpl(String value, List<Evidence> evidences) {
        super(value, evidences);
    }

    @Override
    public boolean isValid() {
        return Utils.notNullNotEmpty(getValue());
    }
}
