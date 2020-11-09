package org.uniprot.core.fasta.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.fasta.ProteinFasta;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 21/10/2020
 */
public abstract class AbstractProteinFastaBuilder<
                B extends AbstractProteinFastaBuilder<B, T>, T extends ProteinFasta>
        implements Builder<T> {

    protected String id;
    protected Sequence sequence;

    public @Nonnull B id(String id) {
        this.id = id;
        return getThis();
    }

    public @Nonnull B sequence(String sequence) {
        this.sequence = new SequenceBuilder(Utils.emptyOrString(sequence)).build();
        return getThis();
    }

    public @Nonnull B sequence(Sequence sequence) {
        this.sequence = sequence;
        return getThis();
    }

    abstract B getThis();

    protected static <B extends AbstractProteinFastaBuilder<B, T>, T extends ProteinFasta> B from(
            @Nonnull B builder, @Nonnull T instance) {
        return builder.id(instance.getId()).sequence(instance.getSequence());
    }
}
