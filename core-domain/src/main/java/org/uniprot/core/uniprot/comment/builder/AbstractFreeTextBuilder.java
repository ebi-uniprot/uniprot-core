package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.comment.FreeText;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public abstract class AbstractFreeTextBuilder<B extends AbstractFreeTextBuilder<B, F>, F extends FreeText> implements Builder<AbstractFreeTextBuilder, F> {
    protected List<EvidencedValue> evidencedValues = new ArrayList<>();

    @Override
    public F build() {
        return createConcreteInstance();
    }

    @Override
    public B from(F instance) {
        this.evidencedValues = nonNullList(instance.getTexts());
        return getThis();
    }

    protected abstract B getThis();
    protected abstract F createConcreteInstance();
}
