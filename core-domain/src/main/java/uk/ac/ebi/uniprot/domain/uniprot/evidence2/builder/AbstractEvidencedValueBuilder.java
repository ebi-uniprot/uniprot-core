package uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidencedValueImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 14/01/19
 *
 * @author Edd
 */
public abstract class AbstractEvidencedValueBuilder<B extends AbstractEvidencedValueBuilder<B>> implements Builder2<B, EvidencedValue> {
    private String value;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public EvidencedValue build() {
        return new EvidencedValueImpl(value, evidences);
    }

    @Override
    public B from(EvidencedValue instance) {
        return  createInstance()
                .evidences(instance.getEvidences())
                .value(instance.getValue());
    }

    public B value(String value) {
        this.value = value;
        return (B) this;
    }

    public B evidences(List<Evidence> evidences) {
        this.evidences.addAll(evidences);
        return (B) this;
    }

    public B addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
        return (B) this;
    }

    public String getValue() {
        return value;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    protected abstract B createInstance();
}
