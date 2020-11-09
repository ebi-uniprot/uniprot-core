package org.uniprot.core.genecentric.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.fasta.impl.AbstractUniProtKBFastaBuilder;
import org.uniprot.core.genecentric.Protein;

/**
 * @author lgonzales
 * @since 15/10/2020
 */
public class ProteinBuilder extends AbstractUniProtKBFastaBuilder<ProteinBuilder, Protein> {

    public static @Nonnull ProteinBuilder from(@Nonnull Protein instance) {
        ProteinBuilder builder = new ProteinBuilder();
        return AbstractUniProtKBFastaBuilder.from(builder, instance);
    }

    public static @Nonnull ProteinBuilder from(@Nonnull UniProtKBFasta instance) {
        AbstractUniProtKBFastaBuilder builder = new ProteinBuilder();
        return (ProteinBuilder) AbstractUniProtKBFastaBuilder.from(builder, instance);
    }

    @Nonnull
    @Override
    public Protein build() {
        return new ProteinImpl(
                entryType,
                id,
                uniProtkbId,
                proteinName,
                organism,
                geneName,
                proteinExistence,
                flagType,
                sequence,
                sequenceVersion);
    }

    @Override
    protected ProteinBuilder getThis() {
        return this;
    }
}
