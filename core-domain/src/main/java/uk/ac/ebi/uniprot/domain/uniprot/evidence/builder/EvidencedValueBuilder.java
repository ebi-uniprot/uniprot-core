package uk.ac.ebi.uniprot.domain.uniprot.evidence.builder;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidencedValueImpl;

import static org.uniprot.core.common.Utils.nonNullList;

import java.util.List;

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
