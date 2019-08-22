package org.uniprot.core.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;

public class NameImpl extends EvidencedValueImpl implements Name {
    private static final long serialVersionUID = 6851897442612438068L;

    private NameImpl() {
        super("", Collections.emptyList());
    }

    public NameImpl(String value, List<Evidence> evidences) {
        super(value, evidences);
    }

    @Override
    public boolean isValid() {
        return getValue() != null && !getValue().isEmpty();
    }
}