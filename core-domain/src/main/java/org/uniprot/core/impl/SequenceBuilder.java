package org.uniprot.core.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;

/**
 * Created 25/01/19
 *
 * @author Edd
 */
public class SequenceBuilder implements Builder<Sequence> {
    private String sequence;

    public SequenceBuilder(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public @Nonnull Sequence build() {
        return new SequenceImpl(sequence);
    }

    public static @Nonnull SequenceBuilder from(@Nonnull Sequence instance) {
        return new SequenceBuilder(instance.getValue());
    }
}
