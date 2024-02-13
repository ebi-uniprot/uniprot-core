package org.uniprot.core.fasta.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.genecentric.Protein;

/**
 * @author lgonzales
 * @since 21/10/2020
 */
public class UniProtKBFastaBuilder
        extends AbstractUniProtKBFastaBuilder<UniProtKBFastaBuilder, UniProtKBFasta> {

    public static @Nonnull UniProtKBFastaBuilder from(@Nonnull UniProtKBFasta instance) {
        UniProtKBFastaBuilder builder = new UniProtKBFastaBuilder();
        return AbstractUniProtKBFastaBuilder.from(builder, instance);
    }

    public static @Nonnull UniProtKBFastaBuilder from(@Nonnull Protein instance) {
        AbstractUniProtKBFastaBuilder builder = new UniProtKBFastaBuilder();
        return (UniProtKBFastaBuilder) AbstractUniProtKBFastaBuilder.from(builder, instance);
    }

    @Nonnull
    @Override
    public UniProtKBFasta build() {
        return new UniProtKBFastaImpl(
                entryType,
                id,
                uniProtkbId,
                proteinName,
                organism,
                geneName,
                proteinExistence,
                flagType,
                sequence,
                sequenceVersion,
                sequenceRange);
    }

    @Override
    protected UniProtKBFastaBuilder getThis() {
        return this;
    }
}
