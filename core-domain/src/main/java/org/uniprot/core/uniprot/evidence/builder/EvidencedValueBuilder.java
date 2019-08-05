package org.uniprot.core.uniprot.evidence.builder;

import static org.uniprot.core.util.Utils.nonNullList;

import java.util.List;

import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class EvidencedValueBuilder extends AbstractEvidencedValueBuilder<EvidencedValueBuilder, EvidencedValue> {
    private EvidencedValueBuilder() {}

    public EvidencedValueBuilder(String value, List<Evidence> evidences) {
        this.value = value;
        this.evidences = nonNullList(evidences);
    }

    @Override
    public EvidencedValue build() {
        return new EvidencedValueImpl(value, evidences);
    }

    @Override
    protected EvidencedValueBuilder getThis() {
        return this;
    }
}
