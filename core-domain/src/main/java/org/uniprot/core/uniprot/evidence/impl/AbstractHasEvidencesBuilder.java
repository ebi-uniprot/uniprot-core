package org.uniprot.core.uniprot.evidence.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public abstract class AbstractHasEvidencesBuilder<
                B extends AbstractHasEvidencesBuilder<B, E>, E extends HasEvidences>
        implements Builder<E> {

    protected List<Evidence> evidences = new ArrayList<>();

    public @Nonnull B evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return getThis();
    }

    public @Nonnull B evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return getThis();
    }

    protected abstract @Nonnull B getThis();

    protected static <B extends AbstractHasEvidencesBuilder<B, E>, E extends HasEvidences>
            void init(@Nonnull B builder, @Nonnull E instance) {
        builder.evidencesSet(instance.getEvidences());
    }
}
