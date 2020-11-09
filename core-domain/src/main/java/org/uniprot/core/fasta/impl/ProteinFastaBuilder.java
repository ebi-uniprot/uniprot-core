package org.uniprot.core.fasta.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.fasta.ProteinFasta;

/**
 * @author lgonzales
 * @since 21/10/2020
 */
public class ProteinFastaBuilder
        extends AbstractProteinFastaBuilder<ProteinFastaBuilder, ProteinFasta> {

    public static @Nonnull ProteinFastaBuilder from(@Nonnull ProteinFasta instance) {
        ProteinFastaBuilder builder = new ProteinFastaBuilder();
        return AbstractProteinFastaBuilder.from(builder, instance);
    }

    @Nonnull
    @Override
    public ProteinFasta build() {
        return new ProteinFastaImpl(id, sequence);
    }

    @Override
    ProteinFastaBuilder getThis() {
        return this;
    }
}
