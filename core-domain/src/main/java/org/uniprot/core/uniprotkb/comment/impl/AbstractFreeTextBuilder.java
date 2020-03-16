package org.uniprot.core.uniprotkb.comment.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.comment.FreeText;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public abstract class AbstractFreeTextBuilder<F extends FreeText> implements Builder<F> {
    protected List<EvidencedValue> evidencedValues = new ArrayList<>();

    @Override
    public @Nonnull F build() {
        return createConcreteInstance();
    }

    protected abstract @Nonnull F createConcreteInstance();
}
