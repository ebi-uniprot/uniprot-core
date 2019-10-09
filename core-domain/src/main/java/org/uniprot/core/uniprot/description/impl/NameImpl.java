package org.uniprot.core.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;
import org.uniprot.core.util.Utils;

import static org.uniprot.core.util.Utils.notNullOrEmpty;

public class NameImpl extends EvidencedValueImpl implements Name {
    private static final long serialVersionUID = 6851897442612438068L;

    // no arg constructor for JSON deserialization
    NameImpl() {
        super("", Collections.emptyList());
    }

    public NameImpl(String value, List<Evidence> evidences) {
        super(value, evidences);
    }

    @Override
    public boolean isValid() {
        return notNullOrEmpty(getValue());
    }
}
