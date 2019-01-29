package uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

/**
 * Created 14/01/19
 *
 * @author Edd
 */
public abstract class AbstractEvidencedValueBuilder<B extends AbstractEvidencedValueBuilder<B, E>, E extends EvidencedValue> implements Builder2<B, E> {
    protected String value;
    protected List<Evidence> evidences = new ArrayList<>();

    @Override
    public B from(E instance) {
        evidences.clear();
        return this
                .evidences(instance.getEvidences())
                .value(instance.getValue());
    }

    public B value(String value) {
        this.value = value;
        return getThis();
    }

    public B evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return getThis();
    }

    public B addEvidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return getThis();
    }

    public String getValue() {
        return value;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    protected abstract B getThis();
}
