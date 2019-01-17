package uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidencedValueImpl;

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
        this.evidences = evidences;
    }

    @Override
    protected EvidencedValueBuilder createBuilderInstance() {
        return new EvidencedValueBuilder();
    }

    @Override
    protected EvidencedValue createConcreteInstance() {
        return new EvidencedValueImpl(value, evidences);
    }

    @Override
    protected EvidencedValueBuilder getThis() {
        return this;
    }
}
