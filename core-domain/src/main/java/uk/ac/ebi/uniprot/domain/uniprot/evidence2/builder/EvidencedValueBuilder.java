package uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidencedValueImpl;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class EvidencedValueBuilder extends AbstractEvidencedValueBuilder<EvidencedValueBuilder, EvidencedValue> {
    @Override
    protected EvidencedValueBuilder createBuilderInstance() {
        return new EvidencedValueBuilder();
    }

    @Override
    protected EvidencedValue createConcreteInstance() {
        return new EvidencedValueImpl(value, evidences);
    }
}
