package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.comment.FreeText;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public abstract class AbstractFreeTextBuilder<
                B extends AbstractFreeTextBuilder<B, F>, F extends FreeText>
        implements Builder<AbstractFreeTextBuilder, F> {
    protected List<EvidencedValue> evidencedValues = new ArrayList<>();

    @Override
    public @Nonnull F build() {
        return createConcreteInstance();
    }

    @Override
    public @Nonnull B from(@Nonnull F instance) {
        this.evidencedValues = modifiableList(instance.getTexts());
        return getThis();
    }

    protected abstract @Nonnull B getThis();

    protected abstract @Nonnull F createConcreteInstance();
}
