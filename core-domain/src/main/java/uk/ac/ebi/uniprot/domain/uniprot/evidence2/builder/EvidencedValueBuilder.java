package uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidencedValueImpl;

import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class EvidencedValueBuilder extends AbstractEvidencedValueBuilder<EvidencedValueBuilder, EvidencedValue> {
    private EvidencedValueBuilder() {}

    public EvidencedValueBuilder(String value, List<Evidence> evidences) {
        this.value = value;
        nonNullAddAll(evidences, this.evidences);
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
