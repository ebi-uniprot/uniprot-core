package org.uniprot.core.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceImpl;

/**
 * Created 25/01/19
 *
 * @author Edd
 */
public class SequenceBuilder implements Builder<SequenceBuilder, Sequence> {
    private String sequence;

    public SequenceBuilder(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public Sequence build() {
        return new SequenceImpl(sequence);
    }

    @Override
    public SequenceBuilder from(Sequence instance) {
        this.sequence = instance.getValue();
        return this;
    }
}
