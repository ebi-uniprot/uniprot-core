package org.uniprot.core.uniprot.evidence.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

/**
 * Created 14/01/19
 *
 * @author Edd
 */
public abstract class AbstractEvidencedValueBuilder<
                B extends AbstractEvidencedValueBuilder<B, E>, E extends EvidencedValue>
        implements Builder<B, E> {
    protected String value;
    protected List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull B from(@Nonnull E instance) {
        evidences.clear();
        return this.evidences(instance.getEvidences()).value(instance.getValue());
    }

    public @Nonnull B value(String value) {
        this.value = value;
        return getThis();
    }

    public @Nonnull B evidences(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return getThis();
    }

    public @Nonnull B addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return getThis();
    }

    protected abstract @Nonnull B getThis();
}
